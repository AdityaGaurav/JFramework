package com.framework.testing.steping.exceptions;


public class NoSuchTestStepException extends RuntimeException
{

	public NoSuchTestStepException( final String message )
	{
		super( message );
	}

	public NoSuchTestStepException( final String message, final Throwable cause )
	{
		super( message, cause );
	}

	public NoSuchTestStepException( final Throwable cause )
	{
		super( cause );
	}

	public NoSuchTestStepException( final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace )
	{
		super( message, cause, enableSuppression, writableStackTrace );
	}
}
