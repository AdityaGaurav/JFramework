package com.framework.driver.event;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.interactions.HasInputDevices;
import org.openqa.selenium.interactions.Mouse;
import org.openqa.selenium.interactions.internal.Coordinates;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: org.framework.webdriver.driver
 *
 * Name   : EventMouse
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2014-12-14
 *
 * Time   : 13:02
 */

public class EventMouse implements Mouse
{

	//region EventMouse - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( EventMouse.class );

	private final HtmlDriver eventDriver;

	private WebDriverListener listener;

	private WebDriverErrorListener errorListener;

	private final Mouse mouse;

	//endregion


	//region EventMouse - Constructor Methods Section

	public EventMouse( HtmlDriver driver, WebDriverListener listener, WebDriverErrorListener errorListener )
	{
		this.eventDriver = driver;
		this.listener = listener;
		this.errorListener = errorListener;
		this.mouse = ( ( HasInputDevices ) this.eventDriver ).getMouse();
	}

	//endregion


	//region EventMouse - Public Methods Section

	//todo: documentation
	//todo: implement dispatcher event
	@Override
	public void click( Coordinates where )
	{
		try
		{
			listener.onMouseAction( createEvent( true, "click", where ) );
			mouse.click( where );
			listener.onMouseAction( createEvent( true, "click" ) );
		}
		catch ( Exception ex )
		{
			errorListener.onException( createErrorEvent( ex ) );
			throw new WebDriverException( ex );
		}
	}

	//todo: documentation
	//todo: implement dispatcher event
	@Override
	public void doubleClick( Coordinates where )
	{
		try
		{
			listener.onMouseAction( createEvent( true, "doubleClick", where ) );
			mouse.doubleClick( where );
			listener.onMouseAction( createEvent( true, "doubleClick" ) );
		}
		catch ( Exception ex )
		{
			errorListener.onException( createErrorEvent( ex ) );
			throw new WebDriverException( ex );
		}
	}

	//todo: documentation
	//todo: implement dispatcher event
	@Override
	public void mouseDown( Coordinates where )
	{
		try
		{
			listener.onMouseAction( createEvent( true, "mouseDown", where ) );
			mouse.mouseDown( where );
			listener.onMouseAction( createEvent( true, "mouseDown" ) );
		}
		catch ( Exception ex )
		{
			errorListener.onException( createErrorEvent( ex ) );
			throw new WebDriverException( ex );
		}
	}

	//todo: documentation
	//todo: implement dispatcher event
	@Override
	public void mouseUp( Coordinates where )
	{
		try
		{
			listener.onMouseAction( createEvent( true, "mouseUp", where ) );
			mouse.mouseUp( where );
			listener.onMouseAction( createEvent( true, "mouseUp" ) );
		}
		catch ( Exception ex )
		{
			errorListener.onException( createErrorEvent( ex ) );
			throw new WebDriverException( ex );
		}
	}

	//todo: documentation
	//todo: implement dispatcher event
	@Override
	public void mouseMove( Coordinates where )
	{
		try
		{
			listener.onMouseAction( createEvent( true, "mouseMove", where ) );
			mouse.mouseMove( where );
			listener.onMouseAction( createEvent( true, "mouseMove" ) );
		}
		catch ( Exception ex )
		{
			errorListener.onException( createErrorEvent( ex ) );
			throw new WebDriverException( ex );
		}
	}

	//todo: documentation
	//todo: implement dispatcher event
	@Override
	public void mouseMove( Coordinates where, long xOffset, long yOffset )
	{
		try
		{
			listener.onMouseAction( createEvent( true, "mouseMove", where, xOffset, yOffset ) );
			mouse.mouseMove( where );
			listener.onMouseAction( createEvent( true, "mouseMove" ) );
		}
		catch ( Exception ex )
		{
			errorListener.onException( createErrorEvent( ex ) );
			throw new WebDriverException( ex );
		}
	}

	//todo: documentation
	//todo: implement dispatcher event
	@Override
	public void contextClick( Coordinates where )
	{
		try
		{
			listener.onMouseAction( createEvent( true, "contextClick", where ) );
			mouse.contextClick( where );
			listener.onMouseAction( createEvent( true, "contextClick" ) );
		}
		catch ( Exception ex )
		{
			errorListener.onException( createErrorEvent( ex ) );
			throw new WebDriverException( ex );
		}
	}

	//endregion


	//region EventMouse - Private Methods Section

	private WebDriverEvent createEvent( boolean isBefore, Object... arguments )
	{
		WebDriver driver = eventDriver.getWrappedDriver();
		return new WebDriverEvent( this, HtmlWebDriver.EVENT_MOUSE_ACTION, driver, isBefore, arguments );
	}

	private WebDriverErrorEvent createErrorEvent( Throwable ex )
	{
		WebDriver driver = eventDriver.getWrappedDriver();
		return new WebDriverErrorEvent( this, HtmlWebDriver.EVENT_MOUSE_ACTION, driver, ex );
	}

	//endregion

}
