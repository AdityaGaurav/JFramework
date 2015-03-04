package com.framework.site.pages.activities;

import com.framework.site.pages.BaseCarnivalPage;
import com.framework.testing.annotations.DefaultUrl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@DefaultUrl( value = "/Activities", matcher = "endsWith()" )
public class ActivitiesPage extends BaseCarnivalPage
{

	//region ActivitiesPage - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( ActivitiesPage.class );

	private static final String LOGICAL_NAME = "Shore Excursions Page";

	// ------------------------------------------------------------------------|
	// --- WEB-OBJECTS DEFINITIONS --------------------------------------------|
	// ------------------------------------------------------------------------|

	//endregion


	//region ActivitiesPage - Constructor Methods Section

	public ActivitiesPage()
	{
		super( LOGICAL_NAME );
	}

	//endregion


	//region ActivitiesPage - Initialization and Validation Methods Section


	protected void validatePageInitialState()
	{
		logger.debug( "validating static elements for: <{}>, name:<{}>...", getQualifier(), getLogicalName() );
	}

	//endregion


	//region ActivitiesPage - Service Methods Section



	//endregion


	//region ActivitiesPage - Business Methods Section

	//endregion


	//region ActivitiesPage - Element Finder Methods Section

	//endregion

}
