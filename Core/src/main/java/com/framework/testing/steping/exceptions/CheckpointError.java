package com.framework.testing.steping.exceptions;

/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.testing.steping.exceptions
 *
 * Name   : CheckpointError 
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-03-09 
 *
 * Time   : 19:49
 *
 */

public class CheckpointError extends Error
{

	//region CheckpointError - Variables Declaration and Initialization Section.

	private static final long serialVersionUID = - 4351092620083641988L;

	//endregion


	//region CheckpointError - Constructor Methods Section

	private CheckpointError( String detailMessage )
	{
		super( detailMessage );
	}

	public CheckpointError( Object detailMessage )
	{
		this( String.valueOf( detailMessage ) );
		if ( detailMessage instanceof Throwable )
		{
			initCause( ( Throwable ) detailMessage );
		}
	}

	public CheckpointError( boolean detailMessage )
	{
		this( String.valueOf( detailMessage ) );
	}

	public CheckpointError( char detailMessage )
	{
		this( String.valueOf( detailMessage ) );
	}

	public CheckpointError( int detailMessage )
	{
		this( String.valueOf( detailMessage ) );
	}

	public CheckpointError( long detailMessage )
	{
		this( String.valueOf( detailMessage ) );
	}

	public CheckpointError( float detailMessage )
	{
		this( String.valueOf( detailMessage ) );
	}

	public CheckpointError( double detailMessage )
	{
		this( String.valueOf( detailMessage ) );
	}

	public CheckpointError( String message, Throwable cause )
	{
		super( message, cause );
	}

	//endregion

}
