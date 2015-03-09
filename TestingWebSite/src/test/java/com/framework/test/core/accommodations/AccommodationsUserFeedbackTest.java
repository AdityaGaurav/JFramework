package com.framework.test.core.accommodations;

import com.framework.BaseTest;
import com.framework.driver.event.HtmlElement;
import com.framework.driver.exceptions.ApplicationException;
import com.framework.site.config.SiteSessionManager;
import com.framework.site.objects.body.staterooms.UserFeedbackObject;
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
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriverException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
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

public class AccommodationsUserFeedbackTest extends BaseTest
{

	//region AccommodationsTest - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( AccommodationsUserFeedbackTest.class );

	private HomePage homePage = null;

	private StateRoomsPage stateRoomsPage = null;


	//endregion


	//region AccommodationsTest - Constructor Methods Section

	private AccommodationsUserFeedbackTest()
	{
		BaseTest.classLogger = LoggerFactory.getLogger( AccommodationsUserFeedbackTest.class );
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

	@UserStory ( "PBI-58081" )
	@TestCasesIds ( ids = { 66104, 66105, 66106 } )
	@Steps ( steps = {
			@Step ( number = 1, description = "GIVEN that the user is in Accommodations Landing page /state-rooms.aspx" ),
			@Step ( number = 2, description = "WHEN user observes \"User Feedback Section\" section" ),
			@Step ( number = 3, description = "AND WHEN user hover on any + tooltip",
			expectedResults = "THEN the tooltip is open displaying his content" )
	} )
	@Test ( description = "Accommodations. Guest Feedback. Verify user actions on + tooltips",
			enabled = true,
			groups = { "US", "UK" }
	)
	public void accommodationsPage_GuestFeedback_Tooltips() throws Exception
	{
		/* GIVEN that the user is in Accommodations Landing page /state-rooms.aspx */
		PreConditions.checkNotNull( this.stateRoomsPage, "State-Rooms page is null, before starting test" );
		try
		{
			/* WHEN user observes "User Feedback Section" section */
			HtmlElement userFeedback = stateRoomsPage.userFeedback().getContainer();
			userFeedback.scrollIntoView( false );

			/* AND WHEN user hover on any + tooltip" */
			Map<UserFeedbackObject.UserFeedbackTooltip, HtmlElement> tooltips = stateRoomsPage.userFeedback().getToolTips();
			for( UserFeedbackObject.UserFeedbackTooltip tooltipTagName : tooltips.keySet() )
			{
				/* THEN the tooltip is open displaying his content */

				HtmlElement qTip = stateRoomsPage.userFeedback().activateTooltip( tooltipTagName, tooltips.get( tooltipTagName ) );
				String REASON = "Validated that tooltip is displayed";
				Boolean ACTUAL_BOOL = qTip.isDisplayed();
				Matcher<Boolean> EXPECTED_OF_BOOL = JMatchers.is( true );
				SiteSessionManager.get().createCheckPoint( userFeedback, "QTIP_" + tooltipTagName + "_DISPLAYED" )
						.assertThat( REASON, ACTUAL_BOOL, EXPECTED_OF_BOOL );

				HtmlElement span = tooltips.get( tooltipTagName );
				String dataTooltip = span.getWrappedElement().getAttribute( "data-tooltip" );

				REASON = "Validated that tooltip has expected image";
				String innerHtml = qTip.findElement( By.className( "descr-image" ) ).getWrappedElement().getAttribute( "innerHTML" );
				Matcher<String> EXPECTED_OF_STR = JMatchers.containsString( innerHtml );
				SiteSessionManager.get().createCheckPoint( userFeedback, "QTIP_" + tooltipTagName + "_IMG" )
						.assertThat( REASON, dataTooltip, EXPECTED_OF_STR );

				REASON = "Validated that tooltip has expected content";
				String text = qTip.findElement( By.cssSelector( "p.quote" ) ).getWrappedElement().getText();
				EXPECTED_OF_STR = JMatchers.containsString( text );
				SiteSessionManager.get().createCheckPoint( userFeedback, "QTIP_" + tooltipTagName + "_CONTENT" )
						.assertThat( REASON, dataTooltip, EXPECTED_OF_STR );

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
