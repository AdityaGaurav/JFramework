package com.framework.reporter;

import com.framework.testing.steping.IssueTracker;
import com.framework.testing.steping.StepsAnnotation;
import com.framework.testing.steping.TestCaseAnnotationsReader;
import com.framework.testing.steping.TestStep;
import com.framework.utils.error.PreConditions;
import com.framework.utils.string.LogStringStyle;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.internal.TestNGMethod;
import org.testng.internal.annotations.TestAnnotation;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Stack;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.reporter.data
 *
 * Name   : TestCaseUtil 
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-02-17 
 *
 * Time   : 23:50
 *
 */

public class TestCase
{

	//region TestCaseUtil - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( TestCase.class );

	private final TestNGMethod testNGMethod;

	private TestContext parentContext = null;

	private Stack<TestCaseInstance> testCaseInstanceStack = new Stack<>();

	private List<IssueTracker> issues = Lists.newArrayList();

	private List<Long> testIds = Lists.newArrayList();

	private Stack<TestStep> steps;

	private TestAnnotation testAnnotation = null;

	private boolean dataDriven = false;

	private boolean annotationsParsed = false;

	//endregion


	//region TestCase - Constructor Methods Section

	public TestCase( final ITestResult result, TestContext parent )
	{
		PreConditions.checkNotNull( result, "ITestResult result argument cannot be null. " );
		PreConditions.checkNotNull( parent, "TestContext parent argument cannot be null. " );

		this.testNGMethod = (  TestNGMethod ) result.getMethod();
		this.parentContext = parent;

		testCaseInstanceStack.push( createTestCaseInstance( result ) );
	}


	//endregion


	//region TestCase - ITestNGMethod Implementation Methods Section

	ITestNGMethod getTestNGMethod()
	{
		return testNGMethod;
	}

	String getName()
	{
		return testNGMethod.getMethodName();
	}

	public TestContext getParentContext()
	{
		return parentContext;
	}

	public Suite getParentSuite()
	{
		return parentContext.getParentSuite();
	}

	//endregion


	//region TestCase - Service Methods Section

	void terminate()
	{
		testCaseInstanceStack.clear();
	}

	@Override
	public String toString()
	{
		return new ToStringBuilder( this, LogStringStyle.LOG_LINE_STYLE )
				.append( "name", null != testNGMethod ? testNGMethod.getMethodName() : "N/A" )
				.append( "testClass", getTestNGMethod() != null ? getTestNGMethod().getTestClass().getName() : "N/A" )
				.toString();
	}

	//endregion


	//region TestCase - TestCaseInstance Implementation Section

	TestCaseInstance getCurrentTestCaseInstance()
	{
		return testCaseInstanceStack.peek();
	}

	TestCaseInstance createTestCaseInstance( ITestResult result )
	{
		TestCaseInstance currentInstance = new TestCaseInstance( result, this );
		return currentInstance;
	}

	//endregion


	//region TestCase - Stepping Implementation Methods Section

	public boolean isAnnotationsParsed()
	{
		return annotationsParsed;
	}

	void parseAnnotations()
	{
		annotationsParsed = true;
		Class<?> clazz = testNGMethod.getRealClass();
		Method method = testNGMethod.getConstructorOrMethod().getMethod();
		TestCaseAnnotationsReader reader = new TestCaseAnnotationsReader( clazz, method );

		this.testAnnotation = reader.getAnnotatedTest();
		// only if both dataProvider and data provider class are null, the test case is not data-driven
//		boolean dpClass = testAnnotation.getDataProviderClass() == null;
//		boolean dp = testAnnotation.getDataProvider() == null;
//		this.dataDriven = ( dpClass && dp );
//		logger.debug( "Determined if the test case is data-driven: < {} >", BooleanUtils.toStringYesNo( dataDriven ) );

		logger.debug( "parsing and adding @Issues and @Issue annotations if present ..." );
		addIssues( reader.getIssueTracker() );
		addIssues( reader.getIssuesTracker() );

		logger.debug( "parsing and adding @TestCaseId and @TestCasesIds annotations if present ..."  );
		addTestCasesIds( reader.getAnnotatedTestCaseId() );
		addTestCasesIds( reader.getAnnotatedTestCaseIds() );
	}

	void registerSteps( StepsAnnotation stepsAnnotation )
	{
		steps = new Stack<>();
		for( Float number : stepsAnnotation.getMap().keySet() )
		{

		}
	}

	private void addIssues( List<IssueTracker> issuesList )
	{
		if( issuesList.size() > 0 )
		{
			logger.info( "Adding < {} > issues to test case ...", issues.size() );
			issues.addAll( issuesList );
			this.issues = removeIssuesDuplicates( issues );
		}
	}

	private void addTestCasesIds( List<Long> list )
	{
		if( list.size() > 0 )
		{
			logger.info( "Registering the following ids to current test cae < {} >", Joiner.on( "," ).join( list ) );
			for( Long id : list )
			{
				if( ! testIds.contains( id ) )
				{
					testIds.add( id );
				}
				else
				{
					final String WRN_MSG = "Duplicate test case id < {} > found.\n" +
							"remove the test case id from the @TestCaseId or @TestCasesIds annotations.";
					logger.warn( WRN_MSG, id );
				}
			}
		}
	}

	private List<IssueTracker> removeIssuesDuplicates( List<IssueTracker> issues )
	{
		logger.info( "Removing duplicated issues if any ... " );
		List<IssueTracker> issuesWithNoDuplicates = Lists.newArrayList();
		if ( issues != null )
		{
			for ( IssueTracker issue : issues )
			{
				if ( ! issuesWithNoDuplicates.contains( issue ) )
				{
					issuesWithNoDuplicates.add( issue );
				}
			}
		}

		logger.info( "Currently are < {} > registered issues to test case.", issuesWithNoDuplicates.size() );
		return issuesWithNoDuplicates;
	}

	//endregion

}
