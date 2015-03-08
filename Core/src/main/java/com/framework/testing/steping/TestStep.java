package com.framework.testing.steping;

import com.framework.testing.annotations.Step;
import com.framework.utils.error.PreConditions;
import com.framework.utils.string.ToLogStringStyle;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * An acceptance test run is made up of test steps.
 * Test steps can be either concrete steps or groups of steps.
 * Each concrete step should represent an action by the user, and (generally) an expected outcome.
 * A test step is described by a narrative-style phrase (e.g. "the user clicks
 * on the 'Search' button', "the user fills in the registration form', etc.).
 * A screenshot is stored for each step.
 *
 * @author johnsmart
 */

public class TestStep implements Comparable<TestStep>//, TimeComparator<TestStep>
{
	//region TestStep - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( TestStep.class );

	private final String description;

	private final float number;

	private final String[] expectedResults;

	//endregion


	//region TestStep - Constructor Methods Section

	public TestStep( final Step step )
	{
		PreConditions.checkNotNull( step, "Annotation Step is null." );

		this.number = step.number();
		this.description = step.description();
		this.expectedResults = step.expectedResults();
	}

	//endregion


	//region TestStep - Comparable implementation Section

	@Override
	public int compareTo( final TestStep o )
	{
		return Float.floatToIntBits( number ) - Float.floatToIntBits( o.number );
	}

	//endregion


	//region TestStep - Members Getters Section

	String[] getExpectedResults()
	{
		return expectedResults;
	}

	public float getNumber()
	{
		return number;
	}

	String getDescription()
	{
		return description;
	}

	//endregion


	//region TestStep - TimeComparator implementation Section

//	@Override
//	public boolean isAfter( final TimeComparator<TestStep> other )
//	{
//		PreConditions.checkState( instant != null, "The step still not initiated." );
//		Instant otherInstance = other.getInstant();
//		return this.getInstant().isAfter( otherInstance );
//	}
//
//	@Override
//	public boolean isBefore( final TimeComparator<TestStep> other )
//	{
//		PreConditions.checkState( instant != null, "The step still not initiated." );
//		Instant otherInstance = other.getInstant();
//		return this.getInstant().isBefore( otherInstance );
//	}
//
//	@Override
//	public boolean isSameDateTime( final TimeComparator<TestStep> other )
//	{
//		PreConditions.checkState( instant != null, "The step still not initiated." );
//		Instant otherInstance = other.getInstant();
//		return this.getInstant().isEqual( otherInstance );
//	}

	//endregion


	//region TestStep - Service Methods Section.









	/**
	 * Indicate that this step failed with a given error.
	 *
	 * @param cause why the test failed.
	 */
	void failedWith( final Throwable cause )
	{
//		FailureCause fc = new RootCauseAnalyzer( cause ).getRootCause();
//		this.exceptions.add( fc );
//		setResultStatus( new FailureAnalysis().resultFor( fc.toException() ) );
	}


	//endregion


	//region TestStep - Private Functions Section.











	//endregion


	//region TestStep - Object Override Methods Section

	@Override
	public String toString()
	{
		return new ToStringBuilder( this, ToLogStringStyle.LOG_MULTI_LINE_STYLE )
				.append( "number", number )
				.append( "description", description )
				.append( "expected results count", expectedResults != null ? expectedResults.length : 0 )
				.toString();
	}

	//endregion
























//	public List<? extends TestStep> getFlattenedSteps()
//	{
//		List<TestStep> flattenedSteps = new ArrayList<>();
//		for ( TestStep child : getChildren() )
//		{
//			flattenedSteps.add( child );
//			if ( child.isAGroup() )
//			{
//				flattenedSteps.addAll( child.getFlattenedSteps() );
//			}
//		}
//		return flattenedSteps;
//	}



//	public Collection<? extends TestStep> getLeafTestSteps()
//	{
//		List<TestStep> leafSteps = new ArrayList<>();
//
//		for ( TestStep child : getChildren() )
//		{
//			if ( child.isAGroup() )
//			{
//				leafSteps.addAll( child.getLeafTestSteps() );
//			}
//			else
//			{
//				leafSteps.add( child );
//			}
//		}
//		return leafSteps;
//	}

	/**
	 * The test has been aborted (marked as pending or ignored) for a reason described in the exception.
	 */
//	public void testAborted( final Throwable exception )
//	{
//		new RootCauseAnalyzer( exception ).getRootCause();
//	}

	//endregion


	//region TestStep - Private Functions Section

//	private void createChildSteps( final List<ChildStep>  childSteps )
//	{
//		for( ChildStep childStep : childSteps )
//		{
//			TestStep child = new TestStep( childStep );
//			addChildStep( child );
//		}
//	}


//
//	TestStep addChildStep( final TestStep child )
//	{
//		this.children.add( child );
//		logger.info( "new child step was added < '{}' >", child.toString( LogStringStyle.LOG_LINE_STYLE ) );
//		return this;
//	}

//	private String errorMessageFrom( final Throwable error )
//	{
//		return ( error.getCause() != null ) ? error.getCause().getMessage() : error.getMessage();
//	}



//	private int renumberChildrenFrom( int count )
//	{
//		for ( TestStep step : children )
//		{
//			count = step.renumberFrom( count );
//		}
//		return count;
//	}





//
//
//	private ResultStatus getResultFromThisStep()
//	{
//		if ( resultStatus != null )
//		{
//			return resultStatus;
//		}
//		else
//		{
//			return ResultStatus.PENDING;
//		}
//	}

	//endregion




}
