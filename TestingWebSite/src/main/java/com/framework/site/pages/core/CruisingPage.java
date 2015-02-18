package com.framework.site.pages.core;

import com.framework.site.config.SiteProperty;
import com.framework.site.pages.BaseCarnivalPage;
import com.framework.testing.annotations.DefaultUrl;
import com.framework.utils.matchers.JMatchers;
import org.hamcrest.Matcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


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

@DefaultUrl( value = "/cruising.aspx", matcher = "contains()" )
public class CruisingPage extends BaseCarnivalPage
{

	//region CruisingPage - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( CruisingPage.class );

	private static final String LOGICAL_NAME = "Cruising Page";

	// ------------------------------------------------------------------------|
	// --- WEB-OBJECTS DEFINITIONS --------------------------------------------|
	// ------------------------------------------------------------------------|


	//endregion


	//region CruisingPage - Constructor Methods Section

	public CruisingPage()
	{
		super( LOGICAL_NAME );
		validatePageInitialState();
	}

	//endregion


	//region CruisingPage - Initialization and Validation Methods Section

	protected void validatePageInitialState()
	{
		logger.debug( "validating static elements for: <{}>, name:<{}>...", getQualifier(), getLogicalName() );
	}

	@Override
	protected void validatePageTitle()
	{
		String title = ( String ) SiteProperty.CRUISING_PAGE_TITLE.fromContext();
		final Matcher<String> EXPECTED_TITLE = JMatchers.equalToIgnoringCase( title );
		final String REASON = String.format( "Asserting \"%s\" page's title", LOGICAL_NAME );

		getDriver().assertThat( REASON, getTitle(), EXPECTED_TITLE );
	}


	//endregion


	//region CruisingPage - Service Methods Section




	//endregion


	//region CruisingPage - Business Methods Section

	//endregion


	//region CruisingPage - Element Finder Methods Section

	//endregion

}
