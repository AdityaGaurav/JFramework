package com.framework.testing.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * Mark a test case with a code identifying a user story.
 *
 */
@Retention ( RetentionPolicy.RUNTIME)
@Target ({ java.lang.annotation.ElementType.METHOD })
public @interface UserStory
{
	String value();
}