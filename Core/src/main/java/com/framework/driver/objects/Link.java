package com.framework.driver.objects;

import com.framework.asserts.JAssertions;
import com.framework.driver.event.EventWebDriver;
import com.framework.driver.exceptions.PreConditionException;
import com.framework.driver.utils.PreConditions;
import com.framework.driver.utils.ui.WaitUtil;
import com.framework.utils.web.CSS2Properties;
import com.google.common.base.MoreObjects;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.hamcrest.Matchers;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * The link class wraps an Anchor Html Element ( WebElement )
 * gives unique functionality for links
 *
 * @see #getReference()
 * @see #hover()
 * @see #testLinkReference()
 */

public class Link extends AbstractElementObject
{

	//region Link - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( Link.class );

	private String href, tagName, text;

	//endregion


	//region Link - Constructor Methods Section

	/**
	 * creates a new {@code Link} instance
	 *
	 * @param driver 	the wrapped {@code WebDriver}
	 * @param element   the wrapped {@code WebElement}
	 *
	 * @throws com.framework.driver.exceptions.PreConditionException if {@code WebElement} is not
	 * 		   a valid anchor element.
	 */
	public Link( final WebDriver driver, final WebElement element )
	{
		super( driver, element );

		try
		{
			PreConditions.checkElementTagName( element, "a" );
			WebElement underlying = ( ( EventWebDriver ) driver ).getWrappedElement( element );

			this.tagName = underlying.getTagName();
			this.text = underlying.getText();
			this.href = underlying.getAttribute( "href" );

			logger.debug( "Creating a new Link object for ( tag:'{}', text:'{}' )", tagName, text );
		}
		catch ( NullPointerException | IllegalArgumentException e )
		{
			logger.error( "throwing a new PreConditionException on Link#constructor." );
			throw new PreConditionException( e.getMessage(), e );
		}
	}

	//endregion


	//region Link - Public Methods Section

	//todo: documentation
	public String getHReference()
	{
		return webElement.getAttribute( "href" );
	}

	//todo: documentation
	public URL getReference()
	{
		String ref = getHReference();
		try
		{
			if( ref.startsWith( "#" ) )
			{
				return new URL( driver.getCurrentUrl() );
			}
			else
			{
				return new URL( ref );
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

			/* creating a 5 seconds wait condition */
			WebDriverWait wdw = WaitUtil.wait5( driver );
			wdw.withMessage( "Waiting for text-decoration value to be underline" );
			ExpectedCondition<Boolean> ec = WaitUtil.elementCssPropertyToMatch( webElement, textDecoration, Matchers.is( "underline" ) );

			/* hovering the link */
			driver.hover( webElement );

			/* asserting text-decoration */

			JAssertions.assertWaitThat( wdw ).matchesCondition( ec );
			return;
		}

		/* hovering without asserting text-decoration */
		super.hover();
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper( this )
				.add( "tagName", tagName )
				.add( "text", text )
				.add( "href", href )
				.omitNullValues()
				.toString();
	}

	//todo: documentation
	public int testLinkReference()
	{

		HttpURLConnection urlConnection = null;

		try
		{
			URL url = getReference();
			logger.info( "Connecting to url -> '{}'", url.toString() );
			urlConnection = ( HttpURLConnection ) url.openConnection();
			urlConnection.connect();
			urlConnection.getResponseCode();
		}
		catch ( IOException e )
		{
			logger.error( e.getMessage(), e );
			throw new WebDriverException( e.getMessage(), e );
		}
		finally
		{
			if( urlConnection != null )
			{
				urlConnection.disconnect();
			}
		}

		return NumberUtils.INTEGER_MINUS_ONE;
	}

	@Override
	public String getText()
	{
		String text;
		text = super.getText();
		if( StringUtils.isNotEmpty( text ) ) return text;
		return super.getAttribute( "textContent" ).trim();
	}

	//endregion
}
