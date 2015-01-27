package com.framework.testing.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.testing.annotations
 *
 * Name   : TestId
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-01-18
 *
 * Time   : 05:26
 */

@Retention ( RetentionPolicy.RUNTIME)
public @interface UseAsTestName
{
	int index() default 0;

	long id() default 0L;
}
