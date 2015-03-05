package com.framework.driver.event;

import com.framework.asserts.JAssertion;
import com.framework.config.Configurations;
import com.framework.config.FrameworkProperty;
import com.framework.utils.error.PreConditions;
import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.hamcrest.Matcher;
import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.interactions.HasInputDevices;
import org.openqa.selenium.interactions.Keyboard;
import org.openqa.selenium.logging.Logs;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.remote.SessionId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.*;
import java.util.concurrent.TimeUnit;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.delegate.event
 *
 * Name   : AbstractEventWebDriver 
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-02-04 
 *
 * Time   : 19:46
 *
 */

public class HtmlWebDriver extends WebDriverEventSource implements HtmlDriver
{

	//region EventWebDriverImpl - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( HtmlWebDriver.class );

	private WebDriver delegate;

	//endregion


	//region EventWebDriverImpl - Constructor Methods Section

	public HtmlWebDriver( final WebDriver delegate )
	{
		PreConditions.checkNotNull( delegate, "WebDriver argument cannot be null" );
		this.delegate = delegate;
		WebDriverListenerAdapter.setLogger( logger );
	}

	//endregion


	//region EventWebDriverImpl - WrapsDriver Implementation Methods Section

	@Override
	public final WebDriver getWrappedDriver()
	{
		return delegate;
	}

	//endregion


	//region EventWebDriverImpl - JavascriptExecutor Implementation Methods Section

	@Override
	public Object executeScript( String script, Object... args )
	{
		boolean isDelegateSupported = javascript().isJavaScriptSupported( delegate.getClass() );

		try
		{
			if ( isDelegateSupported )
			{
				fireEvent( EVENT_JAVASCRIPT, delegate, true, script );
				Object[] usedArgs = unpackWrappedArgs( args );
				Object result = ( ( JavascriptExecutor ) delegate ).executeScript( script, usedArgs );
				fireEvent( EVENT_JAVASCRIPT, delegate, false );
				return result;
			}

			throw new UnsupportedOperationException( "Underlying delegate instance does not support executing javascript" );
		}
		catch ( Exception e )
		{
			fireError( EVENT_JAVASCRIPT, getWrappedDriver(), e );
			throw new WebDriverException( e );
		}
	}

	@Override
	public Object executeAsyncScript( String script, Object... args )
	{
		boolean isDelegateSupported = javascript().isJavaScriptSupported( delegate.getClass() );

		try
		{
			if ( isDelegateSupported )
			{
				fireEvent( EVENT_ASYNC_JAVASCRIPT, delegate, true, script );
				Object[] usedArgs = unpackWrappedArgs( args );
				Object result = ( ( JavascriptExecutor ) delegate ).executeAsyncScript( script, usedArgs );
				fireEvent( EVENT_ASYNC_JAVASCRIPT, delegate, false );
				return result;
			}
			throw new UnsupportedOperationException( "Underlying driver instance does not support executing javascript" );
		}
		catch ( UnsupportedOperationException e )
		{
			fireError( EVENT_ASYNC_JAVASCRIPT, getWrappedDriver(), e );
			throw new WebDriverException( e );
		}
	}

	//endregion


	//region EventWebDriverImpl - WebDriver Classic Implementation Methods Section

	@Override
	public Navigation navigate()
	{
		return new EventNavigation( getWrappedDriver().navigate() );
	}

	@Override
	public Options manage()
	{
		return new EventOptions( getWrappedDriver().manage() );
	}

	@Override
	public void get( final String url )
	{
		try
		{
			fireEvent( EVENT_NAVIGATE_TO, getWrappedDriver(), true, url );
			getWrappedDriver().get( url );
			fireEvent( EVENT_NAVIGATE_TO, getWrappedDriver(), false );
		}
		catch ( Exception e )
		{
			fireError( EVENT_NAVIGATE_TO, getWrappedDriver(), e );
			throw new WebDriverException( e );
		}
	}

	@Override
	public String getCurrentUrl()
	{
		try
		{
			return getWrappedDriver().getCurrentUrl();
		}
		catch ( Exception e )
		{
			fireError( EVENT_GET_CURRENT_URL, getWrappedDriver(), e );
			throw new WebDriverException( e );
		}
	}

	@Override
	public String getTitle()
	{
		try
		{
			return getWrappedDriver().getTitle();
		}
		catch ( Exception e )
		{
			fireError( EVENT_GET_TITLE, getWrappedDriver(), e );
			throw new WebDriverException( e );
		}
	}

	@Override
	public List<HtmlElement> findElements( final By by )
	{
		try
		{
			fireEvent( EVENT_DRIVER_FIND_ELEMENTS, getWrappedDriver(), true, by );
			List<WebElement> elements = getWrappedDriver().findElements( by );
			fireEvent( EVENT_DRIVER_FIND_ELEMENTS, getWrappedDriver(), true, by, elements.size() );
			List<HtmlElement> htmlElements = Lists.newArrayListWithExpectedSize( elements.size() );
			for ( WebElement e : elements )
			{
				htmlElements.add( createHtmlElement( e, delegate, this, by ) );
			}
			return htmlElements;
		}
		catch ( Exception e )
		{
			fireError( EVENT_DRIVER_FIND_ELEMENT, getWrappedDriver(), e );
			throw new WebDriverException( e );
		}
	}

	@Override
	public Optional<HtmlElement> elementExists( By by )
	{
		try
		{
			manage().timeouts().implicitlyWait( 0 );
			WebDriverEventSource.setIgnoreOnError( true );
			return Optional.of( findElement( by ) );
		}
		catch ( NoSuchElementException e )
		{
			return Optional.absent();
		}
		finally
		{
			WebDriverEventSource.setIgnoreOnError( false );
			manage().timeouts().implicitlyWait( Configurations.getInstance().defaultImplicitlyWait() );
		}
	}

	@Override
	public Optional<List<HtmlElement>> allElementExists( final By by )
	{
		try
		{
			manage().timeouts().implicitlyWait( 0 );
			List<HtmlElement> list = findElements( by );
			if( list.size() == 0 ) return Optional.absent();
			return Optional.of( list );
		}
		catch ( NoSuchElementException e )
		{
			return Optional.absent();
		}
		finally
		{
			manage().timeouts().implicitlyWait( Configurations.getInstance().defaultImplicitlyWait() );
		}
	}

	@Override
	public HtmlElement findElement( final By by )
	{
		try
		{
			fireEvent( EVENT_DRIVER_FIND_ELEMENT, getWrappedDriver(), true, by );
			WebElement e = getWrappedDriver().findElement( by );
			fireEvent( EVENT_DRIVER_FIND_ELEMENT, getWrappedDriver(), false, by );
			return createHtmlElement( e, delegate, this, by );
		}
		catch ( NoSuchElementException | StaleElementReferenceException e )
		{
			fireError( EVENT_DRIVER_FIND_ELEMENT, getWrappedDriver(), e );
			throw e;
		}
	}

	@Override
	public String getPageSource()
	{
		try
		{
			return getWrappedDriver().getPageSource();
		}
		catch ( Exception e )
		{
			fireError( EVENT_GET_PAGE_SOURCE, getWrappedDriver(), e );
			throw new WebDriverException( e );
		}
	}

	@Override
	public void close()
	{
		try
		{
			fireEvent( EVENT_CLOSE, getWrappedDriver(), true );
			delegate.close();
			fireEvent( EVENT_CLOSE, getWrappedDriver(), false );
		}
		catch ( Exception e )
		{
			fireError( EVENT_CLOSE, getWrappedDriver(), e );
			throw new WebDriverException( e );
		}
	}

	@Override
	public void quit()
	{
		try
		{
			fireEvent( EVENT_QUIT, getWrappedDriver(), true );
			delegate.quit();
			fireEvent( EVENT_QUIT, getWrappedDriver(), false );
		}
		catch ( Exception e )
		{
			fireError( EVENT_QUIT, getWrappedDriver(), e );
			throw new WebDriverException( e );
		}
	}

	@Override
	public Set<String> getWindowHandles()
	{
		try
		{
			return getWrappedDriver().getWindowHandles();
		}
		catch ( Exception e )
		{
			fireError( EVENT_GET_WINDOW_HANDLE_OR_HANDLES, getWrappedDriver(), e );
			throw new WebDriverException( e );
		}
	}

	@Override
	public String getWindowHandle()
	{
		try
		{
			return getWrappedDriver().getWindowHandle();
		}
		catch ( Exception e )
		{
			fireError( EVENT_GET_WINDOW_HANDLE_OR_HANDLES, getWrappedDriver(), e );
			throw new WebDriverException( e );
		}
	}

	@Override
	public TargetLocator switchTo()
	{
		return new EventTargetLocator( delegate.switchTo() );
	}


	//endregion


	//region EventWebDriverImpl - TakesScreenshot Implementation Methods Section

	@Override
	public <X> X getScreenshotAs( OutputType<X> target ) throws WebDriverException
	{
		if( driverCanTakeScreenshots() )
		{
			try
			{
				return ( ( TakesScreenshot ) delegate ).getScreenshotAs( target );
			}
			catch( WebDriverException wdEx )
			{
				logger.warn("Failed to take screenshot - driver closed already? (" + wdEx.getMessage() + ")");
			}
			catch( OutOfMemoryError oomErr )
			{
				logger.error( "Failed to take screenshot - out of memory", oomErr );
			}
		}

		throw new UnsupportedOperationException( "Underlying driver instance does not support taking screenshots" );
	}

	//endregion


	//region EventWebDriverImpl - HasCapabilities Implementation Methods Section

	@Override
	public Capabilities getCapabilities()
	{
		return ( ( RemoteWebDriver ) delegate ).getCapabilities();
	}

	//endregion


	//region EventWebDriverImpl - HasInputDevices Implementation Methods Section

	@Override
	public Keyboard getKeyboard()
	{
		if ( delegate instanceof HasInputDevices )
		{
			return new EventKeyboard( this, getWebDriverListener(), getWebDriverErrorListener() );
		}
		else
		{
			throw new UnsupportedOperationException( "Underlying driver does not implement advanced" + " user interactions yet." );
		}
	}

	@Override
	public EventMouse getMouse()
	{
		if ( delegate instanceof HasInputDevices )
		{
			return new EventMouse( this, getWebDriverListener(), getWebDriverErrorListener() );
		}
		else
		{
			throw new UnsupportedOperationException( "Underlying driver does not implement advanced" + " user interactions yet." );
		}
	}

	//endregion


	//region EventWebDriverImpl - Service Methods Section

	protected HtmlElement createHtmlElement( WebElement e, SearchContext context, HtmlDriver parent, By locator )
	{
		HtmlObject htmlObject = new HtmlObject( e );
		if( null == parent )
		{
			htmlObject.setParent( this );
		}
		else
		{
			htmlObject.setParent( parent );
		}
		htmlObject.setFoundBy( locator );
		htmlObject.setId( ( ( RemoteWebElement ) e ).getId() );
		logger.debug( "Created a new HtmlElement -> {}", htmlObject.toString() );
		return htmlObject;
	}

	private Object[] unpackWrappedArgs( Object... args )
	{
		// Walk the args: the various drivers expect unpacked versions of the elements
		Object[] usedArgs = new Object[ args.length ];
		for ( int i = 0; i < args.length; i++ )
		{
			usedArgs[ i ] = unpackWrappedElement( args[ i ] );
		}
		return usedArgs;
	}

	private Object unpackWrappedElement( Object arg )
	{
		if ( arg instanceof List<?> )
		{
			List<?> aList = ( List<?> ) arg;
			List<Object> toReturn = new ArrayList<Object>();
			for ( Object anAList : aList )
			{
				toReturn.add( unpackWrappedElement( anAList ) );
			}
			return toReturn;
		}
		else if ( arg instanceof Map<?, ?> )
		{
			Map<?, ?> aMap = ( Map<?, ?> ) arg;
			Map<Object, Object> toReturn = new HashMap<Object, Object>();
			for ( Object key : aMap.keySet() )
			{
				toReturn.put( key, unpackWrappedElement( aMap.get( key ) ) );
			}
			return toReturn;
		}

		return arg;
	}

	private boolean driverCanTakeScreenshots()
	{
		return ( TakesScreenshot.class.isAssignableFrom( delegate.getClass() ) );
	}

	@Override
	public SessionId getSessionId()
	{
		return ( ( RemoteWebDriver ) delegate ).getSessionId();
	}

	@Override
	public <T> void assertThat( final String reason, final T actual, final Matcher<? super T> matcher )
	{
		new JAssertion( this ).assertThat( reason, actual, matcher );
	}

	@Override
	public <T> void assertWaitThat( final String reason, final long timeout, final HtmlCondition<?> condition )
	{
		new JAssertion( this ).assertWaitThat( reason, timeout, condition );
	}

	//endregion


	//region EventWebDriverImpl - EventWebDriver Implementations Methods Section

	@Override
	public SessionStorage sessionStorage()
	{
		return new EventSessionStorage();
	}

	@Override
	public JavaScriptSupport javascript()
	{
		return new EventJavaScript();
	}

	//endregion


	//region EventWebDriverImpl - EventOptions Inner Class Implementation Section

	private class EventOptions implements Options
	{
		private WebDriver.Options options;

		private EventOptions( WebDriver.Options options )
		{
			this.options = options;
		}

		public Logs logs()
		{
			try
			{
				return options.logs();
			}
			catch ( Exception e )
			{
				fireError( EVENT_GET_LOGS, delegate, e );
				throw new WebDriverException( e );
			}
		}

		/**
		 * Add a specific cookie. If the cookie's domain name is left blank, it is assumed that the
		 * cookie is meant for the domain of the current document.
		 *
		 * @param cookie The cookie to add.
		 */
		public void addCookie( Cookie cookie )
		{
			try
			{
				String cookieName = cookie.getName();
				fireEvent( EVENT_ADD_COOKIE, delegate, true, cookie );
				options.addCookie( cookie );
				fireEvent( EVENT_ADD_COOKIE, delegate, false, cookieName );
			}
			catch ( Exception e )
			{
				fireError( EVENT_ADD_COOKIE, delegate, e );
				throw new WebDriverException( e );
			}
		}

		/**
		 * Delete the named cookie from the current domain. This is equivalent to setting the named
		 * cookie's expiry date to some time in the past.
		 *
		 * @param name The name of the cookie to delete
		 */
		public void deleteCookieNamed( String name )
		{
			try
			{
				fireEvent( EVENT_DELETE_COOKIE_NAME, delegate, true, name );
				options.deleteCookieNamed( name );
				fireEvent( EVENT_DELETE_COOKIE_NAME, delegate, false, name );
			}
			catch ( Exception e )
			{
				fireError( EVENT_DELETE_COOKIE_NAME, delegate, e );
				throw new WebDriverException( e );
			}
		}

		/**
		 * Delete a cookie from the browser's "cookie jar". The domain of the cookie will be ignored.
		 *
		 * @param cookie a {@linkplain org.openqa.selenium.Cookie} instance.
		 */
		public void deleteCookie( Cookie cookie )
		{
			try
			{
				String name = cookie.getName();
				fireEvent( EVENT_DELETE_COOKIE, delegate, true, cookie );
				options.deleteCookie( cookie );
				fireEvent( EVENT_DELETE_COOKIE, delegate, false, name );
			}
			catch ( Exception e )
			{
				fireError( EVENT_DELETE_COOKIE, delegate, e );
				throw new WebDriverException( e );
			}
		}

		/**
		 * Delete all the cookies for the current domain.
		 */
		public void deleteAllCookies()
		{
			try
			{
				fireEvent( EVENT_DELETE_ALL_COOKIES, delegate, true );
				options.deleteAllCookies();
				fireEvent( EVENT_DELETE_ALL_COOKIES, delegate, false );
			}
			catch ( Exception e )
			{
				fireError( EVENT_DELETE_ALL_COOKIES, delegate, e );
				throw new WebDriverException( e );
			}
		}

		/**
		 * Get all the cookies for the current domain. This is the equivalent of calling "document.cookie" and parsing the result
		 *
		 * @return A Set of cookies for the current domain.
		 */
		public Set<Cookie> getCookies()
		{
			try
			{
				return options.getCookies();
			}
			catch ( Exception e )
			{
				fireError( EVENT_GET_COOKIES, delegate, e );
				throw new WebDriverException( e );
			}
		}

		/**
		 * Get a cookie with a given name.
		 *
		 * @param name the name of the cookie
		 *
		 * @return the cookie, or null if no cookie with the given name is present
		 */
		public Cookie getCookieNamed( String name )
		{
			try
			{
				fireEvent( EVENT_GET_COOKIE, delegate, true, name );
				return options.getCookieNamed( name );
			}
			catch ( Exception e )
			{
				fireError( EVENT_GET_COOKIE, delegate, e );
				throw new WebDriverException( e );
			}
		}

		/**
		 * Returns the interface for managing delegate timeouts.
		 */
		public EventTimeouts timeouts()
		{
			return new EventTimeouts( options.timeouts() );
		}

		/**
		 * Returns the interface for managing the current window.
		 */
		public Window window()
		{
			return new EventWindow( options.window() );
		}
	}

	//endregion


	//region EventWebDriverImpl - EventNavigation Inner Class Implementation Section

	private class EventNavigation implements Navigation
	{
		private final WebDriver.Navigation navigation;

		EventNavigation( WebDriver.Navigation navigation )
		{
			this.navigation = navigation;
		}

		/**
		 * Load a new web page in the current browser window. This is done using an HTTP GET operation,
		 * and the method will block until the load is complete. This will follow redirects issued
		 * either by the server or as a meta-redirect from within the returned HTML. Should a
		 * meta-redirect "rest" for any duration of time, it is best to wait until this timeout is over,
		 * since should the underlying page change whilst your test is executing the results of future
		 * calls against this interface will be against the freshly loaded page.
		 *
		 * @param url The URL to load. It is best to use a fully qualified URL
		 */
		public void to( String url )
		{
			try
			{
				fireEvent( EVENT_NAVIGATE_TO, delegate, true, url );
				navigation.to( url );
				fireEvent( EVENT_NAVIGATE_TO, delegate, false );
			}
			catch ( Exception e )
			{
				fireError( EVENT_NAVIGATE_TO, delegate, e );
				throw new WebDriverException( e );
			}
		}

		/**
		 * Overloaded version of {@link #to(String)} that makes it easy to pass in a URL.
		 *
		 * @param url a valid {@linkplain java.net.URL}
		 */
		public void to( URL url )
		{
			to( String.valueOf( url ) );
		}

		/**
		 * Move back a single "item" in the browser's history.
		 */
		public void back()
		{
			try
			{
				fireEvent( EVENT_NAVIGATE_BACK, delegate, true );
				navigation.back();
				fireEvent( EVENT_NAVIGATE_BACK, delegate, false );
			}
			catch ( Exception e )
			{
				fireError( EVENT_NAVIGATE_BACK, delegate, e );
				throw new WebDriverException( e );
			}
		}

		/**
		 * Move a single "item" forward in the browser's history. Does nothing if we are on the latest page viewed.
		 */
		public void forward()
		{
			try
			{
				fireEvent( EVENT_NAVIGATE_FORWARD, delegate, true );
				navigation.forward();
				fireEvent( EVENT_NAVIGATE_FORWARD, delegate, false );
			}
			catch ( Exception e )
			{
				fireError( EVENT_NAVIGATE_FORWARD, delegate, e );
				throw new WebDriverException( e );
			}
		}

		/**
		 * Refresh the current page
		 */
		public void refresh()
		{
			try
			{
				fireEvent( EVENT_NAVIGATE_REFRESH, delegate, true );
				navigation.refresh();
				fireEvent( EVENT_NAVIGATE_REFRESH, delegate, false );
			}
			catch ( Exception e )
			{
				fireError( EVENT_NAVIGATE_REFRESH, delegate, e );
				throw new WebDriverException( e );
			}
		}

		@Override
		public History history()
		{
			return new EventHistory( ( JavascriptExecutor ) delegate );
		}
	}

	//endregion


	//region EventWebDriverImpl - EventHistory Inner Class Implementation Section

	private class EventHistory implements History
	{
		JavascriptExecutor js;

		private EventHistory( final JavascriptExecutor js )
		{
			this.js = js;
		}

		@Override
		public void back()
		{
			js.executeScript( "window.history.back();" );
		}

		@Override
		public void forward()
		{
			js.executeScript( "window.history.forward();" );
		}

		@Override
		public int length()
		{
			Number number = ( Number ) js.executeScript( "return window.history.length" );
			return number.intValue();
		}

		/**
		 * Moving to a specific point in history
		 * <p>history.go(-1) is the equivalent of calling back()</p>
		 * <p>history.go(1) is the equivalent of calling forward()</p>
		 *
		 * @param index determine the number of pages in the history stack by looking at the value of the length property:
		 *
		 * @see #length()
		 */
		@Override
		public void go( final int index )
		{
			js.executeScript( "window.history.go( arguments[0] );", index );
		}
	}

	//endregion


	//region EventWebDriverImpl - EventWindow Inner Class Implementation Section

	private class EventWindow implements Window
	{

		private final WebDriver.Window window;

		EventWindow( WebDriver.Window window )
		{
			this.window = window;
		}


		public void setSize( Dimension targetSize )
		{
			try
			{
				fireEvent( EVENT_WINDOW_SIZE, getWrappedDriver(), true, targetSize );
				window.setSize( targetSize );
				fireEvent( EVENT_WINDOW_SIZE, getWrappedDriver(), false );
			}
			catch ( Exception e )
			{
				fireError( EVENT_WINDOW_SIZE, getWrappedDriver(), e );
				throw new WebDriverException( e );
			}
		}

		/**
		 * {@inheritDoc}
		 */
		public void setPosition( Point targetLocation )
		{
			try
			{
				fireEvent( EVENT_WINDOW_POSITION, getWrappedDriver(), true, targetLocation );
				window.setPosition( targetLocation );
				fireEvent( EVENT_WINDOW_POSITION, getWrappedDriver(), false );
			}
			catch ( Exception e )
			{
				fireError( EVENT_WINDOW_POSITION, getWrappedDriver(), e );
				throw new WebDriverException( e );
			}
		}

		/**
		 * {@inheritDoc}
		 */
		public Dimension getSize()
		{
			try
			{
				return window.getSize();
			}
			catch ( Exception e )
			{
				fireError( EVENT_WINDOW_SIZE, getWrappedDriver(), e );
				throw new WebDriverException( e );
			}
		}

		/**
		 * {@inheritDoc}
		 */
		public Point getPosition()
		{
			try
			{
				return window.getPosition();
			}
			catch ( Exception e )
			{
				fireError( EVENT_WINDOW_POSITION, getWrappedDriver(), e );
				throw new WebDriverException( e );
			}
		}

		/**
		 * {@inheritDoc }
		 */
		public void maximize()
		{
			try
			{
				fireEvent( EVENT_WINDOW_MAXIMIZE, getWrappedDriver(), true );
				window.maximize();
				fireEvent( EVENT_WINDOW_MAXIMIZE, getWrappedDriver(), false );
			}
			catch ( Exception e )
			{
				fireError( EVENT_WINDOW_MAXIMIZE, getWrappedDriver(), e );
				throw new WebDriverException( e );
			}
		}
	}

	//endregion


	//region EventWebDriverImpl - EventTimeouts Inner Class Implementation Section

	/**
	 * An interface implementation for managing timeout behavior for WebDriver instances.
	 */
	private class EventTimeouts implements Timeouts
	{

		private final WebDriver.Timeouts timeouts;

		EventTimeouts( WebDriver.Timeouts timeouts )
		{
			this.timeouts = timeouts;
		}

		/**
		 * Specifies the amount of time the delegate should wait when searching for an element if it is
		 * not immediately present.
		 * <p/>
		 * When searching for a single element, the delegate should poll the page until the element has
		 * been found, or this timeout expires before throwing a {@link org.openqa.selenium.NoSuchElementException}. When
		 * searching for multiple elements, the delegate should poll the page until at least one element
		 * has been found or this timeout has expired.
		 * <p/>
		 * Increasing the implicit wait timeout should be used judiciously as it will have an adverse
		 * effect on test run time, especially when used with slower location strategies like XPath.
		 *
		 * @param millis The amount of time to wait.
		 *
		 * @return A self reference.
		 */
		//@Override
		public EventTimeouts implicitlyWait( long millis )
		{
			try
			{
				fireEvent( EVENT_IMPLICITLY_WAIT, getWrappedDriver(), true, millis );
				timeouts.implicitlyWait( millis, TimeUnit.MILLISECONDS );
				fireEvent( EVENT_IMPLICITLY_WAIT, getWrappedDriver(), false );
				return this;
			}
			catch ( Exception e )
			{
				fireError( EVENT_IMPLICITLY_WAIT, getWrappedDriver(), e );
				throw new WebDriverException( e );
			}
		}

		/**
		 * Sets the amount of time to wait for an asynchronous script to finish execution before
		 * throwing an error. If the timeout is negative, then the script will be allowed to run
		 * indefinitely.
		 *
		 * @param millis The timeout value.
		 *
		 * @return A self reference.
		 *
		 * @see org.openqa.selenium.JavascriptExecutor#executeAsyncScript(String, Object...)
		 */
		//@Override
		public EventTimeouts setScriptTimeout( long millis )
		{
			try
			{
				fireEvent( EVENT_SCRIPT_TIMEOUT, getWrappedDriver(), true, millis );
				timeouts.setScriptTimeout( millis, TimeUnit.MILLISECONDS );
				fireEvent( EVENT_SCRIPT_TIMEOUT, getWrappedDriver(), false );
				return this;
			}
			catch ( Exception e )
			{
				fireError( EVENT_SCRIPT_TIMEOUT, getWrappedDriver(), e );
				throw new WebDriverException( e );
			}
		}

		/**
		 * Sets the amount of time to wait for a page load to complete before throwing an error.
		 * If the timeout is negative, page loads can be indefinite.
		 *
		 * @param millis The timeout value.
		 *
		 * @return A Timeouts interface.
		 */
		//@Override
		public EventTimeouts pageLoadTimeout( long millis )
		{
			try
			{
				fireEvent( EVENT_PAGE_LOAD_TIMEOUT, getWrappedDriver(), true, millis );
				timeouts.pageLoadTimeout( millis, TimeUnit.MILLISECONDS );
				fireEvent( EVENT_PAGE_LOAD_TIMEOUT, getWrappedDriver(), false );
				return this;
			}
			catch ( Exception e )
			{
				fireError( EVENT_PAGE_LOAD_TIMEOUT, getWrappedDriver(), e );
				throw new WebDriverException( e );
			}
		}
	}

	//endregion


	//region EventWebDriverImpl - EventSessionStorage Inner Class Implementation Section

	private class EventSessionStorage implements SessionStorage
	{

		@Override
		public void removeItemFromSessionStorage( String item )
		{
			executeScript( String.format( "window.sessionStorage.removeItem('%s');", item ) );
		}

		@Override
		public boolean isItemPresentInSessionStorage( String item )
		{
			return executeScript( String.format( "return window.sessionStorage.getItem('%s');", item ) ) != null;
		}

		@Override
		public String getItemFromSessionStorage( String key )
		{
			return ( String ) executeScript( String.format( "return window.sessionStorage.getItem('%s');", key ) );
		}

		@Override
		public String getKeyFromSessionStorage( int key )
		{
			return ( String ) executeScript( String.format( "return window.sessionStorage.key('%s');", key ) );
		}

		@Override
		public Long getSessionStorageLength()
		{
			return ( Long ) executeScript( "return window.sessionStorage.length;" );
		}

		@Override
		public void setItemInSessionStorage( String item, String value )
		{
			executeScript( String.format( "window.sessionStorage.setItem('%s','%s');", item, value ) );
		}

		@Override
		public void clearSessionStorage()
		{
			executeScript( String.format( "window.sessionStorage.clear();" ) );
		}

		@Override
		public Set<String> getSessionStorageKeys()
		{
			Set<String> keys = Sets.newHashSet();
			long keyCount = getSessionStorageLength();
			for( int i = 0; i < keyCount; i ++ )
			{
				String key = getKeyFromSessionStorage( i );
				keys.add( key );
			}

			return keys;
		}
	}

	//endregion


	//region EventWebDriverImpl - EventJavaScript Inner Class Implementation Section

	private class EventJavaScript implements JavaScriptSupport
	{
		private EventJavaScript()
		{
			super();
		}

		@Override
		public String getString( String script, Object... args )
		{
			try
			{
				Object o = executeScript( script, args );
				return o instanceof String ? ( String ) o : null;
			}
			catch ( Exception e )
			{
				logger.error( e.getMessage() );
				return StringUtils.EMPTY;
			}
		}

		@Override
		public String[] getStringArray( String script, Object... args )
		{
			try
			{
				Object o = executeScript( script, args );
				return o instanceof String[] ? ( String[] ) o : null;
			}
			catch ( Exception e )
			{
				logger.error( e.getMessage() );
				return new String[]{};
			}
		}

		@Override
		public Optional<Number> getNumber( String script, Object... args )
		{
			try
			{
				Object o = executeScript( script, args );
				if( o instanceof Number ) return Optional.of( ( Number ) o );
				return Optional.absent();
			}
			catch ( Exception e )
			{
				logger.error( e.getMessage() );
				return Optional.absent();
			}
		}

		@Override
		public Optional<Boolean> getBoolean( String script, Object... args )
		{
			try
			{
				Object o = executeScript( script, args );
				if( o instanceof Boolean )  return Optional.of( ( Boolean ) o );
				return Optional.absent();
			}
			catch ( Exception e )
			{
				logger.error( e.getMessage() );
				return Optional.absent();
			}
		}

		@SuppressWarnings ( "unchecked" )
		@Override
		public Map<String, String> getMap( final String script, final Object... args )
		{
			Object o = executeScript( script, args );
			return o instanceof Map ? ( Map<String,String> ) o : null;
		}

		@Override
		public HtmlElement getHtmlElement( String script, Object... args )
		{
			try
			{
				Object o = executeScript( script, args );
				if( null == o )
				{
					throw new NoSuchElementException( "script: < '" + script + "' > did not return any result" );
				}
				return o instanceof WebElement
						? createHtmlElement( ( WebElement ) o, delegate, null, null )
						: null;

			}
			catch ( Exception e )
			{
				logger.error( e.getMessage() );
				return null;
			}
		}

		@Override
		public List<HtmlElement> getHtmlElements( String script, Object... args )
		{
			List<HtmlElement> elementList = Lists.newArrayList();

			Object we = executeScript( script, args );
			if( null == we )
			{
				return Lists.newArrayList(  );
			}

			Validate.isInstanceOf( List.class, we, "The returned object is instance of List" );
			List list = ( List ) we;
			if( list.size() > 0 )
			{
				for( Object o : list )
				{
					elementList.add( createHtmlElement( ( WebElement ) o, delegate, null, By.xpath( "" ) ) );
				}
			}

			return elementList;
		}

		@Override
		public Boolean isJavaScriptSupported( Class<? extends WebDriver> driverClass )
		{
			return JavascriptExecutor.class.isAssignableFrom( driverClass );
		}

		@Override
		public Boolean isJQueryEnabled()
		{
			Optional<Boolean> loaded;
			try
			{
				loaded = getBoolean( "return ( typeof jQuery != 'undefined' )" );
				if( loaded.isPresent() )
				{
					return ( ( loaded.get() != null ) && ( loaded.get() ) );
				}
			}
			catch ( WebDriverException e )
			{
				return false;
			}

			return false;
		}

		public void injectJQuery()
		{
			if( ! isJQueryEnabled(  ) )
			{
				String jQueryVersion = FrameworkProperty.JQUERY_VERSION.from( Configurations.getInstance(), "1.7.2" );
				logger.info( "Injecting JQuery version < '{}' > to page ...", jQueryVersion );
				StringBuilder stringBuilder = new StringBuilder( " var headID = document.getElementsByTagName(\"head\")[0];" )
						.append( "var newScript = document.createElement( 'script' );" )
						.append( "newScript.type = 'text/javascript';" )
						.append( "newScript.src = 'http://ajax.googleapis.com/ajax/libs/jquery/" )
						.append( jQueryVersion )
						.append( "/jquery.min.js';" )
						.append( "headID.appendChild( newScript );" );
				executeScript( stringBuilder.toString() );
			}
			else
			{
				String version = getString( "return jQuery.fn.jquery" );
				logger.info( "Page already uses Jquery version < '{}'> ", version );
			}

		}
	}


	//endregion


	//region EventWebDriverImpl - EventTargetLocator Inner Classes Implementation Section

	private class EventTargetLocator implements TargetLocator
	{

		private WebDriver.TargetLocator targetLocator;

		private EventTargetLocator( WebDriver.TargetLocator targetLocator )
		{
			this.targetLocator = targetLocator;
		}

		//@Override
		public boolean frame( int frameIndex )
		{
			try
			{
				fireEvent( EVENT_FRAME_TARGET_LOCATOR, delegate, true, frameIndex );
				delegate = targetLocator.frame( frameIndex );
				fireEvent( EVENT_FRAME_TARGET_LOCATOR, delegate, false );
				return true;
			}
			catch ( Exception e )
			{
				fireError( EVENT_FRAME_TARGET_LOCATOR, delegate, e );
				throw new WebDriverException( e );
			}
		}

		@Override
		public boolean frame( String nameOrId )
		{
			try
			{
				fireEvent( EVENT_FRAME_TARGET_LOCATOR, delegate, true, nameOrId );
				delegate = targetLocator.frame( nameOrId );
				fireEvent( EVENT_FRAME_TARGET_LOCATOR, delegate, false, nameOrId );
				return true;
			}
			catch ( Exception e )
			{
				fireError( EVENT_FRAME_TARGET_LOCATOR, delegate, e );
				throw new WebDriverException( e );
			}
		}

		@Override
		public boolean frame( HtmlElement frameElement )
		{
			try
			{
				fireEvent( EVENT_FRAME_TARGET_LOCATOR, delegate, true, frameElement );
				delegate = targetLocator.frame( frameElement.getWrappedElement() );
				fireEvent( EVENT_FRAME_TARGET_LOCATOR, delegate, false );
				return true;
			}
			catch ( Exception e )
			{
				fireError( EVENT_FRAME_TARGET_LOCATOR, delegate, e );
				throw new WebDriverException( e );
			}
		}

		/**
		 * Change focus to the parent context. If the current context is the top level browsing context,
		 * the context remains unchanged.
		 *
		 * @return This driver focused on the parent frame
		 *
		 * @see org.openqa.selenium.WebDriver.TargetLocator#parentFrame()
		 */
		@Override
		public void parentFrame()
		{
			try
			{
				fireEvent( EVENT_PARENT_FRAME_TARGET_LOCATOR, delegate, true );
				delegate = targetLocator.parentFrame();
				fireEvent( EVENT_PARENT_FRAME_TARGET_LOCATOR, delegate, false );
			}
			catch ( Exception e )
			{
				fireError( EVENT_PARENT_FRAME_TARGET_LOCATOR, delegate, e );
				throw new WebDriverException( e );
			}
		}

		/**
		 * Switch the focus of future commands for this driver to the window with the given name/handle.
		 *
		 * @param nameOrHandle The name of the window or the handle as returned by
		 *                     {@link org.openqa.selenium.WebDriver#getWindowHandle()}
		 *
		 * @return This driver focused on the given window
		 *
		 * @throws org.openqa.selenium.NoSuchWindowException If the window cannot be found
		 *
		 * @see org.openqa.selenium.WebDriver.TargetLocator#window(String)
		 */
		@Override
		public void window( String nameOrHandle )
		{
			try
			{
				fireEvent( EVENT_WINDOW_TARGET_LOCATOR, delegate, true );
				delegate = targetLocator.window( nameOrHandle );
				fireEvent( EVENT_WINDOW_TARGET_LOCATOR, delegate, false );
			}
			catch ( Exception e )
			{
				fireError( EVENT_WINDOW_TARGET_LOCATOR, delegate, e );
				throw new WebDriverException( e );
			}
		}

		/**
		 * Selects either the first frame on the page, or the main document when a page contains iframes.
		 *
		 * @return This driver focused on the top window/first frame.
		 *
		 * @see org.openqa.selenium.WebDriver.TargetLocator#defaultContent()
		 */
		@Override
		public void defaultContent()
		{
			try
			{
				fireEvent( EVENT_DEFAULT_CONTENT_TARGET_LOCATOR, delegate, true );
				delegate = targetLocator.defaultContent();
				fireEvent( EVENT_DEFAULT_CONTENT_TARGET_LOCATOR, delegate, false );
			}
			catch ( Exception e )
			{
				fireError( EVENT_DEFAULT_CONTENT_TARGET_LOCATOR, delegate, e );
				throw new WebDriverException( e );
			}
		}

		/**
		 * Switches to the element that currently has focus within the document currently "switched to",
		 * or the body element if this cannot be detected. This matches the semantics of calling
		 * "document.activeElement" in Javascript.
		 *
		 * @return The WebElement with focus, or the body element if no element with focus can be detected.
		 *
		 * @see org.openqa.selenium.WebDriver.TargetLocator#activeElement()
		 */
		public HtmlElement activeElement()
		{
			try
			{
				fireEvent( EVENT_ACTIVE_ELEMENT_TARGET_LOCATOR, delegate, true );
				WebElement e = targetLocator.activeElement();
				fireEvent( EVENT_ACTIVE_ELEMENT_TARGET_LOCATOR, delegate, false );
				return new HtmlObject( e );
			}
			catch ( Exception e )
			{
				fireError( EVENT_ACTIVE_ELEMENT_TARGET_LOCATOR, delegate, e );
				throw new WebDriverException( e );
			}
		}

		@Override
		public Alert alert()
		{
			return targetLocator.alert();
		}

	}

	//endregion


}
