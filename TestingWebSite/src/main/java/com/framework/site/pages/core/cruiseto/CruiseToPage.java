package com.framework.site.pages.core.cruiseto;

import com.framework.site.config.SiteProperty;
import com.framework.site.pages.BaseCarnivalPage;
import com.framework.testing.annotations.DefaultUrl;
import com.framework.utils.matchers.JMatchers;
import org.hamcrest.Matcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



@DefaultUrl( value = "/cruise-to.aspx", matcher = "endsWith()" )
public class CruiseToPage extends BaseCarnivalPage
{

	//region CruiseToPage - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( CruiseToPage.class );

	private static final String LOGICAL_NAME = "Cruise To Page";


	// ------------------------------------------------------------------------|
	// --- WEB-OBJECTS DEFINITIONS --------------------------------------------|
	// ------------------------------------------------------------------------|

	//endregion


	//region CruiseToPage - Constructor Methods Section

	public CruiseToPage()
	{
		super( LOGICAL_NAME );
		validatePageInitialState();
	}

	//endregion


	//region CruiseToPage - Initialization and Validation Methods Section


	protected void validatePageInitialState()
	{
		logger.debug( "validating static elements for: <{}>, name:<{}>...", getQualifier(), getLogicalName() );
	}

	@Override
	protected void validatePageTitle()
	{
		String title = ( String ) SiteProperty.CRUISE_TO_TITLE.fromContext();
		final Matcher<String> EXPECTED_TITLE = JMatchers.equalToIgnoringCase( title );
		final String REASON = String.format( "Asserting \"%s\" page's title", LOGICAL_NAME );
		getDriver().assertThat( REASON, getTitle(), EXPECTED_TITLE );
	}

	//endregion


	//region CruiseToPage - Service Methods Section

	//endregion


	//region CruiseToPage - Business Methods Section

	//endregion


	//region CruiseToPage - Element Finder Methods Section

	//endregion

}
