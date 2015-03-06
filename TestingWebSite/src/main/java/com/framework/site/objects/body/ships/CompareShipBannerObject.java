package com.framework.site.objects.body.ships;

//todo: class documentation

import com.framework.asserts.JAssertion;
import com.framework.driver.event.HtmlElement;
import com.framework.driver.objects.AbstractWebObject;
import com.framework.site.config.SiteProperty;
import com.framework.site.data.Ships;
import com.framework.site.objects.body.interfaces.CompareShipBanner;
import com.framework.site.pages.core.cruiseships.CompareCruiseShipsPage;
import com.framework.utils.datetime.TimeConstants;
import com.framework.utils.error.PreConditions;
import com.framework.utils.matchers.JMatchers;
import com.google.common.base.Optional;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.commons.lang3.BooleanUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.is;


public class CompareShipBannerObject extends AbstractWebObject implements CompareShipBanner
{

	//region CompareBannerObject - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( CompareShipBannerObject.class );

	private JsonObject sessionStorageCompare;

	private boolean webStorageEnabled = false;

	// ------------------------------------------------------------------------|
	// --- WEB-OBJECTS DEFINITIONS --------------------------------------------|
	// ------------------------------------------------------------------------|

	private HtmlElement compare_banner = null;

	//endregion


	//region CompareBannerObject - Constructor Methods Section

	/**
	 * Creates a new instance of the Compare ship banner section.
	 *
	 * @param rootElement   the ship card root element.
	 */
	public CompareShipBannerObject( final HtmlElement rootElement )
	{
		super( rootElement, CompareShipBanner.LOGICAL_NAME );
		initWebObject();
	}

	//endregion


	//region CompareBannerObject - Initialization and Validation Methods Section

	/**
	 * initialize and validates the ship compare banner object.
	 * This method is called by {@linkplain com.framework.driver.objects.AbstractWebObject} base class.
	 * <p>
	 *     Procedure Steps:
	 *     <ol>
	 *     </ol>
	 * </p>
	 */
	@Override
	protected void initWebObject()
	{
		logger.debug( "validating static elements for web object id: <{}>, name:<{}>...",
				getQualifier(), getLogicalName() );

		String REASON = "assert that element \"%s\" exits";

		JAssertion assertion = getRoot().createAssertion();

		Optional<HtmlElement> optional = getRoot().childExists( CompareShipBanner.ROOT_BY, TimeConstants.THREE_SECONDS );
		assertion.assertThat( String.format( REASON, "compare-banner" ), optional.isPresent(), is( true ) );
		this.compare_banner = optional.get();

		boolean compareCapability = BooleanUtils.toBoolean( SiteProperty.COMPARE_CAPABILITY.fromContext().toString() );
		if( compareCapability )
		{
			HtmlElement he = findBannerH3();
			REASON = "Validate that compare capability banner is visible";
			assertion.assertThat( REASON, he.isDisplayed(), JMatchers.is( true ) );
		}

		this.webStorageEnabled = BooleanUtils.toBoolean( getDriver().getCapabilities().getCapability( "webStorageEnabled" ).toString() );
		Cookie ssQ = getDriver().manage().getCookieNamed( COOKIE_NAME );
		if( null != ssQ && this.webStorageEnabled )
		{
			boolean isPresent = getDriver().sessionStorage().isItemPresentInSessionStorage( SESSION_STORAGE_COMPARE );
			if( isPresent )
			{
				String data = getDriver().sessionStorage().getItemFromSessionStorage( SESSION_STORAGE_COMPARE );
				JsonParser jsonParser = new JsonParser();
				sessionStorageCompare = ( JsonObject ) jsonParser.parse( data );
			}
		}
	}

	//endregion


	//region CompareBannerObject - Service Methods Section

	private HtmlElement getRoot()
	{
		return getBaseRootElement( CompareShipBanner.ROOT_BY );
	}

	//endregion


	//region CompareBannerObject - Implementation Methods Section

	@Override
	public Ships getShip( final int index )
	{
		return null;
	}

	@Override
	public Ships remove( final int index )
	{
		return null;
	}

	@Override
	public void remove( final Ships ship )
	{

	}

	@Override
	public List<Ships> getShips()
	{
		return null;
	}

	@Override
	public List<String> getShipNames()
	{
		return null;
	}

	@Override
	public Map<String, String> getCompareShipInfo( final Ships ship )
	{
		return null;
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 *     determine if the banner is visible by querying the div.banner web-element
	 * </p>
	 *
	 * @return {@inheritDoc}
	 */
	@Override
	public boolean isVisible()
	{
		return getRoot().isDisplayed();
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 *     determine if the banner has items by querying the div.banner.has-items web-element
	 * </p>
	 *
	 * @return {@inheritDoc}
	 */
	@Override
	public boolean hasItems()
	{
		Optional<HtmlElement> optional = getRoot().childExists( By.cssSelector( "div.banner" ) );
		return optional.isPresent();
	}

	@Override
	public boolean hasItems( final long millis )
	{
		Optional<HtmlElement> optional = getRoot().childExists( By.cssSelector( "div.banner" ), millis );
		return ! optional.isPresent();
	}

	@Override
	public List<HtmlElement> getItems()
	{
		return findCurrentlyComparing();
	}

	@Override
	public void cleanSessionStorage()
	{
		PreConditions.checkState( this.webStorageEnabled, "Driver Capabilities for webStorageEnabled is false." );
		if( null != this.sessionStorageCompare )
		{
			boolean a = sessionStorageCompare.isJsonArray();
			boolean b = sessionStorageCompare.isJsonNull();
			//getDriver().sessionStorage().removeItemFromSessionStorage( SESSION_STORAGE_COMPARE );
		}
	}

	@Override
	public JsonObject getSessionStorageKey( final String key )
	{
		PreConditions.checkState( webStorageEnabled, "Driver Capabilities for webStorageEnabled is false." );

		String value = getDriver().sessionStorage().getItemFromSessionStorage( key );
		JsonParser jsonParser = new JsonParser();
		return ( JsonObject ) jsonParser.parse( value );
	}

	@Override
	public CompareCruiseShipsPage clickCompareShips( int ships )
	{
		HtmlElement he = findCompareBannerButton();
		he.click();
		CompareCruiseShipsPage.forShips( ships );
		return new CompareCruiseShipsPage();
	}

	//endregion

//		JAssertion assertion = new JAssertion( getDriver() );
//		ExpectedCondition<Boolean> condition1 =
//				WaitUtil.not( WaitUtil.presenceBy( By.cssSelector( ".banner.has-items" ) ) );
//
//		Cookie ssQ = getDriver().manage().getCookieNamed( COOKIE_NAME );
//		if( null != ssQ )
//		{
//			Set<String> keys = getDriver().sessionStorage().getSessionStorageKeys();
//			logger.info( "Session storage keys -> < '{}' >", Joiner.on(",").join( keys ) );
//			String storage = getDriver().sessionStorage().getItemFromSessionStorage( SESSION_STORAGE_KEY );
//			logger.info( "session storage have item -> < '{}' >", storage );
//			logger.info( "Cleaning cookie and session storage" );
//			getDriver().manage().deleteCookie( ssQ );
//			getDriver().sessionStorage().clearSessionStorage();
//		}
//	}
//
//
//	//endregion
//
//
//	//region CompareBannerObject - Service Methods Section
//
//

//
//	private Ships getCompareShip( WebElement anchor )
//	{
//		String dataId = anchor.getAttribute( "data-id" );
//		Ships ship = Ships.valueById( dataId );
//		if( null == ship )
//		{
//			throw new NoSuchShipException( getDriver(), "The ship id <\"" + dataId + "\"> does not exists" );
//		}
//
//		return ship;
//	}
//
//
//	private void removeInternal( WebElement removeAnchor, int currentSize, Ships removeShip )
//	{
//		final int EXPECTED_SHIPS = currentSize - 1;
//		final String EXPECTED_SHIP_NOT_IN_LIST = removeShip.getId();
//		final boolean EXPECTED_BANNER_DISPLAYED = EXPECTED_SHIPS == 0;
//
//		removeAnchor.click();
//		List<WebElement> anchorsAfter = findRemoveShipAnchors();
//		logger.info( "validating that ships have one less item than <{}>", Integer.valueOf( EXPECTED_SHIPS + 1 ) );
//		//JAssertions.assertThat( anchorsAfter.size() ).isEqualTo( EXPECTED_SHIPS );
//		logger.info( "validating compare banner to be displayed/hidden.." );
//		//JAssertions.assertThat( isVisible() ).isEqualTo( EXPECTED_BANNER_DISPLAYED );
//
//		/* Validating that removed ship is not in banner, if the banner remains displayed */
//
//		if( EXPECTED_BANNER_DISPLAYED )
//		{
//			List<String> shipsIds = BaseElementObject.extractAttribute( anchorsAfter, "data-id" );
//			//JAssertions.assertThat( shipsIds ).doesNotContain( EXPECTED_SHIP_NOT_IN_LIST );
//		}
//	}
//
//	//endregion
//
//
//	//region CompareBannerObject - Business Methods Section
//

//
//	@Override
//	public CompareCruiseShipsPage doCompareShips()
//	{
////		final String ERR_MSG = "The compare banner is not visible.";
////
////		try
////		{
////			PreConditions.checkState( isVisible(), ERR_MSG );
////
////			/* finds the anchor button 'Compare Ships' and click it */
////
////			WebElement blueCta = findCompareShipsAnchor();
////			blueCta.click();
////			return new CompareCruiseShipsPage( objectDriver );
////		}
////		catch( IndexOutOfBoundsException | IllegalStateException ex )
////		{
////			logger.error( "throwing a new PreConditionException on {}#doCompareShips.", getClass().getSimpleName() );
////			throw new PreConditionException( ex.getMessage(), ex );
////		}
////		catch ( Throwable e )
////		{
////			Throwables.propagateIfInstanceOf( e, ApplicationException.class );
////			logger.error( "throwing a new ApplicationException on {}#doCompareShips.", getClass().getSimpleName() );
////			ApplicationException aex = new ApplicationException( objectDriver.getDriver(), e.getMessage(), e );
////			aex.addInfo( "business process", "failed to click on banner's 'Compare Ships' button." );
////			throw aex;
////		}
//
//		return null;
//	}
//
//	/**
//	 * {@inheritDoc}
//	 * <p>
//	 *     Procedure Steps:
//	 *     <ol>
//	 *        <li>validates that banner is visible</li>
//	 *        <li>
//	 *             validates that the requested index is valid
//	 *            {@link com.framework.utils.error.PreConditions#checkPositionIndex(int, int, String)}
//	 *        </li>
//	 *        <li>gets the link at index: argument {@code index}</li>
//	 *     	  <li>validates that the ship exists ( by ship id )</li>
//	 *     </ol>
//	 * </p>
//	 * @param index the index of the ship to retrieve
//	 *
//	 * @return
//	 */
//	@Override
//	public Ships getShipName( final int index )
//	{
//		final String ERR_MSG1 = "The compare banner is not visible.";
//		final String ERR_MSG2 = "The requested ship index is out of bound.";
//
//		try
//		{
//			/* validating that banner is visible */
//
//			PreConditions.checkState( isVisible(), ERR_MSG1 );
//
//			/* validating requested index is not out of bound  */
//
//			List<WebElement> anchors = findRemoveShipAnchors();
//			logger.debug( "found <{}> ships in compare ships banner." );
//			PreConditions.checkPositionIndex( index, anchors.size(), ERR_MSG2 );
//
//			/* get index and return a ship item  */
//
//			return getCompareShip( anchors.get( index ) );
//		}
//		catch ( Throwable t )
//		{
//			Throwables.propagateIfInstanceOf( t, ApplicationException.class );
//			logger.error( "throwing a new ApplicationException on {}#getShipName(int).", getClass().getSimpleName() );
//			ApplicationException appEx = new ApplicationException( getDriver().getDriver(), t.getMessage(), t );
//			appEx.addInfo( "business process", "failed to get ship index <" + index + ">");
//			throw appEx;
//		}
//	}
//
//	/**
//	 * {@inheritDoc}
//	 * <p>
//	 *     Procedure Steps:
//	 *     <ol>
//	 *        <li>validates that banner is visible</li>
//	 *        <li>finds a list of h4 elements and gets the text</li>
//	 *        <li>
//	 *            extracting the text using
//	 *           {@linkplain ListWebElementUtils#extractElementsText(java.util.List)}
//	 *        </li>
//	 *     </ol>
//	 * </p>
//	 *
//	 * @return {@inheritDoc}
//	 *
//	 * @see com.framework.driver.objects.ListWebElementUtils#extractElementsText(java.util.List)
//	 */
//	@Override
//	public List<String> getShipNames()
//	{
//		final String ERR_MSG = "The compare banner is not visible.";
//
//		try
//		{
//			/* validating that banner is visible */
//
//			PreConditions.checkState( isVisible(), ERR_MSG );
//
//			/* get the list of available ship names  */
//
//			List<WebElement> h4s = findH4();
//			return BaseElementObject.extractText( h4s );
//		}
//		catch( IllegalStateException ex )
//		{
//			logger.error( "throwing a new PreConditionException on {}#getShipNames(int).", getClass().getSimpleName() );
//			throw new PreConditionException( ex.getMessage(), ex );
//		}
//		catch ( Throwable t )
//		{
//			Throwables.propagateIfInstanceOf( t, ApplicationException.class );
//			logger.error( "throwing a new ApplicationException on {}#getShipNames.", getClass().getSimpleName() );
//			ApplicationException appEx = new ApplicationException( getDriver(), t.getMessage(), t );
//			appEx.addInfo( "business process", "failed to get ship names." );
//			throw appEx;
//		}
//	}
//
//	/**
//	 * {@inheritDoc}
//	 * <p>
//	 *     Procedure Steps:
//	 *     <ol>
//	 *        <li>validates that banner is visible</li>
//	 *        <li>finds a list of a.remove elements and extract the data-id attribute</li>
//	 *        <li>for each web-element build a {@linkplain com.framework.site.data.Ships} instance</li>
//	 *        <li>appends the instance to a {@literal List<Ships>} list</li>
//	 *     </ol>
//	 * </p>
//	 *
//	 * @return {@inheritDoc}
//	 *
//	 * @see com.framework.driver.objects.ListWebElementUtils#extractElementsText(java.util.List)
//	 * @see #getCompareShip
//	 */
//	@Override
//	public List<Ships> getShips()
//	{
//		final String ERR_MSG = "The compare banner is not visible.";
//
//		try
//		{
//			/* validating that banner is visible */
//
//			PreConditions.checkState( isVisible(), ERR_MSG );
//
//			/* get the list of available ships  */
//
//			List<WebElement> anchors = findRemoveShipAnchors();
//			List<Ships> ships = Lists.newArrayList();
//			for( WebElement anchor : anchors )
//			{
//				ships.add( getCompareShip( anchor ) );
//			}
//
//			return ships;
//		}
//		catch( IllegalStateException ex )
//		{
//			logger.error( "throwing a new PreConditionException on {}#getShips.", getClass().getSimpleName() );
//			throw new PreConditionException( ex.getMessage(), ex );
//		}
//		catch ( Throwable t )
//		{
//			Throwables.propagateIfInstanceOf( t, ApplicationException.class );
//			logger.error( "throwing a new ApplicationException on {}#getShips.", getClass().getSimpleName() );
//			ApplicationException appEx = new ApplicationException( getDriver(), t.getMessage(), t );
//			appEx.addInfo( "business process", "failed to getShips." );
//			throw appEx;
//		}
//	}
//
//	/**
//	 * {@inheritDoc}
//	 * <p>
//	 *     Procedure Steps:
//	 *     <ol>
//	 *        <li>validates that banner is visible</li>
//	 *        <li>finds a list of div.currently-comparing li elements</li>
//	 *        <li>for each web-element found finds the a.remove, h4 and img web-elements</li>
//	 *        <li>get required information and builds the Map</li>
//	 *     </ol>
//	 * </p>
//	 *
//	 * @return {@inheritDoc}
//	 *
//	 */
//	@Override
//	public Map<String, String> getCompareShipInfo( final Ships ship )
//	{
//		final String ERR_MSG1 = "The compare banner is not visible.";
//
//		try
//		{
//			/* validating that banner is visible */
//
//			PreConditions.checkState( isVisible(), ERR_MSG1 );
//
//			/* get the list of available ships  */
//
//			List<WebElement> lis = findComparingLis();
//			for( WebElement li : lis )
//			{
//				WebElement anchor = findRemoveAnchor( li );
//				String currentId = anchor.getAttribute( "data-id" );
//				if( ! currentId.equals( ship.getId() ) ) continue;
//
//				Map<String,String> info = Maps.newHashMap();
//				info.put( "id", currentId );
//				info.put( "name", findCompareShipH4( li ).getText() );
//
//				/* get image attributes alt and src */
//
//				WebElement img = findCompareShipImg( li );
//				info.put( "src", img.getAttribute( "src" ) );
//				info.put( "alt", img.getAttribute( "alt" ) );
//
//				return info;
//			}
//
//			throw new NoSuchShipException( getDriver(), "The ship <\"" + ship.getShipName() + "\"> does not exists." );
//		}
//		catch( IllegalStateException ex )
//		{
//			logger.error( "throwing a new PreConditionException on {}#getCompareShipInfo.", getClass().getSimpleName() );
//			throw new PreConditionException( ex.getMessage(), ex );
//		}
//		catch ( Throwable t )
//		{
//			Throwables.propagateIfInstanceOf( t, ApplicationException.class );
//			logger.error( "throwing a new ApplicationException on {}#getCompareShipInfo.", getClass().getSimpleName() );
//			ApplicationException appEx = new ApplicationException( getDriver(), t.getMessage(), t );
//			appEx.addInfo( "business process", "failed to get ship <\"" + ship.getShipName() + "\"> information." );
//			throw appEx;
//		}
//	}
//
//	/**
//	 * {@inheritDoc}
//	 * <p>
//	 *     Procedure Steps:
//	 *     <ol>
//	 *        <li>validates that banner is visible</li>
//	 *        <li>finds a list of a.remove web-elements</li>
//	 *        <li>search for the requested ship.id provided by argument {@code ship}</li>
//	 *        <li>
//	 *            in case that {@linkplain com.framework.site.data.Ships#getId()} was not located
//	 *            it throws a NoSuchShipException.
//	 *        </li>
//	 *        <li>if ship was found it calls to an internal remove procedure
//	 *            {@linkplain #removeInternal(org.openqa.selenium.WebElement, int, com.framework.site.data.Ships)}</li>
//	 *        </li>
//	 *     </ol>
//	 * </p>
//	 *
//	 * @return {@inheritDoc}
//	 *
//	 * @see #removeInternal(org.openqa.selenium.WebElement, int, com.framework.site.data.Ships)
//	 * @see com.framework.site.exceptions.NoSuchShipException
//	 *
//	 */
//	@Override
//	public void remove( final Ships ship )
//	{
//		final String ERR_MSG = "The compare banner is not visible.";
//		logger.info( "Removing ship <\"{}\"> from comparing banner...", ship.getShipName() );
//
//		try
//		{
//			/* validating that banner is visible */
//
//			PreConditions.checkState( isVisible(), ERR_MSG );
//
//			/* get the list of available ships  */
//
//			List<WebElement> anchors = findRemoveShipAnchors();
//			logger.debug( "found <{}> ships in compare ships banner." );
//			for( WebElement anchor : anchors )
//			{
//				String shipId = anchor.getAttribute( "data-id" );
//				if( ! shipId.equals( ship.getId() ) ) continue;
//
//				/* ship was located, start removing process */
//
//				removeInternal( anchor, anchors.size(), ship );
//			}
//
//			throw new NoSuchShipException( getDriver(), "The ship <\"" + ship.getShipName() + "\"> does not exists." );
//		}
//		catch( IllegalStateException ex )
//		{
//			logger.error( "throwing a new PreConditionException on {}#remove(ship).", getClass().getSimpleName() );
//			throw new PreConditionException( ex.getMessage(), ex );
//		}
//		catch ( Throwable t )
//		{
//			Throwables.propagateIfInstanceOf( t, ApplicationException.class );
//			logger.error( "throwing a new ApplicationException on {}#remove(ship).", getClass().getSimpleName() );
//			ApplicationException appEx = new ApplicationException( getDriver().getDriver(), t.getMessage(), t );
//			appEx.addInfo( "business process", "failed to remove ship <\"" + ship.getShipName() + "\">" );
//			throw appEx;
//		}
//	}
//
//	//todo: documentation
//	@Override
//	public Ships remove( final int index )
//	{
//		final String ERR_MSG1 = "The compare banner is not visible.";
//		final String ERR_MSG2 = "The requested ship index is out of bound.";
//
//		try
//		{
//			/* validating that banner is visible */
//
//			PreConditions.checkState( isVisible(), ERR_MSG1 );
//
//			/* get the list of available ships and validates requested index is not out of bound */
//
//			List<WebElement> anchors = findRemoveShipAnchors();
//			logger.debug( "found <{}> ships in compare ships banner." );
//			PreConditions.checkPositionIndex( index, anchors.size(), ERR_MSG2 );
//
//			/* ship index is valid, start removing process */
//
//			Ships ship = getCompareShip( anchors.get( index ) );
//			removeInternal( anchors.get( index ), anchors.size(), ship );
//
//			logger.info( "Ship <\"{}\" was removed from compare banner.", ship.getShipName() );
//
//			return ship;
//		}
//		catch( IndexOutOfBoundsException | IllegalStateException ex )
//		{
//			logger.error( "throwing a new PreConditionException on {}#remove( index ).", getClass().getSimpleName() );
//			throw new PreConditionException( ex.getMessage(), ex );
//		}
//		catch ( Throwable t )
//		{
//			Throwables.propagateIfInstanceOf( t, ApplicationException.class );
//			logger.error( "throwing a new ApplicationException on {}#remove( index ).", getClass().getSimpleName() );
//			ApplicationException appEx = new ApplicationException( getDriver(), t.getMessage(), t );
//			appEx.addInfo( "business process", "failed to remove compare banner ship index : <" + index + ">" );
//			throw appEx;
//		}
//	}
//
//	//endregion
//
//
	//region CompareBannerObject - Element Finder Methods Section

	private HtmlElement findBannerH3()
	{
		final By findBy = By.cssSelector( ".banner > h3" );
		return getDriver().findElement( findBy );
	}

	private HtmlElement findCompareBannerButton()
	{
		final By findBy = By.className( "blue-cta" );
		return getRoot().findElement( findBy );
	}

	private List<HtmlElement> findCurrentlyComparing()
	{
		final By findBy = By.cssSelector( "div.currently-comparing li" );
		return getRoot().findElements( findBy );
	}

	//endregion


}
