package com.framework.site.pages.core;

import com.framework.driver.event.HtmlElement;
import com.framework.site.objects.body.common.NavStickEmbeddedObject;
import com.framework.site.pages.BaseCarnivalPage;
import com.framework.testing.annotations.DefaultUrl;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@DefaultUrl( value = "/uk-caribbean.aspx", matcher = "endsWith()")
public class UKCaribbeanPage extends BaseCarnivalPage
{

	//region UKCaribbeanPage - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( UKCaribbeanPage.class );

	private static final String LOGICAL_NAME = "UK Caribbean Page";

	public static final String SAIL_FROM = "sail-from";

	public static final String EASTERN_CARIBBEAN = "eastern-caribbean";

	public static final String WESTERN_CARIBBEAN  = "western-caribbean";

	public static final String SOUTHERN_CARIBBEAN ="southern-caribbean";

	// ------------------------------------------------------------------------|
	// --- WEB-OBJECTS DEFINITIONS --------------------------------------------|
	// ------------------------------------------------------------------------|

	private NavStickEmbeddedObject navStickEmbedded;

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

	public NavStickEmbeddedObject navStickEmbedded()
	{
		if ( null == this.navStickEmbedded )
		{
			this.navStickEmbedded = new NavStickEmbeddedObject( findNavStickEmUl() );
		}
		return navStickEmbedded;
	}

	//endregion


	//region UKCaribbeanPage - Business Methods Section

	private HtmlElement findNavStickEmUl()
	{
		return getDriver().findElement( By.cssSelector( "ul.nav.stickem" ) );
	}


	//endregion


	//region UKCaribbeanPage - Element Finder Methods Section

	//endregion

}
