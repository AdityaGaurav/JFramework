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
	BALTIMORE( "MD", "BWI", "baltimore" ),

	BARBADOS( StringUtils.EMPTY, "BDS", "barbados" ),

	CHARLESTON( "SC", "CHS", "charleston" ),

	FORT_LAUDERDALE( "FL", "FLL", "ft-lauderdale" ),

	GALVESTON( "TX", "GAL", "galveston" ),

	HONOLULU( "HI", "HNL", "honolulu" ),

	JACKSONVILLE( "FL", "JAX", "jacksonville" ),

	LOS_ANGELES( "CA", "LAX", "los-angeles" ),

	MIAMI( "FL", "MIA", "miami" ),

	NEW_ORLEANS( "LA", "MSY", "new-orleans" ),

	NEW_YORK( "NY", "NYC", "new-york" ),

	NORFOLK( "VA", "ORF", "norfolk" ),

	PORT_CANAVERAL( "Port Canaveral (Orlando)", "FL", "PCV", "port-canaveral" ),

	SAN_JUAN( "Puerto Rico", "SJU", "san-juan" ),

	SEATTLE( "WA", "SEA", "seattle" ),

	TAMPA( "FL", "TPA", "tampa" ),

	VANCOUVER( "BC, Canada", "YVR", "vancouver" ),

	TRIESTE( "Italy", "TRS", "trieste" ),

	ATHENS( "Greece", "", "athens" ),

	BARCELONA( "Spain", "", "barcelona" ),

	SIDNEY( "", "", "sidney" ),

	SINGAPORE( "", "", "singapore" );

	// ------------------------------------------------------------------------------------------ //

	//region DeparturePorts - Members

	private final String id;

	private final String departurePort;

	private final String href;



	//endregion


	//region DeparturePorts - Enumeration constructors

	private DeparturePorts( final String stateCountry, final String id, final String href )
	{
		this.id = id;
		this.href = href;
		String city =  StringUtils.capitalize( name().replaceAll( "_", " " ).toLowerCase() );
		this.departurePort = ( StringUtils.isEmpty( stateCountry ) ) ? city + ", " + stateCountry : city;
	}

	private DeparturePorts( final String port, final String stateCountry, final String id, final String href )
	{
		this.id = id;
		this.href = href;
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

	public String getHref()
	{
		return href;
	}

	//endregion


	//region DeparturePorts - Search

	public static DeparturePorts valueByHRef( String href )
	{
		for ( DeparturePorts e : values() )
		{
			if ( href.contains( e.getHref() ) )
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

	public static DeparturePorts convert( String port )
	{
		if( StringUtils.startsWithIgnoreCase( port, "Los Angeles" ) ) return DeparturePorts.LOS_ANGELES;
		else if( StringUtils.startsWithIgnoreCase( port, "Baltimore" ) ) return DeparturePorts.BALTIMORE;
		else if( StringUtils.startsWithIgnoreCase( port, "Barbados" ) ) return DeparturePorts.BARBADOS;
		else if( StringUtils.startsWithIgnoreCase( port, "Charleston" ) ) return DeparturePorts.CHARLESTON;
		else if( StringUtils.startsWithIgnoreCase( port, "Galveston" ) ) return DeparturePorts.GALVESTON;
		else if( StringUtils.startsWithIgnoreCase( port, "Fort" ) ) return DeparturePorts.FORT_LAUDERDALE;
		else if( StringUtils.startsWithIgnoreCase( port, "Honolulu" ) ) return DeparturePorts.HONOLULU;
		else if( StringUtils.startsWithIgnoreCase( port, "Jacksonville" ) ) return DeparturePorts.JACKSONVILLE;
		else if( StringUtils.startsWithIgnoreCase( port, "Miami" ) ) return DeparturePorts.MIAMI;
		else if( StringUtils.startsWithIgnoreCase( port, "New Orleans" ) ) return DeparturePorts.NEW_ORLEANS;
		else if( StringUtils.startsWithIgnoreCase( port, "New York" ) ) return DeparturePorts.NEW_YORK;
		else if( StringUtils.startsWithIgnoreCase( port, "Norfolk" ) ) return DeparturePorts.NORFOLK;
		else if( StringUtils.startsWithIgnoreCase( port, "Port Canaveral" ) ) return DeparturePorts.PORT_CANAVERAL;
		else if( StringUtils.startsWithIgnoreCase( port, "San Juan" ) ) return DeparturePorts.SAN_JUAN;
		else if( StringUtils.startsWithIgnoreCase( port, "Seattle" ) ) return DeparturePorts.SEATTLE;
		else if( StringUtils.startsWithIgnoreCase( port, "Tampa" ) ) return DeparturePorts.TAMPA;
		else if( StringUtils.startsWithIgnoreCase( port, "Norfolk" ) ) return DeparturePorts.NORFOLK;
		else if( StringUtils.startsWithIgnoreCase( port, "Vancouver" ) ) return DeparturePorts.VANCOUVER;
		else if( StringUtils.startsWithIgnoreCase( port, "Trieste" ) ) return DeparturePorts.TRIESTE;
		else if( StringUtils.startsWithIgnoreCase( port, "Athens" ) ) return DeparturePorts.ATHENS;
		else if( StringUtils.startsWithIgnoreCase( port, "Barcelona" ) ) return DeparturePorts.BARCELONA;
		else if( StringUtils.startsWithIgnoreCase( port, "Sidney" ) ) return DeparturePorts.SIDNEY;
		else if( StringUtils.startsWithIgnoreCase( port, "Singapore" ) ) return DeparturePorts.SINGAPORE;
		else return null;
	}

	//endregion


	@Override
	public String toString()
	{
		return name();
	}
}
