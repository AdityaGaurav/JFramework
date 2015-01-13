package com.framework.site.objects.body.common;

import com.framework.asserts.JAssertions;
import com.framework.driver.exceptions.ApplicationException;
import com.framework.driver.objects.AbstractWebObject;
import com.framework.driver.objects.Link;
import com.framework.driver.utils.ui.ListWebElementUtils;
import com.framework.driver.utils.ui.WaitUtil;
import com.framework.matchers.MatcherUtils;
import com.framework.site.objects.body.interfaces.BreadcrumbsBar;
import com.framework.site.objects.body.interfaces.ShipSortBar;
import com.framework.site.pages.core.HomePage;
import com.google.common.base.MoreObjects;
import com.google.common.base.Throwables;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static ch.lambdaj.Lambda.*;

//todo: class documentation

public class BreadcrumbsObject extends AbstractWebObject implements BreadcrumbsBar.Breadcrumbs
{

	//region BreadcrumbsObject - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( BreadcrumbsObject.class );

	private static final By HOME_LINK_TEXT = By.linkText( "Home" );

	//endregion


	//region BreadcrumbsObject - Constructor Methods Section

	public BreadcrumbsObject( WebDriver driver, final WebElement rootElement )
	{
		super( BreadcrumbsBar.Breadcrumbs.LOGICAL_NAME, driver, rootElement );
	}

	//endregion


	//region BreadcrumbsObject - Initialization and Validation Methods Section

	@Override
	protected void initWebObject()
	{
		logger.debug( "validating static elements for web object id: <{}>, name:<{}>...", getId(), getLogicalName() );

		By firstChild = By.cssSelector( "ul.breadcrumbs .first-child" );    // should be first-child
		// last-child must be active.last-child
		By lastChildActive = By.cssSelector( ".breadcrumb-bar > ul.breadcrumbs li.active.last-child" );
		WebDriverWait wdw = WaitUtil.wait10( objectDriver );

		try
		{
			/* Home always is first */

			JAssertions.assertWaitThat( wdw ).matchesCondition( WaitUtil.presenceOfChildBy( getRoot(), HOME_LINK_TEXT ) );
			JAssertions.assertWaitThat( wdw ).matchesCondition( WaitUtil.presenceBy( firstChild ) );
			JAssertions.assertWaitThat( wdw ).matchesCondition( WaitUtil.presenceBy( lastChildActive ) );
		}
		catch ( AssertionError ae )
		{
			Throwables.propagateIfInstanceOf( ae, ApplicationException.class );
			logger.error( "throwing a new WebObjectException on {}#initWebObject.", getClass().getSimpleName() );
			ApplicationException appEx = new ApplicationException( objectDriver.getWrappedDriver(), ae.getMessage(),ae );
			appEx.addInfo( "cause", "verification and initialization process for object " + getLogicalName() + " was failed." );
			throw appEx;
		}
	}

	//endregion


	//region BreadcrumbsObject - Service Methods Section

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


	//region BreadcrumbsObject - Business Methods Section

	//todo: method documentation
	@Override
	public HomePage navigateHome()
	{
		logger.info( "Navigating using breadcrumbs to 'Home'" );
		try
		{
			Link findHomeLink = new Link( objectDriver ,findHomeAnchor() );
			findHomeLink.hover( true );
			findHomeLink.click();
			return new HomePage( objectDriver );
		}
		catch ( Throwable t )
		{
			Throwables.propagateIfInstanceOf( t, ApplicationException.class );
			logger.error( "throwing a new ApplicationException on {}#navigateHome.", getClass().getSimpleName() );
			ApplicationException appEx = new ApplicationException( objectDriver.getWrappedDriver(), t.getMessage(), t );
			appEx.addInfo( "business process", "failed to navigate to home page via breadcrumbs" );
			throw appEx;
		}
	}

	//todo: method documentation
	@Override
	public HomePage navigateFirstChild()
	{
		logger.info( "Navigating using breadcrumbs to 'first-child'" );

		try
		{
			Link firstChildLink = new Link( objectDriver ,findFirstChild() );
			firstChildLink.hover( true );
			firstChildLink.click();
			return new HomePage( objectDriver );
		}
		catch ( Throwable t )
		{
			Throwables.propagateIfInstanceOf( t, ApplicationException.class );
			logger.error( "throwing a new ApplicationException on {}#navigateFirstChild.", getClass().getSimpleName() );
			ApplicationException appEx = new ApplicationException( objectDriver.getWrappedDriver(), t.getMessage(), t );
			appEx.addInfo( "business process", "failed to navigate to first-child via breadcrumbs ( Home Page )" );
			throw appEx;
		}
	}

	//todo: method documentation
	@Override
	public boolean isLastChildClickable()
	{
		logger.info( "Determine if the 'last-child' is clickable ( active link )" );

		try
		{
			WebElement lastActive = findLastChildActiveAnchor();
			return ! lastActive.getAttribute( "href" ).isEmpty();
		}
		catch ( Throwable t )
		{
			Throwables.propagateIfInstanceOf( t, ApplicationException.class );
			logger.error( "throwing a new ApplicationException on {}#navigateFirstChild.", getClass().getSimpleName() );
			ApplicationException appEx = new ApplicationException( objectDriver.getWrappedDriver(), t.getMessage(), t );
			appEx.addInfo( "business process", "failed to navigate to first-child via breadcrumbs ( Home Page )" );
			throw appEx;
		}
	}

	//todo: method documentation
	@Override
	public List<String> getNames()
	{
		logger.info( "Getting a List of breadcrumbs names" );

		try
		{
			List<WebElement> lis = findBreadcrumbsLis();
			return ListWebElementUtils.extractElementsText( lis );
		}
		catch ( Throwable t )
		{
			Throwables.propagateIfInstanceOf( t, ApplicationException.class );
			logger.error( "throwing a new ApplicationException on {}#getNames.", getClass().getSimpleName() );
			ApplicationException appEx = new ApplicationException( objectDriver.getWrappedDriver(), t.getMessage(), t );
			appEx.addInfo( "business process", "failed to read breadcrumbs names" );
			throw appEx;
		}
	}

	//todo: method documentation
	@Override
	public boolean breadcrumbItemExists( final String itemName )
	{
		logger.info( "Determine if breadcrumbs item <\"{}\"> exists.", itemName );

		try
		{
			objectDriver.manage().timeouts().implicitlyWait( 0, TimeUnit.MILLISECONDS );
			WebElement we = selectFirst( findBreadcrumbsLis(),
					having( on( WebElement.class ).getText(),
							MatcherUtils.equalToIgnoringWhiteSpace( itemName ) ) );
			return we != null;
		}
		catch ( Throwable t )
		{
			Throwables.propagateIfInstanceOf( t, ApplicationException.class );
			logger.error( "throwing a new ApplicationException on {}#getNames.", getClass().getSimpleName() );
			ApplicationException appEx = new ApplicationException( objectDriver.getWrappedDriver(), t.getMessage(), t );
			appEx.addInfo( "business process", "failed to read breadcrumbs names" );
			throw appEx;
		}
	}

	//endregion


	//region BreadcrumbsObject - Element Finder Methods Section

	private WebElement findHomeAnchor()
	{
		return getRoot().findElement( HOME_LINK_TEXT );
	}

	private WebElement findFirstChild()
	{
		By findBy = By.cssSelector( "li.first-child > a" );
		return getRoot().findElement( findBy );
	}

	private WebElement findLastChildActiveAnchor()
	{
		By findBy = By.cssSelector( "li.last-child.active > a" );
		return getRoot().findElement( findBy );
	}

	private List<WebElement> findBreadcrumbsLis()
	{
		By findBy = By.cssSelector( "ul.breadcrumbs li" );
		return getRoot().findElements( findBy );
	}

	//endregion

}
