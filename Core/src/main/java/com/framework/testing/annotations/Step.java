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
public @interface Step
{
	float number();

	/**
	 * @return  The test case step description.
	 */
	String description() default "";

	/**
	 * @return  an expected result description list ( will be transformed into checkpoints )
	 */
	String[] expectedResults() default {};
}
