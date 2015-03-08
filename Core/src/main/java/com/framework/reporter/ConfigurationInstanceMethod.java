package com.framework.reporter;

import com.framework.config.ResultStatus;
import com.framework.utils.datetime.DateTimeUtils;
import com.framework.utils.string.ToLogStringStyle;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;

import static com.framework.config.ResultStatus.*;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.reporter
 *
 * Name   : ConfigurationInstanceMethod 
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-02-19 
 *
 * Time   : 00:48
 *
 */

public class ConfigurationInstanceMethod
{

	//region ConfigurationInstanceMethod - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( ConfigurationInstanceMethod.class );

	/** registers the starting date and time of this suite */
	private final DateTime startDate;

	/**
	 * registers the executed duration of the whole suite. the {@code endTime} is calculated
	 * form {@linkplain #startDate} + {@code duration}
	 */
	private Duration duration;

	private ResultStatus methodStatus = ResultStatus.UNDEFINED;

	private final ITestResult result;

	private final ConfigurationMethod parentMethod;

	private static int counter = 0;

	private final int index;

	private Throwable throwable;

	private Object[] parameters;

	//endregion


	//region ConfigurationInstanceMethod - Constructor Methods Section

	ConfigurationInstanceMethod( ITestResult testResult, ConfigurationMethod parent )
	{
		this.startDate = DateTime.now();
		this.parentMethod = parent;
		this.result = testResult;
		this.index = ++ counter;
	}

	//endregion


	//region ConfigurationInstanceMethod - Service Methods Section

	public Throwable getThrowable()
	{
		return throwable;
	}

	public void setThrowable( final Throwable throwable )
	{
		this.throwable = throwable;
	}

	public Object[] getParameters()
	{
		return parameters;
	}

	public void setParameters( final Object[] parameters )
	{
		this.parameters = parameters;
	}

	@Override
	public String toString()
	{
		ToStringStyle stringStyle = ToLogStringStyle.LOG_LINE_STYLE;
		if( throwable != null )
		{
			stringStyle = ToLogStringStyle.LOG_MULTI_LINE_STYLE;
		}
		ToStringBuilder tsb = new ToStringBuilder( this, stringStyle )
				.append( "name", parentMethod.getName() )
				.append( "type", parentMethod.getType() )
				.append( "start date", null != startDate ? getFormattedStartDate() : "N/A" )
				.append( "index", index )
				.append( "status", getStatus().name() );
		if( duration != null )
		{
			tsb.append( "duration", getFormattedDuration() );
		}
		if( throwable != null )
		{
			tsb.append( "throwable", throwable.getMessage() );
		}
		return tsb.toString();
	}

	//endregion


	//region ConfigurationInstanceMethod - ConfigurationMethod Methods Section

	public ConfigurationMethod getParentMethod()
	{
		return parentMethod;
	}

	//endregion


	//region ConfigurationInstanceMethod - State and Status Service Methods Section

	public ResultStatus getStatus()
	{
		methodStatus = ResultStatus.valueOf( result.getStatus() );
		return methodStatus;
	}

	private boolean groupResultOverridesChildren()
	{
		return ( ( methodStatus == UNDEFINED )
				|| ( methodStatus == SUCCESS )
				|| ( methodStatus == STARTED )
				|| ( methodStatus == PENDING ) );
	}

	public int getIndex()
	{
		return index;
	}

	//endregion


	//region ConfigurationInstanceMethod - Joda Time Service Methods Section

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
