package com.framework.driver.exceptions;

import org.openqa.selenium.WebDriver;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.driver.exceptions
 *
 * Name   : ObjectApplicationNotFoundException
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-01-11
 *
 * Time   : 19:18
 */

public class ObjectApplicationNotFoundException extends ApplicationException
{
	private static final long serialVersionUID = 2129056691501602205L;

	public ObjectApplicationNotFoundException( final WebDriver driver, final String message )
	{
		super( driver, message );
	}

	public ObjectApplicationNotFoundException( final WebDriver driver, final Throwable cause )
	{
		super( driver, cause );
	}

	public ObjectApplicationNotFoundException( final WebDriver driver, final String message, final Throwable cause )
	{
		super( driver, message, cause );
	}
}
