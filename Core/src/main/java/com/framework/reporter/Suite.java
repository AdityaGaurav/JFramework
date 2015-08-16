package com.framework.reporter;

import com.framework.utils.error.PreConditions;
import com.framework.utils.string.ToLogStringStyle;
import com.google.common.base.Joiner;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ISuite;
import org.testng.ITestResult;
import org.testng.SuiteRunner;
import org.testng.xml.XmlSuite;

import java.io.File;
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

public class Suite extends BaseComponent
{

	//region Suite - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( Suite.class );

	/** instance of the ISuite */
	private SuiteRunner suite;

	/** holds the text contexts ( ordered ) within this suite */
	private final Stack<TestContext> testContextStack = new Stack<>();

	/** keeps the BeforeSuite and AfterSuite Configuration methods */
	private ConfigurationMethod beforeSuite;
	private ConfigurationMethod afterSuite;

	/** test-case counters. skippedTestCase is a test case that was never invoked. */
	private int excludedTestCasesCount = 0;

	//endregion


	//region Suite - Constructor Methods Section

	Suite( DateTime dt, ISuite suite )
	{
		super( dt );
		this.suite = ( SuiteRunner ) PreConditions.checkNotNull( suite, "ISuite suite argument cannot be null." );
	}

	//endregion


	//region Suite - Counter Methods Section

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
		super.increaseCounter( "successConfigurationsCount" );
		super.increaseCounter( "configurationsCount" );
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
		increaseCounter( "failedConfigurationsCount" );
		increaseCounter( "configurationsCount" );
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
		increaseCounter( "skippedConfigurationsCount" );
		increaseCounter( "configurationsCount" );
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
		increaseCounter( "successTestCaseInstanceCount" );
		increaseCounter( "testCaseInstanceCount" );
		return getCurrentTestContext().increaseSuccessTestCaseInstanceCounters( result );
	}

	TestCaseInstance increaseFailedTestCaseInstanceCounters( ITestResult result )
	{
		increaseCounter( "failedTestCaseInstanceCount" );
		increaseCounter( "testCaseInstanceCount" );
		return getCurrentTestContext().increaseFailedTestCaseInstanceCounters( result );
	}

	TestCaseInstance increaseFailedWithSuccessPercentageTestCaseInstanceCounters( ITestResult result )
	{
		increaseCounter( "failedWithSuccessPercentageTestCaseInstanceCount" );
		increaseCounter( "testCaseInstanceCount" );
		return getCurrentTestContext().increaseFailedWithSuccessPercentageTestCaseInstanceCounters( result );
	}

	TestCaseInstance increaseSkippedTestCaseInstanceCounters( ITestResult result )
	{
		increaseCounter( "skippedTestCaseCount" );
		increaseCounter( "testCaseInstanceCount" );
		return getCurrentTestContext().increaseSkippedTestCaseInstanceCounters( result );
	}

	void increaseIgnoredTestCaseCounters( ITestResult result )
	{
		increaseCounter( "ignoredTestCaseCount" );
		increaseCounter( "testCaseInstanceCount" );
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

		return new ToStringBuilder( this, ToLogStringStyle.LOG_MULTI_LINE_STYLE )
				.append( "start date", null != getFormattedStartDate() ? getFormattedStartDate() : "N/A" )
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

		return suite.getName().equals( suite1.getName() ) && getStartDate().equals( suite1.getStartDate() );

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

	public boolean isFailed()
	{
		return suite.getSuiteState().isFailed();
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
