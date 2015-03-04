package com.framework.site.pages.core;

import com.framework.site.objects.body.common.SectionBreadcrumbsBarObject;
import com.framework.site.objects.body.interfaces.BreadcrumbsBar;
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

@DefaultUrl( matcher = "endsWith()", value = "/staterooms.aspx" )
public class StateRoomsPage extends BaseCarnivalPage
{

	//region StateRoomsPage - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( StateRoomsPage.class );

	private static final String LOGICAL_NAME = "State Rooms Page";

	// ------------------------------------------------------------------------|
	// --- WEB-OBJECTS DEFINITIONS --------------------------------------------|
	// ------------------------------------------------------------------------|

	private BreadcrumbsBar breadcrumbsBar;

	//endregion


	//region StateRoomsPage - Constructor Methods Section

	public StateRoomsPage()
	{
		super( LOGICAL_NAME );
		validatePageInitialState();
	}

	//endregion


	//region StateRoomsPage - Initialization and Validation Methods Section

	@Override
	protected void validatePageInitialState()
	{
		logger.debug( "validating static elements for: <{}>, name:<{}>...", getQualifier(), getLogicalName() );

	}

	//endregion


	//region StateRoomsPage - Service Methods Section



	public BreadcrumbsBar breadcrumbsBar()
	{
		if ( null == this.breadcrumbsBar )
		{
			this.breadcrumbsBar = new SectionBreadcrumbsBarObject( findBreadcrumbBarDiv() );
		}
		return breadcrumbsBar;
	}

	//endregion


	//region StateRoomsPage - Business Methods Section

	//endregion


	//region StateRoomsPage - Element Finder Methods Section


	//endregion

}
