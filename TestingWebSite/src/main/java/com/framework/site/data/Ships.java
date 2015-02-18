package com.framework.site.data;

import org.apache.commons.lang3.StringUtils;


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
	UNKNOWN( "UNKNOWN" ),

	PRIDE( "PR" ),

	FREEDOM( "FD" ),

	SUNSHINE( "SH" ),

	BREEZE( "BR" ),

	MAGIC( "MC" ),

	TRIUMPH( "TI" ),

	GLORY( "GL" ),

	CONQUEST( "CQ" ),

	DREAM( "DR" ),

	ECSTASY( "EC" ),

	SPLENDOR( "SL" ),

	VALOR( "VA" ),

	LEGEND( "LE" ),

	LIBERTY( "LI" ),

	ELATION( "EL" ),

	VICTORY( "VI" ),

	MIRACLE( "MI" ),

	FASCINATION( "FS" ),

	FANTASY( "FA" ),

	SENSATION( "SE" ),

	INSPIRATION( "IS" ),

	IMAGINATION( "IM" ),

	VISTA( "VS" ),

	PARADISE( "PA" );

	// ------------------------------------------------------------------------------------------ //

	// ------------------------------------------------------------------------------------------ //

	//region Ships - Members

	private final String id;

	private final String shipFullName;

	private final String shipName;

	private final static String CARNIVAL = "Carnival ";

	//endregion


	//region Ships - Enumeration constructors

	private Ships( final String id )
	{
		this.id = id;
		this.shipFullName = CARNIVAL + StringUtils.capitalize( name().toLowerCase() );
		this.shipName = StringUtils.capitalize( name().toLowerCase() );
	}

	//endregion


	//region Ships getters

	public String getShipName()
	{
		return shipName;
	}

	public String getId()
	{
		return id;
	}

	public String getFullName()
	{
		return shipFullName;
	}

	//endregion


	//region Ships - Search

	public static Ships valueById( String id )
	{
		for ( Ships e : values() )
		{
			if ( e.getId().equals( id.toUpperCase() ) )
			{
				return e;
			}
		}
		return null;
	}

	public static Ships valueByName( String name )
	{
		for ( Ships e : values() )
		{
			if ( e.shipFullName.equals( name ) )
			{
				return e;
			}
		}
		return null;
	}

	//endregion


	@Override
	public String toString()
	{
		return shipName;
	}
}
