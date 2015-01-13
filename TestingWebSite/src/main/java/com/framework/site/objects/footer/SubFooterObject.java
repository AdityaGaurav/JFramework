package com.framework.site.objects.footer;

import com.framework.driver.exceptions.ApplicationException;
import com.framework.driver.objects.AbstractWebObject;
import com.framework.site.objects.footer.interfaces.Footer;
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
 * Package: com.framework.site.objects.footer
 *
 * Name   : SubFooterObject
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-01-06
 *
 * Time   : 16:55
 */

class SubFooterObject extends AbstractWebObject implements Footer.SubFooter
{

	//region SubFooterObject - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( SubFooterObject.class );

	//endregion


	//region SubFooterObject - Constructor Methods Section

	SubFooterObject( WebDriver driver, final WebElement rootElement )
	{
		super( LOGICAL_NAME, driver, rootElement );
	}

	//endregion


	//region SubFooterObject - Initialization and Validation Methods Section

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
			logger.error( "throwing a new WebObjectException on SubFooterObject#initWebObject." );
			ApplicationException ex = new ApplicationException( objectDriver.getWrappedDriver(), ae.getMessage(), ae );
			ex.addInfo( "cause", "verification and initialization process for object " + getLogicalName() + " was failed." );
			throw ex;
		}
	}

	//endregion


	//region SubFooterObject - Service Methods Section

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
			rootElement = objectDriver.findElement( Footer.SubFooter.ROOT_BY );
		}

		return rootElement;
	}

	//endregion


	//region SubFooterObject - Business Methods Section

	@Override
	public boolean isDisplayed()
	{
		return getRoot().isDisplayed();
	}

	//endregion


	//region SubFooterObject - Element Finder Methods Section

	//endregion

}