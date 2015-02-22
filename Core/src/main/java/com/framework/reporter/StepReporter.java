package com.framework.reporter;

import com.framework.testing.steping.StepEventBus;
import com.framework.testing.steping.exceptions.NoSuchTestStepException;
import com.framework.utils.error.PreConditionException;
import com.framework.utils.string.TextArt;
import com.google.common.base.Throwables;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.reporter.utils
 *
 * Name   : StepReporter 
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-02-18 
 *
 * Time   : 20:45
 *
 */

public class StepReporter //extends ScenarioListenerAdapter implements StepListener
{

	//region StepReporter - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( StepReporter.class );

	private TestCase currentTestCase = null;

	//endregion


	//region StepReporter - Constructor Methods Section

	public StepReporter()
	{
		//
	}


	//endregion


	//region StepReporter - Service Methods Section

//	TestStep getCurrentStep()
//	{
//		return currentStepStack.peek();
//	}


	//endregion


	//region StepReporter - StepListener Implementation Methods Section

	public void onTestCaseStart( final ITestResult result )
	{
		/** initializes the {@link com.framework.testing.steping.StepEventBus} */
		StepEventBus.getEventBus().testCaseStarted( result );

		// create a new instance of TestCase
	//	this.currentTestCase = new TestCase( result );

		//this.testContext =  ( TestRunner ) result.getTestContext();
		//

		// adding the @Test annotation as instance to class

//		TestCaseAnnotationsReader reader =
//				new TestCaseAnnotationsReader( currentTestCase.getRealClass(),currentTestCase.getMethod() );
//		currentTestCase.addTestAnnotation( reader.getTestAnnotation() );
//
//		logger.debug( "parsing and adding @Issues and @Issue annotations if present ..." );
//		currentTestCase.addIssues( reader.getIssueTracker() );
//		currentTestCase.addIssues( reader.getIssuesTracker() );
//
//		logger.debug( "parsing and adding @TestCaseId and @TestCasesIds annotations if present ..."  );
//		currentTestCase.addTestCasesIds( reader.getAnnotatedTestCaseId() );
//		currentTestCase.addTestCasesIds( reader.getAnnotatedTestCaseIds() );
//
//		logger.debug( "parsing and adding @Steps/@Step/@ChildStep annotations if present ..." );
//		try
//		{
//			currentTestCase.registerSteps( reader.getAnnotatedTestCaseSteps( allSteps ) );
//		}
//		catch ( ConfigurationException e )
//		{
//			throw new ConfigurationRuntimeException( e );
//		}


	}

	public void onTestStepStart( final float number )
	{
		try
		{
			// validating onTestStepStart pre-conditions

			onTestStepStartPreConditions( number );

			logger.info( TextArt.getStepStart( String.format( "%05f", number ) ) );

//			TestStep testStep = currentTestCase.selectTestStep( number );
//			testStep.recordStartTime();
//			testStep.setResultStatus( ResultStatus.STARTED );
//			//startNewGroupIfNested();
//			currentStepStack.push( testStep );
//			recordStepToCurrentTestCaseInstance( testStep );
		}
		catch ( Throwable t )
		{
			Throwables.propagateIfInstanceOf( t, PreConditionException.class );
			logger.error( ExceptionUtils.getRootCauseMessage( t ) );
			Throwables.propagate( t );
		}
	}

	public void onTestStepSuccess( final float number )
	{
		logger.info( TextArt.getStepEndSuccess( String.format( "%05f", number ) ) );
	}

	public void onTestStepEnd( final float number )
	{
		onTestStepEndPreConditions( number );
	}

	public void onTestCaseEnd( final ITestResult result )
	{
		//currentTestCase.recordDuration();
		//currentStepStack.clear();
	}

	public void assumptionViolated( final String message )
	{
		//currentTestCase.getCurrentTestCaseInstance().addViolationAssumptionMessage( message );
	}

	//endregion


	//region StepReporter - Private Methods Section



	private void onTestStepEndPreConditions( float stepNumber )
	{
		boolean exists = false;//currentTestCase.isRegisteredStep( stepNumber );
		if( ! exists )
		{
			String ERR_MSG = "The step < " + stepNumber + " > is not registered. check your @Steps annotation";
			throw new PreConditionException( new NoSuchTestStepException( ERR_MSG ) );
		}
	}

	private void onTestStepStartPreConditions( float stepNumber )
	{
//		boolean exists = currentTestCase.isRegisteredStep( stepNumber );
//		if( ! exists )
//		{
//			String ERR_MSG = "The step < " + stepNumber + " > is not registered. check your @Steps annotation";
//			throw new PreConditionException( new NoSuchTestStepException( ERR_MSG ) );
//		}
//
//		TestStep testStep = currentTestCase.selectTestStep( stepNumber );
//		if( ! testStep.isPending() )
//		{
//			throw new IllegalStateException(
//					"The started step is not PENDING. his status is < '" + testStep.getResultStatus().getStatusName() + "' >" );
//		}
		/**
		 *  todo: stack is empty?  good - first step
		 *  todo: stack not empty? report violation, end stack change statuses
		 */
	}

//	private void recordStepToCurrentTestCaseInstance( TestStep step )
//	{
//		currentTestCase.getCurrentTestCaseInstance().recordStep( step );
//	}



	//endregion

}
