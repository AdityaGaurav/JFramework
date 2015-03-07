package com.framework.test.core.ships;

import com.framework.BaseTest;
import com.framework.config.Configurations;
import com.framework.driver.event.ExpectedConditions;
import com.framework.driver.event.HtmlCondition;
import com.framework.driver.event.HtmlElement;
import com.framework.driver.exceptions.ApplicationException;
import com.framework.site.config.SiteProperty;
import com.framework.site.config.SiteSessionManager;
import com.framework.site.data.DeparturePorts;
import com.framework.site.data.Destinations;
import com.framework.site.data.Ships;
import com.framework.site.data.TripDurations;
import com.framework.site.objects.body.interfaces.ContentBlockComparing;
import com.framework.site.objects.body.interfaces.ShipCard;
import com.framework.site.objects.header.enums.LevelOneMenuItem;
import com.framework.site.objects.header.enums.MenuItems;
import com.framework.site.pages.bookingengine.CruiseSearchPage;
import com.framework.site.pages.core.CruiseShipsPage;
import com.framework.site.pages.core.HomePage;
import com.framework.site.pages.core.cruiseships.CompareCruiseShipsPage;
import com.framework.testing.annotations.*;
import com.framework.utils.conversion.Converter;
import com.framework.utils.datetime.TimeConstants;
import com.framework.utils.error.PreConditions;
import com.framework.utils.img.ImageCompare;
import com.framework.utils.matchers.JMatchers;
import com.framework.utils.web.CSS2Properties;
import com.google.common.collect.Lists;
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

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.List;
import java.util.Map;


public class ShipCompareTest extends BaseTest
{

	//region ShipCompareTest - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( ShipCompareTest.class );

	private HomePage homePage = null;

	private CruiseShipsPage cruiseShipsPage = null;

	private CompareCruiseShipsPage compareCruiseShipsPage = null;

	private List<ContentBlockComparing.CompareSection> sections = Lists.newArrayList();

	private File blueX, blueO;

	private List<ShipCard> shipCards;

	//endregion


	//region ShipCompareTest - Constructor Methods Section

	private ShipCompareTest()
	{
		BaseTest.classLogger = LoggerFactory.getLogger( ShipCompareTest.class );
	}

	//endregion


	//region ShipCompareTest - Before Configurations Methods Section

	@BeforeClass ( description = "starts a web driver",
			alwaysRun = true,
			groups = { "US", "UK", "AU" }
	)
	public void beforeClass( ITestContext testContext, XmlTest xmlTest ) throws Exception
	{
		killExistingBrowsersOpenedByWebDriver();
		SiteSessionManager.get().startSession();
		testContext.setAttribute( "compare", true );
		URL url = this.getClass().getClassLoader().getResource( CompareCruiseShipsPage.IMG_BLUE_X );
		url = PreConditions.checkNotNull( url, "The file img/blue-x.png was not found in classpath" );
		this.blueX = new File( url.toURI() );
		url = this.getClass().getClassLoader().getResource( CompareCruiseShipsPage.IMG_BLUE_BULLET );
		url = PreConditions.checkNotNull( url, "The file img/blue-bullet.png was not found in classpath" );
		this.blueO = new File( url.toURI() );
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
				this.cruiseShipsPage = ( CruiseShipsPage )
						this.homePage.header().navigationAdditional().clickOnMenuItem( LevelOneMenuItem.EXPLORE, MenuItems.OUR_SHIPS );
			}
			if( method.getName().equals( "compareShips_AccordionCollapse" )
					|| method.getName().equals( "compareShips_HoverAction" ) )
			{
				if( Converter.toBoolean( testContext.getAttribute( "compare" ) ) )
				{
					shipCards = add2Ships();
				}
			}
			else if( method.getName().equals( "compareShips_Display_Difference_Availability" )
					|| method.getName().equals( "compareShips_Display_Available_NotAvailable_Images" ) )
			{
				shipCards = add3Ships();
			}
			else
			{
				shipCards = add3Ships();
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

	@UserStory ( "PBI-65535" )
	@TestCasesIds ( ids = { 60201 } )
	@Steps ( steps = {
			@Step ( number = 1, description = "GIVEN user in compare page \"/cruise-ships/compare-cruise-ships\" with 2 ships." ),
			@Step ( number = 2, description = "WHEN user clicks on the accordion bar which is a \"-\" displayed",
					expectedResults = { "THEN the clicked bar accordion is collapsed",
							            "AND displays a \"+\" sign in the bar" } )
	} )
	@Test ( description = "Compare Ships page. verify accordion collapse",
			enabled = false,
			groups = { "US", "UK"  }
	)
	public void compareShips_AccordionCollapse() throws Exception
	{
		/* GIVEN user in compare page "/cruise-ships/compare-cruise-ships" with 2 ships. */
		PreConditions.checkNotNull( this.compareCruiseShipsPage, "Compare Cruise Ship page is null, before starting test" );

		try
		{
			/* WHEN user clicks on the accordion bar which is a "-" displayed */
			List<ContentBlockComparing.CompareSection> sections = compareCruiseShipsPage.contentBlockComparing().getExpandedSections();
			for( ContentBlockComparing.CompareSection section : sections )
			{
				String name = section.getSectionName();
				/* THEN the clicked bar accordion is collapsed */
				section.collapse();

				/* AND displays a "+" sign in the bar */
				String REASON = "Validate that section " + name + " was collapsed";
				Boolean ACTUAL_BOOL = section.isExpanded();
				Matcher<Boolean> EXPECTED_OF_BOOL = JMatchers.is( false );
				SiteSessionManager.get().createCheckPoint( "SECTION_COLLAPSED_" + name.replace( " ", "_" ).toUpperCase() )
						.assertThat( REASON, ACTUAL_BOOL, EXPECTED_OF_BOOL );
			}

			SiteSessionManager.get().assertAllCheckpoints();
		}
		catch ( WebDriverException | AssertionError e )
		{
			throw new ApplicationException( SiteSessionManager.get().getDriver(), e );
		}
	}

	@UserStory ( "PBI-65535" )
	@TestCasesIds ( ids = { 60209, 68360, 72919 } )
	@Steps ( steps = {
			@Step ( number = 1, description = "GIVEN user in compare page \"/cruise-ships/compare-cruise-ships\" with 3 ships." ),
			@Step ( number = 2, description = "WHEN user clicks on @activityAccordion" ),
			@Step ( number = 3, description = "AND verifies the displayed activities options",
					expectedResults = { "THEN a LINE displayed to the left of the activity name when is a difference on availability" +
							            "among ships being compared." } )
	} )
	@Test ( description = "Compare Ships page. Verify display when difference in availability",
			enabled = false,
			groups = { "US", "UK"  }
	)
	public void compareShips_Display_Difference_Availability() throws Exception
	{
		/* GIVEN user in compare page "/cruise-ships/compare-cruise-ships" with 3 ships. */
		PreConditions.checkNotNull( this.compareCruiseShipsPage, "Compare Cruise Ship page is null, before starting test" );
		try
		{
			List<ContentBlockComparing.CompareSection> sections = compareCruiseShipsPage.contentBlockComparing().getComparisonSections();
			for( ContentBlockComparing.CompareSection section : sections )
			{
				section.expand();
				String sectionName = section.getSectionName();
				// ignoring ship details section
				if( ! sectionName.equalsIgnoreCase( "SHIP DETAILS" ) )
				{
					Map<String, Map.Entry<HtmlElement, Map<Ships, String>>> rows = section.getRows();
					for( String parameter : rows.keySet() )
					{
						Map<Ships,String> values = rows.get( parameter ).getValue();
						// Set<Map.Entry<Ships, String>> entry = values.entrySet();
						// String joined = Joiner.on( " | " ).withKeyValueSeparator( ": " ).join( entry );
						// System.out.println( "Parameter Name:" + parameter + "; values -> " + joined );
						String headerStyle = rows.get( parameter ).getKey().getAttribute( "style" );
						String REASON = String.format( "Validate difference logic on parameter/section %s/%s", parameter, sectionName );
						Boolean ACTUAL_BOOL = headerStyle.isEmpty();
						Matcher<Boolean> EXPECTED_RESULT_OF_BOOL = JMatchers.is( section.indicatorNotVisible( values ) );

						String pName = StringUtils.replaceChars( parameter, " ,", "_" ).toUpperCase();
						SiteSessionManager.get().createCheckPoint( "LINE VISIBILITY_" + pName )
								.assertThat( REASON, ACTUAL_BOOL, EXPECTED_RESULT_OF_BOOL );

					}
				}

				section.collapse();
			}

			SiteSessionManager.get().assertAllCheckpoints();
		}
		catch ( WebDriverException | AssertionError e )
		{
			throw new ApplicationException( SiteSessionManager.get().getDriver(), e );
		}
	}

	@UserStory ( "PBI-65535" )
	@TestCaseId ( id = 60211 )
	@Steps ( steps = {
			@Step ( number = 1, description = "GIVEN user in compare page \"/cruise-ships/compare-cruise-ships\" with 3 ships." ),
			@Step ( number = 2, description = "WHEN user clicks in the bar on @activityAccordion" ),
			@Step ( number = 3, description = "AND examine the comparison results",
					expectedResults = { "THEN the Not available image is a blue X",
							"AND the available image represented by a blue O",
							"AND filter-soon contains @AVAILABLE SOON"
					} )
	} )
	@Test ( description = "Compare Ships page. Verify available and not available images",
			enabled = false,
			groups = { "US", "UK"  }
	)
	public void compareShips_Display_Available_NotAvailable_Images() throws Exception
	{
		/* GIVEN user in compare page "/cruise-ships/compare-cruise-ships" with 3 ships. */
		PreConditions.checkNotNull( this.compareCruiseShipsPage, "Compare Cruise Ship page is null, before starting test" );
		String expectedText = SiteProperty.FILTER_SOON_TEXT.from( Configurations.getInstance(), "COMING SOON" );

		try
		{
			compareCruiseShipsPage.contentBlockComparing().collapseAll();
			List<ContentBlockComparing.CompareSection> sections = compareCruiseShipsPage.contentBlockComparing().getComparisonSections();
			ContentBlockComparing.CompareSection selected;
			String sectionName;
			do
			{
				int rnd = RandomUtils.nextInt( 0, sections.size() - 1 );
				selected = sections.get( rnd );
				sectionName = selected.getSectionName();
			}
			while( sectionName.equalsIgnoreCase( "SHIP DETAILS" ) );

			/* WHEN user clicks in the bar on @activityAccordion */
			selected.expand();

			/* AND examine the comparison results */
			HtmlElement table = selected.getTable();
			table.scrollIntoView( false );
			List<HtmlElement> tds = table.findElements( By.tagName( "td" ) );
			int index = 0;
			for( HtmlElement td : tds )
			{
				String innerHtml = td.getAttribute( "innerHTML" );

				/* AND the available image represented by a blue O */
				if( innerHtml.contains( "availableIcon" ) )
				{
					String src = td.findElement( By.tagName( "img" ) ).getAttribute( "src" );
					URL href = new URL( src );
					String REASON = "Validate available icon image index " + index;
					Boolean ACTUAL_BOOL = ImageCompare.imagesEqual( blueO, href );
					Matcher<Boolean> EXPECTED_OF_BOOLEAN = JMatchers.is( true );
					SiteSessionManager.get().createCheckPoint( "AVAILABLE_ICON_" + index )
							.assertThat( REASON, ACTUAL_BOOL, EXPECTED_OF_BOOLEAN );
					index ++;
				}
				/* THEN the Not available image is a blue X */
				else if( innerHtml.contains( "notAvailableIcon" ) )
				{
					String src = td.findElement( By.tagName( "img" ) ).getAttribute( "src" );
					URL href = new URL( src );
					String REASON = "Validates not available icon image index " + index;
					Boolean ACTUAL_BOOL = ImageCompare.imagesEqual( blueX, href );
					Matcher<Boolean> EXPECTED_OF_BOOLEAN = JMatchers.is( true );
					SiteSessionManager.get().createCheckPoint( "NOT_AVAILABLE_ICON_" + index )
							.assertThat( REASON, ACTUAL_BOOL, EXPECTED_OF_BOOLEAN );
					index ++;
				}
				/* AND filter-soon contains @AVAILABLE SOON */
				else if( innerHtml.contains( "filter-soon" ) )
				{
					String REASON = "Validates filter-soon text index " + index;
					String ACTUAL_STR = td.findElement( By.className( "filter-soon" ) ).getText();
					Matcher<String> EXPECTED_OF_STR = JMatchers.equalToIgnoringCase( expectedText );
					SiteSessionManager.get().createCheckPoint( "FILTER_SOON_TEXT_" + index )
							.assertThat( REASON, ACTUAL_STR, EXPECTED_OF_STR );
					index ++;
				}
			}

			SiteSessionManager.get().assertAllCheckpoints();
		}
		catch ( WebDriverException | AssertionError e )
		{
			throw new ApplicationException( SiteSessionManager.get().getDriver(), e );
		}
	}

	@UserStory ( "PBI-65535" )
	@TestCaseId ( id = 60212 )
	@Steps ( steps = {
			@Step ( number = 1, description = "GIVEN user in compare page \"/cruise-ships/compare-cruise-ships\" with 3 ships." ),
			@Step ( number = 2, description = "WHEN user clicks in the bar on @activityAccordion" ),
			@Step ( number = 3, description = "AND hovers over the displayed activities",
					expectedResults = { "THEN the activities column header being hovered are highlighted #F6F5F6",
										"AND the activities column is highlighted #E3E3E3"} )
	} )
	@Test ( description = "Compare Ships page. Verify hover action",
			enabled = false,
			invocationCount = 3,
			groups = { "US", "UK"  }
	)
	void compareShips_HoverAction() throws Exception
	{
		/* GIVEN user in compare page "/cruise-ships/compare-cruise-ships" with 2 ships. */
		PreConditions.checkNotNull( this.compareCruiseShipsPage, "Compare Cruise Ship page is null, before starting test" );
		try
		{
			compareCruiseShipsPage.contentBlockComparing().collapseAll();
			if( sections.size() == 0 )
			{
				sections = compareCruiseShipsPage.contentBlockComparing().getComparisonSections();
			}

			int rnd = RandomUtils.nextInt( 0, sections.size() - 1 );
			ContentBlockComparing.CompareSection selected = sections.get( rnd );

			/* WHEN user clicks in the bar on @activityAccordion */
			selected.expand();

			/* AND hovers over the displayed activities */
			HtmlElement table = selected.getTable();
			List<HtmlElement> rows = table.findElements( By.tagName( "tr" ) );
			for( HtmlElement tr : rows )
			{
				HtmlElement th = tr.findElement( By.tagName( "th" ) );
				HtmlElement td = tr.findElements( By.tagName( "td" ) ).get( 0 );
				th.scrollIntoView( false );

				/* AND hovers over the displayed activities */
				th.hover();

				/* THEN the activities column header being hovered are highlighted #F6F5F6 */
				String text = th.getText();
				String pName = StringUtils.replaceChars( text, " ,'", "_" ).toUpperCase();
				String REASON = "Validate that TH background-color for " + pName;
				Color color = Color.fromString( "#F6F6F6" );
				HtmlCondition<Boolean> condition =
						ExpectedConditions.elementCssPropertyToMatch( th, CSS2Properties.COLOR.getStringValue(), JMatchers.is( color.asRgba() ) );
				SiteSessionManager.get().createCheckPoint( "TH_BACKGROUND_COLOR_" + pName )
						.assertWaitThat( REASON, TimeConstants.FIVE_SECONDS, condition );

				td.hover();

				REASON = "Validate that TD background-color for " + text;
				color = Color.fromString( "#E3E3E3" );
				condition = ExpectedConditions.elementCssPropertyToMatch(
						th, CSS2Properties.BACKGROUND_COLOR.getStringValue(), JMatchers.is( color.asRgba() ) );
				SiteSessionManager.get().createCheckPoint( "TD_BACKGROUND_COLOR_" + pName )
						.assertWaitThat( REASON, TimeConstants.FIVE_SECONDS, condition );
			}

			SiteSessionManager.get().assertAllCheckpoints();
		}
		catch ( WebDriverException | AssertionError e )
		{
			throw new ApplicationException( SiteSessionManager.get().getDriver(), e );
		}
	}


	@UserStory ( "PBI-65535" )
	@TestCaseId ( id = 68358 )
	@Steps ( steps = {
			@Step ( number = 1, description = "GIVEN user in compare page \"/cruise-ships/compare-cruise-ships\" with 3 ships." ),
			@Step ( number = 2, description = "WHEN user examine the \"Legend\"",
					expectedResults = { "The legend keys are displayed correctly",
										"The available and notAvailable images matching site-core definition" } )
	} )
	@Test ( description = "Compare Ships page. Validate legend keys and images.",
			enabled = false,
			groups = { "US", "UK"  }
	)
	void compareShips_Legend() throws Exception
	{
		/* GIVEN user in compare page "/cruise-ships/compare-cruise-ships" with 2 ships. */
		PreConditions.checkNotNull( this.compareCruiseShipsPage, "Compare Cruise Ship page is null, before starting test" );

		try
		{
			/* WHEN user examine the "Legend" */
			HtmlElement available = compareCruiseShipsPage.getLegendAvailableImg();
			HtmlElement notAvailable = compareCruiseShipsPage.getLegendNotAvailableImg();
			String[] keys = compareCruiseShipsPage.getLegendKeys();

			/* The legend keys are displayed correctly */
			String REASON = "Validating that legend keys are displayed";
			String[] expected = Configurations.getInstance().getStringArray( SiteProperty.COMPARE_SHIPS_LEGEND_KEYS );
			Matcher<String[]> EXPECTED_OF_STR = JMatchers.arrayContaining( expected );
			SiteSessionManager.get().createCheckPoint( "LEGEND_KEYS" )
					.assertThat( REASON, keys, EXPECTED_OF_STR );

			/* The available and notAvailable images matching site-core definition */
			URL href = new URL( available.getAttribute( "src" ) );
			REASON = "Validate legend available icon image";
			Boolean ACTUAL_BOOL = ImageCompare.imagesEqual( blueO, href );
			Matcher<Boolean> EXPECTED_OF_BOOLEAN = JMatchers.is( true );
			SiteSessionManager.get().createCheckPoint( "LEGEND_AVAILABLE_ICON")
					.assertThat( REASON, ACTUAL_BOOL, EXPECTED_OF_BOOLEAN );

			href = new URL( notAvailable.getAttribute( "src" ) );
			REASON = "Validate legend not available icon image";
			ACTUAL_BOOL = ImageCompare.imagesEqual( blueX, href );
			EXPECTED_OF_BOOLEAN = JMatchers.is( true );
			SiteSessionManager.get().createCheckPoint( "LEGEND_NOT_AVAILABLE_ICON")
					.assertThat( REASON, ACTUAL_BOOL, EXPECTED_OF_BOOLEAN );

			SiteSessionManager.get().assertAllCheckpoints();
		}
		catch ( WebDriverException | AssertionError e )
		{
			throw new ApplicationException( SiteSessionManager.get().getDriver(), e );
		}
	}

	@UserStory ( "PBI-65535" )
	@TestCaseId ( id = 0 )
	@Steps ( steps = {
			@Step ( number = 1, description = "GIVEN user in compare page \"/cruise-ships/compare-cruise-ships\" with 3 ships." ),
			@Step ( number = 2, description = "WHEN user examine the \"Itinerary\" section",
					expectedResults = { "The ships name should match @index selection",
							"AND Destinations ship matches destinations from ships landing page.",
					        "AND Departure Port matches departure ports from ships landing page",
							"AND Trip Durations matches departure ports from ships landing page" } ),
			@Step ( number = 3, description = "AND WHEN the user clicks on \"View Sailings\"",
					expectedResults = { "THEN he redirects to bookingEngine/cruise-search/ page",
							 "AND the url query is shipCode=XX" } )
	} )
	@Test ( description = "Compare Ships page. Validate itinerary information",
			enabled = true,
			groups = { "US", "UK"  }
	)
	void compareShips_ItineraryInformation() throws Exception
	{
		/* GIVEN user in compare page "/cruise-ships/compare-cruise-ships" with 3 ships. */
		PreConditions.checkNotNull( this.compareCruiseShipsPage, "Compare Cruise Ship page is null, before starting test" );

		try
		{
		 	/* WHEN user examine the "Itinerary" section */
			HtmlElement he = compareCruiseShipsPage.getItinerarySubSection( CompareCruiseShipsPage.ItineraryRows.BACK );
			List<Ships> ships = compareCruiseShipsPage.getItineraryShips( he );

			/* The ships name should match @index selection */
			String REASON = "The ships name should match @index selection";
			Matcher<Iterable<? extends Ships>> EXPECTED_OF_SHIPS =
					JMatchers.contains( shipCards.get( 0 ).getShip(), shipCards.get( 1 ).getShip(), shipCards.get( 2 ).getShip() );
			SiteSessionManager.get().createCheckPoint( "SHIPS_CONTAINS" )
					.assertThat( REASON, ships, EXPECTED_OF_SHIPS );

			for( int i = 0; i < shipCards.size(); i ++ )
			{
				/* AND Destinations ship matches destinations from ships landing page */
				he = compareCruiseShipsPage.getItinerarySubSection( CompareCruiseShipsPage.ItineraryRows.DESTINATION );
				List<Destinations> EXPECTED_DESTINATIONS = shipCards.get( i ).getDestinations();
				List<Destinations> ACTUAL_DESTINATIONS = compareCruiseShipsPage.getItineraryDestinations( he, i );
				REASON = " Destinations ship matches destinations from ships landing page";
				Matcher<Iterable<? extends Destinations>> EXPECTED_OF_DESTINATIONS =
						JMatchers.containsInAnyOrder( EXPECTED_DESTINATIONS.toArray( new Destinations[ EXPECTED_DESTINATIONS.size() ] ) );
				SiteSessionManager.get().createCheckPoint( "SHIPS_DESTINATIONS_" + shipCards.get( i ).getShip().name() )
						.assertThat( REASON, ACTUAL_DESTINATIONS, EXPECTED_OF_DESTINATIONS );

				/* AND Departure Port matches departure ports from ships landing page */
				he = compareCruiseShipsPage.getItinerarySubSection( CompareCruiseShipsPage.ItineraryRows.DEPARTURE );
				List<DeparturePorts> EXPECTED_PORTS = shipCards.get( i ).getDeparturePorts();
				List<DeparturePorts> ACTUAL_PORTS = compareCruiseShipsPage.getItineraryDepartures( he, i );
				REASON = " Departures ship matches departures from ships landing page";
				Matcher<Iterable<? extends DeparturePorts>> EXPECTED_OF_PORTS =
						JMatchers.containsInAnyOrder( EXPECTED_PORTS.toArray( new DeparturePorts[ EXPECTED_PORTS.size() ] ) );
				SiteSessionManager.get().createCheckPoint( "SHIPS_DEPARTURES_" + shipCards.get( i ).getShip().name() )
						.assertThat( REASON, ACTUAL_PORTS, EXPECTED_OF_PORTS );

				/* AND Trip Durations matches departure ports from ships landing page */
				he = compareCruiseShipsPage.getItinerarySubSection( CompareCruiseShipsPage.ItineraryRows.TRIP );
				List<TripDurations> EXPECTED_DURATIONS = shipCards.get( i ).getTripDurations();
				List<TripDurations> ACTUAL_DURATIONS = compareCruiseShipsPage.getItineraryTripDurations( he, i );
				REASON = " Departures ship matches departures from ships landing page";
				Matcher<Iterable<? extends TripDurations>> EXPECTED_OF_DURATIONS =
						JMatchers.containsInAnyOrder( EXPECTED_DURATIONS.toArray( new TripDurations[ EXPECTED_DURATIONS.size() ] ) );
				SiteSessionManager.get().createCheckPoint( "SHIPS_DURATIONS_" + shipCards.get( i ).getShip().name() )
						.assertThat( REASON, ACTUAL_DURATIONS, EXPECTED_OF_DURATIONS );
			}

			/* AND WHEN the user clicks on "View Sailings" */
			int rnd = RandomUtils.nextInt( 0, shipCards.size() - 1 );
			ShipCard selected = shipCards.get( rnd );
			he = compareCruiseShipsPage.getItinerarySubSection( CompareCruiseShipsPage.ItineraryRows.SAILINGS );
			CruiseSearchPage searchPage = compareCruiseShipsPage.clickViewSailings( he, rnd, selected.getShip() );

			/* AND the url query is shipCode=XX */
			REASON = "Validating Search page query";
			String ACTUAL_STR = searchPage.getURL().getQuery();
			Matcher<String> EXPECTED_OF_STR = JMatchers.equalToIgnoringCase( searchPage.getExpectedUrlQuery() );
			SiteSessionManager.get().createCheckPoint( "VIEW_SAILINGS_QUERY_" + selected.getShip().name() )
					.assertThat( REASON, ACTUAL_STR, EXPECTED_OF_STR );

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

			if( method.getName().equals( "compareShips_HoverAction" ) )
			{
				ITestNGMethod testNGMethod = testResult.getMethod();
				if ( testNGMethod.getInvocationCount() == testNGMethod.getCurrentInvocationCount() )
				{
					SiteSessionManager.get().closeSession();
					this.homePage = null;
					this.cruiseShipsPage = null;
					testResult.getTestContext().setAttribute( "compare", true );
				}
				else
				{
					testResult.getTestContext().setAttribute( "compare", false );
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


	//region ShipCompareTest - private functions Section

	private List<ShipCard> add2Ships()
	{
		List<ShipCard> shipCards = cruiseShipsPage.getRandomShipsToCompare(
				CruiseShipsPage.SelectRandom.LESS_TIMES_SELECTED,
				CruiseShipsPage.SelectRandom.OLDER_SELECTED,
				CruiseShipsPage.SelectRandom.NONE );
		for( int i = 0; i < shipCards.size(); i ++ )
		{
			cruiseShipsPage.addShipToComparison( shipCards.get( i ), i );
		}
		this.compareCruiseShipsPage = cruiseShipsPage.compareBanner().clickCompareShips( 2 );
		return shipCards;
	}

	private List<ShipCard> add3Ships()
	{
		List<ShipCard> shipCards = cruiseShipsPage.getRandomShipsToCompare(
				CruiseShipsPage.SelectRandom.LESS_TIMES_SELECTED,
				CruiseShipsPage.SelectRandom.OLDER_SELECTED,
				CruiseShipsPage.SelectRandom.LESS_TIMES_SELECTED );
		for( int i = 0; i < shipCards.size(); i ++ )
		{
			cruiseShipsPage.addShipToComparison( shipCards.get( i ), i );
		}
		this.compareCruiseShipsPage = cruiseShipsPage.compareBanner().clickCompareShips( 3 );
		return shipCards;
	}

	//endregion





}
