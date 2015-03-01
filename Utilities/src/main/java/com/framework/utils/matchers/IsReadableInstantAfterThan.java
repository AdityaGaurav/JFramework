package com.framework.utils.matchers;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.joda.time.ReadableInstant;


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

public class IsReadableInstantAfterThan extends TypeSafeMatcher<ReadableInstant>
{

	//region IsDateTimeAfterThan - Variables Declaration and Initialization Section.

	private final ReadableInstant readableInstant;


	//endregion


	//region IsDateTimeAfterThan - Constructor Methods Section

	public IsReadableInstantAfterThan( ReadableInstant dateTime )
	{
		this.readableInstant = dateTime;
	}

	//endregion


	//region IsDateTimeAfterThan - Public Methods Section

	@Override
	public boolean matchesSafely( ReadableInstant item )
	{
		return item.compareTo( item ) < 0;
	}

	public void describeTo( Description description )
	{
		description.appendText( "an instance is after than " );
		description.appendValue( readableInstant );
	}

	//endregion

}
