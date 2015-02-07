package com.framework.asserts;

import com.framework.config.ResultStatus;
import com.framework.driver.event.HtmlDriver;
import com.framework.testing.steping.screenshots.Photographer;
import com.framework.utils.error.PreConditions;
import com.framework.utils.string.LogStringStyle;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
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

	private final String id;

	private CheckPointCollector collector;

	//endregion


	//region CheckpointAssert - Constructor Methods Section

	public CheckpointAssert( final HtmlDriver driver, final String id, CheckPointCollector collector )
	{
		super( driver );
		PreConditions.checkNotNullNotBlankOrEmpty( id, "The checkpoint id is either null, blank or empty" );
		this.id = id;
		this.collector = collector;
	}

	//endregion


	//region CheckpointAssert - Public Methods Section

	@Override
	protected void onBeforeAssert( final JAssert assertCommand )
	{
		setStatus( ResultStatus.PENDING );
		logger.debug( "Executing checkpoint \"{}\"", getId() );
	}

	@Override
	protected void onAssertSuccess( final JAssert assertCommand )
	{
		setStatus( ResultStatus.SUCCESS );
		logger.debug( "Executed checkpoint [{}]: {} -> {}", getId(), assertCommand.getReason(), getStatus() );
	}

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

		onAfterAssert( assertCommand );
	}


	public String getId()
	{
		return id;
	}

	//endregion

}
