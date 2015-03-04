package com.framework.site.objects.body.interfaces;

import com.framework.driver.event.HtmlElement;
import com.framework.site.data.DeparturePorts;
import com.framework.site.data.Destinations;
import com.framework.site.data.TripDurations;
import com.google.common.base.Optional;
import org.openqa.selenium.By;

import java.util.List;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.site.objects.body.interfaces
 *
 * Name   : AppFilters
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-01-10
 *
 * Time   : 02:36
 */

public interface FilterCategories
{
	static final String LOGICAL_NAME = "filter Categories Container";

	static final By ROOT_BY = By.cssSelector( "div.filter-block.app-filters[data-app='ships']" );

	void collapse();

	void expand();

	boolean isExpanded();

	List<HtmlElement> getCategories();

	HtmlElement getModeToggle();

	void filterByDeparturePort( DeparturePorts... ports );

	void filterByDestination( Destinations... destinations );

	void filterByTripDurations( TripDurations... durations );

	HtmlElement getCurrentFiltersSection();

	Optional<HtmlElement> filterElementExists( String dataVal );

	List<DeparturePorts> getAvailableDeparturePorts();

	List<Destinations> getAvailableDestinations();

	HtmlElement getFilterItem( DeparturePorts port );

	HtmlElement getFilterItem( Destinations destination );

	HtmlElement getClearAllFilters();

	void clearFilters();
}
