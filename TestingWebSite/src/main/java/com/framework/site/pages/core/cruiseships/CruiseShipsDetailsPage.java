package com.framework.site.pages.core.cruiseships;

import com.framework.driver.event.ExpectedConditions;
import com.framework.site.data.Ships;
import com.framework.site.objects.body.common.SectionBreadcrumbsBarObject;
import com.framework.site.objects.body.interfaces.BreadcrumbsBar;
import com.framework.site.pages.BaseCarnivalPage;
import com.framework.utils.datetime.TimeConstants;
import com.framework.utils.matchers.JMatchers;
import org.hamcrest.Matcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class CruiseShipsDetailsPage extends BaseCarnivalPage
{

	//region CruiseShipsDetailsPage - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( CruiseShipsDetailsPage.class );

	private static final String LOGICAL_NAME = "Cruise Ships Details Page";

	private static final String URL_PATTERN = "/cruise-ships/carnival-%s.aspx";

	private static Ships ship;

	// ------------------------------------------------------------------------|
	// --- WEB-OBJECTS DEFINITIONS --------------------------------------------|
	// ------------------------------------------------------------------------|

	private BreadcrumbsBar breadcrumbsBar;

	//endregion


	//region CruiseShipsDetailsPage - Constructor Methods Section

	public CruiseShipsDetailsPage()
	{
		super( LOGICAL_NAME );
		validatePageInitialState();
	}

	//endregion


	//region CruiseShipsDetailsPage - Initialization and Validation Methods Section

	protected void validatePageInitialState()
	{
		logger.debug( "validating static elements for: <{}>, name:<{}>...", getQualifier(), getLogicalName() );

		/* Validating breadcrumb last value pointing to ship name */

		String lastChild = breadcrumbsBar().breadcrumbs().getLastChildName();
		getDriver().assertThat( "Validate breadcrumb last position", lastChild, JMatchers.is( ship.getFullName() ) );


	}

	/**
	 * Overrides the generic extended class, since the page url is create dynamically.
	 */
	@Override
	protected void validatePageUrl()
	{
		String url = String.format( URL_PATTERN, ship.getShipName().toLowerCase() );
		Matcher<String> matcher = JMatchers.endsWith( url );
		getDriver().assertWaitThat( "Validate page url", TimeConstants.ONE_MINUTE, ExpectedConditions.urlMatches( matcher ) );

	}

	//endregion


	//region CruiseShipsDetailsPage - Service Methods Section

	public static void forShip( final Ships ship )
	{
		CruiseShipsDetailsPage.ship = ship;
	}

	public BreadcrumbsBar breadcrumbsBar()
	{
		if ( null == this.breadcrumbsBar )
		{
			this.breadcrumbsBar = new SectionBreadcrumbsBarObject( findBreadcrumbBarDiv() );
		}
		return breadcrumbsBar;
	}


	//endregion


	//region CruiseShipsDetailsPage - Business Methods Section

	//endregion


	//region CruiseShipsDetailsPage - Element Finder Methods Section


	//endregion

}
