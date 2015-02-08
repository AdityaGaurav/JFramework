package com.framework.driver.exceptions;

import com.framework.driver.event.HtmlDriver;
import org.openqa.selenium.WebDriverException;


/**
 * Extends WebDriver exception
 * The ApplicationException should be thrown when something in the application UI side went unexpected.
 * the class will automatically take a screenshot of the current working page in case that the
 * exception was initialized with a {@code WebDriver}.
 * additional info can be added to the exception.
 * use {@link com.google.common.base.Throwables#propagateIfInstanceOf(Throwable, Class)} (  );} to avoid multiple
 * error reports.
 *
 * @see com.google.common.base.Throwables#propagateIfInstanceOf(Throwable, Class)
 * @see org.openqa.selenium.WebDriverException
 */

public class ApplicationException extends WebDriverException
{

	private static final long serialVersionUID = 1188529006588402959L;

	protected ApplicationException()
	{
	}

	protected ApplicationException( final String message )
	{
		super( message );
	}

	public ApplicationException( final HtmlDriver driver, final String message )
	{
		super( message );
		takeScreenshot( driver );
	}

	public ApplicationException( final HtmlDriver driver, final Throwable cause )
	{
		super( cause );
		takeScreenshot( driver );
	}

	public ApplicationException( final String message, final Throwable cause )
	{
		super( message, cause );
	}

	public ApplicationException( final Throwable cause )
	{
		super( cause );
	}

	public ApplicationException( final HtmlDriver driver, final String message, final Throwable cause )
	{
		super( message, cause );
		takeScreenshot( driver );

	}

	private void takeScreenshot( HtmlDriver driver )
	{
		// System.getProperty( "output.directory" )
		//Screenshot screenshot = new Screenshot( driver, "/Users/solmarkn/IdeaProjects/WebDriverTestNg/Screenshots" );
		//String outputFile = screenshot.takeScreenshot();
		//super.addInfo( "screenshot file", outputFile );
	}

}
