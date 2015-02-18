package com.framework.driver.event;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.internal.Coordinates;

import java.util.concurrent.TimeUnit;


/**
 * Customized EventListener class.
 * added different event listeners and modified listeners to maximize logger endTest error handling
 *
 * @author Dani.Vainstein
 *
 * @see org.openqa.selenium.support.events.WebDriverEventListener
 * @see org.openqa.selenium.support.events.AbstractWebDriverEventListener
 */

public interface EventListener
{

	//region EventListener - TimeOut interface events

	/**
	 * Called before {@link org.openqa.selenium.WebDriver.Timeouts#setScriptTimeout(long, java.util.concurrent.TimeUnit)}
	 *
	 * @param driver 	a wrapped {@linkplain org.openqa.selenium.WebDriver} to avoid activate listener while logging.
	 * @param time      The timeout value set to script timeout.
	 * @param unit      The unit of time set to the method.
	 *
	 * @see org.openqa.selenium.WebDriver.Timeouts
	 */
	void beforeSetScriptTimeout( WebDriver driver, long time, TimeUnit unit );

	/**
	 * Called after {@link org.openqa.selenium.WebDriver.Timeouts#setScriptTimeout(long, java.util.concurrent.TimeUnit)}
	 *
	 * @param time      The timeout value set to script timeout.
	 * @param unit      The unit of time set to the method.
	 *
	 * @see org.openqa.selenium.WebDriver.Timeouts
	 */
	void afterSetScriptTimeout( long time, TimeUnit unit );

	/**
	 * Called before {@link org.openqa.selenium.WebDriver.Timeouts#pageLoadTimeout(long, java.util.concurrent.TimeUnit)}
	 *
	 * @param driver 	a wrapped {@linkplain org.openqa.selenium.WebDriver} to avoid activate listener while logging.
	 * @param time      The timeout value set to page load timeout.
	 * @param unit      The unit of time set to the method.
	 *
	 * @see org.openqa.selenium.WebDriver.Timeouts
	 */
	void beforePageLoadTimeout( WebDriver driver, long time, TimeUnit unit );

	/**
	 * Called after {@link org.openqa.selenium.WebDriver.Timeouts#pageLoadTimeout(long, java.util.concurrent.TimeUnit)}
	 *
	 * @param time      The timeout value set to page load timeout.
	 * @param unit      The unit of time set to the method.
	 *
	 * @see org.openqa.selenium.WebDriver.Timeouts
	 */
	void afterPageLoadTimeout( long time, TimeUnit unit );

	/**
	 * Called before {@link org.openqa.selenium.WebDriver.Timeouts#implicitlyWait(long, java.util.concurrent.TimeUnit)}
	 *
	 * @param driver 	a wrapped {@linkplain org.openqa.selenium.WebDriver} to avoid activate listener while logging.
	 * @param time      The timeout value set to implicitly wait.
	 * @param unit      The unit of time set to the method.
	 *
	 * @see org.openqa.selenium.WebDriver.Timeouts
	 */
	void beforeImplicitlyWait( WebDriver driver, long time, TimeUnit unit );

	/**
	 * Called after {@link org.openqa.selenium.WebDriver.Timeouts#implicitlyWait(long, java.util.concurrent.TimeUnit)}
	 *
	 * @param time      The timeout value set to implicitly wait.
	 * @param unit      The unit of time set to the method.
	 *
	 * @see org.openqa.selenium.WebDriver.Timeouts
	 */
	void afterImplicitlyWait( long time, TimeUnit unit );

	//endregion


	//region EventListener - Window interface events

	/**
	 * Called before {@link org.openqa.selenium.WebDriver.Window#setSize(org.openqa.selenium.Dimension)}
	 *
	 * @param driver 		a wrapped {@linkplain org.openqa.selenium.WebDriver} to avoid activate listener while logging.
	 * @param targetSize    The target size to set.
	 *
	 * @see org.openqa.selenium.WebDriver.Window
	 * @see org.openqa.selenium.Dimension
	 * @see org.openqa.selenium.WebDriver.Options
	 */
	void beforeSetWindowSize( WebDriver driver, Dimension targetSize );

	/**
	 * Called after {@link org.openqa.selenium.WebDriver.Window#setSize(org.openqa.selenium.Dimension)}
	 *
	 * @param driver 		a wrapped {@linkplain org.openqa.selenium.WebDriver} to avoid activate listener while logging.
	 *
	 * @see org.openqa.selenium.WebDriver.Window
	 * @see org.openqa.selenium.WebDriver.Options
	 * @see org.openqa.selenium.Dimension
	 */
	void afterSetWindowSize( WebDriver driver );

	/**
	 * Called before {@link org.openqa.selenium.WebDriver.Window#setPosition(org.openqa.selenium.Point)}
	 *
	 * @param driver 			a wrapped {@linkplain org.openqa.selenium.WebDriver} to avoid activate listener while logging.
	 * @param targetLocation    The target position of the window.
	 *
	 * @see org.openqa.selenium.WebDriver.Window
	 * @see org.openqa.selenium.Dimension
	 * @see org.openqa.selenium.WebDriver.Options
	 */
	void beforeSetWindowPosition( WebDriver driver, Point targetLocation );

	/**
	 * Called after {@link org.openqa.selenium.WebDriver.Window#setPosition(org.openqa.selenium.Point)}
	 *
	 * @param driver 			a wrapped {@linkplain org.openqa.selenium.WebDriver} to avoid activate listener while logging.
	 *
	 * @see org.openqa.selenium.WebDriver.Window
	 * @see org.openqa.selenium.Point
	 * @see org.openqa.selenium.WebDriver.Options
	 */
	void afterSetWindowPosition( WebDriver driver );

	/**
	 * Called before {@link org.openqa.selenium.WebDriver.Window#maximize()}
	 *
	 * @param driver	a wrapped {@linkplain org.openqa.selenium.WebDriver} to avoid activate listener while logging.
	 *
	 * @see org.openqa.selenium.WebDriver.Window
	 * @see org.openqa.selenium.WebDriver.Options
	 */
	void beforeMaximize( WebDriver driver );

	/**
	 * Called after {@link org.openqa.selenium.WebDriver.Window#maximize()}
	 *
	 * @param driver	a wrapped {@linkplain org.openqa.selenium.WebDriver} to avoid activate listener while logging.
	 *
	 * @see org.openqa.selenium.WebDriver.Window
	 * @see org.openqa.selenium.WebDriver.Options
	 */
	void afterMaximize( WebDriver driver );

	//endregion


	//region EventListener - Cookies events

	/**
	 * Called before {@link org.openqa.selenium.WebDriver.Options#deleteAllCookies()}
	 *
	 * @param driver a wrapped {@linkplain org.openqa.selenium.WebDriver} to avoid activate listener while logging.
	 *
	 * @see org.openqa.selenium.WebDriver.Options
	 * @see org.openqa.selenium.Cookie
	 */
	void beforeDeleteAllCookies( WebDriver driver );

	/**
	 * Called after {@link org.openqa.selenium.WebDriver.Options#deleteAllCookies()}
	 *
	 * @param driver a wrapped {@linkplain org.openqa.selenium.WebDriver} to avoid activate listener while logging.
	 *
	 * @see org.openqa.selenium.WebDriver.Options
	 * @see org.openqa.selenium.Cookie
	 */
	void afterDeleteAllCookies( WebDriver driver );

	/**
	 * Called before {@link org.openqa.selenium.WebDriver.Options#deleteCookie(org.openqa.selenium.Cookie)} ()}
	 *
	 * @param driver @param driver a wrapped {@linkplain org.openqa.selenium.WebDriver} to avoid activate listener while logging.
	 * @param cookie the {@link org.openqa.selenium.Cookie} instance to be deleted.
	 *
	 * @see org.openqa.selenium.WebDriver.Options
	 * @see org.openqa.selenium.Cookie
	 */
	void beforeDeleteCookie( WebDriver driver, Cookie cookie );

	/**
	 * Called after {@link org.openqa.selenium.WebDriver.Options#deleteCookie(org.openqa.selenium.Cookie)} ()}
	 *
	 * @param driver @param driver a wrapped {@linkplain org.openqa.selenium.WebDriver} to avoid activate listener while logging.
	 * @param cookie the {@link org.openqa.selenium.Cookie} instance to be deleted.
	 *
	 * @see org.openqa.selenium.WebDriver.Options
	 * @see org.openqa.selenium.Cookie
	 */
	void afterDeleteCookie( WebDriver driver, Cookie cookie );

	/**
	 * Called before {@link org.openqa.selenium.WebDriver.Options#deleteCookie(org.openqa.selenium.Cookie)} ()}
	 *
	 * @param driver 	 a wrapped {@linkplain org.openqa.selenium.WebDriver} to avoid activate listener while logging.
	 * @param cookieName the name of the cookie to be removed.
	 *
	 * @see org.openqa.selenium.WebDriver.Options
	 * @see org.openqa.selenium.Cookie
	 */
	void beforeDeleteCookie( WebDriver driver, String cookieName );

	/**
	 * Called after {@link org.openqa.selenium.WebDriver.Options#deleteCookie(org.openqa.selenium.Cookie)} ()}
	 *
	 * @param driver 	 a wrapped {@linkplain org.openqa.selenium.WebDriver} to avoid activate listener while logging.
	 * @param cookieName the name of the cookie to be removed.
	 *
	 * @see org.openqa.selenium.WebDriver.Options
	 * @see org.openqa.selenium.Cookie
	 */
	void afterDeleteCookie( WebDriver driver, String cookieName );

	/**
	 * Called before {@link org.openqa.selenium.WebDriver.Options#addCookie(org.openqa.selenium.Cookie)} ()}
	 *
	 * @param driver 	a wrapped {@linkplain org.openqa.selenium.WebDriver} to avoid activate listener while logging.
	 * @param cookie 	the name of the cookie to be removed.
	 *
	 * @see org.openqa.selenium.WebDriver.Options
	 * @see org.openqa.selenium.Cookie
	 */
	void beforeAddCookie( WebDriver driver, Cookie cookie );

	/**
	 * Called after {@link org.openqa.selenium.WebDriver.Options#addCookie(org.openqa.selenium.Cookie)} ()}
	 *
	 * @param driver 	a wrapped {@linkplain org.openqa.selenium.WebDriver} to avoid activate listener while logging.
	 * @param cookie 	the name of the cookie to be removed.
	 *
	 * @see org.openqa.selenium.WebDriver.Options
	 * @see org.openqa.selenium.Cookie
	 */
	void afterAddCookie( WebDriver driver, Cookie cookie );

	//endregion


	//region EventListener - TargetLocator events

	/**
	 * Called before {@link org.openqa.selenium.WebDriver.TargetLocator#frame(int)}
	 *
	 * @param driver       a wrapped {@linkplain org.openqa.selenium.WebDriver} to avoid activate listener while logging.
	 * @param frameIndex   (zero-based) index.
	 *
	 * @see org.openqa.selenium.WebDriver.TargetLocator
	 */
	void beforeSwitchToFrame( WebDriver driver, int frameIndex );

	/**
	 * Called after {@link org.openqa.selenium.WebDriver.TargetLocator#frame(int)}
	 *
	 * @param driver        a wrapped {@linkplain org.openqa.selenium.WebDriver} to avoid activate listener while logging.
	 * @param switched		the switched instance of {@code WebDriver}
	 * @param frameIndex    (zero-based) index.
	 *
	 * @see org.openqa.selenium.WebDriver.TargetLocator
	 */
	void afterSwitchToFrame( WebDriver driver, WebDriver switched, int frameIndex );

	/**
	 * Called before {@link org.openqa.selenium.WebDriver.TargetLocator#frame(String)}
	 *
	 * @param driver	a wrapped {@linkplain org.openqa.selenium.WebDriver} to avoid activate listener while logging.
	 * @param name   	the name of the frame window, the id of the &lt;frame&gt; or &lt;iframe&gt; element.
	 *
	 * @see org.openqa.selenium.WebDriver.TargetLocator
	 */
	void beforeSwitchToFrame( WebDriver driver, String name );

	/**
	 * Called after {@link org.openqa.selenium.WebDriver.TargetLocator#frame(int)}
	 *
	 * @param driver        a wrapped {@linkplain org.openqa.selenium.WebDriver} to avoid activate listener while logging.
	 * @param switched		the switched instance of {@code WebDriver}
	 * @param name    		the name of the frame window, the id of the &lt;frame&gt; or &lt;iframe&gt; element.
	 *
	 * @see org.openqa.selenium.WebDriver.TargetLocator
	 */
	void afterSwitchToFrame( WebDriver driver, WebDriver switched, String name );

	/**
	 * Called before {@link org.openqa.selenium.WebDriver.TargetLocator#frame(org.openqa.selenium.WebElement)}
	 *
	 * @param driver	a wrapped {@linkplain org.openqa.selenium.WebDriver} to avoid activate listener while logging.
	 * @param element  	The frame element to switch to.
	 *
	 * @see org.openqa.selenium.WebDriver.TargetLocator
	 */
	void beforeSwitchToFrame( WebDriver driver, WebElement element );

	/**
	 * Called after {@link org.openqa.selenium.WebDriver.TargetLocator#frame(int)}
	 *
	 * @param driver        a wrapped {@linkplain org.openqa.selenium.WebDriver} to avoid activate listener while logging.
	 * @param switched		the switched instance of {@code WebDriver}
	 * @param element    	The switched frame element.
	 *
	 * @see org.openqa.selenium.WebDriver.TargetLocator
	 */
	void afterSwitchToFrame( WebDriver driver, WebDriver switched, WebElement element );

	/**
	 * Called before {@link org.openqa.selenium.WebDriver.TargetLocator#frame(org.openqa.selenium.WebElement)}
	 *
	 * @param driver	a wrapped {@linkplain org.openqa.selenium.WebDriver} to avoid activate listener while logging.
	 *
	 * @see org.openqa.selenium.WebDriver.TargetLocator
	 */
	void beforeSwitchToParentFrame( WebDriver driver );

	/**
	 * Called after {@link org.openqa.selenium.WebDriver.TargetLocator#frame(org.openqa.selenium.WebElement)}
	 *
	 * @param driver	a wrapped {@linkplain org.openqa.selenium.WebDriver} to avoid activate listener while logging.
	 * @param switched	the switched instance of {@code WebDriver}
	 *
	 * @see org.openqa.selenium.WebDriver.TargetLocator
	 */
	void afterSwitchToParentFrame( WebDriver driver, WebDriver switched );

	/**
	 * Called before {@link org.openqa.selenium.WebDriver.TargetLocator#window(String)}
	 *
	 * @param driver		a wrapped {@linkplain org.openqa.selenium.WebDriver} to avoid activate listener while logging.
	 * @param windowName    The name of the window or the handle as returned by {@link org.openqa.selenium.WebDriver#getWindowHandle()}
	 *
	 * @see org.openqa.selenium.WebDriver.TargetLocator
	 */
	void beforeSwitchToWindow( WebDriver driver, String windowName );

	/**
	 * Called after {@link org.openqa.selenium.WebDriver.TargetLocator#window(String)}
	 *
	 * @param driver		a wrapped {@linkplain org.openqa.selenium.WebDriver} to avoid activate listener while logging.
	 * @param windowName    The name of the window or the handle as returned by {@link org.openqa.selenium.WebDriver#getWindowHandle()}
	 * @param switched		the switched instance of {@code WebDriver}
	 *
	 * @see org.openqa.selenium.WebDriver.TargetLocator
	 */
	void afterSwitchToWindow( WebDriver driver, WebDriver switched, String windowName );

	/**
	 * Called before {@link org.openqa.selenium.WebDriver.TargetLocator#defaultContent()}
	 *
	 * @param driver		a wrapped {@linkplain org.openqa.selenium.WebDriver} to avoid activate listener while logging.
	 *
	 * @see org.openqa.selenium.WebDriver.TargetLocator
	 */
	void beforeSwitchToDefaultContent( WebDriver driver );

	/**
	 * Called after {@link org.openqa.selenium.WebDriver.TargetLocator#defaultContent()}
	 *
	 * @param driver		a wrapped {@linkplain org.openqa.selenium.WebDriver} to avoid activate listener while logging.
	 * @param switched		the switched instance of {@code WebDriver}
	 *
	 * @see org.openqa.selenium.WebDriver.TargetLocator
	 */
	void afterSwitchToDefaultContent( WebDriver driver, WebDriver switched );

	/**
	 * Called before {@link org.openqa.selenium.WebDriver.TargetLocator#activeElement()} ()}
	 *
	 * @param driver		a wrapped {@linkplain org.openqa.selenium.WebDriver} to avoid activate listener while logging.
	 *
	 * @see org.openqa.selenium.WebDriver.TargetLocator
	 */
	void beforeSwitchToActiveElement( WebDriver driver );

	/**
	 * Called before {@link org.openqa.selenium.WebDriver.TargetLocator#activeElement()} ()}
	 *
	 * @param driver	a wrapped {@linkplain org.openqa.selenium.WebDriver} to avoid activate listener while logging.
	 * @param element	the switched {@code WebElement}
	 *
	 * @see org.openqa.selenium.WebDriver.TargetLocator
	 */
	void afterSwitchToActiveElement( WebDriver driver, WebElement element );

	//endregion


	//region EventListener - Navigation events

	//todo: documentation
	void beforeClose( WebDriver driver );

	//todo: documentation
	void afterClose( WebDriver driver );

	//todo: documentation
	void beforeQuit( WebDriver driver );

	//todo: documentation
	void afterQuit();

	//todo: documentation
	void beforeRefresh( WebDriver driver );

	//todo: documentation
	void afterRefresh( WebDriver driver );

	/**
	 * Called before {@link org.openqa.selenium.WebDriver#get get(String url)} respectively
	 * {@link org.openqa.selenium.WebDriver.Navigation#to navigate().to(String url)}.
	 *
	 * @param url    The destination url to navigate
	 * @param driver The {@linkplain org.openqa.selenium.WebDriver} instance
	 *
	 * @see org.openqa.selenium.WebDriver.Navigation#to(String)
	 */
	void beforeNavigateTo( String url, WebDriver driver );

	/**
	 * Called after {@link org.openqa.selenium.WebDriver#get get(String url)} respectively
	 * {@link org.openqa.selenium.WebDriver.Navigation#to navigate().to(String url)}. Not called, if an
	 * exception is thrown.
	 */
	void afterNavigateTo( String url, WebDriver driver );

	/**
	 * Called before {@link org.openqa.selenium.WebDriver.Navigation#back navigate().back()}.
	 */
	void beforeNavigateBack( WebDriver driver );

	/**
	 * Called after {@link org.openqa.selenium.WebDriver.Navigation navigate().back()}. Not called, if an
	 * exception is thrown.
	 */
	void afterNavigateBack( WebDriver driver );

	/**
	 * Called before {@link org.openqa.selenium.WebDriver.Navigation#forward navigate().forward()}.
	 */
	void beforeNavigateForward( WebDriver driver );

	/**
	 * Called after {@link org.openqa.selenium.WebDriver.Navigation#forward navigate().forward()}. Not called,
	 * if an exception is thrown.
	 */
	void afterNavigateForward( WebDriver driver );

	//endregion

	/**
	 * Called before
	 * {@link org.openqa.selenium.WebElement#findElement(org.openqa.selenium.By)}, or
	 * {@link org.openqa.selenium.WebElement#findElements(org.openqa.selenium.By)}.
	 * <p/>
	 * This find method called from <code>Webelement</code> only.
	 *
	 * @param by     	the {@link org.openqa.selenium.By} locator criteria
	 * @param element   the {@code WebElement} reference search from.
	 * @param driver    the reference web-driver
	 *
	 * @see ExtendedBy
	 * @see org.openqa.selenium.By
	 * @see #beforeFindBy(org.openqa.selenium.By, org.openqa.selenium.WebDriver)
	 * @see #afterFindBy(org.openqa.selenium.By, org.openqa.selenium.WebElement, org.openqa.selenium.WebDriver)
	 */
	void beforeFindBy( final By by, final WebElement element, final WebDriver driver );

	/**
	 * Called before {@link org.openqa.selenium.WebDriver#findElement WebDriver.findElement(...)}, or
	 * {@link org.openqa.selenium.WebDriver#findElements WebDriver.findElements(...)}
	 * <P>
	 *    This find method called from <code>WebDriver</code> only.
	 * </P>
	 *
	 * @param by     	the {@link org.openqa.selenium.By} locator criteria
	 * @param driver    the reference web-driver
	 *
	 * @see ExtendedBy
	 * @see org.openqa.selenium.By
	 * @see #beforeFindBy(org.openqa.selenium.By, org.openqa.selenium.WebElement, org.openqa.selenium.WebDriver)
	 * @see #afterFindBy(org.openqa.selenium.By, org.openqa.selenium.WebDriver)
	 */
	void beforeFindBy( final By by, WebDriver driver );

	/**
	 * Called after {@link org.openqa.selenium.WebDriver#findElement WebDriver.findElement(...)}, or
	 * {@link org.openqa.selenium.WebDriver#findElements WebDriver.findElements(...)}
	 * <p/>
	 *    This find method called from <code>WebDriver</code> only.
	 *
	 * @param by       the {@link org.openqa.selenium.By} locator criteria
	 * @param driver   the reference web-driver
	 *
	 * @see ExtendedBy
	 * @see org.openqa.selenium.By
	 * @see #afterFindBy(org.openqa.selenium.By, org.openqa.selenium.WebElement, org.openqa.selenium.WebDriver)
	 * @see #beforeFindBy(org.openqa.selenium.By, org.openqa.selenium.WebDriver)
	 */
	void afterFindBy( final By by, WebDriver driver );

	/**
	 * Called before
	 * {@link org.openqa.selenium.WebElement#findElement(org.openqa.selenium.By)}, or
	 * {@link org.openqa.selenium.WebElement#findElements(org.openqa.selenium.By)}.
	 *
	 * @param by     	the {@link org.openqa.selenium.By} locator criteria
	 * @param element   the {@code WebElement} reference search from.
	 * @param driver    the reference web-driver
	 *
	 * @see ExtendedBy
	 * @see org.openqa.selenium.By
	 * @see #beforeFindBy(org.openqa.selenium.By, org.openqa.selenium.WebElement, org.openqa.selenium.WebDriver)
	 * @see #afterFindBy(org.openqa.selenium.By, org.openqa.selenium.WebDriver)
	 */
	void afterFindBy( final By by, WebElement element, WebDriver driver );

	//todo: documentation
	void afterFindBy( final By by, WebElement element, int size, WebDriver driver );

	//todo: documentation
	void afterFindBy( final By by, int size, WebDriver driver );

	/**
	 * Called before {@link org.openqa.selenium.remote.RemoteWebDriver#executeScript(String, Object[]) }
	 */
	void beforeScript( final String script, final WebDriver driver );

	/**
	 * Called after {@link org.openqa.selenium.remote.RemoteWebDriver#executeScript(String, Object[]) }.
	 * Not called if an exception is thrown
	 */
	void afterScript( final WebDriver driver );

	//todo: documentation
	void beforeActionOn( WebElement element, WebDriver driver, String action );

	//todo: documentation
	void afterActionOn( WebElement element, WebDriver driver, String action );

	/**
	 * Called before {@link org.openqa.selenium.WebElement#click WebElement.click()}.
	 */
	void beforeClickOn( WebElement element, WebDriver driver );

	/**
	 * Called after {@link org.openqa.selenium.WebElement#click WebElement.click()}. Not called, if an exception is
	 * thrown.
	 */
	void afterClickOn( WebElement element, WebDriver driver );

	/**
	 * Called before {@link org.openqa.selenium.WebElement#clear WebElement.clear()},
	 * {@link org.openqa.selenium.WebElement#sendKeys(CharSequence...)}
	 */
	void beforeChangeValueOf( WebElement element, WebDriver driver );

	/**
	 * Called after {@link org.openqa.selenium.WebElement#clear WebElement.clear()},
	 * {@link org.openqa.selenium.WebElement#sendKeys(CharSequence...)}.
	 * Not called, if an exception is thrown.
	 */
	void afterChangeValueOf( WebElement element, WebDriver driver );

	void beforeSubmitOn( WebElement element, WebDriver driver );

	//todo: documentation
	void afterSubmitOn( WebElement element, WebDriver driver );

	//todo: documentation
	void beforeHoverOn( WebElement element, WebDriver driver );

	//todo: documentation
	void afterHoverOn( WebElement element, WebDriver driver );

	//todo: documentation
	void beforeHoverOn( WebElement element, WebDriver driver, int xOffset, int yOffset );

	//todo: documentation
	void afterHoverOn( WebElement element, WebDriver driver, int xOffset, int yOffset );

	//todo: documentation
	void beforeBlurFrom( WebElement element, WebDriver driver );

	//todo: documentation
	void afterBlurFrom( WebElement element, WebDriver driver );

	//todo: documentation
	void beforeFocusOn( WebElement element, WebDriver driver );

	//todo: documentation
	void afterFocusOn( WebElement element, WebDriver driver );

	//todo: documentation
	void beforeGetAttributeValueOf( WebElement element, WebDriver driver, String attributeName );

	//todo: documentation
	void beforeGetCssPropertyValueOf( WebElement element, WebDriver driver, String propertyName );

	//todo: documentation
	void beforeKeyboardAction( WebDriver driver, String action, CharSequence sequence );

	//todo: documentation
	void afterKeyboardAction( WebDriver driver, String action, CharSequence sequence );

	//todo: documentation
	void beforeMouseAction( WebDriver driver, String action, Coordinates coordinates );

	//todo: documentation
	void afterMouseAction( WebDriver driver, String action, Coordinates coordinates );

	/**
	 * Called whenever an exception would be thrown.
	 */
	void onException( Throwable throwable, WebDriver driver );
}
