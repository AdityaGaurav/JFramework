package com.framework.test.core.accommodations;

import com.framework.BaseTest;
import com.framework.driver.event.HtmlElement;
import com.framework.driver.exceptions.ApplicationException;
import com.framework.site.config.SiteSessionManager;
import com.framework.site.objects.body.interfaces.CallOut;
import com.framework.site.objects.body.staterooms.DidYouKnowObject;
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

public class AccommodationsDidYouKnowTest extends BaseTest
{

	//region AccommodationsTest - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( AccommodationsDidYouKnowTest.class );

	private HomePage homePage = null;

	private StateRoomsPage stateRoomsPage = null;


	//endregion


	//region AccommodationsTest - Constructor Methods Section

	private AccommodationsDidYouKnowTest()
	{
		BaseTest.classLogger = LoggerFactory.getLogger( AccommodationsDidYouKnowTest.class );
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

	@UserStory ( "PBI-58085" )
	@TestCaseId ( id = 66069 )
	@Steps ( steps = {
			@Step ( number = 1, description = "GIVEN that the user is in Accommodations Landing page /state-rooms.aspx" ),
			@Step ( number = 2, description = "WHEN user observes \"did you know\" section",
					expectedResults = "THEN session did you know look & feel matches the attached mock-up" )
	} )
	@Test ( description = "Accommodations. Did you know? User verifies front-end look and feel",
			enabled = false,
			groups = { "AU", "US", "UK"  }
	)
	public void accommodationsPage_DidYouKnow_LookAndFeel() throws Exception
	{
		/* GIVEN that the user is in Accommodations Landing page /state-rooms.aspx */
		PreConditions.checkNotNull( this.stateRoomsPage, "State-Rooms page is null, before starting test" );
		try
		{
			/* WHEN user observes "did you know" section */
			DidYouKnowObject didYouKnow = stateRoomsPage.didYouKnow();
			HtmlElement container = didYouKnow.getContainer();
			container.scrollIntoView( false );

			/* THEN session did you know look & feel matches the attached mock-up */
			String REASON = "Validates that intro has title.";
			Boolean ACTUAL_BOOL = didYouKnow.hasIntroTitle();
			Matcher<Boolean> EXPECTED_OF_BOOL = JMatchers.is( true );
			SiteSessionManager.get().createCheckPoint( container, "INTRO_TITLE" )
					.assertThat( REASON, ACTUAL_BOOL, EXPECTED_OF_BOOL );

			REASON = "Validates that intro has brief";
			ACTUAL_BOOL = didYouKnow.hasIntroBrief();
			EXPECTED_OF_BOOL = JMatchers.is( true );
			SiteSessionManager.get().createCheckPoint( container, "INTRO_BRIEF" )
					.assertThat( REASON, ACTUAL_BOOL, EXPECTED_OF_BOOL );

			List<CallOut> callOutList = didYouKnow.getCallOuts();
			int index = 0;
			for( CallOut callOut : callOutList )
			{
				REASON = "Validates that call-out index " + index + " has title";
				ACTUAL_BOOL = callOut.hasTitle();
				EXPECTED_OF_BOOL = JMatchers.is( true );
				SiteSessionManager.get().createCheckPoint( container, "CALL_OUT_TITLE_INDEX_" + index ++ )
						.assertThat( REASON, ACTUAL_BOOL, EXPECTED_OF_BOOL );

				REASON = "Validates that call-out index " + index + " has image";
				ACTUAL_BOOL = callOut.hasImage();
				EXPECTED_OF_BOOL = JMatchers.is( true );
				SiteSessionManager.get().createCheckPoint( container, "CALL_OUT_IMG_INDEX_" + index ++ )
						.assertThat( REASON, ACTUAL_BOOL, EXPECTED_OF_BOOL );

				REASON = "Validates that call-out index " + index + " has paragraph";
				ACTUAL_BOOL = callOut.hasParagraph();
				EXPECTED_OF_BOOL = JMatchers.is( true );
				SiteSessionManager.get().createCheckPoint( container, "CALL_OUT_PARA_INDEX_" + index ++ )
						.assertThat( REASON, ACTUAL_BOOL, EXPECTED_OF_BOOL );
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
