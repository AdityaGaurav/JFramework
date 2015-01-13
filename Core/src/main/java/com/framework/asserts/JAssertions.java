package com.framework.asserts;

import com.framework.driver.objects.PageObject;
import com.google.common.base.Optional;
import com.google.common.collect.Multimap;
import com.google.common.collect.Range;
import com.google.common.collect.Table;
import com.google.common.io.ByteSource;
import org.assertj.core.api.Assertions;
import org.assertj.guava.api.*;
import org.assertj.jodatime.api.DateTimeAssert;
import org.assertj.jodatime.api.LocalDateTimeAssert;
import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;
import org.openqa.selenium.support.ui.WebDriverWait;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.asserts
 *
 * Name   : JAssertions
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-01-05
 *
 * Time   : 17:53
 */

public class JAssertions extends Assertions
{

	/**
	 * Creates a new <code>{@link com.framework.asserts.JAssertions}</code>.
	 */
	protected JAssertions()
	{
		// empty
	}

	/**
	 * Creates a new instance of <code>{@link WebDriverAssert}</code>.
	 *
	 * @param actual the actual value.
	 *
	 * @return the created assertion object.
	 */
	public static WebDriverAssert assertThat( org.openqa.selenium.WebDriver actual )
	{
		return new WebDriverAssert( actual );
	}

	/**
	 * Creates a new instance of <code>{@link WebElementAssert}</code>.
	 *
	 * @param actual the actual value.
	 *
	 * @return the created assertion object.
	 */
	public static WebElementAssert assertThat( org.openqa.selenium.WebElement actual )
	{
		return new WebElementAssert( actual );
	}

	/**
	 * Creates a new instance of <code>{@link PageAssert}</code>.
	 *
	 * @param actual the actual value.
	 *
	 * @return the created assertion object.
	 */
	public static PageAssert assertThat( PageObject actual )
	{
		return new PageAssert( actual );
	}

	/**
	 * Creates a new instance of <code>{@link WebDriverWaitAssert}</code>.
	 *
	 * @param actual the actual value.
	 *
	 * @return the created assertion object.
	 */
	public static WebDriverWaitAssert assertWaitThat( WebDriverWait actual )
	{
		return new WebDriverWaitAssert( actual );
	}

	public static DateTimeAssert assertThat( DateTime date )
	{
		return new JodaDateTimeAssert( DateTimeAssert.class, date );
	}

	public static LocalDateTimeAssert assertThat( LocalDateTime localDateTime )
	{
		return new JodaLocalDateTimeAssert( LocalDateTimeAssert.class, localDateTime );
	}

	public static ByteSourceAssert assertThat( final ByteSource actual )
	{
		return new GuavaByteSourceAssert( actual );
	}

	public static <K, V> MultimapAssert<K, V> assertThat( final Multimap<K, V> actual )
	{
		return new GuavaMultimapAssert<K, V>( actual );
	}

	public static <T> OptionalAssert<T> assertThat( final Optional<T> actual )
	{
		return new GuavaOptionalAssert<T>( actual );
	}

	public static <T extends Comparable<T>> RangeAssert<T> assertThat( final Range<T> actual )
	{
		return new GuavaRangeAssert<T>( actual );
	}

	public static <R, C, V> TableAssert<R, C, V> assertThat( Table<R, C, V> actual )
	{
		return new GuavaTableAssert<R, C, V>( actual );
	}


}
