package com.framework.driver.exceptions;

import com.framework.driver.event.HtmlDriver;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.driver.exceptions
 *
 * Name   : ApplicationItemNotFound 
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-01-25 
 *
 * Time   : 23:29
 *
 */

public abstract class ApplicationItemNotFoundException extends ApplicationException
{

	protected ApplicationItemNotFoundException( final HtmlDriver driver, final String message )
	{
		super( driver, message );
	}

	protected ApplicationItemNotFoundException( final HtmlDriver driver, final Throwable cause )
	{
		super( driver, cause );
	}

	protected ApplicationItemNotFoundException( final HtmlDriver driver, final String message, final Throwable cause )
	{
		super( driver, message, cause );
	}
}
