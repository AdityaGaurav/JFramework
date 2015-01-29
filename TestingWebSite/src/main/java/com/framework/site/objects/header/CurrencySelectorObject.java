package com.framework.site.objects.header;

import com.framework.asserts.JAssertion;
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
 * Name   : TopDestinationsObject
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-01-06
 *
 * Time   : 15:43
 */

class CurrencySelectorObject extends HeaderBrandingObject implements Header.HeaderBranding.CurrencySelector
{

	//region TopDestinationsObject - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( CurrencySelectorObject.class );


	//endregion


	//region TopDestinationsObject - Constructor Methods Section

	CurrencySelectorObject( WebDriver driver, final WebElement rootElement )
	{
		super( driver, rootElement, CurrencySelector.LOGICAL_NAME );
	}

	//endregion


	//region TopDestinationsObject - Initialization and Validation Methods Section

	@Override
	protected void initWebObject()
	{
		logger.debug( "validating static elements for web object id: <{}>, name:<{}>...",
				getQualifier(), getLogicalName() );

		JAssertion assertion = new JAssertion( getWrappedDriver() );
		ExpectedCondition<WebElement> condition1 =
				WaitUtil.presenceOfChildBy( getRoot(), By.id( "currencySelector" ) );
		ExpectedCondition<WebElement> condition2 =
				WaitUtil.presenceOfChildBy( getRoot(), By.id( "ccl_header_currency_options" ) );
		assertion.assertWaitThat( "Validate currency selector exists", 5000, condition1 );
		assertion.assertWaitThat( "Validate currency options exists", 5000, condition2 );
	}

	//endregion


	//region TopDestinationsObject - Service Methods Section

	private String getCurrencySelected()
	{
		final String CURRENCY_SELECTED_SCRIPT = "return utag_data.cp.CurrencySelected;";
		return getWrappedDriver().getJavaScriptSupport().getString( CURRENCY_SELECTED_SCRIPT );
	}

	private WebElement getRoot()
	{
		return getBaseRootElement( CurrencySelector.ROOT_BY );
	}

	//endregion


	//region TopDestinationsObject - Business Methods Section

	@Override
	public boolean isDisplayed()
	{
		return getNavTooltipTriggerAnchor().isDisplayed();
	}

	//endregion


	//region TopDestinationsObject - Element Finder Methods Section

	/**
	 * Full xpath : {@code /html/body/div[5]/div[1]/div[1]/div[1]/div[3]/div/ul/li[4]/select}
	 * Css path   : {@code #currencySelector}
	 *
	 * @return a {@code WebElement} instance reference for the select#currencySelector
	 */
	private WebElement getCurrencySelectorSelect()
	{
		By findBy = By.id( "currencySelector" );
		return getWrappedDriver().findElement( findBy );
	}

	/**
	 * Full xpath : {@code /html/body/div[5]/div[1]/div[1]/div[1]/div[3]/div/ul/li[4]/a}
	 * Css path   : {@code #ccl-refresh-header > div.header-nav > div.header-branding > div > ul > li:nth-child(4) > a}
	 *
	 * @return a {@code WebElement} instance for the a.ccl-blue.nav-tooltip-trigger[data-id='currency']
	 */
	private WebElement getNavTooltipTriggerAnchor()
	{
		By findBy = By.cssSelector( "a.ccl-blue.nav-tooltip-trigger" );
		return getWrappedDriver().findElement( findBy );
	}

	/**
	 * Full xpath : {@code /html/body/div[5]/div[1]/div[1]/div[1]/div[3]/div/ul/li[4]/div}
	 * Css path   : {@code #ccl_header_currency_options}
	 *
	 * @return  a {@code WebElement} instance reference for the div#ccl_header_currency_options
	 */
	private WebElement getCclHeaderCurrencyOptionsDiv()
	{
		By findBy = By.id( "ccl_header_currency_options" );
		return getRoot().findElement( findBy );
	}

	//endregion

}