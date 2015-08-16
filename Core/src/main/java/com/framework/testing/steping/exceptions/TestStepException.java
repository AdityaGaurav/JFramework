package com.framework.testing.steping.exceptions;

/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.driver.exceptions
 *
 * Name   : TestStepException
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-01-17
 *
 * Time   : 13:08
 */

public class TestStepException extends EventBusEventException
{

	public TestStepException( final String message )
	{
		super( message );
	}

	public TestStepException( final String message, final Throwable cause )
	{
		super( message, cause );
	}

	public TestStepException( final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace )
	{
		super( message, cause, enableSuppression, writableStackTrace );
	}
}
