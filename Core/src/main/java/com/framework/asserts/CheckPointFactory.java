package com.framework.asserts;

import com.framework.driver.event.HtmlElement;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.asserts
 *
 * Name   : CheckPointFactory 
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-03-18 
 *
 * Time   : 17:12
 *
 */

public interface CheckPointFactory
{
	CheckpointAssert createCheckPoint( final String id );

	CheckpointAssert createCheckPoint( HtmlElement element, final String id );

	void assertAllCheckpoints();
}
