package com.framework.site.objects.header;

import com.framework.asserts.JAssertion;
import com.framework.driver.objects.AbstractWebObject;
import com.framework.driver.utils.ui.WaitUtil;
import com.framework.site.objects.header.interfaces.Header;
import com.framework.utils.datetime.TimeConstants;
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
 * Name   : HeaderSubscribeObject
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-01-06
 *
 * Time   : 10:08
 */

class HeaderSubscribeObject extends AbstractWebObject implements Header.HeaderSubscribe
{

	//region HeaderSubscribeObject - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( HeaderSubscribeObject.class );

	//endregion


	//region HeaderSubscribeObject - Constructor Methods Section

	HeaderSubscribeObject( WebDriver driver, final WebElement rootElement )
	{
		super( driver, rootElement, LOGICAL_NAME );
	}

	//endregion


	//region HeaderSubscribeObject - Initialization and Validation Methods Section

	@Override
	protected void initWebObject()
	{
		logger.debug( "validating static elements for web object id: <{}>, name:<{}>...",
				getQualifier(), getLogicalName() );

		JAssertion assertion = new JAssertion( getWrappedDriver() );
		ExpectedCondition<WebElement> condition = WaitUtil.presenceBy( By.id( "emailOnlyForm" ) );
		assertion.assertWaitThat(
				"Validate \"form#emailOnlyForm\" element exists", TimeConstants.FIFTY_HUNDRED_MILLIS, condition );
	}

	//endregion


	//region HeaderSubscribeObject - Service Methods Section

	private WebElement getRoot()
	{
		return getBaseRootElement( Header.HeaderSubscribe.ROOT_BY );
	}

	//endregion


	//region HeaderSubscribeObject - Business Methods Section

	@Override
	public boolean isDisplayed()
	{
		return getRoot().isDisplayed();
	}

	@Override
	public void subscribe( final String email, final boolean waitAlert )
	{

	}

	//endregion


	//region HeaderSubscribeObject - Element Finder Methods Section

	/**
	 * Full xpath  : {@code /html/body/div[4]/div[1]/div[1]/div[1]/div[4]/div/form}
	 * Short xpath : {@code //*[@id='emailOnlyForm']}
	 * Css selector: {@code form#emailOnlyForm}
	 *
	 * @return The {@linkplain org.openqa.selenium.WebElement}
	 */
	private WebElement getSubscribeForm()
	{
		return getRoot().findElement( By.id( "emailOnlyForm" ) );
	}

	/**
	 * Full xpath  : {@code /html/body/div[4]/div[1]/div[1]/div[1]/div[4]/div/form/input[6]}
	 * Short xpath : {@code //input[@id='email']}
	 * Css selector: {@code form#emailOnlyForm input#email.email.white}
	 *
	 * @return The {@linkplain org.openqa.selenium.WebElement}
	 */
	private WebElement getEmailInput()
	{
		return getSubscribeForm().findElement( By.cssSelector( "input#email.email.white" ) );
	}

	/**
	 * Full xpath  : {@code /html/body/div[4]/div[1]/div[1]/div[1]/div[4]/div/form/a}
	 * Css selector: {@code form#emailOnlyForm a#submit.email-subscribe-submit-btn}
	 *
	 * @return The {@linkplain org.openqa.selenium.WebElement}
	 */
	private WebElement getEmailSubmitAnchor()
	{
		return getSubscribeForm().findElement( By.id( "submit" ) );
	}

	/**
	 * Full xpath  : {@code /html/body/div[4]/div[1]/div[1]/div[1]/div[4]/div/a}
	 * Css selector: {@code div#ccl-refresh-header div.header-nav div.header-subscribe div.max-width a.close}
	 *
	 * @return The {@linkplain org.openqa.selenium.WebElement}
	 */
	private WebElement getCloseAnchor()
	{
		return getRoot().findElement( By.className( "close" ) );
	}

	//endregion

}
