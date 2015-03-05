package com.framework.utils.matchers;

import org.hamcrest.core.DescribedAs;

import java.util.regex.Pattern;


/**
 * Examples for matching collections:
 * <dl>
 *     <dt>check if single element is in a collection:</dt>
 *     <dd>
 *         <pre>
 *             List<String> collection = Lists.newArrayList("ab", "cd", "ef");
 *             assertThat(collection, hasItem("cd"));
 *             assertThat(collection, not(hasItem("zz")));
 *         </pre>
 *     </dd>
 *     <dt>check if multiple elements are in a collection</dt>
 *     <dd>
 *         <pre>
 *             List<String> collection = Lists.newArrayList("ab", "cd", "ef");
 *             assertThat(collection, hasItems("cd", "ef"));
 *         </pre>
 *     </dd>
 *     <dt>check if multiple elements are in a collection with strict order:</dt>
 *     <dd>
 *          <pre>
 *             List<String> collection = Lists.newArrayList("ab", "cd", "ef");
 *             assertThat(collection, contains("ab", "cd", "ef"));
 *         </pre>
 *     </dd>
 *     <dt>check if multiple elements are in a collection with any order</ft>
 *     <dd>
 *       	<pre>
 *          	List<String> collection = Lists.newArrayList("ab", "cd", "ef");
 *           	assertThat(collection, containsInAnyOrder("cd", "ab", "ef"));
 *       	</pre>
 *     </dd>
 *     <dt>check if collection is empty</dt>
 *     <dd>
 *           <pre>
 *               List<String> collection = Lists.newArrayList();
 *               assertThat(collection, empty());
 *           </pre>
 *     </dd>
 *     <dt>check if array is empty</dt>
 *     <dd>
 *         <pre>
 *             String[] array = new String[] { "ab" };
 *             assertThat(array, not(emptyArray()));
 *         </pre>
 *     </dd>
 *     <dt>check if Map is empty</dt>
 *     <dd>
 *         <pre>
 *             Map<String, String> collection = Maps.newHashMap();
 *             assertThat(collection, equalTo(Collections.EMPTY_MAP));
 *         </pre>
 *     </dd>
 *     <dt>check size of a collection</dt>
 *     <dd>
 *         <pre>
 *             List<String> collection = Lists.newArrayList("ab", "cd", "ef");
 *             assertThat(collection, hasSize(3));
 *         </pre>
 *     </dd>
 *     <dt>checking size of an iterable</dt>
 *     <dd>
 *        <pre>
 *           Iterable<String> collection = Lists.newArrayList("ab", "cd", "ef");
 *           assertThat(collection, Matchers.<String> iterableWithSize(3));
 *        </pre>
 *     </dd>
 *     </dt>check condition on every item</dt>
 *     <dd>
 *         <pre>
 *            List<Integer> collection = Lists.newArrayList(15, 20, 25, 30);
 *            assertThat(collection, everyItem(greaterThan(10)));
 *         </pre>
 *     </dd>
 * </dl>
 */

public final class JMatchers extends org.hamcrest.Matchers
{

	private JMatchers()
	{
		// utility
	}


	/**
	 * Can the given pattern be found in the string?
	 *
	 * @param pattern a {@link java.util.regex.Pattern} object.
	 */
	public static org.hamcrest.Matcher<String> containsPattern( java.util.regex.Pattern pattern )
	{
		return ContainsPattern.containsPattern( pattern );
	}

	/**
	 * Can the given pattern be found in the string?
	 *
	 * @param regex a regular expression
	 *
	 * @return String-Matcher
	 */
	public static org.hamcrest.Matcher<String> containsPattern( String regex )
	{
		return ContainsPattern.containsPattern( regex );
	}

	/**
	 * Does the pattern match the entire string?
	 *
	 * @param pattern a {@link java.util.regex.Pattern} object.
	 *
	 * @return String-Matcher
	 */
	public static org.hamcrest.Matcher<String> matchesPattern( Pattern pattern )
	{
		return ContainsPattern.matchesPattern( pattern );
	}

	/**
	 * Does the pattern match the entire string?
	 *
	 * @param regex  a regular expression
	 *
	 * @return String-Matcher
	 */
	public static org.hamcrest.Matcher<String> matchesPattern( String regex )
	{
		return ContainsPattern.matchesPattern( regex );
	}

	public static <T extends java.lang.Comparable<T>> org.hamcrest.Matcher<T> isExplicitlyBetween( T value1, T value2 )
	{
		String format = "a value is between %0 and %1";
		if ( value1.compareTo( value2 ) > 0 )
		{
			return DescribedAs.describedAs(
					format,
					org.hamcrest.Matchers.allOf( org.hamcrest.Matchers.lessThan( value1 ), org.hamcrest.Matchers.greaterThan( value2 ) ),
					value1, value2 );
		}
		return DescribedAs.describedAs(
				format,
				org.hamcrest.Matchers.allOf( org.hamcrest.Matchers.lessThan( value2 ), org.hamcrest.Matchers.greaterThan( value1 ) ),
				value2, value1 );
	}

	public static org.hamcrest.Matcher<java.lang.String> startsWithIgnoreCase( java.lang.String prefix )
	{
		return StringStartsWithIgnoreCase.startsWithIgnoreCase( prefix );
	}

	public static org.hamcrest.Matcher<java.lang.String> endsWithIgnoreCase( java.lang.String prefix )
	{
		return StringEndsWithIgnoreCase.endsWithIgnoreCase( prefix );
	}

	public static org.hamcrest.Matcher<java.lang.String> containsStringIgnoreCase( java.lang.String prefix )
	{
		return StringContainsIgnoreCase.containsStringIgnoreCase( prefix );
	}

	public static org.hamcrest.Matcher<CharSequence> isAllUpperCase( java.lang.CharSequence cs )
	{
		return CharSequenceIsMatcher.isAllUpperCase( cs );
	}

	public static org.hamcrest.Matcher<CharSequence> isAllLowerCase( java.lang.CharSequence cs )
	{
		return CharSequenceIsMatcher.isAllLowerCase( cs );
	}

	public static org.hamcrest.Matcher<CharSequence> isAlpha( java.lang.CharSequence cs )
	{
		return CharSequenceIsMatcher.isAlpha( cs );
	}

	public static org.hamcrest.Matcher<CharSequence> isAlphaNumeric( java.lang.CharSequence cs )
	{
		return CharSequenceIsMatcher.isAlphaNumeric( cs );
	}

	public static org.hamcrest.Matcher<CharSequence> isDigits( java.lang.CharSequence cs )
	{
		return CharSequenceIsMatcher.isDigits( cs );
	}

	public static org.hamcrest.Matcher<CharSequence> isNumeric( java.lang.CharSequence cs )
	{
		return CharSequenceIsMatcher.isNumeric( cs );
	}

	public static org.hamcrest.Matcher<CharSequence> isNumber( java.lang.CharSequence cs )
	{
		return CharSequenceIsMatcher.isNumber( cs );
	}
}
