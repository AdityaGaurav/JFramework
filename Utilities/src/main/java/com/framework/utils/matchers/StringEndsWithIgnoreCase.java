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
 * Name   : StringEndsWithIgnoreCase 
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-03-01 
 *
 * Time   : 00:15
 *
 */

public class StringEndsWithIgnoreCase extends SubstringMatcher
{

	//region StringEndsWithIgnoreCase - Variables Declaration and Initialization Section.

	public StringEndsWithIgnoreCase( String substring )
	{
		super( substring );
	}

	//endregion

	/**
	 * Creates a matcher that matches if the examined {@link String} ends with the specified
	 * {@link String}.
	 * <p/>
	 * For example:
	 * <pre>assertThat("myStringOfNote", endsWith("Note"))</pre>
	 *
	 * @param suffix
	 *      the substring that the returned matcher will expect at the end of any examined string
	 */
	@Factory
	public static Matcher<String> endsWithIgnoreCase( String suffix )
	{
		return new StringEndsWithIgnoreCase( suffix );
	}

	@Override
	protected boolean evalSubstringOf( String s )
	{
		return StringUtils.endsWithIgnoreCase( s, substring );
	}

	@Override
	protected String relationship()
	{
		return "ending with ignore case";
	}
}
