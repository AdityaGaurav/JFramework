package com.framework.testing.steping;

import com.framework.asserts.JAssertion;
import com.framework.reporter.TestCaseInstance;
import org.testng.ITestResult;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.testing.steping
 *
 * Name   : StepListener 
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-01-25 
 *
 * Time   : 23:55
 *
 */

public interface StepListener
{
	void onTestCaseStart( com.framework.reporter.TestCase testCase, ITestResult itr );

	void onTestStepStart( float number, TestCaseInstance instance );

	void onTestStepEnd( float number, TestCaseInstance instance );

	void onTestStepSuccess( float number, TestCaseInstance instance );

	void onTestStepFailure( float number, TestCaseInstance instance, final Throwable cause );

	void onTestStepSkipped( float number );

	void onTestCaseEnd( ITestResult result );

	void assumptionViolated( String message );

	void onCheckpointStarted( Checkpoint checkpoint, TestStepInstance tsi );

	void onCheckPointSuccess( final Checkpoint checkpoint );

	void onCheckpointFailed( final JAssertion assertion, final Checkpoint checkpoint );
}
