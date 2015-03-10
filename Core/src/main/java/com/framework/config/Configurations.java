package com.framework.config;

import com.framework.driver.exceptions.UnsupportedDriverException;
import com.framework.driver.factory.WebDriverFactory;
import com.framework.driver.factory.WebDriverMetadata;
import com.framework.utils.conversion.Converter;
import com.framework.utils.string.ToLogStringStyle;
import org.apache.commons.configuration.*;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.event.ConfigurationErrorListener;
import org.apache.commons.configuration.event.ConfigurationListener;
import org.apache.commons.configuration.reloading.InvariantReloadingStrategy;
import org.apache.commons.configuration.tree.NodeCombiner;
import org.apache.commons.configuration.tree.OverrideCombiner;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.BrowserType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.testng.ISuite;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.annotations.*;

import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import static com.framework.config.FrameworkProperty.*;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.site.config
 *
 * Name   : SiteConfigurations
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-01-17
 *
 * Time   : 20:44
 */

public class Configurations extends CompositeConfiguration implements FrameworkConfiguration
{

	//region Configurations - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( Configurations.class );

	private static Configurations instance = null;

	// ------------------------------------------------ DEFAULTS CONSTANTS --------------------------------------------------------

	/**
	 * Constant for the default test environment as string representation
	 */
	private static final String DEFAULT_TEST_ENVIRONMENT_CODE = "PROD";

	/**
	 * Constant for the default tested site url as string representation
	 */
	private static final String DEFAULT_BASE_URL = "http://www.carnival.com/";

	/**
	 * Constant for the default browser to be used.
	 */
	private static final String DEFAULT_BROWSER = BrowserType.FIREFOX;

	/**
	 * Constant for the user preferences configuration file name
	 */
	private static final String DEFAULT_PROPERTIES = "config/default.jframework.properties";

	/**
	 * Constant for the user preferences configuration file name, that might overrides the defaults
	 */
	public static final String USER_PROPERTIES_PREFERENCES = "jframework.properties";

	/**
	 * a constant that defines the default driver id, if none was specified.
	 * the driver id is defined in drivers.xml file.
	 */
	public static final String DEFAULT_DRIVER_ID = "firefox.default";

	/**
	 * Constant for the user preferences configuration properties file name
	 */
	private static final String DEFAULT_PARAMETER_PREFIX = "testng.param-";

	/**
	 * Constant for the site properties file location that contains unique information about the testing site
	 */
	private static final String SITE_PROPERTIES = "site.properties";


	/**
	 * Constant for the default locale country code
	 */
	private static final String DEFAULT_LOCALE_COUNTRY = "US";

	// ------------------------------------------------ MEMBERS --------------------------------------------------------

	private String locale = DEFAULT_LOCALE_COUNTRY;

	private String currentBrowser = DEFAULT_BROWSER;

	private String baseUrl = DEFAULT_BASE_URL;

	private String driverId = DEFAULT_DRIVER_ID;

	/**
	 * The testng suite to be executed.
	 *
	 * @see org.testng.ISuite
	 */
	//private ISuite suite;

	/**
	 * The testng test context to be executed.
	 *
	 * @see org.testng.ITestContext
	 */
	private ITestContext testContext;

	ApplicationContext applicationContext = null;

	//private EventWebDriver eventWebDriver;

	private WebDriverFactory webDriverFactory;

	private WebDriverMetadata driverMetadata;

	private String outputDirectory;

	//endregion


	//region Configurations - Constructor Methods Section

	public static synchronized Configurations getInstance()
	{
		if ( instance == null )
		{
			instance = new Configurations();
		}

		return instance;
	}

	private Configurations()
	{
		// adding listeners

		ConfigurationListenerAdapter listener = new ConfigurationListenerAdapter( logger );
		super.addErrorListener( listener );
		super.addConfigurationListener( listener );
		super.setThrowExceptionOnMissing( true );
		super.setDetailEvents( true );

		// initialize instance with System amd Environment Configurations

		super.addConfiguration( new SystemConfiguration(), false );
		logger.debug( "Configuration < 'SystemConfiguration' > added. NumberOfConfigurations: < {} >", super.getNumberOfConfigurations() );
		super.addConfiguration( new EnvironmentConfiguration(), false );
		logger.debug( "Configuration < 'EnvironmentConfiguration' > added. NumberOfConfigurations: < {} >", super.getNumberOfConfigurations() );


		// reading default framework configuration

		PropertiesConfiguration defaults = addDefaultConfiguration();
		defaults.addConfigurationListener( listener );
		defaults.addErrorListener( listener );
		createFolders( defaults );

		// loading site properties

		PropertiesConfiguration siteProps = ( PropertiesConfiguration ) addSiteProperties();
		siteProps.addConfigurationListener( listener );
		siteProps.addErrorListener( listener );

		// reading local preferences

		PropertiesConfiguration preferences = ( PropertiesConfiguration ) addLocalPreferences();
		if( null != preferences )
		{
			Configuration combined = combineConfigurations( defaults, siteProps, preferences );
			addConfiguration( combined );
			logger.debug( "Configuration < 'CombinedConfiguration' > added. NumberOfConfigurations: < } >", super.getNumberOfConfigurations() );
		}
		else
		{
			addConfiguration( defaults, true );
			addConfiguration( siteProps );
			logger.debug( "Configuration < 'PropertiesConfiguration' > added. NumberOfConfigurations: < } >", super.getNumberOfConfigurations() );
		}

		// loading spring applications context locations.
		String[] locations = getStringArray( JFRAMEWORK_SPRING_FRAMEWORK_LOCATIONS );
		this.applicationContext = new ClassPathXmlApplicationContext( locations );

		ToStringBuilder tsb = new ToStringBuilder( this.applicationContext, ToLogStringStyle.LOG_MULTI_LINE_STYLE )
				.append( "application name", applicationContext.getApplicationName() )
				.append( "application id", applicationContext.getId() )
				.append( "display name", applicationContext.getDisplayName() )
				.append( "environment", applicationContext.getEnvironment().toString() )
				.append( "startup date", applicationContext.getStartupDate() );

		logger.debug( "context was loaded {}", tsb.toString() );

	}

	//endregion


	//region Configurations - Service Methods Section

	/**
	 * @param method the <b>ITestNGMethod</b> method.
	 *
	 * @return the method annotation name
	 *
	 * @see org.testng.ITestResult#getMethod()
	 */
	public static String getAnnotation( ITestNGMethod method )
	{
		if( method.isBeforeSuiteConfiguration() ) 	return BeforeSuite.class.getSimpleName();
		if( method.isBeforeTestConfiguration() )   	return BeforeTest.class.getSimpleName();
		if( method.isBeforeClassConfiguration() )   return BeforeClass.class.getSimpleName();
		if( method.isBeforeGroupsConfiguration() )  return BeforeGroups.class.getSimpleName();
		if( method.isBeforeMethodConfiguration() )  return BeforeMethod.class.getSimpleName();
		if( method.isAfterClassConfiguration() )    return AfterClass.class.getSimpleName();
		if( method.isAfterGroupsConfiguration() )   return AfterGroups.class.getSimpleName();
		if( method.isAfterMethodConfiguration() )   return AfterMethod.class.getSimpleName();
		if( method.isAfterTestConfiguration() )     return AfterTest.class.getSimpleName();
		if( method.isAfterSuiteConfiguration() )    return AfterSuite.class.getSimpleName();
		return StringUtils.EMPTY;
	}

	/**
	 * Terminates the class by removing listeners and configurations
	 */
	public void terminate()
	{
		// removing all listeners and configurations

		Collection<ConfigurationListener> ls = super.getConfigurationListeners();
		for( ConfigurationListener cl : ls )
		{
			super.removeConfigurationListener( cl );
		}
		Collection<ConfigurationErrorListener> le = super.getErrorListeners();
		for( ConfigurationErrorListener cl : le )
		{
			super.removeErrorListener( cl );
		}
		super.removeConfiguration( super.getInMemoryConfiguration() );
		for( int i = 0; i < super.getNumberOfConfigurations(); i ++ )
		{
			super.removeConfiguration( super.getConfiguration( i ) );
		}

		locale = null;
		currentBrowser = baseUrl = driverId = null;
	}

	//endregion


	//region Configurations - Runtime Configuration Methods Section

	@Override
	public long defaultImplicitlyWait()
	{
		return getLong( WEBDRIVER_TIMEOUTS_IMPLICITLY_WAIT_MILLIS, 5000 );
	}

	@Override
	public boolean maximizeAtStartUp()
	{
		return getBoolean( BROWSER_MAXIMIZE_AT_STARTUP, false );
	}

	@Override
	public boolean cleanAllCookiesOnStartUp()
	{
		return getBoolean( WEBDRIVER_CLEAR_ALL_COOKIES, false );
	}

	@Override
	public long defaultPageLoadTimeout()
	{
		return getLong( WEBDRIVER_TIMEOUTS_PAGE_LOAD_TIMEOUT_MILLIS );
	}

	@Override
	public long defaultScriptTimeout()
	{
		return getLong( WEBDRIVER_TIMEOUTS_SCRIPT_TIMEOUT_MILLIS );
	}

	//endregion


	//region Configurations - FrameworkConfiguration Implementation Section

	@Override
	public final boolean containsKey( final Enum<?> property )
	{
		return super.containsKey( property.toString() );
	}

	@Override
	public final Object getProperty( final Enum<?> property )
	{
		return super.getProperty( property.toString() );
	}

	@Override
	public final Boolean getBoolean( final Enum<?> property )
	{
		return super.getBoolean( property.toString() );
	}

	@Override
	public final Boolean getBoolean( final Enum<?> property, final boolean defaultValue )
	{
		return super.getBoolean( property.toString(), defaultValue );
	}

	@Override
	public final Boolean getBoolean( final Enum<?> property, final Boolean defaultValue )
	{
		return super.getBoolean( property.toString() );
	}

	@Override
	public final byte getByte( final Enum<?> property )
	{
		return super.getByte( property.toString() );
	}

	@Override
	public final byte getByte( final Enum<?> property, final byte defaultValue )
	{
		return super.getByte( property.toString(), defaultValue );
	}

	@Override
	public final Byte getByte( final Enum<?> property, final Byte defaultValue )
	{
		return super.getByte( property.toString(), defaultValue );
	}

	@Override
	public final double getDouble( final Enum<?> property )
	{
		return super.getDouble( property.toString() );
	}

	@Override
	public final double getDouble( final Enum<?> property, final double defaultValue )
	{
		return super.getDouble( property.toString(), defaultValue );
	}

	@Override
	public final Double getDouble( final Enum<?> property, final Double defaultValue )
	{
		return super.getDouble( property.toString(), defaultValue );
	}

	@Override
	public final float getFloat( final Enum<?> property )
	{
		return super.getFloat( property.toString() );
	}

	@Override
	public final float getFloat( final Enum<?> property, final float defaultValue )
	{
		return super.getFloat( property.toString(), defaultValue );
	}

	@Override
	public final Float getFloat( final Enum<?> property, final Float defaultValue )
	{
		return super.getFloat( property.toString(), defaultValue );
	}

	@Override
	public final int getInt( final Enum<?> property )
	{
		return super.getInt( property.toString() );
	}

	@Override
	public final int getInt( final Enum<?> property, final int defaultValue )
	{
		return super.getInt( property.toString(), defaultValue );
	}

	@Override
	public final Integer getInteger( final Enum<?> property, final Integer defaultValue )
	{
		return super.getInteger( property.toString(), defaultValue );
	}

	@Override
	public long getLong( final Enum<?> property )
	{
		return super.getLong( property.toString() );
	}

	@Override
	public final long getLong( final Enum<?> property, final long defaultValue )
	{
		return super.getLong( property.toString(), defaultValue );
	}

	@Override
	public final Long getLong( final Enum<?> property, final Long defaultValue )
	{
		return super.getLong( property.toString(), defaultValue );
	}

	@Override
	public final short getShort( final Enum<?> property )
	{
		return super.getShort( property.toString() );
	}

	@Override
	public final short getShort( final Enum<?> property, final short defaultValue )
	{
		return super.getShort( property.toString(), defaultValue );
	}

	@Override
	public final Short getShort( final Enum<?> property, final Short defaultValue )
	{
		return super.getShort( property.toString(), defaultValue );
	}

	@Override
	public final BigDecimal getBigDecimal( final Enum<?> property )
	{
		return super.getBigDecimal( property.toString() );
	}

	@Override
	public final BigDecimal getBigDecimal( final Enum<?> property, final BigDecimal defaultValue )
	{
		return super.getBigDecimal( property.toString(), defaultValue );
	}

	@Override
	public final BigInteger getBigInteger( final Enum<?> property )
	{
		return super.getBigInteger( property.toString() );
	}

	@Override
	public final BigInteger getBigInteger( final Enum<?> property, final BigInteger defaultValue )
	{
		return super.getBigInteger( property.toString(), defaultValue );
	}

	@Override
	public final String getString( final Enum<?> property )
	{
		return super.getString( property.toString() );
	}

	@Override
	public final String getString( final Enum<?> property, final String defaultValue )
	{
		return super.getString( property.toString(), defaultValue );
	}

	@Override
	public final String[] getStringArray( final Enum<?> property )
	{
		return super.getStringArray( property.toString() );
	}

	@Override
	public final List<Object> getList( final Enum<?> property )
	{
		return super.getList( property.toString() );
	}

	@Override
	public final List<Object> getList( final Enum<?> property, final List<?> defaultValue )
	{
		return super.getList( property.toString(), defaultValue );
	}

	//endregion


	//region Configurations - Member Getters and Setters Section

//	@Override
//	public ISuite currentSuite()
//	{
//		return suite;
//	}
//
//	@Override
//	public ITestContext currentTestContext()
//	{
//		return testContext;
//	}

	public void init( final ISuite suite )
	{
		this.testContext = testContext;

		String driverId = suite.getXmlSuite().getParameter( "driver-id" );
		if( null != driverId )
		{
			setDriverId( driverId );

			// loading bean xml using springframework classes
			setDriverMetadata( ( WebDriverMetadata ) this.applicationContext.getBean( this.driverId ) );
			setCurrentBrowser( getDriverMetadata().getBrowserType() );
			validateBrowserSupportedByCurrentPlatform();

			// define current locale
			addCurrentLocale( suite );

			// define base url
			addBaseUrl();
		}
	}

	public Locale getLocale()
	{
		if( locale.equals( "UK" ) ) return Locale.UK;
		return Converter.toLocale( "en_" + locale );
	}

	public String getTestEnvironment()
	{
		return 	getString( "current.environment.id", DEFAULT_TEST_ENVIRONMENT_CODE );
	}

	@Override
	public String browserType()
	{
		return currentBrowser;
	}

	@Override
	public boolean storeHtmlSourceCode()
	{
		return false;
	}

	@Override
	public String getOutputDirectory()
	{
		return outputDirectory;
	}

	public void setOutputDirectory( final String outputDirectory )
	{
		this.outputDirectory = outputDirectory;
	}

	@Override
	public URL baseUrl()
	{
		return Converter.toURL( baseUrl ) ;
	}

	private void setBaseUrl( final String baseUrl )
	{
		logger.debug( "baseUrl was set to < '{}' >", baseUrl );
		this.baseUrl = baseUrl;
	}

	@Override
	public String driverId()
	{
		return driverId;
	}

	private void setDriverId( final String driverId )
	{
		logger.debug( "driver id was set to < '{}' >", driverId );
		this.driverId = driverId;
	}

	private void setLocale( final String locale )
	{
		logger.debug( "locale was set to < '{}' >", locale );
		this.locale = locale;
	}

	public WebDriverMetadata getDriverMetadata()
	{
		return driverMetadata;
	}

	private void setDriverMetadata( final WebDriverMetadata driverMetadata )
	{
		logger.debug( "driverMetadata was set to < '{}' >", driverMetadata );
		this.driverMetadata = driverMetadata;
	}

	public void setCurrentBrowser( final String currentBrowser )
	{
		logger.debug( "currentBrowser was set to < '{}' >", currentBrowser );
		this.currentBrowser = currentBrowser;
	}

//	public List<String> getRegisteredSuiteListeners()
//	{
//		return this.suite.getXmlSuite().getListeners();
//	}

	public WebDriverFactory getWebDriverFactory()
	{
		return webDriverFactory;
	}

	public void setWebDriverFactory( final WebDriverFactory webDriverFactory )
	{
		this.webDriverFactory = webDriverFactory;
	}

	//endregion


	//region Configurations - Private Functions Section

	/**
	 * Loads the default properties configuration {@linkplain #DEFAULT_PROPERTIES}
	 *
	 * @return an instance of {@linkplain PropertiesConfiguration}
	 */
	private PropertiesConfiguration addDefaultConfiguration()
	{
		try
		{
			PropertiesConfiguration props = new PropertiesConfiguration();
			props.setReloadingStrategy( new InvariantReloadingStrategy() );
			props.setThrowExceptionOnMissing( true );
			props.setFileSystem( new DefaultFileSystem() );
			props.setEncoding( SystemUtils.FILE_ENCODING );
			props.setIncludesAllowed( true );
			props.load( DEFAULT_PROPERTIES );

			logger.info( "Configuration file < '{}' > was loaded successfully!", props.getBasePath() );
			return props;
		}
		catch ( Exception cEx )
		{
			logger.error( cEx.getMessage() );
			throw new com.framework.driver.exceptions.ConfigurationRuntimeException( cEx );
		}
	}

	private Configuration addLocalPreferences()
	{
		// Loading properties files from different paths

		try
		{
			LocalPreferences localPreferences = new PropertiesFileLocalPreferences( new SystemConfiguration() );
			File preferences = localPreferences.locatePreferences();
			logger.info( "Local preferences file {}found", preferences == null ?  "not " : StringUtils.EMPTY );
			if( preferences != null )
			{
				logger.debug( "loading local preferences files ..." );

				PropertiesConfiguration props = new PropertiesConfiguration();
				props.setReloadingStrategy( new InvariantReloadingStrategy() );
				props.setThrowExceptionOnMissing( true );
				props.setFileSystem( new DefaultFileSystem() );
				props.setEncoding( SystemUtils.FILE_ENCODING );
				props.setIncludesAllowed( true );
				props.load( preferences );
				props.addConfigurationListener( new ConfigurationListenerAdapter( logger ) );
				logger.info( "Local Preferences file < '{}' > was loaded successfully!", props.getBasePath() );
				return props;
			}

			return null;
		}
		catch ( Exception ioEx )
		{
			logger.error( ioEx.getMessage() );
			throw new com.framework.driver.exceptions.ConfigurationRuntimeException( ioEx );
		}
	}

	private Configuration combineConfigurations( PropertiesConfiguration... configs)
	{
		NodeCombiner combiner = new OverrideCombiner();

		CombinedConfiguration combinedConfiguration = new CombinedConfiguration( combiner, new Lock( "Configurations" ) );
		combinedConfiguration.setDetailEvents( true );
		combinedConfiguration.setForceReloadCheck( false );
		combinedConfiguration.setForceReloadCheck( false );
		for( PropertiesConfiguration c : configs )
		{
			combinedConfiguration.addConfiguration( c );
		}

		return combinedConfiguration;
	}

	private Configuration addSiteProperties()
	{
		try
		{
			PropertiesConfiguration props = new PropertiesConfiguration();
			props.setReloadingStrategy( new InvariantReloadingStrategy() );
			props.setThrowExceptionOnMissing( true );
			props.setFileSystem( new DefaultFileSystem() );
			props.setEncoding( SystemUtils.FILE_ENCODING );
			props.setIncludesAllowed( true );
			props.load( SITE_PROPERTIES );
			logger.info( "Configuration file < '{}' > was loaded successfully!", props.getBasePath() );
			return props;
		}
		catch ( Exception cEx )
		{
			logger.error( cEx.getMessage() );
			throw new com.framework.driver.exceptions.ConfigurationRuntimeException( cEx );
		}
	}

	private void validateBrowserSupportedByCurrentPlatform()
	{
		// Verifies that the requested browser type is Internet Explorer, and is supported by current platform

		if ( currentBrowser.equals( BrowserType.IE ) || currentBrowser.equals( BrowserType.IEXPLORE ) )
		{
			boolean isMac = Platform.getCurrent().equals( Platform.MAC );
			boolean isUnix = Platform.getCurrent().equals( Platform.UNIX );
			boolean isLinux = Platform.getCurrent().equals( Platform.LINUX );
			boolean isAnyOf = BooleanUtils.isNotTrue( BooleanUtils.or( new Boolean[] { isMac, isUnix, isLinux } ) );

			try
			{
				final String ERR_MSG = "< '%s' > is not supported on < '%s' >";
				Validate.validState( isAnyOf, ERR_MSG, this.currentBrowser, Platform.getCurrent().name() );
			}
			catch ( IllegalStateException isEx )
			{
				logger.error( isEx.getMessage() );
				throw new UnsupportedDriverException( isEx.getMessage(), isEx );
			}
		}
	}

	private void addCurrentLocale( ISuite suite )
	{
		List<String> groups = suite.getXmlSuite().getIncludedGroups();
		List<Object> supportedLocales = getList( "supported.locales" );
		String locale = null;

		for( Object o : supportedLocales )
		{
			String l = ( String ) o;
			if( ! groups.contains( l ) ) continue;
			locale = l;
			break;
		}

		setLocale( StringUtils.defaultString( locale, DEFAULT_LOCALE_COUNTRY ) );
	}

	private void addBaseUrl()
	{
		String key = String.format( "%s.%s.url", locale.toLowerCase(), getTestEnvironment().toLowerCase() );
		if( ! containsKey( key ) )
		{
			throw new ConfigurationRuntimeException( "Invalid environment key " + key );
		}
		setBaseUrl( getString( key, DEFAULT_BASE_URL ) );
	}

	@SuppressWarnings ( "ResultOfMethodCallIgnored" )
	private void createFolders( PropertiesConfiguration def )
	{
		String folder = def.getString( FrameworkProperty.JFRAMEWORK_BASE_LAST_REPORT_DIRECTORY.getPropertyName() );
		File f = new File( folder );
		if( ! f.exists() ) f.mkdirs();
		folder = def.getString( FrameworkProperty.JFRAMEWORK_BASE_LOGS_DIRECTORY.getPropertyName() );
		f = new File( folder );
		if( ! f.exists() ) f.mkdirs();
		folder = def.getString( FrameworkProperty.JFRAMEWORK_BASE_SCREENSHOTS_DIRECTORY.getPropertyName() );
		f = new File( folder );
		if( ! f.exists() ) f.mkdirs();
		folder = def.getString( FrameworkProperty.JFRAMEWORK_DATA_DIR.getPropertyName() );
		f = new File( folder );
		if( ! f.exists() ) f.mkdirs();
	}

	//endregion

	@Override
	public String toString()
	{
		return new ToStringBuilder( this, ToLogStringStyle.LOG_LINE_STYLE )
				.append( "locale", locale )
				.append( "testEnvironment", getTestEnvironment() )
				.append( "currentBrowser", currentBrowser )
				.append( "baseUrl", baseUrl )
				.append( "driverId", driverId )
				.toString();
	}

	public String report( ToStringStyle style )
	{
		return new ToStringBuilder( this, style )
				.append( "locale", locale )
				.append( "testEnvironment", getTestEnvironment() )
				.append( "currentBrowser", currentBrowser )
				.append( "baseUrl", baseUrl )
				.append( "driverId", driverId )
				.toString();
	}
}
