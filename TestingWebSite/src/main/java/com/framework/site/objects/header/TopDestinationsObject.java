package com.framework.site.objects.header;

import com.framework.asserts.JAssertion;
import com.framework.driver.utils.ui.WaitUtil;
import com.framework.site.objects.header.interfaces.Header;
import com.framework.site.pages.core.cruiseto.CruiseToPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
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
		super( driver, rootElement, TopDestinations.LOGICAL_NAME );
	}

	//endregion


	//region TopDestinationsObject - Initialization and Validation Methods Section

	@Override
	protected void initWebObject()
	{
		logger.debug( "validating static elements for web object id: <{}>, name:<{}>...",
				getQualifier(), getLogicalName() );

		JAssertion assertion = new JAssertion( getWrappedDriver() );
		ExpectedCondition<WebElement> condition =
				WaitUtil.presenceBy( By.cssSelector( "..nav-tooltip li.title" ) );
		assertion.assertWaitThat(
				"Validate \".header-links ul.pull-left\" element exists", 5000, condition );
	}

	//endregion


	//region TopDestinationsObject - Service Methods Section

	private WebElement getRoot()
	{
		return getBaseRootElement( TopDestinations.ROOT_BY );
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