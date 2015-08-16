package com.framework.testing.steping.exceptions;

/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.testing
 *
 * Name   : PendingStepException
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-01-17
 *
 * Time   : 13:07
 */

public class PendingStepException extends TestStepException
{

	public PendingStepException( final String message )
	{
		super( message );
	}

	public PendingStepException( final String message, final Throwable cause )
	{
		super( message, cause );
	}

	public PendingStepException( final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace )
	{
		super( message, cause, enableSuppression, writableStackTrace );
	}
}
