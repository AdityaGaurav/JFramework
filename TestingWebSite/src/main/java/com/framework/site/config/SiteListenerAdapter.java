package com.framework.site.config;

import com.framework.utils.string.LogStringStyle;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.site.config
 *
 * Name   : SiteLitenerAdapter 
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-01-24 
 *
 * Time   : 21:10
 *
 */

public class SiteListenerAdapter extends TestListenerAdapter
{
	//region SiteListenerAdapter - Variables Declaration and Initialization Section.

	private static Logger logger = LoggerFactory.getLogger( SiteListenerAdapter.class );

	//endregion

	@Override
	public void onTestFailedButWithinSuccessPercentage( final ITestResult tr )
	{
		SiteSessionManager.get().setLastTestCaseFailed( true );
	}

	@Override
	public void onTestSuccess( final ITestResult tr )
	{
		SiteSessionManager.get().setLastTestCaseFailed( false );
	}

	@Override
	public void onTestFailure( final ITestResult tr )
	{
		SiteSessionManager.get().setLastTestCaseFailed( true );
	}

	@Override
	public void onTestSkipped( final ITestResult tr )
	{
		SiteSessionManager.get().setLastTestCaseFailed( true );
	}

	public static void setLogger( final Logger logger )
	{
		SiteListenerAdapter.logger = logger;
		logger.debug( "listener logger: < '{}' > was set to: < '{}' >",
				logger.getName(), SiteListenerAdapter.class.getSimpleName() );
	}
	@Override
	public String toString()
	{
		return new ToStringBuilder( this, LogStringStyle.LOG_LINE_STYLE )
				.append( "Adapter Type", getClass().getSimpleName() )
				.append( "extends", TestListenerAdapter.class.getName() )
				.toString();
	}

}
