package com.framework.reporter;

import com.framework.config.ResultStatus;
import com.framework.utils.datetime.DateTimeUtils;
import com.framework.utils.string.LogStringStyle;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.reporter.data
 *
 * Name   : TestCaseInstanceUtil 
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-02-17 
 *
 * Time   : 23:50
 *
 */

public class TestCaseInstance
{

	//region TestCaseInstanceUtil - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( TestCaseInstance.class );

	private final DateTime startDate;

	private Duration duration;

	private static int counter = 0;

	private ResultStatus status = ResultStatus.UNDEFINED;

	private final ITestResult testResult;

	private final TestCase parentTestCase;

	private final int index;

	//endregion


	//region TestCaseInstanceUtil - Constructor Methods Section

	public TestCaseInstance( ITestResult result, TestCase parent )
	{
		this.startDate = DateTime.now();
		this.parentTestCase = parent;
		this.testResult = result;
		this.status = ResultStatus.valueOf( result.getStatus() );
		index = ++ counter;
	}


	//endregion





	//region TestCaseInstanceUtil - Service Methods Section

	@Override
	public String toString()
	{
		ToStringBuilder tsb = new ToStringBuilder( this, LogStringStyle.LOG_MULTI_LINE_STYLE )
				.append( "name", parentTestCase.getName() )
				.append( "start date", null != startDate ? getFormattedStartDate() : "N/A" )
				.append( "index", index )
				.append( "status", status.name() );
		if( duration != null )
		{
			tsb.append( "duration", getFormattedDuration() );
		}
		return tsb.toString();
	}

	//endregion


	//region TetCaseInstance - State and Status Service Methods Section

	public ResultStatus getStatus()
	{
		return ResultStatus.valueOf( testResult.getStatus() );
	}
//
//	private boolean groupResultOverridesChildren()
//	{
//		return ( ( testResult.get == UNDEFINED )
//				|| ( methodStatus == SUCCESS )
//				|| ( methodStatus == STARTED )
//				|| ( methodStatus == PENDING ) );
//	}

	public int getIndex()
	{
		return index;
	}

	//endregion


	//region TetCaseInstance - Joda Time Service Methods Section

	String getFormattedStartDate()
	{
		return startDate.toString( Scenario.DATE_TIME_FORMATTER );
	}

	DateTime getStartDate()
	{
		return startDate;
	}

	String getFormattedEndDate()
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

	String getFormattedDuration()
	{
		return DateTimeUtils.getFormattedDuration( startDate, getEndDate() );
	}

	//endregion

}
