package com.framework.driver.exceptions;

/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: org.framework.webdriver.exceptions
 *
 * Name   : UnexpectedTypeException
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2014-12-25
 *
 * Time   : 12:46
 */

public class UnexpectedAttributeValueException extends FrameworkException
{

	private static final long serialVersionUID = 958778477043390620L;

	public UnexpectedAttributeValueException( String attributeName, String expectedValue, String actualValue )
	{
		super( String.format( "Element attribute '%s' should have been \"%s\" but was \"%s\"",
				attributeName, expectedValue, actualValue ) );
	}

}
