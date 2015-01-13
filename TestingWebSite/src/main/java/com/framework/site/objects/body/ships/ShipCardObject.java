package com.framework.site.objects.body.ships;

import ch.lambdaj.Lambda;
import com.framework.asserts.JAssertions;
import com.framework.driver.exceptions.ApplicationException;
import com.framework.driver.exceptions.PreConditionException;
import com.framework.driver.objects.AbstractWebObject;
import com.framework.driver.objects.Link;
import com.framework.driver.utils.PreConditions;
import com.framework.driver.utils.ui.ExtendedBy;
import com.framework.driver.utils.ui.WaitUtil;
import com.framework.matchers.MatcherUtils;
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
import com.framework.site.pages.core.cruisefrom.CruiseFromPortPage;
import com.framework.site.pages.core.cruiseships.CruiseShipsDetailsPage;
import com.framework.site.pages.core.cruiseto.CruiseToDestinationPage;
import com.google.common.base.MoreObjects;
import com.google.common.base.Throwables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.apache.commons.lang3.text.StrSubstitutor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;


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

	private static final int MAX_COMPARISON_ITEMS = 3;

	// provides a substitutor for replacing keys
	private StrSubstitutor substitutor;

	// reference to the current predefined ship info.
	private Ships ship;

	// a set of predefined destinations, to avoid duplications.
	private Set<Destinations> destinations;

	// a set of predefined departure Ports, to avoid duplications.
	private Set<DeparturePorts> departurePorts;

	// a set of predefined trip durations, to avoid duplications.
	private Set<TripDurations> tripDurations;

	//endregion


	//region ShipCardObject - Constructor Methods Section

	/**
	 * Creates a new instance of a single ship card.
	 *
	 * @param driver        the current working {@code WebDriver}
	 * @param rootElement   the ship card root element.
	 */
	public ShipCardObject( WebDriver driver, final WebElement rootElement )
	{
		super( ShipCard.LOGICAL_NAME, driver, rootElement );
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
		logger.debug( "validating static elements for web object id: <{}>, name:<{}>...", getId(), getLogicalName() );
		logger.info( "Parsing ship card..." );

		try
		{
			parseShipCard();

			/* build assertion details for ship img[src] */

			WebElement img = findImageImg();
			final String EXPECTED_IMG_SRC = substitutor.replace( IMG_FORMAT );
			logger.debug( "Expected image[src] is <\"{}\">", EXPECTED_IMG_SRC );
			final String ACTUAL_IMG_SRC = img.getAttribute( "src" );

			/* build assertion details for ship title a[text()] */

			WebElement anchor = findImageTitleAnchor();
			final String EXPECTED_TEXT = this.ship.getTitle();
			logger.debug( "Expected title[text] is <\"{}\">", EXPECTED_TEXT );
			final String ACTUAL_TEXT = anchor.getText();

			JAssertions.assertThat( ACTUAL_IMG_SRC ).contains( EXPECTED_IMG_SRC );
			JAssertions.assertThat( ACTUAL_TEXT ).isEqualToIgnoringCase( EXPECTED_TEXT );
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


	//region ShipCardObject - Service Methods Section

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper( this )
				.add( "logical name", getLogicalName() )
				.add( "id", getId() )
				.omitNullValues()
				.toString();
	}

	/**
	 * parseShipCard the ship card from {@link #rootElement} and populates the {@linkplain #ship} member and populates
	 * the {@linkplain #destinations}, {@linkplain #departurePorts} and {@linkplain #tripDurations} lists.
	 * also populates the {@linkplain #substitutor}
	 */
	@SuppressWarnings ( { "ForeachStatement", "ForLoopReplaceableByForEach" } )
	private void parseShipCard()
	{
		String dataId = rootElement.getAttribute( "data-id" );

		try
		{
			this.ship = Ships.valueById( dataId );
			if( this.ship == null )
			{
				throw new NoSuchShipException( objectDriver, "The ship id <\"" + dataId + "\"> does not exists" );
			}

			/* parsing destinations */

			List<WebElement> data = getShipDataLis();
			JAssertions.assertThat( data.size() ).isEqualTo( MAX_COMPARISON_ITEMS );
			for ( int i = 0; i < data.size(); i ++ )
			{
				String item = findListDataStrong( data.get( i ) ).getText();

				switch ( i )
				{
					case 0:
					{
						/* validate that first item is 'Destination' */

						JAssertions.assertThat( item ).startsWith( "Destination" );
						destinations = Sets.newHashSet();

						/* get a list of destinations */

						List<WebElement> anchors = findListDataSectionAnchors( data.get( i ) );
						for ( Iterator<WebElement> iterator = anchors.iterator(); iterator.hasNext(); )
						{
							final WebElement anchor = iterator.next();

							/* populating destinations */

							String destinationName = anchor.getText();
							Destinations destination = Destinations.valueByName( destinationName );
							if( destination == null )
							{
								throw new NoSuchDestinationException(
										objectDriver, "The destination <\"" + destinationName + "\"> does not exists" );
							}
							destinations.add( destination );
						}
					}

					case 1:
					{
						/* validate that first item is 'Departure Port' */

						JAssertions.assertThat( item ).startsWith( "Departure Port" );
						departurePorts = Sets.newHashSet();

						/* get a list of departure departurePorts */

						List<WebElement> anchors = findListDataSectionAnchors( data.get( i ) );
						for ( Iterator<WebElement> iterator = anchors.iterator(); iterator.hasNext(); )
						{
							final WebElement anchor = iterator.next();

							/* populating departurePorts */

							String portName = anchor.getText();
							DeparturePorts port = DeparturePorts.valueByName( portName );
							if( port == null )
							{
								throw new NoSuchDeparturePortException( objectDriver, "The departure port <\"" + portName + "\"> does not exists" );
							}
							departurePorts.add( port );
						}
					}

					case 2:
					{
						/* validate that first item is 'Departure Port' */

						JAssertions.assertThat( item ).startsWith( "Trip Duration" );
						tripDurations = Sets.newHashSet();

						/* get a list of trip durations */

						List<WebElement> anchors = findListDataSectionAnchors( data.get( i ) );
						for ( Iterator<WebElement> iterator = anchors.iterator(); iterator.hasNext(); )
						{
							final WebElement anchor = iterator.next();

							/* populating trip durations */

							String durationName = anchor.getText();
							TripDurations duration = TripDurations.valueByName( durationName );
							if( duration == null )
							{
								throw new NoSuchTripDurationException(
										objectDriver, "The departure port <\"" + durationName + "\"> does not exists" );
							}
							tripDurations.add( duration );
						}
					}
					default:
					{
						ApplicationException appEx = new ApplicationException( objectDriver, "invalid ship category found \'" + item + "\"" );
						appEx.addInfo( "cause procedure", "parsing ship card" );
						throw appEx;
					}
				}
			}

			/* populating substitutor */

			Map<String,String> keys = Maps.newHashMap();
			keys.put( "ship.code", this.ship.getId() );
			keys.put( "ship.name.dash.lower.case", this.ship.getTitle().replace( " ", "-" ).toLowerCase() );
			substitutor = new StrSubstitutor( keys );
		}
		catch ( Throwable e )
		{
			Throwables.propagateIfInstanceOf( e, ApplicationException.class );
			logger.error( "throwing a new ApplicationException on {}#parseShipCard.", getClass().getSimpleName() );
			ApplicationException aex = new ApplicationException( objectDriver.getWrappedDriver(), e.getMessage(), e );
			aex.addInfo( "business process", "failed to parseShipCard ship card '" + dataId + "'" );
			throw aex;
		}
	}

	//endregion


	//region ShipCardObject - Business Methods Section

	@Override
	public Ships getShip()
	{
		return ship;
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 *     the method flow is determined by the {@code check} argument and the visibility of
	 *     the span.added-msg and span.compare tags.
	 *     usage:
	 *     <pre>
	 *        shipCard.doCompare( true );
	 *        boolean isComparing = shipCard.isComparing();
	 *
	 *        if( ! shipCard.isComparing() )
	 *        {
	 *          shipCard.doCompare( true );
	 *        }
	 *     </pre>
	 * </p>
	 *
	 * @param check indicates to compare the ship or remove from comparison.
	 */
	@Override
	public void doCompare( final boolean check )
	{
		try
		{
			WebElement compareMsg = findCompareMsgSpan();
			WebElement addedMsg = findAddedMsgSpan();
			WebElement compareAnchor = findCompareAnchor();

			if( check )
			{
				if( compareMsg.isDisplayed() && ( ! addedMsg.isDisplayed() ) )
				{
				 	Link link = new Link( objectDriver, compareAnchor );
					link.hover( true );
					link.click();
				}
			}
		}
		catch ( Throwable e )
		{
			Throwables.propagateIfInstanceOf( e, ApplicationException.class );
			logger.error( "throwing a new ApplicationException on {}#doCompare.", getClass().getSimpleName() );
			ApplicationException aex = new ApplicationException( objectDriver.getWrappedDriver(), e.getMessage(), e );
			aex.addInfo( "business process", "failed set/unset <\"{}\"> comparison." + ship.getTitle());
			throw aex;
		}
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 *     Method usage:
	 *     <pre>
	 *        List<Destinations> destinations = shipCard.getDestinations();
	 *        String name = destinations.get( 1 ).getTitle()
	 *        CruiseToDestinationPage destinationPage = shipCard.selectDestination( destinations.get( 1 ) );
	 *     </pre>
	 * </p>
	 */
	@Override
	public List<Destinations> getDestinations()
	{
		return Lists.newArrayList( destinations );
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 *     Method usage:
	 *     <pre>
	 *        List<DeparturePorts> ports = shipCard.getDeparturePorts();
	 *        String name = ports.get( 1 ).getTitle()
	 *        CruiseFromPortPage portsPage = shipCard.selectDeparturePort( ports.get( 1 ) );
	 *     </pre>
	 * </p>
	 */
	@Override
	public List<DeparturePorts> getDeparturePorts()
	{
		return Lists.newArrayList( departurePorts );
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 *     Method usage:
	 *     <pre>
	 *        List<TripDurations> tripDurations = shipCard.getTripDurations();
	 *        String name = tripDurations.get( 1 ).getTitle()
	 *        FindACruisePage findCruisePage = shipCard.selectTripDuration( tripDurations.get( 1 ) );
	 *     </pre>
	 * </p>
	 */
	@Override
	public List<TripDurations> getTripDurations()
	{
		return Lists.newArrayList( tripDurations );
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 *     the method returns if the span.added-msg and span.compare are visible or not and the span
	 *     usage:
	 *     <pre>
	 *        shipCard.doCompare( true );
	 *        boolean isComparing = shipCard.isComparing();
	 *
	 *        if( ! shipCard.isComparing() )
	 *        {
	 *          shipCard.doCompare( true );
	 *        }
	 *     </pre>
	 * </p>
	 *
	 * @return {@inheritDoc}
	 */
	@Override
	public boolean isComparing()
	{
		try
		{
			WebElement compareMsg = findCompareMsgSpan();
			WebElement addedMsg = findAddedMsgSpan();

			return addedMsg.isDisplayed() && ( ! compareMsg.isDisplayed() );
		}
		catch ( Throwable e )
		{
			Throwables.propagateIfInstanceOf( e, ApplicationException.class );
			logger.error( "throwing a new ApplicationException on {}#clickShipTitle.", getClass().getSimpleName() );
			ApplicationException aex = new ApplicationException( objectDriver.getWrappedDriver(), e.getMessage(), e );
			aex.addInfo( "business process", "failed to click on <\"{}\"> title." + ship.getTitle());
			throw aex;
		}
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 *     Procedure Steps:
	 *     <ol>
	 *         <li>gets the anchor element that matches the argument {@code departurePort}</li>
	 *        <ul>
	 *            <li>
	 *                in case that departure port is invalid, a
	 *                {@code com.framework.site.exceptions.InvalidDeparturePortException} will be thrown
	 *            </li>
	 *        </ul>
	 *        <li>hovers on the {@code a[text()=port]} web-element to validate {@code text-decoration}</li>
	 *        <li>clicks on the {@code a[text()=port]} web-element</li>
	 *     	  <li>return a new instance of {@code CruiseFromPortPage}</li>
	 *     </ol>
	 *     Method usage:
	 *     <pre>
	 *        List<DeparturePorts> ports = shipCard.getDeparturePorts();
	 *        String name = ports.get( 1 ).getTitle()
	 *        CruiseFromPortPage portsPage = shipCard.selectDeparturePort( ports.get( 1 ) );
	 *
	 *        DeparturePorts departurePort = DeparturePorts.valueById("HNL");
	 *        CruiseFromPortPage departurePortPage = shipCard.selectDeparturePort( departurePort );
	 *
	 *        DeparturePorts departurePort1 = DeparturePorts.valueByName("Fort Lauderdale, FL");
	 *        DeparturePorts departurePort2 = DeparturePorts.valueByName("fort lauderdale");
	 *        CruiseFromPortPage departurePortPage = shipCard.selectDeparturePort( departurePort1 );
	 *     </pre>
	 * </p>
	 *
	 * @param departurePort the {@linkplain com.framework.site.data.DeparturePorts} value to select.
	 *
	 * @return {@inheritDoc}
	 *
	 * @throws {@inheritDoc}
	 */
	@Override
	public CruiseFromPortPage selectDeparturePort( final DeparturePorts departurePort )
	{
		logger.info( "Selecting ( clicking ) on departure port <\"{}\">", departurePort.getTitle() );

		try
		{
			PreConditions.checkNotNull( departurePort, "DeparturePorts argument is null." );

			/* Validates that requested departure port is available on current ship card */

			if( ! departurePorts.contains( departurePort ) )
			{
				String names = Lambda.join( this.getDeparturePortNames(), " | " );
				final String MSG_FMT = "Invalid departure port: <\"%s\">. assigned departure ports for ship <\"%s\"> are <\"%s\"> only.";
				final String ERR_MSG = String.format( MSG_FMT, departurePort.getTitle(), names, ship.getTitle() );
				throw new NoSuchDeparturePortException( objectDriver, ERR_MSG );
			}

			WebElement departurePortAnchor = findDeparturePortAnchor( departurePort );
			Link departurePortLink = new Link( objectDriver, departurePortAnchor );
			departurePortLink.hover( true );
			departurePortLink.click();
			return new CruiseFromPortPage( objectDriver, departurePort );
		}
		catch ( NullPointerException npEx )
		{
			logger.error( "throwing a new PreConditionException on {}#selectDeparturePort.", getClass().getSimpleName() );
			throw new PreConditionException( npEx.getMessage(), npEx );
		}
		catch ( Throwable t )
		{
			Throwables.propagateIfInstanceOf( t, ApplicationException.class );
			logger.error( "throwing a new ApplicationException on {}#selectDeparturePort.", getClass().getSimpleName() );
			ApplicationException appEx = new ApplicationException( objectDriver.getWrappedDriver(), t.getMessage(), t );
			appEx.addInfo( "business process", "failed to select departure port <\"{}\">." + departurePort.getTitle());
			throw appEx;
		}
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 *     Procedure Steps:
	 *     <ol>
	 *        <li>gets the anchor element that matches the argument {@code destination}</li>
	 *        <ul>
	 *            <li>
	 *                in case that departure port is invalid, a
	 *                {@code com.framework.site.exceptions.NoSuchDestinationException} will be thrown
	 *            </li>
	 *        </ul>
	 *        <li>hovers on the {@code a[text()=destination]} web-element to validate {@code text-decoration}</li>
	 *        <li>clicks on the {@code a[text()=destination]} web-element</li>
	 *     	  <li>return a new instance of {@code CruiseToDestinationPage}</li>
	 *     </ol>
	 *     Method usage:
	 *     <pre>
	 *        List<Destinations> destinations = shipCard.getDestinations();
	 *        String name = destinations.get( 1 ).getTitle()
	 *        CruiseToDestinationPage destinationPage = shipCard.selectDestination( destinations.get( 1 ) );
	 *
	 *        Destinations destination = Destinations.valueById("BM");
	 *        CruiseToDestinationPage destinationPage = shipCard.selectDestination( destination );
	 *
	 *        Destinations destination = Destinations.valueByName("Bermuda");
	 *        CruiseToDestinationPage destinationPage = shipCard.selectDestination( destination );
	 *
	 *     </pre>
	 * </p>
	 *
	 * @param destination the {@linkplain com.framework.site.data.Destinations} value to select.
	 *
	 * @return {@inheritDoc}
	 *
	 * @throws {@inheritDoc}
	 */
	@Override
	public CruiseToDestinationPage selectDestination( final Destinations destination )
	{
		logger.info( "Selecting ( clicking ) on destination <\"{}\">", destination.getTitle() );

		try
		{
			PreConditions.checkNotNull( destination, "Destinations argument is null." );

			/* Validates that requested destination is available on current ship card */

			if( ! destinations.contains( destination ) )
			{
				String names = Lambda.join( this.getDestinationNames(), " | " );
				final String MSG_FMT = "Invalid destination: <\"%s\">. assigned destinations for ship <\"%s\"> are <\"%s\"> only.";
				final String ERR_MSG = String.format( MSG_FMT, destination.getTitle(), names, ship.getTitle() );
				throw new NoSuchDestinationException( objectDriver, ERR_MSG );
			}

			WebElement destinationAnchor = findDestinationAnchor( destination );
			Link destinationLink = new Link( objectDriver, destinationAnchor );
			destinationLink.hover( true );
			destinationLink.click();
			return new CruiseToDestinationPage( objectDriver, destination );
		}
		catch ( NullPointerException npEx )
		{
			logger.error( "throwing a new PreConditionException on {}#selectDestination.", getClass().getSimpleName() );
			throw new PreConditionException( npEx.getMessage(), npEx );
		}
		catch ( Throwable t )
		{
			Throwables.propagateIfInstanceOf( t, ApplicationException.class );
			logger.error( "throwing a new ApplicationException on {}#selectDestination.", getClass().getSimpleName() );
			ApplicationException appEx = new ApplicationException( objectDriver.getWrappedDriver(), t.getMessage(), t );
			appEx.addInfo( "business process", "failed to select destination <\"{}\">." + destination.getTitle() );
			throw appEx;
		}
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 *     Procedure Steps:
	 *     <ol>
	 *        <li>gets the anchor element that matches the argument {@code tripDuration}</li>
	 *        <ul>
	 *            <li>
	 *                in case that departure port is invalid, a
	 *                {@code com.framework.site.exceptions.NoSuchTripDurationException} will be thrown
	 *            </li>
	 *        </ul>
	 *        <li>hovers on the {@code a[text()=tripDuration]} web-element to validate {@code text-decoration}</li>
	 *        <li>clicks on the {@code a[text()=tripDuration]} web-element</li>
	 *     	  <li>return a new instance of {@code FindACruisePage}</li>
	 *     </ol>
	 *     Method usage:
	 *     <pre>
	 *        List<TripDurations> tripDurations = shipCard.getTripDurations();
	 *        String name = tripDurations.get( 1 ).getTitle()
	 *        FindACruisePage findCruisePage = shipCard.selectTripDuration( tripDurations.get( 1 ) );
	 *
	 *        TripDurations tripDuration = DeparturePorts.valueById("D2");
	 *        FindACruisePage findCruisePage = shipCard.selectTripDuration( tripDuration );
	 *
	 *        TripDurations tripDuration1 = DeparturePorts.valueByName("10+");
	 *        TripDurations tripDuration2 = DeparturePorts.valueByName("6-9 Days");
	 *        FindACruisePage findCruisePage = shipCard.selectTripDuration( tripDuration1 );
	 *     </pre>
	 * </p>
	 *
	 * @param tripDuration the {@linkplain com.framework.site.data.Destinations} value to select.
	 *
	 * @return {@inheritDoc}
	 *
	 * @throws {@inheritDoc}
	 */
	@Override
	public FindACruisePage selectTripDuration( final TripDurations tripDuration )
	{
		logger.info( "Selecting ( clicking ) on trip duration <\"{}\">", tripDuration.getTitle() );

		try
		{
			PreConditions.checkNotNull( tripDuration, "TripDurations argument is null." );

			/* Validates that requested destination is available on current ship card */

			if( ! tripDurations.contains( tripDuration ) )
			{
				String names = Lambda.join( this.getTripDurationNames(), " | " );
				final String MSG_FMT = "Invalid trip duration: <\"%s\">. assigned trip durations for ship <\"%s\"> are <\"%s\"> only.";
				final String ERR_MSG = String.format( MSG_FMT, tripDuration.getTitle(), names, ship.getTitle() );
				throw new NoSuchTripDurationException( objectDriver, ERR_MSG );
			}

			WebElement tripDurationAnchor = findTripDurationAnchor( tripDuration );
			Link tripDurationLink = new Link( objectDriver, tripDurationAnchor );
			tripDurationLink.hover( true );
			tripDurationLink.click();
			return new FindACruisePage( objectDriver, tripDuration );
		}
		catch ( NullPointerException npEx )
		{
			logger.error( "throwing a new PreConditionException on {}#selectTripDuration.", getClass().getSimpleName() );
			throw new PreConditionException( npEx.getMessage(), npEx );
		}
		catch ( Throwable t )
		{
			Throwables.propagateIfInstanceOf( t, ApplicationException.class );
			logger.error( "throwing a new ApplicationException on {}#selectTripDuration.", getClass().getSimpleName() );
			ApplicationException appEx = new ApplicationException( objectDriver.getWrappedDriver(), t.getMessage(), t );
			appEx.addInfo( "business process", "failed to select trip duration <\"{}\">." + tripDuration.getTitle() );
			throw appEx;
		}
	}


	/**
	 * {@inheritDoc}
	 * <p>
	 *     Procedure Steps:
	 *     <ol>
	 *        <li>gets the a.upper and h2.title > a web-elements.</li>
	 *        <li>hovers on the h2.title > a web-element</li>
	 *        <li>validates that a.upper web-element is now a.upper.hover</li>
	 *     	  <li>clicks on h2.title > a web-element</li>
	 *     </ol>
	 *     Method usage:
	 *     <pre>
	 *        CruiseShipsDetailsPage shipDetailsPage = shipCard.clicksShipImage( true );
	 *     </pre>
	 * </p>
	 *
	 * @return {@inheritDoc}
	 */
	@Override
	public CruiseShipsDetailsPage clickShipTitle()
	{
		WebDriverWait wdw = WaitUtil.wait5( objectDriver );
		logger.info( "Clicking on <\"{}\"> ship title", ship.getTitle() );

		try
		{
			WebElement upper = findUpperAnchor();
			Link imageTitleLink = new Link( objectDriver, findImageTitleAnchor() );
			imageTitleLink.hover();
			wdw.until( WaitUtil.elementAttributeToMatch( upper, "class", MatcherUtils.containsString( "hover" ) ) );
			imageTitleLink.click();
			return new CruiseShipsDetailsPage( objectDriver, this.ship );
		}
		catch ( Throwable t )
		{
			Throwables.propagateIfInstanceOf( t, ApplicationException.class );
			logger.error( "throwing a new ApplicationException on {}#clickShipTitle.", getClass().getSimpleName() );
			ApplicationException appEx = new ApplicationException( objectDriver.getWrappedDriver(), t.getMessage(), t );
			appEx.addInfo( "business process", "failed to click on <\"{}\"> title." + ship.getTitle());
			throw appEx;
		}
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 *     Procedure Steps:
	 *     <ol>
	 *        <li>gets the a.upper and h2.title > a web-elements.</li>
	 *        <li>hovers on the a.upper web-element</li>
	 *        <li>validates that h2.title > a web-element is now h2.title > a.hover</li>
	 *     	  <li>clicks on a.upper web-element</li>
	 *     </ol>
	 *     Method usage:
	 *     <pre>
	 *        CruiseShipsDetailsPage shipDetailsPage = shipCard.clicksShipImage( true );
	 *     </pre>
	 * </p>
	 *
	 * @return {@inheritDoc}
	 */
	@Override
	public CruiseShipsDetailsPage clickShipImage()
	{
		WebDriverWait wdw = WaitUtil.wait5( objectDriver );
		logger.info( "Clicking on <\"{}\"> ship image", ship.getTitle() );

		try
		{
			WebElement title = findImageTitleAnchor();
			Link upperLink = new Link( objectDriver, findUpperAnchor() );
			upperLink.hover();
			wdw.until( WaitUtil.elementAttributeToMatch( title, "class", MatcherUtils.is( "hover" ) ) );
			upperLink.click();
			return new CruiseShipsDetailsPage( objectDriver, this.ship );
		}
		catch ( Throwable e )
		{
			Throwables.propagateIfInstanceOf( e, ApplicationException.class );
			logger.error( "throwing a new ApplicationException on {}#clickShipImage.", getClass().getSimpleName() );
			ApplicationException aex = new ApplicationException( objectDriver.getWrappedDriver(), e.getMessage(), e );
			aex.addInfo( "business process", "failed to click on <\"{}\"> image." + ship.getTitle());
			throw aex;
		}
	}

	@Override
	public Set<String> getDeparturePortNames()
	{
		List<String> names = Lambda.extract( departurePorts, Lambda.of( DeparturePorts.class ).getTitle() );
		return Sets.newHashSet( names.iterator() );
	}

	@Override
	public Set<String> getDestinationNames()
	{
		List<String> names = Lambda.extract( destinations, Lambda.of( Destinations.class ).getTitle() );
		return Sets.newHashSet( names.iterator() );
	}

	@Override
	public Set<String> getTripDurationNames()
	{
		List<String> names = Lambda.extract( tripDurations, Lambda.of( TripDurations.class ).getTitle() );
		return Sets.newHashSet( names.iterator() );
	}

	//endregion


	//region ShipCardObject - Element Finder Methods Section

	private WebElement findImageImg()
	{
		By findBy = By.xpath( ".//span[@class='image']/img" );
		return rootElement.findElement( findBy );
	}

	private WebElement findUpperAnchor()
	{
		By findBy = By.xpath( ".//a[contains(concat(' ',normalize-space(@class),' '),' upper ')]" );
		return rootElement.findElement( findBy );
	}

	private WebElement findImageTitleAnchor()
	{
		By findBy = By.cssSelector( "h2.title > a" );
		return rootElement.findElement( findBy );
	}

	private WebElement findCompareMsgSpan()
	{
		By findBy = By.cssSelector( "a.compare > span.compare-msg" );
		return rootElement.findElement( findBy );
	}

	private WebElement findAddedMsgSpan()
	{
		By findBy = By.cssSelector( "a.compare > span.added-msg" );
		return rootElement.findElement( findBy );
	}

	private WebElement findCompareAnchor()
	{
		By findBy = ExtendedBy.composite( By.tagName( "span" ), By.className( "compare" ) );
		return rootElement.findElement( findBy );
	}

	private WebElement findDeparturePortAnchor( DeparturePorts port )
	{
		By findBy = By.linkText( port.getTitle() );
		return rootElement.findElement( findBy );
	}

	private WebElement findDestinationAnchor( Destinations destination )
	{
		By findBy = By.linkText( destination.getTitle() );
		return rootElement.findElement( findBy );
	}

	private WebElement findTripDurationAnchor( TripDurations tripDurations )
	{
		By findBy = By.linkText( tripDurations.getTitle() );
		return rootElement.findElement( findBy );
	}

	private List<WebElement> getShipDataLis()
	{
		By findBy = By.tagName( "li" );
		return rootElement.findElements( findBy );
	}

	private WebElement findListDataStrong( WebElement listItem )
	{
		By findBy = By.tagName( "strong" );
		return listItem.findElement( findBy );
	}

	private List<WebElement> findListDataSectionAnchors( WebElement listItem )
	{
		By findBy = By.tagName( "a" );
		return listItem.findElements( findBy );
	}

	//endregion

}
