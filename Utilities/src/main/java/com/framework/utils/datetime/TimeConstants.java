package com.framework.utils.datetime;

import org.apache.commons.lang3.math.NumberUtils;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: org.framework.webdriver.utils
 *
 * Name   : TimeConstants
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2014-12-14
 *
 * Time   : 13:22
 */

public interface TimeConstants
{
	public final long QUARTER_OF_SECOND = NumberUtils.createLong( "250" );

	public final long HALF_OF_SECOND = NumberUtils.createLong( "500" );

	public final long TEN_HUNDRED_MILLIS = NumberUtils.createLong( "1000" );

	public final long FIFTEEN_HUNDRED_MILLIS = NumberUtils.createLong( "1500" );

	public final long MILLIS_IN_ONE_SECONDS = NumberUtils.createLong( "2000" );

	public final long TWENTY_FIVE_HUNDRED_MILLIS = NumberUtils.createLong( "2500" );

	public final long MILLIS_IN_THREE_SECONDS = NumberUtils.createLong( "3000" );

	public final long MILLIS_IN_FIVE_SECONDS = NumberUtils.createLong( "5000" );

	public final long ZERO_SECONDS = NumberUtils.LONG_ZERO;

	public final long ONE_SECOND = NumberUtils.LONG_ONE;

	public final long TWO_SECONDS = NumberUtils.createLong( "2" );

	public final long THREE_SECONDS = NumberUtils.createLong( "3" );

	public final long FIVE_SECONDS = NumberUtils.createLong( "5" );

	public final long TEN_SECONDS = NumberUtils.createLong( "10" );

	public final long TWENTY_SECONDS = NumberUtils.createLong( "20" );

	public final long HALF_MINUTE = NumberUtils.createLong( "30" );

	public final long ONE_MINUTE = NumberUtils.createLong( "60" );

	public final long QUARTER_MINUTE = NumberUtils.createLong( "15" );
}
