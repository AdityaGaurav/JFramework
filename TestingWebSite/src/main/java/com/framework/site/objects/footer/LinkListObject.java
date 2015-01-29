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
 * Name   : LinkListObject
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-01-09
 *
 * Time   : 19:07
 */

class LinkListObject extends AbstractWebObject implements SectionFooterObject.LinkList
{

	//region LinkListObject - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( LinkListObject.class );

	// ------------------------------------------------------------------------|
	// --- WEB-OBJECTS DEFINITIONS --------------------------------------------|
	// ------------------------------------------------------------------------|

	//endregion


	//region LinkListObject - Constructor Methods Section

	LinkListObject( WebDriver driver, final WebElement rootElement )
	{
		super( driver, rootElement, Footer.LinkList.LOGICAL_NAME );
	}

	//endregion


	//region LinkListObject - Initialization and Validation Methods Section

	@Override
	protected void initWebObject()
	{
		logger.debug( "validating static elements for web object id: <{}>, name:<{}>...",
				getQualifier(), getLogicalName() );

		JAssertion assertion = new JAssertion( getWrappedDriver() );
		ExpectedCondition<List<WebElement>> condition = WaitUtil.visibilityOfAllBy( By.cssSelector( ".link-lists li" ), true );
		assertion.assertWaitThat(
				"Validate all \".link-lists li\" elements are displayed", TimeConstants.FIFTY_HUNDRED_MILLIS, condition );
	}

	//endregion


	//region LinkListObject - Service Methods Section

	private WebElement getRoot()
	{
		return getBaseRootElement( Footer.LinkList.ROOT_BY );
	}

	//endregion


	//region LinkListObject - Business Methods Section

	@Override
	public boolean isDisplayed()
	{
		return getRoot().isDisplayed();
	}

	//endregion


	//region LinkListObject - Element Finder Methods Section

	//endregion

}
