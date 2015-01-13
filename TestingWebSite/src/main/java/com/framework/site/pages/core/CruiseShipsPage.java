package com.framework.site.pages.core;

import com.framework.asserts.JAssertions;
import com.framework.driver.exceptions.ApplicationException;
import com.framework.driver.utils.ui.ExtendedBy;
import com.framework.driver.utils.ui.WaitUtil;
import com.framework.matchers.MatcherUtils;
import com.framework.site.config.InitialPage;
import com.framework.site.data.SmartRandomShipCard;
import com.framework.site.objects.body.common.BreadcrumbsBarObject;
import com.framework.site.objects.body.interfaces.*;
import com.framework.site.objects.body.ships.CompareShipBannerObject;
import com.framework.site.objects.body.ships.FilterCategoriesObject;
import com.framework.site.objects.body.ships.ShipCardObject;
import com.framework.site.objects.body.ships.SortBarObject;
import com.framework.site.pages.CarnivalPage;
import com.framework.utils.spring.AppContextProxy;
import com.google.common.base.MoreObjects;
import com.google.common.base.Throwables;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.math.NumberUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.joda.time.DateTime;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileWriter;
import java.util.Collections;
import java.util.List;
import java.util.Locale;


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

public class CruiseShipsPage extends CarnivalPage
{

	//region CruiseShipsPage - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( CruiseShipsPage.class );

	private static final String LOGICAL_NAME = "Cruise Ships Page";

	private static final String URL_PATH = "/cruise-ships.aspx";

	private static final String PAGE_TITLE_KEY = "cruise.ships.title";

	private static final String RANDOM_SHIP_CARDS_XML = "random/random.ships.cards.xml";

	private static final String COMPARE_RANDOM_SHIP_XML = "random/compare.random.ships.xml";

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

	public CruiseShipsPage( final WebDriver driver )
	{
		super( LOGICAL_NAME, driver );
	}

	//endregion


	//region CruiseShipsPage - Initialization and Validation Methods Section

	@Override
	protected void validatePageUrl()
	{
		WebDriverWait wait = WaitUtil.wait60( pageDriver );
		ExpectedCondition<Boolean> expectedCondition;

		try
		{
			expectedCondition = WaitUtil.urlMatches( MatcherUtils.endsWith( URL_PATH ) );

			/* asserting that current url matches expected url */

			JAssertions.assertWaitThat( wait ).matchesCondition( expectedCondition );
			logger.info( "page url successfully asserted -> endsWith( \"{}\" )", URL_PATH );

			/* asserting page title */

			Locale locale = ( Locale ) InitialPage.getRuntimeProperties().getRuntimePropertyValue( "locale" );
			final String EXPECTED_TITLE = ( String ) AppContextProxy.getInstance().getMessage( PAGE_TITLE_KEY, null, locale );
			JAssertions.assertThat( pageDriver ).matchesTitle( MatcherUtils.equalTo( EXPECTED_TITLE ) );
			logger.info( "page title successfully asserted -> equalToIgnoringCase( \"{}\" )", EXPECTED_TITLE  );
		}
		catch ( AssertionError ae )
		{
			Throwables.propagateIfInstanceOf( ae, ApplicationException.class );
			logger.error( "throwing a new WebObjectException on {}#validatePageUrl.", getClass().getSimpleName() );
			ApplicationException ex = new ApplicationException( pageDriver.getWrappedDriver(), ae.getMessage(), ae );
			ex.addInfo( "cause", "verification and initialization process for object " + getLogicalName() + " was failed." );
			throw ex;
		}
	}

	@Override
	protected void initElements()
	{
		logger.debug( "validating static elements for: <{}>, name:<{}>...", getId(), getLogicalName() );

		try
		{

		}
		catch ( AssertionError ae )
		{
			Throwables.propagateIfInstanceOf( ae, ApplicationException.class );
			logger.error( "throwing a new WebObjectException on {}#initElements.", getClass().getSimpleName() );
			ApplicationException ex = new ApplicationException( pageDriver.getWrappedDriver(), ae.getMessage(), ae );
			ex.addInfo( "cause", "verification and initialization process for page " + getLogicalName() + " was failed." );
			throw ex;
		}
	}

	//endregion


	//region CruiseShipsPage - Service Methods Section

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper( this )
				.add( "object id", getId() )
				.add( "page id", pageId() )
				.add( "logical name", getLogicalName() )
				.add( "pageName", pageName() )
				.add( "site region", getSiteRegion() )
				.add( "title", getTitle() )
				.add( "url", getCurrentUrl() )
				.omitNullValues()
				.toString();
	}

	public ShipSortBar sortBar()
	{
		if ( null == this.sortBar )
		{
			this.sortBar = new SortBarObject( pageDriver, findSortBarDiv() );
		}
		return sortBar;
	}

	public BreadcrumbsBar breadcrumbsBar()
	{
		if ( null == this.breadcrumbsBar )
		{
			this.breadcrumbsBar = new BreadcrumbsBarObject( pageDriver, findBreadcrumbBarDiv() );
		}
		return breadcrumbsBar;
	}

	public CompareShipBanner compareBanner()
	{
		if ( null == this.compareShipBanner )
		{
			this.compareShipBanner = new CompareShipBannerObject( pageDriver, findCompareBannerDiv() );
		}
		return compareShipBanner;
	}

	public FilterCategories filterCategories()
	{
		if ( null == this.filterCategories )
		{
			this.filterCategories = new FilterCategoriesObject( pageDriver, findFilterCategories() );
		}
		return filterCategories;
	}

	public List<ShipCard> getShipsCards()
	{
		shipCards = Lists.newArrayList();
		List<WebElement> elements = findShipCards();
		for( WebElement we : elements )
		{
			ShipCard shipCard = new ShipCardObject( pageDriver, we );
			shipCards.add( shipCard );
		}

		return shipCards;

	}

	public void writeToRandomXml( Document document, String xml ) throws Exception
	{
		File f = new File( ClassLoader.getSystemResource( xml ).toURI() );
		String path = f.getAbsolutePath();

		XMLWriter writer = new XMLWriter( new FileWriter( path ) );
		writer.write( document );
		writer.close();
	}

	//endregion


	//region CruiseShipsPage - Business Methods Section

	public ShipCard getSmartRandomShipCardId()
	{
		List<SmartRandomShipCard> cardInfo = Lists.newArrayList();
		int selections = 0;
		DateTime dte = DateTime.now();

		try
		{
			SAXReader reader = new SAXReader();
			Document document = reader.read( ClassLoader.getSystemResource( RANDOM_SHIP_CARDS_XML ) );
			List elements = document.getRootElement().elements( "ship" );
			String siteRegion = getSiteRegion();
			for( Object o : elements )
			{
				Element ship = ( Element ) o;
				String validFor = ship.element( "validFor" ).getStringValue();
				if( validFor.contains( siteRegion ) )
				{
					String lastSelected = ship.element( "lastSelected" ).getStringValue();
					dte = DateTime.parse( "1-Jan-1900" );
					if( ! lastSelected.equals( "null" ) ) dte = DateTime.parse( lastSelected );
					selections = NumberUtils.createInteger( ship.element( "selections" ).getStringValue() );
					String id = ship.attributeValue( "id" );
					String description = ship.attributeValue( "desc" );
					SmartRandomShipCard so = new SmartRandomShipCard( id, dte, selections );
					so.setDescription( description );
					cardInfo.add( so );
				}
			}

			Collections.sort( cardInfo, SmartRandomShipCard.Comparators.BOTH );
			String id = cardInfo.get( 0 ).getId();
			WebElement card = findShipCardById( id );

			Element shipElement = document.getRootElement().elementByID( id );
			shipElement.element( "selections" ).setText( String.valueOf( selections + 1 ) );
			shipElement.element( "lastSelected" ).setText( dte.toString( "dd-MMM-yyyy" ) );
			writeToRandomXml( document, RANDOM_SHIP_CARDS_XML );

			return new ShipCardObject( pageDriver, card );

		}
		catch ( Throwable t )
		{
			Throwables.propagateIfInstanceOf( t, ApplicationException.class );
			logger.error( "throwing a new ApplicationException on {}#getRandomShipCard.", getClass().getSimpleName() );
			ApplicationException appEx = new ApplicationException( pageDriver.getWrappedDriver(), t.getMessage(), t );
			appEx.addInfo( "business process", "failed to set select a random ship card" );
			throw appEx;
		}
	}

	//endregion


	//region CruiseShipsPage - Element Finder Methods Section

	private WebElement findSortBarDiv()
	{
		return pageDriver.findElement( ShipSortBar.ROOT_BY );
	}

	private WebElement findCompareBannerDiv()
	{
		return pageDriver.findElement( CompareShipBanner.ROOT_BY );
	}

	private List<WebElement> findShipCards()
	{
		By findBy = By.cssSelector( "div.container div.ship-result" );
		return pageDriver.findElements( findBy );
	}

	private WebElement findShipCardById( String id )
	{
		By findBy = ExtendedBy.composite( By.tagName( "div" ), ExtendedBy.attribute( "data-id", id ) );
		return pageDriver.findElement( findBy );
	}

	private WebElement findFilterCategories()
	{
		return pageDriver.findElement( FilterCategories.ROOT_BY );
	}

	//endregion

}
