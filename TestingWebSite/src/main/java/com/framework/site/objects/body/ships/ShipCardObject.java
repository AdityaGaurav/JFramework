package com.framework.site.objects.body.ships;

import com.framework.driver.event.HtmlElement;
import com.framework.driver.event.HtmlObject;
import com.framework.driver.exceptions.ApplicationException;
import com.framework.driver.exceptions.UrlNotAvailableException;
import com.framework.driver.objects.AbstractWebObject;
import com.framework.driver.objects.Link;
import com.framework.driver.utils.ui.HighlightStyle;
import com.framework.site.config.SiteSessionManager;
import com.framework.site.data.DeparturePorts;
import com.framework.site.data.Destinations;
import com.framework.site.data.Ships;
import com.framework.site.data.TripDurations;
import com.framework.site.exceptions.NoSuchDeparturePortException;
import com.framework.site.exceptions.NoSuchDestinationException;
import com.framework.site.exceptions.NoSuchShipException;
import com.framework.site.exceptions.NoSuchTripDurationException;
import com.framework.site.objects.body.interfaces.ShipCard;
import com.framework.site.pages.bookingengine.FindACruisePage;
import com.framework.site.pages.core.HomePage;
import com.framework.site.pages.core.cruisefrom.CruiseFromPortPage;
import com.framework.site.pages.core.cruiseships.CruiseShipsDetailsPage;
import com.framework.site.pages.core.cruiseto.CruiseToDestinationPage;
import com.framework.utils.matchers.JMatchers;
import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.text.StrSubstitutor;
import org.hamcrest.Matcher;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.hamcrest.Matchers.contains;


/**
 * The class is a web object functionality handler located in {@link com.framework.site.pages.core.CruiseShipsPage}
 * this class represents a single ship card of the ship cards collection displayed in the page.
 * <p/>
 * the root of the element starts at {@literal <div data-name="${ship-name}" data-id="${ship-id}" class="activity-result ship-result>}
 * tag.
 *
 * @author solmarkn
 *
 * @see com.framework.site.pages.core.CruiseShipsPage
 * @see com.framework.site.pages.core.cruiseships.CruiseShipsDetailsPage
 */

public class ShipCardObject extends AbstractWebObject implements ShipCard
{

	//region ShipCardObject - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( ShipCardObject.class );

	private static final String IMG_FORMAT = "/Images/Ships/${ship.code}/${ship.name.dash.lower.case}-tile";

	private static final int VIEW_PORT_OFFSET_Y = -80;

	/**
	 * provides a substitutor for replacing keys
	 */
	private StrSubstitutor substitutor = null;

	/**
	 * reference to the current predefined ship info.
	 */
	private Ships ship = Ships.UNKNOWN;

	/**
	 * a set of predefined destinations, to avoid duplications.
	 */
	private Set<Destinations> destinations = Sets.newHashSet();

	/**
	 * a set of predefined departure Ports, to avoid duplications.
	 */
	private Set<DeparturePorts> departurePorts = Sets.newHashSet();

	/**
	 * a set of predefined trip durations, to avoid duplications.
	 */
	private Set<TripDurations> tripDurations = Sets.newHashSet();

	private boolean isComparable = false;

	private String expectedImagePath = null;

	// ------------------------------------------------------------------------|
	// --- WEB-OBJECTS CACHING ------------------------------------------------|
	// ------------------------------------------------------------------------|

	private HtmlElement ship_image, h2_title, compare;

	//endregion


	//region ShipCardObject - Constructor Methods Section

	/**
	 * Creates a new instance of a single ship card.
	 *
	 * @param rootElement   the single ship card root element.
	 */
	public ShipCardObject( final HtmlElement rootElement )
	{
		super( rootElement, ShipCard.LOGICAL_NAME );
		initWebObject();
	}

	//endregion


	//region ShipCardObject - Initialization and Validation Methods Section

	/**
	 * initialize and validates the ship card object.
	 * This method is called by {@linkplain com.framework.driver.objects.AbstractWebObject} base class.
	 * <p>
	 *     Procedure Steps:
	 *     <ol>
	 *         <li>
	 *             parseShipCard the ship card, by populating {@linkplain #destinations}, {@linkplain #tripDurations}
	 *             and {@linkplain #departurePorts} internal lists. and populates the {@linkplain #substitutor}
	 *     	   </li>
	 *     	   <li>validates that ship image[src] attribute contains the ship code and ship name on specific format.</li>
	 *         <li>validates that ship title anchor text match the card ship information.</li>
	 *     </ol>
	 * </p>
	 */
	@Override
	protected void initWebObject()
	{
		logger.debug( "validating static elements for web object id: <{}>, name:<{}>...",
				getQualifier(), getLogicalName() );

		try
		{
			getRoot().scrollIntoView();
			getRoot().scrollBy( 0, VIEW_PORT_OFFSET_Y ); // yOffset fix
			getRoot().mark( HighlightStyle.ELEMENT_STYLES[ 3 ] );
			validateShip();
			validateConnectionToShipPage();
			validateImage();
			validateTitle();
			validateShipTravelInfo();
			List<HtmlElement> nodes = findTextNodes();
			validateDestinations( nodes.get( 0 ) );
			validateDeparturePorts( nodes.get( 1 ) );
			validateTripDurations( nodes.get( 2 ) );
			validateComparable();
		}
		catch ( UrlNotAvailableException unaEx )
		{
			throw new ApplicationException( unaEx );
		}

	}

	private void validateComparable()
	{
		// not comparing on Australia domain

		if( ! SiteSessionManager.get().getCurrentLocale().equals( HomePage.AU ) )
		{
			Optional<HtmlElement> response = getRoot().childExists( By.className( "compare" ) );
			if( ! response.isPresent() )
			{
				throw new ApplicationException( getDriver(), "The compare item does not exists on "  + ship.getFullName() );
			}

			compare = response.get();
			isComparable = true;
		}
	}

	private void validateShip()
	{
		logger.info( "Validating ship name on card metadata ..." );

		getRoot().blink();

		String dataId = getRoot().getAttribute( "data-id" );
		String dataName = getRoot().getAttribute( "data-name" );
		this.ship = Ships.valueById( dataId );
		if( ship == null ) throw new NoSuchShipException( getDriver(), "The ship code < " + dataId + " > does not exists" );
		getDriver().assertThat( "Validates the ship name", dataName, JMatchers.is( ship.getFullName() ) );

		Map<String,String> map = Maps.newHashMap();
		map.put( "ship.code", dataId );
		map.put( "ship.name.dash.lower.case", ship.getFullName().toLowerCase().replace( " ", "-" ) );
		this.substitutor = new StrSubstitutor( map );
	}

	private void validateShipTravelInfo()
	{
		logger.info( "Validating ship travel information structure ..." );

		List<HtmlElement> list = findTextNodesStrong();
		List<String> names = HtmlObject.extractText( list );
		getDriver().assertThat( "Validating travel information section", names,
				contains( "Destination:", "Departure Port:", "Trip Duration:" ) );
	}

	private void validateDestinations( HtmlElement li )
	{
		logger.info( "Validating ship destinations ..." );

		li.blink();
		List<HtmlElement> anchors = li.findElements( By.tagName( "a" ) );
		if( anchors.size() == 0 )
		{
			throw new ApplicationException( getDriver(), "No destination for ship " + ship.getFullName() );
		}
		for( HtmlElement anchor : anchors )
		{
			String text = anchor.getText();
			Destinations current;
			try
			{
				current = Destinations.valueByName( text );
				getDriver().assertThat( "Validate \"Destinations\" value for " + text, current, JMatchers.not( JMatchers.nullValue() ) );
			}
			catch ( AssertionError e )
			{
				throw new NoSuchDestinationException( getDriver(), "The destination code < " + text + " > does not exists" );
			}

			this.destinations.add( current );
		}
	}

	private void validateDeparturePorts( HtmlElement li )
	{
		logger.info( "Validating ship departure ports ..." );
		li.blink();
		List<HtmlElement> anchors = li.findElements( By.tagName( "a" ) );
		if( anchors.size() == 0 )
		{
			throw new ApplicationException( getDriver(), "No departure ports for ship " + ship.getFullName() );
		}
		for( HtmlElement anchor : anchors )
		{
			String text = anchor.getText();
			DeparturePorts current;
			try
			{
				int indexOf = text.indexOf( "," );
				if( indexOf > 0 )
				{
					String subString = text.substring( 0, indexOf );
					current = DeparturePorts.valueByName( subString );
				}
				else
				{
					current = DeparturePorts.valueByName( text );
				}

				getDriver().assertThat( "Validate \"Departure Ports\" value for " + text, current, JMatchers.not( JMatchers.nullValue() ) );
			}
			catch ( AssertionError e )
			{
				throw new NoSuchDeparturePortException( getDriver(), "The departure port code < " + text + " > does not exists" );
			}

			this.departurePorts.add( current );
		}
	}

	private void validateTripDurations( HtmlElement li )
	{
		logger.info( "Validating ship trip durations ..." );
		li.blink();

		List<HtmlElement> anchors = li.findElements( By.tagName( "a" ) );
		if( anchors.size() == 0 )
		{
			throw new ApplicationException( getDriver(), "No trip durations ports for ship " + ship.getFullName() );
		}
		for( HtmlElement anchor : anchors )
		{
			String text = anchor.getAttribute( "textContent" ).trim();
			TripDurations current;
			try
			{
				current = TripDurations.valueByName( text );
				getDriver().assertThat( "Validate \"Trip Duration\" value for " + text, current, JMatchers.not( JMatchers.nullValue() ) );
			}
			catch ( AssertionError e )
			{
				throw new NoSuchTripDurationException( getDriver(), "The destination code < " + text + " > does not exists" );
			}

			this.tripDurations.add( current );
		}
	}

	private void validateImage()
	{
		HtmlElement he = findShipImageImg();
		he.blink();
		String src = he.getAttribute( "src" );
		this.expectedImagePath = substitutor.replace( IMG_FORMAT );
		getDriver().assertThat( "Assert ship image", src, JMatchers.containsString( expectedImagePath ) );
	}

	private void validateTitle()
	{
	 	HtmlElement he = findTitleH2();
		he.blink();
		String text = he.getAttribute( "textContent" );
		String expected = this.ship.getFullName();
		getDriver().assertThat( "Assert ship Title", text, JMatchers.is( expected ) );
	}

	private void validateConnectionToShipPage() throws UrlNotAvailableException
	{
		Link link = new Link( findUpperAnchor() );
		link.checkReference( 10000L );
	}

	//endregion


	//region ShipCardObject - Service Methods Section

	private HtmlElement getRoot()
	{
		return getBaseRootElement();
	}

	@Override
	public String toString()
	{
		return new ToStringBuilder( this )
				.append( "ship", null != ship ? ship.getFullName() : "N/A" )
				.append( "ship id", null != ship ?  ship.getId() : "N/A" )
				.append( "destinations", null != destinations ? destinations.size() : 0 )
				.append( "departurePorts", null != departurePorts ? departurePorts.size() : 0 )
				.append( "tripDurations", null != tripDurations ? tripDurations.size() : 0 )
				.append( "isComparable", isComparable )
				.toString();
	}

	@Override
	public String getExpectedImagePath()
	{
		return expectedImagePath;
	}

	//endregion


	//region ShipCardObject - ShipCard Implementation methods section

	@Override
	public Ships getShip()
	{
		return ship;
	}

	@Override
	public List<Destinations> getDestinations()
	{
		return Lists.newArrayList( destinations );
	}

	@Override
	public List<DeparturePorts> getDeparturePorts()
	{
		return Lists.newArrayList( departurePorts );
	}

	@Override
	public List<TripDurations> getTripDurations()
	{
		return Lists.newArrayList( tripDurations );
	}

	@Override
	public void doCompare( final boolean check )
	{
		HtmlElement compareAnchor = findCompareAnchor();
		HtmlElement compareMsg = findCompareMsgSpan();
		HtmlElement addedMsg = findAddedMsgSpan();

		if( check )
		{
			// validate that compare object is not "CHECKED"
			String REASON = "validate that compare object is not \"CHECKED\"";
			boolean ACTUAL = compareMsg.isDisplayed();
			Matcher<Boolean> EXPECTED_OF_BOOL = JMatchers.is( true );
			getDriver().assertThat( REASON, ACTUAL, EXPECTED_OF_BOOL );
		}
		else
		{
			// validate that compare object is "CHECKED"
			String REASON = "validate that compare object is \"CHECKED\"";
			boolean ACTUAL = addedMsg.isDisplayed();
			Matcher<Boolean> EXPECTED_OF_BOOL = JMatchers.is( true );
			getDriver().assertThat( REASON, ACTUAL, EXPECTED_OF_BOOL );
		}
		compareAnchor.jsClick();
	}

	@Override
	public boolean isComparing()
	{
		HtmlElement he = findAddedMessageSpan();
		return he.isDisplayed();
	}

	@Override
	public HtmlElement getImage()
	{
		return findShipImageImg();
	}

	@Override
	public CruiseFromPortPage selectDeparturePort( final DeparturePorts departurePort )
	{
		return null;
	}

	@Override
	public CruiseToDestinationPage selectDestination( final Destinations destination )
	{
		return null;
	}

	@Override
	public FindACruisePage selectTripDuration( final TripDurations tripDuration )
	{
		return null;
	}

	@Override
	public CruiseShipsDetailsPage clickShipTitle()
	{
		return null;
	}

	@Override
	public CruiseShipsDetailsPage clickShipImage()
	{
		return null;
	}

	@Override
	public Set<String> getDeparturePortNames()
	{
		return null;
	}

	@Override
	public Set<String> getDestinationNames()
	{
		return null;
	}

	@Override
	public Set<String> getTripDurationNames()
	{
		return null;
	}

	@Override
	public boolean isComparable()
	{
		return isComparable;
	}

	//endregion


	//region ShipCardObject - Element Finder methods section

	private HtmlElement findUpperAnchor()
	{
		final By findBy = By.className( "upper" );
		return getRoot().findElement( findBy );
	}

	private HtmlElement findShipImageImg()
	{
		if( null == ship_image )
		{
			final By findBy = By.cssSelector( "span.image > img" );
			ship_image = getRoot().findElement( findBy );
		}

		return ship_image;
	}

	private HtmlElement findTitleH2()
	{
		if( h2_title == null )
		{
			final By findBy = By.tagName( "h2" );
			h2_title = getRoot().findElement( findBy );
		}

		return h2_title;
	}

	private List<HtmlElement> findTextNodes()
	{
		final By findBy = By.cssSelector( ".text li" );
		return getRoot().findElements( findBy );
	}

	private List<HtmlElement> findTextNodesStrong()
	{
		final By findBy = By.cssSelector( ".text li > strong" );
		return getRoot().findElements( findBy );
	}

	private HtmlElement findCompareAnchor()
	{
		if( compare == null )
		{
			final By findBy = By.className( "compare" );
			compare = getRoot().findElement( findBy );
		}

		return compare;
	}

	private HtmlElement findCompareMsgSpan()
	{
		final By findBy = By.className( "compare-msg" );
		return findCompareAnchor().findElement( findBy );
	}

	private HtmlElement findAddedMsgSpan()
	{
		final By findBy = By.className( "added-msg" );
		return findCompareAnchor().findElement( findBy );
	}

	private HtmlElement findAddedMessageSpan()
	{
		final By findBy = By.cssSelector( ".text .compare span.added-msg" );
		return getRoot().findElement( findBy );
	}

	//endregion



//	//endregion
//
//
//	//region ShipCardObject - Business Methods Section
//
//	@Override
//	public Ships getShipName()
//	{
//		return ship;
//	}
//
//	/**
//	 * {@inheritDoc}
//	 * <p>
//	 *     the method flow is determined by the {@code check} argument and the visibility of
//	 *     the span.added-msg and span.compare tags.
//	 *     usage:
//	 *     <pre>
//	 *        shipCard.doCompare( true );
//	 *        boolean isComparing = shipCard.isComparing();
//	 *
//	 *        if( ! shipCard.isComparing() )
//	 *        {
//	 *          shipCard.doCompare( true );
//	 *        }
//	 *     </pre>
//	 * </p>
//	 *
//	 * @param check indicates to compare the ship or remove from comparison.
//	 */
//	@Override
//	public void doCompare( final boolean check )
//	{
//		try
//		{
//			WebElement compareMsg = findCompareMsgSpan();
//			WebElement addedMsg = findAddedMsgSpan();
//			WebElement compareAnchor = findCompareAnchor();
//
//			if( check )
//			{
//				if( compareMsg.isDisplayed() && ( ! addedMsg.isDisplayed() ) )
//				{
//				 	Link link = new Link( compareAnchor );
//					link.hover( true );
//					link.click();
//				}
//			}
//		}
//		catch ( Throwable e )
//		{
//			Throwables.propagateIfInstanceOf( e, ApplicationException.class );
//			logger.error( "throwing a new ApplicationException on {}#doCompare.", getClass().getSimpleName() );
//			ApplicationException aex = new ApplicationException( objectDriver.getDriver(), e.getMessage(), e );
//			aex.addInfo( "business process", "failed set/unset <\"{}\"> comparison." + ship);
//			throw aex;
//		}
//	}
//
//	/**
//	 * {@inheritDoc}
//	 * <p>
//	 *     Method usage:
//	 *     <pre>
//	 *        List<Destinations> destinations = shipCard.getDestinations();
//	 *        String name = destinations.get( 1 )
//	 *        CruiseToDestinationPage destinationPage = shipCard.selectDestination( destinations.get( 1 ) );
//	 *     </pre>
//	 * </p>
//	 */
//	@Override
//	public List<Destinations> getDestinations()
//	{
//		return Lists.newArrayList( destinations );
//	}
//
//	/**
//	 * {@inheritDoc}
//	 * <p>
//	 *     Method usage:
//	 *     <pre>
//	 *        List<DeparturePorts> ports = shipCard.getDeparturePorts();
//	 *        String name = ports.get( 1 )
//	 *        CruiseFromPortPage portsPage = shipCard.selectDeparturePort( ports.get( 1 ) );
//	 *     </pre>
//	 * </p>
//	 */
//	@Override
//	public List<DeparturePorts> getDeparturePorts()
//	{
//		return Lists.newArrayList( departurePorts );
//	}
//
//	/**
//	 * {@inheritDoc}
//	 * <p>
//	 *     Method usage:
//	 *     <pre>
//	 *        List<TripDurations> tripDurations = shipCard.getTripDurations();
//	 *        String name = tripDurations.get( 1 )
//	 *        FindACruisePage findCruisePage = shipCard.selectTripDuration( tripDurations.get( 1 ) );
//	 *     </pre>
//	 * </p>
//	 */
//	@Override
//	public List<TripDurations> getTripDurations()
//	{
//		return Lists.newArrayList( tripDurations );
//	}
//
//	/**
//	 * {@inheritDoc}
//	 * <p>
//	 *     the method returns if the span.added-msg and span.compare are visible or not and the span
//	 *     usage:
//	 *     <pre>
//	 *        shipCard.doCompare( true );
//	 *        boolean isComparing = shipCard.isComparing();
//	 *
//	 *        if( ! shipCard.isComparing() )
//	 *        {
//	 *          shipCard.doCompare( true );
//	 *        }
//	 *     </pre>
//	 * </p>
//	 *
//	 * @return {@inheritDoc}
//	 */
//	@Override
//	public boolean isComparing()
//	{
//		try
//		{
//			WebElement compareMsg = findCompareMsgSpan();
//			WebElement addedMsg = findAddedMsgSpan();
//
//			return addedMsg.isDisplayed() && ( ! compareMsg.isDisplayed() );
//		}
//		catch ( Throwable e )
//		{
//			Throwables.propagateIfInstanceOf( e, ApplicationException.class );
//			logger.error( "throwing a new ApplicationException on {}#clickShipTitle.", getClass().getSimpleName() );
//			ApplicationException aex = new ApplicationException( objectDriver.getDriver(), e.getMessage(), e );
//			aex.addInfo( "business process", "failed to click on <\"{}\"> title." + ship );
//			throw aex;
//		}
//	}
//
//	/**
//	 * {@inheritDoc}
//	 * <p>
//	 *     Procedure Steps:
//	 *     <ol>
//	 *         <li>gets the anchor element that matches the argument {@code departurePort}</li>
//	 *        <ul>
//	 *            <li>
//	 *                in case that departure port is invalid, a
//	 *                {@code com.framework.site.exceptions.InvalidDeparturePortException} will be thrown
//	 *            </li>
//	 *        </ul>
//	 *        <li>hovers on the {@code a[text()=port]} web-element to validate {@code text-decoration}</li>
//	 *        <li>clicks on the {@code a[text()=port]} web-element</li>
//	 *     	  <li>return a new instance of {@code CruiseFromPortPage}</li>
//	 *     </ol>
//	 *     Method usage:
//	 *     <pre>
//	 *        List<DeparturePorts> ports = shipCard.getDeparturePorts();
//	 *        String name = ports.get( 1 )
//	 *        CruiseFromPortPage portsPage = shipCard.selectDeparturePort( ports.get( 1 ) );
//	 *
//	 *        DeparturePorts departurePort = DeparturePorts.valueById("HNL");
//	 *        CruiseFromPortPage departurePortPage = shipCard.selectDeparturePort( departurePort );
//	 *
//	 *        DeparturePorts departurePort1 = DeparturePorts.valueByName("Fort Lauderdale, FL");
//	 *        DeparturePorts departurePort2 = DeparturePorts.valueByName("fort lauderdale");
//	 *        CruiseFromPortPage departurePortPage = shipCard.selectDeparturePort( departurePort1 );
//	 *     </pre>
//	 * </p>
//	 *
//	 * @param departurePort the {@linkplain com.framework.site.data.DeparturePorts} value to select.
//	 *
//	 * @return {@inheritDoc}
//	 *
//	 * @throws {@inheritDoc}
//	 */
//	@Override
//	public CruiseFromPortPage selectDeparturePort( final DeparturePorts departurePort )
//	{
//		logger.info( "Selecting ( clicking ) on departure port <\"{}\">", departurePort );
//
//		try
//		{
//			PreConditions.checkNotNull( departurePort, "DeparturePorts argument is null." );
//
//			/* Validates that requested departure port is available on current ship card */
//
//			if( ! departurePorts.contains( departurePort ) )
//			{
//				String names = Lambda.join( this.getDeparturePortNames(), " | " );
//				final String MSG_FMT = "Invalid departure port: <\"%s\">. assigned departure ports for ship <\"%s\"> are <\"%s\"> only.";
//				final String ERR_MSG = String.format( MSG_FMT, departurePort, names, ship );
//				throw new NoSuchDeparturePortException( objectDriver, ERR_MSG );
//			}
//
//			WebElement departurePortAnchor = findDeparturePortAnchor( departurePort );
//			Link departurePortLink = new Link( departurePortAnchor );
//			departurePortLink.hover( true );
//			departurePortLink.click();
//			return new CruiseFromPortPage( objectDriver, departurePort );
//		}
//		catch ( NullPointerException npEx )
//		{
//			logger.error( "throwing a new PreConditionException on {}#selectDeparturePort.", getClass().getSimpleName() );
//			throw new PreConditionException( npEx.getMessage(), npEx );
//		}
//		catch ( Throwable t )
//		{
//			Throwables.propagateIfInstanceOf( t, ApplicationException.class );
//			logger.error( "throwing a new ApplicationException on {}#selectDeparturePort.", getClass().getSimpleName() );
//			ApplicationException appEx = new ApplicationException( objectDriver.getDriver(), t.getMessage(), t );
//			appEx.addInfo( "business process", "failed to select departure port <\"{}\">." + departurePort );
//			throw appEx;
//		}
//	}
//
//	/**
//	 * {@inheritDoc}
//	 * <p>
//	 *     Procedure Steps:
//	 *     <ol>
//	 *        <li>gets the anchor element that matches the argument {@code destination}</li>
//	 *        <ul>
//	 *            <li>
//	 *                in case that departure port is invalid, a
//	 *                {@code com.framework.site.exceptions.NoSuchDestinationException} will be thrown
//	 *            </li>
//	 *        </ul>
//	 *        <li>hovers on the {@code a[text()=destination]} web-element to validate {@code text-decoration}</li>
//	 *        <li>clicks on the {@code a[text()=destination]} web-element</li>
//	 *     	  <li>return a new instance of {@code CruiseToDestinationPage}</li>
//	 *     </ol>
//	 *     Method usage:
//	 *     <pre>
//	 *        List<Destinations> destinations = shipCard.getDestinations();
//	 *        String name = destinations.get( 1 )
//	 *        CruiseToDestinationPage destinationPage = shipCard.selectDestination( destinations.get( 1 ) );
//	 *
//	 *        Destinations destination = Destinations.valueById("BM");
//	 *        CruiseToDestinationPage destinationPage = shipCard.selectDestination( destination );
//	 *
//	 *        Destinations destination = Destinations.valueByName("Bermuda");
//	 *        CruiseToDestinationPage destinationPage = shipCard.selectDestination( destination );
//	 *
//	 *     </pre>
//	 * </p>
//	 *
//	 * @param destination the {@linkplain com.framework.site.data.Destinations} value to select.
//	 *
//	 * @return {@inheritDoc}
//	 *
//	 * @throws {@inheritDoc}
//	 */
//	@Override
//	public CruiseToDestinationPage selectDestination( final Destinations destination )
//	{
//		logger.info( "Selecting ( clicking ) on destination <\"{}\">", destination );
//
//		try
//		{
//			PreConditions.checkNotNull( destination, "Destinations argument is null." );
//
//			/* Validates that requested destination is available on current ship card */
//
//			if( ! destinations.contains( destination ) )
//			{
//				String names = Lambda.join( this.getDestinationNames(), " | " );
//				final String MSG_FMT = "Invalid destination: <\"%s\">. assigned destinations for ship <\"%s\"> are <\"%s\"> only.";
//				final String ERR_MSG = String.format( MSG_FMT, destination, names, ship );
//				throw new NoSuchDestinationException( objectDriver, ERR_MSG );
//			}
//
//			WebElement destinationAnchor = findDestinationAnchor( destination );
//			Link destinationLink = new Link( destinationAnchor );
//			destinationLink.hover( true );
//			destinationLink.click();
//			return new CruiseToDestinationPage( objectDriver, destination );
//		}
//		catch ( NullPointerException npEx )
//		{
//			logger.error( "throwing a new PreConditionException on {}#selectDestination.", getClass().getSimpleName() );
//			throw new PreConditionException( npEx.getMessage(), npEx );
//		}
//		catch ( Throwable t )
//		{
//			Throwables.propagateIfInstanceOf( t, ApplicationException.class );
//			logger.error( "throwing a new ApplicationException on {}#selectDestination.", getClass().getSimpleName() );
//			ApplicationException appEx = new ApplicationException( objectDriver.getDriver(), t.getMessage(), t );
//			appEx.addInfo( "business process", "failed to select destination <\"{}\">." + destination );
//			throw appEx;
//		}
//	}
//
//	/**
//	 * {@inheritDoc}
//	 * <p>
//	 *     Procedure Steps:
//	 *     <ol>
//	 *        <li>gets the anchor element that matches the argument {@code tripDuration}</li>
//	 *        <ul>
//	 *            <li>
//	 *                in case that departure port is invalid, a
//	 *                {@code com.framework.site.exceptions.NoSuchTripDurationException} will be thrown
//	 *            </li>
//	 *        </ul>
//	 *        <li>hovers on the {@code a[text()=tripDuration]} web-element to validate {@code text-decoration}</li>
//	 *        <li>clicks on the {@code a[text()=tripDuration]} web-element</li>
//	 *     	  <li>return a new instance of {@code FindACruisePage}</li>
//	 *     </ol>
//	 *     Method usage:
//	 *     <pre>
//	 *        List<TripDurations> tripDurations = shipCard.getTripDurations();
//	 *        String name = tripDurations.get( 1 )
//	 *        FindACruisePage findCruisePage = shipCard.selectTripDuration( tripDurations.get( 1 ) );
//	 *
//	 *        TripDurations tripDuration = DeparturePorts.valueById("D2");
//	 *        FindACruisePage findCruisePage = shipCard.selectTripDuration( tripDuration );
//	 *
//	 *        TripDurations tripDuration1 = DeparturePorts.valueByName("10+");
//	 *        TripDurations tripDuration2 = DeparturePorts.valueByName("6-9 Days");
//	 *        FindACruisePage findCruisePage = shipCard.selectTripDuration( tripDuration1 );
//	 *     </pre>
//	 * </p>
//	 *
//	 * @param tripDuration the {@linkplain com.framework.site.data.Destinations} value to select.
//	 *
//	 * @return {@inheritDoc}
//	 *
//	 * @throws {@inheritDoc}
//	 */
//	@Override
//	public FindACruisePage selectTripDuration( final TripDurations tripDuration )
//	{
//		logger.info( "Selecting ( clicking ) on trip duration <\"{}\">", tripDuration );
//
//		try
//		{
//			PreConditions.checkNotNull( tripDuration, "TripDurations argument is null." );
//
//			/* Validates that requested destination is available on current ship card */
//
//			if( ! tripDurations.contains( tripDuration ) )
//			{
//				String names = Lambda.join( this.getTripDurationNames(), " | " );
//				final String MSG_FMT = "Invalid trip duration: <\"%s\">. assigned trip durations for ship <\"%s\"> are <\"%s\"> only.";
//				final String ERR_MSG = String.format( MSG_FMT, tripDuration, names, ship );
//				throw new NoSuchTripDurationException( objectDriver, ERR_MSG );
//			}
//
//			WebElement tripDurationAnchor = findTripDurationAnchor( tripDuration );
//			Link tripDurationLink = new Link( tripDurationAnchor );
//			tripDurationLink.hover( true );
//			tripDurationLink.click();
//			return new FindACruisePage( objectDriver, tripDuration );
//		}
//		catch ( NullPointerException npEx )
//		{
//			logger.error( "throwing a new PreConditionException on {}#selectTripDuration.", getClass().getSimpleName() );
//			throw new PreConditionException( npEx.getMessage(), npEx );
//		}
//		catch ( Throwable t )
//		{
//			Throwables.propagateIfInstanceOf( t, ApplicationException.class );
//			logger.error( "throwing a new ApplicationException on {}#selectTripDuration.", getClass().getSimpleName() );
//			ApplicationException appEx = new ApplicationException( objectDriver.getDriver(), t.getMessage(), t );
//			appEx.addInfo( "business process", "failed to select trip duration <\"{}\">." + tripDuration );
//			throw appEx;
//		}
//	}
//
//
//	/**
//	 * {@inheritDoc}
//	 * <p>
//	 *     Procedure Steps:
//	 *     <ol>
//	 *        <li>gets the a.upper and h2.title > a web-elements.</li>
//	 *        <li>hovers on the h2.title > a web-element</li>
//	 *        <li>validates that a.upper web-element is now a.upper.hover</li>
//	 *     	  <li>clicks on h2.title > a web-element</li>
//	 *     </ol>
//	 *     Method usage:
//	 *     <pre>
//	 *        CruiseShipsDetailsPage shipDetailsPage = shipCard.clicksShipImage( true );
//	 *     </pre>
//	 * </p>
//	 *
//	 * @return {@inheritDoc}
//	 */
//	@Override
//	public CruiseShipsDetailsPage clickShipTitle()
//	{
//		WebDriverWait wdw = WaitUtil.wait5( objectDriver );
//		logger.info( "Clicking on <\"{}\"> ship title", ship );
//
//		try
//		{
//			WebElement upper = findUpperAnchor();
//			Link imageTitleLink = new Link( findImageTitleAnchor() );
//			imageTitleLink.hover();
//			wdw.until( WaitUtil.elementAttributeToMatch( upper, "class", MatcherUtils.containsString( "hover" ) ) );
//			imageTitleLink.click();
//			return new CruiseShipsDetailsPage( objectDriver, this.ship );
//		}
//		catch ( Throwable t )
//		{
//			Throwables.propagateIfInstanceOf( t, ApplicationException.class );
//			logger.error( "throwing a new ApplicationException on {}#clickShipTitle.", getClass().getSimpleName() );
//			ApplicationException appEx = new ApplicationException( objectDriver.getDriver(), t.getMessage(), t );
//			appEx.addInfo( "business process", "failed to click on <\"{}\"> title." + ship);
//			throw appEx;
//		}
//	}
//
//	/**
//	 * {@inheritDoc}
//	 * <p>
//	 *     Procedure Steps:
//	 *     <ol>
//	 *        <li>gets the a.upper and h2.title > a web-elements.</li>
//	 *        <li>hovers on the a.upper web-element</li>
//	 *        <li>validates that h2.title > a web-element is now h2.title > a.hover</li>
//	 *     	  <li>clicks on a.upper web-element</li>
//	 *     </ol>
//	 *     Method usage:
//	 *     <pre>
//	 *        CruiseShipsDetailsPage shipDetailsPage = shipCard.clicksShipImage( true );
//	 *     </pre>
//	 * </p>
//	 *
//	 * @return {@inheritDoc}
//	 */
//	@Override
//	public CruiseShipsDetailsPage clickShipImage()
//	{
//		WebDriverWait wdw = WaitUtil.wait5( objectDriver );
//		logger.info( "Clicking on <\"{}\"> ship image", ship );
//
//		try
//		{
//			WebElement title = findImageTitleAnchor();
//			Link upperLink = new Link( findUpperAnchor() );
//			upperLink.hover();
//			wdw.until( WaitUtil.elementAttributeToMatch( title, "class", MatcherUtils.is( "hover" ) ) );
//			upperLink.click();
//			return new CruiseShipsDetailsPage( objectDriver, this.ship );
//		}
//		catch ( Throwable e )
//		{
//			Throwables.propagateIfInstanceOf( e, ApplicationException.class );
//			logger.error( "throwing a new ApplicationException on {}#clickShipImage.", getClass().getSimpleName() );
//			ApplicationException aex = new ApplicationException( objectDriver.getDriver(), e.getMessage(), e );
//			aex.addInfo( "business process", "failed to click on <\"{}\"> image." + ship );
//			throw aex;
//		}
//	}
//
//	@Override
//	public Set<String> getDeparturePortNames()
//	{
//		List<String> names = Lambda.extractString( departurePorts );
//		return Sets.newHashSet( names.iterator() );
//	}
//
//	@Override
//	public Set<String> getDestinationNames()
//	{
//		List<String> names = Lambda.extractString( destinations );
//		return Sets.newHashSet( names.iterator() );
//	}
//
//	@Override
//	public Set<String> getTripDurationNames()
//	{
//		List<String> names = Lambda.extractString( tripDurations );
//		return Sets.newHashSet( names.iterator() );
//	}
//
//	//endregion
//
//
//	//region ShipCardObject - Element Finder Methods Section
//
	private WebElement findImageImg()
	{
		org.openqa.selenium.By findBy = org.openqa.selenium.By.xpath( ".//span[@class='image']/img" );
		//return getRoot().findElement( findBy );
		return null;
	}
//
//	private WebElement findUpperAnchor()
//	{
//		org.openqa.selenium.By findBy = org.openqa.selenium.By.xpath( ".//a[contains(concat(' ',normalize-space(@class),' '),' upper ')]" );
//		return rootElement.findElement( findBy );
//	}
//
//	private WebElement findImageTitleAnchor()
//	{
//		org.openqa.selenium.By findBy = org.openqa.selenium.By.cssSelector( "h2.title > a" );
//		return rootElement.findElement( findBy );
//	}
//
//	private WebElement findCompareMsgSpan()
//	{
//		org.openqa.selenium.By findBy = org.openqa.selenium.By.cssSelector( "a.compare > span.compare-msg" );
//		return rootElement.findElement( findBy );
//	}
//
//	private WebElement findAddedMsgSpan()
//	{
//		org.openqa.selenium.By findBy = org.openqa.selenium.By.cssSelector( "a.compare > span.added-msg" );
//		return rootElement.findElement( findBy );
//	}
//
//	private WebElement findCompareAnchor()
//	{
//		org.openqa.selenium.By findBy = ExtendedBy.composite( org.openqa.selenium.By.tagName( "span" ), org.openqa.selenium.By.className( "compare" ) );
//		return rootElement.findElement( findBy );
//	}
//
//	private WebElement findDeparturePortAnchor( DeparturePorts port )
//	{
//		org.openqa.selenium.By findBy = org.openqa.selenium.By.linkText( port.toString() );
//		return rootElement.findElement( findBy );
//	}
//
//	private WebElement findDestinationAnchor( Destinations destination )
//	{
//		org.openqa.selenium.By findBy = org.openqa.selenium.By.linkText( destination.toString() );
//		return rootElement.findElement( findBy );
//	}
//
//	private WebElement findTripDurationAnchor( TripDurations tripDurations )
//	{
//		org.openqa.selenium.By findBy = org.openqa.selenium.By.linkText( tripDurations.toString() );
//		return rootElement.findElement( findBy );
//	}
//
//	private List<WebElement> getShipDataLis()
//	{
//		org.openqa.selenium.By findBy = org.openqa.selenium.By.tagName( "li" );
//		return rootElement.findElements( findBy );
//	}
//
//	private WebElement findListDataStrong( WebElement listItem )
//	{
//		org.openqa.selenium.By findBy = org.openqa.selenium.By.tagName( "strong" );
//		return listItem.findElement( findBy );
//	}
//
//	private List<WebElement> findListDataSectionAnchors( WebElement listItem )
//	{
//		org.openqa.selenium.By findBy = org.openqa.selenium.By.tagName( "a" );
//		return listItem.findElements( findBy );
//	}

	//endregion

}
