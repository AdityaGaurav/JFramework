package com.framework.utils.datetime;

import org.joda.time.*;
import org.joda.time.format.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: org.framework.webdriver.utils
 *
 * Name   : DateTimeUtils
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2014-12-15
 *
 * Time   : 16:26
 */


public class DateTimeUtils
{

	//region DateTimeUtils - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( DateTimeUtils.class );

	public static final String DATETIME_ZONE_KEY = "dateTimeZone";

	public static final String DEFAULT_LOCALE_KEY = "locale";

	private DateTimeZone dateTimeZone = DateTimeZone.getDefault();

	private Locale locale = Locale.getDefault();

	//endregion


	//region DateTimeUtils - Constructor Methods Section

	private DateTimeUtils()
	{
		super();
	}

	//endregion


	//region DateTimeUtils - Public Methods Section

	public static String getFormattedPeriod( Duration duration )
	{
		PeriodFormatter formatter = new PeriodFormatterBuilder().
			appendMinutes().appendSuffix( " minute", " minutes" ).
			appendSeparator( ", " ).
			appendSeconds().appendSuffix( " second", " seconds" ).
			appendSeparator( " and " ).
			appendMillis3Digit().
			appendSuffix( " millisecond", " milliseconds" ).
			toFormatter();

		return formatter.print( duration.toPeriod() );
	}

	public PeriodFormatter getDefaultPeriodFormatter()
	{
		return new PeriodFormatterBuilder().
				appendMinutes().appendSuffix( " minute", " minutes" ).
				appendSeparator( ", " ).
				appendSeconds().appendSuffix( "second", " seconds" ).
				appendSeparator( " and " ).
				appendMillis3Digit().
				appendSuffix( " millisecond", " milliseconds" ).
				toFormatter();
	}

	public PeriodFormatterBuilder getPeriodFormatterBuilder()
	{
		return new PeriodFormatterBuilder();
	}

	public DateTimeFormatterBuilder getDateTimeFormatterBuilder()
	{
		return new DateTimeFormatterBuilder();
	}

	public DateTimeZone getDateTimeZone()
	{
		return dateTimeZone;
	}

	public Locale getLocale()
	{
		return locale;
	}

	public DateTime now()
	{
		return DateTime.now();
	}

	public DateTime now( DateTimeZone dtz )
	{
		return DateTime.now( dtz );
	}

	//endregion


	//region DateTimeUtils - Protected Methods Section

	//endregion


	//region DateTimeUtils - Private Function Section

	//endregion


	//region DateTimeUtils - Inner Classes Implementation Section

	//endregion

	// ------------------------------------------------------------------------|
	// --- TESTING FUNCTIONS --------------------------------------------------|
	// ------------------------------------------------------------------------|

	public String currentDateMinusOffset(String format,String periodStr){
		DateTime nowDateTime=DateTime.now();
		PeriodFormatter periodFormatter= ISOPeriodFormat.standard();
		Period period=periodFormatter.parsePeriod(periodStr);
		DateTime offsetDateTime=nowDateTime.minus(period);
		SimpleDateFormat fmt=new SimpleDateFormat(format);
		return fmt.format(offsetDateTime.toDate());
	}

	/**
	 * Find the difference between the two dates and express it in natural language
	 * @param dateOne - date in DATE_FORMAT format
	 * @param dateTwo - date in DATE_FORMAT format
	 * @return time between the two given dates in natural terms
	 * @throws java.text.ParseException - if there is any error parsing a date
	 */
	public static String naturalInterval(String dateOne,String dateTwo) throws ParseException
	{
		final String  DATE_FORMAT = "YYYY MM DD";
		DateTimeFormatter dtForm= DateTimeFormat.forPattern( DATE_FORMAT );
		DateTime firstDate=dtForm.parseDateTime(dateOne);
		DateTime secondDate=dtForm.parseDateTime(dateTwo);
		Period period=new Period(firstDate,secondDate);
		PeriodFormatter formatter=new PeriodFormatterBuilder().appendYears().appendSuffix(" year "," years ").appendMonths().appendSuffix(" month "," months ").appendWeeks().appendSuffix(" week "," weeks ").appendDays().appendSuffix(" day"," days").printZeroNever().toFormatter();
		return formatter.print(period);
	}

	public String currentDatePlusOffset(String format,String periodStr){
		DateTime nowDateTime=DateTime.now();
		PeriodFormatter periodFormatter=ISOPeriodFormat.standard();
		Period period=periodFormatter.parsePeriod(periodStr);
		DateTime offsetDateTime=nowDateTime.plus(period);
		SimpleDateFormat fmt=new SimpleDateFormat(format);
		return fmt.format(offsetDateTime.toDate());
	}

	/**
	 * Extracts duration values from an object of this converter's type, and sets them into the given ReadWritableDuration.
	 * @param period  period to get modified
	 * @param object  the String to convert, must not be null
	 * @param chrono  the chronology to use
	 * @return the millisecond duration
	 * @throws ClassCastException if the object is invalid
	 */
	public void setInto(ReadWritablePeriod period,Object object,Chronology chrono){
		String str=(String)object;
		PeriodFormatter parser=ISOPeriodFormat.standard();
		period.clear();
		int pos=parser.parseInto(period,str,0);
		if (pos < str.length()) {
			if (pos < 0) {
				parser.withParseType(period.getPeriodType()).parseMutablePeriod(str);
			}
			throw new IllegalArgumentException("Invalid format: \"" + str + '"');
		}
	}



	private String getPeriodInStringFormat(DateTime localTime){
		DateTime timestamp=localTime;
		DateTime now=new DateTime();
		Period period=new Period(now,timestamp);
		String appendSuffixFormat=" %s ";
		PeriodFormatter formatter=new PeriodFormatterBuilder().appendYears().appendSuffix(String.format(appendSuffixFormat,"?r")).appendMonths().appendSuffix(String.format(appendSuffixFormat,"m?neder")).appendWeeks().appendSuffix(String.format(appendSuffixFormat,"uker")).appendDays().appendSuffix(String.format(appendSuffixFormat,"dager")).appendHours().appendSuffix(String.format(appendSuffixFormat,"timer")).appendMinutes().appendSuffix(String.format(appendSuffixFormat,"minutter")).printZeroNever().toFormatter();
		String elapsed=formatter.print(period);
		return elapsed;
	}




	private static PeriodFormatter createDefaultPeriodFormatted(String pattern){
		String[] variants={" ",",",",and ",", and "};
		final PeriodFormatter formatter;
		if ("y".equals(pattern)) {
			formatter=new PeriodFormatterBuilder().printZeroRarelyLast().appendYears().appendSuffix(" year"," years").toFormatter();
		}
		else   if ("M".equals(pattern)) {
			formatter=new PeriodFormatterBuilder().printZeroNever().appendYears().appendSuffix(" year"," years").printZeroRarelyLast().appendMonths().appendSuffix(" month"," months").toFormatter();
		}
		else   if ("d".equals(pattern)) {
			formatter=new PeriodFormatterBuilder().printZeroNever().appendYears().appendSuffix(" year"," years").appendSeparator(", "," and ",variants).appendMonths().appendSuffix(" month"," months").appendSeparator(", "," and ",variants).printZeroRarelyLast().appendDays().appendSuffix(" day"," days").toFormatter();
		}
		else   if ("H".equals(pattern)) {
			formatter=new PeriodFormatterBuilder().printZeroNever().appendYears().appendSuffix(" year"," years").appendSeparator(", "," and ",variants).appendMonths().appendSuffix(" month"," months").appendSeparator(", "," and ",variants).appendDays().appendSuffix(" day"," days").appendSeparator(", "," and ",variants).printZeroRarelyLast().appendHours().appendSuffix(" hour"," hours").toFormatter();
		}
		else   if ("m".equals(pattern)) {
			formatter=new PeriodFormatterBuilder().printZeroNever().appendYears().appendSuffix(" year"," years").appendSeparator(", "," and ",variants).appendMonths().appendSuffix(" month"," months").appendSeparator(", "," and ",variants).appendDays().appendSuffix(" day"," days").appendSeparator(", "," and ",variants).appendHours().appendSuffix(" hour"," hours").appendSeparator(", "," and ",variants).printZeroRarelyLast().appendMinutes().appendSuffix(" minute"," minutes").toFormatter();
		}
		else   if ("s".equals(pattern)) {
			formatter=new PeriodFormatterBuilder().printZeroNever().appendYears().appendSuffix(" year"," years").appendSeparator(", "," and ",variants).appendMonths().appendSuffix(" month"," months").appendSeparator(", "," and ",variants).appendDays().appendSuffix(" day"," days").appendSeparator(", "," and ",variants).appendHours().appendSuffix(" hour"," hours").appendSeparator(", "," and ",variants).appendMinutes().appendSuffix(" minute"," minutes").appendSeparator(", "," and ",variants).printZeroRarelyLast().appendSeconds().appendSuffix(" second"," seconds").toFormatter();
		}
		else {
			throw new IllegalArgumentException("pattern: '" + pattern + "'");
		}
		return formatter;
	}

	/**
	 * Null-safe method of converting a SQL Timestamp into a DateTime that
	 * it set specifically to be in UTC.
	 * <br>
	 * NOTE: The timestamp also should be in UTC.
	 * @return A UTC DateTime
	 */
	public static DateTime toDateTime(Timestamp value) {
		if (value == null) {
			return null;
		} else {
			return new DateTime(value.getTime(), DateTimeZone.UTC);
		}
	}

	/**
	 * Null-safe method of copying a DateTime
	 * <br>
	 * NOTE: The timestamp also should be in UTC.
	 * @return A UTC DateTime
	 */
	public static DateTime copy(DateTime value) {
		if (value == null) {
			return null;
		} else {
			return new DateTime(value.getMillis(), DateTimeZone.UTC);
		}
	}

	/**
	 * Null-safe method of converting a DateTime to a Timestamp.
	 * <br>
	 * NOTE: The timestamp also should be in UTC.
	 * @return A UTC DateTime
	 */
	public static Timestamp toTimestamp(DateTime dt) {
		if (dt == null) {
			return null;
		} else {
			return new Timestamp(dt.getMillis());
		}
	}

	/**
	 * Get the current time as a DateTime.
	 *
	 * @return A UTC DateTime
	 */
	public static DateTime UtCnow() {
		return new DateTime(DateTimeZone.UTC);
	}

	/**
	 * Parses a string for an embedded date and/or time contained within a
	 * string such as "app.2008-05-01.log". This method expects the string
	 * to contain a pattern of "yyyy-MM-dd".  All dates will be interpreted to
	 * be in the UTC timezone.
	 * @param string0 The string to parse
	 * @return The parsed DateTime value
	 * @throws IllegalArgumentException Thrown if the string did not contain
	 *      an embedded date.
	 */
	public static DateTime parseEmbedded(String string0) throws IllegalArgumentException {
		return parseEmbedded(string0, "yyyy-MM-dd", DateTimeZone.UTC);
	}

	/**
	 * Parses a string for an embedded date and/or time contained within a
	 * string such as "app.2008-05-01.log" or "app-20090624-051112.log.gz".
	 * This method accepts a variety of date and time patterns that are valid
	 * within the Joda DateTime library.  For example, the string "app.2008-05-01.log"
	 * would be a pattern of "yyyy-MM-dd" and the string "app-20090624-151112.log.gz"
	 * would be a pattern of "yyyy-MM-dd-HHmmss".
	 * @param string0 The string to parse
	 * @param pattern The DateTime pattern embedded in the string such as
	 *      "yyyy-MM-dd".  The pattern must be a valid DateTime Joda pattern.
	 * @param zone The timezone the parsed date will be in
	 * @return The parsed DateTime value
	 * @throws IllegalArgumentException Thrown if the pattern is invalid or if
	 *      string did not contain an embedded date.
	 */
	public static DateTime parseEmbedded(String string0, String pattern, DateTimeZone zone) throws IllegalArgumentException {
		// compile a date time formatter -- which also will check that pattern
		DateTimeFormatter fmt = DateTimeFormat.forPattern(pattern).withZone(zone);

		//
		// use the pattern to generate a regular expression now -- we'll do a
		// simple replacement of ascii characters with \\d
		//
		StringBuilder regex = new StringBuilder(pattern.length()*2);
		for (int i = 0; i < pattern.length(); i++) {
			char c = pattern.charAt(i);
			if (Character.isLetter(c)) {
				regex.append("\\d");
			} else {
				regex.append(c);
			}
		}

		// extract the date from the string
		Pattern p = Pattern.compile(regex.toString()); // Compiles regular expression into Pattern.
		Matcher m = p.matcher(string0); // Creates Matcher with subject s and Pattern p.

		if (!m.find()) {
			// if we get here, then no valid grouping was found
			throw new IllegalArgumentException("String '" + string0 + "' did not contain an embedded date [regexPattern='" + regex.toString() + "', datePattern='" + pattern + "']");
		}

		//logger.debug("Matching date: " + m.group());
		// this group represents a string in the format YYYY-MM-DD
		String dateString = m.group();
		// parse the string and return the Date
		return fmt.parseDateTime(dateString);
	}

	/**
	 * Null-safe method that returns a new instance of a DateTime object rounded
	 * downwards to the nearest year. Note that the nearest valid year is actually
	 * the first of that given year (Jan 1). The time zone of the returned DateTime
	 * instance will be the same as the argument. Similar to a floor() function
	 * on a float.<br>
	 * Examples:
	 * <ul>
	 *      <li>null -> null
	 *      <li>"2009-06-24 13:24:51.476 -8:00" -> "2009-01-01 00:00:00.000 -8:00"
	 * </ul>
	 * @param value The DateTime value to round downward
	 * @return Null if the argument is null or a new instance of the DateTime
	 *      value rounded downwards to the nearest year.
	 */
	public static DateTime floorToYear(DateTime value) {
		if (value == null) {
			return null;
		}
		return new DateTime(value.getYear(), 1, 1, 0, 0, 0, 0, value.getZone());
	}

	/**
	 * Null-safe method that returns a new instance of a DateTime object rounded
	 * downwards to the nearest month. Note that the nearest valid month is actually
	 * the first of that given month (1st day of month). The time zone of the returned DateTime
	 * instance will be the same as the argument. Similar to a floor() function
	 * on a float.<br>
	 * Examples:
	 * <ul>
	 *      <li>null -> null
	 *      <li>"2009-06-24 13:24:51.476 -8:00" -> "2009-06-01 00:00:00.000 -8:00"
	 * </ul>
	 * @param value The DateTime value to round downward
	 * @return Null if the argument is null or a new instance of the DateTime
	 *      value rounded downwards to the nearest month.
	 */
	public static DateTime floorToMonth(DateTime value) {
		if (value == null) {
			return null;
		}
		return new DateTime(value.getYear(), value.getMonthOfYear(), 1, 0, 0, 0, 0, value.getZone());
	}

	/**
	 * Null-safe method that returns a new instance of a DateTime object rounded
	 * downwards to the nearest day. The time zone of the returned DateTime
	 * instance will be the same as the argument. Similar to a floor() function
	 * on a float.<br>
	 * Examples:
	 * <ul>
	 *      <li>null -> null
	 *      <li>"2009-06-24 13:24:51.476 -8:00" -> "2009-06-24 00:00:00.000 -8:00"
	 * </ul>
	 * @param value The DateTime value to round downward
	 * @return Null if the argument is null or a new instance of the DateTime
	 *      value rounded downwards to the nearest day.
	 */
	public static DateTime floorToDay(DateTime value) {
		if (value == null) {
			return null;
		}
		return new DateTime(value.getYear(), value.getMonthOfYear(), value.getDayOfMonth(), 0, 0, 0, 0, value.getZone());
	}

	/**
	 * Null-safe method that returns a new instance of a DateTime object rounded
	 * downwards to the nearest hour. The time zone of the returned DateTime
	 * instance will be the same as the argument. Similar to a floor() function
	 * on a float.<br>
	 * Examples:
	 * <ul>
	 *      <li>null -> null
	 *      <li>"2009-06-24 13:24:51.476 -8:00" -> "2009-06-24 13:00:00.000 -8:00"
	 * </ul>
	 * @param value The DateTime value to round downward
	 * @return Null if the argument is null or a new instance of the DateTime
	 *      value rounded downwards to the nearest hour.
	 */
	public static DateTime floorToHour(DateTime value) {
		if (value == null) {
			return null;
		}
		return new DateTime(value.getYear(), value.getMonthOfYear(), value.getDayOfMonth(), value.getHourOfDay(), 0, 0, 0, value.getZone());
	}

	/**
	 * Null-safe method that returns a new instance of a DateTime object rounded
	 * downwards to the nearest half hour (30 minutes). The time zone of the
	 * returned DateTime instance will be the same as the argument. Similar to a
	 * floor() function on a float.<br>
	 * Examples:
	 * <ul>
	 *      <li>null -> null
	 *      <li>"2009-06-24 13:29:51.476 -8:00" -> "2009-06-24 13:00:00.000 -8:00"
	 * </ul>
	 * @param value The DateTime value to round downward
	 * @return Null if the argument is null or a new instance of the DateTime
	 *      value rounded downwards to the nearest 30 minutes.
	 */
	public static DateTime floorToHalfHour(DateTime value) {
		return floorToMinutePeriod(value, 30);
	}

	/**
	 * Null-safe method that returns a new instance of a DateTime object rounded
	 * downwards to the nearest 15 minutes. The time zone of the returned DateTime
	 * instance will be the same as the argument. Similar to a floor() function
	 * on a float.<br>
	 * Examples:
	 * <ul>
	 *      <li>null -> null
	 *      <li>"2009-06-24 13:29:51.476 -8:00" -> "2009-06-24 13:15:00.000 -8:00"
	 * </ul>
	 * @param value The DateTime value to round downward
	 * @return Null if the argument is null or a new instance of the DateTime
	 *      value rounded downwards to the nearest 15 minutes.
	 */
	public static DateTime floorToQuarterHour(DateTime value) {
		return floorToMinutePeriod(value, 15);
	}

	/**
	 * Null-safe method that returns a new instance of a DateTime object rounded
	 * downwards to the nearest 10 minutes. The time zone of the returned DateTime
	 * instance will be the same as the argument. Similar to a floor() function
	 * on a float.<br>
	 * Examples:
	 * <ul>
	 *      <li>null -> null
	 *      <li>"2009-06-24 13:29:51.476 -8:00" -> "2009-06-24 13:20:00.000 -8:00"
	 * </ul>
	 * @param value The DateTime value to round downward
	 * @return Null if the argument is null or a new instance of the DateTime
	 *      value rounded downwards to the nearest 10 minutes.
	 */
	public static DateTime floorToTenMinutes(DateTime value) {
		return floorToMinutePeriod(value, 10);
	}

	/**
	 * Null-safe method that returns a new instance of a DateTime object rounded
	 * downwards to the nearest 5 minutes. The time zone of the returned DateTime
	 * instance will be the same as the argument. Similar to a floor() function
	 * on a float.<br>
	 * Examples:
	 * <ul>
	 *      <li>null -> null
	 *      <li>"2009-06-24 13:24:51.476 -8:00" -> "2009-06-24 13:20:00.000 -8:00"
	 * </ul>
	 * @param value The DateTime value to round downward
	 * @return Null if the argument is null or a new instance of the DateTime
	 *      value rounded downwards to the nearest 5 minutes.
	 */
	public static DateTime floorToFiveMinutes(DateTime value) {
		return floorToMinutePeriod(value, 5);
	}

	/**
	 * Null-safe method that returns a new instance of a DateTime object rounded
	 * downwards to the nearest specified period in minutes. For example, if
	 * a period of 5 minutes is requested, a time of "2009-06-24 13:24:51.476 -8:00"
	 * would return a datetime of "2009-06-24 13:20:00.000 -8:00". The time zone of the
	 * returned DateTime instance will be the same as the argument. Similar to a
	 * floor() function on a float.<br>
	 * NOTE: While any period in minutes between 1 and 59 can be passed into this
	 * method, its only useful if the value can be evenly divided into 60 to make
	 * it as useful as possible.<br>
	 * Examples:
	 * <ul>
	 *      <li>null -> null
	 *      <li>5: "2009-06-24 13:39:51.476 -8:00" -> "2009-06-24 13:35:00.000 -8:00"
	 *      <li>10: "2009-06-24 13:39:51.476 -8:00" -> "2009-06-24 13:30:00.000 -8:00"
	 *      <li>15: "2009-06-24 13:39:51.476 -8:00" -> "2009-06-24 13:30:00.000 -8:00"
	 *      <li>20: "2009-06-24 13:39:51.476 UTC" -> "2009-06-24 13:20:00.000 UTC"
	 * </ul>
	 * @param value The DateTime value to round downward
	 * @return Null if the argument is null or a new instance of the DateTime
	 *      value rounded downwards to the nearest period in minutes.
	 */
	public static DateTime floorToMinutePeriod(DateTime value, int periodInMinutes) {
		if (value == null) {
			return null;
		}
		if (periodInMinutes <= 0 || periodInMinutes > 59) {
			throw new IllegalArgumentException("period in minutes must be > 0 and <= 59");
		}
		int min = value.getMinuteOfHour();
		min = (min / periodInMinutes) * periodInMinutes;
		return new DateTime(value.getYear(), value.getMonthOfYear(), value.getDayOfMonth(), value.getHourOfDay(), min, 0, 0, value.getZone());
	}

	/**
	 * Null-safe method that returns a new instance of a DateTime object rounded
	 * downwards to the nearest minute. The time zone of the returned DateTime
	 * instance will be the same as the argument. Similar to a floor() function
	 * on a float.<br>
	 * Examples:
	 * <ul>
	 *      <li>null -> null
	 *      <li>"2009-06-24 13:24:51.476 -8:00" -> "2009-06-24 13:24:00.000 -8:00"
	 * </ul>
	 * @param value The DateTime value to round downward
	 * @return Null if the argument is null or a new instance of the DateTime
	 *      value rounded downwards to the nearest minute.
	 */
	public static DateTime floorToMinute(DateTime value) {
		if (value == null) {
			return null;
		}
		return new DateTime(value.getYear(), value.getMonthOfYear(), value.getDayOfMonth(), value.getHourOfDay(), value.getMinuteOfHour(), 0, 0, value.getZone());
	}

	/**
	 * Null-safe method that returns a new instance of a DateTime object rounded
	 * downwards to the nearest second. The time zone of the returned DateTime
	 * instance will be the same as the argument. Similar to a floor() function
	 * on a float.<br>
	 * Examples:
	 * <ul>
	 *      <li>null -> null
	 *      <li>"2009-06-24 13:24:51.476 -8:00" -> "2009-06-24 13:24:51.000 -8:00"
	 * </ul>
	 * @param value The DateTime value to round downward
	 * @return Null if the argument is null or a new instance of the DateTime
	 *      value rounded downwards to the nearest second.
	 */
	public static DateTime floorToSecond(DateTime value) {
		if (value == null) {
			return null;
		}
		return new DateTime(value.getYear(), value.getMonthOfYear(), value.getDayOfMonth(), value.getHourOfDay(), value.getMinuteOfHour(), value.getSecondOfMinute(), 0, value.getZone());
	}

  /*
	// parse just dates
	DateTime dt0 = DateTimeUtil.parseEmbedded("app-2009-06-24.log.gz", "yyyy-MM-dd", DateTimeZone.UTC);
	logger.debug("dt = " + dt0);
	Assert.assertEquals(new DateTime(2009,6,24,0,0,0,0,DateTimeZone.UTC), dt0);

	// parse a date and time
	dt0 = DateTimeUtil.parseEmbedded("app-2009-06-24-051112.log.gz", "yyyy-MM-dd-hhmmss", DateTimeZone.UTC);
	logger.debug("dt = " + dt0);
	Assert.assertEquals(new DateTime(2009,6,24,5,11,12,0,DateTimeZone.UTC), dt0);

	// parse different date format
	dt0 = DateTimeUtil.parseEmbedded("app-20090624-051112.log.gz", "yyyyMMdd-hhmmss", DateTimeZone.UTC);
	logger.debug("dt = " + dt0);
	Assert.assertEquals(new DateTime(2009,6,24,5,11,12,0,DateTimeZone.UTC), dt0);

	// parse just year and month
	dt0 = DateTimeUtil.parseEmbedded("app-200906.log.gz", "yyyyMM", DateTimeZone.UTC);
	logger.debug("dt = " + dt0);
	Assert.assertEquals(new DateTime(2009,6,1,0,0,0,0,DateTimeZone.UTC), dt0);

	try {
	// filename is missing a day
	dt0 = DateTimeUtil.parseEmbedded("app-200906.log.gz", "yyyyMMdd", DateTimeZone.UTC);
	Assert.fail("parse should have failed");
} catch (IllegalArgumentException e) {
	// correct behavior
}

	try {
	// june only has 30 days
	dt0 = DateTimeUtil.parseEmbedded("app-20090631.log.gz", "yyyyMMdd", DateTimeZone.UTC);
	Assert.fail("parse should have failed");
} catch (IllegalArgumentException e) {
	// correct behavior
}

	try {
	// tt isn't a valid pattern
	dt0 = DateTimeUtil.parseEmbedded("app-20090631.log.gz", "yyyyMMtt", DateTimeZone.UTC);
	Assert.fail("parse should have failed");
} catch (IllegalArgumentException e) {
	// correct behavior
}
}

	@Test(expected=IllegalArgumentException.class)
	public void parseEmbeddedThrowsException0() throws Exception {
		DateTimeUtil.parseEmbedded("app.2008-05-0.log");
	}

	@Test(expected=IllegalArgumentException.class)
	public void parseEmbeddedThrowsException1() throws Exception {
		DateTimeUtil.parseEmbedded("app.2008-0-01.log");
	}

	@Test(expected=IllegalArgumentException.class)
	public void parseEmbeddedThrowsException2() throws Exception {
		DateTimeUtil.parseEmbedded("app.208-05-01.log");
	}

	/**
	 @Test
	 public void toMidnightUTCDateTime() throws Exception {
	 // create a DateTime in the pacific timezone for June 24, 2009 at 11 PM
	 DateTime dt = new DateTime(2009,6,24,23,30,30,0,DateTimeZone.forID("America/Los_Angeles"));
	 logger.info("Local DateTime: " + dt);
	 // just for making sure we're creating something interesting, let's
	 // just convert this to UTC without using our utility function
	 DateTime utcdt = dt.toDateTime(DateTimeZone.UTC);
	 logger.info("DateTime -> UTC: " + utcdt);
	 // convert this to be in the UTC timezone -- reset to midnight
	 DateTime newdt = DateTimeUtil.toYearMonthDayUTC(dt);
	 logger.debug("DateTime -> UTC (but with util): " + newdt);
	 Assert.assertEquals(2009, newdt.getYear());
	 Assert.assertEquals(6, newdt.getMonthOfYear());
	 Assert.assertEquals(24, newdt.getDayOfMonth());
	 Assert.assertEquals(0, newdt.getHourOfDay());
	 Assert.assertEquals(0, newdt.getMinuteOfHour());
	 Assert.assertEquals(0, newdt.getSecondOfMinute());
	 Assert.assertEquals(0, newdt.getMillisOfSecond());
	 }
	 */
/*
	@Test
	public void floorToYear() throws Exception {
		// create a reference datetime
		DateTime dt0 = new DateTime(2009,6,24,23,30,30,789,DateTimeZone.forID("America/Los_Angeles"));

		//
		// floor to nearest year
		//
		DateTime dt1 = DateTimeUtil.floorToYear(dt0);

		Assert.assertEquals(2009, dt1.getYear());
		Assert.assertEquals(1, dt1.getMonthOfYear());
		Assert.assertEquals(1, dt1.getDayOfMonth());
		Assert.assertEquals(0, dt1.getHourOfDay());
		Assert.assertEquals(0, dt1.getMinuteOfHour());
		Assert.assertEquals(0, dt1.getSecondOfMinute());
		Assert.assertEquals(0, dt1.getMillisOfSecond());
		Assert.assertEquals(DateTimeZone.forID("America/Los_Angeles"), dt1.getZone());

		//
		// floor null
		//
		DateTime dt2 = DateTimeUtil.floorToYear(null);
		Assert.assertNull(dt2);
	}

	@Test
	public void floorToMonth() throws Exception {
		// create a reference datetime
		DateTime dt0 = new DateTime(2009,6,24,23,30,30,789,DateTimeZone.forID("America/Los_Angeles"));

		//
		// floor to nearest month
		//
		DateTime dt1 = DateTimeUtil.floorToMonth(dt0);

		Assert.assertEquals(2009, dt1.getYear());
		Assert.assertEquals(6, dt1.getMonthOfYear());
		Assert.assertEquals(1, dt1.getDayOfMonth());
		Assert.assertEquals(0, dt1.getHourOfDay());
		Assert.assertEquals(0, dt1.getMinuteOfHour());
		Assert.assertEquals(0, dt1.getSecondOfMinute());
		Assert.assertEquals(0, dt1.getMillisOfSecond());
		Assert.assertEquals(DateTimeZone.forID("America/Los_Angeles"), dt1.getZone());

		//
		// floor null
		//
		DateTime dt2 = DateTimeUtil.floorToMonth(null);
		Assert.assertNull(dt2);
	}

	@Test
	public void floorToDay() throws Exception {
		// create a reference datetime
		DateTime dt0 = new DateTime(2009,6,24,23,30,30,789,DateTimeZone.forID("America/Los_Angeles"));

		//
		// floor to nearest day
		//
		DateTime dt1 = DateTimeUtil.floorToDay(dt0);

		Assert.assertEquals(2009, dt1.getYear());
		Assert.assertEquals(6, dt1.getMonthOfYear());
		Assert.assertEquals(24, dt1.getDayOfMonth());
		Assert.assertEquals(0, dt1.getHourOfDay());
		Assert.assertEquals(0, dt1.getMinuteOfHour());
		Assert.assertEquals(0, dt1.getSecondOfMinute());
		Assert.assertEquals(0, dt1.getMillisOfSecond());
		Assert.assertEquals(DateTimeZone.forID("America/Los_Angeles"), dt1.getZone());

		//
		// floor null
		//
		DateTime dt2 = DateTimeUtil.floorToDay(null);
		Assert.assertNull(dt2);
	}

	@Test
	public void floorToHour() throws Exception {
		// create a reference datetime
		DateTime dt0 = new DateTime(2009,6,24,23,30,30,789,DateTimeZone.forID("America/Los_Angeles"));

		//
		// floor to nearest hour
		//
		DateTime dt1 = DateTimeUtil.floorToHour(dt0);

		Assert.assertEquals(2009, dt1.getYear());
		Assert.assertEquals(6, dt1.getMonthOfYear());
		Assert.assertEquals(24, dt1.getDayOfMonth());
		Assert.assertEquals(23, dt1.getHourOfDay());
		Assert.assertEquals(0, dt1.getMinuteOfHour());
		Assert.assertEquals(0, dt1.getSecondOfMinute());
		Assert.assertEquals(0, dt1.getMillisOfSecond());
		Assert.assertEquals(DateTimeZone.forID("America/Los_Angeles"), dt1.getZone());

		//
		// floor null
		//
		DateTime dt2 = DateTimeUtil.floorToHour(null);
		Assert.assertNull(dt2);
	}

	@Test
	public void floorToHalfHour() throws Exception {
		// create a reference datetime
		DateTime dt0 = new DateTime(2009,6,24,23,29,30,789,DateTimeZone.UTC);

		Assert.assertNull(DateTimeUtil.floorToHalfHour(null));

		// floor to nearest half hour
		DateTime dt1 = DateTimeUtil.floorToHalfHour(dt0);

		Assert.assertEquals(2009, dt1.getYear());
		Assert.assertEquals(6, dt1.getMonthOfYear());
		Assert.assertEquals(24, dt1.getDayOfMonth());
		Assert.assertEquals(23, dt1.getHourOfDay());
		Assert.assertEquals(0, dt1.getMinuteOfHour());
		Assert.assertEquals(0, dt1.getSecondOfMinute());
		Assert.assertEquals(0, dt1.getMillisOfSecond());
		Assert.assertEquals(DateTimeZone.UTC, dt1.getZone());

		DateTime dt3 = DateTimeUtil.floorToHalfHour(new DateTime(2009,6,24,10,0,0,0));
		Assert.assertEquals(new DateTime(2009,6,24,10,0,0,0), dt3);

		dt3 = DateTimeUtil.floorToHalfHour(new DateTime(2009,6,24,10,1,23,456));
		Assert.assertEquals(new DateTime(2009,6,24,10,0,0,0), dt3);

		dt3 = DateTimeUtil.floorToHalfHour(new DateTime(2009,6,24,10,30,12,56));
		Assert.assertEquals(new DateTime(2009,6,24,10,30,0,0), dt3);

		dt3 = DateTimeUtil.floorToHalfHour(new DateTime(2009,6,24,10,59,59,999));
		Assert.assertEquals(new DateTime(2009,6,24,10,30,0,0), dt3);

		dt3 = DateTimeUtil.floorToHalfHour(new DateTime(2009,6,24,10,55,59,999));
		Assert.assertEquals(new DateTime(2009,6,24,10,30,0,0), dt3);

		dt3 = DateTimeUtil.floorToHalfHour(new DateTime(2009,6,24,10,46,59,999));
		Assert.assertEquals(new DateTime(2009,6,24,10,30,0,0), dt3);
	}

	@Test
	public void floorToQuarterHour() throws Exception {
		// create a reference datetime
		DateTime dt0 = new DateTime(2009,6,24,23,29,30,789,DateTimeZone.UTC);

		Assert.assertNull(DateTimeUtil.floorToQuarterHour(null));

		// floor to nearest half hour
		DateTime dt1 = DateTimeUtil.floorToQuarterHour(dt0);

		Assert.assertEquals(2009, dt1.getYear());
		Assert.assertEquals(6, dt1.getMonthOfYear());
		Assert.assertEquals(24, dt1.getDayOfMonth());
		Assert.assertEquals(23, dt1.getHourOfDay());
		Assert.assertEquals(15, dt1.getMinuteOfHour());
		Assert.assertEquals(0, dt1.getSecondOfMinute());
		Assert.assertEquals(0, dt1.getMillisOfSecond());
		Assert.assertEquals(DateTimeZone.UTC, dt1.getZone());

		DateTime dt3 = DateTimeUtil.floorToQuarterHour(new DateTime(2009,6,24,10,0,0,0));
		Assert.assertEquals(new DateTime(2009,6,24,10,0,0,0), dt3);

		dt3 = DateTimeUtil.floorToQuarterHour(new DateTime(2009,6,24,10,1,23,456));
		Assert.assertEquals(new DateTime(2009,6,24,10,0,0,0), dt3);

		dt3 = DateTimeUtil.floorToQuarterHour(new DateTime(2009,6,24,10,30,12,56));
		Assert.assertEquals(new DateTime(2009,6,24,10,30,0,0), dt3);

		dt3 = DateTimeUtil.floorToQuarterHour(new DateTime(2009,6,24,10,59,59,999));
		Assert.assertEquals(new DateTime(2009,6,24,10,45,0,0), dt3);

		dt3 = DateTimeUtil.floorToQuarterHour(new DateTime(2009,6,24,10,55,59,999));
		Assert.assertEquals(new DateTime(2009,6,24,10,45,0,0), dt3);

		dt3 = DateTimeUtil.floorToQuarterHour(new DateTime(2009,6,24,10,46,59,999));
		Assert.assertEquals(new DateTime(2009,6,24,10,45,0,0), dt3);
	}

	@Test
	public void floorToTenMinutes() throws Exception {
		// create a reference datetime
		DateTime dt0 = new DateTime(2009,6,24,23,29,30,789,DateTimeZone.UTC);

		Assert.assertNull(DateTimeUtil.floorToTenMinutes(null));

		// floor to nearest half hour
		DateTime dt1 = DateTimeUtil.floorToTenMinutes(dt0);

		Assert.assertEquals(2009, dt1.getYear());
		Assert.assertEquals(6, dt1.getMonthOfYear());
		Assert.assertEquals(24, dt1.getDayOfMonth());
		Assert.assertEquals(23, dt1.getHourOfDay());
		Assert.assertEquals(20, dt1.getMinuteOfHour());
		Assert.assertEquals(0, dt1.getSecondOfMinute());
		Assert.assertEquals(0, dt1.getMillisOfSecond());
		Assert.assertEquals(DateTimeZone.UTC, dt1.getZone());

		DateTime dt3 = DateTimeUtil.floorToTenMinutes(new DateTime(2009,6,24,10,0,0,0));
		Assert.assertEquals(new DateTime(2009,6,24,10,0,0,0), dt3);

		dt3 = DateTimeUtil.floorToTenMinutes(new DateTime(2009,6,24,10,1,23,456));
		Assert.assertEquals(new DateTime(2009,6,24,10,0,0,0), dt3);

		dt3 = DateTimeUtil.floorToTenMinutes(new DateTime(2009,6,24,10,30,12,56));
		Assert.assertEquals(new DateTime(2009,6,24,10,30,0,0), dt3);

		dt3 = DateTimeUtil.floorToTenMinutes(new DateTime(2009,6,24,10,59,59,999));
		Assert.assertEquals(new DateTime(2009,6,24,10,50,0,0), dt3);

		dt3 = DateTimeUtil.floorToTenMinutes(new DateTime(2009,6,24,10,55,59,999));
		Assert.assertEquals(new DateTime(2009,6,24,10,50,0,0), dt3);

		dt3 = DateTimeUtil.floorToTenMinutes(new DateTime(2009,6,24,10,46,59,999));
		Assert.assertEquals(new DateTime(2009,6,24,10,40,0,0), dt3);
	}

	@Test
	public void floorToFiveMinutes() throws Exception {
		// create a reference datetime
		DateTime dt0 = new DateTime(2009,6,24,23,30,30,789,DateTimeZone.forID("America/Los_Angeles"));

		//
		// floor to nearest five minutes
		//
		DateTime dt1 = DateTimeUtil.floorToFiveMinutes(dt0);

		Assert.assertEquals(2009, dt1.getYear());
		Assert.assertEquals(6, dt1.getMonthOfYear());
		Assert.assertEquals(24, dt1.getDayOfMonth());
		Assert.assertEquals(23, dt1.getHourOfDay());
		Assert.assertEquals(30, dt1.getMinuteOfHour());
		Assert.assertEquals(0, dt1.getSecondOfMinute());
		Assert.assertEquals(0, dt1.getMillisOfSecond());
		Assert.assertEquals(DateTimeZone.forID("America/Los_Angeles"), dt1.getZone());

		//
		// floor null
		//
		DateTime dt2 = DateTimeUtil.floorToFiveMinutes(null);
		Assert.assertNull(dt2);

		//
		// various tests since rounding five minutes is more complicated
		//
		DateTime dt3 = DateTimeUtil.floorToFiveMinutes(new DateTime(2009,6,24,10,0,0,0));
		Assert.assertEquals(new DateTime(2009,6,24,10,0,0,0), dt3);

		dt3 = DateTimeUtil.floorToFiveMinutes(new DateTime(2009,6,24,10,1,23,456));
		Assert.assertEquals(new DateTime(2009,6,24,10,0,0,0), dt3);

		dt3 = DateTimeUtil.floorToFiveMinutes(new DateTime(2009,6,24,10,2,12,56));
		Assert.assertEquals(new DateTime(2009,6,24,10,0,0,0), dt3);

		dt3 = DateTimeUtil.floorToFiveMinutes(new DateTime(2009,6,24,10,59,59,999));
		Assert.assertEquals(new DateTime(2009,6,24,10,55,0,0), dt3);

		dt3 = DateTimeUtil.floorToFiveMinutes(new DateTime(2009,6,24,10,55,59,999));
		Assert.assertEquals(new DateTime(2009,6,24,10,55,0,0), dt3);

		dt3 = DateTimeUtil.floorToFiveMinutes(new DateTime(2009,6,24,10,46,59,999));
		Assert.assertEquals(new DateTime(2009,6,24,10,45,0,0), dt3);
	}

	@Test
	public void floorToMinute() throws Exception {
		// create a reference datetime
		DateTime dt0 = new DateTime(2009,6,24,23,30,30,789,DateTimeZone.forID("America/Los_Angeles"));

		//
		// floor to nearest minute
		//
		DateTime dt1 = DateTimeUtil.floorToMinute(dt0);

		Assert.assertEquals(2009, dt1.getYear());
		Assert.assertEquals(6, dt1.getMonthOfYear());
		Assert.assertEquals(24, dt1.getDayOfMonth());
		Assert.assertEquals(23, dt1.getHourOfDay());
		Assert.assertEquals(30, dt1.getMinuteOfHour());
		Assert.assertEquals(0, dt1.getSecondOfMinute());
		Assert.assertEquals(0, dt1.getMillisOfSecond());
		Assert.assertEquals(DateTimeZone.forID("America/Los_Angeles"), dt1.getZone());

		//
		// floor null
		//
		DateTime dt2 = DateTimeUtil.floorToMinute(null);
		Assert.assertNull(dt2);
	}

	@Test
	public void floorToSecond() throws Exception {
		// create a reference datetime
		DateTime dt0 = new DateTime(2009,6,24,23,30,31,789,DateTimeZone.forID("America/Los_Angeles"));

		//
		// floor to nearest second
		//
		DateTime dt1 = DateTimeUtil.floorToSecond(dt0);

		Assert.assertEquals(2009, dt1.getYear());
		Assert.assertEquals(6, dt1.getMonthOfYear());
		Assert.assertEquals(24, dt1.getDayOfMonth());
		Assert.assertEquals(23, dt1.getHourOfDay());
		Assert.assertEquals(30, dt1.getMinuteOfHour());
		Assert.assertEquals(31, dt1.getSecondOfMinute());
		Assert.assertEquals(0, dt1.getMillisOfSecond());
		Assert.assertEquals(DateTimeZone.forID("America/Los_Angeles"), dt1.getZone());

		//
		// floor null
		//
		DateTime dt2 = DateTimeUtil.floorToSecond(null);
		Assert.assertNull(dt2);
	}   */

}
