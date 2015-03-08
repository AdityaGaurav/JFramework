package com.framework.test.core.accommodations;

import com.framework.BaseTest;
import com.framework.asserts.CheckpointAssert;
import com.framework.driver.event.ExpectedConditions;
import com.framework.driver.event.HtmlCondition;
import com.framework.driver.event.HtmlElement;
import com.framework.driver.exceptions.ApplicationException;
import com.framework.site.config.SiteSessionManager;
import com.framework.site.objects.body.common.BreadcrumbsObject;
import com.framework.site.objects.body.common.SectionBreadcrumbsBarObject;
import com.framework.site.objects.footer.SectionFooterObject;
import com.framework.site.objects.header.enums.LevelOneMenuItem;
import com.framework.site.objects.header.enums.MenuItems;
import com.framework.site.pages.core.ExplorePage;
import com.framework.site.pages.core.HomePage;
import com.framework.site.pages.core.StateRoomsPage;
import com.framework.testing.annotations.*;
import com.framework.utils.datetime.TimeConstants;
import com.framework.utils.error.PreConditions;
import com.framework.utils.matchers.JMatchers;
import com.google.common.collect.Lists;
import com.google.common.collect.Table;
import org.hamcrest.Matcher;
import org.openqa.selenium.WebDriverException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.annotations.*;
import org.testng.xml.XmlTest;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;


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

public class AccommodationsTest extends BaseTest
{

	//region AccommodationsTest - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( AccommodationsTest.class );

	private HomePage homePage = null;

	private StateRoomsPage stateRoomsPage = null;


	//endregion


	//region AccommodationsTest - Constructor Methods Section

	private AccommodationsTest()
	{
		BaseTest.classLogger = LoggerFactory.getLogger( AccommodationsTest.class );
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
				if( ! method.getName().equals( "accommodationsPage_ContainsSameFooterAsHomePage" ) )
				{
					this.homePage.header().headerLinks().hoverOnItem( LevelOneMenuItem.EXPLORE );
					this.stateRoomsPage = ( StateRoomsPage )
							this.homePage.header().navigationAdditional().clickOnMenuItem( LevelOneMenuItem.EXPLORE, MenuItems.ACCOMMODATIONS );
				}
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

	@UserStory ( "PBI-46342" )
	@TestCaseId ( id = 62142 )
	@Steps ( steps = {
			@Step ( number = 1, description = "GIVEN that the user is in Accommodations Landing page /state-rooms.aspx" ),
			@Step ( number = 2, description = "WHEN user observes breadcrumbs on page",
					expectedResults = "THEN user see Home > Explore > Accommodations." )
	} )
	@Test ( description = "Explore Accommodations page. Verify Breadcrumbs Displays",
			enabled = false,
			groups = { "AU", "US", "UK"  }
	)
	public void accommodationsPage_BreadCrumb_Display() throws Exception
	{
		/* GIVEN that the user is in Accommodations Landing page /state-rooms.aspx */
		PreConditions.checkNotNull( this.stateRoomsPage, "State-Rooms page is null, before starting test" );
		try
		{
			/* WHEN the user observes the displayed breadcrumbs */
			BreadcrumbsObject breadcrumbs = stateRoomsPage.breadcrumbsBar().breadcrumbs();

			/* THEN user see Home > Explore > Accommodations */
			String REASON = "Validates breadcrumbs text display order.";
			List<String> ACTUAL_OF_LIST_STR = breadcrumbs.getNames();
			Matcher<Iterable<? extends String>> EXPECTED_OF_LIST_STR = JMatchers.contains( "Home", "Explore", "Accommodations" );
			HtmlElement container = ( ( SectionBreadcrumbsBarObject ) stateRoomsPage.breadcrumbsBar() ).getContainer();
			SiteSessionManager.get().createCheckPoint( container, "BREADCRUMB_ORDERING" )
					.assertThat( REASON, ACTUAL_OF_LIST_STR, EXPECTED_OF_LIST_STR );

			SiteSessionManager.get().assertAllCheckpoints();
		}
		catch ( WebDriverException | AssertionError e )
		{
			throw new ApplicationException( SiteSessionManager.get().getDriver(), e );
		}
	}

	@UserStory ( "PBI-46342" )
	@TestCaseId ( id = 62143 )
	@Steps ( steps = {
			@Step ( number = 1, description = "GIVEN that the user is in Accommodations Landing page /state-rooms.aspx" ),
			@Step ( number = 2, description = "WHEN user observes breadcrumbs on page",
					expectedResults = "THEN the \"Accommodations\" last-child is not clickable." )
	} )
	@Test ( description = "Explore Accommodations page. Breadcrumbs functionality. Accommodations",
			dependsOnMethods = { "accommodationsPage_VerifyBreadCrumbs" },
			enabled = false,
			groups = { "AU", "US", "UK"  }
	)
	public void accommodationsPage_Breadcrumb_Accommodations() throws Exception
	{
		/* GIVEN that the user is in Accommodations Landing page /state-rooms.aspx */
		PreConditions.checkNotNull( this.stateRoomsPage, "State-Rooms page is null, before starting test" );

		try
		{
			/* WHEN the user examine the last item on the breadcrumb */
			BreadcrumbsObject breadcrumbs = stateRoomsPage.breadcrumbsBar().breadcrumbs();

			/* THEN the last item text is "Accommodations" */

			String ACTUAL_STR = breadcrumbs.getLastChildName();
			String REASON = "Validates that last child breadcrumb text is 'Accommodations'";
			Matcher<String> EXPECTED_OF_STR = JMatchers.equalTo( "Accommodations" );
			HtmlElement container = ( ( SectionBreadcrumbsBarObject ) stateRoomsPage.breadcrumbsBar() ).getContainer();
			SiteSessionManager.get().createCheckPoint( container, "BREADCRUMB_LAST_CHILD_TEXT" )
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

	@UserStory ( "PBI-46342" )
	@TestCaseId ( id = 62144 )
	@Steps ( steps = {
			@Step ( number = 1, description = "GIVEN that the user is in Accommodations Landing page /state-rooms.aspx" ),
			@Step ( number = 2, description = "WHEN the clicks \"Explore\" in the displayed breadcrumbs",
					expectedResults = {
							"THEN user redirected to \"Explore\" landing page"
					}
			)
	} )
	@Test ( description = "Explore Accommodations page. Breadcrumbs functionality. Explore",
			dependsOnMethods = { "accommodationsPage_VerifyBreadCrumbs" },
			enabled = false,
			groups = { "AU", "US", "UK"  }
	)
	public void accommodationsPage_Breadcrumb_Explore() throws Exception
	{
		/* GIVEN that the user is in Accommodations Landing page /state-rooms.aspx */
		PreConditions.checkNotNull( this.stateRoomsPage, "State-Rooms page is null, before starting test" );
		try
		{
			/* WHEN user clicks "Explore" in the displayed breadcrumb */

			stateRoomsPage.breadcrumbsBar().breadcrumbs().clickOnItem( "Explore" );
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

	@UserStory ( "PBI-46342" )
	@TestCaseId ( id = 62145 )
	@Steps ( steps = {
			@Step ( number = 1, description = "GIVEN that the user is in Accommodations Landing page /state-rooms.aspx" ),
			@Step ( number = 2, description = "WHEN user clicks \"Home\" in the displayed breadcrumb",
					expectedResults = "THEN user redirects to the relevant locale carnival home page" )
	} )
	@Test ( description = "Explore Accommodations page. Breadcrumbs functionality. Home",
			dependsOnMethods = { "accommodationsPage_VerifyBreadCrumbs" },
			enabled = false,
			groups = { "AU", "US", "UK"  }
	)
	public void accommodationsPage_Breadcrumb_Home() throws Exception
	{

		/* GIVEN that the user is in Ships Landing page /cruise-ships.aspx */
		PreConditions.checkNotNull( this.stateRoomsPage, "State-Rooms page is null, before starting test" );
		try
		{
			/* WHEN user clicks "Home" in the displayed breadcrumb */

			homePage = stateRoomsPage.breadcrumbsBar().breadcrumbs().navigateHome();

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

	@UserStory ( "PBI-46342" )
	@TestCaseId ( id = 62435 )
	@Steps ( steps = {
			@Step ( number = 1, description = "GIVEN that the user is in home page." ),
			@Step ( number = 2, description = "AND user goes to accommodations landing page" ),
			@Step ( number = 3, description = "AND WHEN user compares the footer with home page",
					expectedResults = "THEN the footer should be the same than home page section." ),
			@Step ( number = 4, description = "AND WHEN the user compares the post-footer with home page",
					expectedResults = "THEN the post-footer should be the same than home page section." ),
			@Step ( number = 5, description = "AND WHEN the user clicks on any footer link",
					expectedResults = "THEN the system should redirect the user to the selected target link." ),
	} )
	@Test ( description = "Explore Accommodations page. Footer contains the same as home page footer",
			enabled = true,
			groups = { "AU", "US", "UK"  }
	)
	public void accommodationsPage_ContainsSameFooterAsHomePage() throws Exception
	{
		/* GIVEN that the user is in home page. */
		PreConditions.checkNotNull( this.homePage, "Home page page is null, before starting test" );
		try
		{
			Table<String, String, String> homeTable = homePage.footer().linkList().getInfo();
			Map<String,String> homeSubFooter = homePage.footer().subFooter().getInfo();

			/* AND user goes to accommodations landing page */
			this.homePage.header().headerLinks().hoverOnItem( LevelOneMenuItem.EXPLORE );
			this.stateRoomsPage = ( StateRoomsPage )
					this.homePage.header().navigationAdditional().clickOnMenuItem( LevelOneMenuItem.EXPLORE, MenuItems.ACCOMMODATIONS );
			this.stateRoomsPage.footer().scrollIntoView();
			HtmlElement container = ( ( SectionFooterObject ) this.stateRoomsPage.footer() ).getContainer();

			/* AND WHEN user compares the footer with home page" */
			Table<String, String, String> stateRoomTable = stateRoomsPage.footer().linkList().getInfo();
			Map<String,String> stateRoomSubFooter = stateRoomsPage.footer().subFooter().getInfo();

			/* THEN the footer should be the same than home page section.*/
			for ( String key : homeTable.rowKeySet() )
			{
				String REASON = "Validate home page link section exists in accommodations " + key;
				Boolean ACTUAL_BOOL = stateRoomTable.containsRow( key );
				Matcher<Boolean> EXPECTED_OF_BOOL = JMatchers.is( true );
				SiteSessionManager.get().createCheckPoint( container, "SECTION_EXISTS_" + key.replace( " ", "_" ) )
						.assertThat( REASON, ACTUAL_BOOL, EXPECTED_OF_BOOL );

				Map<String, String> homeMap = homeTable.row( key );
				for( String linkName : homeMap.keySet() )
				{
					REASON = "Validate home page link exists in accommodations -> " + linkName;
					ACTUAL_BOOL = stateRoomTable.containsColumn( linkName );
					EXPECTED_OF_BOOL = JMatchers.is( true );
					SiteSessionManager.get().createCheckPoint( container, "LINK_EXISTS_" + linkName.replace( " ", "_" ).toUpperCase() )
							.assertThat( REASON, ACTUAL_BOOL, EXPECTED_OF_BOOL );

				    REASON = "Validate home page link href is equal to accommodation href " + linkName.replace( " ", "_" ).toUpperCase();
					String ACTUAL_STR = stateRoomTable.get( key, linkName );
					Matcher<String> EXPECTED_OF_STR = JMatchers.endsWithIgnoreCase( homeMap.get( linkName ) );
					SiteSessionManager.get().createCheckPoint( container, "HREF_EQUAL_" + linkName.replace( " ", "_" ).toUpperCase() )
							.assertThat( REASON, ACTUAL_STR, EXPECTED_OF_STR );
				}
			}

			SiteSessionManager.get().assertAllCheckpoints();
		}
		catch ( WebDriverException | AssertionError e )
		{
			throw new ApplicationException( SiteSessionManager.get().getDriver(), e );
		}
	}

	@UserStory ( "PBI-46342" )
	@TestCaseId ( id = 62436 )
	@Issues ( issues = { @Issue ( "63169" ), @Issue ( "72095" ) } )
	@Steps ( steps = {
			@Step ( number = 1, description = "GIVEN that the user is in Accommodations Landing page /state-rooms.aspx" ),
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

		/* GIVEN that the user is in Accommodations Landing page /state-rooms.aspx */
		PreConditions.checkNotNull( this.stateRoomsPage, "State-Rooms page is null, before starting test" );
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
					this.stateRoomsPage = null;
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


	//region AccommodationsTest - Data-Provider Implementation Section

	//endregion

}
