package com.framework.driver.exceptions;

import org.openqa.selenium.WebDriver;


/**
 * Extends {@linkplain ApplicationException} functionality.
 * this exception should be thrown on {@link com.framework.driver.objects.ElementObject} implementations.
 *
 * @see com.framework.driver.objects.ElementObject
 * @see com.framework.driver.objects.AbstractElementObject
 */
public class ElementObjectException extends ApplicationException
{

	private static final long serialVersionUID = - 5377404343121067334L;

	public ElementObjectException( final WebDriver driver, final String message )
	{
		super( driver, message );
	}

	public ElementObjectException( final WebDriver driver, final Throwable cause )
	{
		super( driver, cause );
	}

	public ElementObjectException( final WebDriver driver, final String message, final Throwable cause )
	{
		super( driver, message, cause );
	}
}
