package com.framework.site.pages.bookingengine;

import com.framework.driver.event.HtmlElement;
import com.framework.driver.extensions.jquery.By;
import com.framework.site.data.Destinations;
import com.framework.site.data.Ships;
import com.framework.site.data.TripDurations;
import com.framework.site.objects.body.ItineraryObject;
import com.framework.site.objects.body.RefineSearchObject;
import com.framework.site.objects.body.interfaces.Itinerary;
import com.framework.site.pages.BaseCarnivalPage;
import com.framework.testing.annotations.DefaultUrl;
import com.framework.utils.datetime.TimeConstants;
import com.framework.utils.error.PreConditions;
import com.framework.utils.matchers.JMatchers;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


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

	private Destinations destinations;

	private String query = null;

	private List<Itinerary> itineraries = Lists.newArrayList();

	// ------------------------------------------------------------------------|
	// --- WEB-OBJECTS DEFINITIONS --------------------------------------------|
	// ------------------------------------------------------------------------|

	private RefineSearchObject refineSearch;

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

		HtmlElement he = getDriver().findElement( By.tagName( "h1" ) );
		he.createAssertion().assertThat(
				"Validate Page Title is Cruise Search", he.getAttribute( "textContent" ), JMatchers.is( "Cruise Search" ) );

		getDriver().findElement( By.className( "lpg-loader" ) ).waitToBeDisplayed( false, TimeConstants.FIVE_SECONDS );
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

	public void setDestinations( final Destinations destinations )
	{
		logger.info( "Destination < {} > was set for < {} >", destinations.name(), LOGICAL_NAME );
		this.destinations = destinations;
	}

	public void setQuery( final String query )
	{
		this.query = query;
	}

	public RefineSearchObject refineSearch()
	{
		if ( null == this.refineSearch )
		{
			this.refineSearch = new RefineSearchObject( findSearchSideDiv() );
		}
		return refineSearch;
	}

	public List<Itinerary> getItineraries()
	{
		logger.info( "Returning a list of itineraries ..." );

		if( itineraries.size() == 0 )
		{
			int expected = refineSearch().getItinerariesFound();
			List<HtmlElement> divs = Lists.newArrayList();
			while ( divs.size() < expected )
			{
				divs = findItineraries();
				footer().scrollIntoView();
			}

			for ( HtmlElement he : divs )
			{
				Itinerary itinerary = new ItineraryObject( he );
				itineraries.add( itinerary );
			}
		}

		return itineraries;
	}

	public List<Itinerary> getBestPriceUniqueItineraryList()
	{
		Map<String,Itinerary> map = Maps.newHashMap();
		List<Itinerary> itinerariesBefore = getItineraries();
		for( Itinerary before : itinerariesBefore )
		{
			String text = before.getTitle() + " " + before.getDeparturePortAsString();
			if( ! map.containsKey( text ) )
			{
				map.put( text, before );
			}
			else
			{
				BigDecimal bd1 = before.getPrice();
				BigDecimal bd2 =  map.get( text ).getPrice();
				if( bd1.compareTo( bd2 ) < 0 )
				{
					map.put( text, before );
				}
			}
		}

		return Lists.newArrayList( map.values() );
	}

	//endregion


	//region CruiseSearchPage - Business Methods Section

	public String getExpectedUrlQuery()
	{
		PreConditions.checkNotNull( ship, "the ship cannot be null. use #setShip" );

		if( query != null )
		{
			return query;
		}

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
		return getDriver().findElement( RefineSearchObject.ROOT_BY );
	}

	private List<HtmlElement> findItineraries()
	{
		return getDriver().findElements( By.cssSelector( "#results_container > .search-result.optional" ) );
	}

	//endregion

}
