package com.framework.site.data;

import org.apache.commons.lang3.StringUtils;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.site.objects.body
 *
 * Name   : Destinations
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-01-10
 *
 * Time   : 02:39
 */

public enum Destinations
{
	ALASKA( "A" ),

	BAHAMAS( "BH" ),

	BERMUDA( "BM" ),

	CANADA_NEW_ENGLAND( "Canada/New England", "NN" ),

	CARIBBEAN( "C" ),

	CRUISE_TO_NOWHERE( "CN" ),

	HAWAII( "H" ),

	MEXICO( "M" ),

	EUROPE( "E" ),

	TRANSATLANTIC( "?" );

	// ------------------------------------------------------------------------------------------ //

	//region Destinations - Members

	private final String id;

	private final String destination;

	//endregion


	//region Destinations - Enumeration constructors

	private Destinations( final String destination, final String id )
	{
		this.id = id;
		this.destination = destination;
	}

	private Destinations( final String id )
	{
		this.id = id;
		this.destination = StringUtils.capitalize( name().replaceAll( "_", " " ).toLowerCase() );
	}

	//endregion


	//region Destinations getters

	public String getDestination()
	{
		return destination;
	}

	public String getId()
	{
		return id;
	}

	public String getCapitalized()
	{
		return StringUtils.capitalize( name().toLowerCase() );
	}

	//endregion


	//region Destinations - Search

	public static Destinations valueByName( String name )
	{
		for ( Destinations e : values() )
		{
			if ( e.toString().toLowerCase().contains( name.toLowerCase() ) )
			{
				return e;
			}
		}
		return null;
	}

	public static Destinations valueById( String id )
	{
		for ( Destinations e : values() )
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
		return destination;
	}

}

