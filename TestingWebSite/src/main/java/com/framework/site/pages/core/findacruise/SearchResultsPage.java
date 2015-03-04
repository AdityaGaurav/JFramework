package com.framework.site.pages.core.findacruise;

import com.framework.driver.event.HtmlElement;
import com.framework.site.data.Ships;
import com.framework.site.data.TripDurations;
import com.framework.site.pages.BaseCarnivalPage;
import com.framework.utils.error.PreConditions;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.site.pages.core.findacruise
 *
 * Name   : SearchResultsPage 
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-03-03 
 *
 * Time   : 22:37
 *
 */

public class SearchResultsPage extends BaseCarnivalPage
{

	//region SearchResultsPage - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( SearchResultsPage.class );

	private static final String LOGICAL_NAME = "Search Results Page";

	private Ships ship;

	private TripDurations tripDurations;

	//endregion


	//region SearchResultsPage - Constructor Methods Section

	public SearchResultsPage()
	{
		super( LOGICAL_NAME );
	}

	//endregion


	//region SearchResultsPage - Public Methods Section

	@Override
	protected void validatePageInitialState()
	{
		logger.info( "validating static elements for: <{}>, name:<{}>...", getQualifier(), getLogicalName() );
	}

	//endregion


	//region SearchResultsPage - Service Methods Section

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

	//endregion


	//region SearchResultsPage - Business Function Section

	public String getExpectedUrlQuery()
	{
		PreConditions.checkNotNull( ship, "the ship cannot be null. use #setShip" );
		PreConditions.checkNotNull( ship, "the trip duration cannot be null. use #setTripDurations" );
		final String QUERY = "shipCode=%s&dur=%s";

		String query = String.format( QUERY, ship.getId(), tripDurations.getId() );
		logger.info( "expected url query for ship < {} > is < {} >", ship.getFullName(), query );
		return query;
	}

	public int getItinerariesFound()
	{
		int size = findItinerariesDiv().size();
		logger.info( "number of itineraries found < {} >", size );
		return size;
	}

	//endregion


	//region SearchResultsPage - HtmlElement Finder Section

	private List<HtmlElement> findItinerariesDiv()
	{
		final By findBy = By.className( "sr-item " );
		return getDriver().findElements( findBy );
	}

	//endregion

}
