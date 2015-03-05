package com.framework.site.objects.header;

import ch.lambdaj.Lambda;
import com.framework.asserts.JAssertion;
import com.framework.driver.event.HtmlElement;
import com.framework.driver.event.HtmlObject;
import com.framework.driver.exceptions.ApplicationException;
import com.framework.driver.objects.AbstractWebObject;
import com.framework.driver.objects.Link;
import com.framework.driver.objects.PageObject;
import com.framework.site.config.SiteSessionManager;
import com.framework.site.exceptions.NoSuchMenuItemException;
import com.framework.site.objects.header.enums.LevelOneMenuItem;
import com.framework.site.objects.header.enums.MenuItems;
import com.framework.site.objects.header.interfaces.Header;
import com.framework.site.pages.activities.ActivitiesPage;
import com.framework.site.pages.bookedguest.BookedGuestLogonPage;
import com.framework.site.pages.bookingengine.FindACruisePage;
import com.framework.site.pages.core.*;
import com.framework.site.pages.core.cruiseto.CruiseToPage;
import com.framework.site.pages.dining.DiningPage;
import com.framework.site.pages.funshops.FunShopsPage;
import com.framework.site.pages.funville.ForumsPage;
import com.framework.site.pages.loyalty.VifpClubPage;
import com.framework.utils.error.PreConditions;
import com.google.common.base.Optional;
import org.apache.commons.io.FilenameUtils;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Locale;

import static com.framework.utils.datetime.TimeConstants.FIVE_SECONDS;
import static com.framework.utils.datetime.TimeConstants.THREE_SECONDS;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.is;


class NavigationAdditionalObject extends AbstractWebObject implements Header.NavigationAdditional
{

	//region NavigationAdditionalObject - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( NavigationAdditionalObject.class );

	// ------------------------------------------------------------------------|
	// --- WEB-OBJECTS CACHING ------------------------------------------------|
	// ------------------------------------------------------------------------|

	private HtmlElement flyouts;

	private HtmlElement data_ccl_flyout_learn, data_ccl_flyout_explore;

	private HtmlElement data_ccl_flyout_plan, data_ccl_flyout_manage, data_ccl_flyout_search;

	private List<HtmlElement> learns, explores, plans, manages, popular_searches;

	private HtmlElement search_box, popular_box;

	private HtmlElement ccl_header_site_search, ccl_header_site_search_submit;

	//endregion


	//region NavigationAdditionalObject - Constructor Methods Section

	NavigationAdditionalObject( final HtmlElement rootElement )
	{
		super( rootElement, LOGICAL_NAME );
		initWebObject();
	}

	//endregion


	//region NavigationAdditionalObject - Initialization and Validation Methods Section

	@Override
	protected void initWebObject()
	{
		logger.info( "validating static elements for web object id: < {} >, name:< {} >...",
				getQualifier(), getLogicalName() );

		final String REASON = "assert that element \"%s\" exits";
		final String REASON1 = "assert element list size \"%s\"";

		JAssertion assertion = getRoot().createAssertion();

		Optional<List<HtmlElement>> e = getRoot().allChildrenExists( By.cssSelector( "div.flyout" ), FIVE_SECONDS );
		assertion.assertThat( String.format( REASON, "div.flyout" ), e.isPresent(), is( true ) );
		assertion.assertThat( String.format( REASON1, "div.flyout" ), e.get().size(), is( 5 ) );
		for( HtmlElement he : e.get() )
		{
			String attribute = he.getAttribute( "data-ccl-flyout" );
			switch ( attribute )
			{
				case "learn":
					data_ccl_flyout_learn = he;
					break;
				case "explore":
					data_ccl_flyout_explore = he;
					break;
				case "plan":
					data_ccl_flyout_plan = he;
					break;
				case "manage":
					data_ccl_flyout_manage = he;
					break;
				case "search":
					data_ccl_flyout_search = he;
					break;
			}
		}
	}

	//endregion


	//region NavigationAdditionalObject - Service Methods Section

	private HtmlElement getRoot()
	{
		return getBaseRootElement( Header.NavigationAdditional.ROOT_BY );
	}

	//endregion


	//region NavigationAdditionalObject - Business Methods Section

	@Override
	public boolean isDisplayed()
	{
		return getRoot().isDisplayed();
	}

	@Override
	public PageObject clickOnMenuItem( final LevelOneMenuItem level1, final MenuItems level2 )
	{
		PreConditions.checkState( isDisplayed(), "The NaNavigationAdditional section is not displayed." );

		logger.info( "Clicking on menu item < '{}';'{}' >", level1.getTitle(), level2.getTitle() );
		Link link = new Link( findMenuItemAnchor( level1, level2 ) );
		return clickOnMenuItem( link, level2 );
	}

	@Override
	public PageObject clickOnMenuItem( final Link link, final MenuItems level2 )
	{

		logger.info( "clicking on menu item < {} >", level2.name() );
		link.click();
		Locale locale = SiteSessionManager.get().getCurrentLocale();

		switch( level2 )
		{
			case WHY_CARNIVAL		:	return new CruisingPage();
			case WHATS_IT_LIKE  	: 	return new What2ExpectPage();
			case WHERE_CAN_I_GO 	:	return new CruiseDestinationsAndPortsPage();
			case HOW_MUCH_IS_IT 	: 	return new CruiseCostPage();
			case HELP_ME_DECIDE 	: 	return new VacationPlannerPage();
			case DESTINATIONS		:
			{
				if( locale.equals( Locale.UK ) )
				{
					logger.info( "current locale is < {} > returning new instance of BeginnersGuidePage", locale.getDisplayCountry() );
					return new BeginnersGuidePage();
				}
				if( locale.equals( Locale.US ) )
				{
					logger.info( "current locale is < {} > returning new instance of CruiseToPage", locale.getDisplayCountry() );
					return new CruiseToPage();
				}
			}
			case FIND_A_CRUISE		:   return new FindACruisePage();
			case FIND_A_PORT		: 	return new CloseToHomePage();
			case FAQ_S				: 	return new FaqPage();
			case FORUMS				: 	return new ForumsPage();
			case MY_BOOKING			:
			case CHECK_IN			:
			case PLAN_ACTIVITIES	: 	return new BookedGuestLogonPage();
			case VIFP_CLUB			: 	return new VifpClubPage();
			case ONBOARD_ACTIVITIES : 	return new OnboardActivitiesPage();
			case DINING				: 	return new DiningPage();
			case ACCOMMODATIONS 	: 	return new StateRoomsPage();
			case OUR_SHIPS          :
			{
				if( locale.equals( HomePage.AU ) )
				{
					logger.info( "current locale is < {} > returning new instance of CruiseToPage", locale.getDisplayCountry() );
					return new BaseCruiseShipsPage();
				}
				logger.info( "current locale is < {} > returning new instance of CruiseShipsPage", locale.getDisplayCountry() );
				return new CruiseShipsPage();
			}
			case CARIBBEAN			: 	return new UKCaribbeanPage();
			case WHATS_INCLUDED 	:
			case ON_THE_SHIP		:	return new BeginnersGuidePage();
			case SHORE_EXCURSIONS 	:
			{
				if( locale.equals( Locale.UK ) )
				{
					logger.info( "current locale is < {} > returning new instance of BeginnersGuidePage", locale.getDisplayCountry() );
					return new BeginnersGuidePage();
				}
				else if( locale.equals( Locale.US ) )
				{
					logger.info( "current locale is < {} > returning new instance of ActivitiesPage", locale.getDisplayCountry() );
					return new ActivitiesPage();
				}
			}
			case IN_ROOM_GIFTS_AND_SHOPPING : return new FunShopsPage();
			default:
			{
				throw new NoSuchMenuItemException( getDriver(), "The menu item <'{" + level2.getTitle() + "}'> was not found"  );
			}
		}
	}

	/**
	 * Hovers over the {@code menuItem} link, provided by {@code links}
	 * procedure:
	 * <ul>
	 * 	   <li>Waiting for 3 conditions
	 *        <ul>
	 *            <li>1. div.flyout.exposed </li>
	 *            <li>2. div.flyout.active.showUL</li>
	 *            <li>3. div.header-nav-additional is displayed</li>
	 *        </ul>
	 *     </li>
	 *     <li>
	 *         select the first link that matches the requested item-menu
	 *     </li>
	 *     <li>
	 *     		validates that image src before hover as expected ( bundle value )
	 *     </li>
	 *     <li>
	 *         hovering over image
	 *     </li>
	 *     <li>
	 *         validates that image src after hover as expected ( bundle value )
	 *     </li>
	 * </ul>
	 *
	 * @param level1  	a level-1 menu item
	 * @param level2  	a sub-menu item
	 *
	 * @return the hovered link
	 *
	 * @see com.framework.driver.objects.Link
	 * @see com.framework.utils.spring.AppContextProxy
	 * @see ch.lambdaj.Lambda#selectFirst(Object, org.hamcrest.Matcher)
	 *
	 */
	@Override
	public Link hoverOnMenuItem( final LevelOneMenuItem level1, final MenuItems level2 )
	{
		logger.info( "Hovering over menu item < '{}';'{}' >", level1.getTitle(), level2.getTitle() );

		PreConditions.checkState( isDisplayed(), "The NaNavigationAdditional section is not displayed." );

		findFlyoutsDiv().waitAttributeToMatch( "class", endsWith( "exposed" ), THREE_SECONDS );
		Link menuItemL2 = new Link( findMenuItemAnchor( level1, level2 ) );
		menuItemL2.hover( false );

		return menuItemL2;
	}

	@Override
	public void hoverOnMenuItem( final Link link )
	{
		PreConditions.checkState( isDisplayed(), "The NaNavigationAdditional section is not displayed." );
		link.hover( false );
	}

	@Override
	public HtmlElement getImage( final Link link )
	{
		HtmlElement img = link.getHtmlElement().findElement( By.tagName( "img" ) );
		logger.info( "returning img object with src < {} >", img.getAttribute( "src" ) );
		return img;
	}

	@Override
	public Link getLink( final LevelOneMenuItem level1, final MenuItems level2 )
	{
		return new Link( findMenuItemAnchor( level1, level2 ) );
	}

	@Override
	public String getDataDefaultImg( final Link link )
	{
		HtmlElement img = findMenuItemImg( link );
		String value = img.getAttribute( "data-default" );
		String def = FilenameUtils.getName( value );
		logger.info( "Returning data default for img < {} >", def );
		return def;
	}

	@Override
	public String getDataHoverImg( final Link link )
	{
		HtmlElement img = findMenuItemImg( link );
		String value = img.getAttribute( "data-hover" );
		String hover = FilenameUtils.getName( value );
		logger.info( "Returning data hover for img < {} >", hover );
		return hover;
	}

	@Override
	public List<String> getChildMenuItemsNames( LevelOneMenuItem level1 )
	{
		List<HtmlElement> elements = findMenuItemsAnchors( level1 );
		List<String> list = HtmlObject.extractAttribute( elements, "textContent" );
		logger.info( "return a list of child-menu items names < {} >", Lambda.join( list, ", " ) );
		return list;
	}

	//endregion


	//region NavigationAdditionalObject - Element Finder Methods Section

	private HtmlElement findDataCclFlyoutLevelOneItem( LevelOneMenuItem item )
	{
		final By findBy = By.cssSelector( String.format( "div.flyout[data-ccl-flyout=\"%s\"]", item.name().toLowerCase() ) );
		switch ( item )
		{
			case LEARN:
			{
				if( null == data_ccl_flyout_learn )
				{
					data_ccl_flyout_learn = getRoot().findElement( findBy );
				}
				return data_ccl_flyout_learn;
			}
			case EXPLORE:
			{
				if( null == data_ccl_flyout_explore )
				{
					data_ccl_flyout_explore = getRoot().findElement( findBy );
				}
				return data_ccl_flyout_explore;
			}
			case PLAN:
			{
				if( null == data_ccl_flyout_plan )
				{
					data_ccl_flyout_plan = getRoot().findElement( findBy );
				}
				return data_ccl_flyout_plan;
			}
			case MANAGE:
			{
				if( null == data_ccl_flyout_manage )
				{
					data_ccl_flyout_manage = getRoot().findElement( findBy );
				}
				return data_ccl_flyout_manage;
			}
			default:
			{
				if( null == data_ccl_flyout_search )
				{
					data_ccl_flyout_search = getRoot().findElement( findBy );
				}
				return data_ccl_flyout_search;
			}
		}
	}

	private List<HtmlElement> findMenuItemsAnchors( LevelOneMenuItem item )
	{
		final By findBy = By.tagName( "a" );
		switch ( item )
		{
			case LEARN:
			{
				if( null == learns ) learns = findDataCclFlyoutLevelOneItem( item ).findElements( findBy );
				return learns;
			}
			case EXPLORE:
			{
				if( null == explores ) explores = findDataCclFlyoutLevelOneItem( item ).findElements( findBy );
				return explores;
			}
			case PLAN:
			{
				if( null == plans ) plans = findDataCclFlyoutLevelOneItem( item ).findElements( findBy );
				return plans;
			}
			case MANAGE:
			{
				if( null == manages ) manages = findDataCclFlyoutLevelOneItem( item ).findElements( findBy );
				return manages;
			}
			default:
				throw new ApplicationException( new IllegalArgumentException( "Invalid menu item " + item.getTitle() ) );
		}
	}

	private HtmlElement findSearchBoxLi()
	{
		final By findBy = By.className( "search-box" );
		if( null == search_box )
		{
			search_box = findDataCclFlyoutLevelOneItem( LevelOneMenuItem.SEARCH ).findElement( findBy );
		}
		return search_box;
	}

	private HtmlElement findPopularBoxLi()
	{
		final By findBy = By.className( "popular-box" );
		if( null == popular_box )
		{
			popular_box = findDataCclFlyoutLevelOneItem( LevelOneMenuItem.SEARCH ).findElement( findBy );
		}
		return popular_box;
	}

	private HtmlElement findCclHeaderSiteSearch()
	{
		final By findBy = By.id( "ccl_header_site-search" );
		if( null == ccl_header_site_search )
		{
			ccl_header_site_search = findSearchBoxLi().findElement( findBy );
		}
		return ccl_header_site_search;
	}

	private HtmlElement findCclHeaderSiteSearchSubmit()
	{
		final By findBy = By.id( "ccl_header_site-search-submit" );
		if( null == ccl_header_site_search_submit )
		{
			ccl_header_site_search_submit = findSearchBoxLi().findElement( findBy );
		}
		return ccl_header_site_search_submit;
	}

	private List<HtmlElement> findPopularSearchesAnchors()
	{
		final By findBy = By.tagName( "a" );
		if( null == popular_searches )
		{
			popular_searches = findPopularBoxLi().findElements( findBy );
		}
		return popular_searches;
	}

	private HtmlElement findMenuItemAnchor( LevelOneMenuItem menuItem, MenuItems item )
	{
		final By findBy = By.xpath( String.format( ".//span[@class=\"title\" and normalize-space(.)=\"%s\"]/..", item.getTitle() ) );
		return findDataCclFlyoutLevelOneItem( menuItem ).findElement( findBy );
	}

	private HtmlElement findMenuItemImg( Link link )
	{
		return link.getHtmlElement().findElement( By.tagName( "img" ) );
	}

	private HtmlElement findMenuItemTitleSpan( Link link )
	{
		return link.getHtmlElement().findElement( By.cssSelector( "span.title" ) );
	}

	private HtmlElement findFlyoutsDiv()
	{
		final By findBy = By.className( "header-flyouts" );
		if( null == flyouts )
		{
			flyouts = getRoot().findElement( findBy );
		}
		return flyouts;
	}

	//endregion
}