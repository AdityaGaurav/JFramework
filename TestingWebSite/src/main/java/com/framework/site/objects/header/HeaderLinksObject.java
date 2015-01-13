package com.framework.site.objects.header;

import com.framework.driver.exceptions.ApplicationException;
import com.framework.driver.objects.AbstractWebObject;
import com.framework.driver.objects.Link;
import com.framework.driver.objects.PageObject;
import com.framework.driver.utils.ui.ExtendedBy;
import com.framework.driver.utils.ui.ListWebElementUtils;
import com.framework.driver.utils.ui.WaitUtil;
import com.framework.site.objects.header.interfaces.Header;
import com.google.common.base.MoreObjects;
import com.google.common.base.Throwables;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static com.framework.matchers.MatcherUtils.containsString;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.site.objects.header
 *
 * Name   : HeaderLinksObject
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-01-06
 *
 * Time   : 11:34
 */

public class HeaderLinksObject extends AbstractWebObject implements Header.HeaderLinks
{

	//region HeaderLinksObject - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( HeaderLinksObject.class );

	private static final String DATA_HIGHLIGHT_PATTERN = "data-highlightpattern";

	private String dataHighlightPattern;

	//endregion


	//region HeaderLinksObject - Constructor Methods Section

	public HeaderLinksObject( WebDriver driver, final WebElement rootElement )
	{
		super( Header.HeaderLinks.LOGICAL_NAME, driver, rootElement );
	}

	//endregion


	//region HeaderLinksObject - Initialization and Validation Methods Section

	@Override
	protected void initWebObject()
	{

	}

	//endregion


	//region HeaderLinksObject - Service Methods Section

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
		}
		catch ( StaleElementReferenceException ex )
		{
			rootElement = objectDriver.findElement( Header.HeaderLinks.ROOT_BY );
		}

		return rootElement;
	}

	//endregion


	//region HeaderLinksObject - Business Methods Section

	@Override
	public PageObject selectMenuItem( final LevelOneMenuItem item )
	{
		return null;
	}

	@Override
	public void hoverOnMenuItem( final LevelOneMenuItem item )
	{
		try
		{
			logger.debug( "hovering over top-menu element item <\"{}\">", item.getTitle() );
			WebElement itemAnchor =  findTopLevelMenuItemAnchor( item.getTitle() );
			this.dataHighlightPattern = itemAnchor.getAttribute( DATA_HIGHLIGHT_PATTERN );
			Link itemLink = new Link( objectDriver, itemAnchor );
			WebDriverWait wdw = WaitUtil.wait5( objectDriver);

			/* Waiting for anchor to include class name 'hover' */

			ExpectedCondition<Boolean> condition = WaitUtil.elementAttributeToMatch( itemAnchor, "class", containsString( "hover" ) );

			itemLink.hover();
			wdw.until(  condition );

		}
		catch ( Throwable ae )
		{
			Throwables.propagateIfInstanceOf( ae, ApplicationException.class );
			logger.error( "throwing a new ApplicationException on {}#hoverOnMenuItem.", getClass().getSimpleName() );
			ApplicationException aex = new ApplicationException( objectDriver.getWrappedDriver(), ae.getMessage(), ae );
			aex.addInfo( "business process", "hovering over top-level menu item \"" + item.getTitle() + "\"" );
			throw aex;
		}
	}

	@Override
	public boolean isDisplayed()
	{
		return getRoot().isDisplayed();
	}

	@Override
	public String[] getLinkNames()
	{
		try
		{
			List<WebElement> spans = findLinksOrgSpans();
			String names = ListWebElementUtils.joinElementsText( spans, "," );
			logger.info( "returning a list of top-level links names [ {} ] ...", names );
			return StringUtils.split( names, "," );
		}
		catch ( Throwable ae )
		{
			Throwables.propagateIfInstanceOf( ae, ApplicationException.class );
			logger.error( "throwing a new ApplicationException on {}#getLinkNames.", getClass().getSimpleName() );
			ApplicationException aex = new ApplicationException( objectDriver.getWrappedDriver(), ae.getMessage(), ae );
			aex.addInfo( "business process", "failed to read top-level link names" );
			throw aex;
		}
	}

	//endregion


	//region HeaderLinksObject - Info Methods Section

	/**
	 * @return the latest attribute {@code data-highlightpattern} value of the top-level menu item selected or hovered.
	 */
	public String getDataHighlightPattern()
	{
		return dataHighlightPattern;
	}

	//endregion

	//region HeaderLinksObject - Element Finder Methods Section

	/**
	 * Full xpath  : {@code /html/body/div[4]/div[1]/div[1]/div[1]/div[5]/div/nav}
	 * CSS Path    : {@code #ccl-refresh-header > div.header-nav > div.header-links > div > nav}
	 *
	 * @return an instance of nav {@code WebElement}
	 *
	 * @throws java.util.NoSuchElementException if not found.
	 */
	private WebElement findNavElement()
	{
		By findBy = By.tagName( "nav" );
		return getRoot().findElement( findBy );
	}

	/**
	 * Full xpath  : {@code /html/body/div[4]/div[1]/div[1]/div[1]/div[5]/div/nav/ul[1]}
	 * Css Path    : {@code #ccl-refresh-header > div.header-nav > div.header-links > div > nav > ul.pull-left}
	 * Css Style   : {@code #ccl-refresh-header nav > ul.pull-left}
	 *
	 * @return an instance of ul.pull-left {@code WebElement}
	 *
	 * @throws java.util.NoSuchElementException if not found.
	 */
	private WebElement findPullLeftUl()
	{
		ExtendedBy findBy = ExtendedBy.composite( By.tagName( "ul" ), By.className( "pull-left" ) );
		return getRoot().findElement( findBy );
	}

	/**
	 * Full xpath  : {@code /html/body/div[4]/div[1]/div[1]/div[1]/div[5]/div/nav/ul[2]}
	 * Css Path    : {@code #ccl-refresh-header  nav > ul.pull-right}
	 *
	 * @return an instance of ul.pull-right {@code WebElement}
	 *
	 * @throws java.util.NoSuchElementException if not found.
	 */
	private WebElement findPullRightUl()
	{
		By findBy = By.cssSelector( "#ccl-refresh-header nav > ul.pull-right" );
		return objectDriver.findElement( findBy );
	}

	/**
	 * Full xpath : {@code /html/body/div[4]/div[1]/div[1]/div[1]/div[5]/div/nav/ul[1]/li//span[@class='org']}
	 * Css path   : {@code #ccl-refresh-header nav > ul.pull-left a > span.org}

	 * @return  a {@literal List<WebElement>} of span.org elements
	 */
	private List<WebElement> findLinksOrgSpans()
	{
		By findBy = By.cssSelector( "#ccl-refresh-header nav > ul.pull-left a > span.org" );
		return objectDriver.findElements( findBy );
	}

	/**
	 * Css Path  : {@code #ccl-refresh-header nav > ul.pull-left > li > a, #ccl-refresh-header nav > ul.pull-right > li.search > a}
	 *
	 * @return the anchor {@code WebElement} of the matching top-level menu item by argument {@code name}
	 */
	private WebElement findTopLevelMenuItemAnchor( String name )
	{
		final String XPATH_PATTERN = ".//ul[@class='pull-left']//span[@class='org' and text()='%s']/parent::a";
		By findBy = By.xpath( String.format( XPATH_PATTERN, name ) );
		return getRoot().findElement( findBy );
	}


	//endregion

}