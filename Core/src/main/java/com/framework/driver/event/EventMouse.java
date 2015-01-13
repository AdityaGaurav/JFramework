package com.framework.driver.event;

import com.framework.driver.exceptions.ApplicationException;
import com.google.common.base.Throwables;
import org.openqa.selenium.WebDriver;
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

	private final WebDriver driver;

	private final EventListener dispatcher;

	private final Mouse mouse;

	//endregion


	//region EventMouse - Constructor Methods Section

	public EventMouse( WebDriver driver, EventListener dispatcher )
	{
		this.driver = driver;
		this.dispatcher = dispatcher;
		this.mouse = ( ( HasInputDevices ) this.driver ).getMouse();
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
			dispatcher.beforeMouseAction( driver, "click", where );
			mouse.click( where );
			dispatcher.afterMouseAction( driver, "click", where );
		}
		catch ( Exception ex )
		{
			throw propagate( ex, "failed to execute mouse action \"click\" on \"" + where + "\"."  );
		}
	}

	//todo: documentation
	//todo: implement dispatcher event
	@Override
	public void doubleClick( Coordinates where )
	{
		try
		{
			dispatcher.beforeMouseAction( driver, "doubleClick", where );
			mouse.doubleClick( where );
			dispatcher.afterMouseAction( driver, "doubleClick", where );
		}
		catch ( Exception ex )
		{
			throw propagate( ex, "failed to execute mouse action \"doubleClick\" on \"" + where + "\"."  );
		}
	}

	//todo: documentation
	//todo: implement dispatcher event
	@Override
	public void mouseDown( Coordinates where )
	{
		try
		{
			dispatcher.beforeMouseAction( driver, "mouseDown", where );
			mouse.mouseDown( where );
			dispatcher.afterMouseAction( driver, "mouseDown", where );
		}
		catch ( Exception ex )
		{
			throw propagate( ex, "failed to execute mouse action \"mouseDown\" on \"" + where + "\"."  );
		}
	}

	//todo: documentation
	//todo: implement dispatcher event
	@Override
	public void mouseUp( Coordinates where )
	{
		try
		{
			dispatcher.beforeMouseAction( driver, "mouseUp", where );
			mouse.mouseUp( where );
			dispatcher.afterMouseAction( driver, "mouseUp", where );
		}
		catch ( Exception ex )
		{
			throw propagate( ex, "failed to execute mouse action \"mouseUp\" on \"" + where + "\"."  );
		}
	}

	//todo: documentation
	//todo: implement dispatcher event
	@Override
	public void mouseMove( Coordinates where )
	{
		try
		{
			dispatcher.beforeMouseAction( driver, "mouseMove", where );
			mouse.mouseMove( where );
			dispatcher.afterMouseAction( driver, "mouseMove", where );
		}
		catch ( Exception ex )
		{
			throw propagate( ex, "failed to execute mouse action \"mouseMove\" on \"" + where + "\"."  );
		}
	}

	//todo: documentation
	//todo: implement dispatcher event
	@Override
	public void mouseMove( Coordinates where, long xOffset, long yOffset )
	{
		try
		{
			dispatcher.beforeMouseAction( driver, "mouseMove", where );
			mouse.mouseMove( where, xOffset, yOffset );
			dispatcher.afterMouseAction( driver, "mouseMove", where );
		}
		catch ( Exception ex )
		{
			throw propagate( ex, "failed to execute mouse action \"mouseMove\" on \"" + where + "\" with offset \"" + xOffset + ":" + yOffset + "\"." );
		}
	}

	//todo: documentation
	//todo: implement dispatcher event
	@Override
	public void contextClick( Coordinates where )
	{
		try
		{
			dispatcher.beforeMouseAction( driver, "contextClick", where );
			mouse.contextClick( where );
			dispatcher.afterMouseAction( driver, "contextClick", where );
		}
		catch ( Exception ex )
		{
			throw propagate( ex, "failed to execute mouse action \"contextClick\" on \"" + where + "\"."  );
		}
	}

	//endregion


	//region EventMouse - Private Methods Section

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
