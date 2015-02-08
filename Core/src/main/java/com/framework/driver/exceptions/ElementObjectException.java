package com.framework.driver.exceptions;

/**
 * Extends {@linkplain ApplicationException} functionality.
 * this exception should be thrown on {@link com.framework.driver.objects.ElementObjectw} implementations.
 *
 * @see com.framework.driver.objects.ElementObjectw
 * @see com.framework.driver.objects.BaseElementObject
 */
public class ElementObjectException extends RuntimeException
{
	private static final long serialVersionUID = - 5377404343121067334L;

	public ElementObjectException( final String message )
	{
		super( message );
	}

	public ElementObjectException( final Throwable cause )
	{
		super( cause );
	}

	public ElementObjectException( final String message, final Throwable cause )
	{
		super( message, cause );
	}
}
