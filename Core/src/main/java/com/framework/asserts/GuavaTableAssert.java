package com.framework.asserts;

import com.google.common.collect.Table;
import org.assertj.guava.api.TableAssert;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.asserts
 *
 * Name   : GuavaTableAssert
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-01-05
 *
 * Time   : 18:26
 */

public class GuavaTableAssert<R, C, V> extends TableAssert<R, C, V>
{
	//region GuavaTableAssert - Constructor Methods Section

	public GuavaTableAssert( final Table<R, C, V> actual )
	{
		super( actual );
	}

	//endregion
}
