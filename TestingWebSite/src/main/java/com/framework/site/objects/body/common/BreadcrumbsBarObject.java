package com.framework.site.objects.body.common;

import com.framework.driver.exceptions.ApplicationException;
import com.framework.driver.objects.AbstractWebObject;
import com.framework.site.objects.body.interfaces.BreadcrumbsBar;
import com.framework.site.objects.body.interfaces.ShipSortBar;
import com.google.common.base.MoreObjects;
import com.google.common.base.Throwables;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.site.objects.body.common
 *
 * Name   : BreadcrumbsObject
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-01-12
 *
 * Time   : 11:06
 */

public class BreadcrumbsBarObject extends AbstractWebObject implements BreadcrumbsBar
{

	//region BreadcrumbsBarObject - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( BreadcrumbsBarObject.class );

	// ------------------------------------------------------------------------|
	// --- WEB-OBJECTS DEFINITIONS --------------------------------------------|
	// ------------------------------------------------------------------------|

	private Breadcrumbs breadcrumbs;

	private Share share;

	//endregion


	//region BreadcrumbsBarObject - Constructor Methods Section

	public BreadcrumbsBarObject( WebDriver driver, final WebElement rootElement )
	{
		super( BreadcrumbsBar.LOGICAL_NAME, driver, rootElement );
	}

	//endregion


	//region BreadcrumbsBarObject - Initialization and Validation Methods Section

	@Override
	protected void initWebObject()
	{
		logger.debug( "validating static elements for web object id: <{}>, name:<{}>...", getId(), getLogicalName() );
		logger.info( "Parsing ship card..." );

		try
		{

		}
		catch ( AssertionError ae )
		{
			Throwables.propagateIfInstanceOf( ae, ApplicationException.class );
			logger.error( "throwing a new WebObjectException on {}#initWebObject.", getClass().getSimpleName() );
			ApplicationException appEx = new ApplicationException( objectDriver.getWrappedDriver(), ae.getMessage(),ae );
			appEx.addInfo( "cause", "verification and initialization process for object " + getLogicalName() + " was failed." );
			throw appEx;
		}
	}

	//endregion


	//region BreadcrumbsBarObject - Service Methods Section

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper( this )
				.add( "logical name", getLogicalName() )
				.add( "id", getId() )
				.omitNullValues()
				.toString();
	}

	private WebElement getRoot()
	{
		try
		{
			rootElement.getTagName();
			return rootElement;
		}
		catch ( StaleElementReferenceException ex )
		{
			logger.warn( "auto recovering from StaleElementReferenceException ..." );
			return objectDriver.findElement( ShipSortBar.ROOT_BY );
		}
	}

	@Override
	public Share share()
	{
		return null;
	}

	@Override
	public Breadcrumbs breadcrumbs()
	{
		if ( null == this.breadcrumbs )
		{
			this.breadcrumbs = new BreadcrumbsObject( objectDriver, findBreadcrumbUl() );
		}
		return breadcrumbs;
	}

	//endregion


	//region BreadcrumbsBarObject - Business Methods Section


	//endregion


	//region BreadcrumbsBarObject - Element Finder Methods Section

	private WebElement findHiddenFunActivitiesInput()
	{
		By findBy = By.id( "FunActivitiesBaseUrlFragment" );
		return getRoot().findElement( findBy );
	}

	private WebElement findBreadcrumbUl()
	{
		By findBy = By.cssSelector( "ul.breadcrumbs" );
		return getRoot().findElement( findBy );
	}

	//endregion

}
