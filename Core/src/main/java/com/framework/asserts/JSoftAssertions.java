package com.framework.asserts;

import com.framework.driver.event.EventWebDriver;
import com.framework.driver.objects.PageObject;
import com.google.common.collect.Multimap;
import org.assertj.core.api.*;
import org.assertj.core.internal.cglib.proxy.Enhancer;
import org.assertj.guava.api.MultimapAssert;
import org.assertj.jodatime.api.DateTimeAssert;
import org.assertj.jodatime.api.LocalDateTimeAssert;
import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static org.assertj.core.groups.Properties.extractProperty;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.asserts
 *
 * Name   : SoftAssertions
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-01-05
 *
 * Time   : 18:02
 */

public class JSoftAssertions
{

	//region SoftAssertions - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( JSoftAssertions.class );

	/**
	 * Collects error messages of all AssertionErrors thrown by the proxied method.
	 */
	protected final ErrorCollector collector = new ErrorCollector();

	private EventWebDriver driver;

	//endregion


	//region SoftAssertions - Constructor Methods Section

	/**
	 * Creates a new </code>{@link com.framework.asserts.JSoftAssertions}</code>.
	 */
	public JSoftAssertions()
	{}

	public JSoftAssertions( final WebDriver driver )
	{
		this.driver = ( EventWebDriver ) driver;
	}
	//endregion


	//region SoftAssertions - Public Methods Section

	/**
	 * Verifies that no proxied assertion methods have failed.
	 *
	 * @throws org.assertj.core.api.SoftAssertionError if any proxied assertion objects threw
	 */
	public void assertAll()
	{
		List<Throwable> errors = collector.errors();
		if ( ! errors.isEmpty() )
		{
			throw new SoftAssertionError( extractProperty( "message", String.class ).from( errors ) );
		}
	}

	/**
	 * Creates a new "soft" instance of <code>{@link com.framework.asserts.WebDriverAssert}</code>.
	 *
	 * @param actual the actual value.
	 *
	 * @return the created "soft" assertion object.
	 */
	public com.framework.asserts.WebDriverAssert assertThat( WebDriver actual )
	{
		return proxy( com.framework.asserts.WebDriverAssert.class, WebDriver.class, actual );
	}

	/**
	 * Creates a new "soft" instance of <code>{@link com.framework.asserts.WebElementAssert}</code>.
	 *
	 * @param actual the actual value.
	 *
	 * @return the created "soft" assertion object.
	 */
	public com.framework.asserts.WebElementAssert assertThat( WebElement actual )
	{
		return proxy( WebElementAssert.class, WebElement.class, actual );
	}

	/**
	 * Creates a new "soft" instance of <code>{@link com.framework.asserts.PageAssert}</code>.
	 *
	 * @param actual the actual value.
	 *
	 * @return the created "soft" assertion object.
	 */
	public PageAssert assertThat( PageObject actual )
	{
		return proxy( PageAssert.class, PageObject.class, actual );
	}

	/**
	 * Creates a new "soft" instance of <code>{@link com.framework.asserts.PageAssert}</code>.
	 *
	 * @param actual the actual value.
	 *
	 * @return the created "soft" assertion object.
	 */
	public WebDriverWaitAssert assertWaitThat( WebDriverWait actual )
	{
		return proxy( WebDriverWaitAssert.class, WebDriverWait.class, actual );
	}

	public DateTimeAssert assertThat( DateTime date )
	{
		return proxy( DateTimeAssert.class, DateTime.class, date );
	}

	public LocalDateTimeAssert assertThat( LocalDateTime localDateTime )
	{
		return proxy( LocalDateTimeAssert.class, LocalDateTime.class, localDateTime );
	}

	/**
	 * Creates a new instance of <code>{@link org.assertj.core.api.BigDecimalAssert}</code>.
	 *
	 * @param actual the actual value.
	 *
	 * @return the created assertion object.
	 */
	public BigDecimalAssert assertThat( BigDecimal actual )
	{
		return proxy( BigDecimalAssert.class, BigDecimal.class, actual );
	}

	/**
	 * Creates a new instance of <code>{@link org.assertj.core.api.BooleanAssert}</code>.
	 *
	 * @param actual the actual value.
	 *
	 * @return the created assertion object.
	 */
	public BooleanAssert assertThat( boolean actual )
	{
		return proxy( BooleanAssert.class, Boolean.class, actual );
	}

	/**
	 * Creates a new instance of <code>{@link org.assertj.core.api.BooleanAssert}</code>.
	 *
	 * @param actual the actual value.
	 *
	 * @return the created assertion object.
	 */
	public BooleanAssert assertThat( Boolean actual )
	{
		return proxy( BooleanAssert.class, Boolean.class, actual );
	}

	/**
	 * Creates a new instance of <code>{@link org.assertj.core.api.BooleanArrayAssert}</code>.
	 *
	 * @param actual the actual value.
	 *
	 * @return the created assertion object.
	 */
	public BooleanArrayAssert assertThat( boolean[] actual )
	{
		return proxy( BooleanArrayAssert.class, boolean[].class, actual );
	}

	/**
	 * Creates a new instance of <code>{@link org.assertj.core.api.ByteAssert}</code>.
	 *
	 * @param actual the actual value.
	 *
	 * @return the created assertion object.
	 */
	public ByteAssert assertThat( byte actual )
	{
		return proxy( ByteAssert.class, Byte.class, actual );
	}

	/**
	 * Creates a new instance of <code>{@link org.assertj.core.api.ByteAssert}</code>.
	 *
	 * @param actual the actual value.
	 *
	 * @return the created assertion object.
	 */
	public ByteAssert assertThat( Byte actual )
	{
		return proxy( ByteAssert.class, Byte.class, actual );
	}

	/**
	 * Creates a new instance of <code>{@link org.assertj.core.api.ByteArrayAssert}</code>.
	 *
	 * @param actual the actual value.
	 *
	 * @return the created assertion object.
	 */
	public ByteArrayAssert assertThat( byte[] actual )
	{
		return proxy( ByteArrayAssert.class, byte[].class, actual );
	}

	/**
	 * Creates a new instance of <code>{@link org.assertj.core.api.CharacterAssert}</code>.
	 *
	 * @param actual the actual value.
	 *
	 * @return the created assertion object.
	 */
	public CharacterAssert assertThat( char actual )
	{
		return proxy( CharacterAssert.class, Character.class, actual );
	}

	/**
	 * Creates a new instance of <code>{@link org.assertj.core.api.CharArrayAssert}</code>.
	 *
	 * @param actual the actual value.
	 *
	 * @return the created assertion object.
	 */
	public CharArrayAssert assertThat( char[] actual )
	{
		return proxy( CharArrayAssert.class, char[].class, actual );
	}

	/**
	 * Creates a new instance of <code>{@link org.assertj.core.api.CharacterAssert}</code>.
	 *
	 * @param actual the actual value.
	 *
	 * @return the created assertion object.
	 */
	public CharacterAssert assertThat( Character actual )
	{
		return proxy( CharacterAssert.class, Character.class, actual );
	}

	/**
	 * Creates a new instance of <code>{@link org.assertj.core.api.ClassAssert}</code>
	 *
	 * @param actual the actual value.
	 *
	 * @return the created assertion object.
	 */
	public ClassAssert assertThat( Class<?> actual )
	{
		return proxy( ClassAssert.class, Class.class, actual );
	}

	/**
	 * Creates a new instance of <code>{@link org.assertj.core.api.IterableAssert}</code>.
	 *
	 * @param actual the actual value.
	 *
	 * @return the created assertion object.
	 */
	@SuppressWarnings ( "unchecked" )
	public <T> IterableAssert<T> assertThat( Iterable<? extends T> actual )
	{
		return proxy( IterableAssert.class, Iterable.class, actual );
	}

	/**
	 * Creates a new instance of <code>{@link org.assertj.core.api.IterableAssert}</code>. The <code>{@link java.util.Iterator}</code> is first
	 * converted
	 * into an <code>{@link Iterable}</code>
	 *
	 * @param actual the actual value.
	 *
	 * @return the created assertion object.
	 */
	@SuppressWarnings ( "unchecked" )
	public <T> IterableAssert<T> assertThat( Iterator<T> actual )
	{
		return proxy( IterableAssert.class, Iterator.class, actual );
	}

	/**
	 * Creates a new instance of <code>{@link org.assertj.core.api.DoubleAssert}</code>.
	 *
	 * @param actual the actual value.
	 *
	 * @return the created assertion object.
	 */
	public DoubleAssert assertThat( double actual )
	{
		return proxy( DoubleAssert.class, Double.class, actual );
	}

	/**
	 * Creates a new instance of <code>{@link org.assertj.core.api.DoubleAssert}</code>.
	 *
	 * @param actual the actual value.
	 *
	 * @return the created assertion object.
	 */
	public DoubleAssert assertThat( Double actual )
	{
		return proxy( DoubleAssert.class, Double.class, actual );
	}

	/**
	 * Creates a new instance of <code>{@link org.assertj.core.api.DoubleArrayAssert}</code>.
	 *
	 * @param actual the actual value.
	 *
	 * @return the created assertion object.
	 */
	public DoubleArrayAssert assertThat( double[] actual )
	{
		return proxy( DoubleArrayAssert.class, double[].class, actual );
	}

	/**
	 * Creates a new instance of <code>{@link org.assertj.core.api.FileAssert}</code>.
	 *
	 * @param actual the actual value.
	 *
	 * @return the created assertion object.
	 */
	public FileAssert assertThat( File actual )
	{
		return proxy( FileAssert.class, File.class, actual );
	}

	/**
	 * Creates a new instance of <code>{@link org.assertj.core.api.InputStreamAssert}</code>.
	 *
	 * @param actual the actual value.
	 *
	 * @return the created assertion object.
	 */
	public InputStreamAssert assertThat( InputStream actual )
	{
		return proxy( InputStreamAssert.class, InputStream.class, actual );
	}

	/**
	 * Creates a new instance of <code>{@link org.assertj.core.api.FloatAssert}</code>.
	 *
	 * @param actual the actual value.
	 *
	 * @return the created assertion object.
	 */
	public FloatAssert assertThat( float actual )
	{
		return proxy( FloatAssert.class, Float.class, actual );
	}

	/**
	 * Creates a new instance of <code>{@link org.assertj.core.api.FloatAssert}</code>.
	 *
	 * @param actual the actual value.
	 *
	 * @return the created assertion object.
	 */
	public FloatAssert assertThat( Float actual )
	{
		return proxy( FloatAssert.class, Float.class, actual );
	}

	/**
	 * Creates a new instance of <code>{@link org.assertj.core.api.FloatArrayAssert}</code>.
	 *
	 * @param actual the actual value.
	 *
	 * @return the created assertion object.
	 */
	public FloatArrayAssert assertThat( float[] actual )
	{
		return proxy( FloatArrayAssert.class, float[].class, actual );
	}

	/**
	 * Creates a new instance of <code>{@link org.assertj.core.api.IntegerAssert}</code>.
	 *
	 * @param actual the actual value.
	 *
	 * @return the created assertion object.
	 */
	public IntegerAssert assertThat( int actual )
	{
		return proxy( IntegerAssert.class, Integer.class, actual );
	}

	/**
	 * Creates a new instance of <code>{@link org.assertj.core.api.IntArrayAssert}</code>.
	 *
	 * @param actual the actual value.
	 *
	 * @return the created assertion object.
	 */
	public IntArrayAssert assertThat( int[] actual )
	{
		return proxy( IntArrayAssert.class, int[].class, actual );
	}

	/**
	 * Creates a new instance of <code>{@link org.assertj.core.api.IntegerAssert}</code>.
	 *
	 * @param actual the actual value.
	 *
	 * @return the created assertion object.
	 */
	public IntegerAssert assertThat( Integer actual )
	{
		return proxy( IntegerAssert.class, Integer.class, actual );
	}

	/**
	 * Creates a new instance of <code>{@link org.assertj.core.api.ListAssert}</code>.
	 *
	 * @param actual the actual value.
	 *
	 * @return the created assertion object.
	 */
	@SuppressWarnings ( "unchecked" )
	public <T> ListAssert<T> assertThat( List<? extends T> actual )
	{
		return proxy( ListAssert.class, List.class, actual );
	}

	/**
	 * Creates a new instance of <code>{@link org.assertj.core.api.LongAssert}</code>.
	 *
	 * @param actual the actual value.
	 *
	 * @return the created assertion object.
	 */
	public LongAssert assertThat( long actual )
	{
		return proxy( LongAssert.class, Long.class, actual );
	}

	/**
	 * Creates a new instance of <code>{@link org.assertj.core.api.LongAssert}</code>.
	 *
	 * @param actual the actual value.
	 *
	 * @return the created assertion object.
	 */
	public LongAssert assertThat( Long actual )
	{
		return proxy( LongAssert.class, Long.class, actual );
	}

	/**
	 * Creates a new instance of <code>{@link org.assertj.core.api.LongArrayAssert}</code>.
	 *
	 * @param actual the actual value.
	 *
	 * @return the created assertion object.
	 */
	public LongArrayAssert assertThat( long[] actual )
	{
		return proxy( LongArrayAssert.class, long[].class, actual );
	}

	/**
	 * Creates a new instance of <code>{@link org.assertj.core.api.ObjectAssert}</code>.
	 *
	 * @param actual the actual value.
	 *
	 * @return the created assertion object.
	 */
	@SuppressWarnings ( "unchecked" )
	public <T> ObjectAssert<T> assertThat( T actual )
	{
		return proxy( ObjectAssert.class, Object.class, actual );
	}

	/**
	 * Creates a new instance of <code>{@link org.assertj.core.api.ObjectArrayAssert}</code>.
	 *
	 * @param actual the actual value.
	 *
	 * @return the created assertion object.
	 */
	@SuppressWarnings ( "unchecked" )
	public <T> ObjectArrayAssert<T> assertThat( T[] actual )
	{
		return proxy( ObjectArrayAssert.class, Object[].class, actual );
	}

	/**
	 * Creates a new instance of <code>{@link org.assertj.core.api.MapAssert}</code>.
	 *
	 * @param actual the actual value.
	 *
	 * @return the created assertion object.
	 */
	@SuppressWarnings ( "unchecked" )
	public <K, V> MapAssert<K, V> assertThat( Map<K, V> actual )
	{
		return proxy( MapAssert.class, Map.class, actual );
	}

	/**
	 * Creates a new instance of <code>{@link org.assertj.core.api.ShortAssert}</code>.
	 *
	 * @param actual the actual value.
	 *
	 * @return the created assertion object.
	 */
	public ShortAssert assertThat( short actual )
	{
		return proxy( ShortAssert.class, Short.class, actual );
	}

	/**
	 * Creates a new instance of <code>{@link org.assertj.core.api.ShortAssert}</code>.
	 *
	 * @param actual the actual value.
	 *
	 * @return the created assertion object.
	 */
	public ShortAssert assertThat( Short actual )
	{
		return proxy( ShortAssert.class, Short.class, actual );
	}

	/**
	 * Creates a new instance of <code>{@link org.assertj.core.api.ShortArrayAssert}</code>.
	 *
	 * @param actual the actual value.
	 *
	 * @return the created assertion object.
	 */
	public ShortArrayAssert assertThat( short[] actual )
	{
		return proxy( ShortArrayAssert.class, short[].class, actual );
	}

	/**
	 * Creates a new instance of <code>{@link org.assertj.core.api.CharSequenceAssert}</code>.
	 *
	 * @param actual the actual value.
	 *
	 * @return the created assertion object.
	 */
	public CharSequenceAssert assertThat( CharSequence actual )
	{
		return proxy( CharSequenceAssert.class, CharSequence.class, actual );
	}

	/**
	 * Creates a new instance of <code>{@link org.assertj.core.api.StringAssert}</code>.
	 *
	 * @param actual the actual value.
	 *
	 * @return the created assertion object.
	 */
	public StringAssert assertThat( String actual )
	{
		return proxy( StringAssert.class, String.class, actual );
	}

	/**
	 * Creates a new instance of <code>{@link org.assertj.core.api.DateAssert}</code>.
	 *
	 * @param actual the actual value.
	 *
	 * @return the created assertion object.
	 */
	public DateAssert assertThat( Date actual )
	{
		return proxy( DateAssert.class, Date.class, actual );
	}

	/**
	 * Creates a new instance of <code>{@link org.assertj.core.api.ThrowableAssert}</code>.
	 *
	 * @param actual the actual value.
	 *
	 * @return the created assertion Throwable.
	 */
	public ThrowableAssert assertThat( Throwable actual )
	{
		return proxy( ThrowableAssert.class, Throwable.class, actual );
	}

	public <K, V> MultimapAssert<K, V> assertThat( final Multimap<K, V> actual )
	{
		return proxy( MultimapAssert.class, Multimap.class, actual );
	}


	//endregion


	//region SoftAssertions - Protected Methods Section

	@SuppressWarnings ( "unchecked" )
	protected <T, V> V proxy( Class<V> assertClass, Class<T> actualClass, T actual )
	{
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass( assertClass );
		enhancer.setCallback( collector );
		return ( V ) enhancer.create( new Class[] { actualClass }, new Object[] { actual } );
	}

	//endregion


	//region SoftAssertions - Private Function Section

	//endregion


	//region SoftAssertions - Inner Classes Implementation Section

	//endregion

}
