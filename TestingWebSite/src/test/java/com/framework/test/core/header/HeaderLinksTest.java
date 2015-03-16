package com.framework.test.core.header;

import com.framework.BaseTest;
import com.framework.driver.exceptions.ApplicationException;
import com.framework.site.config.SiteSessionManager;
import com.framework.site.data.Guest;
import com.framework.site.objects.header.interfaces.Header;
import com.framework.site.pages.BaseCarnivalPage;
import com.framework.site.pages.core.HomePage;
import com.framework.site.process.BookedGuestLoginProcess;
import com.framework.testing.annotations.Issue;
import com.framework.testing.annotations.Step;
import com.framework.testing.annotations.Steps;
import com.framework.testing.annotations.TestCaseId;
import com.framework.utils.error.PreConditions;
import com.framework.utils.matchers.JMatchers;
import com.framework.utils.spring.AppContextProxy;
import org.hamcrest.Matcher;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriverException;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;
import org.testng.xml.XmlTest;

import java.lang.reflect.Method;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.test.core.header
 *
 * Name   : HeaderLinksTest 
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-02-07 
 *
 * Time   : 22:30
 *
 */

public class HeaderLinksTest extends BaseTest
{

	//region HeaderLinksTest - Variables Declaration and Initialization Section.

	private HomePage homePage = null;

	//endregion


	//region HeaderLinksTest - Constructor Methods Section

	private HeaderLinksTest()
	{
		BaseTest.classLogger = LoggerFactory.getLogger( HeaderLinksTest.class );
	}

	//endregion


	//region HeaderLinksTest - Before Configurations Methods Section

	@BeforeClass ( description = "starts a web driver",
			alwaysRun = true,
			groups = { "US", "UK", "AU" }
	)
	public void beforeClass( ITestContext testContext, XmlTest xmlTest ) throws Exception
	{
		killExistingBrowsersOpenedByWebDriver();
		SiteSessionManager.get().startSession();
	}

	@BeforeGroups ( description = "", enabled = true, alwaysRun = false )
	public void beforeGroup()
	{
		System.out.println(	"VALIDATING LOCALE");
	}

	@BeforeMethod ( description = "Initiates Home Page",
			enabled = true,
			groups = { "US", "UK", "AU" }
	)
	public void beforeMethod( ITestContext testContext, XmlTest xmlTest, Method method, Object[] parameters ) throws Exception
	{
		try
		{
			if( null == this.homePage )
			{
				this.homePage = SiteSessionManager.get().getHomePage();
			}
			if( method.getName().equals( "header_UsernameAndLogoutLinkReplace" ) )
			{
				Guest guest = ( Guest ) AppContextProxy.get().getBean( "user.no.booking" );
				BookedGuestLoginProcess login = new BookedGuestLoginProcess( homePage.header().headerLinks() );
				login.setUser( guest );
				this.homePage = ( HomePage ) login.doLogin( HomePage.class );
				this.homePage.waitForUserCookies();
				testContext.setAttribute( "user.no.booking", guest );
			}
		}
		catch ( Throwable e )
		{
			throw new Exception( e.getMessage(), e );
		}
	}

	//endregion


	//region HeaderLinksTest - Test Methods Section

	@Issue( "TD3475" )
	@TestCaseId( id = 40484 )
	@Steps ( steps = {
			@Step ( number = 1, description = "GIVEN that the user has logged into carnival.com with a profile that has no bookings" ),
			@Step ( number = 2, description = "WHEN the user views the header",
					expectedResults = {
							"THEN the Create Account link has been replaced by \"Hi, <users first name>\"",
							"AND The users first name should be a link that points to /BookedGuest/GuestManagement/MyProfile",
							"AND the Login link is replaced by a \"Logout\" link",
							"AND logout link should point to /bookedguest/guestmanagement/mycarnival/logout"
					} )
	} )
	@Test( description = "Header - Top Tier Navigation - Actions - " +
			             "Verify that the users name and the logout link replace create account and login after the user logs in",
		   enabled = true,
		   groups = { "US", "PROD" }
	)
	public void header_UsernameAndLogoutLinkReplace( ITestContext testContext ) throws Exception
	{
	  	/* GIVEN that the user has logged into carnival.com with a profile that has no bookings */
		PreConditions.checkNotNull( this.homePage, "Home page is null, before starting test" );
		try
		{
			SiteSessionManager.get().createAssertion()
					.assertThat( "User is logged in", homePage.isUserLoggedIn(), JMatchers.is( true ) );

			/* WHEN the user views the header */

			Header.HeaderLinks headerLinks = this.homePage.header().headerLinks();

			/* THEN the Create Account link has been replaced by "Hi, <users first name> */

			Cookie cookie = SiteSessionManager.get().getCookieNamed( BaseCarnivalPage.USER_FIRST_NAME_COOKIE );
			Guest user = ( Guest ) testContext.getAttribute( "user.no.booking" );
			String REASON = "Create Account was replaced by \"Hi, " + user.getFirstName() + "\"";
			Matcher<String> EXPECTED_RESULT_OF_STR = JMatchers.is( "Hi, " + user.getFirstName() );
			String ACTUAL_RESULT_STR = "Hi, " + cookie.getValue();
			SiteSessionManager.get().createCheckPoint( "USER_GREETING_TEXT" )
					.assertThat( REASON, ACTUAL_RESULT_STR, EXPECTED_RESULT_OF_STR );

			/* AND The users first name should be a link that points to /BookedGuest/GuestManagement/MyProfile */

			REASON = "AND The users first name should be a link that points to ...";
			EXPECTED_RESULT_OF_STR = JMatchers.endsWith( "/BookedGuest/GuestManagement/MyProfile" );
			ACTUAL_RESULT_STR = this.homePage.header().headerLinks().getGreetingLink().getHReference();
			SiteSessionManager.get().createCheckPoint( "USER_GREETING_HREF" )
					.assertThat( REASON, ACTUAL_RESULT_STR, EXPECTED_RESULT_OF_STR );

			/* AND the Login link is replaced by a "Logout" link */

			REASON = "AND the Login link is replaced by a \"Logout\" link";
			EXPECTED_RESULT_OF_STR = JMatchers.is( "Logout" );
			ACTUAL_RESULT_STR = this.homePage.header().headerLinks().getLoginLink().getText();
			SiteSessionManager.get().createCheckPoint( "LOGIN_REPLACED_LOGOUT" )
					.assertThat( REASON, ACTUAL_RESULT_STR, EXPECTED_RESULT_OF_STR );

			/* AND logout link should point to /bookedguest/guestmanagement/mycarnival/logout */

			REASON = "AND The logout link should point to ...";
			EXPECTED_RESULT_OF_STR = JMatchers.endsWith( "/bookedguest/guestmanagement/mycarnival/logout" );
			ACTUAL_RESULT_STR = this.homePage.header().headerLinks().getLoginLink().getHReference();
			SiteSessionManager.get().createCheckPoint( "LOGOUT_HREF" )
					.assertThat( REASON, ACTUAL_RESULT_STR, EXPECTED_RESULT_OF_STR );

			SiteSessionManager.get().assertAllCheckpoints();
		}
		catch ( WebDriverException | AssertionError e )
		{
			throw new ApplicationException( SiteSessionManager.get().getDriver(), e );
		}
	}

	//endregion


	//region HeaderLinksTest - After Configurations Methods Section

	@AfterMethod ( description = "", groups = { "US", "UK", "AU" }, enabled = true, alwaysRun = true )
	public void afterMethod( ITestResult testResult, XmlTest xmlTest, Method method ) throws Exception
	{
		try
		{
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


	//region HeaderLinksTest - Data Provider Methods Section

	//endregion

}
