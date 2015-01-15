package com.framework.driver.event;

import com.framework.driver.exceptions.PreConditionException;
import com.framework.driver.utils.PreConditions;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.HasInputDevices;
import org.openqa.selenium.interactions.HasTouchScreen;
import org.openqa.selenium.interactions.Keyboard;
import org.openqa.selenium.interactions.internal.Coordinates;
import org.openqa.selenium.internal.Locatable;
import org.openqa.selenium.internal.WrapsDriver;
import org.openqa.selenium.internal.WrapsElement;
import org.openqa.selenium.logging.Logs;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.remote.SessionId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.URL;
import java.util.*;
import java.util.concurrent.TimeUnit;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: org.framework.webdriver.driver.webdriver.event
 *
 * Name   : WebDriverEvent
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2014-12-31
 *
 * Time   : 12:44
 */

public final class EventWebDriver implements WebDriver, WrapsDriver, HasCapabilities, TakesScreenshot, JavascriptExecutor, HasInputDevices, HasTouchScreen
{

	//region WebDriverEvent - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( EventWebDriver.class );

	private final List<EventListener> eventListeners = Lists.newArrayList();

	private final WebDriver delegate;

	private final EventListener dispatcher;
	{
		InvocationHandler invocationHandler = new InvocationHandler()
		{
			@Override
			public Object invoke( final Object proxy, final Method method, final Object[] args ) throws Throwable
			{
				try
				{
					for ( EventListener eventListener : eventListeners )
					{
						method.invoke( eventListener, args );
					}

					return null;
				}
				catch ( InvocationTargetException e )
				{
					throw e.getTargetException();
				}
			}
		};

		dispatcher = ( EventListener ) Proxy.newProxyInstance(
				EventListener.class.getClassLoader(),
				new Class[] { EventListener.class },
				invocationHandler );
	}

	//endregion


	//region WebDriverEvent - Constructor Methods Section

	public EventWebDriver( final WebDriver delegate, EventListener... listeners )
	{
		Class<?>[] allInterfaces = extractInterfaces( delegate );

		for( EventListener listener : listeners )
		{
			register( listener );
		}

		this.delegate = ( WebDriver ) Proxy.newProxyInstance(
				EventListener.class.getClassLoader(), allInterfaces,
				new InvocationHandler()
				{
					public Object invoke( Object proxy, Method method, Object[] args ) throws Throwable
					{
						if ( "getWrappedDriver".equals( method.getName() ) )
						{
							return delegate;
						}
						try
						{
							return method.invoke( delegate, args );
						}
						catch ( InvocationTargetException e )
						{
							dispatcher.onException( e.getTargetException(), delegate );
							throw e.getTargetException();
						}
					}
				}
															);
	}

	//endregion


	//region WebDriverEvent - Service Methods Section

	/**
	 * Creates a new base HtmlElement
	 *
	 * @param from the base {@linkplain org.openqa.selenium.WebElement}
	 *
	 * @return an {@link com.framework.driver.event.EventWebDriver.EventWebElement} instance
	 */
	private EventWebElement createWebElement( WebElement from )
	{
		return new EventWebElement( from );
	}

	/**
	 * @return this for method chaining.
	 */
	private void register( EventListener eventListener )
	{
		eventListeners.add( eventListener );
	}

	/**
	 * @return this for method chaining.
	 */
	private void unregister( EventListener eventListener )
	{
		eventListeners.remove( eventListener );
	}

	//Todo: Documentation
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

	//Todo: Documentation
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
		else if ( arg instanceof EventWebElement )
		{
			return ( ( EventWebElement ) arg ).getWrappedElement();
		}
		else
		{
			return arg;
		}
	}

	private Class<?>[] extractInterfaces( Object object )
	{
		Set<Class<?>> allInterfaces = Sets.newHashSet();
		allInterfaces.add( WrapsDriver.class );
		if ( object instanceof WebElement )
		{
			allInterfaces.add( WrapsElement.class );
		}
		extractInterfaces( allInterfaces, object.getClass() );

		return allInterfaces.toArray( new Class<?>[ allInterfaces.size() ] );
	}

	private void extractInterfaces( Set<Class<?>> addTo, Class<?> clazz )
	{
		if ( Object.class.equals( clazz ) )
		{
			return; // Done
		}

		Class<?>[] classes = clazz.getInterfaces();
		addTo.addAll( Arrays.asList( classes ) );
		extractInterfaces( addTo, clazz.getSuperclass() );
	}

	//endregion


	//region WebDriverEvent - WebElement Service Methods Section

	//todo: documentation
	public boolean elementExists( By locator )
	{
		delegate.manage().timeouts().implicitlyWait( NumberUtils.LONG_ZERO, TimeUnit.MILLISECONDS );
		try
		{
			List<WebElement> temp = delegate.findElements( locator );
			return temp.size() > 0;
		}
		catch ( NoSuchElementException nse )
		{
			return false;
		}
		finally
		{
			long implicitly = NumberUtils.createLong( System.getProperty( "global.implicitly.wait", "10000" ) );
			delegate.manage().timeouts().implicitlyWait( implicitly, TimeUnit.MILLISECONDS );
		}
	}

	//todo: documentation
	public boolean elementExists( WebElement parentElement, By locator )
	{
		delegate.manage().timeouts().implicitlyWait( NumberUtils.LONG_ZERO, TimeUnit.MILLISECONDS );
		try
		{
			List<WebElement> temp = parentElement.findElements( locator );
			return temp.size() > 0;
		}
		catch ( NoSuchElementException nse )
		{
			return false;
		}
		finally
		{
			long implicitly = NumberUtils.createLong( System.getProperty( "global.implicitly.wait", "10000" ) );
			delegate.manage().timeouts().implicitlyWait( implicitly, TimeUnit.MILLISECONDS );
		}
	}

	//todo: documentation
	public void blur( WebElement we )
	{
		( ( EventWebElement ) we ).blur();
	}

	//todo: documentation
	public void jsClick( WebElement we )
	{
		( ( EventWebElement ) we ).jsClick();
	}

	//todo: documentation
	public void focus( WebElement we )
	{
		( ( EventWebElement ) we ).focus();
	}

	//todo: documentation
	public void hover( WebElement we )
	{
		( ( EventWebElement ) we ).hover();
	}

	//todo: documentation
	public String getElementLocator( WebElement we )
	{
		return ( ( EventWebElement ) we ).getLocator();
	}

	//todo: documentation
	public String getElementId( WebElement we )
	{
		return ( ( EventWebElement ) we ).getElementId();
	}

	//todo: documentation
	public boolean hasAttribute( WebElement we, String name )
	{
		return ( ( EventWebElement ) we ).hasAttribute( name );
	}

	//todo: documentation
	public WebElement getWrappedElement( WebElement we )
	{
		return ( ( EventWebElement ) we ).getWrappedElement();
	}

	public WebElement createEventWebElement( WebElement from )
	{
		return createWebElement( from );
	}

	//endregion


	//region WebDriverEvent - Utilities Methods Section

	public SessionId getSessionId()
	{
		return ( ( RemoteWebDriver ) getWrappedDriver() ).getSessionId();
	}

	//endregion


	//region WebDriverEvent - Interfaces Implementation Methods Section

	/**
	 * @return The driver that contains this element.
	 */
	@Override
	public WebDriver getWrappedDriver()
	{
		if ( delegate instanceof WrapsDriver )
		{
			return ( ( WrapsDriver ) delegate ).getWrappedDriver();
		}
		else
		{
			return delegate;
		}
	}

	@Override
	public Capabilities getCapabilities()
	{
		return ( ( RemoteWebDriver ) getWrappedDriver() ).getCapabilities();
	}

	/**
	 * Close the current window, quitting the browser if it's the last window currently open.
	 */
	@Override
	public void close()
	{
		if( this.getSessionId() != null )
		{
			WebDriver drv = getWrappedDriver();
			dispatcher.beforeClose( drv );
			delegate.close();
			dispatcher.afterClose( drv );
		}
	}

	/**
	 * Quits this driver, closing every associated window.
	 */
	@Override
	public void quit()
	{
//		try
//		{
//			if( this.getSessionId() != null )
//			{
				WebDriver drv = getWrappedDriver();
				dispatcher.beforeQuit( drv );
				delegate.quit();
				dispatcher.afterQuit();
//			}
//		}
//		catch ( UnreachableBrowserException e )
//		{
//			logger.error( "got UnreachableBrowserException while trying to close the browser - there is no way to handle this issue" );
//		}
//		finally
//		{
//			if( eventListeners.size() > 0 )
//			{
//				eventListeners.clear();
//			}
//		}
	}

	/**
	 * @return an opaque handle to this window that uniquely identifies it within this driver instance.
	 * This can be used to switch to this window at a later date
	 *
	 * @see org.openqa.selenium.WebDriver#getWindowHandle()
	 */
	@Override
	public String getWindowHandle()
	{
		return delegate.getWindowHandle();
	}

	/**
	 * Return a set of window handles which can be used to iterate over all open windows of this
	 * WebDriver instance by passing them to {@link #switchTo()}.{@link org.openqa.selenium.WebDriver.Options#window()}
	 *
	 * @return A set of window handles which can be used to iterate over all open windows.
	 *
	 * @see org.openqa.selenium.WebDriver#getWindowHandles()
	 */
	@Override
	public Set<String> getWindowHandles()
	{
		return delegate.getWindowHandles();
	}

	/**
	 * Get the source of the last loaded page. If the page has been modified after loading (for
	 * example, by Javascript) there is no guarantee that the returned text is that of the modified
	 * page. Please consult the documentation of the particular driver being used to determine whether
	 * the returned text reflects the current state of the page or the text last sent by the web
	 * server. The page source returned is a representation of the underlying DOM: do not expect it to
	 * be formatted or escaped in the same way as the response sent from the web server. Think of it as
	 * an artist's impression.
	 *
	 * @return The source of the current page
	 *
	 * @see org.openqa.selenium.WebDriver#getPageSource()
	 */
	@Override
	public String getPageSource()
	{
		return delegate.getPageSource();
	}

	/**
	 * The title of the current page.
	 *
	 * @return The title of the current page, with leading and trailing whitespace stripped, or null
	 *         if one is not already set
	 *
	 * @see org.openqa.selenium.WebDriver#getTitle()
	 */
	public String getTitle()
	{
		return delegate.getTitle();
	}

	/**
	 * Get a string representing the current URL that the browser is looking at.
	 *
	 * @return The URL of the page currently loaded in the browser
	 *
	 * @see org.openqa.selenium.WebDriver#getCurrentUrl()
	 */
	@Override
	public String getCurrentUrl()
	{
		return delegate.getCurrentUrl();
	}

	/**
	 * Load a new web page in the current browser window. This is done using an HTTP GET operation,
	 * and the method will block until the load is complete. This will follow redirects issued either
	 * by the server or as a meta-redirect from within the returned HTML. Should a meta-redirect
	 * "rest" for any duration of time, it is best to wait until this timeout is over, since should
	 * the underlying page change whilst your test is executing the results of future calls against
	 * this interface will be against the freshly loaded page. Synonym for
	 * {@link org.openqa.selenium.WebDriver.Navigation#to(String)}.
	 *
	 * @param url The URL to load. It is best to use a fully qualified URL
	 *
	 * @see org.openqa.selenium.WebDriver#get(String)
	 */
	@Override
	public void get( String url )
	{
		try
		{
			PreConditions.checkNotNullNotBlankOrEmpty( url, "URL is either null blank or empty" );
			WebDriver drv = getWrappedDriver();
			dispatcher.beforeNavigateTo( url, drv );
			delegate.get( url );
			dispatcher.afterNavigateTo( url, drv );
		}
		catch ( NullPointerException | IllegalArgumentException npEx )
		{
			logger.error( "throwing a new PreConditionException on {}#get.", getClass().getSimpleName() );
			throw new PreConditionException( npEx.getMessage(), npEx );
		}
	}

	/**
	 * Find the first {@link org.openqa.selenium.WebElement} using the given method.
	 * This method is affected by the 'implicit wait' times in force at the time of execution.
	 * The findElement(..) invocation will return a matching row, or try again repeatedly until
	 * the configured timeout is reached.
	 *
	 * findElement should not be used to look for non-present elements, use {@link #findElement(org.openqa.selenium.By)}
	 * and assert zero length response instead.
	 *
	 * @param by The locating mechanism
	 *
	 * @return The first matching element on the current page
	 *
	 * @throws org.openqa.selenium.NoSuchElementException If no matching elements are found
	 * @see org.openqa.selenium.By
	 * @see org.openqa.selenium.WebDriver.Timeouts
	 */
	@Override
	public WebElement findElement( By by )
	{
		synchronized ( delegate )
		{
			WebDriver drv = ( ( WrapsDriver ) delegate ).getWrappedDriver();
			dispatcher.beforeFindBy( by, drv );
			WebElement temp = delegate.findElement( by );
			dispatcher.afterFindBy( by, drv );
			return createWebElement( temp );
		}
	}

	/**
	 * Find all elements within the current page using the given mechanism.
	 * This method is affected by the 'implicit wait' times in force at the time of execution. When
	 * implicitly waiting, this method will return as soon as there are more than 0 items in the
	 * found collection, or will return an empty list if the timeout is reached.
	 *
	 * @param by The locating mechanism to use
	 *
	 * @return A list of all {@link org.openqa.selenium.WebElement}s, or an empty list if nothing matches
	 *
	 * @see org.openqa.selenium.By
	 * @see org.openqa.selenium.WebDriver.Timeouts
	 * @see org.openqa.selenium.WebDriver#findElements(org.openqa.selenium.By)
	 */
	@Override
	public List<WebElement> findElements( By by )
	{
		synchronized ( delegate )
		{
			WebDriver drv = ( ( WrapsDriver ) delegate ).getWrappedDriver();
			dispatcher.beforeFindBy( by, drv );
			List<WebElement> temp = delegate.findElements( by );
			dispatcher.afterFindBy( by, temp.size(), drv );
			List<WebElement> result = Lists.newArrayList();
			for ( WebElement element : temp )
			{
				result.add( createWebElement( element ) );
			}
			return result;
		}
	}

	/**
	 * Capture the screenshot and store it in the specified location.
	 *
	 * <p>For WebDriver extending TakesScreenshot, this makes a best effort
	 * depending on the browser to return the following in order of preference:
	 * <ul>
	 * 		<li>Entire page</li>
	 * 		<li>Current window</li>
	 * 		<li>Visible portion of the current frame</li>
	 * 		<li>The screenshot of the entire display containing the browser</li>
	 * </ul>
	 *
	 * <p>For WebElement extending TakesScreenshot, this makes a best effort
	 * depending on the browser to return the following in order of preference:
	 * - The entire content of the HTML element
	 * - The visible portion of the HTML element
	 *
	 * @param <X>    Return type for getScreenshotAs.
	 * @param target target type, @see OutputType
	 *
	 * @return Object in which is stored information about the screenshot.
	 *
	 * @throws org.openqa.selenium.WebDriverException on failure.
	 */
	@Override
	public <X> X getScreenshotAs( OutputType<X> target ) throws WebDriverException
	{
		synchronized( delegate )
		{
			if ( delegate instanceof TakesScreenshot )
			{
				return ( ( TakesScreenshot ) delegate ).getScreenshotAs( target );
			}

			throw new UnsupportedOperationException( "Underlying driver instance does not support taking screenshots" );
		}
	}

	//Todo: Documentation
	@Override
	public Object executeScript( String script, Object... args )
	{
		synchronized( delegate )
		{
			if ( delegate instanceof JavascriptExecutor )
			{
				WebDriver drv = getWrappedDriver();
				dispatcher.beforeScript( script, drv );
				Object[] usedArgs = unpackWrappedArgs( args );
				Object result = ( ( JavascriptExecutor ) delegate ).executeScript( script, usedArgs );
				dispatcher.afterScript( drv );
				return result;
			}
			throw new UnsupportedOperationException( "Underlying driver instance does not support executing javascript" );
		}
	}

	//Todo: Documentation
	@Override
	public Object executeAsyncScript( String script, Object... args )
	{
		synchronized( delegate )
		{
			if ( delegate instanceof JavascriptExecutor )
			{
				WebDriver drv = getWrappedDriver();
				dispatcher.beforeScript( script, drv );
				Object[] usedArgs = unpackWrappedArgs( args );
				Object result = ( ( JavascriptExecutor ) delegate ).executeAsyncScript( script, usedArgs );
				dispatcher.afterScript( drv );
				return result;
			}
			throw new UnsupportedOperationException( "Underlying driver instance does not support executing javascript" );
		}
	}

	// todo:documentation
	public EventTouch getTouch()
	{
		if ( delegate instanceof HasTouchScreen )
		{
			return new EventTouch( delegate, dispatcher );
		}
		else
		{
			throw new UnsupportedOperationException( "Underlying driver does not implement advanced" + " user interactions yet." );
		}
	}

	/**
	 * Send future commands to a different frame or window.
	 *
	 * @return A TargetLocator which can be used to select a frame or window
	 *
	 * @see org.openqa.selenium.WebDriver.TargetLocator
	 */
	@Override
	public TargetLocator switchTo()
	{
		return new EventTargetLocator( delegate.switchTo() );
	}

	/**
	 * An abstraction allowing the driver to access the browser's history and to navigate to a given
	 * URL.
	 *
	 * @return A {@link org.openqa.selenium.WebDriver.Navigation} that allows the selection of what to do next
	 *
	 * @see org.openqa.selenium.WebDriver.Navigation#navigate()
	 */
	@Override
	public Navigation navigate()
	{
		return new EventNavigation( delegate.navigate() );
	}


	public SessionStorage sessionStorage()
	{
		return new EventSessionStorage( delegate );
	}

	/**
	 * Gets the Option interface
	 *
	 * @return An option interface
	 *
	 * @see org.openqa.selenium.WebDriver.Options
	 */
	@Override
	public Options manage()
	{
		return new EventOptions( delegate.manage() );
	}

	/**
	 * Interface implemented by each driver that allows access to the raw input devices.
	 *
	 * @return a {@link org.openqa.selenium.interactions.Keyboard} instance.
	 *
	 * @see EventKeyboard
	 */
	@Override
	public Keyboard getKeyboard()
	{
		if ( delegate instanceof HasInputDevices )
		{
			return new EventKeyboard( delegate, dispatcher );
		}
		else
		{
			throw new UnsupportedOperationException( "Underlying driver does not implement advanced" + " user interactions yet." );
		}
	}

	// todo:documentation
	public EventMouse getMouse()
	{
		if ( delegate instanceof HasInputDevices )
		{
			return new EventMouse( delegate, dispatcher );
		}
		else
		{
			throw new UnsupportedOperationException( "Underlying driver does not implement advanced" + " user interactions yet." );
		}
	}

	// todo:documentation
	public JavaScript getJavaScript()
	{
		return new EventJavaScript();
	}

	//endregion


	//region WebDriverEvent - EventWindow Inner Classes Implementation Section

	private class EventWindow implements Window
	{

		private final Window window;

		EventWindow( Window window )
		{
			this.window = window;
		}


		public void setSize( Dimension targetSize )
		{
			WebDriver drv = getWrappedDriver();
			dispatcher.beforeSetWindowSize( drv, targetSize );
			window.setSize( targetSize );
			dispatcher.afterSetWindowSize( drv );
		}

		/**
		 * {@inheritDoc}
		 */
		public void setPosition( Point targetLocation )
		{
			WebDriver drv = getWrappedDriver();
			dispatcher.beforeSetWindowPosition( drv, targetLocation );
			window.setPosition( targetLocation );
			dispatcher.afterSetWindowPosition( drv );
		}

		/**
		 * {@inheritDoc}
		 */
		public Dimension getSize()
		{
			return window.getSize();
		}

		/**
		 * {@inheritDoc}
		 */
		public Point getPosition()
		{
			return window.getPosition();
		}

		/**
		 * {@inheritDoc }
		 */
		public void maximize()
		{
			WebDriver drv = getWrappedDriver();
			dispatcher.beforeMaximize( drv );
			window.maximize();
			dispatcher.afterMaximize( drv );
		}
	}

	//endregion


	//region WebDriverEvent - EventOptions Inner Classes Implementation Section

	private class EventOptions implements Options
	{

		private Options options;

		private EventOptions( Options options )
		{
			this.options = options;
		}

		public Logs logs()
		{
			return options.logs();
		}

		/**
		 * Add a specific cookie. If the cookie's domain name is left blank, it is assumed that the
		 * cookie is meant for the domain of the current document.
		 *
		 * @param cookie The cookie to add.
		 */
		public void addCookie( Cookie cookie )
		{
			WebDriver drv = getWrappedDriver();
			dispatcher.beforeAddCookie( drv, cookie );
			options.addCookie( cookie );
			dispatcher.afterAddCookie( drv, cookie );
		}

		/**
		 * Delete the named cookie from the current domain. This is equivalent to setting the named
		 * cookie's expiry date to some time in the past.
		 *
		 * @param name The name of the cookie to delete
		 */
		public void deleteCookieNamed( String name )
		{
			WebDriver drv = getWrappedDriver();
			dispatcher.beforeDeleteCookie( drv, name );
			options.deleteCookieNamed( name );
			dispatcher.afterDeleteCookie( drv, name );
		}

		/**
		 * Delete a cookie from the browser's "cookie jar". The domain of the cookie will be ignored.
		 *
		 * @param cookie a {@linkplain org.openqa.selenium.Cookie} instance.
		 */
		public void deleteCookie( Cookie cookie )
		{
			WebDriver drv = getWrappedDriver();
			dispatcher.beforeDeleteCookie( drv, cookie );
			options.deleteCookie( cookie );
			dispatcher.afterDeleteCookie( drv, cookie );
		}

		/**
		 * Delete all the cookies for the current domain.
		 */
		public void deleteAllCookies()
		{
			WebDriver drv = getWrappedDriver();
			dispatcher.beforeDeleteAllCookies( drv );
			options.deleteAllCookies();
			dispatcher.afterDeleteAllCookies( drv );
		}

		/**
		 * Get all the cookies for the current domain. This is the equivalent of calling "document.cookie" and parsing the result
		 *
		 * @return A Set of cookies for the current domain.
		 */
		public Set<Cookie> getCookies()
		{
			return options.getCookies();
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
			return options.getCookieNamed( name );
		}

		/**
		 * Returns the interface for managing driver timeouts.
		 */
		public EventTimeouts timeouts()
		{
			return new EventTimeouts( options.timeouts() );
		}

		/**
		 * Returns the interface for controlling IME engines to generate complex-script input.
		 */
		public ImeHandler ime()
		{
			throw new UnsupportedOperationException( "Driver does not support IME interactions" );
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


	//region WebDriverEvent - EventTimeouts Inner Classes Implementation Section

	/**
	 * An interface implementation for managing timeout behavior for WebDriver instances.
	 */
	private class EventTimeouts implements Timeouts
	{

		private final Timeouts timeouts;

		EventTimeouts( Timeouts timeouts )
		{
			this.timeouts = timeouts;
		}

		/**
		 * Specifies the amount of time the driver should wait when searching for an element if it is
		 * not immediately present.
		 * <p/>
		 * When searching for a single element, the driver should poll the page until the element has
		 * been found, or this timeout expires before throwing a {@link org.openqa.selenium.NoSuchElementException}. When
		 * searching for multiple elements, the driver should poll the page until at least one element
		 * has been found or this timeout has expired.
		 * <p/>
		 * Increasing the implicit wait timeout should be used judiciously as it will have an adverse
		 * effect on test run time, especially when used with slower location strategies like XPath.
		 *
		 * @param time The amount of time to wait.
		 * @param unit The unit of measure for {@code time}.
		 *
		 * @return A self reference.
		 */
		//@Override
		public EventTimeouts implicitlyWait( long time, TimeUnit unit )
		{
			dispatcher.beforeImplicitlyWait( getWrappedDriver(), time, unit );
			timeouts.implicitlyWait( time, unit );
			dispatcher.afterImplicitlyWait( time, unit );
			return this;
		}

		/**
		 * Sets the amount of time to wait for an asynchronous script to finish execution before
		 * throwing an error. If the timeout is negative, then the script will be allowed to run
		 * indefinitely.
		 *
		 * @param time The timeout value.
		 * @param unit The unit of time.
		 *
		 * @return A self reference.
		 *
		 * @see org.openqa.selenium.JavascriptExecutor#executeAsyncScript(String, Object...)
		 */
		//@Override
		public EventTimeouts setScriptTimeout( long time, TimeUnit unit )
		{
			dispatcher.beforeSetScriptTimeout( getWrappedDriver(), time, unit );
			timeouts.setScriptTimeout( time, unit );
			dispatcher.afterSetScriptTimeout( time, unit );
			return this;
		}

		/**
		 * Sets the amount of time to wait for a page load to complete before throwing an error.
		 * If the timeout is negative, page loads can be indefinite.
		 *
		 * @param time The timeout value.
		 * @param unit The unit of time.
		 *
		 * @return A Timeouts interface.
		 */
		//@Override
		public EventTimeouts pageLoadTimeout( long time, TimeUnit unit )
		{
			dispatcher.beforePageLoadTimeout( getWrappedDriver(), time, unit  );
			timeouts.pageLoadTimeout( time, unit );
			dispatcher.afterPageLoadTimeout( time, unit  );
			return this;
		}
	}

	//endregion


	//region WebDriverEvent - EventTargetLocator Inner Classes Implementation Section

	private class EventTargetLocator implements TargetLocator
	{

		private TargetLocator targetLocator;

		private EventTargetLocator( TargetLocator targetLocator )
		{
			this.targetLocator = targetLocator;
		}

		/**
		 * Select a frame by its (zero-based) index. Selecting a frame by index is equivalent to the
		 * JS expression window.frames[index] where "window" is the DOM window represented by the
		 * current context. Once the frame has been selected, all subsequent calls on the WebDriver
		 * interface are made to that frame.
		 *
		 * @param frameIndex (zero-based) index
		 *
		 * @return This driver focused on the given frame
		 *
		 * @throws org.openqa.selenium.NoSuchFrameException If the frame cannot be found
		 *
		 * @see org.openqa.selenium.WebDriver.TargetLocator#frame(int)
		 */
		//@Override
		public WebDriver frame( int frameIndex )
		{
			WebDriver drv = getWrappedDriver();
			dispatcher.beforeSwitchToFrame( drv, frameIndex );
			WebDriver wd = targetLocator.frame( frameIndex );
			dispatcher.afterSwitchToFrame( drv, wd, frameIndex );
			return wd;
		}

		/**
		 * Select a frame by its name or ID. Frames located by matching name attributes are always given
		 * precedence over those matched by ID.
		 *
		 * @param nameOrId the name of the frame window, the id of the &lt;frame&gt; or &lt;iframe&gt;
		 *                 element, or the (zero-based) index
		 *
		 * @return This driver focused on the given frame
		 *
		 * @throws org.openqa.selenium.NoSuchFrameException If the frame cannot be found
		 *
		 * @see org.openqa.selenium.WebDriver.TargetLocator#frame(String)
		 */
		//@Override
		public WebDriver frame( String nameOrId )
		{
			WebDriver drv = getWrappedDriver();
			dispatcher.beforeSwitchToFrame( drv, nameOrId );
			WebDriver wd = targetLocator.frame( nameOrId );
			dispatcher.afterSwitchToFrame( drv, wd, nameOrId );
			return wd;
		}

		/**
		 * Select a frame using its previously located {@link org.openqa.selenium.WebElement}.
		 *
		 * @param frameElement The frame element to switch to.
		 *
		 * @return This driver focused on the given frame.
		 *
		 * @throws org.openqa.selenium.NoSuchFrameException           If the given element is neither an IFRAME nor a FRAME element.
		 * @throws org.openqa.selenium.StaleElementReferenceException If the WebElement has gone stale.
		 *
		 * @see org.openqa.selenium.WebDriver#findElement(org.openqa.selenium.By)
		 * @see org.openqa.selenium.WebDriver.TargetLocator#frame(org.openqa.selenium.WebElement)
		 */
		//@Override
		public WebDriver frame( WebElement frameElement )
		{
			WebDriver drv = getWrappedDriver();
			dispatcher.beforeSwitchToFrame( drv, frameElement );
			WebDriver wd = targetLocator.frame( frameElement );
			dispatcher.afterSwitchToFrame( drv, wd, frameElement );
			return wd;
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
		public WebDriver parentFrame()
		{
			WebDriver drv = getWrappedDriver();
			dispatcher.beforeSwitchToParentFrame( drv );
			WebDriver wd = targetLocator.parentFrame();
			dispatcher.afterSwitchToParentFrame( drv, wd );
			return wd;
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
		public WebDriver window( String nameOrHandle )
		{
			WebDriver drv = getWrappedDriver();
			dispatcher.beforeSwitchToWindow( drv, nameOrHandle );
			WebDriver wd = targetLocator.window( nameOrHandle );
			dispatcher.afterSwitchToWindow( drv, wd, nameOrHandle );
			return wd;
		}

		/**
		 * Selects either the first frame on the page, or the main document when a page contains iframes.
		 *
		 * @return This driver focused on the top window/first frame.
		 *
		 * @see org.openqa.selenium.WebDriver.TargetLocator#defaultContent()
		 */
		@Override
		public WebDriver defaultContent()
		{
			WebDriver drv = getWrappedDriver();
			dispatcher.beforeSwitchToDefaultContent( drv );
			WebDriver wd = targetLocator.defaultContent();
			dispatcher.afterSwitchToDefaultContent( drv, wd );
			return wd;
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
		public WebElement activeElement()
		{
			WebDriver drv = getWrappedDriver();
			dispatcher.beforeSwitchToActiveElement( drv );
			WebElement we = targetLocator.activeElement();
			dispatcher.afterSwitchToActiveElement( drv, we );
			return we;
		}

		/**
		 * Switches to the currently active modal dialog for this particular driver instance.
		 *
		 * @return A handle to the dialog.
		 *
		 * @throws org.openqa.selenium.NoAlertPresentException If the dialog cannot be found
		 */
		//@Override
		public Alert alert()
		{
			return targetLocator.alert();
		}

	}

	//endregion


	//region WebDriverEvent - EventNavigation Inner Classes Implementation Section

	private class EventNavigation implements Navigation
	{
		private final Navigation navigation;

		EventNavigation( Navigation navigation )
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
			dispatcher.beforeNavigateTo( url, getWrappedDriver()  );
			navigation.to( url );
			dispatcher.afterNavigateTo( url, getWrappedDriver()  );
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
			WebDriver drv = getWrappedDriver();
			dispatcher.beforeNavigateBack( drv );
			navigation.back();
			dispatcher.afterNavigateBack( drv );
		}

		/**
		 * Move a single "item" forward in the browser's history. Does nothing if we are on the latest page viewed.
		 */
		public void forward()
		{
			WebDriver drv = getWrappedDriver();
			dispatcher.beforeNavigateForward( drv );
			navigation.forward();
			dispatcher.afterNavigateForward( drv );
		}

		/**
		 * Refresh the current page
		 */
		public void refresh()
		{
			WebDriver drv = getWrappedDriver();
			dispatcher.beforeRefresh( drv );
			navigation.refresh();
			dispatcher.afterRefresh( drv );
		}
	}


	//endregion


	//region WebDriverEvent - EventJavaScript Inner Classes Implementation Section

	private class EventJavaScript implements JavaScript
	{
		private EventJavaScript()
		{
			super();
		}

		public String getString( String script, Object... args )
		{
			return ( String ) executeScript( script, args );
		}

		public String[] getStringArray( String script, Object... args )
		{
			return ( String[] ) executeScript( script, args );
		}

		public Number getNumber( String script, Object... args )
		{
			return ( Number ) executeScript( script, args );
		}

		public boolean getBoolean( String script, Object... args )
		{
			return ( Boolean ) executeScript( script, args );
		}

		public WebElement getWebElement( String script, Object... args )
		{
			WebElement we = ( WebElement ) executeScript( script, args );
			return createWebElement( we );
		}
	}


	//endregion


	//region WebDriverEvent - EventElement Inner Classes Implementation Section

	private class EventWebElement implements WebElement, WrapsElement, WrapsDriver, Locatable
	{

		//region WebDriverEvent.EventWebElement - Variables Declaration and Initialization Section.

		private final WebElement element;

		private final WebElement underlyingElement;

		//endregion


		//region WebDriverEvent.EventWebElement - Constructor Methods Section

		EventWebElement( final WebElement element )
		{
			this.element = ( WebElement ) Proxy.newProxyInstance(
					EventListener.class.getClassLoader(),
					extractInterfaces( element ),
					new InvocationHandler()
					{
						public Object invoke( Object proxy, Method method, Object[] args ) throws Throwable
						{
							if( logger.isTraceEnabled() )
							{
								String joiner = null;
								if( null != args )
								{
									joiner = Joiner.on( "," ).join( args );
								}
								logger.trace( "invoking method \"{}\" with args [{}]", method.getName(), StringUtils.defaultString( joiner, "N/A" ) );
							}
							if ( method.getName().equals( "getWrappedElement" ) )
							{
								return element;
							}
							try
							{
								return method.invoke( element, args );
							}
							catch ( InvocationTargetException e )
							{
								dispatcher.onException( e.getTargetException(), delegate );
								throw e.getTargetException();
							}
						}
					}
			);
			this.underlyingElement = element;
		}

		//endregion


		//region WebDriverEvent.EventWebElement - Service Methods Section

		@Override
		public boolean equals( Object obj )
		{
			if ( ! ( obj instanceof WebElement ) )
			{
				return false;
			}

			WebElement other = ( WebElement ) obj;
			if ( other instanceof WrapsElement )
			{
				other = ( ( WrapsElement ) other ).getWrappedElement();
			}

			return underlyingElement.equals( other );
		}

		@Override
		public int hashCode()
		{
			return underlyingElement.hashCode();
		}

		@Override
		public String toString()
		{
			return underlyingElement.toString();
		}

		//endregion


		//region WebDriverEvent.WebDriverEvent - WebDriver Implementation Methods Section

		/**
		 * Use this to discover where on the screen an element is so that we can click it.
		 * This method should cause the element to be scrolled into view.
		 *
		 * @return The top left-hand corner location on the screen, or null if the element is not visible
		 */
		@Override
		public Coordinates getCoordinates()
		{
			return ( ( Locatable ) underlyingElement ).getCoordinates();
		}

		/**
		 * @return The driver that contains this element.
		 */
		@Override
		public WebDriver getWrappedDriver()
		{
			return delegate;
		}

		/**
		 * @return The driver that contains this element.
		 */
		@Override
		public WebElement getWrappedElement()
		{
			return underlyingElement;
		}

		/**
		 * If this element is a text entry element, this will clear the value. Has no effect on other
		 * elements. Text entry elements are INPUT and TEXTAREA elements.
		 *
		 * Note that the events fired by this event may not be as you'd expect.  In particular, we don't
		 * fire any keyboard or mouse events.  If you want to ensure keyboard events are fired, consider
		 * using something like {@link org.openqa.selenium.WebElement#sendKeys(CharSequence...)} with the backspace key.  To ensure
		 * you get a change event, consider following with a call to {@link org.openqa.selenium.WebElement#sendKeys(CharSequence...)}
		 * with the tab key.
		 *
		 * @see org.openqa.selenium.WebElement#clear()
		 */
		@Override
		public void clear()
		{
			WebDriver drv = getWrappedDriver();
			dispatcher.beforeChangeValueOf( underlyingElement, drv );
			element.clear();
			dispatcher.afterChangeValueOf( underlyingElement, drv );
		}

		/**
		 * Click this element. If this causes a new page to load, this method will attempt to block until
		 * the page has loaded. At this point, you should discard all references to this element and any
		 * further operations performed on this element will throw a StaleElementReferenceException unless
		 * you know the element and the page will still be present. If click() causes a new page to be
		 * loaded via an event or is done by sending a native event then the method will *not* wait for
		 * it to be loaded and the caller should verify that a new page has been loaded.
		 * <p/>
		 * There are some preconditions for an element to be clicked.  The element must be visible and
		 * it must have a height and width greater then 0.
		 *
		 * @throws org.openqa.selenium.StaleElementReferenceException If the element no longer exists as initially defined
		 * @see org.openqa.selenium.WebElement#click()
		 */
		@Override
		public void click()
		{
			WebDriver drv = getWrappedDriver();
			dispatcher.beforeClickOn( underlyingElement, drv );
			element.click();
			dispatcher.afterClickOn( underlyingElement, drv );
		}

		/**
		 * Use this method to simulate typing into an element, which may set its value.
		 *
		 * @param keysToSend the keys sequence to send
		 *
		 * @see org.openqa.selenium.WebElement#sendKeys(CharSequence...)
		 * @see org.openqa.selenium.Keys
		 */
		@Override
		public void sendKeys( CharSequence... keysToSend )
		{
			WebDriver drv = getWrappedDriver();
			dispatcher.beforeChangeValueOf( underlyingElement, drv );
			element.sendKeys( keysToSend );
			dispatcher.afterChangeValueOf( underlyingElement, drv );
		}

		/**
		 * if this current element is a form, or an element within a form,
		 * then this will be submitted to the remote server.
		 * If this causes the current page to change, then this method will block until the new page is loaded.
		 *
		 * @throws org.openqa.selenium.NoSuchContextException If the given element is not within a form
		 * @see org.openqa.selenium.WebElement#submit()
		 */
		@Override
		public void submit()
		{
			WebDriver drv = getWrappedDriver();
			dispatcher.beforeSubmitOn( underlyingElement, drv );
			element.submit();
			dispatcher.afterSubmitOn( underlyingElement, drv );
		}

		/**
		 * Is this element displayed or not? This method avoids the problem of having to parse an
		 * element's "style" attribute.
		 *
		 * @return Whether or not the element is displayed
		 *
		 * @see org.openqa.selenium.WebElement#isDisplayed()
		 */
		public boolean isDisplayed()
		{
			return element.isDisplayed();
		}

		/**
		 * Is the element currently enabled or not? This will generally return true for everything but
		 * disabled input html-elements.
		 *
		 * @return True if the element is enabled, false otherwise.
		 *
		 * @see org.openqa.selenium.WebElement#isEnabled()
		 */
		@Override
		public boolean isEnabled()
		{
			return element.isEnabled();
		}

		/**
		 * Determine whether or not this element is selected or not. This operation only applies to input
		 * elements such as checkboxes, options in a select and radio buttons.
		 *
		 * @return True if the element is currently selected or checked, false otherwise.
		 *
		 * @see org.openqa.selenium.WebElement#isSelected()
		 */
		@Override
		public boolean isSelected()
		{
			return element.isSelected();
		}

		/**
		 * Get the tag name of this element. Not the value of the name attribute
		 *
		 * @return the element tag name
		 *
		 * @see org.openqa.selenium.WebElement#getTagName()
		 */
		@Override
		public String getTagName()
		{
			return element.getTagName().toLowerCase();
		}

		/**
		 * Get the value of a the given attribute of the element. Will return the current value, even if
		 * this has been modified after the page has been loaded. More exactly, this method will return
		 * the value of the given attribute, unless that attribute is not present, in which case the value
		 * of the property with the same name is returned (for example for the "value" property of a
		 * textarea element). If neither value is set, null is returned. The "style" attribute is
		 * converted as best can be to a text representation with a trailing semi-colon. The following are
		 * deemed to be "boolean" attributes, and will return either "true" or null:
		 *
		 * async, autofocus, autoplay, checked, compact, complete, controls, declare, defaultchecked,
		 * defaultselected, defer, disabled, draggable, ended, formnovalidate, hidden, indeterminate,
		 * iscontenteditable, ismap, itemscope, loop, multiple, muted, nohref, noresize, noshade,
		 * novalidate, nowrap, open, paused, pubdate, readonly, required, reversed, scoped, seamless,
		 * seeking, selected, spellcheck, truespeed, willvalidate
		 *
		 * Finally, the following commonly mis-capitalized attribute/property names are evaluated as
		 * expected:
		 *
		 * <ul>
		 * 		<li>"class"
		 * 		<li>"readonly"
		 * </ul>
		 *
		 * @param name The name of the attribute.
		 *
		 * @return The attribute/property's current value or null if the value is not set.
		 *
		 * @see org.openqa.selenium.WebElement#getAttribute(String)
		 */
		@Override
		public String getAttribute( String name )
		{
			WebDriver drv = getWrappedDriver();
			dispatcher.beforeGetAttributeValueOf( underlyingElement, drv, name );
			return element.getAttribute( name );
		}

		/**
		 * Get the value of a given CSS property.
		 * Color values should be returned as rgba strings, so, for example if the "background-color" property is set as "green"
		 * in the HTML source, the returned value will be "rgba(0, 255, 0, 1)".
		 * Note that shorthand CSS properties (e.g. background, font, border, border-top, margin, margin-top, padding, padding-top, list-style, outline, pause, cue) are
		 * not
		 * returned,
		 * <p>in accordance with the DOM CSS2 specification - you should directly access the longhand properties (e.g. background-color)
		 * to access the desired values.
		 * </p>
		 *
		 * @param propertyName the css property name. can be helped using {@link com.framework.utils.web.CSS2Properties}
		 *
		 * @return The current, computed value of the property.
		 *
		 * @see org.openqa.selenium.WebElement#getCssValue(String)
		 * @see com.framework.utils.web.CSS2Properties
		 */
		@Override
		public String getCssValue( String propertyName )
		{
			WebDriver drv = getWrappedDriver();
			dispatcher.beforeGetCssPropertyValueOf( underlyingElement, drv, propertyName );
			return element.getCssValue( propertyName );
		}

		/**
		 * Get the visible (i.e. not hidden by CSS) innerText of this element, including sub-htmlelements,
		 * without any leading or trailing whitespace.
		 *
		 * @return the element text
		 *
		 * @see org.openqa.selenium.WebElement#getText()
		 */
		@Override
		public String getText()
		{
			return element.getText();
		}

		/**
		 * Find the first {@linkplain org.openqa.selenium.WebElement} using the given method.
		 * See the note in {@linkplain org.openqa.selenium.WebElement#findElements(org.openqa.selenium.By)} about finding via XPath.
		 * This method is affected by the 'implicit wait' times in force at the time of execution.
		 * The findElement(..) invocation will return a matching row, or try again repeatedly until the configured timeout is reached.
		 * findElement should not be used to look for non-present htmlelements,
		 * use {@linkplain org.openqa.selenium.WebElement#findElements(org.openqa.selenium.By)}
		 * and assert zero length response instead.
		 *
		 * @param by The locating mechanism to use
		 *
		 * @return The first matching element on the current context.
		 *
		 * @throws org.openqa.selenium.NoSuchElementException - If no matching htmlelements are found
		 * @see org.openqa.selenium.WebElement#findElement(org.openqa.selenium.By)
		 * @see org.openqa.selenium.SearchContext#findElement(org.openqa.selenium.By)
		 * @see org.openqa.selenium.WebDriver.Timeouts
		 */
		@Override
		public WebElement findElement( By by )
		{
			WebDriver drv = getWrappedDriver();
			dispatcher.beforeFindBy( by, element, drv );
			WebElement temp = element.findElement( by );
			dispatcher.afterFindBy( by, element, drv );
			return createWebElement( temp );
		}

		/**
		 * Find all html elements within the current context using the given mechanism.
		 * When using xpath be aware that webdriver follows standard conventions:
		 * a search prefixed with "//" will search the entire document, not just the children of this current node.
		 * Use ".//" to limit your search to the children of this WebElement.
		 * This method is affected by the 'implicit wait' times in force at the time of execution.
		 * When implicitly waiting, this method will return as soon as there are more than 0 items in the found collection,
		 * or will return an empty list if the timeout is reached.
		 *
		 * @param by The locating mechanism to use
		 *
		 * @return A list of all {@linkplain org.openqa.selenium.WebElement}, or an empty list if nothing matches.
		 *
		 * @see org.openqa.selenium.WebElement#findElements(org.openqa.selenium.By)
		 * @see org.openqa.selenium.SearchContext#findElements(org.openqa.selenium.By)
		 * @see org.openqa.selenium.WebDriver.Timeouts
		 */
		@Override
		public List<WebElement> findElements( By by )
		{
			WebDriver drv = getWrappedDriver();
			dispatcher.beforeFindBy( by, element, drv );
			List<WebElement> temp = element.findElements( by );
			dispatcher.afterFindBy( by, element, temp.size(), drv );
			List<WebElement> result = Lists.newArrayList();
			for ( WebElement element : temp )
			{
				result.add( createWebElement( element ) );
			}
			return result;
		}

		/**
		 * Where on the page is the top left-hand corner of the rendered element?
		 *
		 * @return A point, containing the location of the top left-hand corner of the element
		 *
		 * @see org.openqa.selenium.WebElement#getLocation()
		 */
		@Override
		public Point getLocation()
		{
			return element.getLocation();
		}

		/**
		 * What is the width and height of the rendered element?
		 *
		 * @return The size of the element on the page.
		 */
		@Override
		public Dimension getSize()
		{
			return element.getSize();
		}

		//endregion


		//region WebDriverEvent.WebDriverEvent - Additional Methods Section

		public void blur()
		{
			WebDriver drv = getWrappedDriver();
			dispatcher.beforeBlurFrom( underlyingElement, drv );
			executeScript( "elements[0].blur()", element );
			dispatcher.afterBlurFrom( underlyingElement, drv );
		}

		public void focus()
		{
			WebDriver drv = getWrappedDriver();
			dispatcher.beforeFocusOn( underlyingElement, drv );
			executeScript( "elements[0].focus()", element );
			dispatcher.afterFocusOn( underlyingElement, drv );
		}

		public void jsClick()
		{
			WebDriver drv = getWrappedDriver();
			dispatcher.beforeClickOn( underlyingElement, drv );
			executeScript( "elements[0].click()", element );
			dispatcher.afterClickOn( underlyingElement, drv );
		}

		public void hover()
		{
			WebDriver drv = getWrappedDriver();
			dispatcher.beforeHoverOn( underlyingElement, drv );
			new Actions( getWrappedDriver() ).moveToElement( element ).build().perform();
			dispatcher.afterHoverOn( underlyingElement, drv );
		}

		public boolean hasAttribute( final String name )
		{
			return ! StringUtils.isEmpty( element.getAttribute( name ) );
		}

		public String getLocator()
		{
			int index = underlyingElement.toString().indexOf( "->" );
			return StringUtils.substring( underlyingElement.toString(), index + 3 ).replace( "]", StringUtils.EMPTY );
		}

		public String getElementId()
		{
			return ( ( RemoteWebElement ) underlyingElement ).getId();
		}

		//endregion
	}

	//endregion


	//region WebDriverEvent - EventSessionStorage Inner Classes Implementation Section

	private class EventSessionStorage implements SessionStorage
	{
		private JavascriptExecutor js;

		private EventSessionStorage( WebDriver driver )
		{
			js = ( JavascriptExecutor ) driver;
		}

		@Override
		public void removeItemFromSessionStorage( String item )
		{
			js.executeScript( String.format( "window.sessionStorage.removeItem('%s');", item ) );
		}

		@Override
		public boolean isItemPresentInSessionStorage( String item )
		{
			if ( js.executeScript( String.format( "return window.sessionStorage.getItem('%s');", item ) ) == null )
			{
				return false;
			}
			else
			{
				return true;
			}
		}

		@Override
		public String getItemFromSessionStorage( String key )
		{
			return ( String ) js.executeScript( String.format( "return window.sessionStorage.getItem('%s');", key ) );
		}

		@Override
		public String getKeyFromSessionStorage( int key )
		{
			return ( String ) js.executeScript( String.format( "return window.sessionStorage.key('%s');", key ) );
		}

		@Override
		public Long getSessionStorageLength()
		{
			return ( Long ) js.executeScript( "return window.sessionStorage.length;" );
		}

		@Override
		public void setItemInSessionStorage( String item, String value )
		{
			js.executeScript( String.format( "window.sessionStorage.setItem('%s','%s');", item, value ) );
		}

		@Override
		public void clearSessionStorage()
		{
			js.executeScript( String.format( "window.sessionStorage.clear();" ) );
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
}
