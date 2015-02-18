package com.framework.site.pages.core;

import com.framework.site.config.SiteProperty;
import com.framework.site.pages.BaseCarnivalPage;
import com.framework.testing.annotations.DefaultUrl;
import com.framework.utils.matchers.JMatchers;
import org.hamcrest.Matcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@DefaultUrl( value = "/core/closetohome.aspx", matcher = "endsWith()" )
public class CloseToHomePage extends BaseCarnivalPage
{

	//region CloseToHomePage - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( CloseToHomePage.class );

	private static final String LOGICAL_NAME = "Find a Port Page";

	// ------------------------------------------------------------------------|
	// --- WEB-OBJECTS DEFINITIONS --------------------------------------------|
	// ------------------------------------------------------------------------|

	//endregion


	//region CloseToHomePage - Constructor Methods Section

	public CloseToHomePage()
	{
		super( LOGICAL_NAME );
		validatePageInitialState();
	}

	//endregion


	//region CloseToHomePage - Initialization and Validation Methods Section


	protected void validatePageInitialState()
	{
		logger.debug( "validating static elements for: <{}>, name:<{}>...", getQualifier(), getLogicalName() );
	}

	@Override
	protected void validatePageTitle()
	{
		String title = ( String ) SiteProperty.FIND_A_PORT_TITLE.fromContext();
		final Matcher<String> EXPECTED_TITLE = JMatchers.equalToIgnoringCase( title );
		final String REASON = String.format( "Asserting \"%s\" page's title", LOGICAL_NAME );

		getDriver().assertThat( REASON, getTitle(), EXPECTED_TITLE );
	}

	//endregion


	//region CloseToHomePage - Service Methods Section



	//endregion


	//region CloseToHomePage - Business Methods Section

	//endregion


	//region CloseToHomePage - Element Finder Methods Section

	//endregion

}
