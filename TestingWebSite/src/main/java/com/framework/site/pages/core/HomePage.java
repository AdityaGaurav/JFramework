package com.framework.site.pages.core;

import com.framework.asserts.JAssertions;
import com.framework.driver.exceptions.ApplicationException;
import com.framework.driver.utils.ui.WaitUtil;
import com.framework.matchers.MatcherUtils;
import com.framework.site.config.InitialPage;
import com.framework.site.data.TestEnvironment;
import com.framework.site.objects.body.interfaces.LinkTout;
import com.framework.site.objects.footer.interfaces.Footer;
import com.framework.site.objects.header.interfaces.Header;
import com.framework.site.pages.CarnivalPage;
import com.framework.utils.spring.AppContextProxy;
import com.google.common.base.MoreObjects;
import com.google.common.base.Throwables;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Locale;
import java.util.Map;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.site.pages.core
 *
 * Name   : HomePage
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-01-05
 *
 * Time   : 23:10
 */

public class HomePage extends CarnivalPage
{

	//region HomePage - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( HomePage.class );

	private static final String LOGICAL_NAME = "Home Page";

	// ------------------------------------------------------------------------|
	// --- WEB-OBJECTS DEFINITIONS --------------------------------------------|
	// ------------------------------------------------------------------------|

	private List<LinkTout> linkTouts = null;

	//endregion


	//region HomePage - Constructor Methods Section

	public HomePage( final WebDriver driver )
	{
		super( LOGICAL_NAME, driver );
	}


	//endregion


	//region HomePage - Initialization and Validation Methods Section

	@Override
	protected void validatePageUrl()
	{
		WebDriverWait wait = WaitUtil.wait60( pageDriver );
		ExpectedCondition<Boolean> expectedCondition;

		try
		{
			Object ol = InitialPage.getRuntimeProperties().getRuntimePropertyValue( "locale" );
			Object oe = InitialPage.getRuntimeProperties().getRuntimePropertyValue( "environment" );
			Locale expectedLocale = ( Locale ) ol;
			TestEnvironment expectedEnvironment = ( TestEnvironment ) oe;
			logger.debug( "runtime property value for locale is -> {}", expectedLocale.getDisplayCountry() );
			logger.debug( "runtime property value for environment is -> {}", expectedEnvironment.name() );

			String countryCode = expectedLocale.getCountry().equals( "GB" ) ? "UK" :  expectedLocale.getCountry();
			Map environments = ( Map ) AppContextProxy.getInstance().getBean( "domains" + countryCode );

			final String EXPECTED_ENV_URL = ( String ) environments.get( expectedEnvironment );
			final String EXPECTED_TITLE = ( String ) AppContextProxy.getInstance().getMessage( "home.page.title", null, expectedLocale );
			expectedCondition = WaitUtil.urlMatches( MatcherUtils.equalToIgnoringCase( EXPECTED_ENV_URL ) );

			/* asserting that current url matches expected url */

			JAssertions.assertWaitThat( wait ).matchesCondition( expectedCondition );
			logger.info( "page url successfully asserted -> equalToIgnoringCase( \"{}\" )", EXPECTED_ENV_URL );

			/* asserting that page is matching expected locale */

			String actualLocale = getSiteRegion();
			logger.debug( "Current site region is <'{}'>", actualLocale );
			JAssertions.assertThat( actualLocale ).isEqualToIgnoringCase( countryCode );

			/* asserting page title */

			JAssertions.assertThat( pageDriver ).matchesTitle( MatcherUtils.equalTo( EXPECTED_TITLE ) );
			logger.info( "page title successfully asserted -> equalToIgnoringCase( \"{}\" )", EXPECTED_TITLE  );
		}
		catch ( AssertionError ae )
		{
			Throwables.propagateIfInstanceOf( ae, ApplicationException.class );
			logger.error( "throwing a new WebObjectException on {}#validatePageUrl.", getClass().getSimpleName() );
			ApplicationException ex = new ApplicationException( pageDriver.getWrappedDriver(), ae.getMessage(), ae );
			ex.addInfo( "cause", "verification and initialization process for object " + getLogicalName() + " was failed." );
			throw ex;
		}
	}

	/**
	 * asserting presence of static web elements on page.
	 * <ul>
	 *     <li>div#ccl_homepage</li>
	 *     <li>form#form2</li>
	 *     <li>div#ccl-refresh-header</li>
	 *     <li>div.core-footer</li>
	 * </ul>
	 */
	@Override
	protected void initElements()
	{
		logger.debug( "validating static elements for: <{}>, name:<{}>...", getId(), getLogicalName() );
		WebDriverWait wew = WaitUtil.wait10( pageDriver );

		try
		{
			JAssertions.assertWaitThat( wew ).matchesCondition( WaitUtil.presenceBy( By.id( "ccl_homepage" ) ) );
			JAssertions.assertWaitThat( wew ).matchesCondition( WaitUtil.presenceBy( By.id( "form2" ) ) );
			JAssertions.assertWaitThat( wew ).matchesCondition( WaitUtil.presenceBy( Header.ROOT_BY ) );
			JAssertions.assertWaitThat( wew ).matchesCondition( WaitUtil.presenceBy( Footer.ROOT_BY ) );

		}
		catch ( AssertionError ae )
		{
			Throwables.propagateIfInstanceOf( ae, ApplicationException.class );
			logger.error( "throwing a new WebObjectException on {}#initElements.", getClass().getSimpleName() );
			ApplicationException ex = new ApplicationException( pageDriver.getWrappedDriver(), ae.getMessage(), ae );
			ex.addInfo( "cause", "verification and initialization process for page " + getLogicalName() + " was failed." );
			throw ex;
		}
	}

	//endregion


	//region HomePage - Service Methods Section

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper( this )
				.add( "object id", getId() )
				.add( "page id", pageId() )
				.add( "logical name", getLogicalName() )
				.add( "pageName", pageName() )
				.add( "site region", getSiteRegion() )
				.add( "title", getTitle() )
				.add( "url", getCurrentUrl() )
				.omitNullValues()
				.toString();
	}


	//endregion


	//region HomePage - Business Methods Section

	//endregion


	//region HomePage - Element Finder Methods Section

	private WebElement findForm2()
	{
		By findBy = By.id( "form2" );
		return pageDriver.findElement( findBy );
	}

	private WebElement findCclHomePage()
	{
		By findBy = By.id( "ccl-refresh-homepage" );
		return findForm2().findElement( findBy );
	}


	//endregion

}
