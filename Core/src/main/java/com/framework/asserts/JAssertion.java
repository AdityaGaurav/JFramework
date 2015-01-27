package com.framework.asserts;

import com.framework.config.ResultStatus;
import com.framework.testing.reporting.LoggerProvider;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
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

public class JAssertion // implements JAssertLifeCycle
{

	//region JAssertion - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( JAssertion.class );

	private final WebDriver driver;

	private static LoggerProvider reporter;

	private ResultStatus status;

	//endregion


	//region JAssertion - Constructor Methods Section

	/**
	 * Default Constructor.
	 *
	 * @param driver a {@code WebDriver} to be assigned ( for taking screenshots )
	 */
	public JAssertion( final WebDriver driver )
	{
		this.driver = driver;
	}

	public JAssertion()
	{
		this.driver = null;
	}

	//endregion


	//region JAssertion - Public Methods Section

	//todo: method documentation
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

	 	//todo: error description + screenshot;
	}

	//@Override
	protected void onAfterAssert( final JAssert assertCommand )
	{
		logger.debug( "Finished to execute assertion: <'{}'>", assertCommand.getReason() );
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
					description.appendText( reason )
							.appendText( "\nExpected: " )
							.appendDescriptionOf( matcher )
							.appendText( "\nbut: " );
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
			public String getScreenShotFileName()
			{
				return null;
			}

			@Override
			public <T> T getActual()
			{
				return null;
			}
		});
	}

	//todo: method documentation
	public void assertWaitThat( final String reason, final long timeoutMillis, final ExpectedCondition<?> condition )
	{
		doAssert( new SimpleAssert( reason )
		{
			@Override
			public void doAssert()
			{
				WebDriverWait wdw = new WebDriverWait( driver, timeoutMillis, timeoutMillis / 5 );
				Object result = wdw.until( condition );
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
			public String getScreenShotFileName()
			{
				return null;
			}

			@Override
			public <T> T getActual()
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

	//endregion


	//region JAssertion - Protected Methods Section

	protected WebDriver getDriver()
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
