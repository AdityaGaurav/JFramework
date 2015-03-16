package com.framework.site.pages.core;

import com.framework.driver.objects.AbstractPageObject;
import com.framework.site.config.SiteSessionManager;
import com.framework.testing.annotations.DefaultUrl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@DefaultUrl ( value = "/OnlineCheckIn/index.asp", matcher = "contains()" )
public class OnlineCheckInPage extends AbstractPageObject
{

	//region OnlineCheckInPage - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( OnlineCheckInPage.class );

	private static final String LOGICAL_NAME = "Online Check-in Page";

	// ------------------------------------------------------------------------|
	// --- WEB-OBJECTS DEFINITIONS --------------------------------------------|
	// ------------------------------------------------------------------------|

	//endregion


	//region OnlineCheckInPage - Constructor Methods Section

	public OnlineCheckInPage()
	{
		super( SiteSessionManager.get().getDriver(), LOGICAL_NAME );
		validatePageInitialState();
	}

	//endregion


	//region OnlineCheckInPage - Initialization and Validation Methods Section

	protected void validatePageInitialState()
	{
		logger.debug( "validating static elements for: <{}>, name:<{}>...", getQualifier(), getLogicalName() );
	}

	//endregion


	//region OnlineCheckInPage - Service Methods Section

	//endregion


	//region OnlineCheckInPage - Business Function Section

	//endregion


	//region OnlineCheckInPage - Element Finder Methods Section

	//endregion

}
