package com.framework.site.objects.body;

import ch.lambdaj.Lambda;
import com.framework.asserts.JAssertion;
import com.framework.driver.event.HtmlElement;
import com.framework.driver.exceptions.ApplicationException;
import com.framework.driver.objects.AbstractWebObject;
import com.framework.site.data.DeparturePorts;
import com.framework.site.data.Ships;
import com.framework.site.data.TripDurations;
import com.framework.site.objects.body.interfaces.RefineSearch;
import com.framework.utils.matchers.JMatchers;
import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import org.apache.commons.configuration.PropertyConverter;
import org.hamcrest.Matcher;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.List;

import static com.framework.utils.datetime.TimeConstants.FIVE_SECONDS;
import static org.hamcrest.Matchers.is;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.site.objects.body
 *
 * Name   : RefineSearchObject 
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-03-02 
 *
 * Time   : 12:38
 *
 */

public class RefineSearchObject extends AbstractWebObject implements RefineSearch
{

	//region RefineSearchObject - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( RefineSearchObject.class );

	private static final String DEP_CODE = "embkCode";

	private static final String SHIP_CODE = "shipCode";

	private static final String DURATION_CODE = "dur";

	private static final String EVENT_CODE = "ev";

	private static final String SPECIAL_CODE = "special";

	// ------------------------------------------------------------------------|
	// --- WEB-OBJECTS CACHING ------------------------------------------------|
	// ------------------------------------------------------------------------|

	private HtmlElement sideNav, depAccordion, durAccordion, shipAccordion, evAccordion, specialAccordion;

	//endregion


	//region RefineSearchObject - Constructor Methods Section

	public RefineSearchObject( final HtmlElement rootElement )
	{
		super( rootElement, RefineSearch.LOGICAL_NAME );
		initWebObject();
	}

	//endregion


	//region RefineSearchObject - Initialization and Validation Methods Section

	@Override
	protected void initWebObject()
	{
		logger.debug( "validating static elements for web object id: <{}>, name:<{}>...",
				getQualifier(), getLogicalName() );

		final String REASON = "assert that element \"%s\" exits";

		JAssertion assertion = getRoot().createAssertion();
		Optional<HtmlElement> e = getRoot().childExists( By.className( "side-nav" ), FIVE_SECONDS );
		assertion.assertThat( String.format( REASON, ".side-nav" ), e.isPresent(), is( true ) );
		sideNav = e.get();

		String REASON1 = "validates refine search title";
		HtmlElement h2 = findNeedsPieH2();
		String ACTUAL_STR = h2.getText();
		Matcher<String> EXPECTED_OF_STR = JMatchers.equalToIgnoringCase( "Refine Search" );
		h2.createAssertion().assertThat( REASON1, ACTUAL_STR, EXPECTED_OF_STR );

		REASON1 = "validate 5 accordion headers";
		List<HtmlElement> headers = findAccordionHeaderH3();
		Matcher<Collection<?>> EXPECTED_OF_COLLECTION = JMatchers.hasSize( 5 );
		assertion.assertThat( REASON1, headers, EXPECTED_OF_COLLECTION );
	}

	//endregion


	//region RefineSearchObject - Service Methods Section

	private HtmlElement getRoot()
	{
		return getBaseRootElement( RefineSearch.ROOT_BY );
	}

	//endregion


	//region RefineSearchObject - RefineSearch Implementation Methods Section

	@Override
	public List<DeparturePorts> getCheckedDeparturePorts()
	{
		List<HtmlElement> checkedDeparturePorts =
				findAccordionItem( DURATION_CODE ).findElements( By.cssSelector( ".filter-port li.active-filter input" ) );
		logger.info( "Retrieve a list od checked departure ports on RefineSearch. items found < {} >", checkedDeparturePorts.size() );
		List<DeparturePorts> ports = Lists.newArrayListWithCapacity( checkedDeparturePorts.size() );
		for( HtmlElement he : checkedDeparturePorts )
		{
			String portId = he.getAttribute( "value" );
			logger.info( "evaluating departure port < {} >", portId );
			DeparturePorts dp = DeparturePorts.valueById( portId );
			ports.add( dp );
		}

		logger.debug( "checked trip durations are < {} >", Lambda.joinFrom( Lambda.extractProperty( ports, "name" ), "," )  );
		return ports;
	}

	@Override
	public List<TripDurations> getCheckedTripDurations()
	{
		List<HtmlElement> checkedDurations =
				findAccordionItem( DURATION_CODE ).findElements( By.cssSelector( "#group-dir li.is-selected input" ) );
		logger.info( "Retrieve a list of checked trip durations on RefineSearch. items found < {} >", checkedDurations.size() );
		List<TripDurations> durations = Lists.newArrayListWithCapacity( checkedDurations.size() );
		for( HtmlElement he : checkedDurations )
		{
			String durId = he.getAttribute( "value" );
			logger.info( "evaluating trip duration id < {} >", durId );
			TripDurations dur = TripDurations.valueById( durId );
			durations.add( dur );
		}

		logger.debug( "checked trip durations are < {} >", Lambda.joinFrom( Lambda.extractProperty( durations, "name" ), "," )  );
		return durations;
	}

	@Override
	public List<Ships> getCheckedShips()
	{
		List<HtmlElement> checkedShips =
				findAccordionItem( SHIP_CODE ).findElements( By.cssSelector( "ul#ships li.is-selected input" ) );

		logger.info( "Retrieve a list od checked ships on RefineSearch. items found < {} >", checkedShips.size() );
		List<Ships> ships = Lists.newArrayListWithCapacity( checkedShips.size() );
		for( HtmlElement he : checkedShips )
		{
			String shipId = he.getAttribute( "value" );
			logger.info( "evaluating ship id < {} >", shipId );
			Ships ship = Ships.valueById( shipId );
			ships.add( ship );
		}

		logger.debug( "checked ships are < {} >", Lambda.joinFrom( Lambda.extractProperty( ships, "name" ), "," ) );
		return ships;
	}

	@Override
	public List<String> getCheckedEvents()
	{
		return null;
	}

	@Override
	public List<String> getCheckedRates()
	{
		return null;
	}

	@Override
	public int getItinerariesFound()
	{
		HtmlElement he = findItinerariesStrong();
		String text = he.getText();
		logger.info( "Number of itineraries found is < {} >", text );
		return PropertyConverter.toInteger( text );
	}

	//endregion


	//region RefineSearchObject - Element Finder Methods Section

	private HtmlElement findSideNavDiv()
	{
		if( null == sideNav )
		{
			final By findBy = By.className( "side-nav" );
			sideNav = getRoot().findElement( findBy );
		}

		return sideNav;
	}

	private HtmlElement findNeedsPieH2()
	{
		final By findBy = By.tagName( "h2" );
		return findSideNavDiv().findElement( findBy );
	}

	private List<HtmlElement> findAccordionHeaderH3()
	{
		final By findBy = By.cssSelector( ".side-nav h3.accordion-header" );
		return getDriver().findElements( findBy );
	}

	private HtmlElement findAccordionItem( String group )
	{
		if( group.endsWith( SHIP_CODE ) )
		{
			if( null == shipAccordion )
			{
				final By findBy = By.cssSelector( ".accordion-item[data-group=\"" + SHIP_CODE + "\"]" );
				shipAccordion = getDriver().findElement( findBy );
			}

			return shipAccordion;
		}
		else if( group.equals( DEP_CODE ) )
		{
			if( null == depAccordion )
			{
				final By findBy = By.cssSelector( ".accordion-item[data-group=\"" + DEP_CODE + "\"]" );
				depAccordion = getDriver().findElement( findBy );
			}

			return depAccordion;
		}
		else if( group.equals( DURATION_CODE ) )
		{
			if( null == durAccordion )
			{
				final By findBy = By.cssSelector( ".accordion-item[data-group=\"" + DURATION_CODE + "\"]" );
				durAccordion = getDriver().findElement( findBy );
			}

			return durAccordion;
		}
		else if( group.equals( EVENT_CODE ) )
		{
			if( null == evAccordion )
			{
				final By findBy = By.cssSelector( ".accordion-item[data-group=\"" + EVENT_CODE + "\"]" );
				evAccordion = getDriver().findElement( findBy );
			}

			return evAccordion;
		}
		else if( group.equals( SPECIAL_CODE ) )
		{
			if( null == specialAccordion )
			{
				final By findBy = By.cssSelector( ".accordion-item[data-group=\"" + SPECIAL_CODE + "\"]" );
				specialAccordion = getDriver().findElement( findBy );
			}

			return specialAccordion;
		}

		throw new ApplicationException( new IllegalArgumentException( "no item with data-group " + group ) );
	}

	private HtmlElement findItinerariesStrong()
	{
		final By findBy = By.cssSelector( "li.left.num-found strong" );
		return getDriver().findElement( findBy );
	}

	//endregion

}
