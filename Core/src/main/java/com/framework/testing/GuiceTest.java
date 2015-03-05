package com.framework.testing;

import com.framework.asserts.JAssertion;
import com.framework.testing.annotations.UseAsTestName;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.testng.ITest;
import org.testng.annotations.BeforeMethod;

import java.lang.reflect.Method;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.testing
 *
 * Name   : BaseTestNGTest
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-01-17
 *
 * Time   : 22:03
 */

@Deprecated
public class GuiceTest implements ITest
{

	//region BaseTestNGTest - Variables Declaration and Initialization Section.



	//private static final ThreadLocal<FrameworkConfiguration> configurations = new ThreadLocal<FrameworkConfiguration>();

	protected JAssertion jAssert;

	/**
	 * Name of the current test. Used to implement {@link ITest#getTestName()}
	 */
	protected String testInstanceName = StringUtils.EMPTY;

	@Deprecated
	protected ApplicationContext appContext;

	//endregion


	//region BaseTestNGTest - Constructor Methods Section

	public GuiceTest()
	{
		super();
	}

	//endregion


	//region BaseTestNGTest - Public Methods Section

	@Override
	public String getTestName()
	{
		return testInstanceName;
	}

	/**
	 * Allows us to set the current test name internally to this class so that
	 * the TestNG framework can use the {@link ITest} implementation for naming
	 * tests.
	 *
	 * @param anInstanceName the test name to be applied
	 */

	private void setTestName( String anInstanceName )
	{
		testInstanceName = anInstanceName;
	}


	@BeforeMethod(
			description = "Method to transform the name of tests when they are called with the <testName> as " +
					"one of the the parameters or/and id. only affects when UseAsTestName annotation os applied",
			enabled = true,
			alwaysRun = true )
	protected void baseBeforeMethod( Method method, Object[] parameters )
	{
		setTestName( method.getName() );

		/* If there is a UseAsTestCaseID annotation on the method, use it to get a new test name */

		UseAsTestName annotation = method.getAnnotation( UseAsTestName.class );
		if ( annotation != null )
		{
			String testCaseId = null; int paramIndex = 0;
			if( annotation.id() > 0 )
			{
				testCaseId =  String.format( "%05d", annotation.id() );
			}

			if( annotation.index() > 0 )
			{
				if ( annotation.index() > parameters.length - 1 )
				{
					final String ERR_MSG = "We have been asked to use an incorrect parameter as a Test Case ID." +
							"The '%s' annotation on method '%s' is asking us to use the parameter " +
							"at index < %d > in the array and there are only < %d > parameters in the array.";
					throw new IllegalArgumentException(
							String.format( ERR_MSG, UseAsTestName.class.getSimpleName(), method.getName(), annotation.index(), parameters.length )
					);
				}

				Object parmAsObj = parameters[ annotation.index() ];

				if ( ! String.class.isAssignableFrom( parmAsObj.getClass() ) )
				{
					final String ERR_MSG = "We have been asked to use a parameter of an incorrect type as a Test Case Name." +
							"The '%s' annotation on method '%s' is asking us to use " +
							"the parameter at index < %d > in the array that parameter is not usable as a string. It is of type '%s'";

					throw new IllegalArgumentException(
							String.format(
									ERR_MSG, UseAsTestName.class.getSimpleName(), method.getName(), annotation.index(), parmAsObj.getClass().getSimpleName() )
					);
				}

				paramIndex =  annotation.index();
			}

			/*  Get the parameter at the specified index and use it. */

			this.testInstanceName = StringUtils.EMPTY;
			if( paramIndex > 0 &&  ( testCaseId == null ) )
			{
				testInstanceName = String.valueOf( parameters[ annotation.index() ] );
			}
			else if( paramIndex > 0 &&  ( testCaseId != null ) )
			{
				testInstanceName = "TestID-" + testCaseId + "_" + String.valueOf( parameters[ annotation.index() ] );
			}
			else
			{
				testInstanceName = "TestID-" + testCaseId;
			}
			setTestName( testInstanceName );
		}
	}


	//endregion


	//region BaseTestNGTest - Protected Methods Section

	//endregion


	//region BaseTestNGTest - Private Function Section

	//endregion


	//region BaseTestNGTest - Inner Classes Implementation Section

	//endregion

}
