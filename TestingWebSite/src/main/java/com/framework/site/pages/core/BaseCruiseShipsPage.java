package com.framework.site.pages.core;

import com.framework.driver.event.HtmlElement;
import com.framework.site.config.SiteProperty;
import com.framework.site.config.SiteSessionManager;
import com.framework.site.data.DeparturePorts;
import com.framework.site.data.Destinations;
import com.framework.site.data.Enumerators;
import com.framework.site.data.Ships;
import com.framework.site.objects.body.common.SectionBreadcrumbsBarObject;
import com.framework.site.objects.body.interfaces.ShipCard;
import com.framework.site.objects.body.ships.ShipCardObject;
import com.framework.site.objects.body.ships.SortBarObject;
import com.framework.site.pages.BaseCarnivalPage;
import com.framework.testing.annotations.DefaultUrl;
import com.framework.utils.matchers.JMatchers;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.apache.commons.lang3.math.NumberUtils;
import org.hamcrest.Matcher;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;


@DefaultUrl ( matcher = "contains()", value = "/cruise-ships.aspx" )
public class BaseCruiseShipsPage extends BaseCarnivalPage implements Enumerators
{

	//region BaseCruiseShipsPage - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( BaseCruiseShipsPage.class );

	private static final String LOGICAL_NAME = "Base Cruise Ships Page";

	// ------------------------------------------------------------------------|
	// --- WEB-OBJECTS DEFINITIONS --------------------------------------------|
	// ------------------------------------------------------------------------|

	private SortBarObject sortBar;

	private SectionBreadcrumbsBarObject breadcrumbsBar;

	// ------------------------------------------------------------------------|
	// --- WEB-OBJECTS CACHING ------------------------------------------------|
	// ------------------------------------------------------------------------|

	private HtmlElement contentBlock;

	//endregion


	//region BaseCruiseShipsPage - Constructor Methods Section

	public BaseCruiseShipsPage()
	{
		super( LOGICAL_NAME );
		validatePageInitialState();
	}

	public BaseCruiseShipsPage( String logicalName )
	{
		super( logicalName );
	}

	//endregion


	//region BaseCruiseShipsPage - Initialization and Validation Methods Section

	@Override
	protected void validatePageInitialState()
	{
		logger.debug( "validating page initial state for: <{}>, name:<{}>...", getQualifier(), getLogicalName() );

		/* Validating breadcrumb last value pointing to Ships */

		String lastChild = breadcrumbsBar().breadcrumbs().getLastChildName();
		getDriver().assertThat( "Validate breadcrumb last position is \"Ships\"", lastChild, JMatchers.is( "Ships" ) );

		/* Validating number of ships by property value ships.count */

		String REASON = "Validating number of ships by property value ships.count";
		final int shipsCount = NumberUtils.createInteger( SiteProperty.SHIPS_COUNT.fromContext().toString() );
		final Matcher<Integer> EXPECTED_SHIPS_COUNT = JMatchers.is( shipsCount );
		final int ACTUAL_SHIPS_COUNT = NumberUtils.createInteger( sortBar().getResultsElement().getText() );
		getDriver().assertThat( REASON, ACTUAL_SHIPS_COUNT, EXPECTED_SHIPS_COUNT );

		/* Validating sort-type is "FEATURED" by default */

		REASON = "Validating sort-type is \"FEATURED\" by default";
		final Matcher<SortType> EXPECTED_VALUE_OF_SORT_TYPE = JMatchers.is( SortType.FEATURED );
		final SortType ACTUAL_SORT_TYPE = sortBar().getSortType();
		getDriver().assertThat( REASON, ACTUAL_SORT_TYPE, EXPECTED_VALUE_OF_SORT_TYPE );

		/* Validating sort-type is "FEATURED" by default */

		REASON = "Validating layout-type is \"List\" by default";
		final Matcher<LayoutType> EXPECTED_VALUE_OF_LAYOUT_TYPE = JMatchers.is( LayoutType.BY_LIST );
		final LayoutType ACTUAL_LAYOUT_TYPE = sortBar().getLayoutType();
		getDriver().assertThat( REASON, ACTUAL_LAYOUT_TYPE, EXPECTED_VALUE_OF_LAYOUT_TYPE );

	}

	//endregion


	//region BaseCruiseShipsPage - Service Methods Section

	public static boolean isCruiseShipPage()
	{
		return SiteSessionManager.get().getCurrentUrl().getPath().endsWith( "/cruise-ships.aspx" );
	}

	public SortBarObject sortBar()
	{
		if ( null == this.sortBar )
		{
			this.sortBar = new SortBarObject( findSortBarDiv() );
		}
		return sortBar;
	}

	public SectionBreadcrumbsBarObject breadcrumbsBar()
	{
		if ( null == this.breadcrumbsBar )
		{
			this.breadcrumbsBar = new SectionBreadcrumbsBarObject( findBreadcrumbBarDiv() );
		}
		return breadcrumbsBar;
	}

	//endregion


	//region BaseCruiseShipsPage - Business Methods Section

	public int getShipCardsCount()
	{
		return getShipCardDiv().size();
	}

	public List<ShipCard> getAllShipsCards()
	{
		List<HtmlElement> divs = getShipCardDiv();
		List<ShipCard> cardObjects = Lists.newArrayList();
		for( HtmlElement div : divs )
		{
			ShipCard shipCard = new ShipCardObject( div );
			cardObjects.add( shipCard );
		}

		return cardObjects;
	}

	public ShipCard selectShip( Ships ship )
	{
		final String pattern = "div.activity-result.ship-result[data-id=\"%s\"]";
		HtmlElement card = getDriver().findElement( By.cssSelector( String.format( pattern, ship.getId() ) ) );
		return new ShipCardObject( card );
	}

	public List<Ships> getShips()
	{
		List<HtmlElement> cards = getShipCardDiv();
		List<Ships> ships = Lists.newArrayListWithExpectedSize( cards.size() );
		for( HtmlElement card : cards )
		{
			String id = card.getAttribute( "data-id" );
			Ships ship = Ships.valueById( id );
			ships.add( ship );
		}

		return ships;
	}

	public Set<Ships> getShipsMatching( DeparturePorts... ports )
	{
		logger.info( "Selecting a list of ships matching < {} >", Joiner.on( "," ) .join( ports ) );
		Map<String,Ships> shipsMap = Maps.newHashMap();

		for( DeparturePorts port : ports )
		{
			String expression = String.format( "return $(\"li:nth-child(2) a[href*='%s']\").parents('div.ship-result').get()", port.getHref() );
			List<HtmlElement> divs = getDriver().javascript().getHtmlElements( expression );
			for( HtmlElement he : divs )
			{
				String id = he.getAttribute( "data-id" );
				if( ! shipsMap.containsKey( id ) )
				{
					Ships ship = Ships.valueById( id );
					shipsMap.put( id, ship );
				}
			}
		}

		return Sets.newHashSet( shipsMap.values() );
	}

	public Set<Ships> getShipsMatching( Destinations... destinations )
	{
		Map<String,Ships> shipsMap = Maps.newHashMap();

		for( Destinations destination : destinations )
		{
			String expression = String.format( "return $(\"li:nth-child(1) a[href*='%s']\").parents('div.ship-result').get()", destination.getHref() );
			List<HtmlElement> divs = getDriver().javascript().getHtmlElements( expression );
			for( HtmlElement he : divs )
			{
				String id = he.getAttribute( "data-id" );
				if( ! shipsMap.containsKey( id ) )
				{
					Ships ship = Ships.valueById( id );
					shipsMap.put( id, ship );
				}
			}
		}

		return Sets.newHashSet( shipsMap.values() );
	}

	public List<Ships> getShipsSorted( List<Ships> actual )
	{
		List<Ships> sorted = Lists.newArrayList( actual );
		Collections.sort( sorted, new Comparator<Ships>()
		{
			@Override
			public int compare( final Ships o1, final Ships o2 )
			{
				return o1.getFullName().compareTo( o2.getFullName() );
			}
		} );

		return sorted;
	}

	public List<Ships> getShipFeaturedSorted()
	{
		String ids = ( String ) SiteProperty.SITE_CORE_SHIP_FEATURE_IDS.fromContext();
		List<String> list = Lists.newArrayList( org.apache.commons.lang3.StringUtils.split( ids, "," ) );
		List<Ships> ships = Lists.newArrayListWithExpectedSize( list.size() );
		for( String id : list )
		{
			Ships ship = Ships.valueById( id );
			ships.add( ship );
		}

		return ships;
	}

	public List<HtmlElement> getHeroBannerSlides()
	{
		return findHeroBannerContainerLis();
	}

	public HtmlElement getContentBlockResults()
	{
		if( null == contentBlock )
		{
			contentBlock = getDriver().findElement( By.cssSelector( ".content-block.results" ) );
		}

		return contentBlock;
	}

	//endregion


	//region BaseCruiseShipsPage - HtmlElement finder Section

	private HtmlElement findSortBarDiv()
	{
		return getDriver().findElement( SortBarObject.ROOT_BY );
	}

	private List<HtmlElement> getShipCardDiv()
	{
		final By findBy = By.cssSelector( "div.activity-result.ship-result" );
		return getDriver().findElements( findBy );
	}

	private List<HtmlElement> findHeroBannerContainerLis()
	{
		final By findBy = By.cssSelector( "#HeroSlidesContainer > li" );
		return getDriver().findElements( findBy );
	}

	//endregion

}
