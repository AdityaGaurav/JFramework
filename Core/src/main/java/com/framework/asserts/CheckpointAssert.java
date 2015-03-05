package com.framework.asserts;

import com.framework.config.ResultStatus;
import com.framework.driver.event.HtmlDriver;
import com.framework.driver.utils.ui.screenshots.Photographer;
import com.framework.testing.steping.StepEventBus;
import com.framework.utils.error.PreConditions;
import com.framework.utils.string.LogStringStyle;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.openqa.selenium.TimeoutException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.asserts
 *
 * Name   : CheckpointAssert 
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-01-26 
 *
 * Time   : 14:23
 *
 */

public class CheckpointAssert extends JAssertion
{

	//region CheckpointAssert - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( CheckpointAssert.class );

	/** the id of the checkpoint. use the id to identify the checkpoint in results */
	private final String id;

	private CheckPointCollector collector;

	//endregion


	//region CheckpointAssert - Constructor Methods Section

	/**
	 * Creates an instance of a checkpoint.
	 *
	 * @param driver     The active {@code HtmlDriver}
	 * @param id         a string representing the checkpoint identifier.
	 * @param collector  a {@linkplain CheckPointCollector} that collect the results.
	 */
	public CheckpointAssert( final String id, CheckPointCollector collector )
	{
	    this( null, id, collector );
	}

	public CheckpointAssert( final HtmlDriver driver, final String id, CheckPointCollector collector )
	{
		super( driver );
		PreConditions.checkNotNullNotBlankOrEmpty( id, "The checkpoint id is either null, blank or empty" );
		this.id = id;
		this.collector = collector;
	}


	//endregion


	//region CheckpointAssert - Public Methods Section

	/**
	 * {@inheritDoc}
	 * displays a message of the checkpoint id being executed.
	 */
	@Override
	protected void onBeforeAssert( final JAssert assertCommand )
	{
		setStatus( ResultStatus.PENDING );
		logger.debug( "Executing checkpoint \"{}\"", getId() );
		StepEventBus.getEventBus().beforeCheckpoint( getId(), assertCommand );
	}

	/**
	 * {@inheritDoc}
	 * displays a message of the checkpoint that was successfully executed.
	 */
	@Override
	protected void onAssertSuccess( final JAssert assertCommand )
	{
		setStatus( ResultStatus.SUCCESS );
		logger.debug( "Executed checkpoint [{}]: {} -> {}", getId(), assertCommand.getReason(), getStatus() );
	}

	/**
	 * {@inheritDoc}
	 * change the checkpoint status and takes a screenshot.
	 * then is collecting all the checkpoint information to be displayed.
	 */
	@Override
	protected void onAssertFailure( final JAssert assertCommand, final AssertionError ex )
	{
		setStatus( ResultStatus.FAILURE );
		Photographer photographer = new Photographer( getDriver() );
		photographer.setLogger( logger );
		setSnapshot( photographer.grabScreenshot() );

		ToStringBuilder stb = new ToStringBuilder( assertCommand, LogStringStyle.LOG_MULTI_LINE_STYLE );
		stb.append( "checkpoint id", getId() )
				.append( "status", getStatus().getStatusName() )
				.append( "reason", assertCommand.getReason() )
				.append( "actual result", assertCommand.getActual() )
				.append( "expected result", assertCommand.getExpected().toString() )
				.append( "assertion error", ex.getMessage() );
		if( getSnapshot().isPresent() && getSnapshot().get().wasTaken() )
		{
			stb.append( "screenshot file result",getSnapshot().get().getScreenshotName() )
					.append( "html source file result", getSnapshot().get().getHtmlSourceName() )
					.append( "screenshot path", getSnapshot().get().getScreenshotFile().getPath() );
			if( getSnapshot().get().getHtmlSource().isPresent() )
			{
				stb.append( "html source path", getSnapshot().get().getHtmlSource().get().getAbsolutePath() );
			}
		}
		else
		{
			stb.append( "screenshot was taken", BooleanUtils.toStringYesNo( getSnapshot().get().wasTaken() ) );
		}

		logger.error( "Checkpoint Report: {}", stb.toString() );
	}

	@Override
	protected void onAfterAssert( final JAssert assertCommand )
	{
		collector.addCheckpointInfo( getId(), getStatus() );
		StepEventBus.getEventBus().afterCheckpoint( getId(), this );
	}

	@Override
	protected void doAssert( JAssert assertCommand )
	{
		onBeforeAssert( assertCommand );
		try
		{
			executeAssert( assertCommand );
			onAssertSuccess( assertCommand );
		}
		catch ( AssertionError ex )
		{
			onAssertFailure( assertCommand, ex );
		}
		catch ( TimeoutException ex )
		{
			AssertionError aErr = new AssertionError( ex );
			onAssertFailure( assertCommand, aErr );
		}

		onAfterAssert( assertCommand );
	}


	public String getId()
	{
		return id;
	}

	//endregion

}
