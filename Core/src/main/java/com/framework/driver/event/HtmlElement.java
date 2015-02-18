package com.framework.driver.event;

import com.framework.asserts.JAssertion;
import com.framework.driver.utils.ui.HighlightStyle;
import com.framework.testing.steping.screenshots.ScreenshotAndHtmlSource;
import com.google.common.base.Optional;
import org.hamcrest.Matcher;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.internal.Locatable;
import org.openqa.selenium.internal.WrapsElement;

import java.io.IOException;
import java.util.List;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.driver.event
 *
 * Name   : HtmlElement 
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-02-04 
 *
 * Time   : 22:15
 *
 */

public interface HtmlElement extends WrapsElement, WrapsHtmlDriver, Locatable
{
	String getId();

	void click();

	void submit();

	void sendKeys( CharSequence... keysToSend );

	void clear();

	String getTagName();

	String getAttribute( String name );

	boolean isSelected();

	boolean isEnabled();

	String getText();

	List<HtmlElement> findElements( By by );

	HtmlElement findElement( By by );

	boolean isDisplayed();

	Point getLocation();

	Dimension getSize();

	String getCssValue( String propertyName );

	void blur();

	Optional<ScreenshotAndHtmlSource> captureBitmap() throws IOException;

	void contextClick();

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

	void doubleClick();

	public void blink();

	public void mark( HighlightStyle style );

	void scrollIntoView();

	/**
	 * The scrollBy() method scrolls the document by the specified number of pixels.
	 *
	 * @param xNum  How many pixels to scroll by, along the x-axis (horizontal).
	 *              Positive values will scroll to the left, while negative values will scroll to the right
	 * @param yNum  How many pixels to scroll by, along the y-axis (vertical).
	 *              Positive values will scroll down, while negative values scroll up
	 */
	void scrollBy( int xNum, int yNum );

	void focus();

	String getLocator();

	boolean hasAttribute( final String name );

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

	HtmlElement parent();
}
