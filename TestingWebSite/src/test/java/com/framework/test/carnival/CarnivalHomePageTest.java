package com.framework.test.carnival;

import com.framework.BaseTest;
import com.framework.asserts.JAssertions;
import com.framework.asserts.JSoftAssertions;
import com.framework.driver.objects.Link;
import com.framework.driver.utils.PreConditions;
import com.framework.jreporter.TestReporter;
import com.framework.site.config.InitialPage;
import com.framework.site.data.TestEnvironment;
import com.framework.site.objects.header.LevelOneMenuItem;
import com.framework.site.objects.header.MenuItems;
import com.framework.site.objects.header.interfaces.Header;
import com.framework.site.pages.core.*;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.math.NumberUtils;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;
import org.testng.xml.XmlTest;

import java.lang.reflect.Method;
import java.net.URL;
import java.util.List;
import java.util.Locale;


public class CarnivalHomePageTest extends BaseTest
{
	//region HomePageTest - Variables Declaration and Initialization Section.

	private HomePage homePage = null;

	//endregion


	//region HomePageTest - Constructor Methods Section

	private CarnivalHomePageTest()
	{
		super();
	}

	//endregion


	//region HomePageTest - Before Methods Section

	@BeforeClass (
			description = "starts a web driver",
			alwaysRun = true,
			groups = { "US", "UK" }
	)
	public void beforeClass( ITestContext testContext, XmlTest xmlTest ) throws Exception
	{
		try
		{
			//
		}
		catch ( Throwable e )
		{
			TestReporter.error( e.getLocalizedMessage() );
			throw new Exception( e.getMessage(), e );
		}
	}

	@BeforeMethod (
			description = "",
			enabled = true,
			groups = { "US", "UK" }
	)
	public void beforeMethod( ITestContext testContext, XmlTest xmlTest, Method method, Object[] parameters ) throws Exception
	{
		try
		{
			final String ERR_MSG1 = "ITestContext.getAttribute( \"locale\" ) is null.";
			final String ERR_MSG2 = "ITestContext.getAttribute( \"environment\" ) is null.";
			final String ERR_MSG3 = "ITestContext.getAttribute( \"browser\" ) is null.";
			final String LOG_MSG_FORMAT = "Invoking home page for locale: <{}>, environment: <{}> and browser: <{}>";

			if( null == this.homePage )
			{
				this.homePage = InitialPage.getInstance().getHomePage();
				Locale locale = ( Locale ) Validate.notNull( testContext.getAttribute( "locale" ), ERR_MSG1 );
				TestEnvironment env =  ( TestEnvironment ) Validate.notNull( testContext.getAttribute( "environment" ), ERR_MSG2 );
				String browser =  ( String ) Validate.notNull( testContext.getAttribute( "browser" ), ERR_MSG3 );
				TestReporter.step( 1F, LOG_MSG_FORMAT, locale.getCountry(), env.name(), browser );
			}
			else
			{
				TestReporter.debug( "this.homePage still active." );
			}
		}
		catch (  Throwable e )
		{
			TestReporter.error( e.getLocalizedMessage() );
			throw new Exception( e.getMessage(), e );
		}
	}

	//endregion


	//region HomePageTest - Test Methods Section

	@Test (
			description = "TC-ID:40488",
			enabled = true,
			groups = { "US" }
	)
	public void HomePage_US_VerifySubNavigationActionsAreWorkingFine_ID40488( ITestContext testContext ) throws Exception
	{
		LevelOneMenuItem levelOneMenuItem = LevelOneMenuItem.LEARN;
		JSoftAssertions softAssertions = new JSoftAssertions( InitialPage.getInstance().getEventDriver() );

		try
		{
			PreConditions.checkNotNull( this.homePage, "Home page is null, before starting test" );
			Locale locale = ( Locale ) Validate.notNull( testContext.getAttribute( "locale" ) );

			Header header = homePage.header();

			TestReporter.step( 2F, "User goes to home page (carnival.com) and hovers/tabs over \"Learn\" TAB" );

			Header.HeaderLinks headerLinks = header.headerLinks();
			headerLinks.hoverOnMenuItem( levelOneMenuItem );
			TestReporter.checkpoint( "ADDITIONAL_LINKS_VISIBLE", "the navigationAdditional links section is visible" );
			Header.NavigationAdditional navigationAdditional = header.navigationAdditional();
			JAssertions.assertThat( navigationAdditional.isDisplayed() ).isEqualTo( true );

			TestReporter.step( 2.1F, "Validating that  elements should not display more than {<max.menu.sub-items>} items" );

			TestReporter.checkpoint( "NO_MORE_THAN_6_ITEMS", "child elements should not display more than 6 items" );
			List<Link> links = navigationAdditional.getChildMenuItems( levelOneMenuItem );
			String maxMenuItems = appContext.getMessage( "max.menu.sub-items", null, locale );

			/* validating numeric entry before conversion */

			Validate.isTrue( NumberUtils.isNumber( maxMenuItems ), "key <max.menu.sub-items> must be a number, however is %s", maxMenuItems );
			TestReporter.debug( "Expected max.menu.sub-items is -> <{}> ", maxMenuItems );
			softAssertions.assertThat( links.size() ).isLessThanOrEqualTo( NumberUtils.createInteger( maxMenuItems ) );

			// ------------------------------------------------------------------------------------------------------------ //

			TestReporter.step( 3F, "User clicks on \"Why Carnival?\"" );
			TestReporter.checkpoint( "WHY_CARNIVAL_URL", "checking that \"Why Carnival?\" link redirects to correct url" );
			CruisingPage cruisingPage = ( CruisingPage ) navigationAdditional.selectMenuItem( links, MenuItems.WHY_CARNIVAL );

			// ------------------------------------------------------------------------------------------------------------ //

			TestReporter.step( 4F, "User clicks on \"What's it Like?\"" );

			/**
			 *  since the test doesn't want to handle the Vacation with Kids feature, it will return back
			 *  because of that, all previous elements will throw an StaleElementReferenceException when
			 *  trying to access them again. so they need to be reissued again.
			 */
			cruisingPage.navigate().back();
			links = navigationAdditional.getChildMenuItems( levelOneMenuItem );
			headerLinks.hoverOnMenuItem( levelOneMenuItem );
			TestReporter.checkpoint( "WHAT_IS_LIKE_URL", "checking that \"What's it Like?\" link redirects to correct url" );
			What2ExpectPage what2ExpectPage = ( What2ExpectPage ) navigationAdditional.selectMenuItem( links, MenuItems.WHATS_IT_LIKE );

			// ------------------------------------------------------------------------------------------------------------ //

			TestReporter.step( 5F, "User clicks on \"Where can I go?\"" );

			what2ExpectPage.navigate().back();
			links = navigationAdditional.getChildMenuItems( levelOneMenuItem );
			headerLinks.hoverOnMenuItem( levelOneMenuItem );
			TestReporter.checkpoint( "WHERE_CAN_I_GO_URL", "checking that \"Where can I go?\" link redirects to correct url" );
			CruiseDestinationsAndPortsPage destinationsPage =
					( CruiseDestinationsAndPortsPage ) navigationAdditional.selectMenuItem( links, MenuItems.WHERE_CAN_I_GO );

			// ------------------------------------------------------------------------------------------------------------ //

			TestReporter.step( 6F, "User clicks on \"How much is it?\"" );

			destinationsPage.navigate().back();
			links = homePage.header().navigationAdditional().getChildMenuItems( levelOneMenuItem );
			headerLinks.hoverOnMenuItem( levelOneMenuItem );
			TestReporter.checkpoint( "HOW_MUCH_IS_IT_URL", "checking that \"How much is it?\" link redirects to correct url" );
			CruiseCostPage costPage = ( CruiseCostPage ) navigationAdditional.selectMenuItem( links, MenuItems.HOW_MUCH_IS_IT );

			// ------------------------------------------------------------------------------------------------------------ //

			TestReporter.step( 7F, "User clicks on \"Help me decide.\"" );

			costPage.navigate().back();
			links = navigationAdditional.getChildMenuItems( levelOneMenuItem );
			headerLinks.hoverOnMenuItem( levelOneMenuItem );
			TestReporter.checkpoint( "HELP_ME_DECIDE_URL", "checking that \"Help me decide?\" link redirects to correct url" );
			VacationPlannerPage planner = ( VacationPlannerPage ) navigationAdditional.selectMenuItem( links, MenuItems.HELP_ME_DECIDE );
			planner.header().headerBranding().clickOnLogo();

			softAssertions.assertAll();
		}
		catch ( Throwable e )
		{
			TestReporter.error( e.getMessage() );
			throw new Exception( e.getMessage(), e );
		}
	}

	@Test (
			description = "TC-ID:40488",
			enabled = true,
			groups = { "UK" }
	)
	public void HomePage_UK_VerifySubNavigationActionsAreWorkingFine_ID40488( ITestContext testContext ) throws Exception
	{
		LevelOneMenuItem levelOneMenuItem = LevelOneMenuItem.LEARN;
		JSoftAssertions softAssertions = new JSoftAssertions( InitialPage.getInstance().getEventDriver() );

		try
		{
			PreConditions.checkNotNull( this.homePage, "Home page is null, before starting test" );
			Locale locale = ( Locale ) Validate.notNull( testContext.getAttribute( "locale" ) );

			Header header = homePage.header();

			TestReporter.step( 2F, "User goes to home page (carnival.com) and hovers/tabs over \"Learn\" TAB" );

			Header.HeaderLinks headerLinks = header.headerLinks();
			headerLinks.hoverOnMenuItem( levelOneMenuItem );
			TestReporter.checkpoint( "ADDITIONAL_LINKS_VISIBLE", "the navigationAdditional links section is visible" );
			Header.NavigationAdditional navigationAdditional = header.navigationAdditional();
			JAssertions.assertThat( navigationAdditional.isDisplayed() ).isEqualTo( true );

			TestReporter.step( 2.1F, "Validating that  elements should not display more than {<max.menu.sub-items>} items" );

			TestReporter.checkpoint( "NO_MORE_THAN_6_ITEMS", "child elements should not display more than 6 items" );
			List<Link> links = navigationAdditional.getChildMenuItems( levelOneMenuItem );
			String maxMenuItems = appContext.getMessage( "max.menu.sub-items", null, locale );

			/* validating numeric entry before conversion */

			Validate.isTrue( NumberUtils.isNumber( maxMenuItems ), "key <max.menu.sub-items> must be a number, however is %s", maxMenuItems );
			TestReporter.debug( "Expected max.menu.sub-items is -> <{}> ", maxMenuItems );
			softAssertions.assertThat( links.size() ).isLessThanOrEqualTo( NumberUtils.createInteger( maxMenuItems ) );

			// ------------------------------------------------------------------------------------------------------------ //

			TestReporter.step( 3F, "User clicks on \"Caribbean\"" );
			TestReporter.checkpoint( "CARIBBEAN_URL", "checking that \"Caribbean\" link redirects to correct url" );
			UKCaribbeanPage caribbeanPage = ( UKCaribbeanPage ) navigationAdditional.selectMenuItem( links, MenuItems.CARIBBEAN );

			// ------------------------------------------------------------------------------------------------------------ //

			TestReporter.step( 4F, "User clicks on \"What's Included?\"" );

			/**
			 *  since the test doesn't want to handle the Vacation with Kids feature, it will return back
			 *  because of that, all previous elements will throw an StaleElementReferenceException when
			 *  trying to access them again. so they need to be re-assigned again.
			 */
			caribbeanPage.navigate().back();
			links = navigationAdditional.getChildMenuItems( levelOneMenuItem );
			headerLinks.hoverOnMenuItem( levelOneMenuItem );
			TestReporter.checkpoint( "WHATS_INCLUDED_URL", "checking that \"What's Included?\" link redirects to correct url" );
			BeginnersGuidePage beginnersPage = ( BeginnersGuidePage ) navigationAdditional.selectMenuItem( links, MenuItems.WHATS_INCLUDED );

			// ------------------------------------------------------------------------------------------------------------ //

			TestReporter.step( 5F, "User clicks on \"On the Ship\"" );

			beginnersPage.scrollToTop();
			links = navigationAdditional.getChildMenuItems( levelOneMenuItem );
			headerLinks.hoverOnMenuItem( levelOneMenuItem );
			navigationAdditional.selectMenuItem( links, MenuItems.ON_THE_SHIP );

			TestReporter.checkpoint( "ON_THE_SHIP_URL", "checking that \"On the Ship\" link redirects to correct url" );
			URL currentURL = new URL( beginnersPage.getCurrentUrl() );
			softAssertions.assertThat( currentURL.getPath() ).containsIgnoringCase( BeginnersGuidePage.URL_PATH );

			TestReporter.checkpoint( "ON_THE_SHIP_URL_QUERY", "Checking that the url query matches" );
			softAssertions.assertThat( currentURL.getQuery() ).isEqualToIgnoringCase( BeginnersGuidePage.ON_SHIP_QUERY );

			// ------------------------------------------------------------------------------------------------------------ //

			TestReporter.step( 6F, "User clicks on \"Shore Excursions\"" );

			beginnersPage.scrollToTop();
			headerLinks.hoverOnMenuItem( levelOneMenuItem );
			navigationAdditional.selectMenuItem( links, MenuItems.SHORE_EXCURSIONS );

			TestReporter.checkpoint( "SHORE_EXCURSIONS_URL", "checking that \"Shore Excursions\" link redirects to correct url" );
			currentURL = new URL( beginnersPage.getCurrentUrl() );
			softAssertions.assertThat( currentURL.getPath() ).containsIgnoringCase( BeginnersGuidePage.URL_PATH );

			TestReporter.checkpoint( "SHOREX_URL_QUERY", "Checking that the url query matches" );
			softAssertions.assertThat( currentURL.getQuery() ).isEqualToIgnoringCase( BeginnersGuidePage.SHOREX_QUERY );

			// ------------------------------------------------------------------------------------------------------------ //

			TestReporter.step( 7F, "User clicks on \"Destinations\"" );

			beginnersPage.scrollToTop();
			links = navigationAdditional.getChildMenuItems( levelOneMenuItem );
			headerLinks.hoverOnMenuItem( levelOneMenuItem );
			navigationAdditional.selectMenuItem( links, MenuItems.DESTINATIONS );

			TestReporter.checkpoint( "DESTINATIONS_URL", "checking that \"Destinations\" link redirects to correct url" );
			currentURL = new URL( beginnersPage.getCurrentUrl() );
			softAssertions.assertThat( currentURL.getPath() ).containsIgnoringCase( BeginnersGuidePage.URL_PATH );

			TestReporter.checkpoint( "DESTINATIONS_QUERY_QUERY", "Checking that the url query matches" );
			softAssertions.assertThat( currentURL.getQuery() ).isEqualToIgnoringCase( BeginnersGuidePage.DESTINATIONS_QUERY );

			beginnersPage.header().headerBranding().clickOnLogo();

			softAssertions.assertAll();
		}
		catch ( Throwable e )
		{
			TestReporter.error( e.getMessage() );
			throw new Exception( e.getMessage(), e );
		}
	}



	//endregion

	//region HomePageTest - After Methods Section

	@AfterMethod (
			description = "",
			enabled = true,
			alwaysRun = true,
			groups = { "US", "UK" }
	)
	public void afterMethod( ITestResult testResult, XmlTest xmlTest, Method method ) throws Exception
	{
		try
		{
			if( ! method.getName().equals( "homePage_ChangeLocales_Test" ) )
			{
				InitialPage.getInstance().quitAllDrivers();
			}
		}
		catch (  Throwable e )
		{
			TestReporter.error( e.getLocalizedMessage() );
			throw new Exception( e.getMessage(), e );
		}
	}

	@AfterClass ( description = "quit all drivers", alwaysRun = true, groups = { "US", "UK" } )
	public void afterClass() throws Exception
	{
		try
		{
			InitialPage.getInstance().quitAllDrivers();
		}
		catch (  Throwable e )
		{
			TestReporter.error( e.getLocalizedMessage() );
			throw new Exception( e.getMessage(), e );
		}
	}

	//endregion

}