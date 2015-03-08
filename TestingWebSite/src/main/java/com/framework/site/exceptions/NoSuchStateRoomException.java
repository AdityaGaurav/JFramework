package com.framework.site.exceptions;

/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.site.exceptions
 *
 * Name   : InvalidPortException
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-01-11
 *
 * Time   : 15:34
 */

public class NoSuchStateRoomException extends IllegalArgumentException
{
	private static final long serialVersionUID = 7978996500317505389L;

	public NoSuchStateRoomException( final String message )
	{
		super( message );
	}
}
