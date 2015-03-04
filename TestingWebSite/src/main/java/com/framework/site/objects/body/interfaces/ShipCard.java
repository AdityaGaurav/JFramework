package com.framework.site.objects.body.interfaces;

import com.framework.driver.event.HtmlElement;
import com.framework.site.data.DeparturePorts;
import com.framework.site.data.Destinations;
import com.framework.site.data.Ships;
import com.framework.site.data.TripDurations;
import com.framework.site.pages.BaseCarnivalPage;
import com.framework.site.pages.core.cruisefrom.CruiseFromPortPage;
import com.framework.site.pages.core.cruiseships.CruiseShipsDetailsPage;

import java.util.List;
import java.util.Set;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.site.objects.body.interfaces
 *
 * Name   : ShipCard
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-01-10
 *
 * Time   : 02:28
 */

public interface ShipCard
{
	static final String LOGICAL_NAME = "Ship Card";

	/**
	 * @return the {@linkplain com.framework.site.data.Ships} value of the card type
	 */
	Ships getShip();

	/**
	 * @return  a {@code List} of assigned {@linkplain com.framework.site.data.Destinations} values.
	 */
	List<Destinations> getDestinations();

	/**
	 * @return  a {@code List} of assigned {@linkplain com.framework.site.data.DeparturePorts} values.
	 */
	List<DeparturePorts> getDeparturePorts();

	/**
	 * @return  a {@code List} of assigned {@linkplain com.framework.site.data.TripDurations} values.
	 */
	List<TripDurations> getTripDurations();

	String getExpectedImagePath();

	/**
	 * clicks on compare/added by {@code check} argument.
	 *
	 * @param check indicates to compare the ship or remove from comparison.
	 */
 	void doCompare( boolean check );

	/**
	 * determine if the current card compare button is on added or compare state
	 *
	 * @return {@code true} if the compare option was selected, {@code false} otherwise.
	 */
	boolean isComparing();

	boolean isComparable();

	HtmlElement getImage();

	/**
	 * Select ( clicks )  a port from the 'Departure Port' assigned list
	 *
	 * @param departurePort the {@linkplain com.framework.site.data.DeparturePorts} value to select.
	 *
	 * @return a new {@code CruiseFromPortPage} instance page.
	 *
	 * @throws com.framework.site.exceptions.NoSuchDeparturePortException
	 */
	CruiseFromPortPage selectDeparturePort( DeparturePorts departurePort );

	/**
	 * Select ( clicks )  a port from the 'Destination' assigned list
	 *
	 * @param destination the {@linkplain com.framework.site.data.Destinations} value to select.
	 *
	 * @return a new {@code CruiseToDestinationPage} instance
	 *
	 * @throws com.framework.site.exceptions.NoSuchDestinationException in case that the destination was not assigned to ship card.
	 */
	BaseCarnivalPage selectDestination( Destinations destination );

	/**
	 * Select ( clicks )  a port from the 'Trip Duration' assigned list
	 *
	 * @param tripDuration the {@linkplain com.framework.site.data.TripDurations} value to select.
	 *
	 * @return a new {@code FindACruisePage} instance page.
	 *
	 * @throws com.framework.site.exceptions.NoSuchDeparturePortException
	 */
	BaseCarnivalPage selectTripDuration( TripDurations tripDuration );

	/**
	 * Clicks on the ship title link.
	 *
	 * @return a matching cruise ship title page
	 *
	 * @see com.framework.site.pages.core.cruiseships.CruiseShipsDetailsPage
	 */
	CruiseShipsDetailsPage clickShipTitle();

	/**
	 * Clicks on the ship image link.
	 *
	 * @return a matching cruise ship title page
	 *
	 * @see com.framework.site.pages.core.cruiseships.CruiseShipsDetailsPage
	 */
	CruiseShipsDetailsPage clickShipImage();

	/**
	 * @return a unique Set of departure names. also validates no duplicates.
	 */
	Set<String> getDeparturePortNames();

	/**
	 * @return u unique Set of destinations names. also validates no duplicates.
	 */
	Set<String> getDestinationNames();

	/**
	 * @return u unique Set of trip duration names. also validates no duplicates.
	 */
	Set<String> getTripDurationNames();
}
