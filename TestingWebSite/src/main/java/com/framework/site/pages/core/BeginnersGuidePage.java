package com.framework.site.pages.core;

import com.framework.asserts.JAssertions;
import com.framework.driver.exceptions.ApplicationException;
import com.framework.driver.utils.ui.WaitUtil;
import com.framework.matchers.MatcherUtils;
import com.framework.site.config.InitialPage;
import com.framework.site.pages.CarnivalPage;
import com.framework.utils.spring.AppContextProxy;
import com.google.common.base.MoreObjects;
import com.google.common.base.Throwables;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Locale;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.site.pages.core
 *
 * Name   : CruisingPage
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-01-09
 *
 * Time   : 00:08
 */

public class BeginnersGuidePage extends CarnivalPage
{

	//region BeginnersGuidePage - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( BeginnersGuidePage.class );

	private static final String LOGICAL_NAME = "Beginner's Guide";

	public static final String URL_PATH = "/content/rookie/rookie.aspx";

	private static final String PAGE_TITLE_KEY = "beginners.guide.title";

	public static final String ON_SHIP_QUERY = "on-ship";

	public static final String SHOREX_QUERY = "shore-excursions";

	public static final String DESTINATIONS_QUERY = "destinations";

	public static final String INCLUDED_QUERY ="whats-included";

	// ------------------------------------------------------------------------|
	// --- WEB-OBJECTS DEFINITIONS --------------------------------------------|
	// ------------------------------------------------------------------------|


	//endregion


	//region BeginnersGuidePage - Constructor Methods Section

	public BeginnersGuidePage( final WebDriver driver )
	{
		super( LOGICAL_NAME, driver );
	}

	//endregion


	//region BeginnersGuidePage - Initialization and Validation Methods Section

	@Override
	protected void validatePageUrl()
	{
		WebDriverWait wait = WaitUtil.wait60( pageDriver );
		ExpectedCondition<Boolean> expectedCondition;

		try
		{
			expectedCondition = WaitUtil.urlMatches( MatcherUtils.containsString( URL_PATH ) );

			/* asserting that current url matches expected url */

			JAssertions.assertWaitThat( wait ).matchesCondition( expectedCondition );
			logger.info( "page url successfully asserted -> containsString( \"{}\" )", URL_PATH );

			/* asserting page title */

			Locale locale = ( Locale ) InitialPage.getRuntimeProperties().getRuntimePropertyValue( "locale" );
			final String EXPECTED_TITLE = ( String ) AppContextProxy.getInstance().getMessage( PAGE_TITLE_KEY, null, locale );
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

	@Override
	protected void initElements()
	{
		logger.debug( "validating static elements for: <{}>, name:<{}>...", getId(), getLogicalName() );

		try
		{

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


	//region BeginnersGuidePage - Service Methods Section

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


	//region BeginnersGuidePage - Business Methods Section

	//endregion


	//region BeginnersGuidePage - Element Finder Methods Section

	//endregion

}
