package com.framework.testing.steping;

import org.testng.ITestNGMethod;
import org.testng.annotations.*;


public interface LoggerProvider
{
	static int PASSED = 1;

	static int FAILED = 0;

	static final String BEFORE_SUITE = BeforeSuite.class.getSimpleName();

	static final String BEFORE_TEST = BeforeTest.class.getSimpleName();

	static final String BEFORE_CLASS = BeforeClass.class.getSimpleName();

	static final String BEFORE_GROUPS = BeforeGroups.class.getSimpleName();

	static final String BEFORE_METHOD = BeforeMethod.class.getSimpleName();

	static final String AFTER_METHOD = AfterMethod.class.getSimpleName();

	static final String AFTER_GROUPS = AfterGroups.class.getSimpleName();

	static final String AFTER_CLASS = AfterClass.class.getSimpleName();

	static final String AFTER_TEST = AfterTest.class.getSimpleName();

	static final String AFTER_SUITE = AfterSuite.class.getSimpleName();

	void startTest( final String id, final String name );

	void checkpoint( final String id, String expected, int status );

	void endTest( int status, String id, String name );

	void startConfig( final ITestNGMethod method );

	void endConfig( final ITestNGMethod method, int status );

	void step( final float stepNo, String description );

	void step( final float stepNo, final String format, final Object... argArray );
}
