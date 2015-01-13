package com.framework.site.data;

/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.site.objects.body
 *
 * Name   : Ships
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-01-10
 *
 * Time   : 02:50
 */

public enum Ships
{

	PRIDE( "Carnival Pride" ,"PR" ),

	FREEDOM( "Carnival Freedom", "FD" ),

	SUNSHINE( "Carnival Sunshine", "SH" ),

	BREEZE( "Carnival Breeze", "BR" ),

	MAGIC( "Carnival Magic", "MC" ),

	TRIUMPH( "Carnival Triumph", "TI" ),

	GLORY( "Carnival Glory", "GL" ),

	CONQUEST( "Carnival Conquest", "CQ" ),

	DREAM( "Carnival Dream", "DR" ),

	ECSTASY( "Carnival Ecstasy", "EC" ),

	SPLENDOR( "Carnival Splendor", "SL" ),

	VALOR( "Carnival Valor", "VA" ),

	LEGEND( "Carnival Legend", "LE" ),

	LIBERTY( "Carnival Liberty", "LI" ),

	ELATION( "Carnival Elation", "EL" ),

	VICTORY( "Carnival Victory", "VI" ),

	MIRACLE( "Carnival Miracle", "MI" ),

	FASCINATION( "Carnival Fascination", "FS" ),

	FANTASY( "Carnival Fantasy", "FA" ),

	SENSATION( "Carnival Sensation", "SE" ),

	INSPIRATION( "Carnival Inspiration", "IS" ),

	IMAGINATION( "Carnival Imagination", "IM" ),

	VISTA( "Carnival Vista", "VA" ),

	PARADISE( "Carnival Paradise", "PA" );

	// ------------------------------------------------------------------------------------------ //

	private final String title;

	private final String id;

	private Ships( final String title, final String id )
	{
		this.title = title;
		this.id = id;
	}

	public String getTitle()
	{
		return title;
	}

	public String getId()
	{
		return id;
	}

	public static Ships valueById( String id )
	{
		for ( Ships e : values() )
		{
			if ( e.getId().toUpperCase().equals( id.toUpperCase() ) )
			{
				return e;
			}
		}
		return null;
	}
}
