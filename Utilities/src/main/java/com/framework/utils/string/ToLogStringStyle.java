package com.framework.utils.string;

import org.apache.commons.lang3.SystemUtils;
import org.apache.commons.lang3.builder.ToStringStyle;


/**
 * The default toString style. {@linkplain #DEFAULT_STYLE}
 * <pre>
 * Person@182f0db[name=John Doe,age=33,smoker=false]
 * </pre>
 *
 * The multi line toString style.{@linkplain #MULTI_LINE_STYLE}
 * <pre>
 * Person@182f0db[
 *   name=John Doe
 *   age=33
 *   smoker=false
 * ]
 * </pre>
 *
 * The no field names toString style. {@linkplain #NO_FIELD_NAMES_STYLE}
 * <pre>
 * Person@182f0db[John Doe,33,false]
 * </pre>
 *
 * The short prefix toString style. {@linkplain #SHORT_PREFIX_STYLE}
 * <pre>
 * Person[name=John Doe,age=33,smoker=false]
 * </pre>
 * 
 * The simple toString style.
 * <pre>
 * John Doe,33,false
 * </pre>
 */

public class ToLogStringStyle extends ToStringStyle
{

	//region LogStringStyle - Variables Declaration and Initialization Section.

	public static final ToStringStyle LOG_LINE_STYLE = new LogLineToStringStyle();

	public static final ToStringStyle LOG_MULTI_LINE_STYLE = new LogMultiLineToStringStyle();

	public static final ToStringStyle NO_FIELD_NAMES_STYLE = new LogNoFieldsToStringStyle();
	/**
	 * Whether to use the field names, the default is {@code true}.
	 */
	private static final boolean USE_FIELD_NAMES = true;

	/**
	 * Whether to use the class name, the default is {@code true}.
	 */
	private static final boolean USE_CLASS_NAME = true;

	/**
	 * Whether to use short class names, the default is {@code false}.
	 */
	private static final boolean USE_SHORT_CLASS_NAME = true;

	/**
	 * Whether to use the identity hash code, the default is {@code true}.
	 */
	private static final boolean USE_IDENTITY_HASH_CODE = false;

	/**
	 * The content start {@code '['}.
	 */
	private static final String CONTENT_START = "[ ";

	/**
	 * The content end {@code ']'}.
	 */
	private static final String CONTENT_END = " ]";

	/**
	 * The field name value separator {@code '='}.
	 */
	private static final String FIELD_NAME_VALUE_SEPARATOR = ":'";

	/**
	 * Whether the field separator should be added before any other fields. the default is {@code false}
	 */
	private static final boolean FIELD_SEPARATOR_AT_START = false;

	/**
	 * Whether the field separator should be added after any other fields. the default is {@code false}
	 */
	private static final boolean FIELD_SEPARATOR_AT_END = false;

	/**
	 * The field separator {@code ','}.
	 */
	private static final String FIELD_SEPARATOR = "', ";

	/**
	 * The array start {@code '{'}.
	 */
	private static final String ARRAY_START = "{ ";

	/**
	 * The array separator {@code ','}.
	 */
	private static final String ARRAY_SEPARATOR = ", ";

	/**
	 * The detail for array content. the default is {@code true}
	 */
	private static final boolean ARRAY_CONTENT_DETAIL = true;

	/**
	 * The array end {@code '}'}.
	 */
	private static final String ARRAY_END = " }";

	/**
	 * The value to use when fullDetail is {@code null}, the default value is {@code true}.
	 */
	private static final boolean DEFAULT_FULL_DETAIL = true;

	/**
	 * The {@code null} text {@code '&lt;null&gt;'}.
	 */
	private static final String NULL_TEXT = "< null >";

	/**
	 * The summary size text start {@code '&lt;size'}.
	 */
	private static final String SIZE_START_TEXT = "< size:";

	/**
	 * The summary size text start {@code '&gt;'}.
	 */
	private static final String SIZE_END_TEXT = " >";

	/**
	 * The summary object text start {@code '&lt;'}.
	 */
	private static final String SUMMARY_OBJECT_AT_START = "< ";

	/**
	 * The summary object text start {@code '&gt;'}.
	 */
	private static final String SUMMARY_OBJECT_AT_END = " >";

	//endregion


	//region LogStringStyle - Constructor and Initialization Section.

	public ToLogStringStyle()
	{
		super();
	}



	//endregion

	private static class LogLineToStringStyle extends ToStringStyle
	{
		private static final long serialVersionUID = 1L;

		LogLineToStringStyle()
		{
			super();
			setUseFieldNames( USE_FIELD_NAMES );
			setUseClassName( USE_CLASS_NAME );
			setUseShortClassName( USE_SHORT_CLASS_NAME );
			setUseIdentityHashCode( USE_IDENTITY_HASH_CODE );
			setContentStart( CONTENT_START );
			setContentEnd( CONTENT_END );
			setFieldNameValueSeparator( FIELD_NAME_VALUE_SEPARATOR );
			setFieldSeparatorAtStart( FIELD_SEPARATOR_AT_START );
			setFieldSeparatorAtEnd( FIELD_SEPARATOR_AT_END );
			setFieldSeparator( FIELD_SEPARATOR );
			setArrayStart( ARRAY_START );
			setArraySeparator( ARRAY_SEPARATOR );
			setArrayContentDetail( ARRAY_CONTENT_DETAIL );
			setArrayEnd( ARRAY_END );
			setDefaultFullDetail( DEFAULT_FULL_DETAIL );
			setNullText( NULL_TEXT );
			setSizeStartText( SIZE_START_TEXT );
			setSizeEndText( SIZE_END_TEXT );
			setSummaryObjectStartText( SUMMARY_OBJECT_AT_START );
			setSummaryObjectEndText( SUMMARY_OBJECT_AT_END );
		}

		private Object readResolve()
		{
			return ToLogStringStyle.LOG_LINE_STYLE;
		}
	}

	private final static class LogNoFieldsToStringStyle extends LogLineToStringStyle
	{
		private static final long serialVersionUID = 1L;

		LogNoFieldsToStringStyle()
		{
			super();
			setUseFieldNames( false );
		}

		private Object readResolve()
		{
			return ToLogStringStyle.NO_FIELD_NAMES_STYLE;
		}
	}

	private static final class LogMultiLineToStringStyle extends LogLineToStringStyle
	{
		private static final long serialVersionUID = 1L;

		LogMultiLineToStringStyle()
		{
			super();
			setContentStart( "[ " + SystemUtils.LINE_SEPARATOR + "   " );
			setContentEnd( "'" + SystemUtils.LINE_SEPARATOR + "]" );
			setFieldNameValueSeparator( ": '" );
			setFieldSeparator( "'" + SystemUtils.LINE_SEPARATOR + "   " );
		}

		private Object readResolve()
		{
			return ToLogStringStyle.LOG_LINE_STYLE;
		}
	}

}
