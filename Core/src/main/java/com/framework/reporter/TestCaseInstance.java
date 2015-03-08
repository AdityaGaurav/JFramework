package com.framework.reporter;

import ch.lambdaj.Lambda;
import com.framework.config.ResultStatus;
import com.framework.testing.steping.TestResultList;
import com.framework.testing.steping.TestStep;
import com.framework.testing.steping.TestStepInstance;
import com.framework.utils.datetime.DateTimeUtils;
import com.framework.utils.string.ToLogStringStyle;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;

import java.util.List;
import java.util.Map;


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

	private List<ResultStatus> statuses = Lists.newArrayList( ResultStatus.PENDING );

	private ResultStatus finalStatus = null;

	private final ITestResult testResult;

	private final TestCase parentTestCase;

	private final int index;

	private Map<Float,TestStepInstance> testSteps = Maps.newHashMap();

	//endregion


	//region TestCaseInstanceUtil - Constructor Methods Section

	public TestCaseInstance( ITestResult result, TestCase parent )
	{
		this.startDate = DateTime.now();
		this.parentTestCase = parent;
		this.testResult = result;
		this.statuses.add( ResultStatus.STARTED );
		index = ++ counter;
	}


	//endregion





	//region TestCaseInstanceUtil - Service Methods Section

	public TestStepInstance getTestStepInstance( float number )
	{
		return testSteps.get( number );
	}

	public TestStepInstance injectTestStep( TestStep testStep )
	{
		TestStepInstance tsi = new TestStepInstance( testStep );
		logger.debug( "creating a new TestStepInstance -> < {} >", tsi.toString() );
		testSteps.put( testStep.getNumber(), tsi );
		logger.debug( "putting step in testSteps. current map size is -> < {} > ", testSteps.size() );
		return tsi;
	}

	public TestCase getParentTestCase()
	{
		return parentTestCase;
	}

	@Override
	public String toString()
	{
		ToStringBuilder tsb = new ToStringBuilder( this, ToLogStringStyle.LOG_MULTI_LINE_STYLE )
				.append( "name", parentTestCase.getName() )
				.append( "start date", null != startDate ? getFormattedStartDate() : "N/A" )
				.append( "index", index )
				.append( "status", Lambda.join( Lambda.extractString( statuses ), ", " ) );
		if( duration != null )
		{
			tsb.append( "duration", getFormattedDuration() );
		}
		return tsb.toString();
	}

	//endregion


	//region TetCaseInstance - State and Status Service Methods Section

	void addStatus( ResultStatus status )
	{
		statuses.add( status );
	}

	public ResultStatus getStatus()
	{
		if( testSteps.isEmpty() )
		{
			return ResultStatus.valueOf( testResult.getStatus() );
		}

		if( finalStatus != null )
		{
			return finalStatus;
		}

		List<ResultStatus> temp = Lists.newArrayList( statuses );
		for( TestStepInstance tsi : testSteps.values() )
		{
			temp.add( tsi.getFinalStatus() );
		}
		TestResultList trl = new TestResultList( temp );
		this.finalStatus =  trl.getOverallResult();
		return finalStatus;
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
