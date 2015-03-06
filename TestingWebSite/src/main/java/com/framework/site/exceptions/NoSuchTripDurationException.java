package com.framework.site.exceptions;

import com.framework.driver.event.HtmlDriver;
import com.framework.driver.exceptions.ApplicationException;


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

public class NoSuchTripDurationException extends ApplicationException
{
	private static final long serialVersionUID = 7978996500317505389L;

	public NoSuchTripDurationException( final HtmlDriver driver, final String message )
	{
		super( driver, message );
	}
}
