package com.framework.driver.utils.ui;

import com.framework.driver.exceptions.PreConditionException;
import com.framework.driver.utils.PreConditions;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static java.util.Arrays.asList;


//todo: documentation

public abstract class ExtendedBy extends By
{

	//region ExtendedBy - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( ExtendedBy.class );

	//endregion


	/**
	 * Finds elements by the presence of an attribute name irrespective of element name. Currently implemented via XPath.
	 *
	 * @param name The attribute name.
	 *
	 * @return a {@linkplain com.framework.driver.utils.ui.ExtendedBy.ByAttribute} instance
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
	 * @return a {@linkplain com.framework.driver.utils.ui.ExtendedBy.ByAttribute} instance
	 *
	 * @throws NullPointerException     if {@code name} is null.
	 * @throws IllegalArgumentException if( {@code nme} is empty or blank.
	 *
	 * @see com.framework.driver.utils.ui.ExtendedBy.ByAttribute
	 */
	public static ByAttribute attribute( final String name, final String value )
	{
		final String ERR_MSG1 = "Cannot find elements when the attribute name is null, blank or empty";
		final String ERR_MSG2 = "Cannot find elements when the value name is null. use StringUtils.EMPTY";
		try
		{
			PreConditions.checkNotNullNotBlankOrEmpty( name, ERR_MSG1 );
			PreConditions.checkNotNull( value, ERR_MSG2 );
			return new ByAttribute( name, value );
		}
		catch ( IllegalArgumentException e )
		{
			logger.error( "throwing a new PreConditionException on ExtendedBy#attribute." );
			throw new PreConditionException( e.getMessage(), e );
		}
	}

	/**
	 * Finds elements by an named attribute not being present in the element,
	 * irrespective of element name. Currently implemented via XPath.
	 *
	 * @param name The attribute name.
	 *
	 * @return a {@linkplain com.framework.driver.utils.ui.ExtendedBy.NotByAttribute} instance
	 */
	public static ByAttribute notAttribute( final String name )
	{
		final String ERR_MSG = "Cannot find elements when the attribute name is null, blank or empty";
		try
		{
			PreConditions.checkNotNullNotBlankOrEmpty( name, ERR_MSG );
			return new NotByAttribute( name );
		}
		catch ( IllegalArgumentException e )
		{
			logger.error( "throwing a new PreConditionException on ExtendedBy#attribute." );
			throw new PreConditionException( e.getMessage(), e );
		}
	}

	//todo: documentation
	public static ByComposite composite( final By... bys )
	{
		final String ERR_MSG1 = "Cannot make composite with no varargs of Bys";
		final String ERR_MSG2 = "Only 2 Bys are allowed to create a ByComposite instance";
		final String ERR_MSG3 = "First By should be instanceof %s";
		final String ERR_MSG4 = "First By should be instanceof By.ByClassName Or instanceof By.ByAttribute";

		try
		{
			PreConditions.checkNotNull( bys, ERR_MSG1 );
			PreConditions.checkState( bys.length == 2, ERR_MSG2 );
			PreConditions.checkInstanceOf( ByTagName.class, bys[ 0 ], ERR_MSG3, ByTagName.class.getName() );
			final boolean condition4 = ( bys[ 1 ] instanceof ByClassName ) || ( bys[ 1 ] instanceof ByAttribute );
			PreConditions.checkState( condition4, ERR_MSG4, ByClassName.class.getName(), ByAttribute.class.getName() );
			return new ByComposite( bys );
		}
		catch ( NullPointerException | IllegalStateException e )
		{
			logger.error( "throwing a new PreConditionException on ExtendedBy#composite." );
			throw new PreConditionException( e.getMessage(), e );
		}
	}

	//todo: documentation
	public static ByLast last( By by )
	{
		final String ERR_MSG = "last() not allowed for <%s> type";
		try
		{
			PreConditions.checkInstanceOf( ByAttribute.class, by, ERR_MSG );
			return new ByLast( ( ByAttribute ) by );
		}
		catch ( IllegalArgumentException e )
		{
			logger.error( "throwing a new PreConditionException on ExtendedBy#attribute." );
			throw new PreConditionException( e.getMessage(), e );
		}
	}

	//todo: documentation
	public static ByLast last()
	{
		return new ByLast();
	}

	//todo: documentation
	public static ByFirst first( By by )
	{
		final String ERR_MSG = "last() not allowed for <%s> type";
		try
		{
			PreConditions.checkInstanceOf( ByAttribute.class, by, ERR_MSG, by.getClass().getName() );
			return new ByFirst( ( ByAttribute ) by );
		}
		catch ( IllegalArgumentException e )
		{
			logger.error( "throwing a new PreConditionException on ExtendedBy#first." );
			throw new PreConditionException( e.getMessage(), e );
		}
	}

	//todo: documentation
	public static ByFirst first()
	{
		return new ByFirst();
	}


	//region ExtendedBy - Inner Classes Implementation Section

	//todo: documentation
	public static class ByAttribute extends ExtendedBy
	{

		protected final String name;

		private final String value;

		public ByAttribute( String name, String value )
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

		private By makeByXPath()
		{
			return By.xpath( makeXPath() );
		}

		private String makeXPath()
		{
			return ".//*[" + nameAndValue() + "]";
		}

		protected String nameAndValue()
		{
			return "@" + name + val();
		}

		@Override
		public String toString()
		{
			return "EventBy.attribute: " + name + val();
		}

		private String val()
		{
			return value == null ? "" : " = '" + value + "'";
		}
	}

	//todo: documentation
	public static class NotByAttribute extends ByAttribute
	{

		public NotByAttribute( String name )
		{
			super( name, null );
		}

		@Override
		protected String nameAndValue()
		{
			return "not(" + super.nameAndValue() + ")";
		}

		public String toString()
		{
			return "EventBy.notAttribute: " + name;
		}
	}

	//todo: documentation
	private static class ByComposite extends ExtendedBy
	{

		private final By[] bys;

		private ByComposite( By... bys )
		{
			this.bys = bys;
		}

		@Override
		public List<WebElement> findElements( SearchContext context )
		{
			return makeByXPath().findElements( context );
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
				String className = bys[ 1 ].toString().substring( "By.className: ".length() );
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
			return bys[ 0 ].toString().substring( "By.tagName: ".length() );
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
	}

	//todo: documentation
	private static class ByLast extends ExtendedBy
	{

		private final String original;

		private final ByAttribute by;

		public ByLast( ByAttribute by )
		{
			this.by = by;
			this.original = by.makeXPath();
		}

		public ByLast()
		{
			this.original = ".//*[]";
			by = null;
		}

		@Override
		public List<WebElement> findElements( SearchContext context )
		{
			return makeXPath().findElements( context );
		}

		private By makeXPath()
		{
			return By.xpath( ( original.substring( 0, original.length() - 1 ) +
					" and position() = last()]" ).replace( "[ and ", "[" ) );
		}

		@Override
		public WebElement findElement( SearchContext context )
		{
			return makeXPath().findElement( context );
		}

		@Override
		public String toString()
		{
			return "EventBy.last(" + ( by == null ? "" : by ) + ")";
		}

	}

	//todo: documentation
	private static class ByFirst extends ExtendedBy
	{

		private final String original;

		private final ByAttribute by;

		public ByFirst( ByAttribute by )
		{
			this.by = by;
			this.original = by.makeXPath();
		}

		public ByFirst()
		{
			this.original = ".//*[]";
			by = null;
		}

		@Override
		public List<WebElement> findElements( SearchContext context )
		{
			return makeXPath().findElements( context );
		}

		private By makeXPath()
		{
			return By.xpath( ( original.substring( 0, original.length() - 1 ) +
					" and position() = first()]" ).replace( "[ and ", "[" ) );
		}

		@Override
		public WebElement findElement( SearchContext context )
		{
			return makeXPath().findElement( context );
		}

		@Override
		public String toString()
		{
			return "EventBy.first(" + ( by == null ? "" : by ) + ")";
		}
	}

	//endregion

}
