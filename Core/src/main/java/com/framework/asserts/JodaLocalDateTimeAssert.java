package com.framework.asserts;

import org.assertj.jodatime.api.LocalDateTimeAssert;
import org.joda.time.LocalDateTime;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.asserts
 *
 * Name   : JodaLocalDateTimeAssert
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-01-05
 *
 * Time   : 18:19
 */

public class JodaLocalDateTimeAssert extends LocalDateTimeAssert
{
	//region JodaLocalDateTimeAssert - Constructor Methods Section

	public JodaLocalDateTimeAssert( final Class<LocalDateTimeAssert> selfType, final LocalDateTime actual )
	{
		super( selfType, actual );
	}

	//endregion

}
