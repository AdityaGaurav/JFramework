package com.framework.jreporter;

import ch.qos.logback.classic.Level;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MessageFormatter;
import org.testng.ITestNGMethod;
import org.testng.Reporter;
import org.testng.annotations.*;

import java.text.DecimalFormat;
import java.util.Map;


//todo: class documentation

public class TestReporter extends Reporter
{

	//region TestReporter - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( TestReporter.class );

	public static final String BEFORE_SUITE = org.testng.annotations.BeforeSuite.class.getSimpleName();

	public static final String BEFORE_TEST = BeforeTest.class.getSimpleName();

	public static final String BEFORE_CLASS = BeforeClass.class.getSimpleName();

	public static final String BEFORE_GROUPS = BeforeGroups.class.getSimpleName();

	public static final String BEFORE_METHOD = BeforeMethod.class.getSimpleName();

	public static final String AFTER_METHOD = AfterMethod.class.getSimpleName();

	public static final String AFTER_GROUPS = AfterGroups.class.getSimpleName();

	public static final String AFTER_CLASS = AfterClass.class.getSimpleName();

	public static final String AFTER_TEST = AfterTest.class.getSimpleName();

	public static final String AFTER_SUITE = AfterSuite.class.getSimpleName();


	//	private static final String DEBUG = "[DEBUG]";
//
//	private static final String INFO = "[INFO]";
//
//	private static final String ERROR = "[ERROR]";
//
//	private static final String WARNING = "[WARNING]";
//
//	private static final String STEP = "[STEP]";
//
//	private static final String START = "[START]";
//
//	private static final String END = "[END]";
//
//	private static final String START_CONF = "[START-CONFIG]";
//
//	private static final String END_CONF = "[END-CONFIG]";
//
//	private static final String CHECKPOINT = "[CHECKPOINT]";

	private static final Map<Integer,String> statuses = Maps.newHashMap();
	static
	{
		statuses.put( 1, "SUCCESS" );
		statuses.put( 2, "FAILURE" );
		statuses.put( 3, "SKIP" );
		statuses.put( 4, "SUCCESS_PERCENTAGE_FAILURE" );
		statuses.put( 16, "STARTED" );
	}

	private static int lastTestId = NumberUtils.INTEGER_ZERO;

	private static String lastTestName = StringUtils.EMPTY;

	//endregion


	//region TestReporter - Constructor Methods Section

	private TestReporter()
	{
		Reporter.setEscapeHtml( false );
	}

	//endregion

	//todo: method documentation
	public static void startTest( final String id, final String name )
	{
		if( logger.isInfoEnabled() )
		{
			final String PATTERN = "[START-TEST]:[ %s ] - method:\"%s\"";
			String s = String.format( PATTERN, id, name );
			logMessage( Level.INFO_INT, s );
		}
	}

	//todo: method documentation
	public static void endTest( int status, String id, String name )
	{
		if( logger.isInfoEnabled() )
		{
			final String PATTERN = "[START-TEST]:[ %s ] - method:\"%s\"; ; status -> '%s'";
			String s = String.format( PATTERN, id, name, statuses.get( status ) );
			logMessage( Level.INFO_INT, s );
		}
	}

	//todo: method documentation
	public static void startConfig( final ITestNGMethod method )
	{
		if( logger.isInfoEnabled() )
		{
			final String PATTERN = "[START-CONFIG : @%s ] - method: \"%s\"; description: \"%s\"";
			final String ANNOTATION = getAnnotation( method );
			String message = String.format( PATTERN, ANNOTATION, method.getMethodName(), method.getDescription() );
			logMessage( Level.INFO_INT, message );
		}
	}

	//todo: method documentation
	public static void endConfig( final ITestNGMethod method, int status )
	{
		if( logger.isInfoEnabled() )
		{
			final String PATTERN1 = "[END-CONFIG : @%s ] - method: \"%s\"; status -> %s";
			final String ANNOTATION = getAnnotation( method );
			String message = String.format( PATTERN1, ANNOTATION, method.getMethodName(), statuses.get( status ) );
			logMessage( Level.INFO_INT, message );
		}
	}

	//todo: method documentation
	public static void checkpoint( final String id, final String description )
	{
//		String s = String.format( "%s id:%s, %s", CHECKPOINT, id, description );
//		Reporter.log( s );
//		logger.info( s );
	}

	//todo: method documentation
	public static void checkpoint( final String id, final String format, Object arg )
	{
//		FormattingTuple ft = MessageFormatter.format( format, arg );
//		String s = String.format( "%s id:%s, %s", CHECKPOINT, id, ft.getMessage() );
//		Reporter.log( s, false );
//		logger.info( s );
	}

	//todo: method documentation
	public static void checkpoint( final String id, final String format, final Object arg1, final Object arg2 )
	{
//		FormattingTuple ft = MessageFormatter.format( format, arg1, arg2 );
//		String s = String.format( "%s id:%s, %s", CHECKPOINT, id, ft.getMessage() );
//		Reporter.log( s, false );
//		logger.info( s );
	}

	//todo: method documentation
	public static void checkpoint( final String id, final String format, final Object... argArray )
	{
//		FormattingTuple ft = MessageFormatter.arrayFormat( format, argArray );
//		String s = String.format( "%s id:%s, %s", CHECKPOINT, id, ft.getMessage() );
//		Reporter.log( s );
//		logger.info( s );
	}

	//todo: method documentation
	public static void step( final float stepNo, String description )
	{
		if( logger.isInfoEnabled() )
		{
			DecimalFormat df = new DecimalFormat();
			df.setMaximumFractionDigits( 3 );
			final String FMT = "[STEP:%s] - %s</span>";
			logMessage( Level.INFO_INT, String.format( FMT, df.format( stepNo ), description  ) );
		}
	}

	//todo: method documentation
	public static void step( final float stepNo, final String format, final Object... argArray )
	{
		if( logger.isInfoEnabled() )
		{
			FormattingTuple ft = MessageFormatter.arrayFormat( format, argArray );
			DecimalFormat df = new DecimalFormat();
			df.setMaximumFractionDigits( 3 );
			final String FMT = "[STEP:%s] - %s</span>";
			logMessage( Level.INFO_INT, String.format( FMT, df.format( stepNo ), ft  ) );
		}
	}

	/**
	 * Log a message at the DEBUG level.
	 *
	 * @param msg the message string to be logged
	 */
	public static void debug( final String msg )
	{
		if( logger.isInfoEnabled() )
		{
			final String FMT ="[DEBUG] - %s";
			logMessage( Level.DEBUG_INT, String.format( FMT, msg  ) );
		}
	}

	//todo : method documentation
	public static void debug( final String format, final Object... arguments )
	{
		if( logger.isDebugEnabled() )
		{
			final String FMT ="[DEBUG] - %s";
			FormattingTuple ft = MessageFormatter.arrayFormat( format, arguments );
			logMessage( Level.DEBUG_INT, String.format( FMT, ft  ) );
		}
	}

	/**
	 * Log a message at the INFO level.
	 *
	 * @param msg the message string to be logged
	 */
	public static void info( final String msg )
	{
		if( logger.isInfoEnabled() )
		{
			final String FMT ="[INFO] - %s";
			logMessage( Level.INFO_INT, String.format( FMT, msg  ) );
		}
	}

	//todo : method documentation
	public static void info( final String format, final Object... arguments )
	{
		if( logger.isInfoEnabled() )
		{
			final String FMT ="[INFO] - %s";
			FormattingTuple ft = MessageFormatter.arrayFormat( format, arguments );
			logMessage( Level.INFO_INT, String.format( FMT, ft  ) );
		}
	}

	/**
	 * Log a message at the WARN level.
	 *
	 * @param msg the message string to be logged
	 */
	public static void warn( final String msg )
	{
		if( logger.isWarnEnabled() )
		{
			final String FMT ="[WARNING] - %s";
			String message = String.format( FMT, msg );
			logMessage( Level.WARN_INT, message );
		}
	}

	//todo: method documentation
	public static void warn( final String format, final Object... arguments )
	{
		if( logger.isWarnEnabled() )
		{
			final String FMT ="[WARNING] - %s";
			FormattingTuple ft = MessageFormatter.arrayFormat( format, arguments );
			logMessage( Level.WARN_INT, String.format( FMT, ft  ) );
		}
	}

	/**
	 * Log a message at the ERROR level.
	 *
	 * @param msg the message string to be logged
	 */
	public static void error( final String msg )
	{
		final String FMT ="[ERROR] - %s";
		String message = String.format( FMT, msg );
		logMessage( Level.ERROR_INT, message );
	}

	public static void error( final String format, final Object... arguments )
	{
		final String FMT ="[ERROR] - %s";
		FormattingTuple ft = MessageFormatter.arrayFormat( format, arguments );
		logMessage( Level.ERROR_INT, String.format( FMT, ft  ) );
	}

	//endregion


	//region TestReporter - Private Functions Section.

	private static void logMessage( int levelInt, String message )
	{
		Reporter.log( message, false );  // reporting message to TestNG reporter

		switch ( levelInt )
		{
			case Level.DEBUG_INT:
			{
				Reporter.log( "<span style='font-color: #909090; font-weight: normal'>" + message + "</span>", false );
				logger.debug( message );
				break;
			}
			case Level.INFO_INT:
			{
				Reporter.log( "<span style='font-color: #101010; font-weight: normal'>" + message + "</span>", false );
				logger.info( message );
				break;
			}
			case Level.WARN_INT:
			{
				Reporter.log( "<span style='font-color: #FFA500; font-weight: normal'>" + message + "</span>", false );
				logger.warn( message );
				break;
			}
			case Level.ERROR_INT:
			{
				Reporter.log( "<span style='font-color: #E00000; font-weight: bold'>" + message + "</span>", false );
				logger.error( message );
				break;
			}
		}
	}

	public static String getAnnotation( ITestNGMethod method )
	{
		if( method.isBeforeSuiteConfiguration() ) return BEFORE_SUITE;
		if( method.isBeforeClassConfiguration() ) return BEFORE_CLASS;
		if( method.isBeforeGroupsConfiguration() ) return BEFORE_GROUPS;
		if( method.isBeforeMethodConfiguration() ) return BEFORE_METHOD;
		if( method.isBeforeTestConfiguration() ) return BEFORE_TEST;
		return StringUtils.EMPTY;
	}

	private static void main( String[] args )
	{

		System.out.println( String.format( "%05d", 1000 ) );
		System.out.println( String.format( "%+,05d", 1000 ) );

	}

	//endregion
}
