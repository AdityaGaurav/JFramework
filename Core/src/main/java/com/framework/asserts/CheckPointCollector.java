package com.framework.asserts;

import com.framework.config.ResultStatus;
import com.framework.testing.steping.exceptions.CheckpointError;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.asserts
 *
 * Name   : CheckPointCollector 
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-01-31 
 *
 * Time   : 21:52
 *
 */

public class CheckPointCollector
{

	//region CheckPointCollector - Variables Declaration and Initialization Section.

	private static Logger logger = LoggerFactory.getLogger( CheckPointCollector.class );

	private List<String> failed = Lists.newArrayList();

	private List<String> passed = Lists.newArrayList();

	//endregion


	//region CheckPointCollector - Public Methods Section

	public void addCheckpointInfo( String id, ResultStatus status )
	{
		String statusName = status.getStatusName();
		if( status.equals( ResultStatus.SUCCESS ) )
		{
			passed.add( String.format( "Checkpoint id [%s] -> %s\n", id, statusName ) );
		}
		else
		{
			failed.add( String.format( "Checkpoint id [%s] -> %s\n", id, statusName ) );
		}
	}

	public void assertAll()
	{
		int total = passed.size() + failed.size();
		int failedCount = failed.size();
		int passedCount = passed.size();

		if( total == 0 ) return;

		String header = String.format(
				"\nThe Test Case executed <%d> checkpoints, where <%d> passed and <%d> were failed.\n",total, passedCount, failedCount );

		StringBuilder sb = new StringBuilder( header );
		if( passedCount > 0 )
		{
			sb.append( "  **** PASSED CHECKPOINTS ****  \n" );
			for( String p : passed )
			{
				sb.append( p );
			}
		}
		if( failedCount > 0 )
		{
			sb.append( "  **** FAILED CHECKPOINTS ****  \n" );
			for( String p : failed )
			{
				sb.append( p );
			}
		}

		failed.clear(); passed.clear();

		logger.info( sb.toString() );
		if( failedCount > 0 )
		{
			throw new CheckpointError( "a total of <" + failedCount + "> checkpoints were failed. check log for more details." );
		}
	}

	public static Logger getLogger()
	{
		return logger;
	}

	public static void setLogger( final Logger logger )
	{
		CheckPointCollector.logger = logger;
	}

	//endregion


	//region CheckPointCollector - Protected Methods Section

	//endregion


	//region CheckPointCollector - Private Function Section

	//endregion


	//region CheckPointCollector - Inner Classes Implementation Section

	//endregion

}
