package com.framework.driver.exceptions;

import org.openqa.selenium.WebDriverException;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.driver.exceptions
 *
 * Name   : UnsupportedDriverException 
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-01-21 
 *
 * Time   : 23:35
 *
 */

public class UnsupportedDriverException extends WebDriverException
{

	private static final long serialVersionUID = - 6037729905488938123L;

	public UnsupportedDriverException( final String message )
	{
		super( message );
	}

	/**
	 * Give some details about this very rare error.
	 */
	public UnsupportedDriverException( final String message, Throwable cause )
	{
		super( message, cause );
	}

}
