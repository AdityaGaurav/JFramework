package com.framework.site.pages.core;

import com.framework.site.objects.body.common.NavStickEmbeddedObject;
import com.framework.site.objects.body.interfaces.NavStickEmbedded;
import com.framework.site.pages.BaseCarnivalPage;
import com.framework.testing.annotations.DefaultUrl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@DefaultUrl( value = "/uk-caribbean.aspx", matcher = "endsWith()")
public class UKCaribbeanPage extends BaseCarnivalPage
{

	//region UKCaribbeanPage - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( UKCaribbeanPage.class );

	private static final String LOGICAL_NAME = "UK Caribbean Page";

	// ------------------------------------------------------------------------|
	// --- WEB-OBJECTS DEFINITIONS --------------------------------------------|
	// ------------------------------------------------------------------------|

	private NavStickEmbedded navStickEmbedded;

	//endregion


	//region UKCaribbeanPage - Constructor Methods Section

	public UKCaribbeanPage()
	{
		super( LOGICAL_NAME );
		validatePageInitialState();
	}

	//endregion


	//region UKCaribbeanPage - Initialization and Validation Methods Section


	protected void validatePageInitialState()
	{
		logger.debug( "validating static elements for: <{}>, name:<{}>...", getQualifier(), getLogicalName() );
	}

	//endregion


	//region UKCaribbeanPage - Service Methods Section

	public NavStickEmbedded navStickEmbedded()
	{
		if ( null == this.navStickEmbedded )
		{
			this.navStickEmbedded = new NavStickEmbeddedObject( findBreadcrumbBarDiv() );
		}
		return navStickEmbedded;
	}

	//endregion


	//region UKCaribbeanPage - Business Methods Section

	//endregion


	//region UKCaribbeanPage - Element Finder Methods Section

	//endregion

}
