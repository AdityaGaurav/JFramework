package com.framework.site.objects.header;

import com.framework.asserts.JAssertion;
import com.framework.driver.objects.AbstractWebObject;
import com.framework.driver.objects.Link;
import com.framework.driver.utils.ui.WaitUtil;
import com.framework.site.config.SiteSessionManager;
import com.framework.site.objects.header.interfaces.Header;
import com.framework.site.pages.BaseCarnivalPage;
import com.framework.site.pages.core.CruiseDealsPage;
import com.framework.site.pages.core.HomePage;
import com.framework.utils.datetime.TimeConstants;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Locale;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.site.objects.header
 *
 * Name   : HeaderBrandingObject
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-01-06
 *
 * Time   : 10:56
 */

abstract class HeaderBrandingObject extends AbstractWebObject implements Header.HeaderBranding
{

	//region HeaderBrandingObject - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( HeaderBrandingObject.class );

	//endregion


	//region HeaderBrandingObject - Constructor Methods Section

	HeaderBrandingObject( WebDriver driver, final WebElement rootElement)
	{
		super( driver, rootElement, LOGICAL_NAME );
	}

	HeaderBrandingObject(  WebDriver driver, final WebElement rootElement, final String logicalName )
	{
		super( driver, rootElement, logicalName );
	}

	//endregion


	//region HeaderBrandingObject - Initialization and Validation Methods Section

	@Override
	protected void initWebObject()
	{
		logger.debug( "validating static elements for web object id: <{}>, name:<{}>...",
				getQualifier(), getLogicalName() );

		JAssertion assertion = new JAssertion( getWrappedDriver() );
		ExpectedCondition<WebElement> condition1 =
				WaitUtil.presenceOfChildBy( getRoot(), By.cssSelector( "div.header-branding a.logo" ) );
		ExpectedCondition<WebElement> condition2 =
				WaitUtil.presenceOfChildBy( getRoot(), By.cssSelector( "div.header-branding ul.zero-nav" ) );
		assertion.assertWaitThat( "Validate \"a.logo\" selector exists", TimeConstants.FIFTY_HUNDRED_MILLIS, condition1 );
		assertion.assertWaitThat( "Validate \"ul.zero-nav\" options exists", TimeConstants.FIFTY_HUNDRED_MILLIS, condition2 );
	}

	//endregion


	//region HeaderBrandingObject - Service Methods Section

	private WebElement getRoot()
	{
		return getBaseRootElement( CurrencySelector.ROOT_BY );
	}

	//endregion


	//region HeaderBrandingObject - Business Methods Section

	@Override
	public String getLocalePhoneNumber()
	{
		logger.debug( "Reading the local phone number ..." );
		return findLocaleNumberSpan().getText();
	}

	@Override
	public void clickSubscribeAndSave()
	{
		logger.debug( "Clicking on subscribe and save link ..." );
		Link link = new Link( findSubscribeAndSaveAnchor() );
		link.hover( true );
		link.click();
	}

	@Override
	public CruiseDealsPage clickYourCruiseDeals()
	{
		logger.debug( "Clicking on your cruise details link ..." );
		Link link = new Link( findYourCruiseDetailsAnchor() );
		link.hover( true );
		link.click();
		CruiseDealsPage cdp = new CruiseDealsPage( getWrappedDriver() );
		logger.info( "returning a new page instance -> '{}'", cdp );
		return cdp;
	}

	@Override
	public HomePage clickOnLogo()
	{
		logger.debug( "Clicking on logo link ..." );
		findLogoAnchor().click();
		HomePage hp = new HomePage( getWrappedDriver() );
		logger.info( "returning a new page instance -> '{}'", hp );
		return hp;
	}

	@Override
	public Locale getCurrentLocale()
	{
		logger.debug( "Parsing displayed current locale ..." );
		WebElement img = findCclLocaleImage();
		String alt = img.getAttribute( "alt" );
		switch ( alt )
		{
			case "United States":
				return Locale.US;
			case "United Kingdom":
				return Locale.UK;
			case "Australia":
				return BaseCarnivalPage.AU;
		}

		return null;
	}

	@Override
	public HomePage changeLocale( final Locale locale )
	{
		return null;
	}

	@Override
	public boolean isDisplayed()
	{
		return getRoot().isDisplayed();
	}

	@Override
	public boolean hasTopDestinations()
	{
		try
		{
			By findBy = org.openqa.selenium.By.cssSelector( "a.nav-tooltip-trigger[data-id='top-destinations']" );
			SiteSessionManager.getInstance().setImplicitlyWait( 50 );
			List<WebElement> list = getRoot().findElements( findBy );
			return list.size() > 0;
		}
		finally
		{
			SiteSessionManager.getInstance().restoreImplicitlyWait();
		}
	}

	@Override
	public boolean hasCurrencySelector()
	{
		By findBy = By.cssSelector( "a.ccl-blue.nav-tooltip-trigger[data-id='currency']" );
		return getWrappedDriver().elementExists( findBy );
	}

	//endregion


	//region HeaderBrandingObject - Element Finder Methods Section

	/**
	 * Full xpath  : {@code /html/body/div[5]/div[1]/div[1]/div[1]/div[3]/div/a}
	 * Css selector: {@code #ccl-refresh-header .pull-left}
	 *
	 * @return The {@linkplain org.openqa.selenium.WebElement} for the logo.
	 */
	private WebElement findLogoAnchor()
	{
		org.openqa.selenium.By findBy = org.openqa.selenium.By.xpath( ".//a[contains(@class,'logo')]" );
		return getRoot().findElement( findBy );
	}

	/**
	 * Full xpath  :  {@code /html/body/div[5]/div[1]/div[1]/div[1]/div[3]/div/ul/li[5]/span}
	 * Short xpath :  {@code .//*[@id='ccl_header_locale-number']}
	 * Css selector:  {@code ##ccl-refresh-header .header-branding .zero-nav #ccl_header_locale-number}
	 *
	 * @return The {@linkplain org.openqa.selenium.WebElement} for the 'Your Cruise Details' link.
	 */
	private WebElement findLocaleNumberSpan()
	{
		org.openqa.selenium.By findBy = org.openqa.selenium.By.id( "ccl_header_locale-number" );
		return findZeroNavigationDiv().findElement( findBy );
	}

	/**
	 * Full xpath  :  {@code /html/body/div[5]/div[1]/div[1]/div[1]/div[3]/div/ul/li[2]/a}
	 * Short xpath :  {@code .//*[@id='subscribeLink']}
	 * Css selector:  {@code #ccl-refresh-header .ccl-blue}
	 *
	 * @return The {@linkplain org.openqa.selenium.WebElement} for the 'Your Cruise Details' link.
	 */
	private WebElement findSubscribeAndSaveAnchor()
	{
		org.openqa.selenium.By findBy = org.openqa.selenium.By.id( "subscribeLink" );
		return findZeroNavigationDiv().findElement( findBy );
	}

	/**
	 * Full xpath  :  {@code /html/body/div[5]/div[1]/div[1]/div[1]/div[3]/div/ul/li[1]/a}
	 * Short xpath :  {@code .//*[@id='ccl-refresh-header']/div[1]/div[3]/div/ul/li[1]/a}
	 * Css selector:  {@code #ccl-refresh-header .ccl-red}
	 *
	 * @return The {@linkplain org.openqa.selenium.WebElement} for the 'Your Cruise Details' link.
	 */
	private WebElement findYourCruiseDetailsAnchor()
	{
		org.openqa.selenium.By findBy = org.openqa.selenium.By.xpath( "./li/a[@class='ccl-red']" );
		return findZeroNavigationDiv().findElement( findBy );
	}

	private WebElement findZeroNavigationDiv()
	{
		final By findBy = By.cssSelector( "div.header-branding ul.zero-nav" );
		return getWrappedDriver().findElement( findBy );
	}

	private WebElement findCclLocaleImage()
	{
		org.openqa.selenium.By findBy = org.openqa.selenium.By.xpath( "//*[@id='ccl_header_locale']/img" );
		return getWrappedDriver().findElement( findBy );
	}

	//endregion

}
