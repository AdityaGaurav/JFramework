package com.framework.reporter;

import com.framework.utils.datetime.DateTimeUtils;
import com.framework.utils.error.PreConditions;
import com.framework.utils.string.LogStringStyle;
import com.google.common.base.Joiner;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ISuite;
import org.testng.ITestResult;
import org.testng.SuiteRunner;
import org.testng.xml.XmlSuite;

import java.io.File;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Set;
import java.util.Stack;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.reporter.data
 *
 * Name   : SuiteUtil 
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-02-14 
 *
 * Time   : 21:30
 *
 */

public class Suite
{

	//region Suite - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( Suite.class );

	/** registers the starting date and time of this suite */
	private final DateTime startDate;

	/**
	 * registers the executed duration of the whole suite. the {@code endTime} is calculated
	 * form {@linkplain #startDate} + {@code duration}
	 */
	private Duration duration;

	/** instance of the ISuite */
	private SuiteRunner suite;

	/** holds the text contexts ( ordered ) within this suite */
	private final Stack<TestContext> testContextStack = new Stack<>();

	/** keeps the BeforeSuite and AfterSuite Configuration methods */
	private ConfigurationMethod beforeSuite;
	private ConfigurationMethod afterSuite;

	/** configurations invocation counters for the whole scenario */
	private int configurationsCount = 0, successConfigurationsCount = 0, skippedConfigurationsCount = 0, failedConfigurationsCount = 0;

	/** test-cases instances invocation counters for the whole scenario */
	private int testCaseInstanceCount = 0, successTestCaseInstanceCount = 0, failedTestCaseInstanceCount = 0,
			skippedTestCaseCount = 0, ignoredTestCaseCount = 0, failedWithSuccessPercentageTestCaseInstanceCount = 0;

	/** test-case counters. skippedTestCase is a test case that was never invoked. */
	private int excludedTestCasesCount = 0;

	//endregion


	//region Suite - Constructor Methods Section

	Suite( DateTime dt, ISuite suite )
	{
		this.startDate = PreConditions.checkNotNull( dt, "DateTime argument cannot be null." );
		this.suite = ( SuiteRunner ) PreConditions.checkNotNull( suite, "ISuite suite argument cannot be null." );
	}

	//endregion


	//region Suite - Counter Methods Section

	/**
	 * @return the total number of success invoked configurations during all scenario.
	 */
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

	public int getSkippedTestCasesCount()
	{
		return skippedTestCaseCount;
	}

	public int getIgnoredTestCaseCount()
	{
		return ignoredTestCaseCount;
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
		return suite.getExcludedMethods().size();
	}

	TestCase addTestCase( ITestResult itr )
	{
		return getCurrentTestContext().addTestCase( itr );
	}

	void addConfigurationInstance( ITestResult itr )
	{
		if( itr.getMethod().isBeforeSuiteConfiguration() )
		{
			this.beforeSuite = new ConfigurationMethod( itr, this );
			this.beforeSuite.createConfigurationMethodInstance( itr );
		}
		else if( itr.getMethod().isAfterMethodConfiguration() )
		{
			this.afterSuite = new ConfigurationMethod( itr, this );
			this.afterSuite.createConfigurationMethodInstance( itr );
		}
		else
		{
			getCurrentTestContext().addConfigurationInstance( itr );
		}
	}

	ConfigurationInstanceMethod increaseSuccessConfigurationInstanceCounters( ITestResult itr )
	{
		this.successConfigurationsCount ++;
		this.configurationsCount ++;
		if( itr.getMethod().isBeforeSuiteConfiguration() )
		{
			recordEndDate( itr );
			return beforeSuite.getCurrentConfigurationInstanceMethod();
		}
		else if( itr.getMethod().isAfterMethodConfiguration() )
		{
			recordEndDate( itr );
			return afterSuite.getCurrentConfigurationInstanceMethod();
		}
		else
		{
			return getCurrentTestContext().increaseSuccessConfigurationInstanceCounters( itr );
		}
	}

	ConfigurationInstanceMethod increaseFailedConfigurationInstanceCounters( ITestResult itr )
	{
		this.failedConfigurationsCount ++;
		this.configurationsCount ++;
		if( itr.getMethod().isBeforeSuiteConfiguration() )
		{
			recordEndDate( itr );
			return beforeSuite.getCurrentConfigurationInstanceMethod();
		}
		else if( itr.getMethod().isAfterMethodConfiguration() )
		{
			recordEndDate( itr );
			return afterSuite.getCurrentConfigurationInstanceMethod();
		}
		else
		{
			return getCurrentTestContext().increaseFailedConfigurationInstanceCounters( itr );
		}
	}

	/**
	 * increases the skipped configurationStack counter and total counter by 1.
	 */
	ConfigurationInstanceMethod increaseSkippedConfigurationInstanceCounters( ITestResult itr )
	{
		this.skippedConfigurationsCount ++;
		this.configurationsCount ++;
		if( itr.getMethod().isBeforeSuiteConfiguration() )
		{
			recordEndDate( itr );
			return beforeSuite.getCurrentConfigurationInstanceMethod();
		}
		else if( itr.getMethod().isAfterMethodConfiguration() )
		{
			recordEndDate( itr );
			return afterSuite.getCurrentConfigurationInstanceMethod();
		}
		else
		{
			return getCurrentTestContext().increaseSkippedConfigurationInstanceCounters( itr );
		}
	}

	TestCaseInstance increaseSuccessTestCaseInstanceCounters( ITestResult result )
	{
		this.successTestCaseInstanceCount ++;
		this.testCaseInstanceCount ++;
		return getCurrentTestContext().increaseSuccessTestCaseInstanceCounters( result );
	}

	TestCaseInstance increaseFailedTestCaseInstanceCounters( ITestResult result )
	{
		this.failedTestCaseInstanceCount ++;
		this.testCaseInstanceCount ++;
		return getCurrentTestContext().increaseFailedTestCaseInstanceCounters( result );
	}

	TestCaseInstance increaseFailedWithSuccessPercentageTestCaseInstanceCounters( ITestResult result )
	{
		this.failedWithSuccessPercentageTestCaseInstanceCount ++;
		this.testCaseInstanceCount ++;
		return getCurrentTestContext().increaseFailedWithSuccessPercentageTestCaseInstanceCounters( result );
	}

	TestCaseInstance increaseSkippedTestCaseInstanceCounters( ITestResult result )
	{
		this.skippedTestCaseCount ++;
		this.testCaseInstanceCount ++;
		return getCurrentTestContext().increaseSkippedTestCaseInstanceCounters( result );
	}

	void increaseIgnoredTestCaseCounters( ITestResult result )
	{
		this.ignoredTestCaseCount ++;
		this.testCaseInstanceCount ++;
		getCurrentTestContext().increaseIgnoredTestCaseCounters( result );
	}

	private void recordEndDate( ITestResult itr )
	{
		if( itr.getMethod().isBeforeSuiteConfiguration() )
		{
			beforeSuite.getCurrentConfigurationInstanceMethod().recordEndDate();
		}
		else if( itr.getMethod().isAfterMethodConfiguration() )
		{
			afterSuite.getCurrentConfigurationInstanceMethod().recordEndDate();
		}
	}

	//endregion


	//region Suite - Service Methods Section

	void terminate()
	{
		while( ! testContextStack.isEmpty() )
		{
			TestContext testContext = testContextStack.pop();
			testContext.terminate();
		}
	}

	@Override
	public String toString()
	{
		File file = null;
		if( null != suite )
		{
			file = new File( suite.getXmlSuite().getFileName() );
		}

		return new ToStringBuilder( this, LogStringStyle.LOG_MULTI_LINE_STYLE )
				.append( "start date", null != startDate ? getFormattedStartDate() : "N/A" )
				.append( "is failed", null != suite ? suite.getSuiteState().isFailed() : "N/A" )
				.append( "suite name", null != suite ? suite.getName() : "N/A" )
				.append( "file name", null != file ? file.getName() : "N/A" )
				.append( "parent suite", null != suite ? StringUtils.defaultIfBlank( suite.getParentModule(), "<none>" ): "N/A" )
				.append( "excluded methods", null != suite ? suite.getExcludedMethods().size() : "N/A" )
				.toString();
	}

	@Override
	public boolean equals( final Object o )
	{
		if ( this == o )
		{
			return true;
		}
		if ( ! ( o instanceof Suite ) )
		{
			return false;
		}

		final Suite suite1 = ( Suite ) o;

		return suite.getName().equals( suite1.getName() ) && startDate.equals( suite1.startDate );

	}

	@Override
	public int hashCode()
	{
		int result = startDate.hashCode();
		result = 31 * result + ( suite != null ? suite.hashCode() : 0 );
		result = 31 * result + configurationsCount;
		if( null != suite )
		{
			result = 31 * result + suite.getName().hashCode();
		}
		return result;
	}

	//endregion


	//region Suite - TestContext Methods Section

	void pushTestContext( TestContext context )
	{
		this.testContextStack.push( context );
	}

	TestContext getCurrentTestContext()
	{
		return this.testContextStack.peek();
	}

	public int getTestContextCount()
	{
		return testContextStack.size();
	}

	public List<TestContext> getTestContextAsList()
	{
		return testContextStack;
	}


	//endregion


	//region Suite - State and Status Service Methods Section

	Float getSuccessRate()
	{
		return ( successTestCaseInstanceCount * 100 ) / ( float ) testCaseInstanceCount;
	}

	public String getFormattedSuccessRate()
	{
		return new DecimalFormat( "#.##" ).format( getSuccessRate() );
	}

	public boolean isFailed()
	{
		return suite.getSuiteState().isFailed();
	}

	//endregion


	//region Suite - Joda Time Service Methods Section

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

	//endregion


	//region Suite - SuiteRunner runtime info and metadata Methods Section

	public String getName()
	{
		return suite.getName();
	}

	public SuiteRunner getSuiteRunner()
	{
		return suite;
	}

	Set<String> getAttributeNames()
	{
		return suite.getAttributeNames();
	}

	public String getFormattedAttributeNames()
	{
		if( suite.getAttributeNames().size() > 0 )
		{
			return Joiner.on( ", " ).join( suite.getAttributeNames() );
		}

		return "{}";
	}


	public XmlSuite getXmlSuite()   //todo retrieve info
	{
		return suite.getXmlSuite();
	}

	public String getXmlSuiteExcludedGroups()
	{
		if(suite.getXmlSuite().getExcludedGroups().size() == 0 )
		{
			return "{ }";
		}
		return "{ " + Joiner.on( "," ).join( suite.getXmlSuite().getExcludedGroups() ) + " }";
	}

	public String getXmlSuiteIncludedGroups()
	{
		if( suite.getXmlSuite().getIncludedGroups().size() == 0 )
		{
			return "{ }";
		}
		return "{ " + Joiner.on( "," ).join( suite.getXmlSuite().getIncludedGroups() ) + " }";
	}

	public String getXmlSuitePackagesNames()
	{
		if(suite.getXmlSuite().getPackageNames().size() == 0 )
		{
			return "{ }";
		}
		return "{ " + Joiner.on( "," ).join( suite.getXmlSuite().getPackageNames() ) + " }";
	}

	//endregion

}
