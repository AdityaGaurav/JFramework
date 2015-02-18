package com.framework.driver.event;

import com.framework.config.Configurations;
import com.framework.driver.utils.ui.HighlightStyle;
import com.framework.driver.utils.ui.HighlightStyleBackup;
import com.framework.utils.datetime.DateTimeUtils;
import com.framework.utils.datetime.Sleeper;
import com.google.common.base.Preconditions;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.SessionId;
import org.slf4j.Logger;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;

import static com.framework.config.FrameworkProperty.*;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.driver.event
 *
 * Name   : WebDriverListenerAdapter 
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-02-05 
 *
 * Time   : 00:40
 *
 */

public class WebDriverListenerAdapter implements WebDriverListener
{

	//region WebDriverListenerAdapter - Variables Declaration and Initialization Section.

	private static Logger logger; //= LoggerFactory.getLogger( WebDriverListenerAdapter.class );

	private DateTime dateTime;

	private static final NumberFormat formatter = new DecimalFormat( "#,###,###" );

	private final Deque<HighlightStyleBackup> styleBackups = new ArrayDeque<HighlightStyleBackup>();

	//endregion

	@Override
	public void onNavigateTo( final WebDriverEvent event )
	{

	}

	@Override
	public void onClose( final WebDriverEvent event )
	{
		if( event.isBefore() )
		{
			this.dateTime = new DateTime();
			final String MSG_FORMAT = "Closing handle '{}' on \"{}\", total handles: {}";
			final String handle = event.getDriver().getWindowHandle();
			final int handles = event.getDriver().getWindowHandles().size();
			final String url = event.getDriver().getCurrentUrl();

			logger.debug( MSG_FORMAT, handle, url, handles );
		}
		else
		{
			final SessionId sessionId = ( ( RemoteWebDriver ) event.getDriver() ).getSessionId();
			Duration duration = new Duration( this.dateTime, DateTime.now() );  /* measuring between before and after */
			final String fp = DateTimeUtils.getFormattedPeriod( duration );
			logger.debug( "Closed handles on session '{}'  ( duration: {} )", sessionId, fp );
		}
	}

	@Override
	public void onQuit( final WebDriverEvent event )
	{
		if(  event.isBefore() )
		{
			this.dateTime = new DateTime();
			final SessionId sessionId = ( ( RemoteWebDriver ) event.getDriver() ).getSessionId();
			final String MSG_FORMAT = "Quitting session '{}'";
			logger.debug( MSG_FORMAT, sessionId );
		}
		else
		{
			Duration duration = new Duration( this.dateTime, DateTime.now() );
			final String fp = DateTimeUtils.getFormattedPeriod( duration );
			logger.debug( "Quitting WebDriver Instance. ( duration: {} )", fp );
		}
	}

	@Override
	public void onCookieAction( final WebDriverEvent event )
	{
		if( event.isBefore() )
		{
			final SessionId sessionId = ( ( RemoteWebDriver ) event.getDriver() ).getSessionId();
			final int cookiesCount = event.getDriver().manage().getCookies().size();
			this.dateTime = DateTime.now();

			if( event.getType() == WebDriverEventSource.EVENT_DELETE_ALL_COOKIES )
			{
				final String MSG_FORMAT = "Deleting all cookies ( {} ) from session < {} >";
				logger.debug( MSG_FORMAT, sessionId, cookiesCount );
			}
			else if( event.getType() == WebDriverEventSource.EVENT_DELETE_COOKIE_NAME )
			{
				String cookieName = ( String ) event.getArguments()[ 0 ];
				final String MSG_FORMAT = "Deleting cookie with name < '{}' > from session < {} >";
				logger.debug( MSG_FORMAT, cookieName, sessionId );
			}
			else if( event.getType() == WebDriverEventSource.EVENT_DELETE_COOKIE )
			{
				Cookie cookie = ( Cookie ) event.getArguments()[ 0 ];
				String cookieName = cookie.getName();
				final String MSG_FORMAT = "Deleting cookie with name < '{}' > and value < {} > from session < {} >";
				logger.debug( MSG_FORMAT, cookieName, cookie.getValue(), sessionId );
			}
			else if( event.getType() == WebDriverEventSource.EVENT_ADD_COOKIE )
			{
				Cookie cookie = ( Cookie ) event.getArguments()[ 0 ];
				String cookieName = cookie.getName();
				final String MSG_FORMAT = "Adding cookie with name < '{}' > and value < {} > on session < {} >";
				logger.debug( MSG_FORMAT, cookieName, cookie.getValue(), sessionId );
			}
		}
		else
		{
			Duration duration = new Duration( this.dateTime, DateTime.now() );
			String durationName = DateTimeUtils.getFormattedPeriod( duration );
			if( event.getType() == WebDriverEventSource.EVENT_DELETE_ALL_COOKIES )
			{
				int count = event.getDriver().manage().getCookies().size();
				if( count == 0 )
				{
					final String MSG_FORMAT = "All cookies were removed successfully. ( duration: {} )";
					logger.debug( MSG_FORMAT, durationName );
				}
				else
				{
					final String MSG_FORMAT = "Not all cookies were removed. still remained < {} > cookies . ( duration: {} )";
					logger.warn( MSG_FORMAT, count, durationName );
				}
			}
			else if( event.getType() == WebDriverEventSource.EVENT_DELETE_COOKIE_NAME )
			{
				String cookieName = ( String ) event.getArguments()[ 0 ];
				Cookie cookie = event.getDriver().manage().getCookieNamed( cookieName );
				if( null == cookie )
				{
					final String MSG_FORMAT = "Cookie name < '{}' > was removed successfully.";
					logger.debug( MSG_FORMAT, cookieName );
				}
				else
				{
					logger.warn( "Cookie < {} > was not removed.", cookieName );
				}
			}
			else if( event.getType() == WebDriverEventSource.EVENT_DELETE_COOKIE )
			{
				String cookieName = ( String ) event.getArguments()[ 0 ];
				Cookie cookie = event.getDriver().manage().getCookieNamed( cookieName );
				if( null == cookie )
				{
					final String MSG_FORMAT = "Cookie name < '{}' > was removed successfully.";
					logger.debug( MSG_FORMAT, cookieName );
				}
				else
				{
					logger.warn( "Cookie < {} > was not removed.", cookieName );
				}
			}
			else if( event.getType() == WebDriverEventSource.EVENT_ADD_COOKIE )
			{
				String cookieName = ( String ) event.getArguments()[ 0 ];
				Cookie cookie = event.getDriver().manage().getCookieNamed( cookieName );
				if( null != cookie )
				{
					final String MSG_FORMAT = "Cookie name < '{}' > was added successfully to current session.";
					logger.debug( MSG_FORMAT, cookieName );
				}
				else
				{
					logger.warn( "Cookie < {} > was not added to session.", cookieName );
				}
			}
		}
	}

	@Override
	public void onFindElement( final WebDriverEvent event )
	{
		if( event.isBefore() )
		{
			final String MSG_FORMAT1 = "Searching for an element by < {} > on page \"{}\" ";
			final String MSG_FORMAT2 = MSG_FORMAT1 + "; current handle is < {} >  of total < {} > handles.";
			final String MSG_FORMAT3 = "Element [{}] searching for child by < {} > on page \"{}\"";
			final String MSG_FORMAT4 = MSG_FORMAT3 + "; current handle is < {} >  of total < {} > handles.";

			//if( event.getType() == WebDriverEventSource.EVENT_DRIVER_FIND_ELEMENT )
			this.dateTime = DateTime.now();
			final String currentUrl = event.getDriver().getCurrentUrl();
			final int handles = event.getDriver().getWindowHandles().size();
			final By locator = ( By ) event.getArguments()[ 0 ];

			if( event.getType() == WebDriverEventSource.EVENT_DRIVER_FIND_ELEMENT )
			{
				if( handles == 1 )
				{
					logger.debug( MSG_FORMAT1, locator, currentUrl );
				}
				else
				{
					final String handle = event.getDriver().getWindowHandle();
					logger.debug( MSG_FORMAT2, locator, currentUrl, handle, handles );
				}
			}
			else if( event.getType() == WebDriverEventSource.EVENT_ELEMENT_FIND_ELEMENT )
			{
				String parent = ( ( HtmlObject ) event.getSource() ).getLocator();
				if( handles == 1 )
				{
					logger.debug( MSG_FORMAT3, parent, locator, currentUrl );
				}
				else
				{
					final String handle = event.getDriver().getWindowHandle();
					logger.debug( MSG_FORMAT4, parent, locator, currentUrl, handle, handles );
				}
			}
		}
		else
		{
			Duration duration = new Duration( this.dateTime, DateTime.now() );
			final String durationName = DateTimeUtils.getFormattedPeriod( duration );
			final String MSG_FORMAT1 = "Element was successfully located ( duration: {} )";
			logger.debug( MSG_FORMAT1, durationName );
		}
	}

	@Override
	public void onFindElements( final WebDriverEvent event )
	{
		if( event.isBefore() )
		{
			final String MSG_FORMAT1 = "Searching for all elements by < {} > on page \"{}\" ";
			final String MSG_FORMAT2 = MSG_FORMAT1 + "; current handle is < {} >  of total < {} > handles.";
			final String MSG_FORMAT3 = "Element [{}] searching for all child elements by < {} > on page \"{}\"";
			final String MSG_FORMAT4 = MSG_FORMAT3 + "; current handle is < {} >  of total < {} > handles.";

			this.dateTime = DateTime.now();
			final String currentUrl = event.getDriver().getCurrentUrl();
			final int handles = event.getDriver().getWindowHandles().size();
			final By locator = ( By ) event.getArguments()[ 0 ];
			if( event.getType() == WebDriverEventSource.EVENT_DRIVER_FIND_ELEMENTS )
			{
				if( handles == 1 )
				{
					logger.debug( MSG_FORMAT1, locator, currentUrl );
				}
				else
				{
					final String handle = event.getDriver().getWindowHandle();
					logger.debug( MSG_FORMAT2, locator, currentUrl, handle, handles );
				}
			}
			if( event.getType() == WebDriverEventSource.EVENT_ELEMENT_FIND_ELEMENTS )
			{
				String parent = ( ( HtmlObject ) event.getSource() ).getLocator();
				if( handles == 1 )
				{
					logger.debug( MSG_FORMAT3, parent, locator, currentUrl );
				}
				else
				{
					final String handle = event.getDriver().getWindowHandle();
					logger.debug( MSG_FORMAT4, parent, locator, currentUrl, handle, handles );
				}
			}
		}
		else
		{
			Duration duration = new Duration( this.dateTime, DateTime.now() );
			final String durationName = DateTimeUtils.getFormattedPeriod( duration );
			final By locator = ( By ) event.getArguments()[ 0 ];
			final int size = ( Integer ) event.getArguments()[ 1 ];
			final String MSG_FORMAT1 = "Number of elements located by < {} > are < {} > ( duration: {} )";
			logger.debug( MSG_FORMAT1, locator, size, durationName );
		}
	}

	@Override
	public void onTimeoutChange( final WebDriverEvent event )
	{
		if( event.isBefore() )
		{
			this.dateTime = DateTime.now();
			final SessionId sessionId = ( ( RemoteWebDriver ) event.getDriver() ).getSessionId();
			final long timeout = Long.valueOf( event.getArguments()[ 0 ].toString() );
			if( event.getType() == WebDriverEventSource.EVENT_PAGE_LOAD_TIMEOUT )
			{
				final String MSG_FORMAT = "Changing page load timeout value to '{} {}' on session <{}>";
				logger.debug( MSG_FORMAT, formatter.format( timeout ), "ms", sessionId );
			}
			else if( event.getType() == WebDriverEventSource.EVENT_SCRIPT_TIMEOUT )
			{
				final String MSG_FORMAT = "Changing script execution timeout value to '{} {}' on session <{}>";
				logger.debug( MSG_FORMAT, formatter.format( timeout ), "ms", sessionId );
			}
			else
			{
				final String MSG_FORMAT = "Changing implicitly wait value to '{} {}' on session <{}>";
				logger.debug( MSG_FORMAT, formatter.format( timeout ), "ms", sessionId );
			}
		}
		else
		{
			Duration duration = new Duration( this.dateTime, DateTime.now() );
			final String durationName = DateTimeUtils.getFormattedPeriod( duration );
			if( event.getType() == WebDriverEventSource.EVENT_PAGE_LOAD_TIMEOUT )
			{
				final String MSG_FORMAT = "Page load timeout value was changed. ( duration: {} )";
				logger.debug( MSG_FORMAT, durationName );
			}
			else if( event.getType() == WebDriverEventSource.EVENT_SCRIPT_TIMEOUT )
			{
				final String MSG_FORMAT = "Script execution timeout value was changed. ( duration: {} )";
				logger.debug( MSG_FORMAT, durationName );
			}
			else
			{
				final String MSG_FORMAT = "Implicitly wait value changed. ( duration: {} )";
				logger.debug( MSG_FORMAT, durationName );
			}
		}
	}

	@Override
	public void onWindowChange( final WebDriverEvent event )
	{
		String handle = event.getDriver().getWindowHandle();
		Dimension size;

		if( event.isBefore() )
		{
			this.dateTime = DateTime.now();
			size = event.getDriver().manage().window().getSize();
			if( event.getType() == WebDriverEventSource.EVENT_WINDOW_MAXIMIZE )
			{
				final String fromSize = String.format( "%dx%d", size.getWidth(), size.getHeight() );
				final String MSG_FORMAT = "Maximizing window handle: '{}'. current size is < {} >";
				logger.debug( MSG_FORMAT, handle, fromSize );
			}
		}
		else
		{
			Duration duration = new Duration( this.dateTime, DateTime.now() );
			String durationName = DateTimeUtils.getFormattedPeriod( duration );
			size = event.getDriver().manage().window().getSize();
			if( event.getType() == WebDriverEventSource.EVENT_WINDOW_MAXIMIZE )
			{
				final String toSize = String.format( "%dX%d", size.getWidth(), size.getHeight() );
				final String MSG_FORMAT = "Window was maximized. current size is < {} >. ( duration: {} )";
				logger.debug( MSG_FORMAT, toSize, durationName );
			}
		}
	}

	@Override
	public void onJavaScript( final WebDriverEvent event )
	{

	}

	@Override
	public void onSwitchToFrame( final WebDriverEvent event )
	{
		if( event.isBefore() )
		{
			this.dateTime = DateTime.now();
			final String handle = event.getDriver().getWindowHandle();
			Object o = event.getArguments()[ 0 ];
			logger.debug( "Switching to frame < {} >. current handle is < {} >", o, handle );
		}
		else
		{
			Duration duration = new Duration( this.dateTime, DateTime.now() );
			String durationName = DateTimeUtils.getFormattedPeriod( duration );
			final String handle = event.getDriver().getWindowHandle();
			Object o = event.getArguments()[ 0 ];
			logger.debug( "WebDriver switched to frame < {} >. current handle is < {} >", o, handle );
		}
	}

	@Override
	public void onSwitchToWindow( final WebDriverEvent event )
	{

	}

	@Override
	public void onKeyboardAction( final WebDriverEvent event )
	{

	}

	@Override
	public void onMouseAction( final WebDriverEvent event )
	{

	}

	@Override
	public void onHover( final WebDriverEvent event )
	{
		if( event.isBefore() )
		{
			this.dateTime = DateTime.now();
			final String locator = ( String ) event.getArguments()[0];

			if( event.getType() == WebDriverEventSource.EVENT_HOVER )
			{
				HtmlElement element = ( HtmlElement ) event.getSource();
				final String MSG_FORMAT1 = "Hovering on web element < {} >";
				logger.debug( MSG_FORMAT1, locator );
				if( isBlinking() )
				{
					element.blink();
				}
			}
		}
		else
		{
			final String locator = ( String ) event.getArguments()[0];
			Duration duration = new Duration( this.dateTime, DateTime.now() );
			String durationName = DateTimeUtils.getFormattedPeriod( duration );
			logger.debug( "Successfully hovered over element < {} >.  ( duration: {} )", locator, durationName );
		}
	}

	@Override
	public void onClick( final WebDriverEvent event )
	{
		if( event.isBefore() )
		{
			this.dateTime = DateTime.now();
			HtmlElement element = ( HtmlElement ) event.getSource();
			if( event.getType() == WebDriverEventSource.EVENT_CLICK )
			{
				final String MSG_FORMAT1 = "clicking on web element < {} >";
				logger.debug( MSG_FORMAT1, element.getLocator() );
				if ( isMarking() )
				{
					mark( element.getWrappedHtmlDriver(), element, HighlightStyle.ELEMENT_STYLES[ 1 ] );
				}
			}
		}
		else
		{
			HtmlElement element = ( HtmlElement ) event.getSource();
			Duration duration = new Duration( this.dateTime, DateTime.now() );
			String durationName = DateTimeUtils.getFormattedPeriod( duration );
			logger.debug( "Successfully hovered over element < {} >.  ( duration: {} )", element.getLocator(), durationName );
		}
	}

	@Override
	public void onGetText( final WebDriverEvent event )
	{
		if( event.isBefore() )
		{
			this.dateTime = DateTime.now();
			final String locator = ( String ) event.getArguments()[ 0 ];
			final String MSG_FORMAT1 = "reading TEXT from web element < {} >";
			logger.debug( MSG_FORMAT1, locator );
		}
		else
		{
			Duration duration = new Duration( this.dateTime, DateTime.now() );
			String durationName = DateTimeUtils.getFormattedPeriod( duration );
			final String text = ( String ) event.getArguments()[ 0 ];
			logger.debug( "TEXT value is < {} >.  ( duration: {} )", text, durationName );
		}
	}

	@Override
	public void onSendKeys( final WebDriverEvent event )
	{
		if( event.isBefore() )
		{
			this.dateTime = DateTime.now();
			final String keys = ( String ) event.getArguments()[ 0 ];
			HtmlElement element = ( HtmlElement ) event.getSource();
			final String MSG_FORMAT1 = "sending the following keys < {} > to web element < {} >";
			logger.debug( MSG_FORMAT1, keys, element.getLocator() );
			if ( isMarking() )
			{
				mark( ( ( HtmlElement ) event.getSource() ).getWrappedHtmlDriver(), element, HighlightStyle.ELEMENT_STYLES[ 3 ] );
			}
		}
		else
		{
			HtmlElement element = ( HtmlElement ) event.getSource();
			Duration duration = new Duration( this.dateTime, DateTime.now() );
			String durationName = DateTimeUtils.getFormattedPeriod( duration );
			logger.debug( "Keys were sent to target.  ( duration: {} )", element.getLocator(), durationName );
		}
	}

	@Override
	public void onClear( final WebDriverEvent event )
	{
		if( event.isBefore() )
		{
			this.dateTime = DateTime.now();
			final String locator = ( String ) event.getArguments()[ 0 ];
			final String value = ( String ) event.getArguments()[ 1 ];
			HtmlElement element = ( HtmlElement ) event.getSource();
			final String MSG_FORMAT1 = "clearing web element < {} > - current value is < {} >";
			logger.debug( MSG_FORMAT1, locator, value );
			if ( isHighlightOnClear() )
			{
				highlight( ( ( HtmlElement ) event.getSource() ).getWrappedHtmlDriver(), element, HighlightStyle.ELEMENT_STYLES[ 3 ] );
				Sleeper.pauseFor( 400 );
				unHighlight( ( ( HtmlElement ) event.getSource() ).getWrappedHtmlDriver() );
			}
		}
		else
		{
			final String locator = ( String ) event.getArguments()[0];
			Duration duration = new Duration( this.dateTime, DateTime.now() );
			String durationName = DateTimeUtils.getFormattedPeriod( duration );
			String value = ( ( HtmlElement ) event.getSource() ).getAttribute( "value" );
			if( StringUtils.isEmpty( value ) )
			{
				logger.debug( "Successfully cleared value of {}.  ( duration: {} )", locator, durationName );
			}
			else
			{
				logger.warn( "Could not clear element {} value. the current value is {}", locator, value );
			}
		}
	}

	private boolean isMarking()
	{
		return BROWSER_MARK_ON_CLICK.from( Configurations.getInstance(), false ) ;
	}

	private boolean isBlinking()
	{
		return BROWSER_BLINK_ON_HOVER.from( Configurations.getInstance(), false ) ;
	}

	public boolean isHighlightOnClear()
	{
		return BROWSER_HIGHLIGHT_ON_CLEAR.from( Configurations.getInstance(), false ) ;
	}

	private void highlight( final HtmlDriver driver, final HtmlElement elementFinder, HighlightStyle style )
	{
		HtmlDriver drv = Preconditions.checkNotNull( driver, "WebDriver driver should not be null" );
		HtmlElement he = Preconditions.checkNotNull( elementFinder, "WebElement elementFinder should not be null" );

		//List<Locator> selectedFrameLocators = elementFinder.getCurrentFrameLocators();
		Map<String, String> prevStyles = style.doHighlight( drv, he );
		if ( prevStyles == null )
		{
			return;
		}
		HighlightStyleBackup backup = new HighlightStyleBackup( prevStyles, he );
		this.styleBackups.push( backup );
	}

	private void unHighlight( final HtmlDriver driver )
	{
		HtmlDriver drv = Preconditions.checkNotNull( driver, "WebDriver driver should not be null" );

		while ( ! this.styleBackups.isEmpty() )
		{
			HighlightStyleBackup backup = this.styleBackups.pop();
			backup.restore( drv );
		}
	}

	private void mark( final HtmlDriver driver, final HtmlElement element, HighlightStyle style )
	{
		if( null == driver || null == element ) return;

		style.doHighlight( driver, element );
	}

	public static void setLogger( final Logger logger )
	{
		WebDriverListenerAdapter.logger = logger;
	}
}
