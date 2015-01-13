package com.framework.asserts;

import com.google.common.io.ByteSource;
import org.assertj.guava.api.ByteSourceAssert;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.asserts
 *
 * Name   : GuavaByteSourceAssert
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-01-05
 *
 * Time   : 18:33
 */

public class GuavaByteSourceAssert extends ByteSourceAssert
{

	public GuavaByteSourceAssert( final ByteSource actual )
	{
		super( actual );
	}
}
