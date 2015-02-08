package com.framework.driver.exceptions;

/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.driver.exceptions
 *
 * Name   : WrongPageError
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-01-17
 *
 * Time   : 02:48
 */

public class WrongPageError extends AssertionError
{

	private static final long serialVersionUID = 1L;

	public WrongPageError( final String message )
	{
		super( message );
	}
}
