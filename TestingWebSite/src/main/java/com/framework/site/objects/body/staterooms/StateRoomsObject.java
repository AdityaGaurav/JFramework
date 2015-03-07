package com.framework.site.objects.body.staterooms;

import com.framework.asserts.JAssertion;
import com.framework.driver.event.HtmlElement;
import com.framework.driver.objects.AbstractWebObject;
import com.framework.utils.datetime.TimeConstants;
import com.framework.utils.matchers.JMatchers;
import com.google.common.base.Optional;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static com.framework.utils.datetime.TimeConstants.THREE_SECONDS;
import static org.hamcrest.Matchers.is;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.site.objects.body.staterooms
 *
 * Name   : StateRoomsObject 
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-03-07 
 *
 * Time   : 02:06
 *
 */

class StateRoomsObject extends AbstractWebObject
{

	//region StateRoomsObject - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( StateRoomsObject.class );

	static final String LOGICAL_NAME = "State Rooms Thumbnails";

	static final By ROOT_BY = By.cssSelector( "div.row.rooms-thumbnails" );

	public enum RoomType{ INTERIOR, BALCONY, SUITE, OCEAN_VIEW }

	// ------------------------------------------------------------------------|
	// --- WEB-OBJECTS CACHING ------------------------------------------------|
	// ------------------------------------------------------------------------|

	private HtmlElement cboxContent;

	//endregion


	//region StateRoomsObject - Constructor Methods Section

	StateRoomsObject( final HtmlElement rootElement )
	{
		super( rootElement, LOGICAL_NAME );
		initWebObject();
	}

	//endregion


	//region StateRoomsObject - Initialization and Validation Methods Section

	@Override
	protected void initWebObject()
	{
		logger.info( "validating static elements for web object id: <{}>, name:<{}>...",
				getQualifier(), getLogicalName() );

		final String REASON = "assert that all element \"%s\" exits";

		JAssertion assertion = getRoot().createAssertion();
		Optional<List<HtmlElement>> e = getRoot().allChildrenExists( By.className( "div.col.col-10-20" ), THREE_SECONDS );
		assertion.assertThat( String.format( REASON, "div.col.col-10-20" ), e.isPresent(), is( true ) );
		assertion.assertThat( "4 staterooms types", e.get(), JMatchers.hasSize( 4 ) );

		e = getRoot().allChildrenExists( By.id( "room_ligthbox" ), THREE_SECONDS );
		assertion.assertThat( String.format( REASON, "#room_ligthbox" ), e.isPresent(), is( true ) );
	}

	//endregion


	//region StateRoomsObject - Service Methods Section

	private HtmlElement getRoot()
	{
		return getBaseRootElement( ROOT_BY );
	}

	//endregion


	//region StateRoomsObject - StateRooms Interface Implementation Section

	public HtmlElement getContainer()
	{
		return getRoot();
	}

	public HtmlElement clickItem( final RoomType roomType )
	{
	   	logger.info( "Clicking on State-Room < {} >", roomType );
	 	HtmlElement he = findStateRoomAnchor( roomType );
		he.click();
		logger.info( "Waiting for cBoxContent to be displayed" );
		findCBoxContent().waitToBeDisplayed( true, TimeConstants.THREE_SECONDS );
		return findCBoxContent().findElement( By.id( "" ) );
	}


	//endregion


	//region StateRoomsObject - Element finder Section

	private HtmlElement findStateRoomAnchor( RoomType type )
	{
		switch ( type )
		{
			case INTERIOR:
			{
				return getDriver().findElement( By.xpath( "//span[@class=\"h3\" and text()=\"Interior\"]/following-sibling::i" ) );
			}
			case SUITE:
			{
				return getDriver().findElement( By.xpath( "//span[@class=\"h3\" and text()=\"Suite\"]/following-sibling::i" ) );
			}
			case BALCONY:
			{
				return getDriver().findElement( By.xpath( "//span[@class=\"h3\" and text()=\"Balcony\"]/following-sibling::i" ) );
			}
			case OCEAN_VIEW:
			{
				return getDriver().findElement( By.xpath( "//span[@class=\"h3\" and text()=\"Ocean View\"]/following-sibling::i" ) );
			}
		}

		throw new IllegalArgumentException( "Invalid State-Room " + type.name() );
	}

	private HtmlElement findCBoxContent()
	{
		if( cboxContent == null )
		{
			cboxContent = getDriver().findElement( By.id( "cboxContent" ) );
		}
		return cboxContent;
	}

	//endregion

	//region StatesRoomObject - Inner Classes Implementations

}
