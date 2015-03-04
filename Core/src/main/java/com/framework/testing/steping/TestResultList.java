package com.framework.testing.steping;

import com.framework.config.ResultStatus;
import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.testing
 *
 * Name   : TestResultList
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-01-17
 *
 * Time   : 11:27
 */

public class TestResultList
{

	//region TestResultList - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( TestResultList.class );

	private final List<ResultStatus> testResults;

	//endregion


	//region TestResultList - Constructor Methods Section

	public TestResultList( final List<ResultStatus> testResults )
	{
		this.testResults = testResults;
	}

	public static TestResultList of( final List<ResultStatus> testResults )
	{
		return new TestResultList( testResults );
	}

	public static TestResultList of( ResultStatus... testResults )
	{
		return new TestResultList( Arrays.asList( testResults ) );
	}

	//endregion


	//region TestResultList - Public Methods Section

	public boolean isEmpty() {
		return testResults.isEmpty();
	}

	public ResultStatus getOverallResult()
	{
		if ( testResults.isEmpty() )
		{
			return ResultStatus.SUCCESS;
		}

		if ( testResults.contains( ResultStatus.ERROR ) )
		{
			return ResultStatus.ERROR;
		}

		if ( testResults.contains( ResultStatus.FAILURE ) )
		{
			return ResultStatus.FAILURE;
		}

		if ( testResults.contains( ResultStatus.PENDING ) )
		{
			return ResultStatus.PENDING;
		}

		if ( containsOnly( ResultStatus.IGNORED ) )
		{
			return ResultStatus.IGNORED;
		}

		if ( containsOnly( ResultStatus.SKIPPED ) )
		{
			return ResultStatus.SKIPPED;
		}

		if ( containsOnly( ResultStatus.SUCCESS, ResultStatus.IGNORED, ResultStatus.SKIPPED ) )
		{
			return ResultStatus.SUCCESS;
		}
		return ResultStatus.SUCCESS;
	}

	//endregion


	//region TestResultList - Private Function Section

	private boolean containsOnly( final ResultStatus... values )
	{
		Preconditions.checkState( ! isEmpty() );

		List<ResultStatus> authorizedTypes = Arrays.asList( values );

		for ( ResultStatus result : testResults )
		{
			if ( ! authorizedTypes.contains( result ) )
			{
				return false;
			}
		}

		return true;
	}

	//endregion

}
