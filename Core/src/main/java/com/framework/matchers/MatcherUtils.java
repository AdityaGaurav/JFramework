package com.framework.matchers;

import org.hamcrest.Matchers;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.matchers
 *
 * Name   : MatcherUtils
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-01-05
 *
 * Time   : 20:38
 */

public class MatcherUtils extends Matchers
{

	public static org.cthul.matchers.hamcrest.HasDescription message( String string )
	{
		return org.cthul.matchers.hamcrest.HasDescription.message( string );
	}

	public static org.cthul.matchers.hamcrest.HasDescription message( org.hamcrest.Matcher<? super String> stringMatcher )
	{
		return org.cthul.matchers.hamcrest.HasDescription.message( stringMatcher );
	}

	public static org.cthul.matchers.hamcrest.HasDescription description( org.hamcrest.Matcher<? super String> stringMatcher )
	{
		return org.cthul.matchers.hamcrest.HasDescription.description( stringMatcher );
	}

	public static org.cthul.matchers.hamcrest.HasDescription description( String string )
	{
		return org.cthul.matchers.hamcrest.HasDescription.description( string );
	}

	public static <T> org.cthul.matchers.hamcrest.HasMatchResult<T> matchResult( T value, org.hamcrest.Matcher<? super org.cthul.matchers.diagnose.result
			.MatchResult<T>> resultMatcher )
	{
		return org.cthul.matchers.hamcrest.HasMatchResult.<T>matchResult( value, resultMatcher );
	}

	public static <T> org.cthul.matchers.hamcrest.IsMatchResult<T> match( org.hamcrest.Matcher<? super org.cthul.matchers.diagnose.result.MatchResult<T>> resultMatcher )
	{
		return org.cthul.matchers.hamcrest.IsMatchResult.<T>match( resultMatcher );
	}

	public static <T> org.cthul.matchers.hamcrest.IsMatchResult<T> match( String message )
	{
		return org.cthul.matchers.hamcrest.IsMatchResult.<T>match( message );
	}

	public static <T> org.cthul.matchers.hamcrest.IsMatchResult<T> match()
	{
		return org.cthul.matchers.hamcrest.IsMatchResult.<T>match();
	}

	public static <T> org.cthul.matchers.hamcrest.IsMatchResult<T> mismatchWithMessage( org.hamcrest.Matcher<? super String> resultMatcher )
	{
		return org.cthul.matchers.hamcrest.IsMatchResult.<T>mismatchWithMessage( resultMatcher );
	}

	public static <T> org.cthul.matchers.hamcrest.IsMatchResult<T> mismatchWithMessage( String message )
	{
		return org.cthul.matchers.hamcrest.IsMatchResult.<T>mismatchWithMessage( message );
	}

	public static <T> org.cthul.matchers.hamcrest.IsMatchResult<T> matchWithMessage( org.hamcrest.Matcher<? super String> resultMatcher )
	{
		return org.cthul.matchers.hamcrest.IsMatchResult.<T>matchWithMessage( resultMatcher );
	}

	public static <T> org.cthul.matchers.hamcrest.IsMatchResult<T> matchWithMessage( String message )
	{
		return org.cthul.matchers.hamcrest.IsMatchResult.<T>matchWithMessage( message );
	}

	public static <T> org.cthul.matchers.hamcrest.IsMatchResult<T> mismatch( String message )
	{
		return org.cthul.matchers.hamcrest.IsMatchResult.<T>mismatch( message );
	}

	public static <T> org.cthul.matchers.hamcrest.IsMatchResult<T> mismatch()
	{
		return org.cthul.matchers.hamcrest.IsMatchResult.<T>mismatch();
	}

	public static <T> org.cthul.matchers.hamcrest.IsMatchResult<T> mismatch( org.hamcrest.Matcher<? super org.cthul.matchers.diagnose.result.MatchResult<T>>
																					 resultMatcher )
	{
		return org.cthul.matchers.hamcrest.IsMatchResult.<T>mismatch( resultMatcher );
	}

	public static <T> org.cthul.matchers.hamcrest.IsMatchResult<T> expected( String message )
	{
		return org.cthul.matchers.hamcrest.MatchResultExpected.<T>expected( message );
	}

	public static <T> org.cthul.matchers.hamcrest.IsMatchResult<T> expected( org.hamcrest.Matcher<? super String> messageMatcher )
	{
		return org.cthul.matchers.hamcrest.MatchResultExpected.<T>expected( messageMatcher );
	}

	public static org.cthul.matchers.hamcrest.MatchResultExpected expectedDescription( org.hamcrest.Matcher<? super org.hamcrest.SelfDescribing> messageMatcher )
	{
		return org.cthul.matchers.hamcrest.MatchResultExpected.expectedDescription( messageMatcher );
	}

	public static org.cthul.matchers.hamcrest.MatchResultExpected expectedMessage( String message )
	{
		return org.cthul.matchers.hamcrest.MatchResultExpected.expectedMessage( message );
	}

	public static org.cthul.matchers.hamcrest.MatchResultExpected expectedMessage( org.hamcrest.Matcher<? super String> messageMatcher )
	{
		return org.cthul.matchers.hamcrest.MatchResultExpected.expectedMessage( messageMatcher );
	}

	public static <T> org.hamcrest.Matcher<org.hamcrest.Matcher<? super T>> acceptsWithMessage( T value, org.hamcrest.Matcher<? super String> messageMatcher )
	{
		return org.cthul.matchers.hamcrest.MatcherAccepts.<T>acceptsWithMessage( value, messageMatcher );
	}

	public static <T> org.hamcrest.Matcher<org.hamcrest.Matcher<? super T>> acceptsWithMessage( T value, String messageMatcher )
	{
		return org.cthul.matchers.hamcrest.MatcherAccepts.<T>acceptsWithMessage( value, messageMatcher );
	}

	public static <T> org.hamcrest.Matcher<org.hamcrest.Matcher<? super T>> rejectsWithMessage( T value, org.hamcrest.Matcher<? super String> messageMatcher )
	{
		return org.cthul.matchers.hamcrest.MatcherAccepts.<T>rejectsWithMessage( value, messageMatcher );
	}

	public static <T> org.hamcrest.Matcher<org.hamcrest.Matcher<? super T>> rejectsWithMessage( T value, String messageMatcher )
	{
		return org.cthul.matchers.hamcrest.MatcherAccepts.<T>rejectsWithMessage( value, messageMatcher );
	}

	public static <T> org.hamcrest.Matcher<org.hamcrest.Matcher<? super T>> rejectsWithExpectedMessage( T value, org.hamcrest.Matcher<? super String>
			messageMatcher )
	{
		return org.cthul.matchers.hamcrest.MatcherAccepts.<T>rejectsWithExpectedMessage( value, messageMatcher );
	}

	public static <T> org.hamcrest.Matcher<org.hamcrest.Matcher<? super T>> rejectsWithExpectedMessage( T value, String messageMatcher )
	{
		return org.cthul.matchers.hamcrest.MatcherAccepts.<T>rejectsWithExpectedMessage( value, messageMatcher );
	}

	public static <T> org.hamcrest.Matcher<org.hamcrest.Matcher<? super T>> accepts( T value, String messageMatcher )
	{
		return org.cthul.matchers.hamcrest.MatcherAccepts.<T>accepts( value, messageMatcher );
	}

	public static <T> org.hamcrest.Matcher<org.hamcrest.Matcher<? super T>> accepts( T value )
	{
		return org.cthul.matchers.hamcrest.MatcherAccepts.<T>accepts( value );
	}

	public static <T> org.hamcrest.Matcher<org.hamcrest.Matcher<? super T>> accepts( T value, org.hamcrest.Matcher<? super org.cthul.matchers.diagnose.result
			.MatchResult<T>> resultMatcher )
	{
		return org.cthul.matchers.hamcrest.MatcherAccepts.<T>accepts( value, resultMatcher );
	}

	public static <T> org.hamcrest.Matcher<org.hamcrest.Matcher<? super T>> rejects( T value, String messageMatcher )
	{
		return org.cthul.matchers.hamcrest.MatcherAccepts.<T>rejects( value, messageMatcher );
	}

	public static <T> org.hamcrest.Matcher<org.hamcrest.Matcher<? super T>> rejects( T value )
	{
		return org.cthul.matchers.hamcrest.MatcherAccepts.<T>rejects( value );
	}

	public static <T> org.hamcrest.Matcher<org.hamcrest.Matcher<? super T>> rejects( T value, org.hamcrest.Matcher<? super org.cthul.matchers.diagnose.result
			.MatchResult<T>> resultMatcher )
	{
		return org.cthul.matchers.hamcrest.MatcherAccepts.<T>rejects( value, resultMatcher );
	}

	public static <T> org.hamcrest.Matcher<org.hamcrest.Matcher<? super T>> expects( T value, String messageMatcher )
	{
		return org.cthul.matchers.hamcrest.MatcherAccepts.<T>expects( value, messageMatcher );
	}

//	public static <T> org.cthul.matchers.object.CIs<T> is( org.hamcrest.Matcher<? super T> matcher )
//	{
//		return org.cthul.matchers.object.CIs.<T>is( matcher );
//	}

//	public static <T> org.cthul.matchers.object.CIs<T> not( org.hamcrest.Matcher<? super T> matcher )
//	{
//		return org.cthul.matchers.object.CIs.<T>not( matcher );
//	}

	public static <T> org.cthul.matchers.object.CIs<T> _is( org.hamcrest.Matcher<? super T> matcher )
	{
		return org.cthul.matchers.object.CIs.<T>_is( matcher );
	}

	public static <T> org.cthul.matchers.object.CIs<T> has( org.hamcrest.Matcher<? super T> matcher )
	{
		return org.cthul.matchers.object.CIs.<T>has( matcher );
	}

	public static <T> org.cthul.matchers.object.CIs<T> _isNot( org.hamcrest.Matcher<? super T> matcher )
	{
		return org.cthul.matchers.object.CIs.<T>_isNot( matcher );
	}

	public static <T> org.cthul.matchers.object.CIs<T> _not( org.hamcrest.Matcher<? super T> matcher )
	{
		return org.cthul.matchers.object.CIs.<T>_not( matcher );
	}

	public static <T> org.cthul.matchers.object.CIs<T> isNot( org.hamcrest.Matcher<? super T> matcher )
	{
		return org.cthul.matchers.object.CIs.<T>isNot( matcher );
	}

	public static <T> org.cthul.matchers.object.CIs<T> _has( org.hamcrest.Matcher<? super T> matcher )
	{
		return org.cthul.matchers.object.CIs.<T>_has( matcher );
	}

	/**
	 * Can the given pattern be found in the string?
	 */
	public static org.hamcrest.Matcher<CharSequence> containsPattern( java.util.regex.Pattern p )
	{
		return org.cthul.matchers.object.ContainsPattern.containsPattern( p );
	}

	/**
	 * Can the given pattern be found in the string?
	 *
	 * @param regex
	 *
	 * @return String-Matcher
	 */
	public static org.hamcrest.Matcher<CharSequence> containsPattern( String regex )
	{
		return org.cthul.matchers.object.ContainsPattern.containsPattern( regex );
	}

	/**
	 * Does the pattern match the entire string?
	 *
	 * @param p
	 *
	 * @return String-Matcher
	 */
	public static org.hamcrest.Matcher<CharSequence> matchesPattern( java.util.regex.Pattern p )
	{
		return org.cthul.matchers.object.ContainsPattern.matchesPattern( p );
	}

	/**
	 * Does the pattern match the entire string?
	 *
	 * @param regex
	 *
	 * @return String-Matcher
	 */
	public static org.hamcrest.Matcher<CharSequence> matchesPattern( String regex )
	{
		return org.cthul.matchers.object.ContainsPattern.matchesPattern( regex );
	}

	public static <T, T2> org.cthul.matchers.chain.AndChainMatcher.Builder<T> isInstanceThat( Class<T2> clazz, org.hamcrest.Matcher<? super T2> m )
	{
		return org.cthul.matchers.object.InstanceThat.<T, T2>isInstanceThat( clazz, m );
	}

	public static <T, T2> org.hamcrest.Matcher<T> isInstanceThat( Class<T2> clazz, org.hamcrest.Matcher<? super T2>... m )
	{
		return org.cthul.matchers.object.InstanceThat.<T, T2>isInstanceThat( clazz, m );
	}

	public static <T, T2> org.cthul.matchers.chain.AndChainMatcher.Builder<T> instanceThat( Class<T2> clazz, org.hamcrest.Matcher<? super T2> m )
	{
		return org.cthul.matchers.object.InstanceThat.<T, T2>instanceThat( clazz, m );
	}

	public static <T, T2> org.cthul.matchers.chain.AndChainMatcher.Builder<T> instanceThat( Class<T2> clazz, org.hamcrest.Matcher<? super T2>... m )
	{
		return org.cthul.matchers.object.InstanceThat.<T, T2>instanceThat( clazz, m );
	}

	public static <T> org.cthul.matchers.object.InstanceOf<T> a( Class<T> clazz )
	{
		return org.cthul.matchers.object.InstanceOf.<T>a( clazz );
	}

//	public static <T> org.cthul.matchers.object.InstanceOf<T> instanceOf( java.lang.Class<T> clazz )
//	{
//		return org.cthul.matchers.object.InstanceOf.<T>instanceOf( clazz );
//	}
//
//	public static <T> org.cthul.matchers.object.InstanceOf<T> isA( java.lang.Class<T> clazz )
//	{
//		return org.cthul.matchers.object.InstanceOf.<T>isA( clazz );
//	}

	public static <T> org.cthul.matchers.object.InstanceOf<T> _isA( Class<T> clazz )
	{
		return org.cthul.matchers.object.InstanceOf.<T>_isA( clazz );
	}

	public static <T> org.cthul.matchers.object.InstanceOf<T> _instanceOf( Class<T> clazz )
	{
		return org.cthul.matchers.object.InstanceOf.<T>_instanceOf( clazz );
	}

	public static <T> org.cthul.matchers.object.InstanceOf<T> isInstanceOf( Class<T> clazz )
	{
		return org.cthul.matchers.object.InstanceOf.<T>isInstanceOf( clazz );
	}

	public static <T> org.cthul.matchers.chain.AndChainMatcher.Builder<T> all( org.hamcrest.Matcher<? super T> m )
	{
		return org.cthul.matchers.chain.AndChainMatcher.<T>all( m );
	}

	public static <T> org.cthul.matchers.chain.AndChainMatcher.Builder<T> all( org.hamcrest.Matcher<? super T>... m )
	{
		return org.cthul.matchers.chain.AndChainMatcher.<T>all( m );
	}

	public static org.cthul.matchers.chain.ChainFactory all()
	{
		return org.cthul.matchers.chain.AndChainMatcher.all();
	}

	public static <T> org.hamcrest.Matcher<T> and( org.hamcrest.Matcher<? super T>... matchers )
	{
		return org.cthul.matchers.chain.AndChainMatcher.<T>and( matchers );
	}

	public static <T> org.hamcrest.Matcher<T> and( java.util.Collection<? extends org.hamcrest.Matcher<? super T>> matchers )
	{
		return org.cthul.matchers.chain.AndChainMatcher.<T>and( matchers );
	}

//	public static <T> org.cthul.matchers.chain.AndChainMatcher.Builder<T> both( org.hamcrest.Matcher<? super T> m )
//	{
//		return org.cthul.matchers.chain.AndChainMatcher.<T>both( m );
//	}

	public static <T> org.cthul.matchers.chain.AndChainMatcher.Builder<T> both( org.hamcrest.Matcher<? super T>... m )
	{
		return org.cthul.matchers.chain.AndChainMatcher.<T>both( m );
	}

	public static org.cthul.matchers.chain.ChainFactory none()
	{
		return org.cthul.matchers.chain.NOrChainMatcher.none();
	}

	public static <T> org.hamcrest.Matcher<T> none( org.hamcrest.Matcher<? super T>... matchers )
	{
		return org.cthul.matchers.chain.NOrChainMatcher.<T>none( matchers );
	}

	public static <T> org.hamcrest.Matcher<T> none( java.util.Collection<? extends org.hamcrest.Matcher<? super T>> matchers )
	{
		return org.cthul.matchers.chain.NOrChainMatcher.<T>none( matchers );
	}

	public static <T> org.hamcrest.Matcher<T> nor( org.hamcrest.Matcher<? super T>... matchers )
	{
		return org.cthul.matchers.chain.NOrChainMatcher.<T>nor( matchers );
	}

	public static <T> org.hamcrest.Matcher<T> nor( java.util.Collection<? extends org.hamcrest.Matcher<? super T>> matchers )
	{
		return org.cthul.matchers.chain.NOrChainMatcher.<T>nor( matchers );
	}

	public static <T> org.cthul.matchers.chain.NOrChainMatcher.Builder<T> neither( org.hamcrest.Matcher<? super T>... m )
	{
		return org.cthul.matchers.chain.NOrChainMatcher.<T>neither( m );
	}

	public static <T> org.cthul.matchers.chain.NOrChainMatcher.Builder<T> neither( org.hamcrest.Matcher<? super T> m )
	{
		return org.cthul.matchers.chain.NOrChainMatcher.<T>neither( m );
	}

	public static <T> org.hamcrest.Matcher<T> or( org.hamcrest.Matcher<? super T>... matchers )
	{
		return org.cthul.matchers.chain.OrChainMatcher.<T>or( matchers );
	}

	public static <T> org.hamcrest.Matcher<T> or( java.util.Collection<? extends org.hamcrest.Matcher<? super T>> matchers )
	{
		return org.cthul.matchers.chain.OrChainMatcher.<T>or( matchers );
	}

	public static <T> org.hamcrest.Matcher<T> any( org.hamcrest.Matcher<? super T>... matchers )
	{
		return org.cthul.matchers.chain.OrChainMatcher.<T>any( matchers );
	}

	public static org.cthul.matchers.chain.ChainFactory any()
	{
		return org.cthul.matchers.chain.OrChainMatcher.any();
	}

	public static <T> org.hamcrest.Matcher<T> any( java.util.Collection<? extends org.hamcrest.Matcher<? super T>> matchers )
	{
		return org.cthul.matchers.chain.OrChainMatcher.<T>any( matchers );
	}

	public static <T> org.cthul.matchers.chain.OrChainMatcher.Builder<T> either( org.hamcrest.Matcher<? super T>... m )
	{
		return org.cthul.matchers.chain.OrChainMatcher.<T>either( m );
	}

//	public static <T> org.cthul.matchers.chain.OrChainMatcher.Builder<T> either( org.hamcrest.Matcher<? super T> m )
//	{
//		return org.cthul.matchers.chain.OrChainMatcher.<T>either( m );
//	}

	public static org.cthul.matchers.chain.SomeOfChainMatcher.SomeOfChainFactory matches( org.hamcrest.Matcher<? super Integer> countMatcher )
	{
		return org.cthul.matchers.chain.SomeOfChainMatcher.matches( countMatcher );
	}

	public static org.cthul.matchers.chain.SomeOfChainMatcher.SomeOfChainFactory matches( int count )
	{
		return org.cthul.matchers.chain.SomeOfChainMatcher.matches( count );
	}

	public static <T> org.cthul.matchers.chain.SomeOfChainMatcher.Builder<T> matches( int count, org.hamcrest.Matcher<? super T>... matchers )
	{
		return org.cthul.matchers.chain.SomeOfChainMatcher.<T>matches( count, matchers );
	}

	public static <T> org.cthul.matchers.chain.SomeOfChainMatcher.Builder<T> matches( org.hamcrest.Matcher<? super Integer> countMatcher,
																					  org.hamcrest.Matcher<? super T>... matchers )
	{
		return org.cthul.matchers.chain.SomeOfChainMatcher.<T>matches( countMatcher, matchers );
	}

	public static <T> org.hamcrest.Matcher<T> xor( java.util.Collection<? extends org.hamcrest.Matcher<? super T>> matchers )
	{
		return org.cthul.matchers.chain.XOrChainMatcher.<T>xor( matchers );
	}

	public static <T> org.hamcrest.Matcher<T> xor( org.hamcrest.Matcher<? super T>... matchers )
	{
		return org.cthul.matchers.chain.XOrChainMatcher.<T>xor( matchers );
	}

	public static org.cthul.matchers.exceptions.CausedBy directlyCausedBy( org.hamcrest.Matcher<? super Throwable> matcher )
	{
		return org.cthul.matchers.exceptions.CausedBy.directlyCausedBy( matcher );
	}

	public static org.cthul.matchers.exceptions.CausedBy directlyCausedBy( Class<? extends Throwable> clazz, String message )
	{
		return org.cthul.matchers.exceptions.CausedBy.directlyCausedBy( clazz, message );
	}

	public static org.cthul.matchers.exceptions.CausedBy directlyCausedBy( Class<? extends Throwable> clazz )
	{
		return org.cthul.matchers.exceptions.CausedBy.directlyCausedBy( clazz );
	}

	public static org.cthul.matchers.exceptions.CausedBy directlyCausedBy( Class<? extends Throwable> clazz,
																		   org.hamcrest.Matcher<? super Throwable> matcher )
	{
		return org.cthul.matchers.exceptions.CausedBy.directlyCausedBy( clazz, matcher );
	}

	public static org.cthul.matchers.exceptions.CausedBy directlyCausedBy( Class<? extends Throwable> clazz, String message,
																		   org.hamcrest.Matcher<? super Throwable> matcher )
	{
		return org.cthul.matchers.exceptions.CausedBy.directlyCausedBy( clazz, message, matcher );
	}

	public static org.cthul.matchers.exceptions.CausedBy causedBy( Class<? extends Throwable> clazz, String message )
	{
		return org.cthul.matchers.exceptions.CausedBy.causedBy( clazz, message );
	}

	public static org.cthul.matchers.exceptions.CausedBy causedBy( org.hamcrest.Matcher<? super Throwable> matcher )
	{
		return org.cthul.matchers.exceptions.CausedBy.causedBy( matcher );
	}

	public static org.cthul.matchers.exceptions.CausedBy causedBy( Class<? extends Throwable> clazz )
	{
		return org.cthul.matchers.exceptions.CausedBy.causedBy( clazz );
	}

	public static org.cthul.matchers.exceptions.CausedBy causedBy( String message )
	{
		return org.cthul.matchers.exceptions.CausedBy.causedBy( message );
	}

	public static org.cthul.matchers.exceptions.CausedBy causedBy( Class<? extends Throwable> clazz,
																   org.hamcrest.Matcher<? super Throwable> matcher )
	{
		return org.cthul.matchers.exceptions.CausedBy.causedBy( clazz, matcher );
	}

	public static org.cthul.matchers.exceptions.CausedBy causedBy( Class<? extends Throwable> clazz, String message,
																   org.hamcrest.Matcher<? super Throwable> matcher )
	{
		return org.cthul.matchers.exceptions.CausedBy.causedBy( clazz, message, matcher );
	}

	public static org.cthul.matchers.exceptions.ExceptionMessage messageContains( java.util.regex.Pattern pattern )
	{
		return org.cthul.matchers.exceptions.ExceptionMessage.messageContains( pattern );
	}

	public static org.cthul.matchers.exceptions.ExceptionMessage messageContains( String regex )
	{
		return org.cthul.matchers.exceptions.ExceptionMessage.messageContains( regex );
	}

	public static org.cthul.matchers.exceptions.ExceptionMessage messageMatches( java.util.regex.Pattern pattern )
	{
		return org.cthul.matchers.exceptions.ExceptionMessage.messageMatches( pattern );
	}

	public static org.cthul.matchers.exceptions.ExceptionMessage messageMatches( String regex )
	{
		return org.cthul.matchers.exceptions.ExceptionMessage.messageMatches( regex );
	}

	public static org.cthul.matchers.exceptions.ExceptionMessage messageIs( String message )
	{
		return org.cthul.matchers.exceptions.ExceptionMessage.messageIs( message );
	}

	public static org.hamcrest.Matcher<Object> exception( String message )
	{
		return org.cthul.matchers.exceptions.IsThrowable.exception( message );
	}

	public static <T extends Exception> org.cthul.matchers.object.InstanceOf<T> exception( Class<T> clazz )
	{
		return org.cthul.matchers.exceptions.IsThrowable.<T>exception( clazz );
	}

	public static org.cthul.matchers.object.InstanceOf<Exception> exception()
	{
		return org.cthul.matchers.exceptions.IsThrowable.exception();
	}

	public static <T extends Exception> org.hamcrest.Matcher<Object> exception( Class<T> clazz, String message,
																									org.hamcrest.Matcher<? super T> matcher )
	{
		return org.cthul.matchers.exceptions.IsThrowable.<T>exception( clazz, message, matcher );
	}

	public static org.hamcrest.Matcher<Object> exception( org.hamcrest.Matcher<? super Exception> matcher )
	{
		return org.cthul.matchers.exceptions.IsThrowable.exception( matcher );
	}

	public static org.hamcrest.Matcher<Object> exception( Class<? extends Exception> clazz, String message )
	{
		return org.cthul.matchers.exceptions.IsThrowable.exception( clazz, message );
	}

	public static <T extends Exception> org.hamcrest.Matcher<Object> exception( Class<T> clazz, org.hamcrest.Matcher<? super T> matcher )
	{
		return org.cthul.matchers.exceptions.IsThrowable.<T>exception( clazz, matcher );
	}

	public static org.hamcrest.Matcher<Object> throwable( String message )
	{
		return org.cthul.matchers.exceptions.IsThrowable.throwable( message );
	}

	public static <T extends Throwable> org.cthul.matchers.object.InstanceOf<T> throwable( Class<T> clazz )
	{
		return org.cthul.matchers.exceptions.IsThrowable.<T>throwable( clazz );
	}

	public static org.cthul.matchers.object.InstanceOf<Throwable> throwable()
	{
		return org.cthul.matchers.exceptions.IsThrowable.throwable();
	}

	public static <T extends Throwable> org.hamcrest.Matcher<Object> throwable( Class<T> clazz, String message,
																									org.hamcrest.Matcher<? super T> matcher )
	{
		return org.cthul.matchers.exceptions.IsThrowable.<T>throwable( clazz, message, matcher );
	}

	public static org.hamcrest.Matcher<Object> throwable( Class<? extends Throwable> clazz, String message )
	{
		return org.cthul.matchers.exceptions.IsThrowable.throwable( clazz, message );
	}

	public static org.hamcrest.Matcher<Object> throwable( org.hamcrest.Matcher<? super Throwable> matcher )
	{
		return org.cthul.matchers.exceptions.IsThrowable.throwable( matcher );
	}

	public static <T extends Throwable> org.hamcrest.Matcher<Object> throwable( Class<T> clazz, org.hamcrest.Matcher<? super T> matcher )
	{
		return org.cthul.matchers.exceptions.IsThrowable.<T>throwable( clazz, matcher );
	}

	/**
	 * Does the proc raise an exception that satisfies the condition?
	 *
	 * @param clazz
	 * @param regex
	 *
	 * @return Proc-Matcher
	 */
	public static org.hamcrest.Matcher<org.cthul.proc.Proc> raisesException( Class<? extends Exception> clazz, String regex )
	{
		return org.cthul.matchers.proc.Raises.raisesException( clazz, regex );
	}

	/**
	 * Does the proc throw an exception?
	 *
	 * @return Proc-Matcher
	 */
	public static org.hamcrest.Matcher<org.cthul.proc.Proc> raisesException()
	{
		return org.cthul.matchers.proc.Raises.raisesException();
	}

	/**
	 * Does the proc raise an exception that satisfies the condition?
	 */
	public static org.hamcrest.Matcher<org.cthul.proc.Proc> raisesException( String regex )
	{
		return org.cthul.matchers.proc.Raises.raisesException( regex );
	}

	/**
	 * Does the proc raise an exception that satisfies the condition?
	 *
	 * @param clazz
	 *
	 * @return Proc-Matcher
	 */
	public static org.hamcrest.Matcher<org.cthul.proc.Proc> raisesException( Class<? extends Exception> clazz )
	{
		return org.cthul.matchers.proc.Raises.raisesException( clazz );
	}

	/**
	 * Does the proc raise an exception that satisfies the condition?
	 */
	public static org.hamcrest.Matcher<org.cthul.proc.Proc> raisesException( org.hamcrest.Matcher<? super Exception> matcher )
	{
		return org.cthul.matchers.proc.Raises.raisesException( matcher );
	}

	/**
	 * Does the proc raise a throwable that satisfies the condition?
	 *
	 * @return Proc-Matcher
	 */
	public static org.hamcrest.Matcher<org.cthul.proc.Proc> raises( Class<? extends Throwable> clazz, String regex )
	{
		return org.cthul.matchers.proc.Raises.raises( clazz, regex );
	}

	/**
	 * Does the proc raise a throwable that satisfies the condition?
	 *
	 * @param clazz
	 *
	 * @return Proc-Matcher
	 */
	public static org.hamcrest.Matcher<org.cthul.proc.Proc> raises( Class<? extends Throwable> clazz )
	{
		return org.cthul.matchers.proc.Raises.raises( clazz );
	}

	/**
	 * Does the proc raise a throwable that satisfies the condition?
	 *
	 * @param regex
	 *
	 * @return Proc-Matcher
	 */
	public static org.hamcrest.Matcher<org.cthul.proc.Proc> raises( String regex )
	{
		return org.cthul.matchers.proc.Raises.raises( regex );
	}

	/**
	 * Does the proc raise a throwable that satisfies the condition?
	 *
	 * @param clazz
	 * @param matcher
	 *
	 * @return Proc-Matcher
	 */
	public static org.hamcrest.Matcher<org.cthul.proc.Proc> raises( Class<? extends Throwable> clazz, org.hamcrest.Matcher<? superThrowable> matcher )
	{
		return org.cthul.matchers.proc.Raises.raises( clazz, matcher );
	}

	/**
	 * Does the proc raise a throwable that satisfies the condition?
	 *
	 * @param throwableMatcher
	 *
	 * @return Proc-Matcher
	 */
	public static org.hamcrest.Matcher<org.cthul.proc.Proc> raises( org.hamcrest.Matcher<? super Throwable> throwableMatcher )
	{
		return org.cthul.matchers.proc.Raises.raises( throwableMatcher );
	}

	/**
	 * Does the proc return a value equal to {@code value}?
	 *
	 * @param value
	 *
	 * @return Proc-Matcher
	 */
	public static org.hamcrest.Matcher<org.cthul.proc.Proc> result( Object value )
	{
		return org.cthul.matchers.proc.Returns.result( value );
	}

	/**
	 * Does the proc return a value that satisfies the condition?
	 *
	 * @param resultMatcher
	 *
	 * @return Proc-Matcher
	 */
	public static org.hamcrest.Matcher<org.cthul.proc.Proc> result( org.hamcrest.Matcher<?> resultMatcher )
	{
		return org.cthul.matchers.proc.Returns.result( resultMatcher );
	}

	/**
	 * Does the proc complete without throwing an exception?
	 *
	 * @return Proc-Matcher
	 */
	public static org.hamcrest.Matcher<org.cthul.proc.Proc> returns()
	{
		return org.cthul.matchers.proc.Returns.returns();
	}

	/**
	 * Does the proc return a value that satisfies the condition?
	 *
	 * @param resultMatcher
	 *
	 * @return Proc-Matcher
	 */
	public static org.hamcrest.Matcher<org.cthul.proc.Proc> returns( org.hamcrest.Matcher<?> resultMatcher )
	{
		return org.cthul.matchers.proc.Returns.returns( resultMatcher );
	}

	/**
	 * Does the proc return a value equal to {@code value}?
	 *
	 * @param value
	 *
	 * @return Proc-Matcher
	 */
	public static org.hamcrest.Matcher<org.cthul.proc.Proc> returns( Object value )
	{
		return org.cthul.matchers.proc.Returns.returns( value );
	}

	/**
	 * Does the proc complete without throwing an exception?
	 *
	 * @return Proc-Matcher
	 */
	public static org.hamcrest.Matcher<org.cthul.proc.Proc> hasResult()
	{
		return org.cthul.matchers.proc.Returns.hasResult();
	}


}
