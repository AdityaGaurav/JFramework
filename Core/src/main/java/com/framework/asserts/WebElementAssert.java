package com.framework.asserts;

import org.apache.commons.lang3.StringUtils;
import org.assertj.core.api.AbstractAssert;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.asserts
 *
 * Name   : WebElementAssert
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-01-05
 *
 * Time   : 15:34
 */

public class WebElementAssert extends AbstractAssert<WebElementAssert, WebElement>
{

	//region WebElementAssert - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( WebElementAssert.class );

	//endregion


	//region WebElementAssert - Constructor Methods Section

	/**
	 * Creates a new <code>{@link com.framework.asserts.WebElementAssert}</code> to make assertions on actual WebElement.
	 *
	 * @param actual the WebElement we want to make assertions on.
	 */
	public WebElementAssert( WebElement actual )
	{
		super( actual, WebElementAssert.class );
	}

	/**
	 * An entry point for WebElementAssert to follow AssertJ standard <code>assertThat()</code> statements.<br>
	 * With a static import, one can write directly: <code>assertThat(myWebElement)</code> and get specific assertion with code completion.
	 *
	 * @param actual the WebElement we want to make assertions on.
	 *
	 * @return a new <code>{@link com.framework.asserts.WebElementAssert}</code>
	 */
	public static WebElementAssert assertThat( WebElement actual )
	{
		return new WebElementAssert( actual );
	}

	//endregion


	//region WebElementAssert - Public Methods Section

	/**
	 * Verifies that the actual WebElement is displayed.
	 *
	 * @return this assertion object.
	 *
	 * @throws AssertionError - if the actual WebElement is not displayed.
	 */
	public WebElementAssert isDisplayed()
	{
		isNotNull();  // check that actual WebElement we want to make assertions on is not null.
		if ( ! actual.isDisplayed() )
		{
			failWithMessage( " Expected actual WebElement '<%s>' to be displayed but was not.",
					getElementDescription( actual ) );
		}

		return this;  // return the current assertion for method chaining
	}

	/**
	 * Verifies that the actual WebElement is not displayed.
	 *
	 * @return this assertion object.
	 *
	 * @throws AssertionError - if the actual WebElement is displayed.
	 */
	public WebElementAssert isNotDisplayed()
	{
		isNotNull(); // check that actual WebElement we want to make assertions on is not null.

		if ( actual.isDisplayed() )
		{
			failWithMessage( " Expected actual WebElement '<%s>' not to be displayed but was.",
					getElementDescription( actual ) );
		}

		return this; // return the current assertion for method chaining
	}

	/**
	 * Verifies that the actual WebElement is enabled.
	 *
	 * @return this assertion object.
	 *
	 * @throws AssertionError - if the actual WebElement is not enabled.
	 */
	public WebElementAssert isEnabled()
	{
		isNotNull();
		if ( ! actual.isEnabled() )
		{
			failWithMessage( " Expected actual WebElement '<%s>' to be enabled but was not.",
					getElementDescription( actual ) );
		}

		return this;  // return the current assertion for method chaining
	}

	/**
	 * Verifies that the actual WebElement is disabled ( not enabled ).
	 *
	 * @return this assertion object.
	 *
	 * @throws AssertionError - if the actual WebElement is enabled.
	 */
	public WebElementAssert isNotEnabled()
	{
		isNotNull();
		if ( actual.isDisplayed() )
		{
			failWithMessage( " Expected actual WebElement '<%s>' not to be displayed but was.",
					getElementDescription( actual ));
		}

		return this;
	}

	/**
	 * Verifies that the actual WebElement is selected.
	 *
	 * @return this assertion object.
	 *
	 * @throws AssertionError - if the actual WebElement is not selected.
	 */
	public WebElementAssert isSelected()
	{
		isNotNull();
		if ( ! actual.isSelected() )
		{
			failWithMessage( " Expected actual WebElement '<%s>' to be selected but was not.",
					getElementDescription( actual ) );
		}

		return this;  // return the current assertion for method chaining
	}

	/**
	 * Verifies that the actual WebElement is selected/checked.
	 *
	 * @return this assertion object.
	 *
	 * @throws AssertionError - if the actual WebElement is selected/checked.
	 */
	public WebElementAssert isNotSelected()
	{
		isNotNull();
		if ( actual.isSelected() )
		{
			failWithMessage( " Expected actual WebElement '<%s>' not to be selected but was.",
					getElementDescription( actual ));
		}

		return this;
	}

	/**
	 * Verifies that the actual WebElement's text is equal to the given one.
	 *
	 * @param matcher the given {@code Matcher<String>} text to compare the actual WebElement's text to.
	 *
	 * @return this assertion object.
	 *
	 * @throws AssertionError - if the actual WebElement's text is not equal to the given one.
	 */
	public WebElementAssert matchesText( Matcher<String> matcher )
	{
		isNotNull();
		Description description = new StringDescription().appendDescriptionOf( matcher );
		String errMsg = "Expected text of: '<%s>' to be: '<%s>', but was: '<%s>'";

		String actualText = actual.getText();
		if ( ! matcher.matches( actualText ) )
		{
			failWithMessage( errMsg, actual, description.toString(), actualText );
		}

		return this;
	}

	/**
	 * Verifies that the actual WebElement's attribute matches to the given one.
	 *
	 * @param attributeName the attribute name to evaluate
	 * @param matcher 		the given {@code Matcher<String>} text to compare the actual WebElement's attribute to.
	 *
	 * @return this assertion object.
	 *
	 * @throws AssertionError - if the actual WebElement's text is not equal to the given one.
	 */
	public WebElementAssert matchesAttributeValue( String attributeName, Matcher<String> matcher )
	{
		isNotNull();
		Description description = new StringDescription().appendDescriptionOf( matcher );
		String errMsg = "Expected attribute <%s> of: '<%s>' to be: '<%s>', but was: '<%s>'";

		String actualText = actual.getAttribute( attributeName );
		String decodedValue;

		/* attributes that contain urls values ( src, href ) might contain decoding characters ( %20 etc ) */

		try
		{
			decodedValue = java.net.URLDecoder.decode( actualText, "UTF-8" );
			if( ! decodedValue.equals( actualText ) )
			{
				logger.debug( "Attribute value was decoded ..." );
			}
		}
		catch ( UnsupportedEncodingException e )
		{
			decodedValue = actualText;
		}

		if ( ! matcher.matches( decodedValue ) )
		{
			failWithMessage( errMsg, attributeName, actual, description.toString(), actualText );
		}

		return this;
	}

	/**
	 * Verifies that the actual WebElement's css property value matches to the given one.
	 *
	 * @param propertyValue the css property name to evaluate
	 * @param matcher 		the given {@code Matcher<String>} text to compare the actual WebElement's attribute to.
	 *
	 * @return this assertion object.
	 *
	 * @throws AssertionError - if the actual WebElement's text is not equal to the given one.
	 */
	public WebElementAssert matchesCssPropertyValue( String propertyValue, Matcher<String> matcher )
	{
		isNotNull();
		Description description = new StringDescription().appendDescriptionOf( matcher );
		String errMsg = "Expected css attribute '<%s>' of: '<%s>' to be: '<%s>', but was: '<%s>'";

		String actualText = actual.getCssValue( propertyValue );
		if ( ! matcher.matches( actualText ) )
		{
			failWithMessage( errMsg, propertyValue, actual, description.toString(), actualText );
		}

		return this;
	}

	//endregion


	//region WebElementAssert - Private Function Section

	private String getElementDescription( WebElement e )
	{
		String we = e.toString();
		return "found by " + we.substring( we.indexOf( "->" ) ).replace( "]", StringUtils.EMPTY );
	}

	//endregion


	//region WebElementAssert - Inner Classes Implementation Section

	//endregion

}
