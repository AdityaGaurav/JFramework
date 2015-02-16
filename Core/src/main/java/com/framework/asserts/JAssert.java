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
	/**
	 * @return  a description of the assert given by the user
	 */
	String getReason();

	/**
	 * @return  a description of the assert expected result
	 */
	Description getExpected();

	/**
	 * executes the assert command. used by subclasses.
	 */
	void doAssert();

	/**
	 * @return the actual result
	 */
	Object getActual();
}
