package com.framework.utils.matchers;

import org.apache.commons.lang3.StringUtils;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.core.SubstringMatcher;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.utils.matchers
 *
 * Name   : StringContainsIgnoreCase 
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-03-01 
 *
 * Time   : 00:19
 *
 */

public class StringContainsIgnoreCase extends SubstringMatcher
{

	//region StringContainsIgnoreCase - Variables Declaration and Initialization Section.

	public StringContainsIgnoreCase( String substring )
	{
		super( substring );
	}

	//endregion

	/**
	 * Creates a matcher that matches if the examined {@link String} contains the specified
	 * {@link String} anywhere.
	 * <p/>
	 * For example:
	 * <pre>assertThat("myStringOfNote", containsString("ring"))</pre>
	 *
	 * @param substring
	 *     the substring that the returned matcher will expect to find within any examined string
	 *
	 */
	@Factory
	public static Matcher<String> containsStringIgnoreCase( String substring )
	{
		return new StringContainsIgnoreCase( substring );
	}

	@Override
	protected boolean evalSubstringOf( String s )
	{
		return StringUtils.containsIgnoreCase( s, substring );
	}

	@Override
	protected String relationship()
	{
		return "containing ignore case";
	}

}
