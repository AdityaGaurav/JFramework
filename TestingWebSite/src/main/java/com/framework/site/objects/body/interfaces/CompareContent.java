package com.framework.site.objects.body.interfaces;

import com.framework.site.data.Ships;
import com.google.common.collect.Table;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.site.objects.body.interfaces
 *
 * Name   : CompareContent
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-01-10
 *
 * Time   : 02:48
 */

public interface CompareContent
{
	 interface CompareBlock
	 {
		 Table<String,Ships,Boolean> getTable();
	 }
}
