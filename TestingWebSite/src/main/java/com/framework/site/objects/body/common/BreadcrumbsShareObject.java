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

import java.util.List;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.site.objects.body.common
 *
 * Name   : BreadcrumbsShareObject
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-01-12
 *
 * Time   : 12:17
 */

public class BreadcrumbsShareObject extends AbstractWebObject implements BreadcrumbsBar.Share
{

	//region BreadcrumbsShareObject - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( BreadcrumbsShareObject.class );

	//endregion


	//region BreadcrumbsShareObject - Constructor Methods Section

	public BreadcrumbsShareObject( WebDriver driver, final WebElement rootElement )
	{
		super( BreadcrumbsBar.Share.LOGICAL_NAME, driver, rootElement );
	}

	//endregion


	//region BreadcrumbsShareObject - Initialization and Validation Methods Section

	@Override
	protected void initWebObject()
	{

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


	//region BreadcrumbsShareObject - Service Methods Section

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

	//endregion


	//region BreadcrumbsShareObject - Business Methods Section

	//endregion


	//region BreadcrumbsShareObject - Element Finder Methods Section

	private List<WebElement> getTopChickletsLis()
	{
		By findBy = By.id( "top_chicklets" );
		return getRoot().findElement( findBy ).findElements( By.tagName( "li" ) );
	}

	private WebElement getChicletsSearchDiv()
	{
		By findBy = By.id( "chicklet_search" );
		return getRoot().findElement( findBy );
	}

	private WebElement getAllChickletsDiv()
	{
		By findBy = By.id( "all_chicklets" );
		return getRoot().findElement( findBy );
	}

	//endregion

}
