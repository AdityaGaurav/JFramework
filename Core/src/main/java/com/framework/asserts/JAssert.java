package com.framework.asserts;

import org.hamcrest.Description;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.utils.asserts
 *
 * Name   : JAssert
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-01-16
 *
 * Time   : 09:15
 */

public interface JAssert
{
	String getReason();

	Description getExpected();

	void doAssert();

	@Deprecated
	String getScreenShotFileName();

	<T> T getActual();
}
