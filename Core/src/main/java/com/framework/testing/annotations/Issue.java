package com.framework.testing.annotations;

import java.lang.annotation.*;


/**
 * Used to indicate that a test case or test relates to a particular issue or story card in the issue tracking system.
 *
 */

@Documented
@Retention ( RetentionPolicy.RUNTIME )
@Target ( { ElementType.METHOD, ElementType.TYPE } )
public @interface Issue
{
	String value();

	public String getIssueTrackerUrl() default "";
}
