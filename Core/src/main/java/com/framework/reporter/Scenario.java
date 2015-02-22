package com.framework.reporter;

import com.framework.utils.datetime.DateTimeUtils;
import com.framework.utils.error.PreConditions;
import com.framework.utils.string.LogStringStyle;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.math.NumberUtils;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.Instant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Collection;
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

public class Scenario
{

	//region Scenario - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( Scenario.class );

	public static final String DATE_TIME_FORMATTER = "yyyy-MM-dd HH:mm:ss.SSS";

	private static final String TIMESTAMP_PATTERN = "EEEE, MMMM dd, yyyy hh:mm:ss a";

	private static final Comparator<ITestResult> TEST_CASE_COMPARATOR = new TestCaseNameComparator();

	private static final Comparator<ITestResult> CONFIGURATION_TYPE_COMPARATOR = new ConfigurationTypeComparator();


	private enum ConfigurationType
	{
		AFTER_SUITE, AFTER_TEST, AFTER_CLASS, AFTER_GROUP, AFTER_METHOD,
		BEFORE_METHOD, BEFORE_GROUP, BEFORE_CLASS, BEFORE_TEST, BEFORE_SUITE
	}

	/** registers the starting date and time of the whole executed scenario */
	private final DateTime startDate;

	/**
	 * registers the duration of the whole scenario. the {@code endTime} is calculated
	 * form {@linkplain #startDate} + {@code duration}
	 */
	private Duration duration;

	/** a timestamp for the instant of the reporter creation ( after information processed ) */
	private Instant instant;

	//private static final Instant timestamp = new Instant( DateTime.now() );

	private Stack<Suite> suitesStack = new Stack<>();

	/** configurations invocation counters for the whole scenario */
	private int configurationsCount = 0, successConfigurationsCount = 0,
			    skippedConfigurationsCount = 0, failedConfigurationsCount = 0;

	/** test-cases instances invocation counters for the whole scenario */
	private int testCaseInstanceCount = 0, successTestCaseInstanceCount = 0, failedTestCaseInstanceCount = 0,
			skippedTestCaseInstanceCount = 0, failedWithSuccessPercentageTestCaseInstanceCount = 0;

	/** test-case counters. skippedTestCase is a test case that was never invoked. */
	private int excludedTestCasesCount = 0, ignoredTestCasesCount = 0;

	private Stack<ITestResult> configurationStack = new Stack<>();

	private Stack<ITestResult> testCaseStack = new Stack<>();

	//endregion


	//region Scenario - Constructor Methods Section

	Scenario( DateTime dt )
	{
		PreConditions.checkNotNull( dt, "DateTime cannot be null." );
		this.startDate = dt;
	}

	//endregion


	//region Scenario - Counter Methods Section

	public int getSuccessConfigurationsCount()
	{
		return successConfigurationsCount;
	}

	public int getSkippedConfigurationsCount()
	{
		return skippedConfigurationsCount;
	}

	public int getFailedConfigurationsCount()
	{
		return failedConfigurationsCount;
	}

	public int getConfigurationsCount()
	{
		return configurationsCount;
	}

	public int getSuccessTestCasesInstancesCount()
	{
		return successTestCaseInstanceCount;
	}

	public int getFailedTestCaseInstanceCount()
	{
		return failedTestCaseInstanceCount;
	}

	public int getFailedWithSuccessPercentageTestCaseInstanceCount()
	{
		return failedWithSuccessPercentageTestCaseInstanceCount;
	}

	public int getSkippedTestCaseInstanceCount()
	{
		return skippedTestCaseInstanceCount;
	}

	public int getIgnoredTestCaseCount()
	{
		return ignoredTestCasesCount;
	}

	public int getTestCaseInstanceCount()
	{
		return testCaseInstanceCount;
	}

	public int getTotalInvokedMethodsCount()
	{
		return configurationsCount + testCaseInstanceCount;
	}

	public int getExcludedTestCasesCount()
	{
		return excludedTestCasesCount;
	}

	/**
	 * increases the success configurationStack counter and total counter by 1.
	 */
	ConfigurationInstanceMethod increaseSuccessConfigurationInstanceCounters( ITestResult itr )
	{
		int compare = CONFIGURATION_TYPE_COMPARATOR.compare( itr, configurationStack.peek() );
		if( compare == 0 )
		{
			this.successConfigurationsCount ++;
			this.configurationsCount ++;
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
			this.failedConfigurationsCount ++;
			this.configurationsCount ++;
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
			this.skippedConfigurationsCount ++;
			this.configurationsCount ++;
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
			this.successTestCaseInstanceCount ++;
			this.testCaseInstanceCount ++;
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
			this.failedTestCaseInstanceCount ++;
			this.testCaseInstanceCount ++;
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
			this.failedWithSuccessPercentageTestCaseInstanceCount ++;
			this.testCaseInstanceCount ++;
			testCaseStack.pop();
			return getCurrentSuite().increaseFailedWithSuccessPercentageTestCaseInstanceCounters( itr );
		}
		throw new ReporterRuntimeException( "invalid test case was registered as 'failed with success percentage' -> " + configurationStack.pop() );
	}

	void increaseSkippedTestCaseInstanceCounters( ITestResult itr )
	{
		if( ! testCaseStack.isEmpty() )  //todo: implement
		{
			this.skippedTestCaseInstanceCount ++;
			this.testCaseInstanceCount ++;
			getCurrentSuite().increaseSkippedTestCaseInstanceCounters( itr );
			testCaseStack.pop();
		}
		else
		{
			this.ignoredTestCasesCount ++;
			this.testCaseInstanceCount ++;
			getCurrentSuite().increaseIgnoredTestCaseCounters( itr );
		}
	}

	public void setExcludedTestCases( Collection<ITestNGMethod> excludedMethods ) //todo: need method?
	{
		this.excludedTestCasesCount += excludedMethods.size();
		//suites.get( currentSuiteName ).setExcludedTestCases( excludedMethods );
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
		return new ToStringBuilder( this, LogStringStyle.LOG_MULTI_LINE_STYLE )
				.append( "start date", null != startDate ? getFormattedStartDate() : "N/A" )
				.append( "is failed", isFailed() )
				.append( "success rate", getFormattedSuccessRate() )
				.append( "suites", suitesStack.isEmpty() ? "N/A" : suitesStack.size() )
			//	.append( "test-contexts", suitesStack.isEmpty() ? "N/A" : suitesStack.size() )
				.append( "configurations count", configurationsCount )
				.append( "test-case instances count", testCaseInstanceCount )
				.toString();
	}

	//endregion


	//region Scenario - State and Status Service Methods Section

	Float getSuccessRate()
	{
		if( testCaseInstanceCount == 0 ) return NumberUtils.createFloat( "0" );
		return ( successTestCaseInstanceCount * 100 ) / ( float ) testCaseInstanceCount;
	}

	public String getFormattedSuccessRate()
	{
		return new DecimalFormat( "#.##" ).format( getSuccessRate() );
	}

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

	public String getFormattedStartDate()
	{
		return startDate.toString( Scenario.DATE_TIME_FORMATTER );
	}

	DateTime getStartDate()
	{
		return startDate;
	}

	public String getFormattedEndDate()
	{
		return getEndDate().toString( Scenario.DATE_TIME_FORMATTER );
	}

	DateTime getEndDate()
	{
		return startDate.plus( duration );
	}

	void recordEndDate()
	{
		this.duration = new Duration( startDate, DateTime.now() );
	}

	Duration getDuration()
	{
		return duration;
	}

	public String getFormattedDuration()
	{
		return DateTimeUtils.getFormattedDuration( startDate, getEndDate() );
	}

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
