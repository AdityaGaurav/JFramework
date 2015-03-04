package com.framework.site.pages.loyalty;


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

@DefaultUrl( value = "/loyalty/overview.aspx", matcher = "contains()" )
public class VifpClubPage extends BaseCarnivalPage
{

	//region VifpClubPage - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( VifpClubPage.class );

	private static final String LOGICAL_NAME = "VIFP Club Page";

	// ------------------------------------------------------------------------|
	// --- WEB-OBJECTS DEFINITIONS --------------------------------------------|
	// ------------------------------------------------------------------------|

	//endregion


	//region VifpClubPage - Constructor Methods Section

	public VifpClubPage()
	{
		super( LOGICAL_NAME );
		validatePageInitialState();
	}

	//endregion


	//region VifpClubPage - Initialization and Validation Methods Section

	protected void validatePageInitialState()
	{
		logger.debug( "validating static elements for: <{}>, name:<{}>...", getQualifier(), getLogicalName() );
	}

	//endregion


	//region VifpClubPage - Service Methods Section



	//endregion


	//region VifpClubPage - Business Methods Section

	//endregion


	//region VifpClubPage - Element Finder Methods Section

	//endregion

}
