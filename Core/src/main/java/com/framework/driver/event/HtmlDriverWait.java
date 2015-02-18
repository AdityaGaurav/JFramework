package com.framework.driver.event;

import com.framework.utils.datetime.TimeConstants;
import com.framework.utils.error.PreConditions;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Clock;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Sleeper;
import org.openqa.selenium.support.ui.SystemClock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.driver.event
 *
 * Name   : HtmlDriverWait 
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-02-05 
 *
 * Time   : 16:56
 *
 */

public class HtmlDriverWait extends FluentWait<HtmlDriver> implements TimeConstants
{

	//region HtmlDriverWait - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( HtmlDriverWait.class );

	public final static long DEFAULT_SLEEP_TIMEOUT = 500;

	private final HtmlDriver driver;

	//endregion


	//region HtmlDriverWait - Constructor Methods Section

	public HtmlDriverWait( HtmlDriver driver, long timeOutInSeconds )
	{
		this( driver, new SystemClock(), Sleeper.SYSTEM_SLEEPER, timeOutInSeconds, DEFAULT_SLEEP_TIMEOUT );
	}

	public HtmlDriverWait( HtmlDriver driver, long timeOutInSeconds, long sleepInMillis )
	{
		this( driver, new SystemClock(), Sleeper.SYSTEM_SLEEPER, timeOutInSeconds, sleepInMillis );
	}

	public HtmlDriverWait( HtmlDriver driver, Clock clock, Sleeper sleeper, long timeOutInSeconds, long sleepTimeOut )
	{
		super( driver, clock, sleeper );
		withTimeout( timeOutInSeconds, TimeUnit.SECONDS );
		pollingEvery( sleepTimeOut, TimeUnit.MILLISECONDS );
		ignoring( NotFoundException.class );
		this.driver = driver;
	}

	//endregion


	//region HtmlDriverWait - Protected Methods Section

	@Override
	protected RuntimeException timeoutException( String message, Throwable lastException )
	{
		TimeoutException ex = new TimeoutException( message, lastException );
		ex.addInfo( WebDriverException.DRIVER_INFO, driver.getClass().getName() );
		if ( driver instanceof RemoteWebDriver )
		{
			RemoteWebDriver remote = ( RemoteWebDriver ) driver;
			if ( remote.getSessionId() != null )
			{
				ex.addInfo( WebDriverException.SESSION_ID, remote.getSessionId().toString() );
			}
			if ( remote.getCapabilities() != null )
			{
				ex.addInfo( "Capabilities", remote.getCapabilities().toString() );
			}
		}
		throw ex;
	}

	//endregion


	//HtmlDriverWait WaitUtil - Service Methods Section

	/**
	 * Static convenience method that returns a {@linkplain org.openqa.selenium.support.ui.WebDriverWait} instance of 5 seconds
	 *
	 * @param driver a valid {@link org.openqa.selenium.WebDriver} instance.
	 *
	 * @return an instance of {@code WebDriverWait} set to five ( 5 ) seconds
	 *
	 * @throws com.framework.utils.error.PreConditionException in case driver is null.
	 */
	public static HtmlDriverWait wait5( HtmlDriver driver )
	{
		long timeoutSeconds = FIVE_SECONDS;
		long sleepInMillis = TEN_HUNDRED_MILLIS;
		logger.debug( "creating a new WebDriverWait[{}/{}]", timeoutSeconds, sleepInMillis );
		return new HtmlDriverWait( driver, timeoutSeconds, sleepInMillis );
	}

	/**
	 * Static convenience method that returns a {@linkplain org.openqa.selenium.support.ui.WebDriverWait} instance of 10 seconds and
	 * 1000 milliseconds of polling interval.
	 *
	 * @param driver a valid {@link org.openqa.selenium.WebDriver} instance.
	 *
	 * @return an instance of {@code WebDriverWait} set to five ( 10 ) seconds and 1000 millis polling interval.
	 *
	 * @throws com.framework.utils.error.PreConditionException in case driver is null.
	 */
	public static HtmlDriverWait wait10( HtmlDriver driver )
	{
		PreConditions.checkNotNull( driver, "WebDriver argument cannot be null" );

		long timeoutSeconds = TEN_SECONDS;
		long sleepInMillis = FIFTEEN_HUNDRED_MILLIS;
		logger.debug( "creating a new WebDriverWait[{}/{}]", timeoutSeconds, sleepInMillis );
		return new HtmlDriverWait( driver, timeoutSeconds, sleepInMillis );
	}

	/**
	 * Static convenience method that returns a {@linkplain org.openqa.selenium.support.ui.WebDriverWait} instance of 20 seconds and
	 * 2000 milliseconds of polling interval.
	 *
	 * @param driver a valid {@link org.openqa.selenium.WebDriver} instance.
	 *
	 * @return an instance of {@code WebDriverWait} set to five ( 20 ) seconds and 2000 millis polling interval.
	 *
	 * @throws com.framework.utils.error.PreConditionException in case driver is null.
	 */
	public static HtmlDriverWait wait20( HtmlDriver driver )
	{
		PreConditions.checkNotNull( driver, "WebDriver argument cannot be null" );

		long timeoutSeconds = TWENTY_SECONDS;
		long sleepInMillis = TWENTY_FIVE_HUNDRED_MILLIS;
		logger.debug( "creating a new WebDriverWait[{}/{}]", timeoutSeconds, sleepInMillis );
		return new HtmlDriverWait( driver, timeoutSeconds, sleepInMillis );
	}

	/**
	 * Static convenience method that returns a {@linkplain org.openqa.selenium.support.ui.WebDriverWait} instance of 30 seconds and
	 * 3000 milliseconds of polling interval.
	 *
	 * @param driver a valid {@link org.openqa.selenium.WebDriver} instance.
	 *
	 * @return an instance of {@code WebDriverWait} set to five ( 30 ) seconds and 3000 millis polling interval.
	 *
	 * @throws com.framework.utils.error.PreConditionException in case driver is null.
	 */
	public static HtmlDriverWait wait30( HtmlDriver driver )
	{
		PreConditions.checkNotNull( driver, "WebDriver argument cannot be null" );

		long timeoutSeconds = HALF_MINUTE;
		long sleepInMillis = MILLIS_IN_THREE_SECONDS;
		logger.debug( "creating a new WebDriverWait[{}/{}]", timeoutSeconds, sleepInMillis );
		return new HtmlDriverWait( driver, timeoutSeconds, sleepInMillis );
	}

	/**
	 * Static convenience method that returns a {@linkplain org.openqa.selenium.support.ui.WebDriverWait} instance of 60 seconds and
	 * 10000 milliseconds of polling interval.
	 *
	 * @param driver a valid {@link org.openqa.selenium.WebDriver} instance.
	 *
	 * @return an instance of {@code WebDriverWait} set to five ( 30 ) seconds and 10000 millis polling interval.
	 *
	 * @throws com.framework.utils.error.PreConditionException in case driver is null.
	 */
	public static HtmlDriverWait wait60( HtmlDriver driver )
	{
		PreConditions.checkNotNull( driver, "WebDriver argument cannot be null" );

		long timeoutSeconds = HALF_MINUTE * 2;
		long sleepInMillis = MILLIS_IN_ONE_SECONDS * 2;
		logger.debug( "creating a new HtmlDriverWait[{}/{}]", timeoutSeconds, sleepInMillis );
		return new HtmlDriverWait( driver, timeoutSeconds, sleepInMillis );

	}

	public static HtmlDriverWait wait( HtmlDriver driver, long timeoutSeconds )
	{
		PreConditions.checkNotNull( driver, "WebDriver argument cannot be null" );
		PreConditions.checkArgument( timeoutSeconds > 0, "The timeout should be greater than zero." );

		long sleepInMillis = ( timeoutSeconds * 1000 ) / 5;
		logger.debug( "creating a new HtmlDriverWait[{}/{}]", timeoutSeconds, sleepInMillis );
		return new HtmlDriverWait( driver, timeoutSeconds, sleepInMillis );
	}

	//endregion
}
