package com.framework.driver.event;

import org.openqa.selenium.WebDriver;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.driver.event
 *
 * Name   : WebDriverErrorEvent 
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-02-04 
 *
 * Time   : 20:20
 *
 */

public class WebDriverErrorEvent extends WebDriverEvent
{

	//region WebDriverErrorEvent - Variables Declaration and Initialization Section.

	/**
	 * Stores the exception that caused this event.
	 */
	private Throwable cause;

	//endregion


	//region WebDriverErrorEvent - Constructor Methods Section

	public WebDriverErrorEvent( Object source, int type, WebDriver driver, Throwable cause )
	{
		super( source, type, driver, false );
		this.cause = cause;
	}

	//endregion


	//region WebDriverErrorEvent - Public Methods Section

	public Throwable getCause()
	{
		return cause;
	}

	//endregion

}
