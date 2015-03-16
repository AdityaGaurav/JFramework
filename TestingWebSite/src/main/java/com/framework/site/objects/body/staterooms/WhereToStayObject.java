package com.framework.site.objects.body.staterooms;

import com.framework.asserts.JAssertion;
import com.framework.driver.event.HtmlElement;
import com.framework.driver.objects.AbstractWebObject;
import com.framework.site.data.Enumerators;
import com.framework.utils.datetime.TimeConstants;
import com.framework.utils.matchers.JMatchers;
import com.google.common.base.Optional;
import org.apache.commons.lang3.BooleanUtils;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.framework.utils.datetime.TimeConstants.THREE_SECONDS;
import static org.hamcrest.Matchers.is;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.site.objects.body.staterooms
 *
 * Name   : WhereShouldIStayObject 
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-03-08 
 *
 * Time   : 00:17
 *
 */

public class WhereToStayObject extends AbstractWebObject implements Enumerators
{

	//region WhereToStayObject - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( WhereToStayObject.class );

	static final String LOGICAL_NAME = "Where Should I Stay";

	public static final By ROOT_BY = By.className( "where-to-stay" );

	// ------------------------------------------------------------------------|
	// --- WEB-OBJECTS CACHING ------------------------------------------------|
	// ------------------------------------------------------------------------|

	private HtmlElement ship;

	//endregion


	//region WhereToStayObject - Constructor Methods Section

	public WhereToStayObject( final HtmlElement rootElement )
	{
		super( rootElement, LOGICAL_NAME );
		initWebObject();
	}

	//endregion


	//region WhereToStayObject - Initialization and Validation Methods Section

	@Override
	protected void initWebObject()
	{
		logger.info( "validating static elements for web object id: <{}>, name:<{}>...",
				getQualifier(), getLogicalName() );

		final String REASON = "assert that element \"%s\" exits";

		JAssertion assertion = getRoot().createAssertion();
		Optional<HtmlElement> e = getRoot().childExists( By.cssSelector( "div[data-module=\"room-selection\"]" ), THREE_SECONDS );
		assertion.assertThat( String.format( REASON, "div[data-module=\"room-selection\"]" ), e.isPresent(), is( true ) );

		e = getRoot().childExists( By.className( "ship" ), THREE_SECONDS );
		assertion.assertThat( String.format( REASON, "div.ship" ), e.isPresent(), is( true ) );
		ship = e.get();
	}

	//endregion


	//region WhereToStayObject - Service Methods Section

	private HtmlElement getRoot()
	{
		return getBaseRootElement( ROOT_BY );
	}

	//endregion


	//region WhereToStayObject - Implementation Methods Section

	public boolean hasTitle()
	{
		Optional<HtmlElement> oe = getRoot().childExists( By.cssSelector( ".intro > h2.blue" ) );
		boolean isPresent = oe.isPresent() && oe.get().getText().length() > 0;
		logger.info( "Determine if Where Should I Go has title and length > 0. < {} >", BooleanUtils.toStringYesNo( isPresent ) );
		return isPresent;
	}

	public boolean hasDescription()
	{
		Optional<HtmlElement> oe = getRoot().childExists( By.cssSelector( ".intro > p" ) );
		boolean isPresent = oe.isPresent() && oe.get().getText().length() > 0;
		logger.info( "Determine if Where Should I Go has description and length > 0. < {} >", BooleanUtils.toStringYesNo( isPresent ) );
		return isPresent;
	}

	public Decks getActiveDeck()
	{
   		HtmlElement he = findActiveControlLi().findElement( By.tagName( "a" ) );
		String dataTracking = he.getAttribute( "data-tracking" ).replace( " ", "_" ).toUpperCase();
		Decks deck = Decks.valueOf( dataTracking );
		logger.info( "Returning the current active deck. < {} >", deck );

		return deck;
	}

	public Decks getActiveShipDeck()
	{
		Decks activeDeck = null;
		HtmlElement he = findShipActiveControlLi().findElement( By.tagName( "a" ) );
		String dataSection = he.getAttribute( "data-section" );
		switch ( dataSection )
		{
			case "aft-icon":
				activeDeck = Decks.AFT;
				break;
			case "midship-icon":
				activeDeck = Decks.MIDSHIP;
				break;
			case "forward-icon":
				activeDeck = Decks.FORWARD;
				break;
			case "lower-icon":
				activeDeck = Decks.LOWER_DECK;
				break;
			default:
				activeDeck = Decks.UPPER_DECK;
				break;
		}
		logger.info( "Returning the current active ship deck. < {} >", activeDeck.name() );

		return activeDeck;
	}

	public void clickOnDeck( Decks deck )
	{
		logger.info( "Clicking on deck < {} >", deck.name() );
		HtmlElement he = null;

		switch ( deck )
		{
			case LOWER_DECK:
				he = getRoot().findElement( By.className( "left-corner" ) );
				break;
			case AFT:
				he = getRoot().findElement( By.className( "smaller" ) );
				break;
			case MIDSHIP:
				he = getRoot().findElement( By.xpath( "//li/a[@data-trigger='midship']/.." ) );
				break;
			case FORWARD:
				he = getRoot().findElement( By.xpath( "//li/a[@data-trigger='forward']/.." ) );
				break;
			case UPPER_DECK:
				he = getRoot().findElement( By.className( "right-corner" ) );
				break;
		}

		he.findElement( By.tagName( "a" ) ).click();
		he.waitAttributeToMatch( "class", JMatchers.endsWith( "active" ), TimeConstants.TWO_SECONDS );
	}

	public HtmlElement getDeckTooltip()
	{
		logger.info( "Return deck tooltip ..." );
		return getRoot().findElement( By.cssSelector( ".contents p" ) );
	}

	//endregion


	//region WhereToStayObject - Element Finder Section

	private HtmlElement findTitle()
	{
	 	return getRoot().findElement( By.cssSelector( ".intro > h2.blue" ) );
	}

	private HtmlElement findActiveControlLi()
	{
		return getDriver().findElement( By.cssSelector( "div.where-to-stay .controls li.active" ) );
	}

	private HtmlElement findShipActiveControlLi()
	{
		return ship.findElement( By.className( "active" ) );
	}

	//endregion

}
