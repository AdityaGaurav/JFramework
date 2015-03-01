package com.framework.utils.matchers;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.joda.time.DateTimeComparator;
import org.joda.time.DateTimeFieldType;
import org.joda.time.ReadableDateTime;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.utils.matchers
 *
 * Name   : IsDateTimeGraterThan
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-01-16
 *
 * Time   : 13:16
 */

public class IsDateTimePartAfterThan extends TypeSafeMatcher<ReadableDateTime>
{

	//region IsDateTimePartAfterThan - Variables Declaration and Initialization Section.

	private final ReadableDateTime dt;

	private final DateTimeFieldType dateTimeFieldType;


	//endregion


	//region IsDateTimePartAfterThan - Constructor Methods Section

	public IsDateTimePartAfterThan( ReadableDateTime dateTime, DateTimeFieldType dateTimeFieldType )
	{
		this.dt = dateTime;
		this.dateTimeFieldType = dateTimeFieldType;
	}

	//endregion


	//region IsDateTimePartAfterThan - Public Methods Section

	@Override
	public boolean matchesSafely( ReadableDateTime item )
	{
		DateTimeComparator dtComparator = DateTimeComparator.getInstance( dateTimeFieldType );
		return dtComparator.compare( dt, item ) < 0;
	}

	public void describeTo( Description description )
	{
		description.appendText( "a datetime " )
			.appendValue( dateTimeFieldType.toString() )
			.appendText( "instance is after than " );
		description.appendValue( dt );
	}

	//endregion

}
