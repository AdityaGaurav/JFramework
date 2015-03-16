package com.framework.site.pages.core;

import com.framework.site.pages.BaseCarnivalPage;
import com.framework.testing.annotations.DefaultUrl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@DefaultUrl ( value = "/already-booked", matcher = "endsWith()" )
public class AlreadyBookedPage extends BaseCarnivalPage
{

	//region AlreadyBookedPage - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( AlreadyBookedPage.class );

	private static final String LOGICAL_NAME = "Already Booked Page";

	//endregion


	//region AlreadyBookedPage - Constructor Methods Section

	public AlreadyBookedPage()
	{
		super( LOGICAL_NAME );
		validatePageInitialState();
	}

	//endregion


	//region AlreadyBookedPage - Initialization and Validation Methods Section

	@Override
	protected void validatePageInitialState()
	{
		logger.debug( "validating static elements for: <{}>, name:<{}>...", getQualifier(), getLogicalName() );
	}

	//endregion


	//region AlreadyBookedPage - Service Methods Section

	//endregion


	//region AlreadyBookedPage - Business Methods Section

	//endregion


	//region AlreadyBookedPage - Element Finder Section

	//endregion

}
