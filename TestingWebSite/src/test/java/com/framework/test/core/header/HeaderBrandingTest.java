package com.framework.test.core.header;

import com.framework.BaseTest;
import com.framework.driver.event.HtmlElement;
import com.framework.driver.exceptions.ApplicationException;
import com.framework.site.config.SiteProperty;
import com.framework.site.config.SiteSessionManager;
import com.framework.site.data.Destinations;
import com.framework.site.objects.header.interfaces.Header;
import com.framework.site.pages.core.CruiseDealsPage;
import com.framework.site.pages.core.HomePage;
import com.framework.site.pages.core.cruiseto.CruiseToDestinationPage;
import com.framework.testing.annotations.Step;
import com.framework.testing.annotations.Steps;
import com.framework.testing.annotations.TestCaseId;
import com.framework.utils.error.PreConditions;
import com.framework.utils.matchers.JMatchers;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.hamcrest.Matcher;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;
import org.testng.xml.XmlTest;

import java.lang.reflect.Method;
import java.net.URL;
import java.util.List;
import java.util.Locale;


public class HeaderBrandingTest extends BaseTest
{

	//region HeaderBrandingTest - Variables Declaration and Initialization Section.

	private HomePage homePage = null;

	//endregion


	//region HeaderBrandingTest - Constructor Methods Section

	private HeaderBrandingTest()
	{
		BaseTest.classLogger = LoggerFactory.getLogger( HeaderBrandingTest.class );
	}

	//endregion


	//region HeaderBrandingTest - Before Configurations Methods Section

	@BeforeClass ( description = "starts a web driver",
			alwaysRun = true,
			groups = { "US", "UK", "AU" }
	)
	public void beforeClass( ITestContext testContext, XmlTest xmlTest ) throws Exception
	{
		killExistingBrowsersOpenedByWebDriver();
		SiteSessionManager.get().startSession();
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


	//region HomePageTest - Test Methods Section


	//region Test id 1 - Verify Initial URL and Title

	@TestCaseId( id = 1 )
	@Steps ( steps = {
			@Step( number = 1, description = "WHEN user navigates to main page",
				   expectedResults = {
						"THEN url matches the current locale and environment",
						"AND the site title, matches the locale defined title",
				   })
	})
	@Test ( description = "Testing the Locale Common Header Branding initial state and functionality",
			enabled = true,
			groups = { "US", "UK", "AU" }
	)
	public void homePage_InitialState_Title_And_Url( ITestContext testContext ) throws Exception
	{
		//logger.info( "Starting executing method < '{}' > for < '{}' >", testContext.getName(), testContext.getIncludedGroups() );

		PreConditions.checkNotNull( this.homePage, "Home page is null, before starting test" );

		/* THEN url matches the current locale and environment */

		final String REASON1 = "Asserting home page url based locale and environment";
		final Matcher<String> EXPECTED_URL = JMatchers.is( SiteSessionManager.get().getBaseUrl().toExternalForm() );
		final String ACTUAL_URL = homePage.getCurrentUrl();
		SiteSessionManager.get().createCheckPoint( "VALIDATE_URL" ).assertThat( REASON1, ACTUAL_URL, EXPECTED_URL );

		/* AND the site title, matches the locale */

		final String REASON2 = "Asserting home page title match the locale defined title";
		//final Matcher<String> EXPECTED_TITLE = JMatchers.is( ( String ) SiteProperty.HOME_PAGE_TITLE.fromContext() );
		//final String ACTUAL_TITLE = homePage.getTitle();
		//SiteSessionManager.get().createCheckPoint( "VALIDATE_TITLE" ).assertThat( REASON2, ACTUAL_TITLE, EXPECTED_TITLE );

		SiteSessionManager.get().assertAllCheckpoints();

	}

	//endregion


	//region Test id 2 - Change locale

	//endregion


	//region Test id 3 - Header-Branding common parts, initial state and functionality

	@TestCaseId( id = 3 )
	@Steps ( steps = {
			@Step(  number = 1, description = "GIVEN user navigates to main page" ),
			@Step(  number = 2, description = "WHEN examining the header bar, zero navigation section",
					expectedResults = { "THEN the phone number matches the defined locale phone number",
							"AND the displayed flag displayed in the header matches the current locale",
							"AND WHEN the locale is 'US' or 'UK', \"Top Destinations\" should be displayed, otherwise not",
							"OR WHEN if the locale is AU', the \"Currency Selector\" should be displayed, otherwise not",
					} ),
			@Step( number = 3, description = "AND WHEN user clicks back on the \"Subscribe and Save\" link",
					expectedResults = { "THEN the \"Header Subscribe\" section should be hidden." }),
			@Step( number = 4, description = "THEN user clicks on \"Your cruise deals\"",
					expectedResults = { "user redirects to \"/cruise-deals.aspx\" page" } ),
			@Step( number = 5, description = "WHEN user clicks on the company LOGO on the left upper corner.",
					expectedResults = "user redirects back to home page." ),
			@Step( number = 6, description = "WHEN user clicks on the locale country flag.",
					expectedResults = { "THEN a drop down containing country names is displayed.",
										"AND the list contains the expected country names" } ),
			@Step( number = 7, description = "WHEN user clicks back on the locale country flag icon.",
					expectedResults = { "THEN the drop down countries list is hidden back." } ),
	} )
	@Test ( description = "Testing the Header-Branding common part initial state and functionality",
			enabled = true,
			groups = { "US", "UK", "AU" }
	)
	public void homePage_InitialState_Header_Branding( ITestContext testContext ) throws Exception
	{
		/* GIVEN user navigates to main page */
		PreConditions.checkNotNull( this.homePage, "Home page is null, before starting test" );
		try
		{

			/* WHEN examining the header bar, zero navigation section */
			Header.HeaderBranding headerBranding = homePage.header().headerBranding();
			HtmlElement container = homePage.header().getContainer();

			/* THEN the phone number matches the defined locale phone number */
			String REASON = "Asserting the phone number matches the defined locale phone number";
			String ACTUAL_STR = headerBranding.getLocalePhoneNumber();
			Matcher<String> EXPECTED_STR = JMatchers.is( ( String ) SiteProperty.HEADER_PHONE_NUMBER.fromContext() );
			SiteSessionManager.get().createCheckPoint( container, "VALIDATE_PHONE" )
					.assertThat( REASON, ACTUAL_STR, EXPECTED_STR );

			/* AND the displayed flag in the header matches the current locale */
			REASON = "Asserting that the flag displayed in the header matches the current locale";
			Locale ACTUAL_LOCALE = homePage.header().headerBranding().getDisplayedLocale();
			Matcher<Locale> EXPECTED_LOCALE = JMatchers.is( SiteSessionManager.get().getCurrentLocale() );
			SiteSessionManager.get().createCheckPoint( container, "VALIDATE_DISPLAYED_FLAG" )
					.assertThat( REASON, ACTUAL_LOCALE, EXPECTED_LOCALE );

			/* AND WHEN the locale is 'US' or 'UK', "Top Destinations" should be displayed, otherwise not */

			if ( SiteSessionManager.get().getCurrentLocale().equals( Locale.UK )
					|| SiteSessionManager.get().getCurrentLocale().equals( Locale.US ) )
			{
				REASON = "Asserting that \"Top Destinations\" should be displayed";
				boolean ACTUAL_BOOL = headerBranding.hasTopDestinations();
				Matcher<Boolean> EXPECTED_OF_BOOL = JMatchers.is( true );
				SiteSessionManager.get().createCheckPoint( container, "TOP_DESTINATIONS_DISPLAYED" )
						.assertThat( REASON, ACTUAL_BOOL, EXPECTED_OF_BOOL );

				REASON = "Asserting that \"Currency Selector\" should not be displayed";
				ACTUAL_BOOL = headerBranding.hasCurrencySelector();
				EXPECTED_OF_BOOL = JMatchers.is( false );
				SiteSessionManager.get().createCheckPoint( container, "CURRENCY_SELECTOR_NOT_DISPLAYED" )
						.assertThat( REASON, ACTUAL_BOOL, EXPECTED_OF_BOOL );
			}
			else
			{
				REASON = "Asserting that \"Top Destinations\" should be displayed";
				Matcher<Boolean> EXPECTED_OF_BOOL = JMatchers.is( false );
				boolean ACTUAL_BOOL = headerBranding.hasTopDestinations();
				SiteSessionManager.get().createCheckPoint( "TOP_DESTINATIONS_NOT_DISPLAYED" )
						.assertThat( REASON, ACTUAL_BOOL, EXPECTED_OF_BOOL );

				REASON = "Asserting that \"Currency Selector\" should not be displayed";
				EXPECTED_OF_BOOL = JMatchers.is( true );
				ACTUAL_BOOL = homePage.header().headerBranding().hasCurrencySelector();
				SiteSessionManager.get().createCheckPoint( container, "CURRENCY_SELECTOR_IS_DISPLAYED" )
						.assertThat( REASON, ACTUAL_BOOL, EXPECTED_OF_BOOL );
			}

		 	/* AND WHEN user clicks back on the "Subscribe and Save" link */
			headerBranding.clickSubscribeAndSave();

			final String REASON5 = "Asserting that \"Header Subscribe\" should be displayed";
			final ExpectedCondition<WebElement> CONDITION = null;//visibilityBy( Header.HeaderBranding.ROOT_BY, true );
			//		SiteSessionManager.get().createCheckPoint( "HEADER_SUBSCRIBE_VISIBLE" )
			//				.assertWaitThat( REASON5, TimeConstants.FIVE_SECONDS, CONDITION );

		 /* WHEN user clicks again on the "Subscribe and Save" link THEN "Header Subscribe" section should be hidden. */

			headerBranding.clickSubscribeAndSave();
			final String REASON6 = "Asserting that \"Header Subscribe\" should be hidden back";
			//final ExpectedCondition<WebElement> CONDITION1 = visibilityBy( Header.HeaderBranding.ROOT_BY, false );
			//		SiteSessionManager.get().createCheckPoint( "HEADER_SUBSCRIBE_HIDDEN" )
			//				.assertWaitThat( REASON6, TimeConstants.FIVE_SECONDS, CONDITION1 );

		/* WHEN user clicks on "Your cruise deals" THEN user redirects to "/cruise-deals.aspx" page */

			CruiseDealsPage dealsPage = headerBranding.clickYourCruiseDeals();
			final String REASON7 = "Asserting that user redirected to \"/cruise-deals.aspx\" page";
			final Matcher<String> EXPECTED_URL = JMatchers.containsString( "/cruise-deals.aspx" );
			final String ACTUAL_URL = dealsPage.getCurrentUrl();
			SiteSessionManager.get().createCheckPoint( "URL_CRUISE_DEALS" )
					.assertThat( REASON7, ACTUAL_URL, EXPECTED_URL );

		/* WHEN user clicks on the LOGO, THEN user redirects back to home page */

			this.homePage = dealsPage.header().headerBranding().clickOnLogo();
			final String REASON8 = "Asserting that user redirected back to home page";
			final Matcher<URL> EXPECTED_HOME_URL = JMatchers.is( SiteSessionManager.get().getBaseUrl() );
			final URL ACTUAL_HOME_URL = new URL( homePage.getCurrentUrl() );
			SiteSessionManager.get().createCheckPoint( "URL_BACK_HOME" )
					.assertThat( REASON8, ACTUAL_HOME_URL, EXPECTED_HOME_URL );

		/* AND user clicks on the locale country flag. */

			headerBranding = this.homePage.header().headerBranding();

			final String REASON9 = "Asserting that country list is hidden before clicking on the flag icon";
			final Matcher<Boolean> EXPECTED_LIST_STATE1 = JMatchers.is( false );
			final boolean ACTUAL_LIST_STATE1 = headerBranding.isLocalesListOpened();
			SiteSessionManager.get().createCheckPoint( "LOCALE_LIST_INIT_HIDDEN" )
					.assertThat( REASON9, ACTUAL_LIST_STATE1, EXPECTED_LIST_STATE1 );

			List<String> locales = headerBranding.openLocalesList();

		/* THEN a drop down containing country names is displayed. */

			final String REASON10 = "Asserting that country list is displayed after clicking on the flag icon for first time";
			final Matcher<Boolean> EXPECTED_LIST_STATE2 = JMatchers.is( true );
			final boolean ACTUAL_LIST_STATE2 = headerBranding.isLocalesListOpened();
			SiteSessionManager.get().createCheckPoint( "LOCALE_LIST_DISPLAYED" )
					.assertThat( REASON10, ACTUAL_LIST_STATE2, EXPECTED_LIST_STATE2 );

		/* AND the list contains the expected country names */

			String[] ACTUAL_LOCALES = locales.toArray( new String[ locales.size() ] );
			final String REASON11 = "Asserting that country list contains all expected countries";
			final Matcher<String[]> EXPECTED_LOCALES =
					JMatchers.arrayContainingInAnyOrder( "Australia", "United States", "United Kingdom" );
			SiteSessionManager.get().createCheckPoint( "LOCALES_LIST" )
					.assertThat( REASON11, ACTUAL_LOCALES, EXPECTED_LOCALES );

		/* AND user clicks back on the locale country flag icon. */

			headerBranding.closeLocalesList();

		/* THEN the drop down countries list is hidden back. */

			final String REASON12 = "Asserting that country list is hidden after clicking back on the flag icon.";
			final Matcher<Boolean> EXPECTED_LIST_STATE3 = JMatchers.is( false );
			final boolean ACTUAL_LIST_STATE3 = headerBranding.isLocalesListOpened();
			SiteSessionManager.get().createCheckPoint( "LOCALE_LIST_HIDDEN_BACK" )
					.assertThat( REASON12, ACTUAL_LIST_STATE3, EXPECTED_LIST_STATE3 );

			// reporting and asserting checkpoints

			SiteSessionManager.get().assertAllCheckpoints();
		}
		catch ( WebDriverException | AssertionError e )
		{
			throw new ApplicationException( SiteSessionManager.get().getDriver(), e );
		}
	}

	//endregion


	//region Test id 4 - Header Branding "Top Destinations" drop-down links and list functionality

	@TestCaseId( id = 4 )
	@Steps ( steps = {
			@Step ( number = 1, description = "WHEN user navigates to main page" ),
			@Step ( number = 2, description = "AND clicks on the 'Top Destinations' trigger link.",
					expectedResults = {
							"THEN a list of expected destinations should be displayed.",
							"AND The list title should be \"Top Cruise Destinations\"."
					} ),
			@Step ( number = 3, description = "AND user clicks on each available link",
					expectedResults = { "THEN it should be redirected to /cruise-to/${top-destination}-cruises.aspx" } )
	} )
	@Test ( description = "Testing the \"Top Destinations\" drop-down links and list functionality",
			enabled = true,
			dataProvider = "topDestinationsDP",
			groups = { "US", "UK" }
	)
	public void homePage_Top_Destinations( String name, Destinations destination ) throws Exception
	{
		PreConditions.checkNotNull( this.homePage, "Home page is null, before starting test" );

		Header.HeaderBranding.TopDestinations topDestinations =  ( Header.HeaderBranding.TopDestinations ) this.homePage.header().headerBranding();

		/* AND clicks on the 'Top Destinations' trigger link. */

		final String REASON0 = "Asserting that top-destination list is hidden before clicking the trigger for first time.";
		final Matcher<Boolean> EXPECTED_LIST_STATE0 = JMatchers.is( false );
		final boolean ACTUAL_LIST_STATE0 = topDestinations.isTopDestinationsListOpened();
		SiteSessionManager.get().createCheckPoint( "LOCALE_LIST_HIDDEN_BACK" )
				.assertThat( REASON0, ACTUAL_LIST_STATE0, EXPECTED_LIST_STATE0 );

		topDestinations.openTopDestinationsList();

		/* THEN a list of expected destinations should be displayed */

		final String REASON1 = "Asserting that top-destinations list is displayed after clicking on the trigger icon for first time";
		final Matcher<Boolean> EXPECTED_LIST_STATE1 = JMatchers.is( true );
		final boolean ACTUAL_LIST_STATE1 = topDestinations.isTopDestinationsListOpened();
		SiteSessionManager.get().createCheckPoint( "TOP_DESTINATIONS_LIST_DISPLAYED" )
				.assertThat( REASON1, ACTUAL_LIST_STATE1, EXPECTED_LIST_STATE1 );


		/* AND The list title should be "Top Cruise Destinations" */

		final String REASON2 = "Asserting that top-destinations list title is \"Top Cruise Destinations\"";
		final Matcher<String> EXPECTED_TITLE = JMatchers.is( "Top Cruise Destinations" );
		final String ACTUAL_TITLE = topDestinations.getListTitle();
		SiteSessionManager.get().createCheckPoint( "TOP_DESTINATIONS_TITLE" )
				.assertThat( REASON2, ACTUAL_TITLE, EXPECTED_TITLE );

		/* AND user clicks on each available link */

		CruiseToDestinationPage cruiseToDestinationPage = topDestinations.clickLink( destination );
		String destinationName = destination.toString().toUpperCase().replace( " ", "_" );

		final String REASON3 = "Asserting that url query is contains " + name;
		final Matcher<String> EXPECTED_PATH =
				JMatchers.equalToIgnoringCase( String.format( "/cruise-to/%s-cruises.aspx", destination.name().toLowerCase() ) );
		final String ACTUAL_PATH = cruiseToDestinationPage.getURL().getPath();
		SiteSessionManager.get().createCheckPoint( String.format( "URL[%s]_REFERENCE_CHECK", destinationName ) )
				.assertThat( REASON3, ACTUAL_PATH, EXPECTED_PATH );

		this.homePage = cruiseToDestinationPage.header().headerBranding().clickOnLogo();  // bach to home page ( next iteration )

		// reporting and asserting checkpoints

		SiteSessionManager.get().assertAllCheckpoints();
	}

	//endregion


	//region Test id 5 - Header Branding "Change Currency" functionality


	//endregion


	//region Test id 6 - Notific


	//endregion



//	//region Test id 4 - Verifies the Carnival header home page initial state.
//
//	/* ************************************************************************************************
//
//	 TEST[00004]: ( US,UK,AU )
//
//	 Verifies the page Header Module initial state.
//
//	 ************************************************************************************************ */
//
////	@TestCaseInfo ( ids = { 4 },
////			   title = "Verifies the Carnival header home page initial state.",
////			   steps = {
////					  @Step( id = 1F, description = "User navigates to main page" ),
////					  @Step( id = 2.1F, expectedResult = "Message-Bar section is displayed on UK and hidden for US and AU" ),
////					  @Step( id = 2.2F, expectedResult = "Notification-Bar section is hidden" ),
////					  @Step( id = 2.3F, expectedResult = "Header-Branding section is displayed" ),
////					  @Step( id = 2.41F, expectedResult = "Top Destinations item is displayed on US and UK, however not in AU" ),
////					  @Step( id = 2.42F, expectedResult = "Currency Selector item is displayed only in AU" ),
////					  @Step( id = 2.5F, expectedResult = "Header-Subscribe section is hidden" ),
////					  @Step( id = 2.6F, expectedResult = "Header-Links section is displayed and contains expected links" ),
////					  @Step( id = 2.7F, expectedResult = "Additional-Links section is hidden" ),
////			   }
////	)
//	@Test (
//			description = "Verifies the Carnival header home page initial state.",
//			enabled = true,
//			groups = { "US", "UK", "AU" }
//	)
//	public void homePage_InitialState_Header_Test( ITestContext testContext ) throws Exception
//	{
//		JSoftAssertion softAssert = InitialPage.get().createSoftAssertion();
//
//		try
//		{
//			PreConditions.checkNotNull( this.homePage, "Home page is null, before starting test" );
//			Locale locale = ( Locale ) Validate.notNull( testContext.getAttribute( "locale" ) );
//
//			Header header = homePage.header();
//
//			// ----------------------------------------------------------------------------------------------------------------|
//			// --- STEP 2.1 : Message-Bar displayed on UK, however not in US and AU -------------------------------------------|
//			// ----------------------------------------------------------------------------------------------------------------|
//
//			logger.step( 2.1F, "Validate \"Message-Bar\" is hidden for US and AU, however displayed for UK." );
//
//			if( locale.equals( Locale.UK ) )
//			{
//				softAssert.assertThat(
//						"MESSAGE_BAR_DISPLAYED_UK",
//						"Assert that \"Message-Bar\" is displayed on UK",
//						header.messageBar().isDisplayed(), JMatchers.is( true ) );
//			}
//			else
//			{
//				SoftAssert sa = new SoftAssert();
//				sa.assertTrue( true );
//				softAssert.assertThat(
//						"MESSAGE_BAR_DISPLAYED_UK",
//						"Assert that \"Message-Bar\" is hidden on US and AU",
//						header.messageBar().isDisplayed(), JMatchers.is( false ) );
//			}
//
//			// ----------------------------------------------------------------------------------------------------------------|
//			// --- STEP 2.2 : Notification-Bar is not displayed for non-logged-in user ----------------------------------------|
//			// ----------------------------------------------------------------------------------------------------------------|
//
//			logger.step( 2.2F, "Validate \"Notification-Bar\" is hidden." );
//			softAssert.assertThat(
//					"MESSAGE_BAR_HIDDEN",
//					"Assert that \"Notification-Bar\" is hidden",
//					header.notificationBar().isDisplayed(), JMatchers.is( false ) );
//
//			// ----------------------------------------------------------------------------------------------------------------|
//			// --- STEP 2.3 : Header-Branding should be displayed on all locales                                            ---|
//			// ---            1. on AU the Top-Destination drop down does not exists.                                       ---|
//			// ---			  2. on AU the Currency-Selector exists.				                                        ---|
//			// ----------------------------------------------------------------------------------------------------------------|
//
//			logger.step( 2.3F, "Validate \"Header-Branding\" is displayed." );
//			Header.HeaderBranding headerBranding = header.headerBranding();
//			softAssert.assertThat( "HEADER_BRANDING_DISPLAYED",
//					"Validate \"Header-Branding\" is displayed.",
//					headerBranding.isDisplayed(), JMatchers.is( true ) );
//
//
//			// ----------------------------------------------------------------------------------------------------------------|
//			// --- STEP 2.41 : Top Destinations item is displayed on US and UK, however not in AU  ----------------------------|
//			// ----------------------------------------------------------------------------------------------------------------|
//
//			logger.step( 2.41F, "Validate \"Top Destinations\" exists and is displayed on US and UK, however not in AU." );
//
//			if( locale.equals( Locale.UK ) || locale.equals( Locale.US ) )
//			{
//				Header.HeaderBranding.TopDestinations topDestinations_DataProvider = ( Header.HeaderBranding.TopDestinations ) headerBranding;
//				softAssert.assertThat( "TOP_DESTINATIONS_DISPLAYED",
//						"Assert that \"Top Destinations\" is displayed",
//						topDestinations_DataProvider.isDisplayed(), JMatchers.is( true ) );
//			}
//			else
//			{
//				Header.HeaderBranding.TopDestinations topDestinations_DataProvider = ( Header.HeaderBranding.TopDestinations ) headerBranding;
//				softAssert.assertThat( "TOP_DESTINATIONS_HIDDEN",
//						"Assert that \"Top Destinations\" item does not exists.",
//						headerBranding.hasTopDestinations(), JMatchers.is( false ) );
//			}
//
//			// ----------------------------------------------------------------------------------------------------------------|
//			// --- STEP 2.42 : Validate Currency Selection item exists and displayed on AU only.  -----------------------------|
//			// ----------------------------------------------------------------------------------------------------------------|
//
//			logger.step( 2.42F, "Validate \"Currency Selection\" item exists and displayed on AU only." );
//
//			if( locale.equals( Locale.UK ) || locale.equals( Locale.US ) )
//			{
////				softAssert.assertThat( "CURRENCY_SELECTOR_NOT_EXISTS",
////						"Assert that \"Currency selector\" does not exists.",
////						headerBranding.hasCurrencySelector(), JMatchers.is( false ) );
//			}
//			else
//			{
//				Header.HeaderBranding.CurrencySelector currencySelector = ( Header.HeaderBranding.CurrencySelector ) headerBranding;
////				softAssert.assertThat( "CURRENCY_SELECTOR_EXISTS",
////						"Assert that \"Currency selector\" exists and displayed",
////						currencySelector.isDisplayed(), JMatchers.is( true ) );
//			}
//
//			// ----------------------------------------------------------------------------------------------------------------|
//			// --- STEP 2.5 : Header-Subscribe is not displayed ---------------------------------------------------------------|
//			// ----------------------------------------------------------------------------------------------------------------|
//
//			logger.step( 2.5F, "Validate \"Header-Subscribe\" section is hidden." );
////			softAssert.assertThat( "HEADER_SUBSCRIBE_HIDDEN",
////					"Assert that \"Header Subscribe\" is hidden",
////					header.subscribe().isDisplayed(), JMatchers.is( false ) );
//
//			// ----------------------------------------------------------------------------------------------------------------|
//			// --- STEP 2.6 : Header links is displayed and contains 4 top-level links ----------------------------------------|
//			// ----------------------------------------------------------------------------------------------------------------|
//
//			logger.step( 2.6F, "Validate \"Header-Links\" section is displayed and contains expected links." );
//
//			softAssert.assertThat( "HEADER_LINKS_DISPLAYED",
//					"Assert that \"Header Links\" is displayed.",
//					header.headerLinks().isDisplayed(), JMatchers.is( true ) );
//
//			String expectedLinks = appContext.getMessage( "top.level.links", null, null );
//			String actualLinks = Joiner.on( "," ).join( header.headerLinks().getLinkNames() );
//			softAssert.assertThat( "HEADER_LINKS_TOP_LEVEL",
//					"Assert that \"Header Links\" contains expected links.",
//					actualLinks, JMatchers.equalToIgnoringCase( expectedLinks ) );
//
//			// ----------------------------------------------------------------------------------------------------------------|
//			// --- STEP 2.7 : Header additional links ( level 2 ) are hidden --------------------------------------------------|
//			// ----------------------------------------------------------------------------------------------------------------|
//
//			logger.step( 2.7F, "Validate \"Additional-Links\" section is hidden at initial state.." );
//
//			softAssert.assertThat( "ADDITIONAL_LINKS_HIDDEN",
//					"Assert that \"Additional-Links\" are hidden.",
//					header.navigationAdditional().isDisplayed(), JMatchers.is( true ) );
//
//			// validating all soft assert
//
//			softAssert.assertAll();
//
//		}
//		catch ( Throwable e )
//		{
//			//logger.error( e.getMessage() );
//			throw new Exception( e.getMessage(), e );
//		}
//		finally
//		{
//			// todo: temporary. implement list on soft assertions
//			List<String> messages = softAssert.getMessages();
//			for( String message : messages )
//			{
//				TestReporter.info( message );
//			}
//		}
//	}
//
//	//endregion
//
//	/* ************************************************************************************************
//
//	 TEST[00003]: ( US,UK,AU )
//
//	 Verifies the Carnival home page footer initial state
//
//	 ************************************************************************************************ */
//
//	@Test (
//			description = "TC-ID:00003." +
//					      "Verifies the Carnival home page footer initial state",
//			enabled = true,
//			groups = { "US", "UK", "AU", "IN-DEV" }
//	)
//	public void homePage_InitialState_Footer_Test( ITestContext testContext ) throws Exception
//	{
//		//JSoftAssertion softAssertions = new JSoftAssertions( InitialPage.get().getEventDriver() );
//
//		try
//		{
//			PreConditions.checkNotNull( this.homePage, "Home page is null, before starting test" );
//
//			Footer footer = homePage.footer();
//
//			// ----------------------------------------------------------------------------------------------------------------|
//			// --- STEP 1.1 : Link List is Displayed  -------------------------------------------------------------------------|
//			// ----------------------------------------------------------------------------------------------------------------|
//
//
//			//logger.step( 1.1F, "Validate \"Link-List\" is displayed." );
//			//logger.checkpoint( "LINK_LIST_DISPLAYED", "Assert that \"Link-List\" is displayed." );
//			softAssertions.assertThat( footer.linkList().isDisplayed() ).isTrue();
//
//			// ----------------------------------------------------------------------------------------------------------------|
//			// --- STEP 1.2 : Sub-Footer is Displayed  ------------------------------------------------------------------------|
//			// ----------------------------------------------------------------------------------------------------------------|
//
//			//logger.step( 1.2F, "Validate \"Sub-Footer\" is displayed." );
//			//logger.checkpoint( "SUB_FOOTER_DISPLAYED", "Assert that \"Sub-Footer\" is displayed." );
//			//softAssertions.assertThat( footer.subFooter().isDisplayed() ).isTrue();
//
//			// ----------------------------------------------------------------------------------------------------------------|
//			// --- STEP 1.3 : Zero-Footer is Displayed  -----------------------------------------------------------------------|
//			// ----------------------------------------------------------------------------------------------------------------|
//
//			//logger.step( 1.3F, "Validate \"Zero-Footer\" is displayed." );
//			//logger.checkpoint( "ZERO_FOOTER_DISPLAYED", "Assert that \"Zero-Footer\" is displayed." );
//			//softAssertions.assertThat( footer.zeroFooter().isDisplayed() ).isTrue();
//		}
//		catch ( Throwable e )
//		{
//			//logger.error( e.getMessage() );
//			throw new Exception( e.getMessage(), e );
//		}
//	}
//
//	/* ************************************************************************************************
//
//	 TEST[00004]: ( US,UK,AU )
//
//	 Validates functionality of the top-level and 2nd level menus
//
//	 ************************************************************************************************ */
//
//	@Test (
//			description = "TC-ID:00004." +
//					      "Validates functionality of the top-level and 2nd level menus",  //todo: extend description
//			enabled = true,
//			dataProvider = "topLevels",
//			groups = { "US" }
//	)
//	public void homePage_SubMenu_US_Test( LevelOneMenuItem levelOneMenuItem ) throws Exception
//	{
//		JSoftAssertions softAssertions = new JSoftAssertions( InitialPage.get().getEventDriver() );
//
//		final float SUB_STEP_INC = 0.1F;  // represents a sub-test-step
//		try
//		{
//			PreConditions.checkNotNull( this.homePage, "Home page is null, before starting test" );
//			String key = String.format( "%s.menu.items", levelOneMenuItem.name().toLowerCase() );
//			final String EXPECTED_NAMES = appContext.getMessage( key, null, this.homePage.getDisplayedLocale() );
//
//			Header header = homePage.header();
//			Header.HeaderLinks headerLinks = header.headerLinks();
//			Header.NavigationAdditional navigationAdditional = header.navigationAdditional();
//			Locale locale = homePage.getDisplayedLocale();
//			String maxMenuItems = appContext.getMessage( "max.menu.sub-items", null, locale );
//
//			/* retrieve a list of top-level associated links */
//
//			//logger.step( 2F, "User goes to home page (carnival.com) and hovers/tabs over \"{}\" TAB", levelOneMenuItem.getTitle() );
//			headerLinks.hoverOnMenuItem( levelOneMenuItem );
//
//			/* asserting navigation additional is displayed */
//
//		//	JAssertions.assertThat( navigationAdditional.isDisplayed() ).isEqualTo( true );
//
//			//logger.checkpoint( "NAVIGATION_ADDITIONAL_DISPLAYED", "Validate all second level menus are visible" );
//			List<Link> links = navigationAdditional.getChildMenuItems( levelOneMenuItem );
//
//			//logger.checkpoint( "NO_MORE_THAN_6_ITEMS", "child elements should not display more than 6 items" );
//
//			Validate.isTrue( NumberUtils.isNumber( maxMenuItems ), "key <max.menu.sub-items> must be a number, however is %s", maxMenuItems );
//			//logger.debug( "Expected max.menu.sub-items is -> <{}> ", maxMenuItems );
//		//	softAssertions.assertThat( links.size() ).isLessThanOrEqualTo( NumberUtils.createInteger( maxMenuItems ) );
//
//			//logger.checkpoint( "ITEMS_NAMES", "child menu items names matching" );
//			List<String> names = navigationAdditional.getChildMenuItemsNames( links );
//			String actualNames = Joiner.on( "," ).join( names );
//		//	JAssertions.assertThat( EXPECTED_NAMES ).isEqualTo( actualNames );
//
//
//			/* asserting all links are visible */
//
//			//logger.checkpoint( "LINK_VISIBLE", "Asserting all links are visible" );
//			WebDriverWait wait5 = InitialPage.get().wait5();
//			List<WebElement> elements = ListWebElementUtils.extractWebElement( links );
//		//	JAssertions.assertWaitThat( wait5 ).matchesCondition( WaitUtil.visibilityOfAll( elements, true ) );
//
//			float counter = 0.1F;
//			for( Link link : links )
//			{
//				//logger.step( 3F + counter, "User hovers over \"{}\" menu. image change", levelOneMenuItem.getTitle() );
//				String menuText = link.getText();
//				//logger.debug( "Searching for menu item <'{}'>", menuText );
//				for( MenuItems item : MenuItems.values() )
//				{
//					if( item.getTitle().equals( menuText ) )
//					{
//						navigationAdditional.hoverMenuItem( links, item );
//					}
//				}
//
//				counter += SUB_STEP_INC;
//			}
//
//			/**
//			 * 	URL Redirection part
//			 */
//
//			homePage.header().headerLinks().hoverOnMenuItem( levelOneMenuItem );
//
//			/* the links are only valid for the first sub-menu */
//
//			//logger.debug( "Retrieving a list of Level-2 Links for menu item <\"{}\">", levelOneMenuItem.getTitle() );
//			links = homePage.header().navigationAdditional().getChildMenuItems( levelOneMenuItem );
//
//			switch ( levelOneMenuItem )
//			{
//				case LEARN:
//				{
//					//logger.step( 4F, "User clicks on \"Why Carnival?\"" );
//					//logger.checkpoint( "WHY_CARNIVAL_URL", "checking that \"Why Carnival?\" link redirects to correct url" );
//					CruisingPage cruisingPage = ( CruisingPage ) navigationAdditional.selectMenuItem( links, MenuItems.WHY_CARNIVAL );
//					cruisingPage.navigate().back();
//
//					//logger.step( 5F, "User clicks on \"What's it Like?\"" );
//					homePage.header().headerLinks().hoverOnMenuItem( levelOneMenuItem );
//					links = homePage.header().navigationAdditional().getChildMenuItems( levelOneMenuItem );
//					//logger.checkpoint( "WHAT_IS_LIKE_URL", "checking that \"What's it Like?\" link redirects to correct url" );
//					What2ExpectPage what2ExpectPage = ( What2ExpectPage ) navigationAdditional.selectMenuItem( links, MenuItems.WHATS_IT_LIKE );
//					what2ExpectPage.navigate().back();
//
//					//logger.step( 6F, "User clicks on \"Where can I go?\"" );
//					homePage.header().headerLinks().hoverOnMenuItem( levelOneMenuItem );
//					links = homePage.header().navigationAdditional().getChildMenuItems( levelOneMenuItem );
//					//logger.checkpoint( "WHERE_CAN_I_GO_URL", "checking that \"Where can I go?\" link redirects to correct url" );
//					CruiseDestinationsAndPortsPage destinationsPage =
//							( CruiseDestinationsAndPortsPage ) navigationAdditional.selectMenuItem( links, MenuItems.WHERE_CAN_I_GO );
//					destinationsPage.navigate().back();
//
//					//logger.step( 7F, "User clicks on \"How much is it?\"" );
//					homePage.header().headerLinks().hoverOnMenuItem( levelOneMenuItem );
//					links = homePage.header().navigationAdditional().getChildMenuItems( levelOneMenuItem );
//					//logger.checkpoint( "HOW_MUCH_IS_IT_URL", "checking that \"How much is it?\" link redirects to correct url" );
//					CruiseCostPage costPage = ( CruiseCostPage ) navigationAdditional.selectMenuItem( links, MenuItems.HOW_MUCH_IS_IT );
//					costPage.navigate().back();
//
//					//logger.step( 8F, "User clicks on \"Help me decide.\"" );
//					homePage.header().headerLinks().hoverOnMenuItem( levelOneMenuItem );
//					links = homePage.header().navigationAdditional().getChildMenuItems( levelOneMenuItem );
//					//logger.checkpoint( "HELP_ME_DECIDE_URL", "checking that \"Help me decide?\" link redirects to correct url" );
//					VacationPlannerPage planner = ( VacationPlannerPage ) navigationAdditional.selectMenuItem( links, MenuItems.HELP_ME_DECIDE );
//					planner.header().headerBranding().clickOnLogo();
//					break;
//				}
//				case EXPLORE:
//				{
//					//logger.step( 4F, "User clicks on \"Destinations\"" );
//					//logger.checkpoint( "DESTINATIONS_URL", "checking that \"Destinations\" link redirects to correct url" );
//					CruiseToPage cruiseToPage = ( CruiseToPage ) navigationAdditional.selectMenuItem( links, MenuItems.DESTINATIONS );
//
//					//logger.step( 5F, "User clicks on \"Onboard Activities\"" );
//					cruiseToPage.header().headerLinks().hoverOnMenuItem( levelOneMenuItem );
//					links = cruiseToPage.header().navigationAdditional().getChildMenuItems( levelOneMenuItem );
//					////TestReporter.checkpoint( "OBA_URL", "checking that \"Onboard Activities\" link redirects to correct url" );
//					OnboardActivitiesPage obaPage = ( OnboardActivitiesPage ) navigationAdditional.selectMenuItem( links, MenuItems.ONBOARD_ACTIVITIES );
//
//					//TestReporter.step( 6F, "User clicks on \"Dining\"" );
//					obaPage.header().headerLinks().hoverOnMenuItem( levelOneMenuItem );
//					links = obaPage.header().navigationAdditional().getChildMenuItems( levelOneMenuItem );
//					////TestReporter.checkpoint( "DINING_URL", "checking that \"Dining\" link redirects to correct url" );
//					DiningPage diningPage = ( DiningPage ) navigationAdditional.selectMenuItem( links, MenuItems.DINING );
//
//					//TestReporter.step( 7F, "User clicks on \"Accommodations\"" );
//					diningPage.header().headerLinks().hoverOnMenuItem( levelOneMenuItem );
//					links = diningPage.header().navigationAdditional().getChildMenuItems( levelOneMenuItem );
//					////TestReporter.checkpoint( "ACCOMMODATIONS_URL", "checking that \"Accommodations\" link redirects to correct url" );
//					StateRoomsPage stateRoomsPage = ( StateRoomsPage ) navigationAdditional.selectMenuItem( links, MenuItems.ACCOMMODATIONS );
//
//					//TestReporter.step( 8F, "User clicks on \"Our Ships\"" );
//					stateRoomsPage.header().headerLinks().hoverOnMenuItem( levelOneMenuItem );
//					links = stateRoomsPage.header().navigationAdditional().getChildMenuItems( levelOneMenuItem );
//					//TestReporter.checkpoint( "OUR_SHIPS_URL", "checking that \"Our Ships\" link redirects to correct url" );
//					CruiseShipsPage cruiseShipsPage = ( CruiseShipsPage ) navigationAdditional.selectMenuItem( links, MenuItems.OUR_SHIPS );
//
//					//TestReporter.step( 9F, "User clicks on \"Shore Excursions\"" );
//					cruiseShipsPage.header().headerLinks().hoverOnMenuItem( levelOneMenuItem );
//					links = cruiseShipsPage.header().navigationAdditional().getChildMenuItems( levelOneMenuItem );
//					//TestReporter.checkpoint( "SHORE_EX_URL", "checking that \"Our Ships\" link redirects to correct url" );
//					ActivitiesPage activitiesPage = ( ActivitiesPage ) navigationAdditional.selectMenuItem( links, MenuItems.SHORE_EXCURSIONS );
//					activitiesPage.header().headerBranding().clickOnLogo();
//					break;
//				}
//
//				case PLAN:
//				{
//					//TestReporter.step( 4F, "User clicks on \"Find a Cruise\"" );
//					//TestReporter.checkpoint( "FIND_A_CRUISE_URL", "checking that \"Find a Cruise\" link redirects to correct url" );
//					FindACruisePage findACruisePage = ( FindACruisePage ) navigationAdditional.selectMenuItem( links, MenuItems.FIND_A_CRUISE );
//
//					//TestReporter.step( 5F, "User clicks on \"Find a Port\"" );
//					findACruisePage.header().headerLinks().hoverOnMenuItem( levelOneMenuItem );
//					links = findACruisePage.header().navigationAdditional().getChildMenuItems( levelOneMenuItem );
//					//TestReporter.checkpoint( "FIND_A_PORT_URL", "checking that \"Find a Port\" link redirects to correct url" );
//					CloseToHomePage closeToHomePage = ( CloseToHomePage ) navigationAdditional.selectMenuItem( links, MenuItems.FIND_A_PORT );
//
//					//TestReporter.step( 6F, "User clicks on \"Faqs\"" );
//					closeToHomePage.header().headerLinks().hoverOnMenuItem( levelOneMenuItem );
//					links = closeToHomePage.header().navigationAdditional().getChildMenuItems( levelOneMenuItem );
//					//TestReporter.checkpoint( "FAQS_URL", "checking that \"Faqs\" link redirects to correct url" );
//					FaqPage faqPage = ( FaqPage ) navigationAdditional.selectMenuItem( links, MenuItems.FAQ_S );
//
//					//TestReporter.step( 7F, "User clicks on \"Forums\"" );
//					faqPage.header().headerLinks().hoverOnMenuItem( levelOneMenuItem );
//					links = faqPage.header().navigationAdditional().getChildMenuItems( levelOneMenuItem );
//					//TestReporter.checkpoint( "FORUMS_URL", "checking that \"Forums\" link redirects to correct url" );
//					ForumsPage forumsPage = ( ForumsPage ) navigationAdditional.selectMenuItem( links, MenuItems.FORUMS );
//
//					forumsPage.header().headerBranding().clickOnLogo();
//					break;
//				}
//
//				case MANAGE:
//				{
//					//TestReporter.step( 4F, "User clicks on \"My Booking\"" );
//					//TestReporter.checkpoint( "MY_BOOKING_URL", "checking that \"My Booking\" link redirects to correct url" );
//					BookedGuestLogonPage bookedGuestPage = ( BookedGuestLogonPage ) navigationAdditional.selectMenuItem( links, MenuItems.MY_BOOKING );
//					//TestReporter.checkpoint( "MY_BOOKING_URL_QUERY", "checking \"My Booking\" url query" );
//					bookedGuestPage.assertUrlQuery( MenuItems.MY_BOOKING );
//
//					headerLinks = bookedGuestPage.header().headerLinks();
//					navigationAdditional =  bookedGuestPage.header().navigationAdditional();
//
//					//TestReporter.step( 5F, "User clicks on \"Plan Activities\"" );
//					headerLinks.hoverOnMenuItem( levelOneMenuItem );
//					links = navigationAdditional.getChildMenuItems( levelOneMenuItem );
//					//TestReporter.checkpoint( "PLAN_ACTIVITIES_URL", "checking that \"Plan Activities\" link redirects to correct url" );
//					bookedGuestPage = ( BookedGuestLogonPage ) navigationAdditional.selectMenuItem( links, MenuItems.PLAN_ACTIVITIES );
//					bookedGuestPage.assertUrlQuery( MenuItems.PLAN_ACTIVITIES );
//
//					//TestReporter.step( 6F, "User clicks on \"Check-In\"" );
//					headerLinks.hoverOnMenuItem( levelOneMenuItem );
//					links = navigationAdditional.getChildMenuItems( levelOneMenuItem );
//					//TestReporter.checkpoint( "CHECK_IN_URL", "checking that \"Check-In\" link redirects to correct url" );
//					bookedGuestPage = ( BookedGuestLogonPage ) navigationAdditional.selectMenuItem( links, MenuItems.CHECK_IN );
//					bookedGuestPage.assertUrlQuery( MenuItems.CHECK_IN );
//
//					//TestReporter.step( 7F, "User clicks on \"In Room Gifts & Shopping\"" );
//					headerLinks.hoverOnMenuItem( levelOneMenuItem );
//					links = navigationAdditional.getChildMenuItems( levelOneMenuItem );
//					//TestReporter.checkpoint( "IN_ROOM_URL", "checking that \"In Room Gifts & Shopping\" link redirects to correct url" );
//					FunShopsPage funShopsPage = ( FunShopsPage ) navigationAdditional.selectMenuItem( links, MenuItems.IN_ROOM_GIFTS_AND_SHOPPING );
//
//					//TestReporter.step( 8F, "User clicks on \"VIFP Club\"" );
//					funShopsPage.header().headerLinks().hoverOnMenuItem( levelOneMenuItem );
//					links = funShopsPage.header().navigationAdditional().getChildMenuItems( levelOneMenuItem );
//					//TestReporter.checkpoint( "VIFP_URL", "checking that \"VIFP Club\" link redirects to correct url" );
//					VifpClubPage vifpClubPage = ( VifpClubPage ) navigationAdditional.selectMenuItem( links, MenuItems.VIFP_CLUB );
//
//					vifpClubPage.header().headerBranding().clickOnLogo();
//					break;
//				}
//
//				default:
//				{
//					final String MSG = "The menu item '" + levelOneMenuItem.getTitle() + "' does not exists";
//					throw new NoSuchMenuItemException( InitialPage.get().getEventDriver(), MSG );
//				}
//
//			}
//
//			softAssertions.assertAll();
//		}
//		catch ( Throwable e )
//		{
//			//TestReporter.error( e.getMessage() );
//			throw new Exception( e.getMessage(), e );
//		}
//	}
//
//	//endregion

	//region HeaderBranding - After Configurations Methods Section

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


	//region HeaderBrandingTest - Data Provider Methods Section

	@DataProvider ( name = "topDestinationsDP", parallel = false )
	public Object[][] topDestinations_DataProvider( ITestContext context, Method method )
	throws Exception
	{
		////TestReporter.startConfig( "DataProvider", "localesDP" );

		String str = ( String ) SiteProperty.HEADER_TOP_DESTINATIONS.fromContext();
		String[] destinations = StringUtils.split( str, "," );

		List<Object[]> data = Lists.newArrayListWithExpectedSize( destinations.length );
		for( String destination : destinations )
		{
			String destinationName =  destination.replace( " Cruises", "" );
			data.add( new Object[] { destinationName, Destinations.valueOf( destinationName.toUpperCase() ) } );
		}

		return data.toArray( new Object[ data.size() ][] );

	}


	//endregion

}