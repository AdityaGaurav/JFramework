package com.framework.testing.steping;

import com.framework.asserts.JAssert;
import com.framework.asserts.JAssertion;
import com.framework.config.Configurations;
import com.framework.config.FrameworkProperty;
import com.framework.config.ResultStatus;
import com.framework.driver.utils.ui.screenshots.ScreenshotProcessor;
import com.framework.driver.utils.ui.screenshots.SingleThreadScreenshotProcessor;
import com.framework.reporter.TestCaseInstance;
import com.framework.testing.steping.exceptions.EventBusEventException;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;

import java.util.List;
import java.util.Stack;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.testing.steping
 *
 * Name   : StepEventBus 
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-01-26 
 *
 * Time   : 01:07
 *
 */

public class StepEventBus
{

	//region StepEventBus - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( StepEventBus.class );

	private static ThreadLocal<StepEventBus> stepEventBusThreadLocal = new ThreadLocal<StepEventBus>();

	private List<StepListener> registeredListeners = Lists.newArrayList();

	private final ScreenshotProcessor screenshotProcessor;

	private String assumptionViolatedMessage;

	private TestCaseInstance testCaseInstance;

	private ITestResult testResult;

	private static boolean isSteppingEnabled = false;

	//private StepListener adapter;

	//todo: to be examined if required
	private Stack<Float> stepStack = new Stack<Float>();

	//endregion


	//region StepEventBus - Constructor Methods Section

	public StepEventBus( ScreenshotProcessor screenshotProcessor )
	{
		this.screenshotProcessor = screenshotProcessor;
	}

	public static synchronized StepEventBus getEventBus()
	{
		if ( stepEventBusThreadLocal.get() == null )
		{
			isSteppingEnabled = Configurations.getInstance().getBoolean( FrameworkProperty.JFRAMEWORK_ENABLE_STEPPING.getPropertyName(), false );
			stepEventBusThreadLocal.set( new StepEventBus( new SingleThreadScreenshotProcessor() ) );
		}
		return stepEventBusThreadLocal.get();
	}

	//endregion


	//region StepEventBus - Listener Handling Methods Section

	public StepEventBus registerListener( final StepListener listener )
	{
		if ( ! registeredListeners.contains( listener ) )
		{
			logger.debug( "registering listener < '{}' >", listener );
			registeredListeners.add( listener );
		}
		return this;
	}

	public void dropAllListeners()
	{
		registeredListeners.clear();
	}

	private List<StepListener> getAllListeners()
	{
		List<StepListener> allListeners = Lists.newArrayList( registeredListeners );
		return ImmutableList.copyOf( allListeners );
	}

	//endregion


	//region StepEventBus - Event Firing Methods Section

	public void testCaseStarted( final com.framework.reporter.TestCase testCase, ITestResult itr )
	{
		if( isSteppingEnabled )
		{
			clear();
			this.testCaseInstance = testCase.getCurrentTestCaseInstance();
			logger.debug( "Stepping process for test-case instance -> {}", testCaseInstance.toString() );
			this.testResult = itr;
		}
	}

	public void stepStarted( float stepNumber )
	{
		if( !isSteppingEnabled ) return;

		logger.debug( "Posting event \"stepStarted\" with argument < {} > ", String.format( "%05f", stepNumber ) );
		if( stepStack.isEmpty() )
		{
			logger.debug( "New step was added -> {}", stepNumber );
		}
		else
		{
			Float prev = stepStack.peek();
			stepFinished( prev );
		}

		stepStack.push( stepNumber );
		logger.debug( "pushing to stepStack step number < {} >", stepNumber );
		fireEventOnTestStepStart( stepNumber, this.testCaseInstance );

		//		try
		//		{
		//			if( ! stepStack.isEmpty() )
		//			{
		//				// todo verify if prev
		//				Float prev = stepStack.pop();
		//				fireEventOnTestStepEnd( prev, this.testCaseInstance );
		//			}
		//			stepStack.push( stepNumber );
		//			fireEventOnTestStepStart( stepNumber, this.testCaseInstance );
		//		}
		//		catch ( Throwable t )
		//		{
		//			assumptionViolatedMessage = ExceptionUtils.getRootCauseMessage( t );
		//			fireEventAssumptionViolated( t );
		//		}
	}

	public void stepFinished( float stepNumber )
	{
		if( !isSteppingEnabled ) return;
		logger.debug( "Posting event \"stepFinished\" with argument < {} > ", String.format( "%05f", stepNumber ) );

		try
		{
			if( stepStack.empty() )
			{
				final String ERR_MSG = "Request for stepFinished, however the working step stack is empty.";
				fireEventAssumptionViolated( new EventBusEventException( ERR_MSG ) );
				return;
			}

			// firing onTestStepEnd event to listeners. listeners can throw exceptions
			screenshotProcessor.waitUntilDone();
			fireEventOnTestStepEnd( stepNumber, this.testCaseInstance );
			TestStepInstance tsi = this.testCaseInstance.getTestStepInstance( stepNumber );
			fireEventOnTestStepSuccess( stepNumber );


			if ( ! stepStack.empty() )
			{
				//adapter.getCurrentTestCase().getCurrentTestCaseInstance()
				//				stepStack.pop();
				//
				//				if ((!inFluentStepSequence) && currentStepExists()) {
				//					TestStep finishedStep = currentStepStack.pop();
				//					finishedStep.recordDuration();
				//					if (result != null) {
				//						finishedStep.setResult(result);
				//					}
				//				}
			}
			//			getStepCounters().logExecutedTest();

			// firing onTestStepSuccess event to listeners. if any listener throwed an

			//fireEventOnTestStepSuccess( stepNumber );
		}
		catch ( Throwable t )
		{
			assumptionViolatedMessage = ExceptionUtils.getRootCauseMessage( t );
			fireEventAssumptionViolated( t );
			fireEventOnTestStepFailure( stepNumber, t );
			dropAllListeners();
		}
	}

	public void testCaseEnded( ITestResult itr )
	{
		if( !isSteppingEnabled ) return;

		logger.debug( "testCaseEnded request was received on StepEventBus" );
		screenshotProcessor.waitUntilDone();
		Float stepNo = stepStack.peek();
		ResultStatus rs = ResultStatus.valueOf( itr.getStatus() );
		//TestStepInstance tsi = this.testCaseInstance.getTestStepInstance( stepNo );
		fireEventOnTestStepEnd( stepNo, this.testCaseInstance );
		if( rs.equals( ResultStatus.SUCCESS ) )
		{
			fireEventOnTestStepSuccess( stepNo );
		}
		else if( rs.equals( ResultStatus.FAILURE ) )
		{
			fireEventOnTestStepFailure( stepNo, itr.getThrowable() );
		}

		getScreenshotProcessor().terminate();

//		if( stepStack.empty() )
//		{
//			final String ERR_MSG = "Request for stepFinished, however the working step stack is empty.";
//			fireEventAssumptionViolated( new EventBusEventException( ERR_MSG ) );
//			return;
//		}
//
//		ResultStatus rs = ResultStatus.valueOf( itr.getStatus() );
//		Float testNo = stepStack.pop();
//		TestStepInstance tsi = this.testCaseInstance.getTestStepInstance( testNo );
//		fireEventOnTestStepEnd( testNo, this.testCaseInstance );
//
//		tsi.setStatuses( rs );
//		if( null != itr.getThrowable() )
//		{
//			tsi.addThrowable( itr.getThrowable() );
//			fireEventOnTestStepFailure( testNo, itr.getThrowable() );
//		}
//		else
//		{
//			fireEventOnTestStepSuccess( testNo );
//		}
	}

	public void beforeCheckpoint( final String id, JAssert assertCommand )
	{
		if( !isSteppingEnabled ) return;
		TestStepInstance tsi = this.testCaseInstance.getTestStepInstance( stepStack.peek() );
		Checkpoint checkpoint = new Checkpoint( id, assertCommand, tsi );
		fireEventOnCheckpointStarted( checkpoint, tsi );
	}

	public void afterCheckpoint( final String id, JAssertion assertion )
	{
		if( !isSteppingEnabled ) return;
		TestStepInstance tsi = this.testCaseInstance.getTestStepInstance( stepStack.peek() );
		Checkpoint checkpoint = tsi.getCheckpoint( id );

		if( assertion.getStatus().equals( ResultStatus.SUCCESS ) )
		{
			fireEventOnCheckPointSuccess( checkpoint );
		}
		else
		{
			fireEventOnCheckPointFailed( assertion, checkpoint );
		}
	}


	//endregion


	//region StepEventBus - Service Methods Section

	public ScreenshotProcessor getScreenshotProcessor()
	{
		return screenshotProcessor;
	}

	public String getAssumptionViolatedMessage()
	{
		return assumptionViolatedMessage;
	}



	//endregion


	//region StepEventBus - Private Function Section

//	private FailureAnalysis getFailureAnalysis()
//	{
//		return new FailureAnalysis();
//	}

	private void clear()
	{
		stepStack.clear();
//		clearStepFailures();
//		noAssumptionsViolated();
//		counters = null;
//		classUnderTest = null;
//		webdriverSuspensions.clear();
	}

	private void fireEventOnTestStepSuccess( final float stepNumber)
	{
		for ( StepListener stepListener : getAllListeners() )
		{
			stepListener.onTestStepSuccess( stepNumber, this.testCaseInstance );
		}
	}

	private void fireEventOnTestStepEnd( final float stepNumber, TestCaseInstance instance )
	{
		for ( StepListener stepListener : getAllListeners() )
		{
			stepListener.onTestStepEnd( stepNumber, instance );
		}
	}

	private void fireEventAssumptionViolated( Throwable t )
	{
		for ( StepListener stepListener : getAllListeners() )
		{
			stepListener.assumptionViolated( ExceptionUtils.getRootCauseMessage( t ) );
		}

		logger.warn( ExceptionUtils.getRootCauseMessage( t ) );
	}

	private void fireEventOnTestStepFailure( final float stepNumber, Throwable t )
	{
		for ( StepListener stepListener : getAllListeners() )
		{
			stepListener.onTestStepFailure( stepNumber, this.testCaseInstance, t );
		}
	}

	private void fireEventOnTestStepStart( final float stepNumber, TestCaseInstance instance )
	{
		try
		{
			logger.debug( "fireEventOnTestStepStart of step < {} >, test-ase instance  {} >", stepNumber, instance.toString() );
			for ( StepListener stepListener : getAllListeners() )
			{
				stepListener.onTestStepStart( stepNumber, instance );
			}
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}
	}

	private void fireEventOnCheckpointStarted( final Checkpoint checkpoint, final TestStepInstance tsi )
	{
		for ( StepListener stepListener : getAllListeners() )
		{
			stepListener.onCheckpointStarted( checkpoint, tsi );
		}
	}

	private void fireEventOnCheckPointSuccess( final Checkpoint checkpoint )
	{
		for ( StepListener stepListener : getAllListeners() )
		{
			stepListener.onCheckPointSuccess( checkpoint );
		}
	}

	private void fireEventOnCheckPointFailed( final JAssertion assertion, final Checkpoint checkpoint )
	{
		for ( StepListener stepListener : getAllListeners() )
		{
			stepListener.onCheckpointFailed( assertion, checkpoint );
		}
	}

	//endregion
}
