package com.framework.driver.exceptions;

import com.framework.driver.event.HtmlDriver;
import com.framework.driver.event.HtmlElement;
import com.framework.driver.utils.ui.screenshots.Photographer;
import com.framework.driver.utils.ui.screenshots.ScreenshotAndHtmlSource;
import com.google.common.base.Optional;
import com.google.common.collect.Maps;
import org.openqa.selenium.internal.BuildInfo;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;


/**
 * Extends RuntimeException exception
 * The ApplicationException should be thrown when something in the application UI or non-UI process went unexpected.
 * the class will automatically take a screenshot of the current working page in case that the
 * exception was initialized with a {@code HtmlDriver} or or the current object if was initialized with {@code HtmlElement.
 * additional info can be added to the exception.
 * use {@link com.google.common.base.Throwables#propagateIfInstanceOf(Throwable, Class)} (  );} to avoid multiple
 * error reports.
 *
 * @see com.google.common.base.Throwables#propagateIfInstanceOf(Throwable, Class)
 */

public class ApplicationException extends RuntimeException
{
	private static final long serialVersionUID = 1188529006588402959L;

	private Map<String, String> extraInfo = Maps.newHashMap();

	private HtmlDriver driver;

	public ApplicationException()
	{
	}

	/**
	 * Constructs a new runtime exception with the specified detail message.
	 * The exception will not take screenshots.
	 * used for rule violation messages or internal application errors that are not UI related.
	 * the constructor can only be accessed via extending classes.
	 *
	 * @param message the detail message. The detail message is saved for later retrieval by the {@link #getMessage()} method.
	 */
	protected ApplicationException( final String message )
	{
		super( message );
	}

	/**
	 * {@inheritDoc}
	 *  the constructor can only be accessed via extending classes.
	 */
	protected ApplicationException( final String message, Throwable cause )
	{
		super( message, cause );
	}

	/**
	 * {@inheritDoc}
	 *  the constructor can only be accessed via extending classes.
	 */
	public ApplicationException( Throwable cause )
	{
		super( cause );
	}

	/**
	 * a public constructor exception that will automatically takes a screenshot from the working window handle.
	 *
	 * @param driver	an instance of the current active driver.
	 * @param message   the detail message. The detail message is saved for later retrieval by the {@link #getMessage()} method.
	 */
	public ApplicationException( HtmlDriver driver, final String message )
	{
		super( message );
		takeScreenshot( driver );
		this.driver = driver;
	}

	/**
	 * a public con
	 * @param driver  an instance of the current active driver.
	 * @param message the detail message. The detail message is saved for later retrieval by the {@link #getMessage()} method.
	 * @param cause   he cause (which is saved for later retrieval by the {@link #getCause()} method).
	 */
	public ApplicationException( HtmlDriver driver, final String message, Throwable cause )
	{
		super( message, cause );
		this.driver = driver;
		takeScreenshot( driver );
	}

	/**
	 * a public constructor exception that will automatically takes a screenshot from the working window handle.
	 *
	 * @param driver  an instance of the current active driver.
	 * @param cause   the detail message. The detail message is saved for later retrieval by the {@link #getMessage()} method.
	 */
	public ApplicationException( HtmlDriver driver, Throwable cause )
	{
		super( cause );
		this.driver = driver;
		takeScreenshot( driver );
	}


	/**
	 * a public constructor exception that will automatically takes a screenshot from the current element area.
	 *
	 * @param element	an instance of the current active element.
	 * @param message   the detail message. The detail message is saved for later retrieval by the {@link #getMessage()} method.
	 */
	public ApplicationException( HtmlElement element, final String message )
	{
		super( message );
		takeScreenshot( element );
		this.driver = element.getWrappedHtmlDriver();
	}

	/**
	 * a public constructor exception that will automatically takes a screenshot from the current element area.
	 *
	 * @param element  an instance of the current active element.
	 * @param cause    the detail message. The detail message is saved for later retrieval by the {@link #getMessage()} method.
	 */
	public ApplicationException( HtmlElement element, Throwable cause )
	{
		super( cause );
		takeScreenshot( element );
		this.driver = element.getWrappedHtmlDriver();
	}

	/**
	 * a public constructor exception that will automatically takes a screenshot from the current element area.
	 *
	 * @param element 	an instance of the current active element.
	 * @param message   the detail message. The detail message is saved for later retrieval by the {@link #getMessage()} method.
	 * @param cause     the detail message. The detail message is saved for later retrieval by the {@link #getMessage()} method.
	 */
	public ApplicationException( HtmlElement element, final String message, Throwable cause )
	{
		super( message, cause );
		takeScreenshot( element );
		this.driver = element.getWrappedHtmlDriver();
	}

	private String createMessage( String originalMessageString )
	{
		return ( originalMessageString == null ? "" : originalMessageString + "\n" )
				+ getBuildInformation() + "\n"
				+ getSystemInformation()
				+ getAdditionalInformation();
	}

	private BuildInfo getBuildInformation() {
		return new BuildInfo();
	}

	public String getSystemInformation()
	{
		String host = "N/A";
		String ip = "N/A";

		try
		{
			host = InetAddress.getLocalHost().getHostName();
			ip = InetAddress.getLocalHost().getHostAddress();
		}
		catch ( UnknownHostException throw_away )
		{
			//
		}

		return String.format( "System info: host: '%s', ip: '%s', os.name: '%s', os.arch: '%s', os.version: '%s', java.version: '%s'",
				host,
				ip,
				System.getProperty( "os.name" ),
				System.getProperty( "os.arch" ),
				System.getProperty( "os.version" ),
				System.getProperty( "java.version" ) );
	}

	private void takeScreenshot( HtmlDriver driver )
	{
		Photographer photographer = new Photographer( driver );
		Optional<ScreenshotAndHtmlSource> src = photographer.grabScreenshot();
		if( src.isPresent() )
		{
			if( src.get().wasTaken() )
			{
				addInfo( "screenshot name", src.get().getScreenshotName() );
				addInfo( "screenshot path", src.get().getScreenshotFile().getAbsolutePath() );
				if( src.get().getHtmlSource().isPresent() )
				{
					addInfo( "html source", src.get().getHtmlSourceName() );
				}
			}
		}
	}

	private void takeScreenshot( HtmlElement element )
	{
		Optional<ScreenshotAndHtmlSource> src = element.captureBitmap();
		if( src.isPresent() )
		{
			if( src.get().wasTaken() )
			{
				addInfo( "screenshot file", src.get().getScreenshotName() );
			}
		}
	}

	public void addInfo( String key, String value )
	{
		extraInfo.put( key, value );
	}

	public String getAdditionalInformation()
	{
		if( driver != null )
		{
			extraInfo.put( "driver.version", driver.getCapabilities().getVersion() );
			extraInfo.put( "browser.name", driver.getCapabilities().getBrowserName() );
		}
		String result = "";
		for ( Map.Entry<String, String> entry : extraInfo.entrySet() )
		{
			if ( entry.getValue() != null && entry.getValue().startsWith( entry.getKey() ) )
			{
				result += "\n" + entry.getValue();
			}
			else
			{
				result += "\n" + entry.getKey() + ": " + entry.getValue();
			}
		}
		return result;
	}

	@Override
	public String getMessage()
	{
		return createMessage( super.getMessage() );
	}

}
