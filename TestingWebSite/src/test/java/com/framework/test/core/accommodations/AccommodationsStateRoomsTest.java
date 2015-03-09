package com.framework.test.core.accommodations;

import com.framework.BaseTest;
import com.framework.driver.event.HtmlElement;
import com.framework.driver.exceptions.ApplicationException;
import com.framework.site.config.SiteSessionManager;
import com.framework.site.objects.body.staterooms.StateRoomCategoriesObject;
import com.framework.site.objects.header.enums.LevelOneMenuItem;
import com.framework.site.objects.header.enums.MenuItems;
import com.framework.site.pages.core.HomePage;
import com.framework.site.pages.core.StateRoomsPage;
import com.framework.testing.annotations.Step;
import com.framework.testing.annotations.Steps;
import com.framework.testing.annotations.TestCaseId;
import com.framework.testing.annotations.UserStory;
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
import java.util.Collection;
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

public class AccommodationsStateRoomsTest extends BaseTest
{

	//region AccommodationsTest - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( AccommodationsStateRoomsTest.class );

	private HomePage homePage = null;

	private StateRoomsPage stateRoomsPage = null;


	//endregion


	//region AccommodationsTest - Constructor Methods Section

	private AccommodationsStateRoomsTest()
	{
		BaseTest.classLogger = LoggerFactory.getLogger( AccommodationsStateRoomsTest.class );
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
		testContext.setAttribute( "CloseLightbox", "NONE" );
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

	@UserStory ( "PBI-66741" )
	@TestCaseId( id = 66430 )
	@Steps ( steps = {
			@Step ( number = 1, description = "GIVEN that the user is in Accommodations Landing page /state-rooms.aspx" ),
			@Step ( number = 2, description = "WHEN user examine the Staterooms/Accommodations area",
					expectedResults = { "none of the categories should be selected",
									    "the widget has title and introduction" } ),
			@Step ( number = 3, description = "AND WHEN user clicks on an accommodation category type",
					expectedResults = "THEN the subset category should be active" )
	} )
	@Test ( description = "Accommodations. StateRoom Categories. Categories",
			enabled = true,
			groups = { "US", "UK"  }
	)
	public void accommodationsPage_StateRoomCategories_DefaultState() throws Exception
	{
		/* GIVEN that the user is in Accommodations Landing page /state-rooms.aspx */
		PreConditions.checkNotNull( this.stateRoomsPage, "State-Rooms page is null, before starting test" );
		try
		{
			/* WHEN user clicks on any tile displayed in "What's Your Room Type" section */

			HtmlElement container = stateRoomsPage.stateRoomCategories().getContainer();
			StateRoomCategoriesObject categories = stateRoomsPage.stateRoomCategories();
			container.scrollIntoView();

			String REASON = "Validate that none category is selected by default.";
			List<HtmlElement> ACTUAL_ELEM = categories.getActiveSubsets();
			Matcher<Collection<HtmlElement>> EXPECTED_OF_COLL = JMatchers.emptyCollectionOf( HtmlElement.class );
			SiteSessionManager.get().createCheckPoint( container, "DEFAULT_NONE_SELECTED" )
					.assertThat( REASON, ACTUAL_ELEM, EXPECTED_OF_COLL );

			REASON = "Validate that state room type widget has title";
			Boolean ACTUAL_BOOL = categories.hasTitle();
			Matcher<Boolean> EXPECTED_OF_BOOL = JMatchers.is( true );
			SiteSessionManager.get().createCheckPoint( container, "HAS_TITLE" )
					.assertThat( REASON, ACTUAL_BOOL, EXPECTED_OF_BOOL );

			REASON = "Validate that state room type widget has intro";
			ACTUAL_BOOL = categories.hasIntro();
			EXPECTED_OF_BOOL = JMatchers.is( true );
			SiteSessionManager.get().createCheckPoint( container, "HAS_INTRO" )
					.assertThat( REASON, ACTUAL_BOOL, EXPECTED_OF_BOOL );

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
