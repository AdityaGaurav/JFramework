package com.framework.reporter.utils;

import com.framework.utils.datetime.DateTimeUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.testng.ITestResult;

import java.text.DecimalFormat;
import java.util.AbstractMap;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.reporter.utils
 *
 * Name   : ReporterUtil 
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-02-13 
 *
 * Time   : 18:23
 *
 */

@Deprecated
@SuppressWarnings ( "UnusedDeclaration" )
public class ReporterUtil
{

	//region ReporterUtil - Variables Declaration and Initialization Section.

	private List<ITestResult> totalFailedTestCases = Lists.newArrayList();

	private List<ITestResult> totalPassedTestCases = Lists.newArrayList();

	private List<ITestResult> totalSkippedTestCases = Lists.newArrayList();

	private List<ITestResult> totalSuccessPercentageTestCases = Lists.newArrayList();

	private int totalClasses = 0, totalSuites = 0, totalTests = 0, totalInvokedMethods = 0, totalTestMethods = 0;

	private int totalInvokedConfigurations = 0, totalInvokedTestMethods = 0, totalExcludedMethods = 0;

	private Map<String,DateTime> timings = Maps.newHashMap();

	private Map<String,Float> successRates = Maps.newHashMap();

	private Map.Entry<DateTime,DateTime> scenarioTimings;

	//endregion


	//region ReporterUtil - Constructor Methods Section

	//endregion


	//region ReporterUtil - Public Methods Section

	public List<ITestResult> getTotalFailedTestCases()
	{
		return totalFailedTestCases;
	}

	public void addTotalFailedTestCases( final List<ITestResult> totalFailedTestCases )
	{
		this.totalFailedTestCases.addAll( totalFailedTestCases );
	}

	public List<ITestResult> getTotalPassedTestCases()
	{
		return totalPassedTestCases;
	}

	public void addTotalPassedTestCases( final List<ITestResult> totalPassedTestCases )
	{
		this.totalPassedTestCases.addAll( totalPassedTestCases );
	}

	public List<ITestResult> getTotalSkippedTestCases()
	{
		return totalSkippedTestCases;
	}

	public void addTotalSkippedTestCases( final List<ITestResult> totalSkippedTestCases )
	{
		this.totalSkippedTestCases.addAll( totalSkippedTestCases );
	}

	public List<ITestResult> getTotalSuccessPercentageTestCases()
	{
		return totalSuccessPercentageTestCases;
	}

	public void addTotalSuccessPercentageTestCases( final List<ITestResult> totalSuccessPercentageTestCases )
	{
		this.totalSuccessPercentageTestCases.addAll( totalSuccessPercentageTestCases );
	}

	public String getTotalSuccessRate()
	{
		float avg = ( getTotalPassedTestCases().size() * 100 ) / ( float ) getTotalTestMethods();
		return new DecimalFormat( "#.##" ).format( avg );
	}

	public String getSuccessRate( String key )
	{
		float rate = successRates.get( key );
		return new DecimalFormat( "#.##" ).format( rate );
	}

	public void setSuccessRate( final String key, Float value )
	{
		this.successRates.put( key, value );
	}

	public int getTotalClasses()
	{
		return totalClasses;
	}

	public void setTotalClasses( final int totalClasses )
	{
		this.totalClasses = totalClasses;
	}

	public int getTotalSuites()
	{
		return totalSuites;
	}

	public void setTotalSuites( final int totalSuites )
	{
		this.totalSuites = totalSuites;
	}

	public int getTotalInvokedMethods()
	{
		return totalInvokedMethods;
	}

	public void setTotalInvokedMethods( final int totalInvokedMethods )
	{
		this.totalInvokedMethods = totalInvokedMethods;
	}

	public int getTotalTestMethods()
	{
		return totalTestMethods;
	}

	public void setTotalTestMethods( final int totalTestMethods )
	{
		this.totalTestMethods = totalTestMethods;
	}

	public int getTotalTests()
	{
		return totalTests;
	}

	public void setTotalTests( final int totalTests )
	{
		this.totalTests = totalTests;
	}

	public int getTotalInvokedConfigurations()
	{
		return totalInvokedConfigurations;
	}

	public void setTotalInvokedConfigurations( final int totalInvokedConfigurations )
	{
		this.totalInvokedConfigurations = totalInvokedConfigurations;
	}

	public int getTotalInvokedTestMethods()
	{
		return totalInvokedTestMethods;
	}

	public void setTotalInvokedTestMethods( final int totalInvokedTestMethods )
	{
		this.totalInvokedTestMethods = totalInvokedTestMethods;
	}

	public int getTotalExcludedMethods()
	{
		return totalExcludedMethods;
	}

	public void setTotalExcludedMethods( final int totalExcludedMethods )
	{
		this.totalExcludedMethods = totalExcludedMethods;
	}

	public String getFormattedDate( final Date date )
	{
		return getFormattedDate(  new DateTime( date ) );
	}

	private String getFormattedDate( final DateTime date )
	{
		DateTime dt = new DateTime( date );
		return dt.toString( "yyyy-MM-dd HH:mm:ss.SSS" );
	}

	public String getFormattedDuration( final Date start, final Date end )
	{
		return getFormattedDuration( new DateTime( start ), new DateTime( end ) );
	}

	private String getFormattedDuration( final DateTime start, final DateTime end )
	{
		Duration duration = new Duration( start, end );
		return DateTimeUtils.getFormattedPeriodAbbr( duration );
	}

	public String getSuiteEndDate( String key )
	{
		return getFormattedDate( timings.get( key + "_end" ) );
	}

	public String getSuiteStartDate( String key )
	{
		return getFormattedDate( timings.get( key + "_start" ) );
	}

	public String getSuiteDuration( String name )
	{
		return getFormattedDuration( timings.get( name + "_start" ), timings.get( name + "_end" ) );
	}

	public void setSuiteTiming( final String key, DateTime dte )
	{
		this.timings.put( key, dte );
	}

	public void setScenarioTiming( DateTime start, DateTime end )
	{
		scenarioTimings = new AbstractMap.SimpleImmutableEntry<>( start, end );
	}

	public String getScenarioStartDate()
	{
		return getFormattedDate( scenarioTimings.getKey() );
	}

	public String getScenarioEndDate()
	{
		return getFormattedDate( scenarioTimings.getValue() );
	}

	public String getScenarioDuration()
	{
		return getFormattedDuration( scenarioTimings.getKey(), scenarioTimings.getValue() );
	}

	//endregion


	//region ReporterUtil - Protected Methods Section

	//endregion


	//region ReporterUtil - Private Function Section

	//endregion


	//region ReporterUtil - Inner Classes Implementation Section

	//endregion

}
