package com.framework.site.objects.header;

import com.framework.driver.objects.AbstractWebObject;
import com.framework.site.objects.header.interfaces.Header;
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
		super( driver, rootElement, Header.NotificationBar.LOGICAL_NAME );
	}

	//endregion


	//region HeaderObject - Initialization and Validation Methods Section

	@Override
	protected void initWebObject()
	{
		if( getRoot().isDisplayed() )
		{
			logger.debug( "validating static elements for web object id: <{}>, name:<{}>...",
					getQualifier(), getLogicalName() );
			//todo: find out what notif-bar contains
		}
	}

	//endregion


	//region HeaderObject - Service Methods Section

	private WebElement getRoot()
	{
		return getBaseRootElement( Header.NotificationBar.ROOT_BY );
	}

	//endregion


	//region HeaderObject - Business Methods Section

	@Override
	public boolean isDisplayed()
	{
		return getRoot().isDisplayed();
	}

	//endregion

}
