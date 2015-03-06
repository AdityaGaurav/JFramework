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
	ALASKA( "A", "alaska-cruises" ),

	BAHAMAS( "BH", "bahamas-cruises" ),

	BERMUDA( "BM", "bermuda-cruises" ),

	CANADA_NEW_ENGLAND( "NN", "canada-new-england-cruises" ),

	CARIBBEAN( "C", "caribbean-cruises" ),

	CRUISE_TO_NOWHERE( "CN", "cruise-to-nowhere" ),

	HAWAII( "H", "hawaii-cruises" ),

	MEXICO( "M", "mexico-cruises" ),

	EUROPE( "E", "europe-cruises" ),

	TRANSATLANTIC( "?", "transatlantic-cruises" ),

	AUSTRALIA_CRUISES( "Australia Cruises", "australia" ),

	LONG_CRUISES( "Long Cruises", "long-cruises" ),

	PACIFIC_ISLANDS( "Pacific Islands", "pacific-islands" );

	// ------------------------------------------------------------------------------------------ //

	//region Destinations - Members

	private final String id;

	private final String href;

	//endregion


	//region Destinations - Enumeration constructors

	private Destinations( final String id, final String href )
	{
		this.id = id;
		this.href = href;
	}

	//endregion


	//region Destinations getters

	public String getId()
	{
		return id;
	}

	public String getHref()
	{
		return href;
	}

	//endregion


	//region Destinations - Search

	public static Destinations valueByHRef( String href )
	{
		for ( Destinations e : values() )
		{
			String enumHRef = e.getHref();
			if( href.contains( enumHRef ) )
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
		return name().replace( "_", " " );
	}

}

