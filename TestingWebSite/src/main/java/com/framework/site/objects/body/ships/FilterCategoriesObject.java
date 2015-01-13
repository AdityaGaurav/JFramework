package com.framework.site.objects.body.ships;

import com.framework.driver.exceptions.ApplicationException;
import com.framework.driver.objects.AbstractWebObject;
import com.framework.site.objects.body.interfaces.FilterCategories;
import com.framework.site.objects.body.interfaces.ShipSortBar;
import com.google.common.base.MoreObjects;
import com.google.common.base.Throwables;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


//todo: class documentation

public class FilterCategoriesObject extends AbstractWebObject implements FilterCategories
{

	//region FilterCategoriesObject - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( FilterCategoriesObject.class );

	//endregion


	//region FilterCategoriesObject - Constructor Methods Section

	public FilterCategoriesObject( WebDriver driver, final WebElement rootElement )
	{
		super( FilterCategories.LOGICAL_NAME, driver, rootElement );
	}

	//endregion


	//region FilterCategoriesObject - Initialization and Validation Methods Section

	@Override
	protected void initWebObject()
	{
		logger.debug( "validating static elements for web object id: <{}>, name:<{}>...", getId(), getLogicalName() );

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


	//region FilterCategoriesObject - Service Methods Section

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


	//region FilterCategoriesObject - Business Methods Section

	@Override
	public void doToggle()
	{

	}


	//endregion


	//region FilterCategoriesObject - Element Finder Methods Section

	//endregion

}
