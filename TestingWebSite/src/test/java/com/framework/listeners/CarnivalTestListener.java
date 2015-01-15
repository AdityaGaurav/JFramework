package com.framework.listeners;

import com.framework.jreporter.TestReporter;
import com.framework.jreporter.listeners.TestReporterListener;
import com.google.common.base.Throwables;
import org.testng.ITestResult;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.site.config
 *
 * Name   : CarnivalTestListener
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-01-14
 *
 * Time   : 20:42
 */

public class CarnivalTestListener extends TestReporterListener
{

	@Override
	public void onTestStart( final ITestResult result )
	{
		TestReporter.startTest( result.getMethod().getId(), result.getName() );
	}

	@Override
	public void onTestSuccess( final ITestResult result )
	{
		TestReporter.endTest( result.getStatus(), result.getMethod().getId(), result.getName() );
	}

	@Override
	public void beforeConfiguration( final ITestResult tr )
	{
		TestReporter.startConfig( tr.getMethod() );
	}

	@Override
	public void onConfigurationSuccess( final ITestResult itr )
	{
		TestReporter.endConfig( itr.getMethod(), itr.getStatus() );
	}

	@SuppressWarnings ( "ThrowableResultOfMethodCallIgnored" )
	@Override
	public void onConfigurationFailure( final ITestResult itr )
	{
		final String PATTERN = "Method name: <'%s'> of type <'%s'> failed with error -> %s";
		String name = itr.getMethod().getMethodName();
		String annotation = TestReporter.getAnnotation( itr.getMethod() );
		String rootCause = Throwables.getRootCause( itr.getThrowable() ).getMessage();
		TestReporter.error( String.format( PATTERN, name, annotation, rootCause ) );
	}

	@SuppressWarnings ( "ThrowableResultOfMethodCallIgnored" )
	@Override
	public void onConfigurationSkip( final ITestResult itr )
	{
		final String PATTERN = "Method name: <'%s'> of type <'%s'> failed with error -> %s";
		String name = itr.getMethod().getMethodName();
		String annotation = TestReporter.getAnnotation( itr.getMethod() );
		String rootCause = itr.getThrowable() == null ? "No Error" : Throwables.getRootCause( itr.getThrowable() ).getMessage();
		TestReporter.error( String.format( PATTERN, name, annotation, rootCause ) );
		TestReporter.warn( String.format( PATTERN, name, annotation, rootCause ) );
	}

}
