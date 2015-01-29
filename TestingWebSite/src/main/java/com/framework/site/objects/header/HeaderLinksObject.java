package com.framework.site.objects.header;

import com.framework.asserts.JAssertion;
import com.framework.driver.exceptions.ApplicationException;
import com.framework.driver.objects.AbstractWebObject;
import com.framework.driver.objects.BaseElementObject;
import com.framework.driver.objects.Link;
import com.framework.driver.objects.PageObject;
import com.framework.driver.utils.ui.WaitUtil;
import com.framework.site.objects.header.interfaces.Header;
import com.framework.utils.datetime.TimeConstants;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
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

class HeaderLinksObject extends AbstractWebObject implements Header.HeaderLinks
{

	//region HeaderLinksObject - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( HeaderLinksObject.class );

	private static final String DATA_HIGHLIGHT_PATTERN = "data-highlightpattern";

	//private String dataHighlightPattern;

	//endregion


	//region HeaderLinksObject - Constructor Methods Section

	HeaderLinksObject( WebDriver driver, final WebElement rootElement )
	{
		super( driver, rootElement, Header.HeaderLinks.LOGICAL_NAME );
	}

	//endregion


	//region HeaderLinksObject - Initialization and Validation Methods Section

	@Override
	protected void initWebObject()
	{
		logger.debug( "validating static elements for web object id: <{}>, name:<{}>...",
				getQualifier(), getLogicalName() );

		JAssertion assertion = new JAssertion( getWrappedDriver() );
		ExpectedCondition<List<WebElement>> condition =
				WaitUtil.presenceOfAllBy( By.cssSelector( ".header-nav-additional input" ) );
		assertion.assertWaitThat(
				"Validate all \".header-nav-additional input\" elements exists", TimeConstants.FIFTY_HUNDRED_MILLIS, condition );
	}

	//endregion


	//region HeaderLinksObject - Service Methods Section

	private WebElement getRoot()
	{
		return getBaseRootElement( Header.HeaderLinks.ROOT_BY );
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
		logger.debug( "hovering over top-menu element item <\"{}\">", item.getTitle() );

		WebElement itemAnchor = findTopLevelMenuItemAnchor( item.getTitle() );
		String dataHighlightPattern = itemAnchor.getAttribute( DATA_HIGHLIGHT_PATTERN );
		Link itemLink = new Link( itemAnchor );

		// Waiting for anchor to include class name 'hover'

		ExpectedCondition<Boolean> condition = WaitUtil.elementAttributeToMatch( itemAnchor, "class", containsString( "hover" ) );
		itemLink.hover();

		try
		{
			WaitUtil.wait5( getWrappedDriver() ).until( condition );
		}
		catch ( TimeoutException tEx )
		{
			logger.error( "throwing a new ApplicationException on {}#hoverOnMenuItem.", getClass().getSimpleName() );
			ApplicationException aex = new ApplicationException( getWrappedDriver(), tEx.getMessage(), tEx );
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
		logger.info( "Returning a list ot top-level link names ( span.org[testContent] )" );
		List<WebElement> spans = findLinksOrgSpans();
		List<String> names = BaseElementObject.extractAttribute( spans, "textContent" );
		logger.info( "returning a list of top-level links names [ {} ] ...", names );
		return names.toArray( new String[ names.size() ] );
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
		By findBy = By.className( "pull-left" );
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
		By findBy = By.className( "pull-right" );
		return getRoot().findElement( findBy );
	}

	/**
	 * Full xpath : {@code /html/body/div[4]/div[1]/div[1]/div[1]/div[5]/div/nav/ul[1]/li//span[@class='org']}
	 * Css path   : {@code #ccl-refresh-header nav > ul.pull-left a > span.org}

	 * @return  a {@literal List<WebElement>} of span.org elements
	 */
	private List<WebElement> findLinksOrgSpans()
	{
		By findBy = By.cssSelector( "ul.pull-left a > span.org" );
		return getWrappedDriver().findElements( findBy );
	}

	/**
	 * Css Path  : {@code #ccl-refresh-header nav > ul.pull-left > li > a, #ccl-refresh-header nav > ul.pull-right > li.search > a}
	 *
	 * @return the anchor {@code WebElement} of the matching top-level menu item by argument {@code name}
	 */
	private WebElement findTopLevelMenuItemAnchor( String name )
	{
		final String XPATH_PATTERN = "//span[@class='org' and text()='%s']/..";
		By findBy = By.xpath( String.format( XPATH_PATTERN, name ) );
		return getRoot().findElement( findBy );
	}


	//endregion

}