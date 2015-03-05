package com.framework.site.objects.body.common;

import ch.lambdaj.Lambda;
import com.framework.asserts.JAssertion;
import com.framework.driver.event.HtmlElement;
import com.framework.driver.event.HtmlObject;
import com.framework.driver.objects.AbstractWebObject;
import com.framework.driver.objects.Link;
import com.framework.site.objects.body.interfaces.BreadcrumbsBar;
import com.framework.site.pages.core.HomePage;
import com.google.common.base.Optional;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static com.framework.utils.datetime.TimeConstants.FIVE_SECONDS;
import static org.hamcrest.Matchers.is;

//todo: class documentation

public class BreadcrumbsObject extends AbstractWebObject implements BreadcrumbsBar.Breadcrumbs
{

	//region BreadcrumbsObject - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( BreadcrumbsObject.class );

	private static final By HOME_LINK_TEXT = By.linkText( "Home" );

	//endregion


	//region BreadcrumbsObject - Constructor Methods Section

	public BreadcrumbsObject( final HtmlElement rootElement )
	{
		super( rootElement, BreadcrumbsBar.Breadcrumbs.LOGICAL_NAME );
		initWebObject();
	}

	//endregion


	//region BreadcrumbsObject - Initialization and Validation Methods Section

	@Override
	protected void initWebObject()
	{
		logger.info( "validating static elements for web object id: <{}>, name:<{}>...",
				getQualifier(), getLogicalName() );

		final String REASON = "assert that element \"%s\" exits";
		JAssertion assertion = getRoot().createAssertion();

		Optional<HtmlElement> e = getRoot().childExists( By.cssSelector( "ul.breadcrumbs .first-child" ), FIVE_SECONDS );
		assertion.assertThat( String.format( REASON, "ul.breadcrumbs .first-child" ), e.isPresent(), is( true ) );

		e = getRoot().childExists( By.cssSelector( "ul.breadcrumbs .last-child" ), FIVE_SECONDS );
		assertion.assertThat( String.format( REASON, "ul.breadcrumbs .last-child" ), e.isPresent(), is( true ) );
	}

	//endregion


	//region BreadcrumbsObject - Service Methods Section

	private HtmlElement getRoot()
	{
		return getBaseRootElement( BreadcrumbsBar.Breadcrumbs.ROOT_BY );
	}


	//endregion


	//region BreadcrumbsObject - Business Methods Section

	//todo: method documentation
	@Override
	public HomePage navigateHome()
	{
		logger.info( "Navigating \"Home\" using breadcrumbs first item." );

		Link findHomeLink = new Link( findHomeAnchor() );
		findHomeLink.hover( true );
		findHomeLink.click();
		return new HomePage();
	}

	//todo: method documentation
	@Override
	public boolean isLastChildEnabled()
	{
		logger.info( "Determine if the breadcrumbs 'last-child' is enabled ( active link )" );

		HtmlElement lastActive = findLastChildActiveAnchor();
		return ! lastActive.getAttribute( "href" ).isEmpty();
	}

	@Override
	public String getLastChildName()
	{
		String text = findLastChildActiveAnchor().getText();
		logger.info( "reading last-child breadcrumb item name < {} >", text );
		return text;
	}

	//todo: method documentation
	@Override
	public List<String> getNames()
	{
		List<HtmlElement> lis = findBreadcrumbsLis();
		List<String> names = HtmlObject.extractText( lis );
		logger.info( "Getting a List of breadcrumbs names < {} >", Lambda.joinFrom( names ) );
		return names;
	}

	//todo: method documentation
	@Override
	public boolean breadcrumbItemExists( final String itemName )
	{
		logger.info( "Determine if breadcrumbs item <\"{}\"> exists.", itemName );
		return getRoot().childExists( By.linkText( itemName ) ).isPresent();
	}

	@Override
	public void clickOnItem( final String item )
	{
		logger.info( "Clicking on breadcrumb item < {} >", item );
		List<HtmlElement> lis = findBreadcrumbsLis();
		for( HtmlElement li : lis )
		{
			String text = li.getText();
			if( text.endsWith( item ) )
			{
				li.findElement( By.tagName( "a" ) ).click();
				return;
			}
		}
	}

	@Override
	public HtmlElement getLastChild()
	{
		HtmlElement he = findLastChildLi();
		logger.info( "Extracting the breadcrumb last-child element < {} > ", he.getAttribute( "testContext" ) );
		return he;
	}

	//endregion


	//region BreadcrumbsObject - Element Finder Methods Section

	private HtmlElement findHomeAnchor()
	{
		return getRoot().findElement( HOME_LINK_TEXT );
	}

	private HtmlElement findFirstChild()
	{
		By findBy = By.cssSelector( "li.first-child > a" );
		return getRoot().findElement( findBy );
	}

	private HtmlElement findLastChildActiveAnchor()
	{
		By findBy = By.cssSelector( "li.last-child > a" );
		return getRoot().findElement( findBy );
	}

	private HtmlElement findLastChildLi()
	{
		By findBy = By.cssSelector( "li.last-child" );
		return getRoot().findElement( findBy );
	}

	private List<HtmlElement> findBreadcrumbsLis()
	{
		By findBy = By.cssSelector( "ul.breadcrumbs li" );
		return getRoot().findElements( findBy );
	}

	//endregion

}
