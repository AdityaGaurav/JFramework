package com.framework.utils.matchers;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.utils.matchers
 *
 * Name   : IsAllUpperCase 
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-03-01 
 *
 * Time   : 00:25
 *
 */

public class CharSequenceIsMatcher extends CharSequenceMatcher
{
	//region CharSequenceIsMatcher - Constructor

	public CharSequenceIsMatcher( CharSequence cs, ValidationType vt )
	{
		super( cs, vt );
	}

	//endregion


	@Factory
	public static Matcher<CharSequence> isAllUpperCase( CharSequence cs )
	{
		return new CharSequenceIsMatcher( cs, ValidationType.UPPER );
	}

	@Factory
	public static Matcher<CharSequence> isAllLowerCase( CharSequence cs )
	{
		return new CharSequenceIsMatcher( cs, ValidationType.LOWER );
	}

	@Factory
	public static Matcher<CharSequence> isAlpha( CharSequence cs )
	{
		return new CharSequenceIsMatcher( cs, ValidationType.ALPHA );
	}

	@Factory
	public static Matcher<CharSequence> isAlphaNumeric( CharSequence cs )
	{
		return new CharSequenceIsMatcher( cs, ValidationType.ALPHANUMERIC );
	}

	@Factory
	public static Matcher<CharSequence> isNumber( CharSequence cs )
	{
		return new CharSequenceIsMatcher( cs, ValidationType.NUMBER );
	}

	@Factory
	public static Matcher<CharSequence> isNumeric( CharSequence cs )
	{
		return new CharSequenceIsMatcher( cs, ValidationType.NUMERIC );
	}

	@Factory
	public static Matcher<CharSequence> isDigits( CharSequence cs )
	{
		return new CharSequenceIsMatcher( cs, ValidationType.DIGITS );
	}

	@Override
	protected boolean evalCharSequenceOf( CharSequence cs, ValidationType vt )
	{
		switch ( vt )
		{
			case LOWER: return StringUtils.isAllLowerCase( cs );
			case UPPER: return StringUtils.isAllUpperCase( cs );
			case ALPHA: return StringUtils.isAlpha( cs );
			case ALPHANUMERIC: return StringUtils.isAlphanumeric( cs );
			case NUMERIC: return StringUtils.isNumeric( cs );
			case NUMBER: return NumberUtils.isNumber( cs.toString() );
			case DIGITS: return NumberUtils.isDigits( cs.toString() );
		}

		return false;
	}

	@Override
	protected String relationship()
	{
		return "is all uppercase";
	}
}
