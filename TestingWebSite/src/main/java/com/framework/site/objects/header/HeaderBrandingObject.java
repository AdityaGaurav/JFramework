package com.framework.site.objects.header;

import com.framework.asserts.JAssertion;
import com.framework.driver.event.HtmlElement;
import com.framework.driver.event.HtmlObject;
import com.framework.driver.extensions.jquery.By;
import com.framework.driver.objects.AbstractWebObject;
import com.framework.driver.objects.Link;
import com.framework.site.objects.header.interfaces.Header;
import com.framework.site.pages.BaseCarnivalPage;
import com.framework.site.pages.core.CruiseDealsPage;
import com.framework.site.pages.core.HomePage;
import com.google.common.base.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Locale;

import static com.framework.utils.datetime.TimeConstants.FIVE_SECONDS;
import static com.framework.utils.datetime.TimeConstants.THREE_SECONDS;
import static com.framework.utils.matchers.JMatchers.endsWith;
import static com.framework.utils.matchers.JMatchers.is;
import static org.hamcrest.Matchers.isEmptyString;
import static org.hamcrest.Matchers.not;


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

class HeaderBrandingObject extends AbstractWebObject implements Header.HeaderBranding
{

	//region HeaderBrandingObject - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( HeaderBrandingObject.class );

	private HtmlElement logo_pull_left, zero_nav;

	private HtmlElement ccl_red, subscribeLink_ccl_blue;

	private HtmlElement ccl_header_locale, ccl_header_locale_options, ccl_header_locale_img, ccl_header_locale_parent;

	private List<HtmlElement> ccl_header_locale_optionsLinks;

	private HtmlElement ccl_header_locale_number;

	//private WebElement nav_tooltip_trigger_top_destinations;

	//endregion


	//region HeaderBrandingObject - Constructor Methods Section

	protected HeaderBrandingObject( final HtmlElement rootElement)
	{
		this( rootElement, LOGICAL_NAME );
	}

	protected HeaderBrandingObject( final HtmlElement rootElement, final String logicalName )
	{
		super( rootElement, logicalName );
		initWebObject();
	}

	//endregion


	//region HeaderBrandingObject - Initialization and Validation Methods Section

	@Override
	protected void initWebObject()
	{
		logger.debug( "validating static elements for web object id: <{}>, name:<{}>...",
				getQualifier(), getLogicalName() );

		final String REASON = "assert that element \"%s\" exits";
		JAssertion assertion = getRoot().createAssertion();

		Optional<HtmlElement> e = getRoot().childExists( By.cssSelector( "div.header-branding a.logo" ), FIVE_SECONDS );
		assertion.assertThat( String.format( REASON, "div.header-branding a.logo" ), e.isPresent(), is( true ) );

		e = getRoot().childExists( By.cssSelector( "div.header-branding a.logo" ), FIVE_SECONDS );
		assertion.assertThat( String.format( REASON, "div.header-branding a.logo" ), e.isPresent(), is( true ) );
		this.zero_nav = e.get();
	}

	//endregion


	//region HeaderBrandingObject - Service Methods Section

	private HtmlElement getRoot()
	{
		return getBaseRootElement( Header.HeaderBranding.ROOT_BY );
	}

	//endregion


	//region HeaderBrandingObject - Business Methods Section

	@Override
	public String getLocalePhoneNumber()
	{
		logger.debug( "Reading the local phone number ..." );
		return findCclHeaderLocaleNumberSpan().getText();
	}

	@Override
	public void clickSubscribeAndSave()
	{
		logger.debug( "Clicking on subscribe and save link ..." );
		Link link = new Link( findSubscribeLink() );
		link.hover( true );
		link.click();
	}

	@Override
	public CruiseDealsPage clickYourCruiseDeals()
	{
		logger.debug( "Clicking on your cruise details link ..." );
		Link link = new Link( findCclRedAnchor() );
		link.hover( true );
		link.click();
		CruiseDealsPage cdp = new CruiseDealsPage();
		logger.info( "returning a new page instance -> '{}'", cdp );
		return cdp;
	}

	@Override
	public HomePage clickOnLogo()
	{
		logger.info( "Clicking on logo link ..." );
		findLogoPullLeftAnchor().click();
		HomePage hp = new HomePage();
		logger.info( "returning a new page instance -> '{}'", hp );
		return hp;
	}

	@Override
	public Locale getDisplayedLocale()
	{
		logger.debug( "Parsing displayed current locale ..." );
		String alt = findCclHeaderLocaleImage().getAttribute( "alt" );
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
	public boolean isLocalesListOpened()
	{
		return findCclHeaderLocaleOptionsDiv().isDisplayed();
	}

	@Override
	public boolean isDisplayed()
	{
		return getRoot().isDisplayed();
	}

	@Override
	public boolean hasTopDestinations()
	{
		return getDriver().elementExists( By.cssSelector( "a.nav-tooltip-trigger[data-id='top-destinations']" ) ).isPresent() ;
	}

	@Override
	public boolean hasCurrencySelector()
	{
		return getDriver().elementExists( By.cssSelector( "a.ccl-blue.nav-tooltip-trigger[data-id='currency']" ) ).isPresent() ;
	}

	@Override
	public List<String> openLocalesList()
	{
		logger.debug( "opening the country list, by clicking on the current locale flag icon" );

		findCclHeaderLocaleAnchor().click();
		getRoot().waitAttributeToMatch( "class", endsWith( "increaseZ" ), THREE_SECONDS );
		findHeaderLocaleParentLi().waitAttributeToMatch( "class", is( "hover" ), THREE_SECONDS );
		findCclHeaderLocaleOptionsDiv().waitToBeDisplayed( true, THREE_SECONDS );
		List<HtmlElement> spans = findLocaleOptionsAnchors();

		return HtmlObject.extractText( spans );
	}


	@Override
	public HomePage changeLocale( final Locale locale )
	{
		return null;
	}

	@Override
	public void closeLocalesList()
	{
		logger.debug( "closing the country list, by clicking on the current locale flag icon" );

		findCclHeaderLocaleAnchor().click();
		getRoot().waitAttributeToMatch( "class", not( endsWith( "increaseZ" ) ), THREE_SECONDS );
		findHeaderLocaleParentLi().waitAttributeToMatch( "class", isEmptyString(), THREE_SECONDS );
		findCclHeaderLocaleOptionsDiv().waitToBeDisplayed( false, THREE_SECONDS );
	}

	//endregion


	//region HeaderBrandingObject - Element Finder Methods Section

	private HtmlElement findLogoPullLeftAnchor()
	{
		if( null == logo_pull_left )
		{
			logo_pull_left = getRoot().findElement( By.cssSelector( "a.logo.pull-left" ) );
		}
		return logo_pull_left;
	}

	protected HtmlElement findZeroNavUl()
	{
		if( null == zero_nav )
		{
			zero_nav = getRoot().findElement( By.cssSelector( "ul.zero-nav" ) );
		}
		return zero_nav;
	}

	private HtmlElement findCclRedAnchor()
	{
		if( null == ccl_red )
		{
			ccl_red = findZeroNavUl().findElement( By.className( "ccl-red" ) );
		}
		return ccl_red;
	}

	private HtmlElement findSubscribeLink()
	{
		if( null == subscribeLink_ccl_blue )
		{
			subscribeLink_ccl_blue = findZeroNavUl().findElement( By.id( "subscribeLink" ) );
		}
		return subscribeLink_ccl_blue;
	}

	private HtmlElement findCclHeaderLocaleAnchor()
	{
		if( null == ccl_header_locale )
		{
			ccl_header_locale = findZeroNavUl().findElement( By.id( "ccl_header_locale" ) );
		}
		return ccl_header_locale;
	}

	private HtmlElement findCclHeaderLocaleImage()
	{
		if( null == ccl_header_locale_img )
		{
			ccl_header_locale_img = findCclHeaderLocaleAnchor().findElement( By.tagName( "img" ) );
		}
		return ccl_header_locale_img;
	}

	private HtmlElement findCclHeaderLocaleOptionsDiv()
	{
		if( null == ccl_header_locale_options )
		{
			ccl_header_locale_options = findZeroNavUl().findElement( By.id( "ccl_header_locale_options" ) );
		}
		return ccl_header_locale_options;
	}

	private HtmlElement findCclHeaderLocaleNumberSpan()
	{
		if( null == ccl_header_locale_number )
		{
			ccl_header_locale_number = findZeroNavUl().findElement( By.id( "ccl_header_locale_number" ) );
		}
		return ccl_header_locale_number;
	}

	private List<HtmlElement> findLocaleOptionsAnchors()
	{
		if( null == ccl_header_locale_optionsLinks )
		{
			ccl_header_locale_optionsLinks = findCclHeaderLocaleOptionsDiv().findElements( By.tagName( "a" ) );
		}
		return ccl_header_locale_optionsLinks;
	}

	private HtmlElement findHeaderLocaleParentLi()
	{
		if( null == this.ccl_header_locale_parent )
		{
			this.ccl_header_locale_number = getDriver().findElement( By.jQuerySelector( "#ccl_header_locale" ).parent() );
		}
		return ccl_header_locale_number;
	}





//	/**
//	 * Full xpath  : {@code /html/body/div[5]/div[1]/div[1]/div[1]/div[3]/div/a}
//	 * Css selector: {@code #ccl-refresh-header .pull-left}
//	 *
//	 * @return The {@linkplain org.openqa.selenium.WebElement} for the logo.
//	 */
//	private WebElement findLogoAnchor()
//	{
//		org.openqa.selenium.By findBy = org.openqa.selenium.By.xpath( ".//a[contains(@class,'logo')]" );
//		return getRoot().findElement( findBy );
//	}
//
//	/**
//	 * Css selector:  {@code #ccl_header_locale-number}
//	 *
//	 * @return The {@linkplain org.openqa.selenium.WebElement} for the 'Your Cruise Details' link.
//	 */
//	private WebElement findLocaleNumberSpan()
//	{
//		org.openqa.selenium.By findBy = org.openqa.selenium.By.id( "ccl_header_locale-number" );
//		return findZeroNavigationDiv().findElement( findBy );
//	}
//
//	private List<WebElement> findLocaleSpans()
//	{
//		By findBy = By.xpath( "//*[@id='ccl_header_locale_options']//span" );
//		return getDriver().findElements( findBy );
//	}
//
//	/**
//	 * @return a reference to a#ccl_header_locale.nav-tooltip-trigger[data-id='locale']
//	 */
//	private WebElement findLocaleTooltipTriggerAnchor()
//	{
//		By findBy = By.id( "ccl_header_locale" );
//		return getDriver().findElement( findBy );
//	}
//
//
//	private WebElement findLocaleParentLi()
//	{
//		By findBy = By.xpath( "//*[@id='ccl_header_locale']/.." );
//		return getDriver().findElement( findBy );
//	}
//
//	/**
//	 * Full xpath  :  {@code /html/body/div[5]/div[1]/div[1]/div[1]/div[3]/div/ul/li[2]/a}
//	 * Short xpath :  {@code .//*[@id='subscribeLink']}
//	 * Css selector:  {@code #ccl-refresh-header .ccl-blue}
//	 *
//	 * @return The {@linkplain org.openqa.selenium.WebElement} for the 'Your Cruise Details' link.
//	 */
//	private WebElement findSubscribeAndSaveAnchor()
//	{
//		org.openqa.selenium.By findBy = org.openqa.selenium.By.id( "subscribeLink" );
//		return findZeroNavigationDiv().findElement( findBy );
//	}
//
//	/**
//	 * Full xpath  :  {@code /html/body/div[5]/div[1]/div[1]/div[1]/div[3]/div/ul/li[1]/a}
//	 * Short xpath :  {@code .//*[@id='ccl-refresh-header']/div[1]/div[3]/div/ul/li[1]/a}
//	 * Css selector:  {@code #ccl-refresh-header .ccl-red}
//	 *
//	 * @return The {@linkplain org.openqa.selenium.WebElement} for the 'Your Cruise Details' link.
//	 */
//	private WebElement findYourCruiseDetailsAnchor()
//	{
//		By findBy = By.xpath( "./li/a[@class='ccl-red']" );
//		return findZeroNavigationDiv().findElement( findBy );
//	}
//
//	private WebElement findZeroNavigationDiv()
//	{
//		final By findBy = By.cssSelector( "div.header-branding ul.zero-nav" );
//		return getDriver().findElement( findBy );
//	}
//
//	private WebElement findCclLocaleImage()
//	{
//		By findBy = By.xpath( "//*[@id='ccl_header_locale']/img" );
//		return getDriver().findElement( findBy );
//	}

	//endregion

}
