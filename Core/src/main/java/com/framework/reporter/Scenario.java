package com.framework.reporter;

import com.framework.utils.string.ToLogStringStyle;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.joda.time.DateTime;
import org.joda.time.Instant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;
import java.util.Stack;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.reporter.data
 *
 * Name   : ScenarioUtil 
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-02-14 
 *
 * Time   : 21:23
 *
 */

public class Scenario extends BaseComponent
{

	//region Scenario - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( Scenario.class );

	private static final Comparator<ITestResult> TEST_CASE_COMPARATOR = new TestCaseNameComparator();

	private static final Comparator<ITestResult> CONFIGURATION_TYPE_COMPARATOR = new ConfigurationTypeComparator();

	private static final String TIMESTAMP_PATTERN = "EEEE, MMMM dd, yyyy hh:mm:ss a";


	private enum ConfigurationType
	{
		AFTER_SUITE, AFTER_TEST, AFTER_CLASS, AFTER_GROUP, AFTER_METHOD,
		BEFORE_METHOD, BEFORE_GROUP, BEFORE_CLASS, BEFORE_TEST, BEFORE_SUITE
	}





	/** a timestamp for the instant of the reporter creation ( after information processed ) */
	private Instant instant;

	//private static final Instant timestamp = new Instant( DateTime.now() );

	private Stack<Suite> suitesStack = new Stack<>();

	private Stack<ITestResult> configurationStack = new Stack<>();

	private Stack<ITestResult> testCaseStack = new Stack<>();

	//endregion


	//region Scenario - Constructor Methods Section

	Scenario( DateTime dt )
	{
		super( dt );
	}

	//endregion


	//region Scenario - Counter Methods Section

	public int getTotalTestCasesCount()
	{
		int totalMethods = 0;
		for( Suite suite : this.suitesStack )
		{
			for( TestContext context : suite.getTestContextAsList() )
			{
				totalMethods += context.getTInvokedTestCasesCount();
			}
		}

		return totalMethods;
	}

	/**
	 * increases the success configurationStack counter and total counter by 1.
	 */
	ConfigurationInstanceMethod increaseSuccessConfigurationInstanceCounters( ITestResult itr )
	{
		int compare = CONFIGURATION_TYPE_COMPARATOR.compare( itr, configurationStack.peek() );
		if( compare == 0 )
		{
			increaseCounter( "successConfigurationsCount" );
			increaseCounter( "configurationsCount" );
		    configurationStack.pop();
			return getCurrentSuite().increaseSuccessConfigurationInstanceCounters( itr );
		}

		throw new ReporterRuntimeException( "invalid configuration method type was registered as 'success' -> " + configurationStack.pop() );
	}

	/**
	 * increases the failed configurationStack counter and total counter by 1.
	 */
	ConfigurationInstanceMethod increaseFailedConfigurationInstanceCounters( ITestResult itr )
	{
		int compare = CONFIGURATION_TYPE_COMPARATOR.compare( itr, configurationStack.peek() );
		if( compare == 0 )
		{
			increaseCounter( "failedConfigurationsCount" );
			increaseCounter( "configurationsCount" );
			configurationStack.pop();
			return getCurrentSuite().increaseFailedConfigurationInstanceCounters( itr );
		}

		throw new ReporterRuntimeException( "invalid configuration method type was registered as 'failed' -> " + configurationStack.pop() );
	}

	/**
	 * increases the skipped configurationStack counter and total counter by 1.
	 */
	ConfigurationInstanceMethod increaseSkippedConfigurationInstanceCounters( ITestResult itr )
	{
		int compare = CONFIGURATION_TYPE_COMPARATOR.compare( itr, configurationStack.peek() );
		if( compare == 0 )
		{
			increaseCounter( "skippedConfigurationsCount" );
			increaseCounter( "configurationsCount" );
			configurationStack.pop();
			return getCurrentSuite().increaseSkippedConfigurationInstanceCounters( itr );
		}
		throw new ReporterRuntimeException( "invalid configuration method type was registered as 'skipped' -> " + configurationStack.pop() );

	}

	TestCaseInstance increaseSuccessTestCaseInstanceCounters( ITestResult itr )
	{
		int compare = TEST_CASE_COMPARATOR.compare( itr, testCaseStack.peek() );
		if( compare == 0 )
		{
			increaseCounter( "successTestCaseInstanceCount" );
			increaseCounter( "testCaseInstanceCount" );
			testCaseStack.pop();
			return getCurrentSuite().increaseSuccessTestCaseInstanceCounters( itr );
		}
		throw new ReporterRuntimeException( "invalid test case was registered as 'success' -> " + configurationStack.pop() );
	}

	TestCaseInstance increaseFailedTestCaseInstanceCounters( ITestResult itr )
	{
		int compare = TEST_CASE_COMPARATOR.compare( itr, testCaseStack.peek() );
		if( compare == 0 )
		{
			increaseCounter( "failedTestCaseInstanceCount" );
			increaseCounter( "testCaseInstanceCount" );
			testCaseStack.pop();
			return getCurrentSuite().increaseFailedTestCaseInstanceCounters( itr );
		}
		throw new ReporterRuntimeException( "invalid test case was registered as 'failed' -> " + configurationStack.pop() );
	}

	TestCaseInstance increaseFailedWithSuccessPercentageTestCaseInstanceCounters( ITestResult itr )
	{
		int compare = TEST_CASE_COMPARATOR.compare( itr, testCaseStack.peek() );
		if( compare == 0 )
		{
			increaseCounter( "failedWithSuccessPercentageTestCaseInstanceCount" );
			increaseCounter( "testCaseInstanceCount" );
			testCaseStack.pop();
			return getCurrentSuite().increaseFailedWithSuccessPercentageTestCaseInstanceCounters( itr );
		}
		throw new ReporterRuntimeException( "invalid test case was registered as 'failed with success percentage' -> " + configurationStack.pop() );
	}

	void increaseSkippedTestCaseInstanceCounters( ITestResult itr )
	{
		if( ! testCaseStack.isEmpty() )  //todo: implement
		{
			increaseCounter( "skippedTestCaseInstanceCount" );
			increaseCounter( "testCaseInstanceCount" );
			getCurrentSuite().increaseSkippedTestCaseInstanceCounters( itr );
			testCaseStack.pop();
		}
		else
		{
			increaseCounter( "ignoredTestCasesCount" );
			increaseCounter( "testCaseInstanceCount" );
			getCurrentSuite().increaseIgnoredTestCaseCounters( itr );
		}
	}

	void pushConfigurationInstance( ITestResult itr )
	{
		if( configurationStack.isEmpty() )
		{
			this.configurationStack.push( itr );
			getCurrentSuite().addConfigurationInstance( itr );
			return;
		}
		throw new ReporterRuntimeException( "The configuration stack is not empty..." );
	}

	TestCase pushTestCase( ITestResult itr )
	{
		if( testCaseStack.isEmpty() )
		{
			this.testCaseStack.push( itr );
			return getCurrentSuite().addTestCase( itr );
		}
		throw new ReporterRuntimeException( "The Test-Cases stack is not empty..." );
	}

	//endregion


	//region Scenario - Suite Service Methods Section

	void createInstant()
	{
		this.instant = new Instant( DateTime.now() );
	}

	public int getSuitesCount()
	{
		return suitesStack.size();
	}

	public List<Suite> getSuitesAsList()
	{
		return suitesStack;
	}

	void pushSuite( Suite suite )
	{
		this.suitesStack.push( suite );
	}

	public Suite getCurrentSuite()
	{
		if( suitesStack.isEmpty() ) return null;
		return suitesStack.peek();
	}

	//endregion


	//region Scenario - TestContext Service Methods Section

	void pushTestContext( TestContext testContext )
	{
		getCurrentSuite().pushTestContext( testContext );
	}

	public int getTestContextCount()
	{
		int contexts = 0;
		for( Suite suite : suitesStack )
		{
			contexts += suite.getTestContextCount();
		}

		return contexts;
	}

	//endregion


	//region Scenario - Service Methods Section

	void terminate()
	{
		//testClasses.clear();

		while( ! suitesStack.isEmpty() )
		{
			Suite suite = suitesStack.pop();
			suite.terminate();
		}

		configurationStack.clear();
	}

	@Override
	public String toString()
	{
		return new ToStringBuilder( this, ToLogStringStyle.LOG_MULTI_LINE_STYLE )
				.append( "start date", null != getFormattedStartDate() ? getFormattedStartDate() : "N/A" )
				.append( "is failed", isFailed() )
				.append( "success rate", getFormattedSuccessRate() )
				.append( "suites", suitesStack.isEmpty() ? "N/A" : suitesStack.size() )
			//	.append( "test-contexts", suitesStack.isEmpty() ? "N/A" : suitesStack.size() )
			//	.append( "configurations count", configurationsCount )
			//	.append( "test-case instances count", testCaseInstanceCount )
				.toString();
	}

	//endregion


	//region Scenario - State and Status Service Methods Section

	public boolean isFailed()
	{
		if( suitesStack.isEmpty() ) return false;
		if( suitesStack.size() == 1 ) return suitesStack.peek().isFailed();

		List<Boolean> isFailedAnalyzer = Lists.newArrayList();
		for ( final Suite aSuitesStack : suitesStack )
		{
			isFailedAnalyzer.add( aSuitesStack.isFailed() );
		}
		return BooleanUtils.or( isFailedAnalyzer.toArray( new Boolean[ isFailedAnalyzer.size() ] ) );
	}

	//endregion


	//region Scenario - Joda Time Service Methods Section



	public Instant getInstant()
	{
		return instant;
	}

	String getFormattedInstant()
	{
		return instant.toDateTime().toString( TIMESTAMP_PATTERN );
	}

	//endregion


	//region Scenario - Inner Classes Implementation Section

	private static class ConfigurationTypeComparator implements Comparator<ITestResult>, Serializable
	{
		private static final long serialVersionUID = 5558550850685483455L;

		public int compare( ITestResult o1, ITestResult o2 )
		{
			return annotationValue( o1 ) - annotationValue( o2 );
		}

		private static int annotationValue( ITestResult result )
		{
			if ( result.getMethod().isBeforeSuiteConfiguration() )
			{
				return ConfigurationType.BEFORE_SUITE.ordinal() + 1;
			}
			else if ( result.getMethod().isBeforeTestConfiguration() )
			{
				return ConfigurationType.BEFORE_TEST.ordinal() + 1;
			}
			else if ( result.getMethod().isBeforeClassConfiguration() )
			{
				return ConfigurationType.BEFORE_CLASS.ordinal() + 1;
			}
			else if ( result.getMethod().isBeforeGroupsConfiguration() )
			{
				return ConfigurationType.BEFORE_GROUP.ordinal() + 1;
			}
			else if ( result.getMethod().isBeforeMethodConfiguration() )
			{
				return ConfigurationType.BEFORE_METHOD.ordinal() + 1;
			}
			else if ( result.getMethod().isAfterMethodConfiguration() )
			{
				return ConfigurationType.AFTER_METHOD.ordinal() + 1;
			}
			else if ( result.getMethod().isAfterGroupsConfiguration() )
			{
				return ConfigurationType.AFTER_GROUP.ordinal() + 1;
			}
			else if ( result.getMethod().isAfterClassConfiguration() )
			{
				return ConfigurationType.AFTER_CLASS.ordinal() + 1;
			}
			else if ( result.getMethod().isAfterTestConfiguration() )
			{
				return ConfigurationType.AFTER_TEST.ordinal() + 1;
			}
			else if ( result.getMethod().isAfterSuiteConfiguration() )
			{
				return ConfigurationType.AFTER_SUITE.ordinal() + 1;
			}

			return 0;
		}
	}

	private static class TestCaseNameComparator implements Comparator<ITestResult>, Serializable
	{

		private static final long serialVersionUID = - 749236851960419836L;

		@Override
		public int compare( final ITestResult o1, final ITestResult o2 )
		{
			String c1 = o1.getMethod().getConstructorOrMethod().getName();
			String c2 = o2.getMethod().getConstructorOrMethod().getName();
			return c1.compareTo( c2 );
		}
	}

	//endregion

}
