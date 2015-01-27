package com.framework.testing.annotations;

import java.lang.annotation.*;



@Documented
@Retention ( RetentionPolicy.RUNTIME )
@Target ( { ElementType.METHOD } )
public @interface TestCaseId
{
 	long id();
}