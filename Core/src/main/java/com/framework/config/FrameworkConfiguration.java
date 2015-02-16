package com.framework.config;

import com.framework.driver.factory.WebDriverFactory;
import com.framework.driver.factory.WebDriverMetadata;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.testng.ISuite;
import org.testng.ITestContext;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;
import java.util.List;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.config
 *
 * Name   : FrameworkConfiguration 
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-01-20 
 *
 * Time   : 19:05
 *
 */

public interface FrameworkConfiguration extends Configuration
{
	Object getProperty( final Enum<?> property );

	boolean containsKey( final Enum<?> property );

	Boolean getBoolean( final Enum<?> property );

	Boolean getBoolean( final Enum<?> property, boolean defaultValue );

	Boolean getBoolean( final Enum<?> property, Boolean defaultValue );

	byte getByte( final Enum<?> property );

	byte getByte( final Enum<?> property, byte defaultValue );

	Byte getByte( final Enum<?> property, Byte defaultValue );

	double getDouble( final Enum<?> property );

	double getDouble( final Enum<?> property, double defaultValue );

	Double getDouble( final Enum<?> property, Double defaultValue );

	float getFloat( final Enum<?> property );

	float getFloat( final Enum<?> property, float defaultValue );

	Float getFloat( final Enum<?> property, Float defaultValue );

	int getInt( final Enum<?> property );

	int getInt( final Enum<?> property, int defaultValue );

	Integer getInteger( final Enum<?> property, Integer defaultValue );

	long getLong( final Enum<?> property );

	long getLong( final Enum<?> property, long defaultValue );

	Long getLong( final Enum<?> property, Long defaultValue );

	short getShort( final Enum<?> property );

	short getShort( final Enum<?> property, short defaultValue );

	Short getShort( final Enum<?> property, Short defaultValue );

	BigDecimal getBigDecimal( final Enum<?> property );

	BigDecimal getBigDecimal( final Enum<?> property, BigDecimal defaultValue );

	BigInteger getBigInteger( final Enum<?> property );

	BigInteger getBigInteger( final Enum<?> property, BigInteger defaultValue );

	String getString( final Enum<?> property );

	String getString( final Enum<?> property, String defaultValue );

	String[] getStringArray( final Enum<?> property );

	List<Object> getList( final Enum<?> property );

	List<Object> getList( final Enum<?> property, List<?> defaultValue );

	ISuite currentSuite();

	ITestContext currentTestContext();

	String driverId();

	URL baseUrl();

	String browserType();

	boolean storeHtmlSourceCode();

	String getOutputDirectory();

	boolean isSuiteFailed();

	void setSuiteStatus( ResultStatus status );

	//long getCurrentImplicitlyWait();

	long defaultImplicitlyWait();

	boolean maximizeAtStartUp();

	boolean cleanAllCookiesOnStartUp();

	//void setImplicitlyWait( final long millis );

	//long getCurrentPageLoadTimeout();

	long defaultPageLoadTimeout();

	//void setPageLoadTimeout( final long millis );

	//long getCurrentScriptTimeout();

	long defaultScriptTimeout();

	//void setScriptTimeout( final long millis );

	//void restoreImplicitlyWait();

	//void restorePageLoadTimeout();

	//void restoreScriptExecutionTimeout();

	String report( ToStringStyle style );

	ResultStatus getConfigurationStatus();

	void setConfigurationStatus( final ResultStatus configurationStatus );

	WebDriverMetadata getDriverMetadata();

	WebDriverFactory getWebDriverFactory();
}
