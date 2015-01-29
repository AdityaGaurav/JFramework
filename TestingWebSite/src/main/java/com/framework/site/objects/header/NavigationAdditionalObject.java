package com.framework.site.objects.header;

import com.framework.asserts.JAssertion;
import com.framework.driver.exceptions.ApplicationException;
import com.framework.driver.objects.AbstractWebObject;
import com.framework.driver.objects.BaseElementObject;
import com.framework.driver.objects.Link;
import com.framework.driver.objects.PageObject;
import com.framework.driver.utils.ui.WaitUtil;
import com.framework.site.config.InitialPage;
import com.framework.site.config.SiteSessionManager;
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
import com.framework.utils.datetime.TimeConstants;
import com.framework.utils.error.PreConditions;
import com.framework.utils.matchers.JMatchers;
import com.framework.utils.spring.AppContextProxy;
import com.google.common.base.Throwables;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
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

	NavigationAdditionalObject( WebDriver driver, final WebElement rootElement )
	{
		super( driver, rootElement, LOGICAL_NAME );
	}

	//endregion


	//region NavigationAdditionalObject - Initialization and Validation Methods Section

	@Override
	protected void initWebObject()
	{
		logger.debug( "validating static elements for web object id: <{}>, name:<{}>...",
				getQualifier(), getLogicalName() );

		JAssertion assertion = new JAssertion( getWrappedDriver() );
		ExpectedCondition<WebElement> condition1 =
				WaitUtil.presenceBy( By.cssSelector( ".header-links ul.pull-left" ) );
		ExpectedCondition<WebElement> condition2 =
				WaitUtil.presenceBy( By.cssSelector( ".header-links ul.pull-right" ) );
		assertion.assertWaitThat(
				"Validate \".header-links ul.pull-left\" element exists", 5000, condition1 );
		assertion.assertWaitThat(
				"Validate \".header-links ul.pull-right\" element exists", 5000, condition2 );
	}

	//endregion


	//region NavigationAdditionalObject - Service Methods Section

	private WebElement getRoot()
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

	/**
	 * @param item a top-level menu item
	 *
	 * @return a list of {@code WebElement} children of the top-level menu item.
	 */
	public List<Link> getChildMenuItems( LevelOneMenuItem item )
	{
		logger.info( "Return all level-2 menu items from <'{}'> level-1 menu item", item.getTitle() );

		this.levelOne = item.getTitle().toLowerCase();
		List<WebElement> anchors = findChildFlyoutAnchors( item );

		// Waiting for all elements to be visible first */

		WaitUtil.wait10( getWrappedDriver() ).until( WaitUtil.visibilityOfAll( anchors, true ) );
		return BaseElementObject.convertToLink( anchors );
	}

	@Override
	public List<String> getChildMenuItemsNames( List<Link> links )
	{
		logger.info( "Extracting a list of stings from List<Link> of size {}", links.size() );
		return extract( links, on( Link.class ).getText() );
	}

	@Override
	public PageObject selectMenuItem( List<Link> links, final MenuItems menuItem )
	{
		logger.info( "Selecting menu item <'{}'>. link argument is -> Link.size = {}", menuItem.getTitle(), links.size() );

		try
		{
			Link link = hoverMenuItem( links, menuItem );
			Validate.notNull( link, "hoverMenuItem for item <{}> returned null.", menuItem.name() );
			logger.debug( "clicking on <'{}'> link", menuItem.getTitle() );
			link.click();
			switch( menuItem )
			{
				case WHY_CARNIVAL   	: return new CruisingPage( getWrappedDriver() );
				case WHATS_IT_LIKE  	: return new What2ExpectPage( getWrappedDriver() );
				case WHERE_CAN_I_GO 	: return new CruiseDestinationsAndPortsPage( getWrappedDriver() );
				case HOW_MUCH_IS_IT 	: return new CruiseCostPage( getWrappedDriver() );
				case HELP_ME_DECIDE 	: return new VacationPlannerPage( getWrappedDriver() );
				case CARIBBEAN			: return new UKCaribbeanPage( getWrappedDriver() );
				case WHATS_INCLUDED 	:
				case ON_THE_SHIP		:
				case DESTINATIONS		:
				{
					Locale locale = ( Locale ) InitialPage.getRuntimeProperties().getRuntimePropertyValue( "locale" );
					if( locale.equals( Locale.UK ) ) return new BeginnersGuidePage( getWrappedDriver() );
					if( locale.equals( Locale.US ) ) return new CruiseToPage( getWrappedDriver() );
				}
				case ONBOARD_ACTIVITIES : return new OnboardActivitiesPage( getWrappedDriver() );
				case DINING				: return new DiningPage( getWrappedDriver() );
				case ACCOMMODATIONS 	: return new StateRoomsPage( getWrappedDriver() );
				case OUR_SHIPS			: return new CruiseShipsPage( getWrappedDriver() );
				case MY_BOOKING			:
				case CHECK_IN			:
				case PLAN_ACTIVITIES	: return new BookedGuestLogonPage( getWrappedDriver() );
				case VIFP_CLUB			: return new VifpClubPage( getWrappedDriver() );
				case FIND_A_CRUISE		: return new FindACruisePage( getWrappedDriver() );
				case FIND_A_PORT		: return new CloseToHomePage( getWrappedDriver() );
				case FAQ_S				: return new FaqPage( getWrappedDriver() );
				case FORUMS				: return new ForumsPage( getWrappedDriver() );
				case SHORE_EXCURSIONS 	:
				{
					Locale locale = ( Locale ) InitialPage.getRuntimeProperties().getRuntimePropertyValue( "locale" );
					if( locale.equals( Locale.UK ) )
					{
						return new BeginnersGuidePage( getWrappedDriver() );
					}
					else if( locale.equals( Locale.US ) )
					{
						return new ActivitiesPage( getWrappedDriver() );
					}
				}
				case IN_ROOM_GIFTS_AND_SHOPPING: return new FunShopsPage( getWrappedDriver() );
				default:
				{
					throw new ApplicationException( getWrappedDriver(), "The menu item <'{" + menuItem.getTitle() + "}'> was not found"  );
				}
			}
		}
		catch ( Throwable e )
		{
			Throwables.propagateIfInstanceOf( e, ApplicationException.class );
			logger.error( "throwing a new ApplicationException on {}#selectMenuItem.", getClass().getSimpleName() );
			ApplicationException aex = new ApplicationException( getWrappedDriver().getWrappedDriver(), e.getMessage(), e );
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
		logger.info( "Hovering over level-2 menu item <'{}'>. argument links size = {}", menuItem.getTitle(), links.size() );

		final String ERR_MSG1 = "Header additional items are not displayed.";
		final String REASON = "Asserting that all < %d > level-2 links are visible ...";
		JAssertion assertion = new JAssertion( getWrappedDriver() );

		try
		{

			logger.info( String.format( REASON, links.size() ) );
			assertion.assertWaitThat(
					String.format( REASON, links.size() ),
					TimeConstants.FIFTY_HUNDRED_MILLIS,
					WaitUtil.visibilityOfAll( links, true ) );

			/* the additional header is displayed when div.header-flyouts.exposed and div.flyout.active.showUL */

			WebElement hdrFlyOuts = findHeaderFlyoutsDiv();
			ExpectedCondition<Boolean> ec1 = WaitUtil.elementAttributeToMatch( hdrFlyOuts, "class", containsString( "exposed" ) );
			WebElement flyOuts = findFlyoutsDiv();
			ExpectedCondition<Boolean> ec2 = WaitUtil.elementAttributeToMatch( flyOuts, "class", containsString( "active showUL" ) );

			boolean condition1 = WaitUtil.wait5( getWrappedDriver() ).until( WaitUtil.multiple( ec1, ec2 ) );
			PreConditions.checkState( condition1 && isDisplayed(), ERR_MSG1 );

			/* builds from ResourceBundleMessageSource the expected results for img[src] before and after hover ( src ) */

			logger.info( "Trying to get the current locale value from getRuntimeProperties ..." );
			Locale locale = SiteSessionManager.getInstance().getCurrentLocale();
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

			logger.debug( "selectFirst from <{}> links, text expression -> '{}", links.size(), menuItem.getTitle() );
			Link selected = selectFirst( links, having( on( Link.class ).getText(), equalToIgnoringCase( menuItem.getTitle() ) ) );
			Validate.notNull( selected, "selectFirst matching equalToIgnoringCase '" + menuItem.getTitle() + "' returned null." );

			/* reading link associated image and verifies 'img[src]' attribute value before hovering <#BEFORE_HOVER> */

			WebElement img = selected.getWrappedElement().findElement( By.tagName( "img" ) );
			final String REASON1 = "asserting that img[src] value before hovering contains < '%s' >";
			assertion.assertThat(
					String.format( REASON1, BEFORE_HOVER ),
					img.getAttribute( "src" ), JMatchers.containsString( BEFORE_HOVER ) );
			selected.hover();

			// Waiting that img[src] containsString #AFTER_HOVER
			final String REASON2 = "Assert-waiting ( at least 10 sec ) that img[src] value after hovering contains < '%s' >";
			assertion.assertWaitThat(
					String.format( REASON2, AFTER_HOVER ),
					TimeConstants.FIFTY_HUNDRED_MILLIS,
					WaitUtil.elementAttributeToMatch( img, "src", JMatchers.containsString( AFTER_HOVER ) ) );
			return selected;
		}
		catch ( Throwable e )
		{
			Throwables.propagateIfInstanceOf( e, ApplicationException.class );
			logger.error( "throwing a new ApplicationException on {}#hoverMenuItem.", getClass().getSimpleName() );
			ApplicationException aex = new ApplicationException( getWrappedDriver().getWrappedDriver(), e.getMessage(), e );
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

	private WebElement findGlassSearch()
	{
		By findBy = By.id( "glass_search" );
		return getWrappedDriver().findElement( findBy );
	}

	//endregion
}