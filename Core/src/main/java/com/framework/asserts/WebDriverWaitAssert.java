package com.framework.asserts;

import org.assertj.core.api.AbstractAssert;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.asserts
 *
 * Name   : WebDriverWaitAssert
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-01-05
 *
 * Time   : 15:44
 */

public class WebDriverWaitAssert extends AbstractAssert<WebDriverWaitAssert, WebDriverWait>
{

	//region WebDriverWaitAssert - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( WebDriverWaitAssert.class );

	//endregion


	//region WebDriverWaitAssert - Constructor Methods Section

	public WebDriverWaitAssert( WebDriverWait wait )
	{
		super( wait, WebDriverWaitAssert.class );
	}

	//endregion


	//region WebDriverWaitAssert - Public Methods Section

	/**
	 * Verifies that the actual WebDriver's currentUrl is equal to the given one.
	 *
	 * @param condition the given ExpectedCondition matcher to compare the actual WebDriver's ExpectedCondition to.
	 *
	 * @return this assertion object.
	 *
	 * @throws AssertionError - if the actual WebDriver's currentUrl is not equal to the given one.
	 */
	public WebDriverWaitAssert matchesCondition( ExpectedCondition<?> condition )
	{
		// check that actual WebDriver we want to make assertions on is not null.
		isNotNull();

		String errMsg = " Expected condition to be: '<%s>', but was: '<%s>'";

		try   // null safe check
		{
			Object result = actual.until( condition );
			boolean response = ( result == null || result == Boolean.FALSE );
			if( response )
			{
				failWithMessage( errMsg, condition.toString(), false );
			}
		}
		catch ( WebDriverException we )
		{
			failWithMessage( errMsg, condition.toString(), false );
		}

		return this; // return the current assertion for method chaining
	}

	//endregion
}
