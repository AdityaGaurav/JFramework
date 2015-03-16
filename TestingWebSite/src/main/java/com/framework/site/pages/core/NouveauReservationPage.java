package com.framework.site.pages.core;

import com.framework.site.pages.BaseCarnivalPage;
import com.framework.testing.annotations.DefaultUrl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@DefaultUrl ( value = "/request-forms/nouveau-reservation.aspx", matcher = "endsWith()" )
public class NouveauReservationPage extends BaseCarnivalPage
{

	//region NouveauReservationPage - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( NouveauReservationPage.class );

	private static final String LOGICAL_NAME = "Nouveau Reservation Page";

	//endregion


	//region NouveauReservationPage - Constructor Methods Section

	public NouveauReservationPage()
	{
		super( LOGICAL_NAME );
		validatePageInitialState();
	}

	//endregion


	//region NouveauReservationPage - Initialization and Validation Methods Section

	@Override
	protected void validatePageInitialState()
	{
		logger.debug( "validating static elements for: <{}>, name:<{}>...", getQualifier(), getLogicalName() );
	}

	//endregion


	//region NouveauReservationPage - Service Methods Section

	//endregion


	//region NouveauReservationPage - Business Function Section

	//endregion


	//region NouveauReservationPage - Element Finder Section

	//endregion

}
