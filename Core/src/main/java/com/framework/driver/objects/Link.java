package com.framework.driver.objects;

import com.framework.driver.event.HtmlElement;
import com.framework.driver.exceptions.UrlNotAvailableException;
import com.framework.utils.datetime.TimeConstants;
import com.framework.utils.error.PreConditions;
import com.framework.utils.matchers.JMatchers;
import com.framework.utils.web.CSS2Properties;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.math.NumberUtils;
import org.openqa.selenium.net.UrlChecker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;


/**
 * The link class wraps an Anchor Html Element ( WebElement )
 * gives unique functionality for links
 *
 * @see #getReference()
 */

public class Link
{

	//region Link - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( Link.class );

	private String href;

	private HtmlElement element;

	private final String qualifier;

	private static long counter = NumberUtils.LONG_ZERO;

	//endregion


	//region Link - Constructor Methods Section

	/**
	 * creates a new {@code Link} instance
	 *
	 * @param element   the wrapped {@code WebElement}
	 *
	 * @throws com.framework.utils.error.PreConditionException if {@code WebElement} is not
	 * 		   a valid anchor element.
	 */
	public Link( final HtmlElement element )
	{
		String tagName = element.getTagName();
		PreConditions.checkArgument( tagName.toLowerCase().equals( "a" ), "Invalid tag name found for anchor -> %s", tagName );

		this.element = element;
		this.href = element.getAttribute( "href" );
		this.qualifier = String.format( "LINK[%d]", ++ counter );
		logger.debug( "Created a new Link element < {} >", qualifier );
	}

	//endregion


	//region Link - Public Methods Section

	//todo: documentation
	public String getHReference()
	{
		return href;
	}

	//todo: documentation
	public URL getReference()
	{
		try
		{
			if( getHReference().startsWith( "#" ) )
			{
				return new URL( element.getWrappedHtmlDriver().getCurrentUrl() );
			}
			else
			{
				return new URL( getHReference() );
			}
		}
		catch ( MalformedURLException e )
		{
			return null;
		}
	}

	/**
	 * hovers over a link and alternately validates css text-decoration property
	 *
	 * @param validateDecoration {@code true} to make a text-decoration validation
	 *
	 * @see com.framework.utils.web.CSS2Properties
	 */
	public void hover( boolean validateDecoration )
	{
		if ( validateDecoration )
		{
			/* reading the css text-decoration property name */

			String textDecoration = CSS2Properties.TEXT_DECORATION.getStringValue();
			element. /* hovering over the link */ hover();
			element.waitCssPropertyToMatch( textDecoration, JMatchers.is( "underline" ), TimeConstants.FIVE_SECONDS );
		}
		else
		{
			element. /* hovering without asserting text-decoration */ hover();
		}
	}

	public void click()
	{
		element.click();
	}

	//todo: documentation
	public void checkReference( long timeoutMillis ) throws UrlNotAvailableException
	{
		UrlChecker checker = new UrlChecker();
		try
		{
			logger.info( "Testing URL availability of reference < {} > ", getReference() );
			checker.waitUntilAvailable( timeoutMillis, TimeUnit.MILLISECONDS, getReference() );
		}
		catch ( UrlChecker.TimeoutException e )
		{
			logger.error( e.getMessage() );
			throw new UrlNotAvailableException( e.getMessage(), e );
		}
	}

	public String getText()
	{
		String text;
		text = element.getText();
		if( StringUtils.isNotEmpty( text ) ) return text;
		return StringUtils.remove( StringUtils.remove( element.getAttribute( "textContent" ), "\t" ), "\n" );
	}

	public String getQualifier()
	{
		return qualifier;
	}

	public HtmlElement getHtmlElement()
	{
		return element;
	}

	@Override
	public String toString()
	{
		return new ToStringBuilder( this )
				.appendSuper( super.toString() )
				.append( "qualifier", qualifier )
				.append( "href", href )
				.toString();
	}

	//endregion
}
