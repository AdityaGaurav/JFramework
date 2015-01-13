package com.framework.site.data;

/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.site.objects.body
 *
 * Name   : Ports
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-01-10
 *
 * Time   : 03:09
 */

public enum DeparturePorts
{

	BALTIMORE( "Baltimore, MD", "BWI" ),

	BARBADOS( "Barbados", "BDS" ),

	CHARLESTON( "Charleston, SC", "CHS" ),

	FORT_LAUDERDALE( "Fort Lauderdale, FL", "FLL" ),

	GALVESTON( "Galveston, TX", "GAL" ),

	HONOLULU( "Honolulu, HI", "HNL" ),

	JACKSONVILLE( "Jacksonville, FL", "JAX" ),

	LOS_ANGELES( "Los Angeles, CA", "LAX" ),

	MIAMI( "Miami, FL", "MIA" ),

	NEW_ORLEANS( "New Orleans, LA", "MSY" ),

	NEW_YORK( "New York, NY", "NYC" ),

	NORFOLK( "Norfolk, VA", "ORF" ),

	PORT_CANAVERAL( "Port Canaveral (Orlando), FL", "PCV" ),

	SAN_JUAN( "San Juan, Puerto Rico", "SJU" ),

	SEATTLE( "Seattle, WA", "SEA" ),

	TAMPA( "Tampa, FL", "TPA" ),

	VANCOUVER( "Vancouver, BC, Canada", "YVR" );

	// ------------------------------------------------------------------------------------------ //

	private final String title;

	private final String id;

	private DeparturePorts( final String title, final String id )
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

	public static DeparturePorts valueByName( String name )
	{
		for ( DeparturePorts e : values() )
		{
			if ( e.getTitle().toLowerCase().contains( name.toLowerCase() ) )
			{
				return e;
			}
		}
		return null;
	}

	public static DeparturePorts valueById( String id )
	{
		for ( DeparturePorts e : values() )
		{
			if ( e.getId().equals( id.toUpperCase() ) )
			{
				return e;
			}
		}
		return null;
	}
}
