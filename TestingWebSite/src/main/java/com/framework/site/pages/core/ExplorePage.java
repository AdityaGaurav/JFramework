package com.framework.site.pages.core;

import com.framework.site.pages.BaseCarnivalPage;
import com.framework.testing.annotations.DefaultUrl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@DefaultUrl ( value = "/explore-carnival-cruises", matcher = "endsWith()" )
public class ExplorePage extends BaseCarnivalPage
{

	//region ExplorePage - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( ExplorePage.class );

	private static final String LOGICAL_NAME = "Explore Page";

	// ------------------------------------------------------------------------|
	// --- WEB-OBJECTS DEFINITIONS --------------------------------------------|
	// ------------------------------------------------------------------------|


	//endregion


	//region ExplorePage - Constructor Methods Section

	public ExplorePage()
	{
		super( LOGICAL_NAME );
		validatePageInitialState();
	}

	//endregion


	//region ExplorePage - Initialization and Validation Methods Section

	protected void validatePageInitialState()
	{
		logger.debug( "validating static elements for: <{}>, name:<{}>...", getQualifier(), getLogicalName() );
	}

	//endregion


	//region ExplorePage - Protected Methods Section

	//endregion


	//region ExplorePage - Private Function Section

	//endregion


	//region ExplorePage - Inner Classes Implementation Section

	//endregion

}
