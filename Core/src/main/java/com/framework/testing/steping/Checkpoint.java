package com.framework.testing.steping;

import com.framework.asserts.JAssert;
import com.framework.config.ResultStatus;
import com.framework.driver.utils.ui.screenshots.ScreenshotAndHtmlSource;
import com.framework.utils.error.PreConditions;
import com.framework.utils.string.LogStringStyle;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.hamcrest.Description;
import org.joda.time.DateTime;
import org.joda.time.Instant;
import org.joda.time.format.DateTimeFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.testing.steping
 *
 * Name   : StepCheckpoint 
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-01-24 
 *
 * Time   : 16:33
 *
 */

public class Checkpoint //implements TimeComparator<StepCheckpoint>
{

	//region StepCheckpoint - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( Checkpoint.class );

	/**
	 * The parent test step
	 */
	private final TestStepInstance parentStepInstance;

	/**
	 * the checkpoint id given by the test
	 */
	private final String id;

	private final String reason;

	private final Object actual;

	/**
	 * The description of the expected result
	 */
	private final Description expectedResult;

	/**
	 * The timestamp of the checkpoint. can be used in many times.
	 */
	private final Instant instant = DateTime.now().toInstant();

	/**
	 * the status of the checkpoint. starting with {@linkplain com.framework.config.ResultStatus#UNDEFINED}
	 * Possible values are {@linkplain com.framework.config.ResultStatus#FAILURE} or   {@linkplain com.framework.config.ResultStatus#SUCCESS}
	 *
	 */
	private ResultStatus resultStatus = ResultStatus.PENDING;

	private AssertionError assertionError;

	private ScreenshotAndHtmlSource screenshot = null;

	//endregion


	//region StepCheckpoint - Constructor Methods Section

	Checkpoint( final String id, JAssert assertCommand, TestStepInstance parent )
	{
		PreConditions.checkNotNull( assertCommand, "JAssert assertCommand argument cannot be null" );
		this.parentStepInstance = PreConditions.checkNotNull( parent, "TestStepInstance parent argument cannot be null" );
		this.id = PreConditions.checkNotNullNotBlankOrEmpty( id, "String id is either null, empty or blank" );

		this.reason = assertCommand.getReason();
		this.actual = assertCommand.getActual();
		this.expectedResult = assertCommand.getExpected();


		logger.debug( "Creating a new checkpoint < '{}' >", id );

	}

	//endregion


	//region StepCheckpoint - Public Methods Section

	ScreenshotAndHtmlSource getScreenshot()
	{
		return screenshot;
	}

	public void setScreenshot( final ScreenshotAndHtmlSource screenshot )
	{
		this.screenshot = screenshot;
	}

	String getId()
	{
		return id;
	}

	Description getExpectedResult()
	{
		return expectedResult;
	}

	TestStepInstance getParentStepInstance()
	{
		return parentStepInstance;
	}

	Throwable getException()
	{
		if( assertionError != null )
		{
			return ExceptionUtils.getRootCause( assertionError );
		}

		return null;
	}

	public ResultStatus getStatus()
	{
		return resultStatus;
	}

	public void seStatus( final ResultStatus resultStatus )
	{
		this.resultStatus = resultStatus;
	}

	public String getReason()
	{
		return reason;
	}

	public Object getActualResult()
	{
		return actual;
	}

	public AssertionError getAssertionError()
	{
		return assertionError;
	}

	public Instant getInstant()
	{
		return this.instant;
	}

	public void setAssertionError( final AssertionError assertionError )
	{
		this.assertionError = assertionError;
	}

	//endregion


	//region StepCheckpoint - TimeComparing implementation Section.

//	@Override
//	public boolean isAfter( final TimeComparator<StepCheckpoint> other )
//	{
//		Instant otherInstance = other.getInstant();
//		return this.getInstant().isAfter( otherInstance );
//	}
//
//	@Override
//	public boolean isBefore( final TimeComparator<StepCheckpoint> other )
//	{
//		Instant otherInstance = other.getInstant();
//		return this.getInstant().isBefore( otherInstance );
//	}
//
//	@Override
//	public boolean isSameDateTime( final TimeComparator<StepCheckpoint> other )
//	{
//		Instant otherInstance = other.getInstant();
//		return this.getInstant().isEqual( otherInstance );
//	}





	//endregion


	//region StepCheckpoint - Object Override implementation Section.

	@Override
	public boolean equals( final Object o )
	{
		if ( this == o )
		{
			return true;
		}
		if ( ! ( o instanceof Checkpoint ) )
		{
			return false;
		}

		final Checkpoint that = ( Checkpoint ) o;

		if ( ! id.equals( that.id ) )
		{
			return false;
		}
		if ( parentStepInstance != null ? ! parentStepInstance.equals( that.parentStepInstance ) : that.parentStepInstance != null )
		{
			return false;
		}
		if ( ! instant.equals( that.instant ) )
		{
			return false;
		}

		return true;
	}

	@Override
	public int hashCode()
	{
		int result1 = id.hashCode();
		result1 = 31 * result1 + ( expectedResult != null ? expectedResult.hashCode() : 0 );
		result1 = 31 * result1 + instant.hashCode();
		result1 = 31 * result1 + parentStepInstance.hashCode();
		result1 = 31 * result1 + reason.hashCode();
		result1 = 31 * result1 + actual.hashCode();
		return result1;
	}

	//todo: modify and examine information displayed.
	@Override
	public String toString()
	{
		return new ToStringBuilder( this, LogStringStyle.LOG_LINE_STYLE )
				.append( "id", id )
				.append( "status", resultStatus.getStatusName() )
				.toString();
	}

	public String toString( ToStringStyle style )
	{
		return new ToStringBuilder( this, style )
				.append( "id", id )
				.append( "reason", reason )
				.append( "expected result", expectedResult.toString() )
				.append( "actual result", actual.toString() )
				.append( "instant", instant.toString( DateTimeFormat.forPattern( "MMM-dd-yyyy HH:mm:ss.SSS" ) ) )
				.append( "has screenshot", screenshot != null ? screenshot.wasTaken() : "N/A" )
				.append( "status", resultStatus.getStatusName() )
				.toString();
	}

	//endregion
}
