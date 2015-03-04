package com.framework.testing.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;


@Documented
@Retention ( RetentionPolicy.RUNTIME )
@Target ( { METHOD, TYPE, CONSTRUCTOR } )
public @interface TestCaseId
{
 	long id();
}
