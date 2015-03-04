package com.framework.testing.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * possible values
 * <ol>
 *     <li>endsWith</li>
 *     <li>containsPattern</li>
 *     <li>matchedPattern</li>
 *     <lo>startswith</lo>
 *     <li>contains</li>
 *     <li>containsString</li>
 *     <li>equalToIgnoringCase</li>
 *     <lil>equalToIgnoringWhiteSpace</lil>
 * </ol>
 */

@Retention ( RetentionPolicy.RUNTIME )
@Target ( ElementType.TYPE )
public @interface DefaultUrl
{
	String matcher() default "contains()";

	String value();
}
