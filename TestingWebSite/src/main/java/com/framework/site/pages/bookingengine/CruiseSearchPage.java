package com.framework.site.pages.bookingengine;

import com.framework.driver.event.HtmlElement;
import com.framework.site.data.Ships;
import com.framework.site.data.TripDurations;
import com.framework.site.objects.body.RefineSearchObject;
import com.framework.site.objects.body.interfaces.RefineSearch;
import com.framework.site.pages.BaseCarnivalPage;
import com.framework.testing.annotations.DefaultUrl;
import com.framework.utils.error.PreConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@DefaultUrl ( value = "/bookingEngine/cruise-search/", matcher = "contains()" )
public class CruiseSearchPage extends BaseCarnivalPage
{

	//region CruiseSearchPage - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( CruiseSearchPage.class );

	private static final String SHIP_CODE = "shipCode=%s";

	private static final String TRIP_DURATION = "dur=%s";

	private static final String LOGICAL_NAME = "Cruise Search Page";

	private Ships ship;

	private TripDurations tripDurations;

	// ------------------------------------------------------------------------|
	// --- WEB-OBJECTS DEFINITIONS --------------------------------------------|
	// ------------------------------------------------------------------------|

	private RefineSearch refineSearch;

	//endregion


	//region CruiseSearchPage - Constructor Methods Section

	public CruiseSearchPage()
	{
		super( LOGICAL_NAME );
	}

	//endregion


	//region CruiseSearchPage - Initialization and Validation Methods Section

	@Override
	protected void validatePageInitialState()
	{
		logger.info( "validating static elements for: <{}>, name:<{}>...", getQualifier(), getLogicalName() );
	}

	//endregion


	//region CruiseSearchPage - Service Methods Section

	public void setShip( final Ships ship )
	{
		logger.info( "Ship < {} > was set for < {} >", ship.name(), LOGICAL_NAME );
		this.ship = ship;
	}

	public void setTripDurations( final TripDurations tripDurations )
	{
		logger.info( "Trip duration < {} > was set for < {} >", tripDurations.name(), LOGICAL_NAME );
		this.tripDurations = tripDurations;
	}

	public RefineSearch refineSearch()
	{
		if ( null == this.refineSearch )
		{
			this.refineSearch = new RefineSearchObject( findSearchSideDiv() );
		}
		return refineSearch;
	}

	//endregion


	//region CruiseSearchPage - Business Methods Section

	public String getExpectedUrlQuery()
	{
		PreConditions.checkNotNull( ship, "the ship cannot be null. use #setShip" );
		StringBuilder sb = new StringBuilder( String.format( SHIP_CODE, ship.getId() ) );
		if( tripDurations != null )
		{
			sb.append( "&" ).append( String.format( TRIP_DURATION, tripDurations.getId() ) );
		}

		logger.info( "expected url query for ship < {} > is < {} >", ship.getFullName(), sb.toString() );
		return sb.toString();
	}

	//endregion


	//region CruiseSearchPage - Element Finder Methods Section

	private HtmlElement findSearchSideDiv()
	{
		return getDriver().findElement( RefineSearch.ROOT_BY );
	}

	//endregion

}
