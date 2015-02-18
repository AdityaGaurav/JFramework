package com.framework.driver.event;

/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.driver.event
 *
 * Name   : WebDriverErrorListener 
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-02-04 
 *
 * Time   : 19:20
 *
 */

public interface WebDriverErrorListener
{
	void onException( WebDriverErrorEvent event );
}
