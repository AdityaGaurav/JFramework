package com.framework;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.status.OnConsoleStatusListener;
import ch.qos.logback.core.status.StatusManager;
import ch.qos.logback.core.util.StatusPrinter;
import com.framework.driver.utils.PreConditions;
import com.framework.jreporter.TestReporter;
import com.framework.site.config.InitialPage;
import com.framework.site.data.TestEnvironment;
import com.google.common.base.Throwables;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.xml.XmlTest;

import java.util.Locale;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework
 *
 * Name   : BaseTest
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-01-06
 *
 * Time   : 23:36
 */

public class BaseTest
{

	//region BaseTest - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( BaseTest.class );

	private static final String SITE_SETTINGS = "/site.settings.xml";

	protected ApplicationContext appContext;

	//protected Locale currentLocale;

	//endregion

	public BaseTest()
	{
		logger.info( "Testing logger status ..." );
		LoggerContext lc = ( LoggerContext ) LoggerFactory.getILoggerFactory();
		StatusManager statusManager = lc.getStatusManager();
		OnConsoleStatusListener onConsoleListener = new OnConsoleStatusListener();
		statusManager.add( onConsoleListener );
		StatusPrinter.print( lc );
	}


	//region BaseTest - Before Methods Section


	@BeforeSuite ( description = "Starting the spring ApplicationContext context", alwaysRun = true )
	public void beforeSuite()
	{
		this.appContext = new ClassPathXmlApplicationContext( SITE_SETTINGS );
		InitialPage page = ( InitialPage ) appContext.getBean( "initial.page" );
		TestReporter.info( "initialized Spring Framework ApplicationContext and Suite Configuration ..." );
	}


	@Parameters ( { "current-locale", "current-environment", "browser-type" } )
	@BeforeTest ( description = "startup classes, application appContext and bundles", alwaysRun = true )
	public void beforeTest( String locale, String env, String brw, ITestContext testContext, XmlTest xmlTest ) throws Exception
	{
		try
		{
			TestReporter.debug( "Checking Test Suite Pre-conditions ..." );
			PreConditions.checkNotNullNotBlankOrEmpty( locale, "The currentLocale parameter is null, empty or blank." );
			PreConditions.checkNotNullNotBlankOrEmpty( env, "The environment parameter is null, empty or blank." );
			PreConditions.checkNotNullNotBlankOrEmpty( brw, "The browser type parameter is null, empty or blank." );

			Locale currentLocale = getLocale( locale );

			TestReporter.info( "Parameter test 'locale' is <'{}'>", currentLocale.getDisplayCountry() );
			TestReporter.info( "Parameter test 'environment' is <'{}'>", TestEnvironment.valueOf( env ).name() );
			TestReporter.info( "Parameter test 'browser' is <'{}'>", brw );

			testContext.setAttribute( "locale", currentLocale );
			testContext.setAttribute( "environment", TestEnvironment.valueOf( env ) );
			testContext.setAttribute( "browser", brw );

			Validate.notNull( currentLocale, "The initial Locale is null." );

			InitialPage.getInstance().setInitialLocale( currentLocale );
			InitialPage.getInstance().setTestEnvironment( TestEnvironment.valueOf( env ) );
			InitialPage.getInstance().setBrowserType( brw );
		}
		catch ( Throwable e )
		{
			TestReporter.error( e.getMessage() );
			Throwables.propagate( e );
		}
	}


	@AfterSuite( description = "Stopping LoggerContext", enabled = true, alwaysRun = true )
	public void afterSuite() throws Exception
	{
		LoggerContext loggerContext = ( LoggerContext ) LoggerFactory.getILoggerFactory();
		loggerContext.stop();
	}

	//endregion


	//region BaseTest - Private Functions Section

	private Locale getLocale( String locale )
	{
		if( locale.equals( "US" ) )
		{
			return Locale.US;
		}

		if( locale.equals( "UK" ) )
		{
			return Locale.UK;
		}

		if( locale.equals( "UK" ) )
		{
			return new Locale( "en", "AU" );
		}

		return null;
	}

	protected String getTestId( String methodName, ITestContext testContext )
	{
		ITestNGMethod[] testMethods = testContext.getAllTestMethods();
		for( ITestNGMethod method : testMethods )
		{
			if( method.getMethodName().equals( methodName ) )
			{
				return method.getId();
			}
		}

		return methodName;
	}

	//endregion

}
