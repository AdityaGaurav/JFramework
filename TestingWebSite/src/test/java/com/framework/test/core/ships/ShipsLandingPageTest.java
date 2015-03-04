package com.framework.test.core.ships;

import com.framework.BaseTest;
import com.framework.asserts.CheckpointAssert;
import com.framework.driver.event.ExpectedConditions;
import com.framework.driver.event.HtmlCondition;
import com.framework.driver.event.HtmlElement;
import com.framework.driver.exceptions.ApplicationException;
import com.framework.driver.objects.PageObject;
import com.framework.site.config.SiteProperty;
import com.framework.site.config.SiteSessionManager;
import com.framework.site.data.DeparturePorts;
import com.framework.site.data.Destinations;
import com.framework.site.data.Ships;
import com.framework.site.data.TripDurations;
import com.framework.site.objects.body.interfaces.BreadcrumbsBar;
import com.framework.site.objects.body.interfaces.FilterCategories;
import com.framework.site.objects.body.interfaces.ShipCard;
import com.framework.site.objects.body.interfaces.ShipSortBar;
import com.framework.site.objects.header.enums.LevelOneMenuItem;
import com.framework.site.objects.header.enums.MenuItems;
import com.framework.site.pages.BaseCarnivalPage;
import com.framework.site.pages.bookingengine.CruiseSearchPage;
import com.framework.site.pages.core.BaseCruiseShipsPage;
import com.framework.site.pages.core.CruiseShipsPage;
import com.framework.site.pages.core.ExplorePage;
import com.framework.site.pages.core.HomePage;
import com.framework.site.pages.core.cruisefrom.CruiseFromPortPage;
import com.framework.site.pages.core.cruiseto.CruiseToDestinationPage;
import com.framework.site.pages.core.cruiseto.CruiseToPage;
import com.framework.site.pages.core.findacruise.SearchResultsPage;
import com.framework.testing.annotations.*;
import com.framework.utils.datetime.TimeConstants;
import com.framework.utils.error.PreConditions;
import com.framework.utils.matchers.JMatchers;
import com.framework.utils.web.CSS2Properties;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import org.apache.commons.configuration.PropertyConverter;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.hamcrest.Matcher;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.Color;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.annotations.*;
import org.testng.xml.XmlTest;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.test.core.ships
 *
 * Name   : ShipCompareTest 
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-02-26 
 *
 * Time   : 09:08
 *
 */

@Test
public class ShipsLandingPageTest extends BaseTest
{

	//region ShipsLandingPageTest - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( ShipsLandingPageTest.class );

	private HomePage homePage = null;

	private BaseCruiseShipsPage cruiseShipsPage = null;

	//endregion


	//region ShipsLandingPageTest - Constructor Methods Section

	private ShipsLandingPageTest()
	{
		BaseTest.classLogger = LoggerFactory.getLogger( ShipsLandingPageTest.class );
	}

	//endregion


	//region ShipsLandingPageTest - Before Configurations Methods Section

	@BeforeClass ( description = "starts a web driver",
			alwaysRun = true,
			groups = { "US", "UK", "AU" }
	)
	public void beforeClass( ITestContext testContext, XmlTest xmlTest ) throws Exception
	{

		killExistingBrowsersOpenedByWebDriver();
		SiteSessionManager.get().startSession();
	}

	@BeforeGroups ( description = "", enabled = true, groups = { "AU" } )
	public void beforeGroup( ITestContext testContext ) throws Exception
	{
		testContext.setAttribute( "currentGroup", "US" );
	}

	@BeforeMethod ( description = "Initiates Home Page",
			enabled = true,
			alwaysRun = true,
			groups = { "US", "UK", "AU" }
	)
	public void beforeMethod( ITestContext testContext, XmlTest xmlTest, Method method, Object[] parameters ) throws Exception
	{

		try
		{
			if ( ! SiteSessionManager.get().isSessionRunning() )
			{
				this.homePage = SiteSessionManager.get().getHomePage();
			}

			if ( SiteSessionManager.get().isHomePage() )
			{
				this.homePage = new HomePage();
				this.homePage.header().headerLinks().hoverOnItem( LevelOneMenuItem.EXPLORE );
				PageObject p = this.homePage.header().navigationAdditional().clickOnMenuItem( LevelOneMenuItem.EXPLORE, MenuItems.OUR_SHIPS );
				if ( p instanceof CruiseShipsPage ) this.cruiseShipsPage = ( CruiseShipsPage ) p;
				if ( p instanceof BaseCruiseShipsPage ) this.cruiseShipsPage = ( BaseCruiseShipsPage ) p;
			}
			else if ( CruiseShipsPage.isCruiseShipPage() )
			{
				List<String> groups = Lists.newArrayList( testContext.getIncludedGroups() );
				if ( groups.contains( "US" ) || groups.contains( "UK" ) )
				{
					this.cruiseShipsPage = new CruiseShipsPage();
				}
				else
				{
					this.cruiseShipsPage = new BaseCruiseShipsPage();
				}
			}

		}
		catch ( Throwable e )
		{
			if ( e instanceof WebDriverException )
			{
				throw new ApplicationException( SiteSessionManager.get().getDriver(), e );
			}
			else if ( e instanceof AssertionError )
			{
				throw new ApplicationException( SiteSessionManager.get().getDriver(), e );
			}
			throw new Exception( e.getMessage(), e );
		}
	}


	//endregion


	//region ShipCompareTest - Test Methods Section

	@UserStory ( "PBI-52423" )
	@TestCaseId ( id = 58909 )
	@Steps ( steps = {
			@Step ( number = 1, description = "GIVEN that the user is in Ships Landing page /cruise-ships.aspx" ),
			@Step ( number = 2, description = "WHEN user clicks \"Home\" in the displayed breadcrumb",
					expectedResults = "THEN user redirects to the relevant locale carnival home page" )
	} )
	@Test ( description = "Ships Landing page. Breadcrumbs functionality. Home",
			dependsOnMethods = { "shipsLandingPage_Breadcrumb_Display" },
			enabled = true,
			groups = { "AU", "US", "UK"  }
	)
	public void shipsLandingPage_Breadcrumb_Home() throws Exception
	{

		/* GIVEN that the user is in Ships Landing page /cruise-ships.aspx */
		PreConditions.checkNotNull( this.cruiseShipsPage, "Cruise Ship page is null, before starting test" );
		try
		{
			/* WHEN user clicks "Home" in the displayed breadcrumb */

			homePage = cruiseShipsPage.breadcrumbsBar().breadcrumbs().navigateHome();

			/* THEN user redirects to the relevant locale carnival home page */

			final String EXPECTED_ENV_URL = SiteSessionManager.get().getBaseUrl().toString();
			HtmlCondition<Boolean> condition = ExpectedConditions.urlMatches( JMatchers.equalToIgnoringCase( EXPECTED_ENV_URL ) );
			CheckpointAssert checkpoint = SiteSessionManager.get().createCheckPoint( "HOME_PAGE_URL" );
			checkpoint.assertWaitThat( "Asserting Home Page Url", TimeConstants.HALF_MINUTE, condition );

			SiteSessionManager.get().assertAllCheckpoints();
		}
		catch ( WebDriverException | AssertionError e )
		{
			throw new ApplicationException( SiteSessionManager.get().getDriver(), e );
		}
	}

	@UserStory ( "PBI-52423" )
	@TestCaseId ( id = 58910 )
	@Steps ( steps = {
			@Step ( number = 1, description = "GIVEN that the user is in Ships Landing page /cruise-ships.aspx" ),
			@Step ( number = 2, description = "WHEN the clicks \"Explore\" in the displayed breadcrumbs",
					expectedResults = {
							"THEN user redirected to \"Explore\" landing page"
					}
			)
	} )
	@Test ( description = "Ships Landing page. Breadcrumbs functionality. Explore",
			dependsOnMethods = { "shipsLandingPage_Breadcrumb_Display" },
			enabled = true,
			groups = { "AU", "US", "UK"  }
	)
	public void shipsLandingPage_Breadcrumb_Explore() throws Exception
	{

		/* GIVEN that the user is in Ships Landing page /cruise-ships.aspx */
		PreConditions.checkNotNull( this.cruiseShipsPage, "Cruise Ships page is null, before starting test" );
		try
		{

			/* WHEN user clicks "Explore" in the displayed breadcrumb */

			cruiseShipsPage.breadcrumbsBar().breadcrumbs().clickOnItem( "Explore" );
			ExplorePage explorePage = new ExplorePage();

			/* THEN user redirects to the "Explore" landing page */

			final String EXPECTED_URL = "/explore-carnival-cruises";
			HtmlCondition<Boolean> condition = ExpectedConditions.urlMatches( JMatchers.endsWith( EXPECTED_URL ) );
			CheckpointAssert checkpoint = SiteSessionManager.get().createCheckPoint( "EXPLORE_PAGE_URL" );
			checkpoint.assertWaitThat( "Asserting Explore Page Url", TimeConstants.HALF_MINUTE, condition );

			SiteSessionManager.get().assertAllCheckpoints();
		}
		catch ( WebDriverException | AssertionError e )
		{
			throw new ApplicationException( SiteSessionManager.get().getDriver(), e );
		}
	}

	@UserStory ( "PBI-52423" )
	@TestCaseId ( id = 58911 )
	@Steps ( steps = {
			@Step ( number = 1, description = "GIVEN that the user is in Ships Landing page /cruise-ships.aspx" ),
			@Step ( number = 2, description = "WHEN the user examine the last item on the breadcrumb",
					expectedResults = {
							"THEN the last item text is \"Ships\"",
							"AND the last item state should be active."
					} )
	} )
	@Test ( description = "Ships Landing page. Breadcrumbs functionality. Ships",
			dependsOnMethods = { "shipsLandingPage_Breadcrumb_Display" },
			enabled = true,
			groups = { "AU", "US", "UK"  }
	)
	public void shipsLandingPage_Breadcrumb_Ships() throws Exception
	{

		/* GIVEN that the user is in Ships Landing page /cruise-ships.aspx */
		PreConditions.checkNotNull( this.cruiseShipsPage, "Cruise Ship page is null, before starting test" );
		try
		{

			/* WHEN the user examine the last item on the breadcrumb */
			BreadcrumbsBar.Breadcrumbs breadcrumbs = cruiseShipsPage.breadcrumbsBar().breadcrumbs();

			/* THEN the last item text is "Ships" */

			String ACTUAL_STR = breadcrumbs.getLastChildName();
			String REASON = "Validates that last child breadcrumb text is 'Ships'";
			Matcher<String> EXPECTED_OF_STR = JMatchers.equalTo( "Ships" );
			SiteSessionManager.get().createCheckPoint( "BREADCRUMB_LAST_CHILD_TEXT" )
					.assertThat( REASON, ACTUAL_STR, EXPECTED_OF_STR );

			/* AND the last item state should be active */

			ACTUAL_STR = breadcrumbs.getLastChild().getAttribute( "class" );
			REASON = "Validates that last child is active";
			EXPECTED_OF_STR = JMatchers.startsWith( "active" );
			SiteSessionManager.get().createCheckPoint( "BREADCRUMB_LAST_CHILD_ACTIVE" )
					.assertThat( REASON, ACTUAL_STR, EXPECTED_OF_STR );

			SiteSessionManager.get().assertAllCheckpoints();
		}
		catch ( WebDriverException | AssertionError e )
		{
			throw new ApplicationException( SiteSessionManager.get().getDriver(), e );
		}
	}

	@UserStory ( "PBI-52423" )
	@TestCaseId ( id = 58912 )
	@Steps ( steps = {
			@Step ( number = 1, description = "GIVEN that the user is in Ships Landing page /cruise-ships.aspx" ),
			@Step ( number = 2, description = "WHEN the user examine the displayed breadcrumbs",
					expectedResults = {
							"THEN the user verifies that breadcrumbs are Home > Explore > Ships"
					} )
	} )
	@Test ( description = "Ships Landing page. Breadcrumbs functionality. Display",
			enabled = true,
			groups = { "AU", "US", "UK" } )
	public void shipsLandingPage_Breadcrumb_Display() throws Exception
	{

		/* GIVEN that the user is in Ships Landing page /cruise-ships.aspx */
		PreConditions.checkNotNull( this.cruiseShipsPage, "Cruise Ship page is null, before starting test" );
		try
		{
			/* WHEN the user examine the displayed breadcrumbs */

			BreadcrumbsBar.Breadcrumbs breadcrumbs = cruiseShipsPage.breadcrumbsBar().breadcrumbs();

			String REASON = "Validates breadcrumbs text display order.";
			List<String> ACTUAL_OF_LIST_STR = breadcrumbs.getNames();
			Matcher<Iterable<? extends String>> EXPECTED_OF_LIST_STR = JMatchers.contains( "Home", "Explore", "Ships" );
			SiteSessionManager.get().createCheckPoint( "BREADCRUMB_ORDERING" )
					.assertThat( REASON, ACTUAL_OF_LIST_STR, EXPECTED_OF_LIST_STR );

			SiteSessionManager.get().assertAllCheckpoints();
		}
		catch ( WebDriverException | AssertionError e )
		{
			throw new ApplicationException( SiteSessionManager.get().getDriver(), e );
		}
	}

	@UserStory ( "PBI-52423" )
	@TestCaseId ( id = 58961 )
	@Issues ( issues = { @Issue ( "63169" ), @Issue ( "72095" ) } )
	@Steps ( steps = {
			@Step ( number = 1, description = "GIVEN that the user is in Ships Landing page /cruise-ships.aspx" ),
			@Step ( number = 2, description = "WHEN clicks on \"Share\" button",
					expectedResults = {
							"THEN the \"Share\" menu is displayed with social media icons."
					} )
	} )
	@Test ( description = "Ships Landing page. Share",
			enabled = true,
			groups = { "IN-DEV" } )
	public void shipsLandingPage_Share( ITestContext context ) throws Exception
	{

		/* GIVEN that the user is in Ships Landing page /cruise-ships.aspx */
		PreConditions.checkNotNull( this.cruiseShipsPage, "Cruise Ship page is null, before starting test" );
		try
		{
			/* "WHEN clicks on "Share" button */

			SiteSessionManager.get().assertAllCheckpoints();
		}
		catch ( WebDriverException | AssertionError e )
		{
			throw new ApplicationException( SiteSessionManager.get().getDriver(), e );
		}
	}

	@UserStory ( "PBI-52423" )
	@TestCasesIds ( ids = { 59442, 66901 } )
	@Steps ( steps = {
			@Step ( number = 1, description = "GIVEN that the user is in Ships Landing page /cruise-ships.aspx" ),
			@Step ( number = 2, description = "WHEN user examines the left filter",
					expectedResults = { "THEN the filter default state is expanded" } ),
			@Step ( number = 3, description = "AND WHEN user clicks on collapse button",
					expectedResults = {
							"THEN the all filter-category items should be hidden",
							"AND the mode-toggle should be displayed",
							"AND the text on the mode-toggle should be \"Filter Bar\""
					} ),
			@Step ( number = 4, description = "AND WHEN user restores the filter bar to expanded position",
					expectedResults = {
							"THEN the mode-toggle should be hidden",
							"AND all filter-category items are visible."
					} )
	} )
	@Test ( description = "Ships Landing page. Display Ships. Filter by single departure port",
			enabled = true,
			groups = { "US", "UK" } )
	public void shipsLandingPage_FilterExpand() throws Exception
	{

		/* GIVEN that the user is in Ships Landing page /cruise-ships.aspx */
		PreConditions.checkNotNull( this.cruiseShipsPage, "Cruise Ship page is null, before starting test" );
		try
		{
			CruiseShipsPage page = ( CruiseShipsPage ) cruiseShipsPage;

			/* WHEN user examines the left filter */
			FilterCategories filterCategories = page.filterCategories();

			/* THEN the filter default state is expanded */
			String REASON = "Validates that categories filter default state is \"expanded\"";
			Boolean ACTUAL_BOOL = filterCategories.isExpanded();
			Matcher<Boolean> EXPECTED_OF_BOOL = JMatchers.is( true );
			SiteSessionManager.get().createCheckPoint( "FILTER_DEFAULT_EXPANDED" )
					.assertThat( REASON, ACTUAL_BOOL, EXPECTED_OF_BOOL );

			/* AND WHEN user clicks on collapse button */
			page.filterCategories().collapse();

			/* THEN the all filter-category items should be hidden*/

			List<HtmlElement> categories = filterCategories.getCategories();
			List<Boolean> states = Lists.newArrayListWithCapacity( categories.size() );
			for ( HtmlElement he : categories )
			{
				states.add( he.isDisplayed() );
			}
			REASON = "Validates that all filter-category are hidden";
			Matcher<Iterable<? super Boolean>> EXPECTED_OF_ITERABLE_BOOL = JMatchers.not( JMatchers.hasItem( true ) );
			SiteSessionManager.get().createCheckPoint( "ALL_FILTER_CATEGORY_HIDDEN" )
					.assertThat( REASON, states, EXPECTED_OF_ITERABLE_BOOL );

			/* AND the mode-toggle should be displayed */

			HtmlElement modeToggle = filterCategories.getModeToggle();
			REASON = "Validates that mode-toggle is visible";
			ACTUAL_BOOL = modeToggle.isDisplayed();
			EXPECTED_OF_BOOL = JMatchers.is( true );
			SiteSessionManager.get().createCheckPoint( "MODE_TOGGLE_DISPLAYED" )
					.assertThat( REASON, ACTUAL_BOOL, EXPECTED_OF_BOOL );

			/* AND the text on the mode-toggle should be displayed with text "Filter Bar" */

			HtmlElement span = modeToggle.findElement( By.tagName( "span" ) );
			REASON = "Validates that mode-toggle span is visible";
			ACTUAL_BOOL = span.isDisplayed();
			EXPECTED_OF_BOOL = JMatchers.is( true );
			SiteSessionManager.get().createCheckPoint( "MODE_TOGGLE_SPAN_DISPLAYED" )
					.assertThat( REASON, ACTUAL_BOOL, EXPECTED_OF_BOOL );

			/* AND WHEN user restores the filter bar to expanded position */
			filterCategories.expand();

			/* THEN the mode-toggle should be hidden */
			REASON = "Validates that mode-toggle span is not visible";
			ACTUAL_BOOL = span.isDisplayed();
			EXPECTED_OF_BOOL = JMatchers.is( false );
			SiteSessionManager.get().createCheckPoint( "MODE_TOGGLE_SPAN_HIDDEN" )
					.assertThat( REASON, ACTUAL_BOOL, EXPECTED_OF_BOOL );

			/* AND all filter-category items are visible */

			states.clear();
			for ( HtmlElement he : categories )
			{
				states.add( he.isDisplayed() );
			}
			REASON = "Validates that all filter-category are displayed";
			EXPECTED_OF_ITERABLE_BOOL = JMatchers.not( JMatchers.hasItem( false ) );
			SiteSessionManager.get().createCheckPoint( "ALL_FILTER_CATEGORY_DISPLAYED" )
					.assertThat( REASON, states, EXPECTED_OF_ITERABLE_BOOL );

			SiteSessionManager.get().assertAllCheckpoints();
		}
		catch ( WebDriverException | AssertionError e )
		{
			throw new ApplicationException( SiteSessionManager.get().getDriver(), e );
		}
	}

	@UserStory ( "PBI-52423" )
	@TestCasesIds ( ids = { 59442, 66901 } )
	@Steps ( steps = {
			@Step ( number = 1, description = "GIVEN that the user is in Ships Landing page /cruise-ships.aspx" ),
			@Step ( number = 2, description = "WHEN user select a single departure port filter",
					expectedResults = {
							"THEN the departure port filter option should be checked",
							"AND current-filters section should be displayed",
							"AND filter option \" X Departure Port:<Selection>\" should be added",
							"AND \"Clear all filters\" option should be displayed",
							"AND ships Counter matches actual cards count",
							"AND ships matches actual cards"
					} ),
			@Step ( number = 3, description = "AND WHEN user clicks on clear all filters",
					expectedResults = {
							"THEN current-filters section should be not displayed",
							"AND previous selected departure port filter option should be unchecked",
							"AND ships Counter matches actual cards count"
					} )
	} )
	@Test ( description = "Ships Landing page. Display Ships. Filter by single destination",
			enabled = true,
			groups = { "US", "UK" } )
	public void shipsLandingPage_Port_SingleFilter() throws Exception
	{

		 /* GIVEN that the user is in Ships Landing page /cruise-ships.aspx */
		PreConditions.checkNotNull( this.cruiseShipsPage, "Cruise Ship page is null, before starting test" );
		try
		{
			CruiseShipsPage page = ( CruiseShipsPage ) cruiseShipsPage;

			/* WHEN user select a single departure port filter */
			FilterCategories filterCategories = page.filterCategories();
			List<DeparturePorts> ports = filterCategories.getAvailableDeparturePorts();
			int rnd = RandomUtils.nextInt( 0, ports.size() - 1 );
			DeparturePorts selected = ports.get( rnd );
			Set<Ships> EXPECTED_SHIPS = page.getShipsMatching( selected );

			// filtering
			filterCategories.filterByDeparturePort( selected );

			/* THEN the departure port filter option should be checked */
			String REASON = "Validate that selected filter item is checked";
			String ACTUAL_STR = filterCategories.getFilterItem( selected ).getAttribute( "class" );
			Matcher<String> EXPECTED_OF_STR = JMatchers.equalToIgnoringCase( "active-filter" );
			SiteSessionManager.get().createCheckPoint( "FILTER_PORT_CHECKED" )
					.assertThat( REASON, ACTUAL_STR, EXPECTED_OF_STR );

			/* AND current-filters section should be displayed */
			REASON = "Validate current-filters section is displayed";
			Boolean ACTUAL_BOOL = filterCategories.getCurrentFiltersSection().isDisplayed();
			Matcher<Boolean> EXPECTED_OF_BOOL = JMatchers.is( true );
			SiteSessionManager.get().createCheckPoint( "CURRENT_FILTER_DISPLAYED" )
					.assertThat( REASON, ACTUAL_BOOL, EXPECTED_OF_BOOL );

			/* AND filter option " X Departure Port:<Selection>" should be added */
			REASON = "Validate filter option \" X Departure Port:" + selected.getId() + "\" should be added ";
			ACTUAL_BOOL = filterCategories.filterElementExists( selected.getId() ).isPresent();
			EXPECTED_OF_BOOL = JMatchers.is( true );
			SiteSessionManager.get().createCheckPoint( "FILTER_" + selected.name() + "_DISPLAYED" )
					.assertThat( REASON, ACTUAL_BOOL, EXPECTED_OF_BOOL );

			/* AND "Clear all filters" option should be displayed */
			REASON = "Validate that \"Clear all filters\" item is displayed ";
			ACTUAL_BOOL = filterCategories.getClearAllFilters().isDisplayed();
			EXPECTED_OF_BOOL = JMatchers.is( true );
			SiteSessionManager.get().createCheckPoint( "CLEAR_ALL_FILTERS_DISPLAYED" )
					.assertThat( REASON, ACTUAL_BOOL, EXPECTED_OF_BOOL );

			/* AND ships Counter matches actual cards count */
			REASON = "Validate that \"Ships counter\"  matches actual cards count";
			int ACTUAL_INT = cruiseShipsPage.sortBar().getResults();
			Matcher<Integer> EXPECTED_OF_INT = JMatchers.is( EXPECTED_SHIPS.size() );
			SiteSessionManager.get().createCheckPoint( "RESULT_COUNTER_" + EXPECTED_SHIPS.size() )
					.assertThat( REASON, ACTUAL_INT, EXPECTED_OF_INT );

			/* AND ships matches actual cards */
			REASON = "Validate that \"Ships\" matches actual filtered cards";
			List<Ships> expected = Lists.newArrayList( EXPECTED_SHIPS );
			Matcher<Iterable<? extends Ships>> EXPECTED_OF_SHIPS = JMatchers.containsInAnyOrder( expected.toArray( new Ships[ expected.size() ] ) );
			SiteSessionManager.get().createCheckPoint( "SHIPS_MATCHES" )
					.assertThat( REASON, cruiseShipsPage.getShips(), EXPECTED_OF_SHIPS );

			/* AND WHEN user clicks on clear all filters */
			filterCategories.clearFilters();

			/* THEN current-filters section should be not displayed */
			REASON = "Validate current-filters section is not displayed";
			ACTUAL_BOOL = filterCategories.getCurrentFiltersSection().isDisplayed();
			EXPECTED_OF_BOOL = JMatchers.is( false );
			SiteSessionManager.get().createCheckPoint( "CURRENT_FILTER_NOT_DISPLAYED" )
					.assertThat( REASON, ACTUAL_BOOL, EXPECTED_OF_BOOL );

			/* AND previous selected departure port filter option should be unchecked */
			REASON = "Validate that selected filter item is now unchecked";
			ACTUAL_STR = filterCategories.getFilterItem( selected ).getAttribute( "class" );
			EXPECTED_OF_STR = JMatchers.isEmptyString();
			SiteSessionManager.get().createCheckPoint( "FILTER_PORT_UNCHECKED" )
					.assertThat( REASON, ACTUAL_STR, EXPECTED_OF_STR );

			/* AND ships Counter matches actual cards count */
			REASON = "Validate that \"Ships counter\"  matches actual cards count after clear filters";
			ACTUAL_INT = cruiseShipsPage.sortBar().getResults();
			EXPECTED_OF_INT = JMatchers.is( cruiseShipsPage.getShipCardsCount() );
			SiteSessionManager.get().createCheckPoint( "RESULT_COUNTER_AFTER_CLEAR" )
					.assertThat( REASON, ACTUAL_INT, EXPECTED_OF_INT );

			SiteSessionManager.get().assertAllCheckpoints();
		}
		catch ( WebDriverException | AssertionError e )
		{
			throw new ApplicationException( SiteSessionManager.get().getDriver(), e );
		}
	}

	@UserStory ( "Self" )
	@Steps ( steps = {
			@Step ( number = 1, description = "GIVEN that the user is in Ships Landing page /cruise-ships.aspx" ),
			@Step ( number = 2, description = "WHEN user select a single destination filter",
					expectedResults = {
							"THEN the destination filter option should be checked",
							"AND current-filters section should be displayed",
							"AND filter option \" X Destination:<Selection>\" should be added",
							"AND \"Clear all filters\" option should be displayed",
							"AND ships Counter matches actual cards count",
							"AND ships matches actual cards"
					} ),
			@Step ( number = 3, description = "AND WHEN user clicks on clear all filters",
					expectedResults = {
							"THEN current-filters section should be not displayed",
							"AND previous selected destination filter option should be unchecked",
							"AND ships Counter matches actual cards count"
					} )
	} )
	@Test ( description = "Ships Landing page. Display Ships. Results Count",
			enabled = true,
			groups = { "US", "UK" } )
	public void shipsLandingPage_Destination_SingleFilter() throws Exception
	{

		 /* GIVEN that the user is in Ships Landing page /cruise-ships.aspx */
		PreConditions.checkNotNull( this.cruiseShipsPage, "Cruise Ship page is null, before starting test" );
		try
		{
			CruiseShipsPage page = ( CruiseShipsPage ) cruiseShipsPage;

			/* WHEN user select a single destination filter */
			FilterCategories filterCategories = page.filterCategories();
			List<Destinations> destinations = filterCategories.getAvailableDestinations();
			int rnd = RandomUtils.nextInt( 0, destinations.size() - 1 );
			Destinations selected = destinations.get( rnd );
			Set<Ships> EXPECTED_SHIPS = page.getShipsMatching( selected );

			// filtering
			filterCategories.filterByDestination( selected );

			/* THEN the destination filter option should be checked */
			String REASON = "Validate that selected filter item is checked";
			String ACTUAL_STR = filterCategories.getFilterItem( selected ).getAttribute( "class" );
			Matcher<String> EXPECTED_OF_STR = JMatchers.equalToIgnoringCase( "active-filter" );
			SiteSessionManager.get().createCheckPoint( "FILTER_DESTINATION_CHECKED" )
					.assertThat( REASON, ACTUAL_STR, EXPECTED_OF_STR );

			/* AND current-filters section should be displayed */
			REASON = "Validate current-filters section is displayed";
			Boolean ACTUAL_BOOL = filterCategories.getCurrentFiltersSection().isDisplayed();
			Matcher<Boolean> EXPECTED_OF_BOOL = JMatchers.is( true );
			SiteSessionManager.get().createCheckPoint( "CURRENT_FILTER_DISPLAYED" )
					.assertThat( REASON, ACTUAL_BOOL, EXPECTED_OF_BOOL );

			/* AND filter option " X Destination: <Selection>" should be added */
			REASON = "Validate filter option \" X Destination:" + selected.getId() + "\" should be added ";
			ACTUAL_BOOL = filterCategories.filterElementExists( selected.getId() ).isPresent();
			EXPECTED_OF_BOOL = JMatchers.is( true );
			SiteSessionManager.get().createCheckPoint( "FILTER_" + selected.name() + "_DISPLAYED" )
					.assertThat( REASON, ACTUAL_BOOL, EXPECTED_OF_BOOL );

			/* AND "Clear all filters" option should be displayed */
			REASON = "Validate that \"Clear all filters\" item is displayed ";
			ACTUAL_BOOL = filterCategories.getClearAllFilters().isDisplayed();
			EXPECTED_OF_BOOL = JMatchers.is( true );
			SiteSessionManager.get().createCheckPoint( "CLEAR_ALL_FILTERS_DISPLAYED" )
					.assertThat( REASON, ACTUAL_BOOL, EXPECTED_OF_BOOL );

			/* AND ships Counter matches actual cards count */
			REASON = "Validate that \"Ships counter\"  matches actual cards count";
			int ACTUAL_INT = cruiseShipsPage.sortBar().getResults();
			Matcher<Integer> EXPECTED_OF_INT = JMatchers.is( EXPECTED_SHIPS.size() );
			SiteSessionManager.get().createCheckPoint( "RESULT_COUNTER_" + EXPECTED_SHIPS.size() )
					.assertThat( REASON, ACTUAL_INT, EXPECTED_OF_INT );

			/* AND ships matches actual cards */
			REASON = "Validate that \"Ships\" matches actual filtered cards";
			List<Ships> expected = Lists.newArrayList( EXPECTED_SHIPS );
			Matcher<Iterable<? extends Ships>> EXPECTED_OF_SHIPS = JMatchers.containsInAnyOrder( expected.toArray( new Ships[ expected.size() ] ) );
			SiteSessionManager.get().createCheckPoint( "SHIPS_MATCHES" )
					.assertThat( REASON, cruiseShipsPage.getShips(), EXPECTED_OF_SHIPS );

			/* AND WHEN user clicks on clear all filters */
			filterCategories.clearFilters();

			/* THEN current-filters section should be not displayed */
			REASON = "Validate current-filters section is not displayed";
			ACTUAL_BOOL = filterCategories.getCurrentFiltersSection().isDisplayed();
			EXPECTED_OF_BOOL = JMatchers.is( false );
			SiteSessionManager.get().createCheckPoint( "CURRENT_FILTER_NOT_DISPLAYED" )
					.assertThat( REASON, ACTUAL_BOOL, EXPECTED_OF_BOOL );

			/* AND previous selected departure port filter option should be unchecked */
			REASON = "Validate that selected filter item is now unchecked";
			ACTUAL_STR = filterCategories.getFilterItem( selected ).getAttribute( "class" );
			EXPECTED_OF_STR = JMatchers.isEmptyString();
			SiteSessionManager.get().createCheckPoint( "FILTER_PORT_UNCHECKED" )
					.assertThat( REASON, ACTUAL_STR, EXPECTED_OF_STR );

			/* AND ships Counter matches actual cards count */
			REASON = "Validate that \"Ships counter\"  matches actual cards count after clear filters";
			ACTUAL_INT = cruiseShipsPage.sortBar().getResults();
			EXPECTED_OF_INT = JMatchers.is( cruiseShipsPage.getShipCardsCount() );
			SiteSessionManager.get().createCheckPoint( "RESULT_COUNTER_AFTER_CLEAR" )
					.assertThat( REASON, ACTUAL_INT, EXPECTED_OF_INT );

			SiteSessionManager.get().assertAllCheckpoints();
		}
		catch ( WebDriverException | AssertionError e )
		{
			throw new ApplicationException( SiteSessionManager.get().getDriver(), e );
		}
	}

	@UserStory ( "PBI-52423" )
	@TestCaseId ( id = 59443 )
	@Steps ( steps = {
			@Step ( number = 1, description = "GIVEN that the user is in Ships Landing page /cruise-ships.aspx" ),
			@Step ( number = 2, description = "WHEN list is selected in \"GRID\" menu",
					expectedResults = {
							"THEN the display should not be list-layout"
					} ),
			@Step ( number = 3, description = "AND WHEN list is selected in \"LIST\" menu",
					expectedResults = {
							"THEN the display should be list-layout"
					} )
	} )
	@Test ( description = "Ships Landing page. Display Ships. View List Menu",
			enabled = true,
			groups = { "AU", "US", "UK"  } )
	public void shipsLandingPage_DisplayShips_Layout() throws Exception
	{

		/* GIVEN that the user is in Ships Landing page /cruise-ships.aspx */
		PreConditions.checkNotNull( this.cruiseShipsPage, "Cruise Ship page is null, before starting test" );
		try
		{
			/* WHEN list is selected in "GRID" menu */
			cruiseShipsPage.sortBar().setLayoutType( ShipSortBar.LayoutType.BY_GRID );

			/* THEN the display should not be list-layout */
			String REASON = "Validate Layout is Grid";
			ShipSortBar.LayoutType ACTUAL_RESULT_LAYOUT = cruiseShipsPage.sortBar().getLayoutType();
			Matcher<ShipSortBar.LayoutType> EXPECTED_LAYOUT = JMatchers.is( ShipSortBar.LayoutType.BY_GRID );
			SiteSessionManager.get().createCheckPoint( "GRID_LAYOUT" )
					.assertThat( REASON, ACTUAL_RESULT_LAYOUT, EXPECTED_LAYOUT );

			/* AND WHEN list is selected in "LIST" menu */
			cruiseShipsPage.sortBar().setLayoutType( ShipSortBar.LayoutType.BY_LIST );
			REASON = "Validate Layout is List";
			ACTUAL_RESULT_LAYOUT = cruiseShipsPage.sortBar().getLayoutType();
			EXPECTED_LAYOUT = JMatchers.is( ShipSortBar.LayoutType.BY_LIST );
			SiteSessionManager.get().createCheckPoint( "LIST_LAYOUT" )
					.assertThat( REASON, ACTUAL_RESULT_LAYOUT, EXPECTED_LAYOUT );

			SiteSessionManager.get().assertAllCheckpoints();
		}
		catch ( WebDriverException | AssertionError e )
		{
			throw new ApplicationException( SiteSessionManager.get().getDriver(), e );
		}
	}

	@UserStory ( "PBI-52423" )
	@TestCasesIds ( ids = { 59446, 59444, 59449, 59630, 66784 } )
	@Steps ( steps = {
			@Step ( number = 1, description = "GIVEN that the user is in Ships Landing page /cruise-ships.aspx" ),
			@Step ( number = 2, description = "WHEN user hovers over the \"SORT\" options",
					expectedResults = { "THEN the title change color from #0a407d to #009BD0 " } ),
			@Step ( number = 3, description = "AND the user clicks on the list and expand it",
					expectedResults = { "THEN selected option value above should be \"CHECKED\" on the drop-down list" } ),
			@Step ( number = 4, description = "AND the user hovers over the unselected option.",
					expectedResults = { "THEN the background color changes to #DBECFF" } ),
			@Step ( number = 5, description = "WHEN list is selected in \"A-Z\" sort menu",
					expectedResults = {
							"THEN user verifies sort order of displayed results are alphabetical order by name."
					} ),
			@Step ( number = 6, description = "WHEN user hovers over the \"DISPLAY\" options",
					expectedResults = { "THEN the title change color from #0a407d to #009BD0 " } ),
			@Step ( number = 7, description = "AND the user clicks on the list and expand it",
					expectedResults = { "THEN selected option value above should be \"CHECKED\" on the drop-down list" } ),
			@Step ( number = 8, description = "AND the user hovers over the unselected option.",
					expectedResults = { "THEN the background color changes to #DBECFF" } ),
			@Step ( number = 9, description = "AND WHEN list is selected in \"FEATURED\" menu",
					expectedResults = {
							"THEN user verifies FEATURED order displayed should match feature order as in site-core"
					} )
	} )
	@Test ( description = "Ships Landing page. Display Ships. Sort Menu",
			enabled = true,
			groups = { "AU", "US", "UK"  } )
	public void shipsLandingPage_DisplayShips_Sort() throws Exception
	{

		/* GIVEN that the user is in Ships Landing page /cruise-ships.aspx */
		PreConditions.checkNotNull( this.cruiseShipsPage, "Cruise Ship page is null, before starting test" );
		try
		{
			/* WHEN user hovers over the "SORT" options */
			HtmlElement he = cruiseShipsPage.sortBar().hoverSortType();

			/* the title change color from #0a407d to #009BD0 */
			String REASON = "Validates that toggle color changed to #009BD0";
			Color color = Color.fromString( "#009BD0" );
			HtmlCondition<Boolean> condition =
					ExpectedConditions.elementCssPropertyToMatch( he, CSS2Properties.COLOR.getStringValue(), JMatchers.is( color.asRgba() ) );
			SiteSessionManager.get().createCheckPoint( "SORT_FONT_COLOR_ON_HOVER" )
					.assertWaitThat( REASON, TimeConstants.FIVE_SECONDS, condition );

			/* AND the user clicks on the list and expand it */
			ShipSortBar.SortType sortType = cruiseShipsPage.sortBar().getSortType();
			HtmlElement sortTypeOption = cruiseShipsPage.sortBar().getSortTypeOption( sortType );

			/* THEN the selected option value above should be "CHECKED" on the drop-down list */
			REASON = "Validate that selected option is \"CHECKED\"";
			String ACTUAL_STR = sortTypeOption.getAttribute( "class" );
			Matcher<String> EXPECTED_OF_STR = JMatchers.endsWith( "active-filter" );
			SiteSessionManager.get().createCheckPoint( "SORTING_OPTION_CHECKED" )
					.assertThat( REASON, ACTUAL_STR, EXPECTED_OF_STR );

			/* AND the user hovers over the unselected option. */
			HtmlElement unSelectedOption;
			if ( sortType.equals( ShipSortBar.SortType.A_Z ) )
			{
				unSelectedOption = cruiseShipsPage.sortBar().getSortTypeOption( ShipSortBar.SortType.FEATURED );
			}
			else
			{
				unSelectedOption = cruiseShipsPage.sortBar().getSortTypeOption( ShipSortBar.SortType.A_Z );
			}
			unSelectedOption.hover();

			/* THEN the background color changes to #DBECFF */
			REASON = "Validates that unselected option background-color changed to #DBECFF";
			color = Color.fromString( "#DBECFF" );
			condition = ExpectedConditions.elementCssPropertyToMatch(
					unSelectedOption, CSS2Properties.BACKGROUND_COLOR.getStringValue(), JMatchers.is( color.asRgba() ) );
			SiteSessionManager.get().createCheckPoint( "SORT_BACKGROUND_COLOR_ON_HOVER" )
					.assertWaitThat( REASON, TimeConstants.FIVE_SECONDS, condition );

			/* WHEN list is selected in "A-Z" sort menu */
			cruiseShipsPage.sortBar().setSortType( ShipSortBar.SortType.A_Z );

			/* THEN user verifies sort order of displayed results are alphabetical order by name.*/
			REASON = "Validates that list is alphabetical order";
			List<Ships> ACTUAL_SHIPS = cruiseShipsPage.getShips();
			List<Ships> sorted = cruiseShipsPage.getShipsSorted( ACTUAL_SHIPS );
			Matcher<List<Ships>> EXPECTED_OF_SHIPS = JMatchers.equalTo( sorted );
			SiteSessionManager.get().createCheckPoint( "A-Z_SORTING" )
					.assertThat( REASON, ACTUAL_SHIPS, EXPECTED_OF_SHIPS );


			/* WHEN user hovers over the "LAYOUT" options */
			he = cruiseShipsPage.sortBar().hoverLayoutType();

			/* the title change color from #0a407d to #009BD0 */
			REASON = "Validates that toggle color changed to #009BD0";
			color = Color.fromString( "#009BD0" );
			condition =
					ExpectedConditions.elementCssPropertyToMatch( he, CSS2Properties.COLOR.getStringValue(), JMatchers.is( color.asRgba() ) );
			SiteSessionManager.get().createCheckPoint( "LAYOUT_FONT_COLOR_ON_HOVER" )
					.assertWaitThat( REASON, TimeConstants.FIVE_SECONDS, condition );

			/* AND the user clicks on the list and expand it */
			ShipSortBar.LayoutType layout = cruiseShipsPage.sortBar().getLayoutType();
			HtmlElement layoutOption = cruiseShipsPage.sortBar().getLayoutOption( layout );

			/* THEN the selected option value above should be "CHECKED" on the drop-down list */
			REASON = "Validate that selected option is \"CHECKED\"";
			ACTUAL_STR = layoutOption.getAttribute( "class" );
			EXPECTED_OF_STR = JMatchers.endsWith( "active-filter" );
			SiteSessionManager.get().createCheckPoint( "LAYOUT_OPTION_CHECKED" )
					.assertThat( REASON, ACTUAL_STR, EXPECTED_OF_STR );

			/* AND the user hovers over the unselected option. */
			if ( layout.equals( ShipSortBar.LayoutType.BY_GRID ) )
			{
				unSelectedOption = cruiseShipsPage.sortBar().getLayoutOption( ShipSortBar.LayoutType.BY_LIST );
			}
			else
			{
				unSelectedOption = cruiseShipsPage.sortBar().getLayoutOption( ShipSortBar.LayoutType.BY_GRID );
			}
			unSelectedOption.hover();

			/* THEN the background color changes to #DBECFF */
			REASON = "Validates that unselected option background-color changed to #DBECFF";
			color = Color.fromString( "#DBECFF" );
			condition = ExpectedConditions.elementCssPropertyToMatch(
					unSelectedOption, CSS2Properties.BACKGROUND_COLOR.getStringValue(), JMatchers.is( color.asRgba() ) );
			SiteSessionManager.get().createCheckPoint( "LAYOUT_BACKGROUND_COLOR_ON_HOVER" )
					.assertWaitThat( REASON, TimeConstants.FIVE_SECONDS, condition );

			/* AND WHEN list is selected in "FEATURED" menu */
			cruiseShipsPage.sortBar().setSortType( ShipSortBar.SortType.FEATURED );

			/* THEN user verifies FEATURED order displayed should match feature order as in site-core */
			REASON = "Validates that list is in featured order";
			ACTUAL_SHIPS = cruiseShipsPage.getShips();
			sorted = cruiseShipsPage.getShipFeaturedSorted();
			EXPECTED_OF_SHIPS = JMatchers.equalTo( sorted );
			SiteSessionManager.get().createCheckPoint( "FEATURED_LAYOUT" )
					.assertThat( REASON, ACTUAL_SHIPS, EXPECTED_OF_SHIPS );

			SiteSessionManager.get().assertAllCheckpoints();
		}
		catch ( WebDriverException | AssertionError e )
		{
			throw new ApplicationException( SiteSessionManager.get().getDriver(), e );
		}
	}

	@UserStory ( "PBI-52423" )
	@TestCaseId ( id = 66759 )
	@Steps ( steps = {
			@Step ( number = 1, description = "GIVEN that the user is in Ships Landing page /cruise-ships.aspx" ),
			@Step ( number = 2, description = "WHEN user verifies the ship tile",
					expectedResults = {
							"THEN tile display image, Ship Name, Destinations, Departure Ports and Trip Length"
					} )
	} )
	@Test ( description = "Ships Landing page. Tile Display",
			enabled = true,
			invocationCount = 3,
			groups = { "US", "UK" } )
	public void shipsLandingPage_TileDisplay() throws Exception
	{

		/* GIVEN that the user is in Ships Landing page /cruise-ships.aspx */
		PreConditions.checkNotNull( this.cruiseShipsPage, "Cruise Ship page is null, before starting test" );
		try
		{
			ShipCard shipCard = ( ( CruiseShipsPage ) cruiseShipsPage ).selectRandomShip();
			logger.debug( "Ship : {}", shipCard.getShip().getFullName() );
			logger.debug( "Image src: {}", shipCard.getImage().getAttribute( "src" ) );
			logger.debug( "Departure Ports: {}", Joiner.on( ", " ).join( shipCard.getDeparturePortNames() ) );
			logger.debug( "Destinations: {}", Joiner.on( ", " ).join( shipCard.getDestinationNames() ) );
			logger.debug( "Trip Durations: {}", Joiner.on( ", " ).join( shipCard.getTripDurationNames() ) );
		}
		catch ( WebDriverException | AssertionError e )
		{
			throw new ApplicationException( SiteSessionManager.get().getDriver(), e );
		}
	}

	@UserStory ( "PBI-52423" )
	@TestCaseId ( id = 66759 )
	@Steps ( steps = {
			@Step ( number = 1, description = "GIVEN that the user is in Ships Landing page /cruise-ships.aspx" ),
			@Step ( number = 2, description = "WHEN user verifies the ship tile",
					expectedResults = {
							"THEN tile display image, Ship Name, Destinations, Departure Ports and Trip Length"
					} )
	} )
	@Test ( description = "Ships Landing page. Tile Display",
			enabled = true,
			groups = { "AU" } )
	public void shipsLandingPage_TileDisplay_AU() throws Exception
	{

		/* GIVEN that the user is in Ships Landing page /cruise-ships.aspx */
		PreConditions.checkNotNull( this.cruiseShipsPage, "Cruise Ship page is null, before starting test" );
		Ships[] ships = new Ships[] { Ships.SPIRIT, Ships.LEGEND };
		try
		{
			int rnd = RandomUtils.nextInt( 0, 1 );
			ShipCard shipCard = cruiseShipsPage.selectShip( ships[ rnd ] );
			logger.debug( "Ship : {}", shipCard.getShip().getFullName() );
			logger.debug( "Image src: {}", shipCard.getImage().getAttribute( "src" ) );
			logger.debug( "Departure Ports: {}", Joiner.on( ", " ).join( shipCard.getDeparturePortNames() ) );
			logger.debug( "Destinations: {}", Joiner.on( ", " ).join( shipCard.getDestinationNames() ) );
			logger.debug( "Trip Durations: {}", Joiner.on( ", " ).join( shipCard.getTripDurationNames() ) );
		}
		catch ( WebDriverException | AssertionError e )
		{
			throw new ApplicationException( SiteSessionManager.get().getDriver(), e );
		}
	}

	@UserStory ( "PBI-52423" )
	@TestCaseId ( id = 66763 )
	@Steps ( steps = {
			@Step ( number = 1, description = "GIVEN that the user is in Ships Landing page /cruise-ships.aspx" ),
			@Step ( number = 2, description = "WHEN user verifies the ship tile \"Departure Ports\" item",
					expectedResults = {
							"THEN each departure port links to the region page of the specific home-port."
					} )
	} )
	@Test ( description = "Ships Landing page. Tile Destinations",
			enabled = true,
			invocationCount = 3,
			priority = 1,
			groups = { "US", "UK" } )
	public void shipsLandingPage_TileDeparturePorts() throws Exception
	{

		/* GIVEN that the user is in Ships Landing page /cruise-ships.aspx */
		PreConditions.checkNotNull( this.cruiseShipsPage, "Cruise Ship page is null, before starting test" );

		try
		{
			/* WHEN user verifies the ship tile "Departure Ports" item */
			ShipCard shipCard = ( ( CruiseShipsPage ) cruiseShipsPage ).selectRandomShip();
			List<DeparturePorts> departurePorts = shipCard.getDeparturePorts();
			DeparturePorts selected;
			if ( departurePorts.size() == 1 )
			{
				selected = departurePorts.get( 0 );
			}
			else
			{
				int rnd = RandomUtils.nextInt( 0, departurePorts.size() - 1 );
				selected = departurePorts.get( rnd );
			}

			/* THEN each departure port links to the region page of the specific home-port */

			CruiseFromPortPage cruiseFromPortPage = shipCard.selectDeparturePort( selected );
			String REASON = "Validates Departure port url " + selected.name();
			String ACTUAL_STR = cruiseFromPortPage.getCurrentUrl();
			Matcher<String> EXPECTED_OF_STR = JMatchers.endsWithIgnoreCase( selected.getHref() + ".aspx" );
			SiteSessionManager.get().createCheckPoint( "PORT_URL_" + selected.name() )
					.assertThat( REASON, ACTUAL_STR, EXPECTED_OF_STR );

			REASON = "Validates breadcrumb last child " + selected.getDeparturePort();
			ACTUAL_STR = cruiseFromPortPage.breadcrumbsBar().breadcrumbs().getLastChildName();
			EXPECTED_OF_STR = JMatchers.containsStringIgnoreCase( selected.getHref() );
			if ( selected.equals( DeparturePorts.SAN_JUAN ) )
			{
				EXPECTED_OF_STR = JMatchers.containsStringIgnoreCase( "San-Juan" );
			}

			SiteSessionManager.get().createCheckPoint( "BREADCRUMB_" + selected.name() )
					.assertThat( REASON, ACTUAL_STR, EXPECTED_OF_STR );
			cruiseFromPortPage.header().headerLinks().hoverOnItem( LevelOneMenuItem.EXPLORE );
			cruiseFromPortPage.header().navigationAdditional().clickOnMenuItem( LevelOneMenuItem.EXPLORE, MenuItems.OUR_SHIPS );

			SiteSessionManager.get().assertAllCheckpoints();
		}
		catch ( WebDriverException | AssertionError e )
		{
			throw new ApplicationException( SiteSessionManager.get().getDriver(), e );
		}
	}

	@UserStory ( "PBI-52423" )
	@TestCaseId ( id = 66761 )
	@Steps ( steps = {
			@Step ( number = 1, description = "GIVEN that the user is in Ships Landing page /cruise-ships.aspx" ),
			@Step ( number = 2, description = "WHEN user verifies the ship tile \"Destination\" item",
					expectedResults = {
							"THEN each destination links to the region page of the specific destination."
					} )
	} )
	@Test ( description = "Ships Landing page. Tile Destinations",
			enabled = true,
			invocationCount = 3,
			groups = { "AU", "US", "UK"  } )
	public void shipsLandingPage_TileDestinations( ITestContext context ) throws Exception
	{
		ShipCard shipCard;

		/* GIVEN that the user is in Ships Landing page /cruise-ships.aspx */
		PreConditions.checkNotNull( this.cruiseShipsPage, "Cruise Ship page is null, before starting test" );
		try
		{
			/* WHEN user verifies the ship tile "Destinations" item */
			if( SiteSessionManager.get().getCurrentLocale().equals( HomePage.AU ) )
			{
				Ships[] ships = new Ships[] { Ships.SPIRIT, Ships.LEGEND };
				if( context.getAttribute( "tileDestinations" ) == null )
				{
					context.setAttribute( "tileDestinations", 0 );
				}
				int rnd = PropertyConverter.toInteger( context.getAttribute( "tileDestinations" ) );
				if( rnd == 0 )
				{
					shipCard = cruiseShipsPage.selectShip( ships[ 0 ] );
					context.setAttribute( "tileDestinations", 1 );
				}
				else
				{
					shipCard = cruiseShipsPage.selectShip( ships[ 1 ] );
					context.setAttribute( "tileDestinations", 0 );
				}
			}
			else
			{
				shipCard = ( ( CruiseShipsPage ) cruiseShipsPage ).selectRandomShip();
			}

			List<Destinations> destinations = shipCard.getDestinations();
			Destinations selected;
			if ( destinations.size() == 1 )
			{
				selected = destinations.get( 0 );
			}
			else
			{
				int rnd = RandomUtils.nextInt( 0, destinations.size() - 1 );
				selected = destinations.get( rnd );
			}

			BaseCarnivalPage page = shipCard.selectDestination( selected );
			if ( page instanceof CruiseToDestinationPage )
			{
				CruiseToDestinationPage cruiseToDestinationPage = ( CruiseToDestinationPage ) page;
				String REASON = "Validates Departure port url " + selected.name().toLowerCase();
				String ACTUAL_STR = cruiseToDestinationPage.getCurrentUrl();
				String expected;
				if( SiteSessionManager.get().getCurrentLocale().equals( HomePage.AU ) )
				{
					expected = selected.getHref() + ".aspx";
				}
				else
				{
					expected = selected.getHref() + "-cruises.aspx";
				}
				Matcher<String> EXPECTED_OF_STR = JMatchers.endsWithIgnoreCase( expected );
				SiteSessionManager.get().createCheckPoint( "PORT_URL_" + selected.name() )
						.assertThat( REASON, ACTUAL_STR, EXPECTED_OF_STR );

				cruiseToDestinationPage.header().headerLinks().hoverOnItem( LevelOneMenuItem.EXPLORE );
				cruiseToDestinationPage.header().navigationAdditional().clickOnMenuItem( LevelOneMenuItem.EXPLORE, MenuItems.OUR_SHIPS );
			}
			else
			{
				CruiseToPage cruiseToPage = ( CruiseToPage ) page;
				String REASON = "Validates Departure port url " + selected.name().toLowerCase();
				String ACTUAL_STR = cruiseToPage.getCurrentUrl();
				Matcher<String> EXPECTED_OF_STR = JMatchers.endsWithIgnoreCase( "/cruise-to.aspx" );
				SiteSessionManager.get().createCheckPoint( "PORT_URL_" + selected.name() )
						.assertThat( REASON, ACTUAL_STR, EXPECTED_OF_STR );

				cruiseToPage.header().headerLinks().hoverOnItem( LevelOneMenuItem.EXPLORE );
				cruiseToPage.header().navigationAdditional().clickOnMenuItem( LevelOneMenuItem.EXPLORE, MenuItems.OUR_SHIPS );

			}

			SiteSessionManager.get().assertAllCheckpoints();
		}
		catch ( WebDriverException | AssertionError e )
		{
			throw new ApplicationException( SiteSessionManager.get().getDriver(), e );
		}
	}

	@UserStory ( "PBI-52423" )
	@TestCaseId ( id = 66765 )
	@Steps ( steps = {
			@Step ( number = 1, description = "GIVEN that the user is in Ships Landing page /cruise-ships.aspx" ),
			@Step ( number = 2, description = "WHEN user verifies the ship tile \"Trip Duration\" item",
					expectedResults = {
							"THEN each trip duration redirects to the search page with correct query e.g. shipCode=LE&dur=D2",
							"AND search results count should be at least 1",
							"AND Refine Search checked ship item should be the selected ship",
							"AND Refine Search checked trip duration item should be the selected duration item",
					} )
	} )
	@Test ( description = "Ships Landing page. Tile Destinations",
			invocationCount = 3,
			enabled = true,
			groups = { "AU", "US", "UK"  } )
	public void shipsLandingPage_TileTripLength( ITestContext context ) throws Exception
	{
		ShipCard shipCard;
		/* GIVEN that the user is in Ships Landing page /cruise-ships.aspx */
		PreConditions.checkNotNull( this.cruiseShipsPage, "Cruise Ship page is null, before starting test" );
		try
		{
			/* WHEN user verifies the ship tile "Trip Durations" item */
			if( SiteSessionManager.get().getCurrentLocale().equals( HomePage.AU ) )
			{
				Ships[] ships = new Ships[] { Ships.SPIRIT, Ships.LEGEND };
				int rnd = RandomUtils.nextInt( 0, 1 );
				shipCard = cruiseShipsPage.selectShip( ships[ rnd ] );
			}
			else
			{
				shipCard = ( ( CruiseShipsPage ) cruiseShipsPage ).selectRandomShip();
			}

			List<TripDurations> durations = shipCard.getTripDurations();
			TripDurations selected;
			if ( durations.size() == 1 )
			{
				selected = durations.get( 0 );
			}
			else
			{
				int rnd = RandomUtils.nextInt( 0, durations.size() - 1 );
				selected = durations.get( rnd );
			}
			BaseCarnivalPage page = shipCard.selectTripDuration( selected );
			List<String> groups = Lists.newArrayList( context.getIncludedGroups() );
			String expectedQuery;

			/* AND search results count should be at least 1 */

			if ( groups.contains( "US" ) || groups.contains( "UK" ) )
			{
				CruiseSearchPage searchPage = ( CruiseSearchPage ) page;
				String REASON = "Validate that at least one itinerary was found.";
				int ACTUAL_INT = searchPage.refineSearch().getItinerariesFound();
				Matcher<Integer> EXPECTED_OF_INT = JMatchers.greaterThanOrEqualTo( 1 );
				SiteSessionManager.get().createCheckPoint( "MIN_ITINERARIES_FOR_SHIP_" + shipCard.getShip().name() )
						.assertThat( REASON, ACTUAL_INT, EXPECTED_OF_INT );

				/* AND Refine Search checked ship item should be the selected ship */

				REASON = "Validate that ship " + shipCard.getShip().getFullName() + " is checked";
				List<Ships> ACTUAL_OF_SHIPS = searchPage.refineSearch().getCheckedShips();
				Matcher<Iterable<? super Ships>> EXPECTED_OF_SHIPS = JMatchers.hasItem( shipCard.getShip() );
				SiteSessionManager.get().createCheckPoint( "SHIP_CHECKED_" + shipCard.getShip().name() )
						.assertThat( REASON, ACTUAL_OF_SHIPS, EXPECTED_OF_SHIPS );

				/* AND Refine Search checked trip duration item should be the selected duration item */

				REASON = "Validate that duration " + selected.getTitle() + " is checked";
				List<TripDurations> ACTUAL_OF_DURATIONS = searchPage.refineSearch().getCheckedTripDurations();
				Matcher<Iterable<? super TripDurations>> EXPECTED_OF_DURATIONS = JMatchers.hasItem( selected );
				SiteSessionManager.get().createCheckPoint( "DURATION_CHECKED_" + selected.name() )
						.assertThat( REASON, ACTUAL_OF_DURATIONS, EXPECTED_OF_DURATIONS );
				expectedQuery = searchPage.getExpectedUrlQuery();
			}
			else
			{
				SearchResultsPage searchPage = ( SearchResultsPage ) page;
				String REASON = "Validate that at least one itinerary was found.";
				int ACTUAL_INT = searchPage.getItinerariesFound();
				Matcher<Integer> EXPECTED_OF_INT = JMatchers.greaterThanOrEqualTo( 1 );
				SiteSessionManager.get().createCheckPoint( "MIN_ITINERARIES_FOR_SHIP_" + shipCard.getShip().name() )
						.assertThat( REASON, ACTUAL_INT, EXPECTED_OF_INT );

				expectedQuery = searchPage.getExpectedUrlQuery();
			}

			/* THEN each trip duration redirects to the search page with correct query */

			String REASON = "Validates url query";
			String ACTUAL_STR = page.getURL().getQuery();
			Matcher<String> EXPECTED_OF_STR = JMatchers.equalToIgnoringCase( expectedQuery );
			SiteSessionManager.get().createCheckPoint( "URL_QUERY_" + shipCard.getShip().name() )
					.assertThat( REASON, ACTUAL_STR, EXPECTED_OF_STR );

			page.header().headerLinks().hoverOnItem( LevelOneMenuItem.EXPLORE );
			page.header().navigationAdditional().clickOnMenuItem( LevelOneMenuItem.EXPLORE, MenuItems.OUR_SHIPS );


			SiteSessionManager.get().assertAllCheckpoints();
		}
		catch ( WebDriverException | AssertionError e )
		{
			throw new ApplicationException( SiteSessionManager.get().getDriver(), e );
		}

	}

	@UserStory ( "PBI-52423" )
	@TestCaseId ( id = 66767 )
	@Steps ( steps = {
			@Step ( number = 1, description = "GIVEN that the user is in Ships Landing page /cruise-ships.aspx" ),
			@Step ( number = 2, description = "WHEN user verifies the content",
					expectedResults = { "THEN it match the Australia content" } )
	} )
	@Test ( description = "Ships Landing page. Australia Specific Content",
			enabled = true,
			groups = { "AU" } )
	public void shipsLandingPage_AustraliaSpecificContent() throws Exception
	{

		/* GIVEN that the user is in Ships Landing page /cruise-ships.aspx */
		PreConditions.checkNotNull( this.cruiseShipsPage, "Cruise Ship page is null, before starting test" );
		try
		{
			String REASON = "Validate Australia have only X ships.";
			Object o = SiteProperty.SHIPS_COUNT.fromContext();
			int ACTUAL_INT = cruiseShipsPage.getShipCardsCount();
			Matcher<Integer> EXPECTED_OF_INT = JMatchers.is( PropertyConverter.toInteger( o ) );
			SiteSessionManager.get().createCheckPoint( "SHIPS_COUNT" )
					.assertThat( REASON, ACTUAL_INT, EXPECTED_OF_INT );

			REASON = "Validate counter for X ships.";
			ACTUAL_INT = cruiseShipsPage.sortBar().getResults();
			EXPECTED_OF_INT = JMatchers.is( PropertyConverter.toInteger( o ) );
			SiteSessionManager.get().createCheckPoint( "SHIPS_DISPLAY_COUNTER" )
					.assertThat( REASON, ACTUAL_INT, EXPECTED_OF_INT );

			cruiseShipsPage.getAllShipsCards();

			SiteSessionManager.get().assertAllCheckpoints();
		}
		catch ( WebDriverException | AssertionError e )
		{
			throw new ApplicationException( SiteSessionManager.get().getDriver(), e );
		}
	}

	@UserStory ( "PBI-52423" )
	@TestCaseId ( id = 66778 )
	@Steps ( steps = {
			@Step ( number = 1, description = "GIVEN that the user is in Ships Landing page /cruise-ships.aspx" ),
			@Step ( number = 2, description = "WHEN user verifies the Hero banner",
					expectedResults = { "THEN the hero banner images count are greater or equal to 1" } )
	} )
	@Test ( description = "Ships Landing page. Hero banner image",
			enabled = true,
			groups = { "AU", "US", "UK" } )
	public void shipsLandingPage_HeroBannerImage() throws Exception
	{

		/* GIVEN that the user is in Ships Landing page /cruise-ships.aspx */
		PreConditions.checkNotNull( this.cruiseShipsPage, "Cruise Ship page is null, before starting test" );

		try
		{
			/* WHEN user verifies the Hero banner */

			List<HtmlElement> images = cruiseShipsPage.getHeroBannerSlides();

			/* THEN the hero banner images count are greater or equal to 1 */

			String REASON = "Validate Hero banner has at least one image";
			int ACTUAL_INT = images.size();
			Matcher<Integer> EXPECTED_OF_INT = JMatchers.greaterThan( 0 );
			SiteSessionManager.get().createCheckPoint( "HERO_BANNER_IMG_COUNT" )
					.assertThat( REASON, ACTUAL_INT, EXPECTED_OF_INT );

			SiteSessionManager.get().assertAllCheckpoints();
		}
		catch ( WebDriverException | AssertionError e )
		{
			throw new ApplicationException( SiteSessionManager.get().getDriver(), e );
		}
	}

	//endregion


	//region ShipCompareTest - After Configurations Methods Section

	@AfterMethod ( description = "", groups = { "US", "UK", "AU" }, enabled = true, alwaysRun = false )
	public void afterMethod( ITestResult testResult, XmlTest xmlTest, Method method ) throws Exception
	{

		try
		{
			if ( shouldCloseSession( testResult ) )
			{
				SiteSessionManager.get().closeSession();
				this.homePage = null;
				return;
			}

			if ( method.getName().equals( "shipsLandingPage_TileDisplay" )
					|| method.getName().equals( "shipsLandingPage_TileDestinations" )
					|| method.getName().equals( "shipsLandingPage_TileTripLength" )
					|| method.getName().equals( "shipsLandingPage_TileDeparturePorts" ) )
			{
				ITestNGMethod testNGMethod = testResult.getMethod();
				if ( testNGMethod.getInvocationCount() == testNGMethod.getCurrentInvocationCount() )
				{
					SiteSessionManager.get().closeSession();
					this.homePage = null;
					this.cruiseShipsPage = null;
				}
			}
			else
			{
				SiteSessionManager.get().closeSession();
				this.homePage = null;
			}
		}
		catch ( Throwable e )
		{
			//TestReporter.error( e.getLocalizedMessage() );
			throw new Exception( e.getMessage(), e );
		}
	}

	@AfterClass ( description = "quit all drivers", alwaysRun = true )
	public void afterClass() throws Exception
	{
		try
		{
			SiteSessionManager.get().endSession();
			this.homePage = null;
		}
		catch ( Throwable e )
		{
			throw new Exception( e.getMessage(), e );
		}
	}

	//endregion


	//region ShipCompareTest - Data-Providers Section

	@DataProvider ( name = "filterDP", parallel = false )
	public Object[][] filters_DataProvider( ITestContext context, Method method )
	throws Exception
	{
		String str = ( String ) SiteProperty.HEADER_TOP_DESTINATIONS.fromContext();
		String[] destinations = StringUtils.split( str, "," );

		List<Object[]> data = Lists.newArrayListWithExpectedSize( destinations.length );
		for ( String destination : destinations )
		{
			String destinationName = destination.replace( " Cruises", "" );
			data.add( new Object[] { destinationName, Destinations.valueOf( destinationName.toUpperCase() ) } );
		}

		return data.toArray( new Object[ data.size() ][] );

	}

	//endregion

}
