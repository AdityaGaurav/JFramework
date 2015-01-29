package com.framework.driver.objects;

import com.framework.driver.exceptions.UrlNotavailableException;
import com.framework.driver.utils.ui.WaitUtil;
import com.framework.utils.error.PreConditions;
import com.framework.utils.web.CSS2Properties;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hamcrest.Matchers;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.net.UrlChecker;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
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
 * @see #hover()
 */

public class Link extends BaseElementObject
{

	//region Link - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( Link.class );

	private String href, text;

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
	public Link( final WebElement element )
	{
		super( element );

		String tagName = element.getTagName();
		PreConditions.checkArgument( tagName.toLowerCase().equals( "a" ), "Invalid tag name found for anchor -> %s", tagName );
		this.text = element.getText();
		this.href = element.getAttribute( "href" );

		logger.debug( "Creating a new Link object for ( tag:'{}', text:'{}' )", tagName, text );
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
				return new URL( getWrapperDriver().getCurrentUrl() );
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
			// reading the css text-decoration property name */
			String textDecoration = CSS2Properties.TEXT_DECORATION.getStringValue();

			// creating a 5 seconds wait condition */
			WebDriverWait wdw = WaitUtil.wait5( getWrapperDriver() );
			wdw.withMessage( "Waiting for text-decoration value to be underline" );
			ExpectedCondition<Boolean> ec = WaitUtil.elementCssPropertyToMatch( getWrappedElement(), textDecoration, Matchers.is( "underline" ) );

			super.hover();  // hovering over the link

			assertWaitThat /* asserting text-decoration */ ( "asserting text-decoration", 5000, ec );

			return;
		}

		// hovering without asserting text-decoration */
		super.hover();
	}

	//todo: documentation
	public void checkReference( long timeoutMillis ) throws UrlNotavailableException
	{
		UrlChecker checker = new UrlChecker();
		try
		{
			checker.waitUntilAvailable( timeoutMillis, TimeUnit.MILLISECONDS, getReference() );
		}
		catch ( UrlChecker.TimeoutException e )
		{
			logger.error( e.getMessage() );
			throw new UrlNotavailableException( e.getMessage(), e );
		}
	}

	public String getText()
	{
		String text;
		text = super.getText();
		if( StringUtils.isNotEmpty( text ) ) return text;
		return super.getAttribute( "textContent" ).trim();
	}

	@Override
	public String toString()
	{
		return new ToStringBuilder( this )
				.appendSuper( super.toString() )
				.append( "href", href )
				.append( "text", getText() )
				.toString();
	}

	//endregion
}
