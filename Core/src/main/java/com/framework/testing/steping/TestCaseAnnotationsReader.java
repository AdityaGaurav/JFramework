package com.framework.testing.steping;

import com.framework.testing.annotations.Issue;
import com.framework.testing.annotations.Steps;
import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;
import org.testng.internal.annotations.TestAnnotation;

import java.lang.reflect.Method;
import java.util.List;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.testing.steping
 *
 * Name   : TestCaseAnnotationsReader 
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-01-26 
 *
 * Time   : 09:40
 *
 */

public class TestCaseAnnotationsReader
{

	//region TestCaseAnnotationsReader - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( TestCaseAnnotationsReader.class );

	private final TestAnnotations annotations;

	private final Method testCaseMethod;

	//endregion


	//region TestCaseAnnotationsReader - Constructor Methods Section

	public TestCaseAnnotationsReader( final Class<?> realClass, final Method method )
	{
		this.annotations = TestAnnotations.forClass( realClass );
		this.testCaseMethod = method;
	}

	//endregion


	//region TestCaseAnnotationsReader - Public Methods Section

	public TestAnnotation getAnnotatedTest()
	{
		Optional<Test> testAnnotation = annotations.getAnnotatedTestForMethod( testCaseMethod );

		if ( testAnnotation.isPresent() )
		{
			TestAnnotation ta = new TestAnnotation();
			Test test = testAnnotation.get();

			// filling instance with annotation information

			ta.setAlwaysRun( test.alwaysRun() );
			ta.setDataProvider( test.dataProvider() );
			ta.setDataProviderClass( test.dataProviderClass() );
			ta.setDependsOnGroups( test.dependsOnGroups() );
			ta.setDependsOnMethods( test.dependsOnMethods() );
			ta.setDescription( test.description() );
			ta.setEnabled( test.enabled() );
			ta.setExpectedExceptions( test.expectedExceptions() );
			ta.setExpectedExceptionsMessageRegExp( test.expectedExceptionsMessageRegExp() );
			ta.setGroups( test.groups() );
			ta.setIgnoreMissingDependencies( test.ignoreMissingDependencies() );
			ta.setInvocationCount( test.invocationCount() );
			ta.setInvocationTimeOut( test.invocationTimeOut() );
			ta.setPriority( test.priority() );
			ta.setRetryAnalyzer( test.retryAnalyzer() );
			ta.setSingleThreaded( test.singleThreaded() );
			ta.setSkipFailedInvocations( test.skipFailedInvocations() );
			ta.setSuccessPercentage( test.successPercentage() );
			ta.setSuiteName( test.suiteName() );
			ta.setTestName( test.testName() );
			ta.setThreadPoolSize( test.threadPoolSize() );
			ta.setTimeOut( test.timeOut() );
			return ta;
		}

		return null;
	}


	public List<IssueTracker> getIssueTracker( )
	{
		Optional<Issue> issueAnnotation = annotations.getAnnotatedIssueForMethod( testCaseMethod );
		if( issueAnnotation.isPresent() )
		{
			Issue issue = issueAnnotation.get();
			IssueTracker issueTracker = new IssueTracker( issue.value() );
			issueTracker.setIssueTrackerUrl( issue.getIssueTrackerUrl() );
			return Lists.newArrayList( issueTracker );
		}

		return Lists.newArrayListWithExpectedSize( 0 );
	}

	public List<IssueTracker> getIssuesTracker()
	{
		List<IssueTracker> issuesList = Lists.newArrayList();
		Issue[] issues = annotations.getAnnotatedIssuesForMethod( testCaseMethod );

		if( issues.length > 0 )
		{
			for ( Issue issue : issues )
			{
				issuesList.addAll( getIssueTracker( issue ) );
			}
		}

		return issuesList;
	}

	public List<Long> getAnnotatedTestCaseId()
	{
		List<Long> ids = Lists.newArrayList();

		Optional<Long> id = annotations.getAnnotatedTestCaseIdForMethod( testCaseMethod );
		if( id.isPresent() )
		{
			ids.add( id.get() );
		}

		return ids;
	}

	public List<Long> getAnnotatedTestCaseIds()
	{
		Optional<long[]> ids = annotations.getAnnotatedTestCasesIdsForMethod( testCaseMethod );
		if( ids.isPresent() )
		{
			Long[] longObjects = ArrayUtils.toObject( ids.get() );
			List<Long> longList = java.util.Arrays.asList( longObjects );
			return longList;
		}

		return Lists.newArrayListWithExpectedSize( 0 );
	}

	public StepsAnnotation getAnnotatedTestCaseSteps( List<TestStep> allSteps )
	{
		StepsAnnotation pSteps = null;

		Optional<Steps> steps = annotations.getAnnotatedStepsForMethod( testCaseMethod );
		if( steps.isPresent() )
		{
			pSteps = new StepsAnnotation( steps.get() );

//			ListMultimap<Step, ChildStep> stepsMultiMap = pSteps.getSteps();
//			for( Step step : stepsMultiMap.keySet() )
//			{
//				TestStep testStep = new TestStep( step );
//				allSteps.add( testStep );
//			}
		}

		return pSteps;
	}


	//endregion


	//region TestCaseAnnotationsReader - Private Function Section

	private List<IssueTracker> getIssueTracker( Issue issue )
	{
		IssueTracker issueTracker = new IssueTracker( issue.value() );
		issueTracker.setIssueTrackerUrl( issue.getIssueTrackerUrl() );
		return Lists.newArrayList( issueTracker );
	}
	//endregion

}
