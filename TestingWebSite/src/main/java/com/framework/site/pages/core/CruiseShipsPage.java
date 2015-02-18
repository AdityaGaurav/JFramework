package com.framework.site.pages.core;

import com.framework.driver.event.HtmlElement;
import com.framework.site.config.SiteProperty;
import com.framework.site.data.Ships;
import com.framework.site.data.xml.SmartRandomShipCard;
import com.framework.site.data.xml.XmlShipCard;
import com.framework.site.objects.body.common.SectionBreadcrumbsBarObject;
import com.framework.site.objects.body.interfaces.*;
import com.framework.site.objects.body.ships.CompareShipBannerObject;
import com.framework.site.objects.body.ships.ShipCardObject;
import com.framework.site.objects.body.ships.SortBarObject;
import com.framework.site.pages.BaseCarnivalPage;
import com.framework.testing.annotations.DefaultUrl;
import com.framework.utils.datetime.TimeConstants;
import com.framework.utils.error.PreConditions;
import com.framework.utils.matchers.JMatchers;
import com.framework.utils.xml.XmlParseException;
import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.hamcrest.Matcher;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

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

@DefaultUrl( matcher = "contains()", value = "/cruise-ships.aspx" )
public class CruiseShipsPage extends BaseCarnivalPage
{

	//region CruiseShipsPage - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( CruiseShipsPage.class );

	private static final String LOGICAL_NAME = "Cruise Ships Page";

	private static final String RANDOM_SHIP_CARDS_XML = "random.ships.cards.xml";

	private static final String COMPARE_RANDOM_SHIP_XML = "compare.random.ships.xml";

	public enum SelectRandom{ LESS_TIMES_SELECTED, OLDER_SELECTED, MOST_TIME_SELECTED, LAST_SELECTED, RANDOM, NONE }

	private Ships excludeShip = null;

	// ------------------------------------------------------------------------|
	// --- WEB-OBJECTS DEFINITIONS --------------------------------------------|
	// ------------------------------------------------------------------------|

	private ShipSortBar sortBar;

	private CompareShipBanner compareShipBanner;

	private BreadcrumbsBar breadcrumbsBar;

	private FilterCategories filterCategories;

	private List<ShipCard> shipCards;

	//endregion


	//region CruiseShipsPage - Constructor Methods Section

	public CruiseShipsPage()
	{
		super( LOGICAL_NAME );
		validatePageInitialState();
	}

	//endregion


	//region CruiseShipsPage - Initialization and Validation Methods Section

	protected void validatePageTitle()
	{
		String title = ( String ) SiteProperty.CRUISE_SHIPS_TITLE.fromContext();
		final Matcher<String> EXPECTED_TITLE = JMatchers.equalToIgnoringCase( title );
		final String REASON = String.format( "Asserting \"%s\" page's title", LOGICAL_NAME );

		getDriver().assertThat( REASON, getTitle(), EXPECTED_TITLE );
	}


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
		final int ACTUAL_SHIPS_COUNT = sortBar().getResults();
		getDriver().assertThat( REASON, ACTUAL_SHIPS_COUNT, EXPECTED_SHIPS_COUNT );

		/* Validating sort-type is "FEATURED" by default */

		REASON = "Validating sort-type is \"FEATURED\" by default";
		final Matcher<ShipSortBar.SortType> EXPECTED_VALUE_OF_SORT_TYPE = JMatchers.is( ShipSortBar.SortType.DISPLAY_FEATURED );
		final ShipSortBar.SortType ACTUAL_SORT_TYPE = sortBar().getSortType();
		getDriver().assertThat( REASON, ACTUAL_SORT_TYPE, EXPECTED_VALUE_OF_SORT_TYPE );

		/* Validating sort-type is "FEATURED" by default */

		REASON = "Validating layout-type is \"List\" by default";
		final Matcher<ShipSortBar.LayoutType> EXPECTED_VALUE_OF_LAYOUT_TYPE = JMatchers.is( ShipSortBar.LayoutType.BY_LIST );
		final ShipSortBar.LayoutType ACTUAL_LAYOUT_TYPE = sortBar().getLayoutType();
		getDriver().assertThat( REASON, ACTUAL_LAYOUT_TYPE, EXPECTED_VALUE_OF_LAYOUT_TYPE );

	}

	//endregion


	//region CruiseShipsPage - Service Methods Section

	public ShipSortBar sortBar()
	{
		if ( null == this.sortBar )
		{
			this.sortBar = new SortBarObject( findSortBarDiv() );
		}
		return sortBar;
	}

	public BreadcrumbsBar breadcrumbsBar()
	{
		if ( null == this.breadcrumbsBar )
		{
			this.breadcrumbsBar = new SectionBreadcrumbsBarObject( findBreadcrumbBarDiv() );
		}
		return breadcrumbsBar;
	}

	public CompareShipBanner compareBanner()
	{
		if ( null == this.compareShipBanner )
		{
			this.compareShipBanner = new CompareShipBannerObject( findCompareBannerDiv() );
		}
		return compareShipBanner;
	}

	public FilterCategories filterCategories()
	{
//		if ( null == this.filterCategories )
//		{
//			this.filterCategories = new FilterCategoriesObject( getDriver(), findFilterCategories() );
//		}
//		return filterCategories;
		return null;
	}

	//endregion


	//region CruiseShipsPage - Business Methods Section

	/**
	 * Select a list of 1-3 random ship by {@linkplain SelectRandom} queries.
	 * if the 2nd and 3rd argument are {@linkplain SelectRandom#NONE} only 1 {@link ShipCard}
	 * will be returned.
	 *
	 * the procedure only selects but not add the shipd to comparison, since additional verifications required.
	 *
	 * @param first     the first element to select criteria
	 * @param second    the second element to select criteria
	 * @param third     the third element to select criteria
	 *
	 * @return a list of {@link ShipCard} elements.
	 *
	 * @see com.framework.site.data.xml.SmartRandomShipCard
	 * @see com.framework.site.data.xml.XmlShipCard
	 * @see com.framework.site.data.xml.ShipCardContentHandler
	 */
	public List<ShipCard> getRandomShipsToCompare( SelectRandom first, SelectRandom second, SelectRandom third )
	{
		List<XmlShipCard> selectedCards;
		List<ShipCard> cardObjects;
		try
		{
			cardObjects = Lists.newArrayList();
			SmartRandomShipCard randomSelection;
			if( excludeShip != null )
			{
				randomSelection = new SmartRandomShipCard( COMPARE_RANDOM_SHIP_XML, excludeShip );
			}
			else
			{
				randomSelection = new SmartRandomShipCard( COMPARE_RANDOM_SHIP_XML );
			}
			randomSelection.parse();
			randomSelection.selectXmlCard( first, second, third );
			selectedCards = randomSelection.getSelectedCards();
		}
		catch ( Exception e )
		{
			logger.error( ExceptionUtils.getRootCauseMessage( e ) );
			throw new XmlParseException( e );
		}

		for( XmlShipCard selectedCard : selectedCards )
		{
			final String pattern = "div.activity-result.ship-result[data-id=\"%s\"]";
			HtmlElement card = getDriver().findElement( By.cssSelector( String.format( pattern, selectedCard.getShipId() ) ) );
			ShipCard shipCard = new ShipCardObject( card );
			logger.info( "New ship card was created and evaluated -> < {} >", shipCard.toString() );
			cardObjects.add( shipCard );
		}

		return cardObjects;

	}

	public List<ShipCard> getRandomShipsToCompare( Ships first, SelectRandom second, SelectRandom third )
	{
		excludeShip = first;
		List<ShipCard> cardObjects = getRandomShipsToCompare( second, third, SelectRandom.NONE );

		final String pattern = "div.activity-result.ship-result[data-id=\"%s\"]";
		HtmlElement card = getDriver().findElement( By.cssSelector( String.format( pattern, first.getId() ) ) );
		ShipCard shipCard = new ShipCardObject( card );
		cardObjects.add( shipCard );

		return cardObjects;
	}

	public List<ShipCard> getShipsToCompare( Ships first, Ships second, Ships third  )
	{
		List<ShipCard> cardObjects = Lists.newArrayListWithExpectedSize( 3 );
		String[] dup = StringUtils.removeDuplicateStrings( new String[] { first.getId(), second.getId(), third.getId() } );
		PreConditions.checkArgument( dup.length == 3, "one or more ships are equal" );
		final String pattern = "div.activity-result.ship-result[data-id=\"%s\"]";

		HtmlElement card = getDriver().findElement( By.cssSelector( String.format( pattern, first.getId() ) ) );
		ShipCard shipCard = new ShipCardObject( card );
		cardObjects.add( shipCard );

		card = getDriver().findElement( By.cssSelector( String.format( pattern, second.getId() ) ) );
		shipCard = new ShipCardObject( card );
		cardObjects.add( shipCard );

		card = getDriver().findElement( By.cssSelector( String.format( pattern, third.getId() ) ) );
		shipCard = new ShipCardObject( card );
		cardObjects.add( shipCard );

		return cardObjects;
	}

	public void addShipToComparison( ShipCard shipCard, int index )
	{
		boolean isEmpty = compareBanner().hasItems( TimeConstants.FIVE_SECONDS );
		shipCard.doCompare( true );
		if( isEmpty )
		{
			/* first item to be added. the new ship will be in index 0  */

			validateFirstItemInserted( shipCard );
		}
		else
		{
			if( index == 1 )
			{
				validateSecondItemInserted( shipCard );
			}
			else
			{
				validateThirdItemInserted( shipCard );
			}
		}
	}

	//endregion


	//region CruiseShipsPage - Element Finder Methods Section

	private HtmlElement findSortBarDiv()
	{
		return getDriver().findElement( ShipSortBar.ROOT_BY );
	}

	private HtmlElement findCompareBannerDiv()
	{
		return getDriver().findElement( CompareShipBanner.ROOT_BY );
	}

	private List<HtmlElement> findShipCards()
	{
		org.openqa.selenium.By findBy = org.openqa.selenium.By.cssSelector( "div.container div.ship-result" );
		return getDriver().findElements( findBy );
	}

	//endregion


	//region CruiseShipsPage - Private Methods Section

	private void validateThirdItemInserted( ShipCard shipCard )
	{
		List<HtmlElement> items = compareBanner().getItems();
		HtmlElement item = items.get( 2 );
		HtmlElement remove = validateRemove( item, true, 2 );
		validateCircle( item, false, 2 );
		HtmlElement img = item.findElement( By.tagName( "img" ) );
		HtmlElement h4 = item.findElement( By.tagName( "h4" ) );
		validateShipName( h4, shipCard );
		validateShipId( remove, shipCard );
		validateImageSrc( img, shipCard );
	}

	private void validateSecondItemInserted( ShipCard shipCard )
	{
		List<HtmlElement> items = compareBanner().getItems();
		HtmlElement item = items.get( 1 );
		HtmlElement remove = validateRemove( item, true, 1 );
		validateCircle( item, false, 1 );
		HtmlElement img = item.findElement( By.tagName( "img" ) );
		HtmlElement h4 = item.findElement( By.tagName( "h4" ) );
		validateShipName( h4, shipCard );
		validateShipId( remove, shipCard );
		validateImageSrc( img, shipCard );

		item = items.get( 2 );
		validateRemove( item, false, 2  );
		validateCircle( item, true, 2 );
	}

	private void validateFirstItemInserted( ShipCard shipCard )
	{
		List<HtmlElement> items = compareBanner().getItems();
		for( int i = 0; i < items.size(); i ++ )
		{
			HtmlElement item = items.get( i );
			switch( i )
			{
				case 0:
				{
					HtmlElement remove = validateRemove( item, true, i );
					validateCircle( item, false, i );
					HtmlElement img = item.findElement( By.tagName( "img" ) );
					HtmlElement h4 = item.findElement( By.tagName( "h4" ) );
					validateShipName( h4, shipCard );
					validateShipId( remove, shipCard );
					validateImageSrc( img, shipCard );
					break;
				}
				case 1:
				case 2:
				{
					validateRemove( item, false, i );
					validateCircle( item, true, i );
				}
			}

		}
	}

	private void validateShipName( HtmlElement h4, ShipCard card )
	{
		String REASON = "Validate added ship name";
		String ACTUAL_STR = h4.getText();
		Matcher<String> EXPECTED_OF_STR = JMatchers.containsString( card.getShip().getShipName() );
		getDriver().assertThat( REASON, ACTUAL_STR, EXPECTED_OF_STR );
	}

	private void validateShipId( HtmlElement remove, ShipCard card )
	{
		String REASON = "Validate added ship id value";
		String ACTUAL_STR = remove.getAttribute( "data-id" );
		Matcher<String> EXPECTED_OF_STR = JMatchers.equalToIgnoringCase( card.getShip().getId() );
		getDriver().assertThat( REASON, ACTUAL_STR, EXPECTED_OF_STR );
	}

	private void validateImageSrc( HtmlElement img, ShipCard card )
	{
		String REASON = "Validate image src value";
		String ACTUAL_STR = img.getAttribute( "src" );
		Matcher<String> EXPECTED_OF_STR = JMatchers.containsString( card.getExpectedImagePath().replace( "-tile", "-compare" ) );
		getDriver().assertThat( REASON, ACTUAL_STR, EXPECTED_OF_STR );
	}

	private HtmlElement validateRemove( HtmlElement item, boolean exists, int index )
	{
		String reason = String.format( "Validates that \"REMOVE\" %s on item index -> %d", exists ? "exists" : "NOT exists", index );
		Optional<HtmlElement> optional = item.childExists( By.cssSelector( ".ship-pic > a.remove" ) );
		Boolean ACTUAL_BOOL = optional.isPresent();
		Matcher<Boolean> EXPECTED_OF_BOOL = JMatchers.is( exists );
		getDriver().assertThat( reason, ACTUAL_BOOL, EXPECTED_OF_BOOL );
		if( optional.isPresent() ) return optional.get();
		return null;
	}

	private void validateCircle( HtmlElement item, boolean exists, int index )
	{
		String reason = String.format( "Validates that \"EMPTY CIRCLE\" %s on item index -> %d",
				exists ? "exists" : "NOT exists", index );

		Optional<HtmlElement> optional = item.childExists( By.cssSelector( "div.number-circle" ) );
		Boolean ACTUAL_BOOL = optional.isPresent();
		Matcher<Boolean> EXPECTED_OF_BOOL = JMatchers.is( exists );
		getDriver().assertThat( reason, ACTUAL_BOOL, EXPECTED_OF_BOOL );
	}

	//endregion

}
