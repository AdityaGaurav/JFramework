package com.framework.site.objects.header;

import com.framework.asserts.JAssertion;
import com.framework.driver.event.HtmlElement;
import com.framework.driver.objects.AbstractWebObject;
import com.framework.site.config.SiteSessionManager;
import com.framework.site.objects.header.interfaces.Header;
import com.framework.site.pages.BaseCarnivalPage;
import com.framework.utils.matchers.JMatchers;
import com.google.common.base.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Locale;

import static com.framework.utils.datetime.TimeConstants.*;


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

	private HtmlElement header_nav_additional, header_links, header_subscribe, header_branding, notif_bar, message_bar = null;

	//endregion


	//region HeaderSectionObject - Constructor Methods Section

	public SectionHeaderObject( final HtmlElement rootElement )
	{
		super( rootElement, Header.LOGICAL_NAME );
		initWebObject();
	}

	//endregion


	//region HeaderSectionObject - Initialization and Validation Methods Section

	@Override
	protected void initWebObject()
	{
		logger.debug( "validating static elements for web object id: <{}>, name:<{}>...",
				getQualifier(), getLogicalName() );

		final String REASON = "assert that element \"%s\" exits";
		JAssertion assertion = getRoot().createAssertion();

		Optional<HtmlElement> e = getRoot().childExists( MessageBar.ROOT_BY, FIVE_SECONDS );
		assertion.assertThat( String.format( REASON, "div.message-bar" ), e.isPresent(), JMatchers.is( true ) );
		this.message_bar = e.get();

		e = getRoot().childExists( NotificationBar.ROOT_BY, TWO_SECONDS );
		assertion.assertThat( String.format( REASON, "div.notif-bar" ), e.isPresent(), JMatchers.is( true ) );
		this.notif_bar = e.get();

		e = getRoot().childExists( HeaderBranding.ROOT_BY, TWO_SECONDS );
		assertion.assertThat( String.format( REASON, "div.header-branding" ), e.isPresent(), JMatchers.is( true ) );
		this.header_branding = e.get();

		e = getRoot().childExists( HeaderSubscribe.ROOT_BY, ONE_SECOND );
		assertion.assertThat( String.format( REASON, "div.header-subscribe" ), e.isPresent(), JMatchers.is( true ) );
		this.header_subscribe = e.get();

		e = getRoot().childExists( HeaderLinks.ROOT_BY, ONE_SECOND );
		assertion.assertThat( String.format( REASON, "div.header-links" ), e.isPresent(), JMatchers.is( true ) );
		this.header_links = e.get();

		e = getRoot().childExists( NavigationAdditional.ROOT_BY, ONE_SECOND );
		assertion.assertThat( String.format( REASON, "div.header-nav-additional" ), e.isPresent(), JMatchers.is( true ) );
		this.header_nav_additional = e.get();
	}

	//endregion


	//region HeaderSectionObject - Service Methods Section

	@Override
	public MessageBar messageBar()
	{
		if ( null == this.messageBar )
		{
			this.messageBar = new MessageBarObject( findMessageBarDiv() );
		}
		return messageBar;
	}

	@Override
	public NotificationBar notificationBar()
	{
		if ( null == this.notificationBar )
		{
			this.notificationBar = new NotificationBarObject( findNotificationBarDiv() );
		}
		return notificationBar;
	}

	@Override
	public HeaderBranding headerBranding()
	{
		Locale locale = SiteSessionManager.get().getCurrentLocale();

		if ( null == this.headerBranding )
		{
			if( locale.equals( BaseCarnivalPage.AU ))
			{
				return new CurrencySelectorObject( findHeaderBrandingDiv() );
			}
			else
			{
				return new TopDestinationsObject( findHeaderBrandingDiv() );
			}
		}
		return headerBranding;
	}

	@Override
	public HeaderSubscribe subscribe()
	{
		if ( null == this.headerSubscribe )
		{
			this.headerSubscribe = new HeaderSubscribeObject( findHeaderSubscribeDiv() );
		}
		return headerSubscribe;
	}

	@Override
	public HeaderLinks headerLinks()
	{
		if ( null == this.headerLinks )
		{
			this.headerLinks = new HeaderLinksObject( findHeaderLinksDiv() );
		}
		return headerLinks;
	}

	@Override
	public NavigationAdditional navigationAdditional()
	{
		if ( null == this.navigationAdditional )
		{
			this.navigationAdditional = new NavigationAdditionalObject( findNavigationAdditionalDiv() );
		}
		return navigationAdditional;
	}

	private HtmlElement getRoot()
	{
		return getBaseRootElement( SectionHeaderObject.ROOT_BY );
	}

	//endregion


	//region HeaderSectionObject - Element Finder Methods Section

	private HtmlElement findMessageBarDiv()
	{
		if( null == message_bar )
		{
			this.message_bar = getDriver().findElement( MessageBar.ROOT_BY );
		}
		return this.message_bar;
	}

	private HtmlElement findNotificationBarDiv()
	{
		return getRoot().findElement( NotificationBar.ROOT_BY );
	}

	private HtmlElement findHeaderBrandingDiv()
	{
		if( null == header_branding )
		{
			this.header_branding = getDriver().findElement( HeaderBranding.ROOT_BY );
		}
		return this.header_branding;
	}

	private HtmlElement findHeaderSubscribeDiv()
	{
		if( null == header_subscribe )
		{
			this.header_subscribe = getDriver().findElement( HeaderSubscribe.ROOT_BY );
		}
		return this.header_subscribe;
	}

	private HtmlElement findHeaderLinksDiv()
	{
		if( null == header_links )
		{
			this.header_links =  getDriver().findElement( HeaderLinks.ROOT_BY );
		}
		return this.header_links;
	}

	private HtmlElement findNavigationAdditionalDiv()
	{
		if( null == header_nav_additional )
		{
			this.header_nav_additional =  getDriver().findElement( NavigationAdditional.ROOT_BY );
		}
		return this.header_nav_additional;
	}

	private HtmlElement findCurrencySelectorLi()
	{
		return findHeaderBrandingDiv().findElement( HeaderBranding.CurrencySelector.ROOT_BY );
	}

	private HtmlElement findTopDestinations()
	{
		return findHeaderBrandingDiv().findElement( HeaderBranding.TopDestinations.ROOT_BY );
	}

	//endregion

}
