package com.framework.asserts;

import com.framework.config.ResultStatus;
import com.framework.driver.event.HtmlCondition;
import com.framework.driver.event.HtmlDriver;
import com.framework.driver.event.HtmlDriverWait;
import com.framework.testing.steping.LoggerProvider;
import com.framework.testing.steping.screenshots.Photographer;
import com.framework.testing.steping.screenshots.ScreenshotAndHtmlSource;
import com.framework.utils.error.PreConditions;
import com.google.common.base.Optional;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.utils.asserts
 *
 * Name   : JAssertion
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-01-16
 *
 * Time   : 09:35
 */

public class JAssertion
{

	//region JAssertion - Variables Declaration and Initialization Section.

	private final Logger logger = LoggerFactory.getLogger( JAssertion.class );

	private final HtmlDriver driver;

	private static LoggerProvider reporter;

	private ResultStatus status = ResultStatus.UNDEFINED;

	private Optional<ScreenshotAndHtmlSource> snapshot = Optional.absent();

	private AssertionError assertionError = null;

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
		PreConditions.checkNotNull( driver, "The Web Driver assigned cannot be null." );
		this.driver = driver;
	}

	//endregion


	//region JAssertion - Public Methods Section

	//todo: method documentation
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

	//todo: method documentation extension
	protected void onBeforeAssert( final JAssert assertCommand )
	{
		setStatus( ResultStatus.PENDING );
		logger.debug( "Executing assertion: <'{}'>", assertCommand.getReason() );
	}

	protected void setStatus( final ResultStatus status )
	{
		this.status = status;
	}

	/**
	 * Run the assert command in parameter. Meant to be overridden by subclasses.
	 */
	protected void executeAssert( JAssert assertCommand )
	{
		setStatus( ResultStatus.STARTED );
		assertCommand.doAssert();
	}

	//todo: method documentation extension
	protected void onAssertSuccess( final JAssert assertCommand )
	{
		setStatus( ResultStatus.SUCCESS );
	}

	protected void onAssertFailure( final JAssert assertCommand, final AssertionError ex )
	{
		setStatus( ResultStatus.FAILURE );
		logger.error( "Assertion failed with message: < '{}' >", ex.getMessage() );
		Photographer photographer = new Photographer( driver );
		photographer.setLogger( logger );
		this.snapshot = photographer.grabScreenshot(); //todo: publish it
		if( snapshot.isPresent() )
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

	//@Override
	protected void onAfterAssert( final JAssert assertCommand )
	{
		logger.debug( "Finished to execute assertion: <'{}'> with status < {} >", assertCommand.getReason(), status.getStatusName() );
	}

	//todo: method documentation
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

	//todo: method documentation
	public void assertWaitThat( final String reason, final long timeoutSeconds, final HtmlCondition<?> condition )
	{
		doAssert( new SimpleAssert( reason )
		{
			@Override
			public void doAssert()
			{
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


//	public static LoggerProvider getReporter()
//	{
//		return reporter;
//	}
//
//	public static void setReporter( final LoggerProvider reporter )
//	{
//		JAssertion.reporter = reporter;
//	}
//
	public ResultStatus getStatus()
	{
		return status;
	}

	public Optional<ScreenshotAndHtmlSource> getSnapshot()
	{
		return snapshot;
	}

	protected void setSnapshot( Optional<ScreenshotAndHtmlSource> snapshot )
	{
		this.snapshot = snapshot;
	}

	public AssertionError getAssertionError()
	{
		return assertionError;
	}

	public JAssert getAssertCommand()
	{
		return assertCommand;
	}

	protected HtmlDriver getDriver()
	{
		return driver;
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
