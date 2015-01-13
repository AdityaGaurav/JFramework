package com.framework.asserts;

import com.google.common.collect.Multimap;
import org.assertj.guava.api.MultimapAssert;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.asserts
 *
 * Name   : GuavaMultimapAssert
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-01-05
 *
 * Time   : 18:25
 */

public class GuavaMultimapAssert<K, V> extends MultimapAssert<K, V>
{

	//region GuavaMultimapAssert - Constructor Methods Section

	public GuavaMultimapAssert( final Multimap<K, V> actual )
	{
		super( actual );
	}


	//endregion


}
