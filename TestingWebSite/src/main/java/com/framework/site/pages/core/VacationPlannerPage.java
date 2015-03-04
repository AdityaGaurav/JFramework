package com.framework.site.pages.core;

import com.framework.site.pages.BaseCarnivalPage;
import com.framework.testing.annotations.DefaultUrl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@DefaultUrl( value = "/vacation-planner.aspx", matcher = "contains()" )
public class VacationPlannerPage extends BaseCarnivalPage
{

	//region VacationPlannerPage - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( VacationPlannerPage.class );

	private static final String LOGICAL_NAME = "Vacation Planner Page";

	// ------------------------------------------------------------------------|
	// --- WEB-OBJECTS DEFINITIONS --------------------------------------------|
	// ------------------------------------------------------------------------|


	//endregion


	//region VacationPlannerPage - Constructor Methods Section

	public VacationPlannerPage()
	{
		super( LOGICAL_NAME );
		validatePageInitialState();
	}

	//endregion


	//region VacationPlannerPage - Initialization and Validation Methods Section

	protected void validatePageInitialState()
	{
		logger.debug( "validating static elements for: <{}>, name:<{}>...", getQualifier(), getLogicalName() );
	}

	//endregion


	//region VacationPlannerPage - Service Methods Section



	//endregion


	//region VacationPlannerPage - Business Methods Section

	//endregion


	//region VacationPlannerPage - Element Finder Methods Section

	//endregion

}
