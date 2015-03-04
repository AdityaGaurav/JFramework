package com.framework.site.pages.core.cruiseto;

import com.framework.site.pages.BaseCarnivalPage;
import com.framework.testing.annotations.DefaultUrl;
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

	//endregion


	//region CruiseToPage - Service Methods Section

	//endregion


	//region CruiseToPage - Business Methods Section

	//endregion


	//region CruiseToPage - Element Finder Methods Section

	//endregion

}
