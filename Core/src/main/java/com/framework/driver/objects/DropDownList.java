package com.framework.driver.objects;

import com.framework.utils.error.PreConditions;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.StringTokenizer;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.driver.objects
 *
 * Name   : DropDownList
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-01-05
 *
 * Time   : 20:12
 */

public class DropDownList extends BaseElementObject
{

	//region DropDownList - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( DropDownList.class );

	private final boolean isMulti;

	//endregion


	//region DropDownList - Constructor Methods Section

	public DropDownList( final WebElement element )
	{
		super( element );

		final String ERR_MSG1 = "Invalid tag name found for drop-down list -> %s";
		final String ERR_MSG2 = "Invalid multiple attribute found for drop-down list -> %s";

		String tagName = element.getTagName();
		String value = element.getAttribute( "multiple" );
		PreConditions.checkArgument( tagName.toLowerCase().equals( "select" ), ERR_MSG1, tagName );
		if( value != null )
		{
			PreConditions.checkArgument( value.toLowerCase().equals( "true" ), ERR_MSG2, value );
		}
		isMulti = ( value != null && ! "false".equals( value ) );

		logger.debug( "Creating a new Drop-Down List object for ( tag:'{}' )", tagName );
	}

	//endregion


	//region DropDownList - Public Methods Section

	public List<WebElement> getOptions()
	{
		return getWrappedElement().findElements( By.tagName( "option" ) );
	}

	public WebElement getFirstSelectedOption()
	{
		for ( WebElement option : getOptions() )
		{
			if ( option.isSelected() )
			{
				return option;
			}
		}

		throw new NoSuchElementException( "No options are selected" );
	}

	public void selectByVisibleText( String text )
	{
		// try to find the option via XPATH ...
		List<WebElement> options =
				getWrappedElement().findElements( By.xpath( ".//option[normalize-space(.) = " + escapeQuotes( text ) + "]" ) );

		boolean matched = false;
		for ( WebElement option : options )
		{
			setSelected( option );
			if ( ! isMultiple() )
			{
				return;
			}
			matched = true;
		}

		if ( options.isEmpty() && text.contains( " " ) )
		{
			String subStringWithoutSpace = getLongestSubstringWithoutSpace( text );
			List<WebElement> candidates;
			if ( "" .equals( subStringWithoutSpace ) )
			{
				// hmm, text is either empty or contains only spaces - get all options ...
				candidates = getWrappedElement().findElements( By.tagName( "option" ) );
			}
			else
			{
				// get candidates via XPATH ...
				candidates =
						getWrappedElement().findElements( By.xpath( ".//option[contains(., " +
								escapeQuotes( subStringWithoutSpace ) + ")]" ) );
			}
			for ( WebElement option : candidates )
			{
				if ( text.equals( option.getText() ) )
				{
					setSelected( option );
					if ( ! isMultiple() )
					{
						return;
					}
					matched = true;
				}
			}
		}

		if ( ! matched )
		{
			throw new NoSuchElementException( "Cannot locate element with text: " + text );
		}
	}

	public void selectByIndex( int index )
	{
		String match = String.valueOf( index );

		boolean matched = false;
		for ( WebElement option : getOptions() )
		{
			if ( match.equals( option.getAttribute( "index" ) ) )
			{
				setSelected( option );
				if ( ! isMultiple() )
				{
					return;
				}
				matched = true;
			}
		}
		if ( ! matched )
		{
			throw new NoSuchElementException( "Cannot locate option with index: " + index );
		}
	}

	public void selectByValue( String value )
	{
		StringBuilder builder = new StringBuilder( ".//option[@value = " );
		builder.append( escapeQuotes( value ) );
		builder.append( "]" );
		List<WebElement> options = getWrappedElement().findElements( By.xpath( builder.toString() ) );

		boolean matched = false;
		for ( WebElement option : options )
		{
			setSelected( option );
			if ( ! isMultiple() )
			{
				return;
			}
			matched = true;
		}

		if ( ! matched )
		{
			throw new NoSuchElementException( "Cannot locate option with value: " + value );
		}
	}

	public void dropList()
	{
		getWrappedElement().click();
	}

	//endregion


	//region DropDownList - Private Methods Section

	private boolean isMultiple()
	{
		return isMulti;
	}

	private String getLongestSubstringWithoutSpace( String s )
	{
		String result = "";
		StringTokenizer st = new StringTokenizer( s, " " );
		while ( st.hasMoreTokens() )
		{
			String t = st.nextToken();
			if ( t.length() > result.length() )
			{
				result = t;
			}
		}
		return result;
	}

	protected String escapeQuotes( String toEscape )
	{
		// Convert strings with both quotes and ticks into: foo'"bar -> concat("foo'", '"', "bar")
		if ( toEscape.contains( "\"" ) && toEscape.contains( "'" ) )
		{
			boolean quoteIsLast = false;
			if ( toEscape.lastIndexOf( "\"" ) == toEscape.length() - 1 )
			{
				quoteIsLast = true;
			}
			String[] subStrings = toEscape.split( "\"" );

			StringBuilder quoted = new StringBuilder( "concat(" );
			for ( int i = 0; i < subStrings.length; i++ )
			{
				quoted.append( "\"" ).append( subStrings[ i ] ).append( "\"" );
				quoted
						.append( ( ( i == subStrings.length - 1 ) ? ( quoteIsLast ? ", '\"')" : ")" ) : ", '\"', " ) );
			}
			return quoted.toString();
		}

		// Escape string with just a quote into being single quoted: f"oo -> 'f"oo'
		if ( toEscape.contains( "\"" ) )
		{
			return String.format( "'%s'", toEscape );
		}

		// Otherwise return the quoted string
		return String.format( "\"%s\"", toEscape );
	}

	private void setSelected( WebElement option )
	{
		if ( ! option.isSelected() )
		{
			option.click();
		}
	}

	//endregion

}
