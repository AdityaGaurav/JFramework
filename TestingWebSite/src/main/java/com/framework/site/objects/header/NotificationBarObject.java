package com.framework.site.objects.header;

import com.framework.driver.exceptions.ApplicationException;
import com.framework.driver.objects.AbstractWebObject;
import com.framework.site.objects.header.interfaces.Header;
import com.google.common.base.MoreObjects;
import com.google.common.base.Throwables;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.site.objects.header
 *
 * Name   : HeaderObject
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-01-05
 *
 * Time   : 23:11
 */

class NotificationBarObject extends AbstractWebObject implements Header.NotificationBar
{

	//region HeaderObject - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( NotificationBarObject.class );

	// ------------------------------------------------------------------------|
	// --- WEB-OBJECTS DEFINITIONS --------------------------------------------|
	// ------------------------------------------------------------------------|

	//endregion


	//region HeaderObject - Constructor Methods Section

	NotificationBarObject( WebDriver driver, final WebElement rootElement )
	{
		super( LOGICAL_NAME, driver, rootElement );
	}

	//endregion


	//region HeaderObject - Initialization and Validation Methods Section

	@Override
	protected void initWebObject()
	{
		try
		{
			if( getRoot().isDisplayed() )
			{
				logger.debug( "validating static elements for web object id: <{}>, name:<{}>...", getId(), getLogicalName() );
			}
		}
		catch ( AssertionError ae )
		{
			Throwables.propagateIfInstanceOf( ae, ApplicationException.class );
			logger.error( "throwing a new WebObjectException on NotificationBarObject#initWebObject." );
			ApplicationException ex = new ApplicationException( objectDriver.getWrappedDriver(), ae.getMessage() );
			ex.addInfo( "cause", "verification and initialization process for object " + getLogicalName() + " was failed." );
			throw ex;
		}
	}

	//endregion


	//region HeaderObject - Service Methods Section

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
		}
		catch ( StaleElementReferenceException ex )
		{
			logger.warn( "auto recovering from StaleElementReferenceException ..." );
			rootElement = objectDriver.findElement( Header.NotificationBar.ROOT_BY );
		}

		return rootElement;
	}

	//endregion


	//region HeaderObject - Business Methods Section

	@Override
	public boolean isDisplayed()
	{
		return getRoot().isDisplayed();
	}

	//endregion


	//region HeaderObject - Element Finder Methods Section


	//endregion

}
