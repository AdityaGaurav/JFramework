package com.framework.site.pages.core.cruiseships;

import com.framework.driver.event.HtmlElement;
import com.framework.site.data.DeparturePorts;
import com.framework.site.data.Destinations;
import com.framework.site.data.Ships;
import com.framework.site.data.TripDurations;
import com.framework.site.objects.body.ships.ContentBlockComparingObject;
import com.framework.site.pages.BaseCarnivalPage;
import com.framework.site.pages.bookingengine.CruiseSearchPage;
import com.framework.testing.annotations.DefaultUrl;
import com.framework.utils.matchers.JMatchers;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.hamcrest.Matcher;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.site.pages.core.cruiseships
 *
 * Name   : CompareCruiseShipsPage 
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-02-16 
 *
 * Time   : 00:10
 *
 */

@DefaultUrl( value = "/cruise-ships/compare-cruise-ships", matcher = "endsWith()" )
public class CompareCruiseShipsPage extends BaseCarnivalPage
{

	//region CompareCruiseShipsPage - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( CompareCruiseShipsPage.class );

	public enum ItineraryRows{ BACK, DESTINATION, DEPARTURE, TRIP, SAILINGS }

	public static final String IMG_BLUE_X = "img/blue-x.png";

	public static final String IMG_BLUE_BULLET = "img/blue-bullet.png";

	private static final String LOGICAL_NAME = "Compare Cruise Ships";

	private static int compareShipsCount = 1;

	// ------------------------------------------------------------------------|
	// --- WEB-OBJECTS DEFINITIONS --------------------------------------------|
	// ------------------------------------------------------------------------|

	private ContentBlockComparingObject contentBlockComparing = null;

	// ------------------------------------------------------------------------|
	// --- WEB-OBJECTS CACHING ------------------------------------------------|
	// ------------------------------------------------------------------------|

	private HtmlElement legend, table;

	private List<HtmlElement> table_rows = Lists.newArrayList();

	//endregion


	//region CompareCruiseShipsPage - Constructor Methods Section

	public CompareCruiseShipsPage()
	{
		super( LOGICAL_NAME );
		validatePageInitialState();
	}


	//endregion


	//region CompareCruiseShipsPage - Initialization and Validation Methods Section

	@Override
	protected void validatePageInitialState()
	{
		logger.info( "validating page initial state for: <{}>, name:<{}>...", getQualifier(), getLogicalName() );

		String REASON = "Validate that comparison is among/between " + compareShipsCount + " ships";
		String ACTUAL_RESULT_STR = findContentBlockDiv().getAttribute( "class" );
		Matcher<String> EXPECTED_OF_STR = JMatchers.endsWith( String.format( "comparing-%d", compareShipsCount ) );
		getDriver().assertThat( REASON, ACTUAL_RESULT_STR, EXPECTED_OF_STR );

		REASON = "Validate Legend keys";
		ACTUAL_RESULT_STR = findLegendDiv().getAttribute( "textContent" ).trim();
		EXPECTED_OF_STR = JMatchers.allOf(  JMatchers.containsString( "Key:" ),
				JMatchers.containsString( "Available" ),
				JMatchers.containsString( "| Not Available" ) );
		getDriver().assertThat( REASON, ACTUAL_RESULT_STR, EXPECTED_OF_STR );

		List<HtmlElement> images = findLegendImages();
		REASON = "Validate Legend Image Available";
		ACTUAL_RESULT_STR = images.get( 0 ).getAttribute( "class" );
		EXPECTED_OF_STR = JMatchers.is( "availableIcon" );
		getDriver().assertThat( REASON, ACTUAL_RESULT_STR, EXPECTED_OF_STR );

		REASON = "Validate Legend Image Not Available";
		ACTUAL_RESULT_STR = images.get( 1 ).getAttribute( "class" );
		EXPECTED_OF_STR = JMatchers.is( "notAvailableIcon" );
		getDriver().assertThat( REASON, ACTUAL_RESULT_STR, EXPECTED_OF_STR );
	}


	//endregion


	//region CompareCruiseShipsPage - Service Methods Section

	public static void forShips( final int compareShipsCount )
	{
		CompareCruiseShipsPage.compareShipsCount = compareShipsCount;
	}

	public ContentBlockComparingObject contentBlockComparing()
	{
		if( null == this.contentBlockComparing )
		{
			this.contentBlockComparing =
					new ContentBlockComparingObject( getDriver().findElement( ContentBlockComparingObject.ROOT_BY ), compareShipsCount );
		}

		return this.contentBlockComparing;
	}

	//endregion


	//region CompareCruiseShipsPage - Business Methods Section

	public String[] getLegendKeys()
	{
		List<String> response = Lists.newArrayListWithCapacity( 3 );

	  	HtmlElement he = findLegendDiv();
		String text = he.getAttribute( "textContent" );
		String[] keys = StringUtils.split( text, "\n" );
		for( String key : keys )
		{
			key = StringUtils.remove( key, "\t" ).trim();
			if( key.length() > 0 )
			{
				response.add( key );
			}
		}
		//logger.info( "Return the legend text: < {} >", Lambda.join( result, "," ) );
		return response.toArray( new String[ 3 ] );
	}

	public HtmlElement getLegendNotAvailableImg()
	{
		HtmlElement he = findLegendDiv().findElement( By.className( "notAvailableIcon" ) );
		logger.info( "return notAvailableIcon from legend. src: < {} >", he.getAttribute( "src" ) );
		return he;
	}

	public HtmlElement getLegendAvailableImg()
	{
		HtmlElement he = findLegendDiv().findElement( By.className( "availableIcon" ) );
		logger.info( "return availableIcon from legend. src: < {} >", he.getAttribute( "src" ) );
		return he;
	}

	public HtmlElement getItinerarySubSection( ItineraryRows subSection )
	{
		if( table_rows.size() == 0 )
		{
			table_rows = itineraryCompareTable().findElements( By.tagName( "tr" ) );
		}
		for( HtmlElement row : table_rows )
		{
			String text = row.findElement( By.tagName( "th" ) ).getText();
			String textContent = row.getWrappedElement().getAttribute( "textContent" );
			if( StringUtils.startsWithIgnoreCase( text, subSection.name() ) )
			{
				row.scrollIntoView( false );
				return row;
			}
			else if( StringUtils.containsIgnoreCase( textContent, subSection.name() ) )
			{
				row.scrollIntoView( false );
				return row;
			}
		}

		return null;
	}

	public List<Ships> getItineraryShips( HtmlElement itineraryRow )
	{
		logger.info( "Returning a list of itinerary ships..." );
		List<HtmlElement> tds = itineraryRow.findElements( By.className( "preview" ) );
		List<Ships> ships = Lists.newArrayList();
		for( HtmlElement td : tds )
		{
			String shipName = td.findElement( By.tagName( "a" ) ).getText();
			Ships ship = Ships.valueByName( shipName );
			ships.add( ship );
		}

		return ships;
	}

	public List<Destinations> getItineraryDestinations( HtmlElement itineraryRow, int shipIndex )
	{
		logger.info( "Returning a list of itinerary destinations for ship index < {} >", shipIndex );
		List<HtmlElement> tds = itineraryRow.findElements( By.tagName( "td" ) );
		List<Destinations> destinations = Lists.newArrayList();
		HtmlElement td = tds.get( shipIndex );
		List<HtmlElement> anchors = td.findElements( By.tagName( "a" ) );
		for( HtmlElement anchor : anchors )
		{
			destinations.add( Destinations.valueByHRef( anchor.getAttribute( "href" ) ) );
		}

		return destinations;
	}

	public List<DeparturePorts> getItineraryDepartures( HtmlElement itineraryRow, int shipIndex )
	{
		logger.info( "Returning a list of itinerary departure ports for ship index < {} >", shipIndex );
		List<HtmlElement> tds = itineraryRow.findElements( By.tagName( "td" ) );
		List<DeparturePorts> departures = Lists.newArrayList();
		HtmlElement td = tds.get( shipIndex );
		List<HtmlElement> anchors = td.findElements( By.tagName( "a" ) );
		for( HtmlElement anchor : anchors )
		{
			departures.add( DeparturePorts.valueByHRef( anchor.getAttribute( "href" ) ) );
		}

		return departures;
	}

	public List<TripDurations> getItineraryTripDurations( HtmlElement itineraryRow, int shipIndex )
	{
		logger.info( "Returning a list of itinerary trip durations for ship index < {} >", shipIndex );
		List<HtmlElement> tds = itineraryRow.findElements( By.tagName( "td" ) );
		List<TripDurations> durations = Lists.newArrayList();
		HtmlElement td = tds.get( shipIndex );
		List<HtmlElement> anchors = td.findElements( By.tagName( "a" ) );
		for( HtmlElement anchor : anchors )
		{
			durations.add( TripDurations.valueByHRef( anchor.getAttribute( "href" ) ) );
		}

		return durations;
	}

	public CruiseSearchPage clickViewSailings( HtmlElement itineraryRow, int shipIndex, Ships ship )
	{
		logger.info( "Clicking on \"View Sailings\" for ship index < {} >", shipIndex );
		List<HtmlElement> tds = itineraryRow.findElements( By.tagName( "td" ) );
		HtmlElement td = tds.get( shipIndex );
		td.findElement( By.className( "blue-cta" ) ).click();
		CruiseSearchPage csp = new CruiseSearchPage();
		csp.setShip( ship );
		return csp;
	}

	//endregion


	//region CompareCruiseShipsPage - Element Finder Methods Section

	private HtmlElement findContentBlockDiv()
	{
		final By findBy = By.cssSelector( "div.content-block.comparing-" + compareShipsCount );
		return getDriver().findElement( findBy );
	}

	private HtmlElement findLegendDiv()
	{
		if( null == legend )
		{
			final By findBy = By.className( "legend" );
			legend = getDriver().findElement( findBy );
		}

		return legend;
	}

	private List<HtmlElement> findLegendImages()
	{
		final By findBy = By.tagName( "img" );
		return findLegendDiv().findElements( findBy );
	}

	private HtmlElement itineraryCompareTable()
	{
		if( null == table )
		{
			final By findBy = By.cssSelector( "div.compare-section.itinerary-compare table" );
			table = getDriver().findElement( findBy );
		}
		return table;
	}

	//endregion

}
