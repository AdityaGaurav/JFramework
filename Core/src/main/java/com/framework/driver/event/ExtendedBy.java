package com.framework.driver.event;

import com.framework.utils.error.PreConditions;
import org.openqa.selenium.*;

import java.util.List;

import static java.util.Arrays.asList;


//todo: documentation

@Deprecated
public abstract class ExtendedBy extends org.openqa.selenium.By
{

	//region ExtendedBy - Static Methods Section.

	/**
	 * Finds elements by the presence of an attribute name irrespective of element name. Currently implemented via XPath.
	 *
	 * @param name The attribute name.
	 *
	 * @return a {@linkplain ByAttribute} instance
	 *
	 * @throws NullPointerException     if {@code name} is null.
	 * @throws IllegalArgumentException if( {@code nme} is empty or blank.
	 */
	public static ByAttribute attribute( final String name )
	{
		return attribute( name, null );
	}

	/**
	 * Finds elements by an named attribute matching a given value, irrespective of element name. Currently implemented via XPath.
	 *
	 * @param name  The attribute name.
	 * @param value The attribute value.
	 *
	 * @return a {@linkplain ByAttribute} instance
	 *
	 * @throws NullPointerException     if {@code name} is null.
	 * @throws IllegalArgumentException if( {@code nme} is empty or blank.
	 *
	 */
	public static ByAttribute attribute( final String name, final String value )
	{
		final String ERR_MSG1 = "Cannot find elements when the attribute name is null, blank or empty";
		final String ERR_MSG2 = "Cannot find elements when the value name is null. use StringUtils.EMPTY";

		PreConditions.checkNotNullNotBlankOrEmpty( name, ERR_MSG1 );
		PreConditions.checkNotNull( value, ERR_MSG2 );
		return new ByAttribute( name, value );
	}

	/**
	 * Finds elements by an named attribute not being present in the element,
	 * irrespective of element name. Currently implemented via XPath.
	 *
	 * @param name The attribute name.
	 *
	 * @return a {@linkplain NotByAttribute} instance
	 */
	public static ByAttribute notAttribute( final String name )
	{
		final String ERR_MSG = "Cannot find elements when the attribute name is null, blank or empty";
		PreConditions.checkNotNullNotBlankOrEmpty( name, ERR_MSG );
		return new NotByAttribute( name );
	}

	//todo: method documentation
	public static ByComposite composite( final org.openqa.selenium.By ... bys )
	{
		final String ERR_MSG1 = "Cannot make composite with no varargs of Bys";
		final String ERR_MSG2 = "Only 2 Bys are allowed to create a ByComposite instance";
		final String ERR_MSG3 = "First ExtendedBy should be instanceof %s";
		final String ERR_MSG4 = "First ExtendedBy should be instanceof ExtendedBy.ByClassName Or instanceof ExtendedBy.ByAttribute";

		PreConditions.checkNotNull( bys, ERR_MSG1 );
		PreConditions.checkState( bys.length == 2, ERR_MSG2 );
		PreConditions.checkInstanceOf( ByTagName.class, bys[ 0 ], ERR_MSG3, ByTagName.class.getName() );
		final boolean condition4 = ( bys[ 1 ] instanceof ByClassName ) || ( bys[ 1 ] instanceof ByAttribute );
		PreConditions.checkState( condition4, ERR_MSG4, ByClassName.class.getName(), ByAttribute.class.getName() );
		return new ByComposite( bys );
	}

	//todo: method documentation
	public static ByJquerySelector jQuery( final String selectorExpression )
	{
		final String ERR_MSG = "Cannot find elements when selector name is null, blank or empty";
		PreConditions.checkNotNullNotBlankOrEmpty( selectorExpression, ERR_MSG );
		return new ByJquerySelector( selectorExpression );
	}

	//endregion


	//region ExtendedBy - ByAttribute Class Section.

	public static class ByAttribute extends ExtendedBy
	{
		protected final String name;

		private final String value;

		ByAttribute( String name, String value )
		{
			this.name = name;
			this.value = value;
		}

		@Override
		public WebElement findElement( SearchContext context )
		{
			return makeByXPath().findElement( context );
		}

		@Override
		public List<WebElement> findElements( SearchContext context )
		{
			return makeByXPath().findElements( context );
		}

		@Override
		public String toString()
		{
			return "EventBy.attribute: " + name + val();
		}

		protected String nameAndValue()
		{
			return "@" + name + val();
		}

		private By makeByXPath()
		{
			return By.xpath( makeXPath() );
		}

		private String makeXPath()
		{
			return ".//*[" + nameAndValue() + "]";
		}

		private String val()
		{
			return value == null ? "" : " = '" + value + "'";
		}
	}

	//endregion


	//region ExtendedBy - NotByAttribute Class Section.

	public static class NotByAttribute extends ByAttribute
	{
		NotByAttribute( String name )
		{
			super( name, null );
		}

		public String toString()
		{
			return "EventBy.notAttribute: " + name;
		}

		@Override
		protected String nameAndValue()
		{
			return "not(" + super.nameAndValue() + ")";
		}
	}

	//endregion


	//region ExtendedBy - ByComposite Class Section.

	public static class ByComposite extends ExtendedBy
	{
		private final By[] bys;

		ByComposite( By... bys )
		{
			this.bys = bys;
		}

		@Override
		public List<WebElement> findElements( SearchContext context )
		{
			return makeByXPath().findElements( context );
		}

		@Override
		public WebElement findElement( SearchContext context )
		{
			return makeByXPath().findElement( context );
		}

		@Override
		public String toString()
		{
			return "EventBy.composite(" + asList( bys ) + ")";
		}

		private String containingWord( String attribute, String word )
		{
			return "contains(concat(' ',normalize-space(@" + attribute + "),' '),' " + word + " ')";
		}

		private By makeByXPath()
		{
			String xpathExpression = makeXPath();
			return By.xpath( xpathExpression );
		}

		private String makeXPath()
		{
			String xpathExpression = ".//" + getTagName();

			if ( bys[ 1 ] instanceof ByClassName )
			{
				String className = bys[ 1 ].toString().substring( "ExtendedBy.className: ".length() );
				xpathExpression = xpathExpression + "[" + containingWord( "class", className ) + "]";
			}
			else if ( bys[ 1 ] instanceof ByAttribute )
			{
				ByAttribute by = ( ByAttribute ) bys[ 1 ];
				xpathExpression = xpathExpression + "[" + by.nameAndValue() + "]";
			}
			return xpathExpression;
		}

		private String getTagName()
		{
			return bys[ 0 ].toString().substring( "ExtendedBy.tagName: ".length() );
		}
	}

	//endregion


	//region ExtendedBy - ByJQuerySelector Class Section.

	public static class ByJquerySelector extends ExtendedBy
	{
		private static final char DOUBLE_QUOTE = '"';

		private static final String SINGLE_QUOTE = "'";

		private final String jQuerySelector;

		public ByJquerySelector( final String selector )
		{
			this.jQuerySelector = selector;
		}

		@Override
		public List<WebElement> findElements( SearchContext context )
		{
			String jquery = "return $(" + quoted( jQuerySelector ) + ").get();";
			Object o = ( ( JavascriptExecutor ) context ).executeScript( jquery );
			if( o.getClass().isAssignableFrom( List.class ) )
			{
				return ( List<WebElement> ) o;
			}

			return null;
		}

		public WebElement findElement( SearchContext context )
		{
			String jquery = "return $(" + quoted( jQuerySelector ) + ").get(0);";
			WebElement element = ( WebElement ) ( ( JavascriptExecutor ) context ).executeScript( jquery );
			if ( element == null )
			{
				throw new NoSuchElementException( "No element found matching JQuery selector " + jQuerySelector );
			}
			else
			{
				return element;
			}
		}

		@Override
		public String toString() {
			return "ExtendedBy.JquerySelector: " + jQuerySelector;
		}

		private String quoted( final String jQuerySelector )
		{
			if ( jQuerySelector.contains( "'" ) )
			{
				return DOUBLE_QUOTE + jQuerySelector + '"';
			}
			else
			{
				return "'" + jQuerySelector + SINGLE_QUOTE;
			}
		}
	}

	//endregion
}
