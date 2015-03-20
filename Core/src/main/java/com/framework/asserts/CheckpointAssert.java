package com.framework.asserts;

import com.framework.config.ResultStatus;
import com.framework.driver.event.HtmlDriver;
import com.framework.driver.event.HtmlElement;
import com.framework.driver.utils.ui.screenshots.Photographer;
import com.framework.testing.steping.StepEventBus;
import com.framework.utils.error.PreConditions;
import com.framework.utils.string.ToLogStringStyle;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.openqa.selenium.TimeoutException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * the class gives the ability to create checkpoint during the the tests.
 * the class is extending {@linkplain com.framework.asserts.JAssertion} class to validate
 * and Assertion.
 * the {@link com.framework.asserts.CheckPointCollector} accumulates all the assertion details during a single
 * checkpoint execution and the information get published by the {@link CheckPointCollector#assertAll()} method.
 *
 * try to provide a unique id to the checkpoint to be easier identify on logs.
 *
 * do not instantiate this class directly. use the {@link com.framework.asserts.CheckPointFactory} implementation
 * instance
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
	 * initializes a new instance of CheckpointAssert.
	 * if the checkpoint failed, the method {@link #onAfterAssert(JAssert)} will automatically take
	 * a screenshot of the whole screen.
	 *
	 * @param driver    	the working <b>HtmlDriver</b>instance
	 * @param id            a string specifying the checkpoint id.
	 * @param collector     the checkpoint collector class
	 *
	 * @see com.framework.asserts.CheckPointFactory
	 * @see com.framework.asserts.CheckPointCollector
	 */
	public CheckpointAssert( final HtmlDriver driver, final String id, CheckPointCollector collector )
	{
		super( driver );
		PreConditions.checkNotNullNotBlankOrEmpty( id, "The checkpoint id is either null, blank or empty" );
		this.id = id;
		this.collector = collector;
	}

	/**
	 * initializes a new instance of CheckpointAssert.
	 * if the checkpoint failed, the method {@link #onAfterAssert(JAssert)} will automatically take
	 * a screenshot of the provided {@code element}.
	 *
	 * @param element     the element to be added to the screenshot in case of failure.
	 * @param id          a string specifying the checkpoint id.
	 * @param collector   the checkpoint collector class
	 *
	 * @see com.framework.asserts.CheckPointFactory
	 * @see com.framework.asserts.CheckPointCollector
	 */
	public CheckpointAssert( final HtmlElement element, final String id, CheckPointCollector collector )
	{
		super( element );
		PreConditions.checkNotNullNotBlankOrEmpty( id, "The checkpoint id is either null, blank or empty" );
		this.id = id;
		this.collector = collector;
	}

	//endregion


	//region CheckpointAssert - Public Methods Section

	/**
	 * {@inheritDoc}
	 * displays a message of the checkpoint id about to be executed.
	 */
	@Override
	protected void onBeforeAssert( final JAssert assertCommand )
	{
		setStatus( ResultStatus.PENDING );
		logger.info( "Executing checkpoint \"{}\"", getId() );
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
		logger.info( "Executed checkpoint [{}]: {} -> {}", getId(), assertCommand.getReason(), getStatus() );
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
		if( getDriver() == null && getElement() == null  ) return;

		Photographer photographer;
		if( getDriver() != null )
		{
			photographer = new Photographer( getDriver() );
		}
		else
		{
			photographer = new Photographer( getElement() );
		}
		photographer.setLogger( logger );
		setSnapshot( photographer.grabScreenshot() );

		ToStringBuilder stb = new ToStringBuilder( assertCommand, ToLogStringStyle.LOG_MULTI_LINE_STYLE );
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
			stb.append( "screenshot was taken", BooleanUtils.toStringYesNo( getSnapshot().isPresent() ) );
		}

		logger.error( "Checkpoint Report: {}", stb.toString() );
	}

	/**
	 * {@inheritDoc}
	 * adds a status message and info about the checkpoint to the collector.
	 * in case that stepping is available, passed the checkpoint info to the event bus.
	 */
	@Override
	protected void onAfterAssert( final JAssert assertCommand )
	{
		collector.addCheckpointInfo( getId(), getStatus() );
		StepEventBus.getEventBus().afterCheckpoint( getId(), this );
	}

	/**
	 * Manage the checkpoint lifecycle.
	 * <ul>
	 *     <li>calls to {@link #onBeforeAssert(JAssert)}</li>
	 *     <li>calls to {@link #executeAssert(JAssert)}</li>
	 *     <li>calls to {@link #onAssertSuccess(JAssert)} in case that no {@link AssertionError} was thrown.</li>
	 *     <li>calls to {@link #onAssertFailure(JAssert, AssertionError)} in case that {@link AssertionError} was thrown.</li>
	 *     <li>
	 *         calls to {@link #onAssertFailure(JAssert, AssertionError)} in case that {@link TimeoutException} was thrown.
	 *         this one specifies to all the asserts with timeout.
	 *     </li>
	 *     <li>calls to {@link #onAfterAssert(JAssert)}</li>
	 * </ul>
	 *
	 * @param assertCommand  assertCommand the assertion command, with the checkpoint details.
	 */
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

	/**
	 * @return  the checkpoint id.
	 */
	public String getId()
	{
		return id;
	}

	//endregion

}
