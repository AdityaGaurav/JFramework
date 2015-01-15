package com.framework.jreporter.listeners;

import com.framework.jreporter.TestReporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.support
 *
 * Name   : TestReporterListener
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-01-13
 *
 * Time   : 13:24
 */

public class TestReporterListener implements ITestListener, IConfigurationListener2, IExecutionListener
{
	private static final Logger logger = LoggerFactory.getLogger(  TestReporterListener.class );

	private static final String TEST_CASE_ID_PATTERN_KEY = "test.case.id.pattern";

	protected String testIdPattern;

	@Override
	public void onTestStart( final ITestResult result )
	{
	}

	@Override
	public void onTestSuccess( final ITestResult result )
	{
	}

	@Override
	public void onTestFailure( final ITestResult result )
	{
		TestReporter.info( "onTestFailure" );
	}

	@Override
	public void onTestSkipped( final ITestResult result )
	{
		TestReporter.info( "onTestSkipped" );
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage( final ITestResult result )
	{
		TestReporter.info( "onTestFailedButWithinSuccessPercentage" );
	}

	@Override
	public void onStart( final ITestContext context )
	{
		testIdPattern = System.getProperty( TEST_CASE_ID_PATTERN_KEY );
		Pattern pattern = Pattern.compile( testIdPattern );
		String id = "N/A";

		ITestNGMethod[] testMethods = context.getAllTestMethods();
		for( ITestNGMethod method : testMethods )
		{
			String desc = method.getDescription();
			Matcher matcher = pattern.matcher( desc );
			if( matcher.find() )
			{
				id = matcher.group( 0 );
			}

			method.setId( String.valueOf( id )  );
		}
	}

	@Override
	public void onFinish( final ITestContext context )
	{
	}

	@Override
	public void beforeConfiguration( final ITestResult tr )
	{
		if( tr.getName().equals( "beforeMethod" ) )
		{
			String name = tr.getName();
			String mName = tr.getMethod().getMethodName();
			ITestNGMethod y = tr.getMethod();
		}
	}

	@Override
	public void onConfigurationSuccess( final ITestResult itr )
	{
	}

	@Override
	public void onConfigurationFailure( final ITestResult itr )
	{
	}

	@Override
	public void onConfigurationSkip( final ITestResult itr )
	{
	}

	@Override
	public void onExecutionStart()
	{

	}

	@Override
	public void onExecutionFinish()
	{

	}
}
