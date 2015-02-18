package com.framework.driver.event;

import org.openqa.selenium.WebDriver;


public class WebDriverEventSource implements EventTypes
{

	//region WebDriverEvent - Variables Declaration and Initialization Section.

	/**
	 * A collection for the registered event listeners.
	 */
	private WebDriverListener listener;

	/**
	 * A collection for the registered error listeners.
	 */
	private WebDriverErrorListener errorListener;

	private static boolean ignoreOnError = false;

	//endregion


	//region WebDriverEvent - Constructor Methods Section

	public WebDriverEventSource()
	{
		initListeners();
	}

	//endregion


	//region WebDriverEvent - Events Methods Section

	public static boolean isIgnoreOnError()
	{
		return ignoreOnError;
	}

	public static void setIgnoreOnError( final boolean ignoreOnError )
	{
		WebDriverEventSource.ignoreOnError = ignoreOnError;
	}

	protected WebDriverListener getWebDriverListener()
	{
		return listener;
	}

	protected WebDriverErrorListener getWebDriverErrorListener()
	{
		return errorListener;
	}

	public void fireEvent( Object source, int type, WebDriver driver, boolean isBefore, Object... arguments )
	{
		WebDriverEvent event = createEvent( source, type, driver, isBefore, arguments );

		switch ( type )
		{
			case EVENT_ELEMENT_FIND_ELEMENTS:
			{
				listener.onFindElements( event );
				break;
			}
			case EVENT_ELEMENT_FIND_ELEMENT:
			{
				listener.onFindElement( event );
				break;
			}
			case EVENT_HOVER:
			{
				listener.onHover( event );
				break;
			}
			case EVENT_CLICK:
			case EVENT_SUBMIT:
			{
				listener.onClick( event );
				break;
			}
			case EVENT_CLEAR:
			{
				listener.onClear( event );
				break;
			}
			case EVENT_SEND_KEYS:
			{
				listener.onSendKeys( event );
				break;
			}
			case EVENT_GET_TEXT:
			{
				listener.onGetText( event );
				break;
			}
		}
	}

	protected void fireEvent( int type, WebDriver driver, boolean isBefore, Object... arguments )
	{
		WebDriverEvent event = createEvent( type, driver, isBefore, arguments );

		switch ( type )
		{
			case EVENT_NAVIGATE_TO:
			case EVENT_NAVIGATE_BACK:
			case EVENT_NAVIGATE_FORWARD:
			case EVENT_NAVIGATE_REFRESH:
			{
				listener.onNavigateTo( event );
				break;
			}
			case EVENT_CLOSE:
			{
				listener.onClose( event );
				break;
			}
			case EVENT_QUIT:
			{
				listener.onQuit( event );
				break;
			}
			case EVENT_DELETE_COOKIE_NAME:
			case EVENT_ADD_COOKIE:
			case EVENT_DELETE_ALL_COOKIES:
			case EVENT_GET_COOKIE:
			{
				listener.onCookieAction( event );
				break;
			}
			case EVENT_DRIVER_FIND_ELEMENT:
			{
				listener.onFindElement( event );
				break;
			}
			case EVENT_DRIVER_FIND_ELEMENTS:
			{
				listener.onFindElements( event );
				break;
			}
			case EVENT_IMPLICITLY_WAIT:
			case EVENT_SCRIPT_TIMEOUT:
			case EVENT_PAGE_LOAD_TIMEOUT:
			{
				listener.onTimeoutChange( event );
				break;
			}
			case EVENT_FRAME_TARGET_LOCATOR:
			case EVENT_PARENT_FRAME_TARGET_LOCATOR:
			{
				listener.onSwitchToFrame( event );
				break;
			}
			case EVENT_WINDOW_TARGET_LOCATOR:
			{
				listener.onSwitchToWindow( event );
				break;
			}
			case EVENT_WINDOW_MAXIMIZE:
			{
				listener.onWindowChange( event );
				break;
			}
		}
	}

	private WebDriverEvent createEvent( int type, WebDriver driver, boolean isBefore, Object... arguments )
	{
		return new WebDriverEvent( this, type, driver, isBefore, arguments );
	}

	private WebDriverEvent createEvent( Object source, int type, WebDriver driver, boolean isBefore, Object... arguments )
	{
		return new WebDriverEvent( source, type, driver, isBefore, arguments );
	}

	public void fireError( Object source, int type, WebDriver driver, Throwable ex )
	{
		if( ! ignoreOnError )
		{
			WebDriverErrorEvent event = createErrorEvent( source, type, driver, ex );
			errorListener.onException( event );
		}
	}

	protected void fireError( int type, WebDriver driver, Throwable ex )
	{
		if( ! ignoreOnError )
		{
			WebDriverErrorEvent event = createErrorEvent( type, driver, ex );
			errorListener.onException( event );
		}
	}

	private WebDriverErrorEvent createErrorEvent( int type, WebDriver driver, Throwable ex )
	{
		return new WebDriverErrorEvent( this, type, driver, ex );
	}

	private WebDriverErrorEvent createErrorEvent( Object source, int type, WebDriver driver, Throwable ex )
	{
		return new WebDriverErrorEvent( source, type, driver, ex );
	}



	//endregion


	//region WebDriverEvent - Private Function Section

	/**
	 * Initializes the collections for storing registered event listeners.
	 */
	private void initListeners()
	{
		listener = new WebDriverListenerAdapter();
		errorListener = new WebDriverErrorListenerAdapter();
	}

	//endregion

}
