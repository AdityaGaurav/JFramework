package com.framework.driver.event;

import com.framework.driver.exceptions.ApplicationException;
import com.google.common.base.Joiner;
import com.google.common.base.Throwables;
import org.openqa.selenium.WebDriver;
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

	private final WebDriver driver;

	private final EventListener dispatcher;

	private final Keyboard keyboard;

	//endregion


	//region EventKeyboard - Constructor Methods Section

	public EventKeyboard( WebDriver driver, EventListener dispatcher )
	{
		this.driver = driver;
		this.dispatcher = dispatcher;
		this.keyboard = ( ( HasInputDevices ) this.driver ).getKeyboard();
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
			dispatcher.beforeKeyboardAction( driver, "sendKeys", joiner );
			keyboard.sendKeys( keysToSend );
			dispatcher.afterKeyboardAction( driver, "sendKeys", joiner );
		}
		catch ( Exception ex )
		{
			throw propagate( ex, "failed to sendKeys sequence \"" + joiner + "\"."  );
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
			dispatcher.beforeKeyboardAction( driver, "pressKey", keyToPress );
			keyboard.pressKey( keyToPress );
			dispatcher.afterKeyboardAction( driver, "pressKey", keyToPress );
		}
		catch ( Exception ex )
		{
			throw propagate( ex, "failed to press Key \"" + keyToPress + "\"."  );
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
			dispatcher.beforeKeyboardAction( driver, "pressKey", keyToRelease );
			keyboard.releaseKey( keyToRelease );
			dispatcher.afterKeyboardAction( driver, "pressKey", keyToRelease );
		}
		catch ( Exception ex )
		{
			throw propagate( ex, "failed to release Key \"" + keyToRelease + "\"."  );
		}
	}

	//endregion


	//region EventKeyboard - Private Methods Section

	//Todo: Documentation
	private ApplicationException propagate( Exception ex, String msg )
	{
		Throwables.propagateIfInstanceOf( ex, ApplicationException.class );
		logger.error( ex.getMessage() );
		ApplicationException ae = new ApplicationException( driver, ex.getMessage() );
		ae.addInfo( "causing action", msg );
		dispatcher.onException( ae, driver );
		return ae;
	}

	//endregion
}
