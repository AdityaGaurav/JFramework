package com.framework.site.pages.funville;

import com.framework.site.pages.BaseCarnivalPage;
import com.framework.testing.annotations.DefaultUrl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



@DefaultUrl( value = "/Funville/forums/", matcher = "endsWith()" )
public class ForumsPage extends BaseCarnivalPage
{

	//region ForumsPage - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( ForumsPage.class );

	private static final String LOGICAL_NAME = "Forums Page";

	// ------------------------------------------------------------------------|
	// --- WEB-OBJECTS DEFINITIONS --------------------------------------------|
	// ------------------------------------------------------------------------|

	//endregion


	//region ForumsPage - Constructor Methods Section

	public ForumsPage()
	{
		super( LOGICAL_NAME );
		validatePageInitialState();
	}

	//endregion


	//region ForumsPage - Initialization and Validation Methods Section

	protected void validatePageInitialState()
	{
		logger.debug( "validating static elements for: <{}>, name:<{}>...", getQualifier(), getLogicalName() );
	}

	//endregion


	//region ForumsPage - Service Methods Section


	//endregion


	//region ForumsPage - Business Methods Section

	//endregion


	//region ForumsPage - Element Finder Methods Section

	//endregion

}
