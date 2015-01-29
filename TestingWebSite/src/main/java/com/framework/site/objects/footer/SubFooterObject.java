package com.framework.site.objects.footer;

import com.framework.asserts.JAssertion;
import com.framework.driver.objects.AbstractWebObject;
import com.framework.driver.utils.ui.WaitUtil;
import com.framework.site.objects.footer.interfaces.Footer;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
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
		super( driver, rootElement, Footer.SubFooter.LOGICAL_NAME );
	}

	//endregion


	//region SubFooterObject - Initialization and Validation Methods Section

	@Override
	protected void initWebObject()
	{
		logger.debug( "validating static elements for web object id: <{}>, name:<{}>...",
				getQualifier(), getLogicalName() );

		JAssertion assertion = new JAssertion( getWrappedDriver() );
		ExpectedCondition<WebElement> condition1 = WaitUtil.presenceOfChildBy( getRoot(), By.cssSelector( "ul.minor" ) );
		assertion.assertWaitThat(
				"Validate all \"ul.minor.pull-left\" elements are displayed", 5000, condition1 );
		ExpectedCondition<WebElement> condition2 = WaitUtil.presenceOfChildBy( getRoot(), By.cssSelector( "ul.social" ) );
		assertion.assertWaitThat(
				"Validate all \"ul.minor.pull-left\" elements are displayed", 5000, condition2 );
	}

	//endregion


	//region SubFooterObject - Service Methods Section

	private WebElement getRoot()
	{
		return getBaseRootElement( Footer.SubFooter.ROOT_BY );
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