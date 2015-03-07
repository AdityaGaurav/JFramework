package com.framework.site.objects.body.common;

import com.framework.asserts.JAssertion;
import com.framework.driver.event.HtmlElement;
import com.framework.driver.exceptions.ApplicationException;
import com.framework.driver.objects.AbstractWebObject;
import com.framework.site.data.Enumerators;
import com.framework.site.pages.core.BeginnersGuidePage;
import com.google.common.base.Optional;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;

import static com.framework.utils.datetime.TimeConstants.*;
import static org.hamcrest.Matchers.is;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.site.objects.body.common
 *
 * Name   : NavStickemObject 
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-02-07 
 *
 * Time   : 17:47
 *
 */

public class NavStickEmbeddedObject extends AbstractWebObject implements Enumerators
{

	//region NavStickEmbeddedObject - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( NavStickEmbeddedObject.class );

	static final By ROOT_BY = By.cssSelector( "ul.nav.stickem" );

	static final String LOGICAL_NAME = "Stick Embedded";

	// ------------------------------------------------------------------------|
	// --- WEB-OBJECTS CACHING ------------------------------------------------|
	// ------------------------------------------------------------------------|

	private HtmlElement whats_included, on_ship, shore_excursions, destinations;

	//endregion


	//region NavStickEmbeddedObject - Constructor Methods Section

	public NavStickEmbeddedObject( final HtmlElement rootElement )
	{
		super( rootElement, LOGICAL_NAME );
		initWebObject();
	}

	//endregion


	//region NavStickEmbeddedObject - Initialization and Validation Methods Section

	@Override
	protected void initWebObject()
	{
		logger.debug( "validating static elements for web object id: <{}>, name:<{}>...",
				getQualifier(), getLogicalName() );

		final String REASON = "assert that element \"%s\" exits";
		JAssertion assertion = getRoot().createAssertion();

		try
		{
			URL currentUrl = new URL( getDriver().getCurrentUrl() );
			if( null == currentUrl.getRef() )
			{
				Optional<HtmlElement> e = getRoot().childExists( By.cssSelector( "li.whats-included a" ), FIVE_SECONDS );
				assertion.assertThat( String.format( REASON, "li.whats-included a" ), e.isPresent(), is( true ) );
				this.whats_included = e.get();
			}
			else if( currentUrl.getRef().equalsIgnoreCase( BeginnersGuidePage.ON_SHIP_REF ) )
			{
				Optional<HtmlElement> e = getRoot().childExists( By.cssSelector( "li.on-ship a" ), TWO_SECONDS );
				assertion.assertThat( String.format( REASON, "li.on-ship a" ), e.isPresent(), is( true ) );
				this.on_ship = e.get();
			}
			else if( currentUrl.getRef().equalsIgnoreCase( BeginnersGuidePage.SHOREX_REF ) )
			{
				Optional<HtmlElement> e = getRoot().childExists( By.cssSelector( "li.shore-excursions a" ), TWO_SECONDS );
				assertion.assertThat( String.format( REASON, "li.shore-excursions a" ), e.isPresent(), is( true ) );
				this.shore_excursions = e.get();
			}
			else if( currentUrl.getRef().equalsIgnoreCase( BeginnersGuidePage.DESTINATIONS_REF ) )
			{
				Optional<HtmlElement> e = getRoot().childExists( By.cssSelector( "li.destinations a" ), ONE_SECOND );
				assertion.assertThat( String.format( REASON, "li.destinations a" ), e.isPresent(), is( true ) );
				this.destinations = e.get();
			}
			else
			{
				Optional<HtmlElement> e = getRoot().childExists( By.cssSelector( "li.whats-included a" ), FIVE_SECONDS );
				assertion.assertThat( String.format( REASON, "li.whats-included a" ), e.isPresent(), is( true ) );
				this.whats_included = e.get();
			}
		}
		catch ( MalformedURLException e )
		{
			logger.error( ExceptionUtils.getRootCauseMessage( e ) );
			throw new ApplicationException( e );
		}
	}

	//endregion


	//region NavStickEmbeddedObject - Service Methods Section

	private HtmlElement getRoot()
	{
		return getBaseRootElement( ROOT_BY );
	}

	//endregion


	//region NavStickEmbeddedObject - Business Methods Section

	public NavStickItem getActiveItem()
	{
		HtmlElement he = getRoot().findElement( By.cssSelector( "li.link-active" ) );
		if( he.getAttribute( "class" ).contains( BeginnersGuidePage.ON_SHIP_REF ) )
		{
			return NavStickItem.ON_THE_SHIP;
		}
		else if( he.getAttribute( "class" ).contains( BeginnersGuidePage.SHOREX_REF ) )
		{
			return NavStickItem.SHORE_EXCURSIONS;
		}
		else if( he.getAttribute( "class" ).contains( BeginnersGuidePage.DESTINATIONS_REF ) )
		{
			return NavStickItem.DESTINATIONS;
		}
		else if( he.getAttribute( "class" ).contains( BeginnersGuidePage.INCLUDED_REF ) )
		{
			return NavStickItem.WHATS_INCLUDED;
		}

		return null;
	}


	//endregion


	//region NavStickEmbeddedObject - Element Finder Methods Section

	private HtmlElement findWhatsIncludedAnchor()
	{
		if( null == whats_included )
		{
			whats_included = getDriver().findElement( By.cssSelector( "li.whats-included a" ) );
		}
		return whats_included;
	}

	private HtmlElement findOnShipAnchor()
	{
		if( null == on_ship )
		{
			on_ship = getDriver().findElement( By.cssSelector( "li.on-ship a" ) );
		}
		return on_ship;
	}

	private HtmlElement findShoreExcursionsAnchor()
	{
		if( null == shore_excursions )
		{
			shore_excursions = getDriver().findElement( By.cssSelector( "li.shore-excursions a" ) );
		}
		return shore_excursions;
	}

	private HtmlElement findDestinationsAnchor()
	{
		if( null == destinations )
		{
			destinations = getDriver().findElement( By.cssSelector( "li.destinations a" ) );
		}
		return destinations;
	}

	//endregion

}
