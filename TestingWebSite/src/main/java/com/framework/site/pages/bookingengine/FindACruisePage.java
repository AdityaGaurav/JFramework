package com.framework.site.pages.bookingengine;

import com.framework.driver.event.ExpectedConditions;
import com.framework.driver.event.HtmlDriverWait;
import com.framework.driver.event.HtmlElement;
import com.framework.driver.extensions.jquery.By;
import com.framework.site.data.DeparturePorts;
import com.framework.site.data.Destinations;
import com.framework.site.data.Ships;
import com.framework.site.data.TripDurations;
import com.framework.site.pages.BaseCarnivalPage;
import com.framework.testing.annotations.DefaultUrl;
import com.framework.utils.datetime.Sleeper;
import com.framework.utils.datetime.TimeConstants;
import com.framework.utils.error.PreConditions;
import com.framework.utils.matchers.JMatchers;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.RandomUtils;
import org.joda.time.DateTime;
import org.openqa.selenium.InvalidElementStateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.site.pages.core
 *
 * Name   : CruiseToPage
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-01-10
 *
 * Time   : 01:17
 */

@DefaultUrl( value = "/find-a-cruise", matcher = "endsWith()" )
public class FindACruisePage extends BaseCarnivalPage
{

	//region FindACruisePage - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( FindACruisePage.class );

	private static final String LOGICAL_NAME = "Find a Cruise Page";

	public enum NumberOfTravelers{ ONE, TWO, THREE, FOUR, FIVE, MULTIPLE }

	// ------------------------------------------------------------------------|
	// --- WEB-OBJECTS DEFINITIONS --------------------------------------------|
	// ------------------------------------------------------------------------|

	// ------------------------------------------------------------------------|
	// --- WEB-OBJECTS CACHING ------------------------------------------------|
	// ------------------------------------------------------------------------|

	private List<HtmlElement> loaders = Lists.newArrayList();

	private HtmlElement check_list;

	//endregion


	//region FindACruisePage - Constructor Methods Section

	public FindACruisePage()
	{
		super( LOGICAL_NAME );
		validatePageInitialState();
	}

	//endregion


	//region FindACruisePage - Initialization and Validation Methods Section

	@Override
	protected void validatePageInitialState()
	{
		logger.debug( "validating static elements for: <{}>, name:<{}>...", getQualifier(), getLogicalName() );

		loaders = getDriver().findElements( By.cssSelector( "i.loader" ) );
		HtmlDriverWait.wait10( getDriver() ).until(
				ExpectedConditions.visibilityOfAll( loaders, false ) );
	}

	//endregion


	//region StateRoomsPage - Service Methods Section

	private void doSelectFromDropDown( HtmlElement selectDD, HtmlElement target )
	{
		HtmlElement toggle = selectDD.findElement( By.className( "toggle" ) );
		HtmlElement menu = selectDD.findElement( By.className( "menu" ) );
		toggle.click();
		selectDD.waitAttributeToMatch( "class", JMatchers.endsWith( "active" ), TimeConstants.FIVE_SECONDS );
		menu.waitToBeDisplayed( true, TimeConstants.THREE_SECONDS );

		HtmlElement scrollDown = selectDD.findElement( By.cssSelector( "a.scroller.scroll-down" ) );
		while( ! target.isDisplayed() )
		{
			scrollDown.click();
			Sleeper.pauseFor( 100 );
		}
		target.click();
		HtmlDriverWait.wait10( getDriver() ).until(
				ExpectedConditions.visibilityOfAll( loaders, false ) );
	}

	private void doSelectCheckbox( HtmlElement he, boolean check )
	{
		String className = he.getAttribute( "class" );
		if( "selected".equals( className ) && ( ! check ) )
		{
			he.click();
			he.waitAttributeToMatch( "class", JMatchers.is( "" ), TimeConstants.TWO_SECONDS );
		}
		else if( className.equals( "" ) && check )
		{
			he.click();
			he.waitAttributeToMatch( "class", JMatchers.is( "selected" ), TimeConstants.TWO_SECONDS );
		}
	}

	//endregion


	//region FindACruisePage - Business Methods Section

	public String selectNumberOfTravelers( NumberOfTravelers travelers )
	{
		PreConditions.checkNotNull( travelers, "NumberOfTravelers argument port cannot be null" );
		logger.info( "Selecting \"Number of Travelers\" < {} >", travelers );
		String dataId = null;
		switch( travelers )
		{
			case ONE:
				dataId = "1";
				break;
			case TWO:
				dataId = "2";
				break;
			case THREE:
				dataId = "3";
				break;
			case FOUR:
				dataId = "4";
				break;
			case FIVE:
				dataId = "5";
				break;
			case MULTIPLE:
				dataId = "99";
		}
		HtmlElement he = findTravelerAnchor( dataId );
		he.click();
		he.waitAttributeToMatch( "class", JMatchers.is( "selected" ), TimeConstants.FIVE_SECONDS );
		return dataId;
	}

	public String selectNumberOfTravelers()
	{
		List<HtmlElement> anchors = findTravelersAnchors();
		int selected = RandomUtils.nextInt( 0, anchors.size() - 1 );
		logger.info( "Selecting random number of \"Number of Travelers\" < {} >", selected );
		HtmlElement he = anchors.get( selected );
		he.click();
		he.waitAttributeToMatch( "class", JMatchers.is( "selected" ), TimeConstants.FIVE_SECONDS );
		return he.getAttribute( "data-id" );
	}

	public String selectSailTo( Destinations destinations )
	{
		PreConditions.checkNotNull( destinations, "Destinations argument port cannot be null" );
		logger.info( "Selecting \"Sail To\" < {} >", destinations.name() );
		HtmlElement selectDD = getDriver().findElement( By.cssSelector( "div.select-dd[data-param='dest']" ) );
		HtmlElement target = selectDD.findElement( By.cssSelector( ".menu li a[data-id='" + destinations.getId() + "']" ) );
		doSelectFromDropDown( selectDD, target );
		return target.getAttribute( "data-id" );
	}

	public String selectSailTo( int index )
	{
		logger.info( "Selecting \"Sail To\" index < {} >", index );
		PreConditions.checkArgument( index > 0, "The index must be greater than 0" );
		HtmlElement selectDD = getDriver().findElement( By.cssSelector( "div.select-dd[data-param='dest']" ) );
		HtmlElement target = selectDD.findElement( By.cssSelector( "li:nth-child(" + index + ")" ) );
		doSelectFromDropDown( selectDD, target );
		return target.getAttribute( "data-id" );
	}

	public String selectSailTo()
	{
		HtmlElement selectDD = getDriver().findElement( By.cssSelector( "div.select-dd[data-param='dest']" ) );
		List<HtmlElement> targets = selectDD.findElements( By.tagName( "li" ) );
		int selected = RandomUtils.nextInt( 1, targets.size() - 1 );
		logger.info( "Selecting a random \"Sail To\". selected index is < {} >", selected );
		doSelectFromDropDown( selectDD, targets.get( selected ) );
		return targets.get( selected ).getAttribute( "data-id" );
	}

	public String selectDateFrom( DateTime dte )
	{
		PreConditions.checkNotNull( dte, "DateTime argument port cannot be null" );
		String dataId = dte.toString( "MMddyyyy" );
		logger.info( "Selecting \"Date From\" < {} >", dataId );
		HtmlElement selectDD = getDriver().findElement( By.cssSelector( "div.select-dd[data-param='datFrom']" ) );
		HtmlElement target = selectDD.findElement( By.cssSelector( "a[data-id='" + dataId + "']" ) );
		doSelectFromDropDown( selectDD, target );
		return target.getAttribute( "data-id" );
	}

	public String selectDateFrom()
	{
		HtmlElement selectDD = getDriver().findElement( By.cssSelector( "div.select-dd[data-param='datFrom']" ) );
		List<HtmlElement> targets = selectDD.findElements( By.cssSelector( "div.menu .overview a" ) );
		int selected = RandomUtils.nextInt( 1, targets.size() );
		logger.info( "Selecting a random \"Date From\", selected index < {} >", selected );
		doSelectFromDropDown( selectDD, targets.get( selected ) );
		return targets.get( selected ).getAttribute( "data-id" );
	}

	public String selectDateTo( DateTime dte )
	{
		PreConditions.checkNotNull( dte, "DateTime argument port cannot be null" );
		HtmlElement selectDD = getDriver().findElement( By.cssSelector( "div.select-dd[data-param='datTo']" ) );
		String className = selectDD.getAttribute( "class" );
		PreConditions.checkState( ! className.endsWith( "disabled" ), "Select Date To is currently disabled" );

		String dataId = dte.toString( "MMddyyyy" );
		logger.info( "Selecting \"Date To\" < {} >", dataId );
		HtmlElement target = selectDD.findElement( By.cssSelector( "a[data-id='" + dataId + "']" ) );
		doSelectFromDropDown( selectDD, target );
		return target.getAttribute( "data-id" );
	}

	public String selectDateTo()
	{
		HtmlElement selectDD = getDriver().findElement( By.cssSelector( "div.select-dd[data-param='datTo']" ) );
		String className = selectDD.getAttribute( "class" );
		PreConditions.checkState( ! className.endsWith( "disabled" ), "Select Date To is currently disabled" );

		List<HtmlElement> targets = selectDD.findElements( By.cssSelector( "div.menu .overview a" ) );
		int selected = RandomUtils.nextInt( 1, targets.size() );
		logger.info( "Selecting a random \"Date To\", selected index < {} >", selected );
		doSelectFromDropDown( selectDD, targets.get( selected ) );
		return targets.get( selected ).getAttribute( "data-id" );
	}

	public boolean isDurationAvailable( TripDurations duration )
	{
		Sleeper.pauseFor( 1000 );
		HtmlElement toggle = getDriver().findElement( By.cssSelector( ".toggle-buttons[data-param='dur']" ) );
		HtmlElement target = toggle.findElement( By.cssSelector( "a[data-Id='" + duration.getId() + "']" ) );
		boolean disabled = target.getAttribute( "class" ).endsWith( "disabled" );
		boolean enabled = ! disabled;
		logger.info( "Determine if duration < {} > is available < {} >", duration.name(), enabled );

		return enabled;
	}

	public String selectCruiseLength( TripDurations duration )
	{
		PreConditions.checkNotNull( duration, "TripDurations argument port cannot be null" );
		logger.info( "Selecting \"Cruise Length\" < {} >", duration.name() );
		HtmlElement toggle = getDriver().findElement( By.cssSelector( ".toggle-buttons[data-param='dur']" ) );
		HtmlElement target = toggle.findElement( By.cssSelector( "a[data-Id='" + duration.getId() + "']" ) );
		if( target.getAttribute( "class" ).endsWith( "disabled" ) )
		{
			throw new InvalidElementStateException( "The trip duration " + duration.getTitle() + " is disabled" );
		}
		Sleeper.pauseFor( 500 );
		target.click();
		target.waitAttributeToMatch( "class", JMatchers.is( "selected" ), TimeConstants.FIVE_SECONDS );
		return duration.getId();
	}

	public String selectCruiseLength()
	{
		HtmlElement toggle = getDriver().findElement( By.cssSelector( ".toggle-buttons[data-param='dur']" ) );
		int selected = RandomUtils.nextInt( 0, 2 );
		TripDurations duration = TripDurations.values()[ selected ];
		logger.info( "Selecting a random \"Cruise Length\" < {} >", duration.name() );
		HtmlElement target = toggle.findElement( By.cssSelector( "a[dataId='" + duration.getId() + "']" ) );
		target.click();
		target.waitAttributeToMatch( "class", JMatchers.is( "selected" ), TimeConstants.FIVE_SECONDS );
		return duration.getId();
	}

	public String selectSailFrom( DeparturePorts port )
	{
		PreConditions.checkNotNull( port, "DeparturePorts argument port cannot be null" );
		logger.info( "Selecting \"Sail From\" length < {} >", port.name() );
		HtmlElement selectDD = getDriver().findElement( By.cssSelector( "div.select-dd[data-param='port']" ) );
		HtmlElement target = selectDD.findElement( By.cssSelector( ".menu li a[data-id='" + port.getId() + "']" ) );
		doSelectFromDropDown( selectDD, target );
		return target.getAttribute( "data-id" );
	}

	public String selectSailFrom( int index )
	{
		logger.info( "Selecting \"Sail From\" index < {} >", index );
		PreConditions.checkArgument( index > 0, "The index must be greater than 0" );
		HtmlElement selectDD = getDriver().findElement( By.cssSelector( "div.select-dd[data-param='port']" ) );
		HtmlElement target = selectDD.findElement( By.cssSelector( "li:nth-child(" + index + ")" ) );
		doSelectFromDropDown( selectDD, target );
		return target.getAttribute( "data-id" );
	}

	public String selectSailFrom()
	{
		HtmlElement selectDD = getDriver().findElement( By.cssSelector( "div.select-dd[data-param='port']" ) );
		List<HtmlElement> targets = selectDD.findElements( By.tagName( "li" ) );
		int selected = RandomUtils.nextInt( 1, targets.size() - 1 );
		logger.info( "Selecting a random \"Sail From\". selected index is < {} >", selected );
		doSelectFromDropDown( selectDD, targets.get( selected ) );
		return targets.get( selected ).getAttribute( "data-id" );
	}

	public String selectCruiseShip( Ships ship )
	{
		PreConditions.checkNotNull( ship, "Ships argument port cannot be null" );

		logger.info( "Selecting \"Cruise Ship\" < {} >", ship.name() );
		HtmlElement selectDD = getDriver().findElement( By.cssSelector( "div.select-dd[data-param='shipCode']" ) );
		HtmlElement target = selectDD.findElement( By.cssSelector( ".menu li a[data-id='" + ship.getId() + "']" ) );
		String id = target.getAttribute( "data-id" );
		doSelectFromDropDown( selectDD, target );
		return id;
	}

	public String selectCruiseShip( int index )
	{
		logger.info( "Selecting \"Cruise Ship\" index < {} >", index );
		PreConditions.checkArgument( index > 0, "The index must be greater than 0" );
		HtmlElement selectDD = getDriver().findElement( By.cssSelector( "div.select-dd[data-param='shipCode']" ) );
		HtmlElement target = selectDD.findElement( By.cssSelector( "li:nth-child(" + index + ")" ) );
		doSelectFromDropDown( selectDD, target );
		return target.getAttribute( "data-id" );
	}

	public String selectCruiseShip()
	{
		HtmlElement selectDD = getDriver().findElement( By.cssSelector( "div.select-dd[data-param='shipCode']" ) );
		List<HtmlElement> targets = selectDD.findElements( By.tagName( "li" ) );
		int selected = RandomUtils.nextInt( 1, targets.size() - 1 );
		logger.info( "Selecting a random \"Cruise Ship\". selected index is < {} >", selected );
		doSelectFromDropDown( selectDD, targets.get( selected ) );
		return targets.get( selected ).getAttribute( "data-id" );
	}

	public String selectPerformer( int index )
	{
		logger.info( "Selecting \"Performer\" index < {} >", index );
		PreConditions.checkArgument( index > 0, "The index must be greater than 0" );
		HtmlElement selectDD = getDriver().findElement( By.cssSelector( "div.select-dd[data-param='event']" ) );
		HtmlElement target = selectDD.findElement( By.cssSelector( "li:nth-child(" + index + ")" ) );
		doSelectFromDropDown( selectDD, target );
		return target.getAttribute( "data-id" );
	}

	public String selectPerformer()
	{
		HtmlElement selectDD = getDriver().findElement( By.cssSelector( "div.select-dd[data-param='event']" ) );
		List<HtmlElement> targets = selectDD.findElements( By.tagName( "li" ) );
		int selected = RandomUtils.nextInt( 1, targets.size() - 1 );
		logger.info( "Selecting a random \"Performer\". selected index is < {} >", selected );
		doSelectFromDropDown( selectDD, targets.get( selected ) );
		return targets.get( selected ).getAttribute( "data-id" );
	}

	public String selectState( int index )
	{
		logger.info( "Selecting \"State\" index < {} >", index );
		PreConditions.checkArgument( index > 0, "The index must be greater than 0" );
		HtmlElement selectDD = getDriver().findElement( By.cssSelector( "div.select-dd[data-param='StateCode']" ) );
		HtmlElement target = selectDD.findElement( By.cssSelector( "li:nth-child(" + index + ")" ) );
		doSelectFromDropDown( selectDD, target );
		return target.getAttribute( "data-id" );
	}

	public String selectState()
	{
		HtmlElement selectDD = getDriver().findElement( By.cssSelector( "div.select-dd[data-param='StateCode']" ) );
		List<HtmlElement> targets = selectDD.findElements( By.tagName( "li" ) );
		int selected = RandomUtils.nextInt( 1, targets.size() - 1 );
		logger.info( "Selecting a random \"State\". selected index is < {} >", selected );
		doSelectFromDropDown( selectDD, targets.get( selected ) );
		return targets.get( selected ).getAttribute( "data-id" );
	}

	public void selectOver55( boolean check )
	{
		HtmlElement he = getDriver().findElement( By.cssSelector( "a.alt-info[data-param='Senior']" ) );
		doSelectCheckbox( he, check );
	}

	public void selectMilitary( boolean check )
	{
		HtmlElement he = getDriver().findElement( By.cssSelector( "a.alt-info[data-param='Military']" ) );
		doSelectCheckbox( he, check );
	}

	public void selectVifpClub( boolean check )
	{
		HtmlElement he = getDriver().findElement( By.cssSelector( "a.alt-info[data-param='PastGuest']" ) );
		HtmlElement guest_number = getDriver().findElement( By.id( "past-guest-number" ) );
		HtmlElement guest_submit = getDriver().findElement( By.id( "past-guest-submit" ) );

		doSelectCheckbox( he, check );
		guest_number.waitToBeDisplayed( true, TimeConstants.THREE_SECONDS );
		guest_submit.waitToBeDisplayed( true, TimeConstants.THREE_SECONDS );
	}

	public CruiseSearchPage clickSearchCruises()
	{
		HtmlElement search = getDriver().findElement( By.cssSelector( "a.search.red-cta" ) );
		search.click();
		return new CruiseSearchPage();
	}

	//endregion


	//region FindACruisePage - Element Finder Methods Section

	private HtmlElement findTravelerAnchor( String dataId )
	{
		String PATTERN = "ul.toggle-buttons[data-id='numGuests'] li > a[data-param='%s']";
		return getDriver().findElement( By.cssSelector( String.format( PATTERN, dataId ) ) );
	}

	private List<HtmlElement> findTravelersAnchors()
	{
		String PATTERN = "ul.toggle-buttons[data-id='numGuests'] li > a";
		return getDriver().findElements( By.cssSelector( "ul.toggle-buttons[data-param='numGuests'] li > a" ) );
	}

	private HtmlElement findCheckList()
	{
		if( null == check_list )
		{
			check_list = getDriver().findElement( By.cssSelector( "ul.check-list" ) );
		}
		return check_list;
	}

	//endregion

}
