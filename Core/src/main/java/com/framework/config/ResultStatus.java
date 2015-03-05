package com.framework.config;

/**
 * Acceptance test results.
 * Records the possible outcomes of tests within an acceptance test case
 * and of the overall acceptance test case itself.
 *
 * @author johnsmart
 *
 */

public enum ResultStatus
{
	/**
	 * Test result not known yet.
	 */
	UNDEFINED( 0 ),

	/**
	 * The test or test case ran as expected.
	 */
	SUCCESS( 1 ),

	/**
	 * Test failure, due to an assertion error
	 * For a test case, this means one of the tests in the test case failed.
	 */
	FAILURE( 2 ),

	/**
	 * Test failure, due to some other exception.
	 */
	ERROR( 32 ),

	/**
	 * The test or test case was deliberately ignored.
	 * Tests can be ignored via the @Ignore annotation in JUnit, for example.
	 * Ignored tests are not considered the same as pending tests: a pending test is one that
	 * has been specified, but the corresponding code is yet to be implemented, whereas an
	 * ignored test can be a temporarily-deactivated test (during refactoring, for example).
	 */
	IGNORED( 99 ),

	STARTED( 16 ),

	/**
	 * The test step was not executed because a previous step in this test case failed.
	 * A whole test case can be skipped using tags or annotations to indicate that it is currently "work-in-progress"
	 */
	SKIPPED( 3 ),

	SUCCESS_PERCENTAGE_FAILURE( 4 ),

	PENDING( 8 ),

	ENDED( 64 );

	private final int status;

	private ResultStatus( final int status )
	{
		this.status = status;
	}

	public int getStatus()
	{
		return status;
	}

	public String getStatusName()
	{
		return name().replace( "_", "" );
	}

	public static ResultStatus valueOf( int status )
	{
		ResultStatus finalValue = ResultStatus.UNDEFINED;
		for( ResultStatus value : values() )
		{
			if( value.getStatus() != status ) continue;
			finalValue = value;
		}

		return finalValue;
	}
}
