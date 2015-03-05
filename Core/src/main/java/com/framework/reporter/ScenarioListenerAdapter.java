package com.framework.reporter;

import com.framework.asserts.JAssertion;
import com.framework.config.Configurations;
import com.framework.config.FrameworkConfiguration;
import com.framework.config.FrameworkProperty;
import com.framework.config.ResultStatus;
import com.framework.driver.factory.WebDriverFactory;
import com.framework.testing.annotations.Steps;
import com.framework.testing.steping.*;
import com.framework.utils.string.LogStringStyle;
import com.framework.utils.string.TextArt;
import com.google.common.base.Optional;
import org.apache.commons.configuration.ConfigurationException;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.*;
import org.testng.internal.IResultListener2;

import java.io.File;
import java.lang.reflect.Method;

import static com.framework.config.FrameworkProperty.JFRAMEWORK_BASE_REPORTS_DIRECTORY;
import static com.framework.config.FrameworkProperty.REPORT_DIRECTORY_PATTERN;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.reporter
 *
 * Name   : BaseReporter 
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-02-18 
 *
 * Time   : 19:35
 *
 */

// ┌────────────────────────────────────────────────────────────────────┐ \\
// │ Raphaël 2.1.3 - JavaScript Vector Library                          │ \\
// ├────────────────────────────────────────────────────────────────────┤ \\
// │ Copyright © 2008-2012 Dmitry Baranovskiy (http://raphaeljs.com)    │ \\
// │ Copyright © 2008-2012 Sencha Labs (http://sencha.com)              │ \\
// ├────────────────────────────────────────────────────────────────────┤ \\
// │ Licensed under the MIT (http://raphaeljs.com/license.html) license.│ \\
// └────────────────────────────────────────────────────────────────────┘ \\

class ScenarioListenerAdapter implements IExecutionListener, ISuiteListener, IResultListener2, StepListener
{

	//region ScenarioListenerAdapter - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( ScenarioListenerAdapter.class );

	private enum HeadingStyle { ASCII, OLD_BANNER, DELTA_CORPS }

	private Scenario scenarioManager;

	protected FrameworkConfiguration configuration;

	private boolean enableStepping = false, haveStepsAnnotation = false;

	//endregion


	//region ScenarioListenerAdapter - Constructor Methods Section

	ScenarioListenerAdapter()
	{
		this.configuration = Configurations.getInstance();
	}

	//endregion


	//region ScenarioListenerAdapter - IExecutionListener Implementation Section

	/**
	 * Invoked before the TestNG run starts.
	 */
	@Override
	public final void onExecutionStart()
	{
		/* Logging a formatted message */

		String style = FrameworkProperty.JFRAMEWORK_SUITE_START_STYLE.from( configuration, HeadingStyle.DELTA_CORPS.name() );
		logger.info( TextArt.SUITE_STARTED_HEADINGS.get( HeadingStyle.valueOf( style ).ordinal() ) );
		String defaults = configuration.report( LogStringStyle.LOG_MULTI_LINE_STYLE );

		logger.info( "Starting suite execution. initialize SiteConfigurations with default values -> \n{}", defaults );

		// setting the output directory to Configurations
		Configurations.getInstance().setOutputDirectory( generateOutputDirectoryName() );
		logger.info( "Output directory is -> \"{}\"", configuration.getOutputDirectory() );

		// create a new instance of Scenario manager
		this.scenarioManager = new Scenario( DateTime.now() );
		logger.info( "new Scenario manager created -> {}", this.scenarioManager );
	}


	@Override
	public void onExecutionFinish()
	{
		// set the scenario end date
		this.scenarioManager.recordEndDate();

		// determine the scenario status
		boolean failed = this.scenarioManager.isFailed();

		if( ! failed )
		{
			String style = FrameworkProperty.JFRAMEWORK_SUITE_SUCCESS_STYLE.from( configuration, HeadingStyle.DELTA_CORPS.name() );
			logger.info( TextArt.SUITE_PASSED_HEADINGS.get( HeadingStyle.valueOf( style ).ordinal() ) );
		}
		else
		{
			String style = FrameworkProperty.JFRAMEWORK_SUITE_FAILED_STYLE.from( configuration, HeadingStyle.DELTA_CORPS.name() );
			logger.error( TextArt.SUITE_FAILED_HEADINGS.get( HeadingStyle.valueOf( style ).ordinal() ) );
		}
	}

	//endregion


	//region ScenarioListenerAdapter - ISuiteListener Implementation Section

	/**
	 * This method is invoked before the SuiteRunner starts.

	 * @param suite a {@linkplain org.testng.ISuite} instance.
	 */
	@Override
	public void onStart( final ISuite suite )
	{
		boolean startSuite = false;

		if( scenarioManager.getCurrentSuite() == null )
		{
			startSuite = true;
		}
		else if( ! scenarioManager.getCurrentSuite().getName().equals( suite.getName() ) )
		{
			startSuite = true;
		}

		if( startSuite )
		{
			logger.info( TextArt.getSuiteStart( suite.getName() ) );

			Suite suiteManager = new Suite( DateTime.now(), suite );
			scenarioManager.pushSuite( suiteManager );
			Configurations.getInstance().init( suite );
			scenarioManager.setExcludedTestCases( suite.getExcludedMethods() );

			logger.info( "new Suite manager created -> {}", suiteManager );
		}
	}

	/**
	 * This method is invoked after the SuiteRunner has run all the test suites.
	 *
	 * @param suite a {@linkplain org.testng.ISuite} instance.
	 */
	@Override
	public void onFinish( final ISuite suite )
	{
		scenarioManager.getCurrentSuite().recordEndDate();
		//		if( currentSuiteUtil > 0 )
		//		{
		//			getCurrentSuite().setEndTime( DateTime.now() );
		//			currentSuiteUtil = NumberUtils.LONG_MINUS_ONE;
		//
		//			logger.info( "Finished Suite < '{}' >", suite.getName() );
		//			Configurations.getInstance().setEndSuite( suite );
		//		}
	}

	//endregion


	//region ScenarioListenerAdapter - IConfigurationListener2 Implementation Section

	@Override
	public void beforeConfiguration( final ITestResult itr )
	{
		scenarioManager.pushConfigurationInstance( itr );

		if( itr.getMethod().isBeforeSuiteConfiguration() )
		{
			// registering test context to configuration this required for driver initialization
			//logger.debug( "Registering ITestContext < '{}' > in Configurations...", testContext.getName() );
			Configurations.getInstance().init( itr.getTestContext().getSuite() );
		}
		if ( itr.getMethod().isBeforeTestConfiguration() )
		{
			WebDriverFactory factory = new WebDriverFactory( configuration.driverId() );
			Configurations.getInstance().setWebDriverFactory( factory );
		}
		else if ( itr.getMethod().isBeforeClassConfiguration() )
		{
			enableStepping = configuration.getBoolean( FrameworkProperty.JFRAMEWORK_ENABLE_STEPPING.getPropertyName(), false );
		}
	}

	@Override
	public void onConfigurationSkip( final ITestResult itr )
	{
		//this.scenarioUtil.increaseSkippedConfigurations();
		logger.debug( "configuration skipped: {}", scenarioManager.increaseSkippedConfigurationInstanceCounters( itr ) );
	}

	@Override
	public void onConfigurationSuccess( final ITestResult itr )
	{
		logger.debug( "configuration ended: {}", scenarioManager.increaseSuccessConfigurationInstanceCounters( itr ).toString() );

		if( itr.getMethod().isBeforeClassConfiguration() )
		{
			if( enableStepping )
			{
				StepEventBus.getEventBus().registerListener( this );
			}
		}
	}

	public void onConfigurationFailure( ITestResult itr )
	{
		Throwable t = itr.getThrowable();
		if ( t != null )
		{
			LoggerFactory.getLogger( itr.getTestClass().getRealClass() ).error( t.toString(), t );
		}

		logger.debug( "configuration ended: ", scenarioManager.increaseFailedConfigurationInstanceCounters( itr ) );

		if( itr.getMethod().isBeforeSuiteConfiguration() || itr.getMethod().isAfterMethodConfiguration() )
		{
			logger.debug( "Scenario updated -> {}", scenarioManager );
		}
	}

	//endregion


	//region ScenarioListenerAdapter - ITestListener Implementation Section

	/**
	 * Invoked each time before a test will be invoked.
	 * The <code>ITestResult</code> is only partially filled with the references to
	 * class, method, start millis and status.
	 *
	 * @param itr 	the partially filled {@code ITestResult}
	 *
	 * @see org.testng.ITestResult#STARTED
	 */
	@Override
	public void onTestStart( final ITestResult itr )
	{
		logger.info( TextArt.getTestCaseStart( String.format( "'%s'", itr.getName() ) ) );
		TestCase tc = scenarioManager.pushTestCase( itr );
		haveStepsAnnotation = true;
		if( ! tc.isAnnotationsParsed() )
		{
			tc.parseAnnotations();
			if( enableStepping )
			{
				Optional<Steps> annotatedSteps = determineStepAnnotations( itr );
				if ( ! annotatedSteps.isPresent() )
				{
					logger.info( "No @Step annotations were found for method \"{}\"", itr.getMethod().getMethodName() );
					haveStepsAnnotation = false;
					return;
				}
				try
				{
					StepsAnnotation stepsAnnotation = new StepsAnnotation( annotatedSteps.get() );
					tc.registerSteps( stepsAnnotation );
					logger.debug( "Test-case have < {} > registered step/steps.", stepsAnnotation.getMap().size() );
					onTestCaseStart( tc, itr );
				}
				catch ( ConfigurationException e )
				{
					logger.warn( e.getMessage() );
				}
			}
		}
		else
		{
			onTestCaseStart( tc, itr );
		}
	}

	@Override
	public void onTestSuccess( final ITestResult tr )
	{
		scenarioManager.increaseSuccessTestCaseInstanceCounters( tr );
		if( enableStepping && haveStepsAnnotation )
		{
			StepEventBus.getEventBus().testCaseEnded( tr );
		}
	}

	@Override
	public void onTestFailure( final ITestResult tr )
	{
		Throwable t = tr.getThrowable();
		if ( t != null )
		{
			LoggerFactory.getLogger( tr.getTestClass().getRealClass() ).error( t.toString(), t );
		}
		logger.error( "test-case failed: ", scenarioManager.increaseFailedTestCaseInstanceCounters( tr ) );

		if( enableStepping && haveStepsAnnotation )
		{
			StepEventBus.getEventBus().testCaseEnded( tr );
		}
	}

	@Override
	public void onTestSkipped( final ITestResult tr )
	{
		scenarioManager.increaseSkippedTestCaseInstanceCounters( tr );
		if( enableStepping && haveStepsAnnotation )
		{
			StepEventBus.getEventBus().testCaseEnded( tr );
		}
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage( final ITestResult tr )
	{
		scenarioManager.increaseFailedWithSuccessPercentageTestCaseInstanceCounters( tr );
		if( enableStepping && haveStepsAnnotation )
		{
			StepEventBus.getEventBus().testCaseEnded( tr );
		}
	}

	/**
	 * Invoked after the test class is instantiated and before any configuration method is called.
	 */
	@Override
	public void onStart( final ITestContext testContext )
	{
		logger.info( TextArt.getTestStart( testContext.getName() ) );

		TestContext testContextManager = new TestContext( DateTime.now(), testContext, scenarioManager.getCurrentSuite() );
		scenarioManager.pushTestContext( testContextManager );
		logger.debug( "new Test-Context manager created -> {}", testContextManager );

		final String MSG = "Configurations were uploaded successfully. current configuration is -> < '{}' >";
		logger.info( MSG, Configurations.getInstance().toString() );
	}

	@Override
	public void onFinish( final ITestContext testContext )
	{
		scenarioManager.getCurrentSuite().getCurrentTestContext().recordEndDate();
	}

	//endregion


	//region ScenarioListenerAdapter - StepListener Implementation Section

	@Override
	public void onTestCaseStart( final TestCase testCase, ITestResult itr )
	{
		/** initializes the {@link com.framework.testing.steping.StepEventBus} */
		StepEventBus.getEventBus().testCaseStarted( testCase, itr );
	}

	@Override
	public void onTestStepStart( final float number, TestCaseInstance instance )
	{
		TestCase tc = instance.getParentTestCase();
		logger.debug( "Parent test case is < {} >", tc.getName() );
		TestStep tp = tc.getSteps().get( number );
		logger.debug( "injecting Registered step is < {} >", tp.getNumber() );
		TestStepInstance tsi = instance.injectTestStep( tp );
		tsi.setStatuses( ResultStatus.STARTED );
		logger.debug( "TestStepInstance injected ->  {} >", tsi.toString() );
	}

	@Override
	public void onTestStepEnd( final float number, TestCaseInstance instance )
	{
		TestStepInstance tsi = instance.getTestStepInstance( number );
		tsi.recordEndDate();
		tsi.setStatuses( ResultStatus.ENDED );
		logger.debug( "TestStepInstance updated -> < {} >", tsi.toString() );
	}

	@Override
	public void onTestStepSuccess( final float number, TestCaseInstance instance )
	{
		TestStepInstance tsi = instance.getTestStepInstance( number );
		tsi.setStatuses( ResultStatus.SUCCESS );
		logger.debug( "TestStepInstance updated -> < {} >", tsi.toString() );
	}

	@Override
	public void onTestStepFailure( final float number, TestCaseInstance instance, final Throwable cause )
	{
		TestStepInstance tsi = instance.getTestStepInstance( number );
		tsi.setStatuses( ResultStatus.FAILURE );
		tsi.addThrowable( cause );
		logger.debug( "TestStepInstance updated -> < {} >", tsi.toString() );
		tsi.reportErrors( logger );
	}

	@Override
	public void onTestStepSkipped( final float number )
	{

	}

	@Override
	public void onTestCaseEnd( final ITestResult result )
	{

	}

	@Override
	public void assumptionViolated( final String message )
	{

	}

	@Override
	public void onCheckpointStarted( final Checkpoint checkpoint, TestStepInstance tsi )
	{
		tsi.injectCheckpoint( checkpoint );
		checkpoint.seStatus( ResultStatus.STARTED );
		logger.debug( "new checkpoint created -> {}", checkpoint.toString() );
	}

	@Override
	public void onCheckPointSuccess( final Checkpoint checkpoint )
	{
		checkpoint.seStatus( ResultStatus.SUCCESS );
	}

	@Override
	public void onCheckpointFailed( final JAssertion assertion, final Checkpoint checkpoint )
	{
		checkpoint.seStatus( ResultStatus.FAILURE );
		checkpoint.setAssertionError( assertion.getAssertionError() );
		if( assertion.getSnapshot().isPresent() )
		{
			checkpoint.setScreenshot( assertion.getSnapshot().get() );
		}
		logger.debug( "checkpoint result -> {}", checkpoint.toString( LogStringStyle.LOG_MULTI_LINE_STYLE ) );
	}

	private Optional<Steps> determineStepAnnotations( ITestResult itr )
	{
		Class<?> clazz = itr.getMethod().getRealClass();
		Method method = itr.getMethod().getConstructorOrMethod().getMethod();
		TestAnnotations annotations = TestAnnotations.forClass( clazz );
		return annotations.getAnnotatedStepsForMethod( method );
	}

	//endregion


	//region ScenarioListenerAdapter - Service Methods Section

	protected Scenario getScenarioManager()
	{
		return scenarioManager;
	}

	//endregion


	//region BaseReporter - Private Methods Section

	@SuppressWarnings ( "ResultOfMethodCallIgnored" )
	private String generateOutputDirectoryName()
	{
		String defaultOutputDirectory = "";//suite.getOutputDirectory();  //todo: temporary

		File reports = new File( configuration.getString( JFRAMEWORK_BASE_REPORTS_DIRECTORY, defaultOutputDirectory ) );
		if( ! reports.exists() )
		{
			reports.mkdirs();
		}

		String pattern = configuration.getString( REPORT_DIRECTORY_PATTERN, "MMddyyyHHmmss" );
		File timestamped = new File( reports, new DateTime().toString( pattern ) );
		return "/Users/solmarkn/output/reports/lastReport";//timestamped.getAbsolutePath();  //todo: temporary
	}

	//endregion




}
