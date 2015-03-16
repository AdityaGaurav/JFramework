package com.framework.test.core.footer;

import com.framework.BaseTest;
import com.framework.driver.event.HtmlElement;
import com.framework.driver.exceptions.ApplicationException;
import com.framework.site.config.SiteSessionManager;
import com.framework.site.objects.footer.LinkListObject;
import com.framework.site.pages.core.HomePage;
import com.framework.testing.annotations.Step;
import com.framework.testing.annotations.Steps;
import com.framework.testing.annotations.TestCaseId;
import com.framework.utils.error.PreConditions;
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
 * Package: com.framework.test.core.footer
 *
 * Name   : PostFooterTest 
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-02-08 
 *
 * Time   : 18:30
 *
 */

public class LinkListTest extends BaseTest
{

	//region PostFooterTest - Variables Declaration and Initialization Section.

	private HomePage homePage = null;

	//endregion


	//region PostFooterTest - Constructor Methods Section

	private LinkListTest()
	{
		BaseTest.classLogger = LoggerFactory.getLogger( LinkListTest.class );
	}

	//endregion


	//region PostFooterTest - Before Configurations Methods Section

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
			if ( e instanceof WebDriverException || e instanceof AssertionError )
			{
				throw new ApplicationException( SiteSessionManager.get().getDriver(), e );
			}
			throw new Exception( e.getMessage(), e );
		}
	}


	//endregion


	//region PostFooterTest - Test Methods Section

	@TestCaseId ( id = 41088 )
	@Steps ( steps = {
			@Step ( number = 1, description = "GIVEN that I am on www.carnival.com" ),
			@Step ( number = 2, description = "WHEN user observes the link-list and \"ABOUT CARNIVAL\" section",
					expectedResults = { "\"Press/Media\" link exists",
										"\"Video Center\" link exists",
										"\"St. Jude\" link exists",
										"\"Carnival Foundation\" link exists",
										"\"Safety and Security\" link exists",
										"\"Sustainability\" link exists",
										"\"Business Ethics\' link exists",
										"\"Investor Relations\" link exists",
										"\"World’s Leading Cruise Lines\" link exists"
				} ),
			@Step ( number = 3, description = "WHEN user User clicks on each link",
					expectedResults = { "It should take the user to correct page and not broken link." }
			)
	} )
	@Test( description = "Sub Footer - About Carnival. Visit all links",
			enabled = true,
			groups = { "US", "PROD" }
	)
	public void footer_LinkList_AboutCarnival() throws Exception
	{
	 	/* GIVEN user is on carnival home page */
		PreConditions.checkNotNull( this.homePage, "Home page is null, before starting test" );
		try
		{
			HtmlElement container = homePage.footer().linkList().getContainer();
			container.scrollIntoView();

			/* WHEN user observes the link-list and "ABOUT CARNIVAL" section */
			LinkListObject linkList = homePage.footer().linkList();


			/* "Press/Media" link exists */
//			String REASON = "Assert that Press/Media link exists";
//			Matcher<Boolean> EXPECTED_OF_BOOL = JMatchers.is( true );
//			boolean ACTUAL_BOOL = linkList.getInfo()
//			SiteSessionManager.get().createCheckPoint( "SUB_FOOTER_VISIBLE" )
//					.assertThat( REASON, ACTUAL_BOOL, EXPECTED_OF_BOOL );
//
//
//			Table<String, String, String> linksInfo = linkList.getInfo();

			SiteSessionManager.get().assertAllCheckpoints();
		}
		catch ( WebDriverException | AssertionError e )
		{
			throw new ApplicationException( SiteSessionManager.get().getDriver(), e );
		}
	}

	//endregion


	//region PostFooterTest - After Configurations Methods Section

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


	//region PostFooterTest - Data Provider Methods Section

	//endregion

}
