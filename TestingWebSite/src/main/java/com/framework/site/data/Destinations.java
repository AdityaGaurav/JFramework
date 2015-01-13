package com.framework.site.data;

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
	ALASKA( "Alaska", "A" ),

	BAHAMAS( "Bahamas", "BH" ),

	BERMUDA( "Bermuda", "BM" ),

	NEW_ENGLAND( "Canada/New England", "NN" ),

	CARIBBEAN( "Caribbean", "C" ),

	NOWHERE( "Cruise To Nowhere", "CN" ),

	HAWAII( "Hawaii", "H" ),

	MEXICO( "Mexico", "M" );

	// ------------------------------------------------------------------------------------------ //

	private final String title;

	private final String id;

	private Destinations( final String title, final String id )
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

	public static Destinations valueByName( String name )
	{
		for ( Destinations e : values() )
		{
			if ( e.getTitle().toLowerCase().contains( name.toLowerCase() ) )
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
}

