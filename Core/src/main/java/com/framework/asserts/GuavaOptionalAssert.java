package com.framework.asserts;

import com.google.common.base.Optional;
import org.assertj.guava.api.OptionalAssert;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.asserts
 *
 * Name   : GuavaOptionalAssert
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-01-05
 *
 * Time   : 18:32
 */

public class GuavaOptionalAssert<T> extends OptionalAssert<T>
{

	public GuavaOptionalAssert( final Optional<T> actual )
	{
		super( actual );
	}
}
