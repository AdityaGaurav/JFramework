package com.framework.site.config;

import com.framework.driver.event.DriverListenerAdapter;
import com.framework.driver.event.ElementListenerAdapter;
import com.framework.driver.event.EventWebDriver;
import com.framework.driver.utils.ui.WaitUtil;
import com.framework.site.data.TestEnvironment;
import com.framework.site.pages.core.HomePage;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Locale;
import java.util.Map;
import java.util.Properties;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.site.config
 *
 * Name   : InitialPage
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-01-06
 *
 * Time   : 21:20
 */

public class InitialPage
{

	//region InitialPage - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( InitialPage.class );

	private static volatile InitialPage instance = null;

	//todo : do @AutoWired
	private static final Locale AU =  new Locale( "en", "AU" );

	//todo: documentation
	private static volatile RunTimeProperties runTimeProperties = new RunTimeProperties();

	//todo: documentation
	private EventWebDriver driver;

	//todo: documentation
	private Locale initialLocale = Locale.US;

	//todo: documentation
	private boolean highLightOnClick = true;

	//todo: documentation
	private boolean highLightOnHover = true;

	//todo: documentation
	private TestEnvironment testEnvironment = TestEnvironment.PROD;

	//todo: documentation
	private boolean useBrowserStartupPage = true;

	// specifies the starting url at setup. default EMPTY
	private String initialUrl = StringUtils.EMPTY;

	// a flag that indicates if the browser will maximize itself during setup. default is true.
	private boolean maximizeOnStartup = true;

	// the browser type to be used to run a test. by default is FIREFOX
	private String browserType = BrowserType.FIREFOX;

	// a list of domains urls, which the keys are defined as 'country.environment'
	private Properties domains;

	private String testCasePattern;

	//endregion


	//region InitialPage - Constructor Methods Section

	public synchronized static InitialPage getInstance()
	{
		if ( instance == null )
		{
			instance = new InitialPage();
		}

		return instance;
	}

	private InitialPage()
	{
		// singleton
	}

	//endregion


	//region InitialPage - Public Methods Section

	//todo: documentation
	public Locale getInitialLocale()
	{
		return initialLocale;
	}

	//todo: documentation
	public void setInitialLocale( final Locale initialLocale )
	{
		this.initialLocale = initialLocale;
	}

	//todo: documentation
	public boolean isHighLightOnClick()
	{
		return highLightOnClick;
	}

	//todo: documentation
	public void setHighLightOnClick( final boolean highLightOnClick )
	{
		this.highLightOnClick = highLightOnClick;
	}

	//todo: documentation
	public boolean isHighLightOnHover()
	{
		return highLightOnHover;
	}

	//todo: documentation
	public void setHighLightOnHover( final boolean highLightOnHover )
	{
		this.highLightOnHover = highLightOnHover;
	}

	//todo: documentation
	public TestEnvironment getTestEnvironment()
	{
		return testEnvironment;
	}

	//todo: documentation
	public void setTestEnvironment( final TestEnvironment testEnvironment )
	{
		this.testEnvironment = testEnvironment;
	}

	//todo: documentation
	public boolean isUseBrowserStartupPage()
	{
		return useBrowserStartupPage;
	}

	//todo: documentation
	public void setUseBrowserStartupPage( final boolean useBrowserStartupPage )
	{
		this.useBrowserStartupPage = useBrowserStartupPage;
	}

	//todo: documentation
	public boolean isMaximizeOnStartup()
	{
		return maximizeOnStartup;
	}

	//todo: documentation
	public void setMaximizeOnStartup( final boolean maximizeOnStartup )
	{
		this.maximizeOnStartup = maximizeOnStartup;
	}

	/**
	 * <pre>
	 *     usage: InitialPage.getInstance().getBrowserType();
	 * </pre>
	 *
	 * @return the default browser type defined in bean, or any browser type set later.
	 *
	 * @see #setBrowserType(String)
	 * @see org.openqa.selenium.remote.BrowserType
	 */
	public String getBrowserType()
	{
		return browserType;
	}

	/**
	 * Sets the working browser type for the test.
	 * this value can be set by loading bean.xml or during runtime.
	 * <p>
	 *     <pre>
	 *         usage: InitialPage.getInstance().setBrowserType( BrowserType.FIREFOX );
	 *     </pre>
	 * </p>
	 *
	 * @param browserType a browser type as defined in {@link org.openqa.selenium.remote.BrowserType}
	 */
	public void setBrowserType( final String browserType )
	{
		this.browserType = browserType;
	}

	//todo: documentation
	public EventWebDriver getEventDriver()
	{
		return driver;
	}

	//todo: documentation
	public String getInitialUrl()
	{
		return initialUrl;
	}

	//todo: documentation
	public Properties getDomains()
	{
		return domains;
	}

	//todo: documentation
	public void setDomains( final Properties domains )
	{
		this.domains = domains;
	}

	public String getTestCasePattern()
	{
		return testCasePattern;
	}

	public void setTestCasePattern( final String testCasePattern )
	{
		this.testCasePattern = testCasePattern;
		System.setProperty( "test.case.id.pattern", testCasePattern );
	}

	//todo: documentation
	@SuppressWarnings("unchecked")
	public HomePage getHomePage()
	{
		FirefoxDriver firefoxDriver = null;
		//ChromeDriver chromeDriver = null;
		//InternetExplorerDriver internetExplorerDriver = null;

		try
		{
			/* uploading initial settings to runtime properties sub-class */

			getRuntimeProperties().addRuntimeProperty( "environment", this.testEnvironment );
			getRuntimeProperties().addRuntimeProperty( "locale", this.initialLocale );
			getRuntimeProperties().addRuntimeProperty( "browser", this.browserType );

			/* building the key property key name, to get the value from Properties.domains */

			String propertyKey = String.format( "%s.%s",
					initialLocale.getCountry().toLowerCase(), testEnvironment.name().toLowerCase() );
			this.initialUrl = ( String ) domains.get( propertyKey );
			final String MSG_FMT = "Working url defined by environment <{}> and locale <{}> is <\"{}\">";
			logger.info( MSG_FMT,testEnvironment.name(), initialLocale.getDisplayCountry(), this.initialUrl  );


			WebDriver wrappedDriver;
			if( browserType.toLowerCase().equals( BrowserType.FIREFOX ) )
			{
				firefoxDriver = new FirefoxDriver(  );
				wrappedDriver = firefoxDriver;
			}
			else
			{
				throw new WebDriverException( "Driver \"{}\" is currently not supported" );
			}

			DriverListenerAdapter dla = new DriverListenerAdapter( );
			ElementListenerAdapter ela = new ElementListenerAdapter( );

			WebDriver wd = Validate.notNull( wrappedDriver, "The required driver is currently null" );

			this.driver = new EventWebDriver( wd, dla, ela );

			driver.get( initialUrl ); // todo: change with factory
			if( maximizeOnStartup )
			{
				this.driver.manage().window().maximize();
			}
			return new HomePage( this.driver );
		}
		catch ( Exception e )
		{
			WebDriverException wde = new WebDriverException( e.getMessage(), e );
			wde.addInfo( "causing procedure", "Trying to create a new WebDriver instance" );
			throw wde;
		}

	}

	//todo: documentation
	public void quitAllDrivers()
	{
		if( null != driver && driver.getSessionId() != null )
		{
			driver.close();
			if( driver.getSessionId() != null )
			{
				driver.quit();
			}
		}
	}

	//todo: documentation
	public static RunTimeProperties getRuntimeProperties()
	{
		return runTimeProperties;
	}

	//endregion

	//todo: documentation
	public static class RunTimeProperties
	{
		private Map<String,Object> props;

		public RunTimeProperties()
		{
			props = Maps.newConcurrentMap();
		}

		public Object getRuntimePropertyValue( String key )
		{
			return props.get( key );
		}

		public void addRuntimeProperty( String key, Object value )
		{
			props.put( key, value );
		}
	}


	public WebDriverWait wait5()
	{
		return WaitUtil.wait5( driver );
	}
	public WebDriverWait wait10()
	{
		return WaitUtil.wait10( driver );
	}
	public WebDriverWait wait20()
	{
		return WaitUtil.wait20( driver );
	}
	public WebDriverWait wait30()
	{
		return WaitUtil.wait30( driver );
	}
	public WebDriverWait wait60()
	{
		return WaitUtil.wait60( driver );
	}


	//region InitialPage - Private Functions Section



	//endregion
}
