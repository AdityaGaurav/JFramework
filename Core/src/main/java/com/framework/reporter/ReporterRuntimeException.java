package com.framework.reporter;

/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.jreporter.reporter
 *
 * Name   : ReporterRuntimeException 
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-02-09 
 *
 * Time   : 17:49
 *
 */

public class ReporterRuntimeException extends RuntimeException
{

	public ReporterRuntimeException( final String message )
	{
		super( message );
	}

	public ReporterRuntimeException( final String message, final Throwable cause )
	{
		super( message, cause );
	}

	public ReporterRuntimeException( final Throwable cause )
	{
		super( cause );
	}
}
