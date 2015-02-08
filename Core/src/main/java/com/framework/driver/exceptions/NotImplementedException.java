package com.framework.driver.exceptions;

/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.driver.exceptions
 *
 * Name   : NotImplementedException
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-01-16
 *
 * Time   : 15:32
 */

public class NotImplementedException extends FrameworkException
{

	private static final long serialVersionUID = 870167953834215097L;

	public NotImplementedException( final String message )
	{
		super( message );
	}

	public NotImplementedException( final String message, final Throwable cause )
	{
		super( message, cause );
	}

	public NotImplementedException( final String message, final IllegalAccessException cause )
	{
		super( message, cause );
	}
}
