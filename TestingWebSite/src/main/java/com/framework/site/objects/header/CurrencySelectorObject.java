package com.framework.site.objects.header;

import com.framework.asserts.JAssertion;
import com.framework.driver.event.HtmlElement;
import com.framework.site.objects.header.interfaces.Header;
import com.framework.utils.matchers.JMatchers;
import com.google.common.base.Optional;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.framework.utils.datetime.TimeConstants.FIVE_SECONDS;
import static com.framework.utils.datetime.TimeConstants.THREE_SECONDS;


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

	private HtmlElement ccl_header_currency_options, currency_selector;

	//endregion


	//region TopDestinationsObject - Constructor Methods Section

	CurrencySelectorObject( final HtmlElement rootElement )
	{
		super( rootElement, CurrencySelector.LOGICAL_NAME );
		initWebObject();
	}

	//endregion


	//region TopDestinationsObject - Initialization and Validation Methods Section

	@Override
	protected void initWebObject()
	{
		logger.debug( "validating static elements for web object id: <{}>, name:<{}>...",
				getQualifier(), getLogicalName() );

		final String REASON = "assert that element \"%s\" exits";
		JAssertion assertion = getRoot().createAssertion();

		Optional<HtmlElement> e = getRoot().childExists( By.id( "currencySelector" ), FIVE_SECONDS );
		assertion.assertThat( String.format( REASON, "#currencySelector" ), e.isPresent(), JMatchers.is( true ) );
		this.currency_selector = e.get();

		e = getRoot().childExists( By.id( "ccl_header_currency_options" ), THREE_SECONDS );
		assertion.assertThat( String.format( REASON, "#ccl_header_currency_options" ), e.isPresent(), JMatchers.is( true ) );
		this.ccl_header_currency_options = e.get();
	}

	//endregion


	//region TopDestinationsObject - Service Methods Section

	private String getCurrencySelected()
	{
		final String CURRENCY_SELECTED_SCRIPT = "return utag_data.cp.CurrencySelected;";
		return getDriver().javascript().getString( CURRENCY_SELECTED_SCRIPT );
	}

	private HtmlElement getRoot()
	{
		return getBaseRootElement( CurrencySelector.ROOT_BY );
	}

	//endregion


	//region TopDestinationsObject - Header.HeaderBranding.CurrencySelector Implementation Methods Section

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
	private HtmlElement getCurrencySelectorSelect()
	{
		By findBy = By.id( "currencySelector" );
		return getDriver().findElement( findBy );
	}

	/**
	 * Full xpath : {@code /html/body/div[5]/div[1]/div[1]/div[1]/div[3]/div/ul/li[4]/a}
	 * Css path   : {@code #ccl-refresh-header > div.header-nav > div.header-branding > div > ul > li:nth-child(4) > a}
	 *
	 * @return a {@code WebElement} instance for the a.ccl-blue.nav-tooltip-trigger[data-id='currency']
	 */
	private HtmlElement getNavTooltipTriggerAnchor()
	{
		By findBy = By.cssSelector( "a.ccl-blue.nav-tooltip-trigger" );
		return getDriver().findElement( findBy );
	}

	/**
	 * Full xpath : {@code /html/body/div[5]/div[1]/div[1]/div[1]/div[3]/div/ul/li[4]/div}
	 * Css path   : {@code #ccl_header_currency_options}
	 *
	 * @return  a {@code WebElement} instance reference for the div#ccl_header_currency_options
	 */
	private HtmlElement getCclHeaderCurrencyOptionsDiv()
	{
		By findBy = By.id( "ccl_header_currency_options" );
		return getRoot().findElement( findBy );
	}

	//endregion

}