package com.framework.driver.factory;

import com.framework.config.Configurations;
import com.framework.config.FrameworkProperty;
import com.framework.utils.string.LogStringStyle;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.openqa.selenium.Platform;

import java.util.Map;
import java.util.Properties;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.config
 *
 * Name   : DriverBean 
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-01-21 
 *
 * Time   : 18:44
 *
 */

public class WebDriverMetadata
{

	//region DriverBean - Variables Declaration and Initialization Section.

	private final String id;

	private final String browserType;

	private Map<String,Boolean> extensions;

	private String firefoxProfileFileName = null;

	private String fireFoxProfileName = null;

	private Map<String,String> webdriverSystemProperties;

	private Properties firefoxPreferences;

	private Properties webdriverPreferences;

	private boolean setEnableNativeEvents = true;

	private boolean setAcceptUntrustedCertificates = false;

	private boolean setAlwaysLoadNoFocusLib = false;

	private boolean setAssumeUntrustedCertificateIssue = false;

	private String binary;

	//endregion


	//region DriverBean - Constructor Methods Section

	public WebDriverMetadata( final String id, final String browserType )
	{
		this.id = id;
		this.browserType = browserType;
	}

	//endregion


	//region DriverBean - Public Methods Section

	public String getId()
	{
		return id;
	}

	public Map<String,Boolean> getExtensions()
	{
		return extensions;
	}

	public void setExtensions( final Map<String,Boolean> extensions )
	{
		this.extensions = extensions;
	}

	public String getFirefoxProfileFileName()
	{
		return firefoxProfileFileName;
	}

	public void setFirefoxProfileFileName( final String firefoxProfileFileName )
	{
		this.firefoxProfileFileName = firefoxProfileFileName;
	}

	public String getBrowserType()
	{
		return browserType;
	}

	public Map<String, String> getWebdriverSystemProperties()
	{
		return webdriverSystemProperties;
	}

	public void setWebdriverSystemProperties( final Map<String, String> webdriverSystemProperties )
	{
		this.webdriverSystemProperties = webdriverSystemProperties;
	}

	public boolean isSetEnableNativeEvents()
	{
		return setEnableNativeEvents;
	}

	public void setSetEnableNativeEvents( final boolean setEnableNativeEvents )
	{
		this.setEnableNativeEvents = setEnableNativeEvents;
	}

	public String getFireFoxProfileName()
	{
		return fireFoxProfileName;
	}

	public void setFireFoxProfileName( final String fireFoxProfileName )
	{
		this.fireFoxProfileName = fireFoxProfileName;
	}

	public Properties getFirefoxPreferences()
	{
		return firefoxPreferences;
	}

	public void setFirefoxPreferences( final Properties firefoxPreferences )
	{
		this.firefoxPreferences = firefoxPreferences;
	}

	public boolean isSetAcceptUntrustedCertificates()
	{
		return setAcceptUntrustedCertificates;
	}

	public void setSetAcceptUntrustedCertificates( final boolean setAcceptUntrustedCertificates )
	{
		this.setAcceptUntrustedCertificates = setAcceptUntrustedCertificates;
	}

	public boolean isSetAlwaysLoadNoFocusLib()
	{
		return setAlwaysLoadNoFocusLib;
	}

	public void setSetAlwaysLoadNoFocusLib( final boolean setAlwaysLoadNoFocusLib )
	{
		this.setAlwaysLoadNoFocusLib = setAlwaysLoadNoFocusLib;
	}

	public boolean isSetAssumeUntrustedCertificateIssue()
	{
		return setAssumeUntrustedCertificateIssue;
	}

	public void setSetAssumeUntrustedCertificateIssue( final boolean setAssumeUntrustedCertificateIssue )
	{
		this.setAssumeUntrustedCertificateIssue = setAssumeUntrustedCertificateIssue;
	}

	public Properties getWebdriverPreferences()
	{
		return webdriverPreferences;
	}

	public void setWebdriverPreferences( final Properties webdriverPreferences )
	{
		this.webdriverPreferences = webdriverPreferences;
	}

	public String getBinary()
	{
		if( Platform.getCurrent().equals( Platform.MAC ) )
		{
			return Configurations.getInstance().getString( FrameworkProperty.GOOGLE_CHROME_MAC_WEB_DRIVER_FILE );
		}
		if( Platform.getCurrent().equals( Platform.WINDOWS ) )
		{
			return Configurations.getInstance().getString( FrameworkProperty.GOOGLE_CHROME_WIN_WEB_DRIVER_FILE );
		}

		return null;
	}

	public void setBinary( final String binary )
	{
		this.binary = binary;
	}

	@Override
	public String toString()
	{
		return new ToStringBuilder( this, LogStringStyle.LOG_LINE_STYLE )
				.append( "id", id )
				.toString();
	}

	//endregion

}
