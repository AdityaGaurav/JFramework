package com.framework.utils.matchers;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import java.util.regex.Pattern;


public class ContainsPattern extends TypeSafeMatcher<String>
{

	//region ContainsPattern - Variables Declaration and Initialization Section.

	private final Pattern pattern;

	private final boolean match;

	//endregion


	//region ContainsPattern - Constructor Methods Section

	public ContainsPattern( Pattern pattern, boolean match )
	{
		super( String.class );
		this.pattern = pattern;
		this.match = match;
	}

	public ContainsPattern( Pattern pattern )
	{
		this( pattern, false );
	}

	public ContainsPattern( String regex, boolean match )
	{
		this( Pattern.compile( regex ), match );
	}

	public ContainsPattern( String regex )
	{
		this( Pattern.compile( regex ) );
	}

	//endregion


	//region ContainsPattern - Public Methods Section

	@Override
	public void describeTo( Description description )
	{
		description.appendText( "a string " )
				.appendText( match ? "matching" : "containing" )
				.appendText( " pattern" )
				.appendValue( pattern.pattern() );
	}

	@Override
	protected boolean matchesSafely( String item )
	{
		if ( match )
		{
			if ( pattern.matcher( item ).matches() )
			{
				return true;
			}
		}
		else
		{
			if ( pattern.matcher( item ).find() )
			{
				return true;
			}
		}
		return false;
	}

	@Override
	protected void describeMismatchSafely( String item, Description mismatch )
	{
		mismatch.appendValue( item )
				.appendText( " did not " )
				.appendText( match ? "match" : "contain" )
				.appendText( " /" )
				.appendText( pattern.pattern() )
				.appendText( "/" );
	}

	/**
	 * Can the given pattern be found in the string?
	 *
	 * @param regex
	 *
	 * @return String-Matcher
	 */
	@Factory
	public static Matcher<String> containsPattern( String regex )
	{
		return new ContainsPattern( regex, false );
	}

	/**
	 * Can the given pattern be found in the string?
	 *
	 * @param pattern
	 *
	 * @return String-Matcher
	 */
	@Factory
	public static Matcher<String> containsPattern( Pattern pattern )
	{
		return new ContainsPattern( pattern, false );
	}

	/**
	 * Does the pattern match the entire string?
	 *
	 * @param regex
	 *
	 * @return String-Matcher
	 */
	@Factory
	public static Matcher<String> matchesPattern( String regex )
	{
		return new ContainsPattern( regex, true );
	}

	/**
	 * Does the pattern match the entire string?
	 *
	 * @param pattern
	 *
	 * @return String-Matcher
	 */
	@Factory
	public static Matcher<String> matchesPattern( Pattern pattern )
	{
		return new ContainsPattern( pattern, true );
	}

	//endregion

}
