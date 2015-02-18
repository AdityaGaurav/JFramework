package com.framework.driver.event;

import com.google.common.base.Optional;
import org.hamcrest.Matcher;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.HasInputDevices;
import org.openqa.selenium.internal.WrapsDriver;
import org.openqa.selenium.logging.Logs;
import org.openqa.selenium.remote.SessionId;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface HtmlDriver extends WrapsDriver, HasCapabilities, TakesScreenshot, HasInputDevices, JavascriptExecutor
{

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
	 */
	void get( String url );

	/**
	 * Get a string representing the current URL that the browser is looking at.
	 *
	 * @return The URL of the page currently loaded in the browser
	 */
	String getCurrentUrl();

	/**
	 * The title of the current page.
	 *
	 * @return The title of the current page, with leading and trailing whitespace stripped, or null
	 *         if one is not already set
	 */
	String getTitle();

	/**
	 * Find all elements within the current page using the given mechanism.
	 * This method is affected by the 'implicit wait' times in force at the time of execution. When
	 * implicitly waiting, this method will return as soon as there are more than 0 items in the
	 * found collection, or will return an empty list if the timeout is reached.
	 *
	 * @param by The locating mechanism to use
	 *
	 * @return A list of all {@link com.framework.driver.event.HtmlElement}s, or an empty list if nothing matches
	 *
	 * @see org.openqa.selenium.By
	 * @see org.openqa.selenium.TimeoutException
	 */
	List<HtmlElement> findElements( By by );

	/**
	 * Find the first {@link HtmlElement} using the given method.
	 * This method is affected by the 'implicit wait' times in force at the time of execution.
	 * The findElement(..) invocation will return a matching row, or try again repeatedly until
	 * the configured timeout is reached.
	 *
	 * findElement should not be used to look for non-present elements, use {@link #findElements(By)}
	 * and assert zero length response instead.
	 *
	 * @param by The locating mechanism
	 *
	 * @return The first matching element on the current page
	 *
	 * @throws NoSuchElementException If no matching elements are found
	 *
	 * @see org.openqa.selenium.By
	 */
	HtmlElement findElement( By by );

	/**
	 * immediately finds an element by reducing temporary the {@code implicitly wait} to zero.
	 * the it restores the implicitly wait to his default.
	 *
	 * @param by  The locating mechanism
	 *
	 * @return an {@link Optional} <b>HtmlElement</b>
	 *
	 * @see com.google.common.base.Optional#isPresent()
	 */
	Optional<HtmlElement> elementExists( final By by );

	Optional<List<HtmlElement>> allElementExists( final By by );

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
	 */
	String getPageSource();

	/**
	 * Close the current window, quitting the browser if it's the last window currently open.
	 */
	void close();

	/**
	 * Quits this driver, closing every associated window.
	 */
	void quit();

	/**
	 * Return a set of window handles which can be used to iterate over all open windows of this
	 * WebDriver instance by passing them to {@link #switchTo()}.{@link Options#window()}
	 *
	 * @return A set of window handles which can be used to iterate over all open windows.
	 */
	Set<String> getWindowHandles();

	/**
	 * Return an opaque handle to this window that uniquely identifies it within this driver instance.
	 * This can be used to switch to this window at a later date
	 */
	String getWindowHandle();

	TargetLocator switchTo();

	Navigation navigate();

	Options manage();

	/**
	 * returns an instance that provides functionality to work with client session storage.
	 *
	 * @return  a {@link com.framework.driver.event.HtmlDriver.SessionStorage} instance
	 */
	SessionStorage sessionStorage();

	JavaScriptSupport javascript();

	SessionId getSessionId();

	<T> void assertThat( String reason, T actual, Matcher<? super T> matcher );

	<T> void assertWaitThat( String reason, long timeout, HtmlCondition<?> condition );

	interface Options
	{
		void addCookie( Cookie cookie );

		void deleteCookieNamed( String name );

		void deleteCookie( Cookie cookie );

		void deleteAllCookies();

		Set<Cookie> getCookies();

		Cookie getCookieNamed( String name );

		Timeouts timeouts();

		Window window();

		Logs logs();
	}


	/**
	 * An interface for managing timeout behavior for WebDriver instances.
	 */
	interface Timeouts
	{
		Timeouts implicitlyWait( long millis );

		Timeouts setScriptTimeout( long millis );

		Timeouts pageLoadTimeout( long millis );
	}


	/**
	 * Used to locate a given frame or window.
	 */
	interface TargetLocator
	{

		boolean frame( int index );

		boolean frame( String nameOrId );

		boolean frame( HtmlElement frameElement );

		void parentFrame();

		void window( String nameOrHandle );

		void defaultContent();

		HtmlElement activeElement();

		Alert alert();
	}


	interface Navigation
	{
		/**
		 * Move back a single "item" in the browser's history.
		 */
		void back();

		/**
		 * Move a single "item" forward in the browser's history. Does nothing if we are on the latest
		 * page viewed.
		 */
		void forward();

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
		void to( String url );

		/**
		 * Overloaded version of {@link #to(String)} that makes it easy to pass in a URL.
		 *
		 * @param url
		 */
		void to( URL url );

		/**
		 * Refresh the current page
		 */
		void refresh();

		History history();
	}


	interface Window
	{

		Dimension getSize();

		void setSize( Dimension targetSize );

		Point getPosition();

		void setPosition( Point targetPosition );

		void maximize();
	}


	/**
	 * The history object contains the URLs visited by the user (within a browser window).
	 * The history object is part of the window object and is accessed through the window.history property.
	 *
	 * Note: There is no public standard that applies to the history object, but all major browsers support it.
	 */
	interface History
	{

		/**
		 * Loads the previous URL in the history list
		 */
		void back();

		/**
		 * Loads the next URL in the history list
		 */
		void forward();

		/**
		 * @return  Returns the number of URLs in the history list
		 */
		int length();

		/**
		 * goes to a specific index of the browser's history.
		 *
		 * @param index the index steps
		 */
		void go( int index );
	}


	/**
	 * A Web Storage is HTML5 response to cookies limits.
	 * While the world is moving forward improving users experience and web potentiality via constantly updated browsers
	 * like Chrome, Firefox, or Safari, some Jurassic user or company could be still stuck at the origins with obsolete,
	 * deprecated, slow, and loads of bugs, browsers.
	 * The aim of this project is to bring at least one of the Web Storage in latter typology of user, or company,
	 * in a way that should simply work with a focused eye about security.
	 *
	 * There are basically two types of Web Storage so far, and these are the localStorage, and the sessionStorage.
	 * The main difference is that the localStorage persists over different tabs or windows,
	 * and even if we close the browser, accordingly with the domain security policy and user choices about quota limit.
	 *
	 */
	interface SessionStorage
	{
		void removeItemFromSessionStorage( String item );

		boolean isItemPresentInSessionStorage( String item );

		public String getItemFromSessionStorage( String key );

		public String getKeyFromSessionStorage( int key );

		public Long getSessionStorageLength();

		public void setItemInSessionStorage( String item, String value );

		public void clearSessionStorage();

		public Set<String> getSessionStorageKeys();
	}

	interface JavaScriptSupport
	{

		//todo: documentation
		String getString( String script, Object... args );

		//todo: documentation
		String[] getStringArray( String script, Object... args );

		//todo: documentation
		Optional<Number> getNumber( String script, Object... args );

		//todo: documentation
		Optional<Boolean> getBoolean( String script, Object... args );

		//todo: documentation
		HtmlElement getHtmlElement( String script, Object... args );

		Map<String,String> getMap( String script, Object... args );

		//todo: documentation
		List<HtmlElement> getHtmlElements( String script, Object... args );

		//todo: documentation
		Boolean isJavaScriptSupported( Class<? extends WebDriver> driverClass );

		Boolean isJQueryEnabled();

		public void injectJQuery();
	}
}
