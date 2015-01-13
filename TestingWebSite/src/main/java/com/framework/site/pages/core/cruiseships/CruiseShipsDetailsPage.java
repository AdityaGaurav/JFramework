package com.framework.site.pages.core.cruiseships;

import com.framework.driver.exceptions.ApplicationException;
import com.framework.driver.objects.AssertingURL;
import com.framework.site.data.Ships;
import com.framework.site.objects.body.common.BreadcrumbsBarObject;
import com.framework.site.objects.body.interfaces.BreadcrumbsBar;
import com.framework.site.pages.BaseCarnivalPage;
import com.google.common.base.MoreObjects;
import com.google.common.base.Throwables;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.site.pages.core
 *
 * Name   : CruiseToPage
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-01-10
 *
 * Time   : 01:17
 */

public class CruiseShipsDetailsPage extends BaseCarnivalPage implements AssertingURL
{

	//region CruiseShipsDetailsPage - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( CruiseShipsDetailsPage.class );

	private static final String LOGICAL_NAME = "Cruise Ships Details Page";

	private static final String URL_PATH = "/cruise-ships/${ship.name.dash.lower.case}.aspx";

	private static final String PAGE_TITLE_KEY = "ship.details.page.title:";

	private final Ships ship;

	// ------------------------------------------------------------------------|
	// --- WEB-OBJECTS DEFINITIONS --------------------------------------------|
	// ------------------------------------------------------------------------|

	private BreadcrumbsBar breadcrumbsBar;

	//endregion


	//region CruiseShipsDetailsPage - Constructor Methods Section

	public CruiseShipsDetailsPage( final WebDriver driver, Ships ship )
	{
		super( LOGICAL_NAME, driver );
		this.ship = ship;
	}

	//endregion


	//region CruiseShipsDetailsPage - Initialization and Validation Methods Section

	@Override
	protected void initElements()
	{
		logger.debug( "validating static elements for: <{}>, name:<{}>...", getId(), getLogicalName() );

		try
		{

		}
		catch ( AssertionError ae )
		{
			Throwables.propagateIfInstanceOf( ae, ApplicationException.class );
			logger.error( "throwing a new WebObjectException on {}#initElements.", getClass().getSimpleName() );
			ApplicationException ex = new ApplicationException( pageDriver.getWrappedDriver(), ae.getMessage(), ae );
			ex.addInfo( "cause", "verification and initialization process for page " + getLogicalName() + " was failed." );
			throw ex;
		}
	}

	@Override
	public void assertURL( final URL url ) throws AssertionError
	{

	}



	//endregion


	//region CruiseShipsDetailsPage - Service Methods Section

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper( this )
				.add( "object id", getId() )
				.add( "page id", pageId() )
				.add( "logical name", getLogicalName() )
				.add( "pageName", pageName() )
				.add( "site region", getSiteRegion() )
				.add( "title", getTitle() )
				.add( "url", getCurrentUrl() )
				.omitNullValues()
				.toString();
	}

	public Ships getShip()
	{
		return ship;
	}

	public BreadcrumbsBar breadcrumbsBar()
	{
		if ( null == this.breadcrumbsBar )
		{
			this.breadcrumbsBar = new BreadcrumbsBarObject( pageDriver, findBreadcrumbBarDiv() );
		}
		return breadcrumbsBar;
	}


	//endregion


	//region CruiseShipsDetailsPage - Business Methods Section

	//endregion


	//region CruiseShipsDetailsPage - Element Finder Methods Section


	//endregion

}
