package com.framework.driver.objects;

import java.net.URL;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.driver.objects
 *
 * Name   : AssertingURL
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-01-11
 *
 * Time   : 14:00
 */

public interface AssertingURL
{
	void assertURL( URL url ) throws AssertionError;
}
