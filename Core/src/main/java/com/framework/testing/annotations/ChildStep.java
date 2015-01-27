package com.framework.testing.annotations;

import java.lang.annotation.*;


/**
 * Marks a class that implements test steps as individual methods.
 * Each method that represents a test step should be marked by the Step annotation.
 */

@Documented
@Retention ( RetentionPolicy.RUNTIME )
//@Target ( { ElementType.PARAMETER, ElementType.CONSTRUCTOR, ElementType.LOCAL_VARIABLE, ElementType.ANNOTATION_TYPE  } )
@Target ( { ElementType.TYPE, ElementType.ANNOTATION_TYPE } )
public @interface ChildStep
{
	float number();

	String description() default "";

	String[] expectedResults() default {};
}
