package com.framework.driver.extensions.jquery;

/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.driver.extensions.jquery
 *
 * Name   : QuerySelectorNotSupportedException 
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-03-10 
 *
 * Time   : 20:33
 *
 */

public class QuerySelectorNotSupportedException extends Exception
{
	private static final long serialVersionUID = - 5098170762707505535L;

	public QuerySelectorNotSupportedException( final String message )
	{
		super( message );
	}

	public QuerySelectorNotSupportedException( final String message, final Throwable cause )
	{
		super( message, cause );
	}

	public QuerySelectorNotSupportedException( final Throwable cause )
	{
		super( cause );
	}
}
