package com.framework.testing.steping.exceptions;

/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.testing.steping.exceptions
 *
 * Name   : EventBusEventException 
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-01-27 
 *
 * Time   : 13:40
 *
 */

public class EventBusEventException extends RuntimeException
{

	public EventBusEventException( final String message )
	{
		super( message );
	}

	public EventBusEventException( final String message, final Throwable cause )
	{
		super( message, cause );
	}

	public EventBusEventException( final Throwable cause )
	{
		super( cause );
	}

	public EventBusEventException( final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace )
	{
		super( message, cause, enableSuppression, writableStackTrace );
	}
}
