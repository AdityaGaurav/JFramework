package com.framework.driver.factory;

import com.framework.config.FrameworkConfiguration;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.URL;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.driver.factory
 *
 * Name   : ChromeDriverBuilder 
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-03-01 
 *
 * Time   : 21:32
 *
 */

public class ChromeDriverBuilder
{

	//region ChromeDriverBuilder - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( ChromeDriverBuilder.class );

	private final WebDriverMetadata metadata;

	private final FrameworkConfiguration configuration;

	/**
	 * Constant to firefox preference <b>browser.startup.page</b>.
	 * 1 = Start with the web page(s) defined as the home page(s). ( Default )
	 */
	private static final int START_WITH_HOME_PAGE = 1;

	//endregion


	//region ChromeDriverBuilder - Constructor Methods Section

	public ChromeDriverBuilder( final WebDriverMetadata metadata, FrameworkConfiguration config )
	{
		this.metadata = metadata;
		this.configuration = config;
	}

	//endregion


	//region ChromeDriverBuilder - Public Methods Section

	public ChromeDriver build()
	{
		try
		{
			DesiredCapabilities capabilities = DesiredCapabilities.chrome();
			URL url = ClassLoader.getSystemResource( metadata.getBinary() );
			System.setProperty( "webdriver.chrome.driver",  new File( url.toURI() ).getAbsolutePath() );
			ChromeOptions co = new ChromeOptions();
			//Map<String, Object> preferences = Maps.newHashMap();
			//preferences.put( "browser.startup.homepage", configuration.baseUrl().toString() );
			//preferences.put( "browser.startup.page", START_WITH_HOME_PAGE );
			//capabilities.setCapability( ChromeOptions.CAPABILITY, preferences );
			ChromeDriver driver = new ChromeDriver( capabilities );
			driver.get( configuration.baseUrl().toString() );
			return driver;
		}
		catch ( Exception e )
		{
			logger.error( e.getMessage() );
			throw new WebDriverException( e );
		}
	}

	//endregion


	//region ChromeDriverBuilder - Protected Methods Section

	//endregion


	//region ChromeDriverBuilder - Private Function Section

	//endregion


	//region ChromeDriverBuilder - Inner Classes Implementation Section

	//endregion

}
