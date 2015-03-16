package com.framework.driver.factory;

import com.framework.config.Configurations;
import com.framework.config.FrameworkConfiguration;
import com.framework.config.FrameworkProperty;
import com.framework.driver.event.HtmlDriver;
import com.framework.driver.event.HtmlWebDriver;
import com.framework.driver.exceptions.ConfigurationRuntimeException;
import com.framework.utils.error.PreConditionException;
import com.framework.utils.error.PreConditions;
import com.framework.utils.spring.AppContextProxy;
import org.apache.commons.lang3.Validate;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.BrowserType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.driver.factory
 *
 * Name   : WebDriverFactory
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-01-17
 *
 * Time   : 22:25
 */

public class WebDriverFactory
{
	//region WebDriverFactory - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( WebDriverFactory.class );

	private WebDriverMetadata metadata;

	//endregion


	//region WebDriverFactory - Constructor Methods Section

	public WebDriverFactory( final String id )
	{
		try
		{
			PreConditions.checkNotNullNotBlankOrEmpty( id, "Driver is is either null, blank or empty!" );
			this.metadata = ( WebDriverMetadata ) AppContextProxy.get().getBean( id );
		}
		catch ( BeansException bEx )
		{
			logger.error( bEx.getMessage() );
			throw new ConfigurationRuntimeException( bEx );
		}
		catch ( IllegalArgumentException | NullPointerException ex )
		{
			logger.error( ex.getMessage() );
			throw new PreConditionException( ex );
		}
	}

	//endregion


	//region WebDriverFactory - Public Methods Section

	public HtmlDriver createWebDriver( FrameworkConfiguration configuration )
	{
		WebDriver newDriver = null;

		String browserType = metadata.getBrowserType();
		logger.info( "Creating a new web driver of type < '{}' >", browserType );
		switch ( browserType )
		{
			case BrowserType.FIREFOX:
			{
				FireFoxDriverBuilder ffx = new FireFoxDriverBuilder( metadata, configuration );
				newDriver = ffx.build();
				break;
			}
			case BrowserType.GOOGLECHROME:
			case BrowserType.CHROME:
			{
				ChromeDriverBuilder gch = new ChromeDriverBuilder( metadata, configuration );
				newDriver = gch.build();
				break;
			}
			case BrowserType.IE:
			case BrowserType.IEXPLORE:
				throw new WebDriverException( "Driver < '" + browserType + "' > is currently not supported" );
		}

		WebDriver wd = Validate.notNull( newDriver, "The required driver is currently null" );
		HtmlWebDriver hd = new HtmlWebDriver( wd );
		if( FrameworkProperty.JQUERY_INTEGRATION_ENABLED.from( Configurations.getInstance(), true ) )
		{
			hd.javascript().loadJQuery( null );
		}
		return hd;
	}

	//endregion


	//region WebDriverFactory - Protected Methods Section

	//endregion


	//region WebDriverFactory - Private Function Section

	//endregion

}
