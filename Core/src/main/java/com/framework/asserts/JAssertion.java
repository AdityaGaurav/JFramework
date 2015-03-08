package com.framework.asserts;

import com.framework.config.ResultStatus;
import com.framework.driver.event.HtmlCondition;
import com.framework.driver.event.HtmlDriver;
import com.framework.driver.event.HtmlDriverWait;
import com.framework.driver.event.HtmlElement;
import com.framework.driver.utils.ui.screenshots.Photographer;
import com.framework.driver.utils.ui.screenshots.ScreenshotAndHtmlSource;
import com.framework.testing.steping.LoggerProvider;
import com.google.common.base.Optional;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * The class manage and documents all the assertions during the test execution.
 * this class can be used as independent or extended by other implementations like
 * {@link com.framework.asserts.JSoftAssertion} and {@link com.framework.asserts.CheckpointAssert}
 *
 * internally manage an assertion lifecycle and a status. the methods can be used by sub-classes
 * for reporting and/or manipulate the exception thrown.
 *
 * the lifecycle is managed by the methods
 * <ol>
 *     <li>onBeforeAssert</li>
 *     <li>executeAssert</li>
 *     <li>onAssertSuccess</li>
 *     <li>onAssertFailure</li>
 *     <li>onAfterAssert</li>
 * </ol>
 *
 * an optional instance of {@link com.framework.driver.utils.ui.screenshots.ScreenshotAndHtmlSource} might be
 * present when the assertion failed. the html source is another optional error source than can be configured by
 * {@code jframework.store.html.source} property.
 */

public class JAssertion
{

	//region JAssertion - Variables Declaration and Initialization Section.

	private final Logger logger = LoggerFactory.getLogger( JAssertion.class );

	/** and instance of the current active driver */
	private HtmlDriver driver;

	private final HtmlElement element;

	private static LoggerProvider reporter;

	/** the assertion current status UNDEFINED > PENDING > RUNNING > ( SUCCESS | FAILURE ) */
	private ResultStatus status = ResultStatus.UNDEFINED;

	/** an optional screenshot and html source file instance */
	private Optional<ScreenshotAndHtmlSource> snapshot = Optional.absent();

	/** the detailed assertion error, if the assertion failed */
	private AssertionError assertionError = null;

	/** instance of the assert command. the assert command uses as an argument for the assertion lifecycle*/
	private JAssert assertCommand;

	//endregion


	//region JAssertion - Constructor Methods Section

	/**
	 * Default Constructor.
	 *
	// * @param driver a {@code WebDriver} to be assigned ( for taking screenshots )
	 */
	public JAssertion( final HtmlDriver driver )
	{
		this.driver = driver;
		this.element = null;
	}

	public JAssertion( final HtmlElement element )
	{
		this.driver = null;
		this.element = element;
	}

	//endregion


	//region JAssertion - Public Methods Section

	/**
	 * Executes the assert lifecycle.
	 *
	 * <ul>
	 *     <li>fires the event {@linkplain #onBeforeAssert(JAssert)}</li>
	 *     <li>executes the assert by calling {@linkplain #executeAssert(JAssert)} under try/catch clause</li>
	 *     <li>if the assert evaluated successfully, no <b>AssertionError</b> is thrown.
	 *     	   calling to {@linkplain #onAssertSuccess(JAssert)}
	 *     </li>
	 *     <li><b>AssertionError</b> is caught. calling to {@linkplain #onAssertFailure(JAssert, AssertionError)}</li>
	 *     <li>using the clause finally,  fires the event {@linkplain #onAfterAssert(JAssert)}</li>
	 * </ul>
	 *
	 * @param assertCommand  assertCommand the assertion command, with the checkpoint details.
	 *
	 * @see java.lang.AssertionError
	 * @see com.framework.asserts.JAssert
	 */
	protected void doAssert( JAssert assertCommand )
	{
		this.assertCommand = assertCommand;
		onBeforeAssert( assertCommand );
		try
		{
			executeAssert( assertCommand );
			onAssertSuccess( assertCommand );
		}
		catch ( AssertionError ex )
		{
			onAssertFailure( assertCommand, ex );
			throw ex;
		}
		finally
		{
			onAfterAssert( assertCommand );
		}
	}

	/**
	 * The event occurs before a checkpoint is executed.
	 * classes might override this method and display their own messages.
	 *
	 * @param assertCommand the assertion command, with the checkpoint details.
	 *
	 * @see com.framework.asserts.JAssert
	 * @see com.framework.config.ResultStatus
	 */
	protected void onBeforeAssert( final JAssert assertCommand )
	{
		setStatus( ResultStatus.PENDING );
		logger.debug( "Executing assertion: <'{}'>", assertCommand.getReason() );
	}

	/**
	 * Service method to set the status of the checkpoint.
	 *
	 * @param status a {@linkplain ResultStatus} value.
	 *
	 * @see com.framework.config.ResultStatus#SUCCESS
	 * @see com.framework.config.ResultStatus#FAILURE
	 */
	protected void setStatus( final ResultStatus status )
	{
		this.status = status;
	}

	/**
	 * Run the assert command in parameter. Meant to be overridden by subclasses.
	 *
	 * @param assertCommand the assertion command, with the checkpoint details.
	 */
	protected void executeAssert( JAssert assertCommand )
	{
		setStatus( ResultStatus.STARTED );
		assertCommand.doAssert();
	}

	/**
	 * Occurs after the Assertion was successfully evaluated.
	 *
	 * @param assertCommand the assertion command, with the checkpoint details.
	 */
	protected void onAssertSuccess( final JAssert assertCommand )
	{
		setStatus( ResultStatus.SUCCESS );
	}

	/**
	 * Occurs after the Assertion evaluation was failed.
	 * additionally a screenshot and/or html source snapshot will be taken during failure
	 *
	 * @param assertCommand the assertion command, with the checkpoint details.
	 * @param ex			a detailed assertion error instance.
	 *
	 * @see com.framework.driver.utils.ui.screenshots.Photographer
	 * @see com.google.common.base.Optional
	 * @see com.framework.driver.utils.ui.screenshots.ScreenshotAndHtmlSource
	 */
	protected void onAssertFailure( final JAssert assertCommand, final AssertionError ex )
	{
		setStatus( ResultStatus.FAILURE );
		logger.error( "Assertion failed with message: < '{}' >", ex.getMessage() );
		if( driver == null && element == null  ) return;

		Photographer photographer;
		if( driver != null )
		{
			photographer = new Photographer( driver );
		}
		else
		{
			photographer = new Photographer( element );
		}
		photographer.setLogger( logger );
		this.snapshot = photographer.grabScreenshot();
		if ( snapshot.isPresent() )
		{
			String png = snapshot.get().getScreenshotName();
			String html = snapshot.get().getHtmlSourceName();
			logger.info( "Screenshot file captured is < '{}' >", png );
			logger.info( "Html Source file captured is < '{}' >", html );
		}
		else
		{
			logger.warn( "No screenshot was captured." );
		}
	}

	/**
	 * The event occurs immediately after a checkpoint was executed.
	 * classes might override this method and display their own messages.
	 *
	 * @param assertCommand the assertion command, with the checkpoint details.
	 *
	 * @see com.framework.asserts.JAssert
	 * @see com.framework.config.ResultStatus
	 */
	protected void onAfterAssert( final JAssert assertCommand )
	{
		logger.info( "Finished to execute assertion: <'{}'> with status < {} >", assertCommand.getReason(), status.getStatusName() );
	}

	/**
	 * The assertion command implementation.
	 *
	 * @param reason   a description of the assertion
	 * @param actual   an object of type T representing the actual result.
	 * @param matcher  a matcher of T
	 * @param <T>      the assertion type.
	 *
	 * @see com.framework.utils.matchers.JMatchers
	 */
	public  <T> void assertThat( final String reason, final T actual, final Matcher<? super T> matcher )
	{
		doAssert( new SimpleAssert( reason )
		{
			@Override
			public void doAssert()
			{
				if ( ! matcher.matches( actual ) )
				{
					Description description = new StringDescription();
					description.appendText( "\"" + reason + "\"" )
							.appendText( "\nExpected: " )
							.appendDescriptionOf( matcher )
							.appendText( "\nActual: " );
					matcher.describeMismatch( actual, description );

					throw new AssertionError( description.toString() );
				}
			}

			@Override
			public Description getExpected()
			{
				Description description = new StringDescription();
				matcher.describeTo( description );
				return description;
			}

			@Override
			public Object getActual()
			{
				return actual;
			}
		} );
	}

	/**
	 * The assertion command implementation. however the difference between this implementations
	 * an {@linkplain #assertThat(String, Object, org.hamcrest.Matcher)} is that this implementation
	 * waits for the condition evaluation a maximum timeout.
	 *
	 * @param reason   			a description of the assertion
	 * @param timeoutSeconds    an object of type T representing the actual result.
	 * @param condition			an {@link com.framework.driver.event.HtmlCondition} of any type
	 *
	 */
	public void assertWaitThat( final String reason, final long timeoutSeconds, final HtmlCondition<?> condition )
	{
		doAssert( new SimpleAssert( reason )
		{
			@Override
			public void doAssert()
			{
				if( driver == null ) driver = element.getWrappedHtmlDriver();
				Object result = HtmlDriverWait.wait( driver, timeoutSeconds ).until( condition );
				boolean response = ( result == null || result == Boolean.FALSE );

				if( response )
				{
					Description description = new StringDescription();
					description.appendText( condition.toString() );
					throw new AssertionError( description.toString() );
				}
			}

			@Override
			public Description getExpected()
			{
				return new StringDescription().appendText( condition.toString() );
			}

			@Override
			public Object getActual()
			{
				return null;
			}
		});
	}

	/**
	 * @return the current status of the checkpoint.
	 */
	public ResultStatus getStatus()
	{
		return status;
	}

	/**
	 * @return an {@linkplain com.google.common.base.Optional} instance of a screenshot
	 */
	public Optional<ScreenshotAndHtmlSource> getSnapshot()
	{
		return snapshot;
	}

	/**
	 * Sets an optional snapshot. used by sub-classes.
	 *
	 * @param snapshot an {@code Optional} screenshot and/or html resource instance.
	 */
	protected void setSnapshot( Optional<ScreenshotAndHtmlSource> snapshot )
	{
		this.snapshot = snapshot;
	}

	/**
	 * @return the Assertion error
	 */
	public AssertionError getAssertionError()
	{
		return assertionError;
	}

	/**
	 * @return the assertion command
	 */
	public JAssert getAssertCommand()
	{
		return assertCommand;
	}

	protected HtmlDriver getDriver()
	{
		return driver;
	}

	protected HtmlElement getElement()
	{
		return element;
	}

	//endregion


	//region JAssertion - Inner Classes Implementation Section

	abstract private static class SimpleAssert implements JAssert
	{
		private final String message;

		public SimpleAssert( String message )
		{
			this.message = message;
		}

		@Override
		public String getReason()
		{
			return message;
		}

		@Override
		abstract public void doAssert();
	}

	//endregion

}
