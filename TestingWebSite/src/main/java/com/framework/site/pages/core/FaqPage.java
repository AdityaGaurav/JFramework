package com.framework.site.pages.core;

import com.framework.site.pages.BaseCarnivalPage;
import com.framework.testing.annotations.DefaultUrl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@DefaultUrl( value = "/core/faq.aspx", matcher = "endsWith()" )
public class FaqPage extends BaseCarnivalPage
{

	//region FaqPage - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( FaqPage.class );

	private static final String LOGICAL_NAME = "Faq Page";

	// ------------------------------------------------------------------------|
	// --- WEB-OBJECTS DEFINITIONS --------------------------------------------|
	// ------------------------------------------------------------------------|

	//endregion


	//region FaqPage - Constructor Methods Section

	public FaqPage()
	{
		super( LOGICAL_NAME );
		validatePageInitialState();
	}

	//endregion


	//region FaqPage - Initialization and Validation Methods Section


	protected void validatePageInitialState()
	{
		logger.debug( "validating static elements for: <{}>, name:<{}>...", getQualifier(), getLogicalName() );
	}

	//endregion


	//region FaqPage - Service Methods Section


	//endregion


	//region FaqPage - Business Methods Section

	//endregion


	//region FaqPage - Element Finder Methods Section

	//endregion

}
