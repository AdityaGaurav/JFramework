package com.framework.driver.event;

import com.framework.asserts.JAssertion;
import com.framework.driver.utils.ui.HighlightStyle;
import com.framework.driver.utils.ui.screenshots.ScreenshotAndHtmlSource;
import com.google.common.base.Optional;
import org.hamcrest.Matcher;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.internal.Locatable;
import org.openqa.selenium.internal.WrapsElement;

import java.util.List;


/**
 * Represents an HTML element. Generally, all interesting operations to do with interacting with a
 * page will be performed through this interface.
 * <p/>
 * All method calls will do a freshness check to ensure that the element reference is still valid.
 * This essentially determines whether or not the element is still attached to the DOM. If this test
 * fails, then an {@link org.openqa.selenium.StaleElementReferenceException} is thrown, and all
 * future calls to this instance will fail.
 */

@SuppressWarnings ( "UnusedDeclaration" )
public interface HtmlElement extends WrapsElement, WrapsHtmlDriver, Locatable
{
	String getId();

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
	 */
	void click();

	/**
	 * If this current element is a form, or an element within a form, then this will be submitted to
	 * the remote server. If this causes the current page to change, then this method will block until
	 * the new page is loaded.
	 *
	 * @throws org.openqa.selenium.NoSuchElementException If the given element is not within a form
	 */
	void submit();

	/**
	 * Use this method to simulate typing into an element, which may set its value.
	 */
	void sendKeys( CharSequence... keysToSend );

	/**
	 * If this element is a text entry element, this will clear the value. Has no effect on other
	 * elements. Text entry elements are INPUT and TEXTAREA elements.
	 *
	 * Note that the events fired by this event may not be as you'd expect.  In particular, we don't
	 * fire any keyboard or mouse events.  If you want to ensure keyboard events are fired, consider
	 * using something like {@link #sendKeys(CharSequence...)} with the backspace key.  To ensure
	 * you get a change event, consider following with a call to {@link #sendKeys(CharSequence...)}
	 * with the tab key.
	 */
	void clear();

	/**
	 * Get the tag name of this element. <b>Not</b> the value of the name attribute: will return
	 * <code>"input"</code> for the element <code>&lt;input name="foo" /&gt;</code>.
	 *
	 * @return The tag name of this element.
	 */
	String getTagName();

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
	 * 		<li>"class"</li>
	 * 		<li>"readonly"</li>
	 * </ul>
	 *
	 * @param name The name of the attribute.
	 * @return The attribute/property's current value or null if the value is not set.
	 */
	String getAttribute( String name );

	/**
	 * Determine whether or not this element is selected or not. This operation only applies to input
	 * elements such as checkboxes, options in a select and radio buttons.
	 *
	 * @return True if the element is currently selected or checked, false otherwise.
	 */
	boolean isSelected();

	/**
	 * Is the element currently enabled or not? This will generally return true for everything but
	 * disabled input elements.
	 *
	 * @return True if the element is enabled, false otherwise.
	 */
	boolean isEnabled();

	/**
	 * Get the visible (i.e. not hidden by CSS) innerText of this element, including sub-elements,
	 * without any leading or trailing whitespace.
	 *
	 * @return The innerText of this element.
	 */
	String getText();

	List<HtmlElement> findElements( By by );

	HtmlElement findElement( By by );

	/**
	 * Is this element displayed or not? This method avoids the problem of having to parse an
	 * element's "style" attribute.
	 *
	 * @return Whether or not the element is displayed
	 */
	boolean isDisplayed();

	/**
	 * Where on the page is the top left-hand corner of the rendered element?
	 *
	 * @return A point, containing the location of the top left-hand corner of the element
	 */
	Point getLocation();

	/**
	 * What is the width and height of the rendered element?
	 *
	 * @return The size of the element on the page.
	 */
	Dimension getSize();

	/**
	 * Get the value of a given CSS property.
	 * Color values should be returned as rgba strings, so,
	 * for example if the "background-color" property is set as "green" in the
	 * HTML source, the returned value will be "rgba(0, 255, 0, 1)".
	 *
	 * Note that shorthand CSS properties (e.g. background, font, border, border-top, margin,
	 * margin-top, padding, padding-top, list-style, outline, pause, cue) are not returned,
	 * in accordance with the
	 * <a href="http://www.w3.org/TR/DOM-Level-2-Style/css.html#CSS-CSSStyleDeclaration">DOM CSS2 specification</a>
	 * - you should directly access the longhand properties (e.g. background-color) to access the
	 * desired values.
	 *
	 * @return The current, computed value of the property.
	 *
	 * @see com.framework.utils.web.CSS2Properties
	 */
	String getCssValue( String propertyName );

	/**
	 * The blur() method is used to remove focus from an element.
	 */
	void blur();

	Optional<ScreenshotAndHtmlSource> captureBitmap();

	/**
	 * The user-facing API for emulating complex user gestures. Use this class rather than using the
	 * Keyboard or Mouse directly.
	 *
	 * Implements the builder pattern: Builds a {@link org.openqa.selenium.interactions.CompositeAction}
	 * containing all actions specified by the method calls.
	 *
	 *
	 * @return an {@link org.openqa.selenium.interactions.Actions} builder object
	 *
	 * @see org.openqa.selenium.interactions.Actions
	 * @see org.openqa.selenium.interactions.HasInputDevices
	 * @see org.openqa.selenium.interactions.MoveToOffsetAction
	 * @see org.openqa.selenium.interactions.CompositeAction
	 * @see org.openqa.selenium.interactions.KeyDownAction
	 * @see org.openqa.selenium.interactions.KeyUpAction
	 * @see org.openqa.selenium.interactions.SendKeysAction
	 * @see org.openqa.selenium.interactions.ClickAndHoldAction
	 * @see org.openqa.selenium.interactions.ButtonReleaseAction
	 * @see org.openqa.selenium.interactions.ClickAction
	 * @see org.openqa.selenium.interactions.DoubleClickAction
	 * @see org.openqa.selenium.interactions.ContextClickAction
	 */
	Actions createAction();

	/**
	 * uses jquery functions fadeIn and FadeOut
	 * the fadeOut functions Hide the matched elements by fading them to transparent.
	 * while the fadeIn function Display the matched elements by fading them to opaque.
	 */
	public void blink();

	public void mark( HighlightStyle style );

	/**
	 * Gets coordinates on the element relative to the top-left corner of OS-window being used
	 * to display the content. Usually it is the browser window's viewport. This method automatically
	 * scrolls the page and/or frames to make element visible in viewport before calculating its coordinates.
	 *
	 * @throws org.openqa.selenium.ElementNotVisibleException if the element can't be scrolled into view.
	 */
	void scrollIntoView();

	/**
	 * Scrolls the current element into the visible area of the browser window.
	 *
	 * @param alignToTop  If {@code true}, the top of the element will be aligned to the top of the visible
	 *                    area of the scrollable ancestor.
	 *                    If {@code false}, the bottom of the element will be aligned to the bottom of the
	 *                    visible area of the scrollable ancestor.
	 */
	void scrollIntoView( boolean alignToTop );

	/**
	 * The scrollBy() method scrolls the document by the specified number of pixels.
	 *
	 * @param xNum  How many pixels to scroll by, along the x-axis (horizontal).
	 *              Positive values will scroll to the left, while negative values will scroll to the right
	 * @param yNum  How many pixels to scroll by, along the y-axis (vertical).
	 *              Positive values will scroll down, while negative values scroll up
	 */
	void scrollBy( int xNum, int yNum );

	/**
	 * Give focus to an element.
	 * The focus() method is used to give focus to an element (if it can be focused).
	 *
	 * @see #blur()
	 */
	void focus();

	String getLocator();

	boolean hasAttribute( final String name );

	/**
	 * The hover() method specifies two functions to run when the mouse pointer hovers over the selected elements.
	 * This method triggers both the {@code mouseenter} and {@code mouseleave} events.
	 *
	 * @see org.openqa.selenium.interactions.MoveMouseAction
	 * @see Actions#moveToElement(org.openqa.selenium.WebElement)
	 */
	void hover();

	void jsDoubleClick();

	void jsClick();

	JAssertion createAssertion();

	Optional<HtmlElement> childExists( final By by );

	Optional<HtmlElement> childExists( final By locator, long timeoutSeconds );

	Optional<List<HtmlElement>> allChildrenExists( final By by );

	Optional<List<HtmlElement>> allChildrenExists( final By by, long timeoutSeconds );

	Boolean waitToBeDisplayed( final boolean visible, long timeoutSeconds );

	Boolean waitToBeEnabled( final boolean enabled, long timeoutSeconds );

	Boolean waitToBeSelected( final boolean selected, long timeoutSeconds );

	Boolean waitAttributeToMatch( final String attributeName, final Matcher<String> matcher, long timeoutSeconds  );

	Boolean waitCssPropertyToMatch( final String cssProperty, final Matcher<String> matcher, long timeoutSeconds  );

	Boolean waitTextToMatch( final Matcher<String> matcher, long timeoutSeconds  );
}
