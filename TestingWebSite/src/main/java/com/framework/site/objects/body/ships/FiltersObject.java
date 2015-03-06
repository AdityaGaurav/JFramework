package com.framework.site.objects.body.ships;

import ch.lambdaj.Lambda;
import com.framework.asserts.JAssertion;
import com.framework.driver.event.ExpectedConditions;
import com.framework.driver.event.HtmlCondition;
import com.framework.driver.event.HtmlElement;
import com.framework.driver.objects.AbstractWebObject;
import com.framework.site.data.DeparturePorts;
import com.framework.site.data.Destinations;
import com.framework.site.data.TripDurations;
import com.framework.site.objects.body.interfaces.FilterCategories;
import com.framework.utils.datetime.TimeConstants;
import com.framework.utils.matchers.JMatchers;
import com.framework.utils.string.LogStringStyle;
import com.google.common.base.Joiner;
import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;

import static com.framework.utils.datetime.TimeConstants.THREE_SECONDS;
import static org.hamcrest.Matchers.is;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.site.objects.body.ships
 *
 * Name   : FiltersObject 
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-02-27 
 *
 * Time   : 17:45
 *
 */

public class FiltersObject extends AbstractWebObject implements FilterCategories
{

	//region FiltersObject - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( FiltersObject.class );

	private static final String CATEGORY_PORT = "port";

	private static final String CATEGORY_DESTINATIONS = "dest";

	private static final String CATEGORY_DURATION = "dur";

	private static final String CATEGORY_CURRENT_FILTERS = "current-filters";

	// ------------------------------------------------------------------------|
	// --- WEB-OBJECTS CACHING ------------------------------------------------|
	// ------------------------------------------------------------------------|

	private HtmlElement currentFilters, filterPort, filterDestinations, filterDurations;
	private HtmlElement bodyContainer;

	private List<HtmlElement> departurePorts = null;
	private List<HtmlElement> destinations = null;

	//endregion


	//region FiltersObject - Constructor Methods Section

	public FiltersObject( final HtmlElement rootElement )
	{
		super( rootElement, FilterCategories.LOGICAL_NAME );
		initWebObject();
	}


	//endregion


	//region FiltersObject - Initialization and Validation Methods Section

	@Override
	protected void initWebObject()
	{
		logger.debug( "validating static elements for web object id: <{}>, name:<{}>...",
				getQualifier(), getLogicalName() );

		final String REASON = "assert that element \"%s\" exits";

		JAssertion assertion = getRoot().createAssertion();
		Optional<HtmlElement> e = getRoot().childExists( By.cssSelector( "div.filter-category.current-filters" ), THREE_SECONDS );
		assertion.assertThat( String.format( REASON, "div.filter-category.current-filters" ), e.isPresent(), is( true ) );
		currentFilters = e.get();

		e = getRoot().childExists( By.cssSelector( "div.filter-category.filter-port" ), THREE_SECONDS );
		assertion.assertThat( String.format( REASON, "div.filter-category.filter-port" ), e.isPresent(), is( true ) );
		filterPort = e.get();

		e = getRoot().childExists( By.cssSelector( "div.filter-category.filter-dest" ), THREE_SECONDS );
		assertion.assertThat( String.format( REASON, "div.filter-category.filter-dest" ), e.isPresent(), is( true ) );
		filterDestinations = e.get();

		e = getRoot().childExists( By.cssSelector( "div.filter-category.filter-dur" ), THREE_SECONDS );
		assertion.assertThat( String.format( REASON, "div.filter-category.filter-dur" ), e.isPresent(), is( true ) );
		filterDurations = e.get();
	}

	//endregion


	//region FiltersObject - Service Methods Section

	private HtmlElement getRoot()
	{
		return getBaseRootElement( FilterCategories.ROOT_BY );
	}

	private void showMore( HtmlElement filterBlock )
	{
		HtmlElement anchor = findSeeMore( filterBlock );
		HtmlElement span = anchor.findElement( By.tagName( "span" ) );
		span.scrollIntoView();
		String text = span.getText();
		if( text.equals( "Show Less..." ) ) return;
		anchor.click();
		span.waitTextToMatch( JMatchers.is( "Show Less..." ), TimeConstants.FIVE_SECONDS );
	}

	//endregion


	//region FiltersObject - FilterCategories Implementation Section

	@Override
	public void collapse()
	{
		if( isExpanded() )
		{
			HtmlElement anchor = findToggleAnchor();
			HtmlElement container = findBodyContainerDiv();
			anchor.click();
			String REASON = "Validates that filter was collapsed";
			HtmlCondition<Boolean> condition =
					ExpectedConditions.elementAttributeToMatch( container, "class", JMatchers.endsWith( "collapsed" ) );
			findBodyContainerDiv().createAssertion().assertWaitThat( REASON, THREE_SECONDS, condition );
		}
	}

	@Override
	public void expand()
	{
		if( ! isExpanded() )
		{
			HtmlElement anchor = findToggleAnchor();
			HtmlElement container = findBodyContainerDiv();
			anchor.click();
			String REASON = "Validates that filter was expanded";
			HtmlCondition<Boolean> condition =
					ExpectedConditions.elementAttributeToMatch( container, "class", JMatchers.not( JMatchers.endsWith( "collapse" ) ) );
			findBodyContainerDiv().createAssertion().assertWaitThat( REASON, THREE_SECONDS, condition );
		}
	}

	@Override
	public boolean isExpanded()
	{
		HtmlElement div = findBodyContainerDiv();
		String className = div.getAttribute( "class" );
		return ! className.endsWith( "collapsed" );
	}

	@Override
	public void filterByDeparturePort( final DeparturePorts... ports )
	{
		if( ! isExpanded() ) expand();

		showMore( findFilterCategory( CATEGORY_PORT ) );
		for( DeparturePorts port : ports )
		{
			logger.info( "Filtering ships by departure ports < {} >", port );
		 	String id = port.getId();
			HtmlElement dep = findItemLi( id );
			HtmlElement loader = findLoader();
			dep.findElement( By.id( "filter-port-" + id ) ).click();
			loader.waitAttributeToMatch( "class", JMatchers.endsWith( "hidden" ), TimeConstants.THREE_SECONDS );
			dep.waitAttributeToMatch( "class", JMatchers.is( "active-filter"), TimeConstants.THREE_SECONDS );
			By by = By.cssSelector( String.format( "a.remove-filter[data-val=\"%s\"]", id ) );
			Optional<HtmlElement> e = findCurrentFilter().childExists( by, TimeConstants.THREE_SECONDS );
			String REASON = "Filter " + id + " exists";
			findCurrentFilter().createAssertion().assertThat( REASON, e.isPresent(), JMatchers.is( true ) );
		}
	}

	@Override
	public List<DeparturePorts> getAvailableDeparturePorts()
	{
		if( null == departurePorts )
		{
			HtmlElement he = findFilterCategory( CATEGORY_PORT );
			departurePorts = he.findElements( By.tagName( "li" ) );
		}

		logger.info( "Read a list of available departure ports on the filter tab. size < {} >", departurePorts.size() );
		List<DeparturePorts> departurePortsList = Lists.newArrayList();
		for( HtmlElement e : departurePorts )
		{
			String id = e.getAttribute( "data-val" );
			DeparturePorts dp = DeparturePorts.valueById( id );
			departurePortsList.add( dp );
		}

		return departurePortsList;
	}

	@Override
	public List<Destinations> getAvailableDestinations()
	{
		if( null == destinations )
		{
			HtmlElement he = findFilterCategory( CATEGORY_DESTINATIONS );
			destinations = he.findElements( By.tagName( "li" ) );
		}

		logger.info( "Read a list of available destinations on the filter tab. size < {} >", destinations.size() );
		List<Destinations> destinationsList = Lists.newArrayList();
		for( HtmlElement e : destinations )
		{
			String id = e.getAttribute( "data-val" );
			Destinations dst = Destinations.valueById( id );
			destinationsList.add( dst );
		}

		return destinationsList;
	}

	@Override
	public void filterByDestination( final Destinations... destinations )
	{
		if( ! isExpanded() ) expand();

		showMore( findFilterCategory( CATEGORY_DESTINATIONS ) );
		logger.info( "Filtering ships by destinations ports < {} >", Joiner.on( "," ).join( destinations ) );
		for( Destinations destination : destinations )
		{
			String id = destination.getId();
			HtmlElement dep = findItemLi( id );
			dep.scrollIntoView();
			HtmlElement loader = findLoader();
			HtmlElement he = dep.findElement( By.id( "filter-dest-" + id ) );
			he.click();
			loader.waitAttributeToMatch( "class", JMatchers.endsWith( "hidden" ), TimeConstants.THREE_SECONDS );
			dep.waitAttributeToMatch( "class", JMatchers.is( "active-filter"), TimeConstants.THREE_SECONDS );
			By by = By.cssSelector( String.format( "a.remove-filter[data-val=\"%s\"]", id ) );
			Optional<HtmlElement> e = findCurrentFilter().childExists( by, TimeConstants.THREE_SECONDS );
			String REASON = "Filter " + id + " exists";
			findCurrentFilter().createAssertion().assertThat( REASON, e.isPresent(), JMatchers.is( true ) );
		}
	}

	@Override
	public void filterByTripDurations( final TripDurations... ports )
	{
	   	throw new NotImplementedException();
	}

	@Override
	public HtmlElement getFilterItem( final DeparturePorts port )
	{
		String id = port.getId();
		HtmlElement he = findItemLi( id );
		logger.info( "Returning the < {} > departure port filter item element: < {} >", port, he.getLocator() );
		return he;
	}

	@Override
	public HtmlElement getFilterItem( final Destinations destination )
	{
		String id = destination.getId();
		HtmlElement he = findItemLi( id );
		logger.info( "Returning the < {} > destination filter item element: < {} >", destination, he.getLocator() );
		return he;
	}

	@Override
	public List<HtmlElement> getCategories()
	{
		List<HtmlElement> categories = findCategories();
		logger.info( "Returning a list of categories. size : < {} >", categories.size() );
		return categories;
	}

	@Override
	public HtmlElement getModeToggle()
	{
		HtmlElement he = findModeToggleAnchor();
		logger.info( "Returning mode toggle element < {} >", he.getLocator() );
		return he;
	}

	@Override
	public HtmlElement getCurrentFiltersSection()
	{
		return findFilterCategory( CATEGORY_CURRENT_FILTERS );
	}

	@Override
	public Optional<HtmlElement> filterElementExists( final String dataVal )
	{
		By by = By.cssSelector( String.format( "a.remove-filter[data-val=\"%s\"]", dataVal ) );
		return findCurrentFilter().childExists( by );
	}

	@Override
	public HtmlElement getClearAllFilters()
	{
		return findClearFilters();
	}

	@Override
	public void clearFilters()
	{
		getClearAllFilters().click();
		getCurrentFiltersSection().waitToBeDisplayed( false, TimeConstants.FIVE_SECONDS );
	}

	//endregion


	//region FiltersObject - Element Finder methods section

	private HtmlElement findToggleAnchor()
	{
		final By findBy = By.className( "mode-toggle" );
		return getRoot().findElement( findBy );
	}

	private HtmlElement findBodyContainerDiv()
	{
		if( null == bodyContainer )
		{
			final By findBy = By.className( "body-container" );
			bodyContainer = getDriver().findElement( findBy );
		}

		return bodyContainer;
	}

	private HtmlElement findSeeMore( HtmlElement root )
	{
		final By findBy = By.className( "see-more" );
		return root.findElement( findBy );
	}

	private HtmlElement findFilterCategory( String category )
	{
		if( category.equals( CATEGORY_PORT ) )
		{
			if( null == filterPort )
			{
				final By findBy = By.cssSelector( "div.filter-category.filter-port" );
				filterPort = getDriver().findElement( findBy );
			}
			return filterPort;
		}
		else if( category.equals( CATEGORY_DESTINATIONS ) )
		{
			if( null == filterDestinations )
			{
				final By findBy = By.cssSelector( "div.filter-category.filter-dest" );
				filterDestinations = getDriver().findElement( findBy );
			}
			return filterDestinations;
		}
		else if( category.equals( CATEGORY_DURATION ) )
		{
			if( null == filterDurations )
			{
				final By findBy = By.cssSelector( "div.filter-category.filter-dur" );
				filterDurations = getDriver().findElement( findBy );
			}
			return filterDurations;
		}
		else
		{
			if( null == currentFilters )
			{
				final By findBy = By.cssSelector( "div.filter-category.current-filters" );
				currentFilters = getDriver().findElement( findBy );
			}
			return currentFilters;
		}
	}

	private HtmlElement findCurrentFilter()
	{
		if( null == currentFilters )
		{
			final By findBy = By.cssSelector( "div.filter-category.current-filters" );
			currentFilters = getDriver().findElement( findBy );
		}

		return currentFilters;
	}

	private HtmlElement findItemLi( String id )
	{
		final By findBy = By.cssSelector( "li[data-val=\"" + id + "\"]" );
		return getDriver().findElement( findBy );
	}

	private List<HtmlElement> findCategories()
	{
		final By findBy = By.cssSelector( "div.filter-category:not(.current-filters)" );
		return getDriver().findElements( findBy );
	}

	private HtmlElement findModeToggleAnchor()
	{
		final By findBy = By.className( "mode-toggle" );
		return getDriver().findElement( findBy );
	}

	private HtmlElement findLoader()
	{
		final By findBy = By.className( "loader" );
		return getDriver().findElement( findBy );
	}

	private HtmlElement findClearFilters()
	{
		final By findBy = By.className( "clear-filters" );
		return findFilterCategory( CATEGORY_CURRENT_FILTERS ).findElement( findBy );
	}

	private List<HtmlElement> findAllFiltersAnchors()
	{
		final By findBy = By.cssSelector( "a.remove-filter" );
		return getDriver().findElements( findBy );
	}

	@Override
	public String toString()
	{
		ToStringBuilder tsb = new ToStringBuilder( this, LogStringStyle.LOG_MULTI_LINE_STYLE );
		if( departurePorts != null && departurePorts.size() > 0 )
		{
			tsb.append( "departurePorts", Lambda.join( departurePorts, "," ) );
		}
		if( destinations != null && destinations.size() > 0 )
		{
			tsb.append( "destinations", Lambda.join( destinations, "," ) );
		}

		return tsb.toString();
	}
	//endregion

}
