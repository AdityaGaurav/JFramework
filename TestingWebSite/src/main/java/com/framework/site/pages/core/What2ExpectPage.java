package com.framework.site.pages.core;

import com.framework.site.pages.BaseCarnivalPage;
import com.framework.testing.annotations.DefaultUrl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@DefaultUrl( value = "/what-to-expect-on-a-cruise.aspx", matcher = "contains()" )
public class What2ExpectPage extends BaseCarnivalPage
{

	//region What2ExpectPage - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( What2ExpectPage.class );

	private static final String LOGICAL_NAME = "What To Expect on Cruise Page";

	private static final String PAGE_TITLE_KEY = "what.to.expect.title";

	// ------------------------------------------------------------------------|
	// --- WEB-OBJECTS DEFINITIONS --------------------------------------------|
	// ------------------------------------------------------------------------|


	//endregion


	//region What2ExpectPage - Constructor Methods Section

	public What2ExpectPage()
	{
		super( LOGICAL_NAME );
		validatePageInitialState();
	}

	//endregion


	//region What2ExpectPage - Initialization and Validation Methods Section

	protected void validatePageInitialState()
	{
		logger.debug( "validating static elements for: <{}>, name:<{}>...", getQualifier(), getLogicalName() );
	}

	//endregion


	//region What2ExpectPage - Service Methods Section




	//endregion


	//region What2ExpectPage - Business Methods Section

	//endregion


	//region What2ExpectPage - Element Finder Methods Section

	//endregion

}
