package com.framework.asserts;

import com.google.common.collect.Range;
import org.assertj.guava.api.RangeAssert;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.asserts
 *
 * Name   : GuavaRangeAssert
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-01-05
 *
 * Time   : 18:28
 */

public class GuavaRangeAssert<T extends Comparable<T>> extends RangeAssert<T>
{

	public GuavaRangeAssert( final Range<T> actual )
	{
		super( actual );
	}
}
