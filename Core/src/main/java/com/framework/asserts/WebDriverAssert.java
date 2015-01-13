package com.framework.asserts;

import org.assertj.core.api.AbstractAssert;
import org.assertj.core.util.Objects;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.asserts
 *
 * Name   : WebDriverAssert
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-01-05
 *
 * Time   : 15:34
 */

public class WebDriverAssert extends AbstractAssert<WebDriverAssert, WebDriver>
{

	//region WebDriverAssert - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( WebDriverAssert.class );

	//endregion


	//region WebDriverAssert - Constructor Methods Section

	/**
	 * Creates a new <code>{@link com.framework.asserts.WebDriverAssert}</code> to make assertions on actual WebDriver.
	 *
	 * @param actual the WebDriver we want to make assertions on.
	 */
	public WebDriverAssert( WebDriver actual )
	{
		super( actual, WebDriverAssert.class );
	}

	/**
	 * An entry point for WebDriverAssert to follow AssertJ standard <code>assertThat()</code> statements.<br>
	 * With a static import, one can write directly: <code>assertThat(myWebDriver)</code> and get specific assertion with code completion.
	 *
	 * @param actual the WebDriver we want to make assertions on.
	 *
	 * @return a new <code>{@link com.framework.asserts.WebDriverAssert}</code>
	 */
	public static WebDriverAssert assertThat( WebDriver actual )
	{
		return new WebDriverAssert( actual );
	}

	//endregion


	//region WebDriverAssert - Public Methods Section

	/**
	 * Verifies that the actual WebDriver's currentUrl is equal to the given one.
	 *
	 * @param matcher the given currentUrl matcher to compare the actual WebDriver's currentUrl to.
	 *
	 * @return this assertion object.
	 *
	 * @throws AssertionError - if the actual WebDriver's currentUrl is not equal to the given one.
	 */
	public WebDriverAssert matchesUrl( Matcher<String> matcher )
	{
		isNotNull();  // check that actual WebDriver we want to make assertions on is not null.

		//WebDriver driver = ( ( EventWebDriver ) actual ).getWrappedDriver();

		// overrides the default error message with a more explicit one
		Description description = new StringDescription().appendDescriptionOf( matcher );
		String errMsg = " Expected currentUrl to be: '<%s>', but was: '<%s>'";

		String actualCurrentUrl = actual.getCurrentUrl();
		if ( ! matcher.matches( actualCurrentUrl ) )
		{
			failWithMessage( errMsg, description.toString(), actualCurrentUrl );
		}

		return this;   // return the current assertion for method chaining
	}

	/**
	 * Verifies that the actual WebDriver's title is equal to the given one.
	 *
	 * @param title the given title to compare the actual WebDriver's title to.
	 *
	 * @return this assertion object.
	 *
	 * @throws AssertionError - if the actual WebDriver's title is not equal to the given one.
	 */
	public WebDriverAssert matchesTitle( Matcher<String> matcher )
	{
		isNotNull();  // check that actual WebDriver we want to make assertions on is not null.

		//WebDriver driver = ( ( EventWebDriver ) actual ).getWrappedDriver();

		// overrides the default error message with a more explicit one
		Description description = new StringDescription().appendDescriptionOf( matcher );
		String errMsg = " Expected title to be: '<%s>', but was: '<%s>'";

		String actualTitle = actual.getTitle();
		if ( ! matcher.matches( actualTitle ) )
		{
			failWithMessage( errMsg, description.toString(), actualTitle );
		}

		return this;   // return the current assertion for method chaining
	}

	/**
	 * Verifies that the actual WebDriver's windowHandles contains the given String elements.
	 *
	 * @param windowHandles the given count that should be contained in actual WebDriver's windowHandles.
	 *
	 * @return this assertion object.
	 *
	 * @throws AssertionError if the actual WebDriver's windowHandles does not contain all given String elements.
	 */
	public WebDriverAssert hasWindowHandles( int windowHandles )
	{

		isNotNull();  // check that actual WebDriver we want to make assertions on is not null.

		//WebDriver driver = ( ( EventWebDriver ) actual ).getWrappedDriver();

		// overrides the default error message with a more explicit one
		String errMsg = " Expected handles to be: '<%d>', but was: '<%d>'";
		int actualHandles = actual.getWindowHandles().size();
		if ( ! Objects.areEqual( actualHandles, windowHandles ) )
		{
			failWithMessage( errMsg, windowHandles, windowHandles );
		}

		return this;   // return the current assertion for method chaining
	}

	//endregion


	//region WebDriverAssert - Protected Methods Section

	//endregion


	//region WebDriverAssert - Private Function Section

	private String takeScreenshot()
	{
		return null;
	}

	//endregion


	//region WebDriverAssert - Inner Classes Implementation Section

	//endregion

}
