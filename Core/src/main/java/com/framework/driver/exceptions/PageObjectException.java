package com.framework.driver.exceptions;

import com.framework.driver.event.HtmlDriver;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.driver.exceptions
 *
 * Name   : PageObjectException
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-01-05
 *
 * Time   : 18:48
 */

public class PageObjectException extends ApplicationException
{

	public PageObjectException( final HtmlDriver driver, final String message )
	{
		super( driver, message );
	}

	public PageObjectException( final HtmlDriver driver, final Throwable cause )
	{
		super( driver, cause );
	}

	public PageObjectException( final HtmlDriver driver, final String message, final Throwable cause )
	{
		super( driver, message, cause );
	}
}
