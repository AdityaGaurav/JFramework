package com.framework.driver.event;

import com.framework.utils.datetime.DateTimeUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.SessionId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;


/**
 * the class implements the {@link com.framework.driver.event.EventListener} interface
 * it records and reports WebDriver events.
 */

public class DriverListenerAdapter extends AbstractEventListener
{

	//region DriverListenerAdapter - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( DriverListenerAdapter.class );

	public DriverListenerAdapter()
	{
		super();
	}

	//endregion

	//todo: documentation
	@Override
	public void beforeNavigateTo( final String url, final WebDriver driver )
	{
		this.dateTime = DateTime.now();
		final String cu = driver.getCurrentUrl();
		final String handle = driver.getWindowHandle();
		final int handles = driver.getWindowHandles().size();
		final String MSG_FORMAT1 = "navigating from url \"{}\" to url -> \"{}\"; current handle is <{}>";
		final String MSG_FORMAT2 = MSG_FORMAT1 + " of total <{}> handles.";

		if( handles == 1 )
		{
			logger.debug( MSG_FORMAT1, cu, url, handle );
		}
		else
		{
			logger.debug( MSG_FORMAT2, cu, url, handle, handles );
		}
	}

	//todo: documentation
	@Override
	public void afterNavigateTo( final String url, final WebDriver driver )
	{
		Duration duration = new Duration( this.dateTime, DateTime.now() );  /* measuring between before and after */
		final String cu = driver.getCurrentUrl();
		final String fp = DateTimeUtils.getFormattedPeriod( duration );
		final String MSG_FORMAT = "navigation to \"{}\" executed. current url is \"{}\"; ( duration: {} ).";

		logger.debug( MSG_FORMAT, url, cu, fp );
	}

	//todo: documentation
	@Override
	public void beforeFindBy( final By by, final WebDriver driver )
	{
		this.dateTime = new DateTime();
		final String cu = driver.getCurrentUrl();
		final String handle = driver.getWindowHandle();
		final int handles = driver.getWindowHandles().size();
		final String MSG_FORMAT1 = "Searching for element or elements[ {} ] on \"{}\" ";
		final String MSG_FORMAT2 = MSG_FORMAT1 + "; current handle is <{}>  of total <{}> handles.";

		if( handles == 1 )
			logger.debug( MSG_FORMAT1, by, cu );
		else
			logger.debug( MSG_FORMAT2, by, cu, handle, handles );
	}

	//todo: documentation
	@Override
	public void afterFindBy( final By by, final WebDriver driver )
	{
		Duration duration = new Duration( this.dateTime, DateTime.now() );  /* measuring between before and after */
		final String fp = DateTimeUtils.getFormattedPeriod( duration );
		final String MSG_FORMAT1 = "Element [ {} ] found; ( duration: {} )";

		logger.debug( MSG_FORMAT1, by, fp );
	}

	//todo: documentation
	@Override
	public void afterFindBy( final By by, int size, final WebDriver driver )
	{
		Duration duration = new Duration( this.dateTime, DateTime.now() );  /* measuring between before and after */
		final String fp = DateTimeUtils.getFormattedPeriod( duration );
		final String MSG_FORMAT1 = "Elements [ {} ] found; number of elements: {} ( duration: {} )";

		logger.debug( MSG_FORMAT1, by, size, fp );
	}

	//todo: documentation
	@Override
	public void beforePageLoadTimeout( WebDriver driver, final long time, final TimeUnit unit )
	{
		this.dateTime = new DateTime();
		final String MSG_FORMAT = "Changing page load timeout to '{} {}' on session <{}>";
		final SessionId sessionId = ( ( RemoteWebDriver ) driver ).getSessionId();

		logger.debug( MSG_FORMAT, formatter.format( time ), unit.toString(), sessionId );
	}

	//todo: documentation
	@Override
	public void afterPageLoadTimeout( final long time, final TimeUnit unit )
	{
		Duration duration = new Duration( this.dateTime, DateTime.now() );  /* measuring between before and after */
		final String fp = DateTimeUtils.getFormattedPeriod( duration );
		final String MSG_FORMAT = "Page load timeout changed. ( duration: {} )";

		logger.debug( MSG_FORMAT, fp );
	}

	//todo: documentation
	@Override
	public void beforeSetScriptTimeout( final WebDriver driver, final long time, final TimeUnit unit )
	{
		this.dateTime = new DateTime();
		final String MSG_FORMAT = "Changing script execution timeout to '{} {}' on session <{}>";
		final SessionId sessionId = ( ( RemoteWebDriver ) driver ).getSessionId();

		logger.debug( MSG_FORMAT, formatter.format( time ), unit.toString(), sessionId );
	}

	//todo: documentation
	@Override
	public void afterSetScriptTimeout( final long time, final TimeUnit unit )
	{
		Duration duration = new Duration( this.dateTime, DateTime.now() );  /* measuring between before and after */
		final String fp = DateTimeUtils.getFormattedPeriod( duration );
		final String MSG_FORMAT = "Script execution timeout changed. ( duration: {} )";

		logger.debug( MSG_FORMAT, fp );
	}

	//todo: documentation
	@Override
	public void beforeImplicitlyWait( final WebDriver driver, final long time, final TimeUnit unit )
	{
		this.dateTime = new DateTime();
		final String MSG_FORMAT = "Changing implicitly wait value to '{} {}' on session <{}>";
		final SessionId sessionId = ( ( RemoteWebDriver ) driver ).getSessionId();

		logger.debug( MSG_FORMAT, formatter.format( time ), unit.toString(), sessionId );
	}

	//todo: documentation
	@Override
	public void afterImplicitlyWait( final long time, final TimeUnit unit )
	{
		Duration duration = new Duration( this.dateTime, DateTime.now() );  /* measuring between before and after */
		final String fp = DateTimeUtils.getFormattedPeriod( duration );
		final String MSG_FORMAT = "Implicitly wait value was changed. ( duration: {} )";

		logger.debug( MSG_FORMAT, fp );
	}

	//todo: documentation
	@Override
	public void afterClose( final WebDriver driver )
	{
		final SessionId sessionId = ( ( RemoteWebDriver ) driver ).getSessionId();
		Duration duration = new Duration( this.dateTime, DateTime.now() );  /* measuring between before and after */

		final String fp = DateTimeUtils.getFormattedPeriod( duration );
		logger.debug( "Closed handles on session '{}'  ( duration: {} )", sessionId, fp );
	}

	//todo: documentation
	@Override
	public void beforeClose( final WebDriver driver )
	{
		this.dateTime = new DateTime();
		final String MSG_FORMAT = "Closing handle '{}' on \"{}\", total handles: {}";
		final String handle = driver.getWindowHandle();
		final int handles = driver.getWindowHandles().size();
		final String url = driver.getCurrentUrl();

		logger.debug( MSG_FORMAT, handle, url, handles );
	}

	//todo: documentation
	@Override
	public void beforeQuit( final WebDriver driver )
	{
		this.dateTime = new DateTime();
		final String MSG_FORMAT = "Quitting session '{}'";
		final SessionId sessionId = ( ( RemoteWebDriver ) driver ).getSessionId();

		logger.debug( MSG_FORMAT, sessionId );
	}

	//todo: documentation
	@Override
	public void afterQuit()
	{
		Duration duration = new Duration( this.dateTime, DateTime.now() );  /* measuring between before and after */
		final String fp = DateTimeUtils.getFormattedPeriod( duration );
		logger.debug( "Quitting WebDriver Instance. ( duration: {} )", fp );
	}

	//todo: documentation
	@Override
	public void beforeSetWindowSize( final WebDriver driver, final Dimension targetSize )
	{
		this.dateTime = new DateTime();
		Dimension size = driver.manage().window().getSize();
		final String fromSize = String.format( "%dx%d", size.getWidth(), size.getHeight() );
		final String toSize = String.format( "%dx%d", targetSize.getWidth(), targetSize.getHeight() );
		final String handle = driver.getWindowHandle();
		final String MSG_FORMAT = "Changing window '{}' size, from <{}> to <{}>";

		logger.debug( MSG_FORMAT, handle, fromSize, toSize );
	}

	//todo: documentation
	@Override
	public void afterSetWindowSize( final WebDriver driver )
	{
		Duration duration = new Duration( this.dateTime, DateTime.now() );  /* measuring between before and after */
		final String fp = DateTimeUtils.getFormattedPeriod( duration );
		Dimension size = driver.manage().window().getSize();
		final String finalSize = String.format( "%dx%d", size.getWidth(), size.getHeight() );
		final String handle = driver.getWindowHandle();
		final String MSG_FORMAT = "Window '{}' size changed to <{}>. ( duration: {} )";

		logger.debug( MSG_FORMAT, handle, finalSize, fp );
	}

	//todo: documentation
	@Override
	public void beforeSetWindowPosition( final WebDriver driver, final Point targetLocation )
	{
		this.dateTime = new DateTime();
		Point point = driver.manage().window().getPosition();
		final String fromLocation = String.format( "%d:%d", point.getX(), point.getY() );
		final String toLocation = String.format( "%d:%d", targetLocation.getX(), targetLocation.getY() );
		final String handle = driver.getWindowHandle();
		final String MSG_FORMAT = "Changing window '{}' location, from <{}> to <{}>";

		logger.debug( MSG_FORMAT, handle, fromLocation, toLocation );
	}

	@Override
	public void afterSetWindowPosition( final WebDriver driver )
	{
		Duration duration = new Duration( this.dateTime, DateTime.now() );  /* measuring between before and after */
		final String fp = DateTimeUtils.getFormattedPeriod( duration );
		Point point = driver.manage().window().getPosition();
		final String position = String.format( "%d:%d", point.getX(), point.getY() );
		final String handle = driver.getWindowHandle();
		final String MSG_FORMAT = "Window '{}' position changed to <{}>. ( duration: {} )";

		logger.debug( MSG_FORMAT, handle, position, fp );
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 *     the method first takes a datetime value that indicates that {@code WebDriver.manage().window().maximize()} 
	 *     was started.
	 *     provides log debug information about the window current size and the window
	 *     active handle when the maximize operation will take place.
	 * </p>
	 *
	 * @see org.openqa.selenium.Dimension
	 * @see org.joda.time.DateTime
	 * @see org.openqa.selenium.WebDriver#getWindowHandles()
	 * @see org.openqa.selenium.WebDriver.Window#maximize()
	 */
	@Override
	public void beforeMaximize( final WebDriver driver )
	{
		this.dateTime = new DateTime();
		Dimension size = driver.manage().window().getSize();
		final String fromSize = String.format( "%dx%d", size.getWidth(), size.getHeight() );
		final String handle = driver.getWindowHandle();
		final String MSG_FORMAT = "Maximizing window '{}'. current size is <{}>";

		logger.debug( MSG_FORMAT, handle, fromSize );
	}

	
	/**
	 * {@inheritDoc}
	 * <p>
	 *     The method makes a duration calculation from the corresponding {@code before} method.
	 *     provides log debug information about the new window size and the window active handle that was maximized.
	 * </p>
	 * 
	 * @see org.openqa.selenium.Dimension
	 * @see org.joda.time.Duration
	 * @see com.framework.utils.datetime.DateTimeUtils#getFormattedPeriod(org.joda.time.Duration) 
	 * @see org.openqa.selenium.WebDriver#getWindowHandles() 
	 * @see org.openqa.selenium.WebDriver.Window#maximize() 
	 */
	@Override
	public void afterMaximize( final WebDriver driver )
	{
		Duration duration = new Duration( this.dateTime, DateTime.now() );  /* measuring between before and after */
		final String fp = DateTimeUtils.getFormattedPeriod( duration );
		Dimension size = driver.manage().window().getSize();
		final String fromSize = String.format( "%dX%d", size.getWidth(), size.getHeight() );
		final String handle = driver.getWindowHandle();
		final String MSG_FORMAT = "Window '{}' was maximized. current size is <{}>. ( duration: {} )";

		logger.debug( MSG_FORMAT, handle, fromSize, fp );
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 *     The method first takes a datetime value that indicates that {@code WebDriver.manage().navigate().back()}
	 *     was started.
	 *     provides log debug information about the driver current url, before navigating back and the
	 *     window active handle when the back operation will take place.
	 * </p>
	 *
	 * @see org.openqa.selenium.Dimension
	 * @see org.joda.time.DateTime
	 * @see org.openqa.selenium.WebDriver#getWindowHandles()
	 * @see org.openqa.selenium.WebDriver.Navigation#back()
	 */
	@Override
	public void beforeNavigateBack( final WebDriver driver )
	{
		this.dateTime = new DateTime();
		final String currentUrl = driver.getCurrentUrl();
		final String handle = driver.getWindowHandle();
		final String MSG_FORMAT = "Navigating back from <'{}'> on window '{}'";

		logger.debug( MSG_FORMAT, currentUrl, handle );
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 *     The method makes a duration calculation from the corresponding {@code before} method.
	 *     provides log debug information about the new current url and the active handle.
	 * </p>
	 *
	 * @see org.openqa.selenium.Dimension
	 * @see org.joda.time.Duration
	 * @see com.framework.utils.datetime.DateTimeUtils#getFormattedPeriod(org.joda.time.Duration)
	 * @see org.openqa.selenium.WebDriver#getWindowHandles()
	 * @see org.openqa.selenium.WebDriver.Window#maximize()
	 */
	@Override
	public void afterNavigateBack( final WebDriver driver )
	{
		Duration duration = new Duration( this.dateTime, DateTime.now() );  /* measuring between before and after */
		final String fp = DateTimeUtils.getFormattedPeriod( duration );
		final String currentUrl = driver.getCurrentUrl();
		final String MSG_FORMAT = "Navigated back to <'{}'>. ( duration: {} )";

		logger.debug( MSG_FORMAT, currentUrl, fp );
	}

	//todo: documentation
	@Override
	public void beforeDeleteAllCookies( final WebDriver driver )
	{
		this.dateTime = new DateTime();
		int cookies = driver.manage().getCookies().size();
		final SessionId sessionId = ( ( RemoteWebDriver ) driver ).getSessionId();
		final String MSG_FORMAT = "Deleting all cookies on session '{}'. number of cookies <{}>";

		logger.debug( MSG_FORMAT, sessionId, cookies );
	}

	//todo: documentation
	@Override
	public void afterDeleteAllCookies( final WebDriver driver )
	{
		Duration duration = new Duration( this.dateTime, DateTime.now() );  /* measuring between before and after */
		final String fp = DateTimeUtils.getFormattedPeriod( duration );
		final SessionId sessionId = ( ( RemoteWebDriver ) driver ).getSessionId();
		final String MSG_FORMAT = "Removed all cookies from session '{}'. ( duration: {} )";

		logger.debug( MSG_FORMAT, sessionId, fp );
	}

	//todo: documentation
	@Override
	public void beforeScript( final String script, final WebDriver driver )
	{
		this.dateTime = new DateTime();
		final SessionId sessionId = ( ( RemoteWebDriver ) driver ).getSessionId();
		String javascript =  StringUtils.abbreviate( script, 40 );
		final String MSG_FORMAT = "executing javascript on session '{}' -> <'{}'>";

		logger.debug( MSG_FORMAT, sessionId, javascript );
	}

	//todo: documentation
	@Override
	public void afterScript( final WebDriver driver )
	{
		Duration duration = new Duration( this.dateTime, DateTime.now() );  /* measuring between before and after */
		final String fp = DateTimeUtils.getFormattedPeriod( duration );
		final SessionId sessionId = ( ( RemoteWebDriver ) driver ).getSessionId();
		final String MSG_FORMAT = "Finish executing javascript. ( duration: {} )";

		logger.debug( MSG_FORMAT, sessionId, fp );
	}

	//todo: documentation
	@Override
	public void onException( final Throwable throwable, final WebDriver driver )
	{
		final String cu = driver.getCurrentUrl();
		String causeMessage = StringUtils.split( throwable.getCause().getMessage(), "\n" )[0];

		( ( WebDriverException ) throwable ).addInfo( "Screenshot file", "ffffrkforofoowofoworfoirofowrof" );
		logger.error( "exception was thrown on \"{}\"\ncausing message -> {}", cu, causeMessage );  //todo: implement screenshot

		//Screenshot screenshot = new Screenshot( driver, "/Users/solmarkn/IdeaProjects/WebDriverTestNg/Screenshots" );
		//String outputFile = screenshot.takeScreenshot();
		//logger.error( "screenshot file -> {}", outputFile );
	}
}
