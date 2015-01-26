package com.framework.site.exceptions;

import com.framework.driver.exceptions.ApplicationItemNotFoundException;
import org.openqa.selenium.WebDriver;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.site.exceptions
 *
 * Name   : InvalidPortException
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-01-11
 *
 * Time   : 15:34
 */

public class NoSuchMenuItemException extends ApplicationItemNotFoundException
{
	private static final long serialVersionUID = 7978996500317505389L;

	public NoSuchMenuItemException( final WebDriver driver, final String message )
	{
		super( driver, message );
	}
}
