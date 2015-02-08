package com.framework.driver.exceptions;

/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.driver.exceptions
 *
 * Name   : WebdriverAssertionError
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-01-17
 *
 * Time   : 18:48
 */

public class WebdriverAssertionError extends AssertionError
{

	private static final long serialVersionUID = 1L;

	public WebdriverAssertionError( Throwable cause )
	{
		super( cause );
		this.setStackTrace( cause.getStackTrace() );
	}
}
