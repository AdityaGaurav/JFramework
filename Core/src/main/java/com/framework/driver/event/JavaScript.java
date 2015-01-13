package com.framework.driver.event;

import org.openqa.selenium.WebElement;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.driver.event
 *
 * Name   : JavaScript
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-01-08
 *
 * Time   : 19:31
 */

public interface JavaScript
{
	//todo: documentation
	String getString( String script, Object... args );

	//todo: documentation
	String[] getStringArray( String script, Object... args );

	//todo: documentation
	Number getNumber( String script, Object... args );

	//todo: documentation
	boolean getBoolean( String script, Object... args );

	//todo: documentation
	WebElement getWebElement( String script, Object... args );
}
