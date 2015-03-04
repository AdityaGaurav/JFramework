package com.framework.site.objects.body.interfaces;

import com.framework.site.data.DeparturePorts;
import com.framework.site.data.Ships;
import com.framework.site.data.TripDurations;
import org.openqa.selenium.By;

import java.util.List;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.site.objects.body.interfaces
 *
 * Name   : RefineSearch 
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-03-02 
 *
 * Time   : 12:34
 *
 */

public interface RefineSearch
{
 	static final String LOGICAL_NAME = "Refine Search";

	static final By ROOT_BY = By.className( "search-side" );

	List<DeparturePorts> getCheckedDeparturePorts();

	List<TripDurations> getCheckedTripDurations();

	List<Ships> getCheckedShips();

	List<String> getCheckedEvents();

	List<String> getCheckedRates();

	int getItinerariesFound();


}
