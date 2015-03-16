package com.framework.driver.factory;

import com.framework.config.FrameworkConfiguration;
import com.framework.config.FrameworkProperty;
import com.framework.utils.string.ToLogStringStyle;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.driver.factory
 *
 * Name   : FireFoxDriverFactory
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-01-19
 *
 * Time   : 18:37
 */

public class FireFoxDriverBuilder //implements IWebDriverFactory
{

	//region FireFoxDriverFactory - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( FireFoxDriverBuilder.class );

	private final WebDriverMetadata metadata;

	private final FrameworkConfiguration configuration;

	/**
	 * Constant to firefox preference <b>browser.startup.page</b>.
	 * 0 = Start with a blank page (about:blank).
	 */
	private static final int START_WITH_BLANK_PAGE = 0;

	/**
	 * Constant to firefox preference <b>browser.startup.page</b>.
	 * 1 = Start with the web page(s) defined as the home page(s). ( Default )
	 */
	private static final int START_WITH_HOME_PAGE = 1;

	/**
	 * Constant to firefox preference <b>browser.startup.page</b>.
	 * 2 - Load the last visited page.
	 */
	private static final int LOAD_LAST_VISITED_PAGE = 2;

	/**
	 * Constant to firefox preference <b>browser.startup.page</b>.
	 * 3 - Resume the previous browser session
	 */
	private static final int RESUME_PREVIOUS_SESSION = 3;

	//endregion


	//region FireFoxDriverFactory - Constructor Methods Section

	public FireFoxDriverBuilder( final WebDriverMetadata metadata, FrameworkConfiguration config )
	{
		this.metadata = metadata;
		this.configuration = config;
	}

	//endregion


	//region FireFoxDriverFactory - Public Methods Section

	public FirefoxDriver build()
	{
		//metadata.getWebdriverSystemProperties();
		try
		{
			FirefoxProfile profile = buildProfile();
			setProfileExtensions( profile, metadata.getExtensions() );
			setProfilePreferences( profile, metadata.getFirefoxPreferences() );
			DesiredCapabilities capabilities = DesiredCapabilities.firefox();

			FirefoxBinary ffb = new FirefoxBinary();
			FirefoxDriver ff = new FirefoxDriver( ffb, profile, capabilities );
			ff.setLogLevel( Level.INFO );
			return ff;
			//todo add properties
//			ffDriver.getCapabilities().getVersion();
//			ffDriver.getCapabilities().getPlatform();
//			return ffDriver;
		}
		catch ( IOException | URISyntaxException e )
		{
			logger.error( e.getMessage() );
			throw new WebDriverException( e );
		}
	}

	//endregion

	//region FireFoxDriverFactory - Private Function Section

	private FirefoxProfile buildProfile()
	{
		FirefoxProfile profile;

		if( null != metadata.getFireFoxProfileName() )
		{
			ProfilesIni ini = new ProfilesIni();
			return ini.getProfile( metadata.getFireFoxProfileName() );
		}
		if( null != metadata.getFirefoxProfileFileName() )
		{
			File profileDir = new File( metadata.getFirefoxProfileFileName() );
			profile = new FirefoxProfile( profileDir );
			profile.clean( profileDir );
			profile.deleteExtensionsCacheIfItExists( profileDir );
		}
		else
		{
			profile = new FirefoxProfile();
			profile.setEnableNativeEvents(  metadata.isSetEnableNativeEvents() );
			profile.setAcceptUntrustedCertificates( metadata.isSetAcceptUntrustedCertificates() );
			profile.setAlwaysLoadNoFocusLib( metadata.isSetAlwaysLoadNoFocusLib() );
			profile.setAssumeUntrustedCertificateIssuer( metadata.isSetAssumeUntrustedCertificateIssue() );
		}

		//profile.updateUserPrefs()


		ToStringBuilder stringBuilder = new ToStringBuilder( profile, new ToLogStringStyle() )
				.append( "profile.areNativeEventsEnabled", profile.areNativeEventsEnabled() )
				.append( "profile.containsWebDriverExtension", profile.containsWebDriverExtension() )
				.append( "profile.shouldLoadNoFocusLib", profile.shouldLoadNoFocusLib() );
		logger.debug( stringBuilder.toString() );
		return profile;
	}

	private void setProfileExtensions( FirefoxProfile profile, Map<String,Boolean> extensions )
			throws URISyntaxException, IOException
	{
		for ( Map.Entry<String, Boolean> entry : extensions.entrySet() )
		{
			if ( entry.getValue() )
			{
				File extension = new File( ClassLoader.getSystemResource( entry.getKey() ).toURI() );
				profile.addExtension( extension );
			}
		}

		if( configuration.containsKey( FrameworkProperty.BROWSER_FIREFOX_FIREBUG_VERSION ) )
		{
			String firebug = ( String ) FrameworkProperty.BROWSER_FIREFOX_FIREBUG_VERSION.from( configuration );
			profile.setPreference( "extensions.firebug.currentVersion", firebug );
//			profile.setPreference( "extensions.firebug.showStackTrace", "true" );
//			profile.setPreference( "extensions.firebug.delayLoad", "false" );
//			profile.setPreference( "extensions.firebug.showFirstRunPage", "false" );
//			profile.setPreference( "extensions.firebug.allPagesActivation", "on" );
//			profile.setPreference( "extensions.firebug.console.enableSites", "true" );
//			profile.setPreference( "extensions.firebug.defaultPanelName", "console" );
		}
		if( configuration.containsKey( FrameworkProperty.BROWSER_FIREFOX_FIREBUG_VERSION ) )
		{
			String firepath = ( String ) FrameworkProperty.BROWSER_FIREFOX_FIREPATH_VERSION.from( configuration );
			profile.setPreference( "extensions.firepath.currentVersion", firepath );
		}
	}

	private void setProfilePreferences( FirefoxProfile profile, Properties preferences )
	{
		profile.setPreference( "browser.startup.homepage", configuration.baseUrl().toString() );
		profile.setPreference( "browser.startup.page", START_WITH_HOME_PAGE );


		if( false )
		{

			profile.setAcceptUntrustedCertificates(true);
			profile.setPreference("browser.download.folderList",2);//browser dir
			profile.setPreference("browser.download.dir","C:\\temp\\");
			profile.setPreference("browser.helperApps.neverAsk.saveToDisk","application/xml,text/plain,text/xml,image/jpeg,application/pdf");
			profile.setPreference("pdfjs.disabled", true );


			profile.setPreference("extensions.firebug.showStackTrace", "true");
			profile.setPreference("extensions.firebug.delayLoad", "false");
			profile.setPreference("extensions.firebug.showFirstRunPage", "false");
			profile.setPreference("extensions.firebug.allPagesActivation", "on");
			profile.setPreference("extensions.firebug.console.enableSites", "true");
			profile.setPreference("extensions.firebug.defaultPanelName", "console");
		}
	}


	//endregion

//		FirefoxProfile profile = new FirefoxProfile();
//		File logFile = new File("webdriver.log");
//		profile.setPreference("webdriver.log.file", logFile.getAbsolutePath());
//		WebDriver driver = new FirefoxDriver(profile);

//
//		FirefoxProfile profile = new FirefoxProfile();
//		profile.setPreference("webdriver.load.strategy", "fast");
//		driver = new FirefoxDriver(profile);

	//region FireFoxDriverFactory - Inner Classes Implementation Section

	//endregion

}
