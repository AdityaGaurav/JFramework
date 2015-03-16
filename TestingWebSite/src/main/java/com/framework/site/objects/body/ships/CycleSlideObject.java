package com.framework.site.objects.body.ships;

import com.framework.driver.event.HtmlElement;
import com.framework.driver.exceptions.ApplicationException;
import com.framework.driver.objects.AbstractWebObject;
import com.framework.site.data.Ships;
import com.framework.site.pages.bookingengine.CruiseSearchPage;
import com.framework.utils.string.ToLogStringStyle;
import com.google.common.base.Optional;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;

import static com.framework.utils.datetime.TimeConstants.THREE_SECONDS;
import static org.hamcrest.Matchers.is;


public class CycleSlideObject extends AbstractWebObject
{

	//region CycleSlideObject - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( CycleSlideObject.class );

	static final String LOGICAL_NAME = "Cycle Slide Item";

	private String imageAlt, title = null;

	private BigDecimal price = null;

	private boolean displayed;

	private final Ships ship;

	// ------------------------------------------------------------------------|
	// --- WEB-OBJECTS CACHING ------------------------------------------------|
	// ------------------------------------------------------------------------|

	private HtmlElement img;

	//endregion


	//region CycleSlideObject - Constructor Methods Section

	public CycleSlideObject( final HtmlElement rootElement, Ships ship )
	{
		super( rootElement, LOGICAL_NAME );
		initWebObject();
		this.ship = ship;
	}

	//endregion


	//region CycleSlideObject - Initialization and Validation Methods Section

	@Override
	protected void initWebObject()
	{
		logger.info( "validating static elements for web object id: <{}>, name:<{}>...",
				getQualifier(), getLogicalName() );

		final String REASON = "assert that element \"%s\" exits";

		Optional<HtmlElement> e = getRoot().childExists( By.className( "image" ), THREE_SECONDS );
		getDriver().assertThat( String.format( REASON, ".image" ), e.isPresent(), is( true ) );
		HtmlElement image = e.get();

		e = getRoot().childExists( By.className( "content" ), THREE_SECONDS );
		getDriver().assertThat( String.format( REASON, ".content" ), e.isPresent(), is( true ) );
		HtmlElement content = e.get();

		// retrieve img information
		img = image.findElement( By.tagName( "img" ) );
		imageAlt = img.getAttribute( "alt" );
		displayed = img.isDisplayed();

		// retrieve content info
		HtmlElement h3 = content.findElement( By.tagName( "h3" ) );
		title = h3.getAttribute( "textContent" );

		HtmlElement he = content.findElement( By.cssSelector( ".pull-right > strong" ) );
		String text = he.getAttribute( "textContent" ).replace( "$", "" );
		price = new BigDecimal( text );
	}

	//endregion


	//region CycleSlideObject - Service Methods Section

	private HtmlElement getRoot()
	{
		return getBaseRootElement();
	}

	@Override
	public String toString()
	{
		return new ToStringBuilder( this, ToLogStringStyle.LOG_LINE_STYLE )
				.append( "alt", imageAlt == null ? "N/A" : imageAlt )
				.append( "displayed", displayed )
				.toString();
	}

	//endregion


	//region CycleSlideObject - Implementation Function Section

	public HtmlElement getImage()
	{
		return img;
	}

	public String getAlt()
	{
		return imageAlt;
	}

	public boolean isDisplayed()
	{
		return displayed;
	}

	public String getTitle()
	{
		return title;
	}

	public BigDecimal getPrice()
	{
		return price;
	}

	public CruiseSearchPage clickViewSailings()
	{
		logger.info( "Clicking on view sailings for < {} >", imageAlt );

		String query = null;
		HtmlElement he = getRoot().findElement( By.cssSelector( "div.content > div.pull-right > a.green-cta" ) );
		String href = he.getAttribute( "href" );
		try
		{
			URL url = new URL( href );
			query = url.getQuery();
		}
		catch ( MalformedURLException e )
		{
			throw new ApplicationException( getRoot(), e );
		}
		he.click();
		CruiseSearchPage csp = new CruiseSearchPage();
		csp.setShip( ship );
		csp.setQuery( query );
		return csp;
	}


	//endregion


	//region CycleSlideObject - Inner Classes Implementation Section

	//endregion

}
