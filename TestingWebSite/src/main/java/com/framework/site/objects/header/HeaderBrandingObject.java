package com.framework.site.objects.header;

import com.framework.asserts.JAssertions;
import com.framework.driver.exceptions.ApplicationException;
import com.framework.driver.objects.AbstractWebObject;
import com.framework.driver.objects.Link;
import com.framework.driver.utils.ui.ExtendedBy;
import com.framework.driver.utils.ui.WaitUtil;
import com.framework.site.objects.header.interfaces.Header;
import com.framework.site.pages.core.CruiseDealsPage;
import com.framework.site.pages.core.HomePage;
import com.google.common.base.MoreObjects;
import com.google.common.base.Throwables;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;


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

	/**
	 * ul.zero-nav object. holds the zero navigation.
	 */
	private WebElement zeroNavigationElement = null;
	private static final By zeroNavBy = ExtendedBy.composite( By.tagName( "ul" ), By.className( "zero-nav" ) );

	//endregion


	//region HeaderBrandingObject - Constructor Methods Section

	HeaderBrandingObject( WebDriver driver, final WebElement rootElement)
	{
		super( LOGICAL_NAME, driver, rootElement );
	}

	HeaderBrandingObject( final String logicalName ,WebDriver driver, final WebElement rootElement )
	{
		super( logicalName, driver, rootElement );
	}

	//endregion


	//region HeaderBrandingObject - Initialization and Validation Methods Section

	@Override
	protected void initWebObject()
	{
		logger.debug( "validating static elements for web object id: <{}>, name:<{}>...", getId(), getLogicalName() );
		WebDriverWait wew = WaitUtil.wait10( this.objectDriver );

		try
		{
			JAssertions.assertWaitThat( wew ).matchesCondition( WaitUtil.presenceOfChildBy( getRoot(), zeroNavBy ) );
			this.zeroNavigationElement = findZeroNavigationDiv();
		}
		catch ( AssertionError ae )
		{
			Throwables.propagateIfInstanceOf( ae, ApplicationException.class );
			logger.error( "throwing a new WebObjectException on {}#initWebObject.", getClass().getSimpleName() );
			ApplicationException appEx = new ApplicationException( objectDriver.getWrappedDriver(), ae.getMessage(),ae );
			appEx.addInfo( "cause", "verification and initialization process for object " + getLogicalName() + " was failed." );
			throw appEx;
		}
	}

	//endregion


	//region HeaderBrandingObject - Service Methods Section

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper( this )
				.add( "logical name", getLogicalName() )
				.add( "id", getId() )
				.omitNullValues()
				.toString();
	}

	private WebElement getRoot()
	{
		try
		{
			rootElement.getTagName();
			return rootElement;
		}
		catch ( StaleElementReferenceException sreEx )
		{
			logger.warn( "auto recovering from StaleElementReferenceException ..." );
			return objectDriver.findElement( Header.HeaderBranding.ROOT_BY );
		}
	}

	//endregion


	//region HeaderBrandingObject - Business Methods Section

	@Override
	public String getLocalePhoneNumber()
	{
		try
		{
			return findLocaleNumberSpan().getText();
		}
		catch ( Throwable t )
		{
			Throwables.propagateIfInstanceOf( t, ApplicationException.class );
			logger.error( "throwing a new ApplicationException on {}#getLocalePhoneNumber.", getClass().getSimpleName() );
			ApplicationException appEx = new ApplicationException( objectDriver.getWrappedDriver(), t.getMessage(), t );
			appEx.addInfo( "business process", "failed to read local phone number" );
			throw appEx;
		}
	}

	@Override
	public void clickSubscribeAndSave()
	{
		try
		{
			Link link = new Link( objectDriver, findSubscribeAndSaveAnchor() );
			link.hover( true );
			link.click();
		}
		catch ( Throwable t )
		{
			Throwables.propagateIfInstanceOf( t, ApplicationException.class );
			logger.error( "throwing a new ApplicationException on {}#clickSubscribeAndSave.", getClass().getSimpleName() );
			ApplicationException appEx = new ApplicationException( objectDriver.getWrappedDriver(), t.getMessage(), t );
			appEx.addInfo( "business process", "failed to click on Subscribe and Save link" );
			throw appEx;
		}
	}

	@Override
	public CruiseDealsPage clickYourCruiseDeals()
	{
		try
		{
			Link link = new Link( objectDriver, findYourCruiseDetailsAnchor() );
			link.hover( true );
			link.click();
			CruiseDealsPage cdp = new CruiseDealsPage( objectDriver );
			logger.info( "returning a new page instance -> '{}'", cdp );
			return cdp;
		}
		catch ( Throwable t )
		{
			Throwables.propagateIfInstanceOf( t, ApplicationException.class );
			logger.error( "throwing a new ApplicationException on {}#clickYourCruiseDeals.", getClass().getSimpleName() );
			ApplicationException appEx = new ApplicationException( objectDriver.getWrappedDriver(), t.getMessage(), t );
			appEx.addInfo( "business process", "failed to click Your Cruise Deals" );
			throw appEx;
		}
	}

	@Override
	public HomePage clickOnLogo()
	{
		try
		{
		 	findLogoAnchor().click();
			HomePage hp = new HomePage( objectDriver );
			logger.info( "returning a new page instance -> '{}'", hp );
			return hp;
		}
		catch ( Throwable t )
		{
			Throwables.propagateIfInstanceOf( t, ApplicationException.class );
			logger.error( "throwing a new ApplicationException on {}#clickOnLogo.", getClass().getSimpleName() );
			ApplicationException appEx = new ApplicationException( objectDriver.getWrappedDriver(), t.getMessage(), t );
			appEx.addInfo( "business process", "failed to clickOnLogo" );
			throw appEx;
		}
	}

	@Override
	public Locale getCurrentLocale()
	{
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
			By findBy = By.cssSelector( "a.nav-tooltip-trigger[data-id='top-destinations']" );
			objectDriver.manage().timeouts().implicitlyWait( 50, TimeUnit.MILLISECONDS );
			List<WebElement> list = getRoot().findElements( findBy );
			return list.size() > 0;
		}
		finally
		{
			objectDriver.manage().timeouts().implicitlyWait( 10000, TimeUnit.MILLISECONDS ); //todo RESTORE TIMEOUT
		}
	}

	@Override
	public boolean hasCurrencySelector()
	{
		try
		{
			By findBy = By.cssSelector( "a.ccl-blue.nav-tooltip-trigger[data-id='currency']" );
			objectDriver.manage().timeouts().implicitlyWait( 50, TimeUnit.MILLISECONDS );
			List<WebElement> list = getRoot().findElements( findBy );
			return list.size() > 0;
		}
		finally
		{
			objectDriver.manage().timeouts().implicitlyWait( 10000, TimeUnit.MILLISECONDS ); //todo RESTORE TIMEOUT
		}
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
		By findBy = By.xpath( ".//a[contains(@class,'logo')]" );
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
		By findBy = By.id( "ccl_header_locale-number" );
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
		By findBy = By.id( "subscribeLink" );
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
		By findBy = By.xpath( "./li/a[@class='ccl-red']" );
		return findZeroNavigationDiv().findElement( findBy );
	}

	private WebElement findZeroNavigationDiv()
	{
		if( null == zeroNavigationElement )
		{
			this.zeroNavigationElement = getRoot().findElement( zeroNavBy );
		}

		return zeroNavigationElement;
	}

	//endregion

}
