package com.framework.site.pages.core;

import com.framework.driver.event.HtmlElement;
import com.framework.site.objects.body.common.NavStickEmbeddedObject;
import com.framework.site.objects.body.common.SectionBreadcrumbsBarObject;
import com.framework.site.pages.BaseCarnivalPage;
import com.framework.testing.annotations.DefaultUrl;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@DefaultUrl( value = "/content/rookie/rookie.aspx", matcher = "contains()" )
public class BeginnersGuidePage extends BaseCarnivalPage
{

	//region BeginnersGuidePage - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( BeginnersGuidePage.class );

	private static final String LOGICAL_NAME = "Beginner's Guide";

	public static final String ON_SHIP_REF= "on-ship";

	public static final String SHOREX_REF = "shore-excursions";

	public static final String DESTINATIONS_REF  = "destinations";

	public static final String INCLUDED_REF ="whats-included";

	// ------------------------------------------------------------------------|
	// --- WEB-OBJECTS DEFINITIONS --------------------------------------------|
	// ------------------------------------------------------------------------|

	private SectionBreadcrumbsBarObject breadcrumbsBar;

	private NavStickEmbeddedObject navStickEmbedded;

	//endregion


	//region BeginnersGuidePage - Constructor Methods Section

	public BeginnersGuidePage()
	{
		super( LOGICAL_NAME );
		validatePageInitialState();
	}

	//endregion


	//region BeginnersGuidePage - Initialization and Validation Methods Section

	@Override
	protected void validatePageInitialState()
	{
		logger.debug( "validating static elements for: <{}>, name:<{}>...", getQualifier(), getLogicalName() );
	}

	//endregion


	//region BeginnersGuidePage - Service Methods Section

	public SectionBreadcrumbsBarObject breadcrumbsBar()
	{
		if ( null == this.breadcrumbsBar )
		{
			this.breadcrumbsBar = new SectionBreadcrumbsBarObject( findBreadcrumbBarDiv() );
		}
		return breadcrumbsBar;
	}

	public NavStickEmbeddedObject navStickEmbedded()
	{
		if ( null == this.navStickEmbedded )
		{
			this.navStickEmbedded = new NavStickEmbeddedObject( findNavStickEmUl() );
		}
		return navStickEmbedded;
	}

	//endregion


	//region BeginnersGuidePage - Business Methods Section

	private HtmlElement findNavStickEmUl()
	{
	 	return getDriver().findElement( By.cssSelector( "ul.nav.stickem" ) );
	}

	//endregion


	//region BeginnersGuidePage - Element Finder Methods Section

	//endregion

}
