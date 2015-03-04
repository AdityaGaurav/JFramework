package com.framework.site.pages.core;

import com.framework.site.pages.BaseCarnivalPage;
import com.framework.testing.annotations.DefaultUrl;
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

	//endregion


	//region CruiseDestinationsAndPortsPage - Service Methods Section



	//endregion


	//region CruiseDestinationsAndPortsPage - Business Methods Section

	//endregion


	//region CruiseDestinationsAndPortsPage - Element Finder Methods Section

	//endregion

}
