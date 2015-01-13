package com.framework.site.objects.body.ships;

import com.framework.asserts.JAssertions;
import com.framework.driver.exceptions.ApplicationException;
import com.framework.driver.exceptions.PreConditionException;
import com.framework.driver.objects.AbstractWebObject;
import com.framework.driver.utils.PreConditions;
import com.framework.driver.utils.ui.ListWebElementUtils;
import com.framework.driver.utils.ui.WaitUtil;
import com.framework.site.data.Ships;
import com.framework.site.exceptions.NoSuchShipException;
import com.framework.site.objects.body.interfaces.CompareShipBanner;
import com.framework.site.pages.core.cruiseships.CompareCruiseShipsPage;
import com.google.common.base.MoreObjects;
import com.google.common.base.Throwables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;


//todo: class documentation

public class CompareShipBannerObject extends AbstractWebObject implements CompareShipBanner
{

	//region CompareBannerObject - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( CompareShipBannerObject.class );

	//endregion


	//region CompareBannerObject - Constructor Methods Section

	/**
	 * Creates a new instance of the Compare ship banner section.
	 *
	 * @param driver        the current working {@code WebDriver}
	 * @param rootElement   the ship card root element.
	 */
	public CompareShipBannerObject( WebDriver driver, final WebElement rootElement )
	{
		super( CompareShipBanner.LOGICAL_NAME, driver, rootElement );
	}

	//endregion


	//region CompareBannerObject - Initialization and Validation Methods Section

	/**
	 * initialize and validates the ship compare banner object.
	 * This method is called by {@linkplain com.framework.driver.objects.AbstractWebObject} base class.
	 * the parse action will be skipped if the compare banner is currently not displayed.
	 * <p>
	 *     Procedure Steps:
	 *     <ol>
	 *         <li>determine if the compare banner is currently displayed, otherwise no validation</li>
	 *         <li>assert that the 'Compare Ships' button exists</li>
	 *     	   <li>asserts that the 'Compare Ships' button is visible.</li>
	 *     	   <li>asserts that the 'Compare Ships' button is enabled.</li>
	 *     </ol>
	 * </p>
	 */
	@Override
	protected void initWebObject()
	{
		logger.debug( "validating static elements for web object id: <{}>, name:<{}>...", getId(), getLogicalName() );
		WebDriverWait wew = WaitUtil.wait10( objectDriver );

		try
		{
			if( isVisible() )
			{
				/* validates blue-cta link button exists */

				ExpectedCondition<WebElement> condition = WaitUtil.presenceOfChildBy( getRoot(), By.className( "blue-cta" ) );
				JAssertions.assertWaitThat( wew ).matchesCondition( condition );

				/* validates blue-cta link button is displayed and enable */

				WebElement blueCta = findCompareShipsAnchor();
				JAssertions.assertThat( blueCta ).isDisplayed().isEnabled();

				/* validates that id visible the banner have at least one item */

				JAssertions.assertThat( findRemoveShipAnchors().size() ).isGreaterThan( 0 );
			}
		}
		catch ( AssertionError ae )
		{
			Throwables.propagateIfInstanceOf( ae, ApplicationException.class );
			logger.error( "throwing a new WebObjectException on {}#initWebObject.", getClass().getSimpleName() );
			ApplicationException ex = new ApplicationException( objectDriver.getWrappedDriver(), ae.getMessage(),ae );
			ex.addInfo( "cause", "verification and initialization process for object " + getLogicalName() + " was failed." );
			throw ex;
		}
	}


	//endregion


	//region CompareBannerObject - Service Methods Section

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
		catch ( StaleElementReferenceException ex )
		{
			logger.warn( "auto recovering from StaleElementReferenceException ..." );
			return objectDriver.findElement( CompareShipBanner.ROOT_BY );
		}
	}

	private Ships getCompareShip( WebElement anchor )
	{
		String dataId = anchor.getAttribute( "data-id" );
		Ships ship = Ships.valueById( dataId );
		if( null == ship )
		{
			throw new NoSuchShipException( objectDriver, "The ship id <\"" + dataId + "\"> does not exists" );
		}

		return ship;
	}


	private void removeInternal( WebElement removeAnchor, int currentSize, Ships removeShip )
	{
		final int EXPECTED_SHIPS = currentSize - 1;
		final String EXPECTED_SHIP_NOT_IN_LIST = removeShip.getId();
		final boolean EXPECTED_BANNER_DISPLAYED = EXPECTED_SHIPS == 0;

		removeAnchor.click();
		List<WebElement> anchorsAfter = findRemoveShipAnchors();
		logger.info( "validating that ships have one less item than <{}>", Integer.valueOf( EXPECTED_SHIPS + 1 ) );
		JAssertions.assertThat( anchorsAfter.size() ).isEqualTo( EXPECTED_SHIPS );
		logger.info( "validating compare banner to be displayed/hidden.." );
		JAssertions.assertThat( isVisible() ).isEqualTo( EXPECTED_BANNER_DISPLAYED );

		/* Validating that removed ship is not in banner, if the banner remains displayed */

		if( EXPECTED_BANNER_DISPLAYED )
		{
			List<String> shipsIds = ListWebElementUtils.extractElementsAttribute( anchorsAfter, "data-id" );
			JAssertions.assertThat( shipsIds ).doesNotContain( EXPECTED_SHIP_NOT_IN_LIST );
		}
	}

	//endregion


	//region CompareBannerObject - Business Methods Section

	/**
	 * {@inheritDoc}
	 * <p>
	 *     determine if the banner is visible by querying the div.banner.has-items web-element
	 * </p>
	 *
	 * @return {@inheritDoc}
	 */
	@Override
	public boolean isVisible()
	{
		try
		{
		  	WebElement bannerDiv = findDivBanner();
			String classes = bannerDiv.getAttribute( "class" );
			return classes.contains( "has-items" );
		}
		catch ( Throwable e )
		{
			Throwables.propagateIfInstanceOf( e, ApplicationException.class );
			logger.error( "throwing a new ApplicationException on {}#isVisible.", getClass().getSimpleName() );
			ApplicationException aex = new ApplicationException( objectDriver.getWrappedDriver(), e.getMessage(), e );
			aex.addInfo( "business process", "failed to determine ship banner is visible." );
			throw aex;
		}
	}

	@Override
	public CompareCruiseShipsPage doCompareShips()
	{
		final String ERR_MSG = "The compare banner is not visible.";

		try
		{
			PreConditions.checkState( isVisible(), ERR_MSG );

			/* finds the anchor button 'Compare Ships' and click it */

			WebElement blueCta = findCompareShipsAnchor();
			blueCta.click();
			return new CompareCruiseShipsPage( objectDriver );
		}
		catch( IndexOutOfBoundsException | IllegalStateException ex )
		{
			logger.error( "throwing a new PreConditionException on {}#doCompareShips.", getClass().getSimpleName() );
			throw new PreConditionException( ex.getMessage(), ex );
		}
		catch ( Throwable e )
		{
			Throwables.propagateIfInstanceOf( e, ApplicationException.class );
			logger.error( "throwing a new ApplicationException on {}#doCompareShips.", getClass().getSimpleName() );
			ApplicationException aex = new ApplicationException( objectDriver.getWrappedDriver(), e.getMessage(), e );
			aex.addInfo( "business process", "failed to click on banner's 'Compare Ships' button." );
			throw aex;
		}
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 *     Procedure Steps:
	 *     <ol>
	 *        <li>validates that banner is visible</li>
	 *        <li>
	 *             validates that the requested index is valid
	 *            {@link com.framework.driver.utils.PreConditions#checkPositionIndex(int, int, String)}
	 *        </li>
	 *        <li>gets the link at index: argument {@code index}</li>
	 *     	  <li>validates that the ship exists ( by ship id )</li>
	 *     </ol>
	 * </p>
	 * @param index the index of the ship to retrieve
	 *
	 * @return
	 */
	@Override
	public Ships getShip( final int index )
	{
		final String ERR_MSG1 = "The compare banner is not visible.";
		final String ERR_MSG2 = "The requested ship index is out of bound.";

		try
		{
			/* validating that banner is visible */

			PreConditions.checkState( isVisible(), ERR_MSG1 );

			/* validating requested index is not out of bound  */

			List<WebElement> anchors = findRemoveShipAnchors();
			logger.debug( "found <{}> ships in compare ships banner." );
			PreConditions.checkPositionIndex( index, anchors.size(), ERR_MSG2 );

			/* get index and return a ship item  */

			return getCompareShip( anchors.get( index ) );
		}
		catch( IndexOutOfBoundsException | IllegalStateException ex )
		{
			logger.error( "throwing a new PreConditionException on {}#getShip(int).", getClass().getSimpleName() );
			throw new PreConditionException( ex.getMessage(), ex );
		}
		catch ( Throwable t )
		{
			Throwables.propagateIfInstanceOf( t, ApplicationException.class );
			logger.error( "throwing a new ApplicationException on {}#getShip(int).", getClass().getSimpleName() );
			ApplicationException appEx = new ApplicationException( objectDriver.getWrappedDriver(), t.getMessage(), t );
			appEx.addInfo( "business process", "failed to get ship index <" + index + ">");
			throw appEx;
		}
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 *     Procedure Steps:
	 *     <ol>
	 *        <li>validates that banner is visible</li>
	 *        <li>finds a list of h4 elements and gets the text</li>
	 *        <li>
	 *            extracting the text using
	 *           {@linkplain com.framework.driver.utils.ui.ListWebElementUtils#extractElementsText(java.util.List)}
	 *        </li>
	 *     </ol>
	 * </p>
	 *
	 * @return {@inheritDoc}
	 *
	 * @see com.framework.driver.utils.ui.ListWebElementUtils#extractElementsText(java.util.List)
	 */
	@Override
	public List<String> getShipNames()
	{
		final String ERR_MSG = "The compare banner is not visible.";

		try
		{
			/* validating that banner is visible */

			PreConditions.checkState( isVisible(), ERR_MSG );

			/* get the list of available ship names  */

			List<WebElement> h4s = findH4();
			return ListWebElementUtils.extractElementsText( h4s );
		}
		catch( IllegalStateException ex )
		{
			logger.error( "throwing a new PreConditionException on {}#getShipNames(int).", getClass().getSimpleName() );
			throw new PreConditionException( ex.getMessage(), ex );
		}
		catch ( Throwable t )
		{
			Throwables.propagateIfInstanceOf( t, ApplicationException.class );
			logger.error( "throwing a new ApplicationException on {}#getShipNames.", getClass().getSimpleName() );
			ApplicationException appEx = new ApplicationException( objectDriver.getWrappedDriver(), t.getMessage(), t );
			appEx.addInfo( "business process", "failed to get ship names." );
			throw appEx;
		}
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 *     Procedure Steps:
	 *     <ol>
	 *        <li>validates that banner is visible</li>
	 *        <li>finds a list of a.remove elements and extract the data-id attribute</li>
	 *        <li>for each web-element build a {@linkplain com.framework.site.data.Ships} instance</li>
	 *        <li>appends the instance to a {@literal List<Ships>} list</li>
	 *     </ol>
	 * </p>
	 *
	 * @return {@inheritDoc}
	 *
	 * @see com.framework.driver.utils.ui.ListWebElementUtils#extractElementsText(java.util.List)
	 * @see #getCompareShip
	 */
	@Override
	public List<Ships> getShips()
	{
		final String ERR_MSG = "The compare banner is not visible.";

		try
		{
			/* validating that banner is visible */

			PreConditions.checkState( isVisible(), ERR_MSG );

			/* get the list of available ships  */

			List<WebElement> anchors = findRemoveShipAnchors();
			List<Ships> ships = Lists.newArrayList();
			for( WebElement anchor : anchors )
			{
				ships.add( getCompareShip( anchor ) );
			}

			return ships;
		}
		catch( IllegalStateException ex )
		{
			logger.error( "throwing a new PreConditionException on {}#getShips.", getClass().getSimpleName() );
			throw new PreConditionException( ex.getMessage(), ex );
		}
		catch ( Throwable t )
		{
			Throwables.propagateIfInstanceOf( t, ApplicationException.class );
			logger.error( "throwing a new ApplicationException on {}#getShips.", getClass().getSimpleName() );
			ApplicationException appEx = new ApplicationException( objectDriver.getWrappedDriver(), t.getMessage(), t );
			appEx.addInfo( "business process", "failed to getShips." );
			throw appEx;
		}
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 *     Procedure Steps:
	 *     <ol>
	 *        <li>validates that banner is visible</li>
	 *        <li>finds a list of div.currently-comparing li elements</li>
	 *        <li>for each web-element found finds the a.remove, h4 and img web-elements</li>
	 *        <li>get required information and builds the Map</li>
	 *     </ol>
	 * </p>
	 *
	 * @return {@inheritDoc}
	 *
	 */
	@Override
	public Map<String, String> getCompareShipInfo( final Ships ship )
	{
		final String ERR_MSG1 = "The compare banner is not visible.";

		try
		{
			/* validating that banner is visible */

			PreConditions.checkState( isVisible(), ERR_MSG1 );

			/* get the list of available ships  */

			List<WebElement> lis = findComparingLis();
			for( WebElement li : lis )
			{
				WebElement anchor = findRemoveAnchor( li );
				String currentId = anchor.getAttribute( "data-id" );
				if( ! currentId.equals( ship.getId() ) ) continue;

				Map<String,String> info = Maps.newHashMap();
				info.put( "id", currentId );
				info.put( "name", findCompareShipH4( li ).getText() );

				/* get image attributes alt and src */

				WebElement img = findCompareShipImg( li );
				info.put( "src", img.getAttribute( "src" ) );
				info.put( "alt", img.getAttribute( "alt" ) );

				return info;
			}

			throw new NoSuchShipException( objectDriver, "The ship <\"" + ship.getTitle() + "\"> does not exists." );
		}
		catch( IllegalStateException ex )
		{
			logger.error( "throwing a new PreConditionException on {}#getCompareShipInfo.", getClass().getSimpleName() );
			throw new PreConditionException( ex.getMessage(), ex );
		}
		catch ( Throwable t )
		{
			Throwables.propagateIfInstanceOf( t, ApplicationException.class );
			logger.error( "throwing a new ApplicationException on {}#getCompareShipInfo.", getClass().getSimpleName() );
			ApplicationException appEx = new ApplicationException( objectDriver.getWrappedDriver(), t.getMessage(), t );
			appEx.addInfo( "business process", "failed to get ship <\"" + ship.getTitle() + "\"> information." );
			throw appEx;
		}
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 *     Procedure Steps:
	 *     <ol>
	 *        <li>validates that banner is visible</li>
	 *        <li>finds a list of a.remove web-elements</li>
	 *        <li>search for the requested ship.id provided by argument {@code ship}</li>
	 *        <li>
	 *            in case that {@linkplain com.framework.site.data.Ships#getId()} was not located
	 *            it throws a NoSuchShipException.
	 *        </li>
	 *        <li>if ship was found it calls to an internal remove procedure
	 *            {@linkplain #removeInternal(org.openqa.selenium.WebElement, int, com.framework.site.data.Ships)}</li>
	 *        </li>
	 *     </ol>
	 * </p>
	 *
	 * @return {@inheritDoc}
	 *
	 * @see #removeInternal(org.openqa.selenium.WebElement, int, com.framework.site.data.Ships)
	 * @see com.framework.site.exceptions.NoSuchShipException
	 *
	 */
	@Override
	public void remove( final Ships ship )
	{
		final String ERR_MSG = "The compare banner is not visible.";
		logger.info( "Removing ship <\"{}\"> from comparing banner...", ship.getTitle() );

		try
		{
			/* validating that banner is visible */

			PreConditions.checkState( isVisible(), ERR_MSG );

			/* get the list of available ships  */

			List<WebElement> anchors = findRemoveShipAnchors();
			logger.debug( "found <{}> ships in compare ships banner." );
			for( WebElement anchor : anchors )
			{
				String shipId = anchor.getAttribute( "data-id" );
				if( ! shipId.equals( ship.getId() ) ) continue;

				/* ship was located, start removing process */

				removeInternal( anchor, anchors.size(), ship );
			}

			throw new NoSuchShipException( objectDriver, "The ship <\"" + ship.getTitle() + "\"> does not exists." );
		}
		catch( IllegalStateException ex )
		{
			logger.error( "throwing a new PreConditionException on {}#remove(ship).", getClass().getSimpleName() );
			throw new PreConditionException( ex.getMessage(), ex );
		}
		catch ( Throwable t )
		{
			Throwables.propagateIfInstanceOf( t, ApplicationException.class );
			logger.error( "throwing a new ApplicationException on {}#remove(ship).", getClass().getSimpleName() );
			ApplicationException appEx = new ApplicationException( objectDriver.getWrappedDriver(), t.getMessage(), t );
			appEx.addInfo( "business process", "failed to remove ship <\"" + ship.getTitle() + "\">" );
			throw appEx;
		}
	}

	//todo: documentation
	@Override
	public Ships remove( final int index )
	{
		final String ERR_MSG1 = "The compare banner is not visible.";
		final String ERR_MSG2 = "The requested ship index is out of bound.";

		try
		{
			/* validating that banner is visible */

			PreConditions.checkState( isVisible(), ERR_MSG1 );

			/* get the list of available ships and validates requested index is not out of bound */

			List<WebElement> anchors = findRemoveShipAnchors();
			logger.debug( "found <{}> ships in compare ships banner." );
			PreConditions.checkPositionIndex( index, anchors.size(), ERR_MSG2 );

			/* ship index is valid, start removing process */

			Ships ship = getCompareShip( anchors.get( index ) );
			removeInternal( anchors.get( index ), anchors.size(), ship );

			logger.info( "Ship <\"{}\" was removed from compare banner.", ship.getTitle() );

			return ship;
		}
		catch( IndexOutOfBoundsException | IllegalStateException ex )
		{
			logger.error( "throwing a new PreConditionException on {}#remove( index ).", getClass().getSimpleName() );
			throw new PreConditionException( ex.getMessage(), ex );
		}
		catch ( Throwable t )
		{
			Throwables.propagateIfInstanceOf( t, ApplicationException.class );
			logger.error( "throwing a new ApplicationException on {}#remove( index ).", getClass().getSimpleName() );
			ApplicationException appEx = new ApplicationException( objectDriver.getWrappedDriver(), t.getMessage(), t );
			appEx.addInfo( "business process", "failed to remove compare banner ship index : <" + index + ">" );
			throw appEx;
		}
	}

	//endregion


	//region CompareBannerObject - Element Finder Methods Section

	private WebElement findCompareShipsAnchor()
	{
		By findBy = By.className( "blue-cta" );
		return getRoot().findElement( findBy );
	}

	private List<WebElement> findRemoveShipAnchors()
	{
		By findBy = By.cssSelector( "div.currently-comparing a.remove" );
		return getRoot().findElements( findBy );
	}

	private List<WebElement> findH4()
	{
		By findBy = By.cssSelector( "div.currently-comparing h4" );
		return getRoot().findElements( findBy );
	}

	private List<WebElement> findComparingLis()
	{
		By findBy = By.cssSelector( "div.currently-comparing li" );
		return getRoot().findElements( findBy );
	}

	private WebElement findRemoveAnchor( WebElement li )
	{
		By findBy = By.tagName( "a" );
		return li.findElement( findBy );
	}

	private WebElement findCompareShipH4( WebElement li )
	{
		By findBy = By.tagName( "h4" );
		return li.findElement( findBy );
	}

	private WebElement findCompareShipImg( WebElement li )
	{
		By findBy = By.tagName( "img" );
		return li.findElement( findBy );
	}

	private WebElement findDivBanner()
	{
		By findBy = By.cssSelector( "div.banner" );
		return getRoot().findElement( findBy );
	}

	//endregion

}
