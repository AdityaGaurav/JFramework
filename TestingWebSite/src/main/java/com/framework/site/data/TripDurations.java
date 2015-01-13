package com.framework.site.data;

/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.site.objects.body.enums
 *
 * Name   : TripDurations
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-01-10
 *
 * Time   : 03:29
 */

public enum TripDurations
{
	TwoFiveDays( "3-5 Days", "D1"),

	SixNineDays( "6-9 Days", "D2" ),

	TenPlusDays( "10+ Days", "D3" );


	private final String title;

	private final String id;

	private TripDurations( final String title, final String id )
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

	public static TripDurations valueByName( String name )
	{
		for ( TripDurations e : values() )
		{
			if ( e.getTitle().toLowerCase().contains( name.toLowerCase() ) )
			{
				return e;
			}
		}
		return null;
	}

	public static TripDurations valueById( String id )
	{
		for ( TripDurations e : values() )
		{
			if ( e.getId().equals( id.toUpperCase() ) )
			{
				return e;
			}
		}
		return null;
	}

}
