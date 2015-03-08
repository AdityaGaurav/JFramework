package com.framework.site.objects.body.staterooms;

import com.framework.asserts.JAssertion;
import com.framework.driver.event.HtmlElement;
import com.framework.driver.objects.AbstractWebObject;
import com.framework.site.data.Enumerators;
import com.framework.site.objects.body.common.cbox.CBoxLoadedContentObject;
import com.framework.site.objects.body.common.cbox.CBoxWrapperObject;
import com.framework.utils.datetime.TimeConstants;
import com.framework.utils.matchers.JMatchers;
import com.google.common.base.Optional;
import org.apache.commons.lang3.StringUtils;
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

public class StateRoomsObject extends AbstractWebObject implements Enumerators
{

	//region StateRoomsObject - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( StateRoomsObject.class );

	static final String LOGICAL_NAME = "State Rooms Types";

	public static final By ROOT_BY = By.cssSelector( "div.row.rooms-thumbnails" );

	// ------------------------------------------------------------------------|
	// --- WEB-OBJECTS DEFINITIONS --------------------------------------------|
	// ------------------------------------------------------------------------|

	private CBoxWrapperObject cBoxWrapper;

	// ------------------------------------------------------------------------|
	// --- WEB-OBJECTS CACHING ------------------------------------------------|
	// ------------------------------------------------------------------------|

	private HtmlElement cboxContent;

	//endregion


	//region StateRoomsObject - Constructor Methods Section

	public StateRoomsObject( final HtmlElement rootElement )
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

		final String REASON1 = "assert that all element \"%s\" exits";

		JAssertion assertion = getRoot().createAssertion();
		Optional<List<HtmlElement>> es = getRoot().allChildrenExists( By.cssSelector( "div.col.col-10-20" ), THREE_SECONDS );
		assertion.assertThat( String.format( REASON1, "div.col.col-10-20" ), es.isPresent(), is( true ) );
		assertion.assertThat( "4 staterooms types", es.get(), JMatchers.hasSize( 4 ) );
	}

	//endregion


	//region StateRoomsObject - Service Methods Section

	private HtmlElement getRoot()
	{
		return getBaseRootElement( ROOT_BY );
	}

	public CBoxWrapperObject cboxWrapper()
	{
		if ( null == this.cBoxWrapper )
		{
			this.cBoxWrapper = new CBoxWrapperObject( findCboxWrapperDiv() );
		}
		return cBoxWrapper;
	}

	//endregion


	//region StateRoomsObject - StateRooms Interface Implementation Section

	public HtmlElement getContainer()
	{
		return getRoot();
	}

	public CBoxWrapperObject clickStateRoom( final RoomType roomType )
	{
	   	logger.info( "Clicking on State-Room < {} >", roomType );
	 	HtmlElement he = findStateRoomI( roomType );
		CBoxLoadedContentObject.forContentType( CboxLoadedContentType.STATE_ROOMS );
		he.click();
		logger.info( "Waiting for cBoxContent to be displayed" );
		findCboxWrapperDiv().waitToBeDisplayed( true, TimeConstants.THREE_SECONDS );
		return cboxWrapper();
	}

	public HtmlElement getImage( final RoomType roomType )
	{
		logger.info( "Returning image for State-Room < {} >", roomType );
		return findStateRoomImg( roomType );
	}

	//endregion


	//region StateRoomsObject - Element finder Section

	private HtmlElement findStateRoomI( RoomType type )
	{
		final String PATTERN = "//span[@class=\"h3\" and text()=\"%s\"]/following-sibling::i";
		String stateRoomName = StringUtils.EMPTY;
		switch ( type )
		{
			case INTERIOR:
				stateRoomName = "Interior";
				break;
			case SUITE:
				stateRoomName = "Suite";
				break;
			case BALCONY:
				stateRoomName = "Balcony";
				break;
			case OCEAN_VIEW:
				stateRoomName = "Ocean View";
				break;
			default:
				throw new IllegalArgumentException( "Invalid State-Room " + type.name() );
		}

		return getDriver().findElement( By.xpath( String.format( PATTERN, stateRoomName ) ) );
	  ////span[@class="h3" and text()="Balcony"]/../../following-sibling::img

	}

	private HtmlElement findStateRoomImg( RoomType type )
	{
		final String PATTERN = "//span[@class=\"h3\" and text()=\"%s\"]/../../following-sibling::img";
		String stateRoomName = StringUtils.EMPTY;
		switch ( type )
		{
			case INTERIOR:
				stateRoomName = "Interior";
				break;
			case SUITE:
				stateRoomName = "Suite";
				break;
			case BALCONY:
				stateRoomName = "Balcony";
				break;
			case OCEAN_VIEW:
				stateRoomName = "Ocean View";
				break;
			default:
				throw new IllegalArgumentException( "Invalid State-Room " + type.name() );
		}

		return getDriver().findElement( By.xpath( String.format( PATTERN, stateRoomName ) ) );
	}

	private HtmlElement findCBoxContent()
	{
		if( cboxContent == null )
		{
			cboxContent = getDriver().findElement( By.id( "cboxContent" ) );
		}
		return cboxContent;
	}

	private HtmlElement findCboxWrapperDiv()
	{
		return getDriver().findElement( CBoxWrapperObject.ROOT_BY );
	}

	//endregion

	//region StatesRoomObject - Inner Classes Implementations

}
