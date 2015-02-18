package com.framework.driver.objects;

import com.framework.driver.event.HtmlDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.driver.objects
 *
 * Name   : PageObject
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-01-05
 *
 * Time   : 12:35
 */

public interface PageObject
{
	/**
	 * @return the page logical name provided by extending page class
	 */
	String getLogicalName();

	/**
	 * Get a string representing the current URL that the browser is looking at.
	 *
	 * @return The URL of the page currently loaded in the browser
	 *
	 * @see org.openqa.selenium.WebDriver#getCurrentUrl()
	 */
	String getCurrentUrl();

	/**
	 * a URL instance based on {@linkplain #getCurrentUrl()}
	 * this is useful for tests that's want to verify url protocols, queries and paths.
	 *
	 * @return the current ulr in {@link URL} format.
	 *
	 * @throws MalformedURLException in case that the url is invalid
	 *
	 * @see java.net.URL#getRef()
	 * @see java.net.URL#getQuery()
	 * @see java.net.URL#getPath()
	 * @see java.net.URL#getHost()
	 */
	URL getURL() throws MalformedURLException;

	/**
	 * The title of the current page.
	 *
	 * @return The title of the current page, with leading and trailing whitespace stripped, or {@code null}
	 *         if one is not already set
	 *
	 * @see org.openqa.selenium.WebDriver#getTitle()
	 */
	String getTitle();

	/**
	 * Set the current vertical position of the scroll bar for each of the set of matched elements.
	 */
	void scrollToTop();

	/**
	 * Scrolls to a particular set of coordinates in the document.
	 *
	 * @param x   x-coord is the pixel along the horizontal axis of the document that you want displayed in the upper left
	 * @param y   y-coord is the pixel along the vertical axis of the document that you want displayed in the upper left.
	 *
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/Window.scrollTo">scrollTo</a>
	 */
	void scrollTo( long x, long y );

	/**
	 * Return an opaque handle to this window that uniquely identifies it within this driver instance.
	 * This can be used to switch to this window at a later date
	 */
	String getWindowHandle();

	/**
	 * Return a set of window handles which can be used to iterate over all open windows of this
	 * WebDriver instance by passing them to {@link #switchTo()}.{@link HtmlDriver.Options#window()}
	 *
	 * @return A set of window handles which can be used to iterate over all open windows.
	 */
	Set<String> getWindowHandles();

	/**
	 * Send future commands to a different frame or window.
	 *
	 * @return A TargetLocator which can be used to select a frame or window
	 *
	 * @see org.openqa.selenium.WebDriver.TargetLocator
	 */
	HtmlDriver.TargetLocator switchTo();

	/**
	 * An abstraction allowing the driver to access the browser's history and to navigate to a given URL.
	 *
	 * @return A {@link org.openqa.selenium.WebDriver.Navigation} that allows the selection of what to
	 *         do next
	 */
	HtmlDriver.Navigation navigate();

	/**
	 * Gets the Option interface
	 *
	 * @return An option interface
	 *
	 * @see org.openqa.selenium.WebDriver.Options
	 */
	HtmlDriver.Options manage();

	/**
	 * Provides {@link com.framework.driver.event.HtmlDriver.History} object to tests.
	 *
	 * @return an instance of {@code History} object.
	 */
	HtmlDriver.History history();

	/**
	 * Provides {@link com.framework.driver.event.HtmlDriver.SessionStorage} object to tests.
	 *
	 * @return an instance of {@code SessionStorage} object.
	 */
	HtmlDriver.SessionStorage sessionStorage();

	/**
	 * An abstraction allowing the driver to access the browser window dimensions
	 * includes the option to set a window size, maximize and change window location.
	 *
	 * @return A {@link org.openqa.selenium.WebDriver.Navigation} that allows the selection of what to
	 *         do next
	 */
	HtmlDriver.Window window();

	/**
	 * the value is created on the class constructor using the pattern PAGE[%d], where %d
	 * id the static variable {@linkplain com.framework.driver.objects.AbstractPageObject#counter}
	 * this field is most used for debug proposals.
	 *
	 * @return a unique qualifier of the page instance.
	 */
	String getQualifier();

	/**
	 * assure that the new window GETS focus (send the new window to the front):
	 */
	void setWindowFocus();
}
