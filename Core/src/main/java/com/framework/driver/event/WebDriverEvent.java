package com.framework.driver.event;

import org.openqa.selenium.WebDriver;

import java.util.EventObject;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.driver.event
 *
 * Name   : WebDriverEvent 
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-02-04 
 *
 * Time   : 19:36
 *
 */

public class WebDriverEvent extends EventObject
{

	//region WebDriverEvent - Variables Declaration and Initialization Section.

	/**
	 * The serial version UID.
	 */
	private static final long serialVersionUID = 3277235219373504136L;

	/**
	 * Stores the event type.
	 */
	private final int type;

	private final WebDriver driver;

	private final boolean isBefore;

	private final Object[] arguments;


	//endregion


	//region WebDriverEvent - Constructor Methods Section

	public WebDriverEvent( Object source, int type, WebDriver driver, boolean isBefore, Object... arguments )
	{
		super( source );
		this.type = type;
		this.driver = driver;
		this.isBefore = isBefore;
		this.arguments = arguments;
	}

	//endregion


	//region WebDriverEvent - Public Methods Section

	public int getType()
	{
		return type;
	}

	public WebDriver getDriver()
	{
		return driver;
	}

	public boolean isBefore()
	{
		return isBefore;
	}

	public Object[] getArguments()
	{
		return arguments;
	}

	//endregion

}
