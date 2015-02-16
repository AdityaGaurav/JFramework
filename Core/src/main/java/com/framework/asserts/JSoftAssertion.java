package com.framework.asserts;

import com.framework.driver.event.HtmlDriver;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;


/**
 * When an assertion fails, don't throw an exception but record the failure.
 * Calling {@code assertAll()} will cause an exception to be thrown if at
 * least one assertion failed.
 */
public class JSoftAssertion extends JAssertion
{

	//region JSoftAssertion - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( JSoftAssertion.class );

	/** a map that stores all the assertion errors before released by {@linkplain #assertAll()} */
	private Map<AssertionError, JAssert> errors = Maps.newLinkedHashMap();

	/** a list of messages accumulated by the assertions, before released by {@linkplain #assertAll()}*/
	private List<String> messages = Lists.newArrayList();

	//endregion


	//region JSoftAssertion - Constructor Methods Section

	public JSoftAssertion( HtmlDriver driver )
	{
	 	super( driver );
	}

	//endregion


	//region JSoftAssertion - Public Methods Section

	@Override
	protected void executeAssert( JAssert a )
	{
		try
		{
			a.doAssert();
		}
		catch ( AssertionError ex )
		{
			onAssertFailure( a, ex );
			errors.put( ex, a );
		}
	}

	@Override
	protected void onBeforeAssert( final JAssert assertCommand )
	{
		// override for having no message
	}

	@Override
	protected void onAssertSuccess( final JAssert assertCommand )
	{
		logger.error( "Assertion < {} > success.", assertCommand.getReason() );
	}

	@Override
	protected void onAssertFailure( final JAssert assertCommand, final AssertionError ex )
	{
		logger.error( "Assertion < {} > failed.", assertCommand.getReason() );
	}

	@Override
	protected void onAfterAssert( final JAssert assertCommand )
	{
		// override for having no message
	}

	public List<String> getMessages()
	{
		return messages;
	}

	@SuppressWarnings ( "ThrowableResultOfMethodCallIgnored" )
	public void assertAll()
	{
		if ( ! errors.isEmpty() )
		{
			StringBuilder sb = new StringBuilder( "The following asserts failed:\n" );
			boolean first = true;
			for ( Map.Entry<AssertionError, JAssert> ae : errors.entrySet() )
			{
				if ( first )
				{
					first = false;
				}
				else
				{
					sb.append( ", " );
				}
				sb.append( ae.getKey().getMessage() );
			}

			throw new AssertionError( sb.toString() );
		}
	}

	//endregion

}
