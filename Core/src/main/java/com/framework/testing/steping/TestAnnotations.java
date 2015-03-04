package com.framework.testing.steping;

import com.framework.testing.annotations.*;
import com.google.common.base.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class TestAnnotations
{

	//region TestAnnotations - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( TestAnnotations.class );

	private static final String INDEXED_METHOD_NAME = ".*\\[\\d+]";

	private final Class<?> testClass;

	//endregion


	//region TestAnnotations - Constructor Methods Section

	private TestAnnotations( final Class<?> testClass )
	{
		this.testClass = testClass;
	}

	public static TestAnnotations forClass( final Class<?> testClass )
	{
		return new TestAnnotations( testClass );
	}

	//endregion


	//region TestAnnotations - Public Methods Section

	public static String stripArgumentsFrom( final String methodName )
	{
		if ( methodName == null )
		{
			return null;
		}
		int firstArgument = methodName.indexOf( ':' );
		if ( firstArgument > 0 )
		{
			return methodName.substring( 0, firstArgument );
		}
		else
		{
			return methodName;
		}
	}

	public static String stripIndexesFrom( final String methodName )
	{
		if ( methodName == null )
		{
			return null;
		}
		return ( methodName.matches( INDEXED_METHOD_NAME ) ) ? methodName.substring( 0, methodName.lastIndexOf( '[' ) ) : methodName;
	}

	public static String withNoArguments( final String methodName )
	{
		return stripArgumentsFrom( stripIndexesFrom( methodName ) );
	}

	//endregion


	//region TestAnnotations - @Issues/@Issue annotations Methods Section

	public Optional<Issue> getAnnotatedIssueForMethodName( String methodName )
	{
		Optional<Method> testMethod = getMethodCalled( methodName );
		if ( ( testMethod.isPresent() ) && ( testMethod.get().getAnnotation( Issue.class ) != null ) )
		{
			return Optional.of( testMethod.get().getAnnotation( Issue.class ) );
		}
		return Optional.absent();
	}

	public Issue[] getAnnotatedIssuesForMethodName( String methodName )
	{
		Optional<Method> testMethod = getMethodCalled( methodName );
		if ( ( testMethod.isPresent() ) && ( testMethod.get().getAnnotation( Issues.class ) != null ) )
		{
			return testMethod.get().getAnnotation( Issues.class ).issues();
		}
		return new Issue[] {};
	}

	public Optional<Issue> getAnnotatedIssueForMethod( Method method )
	{
		Optional<Issue> issue = Optional.fromNullable( method.getAnnotation( Issue.class ) );
		if( issue.isPresent() )
		{
			return issue;
		}

		return Optional.absent();
	}

	public Issue[] getAnnotatedIssuesForMethod( Method method )
	{
		if ( method.getAnnotation( Issues.class ) != null )
		{
			return method.getAnnotation( Issues.class ).issues();
		}
		return new Issue[] {};
	}

	public Optional<Issue> getAnnotatedIssueForTestClass( Class<?> testClass )
	{
		Optional<Issue> issue = Optional.fromNullable( testClass.getAnnotation( Issue.class ) );
		if( issue.isPresent() )
		{
			return issue;
		}

		return Optional.absent();
	}

	//endregion


	//region TestAnnotations - @Test annotation Methods Section

	public Optional<Test> getAnnotatedTestForMethod( Method method )
	{
		if ( method.getAnnotation( Test.class ) != null )
		{
			return Optional.of( method.getAnnotation( Test.class ) );
		}

		return Optional.absent();
	}

	//endregion


	//region TestAnnotations - @TestCaseId/@TestCasesIds annotation Methods Section

	public Optional<Long> getAnnotatedTestCaseIdForMethod( Method method )
	{
		if( null != method.getAnnotation( TestCaseId.class ) )
		{
			Optional<Long> issue = Optional.fromNullable( method.getAnnotation( TestCaseId.class ).id() );
			if ( issue.isPresent() )
			{
				return issue;
			}
		}

		return Optional.absent();
	}

	public Optional<long[]> getAnnotatedTestCasesIdsForMethod( Method method )
	{
		if ( method.getAnnotation( TestCasesIds.class ) != null )
		{
			return Optional.fromNullable( method.getAnnotation( TestCasesIds.class ).ids() );
		}

		return Optional.absent();
	}

	//endregion


	//region TestAnnotations - DefaultUrl annotation Section

	public boolean hasDefaultUrl( final String methodName )
	{
		Optional<Method> method = getMethodCalled( methodName );
		return method.isPresent() && isDefaultUrl( method.get() );
	}

	private static boolean isDefaultUrl( final Method method )
	{
		return method != null && ( method.getAnnotation( DefaultUrl.class ) != null );
	}

	public String getAnnotatedDefaultUrl( Class<?> pageObject )
	{
		DefaultUrl urlAnnotation = testClass.getAnnotation( DefaultUrl.class );
		if ( urlAnnotation != null )
		{
			return urlAnnotation.value();
		}
		else
		{
			return null;
		}
	}

	//endregion


	//region TestAnnotations - @Steps/@Step/@ChildStep annotation Section

	public Optional<Steps> getAnnotatedStepsForMethod( Method method )
	{
		if ( method.getAnnotation( Steps.class ) != null )
		{
			return Optional.fromNullable( method.getAnnotation( Steps.class ) );
		}

		return Optional.absent();
	}

	public boolean hasSteps( final String methodName )
	{
		Optional<Method> testMethod = getMethodCalled( methodName );
		return testMethod.isPresent() && hasAnnotationCalled( testMethod.get(), "Steps" );
	}

	//endregion


	//region TestAnnotations - Private Function Section

	private static boolean hasAnnotationCalled( Method method, String annotationName )
	{
		Annotation[] annotations = method.getAnnotations();
		for ( Annotation annotation : annotations )
		{
			if ( annotation.annotationType().getSimpleName().equals( annotationName ) )
			{
				return true;
			}
		}
		return false;
	}

	private boolean testClassHasMethodCalled( final String methodName )
	{
		return ( getMethodCalled( methodName ).isPresent() );
	}

	public Optional<Method> getMethodCalled( final String methodName )
	{
		if ( testClass == null )
		{
			return Optional.absent();
		}
		String baseMethodName = withNoArguments( methodName );
		try
		{
			if ( baseMethodName == null )
			{
				return Optional.absent();
			}
			else
			{
				return Optional.fromNullable( testClass.getMethod( baseMethodName ) );
			}
		}
		catch ( NoSuchMethodException e )
		{
			Method[] methods = testClass.getMethods();
			for( Method method : methods )
			{
				if( ! method.getName().contains( baseMethodName ) ) continue;
				return Optional.fromNullable( method );
			}
			return Optional.absent();
		}
	}

	//endregion
}
