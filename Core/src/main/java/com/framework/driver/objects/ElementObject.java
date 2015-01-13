package com.framework.driver.objects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.internal.WrapsElement;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.driver.objects
 *
 * Name   : ElementObject
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-01-08
 *
 * Time   : 15:13
 */

public interface ElementObject extends WebElement, WrapsElement
{
	Action createAction();

	/**
	 * Removes focus from input; keystrokes will subsequently go nowhere.
	 *
	 * @see org.openqa.selenium.JavascriptExecutor
	 * @see <a href="https://developer.mozilla.org/en/docs/Web/API/HTMLInputElement">HTMLInputElement</a>
	 */
	void blur();

	/**
	 * The user-facing API for emulating complex user gestures.
	 * Hovers over a {@linkplain org.openqa.selenium.WebElement}
	 *
	 * @see org.openqa.selenium.interactions.Actions
	 * @see org.openqa.selenium.interactions.Actions#moveToElement(org.openqa.selenium.WebElement)
	 * @see org.openqa.selenium.interactions.MoveToOffsetAction
	 */
	void hover();

	/**
	 * Focus on input; keystrokes will subsequently go to this element.
	 *
	 * @see org.openqa.selenium.JavascriptExecutor
	 * @see <a href="https://developer.mozilla.org/en/docs/Web/API/HTMLInputElement">HTMLInputElement</a>
	 */
	void focus();

	/**
	 * Simulates a click on the element.
	 *
	 * @see <a href="https://developer.mozilla.org/en/docs/Web/API/HTMLInputElement">HTMLInputElement</a>
	 */
	void jsClick();

	public String getLocator();

	/**
	 * Returns true if the specified attribute exists, otherwise it returns false.
	 *
	 * @param name The name of the attribute you want to check if exists
	 *
	 * @return {@code true} if the specified attribute exists, otherwise it returns {@code false}
	 */
	boolean hasAttribute( final String name );

	boolean exists( By by );















	/**
	 * classList returns a token list of the class attribute of the element.
	 * @return
	 */
	//List<String> getClassList();
	//

	//
	//	/**
	//	 * The user-facing API for emulating complex user gestures. Use this class rather than using the
	//	 * Keyboard or Mouse directly.
	//	 * <p>
	//	 * Implements the builder pattern: Builds a {@link org.openqa.selenium.interactions.CompositeAction}
	//	 * containing all actions specified by the method calls.
	//	 * </p>
	//	 *
	//	 * @return an {@link org.openqa.selenium.interactions.Actions} builder object
	//	 *
	//	 * @see org.openqa.selenium.interactions.Actions
	//	 * @see org.openqa.selenium.interactions.HasInputDevices
	//	 * @see org.openqa.selenium.interactions.MoveToOffsetAction
	//	 * @see org.openqa.selenium.interactions.CompositeAction
	//	 * @see org.openqa.selenium.interactions.KeyDownAction
	//	 * @see org.openqa.selenium.interactions.KeyUpAction
	//	 * @see org.openqa.selenium.interactions.SendKeysAction
	//	 * @see org.openqa.selenium.interactions.ClickAndHoldAction
	//	 * @see org.openqa.selenium.interactions.ButtonReleaseAction
	//	 * @see org.openqa.selenium.interactions.ClickAction
	//	 * @see org.openqa.selenium.interactions.DoubleClickAction
	//	 * @see org.openqa.selenium.interactions.ContextClickAction
	//	 */
	//	Actions createAction();
	//
	//	/**
	//	 * Performs a double-click at middle of the given element.
	//	 * Equivalent to: <i>Actions.moveToElement(element).doubleClick()</i>
	//	 *
	//	 * @see org.openqa.selenium.interactions.Actions#doubleClick() ()
	//	 * @see  org.openqa.selenium.interactions.Actions#doubleClick(org.openqa.selenium.WebElement)
	//	 */
	//	void doubleClick();
	//
	//	/**
	//	 * Context-clicks an element
	//	 *
	//	 * @see org.openqa.selenium.interactions.Actions#contextClick()
	//	 * @see org.openqa.selenium.interactions.Actions#contextClick(org.openqa.selenium.WebElement)
	//	 */
	//	void contextClick();
	//

	//
	//	/**
	//	 * The user-facing API for emulating complex user gestures.
	//	 * Hovers over a {@linkplain org.openqa.selenium.WebElement}
	//	 *
	//	 * @see org.openqa.selenium.interactions.Actions
	//	 * @see Actions#moveToElement(org.openqa.selenium.WebElement)
	//	 * @see org.openqa.selenium.interactions.MoveToOffsetAction
	//	 */
	//	void hover( int xOffset, int yOffset );
	//













	//
	//	String getElementSystemId();
	//
	//	EventWebElement getElement( By locator );
	//
	//	List<EventWebElement> getElements( By locator );

}
