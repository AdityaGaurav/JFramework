package com.framework;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.status.OnConsoleStatusListener;
import ch.qos.logback.core.status.StatusManager;
import ch.qos.logback.core.util.StatusPrinter;
import com.framework.config.Configurations;
import com.framework.testing.steping.TestReporter;
import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.BrowserType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.SkipException;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.xml.XmlTest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class BaseTest
{

	//region BaseTest - Variables Declaration and Initialization Section.

	protected static Logger classLogger = LoggerFactory.getLogger( BaseTest.class );

	protected final com.framework.testing.steping.LoggerProvider logger = new TestReporter( classLogger );

	static
	{
		classLogger.info( "Testing classLogger status ..." );

		LoggerContext lc = ( LoggerContext ) LoggerFactory.getILoggerFactory();
		StatusManager statusManager = lc.getStatusManager();
		OnConsoleStatusListener onConsoleListener = new OnConsoleStatusListener();
		statusManager.add( onConsoleListener );
		StatusPrinter.print( lc );
	}

	//endregion


	//region BaseTest - Constructor Section.

	public BaseTest()
	{
		//
	}

	//endregion


	//region BaseTest - Before Methods Section

	@BeforeSuite( description = "Initializes the registered listener loggers", enabled = true, alwaysRun = true )
	public static void beforeSuite( ITestContext testContext, XmlTest xmlTest ) throws Exception
	{
		Reporter.log("@BeforeSuite");
	}


	@BeforeTest ( description = "startup classes, application appContext and bundles", alwaysRun = true, enabled = true )
	public void beforeTest( ITestContext testContext, XmlTest xmlTest ) throws Exception
	{
		classLogger.debug( "Executing Test index < {} > on test: < '{}' >", xmlTest.getIndex(), xmlTest.getName() );
	}


	@AfterSuite( description = "Stopping LoggerContext", enabled = true, alwaysRun = true )
	public void afterSuite() throws Exception
	{
		LoggerContext loggerContext = ( LoggerContext ) LoggerFactory.getILoggerFactory();
		loggerContext.stop();
	}

	//endregion


	//region BaseTest - Private Functions Section

	protected void killExistingBrowsersOpenedByWebDriver()
	{
		final String FIREFOX_MAC_PROCESS = "MacOS/firefox-bin";

		Process kill = null;

		if( Platform.getCurrent().equals( Platform.MAC ) )
		{
			Pattern pattern = Pattern.compile( "([0-9]+)" );
			String processBuffer, processName = null;
			BufferedReader input;
			if( Configurations.getInstance().browserType().equals( BrowserType.FIREFOX ) )
			{
				processName = FIREFOX_MAC_PROCESS;
			}
			else if( Configurations.getInstance().browserType().equals( BrowserType.CHROME ) )
			{
				processName = "Chrome";
			}


			if( processName == null )
			{
				return;
			}

			/**
			 *   getRuntime: Returns the runtime object associated with the current Java application.
			 *   xec: Executes the specified string command in a separate process.
			 */

			try
			{
				Process process = Runtime.getRuntime().exec( "ps -ax" );
				input = new BufferedReader( new InputStreamReader( process.getInputStream() ) );
				while ( ( processBuffer = input.readLine() ) != null )
				{
					if ( processBuffer.contains( processName ) )
					{
						Matcher matcher = pattern.matcher( processBuffer );
						if ( matcher.find() )
						{
							classLogger.info( "Killing existing firefox process < {} > that was created by web driver...", matcher.group( 0 ) );
							kill = Runtime.getRuntime().exec( "kill " + matcher.group( 0 ) );
							int response = kill.waitFor();
							classLogger.debug( "Response from kill command is < {} >", response );
						}
					}
				}
				input.close();
			}
			catch ( IOException | InterruptedException e )
			{
				classLogger.warn( "Killing firefox instance throw an error -> '{}' ", e.getMessage() );
				if( kill != null )
				{
					try
					{
						InputStream error = kill.getErrorStream();
						for ( int i = 0; i < error.available(); i++ )
						{
							System.out.println( "" + error.read() );
						}
					}
					catch ( IOException e1 )
					{
						//
					}
				}
			}
			finally
			{
				if ( kill != null ) kill.destroy();
			}
		}
	}

	public <T> T skipIfNull( T reference, Object errorMessage )
	{
		if ( reference == null )
		{
			throw new SkipException( errorMessage.toString() );
		}
		return reference;
	}

	protected boolean shouldCloseSession( ITestResult testResult )
	{
		return testResult.getStatus() == ITestResult.FAILURE
				|| testResult.getStatus() == ITestResult.SUCCESS_PERCENTAGE_FAILURE
				|| testResult.getStatus() == ITestResult.SKIP;
	}

	//endregion

}
