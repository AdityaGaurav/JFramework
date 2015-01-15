package com.framework.site.objects.header;

import com.framework.asserts.JAssertions;
import com.framework.driver.exceptions.ApplicationException;
import com.framework.driver.exceptions.PreConditionException;
import com.framework.driver.objects.AbstractWebObject;
import com.framework.driver.objects.Link;
import com.framework.driver.objects.PageObject;
import com.framework.driver.utils.PreConditions;
import com.framework.driver.utils.ui.ListWebElementUtils;
import com.framework.driver.utils.ui.WaitUtil;
import com.framework.site.config.InitialPage;
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
import com.framework.utils.spring.AppContextProxy;
import com.google.common.base.MoreObjects;
import com.google.common.base.Throwables;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Locale;

import static ch.lambdaj.Lambda.*;
import static com.framework.matchers.MatcherUtils.containsString;
import static com.framework.matchers.MatcherUtils.equalToIgnoringCase;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.site.objects.header
 *
 * Name   : NavigationAdditionalObject
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-01-06
 *
 * Time   : 11:28
 */

class NavigationAdditionalObject extends AbstractWebObject implements Header.NavigationAdditional
{

	//region NavigationAdditionalObject - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( NavigationAdditionalObject.class );

	private String levelOne;

	//endregion


	//region NavigationAdditionalObject - Constructor Methods Section

	NavigationAdditionalObject( WebDriver driver,  final WebElement rootElement )
	{
		super( LOGICAL_NAME, driver, rootElement );
	}

	//endregion


	//region NavigationAdditionalObject - Initialization and Validation Methods Section

	@Override
	protected void initWebObject()
	{

	}

	//endregion


	//region NavigationAdditionalObject - Service Methods Section

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
		}
		catch ( StaleElementReferenceException ex )
		{
			logger.warn( "auto recovering from StaleElementReferenceException ..." );
			rootElement = objectDriver.findElement( Header.NavigationAdditional.ROOT_BY );
		}

		return rootElement;
	}

	//endregion


	//region NavigationAdditionalObject - Business Methods Section

	@Override
	public boolean isDisplayed()
	{
		return rootElement.isDisplayed();
	}

	/**
	 * @param item a top-level menu item
	 *
	 * @return a list of {@code WebElement} children of the top-level menu item.
	 */
	public List<Link> getChildMenuItems( LevelOneMenuItem item )
	{
		try
		{
			this.levelOne = item.getTitle().toLowerCase();
			List<WebElement> anchors = findChildFlyoutAnchors( item );
			return ListWebElementUtils.convertToLink( objectDriver, anchors );
		}
		catch ( Throwable ae )
		{
			Throwables.propagateIfInstanceOf( ae, ApplicationException.class );
			logger.error( "throwing a new ApplicationException on {}#getChildMenuItems.", getClass().getSimpleName() );
			ApplicationException aex = new ApplicationException( objectDriver.getWrappedDriver(), ae.getMessage(), ae );
			aex.addInfo( "business process", "failed to read child menu items of '" + item.getTitle() + "'" );
			throw aex;
		}
	}

	@Override
	public List<String> getChildMenuItemsNames( List<Link> links )
	{
		try
		{
			return extract( links, on( Link.class ).getText() );
		}
		catch ( Throwable ae )
		{
			Throwables.propagateIfInstanceOf( ae, ApplicationException.class );
			logger.error( "throwing a new ApplicationException on {}#getChildMenuItemsNames.", getClass().getSimpleName() );
			ApplicationException aex = new ApplicationException( objectDriver.getWrappedDriver(), ae.getMessage(), ae );
			aex.addInfo( "business process", "failed to extract link names." );
			throw aex;
		}
	}

	@Override
	public PageObject selectMenuItem( List<Link> links, final MenuItems menuItem )
	{
		try
		{
			Link link = hoverMenuItem( links, menuItem );
			Validate.notNull( link, "hoverMenuItem for item <{}> returned null.", menuItem.name() );
			logger.debug( "clicking on <'{}'> link", menuItem.getTitle() );
			link.click();
			switch( menuItem )
			{
				case WHY_CARNIVAL   	: return new CruisingPage( objectDriver );
				case WHATS_IT_LIKE  	: return new What2ExpectPage( objectDriver );
				case WHERE_CAN_I_GO 	: return new CruiseDestinationsAndPortsPage( objectDriver );
				case HOW_MUCH_IS_IT 	: return new CruiseCostPage( objectDriver );
				case HELP_ME_DECIDE 	: return new VacationPlannerPage( objectDriver );
				case CARIBBEAN			: return new UKCaribbeanPage( objectDriver );
				case WHATS_INCLUDED 	:
				case ON_THE_SHIP		:
				case DESTINATIONS		:
				{
					Locale locale = ( Locale ) InitialPage.getRuntimeProperties().getRuntimePropertyValue( "locale" );
					if( locale.equals( Locale.UK ) ) return new BeginnersGuidePage( objectDriver );
					if( locale.equals( Locale.US ) ) return new CruiseToPage( objectDriver );
				}
				case ONBOARD_ACTIVITIES : return new OnboardActivitiesPage( objectDriver );
				case DINING				: return new DiningPage( objectDriver );
				case ACCOMMODATIONS 	: return new StateRoomsPage( objectDriver );
				case OUR_SHIPS			: return new CruiseShipsPage( objectDriver );
				case MY_BOOKING			:
				case CHECK_IN			:
				case PLAN_ACTIVITIES	: return new BookedGuestLogonPage( objectDriver );
				case VIFP_CLUB			: return new VifpClubPage( objectDriver );
				case FIND_A_CRUISE		: return new FindACruisePage( objectDriver );
				case FIND_A_PORT		: return new CloseToHomePage( objectDriver );
				case FAQ_S				: return new FaqPage( objectDriver );
				case FORUMS				: return new ForumsPage( objectDriver );
				case SHORE_EXCURSIONS 	:
				{
					String region = objectDriver.getJavaScript().getString( Header.SITE_REGION_SCRIPT );
					if( region.equals( "UK" ) )
					{
						return new BeginnersGuidePage( objectDriver );
					}
					else if( region.equals( "US" ) )
					{
						return new ActivitiesPage( objectDriver );
					}
				}
				case IN_ROOM_GIFTS_AND_SHOPPING: return new FunShopsPage( objectDriver );
				default:
				{
					throw new ApplicationException( objectDriver, "The menu item <'{" + menuItem.getTitle() + "}'> was not found"  );
				}
			}
		}
		catch ( Throwable e )
		{
			Throwables.propagateIfInstanceOf( e, ApplicationException.class );
			logger.error( "throwing a new ApplicationException on {}#selectMenuItem.", getClass().getSimpleName() );
			ApplicationException aex = new ApplicationException( objectDriver.getWrappedDriver(), e.getMessage(), e );
			aex.addInfo( "business process", "failed to select( click ) menu item '" + menuItem.getTitle() + "'" );
			throw aex;
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
	 * @param links  	a collection of top-level {@link com.framework.driver.objects.Link} objects
	 * @param menuItem  a sub-menu item
	 *
	 * @return the hovered link
	 *
	 * @see com.framework.driver.objects.Link
	 * @see com.framework.driver.utils.ui.WaitUtil
	 * @see com.framework.utils.spring.AppContextProxy
	 * @see ch.lambdaj.Lambda#selectFirst(Object, org.hamcrest.Matcher)
	 * @see com.framework.driver.utils.ui.WaitUtil#elementsAttributeToMatch(java.util.List, String, org.hamcrest.Matcher)
	 *
	 */
	@Override
	public Link hoverMenuItem( final List<Link> links, final MenuItems menuItem )
	{
		final String ERR_MSG1 = "Header additional items are not displayed.";
		WebDriverWait wait5 = WaitUtil.wait5( objectDriver );

		try
		{
			// if ( menuItemHaveException( menuItem ) ) return null;  // exception on locale + menu item name

			List<WebElement> elements = ListWebElementUtils.extractWebElement( links );
			JAssertions.assertWaitThat( wait5 ).matchesCondition( WaitUtil.visibilityOfAll( elements, true ) );

			/* the additional header is displayed when div.header-flyouts.exposed and div.flyout.active.showUL */

			WebElement hdrFlyOuts = findHeaderFlyoutsDiv();
			ExpectedCondition<Boolean> ec1 = WaitUtil.elementAttributeToMatch( hdrFlyOuts, "class", containsString( "exposed" ) );
			WebElement flyOuts = findFlyoutsDiv();
			ExpectedCondition<Boolean> ec2 = WaitUtil.elementAttributeToMatch( flyOuts, "class", containsString( "active showUL" ) );

			boolean condition1 = wait5.until( WaitUtil.multiple( ec1, ec2 ) );
			PreConditions.checkState( condition1 && isDisplayed(), ERR_MSG1 );

			/* builds from ResourceBundleMessageSource the expected results for img[src] before and after hover ( src ) */

			Locale locale = ( Locale ) InitialPage.getRuntimeProperties().getRuntimePropertyValue( "locale" );
			String key = menuItem.getBundleKey( menuItem );
			logger.debug( "key for menu-item <\"{}\"> is <\"{}\">", menuItem.getTitle(), key );

			final String BEFORE_HOVER =
					( String ) AppContextProxy.getInstance().getMessage( key, new Object[] { StringUtils.EMPTY }, locale );

			/* particular case on MenuItems.WHY_CARNIVAL on US and MenuItems.WHY_CARNIVAL on UK img[src] */

			final String hoverTag = menuItem.equals( MenuItems.WHY_CARNIVAL ) || menuItem.equals( MenuItems.WHATS_INCLUDED )
					? "_hover"
					: "-hover";

			final String AFTER_HOVER = ( String ) AppContextProxy.getInstance().getMessage( key, new Object[] { hoverTag }, locale );

			logger.debug( "locating link matching text -> <'{}'>", menuItem );

			/* filtering requested link */

			logger.debug( "selectFirst from <{}> links for text expression -> <{}>", links.size(), menuItem.getTitle() );
			Link selected = selectFirst( links, having( on( Link.class ).getText(), equalToIgnoringCase( menuItem.getTitle() ) ) );
			Validate.notNull( selected, "selectFirst matching equalToIgnoringCase '" + menuItem.getTitle() + "' returned null." );

			/* reading link associated image and verifies 'img[src]' attribute value before hovering <#BEFORE_HOVER> */

			WebElement img = selected.getWrappedElement().findElement( By.tagName( "img" ) );
			JAssertions.assertThat( img ).matchesAttributeValue( "src", containsString( BEFORE_HOVER ) );

			/* actually hovering over image */

			selected.hover();

			/* Waiting that img[src] containsString #AFTER_HOVER */

			logger.trace( "waiting tha img[src] contains -> <{}>", AFTER_HOVER );
			WaitUtil.wait10( objectDriver ).until( WaitUtil.elementAttributeToMatch( img, "src", containsString( AFTER_HOVER ) ) );
			return selected;
		}
		catch ( IllegalStateException e )
		{
			logger.error( "throwing a new PreConditionException on {}#hoverMenuItem.", getClass().getSimpleName() );
			throw new PreConditionException( e.getMessage(), e );
		}
		catch ( Throwable e )
		{
			Throwables.propagateIfInstanceOf( e, ApplicationException.class );
			logger.error( "throwing a new ApplicationException on {}#hoverMenuItem.", getClass().getSimpleName() );
			ApplicationException aex = new ApplicationException( objectDriver.getWrappedDriver(), e.getMessage(), e );
			aex.addInfo( "business process", "failed to hover over menu item '" + menuItem.getTitle() + "'" );
			throw aex;
		}
	}

	//endregion


	//region NavigationAdditionalObject - Element Finder Methods Section

	private List<WebElement> findChildFlyoutAnchors( LevelOneMenuItem item )
	{
		final String XPATH_PATTERN = ".//div[@data-ccl-flyout='%s']//a";
		By findBy = By.xpath( String.format( XPATH_PATTERN, item.getTitle().toLowerCase() ) );
		return getRoot().findElements( findBy );
	}

	private WebElement findHeaderFlyoutsDiv()
	{
		By findBy = By.className( "header-flyouts" );
		return getRoot().findElement( findBy );
	}

	private WebElement findFlyoutsDiv()
	{
		By findBy = By.cssSelector( String.format( ".flyout[data-ccl-flyout='%s']", this.levelOne ) );
		return getRoot().findElement( findBy );
	}

	//endregion


	//region NavigationAdditionalObject - Private Functions Section

//	private boolean menuItemHaveException( MenuItems menuItem )
//	{
//		Locale locale = ( Locale ) InitialPage.getRuntimeProperties().getRuntimePropertyValue( "locale" );
//		return menuItem.equals( MenuItems.DESTINATIONS0 ) && ( ! locale.equals( Locale.UK ) );
//	}

	//endregion

}