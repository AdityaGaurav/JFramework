package com.framework.test;

import com.framework.BaseTest;
import com.framework.asserts.JAssertions;
import com.framework.asserts.JSoftAssertions;
import com.framework.driver.objects.Link;
import com.framework.driver.utils.PreConditions;
import com.framework.driver.utils.ui.ListWebElementUtils;
import com.framework.driver.utils.ui.WaitUtil;
import com.framework.jreporter.TestReporter;
import com.framework.site.config.InitialPage;
import com.framework.site.data.TestEnvironment;
import com.framework.site.exceptions.NoSuchMenuItemException;
import com.framework.site.objects.footer.interfaces.Footer;
import com.framework.site.objects.header.LevelOneMenuItem;
import com.framework.site.objects.header.MenuItems;
import com.framework.site.objects.header.interfaces.Header;
import com.framework.site.pages.core.*;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.math.NumberUtils;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;
import org.testng.xml.XmlTest;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Locale;


public class HomePageTest extends BaseTest
{
	//region HomePageTest - Variables Declaration and Initialization Section.

	private HomePage homePage = null;


	//endregion

	//region HomePageTest - Constructor Methods Section

	private HomePageTest()
	{
		super();
	}

	//endregion


	//region HomePageTest - Before Methods Section

	@BeforeClass (
			description = "starts a web driver",
			alwaysRun = true,
			groups = { "US", "UK", "AU" }
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
			groups = { "US", "UK", "AU" }
	)
	public void beforeMethod( ITestContext testContext, XmlTest xmlTest, Method method, Object[] parameters ) throws Exception
	{
		try
		{
			TestReporter.info( "Executing test <\"{}\"> id: <\"{}\">", method.getName(), this.getTestId( method.getName(), testContext ) );

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
		catch ( Throwable e )
		{
			TestReporter.error( e.getLocalizedMessage() );
			throw new Exception( e.getMessage(), e );
		}
	}

	//endregion


	//region HomePageTest - Test Methods Section

	/* ************************************************************************************************

	 TEST[00001]

	 Verifies url and title using assert-j and custom WebDriverAssertions class

	 ************************************************************************************************ */

	@Test (
			description = "TC-ID:00001 Verifies url and title using assert-j and custom WebDriverAssertions class",
			enabled = true,
			groups = { "US", "UK", "AU" }
	)
	public void homePage_InitialState_TitleAndUrl_Test( ITestContext testContext ) throws Exception
	{
		final String ERR_MSG1 = "ITestContext.getAttribute( \"locale\" ) is null.";

		try
		{
			PreConditions.checkNotNull( this.homePage, "Home page is null, before starting test" );
			Locale locale = ( Locale ) Validate.notNull( testContext.getAttribute( "locale" ), ERR_MSG1 );

			final String EXPECTED_URL = InitialPage.getInstance().getInitialUrl();
			final String EXPECTED_TITLE = appContext.getMessage( "home.page.title", null, locale );
			List<String> titleParts = Splitter.on( ", " ).splitToList( EXPECTED_TITLE );

			TestReporter.checkpoint( "URL_EQ",
					"Asserting home page currentUrl isEqualToIgnoringCase to {}", EXPECTED_URL );
			JAssertions.assertThat( homePage.getCurrentUrl() ).isEqualToIgnoringCase( EXPECTED_URL );

			TestReporter.checkpoint( "URL_CONTAINS",
					"Asserting home page currentUrl containsIgnoringCase <b>'carnival'</b> and startsWith <b>'http'</b>" );
			JAssertions.assertThat( homePage.getCurrentUrl() ).containsIgnoringCase( "carnival" ).startsWith( "http" );

			if ( locale.equals( Locale.US ) )
			{
				String testContains = Joiner.on( ", " ).join( titleParts.get( 1 ), titleParts.get( 2 ), titleParts.get( 3 ) );
				String notContains = Joiner.on( ", " ).join( "UK", "Asia", "Hawaii" );

				TestReporter.checkpoint( "US_TITLE_CONTAINS_NOT_CONTAINS",
						"Asserting home page '{}' title contains \"{}\", " +
								"and does not contains \"{}\", and matches regex:<b>'Cruises.*'</b> ",
						locale.getDisplayCountry(), testContains, notContains );

				JAssertions.assertThat( homePage.getTitle() ).
						doesNotContain( "UK" ).
						contains( titleParts.get( 1 ) ).
						doesNotContain( "Asia" ).
						contains( titleParts.get( 2 ) ).
						doesNotContain( "Hawaii" ).
						contains( titleParts.get( 3 ) ).
						matches( "Cruises.*" );
			}
			else if ( locale.equals( Locale.UK ) )
			{
				String testContains = "UK";
				String notContains = Joiner.on( ", " ).join( "Caribbean", "Australia", "Asia" );

				TestReporter.checkpoint( "UK_TITLE_CONTAINS_NOT_CONTAINS",
						"Asserting home page '{}' title contains \"{}\", " +
								"and does not contains \"{}\", and matches regex:<b>'Cruises.*'</b> ",
						locale.getDisplayCountry(), testContains, notContains );

				JAssertions.assertThat( homePage.getTitle() ).
						doesNotContain( "Caribbean" ).
						contains( "UK" ).
						doesNotContain( "Asia" ).
						doesNotContain( "Australia" );
			}
			else
			{
				String testContains = Joiner.on( ", " ).join( titleParts.get( 1 ), titleParts.get( 2 ), titleParts.get( 3 ) );
				String notContains = Joiner.on( ", " ).join( "Europe", "Asia", "Caribbean" );

				TestReporter.checkpoint( "AU_TITLE_CONTAINS_NOT_CONTAINS",
						"Asserting home page '{}' title contains \"{}\", " +
								"and does not contains \"{}\", and matches regex:<b>'Cruises.*'</b> ",
						locale.getDisplayCountry(), testContains, notContains );

				JAssertions.assertThat( homePage.getTitle() ).
						doesNotContain( "Europe" ).
						contains( titleParts.get( 1 ) ).
						doesNotContain( "Asia" ).
						contains( "Tasmania" ).
						doesNotContain( "Caribbean" ).
						contains( "Australia" ).
						matches( "Cruises.*" );
			}
		}
		catch ( Throwable e )
		{
			TestReporter.error( e.getMessage() );
			throw new Exception( e.getMessage(), e );
		}
	}

	/* ************************************************************************************************

	 TEST[00002]: ( US,UK,AU )

	 Verifies the page Header Module initial state.

	 ************************************************************************************************ */

	@Test (
			description = "TC-ID:00002." +
				"Verifies the Carnival header home page initial state." +
			    "1. Message-Bar section is displayed on UK and hidden for US and AU" +
			    "2. Notification-Bar section is hidden" +
				"3. Header-Branding section is displayed" +
				"3.1 Top Destinations item is displayed on US and UK, however not in AU" +
				"3.2 Currency Selector item is displayed only in AU" +
			    "4. Header-Subscribe section is hidden" +
				"5. Header-Links section is displayed and contains expected links" +
				"6. Additional-Links section is hidden",
			enabled = true,
			groups = { "US", "UK", "AU" }
	)
	public void homePage_InitialState_Header_Test( ITestContext testContext ) throws Exception
	{
		JSoftAssertions softAssertions = new JSoftAssertions( InitialPage.getInstance().getEventDriver() );

		try
		{
			PreConditions.checkNotNull( this.homePage, "Home page is null, before starting test" );
			Locale locale = ( Locale ) Validate.notNull( testContext.getAttribute( "locale" ) );

			Header header = homePage.header();

			// ----------------------------------------------------------------------------------------------------------------|
			// --- STEP 1.1 : Message-Bar displayed on UK, however not in US and AU -------------------------------------------|
			// ----------------------------------------------------------------------------------------------------------------|

			TestReporter.step( 1.1F, "Validate \"Message-Bar\" is hidden for US and AU, however displayed for UK." );

			if( locale.equals( Locale.UK ) )
			{
				TestReporter.checkpoint( "MESSAGE_BAR_DISPLAYED", "Assert that \"Message-Bar\" is displayed on UK" );
				softAssertions.assertThat( header.messageBar().isDisplayed() ).isEqualTo( true );
			}
			else
			{
				TestReporter.checkpoint( "MESSAGE_BAR_HIDDEN", "Assert that \"Message-Bar\" is hidden on US and AU" );
				softAssertions.assertThat( header.messageBar().isDisplayed() ).isEqualTo( false );
			}

			// ----------------------------------------------------------------------------------------------------------------|
			// --- STEP 1.2 : Notification-Bar is not displayed for non-logged-in user ----------------------------------------|
			// ----------------------------------------------------------------------------------------------------------------|

			TestReporter.step( 1.2F, "Validate \"Notification-Bar\" is hidden." );
			TestReporter.checkpoint( "NOTIFICATION_BAR_HIDDEN", "Assert that \"Notification-Bar\" is hidden" );
			softAssertions.assertThat( header.notificationBar().isDisplayed() ).isEqualTo( false );

			// ----------------------------------------------------------------------------------------------------------------|
			// --- STEP 1.3 : Header-Branding should be displayed on all locales                                            ---|
			// ---            1. on AU the Top-Destination drop down does not exists.                                       ---|
			// ---			  2. on AU the Currency-Selector exists.				                                        ---|
			// ----------------------------------------------------------------------------------------------------------------|

			Header.HeaderBranding headerBranding = header.headerBranding();
			TestReporter.step( 1.3F, "Validate \"Header-Branding\" is displayed." );
			TestReporter.checkpoint( "HEADER_BRANDING_DISPLAYED", "Assert that \"Header-Branding\" is displayed" );
			softAssertions.assertThat( headerBranding.isDisplayed() ).isEqualTo( false );

			TestReporter.step( 1.31F, "Validate \"Top Destinations\" exists and is displayed on US and UK, however not in AU." );

			if( locale.equals( Locale.UK ) || locale.equals( Locale.US ) )
			{
				Header.HeaderBranding.TopDestinations topDestinations = ( Header.HeaderBranding.TopDestinations ) headerBranding;
				TestReporter.checkpoint( "TOP_DESTINATIONS_DISPLAYED", "Assert that \"Top Destinations\" is displayed" );
				softAssertions.assertThat( topDestinations.isDisplayed() ).isEqualTo( true );
			}
			else
			{
				TestReporter.checkpoint( "TOP_DESTINATIONS_NOT_EXISTS", "Assert that \"Top Destinations\" item does not exists." );
				softAssertions.assertThat( headerBranding.hasTopDestinations() ).isEqualTo( false );
			}

			TestReporter.step( 1.32F, "Validate \"Currency Selection\" item exists and displayed on AU only." );

			if( locale.equals( Locale.UK ) || locale.equals( Locale.US ) )
			{
				TestReporter.checkpoint( "CURRENCY_SELECTOR_NOT_EXISTS", "Assert that \"Currency selector\" does not exists." );
				softAssertions.assertThat( headerBranding.hasCurrencySelector() ).isEqualTo( false );
			}
			else
			{
				Header.HeaderBranding.CurrencySelector currSelector = ( Header.HeaderBranding.CurrencySelector ) headerBranding;
				TestReporter.checkpoint( "CURRENCY_SELECTOR_EXISTS", "Assert that \"Currency selector\" exists and displayed." );
				softAssertions.assertThat( currSelector.isDisplayed() ).isEqualTo( true );
			}

			// ----------------------------------------------------------------------------------------------------------------|
			// --- STEP 1.4 : Header-Subscribe is not displayed ---------------------------------------------------------------|
			// ----------------------------------------------------------------------------------------------------------------|

			TestReporter.step( 1.4F, "Validate \"Header-Subscribe\" section is hidden." );
			TestReporter.checkpoint( "HEADER_SUBSCRIBE_HIDDEN", "Assert that \"Header Subscribe\" is hidden" );
			softAssertions.assertThat( header.subscribe().isDisplayed() ).isEqualTo( false );

			// ----------------------------------------------------------------------------------------------------------------|
			// --- STEP 1.5 : Header links is displayed and contains 4 top-level links ----------------------------------------|
			// ----------------------------------------------------------------------------------------------------------------|

			TestReporter.step( 1.5F, "Validate \"Header-Links\" section is displayed and contains expected links." );
			TestReporter.checkpoint( "HEADER_LINKS_DISPLAYED", "Assert that \"Header Links\" is displayed." );
			softAssertions.assertThat( header.headerLinks().isDisplayed() ).isEqualTo( true );
			TestReporter.checkpoint( "HEADER_LINKS_TOP_LEVEL", "Assert that \"Header Links\" contains expected links." );
			String expectedLinks = appContext.getMessage( "top.level.links", null, null );
			String actualLinks = Joiner.on( "," ).join( header.headerLinks().getLinkNames() );
			softAssertions.assertThat( actualLinks ).isEqualToIgnoringCase( expectedLinks );

			// ----------------------------------------------------------------------------------------------------------------|
			// --- STEP 1.6 : Header additional links ( level 2 ) are hidden --------------------------------------------------|
			// ----------------------------------------------------------------------------------------------------------------|

			TestReporter.step( 1.6F, "Validate \"Additional-Links\" section is hidden at initial state.." );
			TestReporter.checkpoint( "ADDITIONAL_LINKS_HIDDEN", "Assert that \"Additional-Links\" are hidden." );
			softAssertions.assertThat( header.navigationAdditional().isDisplayed() ).isEqualTo( false );

		}
		catch ( Throwable e )
		{
			TestReporter.error( e.getMessage() );
			throw new Exception( e.getMessage(), e );
		}
	}

	/* ************************************************************************************************

	 TEST[00003]: ( US,UK,AU )

	 Verifies the Carnival home page footer initial state

	 ************************************************************************************************ */

	@Test (
			description = "TC-ID:00003." +
					      "Verifies the Carnival home page footer initial state",
			enabled = true,
			groups = { "US", "UK", "AU", "IN-DEV" }
	)
	public void homePage_InitialState_Footer_Test( ITestContext testContext ) throws Exception
	{
		JSoftAssertions softAssertions = new JSoftAssertions( InitialPage.getInstance().getEventDriver() );

		try
		{
			PreConditions.checkNotNull( this.homePage, "Home page is null, before starting test" );

			Footer footer = homePage.footer();

			// ----------------------------------------------------------------------------------------------------------------|
			// --- STEP 1.1 : Link List is Displayed  -------------------------------------------------------------------------|
			// ----------------------------------------------------------------------------------------------------------------|


			TestReporter.step( 1.1F, "Validate \"Link-List\" is displayed." );
			TestReporter.checkpoint( "LINK_LIST_DISPLAYED", "Assert that \"Link-List\" is displayed." );
			softAssertions.assertThat( footer.linkList().isDisplayed() ).isTrue();

			// ----------------------------------------------------------------------------------------------------------------|
			// --- STEP 1.2 : Sub-Footer is Displayed  ------------------------------------------------------------------------|
			// ----------------------------------------------------------------------------------------------------------------|

			TestReporter.step( 1.2F, "Validate \"Sub-Footer\" is displayed." );
			TestReporter.checkpoint( "SUB_FOOTER_DISPLAYED", "Assert that \"Sub-Footer\" is displayed." );
			softAssertions.assertThat( footer.subFooter().isDisplayed() ).isTrue();

			// ----------------------------------------------------------------------------------------------------------------|
			// --- STEP 1.3 : Zero-Footer is Displayed  -----------------------------------------------------------------------|
			// ----------------------------------------------------------------------------------------------------------------|

			TestReporter.step( 1.3F, "Validate \"Zero-Footer\" is displayed." );
			TestReporter.checkpoint( "ZERO_FOOTER_DISPLAYED", "Assert that \"Zero-Footer\" is displayed." );
			softAssertions.assertThat( footer.zeroFooter().isDisplayed() ).isTrue();
		}
		catch ( Throwable e )
		{
			TestReporter.error( e.getMessage() );
			throw new Exception( e.getMessage(), e );
		}
	}

	/* ************************************************************************************************

	 TEST[00004]: ( US,UK,AU )

	 Validates functionality of the top-level and 2nd level menus

	 ************************************************************************************************ */

	@Test (
			description = "TC-ID:00004." +
					      "Validates functionality of the top-level and 2nd level menus",  //todo: extend description
			enabled = true,
			dataProvider = "topLevels",
			groups = { "US" }
	)
	public void homePage_SubMenu_US_Test( LevelOneMenuItem levelOneMenuItem, ITestContext testContext ) throws Exception
	{
		JSoftAssertions softAssertions = new JSoftAssertions( InitialPage.getInstance().getEventDriver() );

		try
		{
			PreConditions.checkNotNull( this.homePage, "Home page is null, before starting test" );
			Locale locale = ( Locale ) Validate.notNull( testContext.getAttribute( "locale" ) );
			String maxMenuItems = appContext.getMessage( "max.menu.sub-items", null, locale );
			String key = String.format( "%s.menu.items", levelOneMenuItem.name().toLowerCase() );
			final String EXPECTED_NAMES = appContext.getMessage( key, null, locale );

			Header header = homePage.header();
			Header.HeaderLinks headerLinks = header.headerLinks();
			Header.NavigationAdditional navigationAdditional = header.navigationAdditional();

			/* retrieve a list of top-level associated links */

			TestReporter.step( 2F, "User goes to home page (carnival.com) and hovers/tabs over \"{}\" TAB", levelOneMenuItem.getTitle() );
			headerLinks.hoverOnMenuItem( levelOneMenuItem );

			/* asserting navigation additional is displayed */

			JAssertions.assertThat( navigationAdditional.isDisplayed() ).isEqualTo( true );

			TestReporter.checkpoint( "NAVIGATION_ADDITIONAL_DISPLAYED", "Validate all second level menus are visible" );
			List<Link> links = navigationAdditional.getChildMenuItems( levelOneMenuItem );

			TestReporter.checkpoint( "NO_MORE_THAN_6_ITEMS", "child elements should not display more than 6 items" );

			Validate.isTrue( NumberUtils.isNumber( maxMenuItems ), "key <max.menu.sub-items> must be a number, however is %s", maxMenuItems );
			TestReporter.debug( "Expected max.menu.sub-items is -> <{}> ", maxMenuItems );
			softAssertions.assertThat( links.size() ).isLessThanOrEqualTo( NumberUtils.createInteger( maxMenuItems ) );

			TestReporter.checkpoint( "ITEMS_NAMES", "child menu items names matching" );
			List<String> names = navigationAdditional.getChildMenuItemsNames( links );
			String actualNames = Joiner.on( "," ).join( names );
			JAssertions.assertThat( EXPECTED_NAMES ).isEqualTo( actualNames );


			/* asserting all links are visible */

			TestReporter.checkpoint( "LINK_VISIBLE", "Asserting all links are visible" );
			WebDriverWait wait5 = InitialPage.getInstance().wait5();
			List<WebElement> elements = ListWebElementUtils.convertToLinkToWebElement( links );
			JAssertions.assertWaitThat( wait5 ).matchesCondition( WaitUtil.visibilityOfAll( elements, true ) );
			//JAssertions.assertThat( links ).haveExactly( links.size(), Conditions.linkIsVisible );

			float counter = 0.1F;
			for( Link link : links )
			{
				TestReporter.step( 3F + counter, "User hovers over \"{}\" menu. image change", levelOneMenuItem.getTitle() );
				String menuText = link.getText();
				TestReporter.debug( "Searching for menu item <'{}'>", menuText );
				for( MenuItems item : MenuItems.values() )
				{
					if( item.getTitle().equals( menuText ) )
					{
						navigationAdditional.hoverMenuItem( links, item );
					}
				}

				counter += 0.1F;
			}

			/**
			 * 	URL Redirection part
			 */

			switch ( levelOneMenuItem )
			{
				case LEARN:
				{
					homePage.header().headerLinks().hoverOnMenuItem( levelOneMenuItem );
					links = homePage.header().navigationAdditional().getChildMenuItems( levelOneMenuItem );
					TestReporter.step( 4F, "User clicks on \"Why Carnival?\"" );
					TestReporter.checkpoint( "WHY_CARNIVAL_URL", "checking that \"Why Carnival?\" link redirects to correct url" );
					CruisingPage cruisingPage = ( CruisingPage ) navigationAdditional.selectMenuItem( links, MenuItems.WHY_CARNIVAL );
					cruisingPage.navigate().back();

					TestReporter.step( 5F, "User clicks on \"What's it Like?\"" );
					homePage.header().headerLinks().hoverOnMenuItem( levelOneMenuItem );
					links = homePage.header().navigationAdditional().getChildMenuItems( levelOneMenuItem );
					TestReporter.checkpoint( "WHAT_IS_LIKE_URL", "checking that \"What's it Like?\" link redirects to correct url" );
					What2ExpectPage what2ExpectPage = ( What2ExpectPage ) navigationAdditional.selectMenuItem( links, MenuItems.WHATS_IT_LIKE );
					what2ExpectPage.navigate().back();

					TestReporter.step( 6F, "User clicks on \"Where can I go?\"" );
					homePage.header().headerLinks().hoverOnMenuItem( levelOneMenuItem );
					links = homePage.header().navigationAdditional().getChildMenuItems( levelOneMenuItem );
					TestReporter.checkpoint( "WHERE_CAN_I_GO_URL", "checking that \"Where can I go?\" link redirects to correct url" );
					CruiseDestinationsAndPortsPage destinationsPage =
							( CruiseDestinationsAndPortsPage ) navigationAdditional.selectMenuItem( links, MenuItems.WHERE_CAN_I_GO );
					destinationsPage.navigate().back();

					TestReporter.step( 7F, "User clicks on \"How much is it?\"" );
					homePage.header().headerLinks().hoverOnMenuItem( levelOneMenuItem );
					links = homePage.header().navigationAdditional().getChildMenuItems( levelOneMenuItem );
					TestReporter.checkpoint( "HOW_MUCH_IS_IT_URL", "checking that \"How much is it?\" link redirects to correct url" );
					CruiseCostPage costPage = ( CruiseCostPage ) navigationAdditional.selectMenuItem( links, MenuItems.HOW_MUCH_IS_IT );
					costPage.navigate().back();

					TestReporter.step( 8F, "User clicks on \"Help me decide.\"" );
					homePage.header().headerLinks().hoverOnMenuItem( levelOneMenuItem );
					links = homePage.header().navigationAdditional().getChildMenuItems( levelOneMenuItem );
					TestReporter.checkpoint( "HELP_ME_DECIDE_URL", "checking that \"Help me decide?\" link redirects to correct url" );
					VacationPlannerPage planner = ( VacationPlannerPage ) navigationAdditional.selectMenuItem( links, MenuItems.HELP_ME_DECIDE );
					planner.header().headerBranding().clickOnLogo();
					break;
				}

				default:
				{
					final String MSG = "The menu item '" + levelOneMenuItem.getTitle() + "' does not exists";
					throw new NoSuchMenuItemException( InitialPage.getInstance().getEventDriver(), MSG );
				}

			}

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

	@AfterMethod ( description = "", groups = { "US", "UK", "AU" } )
	public void afterMethod( ITestResult testResult, XmlTest xmlTest, Method method ) throws Exception
	{
		try
		{
			if( this.homePage != null )
			{
				InitialPage.getInstance().quitAllDrivers();
				this.homePage = null;
			}
		}
		catch (  Throwable e )
		{
			TestReporter.error( e.getLocalizedMessage() );
			throw new Exception( e.getMessage(), e );
		}
	}

	@AfterClass ( description = "quit all drivers", alwaysRun = true )
	public void afterClass() throws Exception
	{
		try
		{
			if( this.homePage != null )
			{
				InitialPage.getInstance().quitAllDrivers();
				this.homePage = null;
			}
		}
		catch (  Throwable e )
		{
			TestReporter.error( e.getLocalizedMessage() );
			throw new Exception( e.getMessage(), e );
		}
	}

	//endregion


	//region HomePageTest - Data Provider Methods Section

	@DataProvider ( name = "topLevels", parallel = false, indices = { 1 })
	public Object[][] topLevelsDP( ITestContext context, Method method )
	throws Exception
	{
		//TestReporter.startConfig( "DataProvider", "localesDP" );

		List<Object[]> data = Lists.newArrayList();

		try
		{
			data.add( new Object[] { LevelOneMenuItem.LEARN } );
			data.add( new Object[] { LevelOneMenuItem.EXPLORE } );
			data.add( new Object[] { LevelOneMenuItem.PLAN } );
			data.add( new Object[] { LevelOneMenuItem.MANAGE } );
			return data.toArray( new Object[ data.size() ][] );
		}
		finally
		{
			//TestReporter.endConfig( "DataProvider", "localesDP" );
		}

	}


	//endregion

}