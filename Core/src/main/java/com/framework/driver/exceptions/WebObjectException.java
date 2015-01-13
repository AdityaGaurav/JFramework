package com.framework.driver.exceptions;

import org.openqa.selenium.WebDriver;


/**
 * Extends {@linkplain ApplicationException} functionality.
 * this exception should be thrown on {@link com.framework.driver.objects.WebObject} implementations.
 *
 * @see com.framework.driver.objects.WebObject
 * @see com.framework.driver.objects.AbstractWebObject
 */

public class WebObjectException extends ApplicationException
{
	private static final long serialVersionUID = 8020339250176309089L;

	public WebObjectException( final WebDriver driver, final String message )
	{
		super( driver, message );
	}

	public WebObjectException( final WebDriver driver, final Throwable cause )
	{
		super( driver, cause );
	}

	public WebObjectException( final WebDriver driver, final String message, final Throwable cause )
	{
		super( driver, message, cause );
	}
}
