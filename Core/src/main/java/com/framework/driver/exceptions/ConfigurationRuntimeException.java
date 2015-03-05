package com.framework.driver.exceptions;

/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.driver.exceptions
 *
 * Name   : ConfigurationRuntimeException 
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-03-05 
 *
 * Time   : 16:54
 *
 */

public class ConfigurationRuntimeException extends RuntimeException
{

	private static final long serialVersionUID = 5701765920793191114L;

	public ConfigurationRuntimeException()
	{
	}

	public ConfigurationRuntimeException( final String message )
	{
		super( message );
	}

	public ConfigurationRuntimeException( final String message, final Throwable cause )
	{
		super( message, cause );
	}

	public ConfigurationRuntimeException( final Throwable cause )
	{
		super( cause );
	}
}
