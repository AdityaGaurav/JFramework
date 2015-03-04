package com.framework.testing.steping.exceptions;

/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.testing.steping.exceptions
 *
 * Name   : DuplicateTestStepException 
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-01-25 
 *
 * Time   : 23:13
 *
 */

public class DuplicateTestStepException extends TestStepException
{

	public DuplicateTestStepException( final String message )
	{
		super( message );
	}

	public DuplicateTestStepException( final String message, final Throwable cause )
	{
		super( message, cause );
	}

	public DuplicateTestStepException( final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace )
	{
		super( message, cause, enableSuppression, writableStackTrace );
	}
}
