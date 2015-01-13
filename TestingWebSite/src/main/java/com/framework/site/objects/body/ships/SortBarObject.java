package com.framework.site.objects.body.ships;

import com.framework.asserts.JAssertions;
import com.framework.driver.exceptions.ApplicationException;
import com.framework.driver.objects.AbstractWebObject;
import com.framework.driver.utils.ui.ExtendedBy;
import com.framework.driver.utils.ui.WaitUtil;
import com.framework.matchers.MatcherUtils;
import com.framework.site.objects.body.interfaces.ShipSortBar;
import com.google.common.base.MoreObjects;
import com.google.common.base.Throwables;
import org.apache.commons.lang3.math.NumberUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

//todo: class documentation

public class SortBarObject extends AbstractWebObject implements ShipSortBar
{

	//region SortBarObject - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( SortBarObject.class );

	private static final int LAYOUT_INDEX = 1;

	private static final int SORT_INDEX = 0;

	//endregion


	//region SortBarObject - Constructor Methods Section

	public SortBarObject( WebDriver driver, final WebElement rootElement )
	{
		super( ShipSortBar.LOGICAL_NAME, driver, rootElement );
	}

	//endregion


	//region SortBarObject - Initialization and Validation Methods Section

	@Override
	protected void initWebObject()
	{
		logger.debug( "validating static elements for web object id: <{}>, name:<{}>...", getId(), getLogicalName() );
		logger.info( "Parsing ship card..." );

		try
		{

		}
		catch ( AssertionError ae )
		{
			Throwables.propagateIfInstanceOf( ae, ApplicationException.class );
			logger.error( "throwing a new WebObjectException on {}#initWebObject.", getClass().getSimpleName() );
			ApplicationException ex = new ApplicationException( objectDriver.getWrappedDriver(), ae.getMessage(),ae );
			ex.addInfo( "cause", "verification and initialization process for object " + getLogicalName() + " was failed." );
			throw ex;
		}
	}

	//endregion


	//region SortBarObject - Service Methods Section

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper( this )
				.add( "logical name", getLogicalName() )
				.add( "id", getId() )
				.omitNullValues()
				.toString();
	}

	private WebElement getRoot()
	{
		try
		{
			rootElement.getTagName();
			return rootElement;
		}
		catch ( StaleElementReferenceException ex )
		{
			logger.warn( "auto recovering from StaleElementReferenceException ..." );
			return objectDriver.findElement( ShipSortBar.ROOT_BY );
		}
	}

	//endregion


	//region SortBarObject - Business Methods Section

	//todo : documentation
	@Override
	public int getResults()
	{
		try
		{
		 	String value = findSortBarH3().getText();
			return NumberUtils.createInteger( value );
		}
		catch ( Throwable t )
		{
			Throwables.propagateIfInstanceOf( t, ApplicationException.class );
			logger.error( "throwing a new ApplicationException on {}#getShipNames.", getClass().getSimpleName() );
			ApplicationException appEx = new ApplicationException( objectDriver.getWrappedDriver(), t.getMessage(), t );
			appEx.addInfo( "business process", "failed to get ship names." );
			throw appEx;
		}
	}

	//todo : documentation
	@Override
	public void setLayoutType( final LayoutType layout )
	{
		logger.info( "Changing layout type to <\"{}\">", layout.name() );
		WebDriverWait wdw = WaitUtil.wait5( objectDriver );
		WebDriverWait wdw20 = WaitUtil.wait20( objectDriver );
		String layoutText = layout.equals( LayoutType.BY_GRID ) ? "Grid" : "List";
		final String SELECTED_PATTERN = ".//a[@data-param='layout' and @data-label='%s' and @class='active-filter']";
		final String SELECTED_XPATH = String.format( SELECTED_PATTERN, layoutText );

		try
		{
			LayoutType current = getLayoutType();
			if( ! current.equals( layout ) )
			{
				logger.info( "Changing ships layout to <\"{}\">", layout.name() );

				/* finding all required web-elements */

				WebElement notActiveLayout = findLayoutNotActiveFilter();
				WebElement subList = findSubListUl( notActiveLayout );
				WebElement arrow = findArrows().get( LAYOUT_INDEX );
				WebElement toggle = findToggles().get( LAYOUT_INDEX );

				arrow.click();

				/* waiting for web-element classes */

				wdw.until( WaitUtil.visibilityOf( subList, true ) );
				wdw.until( WaitUtil.elementAttributeToMatch( toggle, "class", MatcherUtils.containsString( "active" ) ) );
				notActiveLayout.click();
				JAssertions.assertWaitThat( wdw20 )
						.matchesCondition( WaitUtil.presenceBy( By.xpath( SELECTED_XPATH ) ) );
			}
		}
		catch ( Throwable t )
		{
			Throwables.propagateIfInstanceOf( t, ApplicationException.class );
			logger.error( "throwing a new ApplicationException on {}#setLayoutType.", getClass().getSimpleName() );
			ApplicationException appEx = new ApplicationException( objectDriver.getWrappedDriver(), t.getMessage(), t );
			appEx.addInfo( "business process", "failed to set ships layout type <\"" + layout.name() + "\">" );
			throw appEx;
		}
	}

	@Override
	public void setSortType( final SortType sortType )
	{
		logger.info( "Changing sort type to <\"{}\">", sortType.name() );
		WebDriverWait wdw = WaitUtil.wait5( objectDriver );
		WebDriverWait wdw20 = WaitUtil.wait20( objectDriver );
		String sortText = sortType.equals( SortType.DISPLAY_FEATURED ) ? "Featured" : "A-Z";
		final String SELECTED_PATTERN = ".//a[@data-param='sort' and @data-label='%s' and @class='active-filter']";
		final String SELECTED_XPATH = String.format( SELECTED_PATTERN, sortText );

		try
		{
			SortType current = getSortType();
			if( ! current.equals( sortType ) )
			{
				logger.info( "Changing ships sort to <\"{}\">", sortType.name() );

				/* finding all required web-elements */

				WebElement notActiveSort = findSortNotActiveFilter();
				WebElement subList = findSubListUl( notActiveSort );
				WebElement arrow = findArrows().get( SORT_INDEX );
				WebElement toggle =  findToggles().get( SORT_INDEX );

				arrow.click();

				/* waiting for web-element classes */

				wdw.until( WaitUtil.visibilityOf( subList, true ) );
				wdw.until( WaitUtil.elementAttributeToMatch( toggle, "class", MatcherUtils.containsString( "active" ) ) );
				notActiveSort.click();
				JAssertions.assertWaitThat( wdw20 )
						.matchesCondition( WaitUtil.presenceBy( By.xpath( SELECTED_XPATH ) ) );
			}
		}
		catch ( Throwable t )
		{
			Throwables.propagateIfInstanceOf( t, ApplicationException.class );
			logger.error( "throwing a new ApplicationException on {}#setSortType.", getClass().getSimpleName() );
			ApplicationException appEx = new ApplicationException( objectDriver.getWrappedDriver(), t.getMessage(), t );
			appEx.addInfo( "business process", "failed to set ships sort type <\"" + sortType.name() + "\">" );
			throw appEx;
		}
	}

	//todo : documentation
	@Override
	public LayoutType getLayoutType()
	{
		try
		{
			WebElement anchor = findLayoutActiveFilterAnchor();
			String dataLabel = anchor.getAttribute( "data-label" );
			if( dataLabel.equals( "Grid" ) ) return LayoutType.BY_GRID;
			return LayoutType.BY_LIST;
		}
		catch ( Throwable t )
		{
			Throwables.propagateIfInstanceOf( t, ApplicationException.class );
			logger.error( "throwing a new ApplicationException on {}#getLayoutType.", getClass().getSimpleName() );
			ApplicationException appEx = new ApplicationException( objectDriver.getWrappedDriver(), t.getMessage(), t );
			appEx.addInfo( "business process", "failed to get the ships layout type." );
			throw appEx;
		}
	}

	//todo : documentation
	@Override
	public SortType getSortType()
	{
		try
		{
		 	WebElement anchor = findSortActiveFilterAnchor();
			String dataLabel = anchor.getAttribute( "data-label" );
			if( dataLabel.equals( "Featured" ) ) return SortType.DISPLAY_FEATURED;
			return SortType.DISPLAY_AZ;
		}
		catch ( Throwable t )
		{
			Throwables.propagateIfInstanceOf( t, ApplicationException.class );
			logger.error( "throwing a new ApplicationException on {}#getSortBy.", getClass().getSimpleName() );
			ApplicationException appEx = new ApplicationException( objectDriver.getWrappedDriver(), t.getMessage(), t );
			appEx.addInfo( "business process", "failed to get the ships sort type." );
			throw appEx;
		}
	}


	//endregion


	//region SortBarObject - Element Finder Methods Section

	private WebElement findSortBarH3()
	{
		By findBy = By.tagName( "h3" );
		return getRoot().findElement( findBy );
	}

	private WebElement findSortActiveFilterAnchor()
	{
		By findBy = By.cssSelector( "a.active-filter[data-param='sort']" );
		return getRoot().findElement( findBy );
	}

	private WebElement findSortNotActiveFilter()
	{
		By findBy = By.xpath( ".//a[@data-param='sort' and not(@class='active-filter')]" );
		return getRoot().findElement( findBy );
	}

	private WebElement findLayoutNotActiveFilter()
	{
		By findBy = By.xpath( ".//a[@data-param='layout' and not(@class='active-filter')]" );
		return getRoot().findElement( findBy );
	}

	private WebElement findLayoutActiveFilterAnchor()
	{
		By findBy = By.cssSelector( "a.active-filter[data-param='layout']" );
		return getRoot().findElement( findBy );
	}

	private WebElement findSubListUl( WebElement type )
	{
		By findBy = By.xpath( "/parent::li/parent::ul" );
		return getRoot().findElement( findBy );
	}

	private List<WebElement> findArrows()
	{
		By findBy = ExtendedBy.composite( By.tagName( "i" ), By.className( "arrow" ) );
		return getRoot().findElements( findBy );
	}

	private List<WebElement> findToggles()
	{
		By findBy = ExtendedBy.composite( By.tagName( "a" ), By.className( "toggle" ) );
		return getRoot().findElements( findBy );
	}



//	private WebElement findArrow( String current )
//	{
//		final String XPATH_FMT = "//ul[@class='options']//a[@class='toggle']/span[text()='%s']/../i";
//		String xpath = String.format( XPATH_FMT, current );
//		By findBy = By.xpath( xpath );
//		return getRoot().findElement( findBy );
//	}
//
//	private WebElement findArrowParent( WebElement arrow )
//	{
//		By findBy = By.xpath( "/parent::a" );
//		return arrow.findElement( findBy );
//	}
//
//	private WebElement findSubListUl( String current )
//	{
//		final String XPATH_FMT = "//a[@data-label='%s']/parent::li/parent::ul" ;
//		String xpath = String.format( XPATH_FMT, current );
//		By findBy = By.xpath( xpath );
//		return getRoot().findElement( findBy );
//	}

	//endregion

}
