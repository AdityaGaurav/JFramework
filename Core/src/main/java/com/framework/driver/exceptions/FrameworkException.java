package com.framework.driver.exceptions;

import org.openqa.selenium.WebDriverException;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.driver.exceptions
 *
 * Name   : FrameworkException
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-01-03
 *
 * Time   : 21:53
 */

public class FrameworkException extends WebDriverException
{

	private static final long serialVersionUID = 6853598206454723242L;

	public FrameworkException( final String message )
	{
		super( message );
	}

	public FrameworkException( final Throwable cause )
	{
		super( cause );
	}

	public FrameworkException( final String message, final Throwable cause )
	{
		super( message, cause );
	}
}
