package com.framework.testing.steping;

import ch.lambdaj.Lambda;
import com.framework.config.ResultStatus;
import com.framework.driver.utils.ui.screenshots.ScreenshotAndHtmlSource;
import com.framework.reporter.Scenario;
import com.framework.utils.datetime.DateTimeUtils;
import com.framework.utils.string.ErrorMessageFormatter;
import com.framework.utils.string.ToLogStringStyle;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.testing.steping
 *
 * Name   : TestStepInstance 
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-02-21 
 *
 * Time   : 20:32
 *
 */

public class TestStepInstance
{

	//region TestStepInstance - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( TestStepInstance.class );

	private final DateTime startDate;

	private Duration duration;

	private List<ResultStatus> statuses = Lists.newArrayList( ResultStatus.PENDING );

	private ResultStatus finalStatus = null;

	private final TestStep parentTestStep;

	private List<Throwable> exceptions = Lists.newArrayList();

	private List<ScreenshotAndHtmlSource> screenshots = Lists.newArrayList();

	/**
	 * checkpoint statistics
	 */
	private Map<String,Checkpoint> allCheckpoints = Maps.newLinkedHashMap();;
	private List<Checkpoint> failedCheckpoints = Lists.newArrayList();
	private List<Checkpoint> successCheckpoints = Lists.newArrayList();

	//endregion


	//region TestStepInstance - Constructor Methods Section

	public TestStepInstance( TestStep parent )
	{
		this.startDate = DateTime.now();
		this.parentTestStep = parent;
	}


	//endregion


	//region TestStepInstance - Service Methods Section

	public TestStep getParentTestStep()
	{
		return parentTestStep;
	}

	@Override
	public String toString()
	{
		ToStringBuilder tsb = new ToStringBuilder( this, ToLogStringStyle.LOG_MULTI_LINE_STYLE )
				.append( "name", parentTestStep.getDescription() )
				.append( "start date", null != startDate ? getFormattedStartDate() : "N/A" )
				.append( "number", parentTestStep.getNumber() )
				.append( "statuses", Lambda.join( Lambda.extractString( statuses ), ", " ) );
		if( duration != null )
		{
			tsb.append( "duration", getFormattedDuration() );
		}
		return tsb.toString();
	}
	//endregion


	//region TestStepInstance - Error Methods Section

	List<Throwable> getExceptions()
	{
		return exceptions;
	}

	String getErrorMessage( int index )
	{
		return ( this.exceptions.size() > 0 ) ? this.exceptions.get( index ).getMessage() : StringUtils.EMPTY;
	}

	String getShortErrorMessage( int index )
	{
		return new ErrorMessageFormatter( getErrorMessage( index ) ).getShortErrorMessage();
	}

	List<String> getAllErrorMessages()
	{
		if( exceptions.size() > 0 )
		{
			List<String> messages = Lists.newArrayListWithExpectedSize( this.exceptions.size() );
			for( Throwable exception : this.exceptions )
			{
				messages.add( getShortErrorMessage( exception ) );
			}

			return messages;
		}

		return Lists.newArrayListWithExpectedSize( 0 );
	}

	private boolean exceptionsAreEqual( Throwable exception, Throwable otherException )
	{
		if ( ( exception == null ) && ( otherException == null ) )
		{
			return true;
		}
		assert exception != null;
		return ( StringUtils.equals( exception.getMessage(), otherException.getMessage() )
				&& ( exceptionsAreEqual( exception.getCause(), otherException.getCause() ) ) );
	}

	private String getShortErrorMessage( Throwable exception )
	{
		return new ErrorMessageFormatter( exception.getMessage() ).getShortErrorMessage();
	}

	public void addThrowable( Throwable t )
	{
		this.exceptions.add( t );
	}

	public void reportErrors( Logger log )
	{
		ToStringBuilder tsb = new ToStringBuilder( this, ToLogStringStyle.LOG_MULTI_LINE_STYLE );
		List<String> errors = getAllErrorMessages();
		for( int i = 0; i < errors.size(); i ++ )
		{
			tsb.append( "error " + ( i + 1 ), getErrorMessage( i ) );
			tsb.append( "short error " + ( i + 1 ), getShortErrorMessage( i ) );
		}

		log.error( "Errors: {}", tsb.toString() );
	}

	//endregion


	//region TestStepInstance - Screenshots Methods Section

	void terminate()
	{
		this.getAllCheckpoints().clear();
		this.getFailedCheckpoints().clear();
		this.getSuccessCheckpoints().clear();
		this.getFailedCheckpoints().clear();
		this.screenshots.clear();
		this.getExceptions().clear();
	}

	ScreenshotAndHtmlSource getFirstScreenshot()
	{
		return screenshots.get( 0 );
	}

	boolean hasScreenshots()
	{
		return ! screenshots.isEmpty();
	}

	public TestStepInstance addScreenshot( ScreenshotAndHtmlSource screenshotAndHtmlSource )
	{
		if ( thisIsANew( screenshotAndHtmlSource ) )
		{
			screenshots.add( screenshotAndHtmlSource );
		}
		return this;
	}

	public List<ScreenshotAndHtmlSource> getScreenshots()
	{
		return ImmutableList.copyOf( screenshots );
	}

	void removeScreenshot( int index )
	{
		this.screenshots.remove( index );
	}

	int getScreenshotCount()
	{
		return screenshots.size();
	}

	private boolean thisIsANew( ScreenshotAndHtmlSource screenshotAndHtmlSource )
	{
		if ( screenshots.isEmpty() )
		{
			return true;
		}
		else
		{
			ScreenshotAndHtmlSource latestScreenshotAndHtmlSource = screenshots.get( screenshots.size() - 1 );
			return ! latestScreenshotAndHtmlSource.equals( screenshotAndHtmlSource );
		}
	}


	//endregion


	//region TestStepInstance - Checkpoints Methods Section

	public Map<String,Checkpoint> getAllCheckpoints()
	{
		return allCheckpoints;
	}

	public List<Checkpoint> getFailedCheckpoints()
	{
		return failedCheckpoints;
	}

	public List<Checkpoint> getSuccessCheckpoints()
	{
		return successCheckpoints;
	}

	public void injectCheckpoint( Checkpoint checkpoint )
	{
		allCheckpoints.putIfAbsent( checkpoint.getId(), checkpoint );
	}

	public Checkpoint getCheckpoint( String id )
	{
		return allCheckpoints.get( id );
	}

	//endregion


	//region TestStepInstance - Status Methods Section

	public void setStatuses( final ResultStatus resultStatus )
	{
		statuses.add( resultStatus );
	}

	public List<ResultStatus> getStatuses()
	{
		return statuses;
	}

	public ResultStatus getFinalStatus()
	{
		if( null != finalStatus ) return finalStatus;
		TestResultList trl = new TestResultList( getStatuses() );
		this.finalStatus =  trl.getOverallResult();
		return finalStatus;
	}

	Boolean isSuccessful()
	{
		return getFinalStatus() == ResultStatus.SUCCESS;
	}

	Boolean isFailure()
	{
		return getFinalStatus() == ResultStatus.FAILURE;
	}

	Boolean isError()
	{
		return getFinalStatus() == ResultStatus.ERROR;
	}

	Boolean isIgnored()
	{
		return getFinalStatus() == ResultStatus.IGNORED;
	}

	Boolean isSkipped()
	{
		return getFinalStatus() == ResultStatus.SKIPPED;
	}

	public Boolean isPending()
	{
		return getFinalStatus() == ResultStatus.PENDING;
	}

	//endregion


	//region TestStepInstance - Joda Time Service Methods Section

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

	public void recordEndDate()
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


}
