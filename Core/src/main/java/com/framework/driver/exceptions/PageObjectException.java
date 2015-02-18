package com.framework.driver.exceptions;

import com.framework.driver.event.HtmlDriver;
import com.framework.driver.event.HtmlElement;


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

	public PageObjectException( final String message )
	{
		super( message );
	}

	public PageObjectException( final String message, final Throwable cause )
	{
		super( message, cause );
	}

	public PageObjectException( final Throwable cause )
	{
		super( cause );
	}

	public PageObjectException( final HtmlDriver driver, final String message )
	{
		super( driver, message );
	}

	public PageObjectException( final HtmlDriver driver, final String message, final Throwable cause )
	{
		super( driver, message, cause );
	}

	public PageObjectException( final HtmlDriver driver, final Throwable cause )
	{
		super( driver, cause );
	}

	public PageObjectException( final HtmlElement element, final String message )
	{
		super( element, message );
	}

	public PageObjectException( final HtmlElement element, final Throwable cause )
	{
		super( element, cause );
	}

	public PageObjectException( final HtmlElement element, final String message, final Throwable cause )
	{
		super( element, message, cause );
	}
}
