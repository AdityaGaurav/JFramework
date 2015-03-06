package com.framework.site.objects.header;

import ch.lambdaj.Lambda;
import com.framework.asserts.JAssertion;
import com.framework.driver.event.HtmlElement;
import com.framework.driver.event.HtmlObject;
import com.framework.driver.exceptions.ApplicationException;
import com.framework.driver.exceptions.UrlNotAvailableException;
import com.framework.driver.objects.AbstractWebObject;
import com.framework.driver.objects.Link;
import com.framework.site.objects.header.enums.LevelOneMenuItem;
import com.framework.site.objects.header.interfaces.Header;
import com.framework.site.pages.BaseCarnivalPage;
import com.framework.site.pages.bookedguest.BookedGuestLogonPage;
import com.framework.site.pages.core.CruisingPage;
import com.google.common.base.Optional;
import com.google.common.base.Splitter;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static com.framework.utils.datetime.TimeConstants.FIVE_SECONDS;
import static com.framework.utils.matchers.JMatchers.containsString;
import static com.framework.utils.matchers.JMatchers.is;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.site.objects.header
 *
 * Name   : HeaderLinksObject
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-01-06
 *
 * Time   : 11:34
 */

class HeaderLinksObject extends AbstractWebObject implements Header.HeaderLinks
{
	//region HeaderLinksObject - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( HeaderLinksObject.class );

	// ------------------------------------------------------------------------|
	// --- WEB-OBJECTS CACHING ------------------------------------------------|
	// ------------------------------------------------------------------------|

	private HtmlElement ul_pull_left, ul_pull_right;

	private HtmlElement data_ccl_flyout_learn, data_ccl_flyout_explore, data_ccl_flyout_manage, data_ccl_flyout_plan;

	private HtmlElement notification_flag, li_log, li_search;

	private HtmlElement ccl_header_expand_login_link, greeting, join;

	private HtmlElement glass_search;

	private List<HtmlElement> span_org;

	//endregion


	//region HeaderLinksObject - Constructor Methods Section

	HeaderLinksObject( final HtmlElement rootElement )
	{
		super( rootElement, Header.HeaderLinks.LOGICAL_NAME );
		initWebObject();
	}

	//endregion


	//region HeaderLinksObject - Initialization and Validation Methods Section

	@Override
	protected void initWebObject()
	{
		logger.info( "validating static elements for web object id: <{}>, name:<{}>...",
				getQualifier(), getLogicalName() );

		final String REASON = "assert that element \"%s\" exits";
		JAssertion assertion = getRoot().createAssertion();

		Optional<HtmlElement> e = getRoot().childExists( By.cssSelector( ".header-links ul.pull-right" ), FIVE_SECONDS );
		assertion.assertThat( String.format( REASON, ".header-links ul.pull-right" ), e.isPresent(), is( true ) );
		this.ul_pull_right = e.get();

		e = getRoot().childExists( By.cssSelector( ".header-links ul.pull-left" ), FIVE_SECONDS );
		assertion.assertThat( String.format( REASON, ".header-links ul.pull-left" ), e.isPresent(), is( true ) );
		this.ul_pull_left = e.get();
	}

	//endregion


	//region HeaderLinksObject - Service Methods Section

	private HtmlElement getRoot()
	{
		return getBaseRootElement( Header.HeaderLinks.ROOT_BY );
	}

	private List<String> getDataHighLightPattern( LevelOneMenuItem item )
	{
		String highLightPattern = findLevelOneMenuItemAnchor( item ).getAttribute( "data-highlightpattern" );
		return Splitter.on( "|" ).splitToList( highLightPattern );
	}

	//endregion


	//region HeaderLinksObject - Business Methods Section

	@Override
	public void hoverOnItem( final LevelOneMenuItem item )
	{
		logger.info( "hovering over level-one element item < '{}' >", item.getTitle() );

		Link link = new Link( findLevelOneMenuItemAnchor( item ) );
		link.hover( false );
		findLevelOneMenuItemAnchor( item ).waitAttributeToMatch( "class", containsString( " hover " ), FIVE_SECONDS );
	}

	@Override
	public BaseCarnivalPage clickItem( final LevelOneMenuItem item )
	{
		logger.info( "clicking on level-one element item < '{}' >", item.getTitle() );

		try
		{
			Link link = new Link( findLevelOneMenuItemAnchor( item ) );
			link.checkReference( 5000 );
			link.click();
			CruisingPage cp = new CruisingPage( );
			logger.info( "returning a new page instance -> '{}'", cp );
			return cp;
		}
		catch ( UrlNotAvailableException e )
		{
			throw new ApplicationException( e );
		}
	}

	@Override
	public Link getLink( final LevelOneMenuItem item )
	{
		return new Link( findLevelOneMenuItemAnchor( item ) );
	}

	@Override
	public String[] getLinkNames()
	{
		List<HtmlElement> spans = fidItemsOrgSpans();
		List<String> names = HtmlObject.extractAttribute( spans, "textContent" );
		logger.info( "returning a list of top-level links names [ {} ] ...", Lambda.join( names, ", " ) );
		return names.toArray( new String[ names.size() ] );
	}

	@Override
	public BookedGuestLogonPage clickLogin()
	{
		logger.info( "clicking on login ..." );
		Link login = new Link( findExpandLoginLinkAnchor() );
		login.hover( true );
		login.click();
		return new BookedGuestLogonPage();
	}

	@Override
	public String getGreeting()
	{
		logger.info( "reading greeting span element ..." );
		HtmlElement greetingSpan = findGreetingSpan();
		HtmlElement join = findJoinAnchor();
		return greetingSpan.getText() + join.getText();
	}

	@Override
	public Link getGreetingLink()
	{
		logger.info( "reading greeting link ..." );
		return new Link( findJoinAnchor() );
	}

	@Override
	public Link getLoginLink()
	{
		logger.info( "reading login link ..." );
		return new Link( findExpandLoginLinkAnchor() );
	}

	//endregion


	//region HeaderLinksObject - Element Finder Methods Section


	private HtmlElement findUlPullLeft()
	{
		final By findBy = By.cssSelector( ".header-links ul.pull-left" );
		if( null == this.ul_pull_left )
		{
			this.ul_pull_left = getDriver().findElement( findBy );
		}
		return this.ul_pull_left;
	}

	private HtmlElement findUlPullRight()
	{
		final By findBy = By.cssSelector( ".header-links ul.pull-right" );
		if( null == this.ul_pull_right )
		{
			this.ul_pull_right = getDriver().findElement( findBy );
		}

		return this.ul_pull_right;
	}

	private HtmlElement findLevelOneMenuItemAnchor( LevelOneMenuItem item )
	{
		final By findBy = By.cssSelector( String.format( "a[data-ccl-flyout='%s']", item.name().toLowerCase() ) );
		switch ( item )
		{
			case LEARN:
			{
				if( null == this.data_ccl_flyout_learn )
				{
					this.data_ccl_flyout_learn = findUlPullLeft().findElement( findBy );
				}
				return data_ccl_flyout_learn;
			}
			case PLAN:
			{
				if( null == this.data_ccl_flyout_plan )
				{
					this.data_ccl_flyout_plan = findUlPullLeft().findElement( findBy );
				}
				return data_ccl_flyout_plan;
			}
			case MANAGE:
			{
				if( null == this.data_ccl_flyout_manage )
				{
					this.data_ccl_flyout_manage = findUlPullLeft().findElement( findBy );
				}
				return data_ccl_flyout_manage;
			}
			case EXPLORE:
			{
				if( null == this.data_ccl_flyout_explore )
				{
					this.data_ccl_flyout_explore = findUlPullLeft().findElement( findBy );
				}
				return data_ccl_flyout_explore;
			}
			default:
				return null;
		}
	}

	private HtmlElement findNotificationFlag()
	{
		final By findBy = By.className( "notification-flag" );
		if( null == this.notification_flag )
		{
			this.notification_flag = findUlPullRight().findElement( findBy );
		}
		return this.notification_flag;
	}

	private HtmlElement findExpandLoginLinkAnchor()
	{
		final By findBy = By.id( "ccl_header_expand-login-link" );
		if( null == this.ccl_header_expand_login_link )
		{
			this.ccl_header_expand_login_link = getDriver().findElement( findBy );
		}
		return this.ccl_header_expand_login_link;
	}

	private HtmlElement findLogLi()
	{
		final By findBy = By.className( "log" );
		if( null == this.li_log )
		{
			this.li_log = findUlPullRight().findElement( findBy );
		}
		return this.li_log;
	}

	private HtmlElement findGreetingSpan()
	{
		final By findBy = By.id( "greeting" );
		if( null == this.greeting )
		{
			this.greeting = findUlPullRight().findElement( findBy );
		}
		return this.greeting;
	}

	private HtmlElement findJoinAnchor()
	{
		final By findBy = By.id( "join" );
		if( null == this.join )
		{
			this.join = findUlPullRight().findElement( findBy );
		}
		return this.join;
	}

	private HtmlElement findSearchLi()
	{
		final By findBy = By.className( "search" );
		if( null == this.li_search )
		{
			this.li_search = findUlPullRight().findElement( findBy );
		}
		return this.li_search;
	}

	private HtmlElement findGlassSearchAnchor()
	{
		final By findBy = By.id( "glass_search" );
		if( null == this.glass_search )
		{
			this.glass_search = getDriver().findElement( findBy );
		}
		return this.glass_search;
	}

	private List<HtmlElement> fidItemsOrgSpans()
	{
		final By findBy = By.className( "org" );
		if( null == this.span_org )
		{
			this.span_org = findUlPullLeft().findElements( findBy );
		}
		return this.span_org;
	}


	//endregion

}