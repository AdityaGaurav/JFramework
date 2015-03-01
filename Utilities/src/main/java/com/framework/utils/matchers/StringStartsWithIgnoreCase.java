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
 * Name   : StartsWithIgnoreCase 
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-03-01 
 *
 * Time   : 00:10
 *
 */

public class StringStartsWithIgnoreCase extends SubstringMatcher
{

	//region StartsWithIgnoreCase - Variables Declaration and Initialization Section.

	public StringStartsWithIgnoreCase( String substring )
	{
		super( substring );
	}

	//endregion

	@Factory
	public static Matcher<String> startsWithIgnoreCase( String prefix )
	{
		return new StringStartsWithIgnoreCase( prefix );
	}

	@Override
	protected boolean evalSubstringOf( String s )
	{
		return StringUtils.startsWithIgnoreCase( s, substring );
	}

	@Override
	protected String relationship()
	{
		return "starting with ignore case";
	}

}
