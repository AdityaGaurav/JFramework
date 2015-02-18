package com.framework.driver.exceptions;


@Deprecated
public class WrongPageError extends AssertionError
{

	private static final long serialVersionUID = 1L;

	public WrongPageError( final String message )
	{
		super( message );
	}
}
