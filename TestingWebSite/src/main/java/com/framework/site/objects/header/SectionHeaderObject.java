package com.framework.site.objects.header;

import com.framework.asserts.JAssertions;
import com.framework.driver.exceptions.ApplicationException;
import com.framework.driver.objects.AbstractWebObject;
import com.framework.driver.utils.ui.WaitUtil;
import com.framework.site.objects.header.interfaces.Header;
import com.google.common.base.MoreObjects;
import com.google.common.base.Throwables;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.site.objects.header
 *
 * Name   : HeaderObject
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-01-05
 *
 * Time   : 23:11
 */

public class SectionHeaderObject extends AbstractWebObject implements Header
{

	//region HeaderSectionObject - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( SectionHeaderObject.class );

	// ------------------------------------------------------------------------|
	// --- WEB-OBJECTS DEFINITIONS --------------------------------------------|
	// ------------------------------------------------------------------------|

	private NotificationBar notificationBar = null;

	private MessageBar messageBar = null;

	private HeaderLinks headerLinks = null;

	private HeaderSubscribe headerSubscribe = null;

	private HeaderBranding headerBranding = null;

	private NavigationAdditional navigationAdditional = null;

	//endregion


	//region HeaderSectionObject - Constructor Methods Section

	public SectionHeaderObject( WebDriver driver, final WebElement rootElement )
	{
		super( Header.LOGICAL_NAME, driver, rootElement );
	}

	//endregion


	//region HeaderSectionObject - Initialization and Validation Methods Section

	@Override
	protected void initWebObject()
	{
		logger.debug( "validating static elements for web object id: <{}>, name:<{}>...", getId(), getLogicalName() );
		WebDriverWait wew = WaitUtil.wait10( objectDriver );

		try
		{
			JAssertions.assertWaitThat( wew ).matchesCondition( WaitUtil.presenceOfChildBy( getRoot(), MessageBar.ROOT_BY ) );
			JAssertions.assertWaitThat( wew ).matchesCondition( WaitUtil.presenceOfChildBy( getRoot(), NotificationBar.ROOT_BY ) );
			JAssertions.assertWaitThat( wew ).matchesCondition( WaitUtil.presenceOfChildBy( getRoot(), HeaderBranding.ROOT_BY ) );
			JAssertions.assertWaitThat( wew ).matchesCondition( WaitUtil.presenceOfChildBy( getRoot(), HeaderSubscribe.ROOT_BY ) );
			JAssertions.assertWaitThat( wew ).matchesCondition( WaitUtil.presenceOfChildBy( getRoot(), HeaderLinks.ROOT_BY ) );
			JAssertions.assertWaitThat( wew ).matchesCondition( WaitUtil.presenceOfChildBy( getRoot(), NavigationAdditional.ROOT_BY ) );
		}
		catch ( AssertionError ae )
		{
			Throwables.propagateIfInstanceOf( ae, ApplicationException.class );
			logger.error( "throwing a new WebObjectException on {}#initWebObject.", getClass().getSimpleName() );
			ApplicationException ex = new ApplicationException( objectDriver.getWrappedDriver(), ae.getMessage(), ae );
			ex.addInfo( "cause", "verification and initialization process for object " + getLogicalName() + " was failed." );
			throw ex;
		}
	}

	//endregion


	//region HeaderSectionObject - Service Methods Section

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper( this )
				.add( "logical name", getLogicalName() )
				.add( "id", getId() )
				.omitNullValues()
				.toString();
	}

	@Override
	public MessageBar messageBar()
	{
		if ( null == this.messageBar )
		{
			this.messageBar = new MessageBarObject( objectDriver, findMessageBarDiv() );
		}
		return messageBar;
	}

	@Override
	public NotificationBar notificationBar()
	{
		if ( null == this.notificationBar )
		{
			this.notificationBar = new NotificationBarObject( objectDriver, findNotificationBarDiv() );
		}
		return notificationBar;
	}

	@Override
	public HeaderBranding headerBranding()
	{
		String region = objectDriver.getJavaScript().getString( Header.SITE_REGION_SCRIPT );

		if ( null == this.headerBranding )
		{
			if( region.equals( "AU" ) )
			{
				return new CurrencySelectorObject( objectDriver, getRoot() );
			}
			else
			{
				return new TopDestinationsObject( objectDriver, getRoot() );
			}
		}
		return headerBranding;
	}

	@Override
	public HeaderSubscribe subscribe()
	{
		if ( null == this.headerSubscribe )
		{
			this.headerSubscribe = new HeaderSubscribeObject( objectDriver, findHeaderSubscribeDiv() );
		}
		return headerSubscribe;
	}

	@Override
	public HeaderLinks headerLinks()
	{
		if ( null == this.headerLinks )
		{
			this.headerLinks = new HeaderLinksObject( objectDriver, findHeaderLinksDiv() );
		}
		return headerLinks;
	}

	@Override
	public NavigationAdditional navigationAdditional()
	{
		if ( null == this.navigationAdditional )
		{
			this.navigationAdditional = new NavigationAdditionalObject( objectDriver, findNavigationAdditionalDiv() );
		}
		return navigationAdditional;
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
			return objectDriver.findElement( SectionHeaderObject.ROOT_BY );
		}
	}

	//endregion


	//region HeaderSectionObject - Business Methods Section

	//endregion


	//region HeaderSectionObject - Element Finder Methods Section

	private WebElement findMessageBarDiv()
	{
		return getRoot().findElement( MessageBar.ROOT_BY );
	}

	private WebElement findNotificationBarDiv()
	{
		return getRoot().findElement( NotificationBar.ROOT_BY );
	}

	private WebElement findHeaderBrandingDiv()
	{
		return getRoot().findElement( HeaderBranding.ROOT_BY );
	}

	private WebElement findHeaderSubscribeDiv()
	{
		return getRoot().findElement( HeaderSubscribe.ROOT_BY );
	}

	private WebElement findHeaderLinksDiv()
	{
		return getRoot().findElement( HeaderLinks.ROOT_BY );
	}

	private WebElement findNavigationAdditionalDiv()
	{
		return getRoot().findElement( NavigationAdditional.ROOT_BY );
	}

	private WebElement findCurrencySelectorLi()
	{
		return findHeaderBrandingDiv().findElement( HeaderBranding.CurrencySelector.ROOT_BY );
	}

	private WebElement findTopDestinations()
	{
		return findHeaderBrandingDiv().findElement( HeaderBranding.TopDestinations.ROOT_BY );
	}

	//endregion

}
