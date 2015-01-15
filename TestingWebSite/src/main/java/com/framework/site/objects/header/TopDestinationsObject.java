package com.framework.site.objects.header;

import com.framework.driver.exceptions.ApplicationException;
import com.framework.driver.utils.ui.WaitUtil;
import com.framework.site.objects.header.interfaces.Header;
import com.framework.site.pages.core.cruiseto.CruiseToPage;
import com.google.common.base.MoreObjects;
import com.google.common.base.Throwables;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.site.objects.header
 *
 * Name   : TopDestinationsObject
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-01-06
 *
 * Time   : 15:43
 */

class TopDestinationsObject extends HeaderBrandingObject implements Header.HeaderBranding.TopDestinations
{

	//region TopDestinationsObject - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( TopDestinationsObject.class );

	//endregion


	//region TopDestinationsObject - Constructor Methods Section

	TopDestinationsObject( WebDriver driver, final WebElement rootElement )
	{
		super( TopDestinations.LOGICAL_NAME, driver, rootElement );
	}

	//endregion


	//region TopDestinationsObject - Initialization and Validation Methods Section

	@Override
	protected void initWebObject()
	{
		logger.debug( "validating static elements for web object id: <{}>, name:<{}>...", getId(), getLogicalName() );
		WebDriverWait wait = WaitUtil.wait10( objectDriver );

		try
		{

		}
		catch ( AssertionError ae )
		{
			Throwables.propagateIfInstanceOf( ae, ApplicationException.class );
			logger.error( "throwing a new WebObjectException on {}#initWebObject.", getClass().getSimpleName() );
			ApplicationException ex = new ApplicationException( objectDriver.getWrappedDriver(), ae.getMessage(), ae );
			ex.addInfo( "cause", "verification and initialization process for object " + getLogicalName() + " was failed." );
			throw ex;
		}
	}

	//endregion


	//region TopDestinationsObject - Service Methods Section

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
			rootElement = objectDriver.findElement( TopDestinations.ROOT_BY );
		}

		return rootElement;
	}

	//endregion


	//region TopDestinationsObject - Business Methods Section

	@Override
	public CruiseToPage clickOnTopDestinationLink( final int index )
	{
		return null;
	}

	@Override
	public List<String> getTopDestinationsNames()
	{
		return null;
	}

	@Override
	public boolean isDisplayed()
	{
		return getRoot().isDisplayed();
	}

	@Override
	public void clickTopDestinations()
	{

	}

	//endregion


	//region TopDestinationsObject - Element Finder Methods Section


	//endregion

}