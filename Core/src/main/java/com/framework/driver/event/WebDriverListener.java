package com.framework.driver.event;

/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.driver.event
 *
 * Name   : WebDriverListener 
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-02-04 
 *
 * Time   : 19:19
 *
 */

public interface WebDriverListener
{
	void onNavigateTo( WebDriverEvent event );

	void onClose( WebDriverEvent event );

	void onQuit( WebDriverEvent event );

	void onCookieAction( WebDriverEvent event );

	void onFindElement( WebDriverEvent event );

	void onFindElements( WebDriverEvent event );

	void onTimeoutChange( WebDriverEvent event );

	void onWindowChange( WebDriverEvent event );

	void onJavaScript( WebDriverEvent event );

	void onSwitchToFrame( WebDriverEvent event );

	void onSwitchToWindow( WebDriverEvent event );

	void onKeyboardAction( WebDriverEvent event );

	void onMouseAction( WebDriverEvent event );

	void onHover( WebDriverEvent event );

	void onClick( WebDriverEvent event );

	void onClear( WebDriverEvent event );

	void onSendKeys( WebDriverEvent event );

	void onGetText(  WebDriverEvent event );
}
