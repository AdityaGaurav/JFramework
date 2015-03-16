package com.framework.driver.extensions.jquery;

/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.driver.extensions.jquery
 *
 * Name   : ExternalLibraryLoadException 
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-03-10 
 *
 * Time   : 20:43
 *
 */

public class ExternalLibraryLoadException extends RuntimeException
{
	private static final long serialVersionUID = 4681990286707754370L;

	public ExternalLibraryLoadException( final String message )
	{
		super( message );
	}

	public ExternalLibraryLoadException( final String message, final Throwable cause )
	{
		super( message, cause );
	}

	public ExternalLibraryLoadException( final Throwable cause )
	{
		super( cause );
	}
}
