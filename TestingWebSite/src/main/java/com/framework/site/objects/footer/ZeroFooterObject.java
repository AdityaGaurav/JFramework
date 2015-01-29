package com.framework.site.objects.footer;

import com.framework.asserts.JAssertion;
import com.framework.driver.objects.AbstractWebObject;
import com.framework.driver.utils.ui.WaitUtil;
import com.framework.site.objects.footer.interfaces.Footer;
import com.framework.utils.datetime.TimeConstants;
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
 * Package: com.framework.site.objects.footer
 *
 * Name   : ZeroFooterObject
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-01-06
 *
 * Time   : 16:56
 */

class ZeroFooterObject extends AbstractWebObject implements Footer.ZeroFooter
{

	//region ZeroFooterObject - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( ZeroFooterObject.class );

	//endregion


	//region ZeroFooterObject - Constructor Methods Section

	ZeroFooterObject( WebDriver driver, final WebElement rootElement )
	{
		super( driver, rootElement, Footer.ZeroFooter.LOGICAL_NAME );
	}

	//endregion


	//region ZeroFooterObject - Initialization and Validation Methods Section

	@Override
	protected void initWebObject()
	{
		logger.debug( "validating static elements for web object id: <{}>, name:<{}>...",
				getQualifier(), getLogicalName() );

		JAssertion assertion = new JAssertion( getWrappedDriver() );
		ExpectedCondition<List<WebElement>> condition = WaitUtil.visibilityOfAllBy( By.tagName( "li" ), true );
		assertion.assertWaitThat(
				"Validate all \"li\" elements are displayed", TimeConstants.FIFTY_HUNDRED_MILLIS, condition );
	}

	//endregion


	//region ZeroFooterObject - Service Methods Section

	private WebElement getRoot()
	{
		return getBaseRootElement( Footer.ZeroFooter.ROOT_BY );
	}

	//endregion


	//region ZeroFooterObject - Business Methods Section

	@Override
	public boolean isDisplayed()
	{
		return getRoot().isDisplayed();
	}

	//endregion


	//region ZeroFooterObject - Element Finder Methods Section

	//endregion

}