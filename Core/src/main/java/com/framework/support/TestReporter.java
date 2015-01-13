package com.framework.support;

import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MessageFormatter;
import org.testng.Reporter;

import java.text.DecimalFormat;
import java.util.Map;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.test
 *
 * Name   : TestReporter
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-01-06
 *
 * Time   : 23:49
 */

public class TestReporter extends Reporter
{

	//region TestReporter - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( TestReporter.class );

	private static final String DEBUG = "[DEBUG]";

	private static final String INFO = "[INFO]";

	private static final String ERROR = "[ERROR]";

	private static final String WARNING = "[WARNING]";

	private static final String STEP = "[STEP]";

	private static final String START = "[START]";

	private static final String END = "[END]";

	private static final String START_CONF = "[START-CONFIG]";

	private static final String END_CONF = "[END-CONFIG]";

	private static final String CHECKPOINT = "[CHECKPOINT]";

	private static final Map<Integer,String> statuses;
	static
	{
		statuses = Maps.newHashMap();
		statuses.put( 1, "SUCCESS" );
		statuses.put( 2, "FAILURE" );
		statuses.put( 3, "SKIP" );
		statuses.put( 4, "SUCCESS_PERCENTAGE_FAILURE" );
		statuses.put( 16, "STARTED" );
	}

	//endregion


	//region TestReporter - Constructor Methods Section

	private TestReporter(){}

	//endregion

	//todo: documentation
	public static void startTest( final String annotation, final String name )
	{
		String s = String.format( "%s_%s - method:'%s'", START, annotation.toUpperCase(), name );
		Reporter.log( s, false );
		logger.info( s );
	}

	//todo: documentation
	public static void endTest( final String annotation, final String name )
	{
		String s;
		if( null == getCurrentTestResult() )
		{
			s = String.format( "%s_%s, status:N/A, method:'%s'", END, annotation.toUpperCase(), name );
		}
		else
		{
			String status = statuses.get( getCurrentTestResult().getStatus() );
			s = String.format( "%s_%s, status:%s, method:'%s'", END, annotation.toUpperCase(), status, name );
		}
		Reporter.log( s, false );
		logger.info( s );
	}

	//todo: documentation
	public static void startConfig( final String annotation, final String name )
	{
		String s = String.format( "%s_%s - method:'%s'", START_CONF, annotation.toUpperCase(), name );
		Reporter.log( s, false );
		logger.info( s );
	}

	//todo: documentation
	public static void endConfig( final String annotation, final String name )
	{
		String s;
		if( null == getCurrentTestResult() )
		{
			s = String.format( "%s_%s, status:N/A, method:'%s'", END_CONF, annotation.toUpperCase(), name );
		}
		else
		{
			String status = statuses.get( getCurrentTestResult().getStatus() );
			s = String.format( "%s_%s, status:%s, method:'%s'", END, annotation.toUpperCase(), status, name );
		}
		Reporter.log( s, false );
		logger.info( s );
	}

	//todo: documentation
	public static void checkpoint( final String id, final String description )
	{
		String s = String.format( "%s id:%s, %s", CHECKPOINT, id, description );
		Reporter.log( s );
		logger.info( s );
	}

	//todo: documentation
	public static void checkpoint( final String id, final String format, Object arg )
	{
		FormattingTuple ft = MessageFormatter.format( format, arg );
		String s = String.format( "%s id:%s, %s", CHECKPOINT, id, ft.getMessage() );
		Reporter.log( s, false );
		logger.info( s );
	}

	//todo: documentation
	public static void checkpoint( final String id, final String format, final Object arg1, final Object arg2 )
	{
		FormattingTuple ft = MessageFormatter.format( format, arg1, arg2 );
		String s = String.format( "%s id:%s, %s", CHECKPOINT, id, ft.getMessage() );
		Reporter.log( s, false );
		logger.info( s );
	}

	//todo: documentation
	public static void checkpoint( final String id, final String format, final Object... argArray )
	{
		FormattingTuple ft = MessageFormatter.arrayFormat( format, argArray );
		String s = String.format( "%s id:%s, %s", CHECKPOINT, id, ft.getMessage() );
		Reporter.log( s );
		logger.info( s );
	}

	//todo: documentation
	public static void step( final float stepNo, String description )
	{
		String s = String.format( "%s number:%s, %s", STEP, new DecimalFormat("#.##").format( stepNo ), description );
		Reporter.log( s );
		logger.info( s );
	}

	//todo: documentation
	public static void step( final float stepNo, final String format, Object arg )
	{
		FormattingTuple ft = MessageFormatter.format( format, arg );
		String s = String.format( "%s number:%s, %s", STEP, new DecimalFormat("#.##").format( stepNo ), ft.getMessage() );
		Reporter.log( s, false );
		logger.info( s );
	}

	//todo: documentation
	public static void step( final float stepNo, final String format, final Object arg1, final Object arg2 )
	{
		FormattingTuple ft = MessageFormatter.format( format, arg1, arg2 );
		String s = String.format( "%s number:%s, %s", STEP, new DecimalFormat("#.##").format( stepNo ), ft.getMessage() );
		Reporter.log( s );
		logger.info( s );
	}

	//todo: documentation
	public static void step( final float stepNo, final String format, final Object... argArray )
	{
		FormattingTuple ft = MessageFormatter.arrayFormat( format, argArray );
		String s = String.format( "%s number:%s, %s", STEP, new DecimalFormat("#.##").format( stepNo ), ft.getMessage() );
		Reporter.log( s, true );
		logger.info( s );
	}

	/**
	 * Log a message at the DEBUG level.
	 *
	 * @param msg the message string to be logged
	 */
	public static void debug( final String msg )
	{
		Reporter.log( DEBUG + msg, false );
		logger.debug( msg );
	}

	/**
	 * Log a message at the DEBUG level according to the specified format and argument.
	 * <p/>
	 * <p>This form avoids superfluous object creation when the logger
	 * is disabled for the DEBUG level. </p>
	 *
	 * @param format the format string
	 * @param arg    the argument
	 */
	public static void debug( String format, final Object arg )
	{
		FormattingTuple ft = MessageFormatter.format( format, arg );
		String s = DEBUG + ft.getMessage();
		Reporter.log( s );
		logger.debug( ft.getMessage() );
	}

	/**
	 * Log a message at the DEBUG level according to the specified format
	 * and arguments.
	 * <p/>
	 * <p>This form avoids superfluous object creation when the logger
	 * is disabled for the DEBUG level. </p>
	 *
	 * @param format the format string
	 * @param arg1   the first argument
	 * @param arg2   the second argument
	 */
	public static void debug( final String format, final Object arg1, final Object arg2 )
	{
		FormattingTuple ft = MessageFormatter.format( format, arg1, arg2 );
		String s = DEBUG + ft.getMessage();
		Reporter.log( s, false );
		logger.debug( ft.getMessage() );
	}

	/**
	 * Log a message at the DEBUG level according to the specified format and arguments.
	 * <p/>
	 * <p>This form avoids superfluous string concatenation when the logger
	 * is disabled for the DEBUG level. However, this variant incurs the hidden
	 * (and relatively small) cost of creating an <code>Object[]</code> before invoking the method,
	 * even if this logger is disabled for DEBUG. The variants taking
	 * {@link #debug(String, Object) one} and {@link #debug(String, Object, Object) two}
	 * arguments exist solely in order to avoid this hidden cost.</p>
	 *
	 * @param format    the format string
	 * @param arguments a list of 3 or more arguments
	 */
	public static void debug( final String format, final Object... arguments )
	{
		FormattingTuple ft = MessageFormatter.arrayFormat( format, arguments );
		String s = DEBUG + ft.getMessage();
		Reporter.log( s, false );
		logger.debug( ft.getMessage() );
	}

	/**
	 * Log a message at the INFO level.
	 *
	 * @param msg the message string to be logged
	 */
	public static void info( final String msg )
	{
		Reporter.log( msg, false );
		logger.info( msg );
	}

	/**
	 * Log a message at the INFO level according to the specified format and argument.
	 * <p/>
	 * <p>This form avoids superfluous object creation when the logger is disabled for the INFO level. </p>
	 *
	 * @param format the format string
	 * @param arg    the argument
	 */
	public static void info( String format, final Object arg )
	{
		FormattingTuple ft = MessageFormatter.format( format, arg );
		String s = INFO + ft.getMessage();
		Reporter.log( s, false );
		logger.info( ft.getMessage() );
	}

	/**
	 * Log a message at the INFO level according to the specified format
	 * and arguments.
	 * <p/>
	 * <p>This form avoids superfluous object creation when the logger
	 * is disabled for the INFO level. </p>
	 *
	 * @param format the format string
	 * @param arg1   the first argument
	 * @param arg2   the second argument
	 */
	public static void info( final String format, final Object arg1, final Object arg2 )
	{
		FormattingTuple ft = MessageFormatter.format( format, arg1, arg2 );
		String s = INFO + ft.getMessage();
		Reporter.log( s, false );
		logger.info( ft.getMessage() );
	}

	/**
	 * Log a message at the INFO level according to the specified format
	 * and arguments.
	 * <p/>
	 * <p>This form avoids superfluous string concatenation when the logger
	 * is disabled for the INFO level. However, this variant incurs the hidden
	 * (and relatively small) cost of creating an <code>Object[]</code> before invoking the method,
	 * even if this logger is disabled for INFO. The variants taking
	 * {@link #info(String, Object) one} and {@link #info(String, Object, Object) two}
	 * arguments exist solely in order to avoid this hidden cost.</p>
	 *
	 * @param format    the format string
	 * @param arguments a list of 3 or more arguments
	 */
	public static void info( final String format, final Object... arguments )
	{
		FormattingTuple ft = MessageFormatter.arrayFormat( format, arguments );
		String s = INFO + ft.getMessage();
		Reporter.log( s, false );
		logger.info( ft.getMessage() );
	}

	/**
	 * Log a message at the WARN level.
	 *
	 * @param msg the message string to be logged
	 */
	public static void warn( final String msg )
	{
		Reporter.log( WARNING + msg, false );
		logger.warn( msg );
	}

	/**
	 * Log a message at the WARN level according to the specified format
	 * and argument.
	 * <p/>
	 * <p>This form avoids superfluous object creation when the logger
	 * is disabled for the WARN level. </p>
	 *
	 * @param format the format string
	 * @param arg    the argument
	 */
	public static void warn( String format, final Object arg )
	{
		FormattingTuple ft = MessageFormatter.format( format, arg );
		String s = WARNING + ft.getMessage();
		Reporter.log( s, false );
		logger.warn( ft.getMessage() );
	}

	/**
	 * Log a message at the WARN level according to the specified format
	 * and arguments.
	 * <p/>
	 * <p>This form avoids superfluous object creation when the logger
	 * is disabled for the WARN level. </p>
	 *
	 * @param format the format string
	 * @param arg1   the first argument
	 * @param arg2   the second argument
	 */
	public static void warn( final String format, final Object arg1, final Object arg2 )
	{
		FormattingTuple ft = MessageFormatter.format( format, arg1, arg2 );
		String s = WARNING + ft.getMessage();
		Reporter.log( s, false );
		logger.warn( ft.getMessage() );
	}

	/**
	 * Log a message at the WARN level according to the specified format
	 * and arguments.
	 * <p/>
	 * <p>This form avoids superfluous string concatenation when the logger
	 * is disabled for the WARN level. However, this variant incurs the hidden
	 * (and relatively small) cost of creating an <code>Object[]</code> before invoking the method,
	 * even if this logger is disabled for WARN. The variants taking
	 * {@link #warn(String, Object) one} and {@link #warn(String, Object, Object) two}
	 * arguments exist solely in order to avoid this hidden cost.</p>
	 *
	 * @param format    the format string
	 * @param arguments a list of 3 or more arguments
	 */
	public static void warn( final String format, final Object... arguments )
	{
		FormattingTuple ft = MessageFormatter.arrayFormat( format, arguments );
		String s = WARNING + ft.getMessage();
		Reporter.log( s, false );
		logger.warn( ft.getMessage() );
	}

	/**
	 * Log a message at the ERROR level.
	 *
	 * @param msg the message string to be logged
	 */
	public static void error( final String msg )
	{
		Reporter.log( ERROR + msg );
		logger.error( msg );
	}

	/**
	 * Log a message at the ERROR level according to the specified format
	 * and argument.
	 * <p/>
	 * <p>This form avoids superfluous object creation when the logger
	 * is disabled for the ERROR level. </p>
	 *
	 * @param format the format string
	 * @param arg    the argument
	 */
	public static void error( String format, final Object arg )
	{
		FormattingTuple ft = MessageFormatter.format( format, arg );
		String s = ERROR + ft.getMessage();
		Reporter.log( s );
		logger.error( ft.getMessage() );
	}

	/**
	 * Log a message at the ERROR level according to the specified format
	 * and arguments.
	 * <p/>
	 * <p>This form avoids superfluous object creation when the logger
	 * is disabled for the ERROR level. </p>
	 *
	 * @param format the format string
	 * @param arg1   the first argument
	 * @param arg2   the second argument
	 */
	public static void error( final String format, final Object arg1, final Object arg2 )
	{
		FormattingTuple ft = MessageFormatter.format( format, arg1, arg2 );
		String s = ERROR + ft.getMessage();
		Reporter.log( s );
		logger.error( ft.getMessage() );
	}

	/**
	 * Log a message at the ERROR level according to the specified format
	 * and arguments.
	 * <p/>
	 * <p>This form avoids superfluous string concatenation when the logger
	 * is disabled for the ERROR level. However, this variant incurs the hidden
	 * (and relatively small) cost of creating an <code>Object[]</code> before invoking the method,
	 * even if this logger is disabled for ERROR. The variants taking
	 * {@link #error(String, Object) one} and {@link #error(String, Object, Object) two}
	 * arguments exist solely in order to avoid this hidden cost.</p>
	 *
	 * @param format    the format string
	 * @param arguments a list of 3 or more arguments
	 */
	public static void error( final String format, final Object... arguments )
	{
		FormattingTuple ft = MessageFormatter.arrayFormat( format, arguments );
		String s = ERROR + ft.getMessage();
		Reporter.log( s );
		logger.error( ft.getMessage() );
	}

	//endregion
}
