package com.framework.site.pages.core;

import com.framework.site.config.SiteProperty;
import com.framework.site.pages.BaseCarnivalPage;
import com.framework.testing.annotations.DefaultUrl;
import com.framework.utils.matchers.JMatchers;
import org.hamcrest.Matcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



@DefaultUrl( value = "/cruise-destinations-and-ports.aspx", matcher = "contains()" )
public class CruiseDestinationsAndPortsPage extends BaseCarnivalPage
{

	//region CruiseDestinationsAndPortsPage - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( CruiseDestinationsAndPortsPage.class );

	private static final String LOGICAL_NAME = "Destinations and Ports Page";

	private static final String PAGE_TITLE_KEY = "cruise.destinations.and.ports.tile";

	// ------------------------------------------------------------------------|
	// --- WEB-OBJECTS DEFINITIONS --------------------------------------------|
	// ------------------------------------------------------------------------|


	//endregion


	//region CruiseDestinationsAndPortsPage - Constructor Methods Section

	public CruiseDestinationsAndPortsPage()
	{
		super( LOGICAL_NAME );
		validatePageInitialState();
	}

	//endregion


	//region CruiseDestinationsAndPortsPage - Initialization and Validation Methods Section

	protected void validatePageInitialState()
	{
		logger.debug( "validating static elements for: <{}>, name:<{}>...", getQualifier(), getLogicalName() );
	}

	@Override
	protected void validatePageTitle()
	{
		String title = ( String ) SiteProperty.CRUISE_DESTINATIONS_AND_PORTS_TILE.fromContext();
		final Matcher<String> EXPECTED_TITLE = JMatchers.equalToIgnoringCase( title );
		final String REASON = String.format( "Asserting \"%s\" page's title", LOGICAL_NAME );

		getDriver().assertThat( REASON, getTitle(), EXPECTED_TITLE );
	}

	//endregion


	//region CruiseDestinationsAndPortsPage - Service Methods Section



	//endregion


	//region CruiseDestinationsAndPortsPage - Business Methods Section

	//endregion


	//region CruiseDestinationsAndPortsPage - Element Finder Methods Section

	//endregion

}
