package com.framework.site.pages.core;

import com.framework.site.config.SiteProperty;
import com.framework.site.objects.body.common.NavStickEmbeddedObject;
import com.framework.site.objects.body.common.SectionBreadcrumbsBarObject;
import com.framework.site.objects.body.interfaces.BreadcrumbsBar;
import com.framework.site.objects.body.interfaces.NavStickEmbedded;
import com.framework.site.pages.BaseCarnivalPage;
import com.framework.testing.annotations.DefaultUrl;
import com.framework.utils.matchers.JMatchers;
import org.hamcrest.Matcher;
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

	private BreadcrumbsBar breadcrumbsBar;

	private NavStickEmbedded navStickEmbedded;

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

	@Override
	protected void validatePageTitle()
	{
		String title = ( String ) SiteProperty.BEGINNERS_GUIDE_TITLE.fromContext();
		final Matcher<String> EXPECTED_TITLE = JMatchers.equalToIgnoringCase( title );
		final String REASON = String.format( "Asserting \"%s\" page's title", LOGICAL_NAME );

		getDriver().assertThat( REASON, getTitle(), EXPECTED_TITLE );
	}

	//endregion


	//region BeginnersGuidePage - Service Methods Section

	public BreadcrumbsBar breadcrumbsBar()
	{
		if ( null == this.breadcrumbsBar )
		{
			this.breadcrumbsBar = new SectionBreadcrumbsBarObject( findBreadcrumbBarDiv() );
		}
		return breadcrumbsBar;
	}

	public NavStickEmbedded navStickEmbedded()
	{
		if ( null == this.navStickEmbedded )
		{
			this.navStickEmbedded = new NavStickEmbeddedObject( findBreadcrumbBarDiv() );
		}
		return navStickEmbedded;
	}

	//endregion


	//region BeginnersGuidePage - Business Methods Section

	//endregion


	//region BeginnersGuidePage - Element Finder Methods Section

	//endregion

}
