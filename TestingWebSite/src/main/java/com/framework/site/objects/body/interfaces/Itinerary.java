package com.framework.site.objects.body.interfaces;

import com.framework.driver.event.HtmlElement;
import com.framework.site.data.DeparturePorts;
import com.framework.site.data.Destinations;
import com.framework.site.data.ItinerarySail;
import com.framework.site.data.Ships;

import java.math.BigDecimal;
import java.util.List;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.site.objects.body.interfaces
 *
 * Name   : SearchResultItem 
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-03-13 
 *
 * Time   : 10:02
 *
 */

public interface Itinerary
{
 	String itineraryId();

	String sailEventCode();

	void ShowHideDates( boolean show );

	HtmlElement getImage();

	void compare();

	void addToFavorites();

	List<ItinerarySail> getSailings();

	ItinerarySail getTopBestPrice();

	String getTitle();

	DeparturePorts getDeparturePort();

	String getDeparturePortAsString();

	List<Destinations> getDestinations();

	Ships getShip();

	BigDecimal getPrice();

	void clickLearMore();
}

