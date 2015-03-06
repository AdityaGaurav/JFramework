package com.framework.site.data;

import com.framework.site.config.SiteSessionManager;
import com.framework.site.pages.core.HomePage;


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
	TWO_FIVE_DAYS( SiteSessionManager.get().getCurrentLocale().equals( HomePage.AU ) ? "2 – 5 Days" : "2-5 Days", "D1"),

	SIX_NINE_DAYS( SiteSessionManager.get().getCurrentLocale().equals( HomePage.AU ) ? "6 – 9 Days" : "6-9 Days", "D2" ),

	TEN_PLUS_DAYS( "10+ Days", "D3" );

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
			if ( e.getTitle().equalsIgnoreCase( name ) )
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

	public static TripDurations valueByHRef( String href )
	{
		for ( TripDurations e : values() )
		{
			String id = e.getId();
			if( href.endsWith( id ) )
			{
				return e;
			}
		}
		return null;
	}

	@Override
	public String toString()
	{
		return title;
	}
}
