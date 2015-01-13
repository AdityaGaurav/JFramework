package com.framework.asserts;

import org.assertj.jodatime.api.DateTimeAssert;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.asserts
 *
 * Name   : JodaDateTimeAssert
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-01-05
 *
 * Time   : 18:17
 */

public class JodaDateTimeAssert extends DateTimeAssert
{

	//region JodaDateTimeAssert - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( JodaDateTimeAssert.class );

	//endregion


	//region JodaDateTimeAssert - Constructor Methods Section

	public JodaDateTimeAssert( final Class<DateTimeAssert> selfType, final DateTime actual )
	{
		super( selfType, actual );
	}

	//endregion


	//region JodaDateTimeAssert - Public Methods Section

	//endregion


	//region JodaDateTimeAssert - Protected Methods Section

	//endregion


	//region JodaDateTimeAssert - Private Function Section

	//endregion


	//region JodaDateTimeAssert - Inner Classes Implementation Section

	//endregion

}
