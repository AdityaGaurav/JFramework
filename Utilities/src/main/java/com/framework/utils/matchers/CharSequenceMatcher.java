package com.framework.utils.matchers;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.utils.matchers
 *
 * Name   : CharSequenceMatcher 
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-03-01 
 *
 * Time   : 00:28
 *
 */

public abstract class CharSequenceMatcher extends TypeSafeMatcher<CharSequence>
{

	//region CharSequenceMatcher - Variables Declaration and Initialization Section.

	protected enum ValidationType{ LOWER, UPPER, ALPHA, ALPHANUMERIC, NUMERIC, NUMBER, DIGITS  }

	protected final CharSequence charSequence;

	protected final ValidationType vt;

	//endregion


	//region CharSequenceMatcher - Constructor Methods Section

	protected CharSequenceMatcher( final CharSequence cs, final ValidationType vt )
	{
		this.charSequence = cs;
		this.vt = vt;
	}

	//endregion

	@Override
	public boolean matchesSafely( CharSequence item )
	{
		return evalCharSequenceOf( item, vt );
	}

	@Override
	public void describeMismatchSafely( CharSequence item, Description mismatchDescription )
	{
		mismatchDescription.appendText( "was \"" ).appendText( item.toString() ).appendText( "\"" );
	}

	@Override
	public void describeTo( Description description )
	{
		description.appendText( "a char-sequence " )
				.appendText( relationship() )
				.appendText( " " )
				.appendValue( charSequence );
	}

	protected abstract boolean evalCharSequenceOf( CharSequence cs, ValidationType vt );

	protected abstract String relationship();
}
