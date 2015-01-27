package com.framework.testing.annotations;

import java.lang.annotation.*;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.testing.annotations
 *
 * Name   : TestInfo
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-01-16
 *
 * Time   : 15:52
 */

@Documented
@Retention ( RetentionPolicy.RUNTIME )
@Target ( { ElementType.METHOD, ElementType.TYPE } )
public @interface Steps
{
	Step[] steps() default {};
}
