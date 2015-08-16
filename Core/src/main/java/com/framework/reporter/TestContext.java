package com.framework.reporter;

import ch.lambdaj.Lambda;
import com.framework.config.ResultStatus;
import com.framework.utils.datetime.DateTimeUtils;
import com.framework.utils.error.PreConditions;
import com.framework.utils.matchers.JMatchers;
import com.framework.utils.string.ToLogStringStyle;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.math.NumberUtils;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.TestRunner;
import org.testng.xml.XmlTest;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;
import java.util.Stack;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.reporter.data
 *
 * Name   : TestContextUtil 
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-02-14 
 *
 * Time   : 22:42
 *
 */

public class TestContext
{

	//region TestContext - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( TestContext.class );

	private enum ConfigurationType
	{
		AFTER_SUITE, AFTER_TEST, AFTER_CLASS, AFTER_GROUP, AFTER_METHOD,
		BEFORE_METHOD, BEFORE_GROUP, BEFORE_CLASS, BEFORE_TEST, BEFORE_SUITE
	}

	/** registers the starting date and time of this text context.  */
	private final DateTime startDate;

	/**
	 * registers the executed duration of the whole suite. the {@code endTime} is calculated
	 * form {@linkplain #startDate} + {@code duration}
	 */
	private Duration duration;

	private final Suite parentSuite;

	private final TestRunner testContextRunner;

	private Map<String,Integer> counters;

	private Stack<TestCase> invokedTestCases = new Stack<>();

	private List<ConfigurationMethod> configurationMethods = Lists.newArrayList();
	//endregion


	//region TestContext - Constructor Methods Section

	TestContext( final DateTime dt, ITestContext context, Suite parent )
	{
		this.startDate = PreConditions.checkNotNull( dt, "DateTime argument cannot be null." );
		this.testContextRunner =
				( TestRunner ) PreConditions.checkNotNull( context, "ITestContext context argument cannot be null." );
		this.parentSuite = PreConditions.checkNotNull( parent, "Suite argument cannot be null." );

		int x = testContextRunner.getSuite().getAllInvokedMethods().size();  //todo: validate diff n- - counts.beforesuite
		int y =  testContextRunner.getSuite().getAllMethods().size();        //todo: validate diff

		counters = Maps.newLinkedHashMap();
		counters.put( "configurationsCount", 0 );
		counters.put( "successConfigurationsCount", 0 );
		counters.put( "skippedConfigurationsCount", 0 );
		counters.put( "failedConfigurationsCount", 0 );
		counters.put( "testCaseInstanceCount", 0 );
		counters.put( "successTestCaseInstanceCount", 0 );
		counters.put( "failedTestCaseInstanceCount", 0 );
		counters.put( "skippedTestCaseCount", 0 );
		counters.put( "ignoredTestCaseCount", 0 );
		counters.put( "failedWithSuccessPercentageTestCaseInstanceCount", 0 );

		logger.info( "starting TestContext instance. name -> < {} >", context.getName() );
	}

	//endregion


	//region Suite - Counter Methods Section

	public int getCounterValue( final String key )
	{
		if( counters.containsKey( key ) )
		{
			return counters.get( key );
		}

		return NumberUtils.INTEGER_MINUS_ONE;
	}

	public int getTotalInvokedMethodsCount()
	{
		return counters.get( "configurationsCount" ) + counters.get( "testCaseInstanceCount" );
	}

	ConfigurationInstanceMethod increaseSuccessConfigurationInstanceCounters( ITestResult itr )
	{
		ConfigurationMethod cf = getConfigurationMethod( itr );
		ConfigurationInstanceMethod cif = cf.getCurrentConfigurationInstanceMethod();
		cif.recordEndDate();
		counters.computeIfPresent( "successConfigurationsCount", ( a, b ) -> b + 1 );
		counters.computeIfPresent( "configurationsCount", ( a, b ) -> b + 1 );
		return cif;
	}

	ConfigurationInstanceMethod increaseFailedConfigurationInstanceCounters( ITestResult itr )
	{
		ConfigurationMethod cf = getConfigurationMethod( itr );
		ConfigurationInstanceMethod cif = cf.getCurrentConfigurationInstanceMethod();
		cif.recordEndDate();
		counters.computeIfPresent( "failedConfigurationsCount", ( a, b ) -> b + 1 );
		counters.computeIfPresent( "configurationsCount", ( a, b ) -> b + 1 );
		return cif;
	}

	ConfigurationInstanceMethod increaseSkippedConfigurationInstanceCounters( ITestResult itr )
	{
		ConfigurationMethod cf = getConfigurationMethod( itr );
		ConfigurationInstanceMethod cif = cf.getCurrentConfigurationInstanceMethod();
		cif.recordEndDate();
		counters.computeIfPresent( "skippedConfigurationsCount", ( a, b ) -> b + 1 );
		counters.computeIfPresent( "configurationsCount", ( a, b ) -> b + 1 );
		return cif;
	}

	TestCaseInstance increaseSuccessTestCaseInstanceCounters( ITestResult result )
	{
		counters.computeIfPresent( "successTestCaseInstanceCount", ( a, b ) -> b + 1 );
		counters.computeIfPresent( "testCaseInstanceCount", ( a, b ) -> b + 1 );



		TestCaseInstance tci = invokedTestCases.peek().getCurrentTestCaseInstance();
		tci.recordEndDate();
		tci.addStatus( ResultStatus.SUCCESS );
		return tci;
	}

	TestCaseInstance increaseFailedTestCaseInstanceCounters( ITestResult result )
	{
		counters.computeIfPresent( "failedTestCaseInstanceCount", ( a, b ) -> b + 1 );
		counters.computeIfPresent( "testCaseInstanceCount", ( a, b ) -> b + 1 );
		TestCaseInstance tci = invokedTestCases.peek().getCurrentTestCaseInstance();
		tci.recordEndDate();
		tci.addStatus( ResultStatus.FAILURE );
		return tci;
	}

	TestCaseInstance increaseFailedWithSuccessPercentageTestCaseInstanceCounters( ITestResult result )
	{
		counters.computeIfPresent( "failedWithSuccessPercentageTestCaseInstanceCount", ( a, b ) -> b + 1 );
		counters.computeIfPresent( "testCaseInstanceCount", ( a, b ) -> b + 1 );
		TestCaseInstance tci = invokedTestCases.peek().getCurrentTestCaseInstance();
		tci.recordEndDate();
		tci.addStatus( ResultStatus.SUCCESS_PERCENTAGE_FAILURE );
		return tci;
	}

	TestCaseInstance increaseSkippedTestCaseInstanceCounters( ITestResult result )
	{
		counters.computeIfPresent( "skippedTestCaseInstanceCount", ( a, b ) -> b + 1 );
		counters.computeIfPresent( "testCaseInstanceCount", ( a, b ) -> b + 1 );
		TestCaseInstance tci = invokedTestCases.peek().getCurrentTestCaseInstance();
		tci.recordEndDate();
		tci.addStatus( ResultStatus.SKIPPED );
		return tci;
	}

	TestCaseInstance increaseIgnoredTestCaseCounters( ITestResult result )
	{
		counters.computeIfPresent( "ignoredTestCasesCount", ( a, b ) -> b + 1 );
		counters.computeIfPresent( "testCaseInstanceCount", ( a, b ) -> b + 1 );
		//TestCaseInstance tci = invokedTestCases.peek().createTestCaseInstance( result );
		//tci.addStatus( ResultStatus.IGNORED );
		//return tci;
		return null;
	}

	void addConfigurationInstance( ITestResult itr )
	{
		ConfigurationMethod cf = getConfigurationMethod( itr );
		cf.createConfigurationMethodInstance( itr );
	}

	TestCase addTestCase( ITestResult itr )
	{
		TestCase tc = getTestCase( itr );
		tc.createTestCaseInstance( itr );
		return tc;
	}

	TestCase getCurrentTestCase()
	{
		return invokedTestCases.peek();
	}

	TestCase getTestCase( ITestResult itr )
	{
		TestCase tc;
		if( invokedTestCases.isEmpty() )
		{
			tc = new TestCase( itr, this );
			invokedTestCases.push( tc );
		}
		else
		{
			tc = Lambda.selectFirst( invokedTestCases,
					Lambda.having( Lambda.on( TestCase.class ).getTestNGMethod().getConstructorOrMethod().getName(),
							JMatchers.equalToIgnoringCase( itr.getMethod().getConstructorOrMethod().getName() ) ) );
			if( tc == null )
			{
				tc = new TestCase( itr, this );
				invokedTestCases.push( tc );
			}
		}

		return tc;
	}


	private ConfigurationMethod getConfigurationMethod( ITestResult itr )
	{
		ConfigurationMethod cf;
		if( configurationMethods.isEmpty() )
		{
			cf = new ConfigurationMethod( itr, this );
			configurationMethods.add( cf );
		}
		else
		{
			cf = Lambda.selectFirst( configurationMethods,
					Lambda.having( Lambda.on( ConfigurationMethod.class ).getTestNGMethod().getConstructorOrMethod().getName(),
							JMatchers.equalToIgnoringCase( itr.getMethod().getConstructorOrMethod().getName() ) ) );
			if( cf == null )
			{
				cf = new ConfigurationMethod( itr, this );
				configurationMethods.add( cf );
			}
		}

		return cf;
	}

	//endregion


	//region TestContext - Service Methods Section

	public Suite getParentSuite()
	{
		return parentSuite;
	}

	void terminate()
	{
//		for( TestCase testCase : testCases )
//		{
//			testCase.terminate();
//		}
//		testCases.clear();
		for( ConfigurationMethod configs : configurationMethods )
		{
			configs.terminate();
		}
		configurationMethods.clear();
	}

	@Override
	public String toString()
	{
		return new ToStringBuilder( this, ToLogStringStyle.LOG_MULTI_LINE_STYLE )
				.append( "start date", null != startDate ? getFormattedEndDate() : "N/A" )
				.append( "name", testContextRunner.getName() )
				.append( "parent suite", parentSuite.getName() )
				.append( "index", testContextRunner.getTest().getIndex() )
				.append( "classes", testContextRunner.getTest().getClasses().size() )
				.append( "packages", testContextRunner.getTest().getPackages().size() )
				.append( "included groups", Joiner.on( ", " ).join( testContextRunner.getTest().getIncludedGroups() ) )
				.append( "excluded groups", Joiner.on( ", " ).join( testContextRunner.getTest().getExcludedGroups() ) )
				.append( "test-cases", testContextRunner.getAllTestMethods().length )
				.append( "excluded test-cases", testContextRunner.getExcludedMethods().size() )
				.toString();

	}

	//endregion


	//region TestContext - State and Status Service Methods Section

	public Float getSuccessRate()
	{
		int successTestCaseInstanceCount = counters.get( "successTestCaseInstanceCount" ) * 100;
		float testCaseInstanceCount = Float.valueOf( counters.get( "testCaseInstanceCount" ) );
		return successTestCaseInstanceCount / testCaseInstanceCount;
	}

	public String getFormattedSuccessRate()
	{
		return new DecimalFormat( "#.##" ).format( getSuccessRate() );
	}

	public boolean isFailed()
	{
		return testContextRunner.getSuite().getSuiteState().isFailed();
	}

	//endregion


	//region TestContext - Joda Time Service Methods Section

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


	//region TestContext - TestRunner runtime info and metadata Methods Section

	List<ITestNGMethod> getExcludedTestCases()
	{
		return Lists.newArrayList( testContextRunner.getExcludedMethods() );
	}

	int getIndex()
	{
		return testContextRunner.getCurrentXmlTest().getIndex();
	}

	public String getName()
	{
		return testContextRunner.getName();
	}

	public TestRunner getTestRunner()
	{
		return testContextRunner;
	}

	public XmlTest getCurrentXmlTest()
	{
		return testContextRunner.getCurrentXmlTest();
	}

	public String getCurrentXmlTestIncludedGroups()
	{
		if( testContextRunner.getCurrentXmlTest().getIncludedGroups().size() == 0 )
		{
			return "{ }";
		}
		return "{ " + Joiner.on( "," ).join( testContextRunner.getCurrentXmlTest().getIncludedGroups() ) + " }";
	}

	public String getCurrentXmlTestExcludedGroups()
	{
		if( testContextRunner.getCurrentXmlTest().getExcludedGroups().size() == 0 )
		{
			return "{ }";
		}
		return "{ " + Joiner.on( "," ).join( testContextRunner.getCurrentXmlTest().getExcludedGroups() ) + " }";
	}

	//endregion




}
