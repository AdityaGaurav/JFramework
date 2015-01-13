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

public class UnexpectedTypeException extends FrameworkException
{

	private static final long serialVersionUID = 8083365507718227684L;

	public UnexpectedTypeException( String expectedType, String actualType )
	{
		super( String.format( "Element type should have been \"%s\" but was \"%s\"", expectedType, actualType ) );
	}

}
