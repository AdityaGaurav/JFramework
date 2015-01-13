package com.framework.asserts;

import com.framework.driver.objects.PageObject;
import org.assertj.core.api.AbstractAssert;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;
import org.openqa.selenium.Cookie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Extending {@link org.assertj.core.api.AbstractAssert}
 * this assert used to assert the {@link com.framework.driver.objects.PageObject} extension
 * which all page classes in a project implements this interface.
 *
 * @see com.framework.driver.objects.PageObject
 * @see com.framework.driver.objects.AbstractPageObject
 */


public class PageAssert extends AbstractAssert<PageAssert, PageObject>
{

	//region PageAssert - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( PageAssert.class );

	//endregion


	//region PageAssert - Constructor Methods Section

	//todo: documentation
	public PageAssert( PageObject actual )
	{
		super( actual, PageAssert.class );
	}

	//todo: documentation
	public static PageAssert assertThat( PageObject actual )
	{
		return new PageAssert( actual );
	}

	//endregion


	//region PageAssert - Public Methods Section

	/**
	 * Verifies that the actual WebDriver's currentUrl matches to the given one.
	 *
	 * @param matcher the given currentUrl matcher to compare the actual WebDriver's currentUrl to.
	 *
	 * @return this assertion object.
	 *
	 * @throws AssertionError - if the actual WebDriver's currentUrl is not equal to the given one.
	 */
	public PageAssert matchesUrl( Matcher<String> matcher )
	{
		// check that actual WebDriver we want to make assertions on is not null.

		isNotNull();

		String logicalName = actual.getLogicalName();

		// overrides the default error message with a more explicit one
		Description description = new StringDescription().appendDescriptionOf( matcher );
		String errMsg = " Expected url on page '<%s>' to be: '<%s>', but was: '<%s>'";

		String actualCurrentUrl = actual.getCurrentUrl();
		if ( ! matcher.matches( actualCurrentUrl ) )
		{
			failWithMessage( errMsg, logicalName, description.toString(), actualCurrentUrl );
		}

		// return the current assertion for method chaining

		return this;
	}

	/**
	 * Verifies that the actual WebDriver's title matches to the given one.
	 *
	 * @param matcher the given matcher to compare the actual WebDriver's title to.
	 *
	 * @return this assertion object.
	 *
	 * @throws AssertionError - if the actual WebDriver's title is not equal to the given one.
	 */
	public PageAssert matchesTitle( Matcher<String> matcher )
	{
		// check that actual WebDriver we want to make assertions on is not null.

		isNotNull();

		String logicalName = actual.getLogicalName();

		// overrides the default error message with a more explicit one

		Description description = new StringDescription().appendDescriptionOf( matcher );
		String errMsg = " Expected title on page '<%s>' to be: '<%s>', but was: '<%s>'";

		String actualTitle = actual.getTitle();
		if ( ! matcher.matches( actualTitle ) )
		{
			failWithMessage( errMsg, logicalName, description.toString(), actualTitle );
		}

		return this;   // return the current assertion for method chaining
	}

	public PageAssert matchesWindowHandles( Matcher<Integer> matcher )
	{
		// check that actual WebDriver we want to make assertions on is not null.

		isNotNull();

		// overrides the default error message with a more explicit one

		Description description = new StringDescription().appendDescriptionOf( matcher );
		String errMsg = " Expected windows handles managed by driver to be: '<%d>', but was: '<%d>'";

		int handles = actual.getWindowHandles().size();
		if ( ! matcher.matches( handles ) )
		{
			failWithMessage( errMsg, description.toString(), handles );
		}

		return this;   // return the current assertion for method chaining
	}

	public PageAssert hasCookie( String cookieName )
	{
		// check that actual WebDriver we want to make assertions on is not null.

		isNotNull();

		String logicalName = actual.getLogicalName();

		// overrides the default error message with a more explicit one

		String errMsg = " Expecting that session have a cookie named: '<%s>', but there is not";

		Cookie cookie = actual.manage().getCookieNamed( cookieName );
		if ( cookie == null )
		{
			failWithMessage( errMsg, cookieName );
		}

		return this;   // return the current assertion for method chaining
	}

	public PageAssert doesNotHaveCookie( String cookieName )
	{
		// check that actual WebDriver we want to make assertions on is not null.

		isNotNull();

		String logicalName = actual.getLogicalName();

		// overrides the default error message with a more explicit one

		String errMsg = " Expecting that session does not have a cookie named: '<%s>', but there is";

		Cookie cookie = actual.manage().getCookieNamed( cookieName );
		if ( cookie != null )
		{
			failWithMessage( errMsg, cookieName );
		}

		return this;   // return the current assertion for method chaining
	}

	//endregion

}
