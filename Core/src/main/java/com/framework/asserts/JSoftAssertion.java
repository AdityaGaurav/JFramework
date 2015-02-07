package com.framework.asserts;

import com.framework.driver.event.HtmlDriver;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.utils.asserts
 *
 * Name   : JSoftAssertion
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-01-16
 *
 * Time   : 10:52
 */

public class JSoftAssertion extends JAssertion
{

	//region JSoftAssertion - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( JSoftAssertion.class );

	private Map<AssertionError, JAssert> errors = Maps.newLinkedHashMap();

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
	}

	@Override
	protected void onAssertSuccess( final JAssert assertCommand )
	{
	}

	@Override
	protected void onAssertFailure( final JAssert assertCommand, final AssertionError ex )
	{
		//todo: error description + screenshot;
	}

	@Override
	protected void onAfterAssert( final JAssert assertCommand )
	{

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
