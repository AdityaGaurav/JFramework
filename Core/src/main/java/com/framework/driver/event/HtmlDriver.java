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

	void get( String url );

	String getCurrentUrl();

	String getTitle();

	List<HtmlElement> findElements( By by );

	HtmlElement findElement( By by );

	Optional<HtmlElement> elementExists( final By by );

	Optional<List<HtmlElement>> allElementExists( final By by );

	String getPageSource();

	void close();

	void quit();

	Set<String> getWindowHandles();

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

		void back();

		void forward();

		void to( String url );

		void to( URL url );

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


	interface History
	{

		void back();

		void forward();

		int length();

		void go( int index );

		void pushState();

		void replaceState();
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
