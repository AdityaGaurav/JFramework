package com.framework.site.objects.body.common.cbox;

import com.framework.asserts.JAssertion;
import com.framework.driver.event.HtmlElement;
import com.framework.driver.event.HtmlObject;
import com.framework.driver.objects.AbstractWebObject;
import com.framework.site.data.Enumerators;
import com.framework.site.exceptions.NoSuchStateRoomException;
import com.framework.utils.datetime.TimeConstants;
import com.framework.utils.matchers.JMatchers;
import com.framework.utils.web.CSS2Properties;
import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static com.framework.utils.datetime.TimeConstants.FIVE_SECONDS;
import static org.hamcrest.Matchers.is;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.site.objects.body.common.cbox
 *
 * Name   : RoomLightbox 
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-03-07 
 *
 * Time   : 14:17
 *
 */

public class RoomLightboxObject extends AbstractWebObject implements Enumerators
{

	//region RoomLightbox - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( RoomLightboxObject.class );

	static final String LOGICAL_NAME = "Room Lightbox";

	static final By ROOT_BY = By.id( "room_ligthbox" );

	// ------------------------------------------------------------------------|
	// --- WEB-OBJECTS DEFINITIONS --------------------------------------------|
	// ------------------------------------------------------------------------|


	// ------------------------------------------------------------------------|
	// --- WEB-OBJECTS CACHING ------------------------------------------------|
	// ------------------------------------------------------------------------|

	private HtmlElement lb_contents, unhidden_data_row;

	private HtmlElement prev, next;

	private List<HtmlElement> dataRows = Lists.newArrayList();

	private List<HtmlElement> buttons = Lists.newArrayList();

	private List<HtmlElement> parents = Lists.newArrayList();

	//endregion


	//region RoomLightbox - Constructor Methods Section

	RoomLightboxObject( final HtmlElement rootElement )
	{
		super( rootElement, LOGICAL_NAME );
		initWebObject();
	}

	//endregion


	//region RoomLightbox - initialization and Validation Methods Section

	@Override
	protected void initWebObject()
	{
		logger.info( "validating static elements for web object id: <{}>, name:<{}>...",
				getQualifier(), getLogicalName() );

		final String REASON = "assert that element \"%s\" exits";
		JAssertion assertion = getRoot().createAssertion();

		Optional<HtmlElement> e = getRoot().childExists( By.className( "lb-contents" ), FIVE_SECONDS );
		assertion.assertThat( String.format( REASON, ".lb-contents" ), e.isPresent(), is( true ) );
		lb_contents = e.get();
	}

	//endregion


	//region RoomLightbox - Service Methods Section

	private HtmlElement getRoot()
	{
		return getBaseRootElement( ROOT_BY );
	}

	//endregion


	//region RoomLightbox - Implementation Methods Section

	public RoomType getRoomType()
	{
		RoomType roomType;

		HtmlElement row = findDataRows().get( 1 );
		HtmlElement h4 = row.findElement( By.tagName( "h4" ) );
		String text = h4.getAttribute( "textContent" ).toUpperCase();
		switch ( text )
		{
			case "BALCONY":
				roomType = RoomType.BALCONY;
				break;
			case "SUITE":
				roomType = RoomType.SUITE;
				break;
			case "OCEAN VIEW":
				roomType = RoomType.OCEAN_VIEW;
				break;
			case "INTERIOR":
				roomType = RoomType.INTERIOR;
				break;
			default:
				throw new NoSuchStateRoomException( "Invalid state room found -> " + text );
		}
		logger.info( "Return the lightbox room-type < {} >", roomType );
		return roomType;
	}

	public List<HtmlElement> getCarouselPagerButtons()
	{
		logger.info( "Returning carousel pager object ..." );
		return getCarouselPagerAnchors();
	}

	public List<HtmlElement> getSlides()
	{
		logger.info( "Returning lightbox slides ..." );
		HtmlElement row = findDataRows().get( 0 );
		return row.findElements( By.cssSelector( "div.slides a:not(.cycle-sentinel) > img" ) );
	}

	public void clickCarouselButton( HtmlElement button )
	{
		logger.info( "Clicking on carousel button < {} >", button.getAttribute( "textContent" ) );
		HtmlElement parentLi = button.findElement( By.xpath( "parent::li" ) );
		clickCarouselButton( button, parentLi );
	}

	public HtmlElement hoverOnPrevSlide()
	{
		logger.info( "Hovering on previous slide indicator ..." );

		String prop = CSS2Properties.BACKGROUND_POSITION.getStringValue();
		HtmlElement row = findDataRows().get( 0 );
		String backgroundPosition = findPrevSlideAnchor( row ).getCssValue( prop );
		prev.hover();
		prev.waitCssPropertyToMatch( prop, JMatchers.not( JMatchers.is( backgroundPosition ) ), TimeConstants.FIVE_SECONDS );
		return prev;
	}

	public HtmlElement hoverOnNextSlide()
	{
		logger.info( "Hovering on next slide indicator ..." );

		String prop = CSS2Properties.BACKGROUND_POSITION.getStringValue();
		HtmlElement row = findDataRows().get( 0 );
		String backgroundPosition = findNextSlideAnchor( row ).getCssValue( prop );
		next.hover();
		next.waitCssPropertyToMatch( prop, JMatchers.not( JMatchers.is( backgroundPosition ) ), TimeConstants.FIVE_SECONDS );
		return next;
	}

	public void clickOnNextSlide( HtmlElement buttonParent )
	{
		logger.info( "clicking on next slide indicator ..." );

		HtmlElement row = findDataRows().get( 0 );
		findNextSlideAnchor( row ).click();
		buttonParent.waitAttributeToMatch( "class", JMatchers.is( "cycle-pager-active" ), TimeConstants.THREE_SECONDS );
	}

	public void clickOnPrevSlide( HtmlElement buttonParent )
	{
		logger.info( "clicking on previous slide indicator ..." );

		HtmlElement row = findDataRows().get( 0 );
		findPrevSlideAnchor( row ).click();
		buttonParent.waitAttributeToMatch( "class", JMatchers.is( "cycle-pager-active" ), TimeConstants.THREE_SECONDS );
	}

	protected void reset()
	{
		dataRows.clear();
		parents.clear();
		buttons.clear();
		prev = null; next = null;
		unhidden_data_row = null;
	}

	public List<String> getAvailableFeaturesNames()
	{
		HtmlElement row = findDataRows().get( 1 );
		List<HtmlElement> spans = row.findElements( By.cssSelector( ".col-5-20 .unstyled-list span" ) );
		List<String> list = HtmlObject.extractText( spans );
		logger.info( "return a list of available features size: < {} >", list.size() );
		return list;
	}

	public List<HtmlElement> getAvailableFeatures()
	{
		HtmlElement row = findDataRows().get( 1 );
		List<HtmlElement> spans = row.findElements( By.cssSelector( ".col-5-20 .unstyled-list span" ) );
		logger.info( "return a list of available features size: < {} >", spans.size() );
		return spans;
	}

	public String getAvailableFeaturesTitle()
	{
		HtmlElement row = findDataRows().get( 1 );
		HtmlElement h5 = row.findElement( By.cssSelector( ".col-5-20 > h5" ) );
		String text = h5.getAttribute( "textContent" );
		logger.info( "return the available features title < {} >", text );
		return text;
	}

	//endregion


	//region RoomLightbox - Element Finder Section

	private List<HtmlElement> findDataRows()
	{
		if( dataRows.size() == 0 )
		{
			dataRows = findUnhiddenDataRoomType().findElements( By.className( "row" ) );
		}
		return dataRows;
	}

	private HtmlElement findUnhiddenDataRoomType()
	{
		if( unhidden_data_row == null )
		{
			unhidden_data_row = lb_contents.findElement( By.cssSelector( ".lb-contents > div:not(.hidden)" ) );
		}
		return unhidden_data_row;
	}

	public List<HtmlElement> getCarouselPagerButtonsParents()
	{
		if( parents.size() == 0 )
		{
			HtmlElement row = findDataRows().get( 0 );
			parents = row.findElements( By.cssSelector( ".carousel-pager li" ) );
		}
		return parents;
	}

	private List<HtmlElement> getCarouselPagerAnchors()
	{
		if( buttons.size() == 0 )
		{
			HtmlElement row = findDataRows().get( 0 );
			buttons = row.findElements( By.cssSelector( ".carousel-pager li > a" ) );
		}

		return buttons;
	}

	private void clickCarouselButton( HtmlElement button, HtmlElement parent )
	{
		button.click();
		parent.waitAttributeToMatch( "class", JMatchers.is( "cycle-pager-active" ), TimeConstants.THREE_SECONDS );
	}

	private HtmlElement findNextSlideAnchor( HtmlElement row )
	{
		if( next == null )
		{
			next = row.findElement( By.cssSelector( ".next-slide > a" ) );
		}
		return next;
	}

	private HtmlElement findPrevSlideAnchor( HtmlElement row )
	{
		if( prev == null )
		{
			prev = row.findElement( By.cssSelector( ".prev-slide > a" ) );
		}
		return prev;
	}

	//endregion

}
