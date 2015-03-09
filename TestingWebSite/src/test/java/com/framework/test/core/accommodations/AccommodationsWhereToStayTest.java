package com.framework.test.core.accommodations;

import com.framework.BaseTest;
import com.framework.driver.event.HtmlElement;
import com.framework.driver.exceptions.ApplicationException;
import com.framework.site.config.SiteSessionManager;
import com.framework.site.data.Enumerators;
import com.framework.site.objects.body.staterooms.WhereToStayObject;
import com.framework.site.objects.header.enums.LevelOneMenuItem;
import com.framework.site.objects.header.enums.MenuItems;
import com.framework.site.pages.core.HomePage;
import com.framework.site.pages.core.StateRoomsPage;
import com.framework.testing.annotations.Step;
import com.framework.testing.annotations.Steps;
import com.framework.testing.annotations.TestCasesIds;
import com.framework.testing.annotations.UserStory;
import com.framework.utils.datetime.Sleeper;
import com.framework.utils.error.PreConditions;
import com.framework.utils.matchers.JMatchers;
import com.google.common.collect.Lists;
import org.hamcrest.Matcher;
import org.openqa.selenium.WebDriverException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;
import org.testng.xml.XmlTest;

import java.lang.reflect.Method;
import java.util.List;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.test.core
 *
 * Name   : AccommodationsTest 
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-03-06 
 *
 * Time   : 10:41
 *
 */

public class AccommodationsWhereToStayTest extends BaseTest
{

	//region AccommodationsTest - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( AccommodationsWhereToStayTest.class );

	private HomePage homePage = null;

	private StateRoomsPage stateRoomsPage = null;


	//endregion


	//region AccommodationsTest - Constructor Methods Section

	private AccommodationsWhereToStayTest()
	{
		BaseTest.classLogger = LoggerFactory.getLogger( AccommodationsWhereToStayTest.class );
	}

	//endregion


	//region AccommodationsTest - Before Configurations Methods Section

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
				this.stateRoomsPage = ( StateRoomsPage )
						this.homePage.header().navigationAdditional().clickOnMenuItem( LevelOneMenuItem.EXPLORE, MenuItems.ACCOMMODATIONS );
			}
			else if ( StateRoomsPage.isStateRoomsPage() )
			{
				List<String> groups = Lists.newArrayList( testContext.getIncludedGroups() );
				this.stateRoomsPage = new StateRoomsPage();
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


	//region AccommodationsTest - Tests Methods Section

	@UserStory ( "PBI-58084" )
	@TestCasesIds ( ids = { 66741, 66094 } )
	@Steps ( steps = {
			@Step ( number = 1, description = "GIVEN that the user is in Accommodations Landing page /state-rooms.aspx" ),
			@Step ( number = 2, description = "WHEN user observes \"Where To Stay\" section",
					expectedResults = "THEN section has title and description" ),
			@Step ( number = 2, description = "WHEN user observes the decks section",
					expectedResults = "THEN \"Lower Deck\" section is selected by default" )
	} )
	@Test ( description = "Accommodations. Where To Stay. Titles",
			enabled = true,
			groups = { "AU", "US", "UK"  }
	)
	public void accommodationsPage_WhereToStay_Titles() throws Exception
	{
		/* GIVEN that the user is in Accommodations Landing page /state-rooms.aspx */
		PreConditions.checkNotNull( this.stateRoomsPage, "State-Rooms page is null, before starting test" );
		try
		{
			/* WHEN user observes "Where Should I Stay" section */
			WhereToStayObject whereToStay = stateRoomsPage.whereToStay();
			HtmlElement container = whereToStay.getContainer();
			container.scrollIntoView( false );

			/* THEN section has title and description */
			String REASON = "Validates that Where Should I Stay has title.";
			Boolean ACTUAL_BOOL = whereToStay.hasTitle();
			Matcher<Boolean> EXPECTED_OF_BOOL = JMatchers.is( true );
			SiteSessionManager.get().createCheckPoint( container, "TITLE" )
					.assertThat( REASON, ACTUAL_BOOL, EXPECTED_OF_BOOL );

			REASON = "Validates that Where Should I Stay has description.";
			ACTUAL_BOOL = whereToStay.hasDescription();
			EXPECTED_OF_BOOL = JMatchers.is( true );
			SiteSessionManager.get().createCheckPoint( container, "DESCRIPTION" )
					.assertThat( REASON, ACTUAL_BOOL, EXPECTED_OF_BOOL );

			REASON = "Validates that default deck is lower deck.";
			Enumerators.Decks ACTUAL_DECK = whereToStay.getActiveDeck();
			Matcher<Enumerators.Decks> EXPECTED_OF_DECKS = JMatchers.is( Enumerators.Decks.LOWER_DECK );
			SiteSessionManager.get().createCheckPoint( container, "DEFAULT_DECK" )
					.assertThat( REASON, ACTUAL_DECK, EXPECTED_OF_DECKS );

			SiteSessionManager.get().assertAllCheckpoints();
		}
		catch ( WebDriverException | AssertionError e )
		{
			throw new ApplicationException( SiteSessionManager.get().getDriver(), e );
		}
	}


	@UserStory ( "PBI-58084" )
	@TestCasesIds ( ids = { 66069 } )
	@Steps ( steps = {
			@Step ( number = 1, description = "GIVEN that the user is in Accommodations Landing page /state-rooms.aspx" ),
			@Step ( number = 2, description = "WHEN user observes \"Where To Stay\" section" ),
			@Step ( number = 3, description = "AND user selects any deck",
					expectedResults = { "THEN the deck description should be visible",
						"AND the control deck should be active.",
						"AND the ship image deck should be highlighted" } )
	} )
	@Test ( description = "Accommodations. Where To Stay. Decks",
			enabled = true,
			dependsOnMethods = { "accommodationsPage_WhereToStay_Titles" },
			groups = { "US", "UK"  }
	)
	public void accommodationsPage_WhereToStay_Decks() throws Exception
	{
		/* GIVEN that the user is in Accommodations Landing page /state-rooms.aspx */
		PreConditions.checkNotNull( this.stateRoomsPage, "State-Rooms page is null, before starting test" );
		try
		{
			/* WHEN user observes "Where Should I Stay" section */
			WhereToStayObject whereToStay = stateRoomsPage.whereToStay();
			HtmlElement container = whereToStay.getContainer();
			container.scrollIntoView( false );

			/* AND user selects any deck */
			Enumerators.Decks[] decks = Enumerators.Decks.values();
			for( Enumerators.Decks deck : decks )
			{
			 	whereToStay.clickOnDeck( deck );
				HtmlElement tooltip = whereToStay.getDeckTooltip();
				/* THEN the deck description should be visible */
				String REASON = "Validates that deck description is visible for deck " + deck.name();
				Boolean ACTUAL_BOOL = tooltip.isDisplayed();
				Matcher<Boolean> EXPECTED_OF_BOOL = JMatchers.is( true );
				SiteSessionManager.get().createCheckPoint( container, "DECK_TOOL_TIP_" + deck.name() )
						.assertThat( REASON, ACTUAL_BOOL, EXPECTED_OF_BOOL );

				REASON = "Validates that deck description is not empty " + deck.name();
				int ACTUAL_INT = tooltip.getText().length();
				Matcher<Integer> EXPECTED_OF_INT = JMatchers.greaterThan( 0 );
				SiteSessionManager.get().createCheckPoint( container, "DECK_TOOL_TIP_LEN_" + deck.name() )
						.assertThat( REASON, ACTUAL_INT, EXPECTED_OF_INT );

				/* AND the control deck should be active. */
				REASON = "Validates the control deck should be active for deck " + deck.name();
				Enumerators.Decks ACTUAL_DECK = whereToStay.getActiveDeck();
				Matcher<Enumerators.Decks> EXPECTED_OF_DECKS = JMatchers.is( deck );
				SiteSessionManager.get().createCheckPoint( container, "ACTIVE_" + deck.name() )
						.assertThat( REASON, ACTUAL_DECK, EXPECTED_OF_DECKS );

				/* AND the ship image deck should be highlighted */
				REASON = "Validates the ship deck should be active for deck " + deck.name();
				ACTUAL_DECK = whereToStay.getActiveShipDeck();
				EXPECTED_OF_DECKS = JMatchers.is( deck );
				SiteSessionManager.get().createCheckPoint( container, "ACTIVE_SHIP_" + deck.name() )
						.assertThat( REASON, ACTUAL_DECK, EXPECTED_OF_DECKS );

				Sleeper.pauseFor( 200 );

			}

			SiteSessionManager.get().assertAllCheckpoints();
		}
		catch ( WebDriverException | AssertionError e )
		{
			throw new ApplicationException( SiteSessionManager.get().getDriver(), e );
		}
	}

	//endregion


	//region AccommodationsTest -  After Configurations Methods Section

	@AfterMethod ( description = "", groups = { "US", "UK", "AU" }, enabled = true, alwaysRun = false )
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


	//region AccommodationsTest - Data-Provider Implementation Section

	//endregion

}
