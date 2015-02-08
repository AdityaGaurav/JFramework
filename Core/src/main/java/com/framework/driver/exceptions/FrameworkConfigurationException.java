package com.framework.driver.exceptions;

/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.driver.exceptions
 *
 * Name   : FrameworkConfigurationException
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-01-17
 *
 * Time   : 22:01
 */

public class FrameworkConfigurationException extends Exception
{

	public FrameworkConfigurationException( final String message )
	{
		super( message );
	}

	public FrameworkConfigurationException( final String message, final Throwable cause )
	{
		super( message, cause );
	}

	public FrameworkConfigurationException( final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace )
	{
		super( message, cause, enableSuppression, writableStackTrace );
	}
}
