package com.framework.utils.matchers;

import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.joda.time.DateTime;
import org.joda.time.DateTimeFieldType;
import org.joda.time.ReadableDateTime;
import org.joda.time.ReadableInstant;

import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.AnyOf.anyOf;
import static org.hamcrest.core.DescribedAs.describedAs;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.utils.matchers
 *
 * Name   : DateTimeOrderingComparisons
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-01-16
 *
 * Time   : 13:14
 */

public class ReadableInstantOrderingComparisons
{

	@Factory
	public static Matcher<ReadableInstant> isAfterThan( ReadableInstant instance )
	{
		return new IsReadableInstantAfterThan( instance );
	}

	@Factory
	public static Matcher<ReadableInstant> isAfterOrSameThan( ReadableInstant instance )
	{
		return describedAs( "an instance is after or same to %0", anyOf( isAfterThan( instance ), equalTo( instance ) ), instance );
	}

	@Factory
	public static Matcher<ReadableInstant> isBeforeThan( ReadableInstant instance )
	{
		return describedAs( "an instance is before than %0", not( isAfterOrSameThan( instance ) ), instance );
	}

	@Factory
	public static Matcher<ReadableInstant> isBeforeOrSameThan( ReadableInstant instance )
	{
		return describedAs( "an instance is before or same than %0", not( isAfterOrSameThan( instance ) ), instance );
	}

	@Factory
	public static Matcher<ReadableInstant> isExplicitlyBetween( ReadableInstant instance1, ReadableInstant instance2 )
	{
		String desc = "an instance is between %0 and between %1";
		if( instance1.compareTo( instance2 ) > 0 )
		{
			return describedAs( desc, allOf( isBeforeThan( instance1 ) , isAfterThan( instance2 ) ), instance1, instance2 );
		}
		else
		{
			return describedAs( desc, allOf( isBeforeThan( instance2 ) , isAfterThan( instance1 ) ), instance2, instance1 );
		}
	}

	@Factory
	public static Matcher<ReadableInstant> isImplicitlyBetween( ReadableInstant instance1, ReadableInstant instance2 )
	{
		String desc = "an instance is between %0 and between %1";
		if( instance1.compareTo( instance2 ) > 0 )
		{
			return describedAs( desc, allOf( isBeforeOrSameThan( instance1 ), isAfterOrSameThan( instance2 ) ), instance2, instance1 );
		}
		else
		{
			return describedAs( desc, allOf( isBeforeOrSameThan( instance2 ) , isAfterOrSameThan( instance1 ) ), instance2, instance1 );
		}
	}

	@Factory
	public static Matcher<ReadableDateTime> isDateTimePartTypeAfterThan( DateTime dt, DateTimeFieldType dateTimeFieldType )
	{
		return new IsDateTimePartAfterThan( dt, dateTimeFieldType );
	}

	@Factory
	public static Matcher<ReadableDateTime> isDateTimePartTypeBeforeThan( DateTime dt, DateTimeFieldType dateTimeFieldType )
	{
		return describedAs( "an datetime part type is before than %0", not( isDateTimePartTypeAfterThan( dt, dateTimeFieldType ) ), dt );
	}

}
