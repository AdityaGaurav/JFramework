package com.framework.site.config;

import com.framework.asserts.CheckPointCollector;
import com.framework.asserts.CheckPointFactory;
import com.framework.asserts.CheckpointAssert;
import com.framework.asserts.JAssertion;
import com.framework.config.Configurations;
import com.framework.config.FrameworkConfiguration;
import com.framework.driver.event.HtmlDriver;
import com.framework.driver.event.HtmlElement;
import com.framework.site.pages.core.HomePage;
import com.framework.utils.conversion.Converter;
import com.framework.utils.error.PreConditions;
import org.apache.commons.lang3.math.NumberUtils;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.logging.Logs;
import org.openqa.selenium.remote.SessionId;
import org.openqa.selenium.remote.UnreachableBrowserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.Locale;
import java.util.Set;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.site.config
 *
 * Name   : SiteSession 
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-01-22 
 *
 * Time   : 11:39
 *
 */

public class SiteSessionManager implements CheckPointFactory
{

	//region SiteSessionManager - Variables Declaration and Initialization Section.

	private final Logger logger = LoggerFactory.getLogger( SiteSessionManager.class );

	private static volatile SiteSessionManager instance = null;

	private String lastWindowHandle;

	//private boolean highLightOnClick = true;

	//todo: documentation
	//private boolean highLightOnHover = true;

	private final FrameworkConfiguration configuration = Configurations.getInstance();

	private String hstValue;

	private long implicitlyWaitMillis;

	private long pageLoadTimeoutMillis;

	private long scriptExecutionTimeoutMillis;

	private HtmlDriver webDriver = null;

	private CheckPointCollector collector = new CheckPointCollector();

	private boolean lastTestCaseFailed = true;


	//endregion


	//region SiteSessionManager - Constructor Methods Section

	private SiteSessionManager()
	{
		//
	}

	public synchronized static SiteSessionManager get()
	{
		if ( instance == null )
		{
			instance = new SiteSessionManager();
		}
		return instance;
	}

	//endregion


	//region SiteSession - Service Methods Section

	public void closeSession()
	{
		try
		{
			if( getWindowHandles().size() == 1 )
			{
				getDriver().quit();
				this.webDriver = null;
			}
			else
			{
				getDriver().close();
			}
		}
		catch ( UnreachableBrowserException ubEx )
		{
			this.webDriver = null;
		}
	}

	public void endSession()
	{
		try
		{
			if( null != getDriver() )
			{
				getDriver().quit();
				this.webDriver = null;
			}
		}
		catch ( UnreachableBrowserException ubEx )
		{
			this.webDriver = null;
		}
	}

	public void startSession()
	{
		if( null == webDriver )
		{
			setWebDriver( Configurations.getInstance().getWebDriverFactory().createWebDriver( configuration ) );
			if ( currentScriptExecutionTimeoutMillis() != configuration.defaultPageLoadTimeout() )
			{
				setCurrentPageLoadTimeoutMillis( configuration.defaultPageLoadTimeout() );
			}
			if ( currentImplicitlyWaitMillis() != configuration.defaultImplicitlyWait() )
			{
				setCurrentImplicitlyWaitMillis( configuration.defaultImplicitlyWait() );
			}
			if ( currentPageLoadTimeoutMillis() != configuration.defaultPageLoadTimeout() )
			{
				setCurrentPageLoadTimeoutMillis( configuration.defaultPageLoadTimeout() );
			}

			if ( configuration.cleanAllCookiesOnStartUp() )
			{
				this.deleteAllCookies();
			}

			if ( configuration.maximizeAtStartUp() )
			{
				if( getDriver().getWrappedDriver() instanceof ChromeDriver )
				{
					getDriver().executeScript( "window.resizeTo( 1024, 768);" );
				}
				else
				{
					this.window().maximize();
				}
			}
		}
		else
		{
			throw new WebDriverException( "Session WebDriver was already initialized" );
		}
	}

	@Override
	public void assertAllCheckpoints()
	{
		PreConditions.checkNotNull( collector, "The collector is empty or null" );
		if( null != collector )
		{
			collector.assertAll();
		}
	}

	@Override
	public CheckpointAssert createCheckPoint( final String id )
	{
		return new CheckpointAssert( getDriver(), id, collector );
	}

	@Override
	public CheckpointAssert createCheckPoint( HtmlElement element, final String id )
	{
		return new CheckpointAssert( element, id, collector );
	}

	public FrameworkConfiguration getConfiguration()
	{
		return this.configuration;
	}

	//endregion


	//region SiteSessionManager - WebDriver Utility Methods Section

	public HtmlDriver.Window window()
	{
		return getDriver().manage().window();
	}

	public Set<String> getWindowHandles()
	{
		return getDriver().getWindowHandles();
	}

	public void deleteAllCookies()
	{
		getDriver().manage().deleteAllCookies();
	}

	public Cookie getCookieNamed( final String name )
	{
		return getDriver().manage().getCookieNamed( name );
	}

	public Logs logs()
	{
		return getDriver().manage().logs();
	}

	public Capabilities getCapabilities()
	{
		return getDriver().getCapabilities();
	}

	public SessionId getSessionId()
	{
		return getDriver().getSessionId();
	}

	public HtmlDriver.SessionStorage getSessionStorage()
	{
		return getDriver().sessionStorage();
	}

	public JAssertion createAssertion()
	{
		return new JAssertion( getDriver() );
	}

	//endregion


	//region SiteSession - SiteVariables Implementation Section

	public Locale getCurrentLocale()
	{
		return Configurations.getInstance().getLocale();
	}

	public URL getCurrentUrl()
	{
		return Converter.toURL( getDriver().getCurrentUrl() );
	}

	public boolean isHomePage()
	{
		return getCurrentUrl().equals( getBaseUrl() );
	}

	public boolean isSessionRunning()
	{
		return null != getDriver();
	}

	public URL getBaseUrl()
	{
		return getConfiguration().baseUrl();
	}

	public String getHstValue()
	{
		return hstValue;
	}

	public void setHstValue( final String hst )
	{
		this.hstValue = hst;
	}

	public HomePage getHomePage()
	{
		if( null != getDriver() )
		{
			return new HomePage();
		}
		else
		{
			startSession();
			return new HomePage();
		}
	}

	private long currentImplicitlyWaitMillis()
	{
		return implicitlyWaitMillis;
	}

	private void setCurrentImplicitlyWaitMillis( final long millis )
	{
		final String ERR_MSG = "invalid value < %d >. The millis value should be equal or greater than zero.";
		PreConditions.checkArgument( millis >= NumberUtils.LONG_ZERO, ERR_MSG, millis );
		this.implicitlyWaitMillis = millis;
		getDriver().manage().timeouts().implicitlyWait( millis );
	}

	private long currentPageLoadTimeoutMillis()
	{
		return pageLoadTimeoutMillis;
	}

	private void setCurrentPageLoadTimeoutMillis( final long millis )
	{
		final String ERR_MSG = "invalid value < %d >. The millis value should be equal or greater than zero.";
		PreConditions.checkArgument( millis >= NumberUtils.LONG_ZERO, ERR_MSG, millis );
		this.pageLoadTimeoutMillis = millis;
		getDriver().manage().timeouts().pageLoadTimeout( millis );
	}

	private long currentScriptExecutionTimeoutMillis()
	{
		return scriptExecutionTimeoutMillis;
	}

	private void setCurrentScriptExecutionTimeoutMillis( final long millis )
	{
		final String ERR_MSG = "invalid value < %d >. The millis value should be equal or greater than zero.";
		PreConditions.checkArgument( millis >= NumberUtils.LONG_ZERO, ERR_MSG, millis );
		this.scriptExecutionTimeoutMillis = millis;
		getDriver().manage().timeouts().setScriptTimeout( millis );
	}

	public boolean isLastTestCaseFailed()
	{
		return lastTestCaseFailed;
	}

	public void setLastTestCaseFailed( final boolean lastTestCaseFailed )
	{
		this.lastTestCaseFailed = lastTestCaseFailed;
	}

	//endregion

	public HtmlDriver getDriver()
	{
		if( null == webDriver ) return null;
		logger.debug( "returning an instance of HtmlDriver" );
		if( webDriver.getWindowHandles().size() == 2 )
		{
			if( null == lastWindowHandle )
			{
				lastWindowHandle = webDriver.getWindowHandle();
				webDriver.switchTo().window( getNewWindowHandle() );
			}
		}

		return this.webDriver;
	}

	private void setWebDriver( final HtmlDriver webDriver )
	{
		this.webDriver = webDriver;
	}

	private String getNewWindowHandle()
	{
		lastWindowHandle = webDriver.getWindowHandle();
		for( String handle : webDriver.getWindowHandles() )
		{
			if( ! handle.equals( lastWindowHandle ) )
			{
				logger.debug( "new window handle is < {} >", handle );
				return handle;
			}
		}

		return lastWindowHandle;
	}
}
