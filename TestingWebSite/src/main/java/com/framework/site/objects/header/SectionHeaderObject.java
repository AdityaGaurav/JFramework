package com.framework.site.objects.header;

import com.framework.asserts.JAssertion;
import com.framework.driver.objects.AbstractWebObject;
import com.framework.driver.utils.ui.WaitUtil;
import com.framework.site.config.SiteSessionManager;
import com.framework.site.objects.header.interfaces.Header;
import com.framework.site.pages.BaseCarnivalPage;
import com.framework.utils.datetime.TimeConstants;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Locale;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.site.objects.header
 *
 * Name   : HeaderObject
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-01-05
 *
 * Time   : 23:11
 */

public class SectionHeaderObject extends AbstractWebObject implements Header
{

	//region HeaderSectionObject - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( SectionHeaderObject.class );

	// ------------------------------------------------------------------------|
	// --- WEB-OBJECTS DEFINITIONS --------------------------------------------|
	// ------------------------------------------------------------------------|

	private NotificationBar notificationBar = null;

	private MessageBar messageBar = null;

	private HeaderLinks headerLinks = null;

	private HeaderSubscribe headerSubscribe = null;

	private HeaderBranding headerBranding = null;

	private NavigationAdditional navigationAdditional = null;

	//endregion


	//region HeaderSectionObject - Constructor Methods Section

	public SectionHeaderObject( WebDriver driver, final WebElement rootElement )
	{
		super( driver, rootElement, Header.LOGICAL_NAME );
	}

	//endregion


	//region HeaderSectionObject - Initialization and Validation Methods Section

	@Override
	protected void initWebObject()
	{
		JAssertion assertion = new JAssertion( getWrappedDriver() );

		assertion.assertWaitThat(
				"assert that element \"div.message-bar\" exits",
				TimeConstants.FIFTY_HUNDRED_MILLIS,
				WaitUtil.presenceOfChildBy( getRoot(), MessageBar.ROOT_BY ) );
		assertion.assertWaitThat(
				"assert that element \"div.notif-bar\" exits",
				TimeConstants.FIFTEEN_HUNDRED_MILLIS,
				WaitUtil.presenceOfChildBy( getRoot(), NotificationBar.ROOT_BY ) );
		assertion.assertWaitThat(
				"assert that element \"div.header-branding\" exits",
				TimeConstants.FIFTEEN_HUNDRED_MILLIS,
				WaitUtil.presenceOfChildBy( getRoot(), HeaderBranding.ROOT_BY ) );
		assertion.assertWaitThat(
				"assert that element \"div.header-subscribe\" exits",
				TimeConstants.FIFTEEN_HUNDRED_MILLIS,
				WaitUtil.presenceOfChildBy( getRoot(), HeaderSubscribe.ROOT_BY ) );
		assertion.assertWaitThat(
				"assert that element \"div.header-links\" exits",
				TimeConstants.FIFTEEN_HUNDRED_MILLIS,
				WaitUtil.presenceOfChildBy( getRoot(), HeaderLinks.ROOT_BY ) );
		assertion.assertWaitThat(
				"assert that element \"div.header-nav-additional\" exits",
				TimeConstants.FIFTY_HUNDRED_MILLIS,
				WaitUtil.presenceOfChildBy( getRoot(), NavigationAdditional.ROOT_BY ) );
	}

	//endregion


	//region HeaderSectionObject - Service Methods Section

	@Override
	public MessageBar messageBar()
	{
		if ( null == this.messageBar )
		{
			this.messageBar = new MessageBarObject( getWrappedDriver(), findMessageBarDiv() );
		}
		return messageBar;
	}

	@Override
	public NotificationBar notificationBar()
	{
		if ( null == this.notificationBar )
		{
			this.notificationBar = new NotificationBarObject( getWrappedDriver(), findNotificationBarDiv() );
		}
		return notificationBar;
	}

	@Override
	public HeaderBranding headerBranding()
	{
		Locale locale = SiteSessionManager.getInstance().getCurrentLocale();

		if ( null == this.headerBranding )
		{
			if( locale.equals( BaseCarnivalPage.AU ))
			{
				return new CurrencySelectorObject( getWrappedDriver(), getRoot() );
			}
			else
			{
				return new TopDestinationsObject( getWrappedDriver(), getRoot() );
			}
		}
		return headerBranding;
	}

	@Override
	public HeaderSubscribe subscribe()
	{
		if ( null == this.headerSubscribe )
		{
			this.headerSubscribe = new HeaderSubscribeObject( getWrappedDriver(), findHeaderSubscribeDiv() );
		}
		return headerSubscribe;
	}

	@Override
	public HeaderLinks headerLinks()
	{
		if ( null == this.headerLinks )
		{
			this.headerLinks = new HeaderLinksObject( getWrappedDriver(), findHeaderLinksDiv() );
		}
		return headerLinks;
	}

	@Override
	public NavigationAdditional navigationAdditional()
	{
		if ( null == this.navigationAdditional )
		{
			this.navigationAdditional = new NavigationAdditionalObject( getWrappedDriver(), findNavigationAdditionalDiv() );
		}
		return navigationAdditional;
	}

	private WebElement getRoot()
	{
		return getBaseRootElement( SectionHeaderObject.ROOT_BY );
	}

	//endregion


	//region HeaderSectionObject - Element Finder Methods Section

	private WebElement findMessageBarDiv()
	{
		return getRoot().findElement( MessageBar.ROOT_BY );
	}

	private WebElement findNotificationBarDiv()
	{
		return getRoot().findElement( NotificationBar.ROOT_BY );
	}

	private WebElement findHeaderBrandingDiv()
	{
		return getRoot().findElement( HeaderBranding.ROOT_BY );
	}

	private WebElement findHeaderSubscribeDiv()
	{
		return getRoot().findElement( HeaderSubscribe.ROOT_BY );
	}

	private WebElement findHeaderLinksDiv()
	{
		return getRoot().findElement( HeaderLinks.ROOT_BY );
	}

	private WebElement findNavigationAdditionalDiv()
	{
		return getRoot().findElement( NavigationAdditional.ROOT_BY );
	}

	private WebElement findCurrencySelectorLi()
	{
		return findHeaderBrandingDiv().findElement( HeaderBranding.CurrencySelector.ROOT_BY );
	}

	private WebElement findTopDestinations()
	{
		return findHeaderBrandingDiv().findElement( HeaderBranding.TopDestinations.ROOT_BY );
	}

	//endregion

}
