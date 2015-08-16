package com.framework.reporter;

import com.framework.utils.datetime.DateTimeUtils;
import com.framework.utils.error.PreConditions;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.math.NumberUtils;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestNGMethod;

import java.text.DecimalFormat;
import java.util.Collection;
import java.util.Map;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.reporter.utils
 *
 * Name   : Counters 
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-03-22 
 *
 * Time   : 13:33
 *
 */

class BaseComponent
{

	//region Counters - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( BaseComponent.class );

	public static final String DATE_TIME_FORMATTER = "yyyy-MM-dd HH:mm:ss.SSS";

	private Map<String,Integer> counters;

	/** registers the starting date and time of the whole executed scenario */
	private final DateTime startDate;

	/**
	 * registers the duration of the whole scenario. the {@code endTime} is calculated
	 * form {@linkplain #startDate} + {@code duration}
	 */
	private Duration duration;

	//endregion


	//region Counters - Constructor Methods Section

	BaseComponent( DateTime dt )
	{
		PreConditions.checkNotNull( dt, "DateTime cannot be null." );

		counters = Maps.newHashMap();
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
		counters.put( "excludedTestCasesCount", 0 );
		this.startDate = dt;
	}


	//endregion


	//region Counters - Public Methods Section

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

	public void setExcludedTestCases( Collection<ITestNGMethod> excludedMethods ) //todo: need method?
	{
		counters.computeIfPresent( "excludedTestCasesCount", ( a, b ) -> b + excludedMethods.size() );
	}

	public Float getSuccessRate()
	{
		if( counters.get( "testCaseInstanceCount" ) == 0 )
		{
			return NumberUtils.createFloat( "0" );
		}
		int successTestCaseInstanceCount = counters.get( "successTestCaseInstanceCount" ) * 100;
		float testCaseInstanceCount = Float.valueOf( counters.get( "testCaseInstanceCount" ) );
		return successTestCaseInstanceCount / testCaseInstanceCount;
	}

	public String getFormattedSuccessRate()
	{
		return new DecimalFormat( "#.##" ).format( getSuccessRate() );
	}

	public String getFormattedStartDate()
	{
		return startDate.toString( Scenario.DATE_TIME_FORMATTER );
	}

	protected DateTime getStartDate()
	{
		return startDate;
	}

	public String getFormattedEndDate()
	{
		return getEndDate().toString( Scenario.DATE_TIME_FORMATTER );
	}

	protected DateTime getEndDate()
	{
		return startDate.plus( duration );
	}

	void recordEndDate()
	{
		this.duration = new Duration( startDate, DateTime.now() );
	}

	protected Duration getDuration()
	{
		return duration;
	}

	public String getFormattedDuration()
	{
		return DateTimeUtils.getFormattedDuration( startDate, getEndDate() );
	}

	//endregion


	//region Counters - Protected Methods Section

	protected void increaseCounter( final String key )
	{
		counters.computeIfPresent( key, ( a, b ) -> b + 1 );
	}

	//endregion


	//region Counters - Private Function Section

	//endregion


	//region Counters - Inner Classes Implementation Section

	//endregion

}
