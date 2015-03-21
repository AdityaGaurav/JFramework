package com.framework.site.pages.core.cruiseships;

import com.framework.driver.event.ExpectedConditions;
import com.framework.driver.event.HtmlCondition;
import com.framework.driver.event.HtmlElement;
import com.framework.driver.extensions.jquery.By;
import com.framework.site.data.Enumerators;
import com.framework.site.data.Ships;
import com.framework.site.objects.body.common.SectionBreadcrumbsBarObject;
import com.framework.site.objects.body.ships.CycleSlideObject;
import com.framework.site.pages.BaseCarnivalPage;
import com.framework.site.pages.bookingengine.CruiseSearchPage;
import com.framework.utils.datetime.TimeConstants;
import com.framework.utils.matchers.JMatchers;
import com.google.common.base.Optional;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.BooleanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.is;


public class CruiseShipsDetailsPage extends BaseCarnivalPage implements Enumerators
{

	//region CruiseShipsDetailsPage - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( CruiseShipsDetailsPage.class );

	private static final String LOGICAL_NAME = "Cruise Ships Details Page";

	private static final String URL_PATTERN = "/cruise-ships/carnival-%s.aspx";

	private static Ships ship;

	// ------------------------------------------------------------------------|
	// --- WEB-OBJECTS DEFINITIONS --------------------------------------------|
	// ------------------------------------------------------------------------|

	private SectionBreadcrumbsBarObject breadcrumbsBar;

	// ------------------------------------------------------------------------|
	// --- WEB-OBJECTS CACHING ------------------------------------------------|
	// ------------------------------------------------------------------------|

	private HtmlElement ship_page_menu, deck_plans, overview, itinerary, accommodations, dining_activities;

	//endregion


	//region CruiseShipsDetailsPage - Constructor Methods Section

	public CruiseShipsDetailsPage()
	{
		super( LOGICAL_NAME );
		validatePageInitialState();
	}

	//endregion


	//region CruiseShipsDetailsPage - Initialization and Validation Methods Section

	protected void validatePageInitialState()
	{
		logger.debug( "validating static elements for: <{}>, name:<{}>...", getQualifier(), getLogicalName() );

		/* Validating breadcrumb last value pointing to ship name */

		String lastChild = breadcrumbsBar().breadcrumbs().getLastChildName();
		getDriver().assertThat( "Validate breadcrumb last position", lastChild, JMatchers.equalToIgnoringCase( ship.getFullName() ) );

		HtmlElement he = getDriver().findElement( By.cssSelector( ".blurb > h1" ) );
		String text = he.getText();
		getDriver().assertThat( "Validate ship title", text, JMatchers.equalToIgnoringCase( ship.getFullName() ) );

		final String REASON = "assert that element \"%s\" exits";
		Optional<HtmlElement> e = getDriver().elementExists( By.id( "ship-page-menu" ) );
		getDriver().assertThat( String.format( REASON, "#ship-page-menu" ), e.isPresent(), is( true ) );
		ship_page_menu = e.get();

		e = getDriver().elementExists( By.id( "deckplans" ) );
		getDriver().assertThat( String.format( REASON, "#deckplans" ), e.isPresent(), is( true ) );
		deck_plans = e.get();

		e = getDriver().elementExists( By.id( "overview" ) );
		getDriver().assertThat( String.format( REASON, "#overview" ), e.isPresent(), is( true ) );
		overview = e.get();

		e = getDriver().elementExists( By.id( "itinerary" ) );
		getDriver().assertThat( String.format( REASON, "#itinerary" ), e.isPresent(), is( true ) );
		itinerary = e.get();

		e = getDriver().elementExists( By.id( "accommodations" ) );
		getDriver().assertThat( String.format( REASON, "#accommodations" ), e.isPresent(), is( true ) );
		accommodations = e.get();

		e = getDriver().elementExists( By.id( "dining-activities" ) );
		getDriver().assertThat( String.format( REASON, "#dining-activities" ), e.isPresent(), is( true ) );
		dining_activities = e.get();
	}

	/**
	 * Overrides the generic extended class, since the page url is create dynamically.
	 */
	@Override
	protected void validatePageUrl()
	{
		String url = String.format( URL_PATTERN, ship.getShipName().toLowerCase() );
		getDriver().assertWaitThat(
				"Validate page url", TimeConstants.ONE_MINUTE, ExpectedConditions.urlMatches( JMatchers.endsWith( url ) ) );
	}

	//endregion


	//region CruiseShipsDetailsPage - Service Methods Section

	public static void forShip( final Ships ship )
	{
		CruiseShipsDetailsPage.ship = ship;
	}

	public SectionBreadcrumbsBarObject breadcrumbsBar()
	{
		if ( null == this.breadcrumbsBar )
		{
			this.breadcrumbsBar = new SectionBreadcrumbsBarObject( findBreadcrumbBarDiv() );
		}
		return breadcrumbsBar;
	}


	//endregion


	//region CruiseShipsDetailsPage - Business Methods Section

	public void gotoSection( ShipDetailsSection section )
	{
		logger.info( "Moving to ship details section < {} >", section.name() );
		HtmlElement li = null, anchor = null;

		switch ( section )
		{
			case ACCOMMODATIONS:
				anchor = getDriver().findElement( By.cssSelector( "a[href='#accommodations']" ) );
				li = getDriver().findElement( By.jQuerySelector( "a[href='#accommodations']" ).parent() );
				break;
			case DECK_PLANS:
				anchor = getDriver().findElement( By.cssSelector( "a[href='#deckplans']" ) );
				li = getDriver().findElement( By.jQuerySelector( "a[href='#deckplans']" ).parent() );
				break;
			case DINING_AND_ACTIVITIES:
				anchor = getDriver().findElement( By.cssSelector( "a[href='#dining-activities']" ) );
				li = getDriver().findElement( By.jQuerySelector( "a[href='#dining-activities']" ).parent() );
				break;
			case GUEST_PHOTOS:
				anchor = getDriver().findElement( By.cssSelector( "a[href='#deckplans']" ) );
				li = getDriver().findElement( By.jQuerySelector( "a[href='#deckplans']" ).parent() );
				break;
			case ITINERARY:
				anchor = getDriver().findElement( By.cssSelector( "a[href='#itinerary']" ) );
				li = getDriver().findElement( By.jQuerySelector( "a[href='#itinerary']" ).parent() );
				break;
			case OVERVIEW:
				anchor = getDriver().findElement( By.cssSelector( "a[href='#pastguestphotos']" ) );
				li = getDriver().findElement( By.jQuerySelector( "a[href='#pastguestphotos']" ).parent() );
			default:
				throw new IllegalArgumentException( "Invalid ShipDetailsSection -> " + section.name() );
		}

		anchor.click();
		li.waitAttributeToMatch( "class",JMatchers.is( "active" ), TimeConstants.THREE_SECONDS );
	}

	public CruiseSearchPage viewSailings()
	{
		logger.info( "Launching sailings for ship  < {} >", ship.name() );
		HtmlElement he = ship_page_menu.findElement( By.className( "red-cta" ) );
		he.click();
		CruiseSearchPage csp = new CruiseSearchPage();
		csp.setShip( ship );
		return csp;
	}

	public void launchDeckPlans()
	{
		logger.info( "Launching deck plans ...." );
		HtmlElement he = deck_plans.findElement( By.className( "launch-deckplans" ) );
		he.click();
		HtmlCondition<HtmlElement> condition = ExpectedConditions.visibilityBy( By.id( "deckPlansIframe" ), true );
		getDriver().assertWaitThat( "Validating iframe exists", TimeConstants.TEN_SECONDS, condition );
		getDriver().switchTo().frame( "deckPlansIframe" );
	}

	public boolean itineraryHasTitle()
	{
		boolean exists = itinerary.childExists( By.cssSelector( "div.overlay > h2" ) ).isPresent();
		logger.info( "Determine if itinerary section has title < {} >", BooleanUtils.toStringYesNo( exists ) );
		return exists;
	}

	public Map<String,CycleSlideObject> getCycleSlides()
	{
		List<HtmlElement> slides, images;
		slides = getDriver().findElements( By.cssSelector( "#itinerary .cycle-carousel-wrap > li" ) );
		if( slides.size() == 0 )
		{
			slides = getDriver().findElements( By.cssSelector( "#itinerary .sailings-carousel > li" ) );
			images = getDriver().findElements( By.cssSelector( "#itinerary .sailings-carousel > li img" ) );
		}
		else
		{
			images = getDriver().findElements( By.cssSelector( "#itinerary .cycle-carousel-wrap > li img" ) );
		}
		Map<String,CycleSlideObject> scoMap = Maps.newHashMap();
		for( int i = 0; i < images.size(); i ++ )
		{
			if( ! scoMap.containsKey( images.get( i ).getAttribute( "alt" ) ) )
			{
				CycleSlideObject cso = new CycleSlideObject( slides.get( i ), ship );
				scoMap.put( cso.getAlt(), cso );
				logger.info( "Adding image < {} > with price < {} >", cso.getAlt(), cso.getPrice() );
			}
		}
		return scoMap;
	}

	//endregion


	//region CruiseShipsDetailsPage - Element Finder Methods Section


	//endregion


	private class SailingsCarousel
	{
		private final HtmlElement prev;
		private final HtmlElement next;

		private SailingsCarousel()
		{
			HtmlElement root = itinerary.findElement( By.className( "sailings-carousel" ) );
			this.prev = root.findElement( By.className( "prev" ) );
			this.next = root.findElement( By.className( "next" ) );
		}

		void clickPrev()
		{
			prev.click();
		}

		void clickNext()
		{
			next.click();
		}
	}

}
