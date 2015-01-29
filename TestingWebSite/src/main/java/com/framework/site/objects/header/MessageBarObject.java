package com.framework.site.objects.header;

import com.framework.asserts.JAssertion;
import com.framework.driver.objects.AbstractWebObject;
import com.framework.driver.utils.ui.WaitUtil;
import com.framework.site.objects.header.interfaces.Header;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
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

class MessageBarObject extends AbstractWebObject implements Header.MessageBar
{

	//region HeaderObject - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( MessageBarObject.class );

	// ------------------------------------------------------------------------|
	// --- WEB-OBJECTS DEFINITIONS --------------------------------------------|
	// ------------------------------------------------------------------------|

	//endregion


	//region HeaderObject - Constructor Methods Section

	MessageBarObject( WebDriver driver,  final WebElement rootElement )
	{
		super( driver, rootElement, LOGICAL_NAME );
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

			JAssertion assertion = new JAssertion( getWrappedDriver() );
			ExpectedCondition<WebElement> condition =
					WaitUtil.presenceBy( By.cssSelector( "div.message-bar > div.cookiemandate" ) );
			assertion.assertWaitThat(
					"Validate \"div.cookiemandate\" element exists", 5000, condition );
		}
	}

	//endregion


	//region HeaderObject - Service Methods Section

	private WebElement getRoot()
	{
		return getBaseRootElement( Header.MessageBar.ROOT_BY );
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
