package com.framework.test.core.header;

import com.framework.BaseTest;
import com.framework.driver.event.ExpectedConditions;
import com.framework.driver.event.HtmlCondition;
import com.framework.driver.event.HtmlElement;
import com.framework.driver.exceptions.ApplicationException;
import com.framework.driver.objects.Link;
import com.framework.site.config.SiteProperty;
import com.framework.site.config.SiteSessionManager;
import com.framework.site.data.Enumerators;
import com.framework.site.objects.body.common.NavStickEmbeddedObject;
import com.framework.site.objects.header.interfaces.Header;
import com.framework.site.pages.activities.ActivitiesPage;
import com.framework.site.pages.bookedguest.BookedGuestLogonPage;
import com.framework.site.pages.bookingengine.FindACruisePage;
import com.framework.site.pages.core.*;
import com.framework.site.pages.core.cruisefrom.CruiseFromPortPage;
import com.framework.site.pages.funshops.FunShopsPage;
import com.framework.site.pages.funville.ForumsPage;
import com.framework.site.pages.loyalty.VifpClubPage;
import com.framework.testing.annotations.Step;
import com.framework.testing.annotations.Steps;
import com.framework.testing.annotations.TestCaseId;
import com.framework.utils.datetime.Sleeper;
import com.framework.utils.datetime.TimeConstants;
import com.framework.utils.error.PreConditions;
import com.framework.utils.matchers.JMatchers;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;
import org.hamcrest.Matcher;
import org.joda.time.DateTime;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriverException;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;
import org.testng.xml.XmlTest;

import java.lang.reflect.Method;
import java.net.URL;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import static com.framework.site.objects.header.enums.LevelOneMenuItem.*;
import static com.framework.site.objects.header.enums.MenuItems.*;
import static com.framework.utils.datetime.TimeConstants.FIVE_SECONDS;
import static com.framework.utils.matchers.JMatchers.*;

public class NavigationAdditionalTest extends BaseTest
{

	//region NavigationAdditionalTest - Variables Declaration and Initialization Section.

	private HomePage homePage = null;

	//endregion


	//region NavigationAdditionalTest - Constructor Methods Section

	private NavigationAdditionalTest()
	{
		BaseTest.classLogger = LoggerFactory.getLogger( NavigationAdditionalTest.class );
	}

	//endregion


	//region NavigationAdditionalTest - Before Configurations Methods Section

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
			if( method.getName().contains( "header_SubNavigation_Learn_Actions" ) )
			{
				if( homePage.manage().getCookieNamed( "ccl_learn_experience" ) == null )
				{
					Cookie ccl_learn_experience = new Cookie(
							"ccl_learn_experience", "default", "/", DateTime.now().plusMonths( 1 ).toDate() );
					homePage.manage().addCookie( ccl_learn_experience );
				}
			}
		}
		catch ( Throwable e )
		{
			if ( e instanceof WebDriverException || e instanceof AssertionError )
			{
				throw new ApplicationException( SiteSessionManager.get().getDriver(), e );
			}
			throw new Exception( e.getMessage(), e );
		}
	}

	//endregion


	//region NavigationAdditionalTest - Test Methods Section

	//region NavigationAdditionalTest - header_SubNavigation_Learn_Actions_US

	@TestCaseId ( id = 40488 )
	@Steps ( steps = {
			@Step ( number = 1, description = "GIVEN user is on carnival home page" ),
			@Step ( number = 2, description = "WHEN user hovers over \"Learn\" tab",
					expectedResults = {
							"THEN should see No more than 6 items on the additional level-2 menu.",
							"AND The items appearance order matches the required order based on the current locale."
			} ),
			@Step ( number = 3, description = "AND WHEN the user hovers and clicks on \"Why Carnival?\" item.",
					expectedResults = {
							"THEN The image should change when hover to IMG-hover.",
							"AND It should take the user to /cruising.aspx.",
							"AND The \"LEARN\" level 1 menu item should be active."
			} ),
			@Step ( number = 4, description = "AND WHEN the user hovers and clicks on \"What's it like?\" item.",
			expectedResults = {
					"THEN The image should change when hover to IMG-hover.",
					"AND it should take the user to /what-to-expect-on-a-cruise.aspx.",
					"AND The \"LEARN\" level 1 menu item should be active."
			} ),
			@Step ( number = 5, description = "AND WHEN the user hovers and clicks on \"Where can I go?\" item.",
					expectedResults = {
							"THEN the image should change when hover to IMG-hover.",
							"THEN It should take the user to /cruise-destinations-and-ports.aspx.",
							"AND The \"LEARN\" level 1 menu item should be active."
			} ),
			@Step ( number = 6, description = "AND WHEN the user hovers and clicks on \"How much is it?\" item.",
					expectedResults = {
							"THEN the image should change when hover to IMG-hover.",
							"THEN It should take the user to /how-much-does-a-cruise-cost.aspx.",
							"AND The \"LEARN\" level 1 menu item should be active."
			} ),
			@Step ( number = 7, description = "AND WHEN the user hovers and clicks on \"Help me decide.\" item.",
					expectedResults = {
							"THEN the image should change when hover to IMG-hover.",
							"THEN It should take the user to Vacation Planner page: /vacation-planner.aspx.",
							"AND The \"LEARN\" level 1 menu item should be active."
					} )
	} )
	@Test( description = "Header - SubNavigation - Learn - Actions - Verify SubNavigation associated links",
		   enabled = false,
	       groups = { "US" } )
	public void header_SubNavigation_Learn_Actions_US() throws Exception
	{
		/* GIVEN user is on carnival home page" */
		PreConditions.checkNotNull( this.homePage, "Home page is null, before starting test" );
		try
		{
			Integer maxMenuItems = Integer.valueOf( SiteProperty.MAX_MENU_SUB_ITEMS.fromContext().toString() );
			String names = SiteProperty.LEARN_MENU_ITEMS.fromContext().toString();
			HtmlElement container = homePage.header().getContainer();
			Header.NavigationAdditional navigationAdditional = homePage.header().navigationAdditional();
			Header.HeaderLinks headerLinks = homePage.header().headerLinks();

			/* WHEN user hovers over "Learn" tab */

			headerLinks.hoverOnItem( LEARN );

			/* should see No more than 6 items on the additional level-2 menu */
			String REASON = "Asserting that menu sub-items are displayed";
			boolean ACTUAL_BOOL = navigationAdditional.isDisplayed();
			Matcher<Boolean> EXPECTED_OF_BOOL = is( true );
			SiteSessionManager.get().createCheckPoint( container, "LEARN_ADDITIONAL_LINKS_DISPLAYED" )
					.assertThat( REASON, ACTUAL_BOOL, EXPECTED_OF_BOOL );

			List<String> itemsNames = navigationAdditional.getChildMenuItemsNames( LEARN );
			REASON = "Asserting that we should see No more than 6 items on the additional level-2 menu";
			Matcher<Collection<? extends String>> EXPECTED_RESULT_OF_COLL_STR = hasSize( lessThanOrEqualTo( maxMenuItems ) );
			SiteSessionManager.get().createCheckPoint( container, "LEARN_NO_MORE_THAN_6" )
					.assertThat( REASON, itemsNames, EXPECTED_RESULT_OF_COLL_STR );

			/* AND The items appearance order matches the required order based on the current locale. */

			REASON = "Asserting that all level-2 items appearance order matches the required order based on the current locale";
			String[] ACTUAL_OF_STR_ARR = itemsNames.toArray( new String[ itemsNames.size() ] );
			Matcher<String[]> EXPECTED_OF_STR_ARR = arrayContaining( StringUtils.split( names, "," ) );
			SiteSessionManager.get().createCheckPoint( container, "LEARN_LEVEL_2_ITEMS" )
					.assertThat( REASON, ACTUAL_OF_STR_ARR, EXPECTED_OF_STR_ARR );

			/* AND WHEN the user hovers and clicks on "Why Carnival?" item. */
			Link whyCarnival = navigationAdditional.getLink( LEARN, WHY_CARNIVAL );
			String dataDefault = navigationAdditional.getDataDefaultImg( whyCarnival );
			HtmlElement img = navigationAdditional.getImage( whyCarnival );

			/* The image should change when hover to IMG-hover. */
			REASON = "asserting that  \"why carnival\" img[src] value before hovering ends with < '%s' >";
			Matcher<String> EXPECTED_OF_STR = endsWith( dataDefault );
			HtmlCondition<Boolean> CONDITION_BOOL = ExpectedConditions.elementAttributeToMatch( img, "src", EXPECTED_OF_STR );
			SiteSessionManager.get().createCheckPoint( container, "WHY_CARNIVAL_BEFORE_HOVER" )
					.assertWaitThat( String.format( REASON, dataDefault ), FIVE_SECONDS, CONDITION_BOOL );

			navigationAdditional.hoverOnMenuItem( whyCarnival );

			String dataHover = navigationAdditional.getDataHoverImg( whyCarnival );
			REASON = "asserting that \"why carnival\" img[src] value after hovering ends with < '%s' >";
			EXPECTED_OF_STR = endsWith( dataHover );
			CONDITION_BOOL = ExpectedConditions.elementAttributeToMatch( img, "src", EXPECTED_OF_STR );
			SiteSessionManager.get().createCheckPoint( container, "WHY_CARNIVAL_AFTER_HOVER" )
					.assertWaitThat( String.format( REASON, dataDefault ), FIVE_SECONDS, CONDITION_BOOL );

			/* THEN It should take the user to /cruising.aspx. */
			CruisingPage cruisingPage = ( CruisingPage ) navigationAdditional.clickOnMenuItem( whyCarnival, WHY_CARNIVAL );
			navigationAdditional = cruisingPage.header().navigationAdditional();
			headerLinks = cruisingPage.header().headerLinks();
			container = cruisingPage.header().getContainer();

			/* AND the "LEARN" level 1 menu item should be active. */
			Link learn = headerLinks.getLink( LEARN );
			REASON = "asserting that \"LEARN\" menu item is 'active'";
			EXPECTED_OF_STR = containsString( "active" );
			String ACTUAL_STR = learn.getHtmlElement().getAttribute( "class" );
			SiteSessionManager.get().createCheckPoint( "LEARN_IS_ACTIVE_ON_WHY_CARNIVAL" )
					.assertThat( REASON, ACTUAL_STR, EXPECTED_OF_STR );

			/* AND WHEN the user hovers and clicks on "What's it like?" item. */
			headerLinks.hoverOnItem( LEARN );
			Link whatIsLike = navigationAdditional.getLink( LEARN, WHATS_IT_LIKE );
			dataDefault = navigationAdditional.getDataDefaultImg( whatIsLike );
			dataHover = navigationAdditional.getDataHoverImg( whatIsLike );
			img = navigationAdditional.getImage( whatIsLike );

			/* The image should change when hover to IMG-hover. */

			REASON = "asserting that \"what is like\" img[src] value before hovering ends with < '%s' >";
			EXPECTED_OF_STR = endsWith( dataDefault );
			CONDITION_BOOL = ExpectedConditions.elementAttributeToMatch( img, "src", EXPECTED_OF_STR );
			SiteSessionManager.get().createCheckPoint( container, "WHAT_IS_LIKE_BEFORE_HOVER" )
					.assertWaitThat( String.format( REASON, dataDefault ), FIVE_SECONDS, CONDITION_BOOL );

			navigationAdditional.hoverOnMenuItem( whatIsLike );

			REASON = "asserting that \"what is like\"  img[src] value after hovering ends with < '%s' >";
			EXPECTED_OF_STR = endsWith( dataHover );
			CONDITION_BOOL = ExpectedConditions.elementAttributeToMatch( img, "src", EXPECTED_OF_STR );
			SiteSessionManager.get().createCheckPoint( container, "WHAT_IS_LIKE_AFTER_HOVER" )
					.assertWaitThat( String.format( REASON, dataDefault ), FIVE_SECONDS, CONDITION_BOOL );

			/* THEN It should take the user to /what-to-expect-on-a-cruise.aspx. */

			What2ExpectPage what2ExpectPage = ( What2ExpectPage )
					navigationAdditional.clickOnMenuItem( whatIsLike, WHATS_IT_LIKE );
			navigationAdditional = what2ExpectPage.header().navigationAdditional();
			headerLinks = what2ExpectPage.header().headerLinks();
			container = what2ExpectPage.header().getContainer();

			/* AND The "LEARN" level 1 menu item should be active. */

			learn = headerLinks.getLink( LEARN );

			REASON = "asserting that \"LEARN\" menu item is 'active'";
			EXPECTED_OF_STR = containsString( "active" );
			ACTUAL_STR = learn.getHtmlElement().getAttribute( "class" );
			SiteSessionManager.get().createCheckPoint( "LEARN_IS_ACTIVE_ON_WHATS_IT_LIKE" )
					.assertThat( REASON, ACTUAL_STR, EXPECTED_OF_STR );

			/* AND WHEN the user hovers and clicks on "Where can I go?" item. */

			headerLinks.hoverOnItem( LEARN );

			Link whereCanIgo = navigationAdditional.getLink( LEARN, WHERE_CAN_I_GO );
			dataDefault = navigationAdditional.getDataDefaultImg( whereCanIgo );
			dataHover = navigationAdditional.getDataHoverImg( whereCanIgo );
			img = navigationAdditional.getImage( whereCanIgo );

			/* The image should change when hover to IMG-hover. */

			REASON = "asserting that \"where can i go?\" img[src] value before hovering ends with < '%s' >";
			EXPECTED_OF_STR = endsWith( dataDefault );
			CONDITION_BOOL = ExpectedConditions.elementAttributeToMatch( img, "src", EXPECTED_OF_STR );
			SiteSessionManager.get().createCheckPoint( container, "WHERE_CAN_I_GO_BEFORE_HOVER" )
					.assertWaitThat( String.format( REASON, dataDefault ), FIVE_SECONDS, CONDITION_BOOL );

			navigationAdditional.hoverOnMenuItem( whereCanIgo );

			REASON = "asserting that \"where can i go?\"  img[src] value after hovering ends with < '%s' >";
			EXPECTED_OF_STR = endsWith( dataHover );
			CONDITION_BOOL = ExpectedConditions.elementAttributeToMatch( img, "src", EXPECTED_OF_STR );
			SiteSessionManager.get().createCheckPoint( container, "WHERE_CAN_I_GO_AFTER_HOVER" )
					.assertWaitThat( String.format( REASON, dataDefault ), FIVE_SECONDS, CONDITION_BOOL );

			/* It should take the user to /cruise-destinations-and-ports.aspx. */

			CruiseDestinationsAndPortsPage destinationsPage = ( CruiseDestinationsAndPortsPage )
					navigationAdditional.clickOnMenuItem( whereCanIgo, WHERE_CAN_I_GO );
			navigationAdditional = destinationsPage.header().navigationAdditional();
			headerLinks = destinationsPage.header().headerLinks();
			container = destinationsPage.header().getContainer();

			/* The "LEARN" level 1 menu item should be active.*/

			learn = headerLinks.getLink( LEARN );

			REASON = "asserting that \"LEARN\" menu item is 'active'";
			EXPECTED_OF_STR = containsString( "active" );
			ACTUAL_STR = learn.getHtmlElement().getAttribute( "class" );
			SiteSessionManager.get().createCheckPoint( "LEARN_IS_ACTIVE_ON_WHERE_CAN_I_GO" )
					.assertThat( REASON, ACTUAL_STR, EXPECTED_OF_STR );

			/* AND the user hovers and clicks on "How much is it?" item. */

			headerLinks.hoverOnItem( LEARN );

			Link howMuchIsIt = navigationAdditional.getLink( LEARN, HOW_MUCH_IS_IT );
			dataDefault = navigationAdditional.getDataDefaultImg( howMuchIsIt );
			dataHover = navigationAdditional.getDataHoverImg( howMuchIsIt );
			img = navigationAdditional.getImage( howMuchIsIt );

			/* The image should change when hover to IMG-hover. */

			REASON = "asserting that \"How much is it\" img[src] value before hovering ends with < '%s' >";
			EXPECTED_OF_STR = endsWith( dataDefault );
			CONDITION_BOOL = ExpectedConditions.elementAttributeToMatch( img, "src", EXPECTED_OF_STR );
			SiteSessionManager.get().createCheckPoint( container, "HOW_MUCH_IS_IT_BEFORE_HOVER" )
					.assertWaitThat( String.format( REASON, dataDefault ), FIVE_SECONDS, CONDITION_BOOL );

			destinationsPage.header().navigationAdditional().hoverOnMenuItem( howMuchIsIt );

			REASON = "asserting that \"How much is it\" img[src] value after hovering ends with < '%s' >";
			EXPECTED_OF_STR = endsWith( dataHover );
			CONDITION_BOOL = ExpectedConditions.elementAttributeToMatch( img, "src", EXPECTED_OF_STR );
			SiteSessionManager.get().createCheckPoint( container, "HOW_MUCH_IS_IT_AFTER_HOVER" )
					.assertWaitThat( String.format( REASON, dataDefault ), FIVE_SECONDS, CONDITION_BOOL );

			/* It should take the user to /how-much-does-a-cruise-cost.aspx. */

			CruiseCostPage cruiseCostPage = ( CruiseCostPage )
					navigationAdditional.clickOnMenuItem( howMuchIsIt, HOW_MUCH_IS_IT );
			navigationAdditional = cruiseCostPage.header().navigationAdditional();
			headerLinks = cruiseCostPage.header().headerLinks();
			container = cruiseCostPage.header().getContainer();

			/* The "LEARN" level 1 menu item should be active. */

			learn = headerLinks.getLink( LEARN );

			REASON = "asserting that \"LEARN\" menu item is 'active'";
			EXPECTED_OF_STR = containsString( "active" );
			ACTUAL_STR = learn.getHtmlElement().getAttribute( "class" );
			SiteSessionManager.get().createCheckPoint( "LEARN_IS_ACTIVE_ON_HOW_MUCH_IS_IT" )
					.assertThat( REASON, ACTUAL_STR, EXPECTED_OF_STR );

			/* AND the user hovers and clicks on "Help me decide." item. */

			headerLinks.hoverOnItem( LEARN );

			Link helpMeDecide = navigationAdditional.getLink( LEARN, HELP_ME_DECIDE );
			dataDefault = navigationAdditional.getDataDefaultImg( helpMeDecide );
			dataHover = navigationAdditional.getDataHoverImg( helpMeDecide );
			img = navigationAdditional.getImage( helpMeDecide );

			/* The image should change when hover to IMG-hover. */

			REASON = "asserting that \"Help me decide\" img[src] value before hovering ends with < '%s' >";
			EXPECTED_OF_STR = endsWith( dataDefault );
			CONDITION_BOOL = ExpectedConditions.elementAttributeToMatch( img, "src", EXPECTED_OF_STR );
			SiteSessionManager.get().createCheckPoint( container, "HELP_ME_DECIDE_BEFORE_HOVER" )
					.assertWaitThat( String.format( REASON, dataDefault ), FIVE_SECONDS, CONDITION_BOOL );

			navigationAdditional.hoverOnMenuItem( helpMeDecide );

			REASON = "asserting that \"Help me decide\" img[src] value after hovering ends with < '%s' >";
			EXPECTED_OF_STR = endsWith( dataHover );
			CONDITION_BOOL = ExpectedConditions.elementAttributeToMatch( img, "src", EXPECTED_OF_STR );
			SiteSessionManager.get().createCheckPoint( container, "HELP_ME_DECIDE_AFTER_HOVER" )
					.assertWaitThat( String.format( REASON, dataDefault ), FIVE_SECONDS, CONDITION_BOOL );

			/* It should take the user to Vacation Planner page: /vacation-planner.aspx. */

			VacationPlannerPage vacationPlannerPage = ( VacationPlannerPage )
					navigationAdditional.clickOnMenuItem( helpMeDecide, HELP_ME_DECIDE );
			headerLinks = vacationPlannerPage.header().headerLinks();
			container = vacationPlannerPage.header().getContainer();

			/* The "LEARN" level 1 menu item should be active.*/

			learn = headerLinks.getLink( LEARN );

			REASON = "asserting that \"LEARN\" menu item is 'active'";
			EXPECTED_OF_STR = containsString( "active" );
			ACTUAL_STR = learn.getHtmlElement().getAttribute( "class" );
			SiteSessionManager.get().createCheckPoint( "LEARN_IS_ACTIVE_ON_HELP_ME_DECIDE" )
					.assertThat( REASON, ACTUAL_STR, EXPECTED_OF_STR );

			vacationPlannerPage.header().headerBranding().clickOnLogo();

			SiteSessionManager.get().assertAllCheckpoints();
		}
		catch ( WebDriverException | AssertionError e )
		{
			throw new ApplicationException( SiteSessionManager.get().getDriver(), e );
		}
	}

	//endregion

	//region NavigationAdditionalTest - header_SubNavigation_Learn_Actions_UK


	@TestCaseId ( id = 40488 )
	@Steps ( steps = {
			@Step ( number = 1, description = "GIVEN user is on carnival home page" ),
			@Step ( number = 2, description = "WHEN user hovers over \"Learn\" tab",
					expectedResults = {
							"THEN he should see No more than 6 items on the additional level-2 menu.",
							"AND The items appearance order matches the required order based on the current locale."
					} ),
			@Step ( number = 3, description = "AND WHEN the user hovers and clicks on \"Caribbean\" item.",
					expectedResults = {
							"THEN the image should change when hover to IMG-hover.",
							"THEN It should take the user to the page: /uk-caribbean.aspx",
							"AND the active link on navigation sticker should be \"Where we sail from\""
					} ),
			@Step ( number = 4, description = "AND WHEN the user hovers and clicks on \"What's Included?\" item.",
					expectedResults = {
							"THEN the image should change when hover to IMG-hover.",
							"THEN It should take the user to the page: /content/rookie/rookie.aspx",
							"AND the active link on navigation sticker should be \"What's Included\"",
					} ),
			@Step ( number = 5,
					description = "AND WHEN the user returns to home page, hovers and clicks on \"On the ship\" item.",
					expectedResults = {
							"THEN image should change when hover to IMG-hover.",
							"AND It should take the user to /content/rookie/rookie.aspx",
							"AND the url reference should contains #on-ship",
							"AND the active link on navigation sticker should be \"On the Ship\""
					} ),
			@Step ( number = 6,
					description = "AND WHEN the user returns to home page, hovers and clicks on \"Shore Excursions\" item.",
					expectedResults = {
							"THEN the image should change when hover to IMG-hover.",
							"THEN It should take the user to /content/rookie/rookie.aspx",
							"AND the url reference should contains #shore-excursions",
							"AND the active link on navigation sticker should be \"Shore Excursions\""
					} ),
			@Step ( number = 7,
					description = "AND WHEN the user returns to home page, hovers and clicks on \"Destinations\" item.",
					expectedResults = {
							"The image should change when hover to IMG-hover.",
							"THEN It should take the user to /content/rookie/rookie.aspx",
							"AND the url reference should contains #destinations",
							"AND the active link on navigation sticker should be \"Destinations\""
					} )
	} )
	@Test( description = "Header - SubNavigation - Learn - Actions - Verify SubNavigation associated links",
			enabled = false,
			groups = { "UK", "PROD" } )
	public void header_SubNavigation_Learn_Actions_UK() throws Exception
	{

		/* GIVEN user is on carnival home page" */
		PreConditions.checkNotNull( this.homePage, "Home page is null, before starting test" );
		try
		{
			Integer maxMenuItems = Integer.valueOf( SiteProperty.MAX_MENU_SUB_ITEMS.fromContext().toString() );
			String names = SiteProperty.LEARN_MENU_ITEMS.fromContext().toString();
			HtmlElement container = homePage.header().getContainer();
			Header.NavigationAdditional navigationAdditional = homePage.header().navigationAdditional();
			Header.HeaderLinks headerLinks = homePage.header().headerLinks();

			/* WHEN user hovers over "Learn" tab */
			headerLinks.hoverOnItem( LEARN );

			/* should see No more than 6 items on the additional level-2 menu */
			String REASON = "Asserting that menu sub-items are displayed";
			boolean ACTUAL_BOOL = navigationAdditional.isDisplayed();
			Matcher<Boolean> EXPECTED_OF_BOOL = is( true );
			SiteSessionManager.get().createCheckPoint( container, "LEARN_ADDITIONAL_LINKS_DISPLAYED" )
					.assertThat( REASON, ACTUAL_BOOL, EXPECTED_OF_BOOL );

			List<String> itemsNames = navigationAdditional.getChildMenuItemsNames( LEARN );
			REASON = "Asserting that we should see No more than 6 items on the additional level-2 menu";
			Matcher<Collection<? extends String>> EXPECTED_OF_COLL_STR = hasSize( lessThanOrEqualTo( maxMenuItems ) );
			SiteSessionManager.get().createCheckPoint( container, "LEARN_NO_MORE_THAN_6" )
					.assertThat( REASON, itemsNames, EXPECTED_OF_COLL_STR );

			/* AND The items appearance order matches the required order based on the current locale. */
			REASON = "Asserting that all level-2 items appearance order matches the required order based on the current locale";
			String[] ACTUAL_STR_ARR = itemsNames.toArray( new String[ itemsNames.size() ] );
			Matcher<String[]> EXPECTED_OF_STR_ARR = arrayContaining( StringUtils.split( names, "," ) );
			SiteSessionManager.get().createCheckPoint( container, "LEARN_LEVEL_2_ITEMS" )
					.assertThat( REASON, ACTUAL_STR_ARR, EXPECTED_OF_STR_ARR );

			/* AND WHEN the user hovers and clicks on "Caribbean" item  */
			Link caribbean = navigationAdditional.getLink( LEARN, CARIBBEAN );
			String dataDefault = navigationAdditional.getDataDefaultImg( caribbean );
			HtmlElement img = navigationAdditional.getImage( caribbean );

			/* The image should change when hover to IMG-hover. */
			REASON = "asserting that  \"Caribbean\" img[src] value before hovering ends with < '%s' >";
			Matcher<String> EXPECTED_OF_STR = endsWith( dataDefault );
			HtmlCondition<Boolean> CONDITION_BOOL = ExpectedConditions.elementAttributeToMatch( img, "src", EXPECTED_OF_STR );
			SiteSessionManager.get().createCheckPoint( container, "CARIBBEAN_BEFORE_HOVER" )
					.assertWaitThat( String.format( REASON, dataDefault ), FIVE_SECONDS, CONDITION_BOOL );

			navigationAdditional.hoverOnMenuItem( caribbean );

			String dataHover = navigationAdditional.getDataHoverImg( caribbean );
			REASON = "asserting that \"Caribbean\" img[src] value after hovering ends with < '%s' >";
			EXPECTED_OF_STR = endsWith( dataHover );
			CONDITION_BOOL = ExpectedConditions.elementAttributeToMatch( img, "src", EXPECTED_OF_STR );
			SiteSessionManager.get().createCheckPoint( container, "CARIBBEAN_AFTER_HOVER" )
					.assertWaitThat( String.format( REASON, dataDefault ), FIVE_SECONDS, CONDITION_BOOL );

			/* THEN It should take the user to the page: /uk-caribbean.aspx */
			UKCaribbeanPage ukCaribbeanPage = ( UKCaribbeanPage ) navigationAdditional.clickOnMenuItem( caribbean, CARIBBEAN );
			navigationAdditional = ukCaribbeanPage.header().navigationAdditional();
			headerLinks = ukCaribbeanPage.header().headerLinks();
			container = ukCaribbeanPage.header().getContainer();

			/* AND the "LEARN" level 1 menu item should be active. */
			REASON = "asserting that \"Where we sail from\" is the active link on navigation sticker";
			Matcher<Enumerators.NavStickItem> EXPECTED_OF_NAV = is( NavStickEmbeddedObject.NavStickItem.WHERE_WE_SAIL_FROM );
			Enumerators.NavStickItem ACTUAL_NAV = ukCaribbeanPage.navStickEmbedded().getActiveItem();
			SiteSessionManager.get().createCheckPoint( "WHERE_WE_SAIL_FROM_ACTIVE_STICK_ITEM" )
					.assertThat( REASON, ACTUAL_NAV, EXPECTED_OF_NAV );

			/* AND WHEN the user hovers and clicks on "What's Included?" item. */
			headerLinks.hoverOnItem( LEARN );

			Link whatsIncluded = navigationAdditional.getLink( LEARN, WHATS_INCLUDED );
			dataDefault = navigationAdditional.getDataDefaultImg( whatsIncluded );
			dataHover = navigationAdditional.getDataHoverImg( whatsIncluded );
			img = navigationAdditional.getImage( whatsIncluded );

			/* THEN the image should change when hover to IMG-hover. */

			REASON = "asserting that \"What's included\" img[src] value before hovering ends with < '%s' >";
			EXPECTED_OF_STR = endsWith( dataDefault );
			CONDITION_BOOL = ExpectedConditions.elementAttributeToMatch( img, "src", EXPECTED_OF_STR );
			SiteSessionManager.get().createCheckPoint( container, "WHATS_INCLUDED_BEFORE_HOVER" )
					.assertWaitThat( String.format( REASON, dataDefault ), FIVE_SECONDS, CONDITION_BOOL );

			navigationAdditional.hoverOnMenuItem( whatsIncluded );

			REASON = "asserting that \"What's included\"  img[src] value after hovering ends with < '%s' >";
			EXPECTED_OF_STR = endsWith( dataHover );
			CONDITION_BOOL = ExpectedConditions.elementAttributeToMatch( img, "src", EXPECTED_OF_STR );
			SiteSessionManager.get().createCheckPoint( container, "WHATS_INCLUDED_AFTER_HOVER" )
					.assertWaitThat( String.format( REASON, dataDefault ), FIVE_SECONDS, CONDITION_BOOL );

			/* AND It should take the user to /content/rookie/rookie.aspx */

			BeginnersGuidePage whatsIncludedPage = ( BeginnersGuidePage )
					navigationAdditional.clickOnMenuItem( whatsIncluded, WHATS_INCLUDED );
			navigationAdditional = whatsIncludedPage.header().navigationAdditional();
			container = whatsIncludedPage.header().getContainer();

			/* AND the active link on navigation sticker should be "What's Included" */
			REASON = "asserting that \"What's Included\" is the active link on navigation sticker";
			EXPECTED_OF_NAV = is( NavStickEmbeddedObject.NavStickItem.WHATS_INCLUDED );
			ACTUAL_NAV = whatsIncludedPage.navStickEmbedded().getActiveItem();
			HtmlElement nsc = ukCaribbeanPage.navStickEmbedded().getNavStickemContainer();
			SiteSessionManager.get().createCheckPoint( nsc, "WHATS_INCLUDED_ACTIVE_STICK_ITEM" )
					.assertThat( REASON, ACTUAL_NAV, EXPECTED_OF_NAV );

			/* AND WHEN the user navigates back to home page, hovers and clicks on "On the ship" item. */
			homePage = whatsIncludedPage.header().headerBranding().clickOnLogo();
			homePage.header().headerLinks().hoverOnItem( LEARN );
			Link onTheShip = homePage.header().navigationAdditional().getLink( LEARN, ON_THE_SHIP );
			dataDefault = homePage.header().navigationAdditional().getDataDefaultImg( onTheShip );
			dataHover = navigationAdditional.getDataHoverImg( onTheShip );
			img = homePage.header().navigationAdditional().getImage( onTheShip );
			container = homePage.header().getContainer();

			/* The image should change when hover to IMG-hover. */
			REASON = "asserting that  \"On The Ship\" img[src] value before hovering ends with < '%s' >";
			EXPECTED_OF_STR = endsWith( dataDefault );
			CONDITION_BOOL = ExpectedConditions.elementAttributeToMatch( img, "src", EXPECTED_OF_STR );
			SiteSessionManager.get().createCheckPoint( container, "ON_THE_SHIP_BEFORE_HOVER" )
					.assertWaitThat( String.format( REASON, dataDefault ), FIVE_SECONDS, CONDITION_BOOL );

			homePage.header().navigationAdditional().hoverOnMenuItem( onTheShip );

			REASON = "asserting that \"ON_THE_SHIP\" img[src] value after hovering ends with < '%s' >";
			EXPECTED_OF_STR = endsWith( dataHover );
			CONDITION_BOOL = ExpectedConditions.elementAttributeToMatch( img, "src", EXPECTED_OF_STR );
			SiteSessionManager.get().createCheckPoint( container, "ON_THE_SHIP_AFTER_HOVER" )
					.assertWaitThat( String.format( REASON, dataDefault ), FIVE_SECONDS, CONDITION_BOOL );

			/* AND It should take the user to /content/rookie/rookie.aspx */

			BeginnersGuidePage guidePage = ( BeginnersGuidePage )
					homePage.header().navigationAdditional().clickOnMenuItem( LEARN, ON_THE_SHIP );
			container = guidePage.header().getContainer();

			/* AND the url reference should contains #on-ship" */

			REASON = "asserting that \"url\" contains the reference \"on-ship\"";
			EXPECTED_OF_STR = is( "on-ship" );
			String ACTUAL_STR = guidePage.getURL().getRef();
			SiteSessionManager.get().createCheckPoint( container, "ON_THE_SHIP_URL_REF" )
					.assertThat( REASON, ACTUAL_STR, EXPECTED_OF_STR );

			/* AND the active link on navigation sticker should be "On the Ship" */

			REASON = "asserting that \"on the ship\" is the active link on navigation sticker";
			EXPECTED_OF_NAV = is( NavStickEmbeddedObject.NavStickItem.ON_THE_SHIP );
			ACTUAL_NAV = guidePage.navStickEmbedded().getActiveItem();
			nsc = guidePage.navStickEmbedded().getNavStickemContainer();
			SiteSessionManager.get().createCheckPoint( nsc, "ON_THE_SHIP_ACTIVE_STICK_ITEM" )
					.assertThat( REASON, ACTUAL_NAV, EXPECTED_OF_NAV );

			/* AND WHEN the user navigates back to home page, hovers and clicks on "Shore Excursions" item." */

			guidePage.scrollToTop();
			this.homePage = guidePage.header().headerBranding().clickOnLogo();
			this.homePage.header().headerLinks().hoverOnItem( LEARN );
			Link shoreEx = this.homePage.header().navigationAdditional().getLink( LEARN, SHORE_EXCURSIONS );
			dataDefault = this.homePage.header().navigationAdditional().getDataDefaultImg( shoreEx );
			dataHover = this.homePage.header().navigationAdditional().getDataHoverImg( shoreEx );
			img = this.homePage.header().navigationAdditional().getImage( shoreEx );
			container = homePage.header().getContainer();

			/* The image should change when hover to IMG-hover. */

			REASON = "asserting that \"shore excursions\" img[src] value before hovering ends with < '%s' >";
			EXPECTED_OF_STR = endsWith( dataDefault );
			CONDITION_BOOL = ExpectedConditions.elementAttributeToMatch( img, "src", EXPECTED_OF_STR );
			SiteSessionManager.get().createCheckPoint( container, "SHORE_EXCURSIONS_BEFORE_HOVER" )
					.assertWaitThat( String.format( REASON, dataDefault ), FIVE_SECONDS, CONDITION_BOOL );

			this.homePage.header().navigationAdditional().hoverOnMenuItem( shoreEx );

			REASON = "asserting that \"shore excursions\"  img[src] value after hovering ends with < '%s' >";
			EXPECTED_OF_STR = endsWith( dataHover );
			CONDITION_BOOL = ExpectedConditions.elementAttributeToMatch( img, "src", EXPECTED_OF_STR );
			SiteSessionManager.get().createCheckPoint( container, "SHORE_EXCURSIONS_AFTER_HOVER" )
					.assertWaitThat( String.format( REASON, dataDefault ), FIVE_SECONDS, CONDITION_BOOL );

			/* THEN It should take the user to /content/rookie/rookie.aspx" */

			guidePage = ( BeginnersGuidePage )
					this.homePage.header().navigationAdditional().clickOnMenuItem( shoreEx, SHORE_EXCURSIONS );
			container = guidePage.header().getContainer();

			/* AND the url reference should contains #shore-excursions" */

			REASON = "asserting that \"url\" contains the reference \"shore-excursions\"";
			EXPECTED_OF_STR = is( "shore-excursions" );
			ACTUAL_STR = guidePage.getURL().getRef();
			SiteSessionManager.get().createCheckPoint( container, "SHORE_EXCURSIONS_URL_REF" )
					.assertThat( REASON, ACTUAL_STR, EXPECTED_OF_STR );

			/* AND the active link on navigation sticker should be "Shore Excursions" */

			REASON = "asserting that \"shore-excursions\" is the active link on navigation sticker";
			EXPECTED_OF_NAV = is( NavStickEmbeddedObject.NavStickItem.SHORE_EXCURSIONS );
			ACTUAL_NAV = guidePage.navStickEmbedded().getActiveItem();
			nsc = guidePage.navStickEmbedded().getNavStickemContainer();
			SiteSessionManager.get().createCheckPoint( nsc, "SHORE_EXCURSIONS_ACTIVE_STICK_ITEM" )
					.assertThat( REASON, ACTUAL_NAV, EXPECTED_OF_NAV );

			/* AND WHEN the user navigates back to home page, hovers and clicks on "Destinations" item. */

			guidePage.scrollToTop();
			this.homePage = guidePage.header().headerBranding().clickOnLogo();
			this.homePage.header().headerLinks().hoverOnItem( LEARN );
			Link destinations = this.homePage.header().navigationAdditional().getLink( LEARN, DESTINATIONS );
			dataDefault = this.homePage.header().navigationAdditional().getDataDefaultImg( destinations );
			dataHover = this.homePage.header().navigationAdditional().getDataHoverImg( destinations );
			img = this.homePage.header().navigationAdditional().getImage( destinations );

			/* The image should change when hover to IMG-hover. */

			REASON = "asserting that \"destinations\" img[src] value before hovering ends with < '%s' >";
			EXPECTED_OF_STR = endsWith( dataDefault );
			CONDITION_BOOL = ExpectedConditions.elementAttributeToMatch( img, "src", EXPECTED_OF_STR );
			SiteSessionManager.get().createCheckPoint( container, "DESTINATIONS_BEFORE_HOVER" )
					.assertWaitThat( String.format( REASON, dataDefault ), FIVE_SECONDS, CONDITION_BOOL );

			this.homePage.header().navigationAdditional().hoverOnMenuItem( destinations );

			REASON = "asserting that \"shore excursions\" img[src] value after hovering ends with < '%s' >";
			EXPECTED_OF_STR = endsWith( dataHover );
			CONDITION_BOOL = ExpectedConditions.elementAttributeToMatch( img, "src", EXPECTED_OF_STR );
			SiteSessionManager.get().createCheckPoint( container, "DESTINATIONS_AFTER_HOVER" )
					.assertWaitThat( String.format( REASON, dataDefault ), FIVE_SECONDS, CONDITION_BOOL );

			/* THEN It should take the user to /content/rookie/rookie.aspx */

		 	guidePage = ( BeginnersGuidePage )
					this.homePage.header().navigationAdditional().clickOnMenuItem( destinations, DESTINATIONS );

			/* AND the url reference should contains #destinations */

			REASON = "asserting that \"url\" contains the reference \"destinations\"";
			EXPECTED_OF_STR = is( "destinations" );
			ACTUAL_STR = guidePage.getURL().getRef();
			SiteSessionManager.get().createCheckPoint( "DESTINATIONS_EXCURSIONS_URL_REF" )
					.assertThat( REASON, ACTUAL_STR, EXPECTED_OF_STR );

			/* AND the active link on navigation sticker should be "Destinations" */

			REASON = "asserting that \"shore-excursions\" is the active link on navigation sticker";
			EXPECTED_OF_NAV = is( NavStickEmbeddedObject.NavStickItem.DESTINATIONS );
			ACTUAL_NAV = guidePage.navStickEmbedded().getActiveItem();
			nsc = guidePage.navStickEmbedded().getNavStickemContainer();
			SiteSessionManager.get().createCheckPoint( nsc, "DESTINATIONS_ACTIVE_STICK_ITEM" )
					.assertThat( REASON, ACTUAL_NAV, EXPECTED_OF_NAV );

			guidePage.scrollToTop();
			guidePage.header().headerBranding().clickOnLogo();

			SiteSessionManager.get().assertAllCheckpoints();
		}
		catch ( WebDriverException | AssertionError e )
		{
			throw new ApplicationException( SiteSessionManager.get().getDriver(), e );
		}
	}

	//endregion

	//region NavigationAdditionalTest - header_SubNavigation_Learn_Actions_AU

	@TestCaseId ( id = 40488 )
	@Steps ( steps = {
			@Step ( number = 1, description = "GIVEN user is on carnival home page" ),
			@Step ( number = 2, description = "WHEN user hovers over \"Learn\" tab",
					expectedResults = {
							"THEN should see No more than 6 items on the additional level-2 menu.",
							"AND The items appearance order matches the required order based on the current locale."
					} ),
			@Step ( number = 3, description = "AND WHEN the user hovers and clicks on \"Why Carnival?\" item.",
					expectedResults = {
							"THEN The image should change when hover to IMG-hover.",
							"AND It should take the user to /cruising.aspx.",
							"AND The \"LEARN\" level 1 menu item should be active."
					} ),
			@Step ( number = 4, description = "AND WHEN the user hovers and clicks on \"What's it like?\" item.",
					expectedResults = {
							"THEN The image should change when hover to IMG-hover.",
							"AND it should take the user to /what-to-expect-on-a-cruise.aspx.",
							"AND The \"LEARN\" level 1 menu item should be active."
					} ),
			@Step ( number = 5, description = "AND WHEN the user hovers and clicks on \"Where can I go?\" item.",
					expectedResults = {
							"THEN the image should change when hover to IMG-hover.",
							"THEN It should take the user to /cruise-destinations-and-ports.aspx.",
							"AND The \"LEARN\" level 1 menu item should be active."
					} )
	} )
	@Test( description = "Header - SubNavigation - Learn - Actions - Verify SubNavigation associated links",
			enabled = false,
			groups = { "AU", "PROD" } )
	public void header_SubNavigation_Learn_Actions_AU() throws Exception
	{
	/* GIVEN user is on carnival home page" */
		PreConditions.checkNotNull( this.homePage, "Home page is null, before starting test" );
		try
		{
			Integer maxMenuItems = Integer.valueOf( SiteProperty.MAX_MENU_SUB_ITEMS.fromContext().toString() );
			String names = SiteProperty.LEARN_MENU_ITEMS.fromContext().toString();
			HtmlElement container = homePage.header().getContainer();
			Header.NavigationAdditional navigationAdditional = homePage.header().navigationAdditional();
			Header.HeaderLinks headerLinks = homePage.header().headerLinks();

			/* WHEN user hovers over "Learn" tab */

			headerLinks.hoverOnItem( LEARN );

			/* should see No more than 6 items on the additional level-2 menu */
			String REASON = "Asserting that menu sub-items are displayed";
			boolean ACTUAL_BOOL = navigationAdditional.isDisplayed();
			Matcher<Boolean> EXPECTED_OF_BOOL = is( true );
			SiteSessionManager.get().createCheckPoint( container, "LEARN_ADDITIONAL_LINKS_DISPLAYED" )
					.assertThat( REASON, ACTUAL_BOOL, EXPECTED_OF_BOOL );

			List<String> itemsNames = navigationAdditional.getChildMenuItemsNames( LEARN );
			REASON = "Asserting that we should see No more than 6 items on the additional level-2 menu";
			Matcher<Collection<? extends String>> EXPECTED_RESULT_OF_COLL_STR = hasSize( lessThanOrEqualTo( maxMenuItems ) );
			SiteSessionManager.get().createCheckPoint( container, "LEARN_NO_MORE_THAN_6" )
					.assertThat( REASON, itemsNames, EXPECTED_RESULT_OF_COLL_STR );

			/* AND The items appearance order matches the required order based on the current locale. */

			REASON = "Asserting that all level-2 items appearance order matches the required order based on the current locale";
			String[] ACTUAL_OF_STR_ARR = itemsNames.toArray( new String[ itemsNames.size() ] );
			Matcher<String[]> EXPECTED_OF_STR_ARR = arrayContaining( StringUtils.split( names, "," ) );
			SiteSessionManager.get().createCheckPoint( container, "LEARN_LEVEL_2_ITEMS" )
					.assertThat( REASON, ACTUAL_OF_STR_ARR, EXPECTED_OF_STR_ARR );

			/* AND WHEN the user hovers and clicks on "Why Carnival?" item. */
			Link whyCarnival = navigationAdditional.getLink( LEARN, WHY_CARNIVAL );
			String dataDefault = navigationAdditional.getDataDefaultImg( whyCarnival );
			HtmlElement img = navigationAdditional.getImage( whyCarnival );

			/* The image should change when hover to IMG-hover. */
			REASON = "asserting that  \"why carnival\" img[src] value before hovering ends with < '%s' >";
			Matcher<String> EXPECTED_OF_STR = endsWith( dataDefault );
			HtmlCondition<Boolean> CONDITION_BOOL = ExpectedConditions.elementAttributeToMatch( img, "src", EXPECTED_OF_STR );
			SiteSessionManager.get().createCheckPoint( container, "WHY_CARNIVAL_BEFORE_HOVER" )
					.assertWaitThat( String.format( REASON, dataDefault ), FIVE_SECONDS, CONDITION_BOOL );

			navigationAdditional.hoverOnMenuItem( whyCarnival );

			String dataHover = navigationAdditional.getDataHoverImg( whyCarnival );
			REASON = "asserting that \"why carnival\" img[src] value after hovering ends with < '%s' >";
			EXPECTED_OF_STR = endsWith( dataHover );
			CONDITION_BOOL = ExpectedConditions.elementAttributeToMatch( img, "src", EXPECTED_OF_STR );
			SiteSessionManager.get().createCheckPoint( container, "WHY_CARNIVAL_AFTER_HOVER" )
					.assertWaitThat( String.format( REASON, dataDefault ), FIVE_SECONDS, CONDITION_BOOL );

			/* THEN It should take the user to /cruising.aspx. */
			CruisingPage cruisingPage = ( CruisingPage ) navigationAdditional.clickOnMenuItem( whyCarnival, WHY_CARNIVAL );
			navigationAdditional = cruisingPage.header().navigationAdditional();
			headerLinks = cruisingPage.header().headerLinks();
			container = cruisingPage.header().getContainer();

			/* AND the "LEARN" level 1 menu item should be active. */
			Link learn = headerLinks.getLink( LEARN );
			REASON = "asserting that \"LEARN\" menu item is 'active'";
			EXPECTED_OF_STR = containsString( "active" );
			String ACTUAL_STR = learn.getHtmlElement().getAttribute( "class" );
			SiteSessionManager.get().createCheckPoint( container, "LEARN_IS_ACTIVE_ON_WHY_CARNIVAL" )
					.assertThat( REASON, ACTUAL_STR, EXPECTED_OF_STR );

			/* AND WHEN the user hovers and clicks on "What's it like?" item. */
			headerLinks.hoverOnItem( LEARN );
			Link whatIsLike = navigationAdditional.getLink( LEARN, WHATS_IT_LIKE );
			dataDefault = navigationAdditional.getDataDefaultImg( whatIsLike );
			dataHover = navigationAdditional.getDataHoverImg( whatIsLike );
			img = navigationAdditional.getImage( whatIsLike );

			/* The image should change when hover to IMG-hover. */

			REASON = "asserting that \"what is like\" img[src] value before hovering ends with < '%s' >";
			EXPECTED_OF_STR = endsWith( dataDefault );
			CONDITION_BOOL = ExpectedConditions.elementAttributeToMatch( img, "src", EXPECTED_OF_STR );
			SiteSessionManager.get().createCheckPoint( container, "WHAT_IS_LIKE_BEFORE_HOVER" )
					.assertWaitThat( String.format( REASON, dataDefault ), FIVE_SECONDS, CONDITION_BOOL );

			navigationAdditional.hoverOnMenuItem( whatIsLike );

			REASON = "asserting that \"what is like\"  img[src] value after hovering ends with < '%s' >";
			EXPECTED_OF_STR = endsWith( dataHover );
			CONDITION_BOOL = ExpectedConditions.elementAttributeToMatch( img, "src", EXPECTED_OF_STR );
			SiteSessionManager.get().createCheckPoint( container, "WHAT_IS_LIKE_AFTER_HOVER" )
					.assertWaitThat( String.format( REASON, dataDefault ), FIVE_SECONDS, CONDITION_BOOL );

			/* THEN It should take the user to /what-to-expect-on-a-cruise.aspx. */

			What2ExpectPage what2ExpectPage = ( What2ExpectPage )
					navigationAdditional.clickOnMenuItem( whatIsLike, WHATS_IT_LIKE );
			headerLinks = what2ExpectPage.header().headerLinks();
			navigationAdditional = what2ExpectPage.header().navigationAdditional();
			container = what2ExpectPage.header().getContainer();

			/* AND The "LEARN" level 1 menu item should be active. */

			learn = headerLinks.getLink( LEARN );

			REASON = "asserting that \"LEARN\" menu item is 'active'";
			EXPECTED_OF_STR = containsString( "active" );
			ACTUAL_STR = learn.getHtmlElement().getAttribute( "class" );
			SiteSessionManager.get().createCheckPoint( container, "LEARN_IS_ACTIVE_ON_WHATS_IT_LIKE" )
					.assertThat( REASON, ACTUAL_STR, EXPECTED_OF_STR );

			/* AND WHEN the user hovers and clicks on "Where can I go?" item. */

			what2ExpectPage.header().headerLinks().hoverOnItem( LEARN );

			Link whereCanIgo = navigationAdditional.getLink( LEARN, WHERE_CAN_I_GO );
			dataDefault = navigationAdditional.getDataDefaultImg( whereCanIgo );
			dataHover = navigationAdditional.getDataHoverImg( whereCanIgo );
			img = navigationAdditional.getImage( whereCanIgo );

			/* The image should change when hover to IMG-hover. */

			REASON = "asserting that \"where can i go?\" img[src] value before hovering ends with < '%s' >";
			EXPECTED_OF_STR = endsWith( dataDefault );
			CONDITION_BOOL = ExpectedConditions.elementAttributeToMatch( img, "src", EXPECTED_OF_STR );
			SiteSessionManager.get().createCheckPoint( container, "WHERE_CAN_I_GO_BEFORE_HOVER" )
					.assertWaitThat( String.format( REASON, dataDefault ), FIVE_SECONDS, CONDITION_BOOL );

			navigationAdditional.hoverOnMenuItem( whereCanIgo );

			REASON = "asserting that \"where can i go?\"  img[src] value after hovering ends with < '%s' >";
			EXPECTED_OF_STR = endsWith( dataHover );
			CONDITION_BOOL = ExpectedConditions.elementAttributeToMatch( img, "src", EXPECTED_OF_STR );
			SiteSessionManager.get().createCheckPoint( container, "WHERE_CAN_I_GO_AFTER_HOVER" )
					.assertWaitThat( String.format( REASON, dataDefault ), FIVE_SECONDS, CONDITION_BOOL );

			/* It should take the user to /cruise-destinations-and-ports.aspx. */

			CruiseDestinationsAndPortsPage destinationsPage = ( CruiseDestinationsAndPortsPage )
					navigationAdditional.clickOnMenuItem( whereCanIgo, WHERE_CAN_I_GO );
			headerLinks = destinationsPage.header().headerLinks();
			container = destinationsPage.header().getContainer();

			/* The "LEARN" level 1 menu item should be active.*/

			learn = headerLinks.getLink( LEARN );

			REASON = "asserting that \"LEARN\" menu item is 'active'";
			EXPECTED_OF_STR = containsString( "active" );
			ACTUAL_STR = learn.getHtmlElement().getAttribute( "class" );
			SiteSessionManager.get().createCheckPoint( container, "LEARN_IS_ACTIVE_ON_WHERE_CAN_I_GO" )
					.assertThat( REASON, ACTUAL_STR, EXPECTED_OF_STR );

			destinationsPage.header().headerBranding().clickOnLogo();

			SiteSessionManager.get().assertAllCheckpoints();
		}
		catch ( WebDriverException | AssertionError e )
		{
			throw new ApplicationException( SiteSessionManager.get().getDriver(), e );
		}
	}

	//endregion

	//region NavigationAdditionalTest - header_SubNavigation_Plan_Actions_US

	@TestCaseId ( id = 40500 )
	@Steps ( steps = {
			@Step ( number = 1, description = "GIVEN user is on carnival home page" ),
			@Step ( number = 2, description = "WHEN user hovers over \"Plan\" tab",
					expectedResults = {
							"THEN should see No more than 6 items on the additional level-2 menu.",
							"AND The items appearance order matches the required order based on the current locale."
					} ),
			@Step ( number = 3, description = "AND WHEN the user hovers and clicks on \"Find a Cruise\" item.",
					expectedResults = {
							"THEN The image should change when hover to IMG-hover.",
							"AND it should take the user to Find a Cruise landing page: /bookingengine/findacruise",
							"AND The \"PLAN\" level 1 menu item should be active."
					} ),
			@Step ( number = 4, description = "AND WHEN the user hovers and clicks on \"Find a Port\" item.",
					expectedResults = {
							"THEN The image should change when hover to IMG-hover.",
							"AND it should take the user to Cruises page: /cruise-from.aspx.",
							"AND The \"PLAN\" level 1 menu item should be active."
					} ),
			@Step ( number = 5, description = "AND WHEN the user hovers and clicks on \"FAQs\" item.",
					expectedResults = {
							"THEN the image should change when hover to IMG-hover.",
							"THEN it should take the user to FAQS landing page: /core/faq.aspx.",
							"AND The \"PLAN\" level 1 menu item should be active."
					} ),
			@Step ( number = 6, description = "AND WHEN the user hovers and clicks on \"Forums\" item.",
					expectedResults = {
							"THEN the image should change when hover to IMG-hover.",
							"THEN it should take the user to forums landing page: /Funville/forums/",
							"AND The \"PLAN\" level 1 menu item should be active."
					} )
	} )
	@Test( description = "Header - Sub Navigation - Plan - Actions - Verify SubNavigation associated links",
			enabled = false,
			groups = { "US", "PROD" } )
	public void header_SubNavigation_Plan_Actions_US() throws Exception
	{
		/* GIVEN user is on carnival home page" */
		PreConditions.checkNotNull( this.homePage, "Home page is null, before starting test" );
		try
		{
			Integer maxMenuItems = Integer.valueOf( SiteProperty.MAX_MENU_SUB_ITEMS.fromContext().toString() );
			String names = SiteProperty.PLAN_MENU_ITEMS.fromContext().toString();
			HtmlElement container = homePage.header().getContainer();
			Header.NavigationAdditional navigationAdditional = homePage.header().navigationAdditional();
			Header.HeaderLinks headerLinks = homePage.header().headerLinks();

			/* WHEN user hovers over "Plan" tab */

			headerLinks.hoverOnItem( PLAN );

			/* should see No more than 6 items on the additional level-2 menu */
			String REASON = "Asserting that menu sub-items are displayed";
			boolean ACTUAL_BOOL = navigationAdditional.isDisplayed();
			Matcher<Boolean> EXPECTED_OF_BOOL = is( true );
			SiteSessionManager.get().createCheckPoint( container, "PLAN_ADDITIONAL_LINKS_DISPLAYED" )
					.assertThat( REASON, ACTUAL_BOOL, EXPECTED_OF_BOOL );

			List<String> itemsNames = navigationAdditional.getChildMenuItemsNames( PLAN );
			REASON = "Asserting that we should see No more than 6 items on the additional level-2 menu";
			Matcher<Collection<? extends String>> EXPECTED_RESULT_OF_COLL_STR = hasSize( lessThanOrEqualTo( maxMenuItems ) );
			SiteSessionManager.get().createCheckPoint( container, "PLAN_NO_MORE_THAN_6" )
					.assertThat( REASON, itemsNames, EXPECTED_RESULT_OF_COLL_STR );

			/* AND The items appearance order matches the required order based on the current locale. */

			REASON = "Asserting that all level-2 items appearance order matches the required order based on the current locale";
			String[] ACTUAL_OF_STR_ARR = itemsNames.toArray( new String[ itemsNames.size() ] );
			Matcher<String[]> EXPECTED_OF_STR_ARR = arrayContaining( StringUtils.split( names, "," ) );
			SiteSessionManager.get().createCheckPoint( container, "PLAN_LEVEL_2_ITEMS" )
					.assertThat( REASON, ACTUAL_OF_STR_ARR, EXPECTED_OF_STR_ARR );

			/* AND WHEN the user hovers and clicks on "Find a Cruise" item. */
			Link findCruise = navigationAdditional.getLink( PLAN, FIND_A_CRUISE );
			String dataDefault = navigationAdditional.getDataDefaultImg( findCruise );
			HtmlElement img = navigationAdditional.getImage( findCruise );

			/* The image should change when hover to IMG-hover. */
			REASON = "asserting that  \"find a cruise\" img[src] value before hovering ends with < '%s' >";
			Matcher<String> EXPECTED_OF_STR = endsWith( dataDefault );
			HtmlCondition<Boolean> CONDITION_BOOL = ExpectedConditions.elementAttributeToMatch( img, "src", EXPECTED_OF_STR );
			SiteSessionManager.get().createCheckPoint( container, "FIND_A_CRUISE_BEFORE_HOVER" )
					.assertWaitThat( String.format( REASON, dataDefault ), FIVE_SECONDS, CONDITION_BOOL );

			navigationAdditional.hoverOnMenuItem( findCruise );

			String dataHover = navigationAdditional.getDataHoverImg( findCruise );
			REASON = "asserting that \"find a cruise\" img[src] value after hovering ends with < '%s' >";
			EXPECTED_OF_STR = endsWith( dataHover );
			CONDITION_BOOL = ExpectedConditions.elementAttributeToMatch( img, "src", EXPECTED_OF_STR );
			SiteSessionManager.get().createCheckPoint( container, "FIND_A_CRUISE_AFTER_HOVER" )
					.assertWaitThat( String.format( REASON, dataDefault ), FIVE_SECONDS, CONDITION_BOOL );

			/* AND it should take the user to Find a Cruise landing page: /bookingengine/findacruise */
			FindACruisePage findACruisePage = ( FindACruisePage ) navigationAdditional.clickOnMenuItem( findCruise, FIND_A_CRUISE );
			navigationAdditional = findACruisePage.header().navigationAdditional();
			headerLinks = findACruisePage.header().headerLinks();
			container = findACruisePage.header().getContainer();

			/* AND the "PLAN" level 1 menu item should be active. */
			Link learn = headerLinks.getLink( PLAN );
			REASON = "asserting that \"PLAN\" menu item is 'active'";
			EXPECTED_OF_STR = containsString( "active" );
			String ACTUAL_STR = learn.getHtmlElement().getAttribute( "class" );
			SiteSessionManager.get().createCheckPoint( "PLAN_IS_ACTIVE_ON_FIND_A_CRUISE" )
					.assertThat( REASON, ACTUAL_STR, EXPECTED_OF_STR );

			/* AND WHEN the user hovers and clicks on "Find a Port" item. */
			headerLinks.hoverOnItem( PLAN );
			Link findPort = navigationAdditional.getLink( PLAN, FIND_A_PORT );
			dataDefault = navigationAdditional.getDataDefaultImg( findPort );
			dataHover = navigationAdditional.getDataHoverImg( findPort );
			img = navigationAdditional.getImage( findPort );

			/* The image should change when hover to IMG-hover. */

			REASON = "asserting that \"find a port\" img[src] value before hovering ends with < '%s' >";
			EXPECTED_OF_STR = endsWith( dataDefault );
			CONDITION_BOOL = ExpectedConditions.elementAttributeToMatch( img, "src", EXPECTED_OF_STR );
			SiteSessionManager.get().createCheckPoint( container, "FIND_A_PORT_BEFORE_HOVER" )
					.assertWaitThat( String.format( REASON, dataDefault ), FIVE_SECONDS, CONDITION_BOOL );

			navigationAdditional.hoverOnMenuItem( findPort );

			REASON = "asserting that \"find a port\"  img[src] value after hovering ends with < '%s' >";
			EXPECTED_OF_STR = endsWith( dataHover );
			CONDITION_BOOL = ExpectedConditions.elementAttributeToMatch( img, "src", EXPECTED_OF_STR );
			SiteSessionManager.get().createCheckPoint( container, "FIND_A_PORT_AFTER_HOVER" )
					.assertWaitThat( String.format( REASON, dataDefault ), FIVE_SECONDS, CONDITION_BOOL );

			/**
			 * March-11-2015 changed by marketing
			 */
			/* AND it should take the user to Close to Home page: /cruise-from.aspx. */

			CruiseFromPortPage closeToHomePage = ( CruiseFromPortPage )
					navigationAdditional.clickOnMenuItem( findPort, FIND_A_PORT );
			navigationAdditional = closeToHomePage.header().navigationAdditional();
			headerLinks = closeToHomePage.header().headerLinks();
			container = closeToHomePage.header().getContainer();

			/* The "PLAN" level 1 menu item should be active.*/

			learn = headerLinks.getLink( PLAN );

			REASON = "asserting that \"PLAN\" menu item is 'active'";
			EXPECTED_OF_STR = containsString( "active" );
			ACTUAL_STR = learn.getHtmlElement().getAttribute( "class" );
			SiteSessionManager.get().createCheckPoint( "PLAN_IS_ACTIVE_ON_FIND_A_PORT" )
					.assertThat( REASON, ACTUAL_STR, EXPECTED_OF_STR );

			/* AND the user hovers and clicks on "FAQ's" item. */

			headerLinks.hoverOnItem( PLAN );

			Link faqs = navigationAdditional.getLink( PLAN, FAQ_S );
			dataDefault = navigationAdditional.getDataDefaultImg( faqs );
			dataHover = navigationAdditional.getDataHoverImg( faqs );
			img = navigationAdditional.getImage( faqs );

			/* The image should change when hover to IMG-hover. */

			REASON = "asserting that \"FAQ's\" img[src] value before hovering ends with < '%s' >";
			EXPECTED_OF_STR = endsWith( dataDefault );
			CONDITION_BOOL = ExpectedConditions.elementAttributeToMatch( img, "src", EXPECTED_OF_STR );
			SiteSessionManager.get().createCheckPoint( container, "FAQS_BEFORE_HOVER" )
					.assertWaitThat( String.format( REASON, dataDefault ), FIVE_SECONDS, CONDITION_BOOL );

			navigationAdditional.hoverOnMenuItem( faqs );

			REASON = "asserting that \"faqs\" img[src] value after hovering ends with < '%s' >";
			EXPECTED_OF_STR = endsWith( dataHover );
			CONDITION_BOOL = ExpectedConditions.elementAttributeToMatch( img, "src", EXPECTED_OF_STR );
			SiteSessionManager.get().createCheckPoint( container, "FAQS_AFTER_HOVER" )
					.assertWaitThat( String.format( REASON, dataDefault ), FIVE_SECONDS, CONDITION_BOOL );

			/* THEN it should take the user to FAQS landing page: /core/faq.aspx. */

			FaqPage faqPage = ( FaqPage )
					navigationAdditional.clickOnMenuItem( faqs, FAQ_S );
			navigationAdditional = faqPage.header().navigationAdditional();
			headerLinks = faqPage.header().headerLinks();
			container = faqPage.header().getContainer();

			/* The "LEARN" level 1 menu item should be active. */

			learn = headerLinks.getLink( PLAN );

			REASON = "asserting that \"PLAN\" menu item is 'active'";
			EXPECTED_OF_STR = containsString( "active" );
			ACTUAL_STR = learn.getHtmlElement().getAttribute( "class" );
			SiteSessionManager.get().createCheckPoint( "PLAN_IS_ACTIVE_ON_FAQS" )
					.assertThat( REASON, ACTUAL_STR, EXPECTED_OF_STR );

			/* AND the user hovers and clicks on "Forums" item. */
			headerLinks.hoverOnItem( PLAN );

			Link forums = navigationAdditional.getLink( PLAN, FORUMS );
			dataDefault = navigationAdditional.getDataDefaultImg( forums );
			dataHover = navigationAdditional.getDataHoverImg( forums );
			img = navigationAdditional.getImage( forums );

			/* The image should change when hover to IMG-hover. */

			REASON = "asserting that \"forums\" img[src] value before hovering ends with < '%s' >";
			EXPECTED_OF_STR = endsWith( dataDefault );
			CONDITION_BOOL = ExpectedConditions.elementAttributeToMatch( img, "src", EXPECTED_OF_STR );
			SiteSessionManager.get().createCheckPoint( container, "FORUMS_BEFORE_HOVER" )
					.assertWaitThat( String.format( REASON, dataDefault ), FIVE_SECONDS, CONDITION_BOOL );

			navigationAdditional.hoverOnMenuItem( forums );

			REASON = "asserting that \"forums\" img[src] value after hovering ends with < '%s' >";
			EXPECTED_OF_STR = endsWith( dataHover );
			CONDITION_BOOL = ExpectedConditions.elementAttributeToMatch( img, "src", EXPECTED_OF_STR );
			SiteSessionManager.get().createCheckPoint( container, "FORUMS_AFTER_HOVER" )
					.assertWaitThat( String.format( REASON, dataDefault ), FIVE_SECONDS, CONDITION_BOOL );

			/* THEN it should take the user to forums landing page: /Funville/forums/ */

			ForumsPage forumsPage = ( ForumsPage )
					navigationAdditional.clickOnMenuItem( forums, FORUMS );
			headerLinks = forumsPage.header().headerLinks();

			/* The "LEARN" level 1 menu item should be active.*/

			learn = headerLinks.getLink( PLAN );

			REASON = "asserting that \"PLAN\" menu item is 'active'";
			EXPECTED_OF_STR = containsString( "active" );
			ACTUAL_STR = learn.getHtmlElement().getAttribute( "class" );
			SiteSessionManager.get().createCheckPoint( "PLAN_IS_ACTIVE_ON_FORUMS" )
					.assertThat( REASON, ACTUAL_STR, EXPECTED_OF_STR );

			forumsPage.header().headerBranding().clickOnLogo();

			SiteSessionManager.get().assertAllCheckpoints();
		}
		catch ( WebDriverException | AssertionError e )
		{
			throw new ApplicationException( SiteSessionManager.get().getDriver(), e );
		}
	}

	//endregion

	//region NavigationAdditionalTest - header_SubNavigation_Plan_Actions_AU

	@TestCaseId ( id = 40500 )
	@Steps ( steps = {
			@Step ( number = 1, description = "GIVEN user is on carnival home page" ),
			@Step ( number = 2, description = "WHEN user hovers over \"Plan\" tab",
					expectedResults = {
							"THEN should see No more than 6 items on the additional level-2 menu.",
							"AND The items appearance order matches the required order based on the current locale."
					} ),
			@Step ( number = 3, description = "AND WHEN the user hovers and clicks on \"Find a Cruise\" item.",
					expectedResults = {
							"THEN The image should change when hover to IMG-hover.",
							"AND it should take the user to Find a Cruise landing page: /bookingengine/findacruise",
							"AND The \"PLAN\" level 1 menu item should be active."
					} ),
			@Step ( number = 4, description = "AND WHEN the user hovers and clicks on \"Your Adventure\" item.",
					expectedResults = {
							"THEN The image should change when hover to IMG-hover.",
							"AND it should take the user to page: http://youradventure.carnival.com.au/"
					} ),
			@Step ( number = 5, description = "AND WHEN the user hovers and clicks on \"FAQs\" item.",
					expectedResults = {
							"THEN the image should change when hover to IMG-hover.",
							"THEN it should take the user to FAQS landing page: /core/faq.aspx.",
							"AND The \"PLAN\" level 1 menu item should be active."
					} ),
	} )
	@Test( description = "Header - Sub Navigation - Plan - Actions - Verify SubNavigation associated links",
			enabled = false,
			groups = { "UK", "PROD" } )
	public void header_SubNavigation_Plan_Actions_AU() throws Exception
	{
		/* GIVEN user is on carnival home page" */
		PreConditions.checkNotNull( this.homePage, "Home page is null, before starting test" );
		try
		{
			Integer maxMenuItems = Integer.valueOf( SiteProperty.MAX_MENU_SUB_ITEMS.fromContext().toString() );
			String names = SiteProperty.PLAN_MENU_ITEMS.fromContext().toString();
			HtmlElement container = homePage.header().getContainer();
			Header.NavigationAdditional navigationAdditional = homePage.header().navigationAdditional();
			Header.HeaderLinks headerLinks = homePage.header().headerLinks();

			/* WHEN user hovers over "Plan" tab */

			headerLinks.hoverOnItem( PLAN );

			/* should see No more than 6 items on the additional level-2 menu */
			String REASON = "Asserting that menu sub-items are displayed";
			boolean ACTUAL_BOOL = navigationAdditional.isDisplayed();
			Matcher<Boolean> EXPECTED_OF_BOOL = is( true );
			SiteSessionManager.get().createCheckPoint( container, "PLAN_ADDITIONAL_LINKS_DISPLAYED" )
					.assertThat( REASON, ACTUAL_BOOL, EXPECTED_OF_BOOL );

			List<String> itemsNames = navigationAdditional.getChildMenuItemsNames( PLAN );
			REASON = "Asserting that we should see No more than 6 items on the additional level-2 menu";
			Matcher<Collection<? extends String>> EXPECTED_RESULT_OF_COLL_STR = hasSize( lessThanOrEqualTo( maxMenuItems ) );
			SiteSessionManager.get().createCheckPoint( container, "PLAN_NO_MORE_THAN_6" )
					.assertThat( REASON, itemsNames, EXPECTED_RESULT_OF_COLL_STR );

			/* AND The items appearance order matches the required order based on the current locale. */

			REASON = "Asserting that all level-2 items appearance order matches the required order based on the current locale";
			String[] ACTUAL_OF_STR_ARR = itemsNames.toArray( new String[ itemsNames.size() ] );
			Matcher<String[]> EXPECTED_OF_STR_ARR = arrayContaining( StringUtils.split( names, "," ) );
			SiteSessionManager.get().createCheckPoint( container, "PLAN_LEVEL_2_ITEMS" )
					.assertThat( REASON, ACTUAL_OF_STR_ARR, EXPECTED_OF_STR_ARR );

			/* AND WHEN the user hovers and clicks on "Find a Cruise" item. */
			Link findCruise = navigationAdditional.getLink( PLAN, FIND_A_CRUISE );
			String dataDefault = navigationAdditional.getDataDefaultImg( findCruise );
			HtmlElement img = navigationAdditional.getImage( findCruise );

			/* The image should change when hover to IMG-hover. */
			REASON = "asserting that  \"find a cruise\" img[src] value before hovering ends with < '%s' >";
			Matcher<String> EXPECTED_OF_STR = endsWith( dataDefault );
			HtmlCondition<Boolean> CONDITION_BOOL = ExpectedConditions.elementAttributeToMatch( img, "src", EXPECTED_OF_STR );
			SiteSessionManager.get().createCheckPoint( container, "FIND_A_CRUISE_BEFORE_HOVER" )
					.assertWaitThat( String.format( REASON, dataDefault ), FIVE_SECONDS, CONDITION_BOOL );

			navigationAdditional.hoverOnMenuItem( findCruise );

			String dataHover = navigationAdditional.getDataHoverImg( findCruise );
			REASON = "asserting that \"find a cruise\" img[src] value after hovering ends with < '%s' >";
			EXPECTED_OF_STR = endsWith( dataHover );
			CONDITION_BOOL = ExpectedConditions.elementAttributeToMatch( img, "src", EXPECTED_OF_STR );
			SiteSessionManager.get().createCheckPoint( container, "FIND_A_CRUISE_AFTER_HOVER" )
					.assertWaitThat( String.format( REASON, dataDefault ), FIVE_SECONDS, CONDITION_BOOL );

			/* AND it should take the user to Find a Cruise landing page: /bookingengine/findacruise */
			FindACruisePage findACruisePage = ( FindACruisePage ) navigationAdditional.clickOnMenuItem( findCruise, FIND_A_CRUISE );
			navigationAdditional = findACruisePage.header().navigationAdditional();
			headerLinks = findACruisePage.header().headerLinks();
			container = findACruisePage.header().getContainer();

			/* AND the "PLAN" level 1 menu item should be active. */
			Link learn = headerLinks.getLink( PLAN );
			REASON = "asserting that \"PLAN\" menu item is 'active'";
			EXPECTED_OF_STR = containsString( "active" );
			String ACTUAL_STR = learn.getHtmlElement().getAttribute( "class" );
			SiteSessionManager.get().createCheckPoint( "PLAN_IS_ACTIVE_ON_FIND_A_CRUISE" )
					.assertThat( REASON, ACTUAL_STR, EXPECTED_OF_STR );

			/* AND WHEN the user hovers and clicks on "Find a Port" item. */
			headerLinks.hoverOnItem( PLAN );
			Link findPort = navigationAdditional.getLink( PLAN, FIND_A_PORT );
			dataDefault = navigationAdditional.getDataDefaultImg( findPort );
			dataHover = navigationAdditional.getDataHoverImg( findPort );
			img = navigationAdditional.getImage( findPort );

			/* The image should change when hover to IMG-hover. */

			REASON = "asserting that \"find a port\" img[src] value before hovering ends with < '%s' >";
			EXPECTED_OF_STR = endsWith( dataDefault );
			CONDITION_BOOL = ExpectedConditions.elementAttributeToMatch( img, "src", EXPECTED_OF_STR );
			SiteSessionManager.get().createCheckPoint( container, "FIND_A_PORT_BEFORE_HOVER" )
					.assertWaitThat( String.format( REASON, dataDefault ), FIVE_SECONDS, CONDITION_BOOL );

			navigationAdditional.hoverOnMenuItem( findPort );

			REASON = "asserting that \"find a port\"  img[src] value after hovering ends with < '%s' >";
			EXPECTED_OF_STR = endsWith( dataHover );
			CONDITION_BOOL = ExpectedConditions.elementAttributeToMatch( img, "src", EXPECTED_OF_STR );
			SiteSessionManager.get().createCheckPoint( container, "FIND_A_PORT_AFTER_HOVER" )
					.assertWaitThat( String.format( REASON, dataDefault ), FIVE_SECONDS, CONDITION_BOOL );

			/* AND it should take the user to Close to Home page: /core/closetohome.aspx. */

			CloseToHomePage closeToHomePage = ( CloseToHomePage )
					navigationAdditional.clickOnMenuItem( findPort, FIND_A_PORT );
			navigationAdditional = closeToHomePage.header().navigationAdditional();
			headerLinks = closeToHomePage.header().headerLinks();
			container = closeToHomePage.header().getContainer();

			/* The "PLAN" level 1 menu item should be active.*/

			learn = headerLinks.getLink( PLAN );

			REASON = "asserting that \"PLAN\" menu item is 'active'";
			EXPECTED_OF_STR = containsString( "active" );
			ACTUAL_STR = learn.getHtmlElement().getAttribute( "class" );
			SiteSessionManager.get().createCheckPoint( "PLAN_IS_ACTIVE_ON_FIND_A_PORT" )
					.assertThat( REASON, ACTUAL_STR, EXPECTED_OF_STR );

			/* AND the user hovers and clicks on "FAQ's" item. */

			headerLinks.hoverOnItem( PLAN );

			Link faqs = navigationAdditional.getLink( PLAN, FAQ_S );
			dataDefault = navigationAdditional.getDataDefaultImg( faqs );
			dataHover = navigationAdditional.getDataHoverImg( faqs );
			img = navigationAdditional.getImage( faqs );

			/* The image should change when hover to IMG-hover. */

			REASON = "asserting that \"FAQ's\" img[src] value before hovering ends with < '%s' >";
			EXPECTED_OF_STR = endsWith( dataDefault );
			CONDITION_BOOL = ExpectedConditions.elementAttributeToMatch( img, "src", EXPECTED_OF_STR );
			SiteSessionManager.get().createCheckPoint( container, "FAQS_BEFORE_HOVER" )
					.assertWaitThat( String.format( REASON, dataDefault ), FIVE_SECONDS, CONDITION_BOOL );

			navigationAdditional.hoverOnMenuItem( faqs );

			REASON = "asserting that \"faqs\" img[src] value after hovering ends with < '%s' >";
			EXPECTED_OF_STR = endsWith( dataHover );
			CONDITION_BOOL = ExpectedConditions.elementAttributeToMatch( img, "src", EXPECTED_OF_STR );
			SiteSessionManager.get().createCheckPoint( container, "FAQS_AFTER_HOVER" )
					.assertWaitThat( String.format( REASON, dataDefault ), FIVE_SECONDS, CONDITION_BOOL );

			/* THEN it should take the user to FAQS landing page: /core/faq.aspx. */

			FaqPage faqPage = ( FaqPage )
					navigationAdditional.clickOnMenuItem( faqs, FAQ_S );
			navigationAdditional = faqPage.header().navigationAdditional();
			headerLinks = faqPage.header().headerLinks();
			container = faqPage.header().getContainer();

			/* The "LEARN" level 1 menu item should be active. */

			learn = headerLinks.getLink( PLAN );

			REASON = "asserting that \"PLAN\" menu item is 'active'";
			EXPECTED_OF_STR = containsString( "active" );
			ACTUAL_STR = learn.getHtmlElement().getAttribute( "class" );
			SiteSessionManager.get().createCheckPoint( "PLAN_IS_ACTIVE_ON_FAQS" )
					.assertThat( REASON, ACTUAL_STR, EXPECTED_OF_STR );

			faqPage.header().headerBranding().clickOnLogo();

			SiteSessionManager.get().assertAllCheckpoints();
		}
		catch ( WebDriverException | AssertionError e )
		{
			throw new ApplicationException( SiteSessionManager.get().getDriver(), e );
		}
	}

	//endregion

	//region NavigationAdditionalTest - header_SubNavigation_Plan_Actions_UK

	@TestCaseId ( id = 40500 )
	@Steps ( steps = {
			@Step ( number = 1, description = "GIVEN user is on carnival home page" ),
			@Step ( number = 2, description = "WHEN user hovers over \"Plan\" tab",
					expectedResults = {
							"THEN should see No more than 6 items on the additional level-2 menu.",
							"AND The items appearance order matches the required order based on the current locale."
					} ),
			@Step ( number = 3, description = "AND WHEN the user hovers and clicks on \"Find a Cruise\" item.",
					expectedResults = {
							"THEN The image should change when hover to IMG-hover.",
							"AND it should take the user to Find a Cruise landing page: /bookingengine/findacruise",
							"AND The \"PLAN\" level 1 menu item should be active."
					} ),
			@Step ( number = 4, description = "AND WHEN the user hovers and clicks on \"Find a Port\" item.",
					expectedResults = {
							"THEN The image should change when hover to IMG-hover.",
							"AND it should take the user to Close to Home page: /core/closetohome.aspx.",
							"AND The \"PLAN\" level 1 menu item should be active."
					} ),
			@Step ( number = 5, description = "AND WHEN the user hovers and clicks on \"FAQs\" item.",
					expectedResults = {
							"THEN the image should change when hover to IMG-hover.",
							"THEN it should take the user to FAQS landing page: /core/faq.aspx.",
							"AND The \"PLAN\" level 1 menu item should be active."
					} ),
	} )
	@Test( description = "Header - Sub Navigation - Plan - Actions - Verify SubNavigation associated links",
			enabled = false,
			groups = { "UK", "PROD" } )
	public void header_SubNavigation_Plan_Actions_UK() throws Exception
	{
		/* GIVEN user is on carnival home page" */
		PreConditions.checkNotNull( this.homePage, "Home page is null, before starting test" );
		try
		{
			Integer maxMenuItems = Integer.valueOf( SiteProperty.MAX_MENU_SUB_ITEMS.fromContext().toString() );
			String names = SiteProperty.PLAN_MENU_ITEMS.fromContext().toString();
			HtmlElement container = homePage.header().getContainer();
			Header.NavigationAdditional navigationAdditional = homePage.header().navigationAdditional();
			Header.HeaderLinks headerLinks = homePage.header().headerLinks();

			/* WHEN user hovers over "Plan" tab */

			headerLinks.hoverOnItem( PLAN );

			/* should see No more than 6 items on the additional level-2 menu */
			String REASON = "Asserting that menu sub-items are displayed";
			boolean ACTUAL_BOOL = navigationAdditional.isDisplayed();
			Matcher<Boolean> EXPECTED_OF_BOOL = is( true );
			SiteSessionManager.get().createCheckPoint( container, "PLAN_ADDITIONAL_LINKS_DISPLAYED" )
					.assertThat( REASON, ACTUAL_BOOL, EXPECTED_OF_BOOL );

			List<String> itemsNames = navigationAdditional.getChildMenuItemsNames( PLAN );
			REASON = "Asserting that we should see No more than 6 items on the additional level-2 menu";
			Matcher<Collection<? extends String>> EXPECTED_RESULT_OF_COLL_STR = hasSize( lessThanOrEqualTo( maxMenuItems ) );
			SiteSessionManager.get().createCheckPoint( container, "PLAN_NO_MORE_THAN_6" )
					.assertThat( REASON, itemsNames, EXPECTED_RESULT_OF_COLL_STR );

			/* AND The items appearance order matches the required order based on the current locale. */

			REASON = "Asserting that all level-2 items appearance order matches the required order based on the current locale";
			String[] ACTUAL_OF_STR_ARR = itemsNames.toArray( new String[ itemsNames.size() ] );
			Matcher<String[]> EXPECTED_OF_STR_ARR = arrayContaining( StringUtils.split( names, "," ) );
			SiteSessionManager.get().createCheckPoint( container, "PLAN_LEVEL_2_ITEMS" )
					.assertThat( REASON, ACTUAL_OF_STR_ARR, EXPECTED_OF_STR_ARR );

			/* AND WHEN the user hovers and clicks on "Find a Cruise" item. */
			Link findCruise = navigationAdditional.getLink( PLAN, FIND_A_CRUISE );
			String dataDefault = navigationAdditional.getDataDefaultImg( findCruise );
			HtmlElement img = navigationAdditional.getImage( findCruise );

			/* The image should change when hover to IMG-hover. */
			REASON = "asserting that  \"find a cruise\" img[src] value before hovering ends with < '%s' >";
			Matcher<String> EXPECTED_OF_STR = endsWith( dataDefault );
			HtmlCondition<Boolean> CONDITION_BOOL = ExpectedConditions.elementAttributeToMatch( img, "src", EXPECTED_OF_STR );
			SiteSessionManager.get().createCheckPoint( container, "FIND_A_CRUISE_BEFORE_HOVER" )
					.assertWaitThat( String.format( REASON, dataDefault ), FIVE_SECONDS, CONDITION_BOOL );

			navigationAdditional.hoverOnMenuItem( findCruise );

			String dataHover = navigationAdditional.getDataHoverImg( findCruise );
			REASON = "asserting that \"find a cruise\" img[src] value after hovering ends with < '%s' >";
			EXPECTED_OF_STR = endsWith( dataHover );
			CONDITION_BOOL = ExpectedConditions.elementAttributeToMatch( img, "src", EXPECTED_OF_STR );
			SiteSessionManager.get().createCheckPoint( container, "FIND_A_CRUISE_AFTER_HOVER" )
					.assertWaitThat( String.format( REASON, dataDefault ), FIVE_SECONDS, CONDITION_BOOL );

			/* AND it should take the user to Find a Cruise landing page: /bookingengine/findacruise */
			FindACruisePage findACruisePage = ( FindACruisePage ) navigationAdditional.clickOnMenuItem( findCruise, FIND_A_CRUISE );
			navigationAdditional = findACruisePage.header().navigationAdditional();
			headerLinks = findACruisePage.header().headerLinks();
			container = findACruisePage.header().getContainer();

			/* AND the "PLAN" level 1 menu item should be active. */
			Link learn = headerLinks.getLink( PLAN );
			REASON = "asserting that \"PLAN\" menu item is 'active'";
			EXPECTED_OF_STR = containsString( "active" );
			String ACTUAL_STR = learn.getHtmlElement().getAttribute( "class" );
			SiteSessionManager.get().createCheckPoint( "PLAN_IS_ACTIVE_ON_FIND_A_CRUISE" )
					.assertThat( REASON, ACTUAL_STR, EXPECTED_OF_STR );

			/* AND WHEN the user hovers and clicks on "Find a Port" item. */
			headerLinks.hoverOnItem( PLAN );
			Link findPort = navigationAdditional.getLink( PLAN, FIND_A_PORT );
			dataDefault = navigationAdditional.getDataDefaultImg( findPort );
			dataHover = navigationAdditional.getDataHoverImg( findPort );
			img = navigationAdditional.getImage( findPort );

			/* The image should change when hover to IMG-hover. */

			REASON = "asserting that \"find a port\" img[src] value before hovering ends with < '%s' >";
			EXPECTED_OF_STR = endsWith( dataDefault );
			CONDITION_BOOL = ExpectedConditions.elementAttributeToMatch( img, "src", EXPECTED_OF_STR );
			SiteSessionManager.get().createCheckPoint( container, "FIND_A_PORT_BEFORE_HOVER" )
					.assertWaitThat( String.format( REASON, dataDefault ), FIVE_SECONDS, CONDITION_BOOL );

			navigationAdditional.hoverOnMenuItem( findPort );

			REASON = "asserting that \"find a port\"  img[src] value after hovering ends with < '%s' >";
			EXPECTED_OF_STR = endsWith( dataHover );
			CONDITION_BOOL = ExpectedConditions.elementAttributeToMatch( img, "src", EXPECTED_OF_STR );
			SiteSessionManager.get().createCheckPoint( container, "FIND_A_PORT_AFTER_HOVER" )
					.assertWaitThat( String.format( REASON, dataDefault ), FIVE_SECONDS, CONDITION_BOOL );

			/* AND it should take the user to Close to Home page: /core/closetohome.aspx. */

			CruiseFromPortPage closeToHomePage = ( CruiseFromPortPage )
					navigationAdditional.clickOnMenuItem( findPort, FIND_A_PORT );
			navigationAdditional = closeToHomePage.header().navigationAdditional();
			headerLinks = closeToHomePage.header().headerLinks();
			container = closeToHomePage.header().getContainer();

			/* The "PLAN" level 1 menu item should be active.*/

			learn = headerLinks.getLink( PLAN );

			REASON = "asserting that \"PLAN\" menu item is 'active'";
			EXPECTED_OF_STR = containsString( "active" );
			ACTUAL_STR = learn.getHtmlElement().getAttribute( "class" );
			SiteSessionManager.get().createCheckPoint( "PLAN_IS_ACTIVE_ON_FIND_A_PORT" )
					.assertThat( REASON, ACTUAL_STR, EXPECTED_OF_STR );

			/* AND the user hovers and clicks on "FAQ's" item. */

			headerLinks.hoverOnItem( PLAN );

			Link faqs = navigationAdditional.getLink( PLAN, FAQ_S );
			dataDefault = navigationAdditional.getDataDefaultImg( faqs );
			dataHover = navigationAdditional.getDataHoverImg( faqs );
			img = navigationAdditional.getImage( faqs );

			/* The image should change when hover to IMG-hover. */

			REASON = "asserting that \"FAQ's\" img[src] value before hovering ends with < '%s' >";
			EXPECTED_OF_STR = endsWith( dataDefault );
			CONDITION_BOOL = ExpectedConditions.elementAttributeToMatch( img, "src", EXPECTED_OF_STR );
			SiteSessionManager.get().createCheckPoint( container, "FAQS_BEFORE_HOVER" )
					.assertWaitThat( String.format( REASON, dataDefault ), FIVE_SECONDS, CONDITION_BOOL );

			navigationAdditional.hoverOnMenuItem( faqs );

			REASON = "asserting that \"faqs\" img[src] value after hovering ends with < '%s' >";
			EXPECTED_OF_STR = endsWith( dataHover );
			CONDITION_BOOL = ExpectedConditions.elementAttributeToMatch( img, "src", EXPECTED_OF_STR );
			SiteSessionManager.get().createCheckPoint( container, "FAQS_AFTER_HOVER" )
					.assertWaitThat( String.format( REASON, dataDefault ), FIVE_SECONDS, CONDITION_BOOL );

			/* THEN it should take the user to FAQS landing page: /core/faq.aspx. */

			FaqPage faqPage = ( FaqPage )
					navigationAdditional.clickOnMenuItem( faqs, FAQ_S );
			navigationAdditional = faqPage.header().navigationAdditional();
			headerLinks = faqPage.header().headerLinks();
			container = faqPage.header().getContainer();

			/* The "LEARN" level 1 menu item should be active. */

			learn = headerLinks.getLink( PLAN );

			REASON = "asserting that \"PLAN\" menu item is 'active'";
			EXPECTED_OF_STR = containsString( "active" );
			ACTUAL_STR = learn.getHtmlElement().getAttribute( "class" );
			SiteSessionManager.get().createCheckPoint( "PLAN_IS_ACTIVE_ON_FAQS" )
					.assertThat( REASON, ACTUAL_STR, EXPECTED_OF_STR );

			faqPage.header().headerBranding().clickOnLogo();

			SiteSessionManager.get().assertAllCheckpoints();
		}
		catch ( WebDriverException | AssertionError e )
		{
			throw new ApplicationException( SiteSessionManager.get().getDriver(), e );
		}
	}

	//endregion

	//region NavigationAdditionalTest - header_SubNavigation_Manage_Actions_US

	@TestCaseId ( id = 65358 )
	@Steps ( steps = {
			@Step ( number = 1, description = "GIVEN user is on carnival home page" ),
			@Step ( number = 2, description = "WHEN user hovers over \"Manage\" tab",
					expectedResults = {
							"THEN should see No more than 6 items on the additional level-2 menu.",
							"AND The items appearance order matches the required order based on the current locale."
					} ),
			@Step ( number = 3, description = "AND WHEN the user hovers and clicks on \"My Booking\" item.",
					expectedResults = {
							"THEN The image should change when hover to IMG-hover.",
							"AND It should take the user to /BookedGuest/GuestManagement/MyCarnival/LogOn",
							"AND the domain is secure",
							"AND the protocol is https",
							"AND the url query ends-with icid=CC_my-booking_1862&goal goal=mybooking",
							"AND The \"MANAGE\" level 1 menu item should be active."
					} ),
			@Step ( number = 4, description = "AND WHEN the user hovers and clicks on \"Plan Activities\" item.",
					expectedResults = {
							"THEN The image should change when hover to IMG-hover.",
							"AND it should take the user to /BookedGuest/GuestManagement/MyCarnival/LogOn",
							"AND the domain is secure",
							"AND the protocol is https",
							"AND the url query ends-with icid=CC_excursions_1863&goal=excursions",
							"AND The \"MANAGE\" level 1 menu item should be active."
					} ),
			@Step ( number = 5, description = "AND WHEN the user hovers and clicks on \"In Room Gifts & Shopping\" item.",
					expectedResults = {
							"THEN the image should change when hover to IMG-hover.",
							"AND It should take the user to http://www.carnival.com/Funshops/",
							"AND the url query is icid=CC_fun-shops_1864",
							"AND The \"MANAGE\" level 1 menu item should be active."
					} ),
			@Step ( number = 6, description = "AND WHEN the user hovers and clicks on \"Check-In\" item.",
					expectedResults = {
							"THEN the image should change when hover to IMG-hover.",
							"THEN It should take the user to /BookedGuest/GuestManagement/MyCarnival/LogOn",
							"AND the domain is secure",
							"AND the protocol is https",
							"AND the url query ends-with icid=CC_check-in_1865&goal=checkin",
							"AND The \"MANAGE\" level 1 menu item should be active."
					} ),
			@Step ( number = 7, description = "AND WHEN the user hovers and clicks on \"VIFP Club\" item.",
					expectedResults = {
							"THEN the image should change when hover to IMG-hover.",
							"THEN It should take the user to Vacation Planner page: /loyalty/overview.aspx",
							"AND the url query is icid=CC_vifp-club_1866",
							"AND The \"MANAGE\" level 1 menu item should be active."
					} )
	} )
	@Test( description = "Header - SubNavigation - Manage - Actions - Verify SubNavigation associated links",
			enabled = true,
			groups = { "US", "PROD" } )
	public void header_SubNavigation_Manage_Actions_US() throws Exception
	{
		/* GIVEN user is on carnival home page" */
		PreConditions.checkNotNull( this.homePage, "Home page is null, before starting test" );
		try
		{
			Integer maxMenuItems = Integer.valueOf( SiteProperty.MAX_MENU_SUB_ITEMS.fromContext().toString() );
			String names = SiteProperty.MANAGE_MENU_ITEMS.fromContext().toString();
			HtmlElement container = homePage.header().getContainer();
			Header.NavigationAdditional navigationAdditional = homePage.header().navigationAdditional();
			Header.HeaderLinks headerLinks = homePage.header().headerLinks();

			/* WHEN user hovers over "Learn" tab */

			headerLinks.hoverOnItem( MANAGE );

			/* should see No more than 6 items on the additional level-2 menu */
			String REASON = "Asserting that menu sub-items are displayed";
			boolean ACTUAL_BOOL = navigationAdditional.isDisplayed();
			Matcher<Boolean> EXPECTED_OF_BOOL = is( true );
			SiteSessionManager.get().createCheckPoint( container, "MANAGE_ADDITIONAL_LINKS_DISPLAYED" )
					.assertThat( REASON, ACTUAL_BOOL, EXPECTED_OF_BOOL );

			List<String> itemsNames = navigationAdditional.getChildMenuItemsNames( MANAGE );
			REASON = "Asserting that we should see No more than 6 items on the additional level-2 menu";
			Matcher<Collection<? extends String>> EXPECTED_RESULT_OF_COLL_STR = hasSize( lessThanOrEqualTo( maxMenuItems ) );
			SiteSessionManager.get().createCheckPoint( container, "MANAGE_NO_MORE_THAN_6" )
					.assertThat( REASON, itemsNames, EXPECTED_RESULT_OF_COLL_STR );

			/* AND The items appearance order matches the required order based on the current locale. */

			REASON = "Asserting that all level-2 items appearance order matches the required order based on the current locale";
			String[] ACTUAL_OF_STR_ARR = itemsNames.toArray( new String[ itemsNames.size() ] );
			Matcher<String[]> EXPECTED_OF_STR_ARR = arrayContaining( StringUtils.split( names, "," ) );
			SiteSessionManager.get().createCheckPoint( container, "MANAGE_LEVEL_2_ITEMS" )
					.assertThat( REASON, ACTUAL_OF_STR_ARR, EXPECTED_OF_STR_ARR );

			/* AND WHEN the user hovers and clicks on "My Booking" item. */
			Link myBooking = navigationAdditional.getLink( MANAGE, MY_BOOKING );
			String dataDefault = navigationAdditional.getDataDefaultImg( myBooking );
			HtmlElement img = navigationAdditional.getImage( myBooking );

			/* The image should change when hover to IMG-hover. */
			REASON = "asserting that  \"my booking\" img[src] value before hovering ends with < '%s' >";
			Matcher<String> EXPECTED_OF_STR = endsWith( dataDefault );
			HtmlCondition<Boolean> CONDITION_BOOL = ExpectedConditions.elementAttributeToMatch( img, "src", EXPECTED_OF_STR );
			SiteSessionManager.get().createCheckPoint( container, "MY_BOOKING_BEFORE_HOVER" )
					.assertWaitThat( String.format( REASON, dataDefault ), FIVE_SECONDS, CONDITION_BOOL );

			navigationAdditional.hoverOnMenuItem( myBooking );

			String dataHover = navigationAdditional.getDataHoverImg( myBooking );
			REASON = "asserting that \"my booking\" img[src] value after hovering ends with < '%s' >";
			EXPECTED_OF_STR = endsWith( dataHover );
			CONDITION_BOOL = ExpectedConditions.elementAttributeToMatch( img, "src", EXPECTED_OF_STR );
			SiteSessionManager.get().createCheckPoint( container, "MY_BOOKING_AFTER_HOVER" )
					.assertWaitThat( String.format( REASON, dataDefault ), FIVE_SECONDS, CONDITION_BOOL );

			/* AND It should take the user to /BookedGuest/GuestManagement/MyCarnival/LogOn */
			BookedGuestLogonPage bookedGuestPage = ( BookedGuestLogonPage )
					navigationAdditional.clickOnMenuItem( myBooking, MY_BOOKING );
			navigationAdditional = bookedGuestPage.header().navigationAdditional();
			headerLinks = bookedGuestPage.header().headerLinks();
			container = bookedGuestPage.header().getContainer();

			/* AND the domain is secure" */
			URL url = bookedGuestPage.getURL();
			REASON = "asserting that domain is secure";
			String ACTUAL_STR = bookedGuestPage.getSecuredUrl();
			EXPECTED_OF_STR = containsStringIgnoreCase( url.getHost() );
			SiteSessionManager.get().createCheckPoint( container, "MY_BOOKING_SECURE" )
					.assertThat( REASON, ACTUAL_STR, EXPECTED_OF_STR );

			/* AND the protocol is https" */
			REASON = "asserting that protocol is https";
			ACTUAL_STR = url.getProtocol();
			EXPECTED_OF_STR = equalToIgnoringCase( "https" );
			SiteSessionManager.get().createCheckPoint( container, "MY_BOOKING_PROTOCOL" )
					.assertThat( REASON, ACTUAL_STR, EXPECTED_OF_STR );

			/* AND the url query ends-with icid=CC_my-booking_1862&goal goal=mybooking */
			REASON = "asserting url query is icid=CC_my-booking_1862&goal goal=mybooking";
			ACTUAL_STR = java.net.URLDecoder.decode( url.getQuery(), SystemUtils.FILE_ENCODING );
			EXPECTED_OF_STR = endsWith( "icid=CC_my-booking_1862&goal=mybooking" );
			SiteSessionManager.get().createCheckPoint( container, "MY_BOOKING_QUERY" )
					.assertThat( REASON, ACTUAL_STR, EXPECTED_OF_STR );

			/* AND the "MANAGE" level 1 menu item should be active. */
			Link manage = headerLinks.getLink( MANAGE );
			REASON = "asserting that \"MANAGE\" menu item is 'active'";
			EXPECTED_OF_STR = containsString( "active" );
			ACTUAL_STR = manage.getHtmlElement().getAttribute( "class" );
			SiteSessionManager.get().createCheckPoint( "MANAGE_ACTIVE_ON_MY_BOOKINGS" )
					.assertThat( REASON, ACTUAL_STR, EXPECTED_OF_STR );

			/* AND WHEN the user hovers and clicks on "Plan Activities" item. */
			headerLinks.hoverOnItem( MANAGE );
			Link planActivities = navigationAdditional.getLink( MANAGE, PLAN_ACTIVITIES );
			dataDefault = navigationAdditional.getDataDefaultImg( planActivities );
			dataHover = navigationAdditional.getDataHoverImg( planActivities );
			img = navigationAdditional.getImage( planActivities );

			/* THEN The image should change when hover to IMG-hover." */
			REASON = "asserting that \"plan activities\" img[src] value before hovering ends with < '%s' >";
			EXPECTED_OF_STR = endsWith( dataDefault );
			CONDITION_BOOL = ExpectedConditions.elementAttributeToMatch( img, "src", EXPECTED_OF_STR );
			SiteSessionManager.get().createCheckPoint( container, "PLAN_ACTIVITIES_BEFORE_HOVER" )
					.assertWaitThat( String.format( REASON, dataDefault ), FIVE_SECONDS, CONDITION_BOOL );

			navigationAdditional.hoverOnMenuItem( planActivities );

			REASON = "asserting that \"plan activities\"  img[src] value after hovering ends with < '%s' >";
			EXPECTED_OF_STR = endsWith( dataHover );
			CONDITION_BOOL = ExpectedConditions.elementAttributeToMatch( img, "src", EXPECTED_OF_STR );
			SiteSessionManager.get().createCheckPoint( container, "PLAN_ACTIVITIES_AFTER_HOVER" )
					.assertWaitThat( String.format( REASON, dataDefault ), FIVE_SECONDS, CONDITION_BOOL );

			/* AND it should take the user to /BookedGuest/GuestManagement/MyCarnival/LogOn" */
			bookedGuestPage = ( BookedGuestLogonPage )
					navigationAdditional.clickOnMenuItem( planActivities, PLAN_ACTIVITIES );
			navigationAdditional = bookedGuestPage.header().navigationAdditional();
			headerLinks = bookedGuestPage.header().headerLinks();
			container = bookedGuestPage.header().getContainer();

			/* AND the domain is secure" */
			url = bookedGuestPage.getURL();
			REASON = "asserting that domain is secure";
			ACTUAL_STR = bookedGuestPage.getSecuredUrl();
			EXPECTED_OF_STR = containsStringIgnoreCase( url.getHost() );
			SiteSessionManager.get().createCheckPoint( container, "PLAN_ACTIVITIES_SECURE" )
					.assertThat( REASON, ACTUAL_STR, EXPECTED_OF_STR );

			/* AND the protocol is https" */
			REASON = "asserting that protocol is https";
			ACTUAL_STR = url.getProtocol();
			EXPECTED_OF_STR = equalToIgnoringCase( "https" );
			SiteSessionManager.get().createCheckPoint( container, "PLAN_ACTIVITIES_PROTOCOL" )
					.assertThat( REASON, ACTUAL_STR, EXPECTED_OF_STR );

			/* AND the url query ends-with icid=CC_excursions_1863&goal=excursions */
			REASON = "asserting that url query ends-with icid=CC_excursions_1863&goal=excursions";
			ACTUAL_STR = java.net.URLDecoder.decode( url.getQuery(), SystemUtils.FILE_ENCODING );
			EXPECTED_OF_STR = endsWith( "icid=CC_excursions_1863&goal=excursions" );
			SiteSessionManager.get().createCheckPoint( container, "PLAN_ACTIVITIES_QUERY" )
					.assertThat( REASON, ACTUAL_STR, EXPECTED_OF_STR );

			/* AND the "MANAGE" level 1 menu item should be active. */
			manage = headerLinks.getLink( MANAGE );
			REASON = "asserting that \"MANAGE\" menu item is 'active'";
			EXPECTED_OF_STR = containsString( "active" );
			ACTUAL_STR = manage.getHtmlElement().getAttribute( "class" );
			SiteSessionManager.get().createCheckPoint( "MANAGE_ACTIVE_ON_PLAN_ACTIVITIES" )
					.assertThat( REASON, ACTUAL_STR, EXPECTED_OF_STR );

			/* AND WHEN the user hovers and clicks on "In Room Gifts & Shopping" item. */
			headerLinks.hoverOnItem( MANAGE );
			Link inRoomGifts = navigationAdditional.getLink( MANAGE, IN_ROOM_GIFTS_AND_SHOPPING );
			dataDefault = navigationAdditional.getDataDefaultImg( inRoomGifts );
			dataHover = navigationAdditional.getDataHoverImg( inRoomGifts );
			img = navigationAdditional.getImage( inRoomGifts );

			/* THEN the image should change when hover to IMG-hover." */
			REASON = "asserting that \"in room gifts\" img[src] value before hovering ends with < '%s' >";
			EXPECTED_OF_STR = endsWith( dataDefault );
			CONDITION_BOOL = ExpectedConditions.elementAttributeToMatch( img, "src", EXPECTED_OF_STR );
			SiteSessionManager.get().createCheckPoint( container, "IN_ROOM_GIFTS_BEFORE_HOVER" )
					.assertWaitThat( String.format( REASON, dataDefault ), FIVE_SECONDS, CONDITION_BOOL );

			navigationAdditional.hoverOnMenuItem( inRoomGifts );

			REASON = "asserting that \"in room gifts\"  img[src] value after hovering ends with < '%s' >";
			EXPECTED_OF_STR = endsWith( dataHover );
			CONDITION_BOOL = ExpectedConditions.elementAttributeToMatch( img, "src", EXPECTED_OF_STR );
			SiteSessionManager.get().createCheckPoint( container, "IN_ROOM_GIFTS_AFTER_HOVER" )
					.assertWaitThat( String.format( REASON, dataDefault ), FIVE_SECONDS, CONDITION_BOOL );

			/* AND It should take the user to http://www.carnival.com/Funshops/ */
			FunShopsPage funShops = ( FunShopsPage )
					navigationAdditional.clickOnMenuItem( inRoomGifts, IN_ROOM_GIFTS_AND_SHOPPING );
			navigationAdditional = funShops.header().navigationAdditional();
			headerLinks = funShops.header().headerLinks();
			container = funShops.header().getContainer();

			/* AND the url query is icid=CC_fun-shops_1864 */
			url = funShops.getURL();
			REASON = "asserting that url query ends-with icid=CC_fun-shops_1864";
			ACTUAL_STR = java.net.URLDecoder.decode( url.getQuery(), SystemUtils.FILE_ENCODING );
			EXPECTED_OF_STR = endsWith( "icid=CC_fun-shops_1864" );
			SiteSessionManager.get().createCheckPoint( container, "IN_ROOM_GIFTS_QUERY" )
					.assertThat( REASON, ACTUAL_STR, EXPECTED_OF_STR );

			/* AND the "MANAGE" level 1 menu item should be active. */
			manage = headerLinks.getLink( MANAGE );
			REASON = "asserting that \"MANAGE\" menu item is 'active'";
			EXPECTED_OF_STR = containsString( "active" );
			ACTUAL_STR = manage.getHtmlElement().getAttribute( "class" );
			SiteSessionManager.get().createCheckPoint( "MANAGE_ACTIVE_ON_IN_ROOM_GIFTS" )
					.assertThat( REASON, ACTUAL_STR, EXPECTED_OF_STR );

			/* AND WHEN the user hovers and clicks on "Check-In" item. */
			headerLinks.hoverOnItem( MANAGE );
			Link checkIn = navigationAdditional.getLink( MANAGE, CHECK_IN );
			dataDefault = navigationAdditional.getDataDefaultImg( checkIn );
			dataHover = navigationAdditional.getDataHoverImg( checkIn );
			img = navigationAdditional.getImage( checkIn );

			/* THEN The image should change when hover to IMG-hover." */
			REASON = "asserting that \"check-in\" img[src] value before hovering ends with < '%s' >";
			EXPECTED_OF_STR = endsWith( dataDefault );
			CONDITION_BOOL = ExpectedConditions.elementAttributeToMatch( img, "src", EXPECTED_OF_STR );
			SiteSessionManager.get().createCheckPoint( container, "CHECK_IN_BEFORE_HOVER" )
					.assertWaitThat( String.format( REASON, dataDefault ), FIVE_SECONDS, CONDITION_BOOL );

			navigationAdditional.hoverOnMenuItem( checkIn );

			REASON = "asserting that \"check-in\" img[src] value after hovering ends with < '%s' >";
			EXPECTED_OF_STR = endsWith( dataHover );
			CONDITION_BOOL = ExpectedConditions.elementAttributeToMatch( img, "src", EXPECTED_OF_STR );
			SiteSessionManager.get().createCheckPoint( container, "CHECK_IN_AFTER_HOVER" )
					.assertWaitThat( String.format( REASON, dataDefault ), FIVE_SECONDS, CONDITION_BOOL );

			/* THEN It should take the user to /BookedGuest/GuestManagement/MyCarnival/LogOn */
			bookedGuestPage = ( BookedGuestLogonPage )
					navigationAdditional.clickOnMenuItem( checkIn, CHECK_IN );
			navigationAdditional = bookedGuestPage.header().navigationAdditional();
			headerLinks = bookedGuestPage.header().headerLinks();
			container = bookedGuestPage.header().getContainer();

			/* AND the domain is secure" */
			url = bookedGuestPage.getURL();
			REASON = "asserting that domain is secure";
			ACTUAL_STR = bookedGuestPage.getSecuredUrl();
			EXPECTED_OF_STR = containsStringIgnoreCase( url.getHost() );
			SiteSessionManager.get().createCheckPoint( container, "CHECK_IN_SECURE" )
					.assertThat( REASON, ACTUAL_STR, EXPECTED_OF_STR );

			/* AND the protocol is https" */
			REASON = "asserting that protocol is https";
			ACTUAL_STR = url.getProtocol();
			EXPECTED_OF_STR = equalToIgnoringCase( "https" );
			SiteSessionManager.get().createCheckPoint( container, "CHECK_IN_PROTOCOL" )
					.assertThat( REASON, ACTUAL_STR, EXPECTED_OF_STR );

			/* AND the url query ends-with icid=CC_check-in_1865&goal=checkin */
			REASON = "asserting that url query ends-with icid=CC_check-in_1865&goal=checkin";
			ACTUAL_STR = java.net.URLDecoder.decode( url.getQuery(), SystemUtils.FILE_ENCODING );
			EXPECTED_OF_STR = endsWith( "icid=CC_check-in_1865&goal=checkin" );
			SiteSessionManager.get().createCheckPoint( container, "CHECK_IN_QUERY" )
					.assertThat( REASON, ACTUAL_STR, EXPECTED_OF_STR );

			/* AND the "MANAGE" level 1 menu item should be active. */
			manage = headerLinks.getLink( MANAGE );
			REASON = "asserting that \"MANAGE\" menu item is 'active'";
			EXPECTED_OF_STR = containsString( "active" );
			ACTUAL_STR = manage.getHtmlElement().getAttribute( "class" );
			SiteSessionManager.get().createCheckPoint( "MANAGE_ACTIVE_ON_CHECK_IN" )
					.assertThat( REASON, ACTUAL_STR, EXPECTED_OF_STR );

			/* AND WHEN the user hovers and clicks on "VIFP Club" item. */
			headerLinks.hoverOnItem( MANAGE );
			Link vifp = navigationAdditional.getLink( MANAGE, VIFP_CLUB );
			dataDefault = navigationAdditional.getDataDefaultImg( vifp );
			dataHover = navigationAdditional.getDataHoverImg( vifp );
			img = navigationAdditional.getImage( vifp );

			/* THEN The image should change when hover to IMG-hover." */
			REASON = "asserting that \"vifp club\" img[src] value before hovering ends with < '%s' >";
			EXPECTED_OF_STR = endsWith( dataDefault );
			CONDITION_BOOL = ExpectedConditions.elementAttributeToMatch( img, "src", EXPECTED_OF_STR );
			SiteSessionManager.get().createCheckPoint( container, "VIFP_CLUB_BEFORE_HOVER" )
					.assertWaitThat( String.format( REASON, dataDefault ), FIVE_SECONDS, CONDITION_BOOL );

			navigationAdditional.hoverOnMenuItem( vifp );

			REASON = "asserting that \"vifp club\"  img[src] value after hovering ends with < '%s' >";
			EXPECTED_OF_STR = endsWith( dataHover );
			CONDITION_BOOL = ExpectedConditions.elementAttributeToMatch( img, "src", EXPECTED_OF_STR );
			SiteSessionManager.get().createCheckPoint( container, "VIFP_CLUB_AFTER_HOVER" )
					.assertWaitThat( String.format( REASON, dataDefault ), FIVE_SECONDS, CONDITION_BOOL );

			/* THEN It should take the user to Vacation Planner page: /loyalty/overview.aspx */
			VifpClubPage vifpClubPage = ( VifpClubPage )
					navigationAdditional.clickOnMenuItem( vifp, VIFP_CLUB );
			headerLinks = vifpClubPage.header().headerLinks();
			container = vifpClubPage.header().getContainer();

			/* AND the url query ends-with icid=CC_vifp-club_1866 */
			url = vifpClubPage.getURL();
			REASON = "asserting that url query ends-with icid=CC_vifp-club_1866";
			ACTUAL_STR = java.net.URLDecoder.decode( url.getQuery(), SystemUtils.FILE_ENCODING );
			EXPECTED_OF_STR = endsWith( "icid=CC_vifp-club_1866" );
			SiteSessionManager.get().createCheckPoint( container, "VIFP_CLUB_QUERY" )
					.assertThat( REASON, ACTUAL_STR, EXPECTED_OF_STR );

			/* AND the "MANAGE" level 1 menu item should be active. */
			manage = headerLinks.getLink( MANAGE );
			REASON = "asserting that \"MANAGE\" menu item is 'active'";
			EXPECTED_OF_STR = containsString( "active" );
			ACTUAL_STR = manage.getHtmlElement().getAttribute( "class" );
			SiteSessionManager.get().createCheckPoint( "MANAGE_ACTIVE_ON_VIFP_CLUB" )
					.assertThat( REASON, ACTUAL_STR, EXPECTED_OF_STR );

			SiteSessionManager.get().assertAllCheckpoints();
		}
		catch ( WebDriverException | AssertionError e )
		{
			throw new ApplicationException( SiteSessionManager.get().getDriver(), e );
		}
	}

	//endregion

	//region NavigationAdditionalTest - header_SubNavigation_Manage_Actions_UK

	@TestCaseId ( id = 65358 )
	@Steps ( steps = {
			@Step ( number = 1, description = "GIVEN user is on carnival home page" ),
			@Step ( number = 2, description = "WHEN user hovers over \"Manage\" tab",
					expectedResults = {
							"THEN should see No more than 6 items on the additional level-2 menu.",
							"AND The items appearance order matches the required order based on the current locale."
					} ),
			@Step ( number = 3, description = "AND WHEN the user hovers and clicks on \"My Booking\" item.",
					expectedResults = {
							"THEN The image should change when hover to IMG-hover.",
							"AND It should take the user to /BookedGuest/GuestManagement/MyCarnival/LogOn",
							"AND the domain is secure",
							"AND the protocol is https",
							"AND the url query ends-with ?goal=mybooking",
							"AND The \"MANAGE\" level 1 menu item should be active."
					} ),
			@Step ( number = 4, description = "AND WHEN the user hovers and clicks on \"Plan Activities\" item.",
					expectedResults = {
							"THEN The image should change when hover to IMG-hover.",
							"AND it should take the user to /activities",
							"AND The \"MANAGE\" level 1 menu item should be active."
					} ),
			@Step ( number = 5, description = "AND WHEN the user hovers and clicks on \"Check-In\" item.",
					expectedResults = {
							"THEN the image should change when hover to IMG-hover.",
							"THEN It should take the user to /BookedGuest/GuestManagement/MyCarnival/LogOn",
							"AND the domain is secure",
							"AND the protocol is https",
							"AND the url query ends-with icid=CC_check-in_1865&goal=checkin",
							"AND The \"MANAGE\" level 1 menu item should be active."
					} ),
			@Step ( number = 6, description = "AND WHEN the user hovers and clicks on \"VIFP Club\" item.",
					expectedResults = {
							"THEN the image should change when hover to IMG-hover.",
							"THEN It should take the user to Vacation Planner page: /loyalty/overview.aspx",
							"AND the url query is icid=CC_vifp-club_1866",
							"AND The \"MANAGE\" level 1 menu item should be active."
					} )
	} )
	@Test( description = "Header - SubNavigation - Manage - Actions - Verify SubNavigation associated links",
			enabled = true,
			groups = { "UK", "PROD" }
	)
	public void header_SubNavigation_Manage_Actions_UK() throws Exception
	{
		/* GIVEN user is on carnival home page" */
		PreConditions.checkNotNull( this.homePage, "Home page is null, before starting test" );
		try
		{
			Integer maxMenuItems = Integer.valueOf( SiteProperty.MAX_MENU_SUB_ITEMS.fromContext().toString() );
			String names = SiteProperty.MANAGE_MENU_ITEMS.fromContext().toString();
			HtmlElement container = homePage.header().getContainer();
			Header.NavigationAdditional navigationAdditional = homePage.header().navigationAdditional();
			Header.HeaderLinks headerLinks = homePage.header().headerLinks();

			/* WHEN user hovers over "Learn" tab */
			headerLinks.hoverOnItem( MANAGE );

			/* should see No more than 6 items on the additional level-2 menu */
			String REASON = "Asserting that menu sub-items are displayed";
			boolean ACTUAL_BOOL = navigationAdditional.isDisplayed();
			Matcher<Boolean> EXPECTED_OF_BOOL = is( true );
			SiteSessionManager.get().createCheckPoint( container, "MANAGE_ADDITIONAL_LINKS_DISPLAYED" )
					.assertThat( REASON, ACTUAL_BOOL, EXPECTED_OF_BOOL );

			List<String> itemsNames = navigationAdditional.getChildMenuItemsNames( MANAGE );
			REASON = "Asserting that we should see No more than 6 items on the additional level-2 menu";
			Matcher<Collection<? extends String>> EXPECTED_RESULT_OF_COLL_STR = hasSize( lessThanOrEqualTo( maxMenuItems ) );
			SiteSessionManager.get().createCheckPoint( container, "MANAGE_NO_MORE_THAN_6" )
					.assertThat( REASON, itemsNames, EXPECTED_RESULT_OF_COLL_STR );

			/* AND The items appearance order matches the required order based on the current locale. */
			REASON = "Asserting that all level-2 items appearance order matches the required order based on the current locale";
			String[] ACTUAL_OF_STR_ARR = itemsNames.toArray( new String[ itemsNames.size() ] );
			Matcher<String[]> EXPECTED_OF_STR_ARR = arrayContaining( StringUtils.split( names, "," ) );
			SiteSessionManager.get().createCheckPoint( container, "MANAGE_LEVEL_2_ITEMS" )
					.assertThat( REASON, ACTUAL_OF_STR_ARR, EXPECTED_OF_STR_ARR );

			/* AND WHEN the user hovers and clicks on "My Booking" item. */
			Link myBooking = navigationAdditional.getLink( MANAGE, MY_BOOKING );
			String dataDefault = navigationAdditional.getDataDefaultImg( myBooking );
			HtmlElement img = navigationAdditional.getImage( myBooking );

			/* The image should change when hover to IMG-hover. */
			REASON = "asserting that  \"my booking\" img[src] value before hovering ends with < '%s' >";
			Matcher<String> EXPECTED_OF_STR = endsWith( dataDefault );
			HtmlCondition<Boolean> CONDITION_BOOL = ExpectedConditions.elementAttributeToMatch( img, "src", EXPECTED_OF_STR );
			SiteSessionManager.get().createCheckPoint( container, "MY_BOOKING_BEFORE_HOVER" )
					.assertWaitThat( String.format( REASON, dataDefault ), FIVE_SECONDS, CONDITION_BOOL );

			navigationAdditional.hoverOnMenuItem( myBooking );

			String dataHover = navigationAdditional.getDataHoverImg( myBooking );
			REASON = "asserting that \"my booking\" img[src] value after hovering ends with < '%s' >";
			EXPECTED_OF_STR = endsWith( dataHover );
			CONDITION_BOOL = ExpectedConditions.elementAttributeToMatch( img, "src", EXPECTED_OF_STR );
			SiteSessionManager.get().createCheckPoint( container, "MY_BOOKING_AFTER_HOVER" )
					.assertWaitThat( String.format( REASON, dataDefault ), FIVE_SECONDS, CONDITION_BOOL );

			/* AND It should take the user to /BookedGuest/GuestManagement/MyCarnival/LogOn */
			BookedGuestLogonPage bookedGuestPage = ( BookedGuestLogonPage )
					navigationAdditional.clickOnMenuItem( myBooking, MY_BOOKING );
			navigationAdditional = bookedGuestPage.header().navigationAdditional();
			headerLinks = bookedGuestPage.header().headerLinks();
			container = bookedGuestPage.header().getContainer();

			/* AND the domain is secure" */
			URL url = bookedGuestPage.getURL();
			REASON = "asserting that domain is secure";
			String ACTUAL_STR = bookedGuestPage.getSecuredUrl();
			EXPECTED_OF_STR = containsStringIgnoreCase( url.getHost() );
			SiteSessionManager.get().createCheckPoint( container, "MY_BOOKING_SECURE" )
					.assertThat( REASON, ACTUAL_STR, EXPECTED_OF_STR );

			/* AND the protocol is https" */
			REASON = "asserting that protocol is https";
			ACTUAL_STR = url.getProtocol();
			EXPECTED_OF_STR = equalToIgnoringCase( "https" );
			SiteSessionManager.get().createCheckPoint( container, "MY_BOOKING_PROTOCOL" )
					.assertThat( REASON, ACTUAL_STR, EXPECTED_OF_STR );

			/* AND the url query ends-with ?goal=mybooking */
			REASON = "asserting url query is ?goal=mybooking";
			ACTUAL_STR = java.net.URLDecoder.decode( url.getQuery(), SystemUtils.FILE_ENCODING );
			EXPECTED_OF_STR = endsWith( "?goal=mybooking" );
			SiteSessionManager.get().createCheckPoint( container, "MY_BOOKING_QUERY" )
					.assertThat( REASON, ACTUAL_STR, EXPECTED_OF_STR );

			/* AND the "MANAGE" level 1 menu item should be active. */
			Link manage = headerLinks.getLink( MANAGE );
			REASON = "asserting that \"MANAGE\" menu item is 'active'";
			EXPECTED_OF_STR = containsString( "active" );
			ACTUAL_STR = manage.getHtmlElement().getAttribute( "class" );
			SiteSessionManager.get().createCheckPoint( "MANAGE_ACTIVE_ON_MY_BOOKINGS" )
					.assertThat( REASON, ACTUAL_STR, EXPECTED_OF_STR );

			/* AND WHEN the user hovers and clicks on "Plan Activities" item. */
			headerLinks.hoverOnItem( MANAGE );
			Link planActivities = navigationAdditional.getLink( MANAGE, PLAN_ACTIVITIES );
			dataDefault = navigationAdditional.getDataDefaultImg( planActivities );
			dataHover = navigationAdditional.getDataHoverImg( planActivities );
			img = navigationAdditional.getImage( planActivities );

			/* THEN The image should change when hover to IMG-hover." */
			REASON = "asserting that \"plan activities\" img[src] value before hovering ends with < '%s' >";
			EXPECTED_OF_STR = endsWith( dataDefault );
			CONDITION_BOOL = ExpectedConditions.elementAttributeToMatch( img, "src", EXPECTED_OF_STR );
			SiteSessionManager.get().createCheckPoint( container, "PLAN_ACTIVITIES_BEFORE_HOVER" )
					.assertWaitThat( String.format( REASON, dataDefault ), FIVE_SECONDS, CONDITION_BOOL );

			navigationAdditional.hoverOnMenuItem( planActivities );

			REASON = "asserting that \"plan activities\"  img[src] value after hovering ends with < '%s' >";
			EXPECTED_OF_STR = endsWith( dataHover );
			CONDITION_BOOL = ExpectedConditions.elementAttributeToMatch( img, "src", EXPECTED_OF_STR );
			SiteSessionManager.get().createCheckPoint( container, "PLAN_ACTIVITIES_AFTER_HOVER" )
					.assertWaitThat( String.format( REASON, dataDefault ), FIVE_SECONDS, CONDITION_BOOL );

			/* AND it should take the user to /BookedGuest/GuestManagement/MyCarnival/LogOn" */
			ActivitiesPage activitiesPage = ( ActivitiesPage )
					navigationAdditional.clickOnMenuItem( planActivities, PLAN_ACTIVITIES );
			navigationAdditional = activitiesPage.header().navigationAdditional();
			headerLinks = activitiesPage.header().headerLinks();
			container = activitiesPage.header().getContainer();

			/* AND the "MANAGE" level 1 menu item should be active. */
			manage = headerLinks.getLink( MANAGE );
			REASON = "asserting that \"MANAGE\" menu item is 'active'";
			EXPECTED_OF_STR = containsString( "active" );
			ACTUAL_STR = manage.getHtmlElement().getAttribute( "class" );
			SiteSessionManager.get().createCheckPoint( "MANAGE_ACTIVE_ON_PLAN_ACTIVITIES" )
					.assertThat( REASON, ACTUAL_STR, EXPECTED_OF_STR );

			/* AND WHEN the user hovers and clicks on "Check-In" item. */
			headerLinks.hoverOnItem( MANAGE );
			Link checkIn = navigationAdditional.getLink( MANAGE, CHECK_IN );
			dataDefault = navigationAdditional.getDataDefaultImg( checkIn );
			dataHover = navigationAdditional.getDataHoverImg( checkIn );
			img = navigationAdditional.getImage( checkIn );

			/* THEN The image should change when hover to IMG-hover." */
			REASON = "asserting that \"check-in\" img[src] value before hovering ends with < '%s' >";
			EXPECTED_OF_STR = endsWith( dataDefault );
			CONDITION_BOOL = ExpectedConditions.elementAttributeToMatch( img, "src", EXPECTED_OF_STR );
			SiteSessionManager.get().createCheckPoint( container, "CHECK_IN_BEFORE_HOVER" )
					.assertWaitThat( String.format( REASON, dataDefault ), FIVE_SECONDS, CONDITION_BOOL );

			navigationAdditional.hoverOnMenuItem( checkIn );

			REASON = "asserting that \"check-in\" img[src] value after hovering ends with < '%s' >";
			EXPECTED_OF_STR = endsWith( dataHover );
			CONDITION_BOOL = ExpectedConditions.elementAttributeToMatch( img, "src", EXPECTED_OF_STR );
			SiteSessionManager.get().createCheckPoint( container, "CHECK_IN_AFTER_HOVER" )
					.assertWaitThat( String.format( REASON, dataDefault ), FIVE_SECONDS, CONDITION_BOOL );

			/* THEN It should take the user to /BookedGuest/GuestManagement/MyCarnival/LogOn */
			bookedGuestPage = ( BookedGuestLogonPage )
					navigationAdditional.clickOnMenuItem( checkIn, CHECK_IN );
			navigationAdditional = bookedGuestPage.header().navigationAdditional();
			headerLinks = bookedGuestPage.header().headerLinks();
			container = bookedGuestPage.header().getContainer();

			/* AND the domain is secure" */
			url = bookedGuestPage.getURL();
			REASON = "asserting that domain is secure";
			ACTUAL_STR = bookedGuestPage.getSecuredUrl();
			EXPECTED_OF_STR = containsStringIgnoreCase( url.getHost() );
			SiteSessionManager.get().createCheckPoint( container, "CHECK_IN_SECURE" )
					.assertThat( REASON, ACTUAL_STR, EXPECTED_OF_STR );

			/* AND the protocol is https" */
			REASON = "asserting that protocol is https";
			ACTUAL_STR = url.getProtocol();
			EXPECTED_OF_STR = equalToIgnoringCase( "https" );
			SiteSessionManager.get().createCheckPoint( container, "CHECK_IN_PROTOCOL" )
					.assertThat( REASON, ACTUAL_STR, EXPECTED_OF_STR );

			/* AND the url query ends-with icid=CC_check-in_1865&goal=checkin */
			REASON = "asserting that url query ends-with icid=CC_check-in_1865&goal=checkin";
			ACTUAL_STR = java.net.URLDecoder.decode( url.getQuery(), SystemUtils.FILE_ENCODING );
			EXPECTED_OF_STR = endsWith( "icid=CC_check-in_1865&goal=checkin" );
			SiteSessionManager.get().createCheckPoint( container, "CHECK_IN_QUERY" )
					.assertThat( REASON, ACTUAL_STR, EXPECTED_OF_STR );

			/* AND the "MANAGE" level 1 menu item should be active. */
			manage = headerLinks.getLink( MANAGE );
			REASON = "asserting that \"MANAGE\" menu item is 'active'";
			EXPECTED_OF_STR = containsString( "active" );
			ACTUAL_STR = manage.getHtmlElement().getAttribute( "class" );
			SiteSessionManager.get().createCheckPoint( "MANAGE_ACTIVE_ON_CHECK_IN" )
					.assertThat( REASON, ACTUAL_STR, EXPECTED_OF_STR );

			/* AND WHEN the user hovers and clicks on "VIFP Club" item. */
			headerLinks.hoverOnItem( MANAGE );
			Link vifp = navigationAdditional.getLink( MANAGE, VIFP_CLUB );
			dataDefault = navigationAdditional.getDataDefaultImg( vifp );
			dataHover = navigationAdditional.getDataHoverImg( vifp );
			img = navigationAdditional.getImage( vifp );

			/* THEN The image should change when hover to IMG-hover." */
			REASON = "asserting that \"vifp club\" img[src] value before hovering ends with < '%s' >";
			EXPECTED_OF_STR = endsWith( dataDefault );
			CONDITION_BOOL = ExpectedConditions.elementAttributeToMatch( img, "src", EXPECTED_OF_STR );
			SiteSessionManager.get().createCheckPoint( container, "VIFP_CLUB_BEFORE_HOVER" )
					.assertWaitThat( String.format( REASON, dataDefault ), FIVE_SECONDS, CONDITION_BOOL );

			navigationAdditional.hoverOnMenuItem( vifp );

			REASON = "asserting that \"vifp club\"  img[src] value after hovering ends with < '%s' >";
			EXPECTED_OF_STR = endsWith( dataHover );
			CONDITION_BOOL = ExpectedConditions.elementAttributeToMatch( img, "src", EXPECTED_OF_STR );
			SiteSessionManager.get().createCheckPoint( container, "VIFP_CLUB_AFTER_HOVER" )
					.assertWaitThat( String.format( REASON, dataDefault ), FIVE_SECONDS, CONDITION_BOOL );

			/* THEN It should take the user to Vacation Planner page: /loyalty/overview.aspx */
			VifpClubPage vifpClubPage = ( VifpClubPage )
					navigationAdditional.clickOnMenuItem( vifp, VIFP_CLUB );
			headerLinks = vifpClubPage.header().headerLinks();
			container = vifpClubPage.header().getContainer();

			/* AND the url query ends-with icid=CC_vifp-club_1866 */
			url = vifpClubPage.getURL();
			REASON = "asserting that url query ends-with icid=CC_vifp-club_1866";
			ACTUAL_STR = java.net.URLDecoder.decode( url.getQuery(), SystemUtils.FILE_ENCODING );
			EXPECTED_OF_STR = endsWith( "icid=CC_vifp-club_1866" );
			SiteSessionManager.get().createCheckPoint( container, "VIFP_CLUB_QUERY" )
					.assertThat( REASON, ACTUAL_STR, EXPECTED_OF_STR );

			/* AND the "MANAGE" level 1 menu item should be active. */
			manage = headerLinks.getLink( MANAGE );
			REASON = "asserting that \"MANAGE\" menu item is 'active'";
			EXPECTED_OF_STR = containsString( "active" );
			ACTUAL_STR = manage.getHtmlElement().getAttribute( "class" );
			SiteSessionManager.get().createCheckPoint( container, "MANAGE_ACTIVE_ON_VIFP_CLUB" )
					.assertThat( REASON, ACTUAL_STR, EXPECTED_OF_STR );

			SiteSessionManager.get().assertAllCheckpoints();
		}
		catch ( WebDriverException | AssertionError e )
		{
			throw new ApplicationException( SiteSessionManager.get().getDriver(), e );
		}
	}

	//endregion

	//region NavigationAdditionalTest - header_SubNavigation_Manage_Actions_AU

	@TestCaseId ( id = 65358 )
	@Steps ( steps = {
			@Step ( number = 1, description = "GIVEN user is on carnival home page" ),
			@Step ( number = 2, description = "WHEN user hovers over \"Manage\" tab",
					expectedResults = {
							"THEN should see No more than 6 items on the additional level-2 menu.",
							"AND The items appearance order matches the required order based on the current locale."
					} ),
			@Step ( number = 3, description = "AND WHEN the user hovers and clicks on \"Already Booked\" item.",
					expectedResults = {
							"THEN The image should change when hover to IMG-hover.",
							"AND It should take the user to /already-booked"
					} ),
			@Step ( number = 4, description = "AND WHEN the user hovers and clicks on \"Nouveau Reservation\" item.",
					expectedResults = {
							"THEN The image should change when hover to IMG-hover.",
							"AND it should take the user to /request-forms/nouveau-reservation.aspx",
							"AND The \"MANAGE\" level 1 menu item should be active."
					} ),
			@Step ( number = 5, description = "AND WHEN the user hovers and clicks on \"Check-In\" item.",
					expectedResults = {
							"THEN the image should change when hover to IMG-hover.",
							"THEN It should take the user to /OnlineCheckIn/index.asp on a new page",
							"AND the domain is secure",
							"AND the protocol is https",
							"AND the url query ends-with &brandCode=CL"
					} )
	} )
	@Test( description = "Header - SubNavigation - Manage - Actions - Verify SubNavigation associated links",
			enabled = true,
			groups = { "AU", "PROD" }
	)
	public void header_SubNavigation_Manage_Actions_AU() throws Exception
	{
		/* GIVEN user is on carnival home page" */
		PreConditions.checkNotNull( this.homePage, "Home page is null, before starting test" );
		try
		{
			Integer maxMenuItems = Integer.valueOf( SiteProperty.MAX_MENU_SUB_ITEMS.fromContext().toString() );
			String names = SiteProperty.MANAGE_MENU_ITEMS.fromContext().toString();
			HtmlElement container = homePage.header().getContainer();
			Header.NavigationAdditional navigationAdditional = homePage.header().navigationAdditional();
			Header.HeaderLinks headerLinks = homePage.header().headerLinks();

			/* WHEN user hovers over "Learn" tab */
			headerLinks.hoverOnItem( MANAGE );

			/* should see No more than 6 items on the additional level-2 menu */
			String REASON = "Asserting that menu sub-items are displayed";
			boolean ACTUAL_BOOL = navigationAdditional.isDisplayed();
			Matcher<Boolean> EXPECTED_OF_BOOL = is( true );
			SiteSessionManager.get().createCheckPoint( container, "MANAGE_ADDITIONAL_LINKS_DISPLAYED" )
					.assertThat( REASON, ACTUAL_BOOL, EXPECTED_OF_BOOL );

			List<String> itemsNames = navigationAdditional.getChildMenuItemsNames( MANAGE );
			REASON = "Asserting that we should see No more than 6 items on the additional level-2 menu";
			Matcher<Collection<? extends String>> EXPECTED_RESULT_OF_COLL_STR = hasSize( lessThanOrEqualTo( maxMenuItems ) );
			SiteSessionManager.get().createCheckPoint( container, "MANAGE_NO_MORE_THAN_6" )
					.assertThat( REASON, itemsNames, EXPECTED_RESULT_OF_COLL_STR );

			/* AND The items appearance order matches the required order based on the current locale. */
			REASON = "Asserting that all level-2 items appearance order matches the required order based on the current locale";
			String[] ACTUAL_OF_STR_ARR = itemsNames.toArray( new String[ itemsNames.size() ] );
			Matcher<String[]> EXPECTED_OF_STR_ARR = arrayContaining( StringUtils.split( names, "," ) );
			SiteSessionManager.get().createCheckPoint( container, "MANAGE_LEVEL_2_ITEMS" )
					.assertThat( REASON, ACTUAL_OF_STR_ARR, EXPECTED_OF_STR_ARR );

			/* AND WHEN the user hovers and clicks on "My Booking" item. */
			Link alreadyBooked = navigationAdditional.getLink( MANAGE, ALREADY_BOOKED );
			String dataDefault = navigationAdditional.getDataDefaultImg( alreadyBooked );
			String dataHover = navigationAdditional.getDataHoverImg( alreadyBooked );
			HtmlElement img = navigationAdditional.getImage( alreadyBooked );

			/* The image should change when hover to IMG-hover. */
			REASON = "asserting that  \"already booked\" img[src] value before hovering ends with < '%s' >";
			Matcher<String> EXPECTED_OF_STR = endsWith( dataDefault );
			HtmlCondition<Boolean> CONDITION_BOOL =
					ExpectedConditions.elementAttributeToMatch( img, "src", EXPECTED_OF_STR );
			SiteSessionManager.get().createCheckPoint( container, "ALREADY_BOOKED_BEFORE_HOVER" )
					.assertWaitThat( String.format( REASON, dataDefault ), FIVE_SECONDS, CONDITION_BOOL );

			navigationAdditional.hoverOnMenuItem( alreadyBooked );

			REASON = "asserting that \"already booked\" img[src] value after hovering ends with < '%s' >";
			EXPECTED_OF_STR = endsWith( dataHover );
			CONDITION_BOOL = ExpectedConditions.elementAttributeToMatch( img, "src", EXPECTED_OF_STR );
			SiteSessionManager.get().createCheckPoint( container, "ALREADY_BOOKED_AFTER_HOVER" )
					.assertWaitThat( String.format( REASON, dataDefault ), FIVE_SECONDS, CONDITION_BOOL );

			/* AND It should take the user to /already-booked */
			AlreadyBookedPage alreadyBookedPage = ( AlreadyBookedPage )
					navigationAdditional.clickOnMenuItem( alreadyBooked, ALREADY_BOOKED );
			navigationAdditional = alreadyBookedPage.header().navigationAdditional();
			headerLinks = alreadyBookedPage.header().headerLinks();
			container = alreadyBookedPage.header().getContainer();

			/* AND WHEN the user hovers and clicks on "Nouveau Reservation" item. */
			headerLinks.hoverOnItem( MANAGE );
			Link nouveauRes = navigationAdditional.getLink( MANAGE, NOUVEAU_RESERVATION );
			dataDefault = navigationAdditional.getDataDefaultImg( nouveauRes );
			dataHover = navigationAdditional.getDataHoverImg( nouveauRes );
			img = navigationAdditional.getImage( nouveauRes );

			/* THEN The image should change when hover to IMG-hover." */
			REASON = "asserting that \"nouveau reservation\" img[src] value before hovering ends with < '%s' >";
			EXPECTED_OF_STR = endsWith( dataDefault );
			CONDITION_BOOL = ExpectedConditions.elementAttributeToMatch( img, "src", EXPECTED_OF_STR );
			SiteSessionManager.get().createCheckPoint( container, "NOUVEAU_RESERVATION_BEFORE_HOVER" )
					.assertWaitThat( String.format( REASON, dataDefault ), FIVE_SECONDS, CONDITION_BOOL );

			navigationAdditional.hoverOnMenuItem( nouveauRes );

			REASON = "asserting that \"nouveau reservation\" img[src] value after hovering ends with < '%s' >";
			EXPECTED_OF_STR = endsWith( dataHover );
			CONDITION_BOOL = ExpectedConditions.elementAttributeToMatch( img, "src", EXPECTED_OF_STR );
			SiteSessionManager.get().createCheckPoint( container, "NOUVEAU_RESERVATION_AFTER_HOVER" )
					.assertWaitThat( String.format( REASON, dataDefault ), FIVE_SECONDS, CONDITION_BOOL );

			/* THEN It should take the user to Vacation Planner page: /loyalty/overview.aspx */
			NouveauReservationPage nouveauReservationPage = ( NouveauReservationPage )
					navigationAdditional.clickOnMenuItem( nouveauRes, NOUVEAU_RESERVATION );
			headerLinks = nouveauReservationPage.header().headerLinks();
			navigationAdditional = nouveauReservationPage.header().navigationAdditional();
			container = nouveauReservationPage.header().getContainer();

			/* AND The "MANAGE" level 1 menu item should be active. */
			Link manage = headerLinks.getLink( MANAGE );
			REASON = "asserting that \"MANAGE\" menu item is 'active'";
			HtmlElement he = manage.getHtmlElement();
			CONDITION_BOOL = ExpectedConditions.elementAttributeToMatch( he, "class", containsString( "active" ) );
			SiteSessionManager.get().createCheckPoint( container, "MANAGE_ACTIVE_ON_NOUVEAU_RESERVATION" )
					.assertWaitThat( REASON, TimeConstants.FIVE_SECONDS, CONDITION_BOOL );

			 /* AND WHEN the user hovers and clicks on "Check-In" item. */
			headerLinks.hoverOnItem( MANAGE );
			Sleeper.pauseFor( 1000 );
			Link checkIn = navigationAdditional.getLink( MANAGE, CHECK_IN );
			dataDefault = navigationAdditional.getDataDefaultImg( checkIn );
			dataHover = navigationAdditional.getDataHoverImg( checkIn );
			img = navigationAdditional.getImage( checkIn );

			/* The image should change when hover to IMG-hover. */
			REASON = "asserting that  \"check-in\" img[src] value before hovering ends with < '%s' >";
			EXPECTED_OF_STR = endsWith( dataDefault );
			CONDITION_BOOL = ExpectedConditions.elementAttributeToMatch( img, "src", EXPECTED_OF_STR );
			SiteSessionManager.get().createCheckPoint( container, "ONLINE_CHECKING_BEFORE_HOVER" )
					.assertWaitThat( String.format( REASON, dataDefault ), FIVE_SECONDS, CONDITION_BOOL );

			navigationAdditional.hoverOnMenuItem( checkIn );

			REASON = "asserting that \"check-in\" img[src] value after hovering ends with < '%s' >";
			EXPECTED_OF_STR = endsWith( dataHover );
			CONDITION_BOOL = ExpectedConditions.elementAttributeToMatch( img, "src", EXPECTED_OF_STR );
			SiteSessionManager.get().createCheckPoint( container, "ONLINE_CHECKING_AFTER_HOVER" )
					.assertWaitThat( String.format( REASON, dataDefault ), FIVE_SECONDS, CONDITION_BOOL );

			/* AND WHEN the user hovers and clicks on "Check-In" item. */
			OnlineCheckInPage onlineCheckInPage = ( OnlineCheckInPage )
					navigationAdditional.clickOnMenuItem( checkIn, CHECK_IN );

			/* HEN It should take the user to /OnlineCheckIn/index.asp on a new page */
			REASON = "Validates that a new window was opened";
			Set<String> ACTUAL_SET = onlineCheckInPage.getWindowHandles();
			Matcher<Collection<?>> EXPECTED_OF_SET = JMatchers.hasSize( 2 );
			SiteSessionManager.get().createCheckPoint( "ONLINE_CHECKING_HANDLES_COUNT" )
					.assertThat( REASON, ACTUAL_SET, EXPECTED_OF_SET );

			/* AND the protocol is https" */
			URL url = onlineCheckInPage.getURL();
			REASON = "asserting that protocol is https";
			String ACTUAL_STR = url.getProtocol();
			EXPECTED_OF_STR = equalToIgnoringCase( "https" );
			SiteSessionManager.get().createCheckPoint( container, "ONLINE_CHECKING_PROTOCOL" )
					.assertThat( REASON, ACTUAL_STR, EXPECTED_OF_STR );

			/* AND the url query ends-with &brandCode=CL */
			REASON = "asserting url query is &brandCode=CL";
			ACTUAL_STR = java.net.URLDecoder.decode( url.getQuery(), SystemUtils.FILE_ENCODING );
			EXPECTED_OF_STR = endsWith( "&brandCode=CL" );
			SiteSessionManager.get().createCheckPoint( container, "ONLINE_CHECKING_QUERY" )
					.assertThat( REASON, ACTUAL_STR, EXPECTED_OF_STR );

			onlineCheckInPage.close();

			SiteSessionManager.get().assertAllCheckpoints();
		}
		catch ( WebDriverException | AssertionError e )
		{
			throw new ApplicationException( SiteSessionManager.get().getDriver(), e );
		}
	}

	//endregion


	//endregion Tests


	//region NavigationAdditionalTest - After Configurations Methods Section

	@AfterMethod ( description = "", groups = { "US", "UK", "AU" }, enabled = true, alwaysRun = true )
	public void afterMethod( ITestResult testResult, XmlTest xmlTest, Method method ) throws Exception
	{
		try
		{
			if( method.getName().contains( "Manage" ) )
			{
				SiteSessionManager.get().closeSession();
				this.homePage = null;
			}

			if( this.homePage != null )
			{
				if( shouldCloseSession( testResult ) )
				{
					SiteSessionManager.get().closeSession();
					this.homePage = null;
				}
			}
		}
		catch (  Throwable e )
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
		catch (  Throwable e )
		{
			throw new Exception( e.getMessage(), e );
		}
	}

	//endregion


	//region HomePageTest - Data Provider Methods Section

	//endregion


}
