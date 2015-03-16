package com.framework.test.core.footer;

import com.framework.BaseTest;
import com.framework.driver.event.HtmlElement;
import com.framework.driver.exceptions.ApplicationException;
import com.framework.driver.objects.Link;
import com.framework.site.config.SiteSessionManager;
import com.framework.site.objects.footer.enums.FooterItem;
import com.framework.site.pages.core.HomePage;
import com.framework.testing.annotations.Step;
import com.framework.testing.annotations.Steps;
import com.framework.testing.annotations.TestCaseId;
import com.framework.utils.error.PreConditions;
import com.framework.utils.matchers.JMatchers;
import org.hamcrest.Matcher;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriverException;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;
import org.testng.xml.XmlTest;

import java.lang.reflect.Method;
import java.util.Map;


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

public class SubFooterTest extends BaseTest
{

	//region PostFooterTest - Variables Declaration and Initialization Section.

	private HomePage homePage = null;

	//endregion


	//region PostFooterTest - Constructor Methods Section

	private SubFooterTest()
	{
		BaseTest.classLogger = LoggerFactory.getLogger( SubFooterTest.class );
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

	@TestCaseId ( id = 40607 )
	@Steps ( steps = {
			@Step ( number = 1, description = "GIVEN that I am on www.carnival.com" ),
			@Step ( number = 2, description = "WHEN I view the post footer ribbon",
					expectedResults = {
							"THEN \"About Carnival\" link should exists",
							"AND \"Legal Notices\" link should exists",
							"AND \"Privacy Policy\" link should exists",
							"AND \"Careers\" link should exists",
							"AND \"Travel Partners\" link should exists",
							"AND \"Site Map\" link should exists",
							"AND \"FUN FOR ALL. ALL FOR FUN.\" Words should be trademarked",
							"AND Facebook Like button Link associated should be https://www.facebook.com/plugins/like/connect",
							"AND Facebook icon Link associated should be https://www.facebook.com/Carnival?ref=ts",
							"AND Twitter icon Link associated should be https://twitter.com/carnivalcruise",
							"And Funville icon Link associated should be http://www.carnival.com/Funville/?icid=CC_Footer_570",
							"And Instagram icon Link associated should be http://instagram.com/carnival",
							"And Pinterest icon Link associated should http://www.pinterest.com/carnivalcruise/"
					} ),
			@Step ( number = 3, description = "WHEN I click on \"About Carnival\" link",
					expectedResults = {
							"THEN the Link associated should redirect to /about-carnival/about-us.aspx?icid=CC_Footer_103"
					} ),
			@Step ( number = 4, description = "AND WHEN I click on \"Legal Notices\" link",
					expectedResults = {
							"THEN the Link associated should redirect to /about-carnival/legal-notice.aspx?icid=CC_Footer_83"
					} ),
			@Step ( number = 5, description = "AND WHEN I click on \"Privacy Policy\" link",
					expectedResults = {
							"THEN the Link associated should redirect to /legal-notice/privacy-policy.aspx?icid=CC_Footer_82"
					} ),
			@Step ( number = 6, description = "AND WHEN I click on \"Careers\" link",
					expectedResults = {
							"THEN the Link associated should redirect to /careers.aspx"
					} ),
			@Step ( number = 7, description = "AND WHEN I click on \"Travel Partners\" link",
					expectedResults = {
							"THEN the Link associated should redirect to http://www.goccl.com/?icid=CC_travel-partners_1758"
					} ),
			@Step ( number = 8, description = "AND WHEN I click on \"Site Map\" link",
					expectedResults = {
							"THEN the Link associated should redirect to /sitemap?icid=CC_sitemap-footer_1856"
					} ),
	} )
	@Test( description = "Sub Footer - Verify the elements in the post footer ribbon",
		   enabled = false,
		   groups = { "US", "PROD" }
	)
	public void footer_SubFooter_Elements_US() throws Exception
	{
		/* GIVEN user is on carnival home page" */
		PreConditions.checkNotNull( this.homePage, "Home page is null, before starting test" );
		try
		{
			HtmlElement container = homePage.footer().subFooter().getContainer();
			container.scrollIntoView();

			/* WHEN I view the post footer ribbon */
			String REASON = "Assert that Sub-Footer sections is visible";
			Matcher<Boolean> EXPECTED_OF_BOOL = JMatchers.is( true );
			boolean ACTUAL_BOOL = homePage.footer().subFooter().isDisplayed();
			SiteSessionManager.get().createCheckPoint( "SUB_FOOTER_VISIBLE" )
					.assertThat( REASON, ACTUAL_BOOL, EXPECTED_OF_BOOL );

			/* THEN "About Carnival" link should exists */
			REASON = "Assert that Sub-Footer item \"About Carnival\" exists.";
			EXPECTED_OF_BOOL = JMatchers.is( true );
			ACTUAL_BOOL = homePage.footer().subFooter().itemExists( FooterItem.ABOUT_CARNIVAL );
			SiteSessionManager.get().createCheckPoint( container, "ABOUT_CARNIVAL_EXISTS" )
					.assertThat( REASON, ACTUAL_BOOL, EXPECTED_OF_BOOL );

			Map<String,String> linksInfo = homePage.footer().subFooter().getInfo();

			/* THEN the Link associated should redirect to /about-carnival/about-us.aspx?icid=CC_Footer_103 */
			REASON = "Assert that Sub-Footer item \"About Carnival\" href ends-with /about-carnival/about-us.aspx?icid=CC_Footer_103.";
			Matcher<String> EXPECTED_STR = JMatchers.endsWithIgnoreCase( "/about-carnival/about-us.aspx?icid=CC_Footer_103" );
			String ACTUAL_OF_STR = linksInfo.get( "About Carnival" );
			SiteSessionManager.get().createCheckPoint( container, "ABOUT_CARNIVAL_HREF" )
					.assertThat( REASON, ACTUAL_OF_STR, EXPECTED_STR );

			/* AND "Legal Notices" link should exists */
			REASON = "Assert that Sub-Footer item \"Legal Notices\" exists.";
			EXPECTED_OF_BOOL = JMatchers.is( true );
			ACTUAL_BOOL = homePage.footer().subFooter().itemExists( FooterItem.LEGAL_NOTICES );
			SiteSessionManager.get().createCheckPoint( container, "LEGAL_NOTICES_EXISTS" )
					.assertThat( REASON, ACTUAL_BOOL, EXPECTED_OF_BOOL );

			/* THEN the Link associated should redirect to /about-carnival/legal-notice.aspx?icid=CC_Footer_83 */
			REASON = "Assert that Sub-Footer item \"Legal Notices\" href /about-carnival/legal-notice.aspx?icid=CC_Footer_83.";
			EXPECTED_STR = JMatchers.endsWithIgnoreCase( "/about-carnival/legal-notice.aspx?icid=CC_Footer_83" );
			ACTUAL_OF_STR = linksInfo.get( "Legal Notices" );
			SiteSessionManager.get().createCheckPoint( container, "LEGAL_NOTICES_HREF" )
					.assertThat( REASON, ACTUAL_OF_STR, EXPECTED_STR );

			/* AND "Privacy Policy" link should exists */
			REASON = "Assert that Sub-Footer item \"Privacy Policy\" exists.";
			EXPECTED_OF_BOOL = JMatchers.is( true );
			ACTUAL_BOOL = homePage.footer().subFooter().itemExists( FooterItem.PRIVACY_POLICY );
			SiteSessionManager.get().createCheckPoint( container, "PRIVACY_POLICY_EXISTS" )
					.assertThat( REASON, ACTUAL_BOOL, EXPECTED_OF_BOOL );

			/* THEN the Link associated should redirect to /legal-notice/privacy-policy.aspx?icid=CC_Footer_82 */
			REASON = "Assert that Sub-Footer item \"Privacy Policy\" href /legal-notice/privacy-policy.aspx?icid=CC_Footer_82.";
			EXPECTED_STR = JMatchers.endsWithIgnoreCase( "/legal-notice/privacy-policy.aspx?icid=CC_Footer_82" );
			ACTUAL_OF_STR = linksInfo.get( "Privacy Policy" );
			SiteSessionManager.get().createCheckPoint( container, "PRIVACY_POLICY_HREF" )
					.assertThat( REASON, ACTUAL_OF_STR, EXPECTED_STR );

			/* AND "Careers" link should exists */
			REASON = "Assert that Sub-Footer item \"Careers\" exists.";
			EXPECTED_OF_BOOL = JMatchers.is( true );
			ACTUAL_BOOL = homePage.footer().subFooter().itemExists( FooterItem.CAREERS );
			SiteSessionManager.get().createCheckPoint( container, "CAREERS_EXISTS" )
					.assertThat( REASON, ACTUAL_BOOL, EXPECTED_OF_BOOL );

			/* THEN the Link associated should redirect to /careers.aspx */
			REASON = "Assert that Sub-Footer item \"Careers\" href /careers.aspx.";
			EXPECTED_STR = JMatchers.endsWithIgnoreCase( "/careers.aspx" );
			ACTUAL_OF_STR = linksInfo.get( "Careers" );
			SiteSessionManager.get().createCheckPoint( container, "CAREERS_HREF" )
					.assertThat( REASON, ACTUAL_OF_STR, EXPECTED_STR );

			/* AND "Travel Partners" link should exists */
			REASON = "Assert that Sub-Footer item \"Travel Partners\" exists.";
			EXPECTED_OF_BOOL = JMatchers.is( true );
			ACTUAL_BOOL = homePage.footer().subFooter().itemExists( FooterItem.TRAVEL_PARTNERS );
			SiteSessionManager.get().createCheckPoint( container, "TRAVEL_PARTNERS_EXISTS" )
					.assertThat( REASON, ACTUAL_BOOL, EXPECTED_OF_BOOL );

			/* THEN the Link associated should redirect to http://www.goccl.com/?icid=CC_travel-partners_1758 */
			REASON = "Assert that Sub-Footer item \"Travel Partners\" href http://www.goccl.com/?icid=CC_travel-partners_1758.";
			EXPECTED_STR = JMatchers.endsWithIgnoreCase( "http://www.goccl.com/?icid=CC_travel-partners_1758" );
			ACTUAL_OF_STR = linksInfo.get( "Travel Partners" );
			SiteSessionManager.get().createCheckPoint( container, "TRAVEL_PARTNERS_HREF" )
					.assertThat( REASON, ACTUAL_OF_STR, EXPECTED_STR );

			/* AND "Site Map" link should exists */
			REASON = "Assert that Sub-Footer item \"Site Map\" exists.";
			EXPECTED_OF_BOOL = JMatchers.is( true );
			ACTUAL_BOOL = homePage.footer().subFooter().itemExists( FooterItem.SITE_MAP );
			SiteSessionManager.get().createCheckPoint( container, "SITE_MAP_EXISTS" )
					.assertThat( REASON, ACTUAL_BOOL, EXPECTED_OF_BOOL );

			/* THEN the Link associated should redirect to /sitemap?icid=CC_sitemap-footer_1856 */
			REASON = "Assert that Sub-Footer item \"Travel Partners\" href /sitemap?icid=CC_sitemap-footer_1856";
			EXPECTED_STR = JMatchers.endsWithIgnoreCase( "/sitemap?icid=CC_sitemap-footer_1856" );
			ACTUAL_OF_STR = linksInfo.get( "Site Map" );
			SiteSessionManager.get().createCheckPoint( container, "SITE_MAP_HREF" )
					.assertThat( REASON, ACTUAL_OF_STR, EXPECTED_STR );

			/* AND "FUN FOR ALL. ALL FOR FUN." Words should be trademarked */
			REASON = "Assert that \"FUN FOR ALL. ALL FOR FUN\" is trademarked.";
			EXPECTED_OF_BOOL = JMatchers.is( true );
			ACTUAL_BOOL = homePage.footer().subFooter().getTradeMark().childExists( By.tagName( "sup" ) ).isPresent();
			SiteSessionManager.get().createCheckPoint( container, "SLOGAN_IS_TRADEMARKED" )
					.assertThat( REASON, ACTUAL_BOOL, EXPECTED_OF_BOOL );

			/* AND Facebook Like button Link associated should be https://www.facebook.com/plugins/like/connect */
			REASON = "Assert that \"Facebook Like button\" is associated with ref.";
			Matcher<String> EXPECTED_OF_STR = JMatchers.endsWith( "/plugins/like/connect" );
			ACTUAL_OF_STR = homePage.footer().subFooter().getFacebookLikeRef();
			SiteSessionManager.get().createCheckPoint( container, "FACEBOOK_LIKE_HREF" )
					.assertThat( REASON, ACTUAL_OF_STR, EXPECTED_OF_STR );

			/* AND Facebook icon Link associated should be https://www.facebook.com/Carnival?ref=ts */

			REASON = "Assert that \"Facebook\" reference is http://www.facebook.com/Carnival?ref=ts";
			EXPECTED_OF_STR = JMatchers.is( "http://www.facebook.com/Carnival?ref=ts" );
			Link facebook = homePage.footer().subFooter().getFooterLinkItem( FooterItem.FACEBOOK );
			String ACTUAL_STR = facebook.getHReference();
			SiteSessionManager.get().createCheckPoint( container, "FACEBOOK_HREF" )
					.assertThat( REASON, ACTUAL_STR, EXPECTED_OF_STR );

			/* AND Twitter icon Link associated should be https://twitter.com/carnivalcruise */
			REASON = "Assert that \"Twitter\" reference is http://twitter.com/carnivalcruise";
			EXPECTED_OF_STR = JMatchers.is( "http://twitter.com/carnivalcruise" );
			Link twitter = homePage.footer().subFooter().getFooterLinkItem( FooterItem.TWITTER );
			ACTUAL_STR = twitter.getHReference();
			SiteSessionManager.get().createCheckPoint( container, "TWITTER_HREF" )
					.assertThat( REASON, ACTUAL_STR, EXPECTED_OF_STR );

			/* And Funville icon Link associated should be http://www.carnival.com/Funville/?icid=CC_Footer_570 */
			REASON = "Assert that \"Funville\" reference is http://www.carnival.com/Funville/?icid=CC_Footer_570";
			EXPECTED_OF_STR = JMatchers.is( "http://www.carnival.com/Funville/?icid=CC_Footer_570" );
			Link funville = homePage.footer().subFooter().getFooterLinkItem( FooterItem.FUNVILLE );
			ACTUAL_STR = funville.getHReference();
			SiteSessionManager.get().createCheckPoint( container, "FUNVILLE_HREF" )
					.assertThat( REASON, ACTUAL_STR, EXPECTED_OF_STR );

			/* And Instagram icon Link associated should be http://instagram.com/carnival */
			REASON = "Assert that \"Instagram\" reference is http://instagram.com/carnival";
			EXPECTED_OF_STR = JMatchers.is( "http://instagram.com/carnival" );
			Link instagram = homePage.footer().subFooter().getFooterLinkItem( FooterItem.INSTAGRAM );
			ACTUAL_STR = instagram.getHReference();
			SiteSessionManager.get().createCheckPoint( "container, INSTAGRAM_HREF" )
					.assertThat( REASON, ACTUAL_STR, EXPECTED_OF_STR );

			/* And Pinterest icon Link associated should http://www.pinterest.com/carnivalcruise/ */
			REASON = "Assert that \"Pinterest\" reference is http://instagram.com/carnival";
			EXPECTED_OF_STR = JMatchers.is( "http://www.pinterest.com/carnivalcruise/" );
			Link pinterest = homePage.footer().subFooter().getFooterLinkItem( FooterItem.PINTEREST );
			ACTUAL_STR = pinterest.getHReference();
			SiteSessionManager.get().createCheckPoint( "PINTEREST_HREF" )
					.assertThat( REASON, ACTUAL_STR, EXPECTED_OF_STR );

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
