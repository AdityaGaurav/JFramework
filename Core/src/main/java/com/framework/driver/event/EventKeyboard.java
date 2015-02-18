package com.framework.driver.event;

import com.google.common.base.Joiner;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.interactions.HasInputDevices;
import org.openqa.selenium.interactions.Keyboard;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: org.framework.webdriver.driver
 *
 * Name   : EventKeyboard
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2014-12-14
 *
 * Time   : 12:59
 */

public class EventKeyboard implements Keyboard
{

	//region EventKeyboard - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( EventKeyboard.class );

	private final HtmlDriver eventDriver;

	private WebDriverListener listener;

	private WebDriverErrorListener errorListener;

	private final Keyboard keyboard;

	//endregion


	//region EventKeyboard - Constructor Methods Section

	public EventKeyboard( HtmlDriver driver, WebDriverListener listener, WebDriverErrorListener errorListener )
	{
		this.eventDriver = driver;
		this.listener = listener;
		this.errorListener = errorListener;
		this.keyboard = ( ( HasInputDevices ) this.eventDriver ).getKeyboard();
	}

	//endregion


	//region EventKeyboard - Public Methods Section

	/**
	 * Sends keys to the keyboard representation in the browser.
	 *
	 * Special keys that are not text, represented as {@link org.openqa.selenium.Keys} are recognized
	 * both as part of sequences of characters, or individually.
	 *
	 * Modifier keys are preserved throughout the lifetime of the send keys operation, and are
	 * released upon this method returning.
	 *
	 * @param keysToSend one or more sequences of characters or key representations to type on the
	 *                   keyboard
	 */
	@Override
	public void sendKeys( CharSequence... keysToSend )
	{
		String joiner = Joiner.on( ";" ).useForNull( "[NULL]" ).join( keysToSend );

		try
		{
			listener.onKeyboardAction( createEvent( true, "sendKeys", joiner ) );
			keyboard.sendKeys( keysToSend );
			listener.onKeyboardAction( createEvent( false, "sendKeys" ) );
		}
		catch ( Exception ex )
		{
			errorListener.onException( createErrorEvent( ex ) );
			throw new WebDriverException( ex );
		}
	}

	/**
	 * Press a key on the keyboard that isn't text.  Please see {@link org.openqa.selenium.Keys} for
	 * an exhaustive list of recognized pressable keys.
	 *
	 * If <code>keyToPress</code> is a sequence of characters, different driver implementations may
	 * choose to throw an exception or to read only the first character in the sequence.
	 *
	 * @param keyToPress the key to press, if a sequence only the first character will be read or an
	 *                   exception is thrown
	 */
	@Override
	public void pressKey( CharSequence keyToPress )
	{
		try
		{
			listener.onKeyboardAction( createEvent( true, "pressKey", keyToPress ) );
			keyboard.pressKey( keyToPress );
			listener.onKeyboardAction( createEvent( true, "pressKey" ) );
		}
		catch ( Exception ex )
		{
			errorListener.onException( createErrorEvent( ex ) );
			throw new WebDriverException( ex );
		}

	}

	/**
	 * Release a key on the keyboard that isn't text.  Please see {@link org.openqa.selenium.Keys} for
	 * an exhaustive list of recognized pressable keys.
	 *
	 * If <code>keyToRelease</code> is a sequence of characters, different driver implementations may
	 * choose to throw an exception or to read only the first character in the sequence.
	 *
	 * @param keyToRelease the key to press, if a sequence only the first character will be read or an exception is thrown
	 */
	@Override
	public void releaseKey( CharSequence keyToRelease )
	{
		try
		{
			listener.onKeyboardAction( createEvent( true, "releaseKey", keyToRelease ) );
			keyboard.releaseKey( keyToRelease );
			listener.onKeyboardAction( createEvent( true, "releaseKey" ) );
		}
		catch ( Exception ex )
		{
			errorListener.onException( createErrorEvent( ex ) );
			throw new WebDriverException( ex );
		}
	}

	//endregion


	//region EventKeyboard - Private Methods Section


	private WebDriverEvent createEvent( boolean isBefore, Object... arguments )
	{
		WebDriver driver = eventDriver.getWrappedDriver();
		return new WebDriverEvent( this, HtmlWebDriver.EVENT_KEYBOARD_ACTION, driver, isBefore, arguments );
	}

	private WebDriverErrorEvent createErrorEvent( Throwable ex )
	{
		WebDriver driver = eventDriver.getWrappedDriver();
		return new WebDriverErrorEvent( this, HtmlWebDriver.EVENT_KEYBOARD_ACTION, driver, ex );
	}

	//endregion
}
