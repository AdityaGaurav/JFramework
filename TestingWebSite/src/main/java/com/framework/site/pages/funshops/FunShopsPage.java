package com.framework.site.pages.funshops;

import com.framework.site.pages.BaseCarnivalPage;
import com.framework.testing.annotations.DefaultUrl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.site.pages.core
 *
 * Name   : CruiseToPage
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-01-10
 *
 * Time   : 01:17
 */

@DefaultUrl( value = "/Funshops/", matcher = "contains()" )
public class FunShopsPage extends BaseCarnivalPage
{

	//region FunShopsPage - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( FunShopsPage.class );

	private static final String LOGICAL_NAME = "Fun Shop Page";

	// ------------------------------------------------------------------------|
	// --- WEB-OBJECTS DEFINITIONS --------------------------------------------|
	// ------------------------------------------------------------------------|

	//endregion


	//region FunShopsPage - Constructor Methods Section

	public FunShopsPage()
	{
		super( LOGICAL_NAME );
		validatePageInitialState();
	}

	//endregion


	//region FunShopsPage - Initialization and Validation Methods Section

	protected void validatePageInitialState()
	{
		logger.debug( "validating static elements for: <{}>, name:<{}>...", getQualifier(), getLogicalName() );
	}

	//endregion


	//region FunShopsPage - Service Methods Section



	//endregion


	//region FunShopsPage - Business Methods Section

	//endregion


	//region FunShopsPage - Element Finder Methods Section

	//endregion

}
