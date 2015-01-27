package com.framework.testing.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * Used to associate a test with a particular requirement or issue.
 */
@Retention ( RetentionPolicy.RUNTIME )
@Target ( { ElementType.METHOD } )
public @interface TestRequirement
{
	String value();
}
