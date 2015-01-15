package com.framework.site.objects.header;

import com.framework.driver.exceptions.ApplicationException;
import com.framework.driver.utils.ui.WaitUtil;
import com.framework.site.objects.header.interfaces.Header;
import com.framework.site.pages.BaseCarnivalPage;
import com.google.common.base.MoreObjects;
import com.google.common.base.Throwables;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.NoSuchElementException;


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
		super( CurrencySelector.LOGICAL_NAME, driver, rootElement );
	}

	//endregion


	//region TopDestinationsObject - Initialization and Validation Methods Section

	@Override
	protected void initWebObject()
	{
		logger.debug( "validating static elements for web object id: <{}>, name:<{}>...", getId(), getLogicalName() );
		WebDriverWait wew = WaitUtil.wait10( objectDriver );

		try
		{
		 	if( ! getCurrentLocale().equals( BaseCarnivalPage.AU ) )
			{

			}
			else
			{
				logger.debug( "CurrencySelector is not supported on locale <{}>. skipped initWebObject"  );
			}
		}
		catch ( AssertionError ae )
		{
			Throwables.propagateIfInstanceOf( ae, ApplicationException.class );
			logger.error( "throwing a new WebObjectException on TopDestinationsObject#initWebObject." );
			ApplicationException ex = new ApplicationException( objectDriver.getWrappedDriver(), ae.getMessage(), ae );
			ex.addInfo( "cause", "verification and initialization process for object " + getLogicalName() + " was failed." );
			throw ex;
		}
	}

	//endregion


	//region TopDestinationsObject - Service Methods Section

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper( this )
				.add( "logical name", getLogicalName() )
				.add( "id", getId() )
				.omitNullValues()
				.toString();
	}

	private String getCurrencySelected()
	{
		final String CURRENCY_SELECTED_SCRIPT = "return utag_data.cp.CurrencySelected;";
		return objectDriver.getJavaScript().getString( CURRENCY_SELECTED_SCRIPT );
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
			rootElement = objectDriver.findElement( CurrencySelector.ROOT_BY );
		}

		return rootElement;
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
		return objectDriver.findElement( findBy );
	}

	/**
	 * Full xpath : {@code /html/body/div[5]/div[1]/div[1]/div[1]/div[3]/div/ul/li[4]/a}
	 * Css path   : {@code #ccl-refresh-header > div.header-nav > div.header-branding > div > ul > li:nth-child(4) > a}
	 *
	 * @return a {@code WebElement} instance for the a.ccl-blue.nav-tooltip-trigger[data-id='currency']
	 */
	private WebElement getNavTooltipTriggerAnchor()
	{
		try
		{
			By findBy = By.className( "nav-tooltip-trigger" );
			return objectDriver.findElement( findBy );
		}
		catch ( NoSuchElementException e )
		{
			return null;
		}
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