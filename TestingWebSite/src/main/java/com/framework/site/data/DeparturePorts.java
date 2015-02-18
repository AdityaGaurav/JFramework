package com.framework.site.data;

import org.apache.commons.lang3.StringUtils;


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

	BALTIMORE( "MD", "BWI" ),

	BARBADOS( StringUtils.EMPTY, "BDS" ),

	CHARLESTON( "SC", "CHS" ),

	FORT_LAUDERDALE( "FL", "FLL" ),

	GALVESTON( "TX", "GAL" ),

	HONOLULU( "HI", "HNL" ),

	JACKSONVILLE( "FL", "JAX" ),

	LOS_ANGELES( "CA", "LAX" ),

	MIAMI( "FL", "MIA" ),

	NEW_ORLEANS( "LA", "MSY" ),

	NEW_YORK( "NY", "NYC" ),

	NORFOLK( "VA", "ORF" ),

	PORT_CANAVERAL( "Port Canaveral (Orlando)", "FL", "PCV" ),

	SAN_JUAN( "Puerto Rico", "SJU" ),

	SEATTLE( "WA", "SEA" ),

	TAMPA( "FL", "TPA" ),

	VANCOUVER( "BC, Canada", "YVR" ),

	TRIESTE( "Italy", "TRS" ),

	ATHENS( "Greece", "" ),

	BARCELONA( "Spain", "" );

	// ------------------------------------------------------------------------------------------ //

	//region DeparturePorts - Members

	private final String id;

	private final String departurePort;

	//endregion


	//region DeparturePorts - Enumeration constructors

	private DeparturePorts( final String stateCountry, final String id )
	{
		this.id = id;
		String city =  StringUtils.capitalize( name().replaceAll( "_", " " ).toLowerCase() );
		this.departurePort = ( StringUtils.isEmpty( stateCountry ) ) ? city + ", " + stateCountry : city;
	}

	private DeparturePorts( final String port, final String stateCountry, final String id )
	{
		this.id = id;
		this.departurePort = ( StringUtils.isEmpty( stateCountry ) ) ? port + ", " + stateCountry : port;
	}

	//endregion


	//region DeparturePorts getters

	public String getDeparturePort()
	{
		return departurePort;
	}

	public String getId()
	{
		return id;
	}

	//endregion


	//region DeparturePorts - Search

	public static DeparturePorts valueByName( String name )
	{
		for ( DeparturePorts e : values() )
		{
			if ( e.toString().toLowerCase().contains( name.toLowerCase() ) )
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

	//endregion


	@Override
	public String toString()
	{
		return departurePort;
	}
}
