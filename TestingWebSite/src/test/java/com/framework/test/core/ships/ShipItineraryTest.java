package com.framework.test.core.ships;

import com.framework.BaseTest;
import com.framework.driver.exceptions.ApplicationException;
import com.framework.site.config.SiteSessionManager;
import com.framework.site.data.Enumerators;
import com.framework.site.data.Ships;
import com.framework.site.objects.body.interfaces.Itinerary;
import com.framework.site.objects.body.ships.CycleSlideObject;
import com.framework.site.objects.header.enums.LevelOneMenuItem;
import com.framework.site.objects.header.enums.MenuItems;
import com.framework.site.pages.bookingengine.CruiseSearchPage;
import com.framework.site.pages.bookingengine.FindACruisePage;
import com.framework.site.pages.core.CruiseShipsPage;
import com.framework.site.pages.core.HomePage;
import com.framework.site.pages.core.cruiseships.CruiseShipsDetailsPage;
import com.framework.utils.error.PreConditions;
import com.framework.utils.matchers.JMatchers;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.RandomUtils;
import org.hamcrest.Matcher;
import org.openqa.selenium.WebDriverException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;
import org.testng.xml.XmlTest;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class ShipItineraryTest extends BaseTest
{

	//region ShipItineraryTest - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( ShipItineraryTest.class );

	private HomePage homePage = null;

	private FindACruisePage findACruisePage;

	//endregion


	//region ShipItineraryTest - Constructor Methods Section

	private ShipItineraryTest()
	{
		BaseTest.classLogger = LoggerFactory.getLogger( ShipItineraryTest.class );
	}

	//endregion


	//region ShipItineraryTest - Before Configurations Methods Section

	@BeforeClass ( description = "starts a web driver",
			alwaysRun = true,
			groups = { "US", "UK" }
	)
	public void beforeClass( ITestContext testContext, XmlTest xmlTest ) throws Exception
	{
		killExistingBrowsersOpenedByWebDriver();
		SiteSessionManager.get().startSession();
	}

	@BeforeMethod ( description = "Initiates Home Page",
			enabled = true,
			alwaysRun = true,
			groups = { "US", "UK" }
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
				homePage.header().headerLinks().hoverOnItem( LevelOneMenuItem.PLAN );
				findACruisePage = ( FindACruisePage )
						homePage.header().navigationAdditional().clickOnMenuItem(
								LevelOneMenuItem.PLAN, MenuItems.FIND_A_CRUISE );
			}
		}
		catch ( Throwable e )
		{
			if ( e instanceof WebDriverException || e instanceof AssertionError  )
			{
				throw new ApplicationException( SiteSessionManager.get().getDriver(), e );
			}
			throw new Exception( e.getMessage(), e );
		}
	}

	//endregion


	//region ShipItineraryTest - Test Methods Section

	@Test ( description = "Compare Ships page. verify accordion collapse",
			enabled = true,
			dataProvider = "search",
			groups = { "US", "UK"  }
	)
	public void shipItinerary_ValidateItineraries( Ships ship ) throws Exception
	{
		/* GIVEN user in compare page "/cruise-ships/compare-cruise-ships" with 2 ships. */
		PreConditions.checkNotNull( this.findACruisePage, "Find Cruise Ship page is null, before starting test" );

		try
		{
			findACruisePage.selectCruiseShip( ship );

			CruiseSearchPage cruiseSearchPage = findACruisePage.clickSearchCruises();
			cruiseSearchPage.setShip( ship );
			List<Itinerary> itineraries = cruiseSearchPage.getBestPriceUniqueItineraryList();

			findACruisePage.header().headerLinks().hoverOnItem( LevelOneMenuItem.EXPLORE );
			CruiseShipsPage cruiseShipsPage = ( CruiseShipsPage )
					findACruisePage.header().navigationAdditional().clickOnMenuItem( LevelOneMenuItem.EXPLORE, MenuItems.OUR_SHIPS );
			CruiseShipsDetailsPage cruiseShipsDetailsPage = cruiseShipsPage.selectShip( ship ).clickShipImage();
			cruiseShipsDetailsPage.gotoSection( Enumerators.ShipDetailsSection.ITINERARY );
			Map<String, CycleSlideObject> slidesMap = cruiseShipsDetailsPage.getCycleSlides();

			for ( Itinerary itinerary : itineraries )
			{
				String REASON = "Validate that itinerary exists";
				final String id = itinerary.getTitle().replace( " ", "_" ).replace( "-", "" ).toUpperCase();
				String key = itinerary.getTitle() + " " + itinerary.getDeparturePortAsString();
				Matcher<Map<? extends String, ?>> EXPECTED_OF_MAP =
						JMatchers.hasKey( key );
				SiteSessionManager.get().createCheckPoint( "ITINERARY_KEY_" + id )
						.assertThat( REASON, slidesMap, EXPECTED_OF_MAP );

				if( null != slidesMap.get( key ) )
				{
					REASON = "Validate that itinerary prices are equal";
					BigDecimal ACTUAL_BIG_DEC = slidesMap.get( key ).getPrice();
					Matcher<BigDecimal> EXPECTED_OF_BIG_DEC = JMatchers.equalTo( itinerary.getPrice() );
					SiteSessionManager.get().createCheckPoint( "ITINERARY_PRICE_" + id )
							.assertThat( REASON, ACTUAL_BIG_DEC, EXPECTED_OF_BIG_DEC );
				}
			}
			cruiseShipsDetailsPage.header().headerLinks().hoverOnItem( LevelOneMenuItem.PLAN );
			findACruisePage = ( FindACruisePage )
					cruiseShipsDetailsPage.header().navigationAdditional().clickOnMenuItem(
							LevelOneMenuItem.PLAN, MenuItems.FIND_A_CRUISE );

			SiteSessionManager.get().assertAllCheckpoints();
		}
		catch ( WebDriverException | AssertionError e )
		{
			throw new ApplicationException( SiteSessionManager.get().getDriver(), e );
		}
	}

	//endregion


	//region ShipItineraryTest - After Configurations Methods Section

	@AfterMethod ( description = "", groups = { "US", "UK" }, enabled = true, alwaysRun = false )
	public void afterMethod( ITestResult testResult, XmlTest xmlTest, Method method ) throws Exception
	{
		try
		{
			if ( shouldCloseSession( testResult ) )
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


	//region ShipItineraryTest - DataProvider Methods Section

	@DataProvider ( name = "search", parallel = false )
	public Object[][] topDestinations_DataProvider( ITestContext context, Method method )
	throws Exception
	{
		List<Object[]> data = Lists.newArrayList();

//		Ships[] ships = Ships.values();
//		Set<Ships> selected = Sets.newHashSet();

		data.add( new Object[] { Ships.BREEZE } );
		data.add( new Object[] { Ships.CONQUEST } );
		data.add( new Object[] { Ships.DREAM } );
		data.add( new Object[] { Ships.ECSTASY } );
		data.add( new Object[] { Ships.ELATION } );
		data.add( new Object[] { Ships.FANTASY } );
		data.add( new Object[] { Ships.FASCINATION } );
		data.add( new Object[] { Ships.FREEDOM } );
		data.add( new Object[] { Ships.GLORY } );
		data.add( new Object[] { Ships.IMAGINATION } );
		data.add( new Object[] { Ships.INSPIRATION } );
		data.add( new Object[] { Ships.LEGEND } );
		data.add( new Object[] { Ships.LIBERTY } );
		data.add( new Object[] { Ships.MAGIC } );
		data.add( new Object[] { Ships.MIRACLE } );
		data.add( new Object[] { Ships.PARADISE } );
		data.add( new Object[] { Ships.PRIDE } );
		data.add( new Object[] { Ships.SENSATION } );
		data.add( new Object[] { Ships.SPLENDOR } );
		data.add( new Object[] { Ships.SUNSHINE } );
		data.add( new Object[] { Ships.TRIUMPH } );
		data.add( new Object[] { Ships.VALOR } );
		data.add( new Object[] { Ships.VICTORY } );
		data.add( new Object[] { Ships.VISTA } );

		return data.toArray( new Object[ data.size() ][] );

	}

	private Ships selectShip( Set<Ships> alreadySelected, Ships[] ships )
	{
		Ships selected = null;
		do
		{
			int randomShip = RandomUtils.nextInt( 1, ships.length - 1 );
			selected = ships[ randomShip ];
		}
		while ( alreadySelected.contains( selected ) );

		return selected;
	}

	//endregion
}
