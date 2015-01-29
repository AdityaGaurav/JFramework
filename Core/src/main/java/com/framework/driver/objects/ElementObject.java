package com.framework.driver.objects;

import com.framework.asserts.CheckpointAssert;
import org.hamcrest.Matcher;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.internal.WrapsElement;
import org.openqa.selenium.support.ui.ExpectedCondition;

import java.io.File;
import java.io.IOException;


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
	<T> void assertThat( String reason, T actual, Matcher<? super T> matcher );

	<T> void assertWaitThat( String reason, long timeout, ExpectedCondition<?> condition );

	void blur();

	File captureBitmap() throws IOException;

	<T> CheckpointAssert checkPoint( String id, String reason, T actual, Matcher<? super T> matcher );

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

	boolean exists( By by );

	void scrollIntoView();

	void focus();

	String getLocator();

	boolean hasAttribute( final String name );

	void hover();

	void jsDoubleClick();

	void jsClick();

	void select(); //todo missing full implementation

	<T> CheckpointAssert waitCheckPoint( String id, String reason, long timeout, ExpectedCondition<?> condition );
}
