/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.framework.driver.utils;

import com.framework.driver.exceptions.UnexpectedAttributeValueException;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.UnexpectedTagNameException;


/**
 * Static convenience methods that help a method or constructor check whether it was invoked
 * correctly (whether its <i>preconditions</i> have been met). These methods generally accept a
 * {@code boolean} expression which is expected to be {@code true} (or in the case of {@code
 * checkNotNull}, an object reference which is expected to be non-null). When {@code false} (or
 * {@code null}) is passed instead, the {@code Preconditions} method throws an unchecked exception,
 * which helps the calling method communicate to <i>its</i> caller that <i>that</i> caller has made a mistake.
 * Verifies correct argument values and state. Borrowed from Guava.
 */

public final class PreConditions
{
	//region PreConditions - Constructor Methods Section

	private PreConditions()
	{
		// util
	}

	//endregion


	//region PreConditions - Check Argument Methods Section


	/**
	 * Ensures the truth of an expression involving one or more parameters to the calling method.
	 *
	 * @param expression   a boolean expression
	 * @param errorMessage the exception message to use if the check fails; will be converted to a
	 *                     string using {@link String#valueOf(Object)}
	 *
	 * @throws IllegalArgumentException if {@code expression} is false
	 *
	 * @see com.google.common.base.Preconditions#checkArgument(boolean, Object)
	 */
	public static void checkArgument( boolean expression, Object errorMessage )
	{
		if ( ! expression )
		{
			throw new IllegalArgumentException( String.valueOf( errorMessage ) );
		}
	}

	/**
	 * Ensures the truth of an expression involving one or more parameters to the calling method.
	 *
	 * @param expression           a boolean expression
	 * @param errorMessageTemplate a template for the exception message should the check fail. The
	 *                             message is formed by replacing each {@code %s} placeholder in the template with an
	 *                             argument. These are matched by position - the first {@code %s} gets {@code
	 *                             errorMessageArgs[0]}, etc.  Unmatched arguments will be appended to the formatted message
	 *                             in square braces. Unmatched placeholders will be left as-is.
	 * @param errorMessageArgs     the arguments to be substituted into the message template. Arguments
	 *                             are converted to strings using {@link String#valueOf(Object)}.
	 *
	 * @throws IllegalArgumentException if {@code expression} is false
	 * @throws NullPointerException     if the check fails and either {@code errorMessageTemplate} or
	 *                                  {@code errorMessageArgs} is null (don't let this happen)
	 *
	 * @see com.google.common.base.Preconditions#checkArgument(boolean, String, Object...)
	 */
	public static void checkArgument( boolean expression, String errorMessageTemplate, Object... errorMessageArgs )
	{
		if ( ! expression )
		{
			throw new IllegalArgumentException( format( errorMessageTemplate, errorMessageArgs ) );
		}
	}


	//endregion


	//region PreConditions - Check State Methods Section

	/**
	 * Ensures the truth of an expression involving the state of the calling instance, but not
	 * involving any parameters to the calling method.
	 *
	 * @param expression   a boolean expression
	 * @param errorMessage the exception message to use if the check fails; will be converted to a
	 *                     string using {@link String#valueOf(Object)}
	 *
	 * @throws IllegalStateException if {@code expression} is false
	 *
	 * @see com.google.common.base.Preconditions#checkState(boolean, Object)
	 */
	public static void checkState( boolean expression, Object errorMessage )
	{
		if ( ! expression )
		{
			throw new IllegalStateException( String.valueOf( errorMessage ) );
		}
	}

	/**
	 * Ensures the truth of an expression involving the state of the calling instance, but not
	 * involving any parameters to the calling method.
	 *
	 * @param expression           a boolean expression
	 * @param errorMessageTemplate a template for the exception message should the check fail. The
	 *                             message is formed by replacing each {@code %s} placeholder in the template with an
	 *                             argument. These are matched by position - the first {@code %s} gets {@code
	 *                             errorMessageArgs[0]}, etc.  Unmatched arguments will be appended to the formatted message
	 *                             in square braces. Unmatched placeholders will be left as-is.
	 * @param errorMessageArgs     the arguments to be substituted into the message template. Arguments
	 *                             are converted to strings using {@link String#valueOf(Object)}.
	 *
	 * @throws IllegalStateException if {@code expression} is false
	 * @throws NullPointerException  if the check fails and either {@code errorMessageTemplate} or
	 *                               {@code errorMessageArgs} is null (don't let this happen)
	 *
	 * @see com.google.common.base.Preconditions#checkState(boolean, String, Object...)
	 */
	public static void checkState( boolean expression, String errorMessageTemplate, Object... errorMessageArgs )
	{
		if ( ! expression )
		{
			throw new IllegalStateException( format( errorMessageTemplate, errorMessageArgs ) );
		}
	}

	//todo: documentation
	public static void checkInstanceOf( final Class<?> type, final Object obj, String errorMessageTemplate, Object... errorMessageArgs)
	{
		if ( ! type.isInstance( obj ) )
		{
			throw new IllegalArgumentException( format( errorMessageTemplate, errorMessageArgs ) );
		}
	}

	//endregion


	//region PreConditions - Check Not Null Methods Section

	/**
	 * Ensures that an object reference passed as a parameter to the calling method is not null.
	 *
	 * @param reference    an object reference
	 * @param errorMessage the exception message to use if the check fails; will be converted to a
	 *                     string using {@link String#valueOf(Object)}
	 *
	 * @return the non-null reference that was validated
	 *
	 * @throws NullPointerException if {@code reference} is null
	 */
	public static <T> T checkNotNull( T reference, Object errorMessage )
	{
		if ( reference == null )
		{
			throw new NullPointerException( String.valueOf( errorMessage ) );
		}
		return reference;
	}

	/**
	 * Ensures that an object reference passed as a parameter to the calling method is not null.
	 *
	 * @param reference            an object reference
	 * @param errorMessageTemplate a template for the exception message should the check fail. The
	 *                             message is formed by replacing each {@code %s} placeholder in the template with an
	 *                             argument. These are matched by position - the first {@code %s} gets {@code
	 *                             errorMessageArgs[0]}, etc.  Unmatched arguments will be appended to the formatted message
	 *                             in square braces. Unmatched placeholders will be left as-is.
	 * @param errorMessageArgs     the arguments to be substituted into the message template. Arguments
	 *                             are converted to strings using {@link String#valueOf(Object)}.
	 *
	 * @return the non-null reference that was validated
	 *
	 * @throws NullPointerException if {@code reference} is null
	 */
	public static <T> T checkNotNull( T reference, String errorMessageTemplate, Object... errorMessageArgs )
	{
		if ( reference == null )
		{
			// If either of these parameters is null, the right thing happens anyway
			throw new NullPointerException( format( errorMessageTemplate, errorMessageArgs ) );
		}
		return reference;
	}

	//endregion


	//region PreConditions - Check Element Index Methods Section

	/**
	 * Ensures that {@code index} specifies a valid <i>element</i> in an array, list or string of size
	 * {@code size}. An element index may range from zero, inclusive, to {@code size}, exclusive.
	 *
	 * @param index a user-supplied index identifying an element of an array, list or string
	 * @param size  the size of that array, list or string
	 * @param desc  the text to use to describe this index in an error message
	 *
	 * @return the value of {@code index}
	 *
	 * @throws IndexOutOfBoundsException if {@code index} is negative or is not less than {@code size}
	 * @throws IllegalArgumentException  if {@code size} is negative
	 */
	public static int checkElementIndex( int index, int size, String desc )
	{
		// Carefully optimized for execution by hotspot (explanatory comment above)
		if ( index < 0 || index >= size )
		{
			throw new IndexOutOfBoundsException( badElementIndex( index, size, desc ) );
		}
		return index;
	}

	/**
	 * Ensures that {@code index} specifies a valid <i>position</i> in an array, list or string of
	 * size {@code size}. A position index may range from zero to {@code size}, inclusive.
	 *
	 * @param index a user-supplied index identifying a position in an array, list or string
	 * @param size  the size of that array, list or string
	 * @param desc  the text to use to describe this index in an error message
	 *
	 * @return the value of {@code index}
	 *
	 * @throws IndexOutOfBoundsException if {@code index} is negative or is greater than {@code size}
	 * @throws IllegalArgumentException  if {@code size} is negative
	 */
	public static int checkPositionIndex( int index, int size, String desc )
	{
		// Carefully optimized for execution by hotspot (explanatory comment above)
		if ( index < 0 || index > size )
		{
			throw new IndexOutOfBoundsException( badPositionIndex( index, size, desc ) );
		}
		return index;
	}

	/**
	 * Ensures that {@code startTest} and {@code endTest} specify a valid <i>positions</i> in an array, list
	 * or string of size {@code size}, and are in order. A position index may range from zero to
	 * {@code size}, inclusive.
	 *
	 * @param start a user-supplied index identifying a starting position in an array, list or string
	 * @param end   a user-supplied index identifying a ending position in an array, list or string
	 * @param size  the size of that array, list or string
	 *
	 * @throws IndexOutOfBoundsException if either index is negative or is greater than {@code size},
	 *                                   or if {@code endTest} is less than {@code startTest}
	 * @throws IllegalArgumentException  if {@code size} is negative
	 */
	public static void checkPositionIndexes( int start, int end, int size )
	{
		// Carefully optimized for execution by hotspot (explanatory comment above)
		if ( start < 0 || end < start || end > size )
		{
			throw new IndexOutOfBoundsException( badPositionIndexes( start, end, size ) );
		}
	}

	private static String badElementIndex( int index, int size, String desc )
	{
		if ( index < 0 )
		{
			return format( "%s (%s) must not be negative", desc, index );
		}
		else if ( size < 0 )
		{
			throw new IllegalArgumentException( "negative size: " + size );
		}
		else
		{ // index >= size
			return format( "%s (%s) must be less than size (%s)", desc, index, size );
		}
	}

	private static String badPositionIndex( int index, int size, String desc )
	{
		if ( index < 0 )
		{
			return format( "%s (%s) must not be negative", desc, index );
		}
		else if ( size < 0 )
		{
			throw new IllegalArgumentException( "negative size: " + size );
		}
		else
		{ // index > size
			return format( "%s (%s) must not be greater than size (%s)", desc, index, size );
		}
	}

	private static String badPositionIndexes( int start, int end, int size )
	{
		if ( start < 0 || start > size )
		{
			return badPositionIndex( start, size, "startTest index" );
		}
		if ( end < 0 || end > size )
		{
			return badPositionIndex( end, size, "endTest index" );
		}
		// endTest < startTest
		return format( "endTest index (%s) must not be less than startTest index (%s)", end, start );
	}

	//endregion


	//region PreConditions - Check Null, Empty or Blank Methods Section

	/**
	 * Verifies that the given {@code String} is not {@code null} or empty.
	 *
	 * @param s       the given {@code String}.
	 * @param message error message in case of empty {@code String}.
	 *
	 * @return the validated {@code String}.
	 *
	 * @throws NullPointerException     if the given {@code String} is {@code null}.
	 * @throws IllegalArgumentException if the given {@code String} is empty.
	 */
	public static String checkNotNullOrEmpty( String s, String message )
	{
		checkNotNull( s, message );
		if ( s.isEmpty() )
		{
			throw new IllegalArgumentException( message );
		}
		return s;
	}

	/**
	 * Verifies that the given {@code String} is not {@code null} or empty.
	 *
	 * @param s                    a String reference
	 * @param errorMessageTemplate a template for the exception message should the check fail. The
	 *                             message is formed by replacing each {@code %s} placeholder in the template with an
	 *                             argument. These are matched by position - the first {@code %s} gets {@code
	 *                             errorMessageArgs[0]}, etc.  Unmatched arguments will be appended to the formatted message
	 *                             in square braces. Unmatched placeholders will be left as-is.
	 * @param errorMessageArgs     the arguments to be substituted into the message template. Arguments
	 *                             are converted to strings using {@link String#valueOf(Object)}.
	 *
	 * @return the validated {@code String}.
	 *
	 * @throws NullPointerException     if the given {@code String} is {@code null}.
	 * @throws IllegalArgumentException if the given {@code String} is empty.
	 */
	public static String checkNotNullOrEmpty( String s, String errorMessageTemplate, Object... errorMessageArgs )
	{
		checkNotNull( s, format( errorMessageTemplate, errorMessageArgs ) );
		if ( s.isEmpty() )
		{
			throw new IllegalArgumentException( format( errorMessageTemplate, errorMessageArgs ) );
		}
		return s;
	}

	/**
	 * Verifies that the given {@code String} is not {@code null}, blank or empty.
	 *
	 * @param s       the given {@code String}.
	 * @param message error message in case of empty {@code String}.
	 *
	 * @return the validated {@code String}.
	 *
	 * @throws NullPointerException     if the given {@code String} is {@code null}.
	 * @throws IllegalArgumentException if the given {@code String} is empty.
	 */
	public static String checkNotNullNotBlankOrEmpty( String s, String message )
	{
		checkNotNull( s, message );
		if ( s.isEmpty() )
		{
			throw new IllegalArgumentException( message );
		}
		if ( StringUtils.isBlank( s ) )
		{
			throw new IllegalArgumentException( message );
		}

		return s;
	}

	/**
	 * Verifies that the given {@code String} is not {@code null}, blank or empty.
	 *
	 * @param s                    a String reference
	 * @param errorMessageTemplate a template for the exception message should the check fail. The
	 *                             message is formed by replacing each {@code %s} placeholder in the template with an
	 *                             argument. These are matched by position - the first {@code %s} gets {@code
	 *                             errorMessageArgs[0]}, etc.  Unmatched arguments will be appended to the formatted message
	 *                             in square braces. Unmatched placeholders will be left as-is.
	 * @param errorMessageArgs     the arguments to be substituted into the message template. Arguments
	 *                             are converted to strings using {@link String#valueOf(Object)}.
	 *
	 * @return the validated {@code String}.
	 *
	 * @throws NullPointerException     if the given {@code String} is {@code null}.
	 * @throws IllegalArgumentException if the given {@code String} is empty or blank.
	 */
	public static String checkNotNullNotBlankOrEmpty( String s, String errorMessageTemplate, Object... errorMessageArgs )
	{
		checkNotNull( s, format( errorMessageTemplate, errorMessageArgs ) );
		if ( s.isEmpty() )
		{
			throw new IllegalArgumentException( format( errorMessageTemplate, errorMessageArgs ) );
		}
		if ( StringUtils.isBlank( s ) )
		{
			throw new IllegalArgumentException( format( errorMessageTemplate, errorMessageArgs ) );
		}

		return s;
	}

	//endregion


	//region PreConditions - Check WebElement Methods Section

	public static WebElement checkElementTagName( final WebElement element, final String tagName )
	{
		String actual = element.getTagName();
		if ( null == actual || ! tagName.equals( actual.toLowerCase() ) )
		{
			throw new UnexpectedTagNameException( tagName, actual );
		}

		return element;
	}

	public static WebElement checkElementAttribute( final WebElement element, final String attributeName, final String attributeValue )
	{
		String actual = element.getAttribute( attributeName );
		if ( null == actual || ! actual.equals( attributeValue.toLowerCase() ) )
		{
			throw new UnexpectedAttributeValueException( attributeName, attributeValue, actual );
		}

		return element;
	}

	//endregion


	//region PreConditions - Private Function Section

	/**
	 * Substitutes each {@code %s} in {@code template} with an argument. These are matched by
	 * position: the first {@code %s} gets {@code args[0]}, etc.  If there are more arguments than
	 * placeholders, the unmatched arguments will be appended to the endTest of the formatted message in
	 * square braces.
	 *
	 * @param template a non-null string containing 0 or more {@code %s} placeholders.
	 * @param args     the arguments to be substituted into the message template. Arguments are converted
	 *                 to strings using {@link String#valueOf(Object)}. Arguments can be null.
	 */
	// Note that this is somewhat-improperly used from Verify.java as well.
	static String format( String template, Object... args )
	{
		template = String.valueOf( template ); // null -> "null"

		// startTest substituting the arguments into the '%s' placeholders
		StringBuilder builder = new StringBuilder( template.length() + 16 * args.length );
		int templateStart = 0;
		int i = 0;
		while ( i < args.length )
		{
			int placeholderStart = template.indexOf( "%s", templateStart );
			if ( placeholderStart == - 1 )
			{
				break;
			}
			builder.append( template.substring( templateStart, placeholderStart ) );
			builder.append( args[ i++ ] );
			templateStart = placeholderStart + 2;
		}
		builder.append( template.substring( templateStart ) );

		// if we run out of placeholders, append the extra args in square braces
		if ( i < args.length )
		{
			builder.append( " [" );
			builder.append( args[ i++ ] );
			while ( i < args.length )
			{
				builder.append( ", " );
				builder.append( args[ i++ ] );
			}
			builder.append( ']' );
		}

		return builder.toString();
	}

	//endregion

}
