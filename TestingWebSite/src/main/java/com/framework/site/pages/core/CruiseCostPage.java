package com.framework.site.pages.core;

import com.framework.site.pages.BaseCarnivalPage;
import com.framework.testing.annotations.DefaultUrl;
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

@DefaultUrl( matcher = "endsWith()", value = "/how-much-does-a-cruise-cost.aspx" )
public class CruiseCostPage extends BaseCarnivalPage
{

	//region CruiseCostPage - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( CruiseCostPage.class );

	private static final String LOGICAL_NAME = "Cruise Cost Page";

	// ------------------------------------------------------------------------|
	// --- WEB-OBJECTS DEFINITIONS --------------------------------------------|
	// ------------------------------------------------------------------------|


	//endregion


	//region CruiseCostPage - Constructor Methods Section

	public CruiseCostPage()
	{
		super( LOGICAL_NAME );
		validatePageInitialState();
	}

	//endregion


	//region CruiseCostPage - Initialization and Validation Methods Section

	@Override
	protected void validatePageInitialState()
	{
		logger.debug( "validating static elements for: <{}>, name:<{}>...", getQualifier(), getLogicalName() );
	}


	//endregion


	//region CruiseCostPage - Service Methods Section

	//endregion


	//region CruiseCostPage - Business Methods Section

	//endregion


	//region CruiseCostPage - Element Finder Methods Section

	//endregion

}
