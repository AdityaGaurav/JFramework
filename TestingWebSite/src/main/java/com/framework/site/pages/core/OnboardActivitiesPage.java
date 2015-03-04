package com.framework.site.pages.core;

import com.framework.site.pages.BaseCarnivalPage;
import com.framework.testing.annotations.DefaultUrl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@DefaultUrl( value = "/onboard.aspx", matcher = "endsWith()" )
public class OnboardActivitiesPage extends BaseCarnivalPage
{

	//region CruiseToPage - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( OnboardActivitiesPage.class );

	private static final String LOGICAL_NAME = "Onboard Activities Page";

	// ------------------------------------------------------------------------|
	// --- WEB-OBJECTS DEFINITIONS --------------------------------------------|
	// ------------------------------------------------------------------------|

	//endregion


	//region CruiseToPage - Constructor Methods Section

	public OnboardActivitiesPage()
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

	//endregion


	//region CruiseToPage - Service Methods Section



	//endregion


	//region CruiseToPage - Business Methods Section

	//endregion


	//region CruiseToPage - Element Finder Methods Section

	//endregion

}
