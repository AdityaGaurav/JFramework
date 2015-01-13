package com.framework.site.pages.funshops;

import com.framework.asserts.JAssertions;
import com.framework.driver.exceptions.ApplicationException;
import com.framework.driver.utils.ui.WaitUtil;
import com.framework.matchers.MatcherUtils;
import com.framework.site.config.InitialPage;
import com.framework.site.pages.BaseCarnivalPage;
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
 * Name   : CruiseToPage
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-01-10
 *
 * Time   : 01:17
 */

public class FunShopsPage extends BaseCarnivalPage
{

	//region FunShopsPage - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( FunShopsPage.class );

	private static final String LOGICAL_NAME = "Fun Shop Page";

	private static final String URL_PATH = "/Funshops/";

	private static final String PAGE_TITLE_KEY = "fun.shop.title";

	// ------------------------------------------------------------------------|
	// --- WEB-OBJECTS DEFINITIONS --------------------------------------------|
	// ------------------------------------------------------------------------|

	//endregion


	//region FunShopsPage - Constructor Methods Section

	public FunShopsPage( final WebDriver driver )
	{
		super( LOGICAL_NAME, driver );
	}

	//endregion


	//region FunShopsPage - Initialization and Validation Methods Section

	@Override
	protected void validatePageUrl()
	{
		WebDriverWait wait = WaitUtil.wait60( pageDriver );
		ExpectedCondition<Boolean> expectedCondition;

		try
		{
			expectedCondition = WaitUtil.urlMatches( MatcherUtils.endsWith( URL_PATH ) );

			/* asserting that current url matches expected url */

			JAssertions.assertWaitThat( wait ).matchesCondition( expectedCondition );
			logger.info( "page url successfully asserted -> endsWith( \"{}\" )", URL_PATH );

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


	//region FunShopsPage - Service Methods Section

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


	//region FunShopsPage - Business Methods Section

	//endregion


	//region FunShopsPage - Element Finder Methods Section

	//endregion

}
