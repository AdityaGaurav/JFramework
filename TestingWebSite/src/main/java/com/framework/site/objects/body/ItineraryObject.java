package com.framework.site.objects.body;

import com.framework.asserts.JAssertion;
import com.framework.driver.event.HtmlElement;
import com.framework.driver.extensions.jquery.By;
import com.framework.driver.objects.AbstractWebObject;
import com.framework.site.data.DeparturePorts;
import com.framework.site.data.Destinations;
import com.framework.site.data.ItinerarySail;
import com.framework.site.data.Ships;
import com.framework.site.objects.body.interfaces.Itinerary;
import com.framework.utils.datetime.TimeConstants;
import com.framework.utils.error.PreConditions;
import com.framework.utils.matchers.JMatchers;
import com.framework.utils.string.ToLogStringStyle;
import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.List;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.site.objects.body
 *
 * Name   : ItineraryObject 
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-03-13 
 *
 * Time   : 15:34
 *
 */

public class ItineraryObject extends AbstractWebObject implements Itinerary
{

	//region ItineraryObject - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( ItineraryObject.class );

	static final String LOGICAL_NAME = "Itinerary Card";

	private String itineraryId = StringUtils.EMPTY;

	private String sailingEventCode = StringUtils.EMPTY;

	String title = null;

	private BigDecimal price = null;

	private DeparturePorts port = null;

	private String portString = null;

	private String itinCloseId;

	// ------------------------------------------------------------------------|
	// --- WEB-OBJECTS CACHING ------------------------------------------------|
	// ------------------------------------------------------------------------|

	private HtmlElement itin_close_id, itin_select, itin_details_h4;

	//endregion


	//region ItineraryObject - Constructor Methods Section

	public ItineraryObject( final HtmlElement rootElement )
	{
		super( rootElement, LOGICAL_NAME );

		itineraryId = getRoot().getAttribute( "data-itinid" );
		PreConditions.checkNotNullNotBlankOrEmpty( itineraryId, "itinerary id is currently null, empty or blank" );
		sailingEventCode = StringUtils.defaultString( getRoot().getAttribute( "sailingeventcode" ), StringUtils.EMPTY );
		itin_close_id = getRoot().findElement( By.cssSelector( "div.right-side > a" ) );
		itinCloseId = itin_close_id.getAttribute( "data-itincloseid" );
		initWebObject();
	}

	//endregion


	//region ItineraryObject - Initialization and Validation Methods Section

	@Override
	protected void initWebObject()
	{
		logger.info( "validating static elements for web object id: <{}>, name:<{}>...",
				getQualifier(), getLogicalName() );

		final String REASON = "assert that element \"%s\" exits";
		JAssertion assertion = getRoot().createAssertion();

		Optional<HtmlElement> es = getRoot().childExists( By.className( "wrapper" ), TimeConstants.FIVE_SECONDS );
		assertion.assertThat( String.format( REASON, ".wrapper" ), es.isPresent(), JMatchers.is( true ) );

		es = getRoot().childExists( By.cssSelector( ".wrapper.second-row" ), TimeConstants.THREE_SECONDS );
		assertion.assertThat( String.format( REASON, ".wrapper.second-row" ), es.isPresent(), JMatchers.is( true ) );

		es = getRoot().childExists( By.className( "alt-content" ), TimeConstants.THREE_SECONDS );
		assertion.assertThat( String.format( REASON, ".alt-content" ), es.isPresent(), JMatchers.is( true ) );

		title = getTitle();
		price = getPrice();
		port = getDeparturePort();
	}

	//endregion


	//region ItineraryObject - Service Methods Section

	private HtmlElement getRoot()
	{
		return getBaseRootElement();
	}

	@Override
	public String toString()
	{
		return new ToStringBuilder( this, ToLogStringStyle.LOG_LINE_STYLE )
				.append( "itinerary Id", null == itineraryId ? "N/A" : itineraryId )
				.append( "sailing Event Code", null == sailingEventCode ? "N/A" : sailingEventCode )
				.append( "title", null == title ? "N/A" : title )
				.append( "price", null == price ? "N/A" : price  )
				.append( "port", null == port ? "N/A" : port.name()  )
				.toString();
	}

	//endregion


	//region ItineraryObject - Itinerary Implementation Section

	@Override
	public String itineraryId()
	{
		return itineraryId;
	}

	@Override
	public String sailEventCode()
	{
		return sailingEventCode;
	}

	@Override
	public void ShowHideDates( boolean show )
	{
		logger.info( "{} itinerary dates for itinerary < {} >...", show ? "Showing " : "Hiding ", itineraryId );
		String className = itin_close_id.getAttribute( "class" );
		if( show && ( ! className.endsWith( "active" ) ) )
		{
			HtmlElement span = getRoot().findElement( By.cssSelector( "div.right-side > a > span" ) );
			itin_close_id.click();
			span.waitTextToMatch( JMatchers.is( "Hide Dates" ), TimeConstants.THREE_SECONDS );
		}
		else if( ! show && className.endsWith( "active" ) )
		{
			HtmlElement span = getRoot().findElement( By.cssSelector( "div.right-side > a > span" ) );
			itin_close_id.click();
			span.waitTextToMatch( JMatchers.is( "Show Dates" ), TimeConstants.THREE_SECONDS );
		}
	}

	@Override
	public HtmlElement getImage()
	{
		logger.info( "returning itinerary < {} > image ...", itineraryId );
		return getRoot().findElement( By.cssSelector( "a.itin-img > img" ) );
	}

	@Override
	public void compare()
	{

	}

	@Override
	public void addToFavorites()
	{

	}

	@Override
	public List<ItinerarySail> getSailings()
	{
		List<HtmlElement> uls = getRoot().findElements( By.cssSelector( "div.content .sailings ul" ) );
		logger.info( "Returning a list of < {} > sailings for itinerary  < {} >", uls.size(), itineraryId );
		List<ItinerarySail> sailings = Lists.newArrayListWithCapacity( uls.size() );
		for( HtmlElement ul : uls )
		{
			sailings.add( new ItinerarySail( ul ) );
		}

		return sailings;
	}

	@Override
	public ItinerarySail getTopBestPrice()
	{
		HtmlElement he = getRoot().findElement( By.className( ".top-best-price ul" ) );
		return new ItinerarySail( he );
	}

	@Override
	public String getTitle()
	{
		if( null != title ) return title;

		if( null == itin_select )
		{
			itin_select = getRoot().findElement( By.cssSelector( "a.itin-select:not(.itin-img)" ) );
		}
		String text = itin_select.getText();
		logger.info( "Return itinerary title < {} > for itinerary id < {} >", text, itineraryId );
		return text;
	}

	@Override
	public DeparturePorts getDeparturePort()
	{
		if( port != null ) return port;

		HtmlElement he = findItinDetailsH4();
		String text = he.getText().replace( "from", "" );
		DeparturePorts port = DeparturePorts.convert( text.trim() );
		logger.info( "Returning departure port < {} > for itinerary is < {} >", port.name(), itineraryId );
		return port;
	}

	@Override
	public String getDeparturePortAsString()
	{
		if( portString != null ) return portString;

		HtmlElement he = findItinDetailsH4();
		portString = he.getAttribute( "textContent" );
		return portString;
	}

	@Override
	public List<Destinations> getDestinations()
	{
		return null;
	}

	@Override
	public Ships getShip()
	{
		HtmlElement he = getRoot().findElement( By.cssSelector( "a.ship-lightbox" ) );
		String text = he.getText().replace( "Carnival ", "" );
		Ships ship = Ships.valueByName( text );
		logger.info( "Returning ship < {} > for itinerary id < {} >", ship.name(), itineraryId );
		return ship;
	}

	@Override
	public BigDecimal getPrice()
	{
		if( null != price ) return price;

		HtmlElement he = getRoot().findElement( By.cssSelector( "div.right-side strong" ) );
		String text = he.getText().replace( "$", "" );
		logger.info( "Returning price < {} > for itinerary id < {} >", text, itineraryId );
		return new BigDecimal( text );
	}

	@Override
	public void clickLearMore()
	{

	}


	//endregion


	//region ItineraryObject - Inner Classes Implementation Section

	private HtmlElement findArrowContainerLi()
	{
		return getRoot().findElement( By.cssSelector( "li.arrow-container a" ) );
	}

	private HtmlElement findItinDetailsH4()
	{
		if( null == itin_details_h4 )
		{
			itin_details_h4 = getRoot().findElement( By.cssSelector( ".itin-details h4" ) );
		}
		return itin_details_h4;
	}

	//endregion

}
