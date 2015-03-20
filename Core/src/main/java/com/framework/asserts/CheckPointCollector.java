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

	private static Logger logger = LoggerFactory.getLogger( CheckpointAssert.class );

	/**
	 * keeps information about the PASSED checkpoints
	 */
	private List<String> failed = Lists.newArrayList();

	/**
	 * keeps information about the FAILED checkpoints
	 */
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

	/**
	 * resume all the checkpoints during a test-case instance.
	 * <ul>
	 *     <li>summarize the amount of {@linkplain #failed} checkpoints + {@linkplain #passed} checkpoints</li>
	 *     <li>if no checkpoints then procedure is aborted</li>
	 *     <li>reports/logs the summary of success checkpoints if {@linkplain #passed} size is gt zero.</li>
	 *     <li>reports/logs the summary of failed checkpoints if {@linkplain #failed} size is gt zero.</li>
	 *     <li>clears and reset the lists to be ready for next test-case</li>
	 *     <li>throwing a new instance of CheckpointError for the filed messages, only if  {@linkplain #failed} GT 0 ( zero )</li>
	 * </ul>
	 *
	 * @see com.framework.asserts.CheckPointCollector
	 * @see com.framework.testing.steping.exceptions.CheckpointError
	 */
	public void assertAll()
	{
		// summarize checkpoints ids lists in case that total is zero, procedure is aborted

		int total = passed.size() + failed.size();
		int failedCount = failed.size();
		int passedCount = passed.size();
		logger.info( "Asserting all checkpoints.  total checkpoints to report: < {} >", total );
		if( total == 0 ) return;

		final String FMT = "\nThe Test Case executed <%d> checkpoints, where <%d> passed and <%d> were failed.\n";
		String header = String.format( FMT, total, passedCount, failedCount );

		StringBuilder sb = new StringBuilder( header );
		if /* will report message only if size gt zero */ ( passedCount > 0 )
		{
			sb.append( "  **** PASSED CHECKPOINTS ****  \n" );
			for( String p : passed )
			{
				sb.append( p );
			}
		}
		if /* will report message only if size gt zero */ ( failedCount > 0 )
		{
			sb.append( "  **** FAILED CHECKPOINTS ****  \n" );
			for( String p : failed )
			{
				sb.append( p );
			}
		}

		failed.clear(); passed.clear();  // resetting lists.

		/**
		 * throwing a new instance of CheckpointError for the filed messages, only if  {@linkplain #failed} GT 0 ( zero )
		 * @see com.framework.testing.steping.exceptions.CheckpointError
		 */
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
}
