package com.framework.utils.string;

import ch.qos.logback.core.CoreConstants;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.utils.string
 *
 * Name   : JConstants 
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-01-21 
 *
 * Time   : 18:07
 *
 */

public class JConstants
{

	public static final String ISO8601_STR = CoreConstants.ISO8601_STR;

	public static final String ISO8601_PATTERN = CoreConstants.ISO8601_PATTERN; //  "yyyy-MM-dd HH:mm:ss,SSS";

	public static final String DAILY_DATE_PATTERN = CoreConstants.DAILY_DATE_PATTERN; // "yyyy-MM-dd";

	/**
	 * Time format used in Common Log Format
	 */
	public static final String CLF_DATE_PATTERN = CoreConstants.CLF_DATE_PATTERN; // "dd/MMM/yyyy:HH:mm:ss Z";

	public static final char PERCENT_CHAR = '%';
	public static final char LEFT_PARENTHESIS_CHAR = '(';
	public static final char RIGHT_PARENTHESIS_CHAR = ')';

	public static final char ESCAPE_CHAR = '\\';
	public static final char CURLY_LEFT = '{';
	public static final char CURLY_RIGHT = '}';
	public static final char COMMA_CHAR = ',';
	public static final char DOUBLE_QUOTE_CHAR = '"';
	public static final char SINGLE_QUOTE_CHAR = '\'';
	public static final char COLON_CHAR = ':';
	public static final char DASH_CHAR = '-';
	public static final String DEFAULT_VALUE_SEPARATOR = ":-";
	public static final char DOT = '.';
	public static final char TAB = '\t';
	public static final char DOLLAR = '$';
	public static final char LINE_SEP = '|';

	public static final String LEFT_ACCOLADE = CoreConstants.LEFT_ACCOLADE;
	public static final String RIGHT_ACCOLADE = CoreConstants.RIGHT_ACCOLADE;

	public static final int MILLIS_IN_ONE_SECOND = 1000;
	public static final int MILLIS_IN_ONE_MINUTE = MILLIS_IN_ONE_SECOND*60;
	public static final int MILLIS_IN_ONE_HOUR = MILLIS_IN_ONE_MINUTE*60;
	public static final int MILLIS_IN_ONE_DAY = MILLIS_IN_ONE_HOUR*24;
	public static final int MILLIS_IN_ONE_WEEK = MILLIS_IN_ONE_DAY*7;

}
