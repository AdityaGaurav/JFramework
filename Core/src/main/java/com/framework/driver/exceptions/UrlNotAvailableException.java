package com.framework.driver.exceptions;

/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.driver.exceptions
 *
 * Name   : UrlApplicationException 
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-01-24 
 *
 * Time   : 00:51
 *
 */

public class UrlNotAvailableException extends Exception
{

	public UrlNotAvailableException( final Throwable cause )
	{
		super( cause );
	}

	public UrlNotAvailableException( final String message, final Throwable cause )
	{
		super( message, cause );
	}
}
