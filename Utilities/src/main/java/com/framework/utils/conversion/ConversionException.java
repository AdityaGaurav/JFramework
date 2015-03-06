package com.framework.utils.conversion;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.utils.conversion
 *
 * Name   : ConversionException 
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-03-05 
 *
 * Time   : 21:23
 *
 */

public class ConversionException extends RuntimeException
{

	//region ConversionException - Variables Declaration and Initialization Section.

	private static final Logger LOG = LoggerFactory.getLogger( ConversionException.class );

	private static final long serialVersionUID = 2754598309819001819L;

	//endregion

	public ConversionException( final String message )
	{
		super( message );
	}

	public ConversionException( final String message, final Throwable cause )
	{
		super( message, cause );
	}

	public ConversionException( final Throwable cause )
	{
		super( cause );
	}
}
