package com.framework.testing.steping.exceptions;

/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.driver.exceptions
 *
 * Name   : StepFailureException
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-01-17
 *
 * Time   : 13:37
 */

public class StepFailureException extends TestStepException
{

	public StepFailureException( final String message )
	{
		super( message );
	}

	public StepFailureException( final String message, final Throwable cause )
	{
		super( message, cause );
	}

	public StepFailureException( final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace )
	{
		super( message, cause, enableSuppression, writableStackTrace );
	}
}
