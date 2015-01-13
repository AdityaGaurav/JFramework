package com.framework.utils.string;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.SystemUtils;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Map;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: org.framework.webdriver.utils
 *
 * Name   : LogStringStyle
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2014-12-23
 *
 * Time   : 20:17
 */

public class LogStringStyle extends ToStringStyle
{

	//region LogStringStyle - Variables Declaration and Initialization Section.

	private static final Logger LOG = LoggerFactory.getLogger( LogStringStyle.class );

	//endregion


	private static final String CONTENT_START = "[ ";
	private static final String FIELD_NAME_VALUE_SEPARATOR = " -> ";
	private static final String FIELD_SEPARATOR = SystemUtils.LINE_SEPARATOR + "  ";
	private static final String NULL_TEXT = "<NULL>";
	private static final String ARRAY_START = "{ ";
	private static final String ARRAY_END = " }";
	private static final String CONTENT_END = SystemUtils.LINE_SEPARATOR + "]";
	private static final String ARRAY_SEPARATOR = ", ";
	private static final boolean USE_SHORT_CLASS_NAME = true;
	private static final boolean USE_IDENTITY_HASH_CODE = false;
	private static final boolean USE_DEFAULT_FULL_DETAIL = true;
	private static final boolean USE_FIELD_NAMES = true;
	private static final boolean USE_CLASS_NAME = true;
	private static final boolean ARRAY_CONTENT_DETAIL = true;
	private static final boolean FIELD_SEPARATOR_AT_START = true;
	private static final boolean FIELD_SEPARATOR_AT_END = false;

	//endregion


	//region LogStringStyle - Constructor Methods Section

	public LogStringStyle()
	{
		super();
		setContentStart( CONTENT_START );
		setUseShortClassName( USE_SHORT_CLASS_NAME );
		setFieldNameValueSeparator( FIELD_NAME_VALUE_SEPARATOR );
		setUseIdentityHashCode( USE_IDENTITY_HASH_CODE );
		setDefaultFullDetail( USE_DEFAULT_FULL_DETAIL );
		setUseFieldNames( USE_FIELD_NAMES );
		setUseClassName( USE_CLASS_NAME );
		setNullText( NULL_TEXT );
		setArrayStart( ARRAY_START );
		setArrayEnd( ARRAY_END );
		setArraySeparator( ARRAY_SEPARATOR );
		setArrayContentDetail( ARRAY_CONTENT_DETAIL );
		setFieldSeparator( FIELD_SEPARATOR );
		setFieldSeparatorAtStart( FIELD_SEPARATOR_AT_START );
		setFieldSeparatorAtEnd( FIELD_SEPARATOR_AT_END );
		setContentEnd( CONTENT_END );
		org.apache.commons.lang3.tuple.Pair p = org.apache.commons.lang3.tuple.Pair .of( "A", "B" );
	}


	//endregion


	//region LogStringStyle - Public Methods Section

	//endregion


	//region LogStringStyle - Protected Methods Section

	@Override
	protected void appendDetail( StringBuffer buffer, String fieldName, Object value )
	{
		if ( value instanceof Date )
		{
			value = new SimpleDateFormat( "yyyy-MM-dd" ).format( value );
		}
//		if ( value instanceof DateTime )
//		{
//			value =  DateTime.parse( ( ( DateTime ) value ).toString( "dd-MM-yyyy" ) );
//		}
		buffer.append( value );
	}

//	@Override
//	protected void appendContentStart( final StringBuffer buffer )
//	{
//		super.appendContentStart( buffer );
//	}

	@Override
	public void appendSuper( final StringBuffer buffer, final String superToString )
	{
		super.appendSuper( buffer, superToString );
	}

	@Override
	public void appendToString( final StringBuffer buffer, final String toString )
	{
		super.appendToString( buffer, toString );
	}

	@Override
	protected void removeLastFieldSeparator( final StringBuffer buffer )
	{
		super.removeLastFieldSeparator( buffer );
	}

	@Override
	public void append( final StringBuffer buffer, final String fieldName, final Object value, final Boolean fullDetail )
	{
		super.append( buffer, fieldName, value, fullDetail );
	}

	@Override
	protected void appendInternal( final StringBuffer buffer, final String fieldName, final Object value, final boolean detail )
	{
		super.appendInternal( buffer, fieldName, value, detail );
	}

	@Override
	protected void appendCyclicObject( final StringBuffer buffer, final String fieldName, final Object value )
	{
		super.appendCyclicObject( buffer, fieldName, value );
	}

	@Override
	protected void appendDetail( final StringBuffer buffer, final String fieldName, final Collection<?> coll )
	{
		super.appendDetail( buffer, fieldName, coll );
	}

	@Override
	protected void appendDetail( final StringBuffer buffer, final String fieldName, final Map<?, ?> map )
	{
		super.appendDetail( buffer, fieldName, map );
	}

	@Override
	protected void appendSummary( final StringBuffer buffer, final String fieldName, final Object value )
	{
		super.appendSummary( buffer, fieldName, value );
	}

	@Override
	public void append( final StringBuffer buffer, final String fieldName, final long value )
	{
		super.append( buffer, fieldName, value );
	}

	@Override
	protected void appendDetail( final StringBuffer buffer, final String fieldName, final long value )
	{
		super.appendDetail( buffer, fieldName, value );
	}

	@Override
	public void append( final StringBuffer buffer, final String fieldName, final int value )
	{
		super.append( buffer, fieldName, value );
	}

	@Override
	protected void appendDetail( final StringBuffer buffer, final String fieldName, final int value )
	{
		super.appendDetail( buffer, fieldName, value );
	}

	@Override
	public void append( final StringBuffer buffer, final String fieldName, final short value )
	{
		super.append( buffer, fieldName, value );
	}

	@Override
	protected void appendDetail( final StringBuffer buffer, final String fieldName, final short value )
	{
		super.appendDetail( buffer, fieldName, value );
	}

	@Override
	public void append( final StringBuffer buffer, final String fieldName, final byte value )
	{
		super.append( buffer, fieldName, value );
	}

	@Override
	protected void appendDetail( final StringBuffer buffer, final String fieldName, final byte value )
	{
		super.appendDetail( buffer, fieldName, value );
	}

	@Override
	public void append( final StringBuffer buffer, final String fieldName, final char value )
	{
		super.append( buffer, fieldName, value );
	}

	@Override
	protected void appendDetail( final StringBuffer buffer, final String fieldName, final char value )
	{
		super.appendDetail( buffer, fieldName, value );
	}

	@Override
	public void append( final StringBuffer buffer, final String fieldName, final double value )
	{
		super.append( buffer, fieldName, value );
	}

	@Override
	protected void appendDetail( final StringBuffer buffer, final String fieldName, final double value )
	{
		super.appendDetail( buffer, fieldName, value );
	}

	@Override
	public void append( final StringBuffer buffer, final String fieldName, final float value )
	{
		super.append( buffer, fieldName, value );
	}

	@Override
	protected void appendDetail( final StringBuffer buffer, final String fieldName, final float value )
	{
		super.appendDetail( buffer, fieldName, value );
	}


	@Override
	protected void appendDetail( final StringBuffer buffer, final String fieldName, final boolean value )
	{
		buffer.append( BooleanUtils.toStringYesNo( value ).toUpperCase() );
	}

	@Override
	public void append( final StringBuffer buffer, final String fieldName, final Object[] array, final Boolean fullDetail )
	{
		super.append( buffer, fieldName, array, fullDetail );
	}

	@Override
	protected void appendDetail( final StringBuffer buffer, final String fieldName, final Object[] array )
	{
		super.appendDetail( buffer, fieldName, array );
	}

	@Override
	protected void reflectionAppendArrayDetail( final StringBuffer buffer, final String fieldName, final Object array )
	{
		super.reflectionAppendArrayDetail( buffer, fieldName, array );
	}

	@Override
	protected void appendSummary( final StringBuffer buffer, final String fieldName, final Object[] array )
	{
		super.appendSummary( buffer, fieldName, array );
	}

	@Override
	public void append( final StringBuffer buffer, final String fieldName, final long[] array, final Boolean fullDetail )
	{
		super.append( buffer, fieldName, array, fullDetail );
	}

	@Override
	protected void appendDetail( final StringBuffer buffer, final String fieldName, final long[] array )
	{
		super.appendDetail( buffer, fieldName, array );
	}

	@Override
	protected void appendSummary( final StringBuffer buffer, final String fieldName, final long[] array )
	{
		super.appendSummary( buffer, fieldName, array );
	}

	@Override
	public void append( final StringBuffer buffer, final String fieldName, final int[] array, final Boolean fullDetail )
	{
		super.append( buffer, fieldName, array, fullDetail );
	}

	@Override
	protected void appendDetail( final StringBuffer buffer, final String fieldName, final int[] array )
	{
		super.appendDetail( buffer, fieldName, array );
	}

	@Override
	protected void appendSummary( final StringBuffer buffer, final String fieldName, final int[] array )
	{
		super.appendSummary( buffer, fieldName, array );
	}

	@Override
	public void append( final StringBuffer buffer, final String fieldName, final short[] array, final Boolean fullDetail )
	{
		super.append( buffer, fieldName, array, fullDetail );
	}

	@Override
	protected void appendDetail( final StringBuffer buffer, final String fieldName, final short[] array )
	{
		super.appendDetail( buffer, fieldName, array );
	}

	@Override
	protected void appendSummary( final StringBuffer buffer, final String fieldName, final short[] array )
	{
		super.appendSummary( buffer, fieldName, array );
	}

	@Override
	public void append( final StringBuffer buffer, final String fieldName, final byte[] array, final Boolean fullDetail )
	{
		super.append( buffer, fieldName, array, fullDetail );
	}

	@Override
	protected void appendDetail( final StringBuffer buffer, final String fieldName, final byte[] array )
	{
		super.appendDetail( buffer, fieldName, array );
	}

	@Override
	protected void appendSummary( final StringBuffer buffer, final String fieldName, final byte[] array )
	{
		super.appendSummary( buffer, fieldName, array );
	}

	@Override
	public void append( final StringBuffer buffer, final String fieldName, final char[] array, final Boolean fullDetail )
	{
		super.append( buffer, fieldName, array, fullDetail );
	}

	@Override
	protected void appendDetail( final StringBuffer buffer, final String fieldName, final char[] array )
	{
		super.appendDetail( buffer, fieldName, array );
	}

	@Override
	protected void appendSummary( final StringBuffer buffer, final String fieldName, final char[] array )
	{
		super.appendSummary( buffer, fieldName, array );
	}

	@Override
	public void append( final StringBuffer buffer, final String fieldName, final double[] array, final Boolean fullDetail )
	{
		super.append( buffer, fieldName, array, fullDetail );
	}

	@Override
	protected void appendDetail( final StringBuffer buffer, final String fieldName, final double[] array )
	{
		super.appendDetail( buffer, fieldName, array );
	}

	@Override
	protected void appendSummary( final StringBuffer buffer, final String fieldName, final double[] array )
	{
		super.appendSummary( buffer, fieldName, array );
	}

	@Override
	public void append( final StringBuffer buffer, final String fieldName, final float[] array, final Boolean fullDetail )
	{
		super.append( buffer, fieldName, array, fullDetail );
	}

	@Override
	protected void appendDetail( final StringBuffer buffer, final String fieldName, final float[] array )
	{
		super.appendDetail( buffer, fieldName, array );
	}

	@Override
	protected void appendSummary( final StringBuffer buffer, final String fieldName, final float[] array )
	{
		super.appendSummary( buffer, fieldName, array );
	}

	@Override
	public void append( final StringBuffer buffer, final String fieldName, final boolean[] array, final Boolean fullDetail )
	{
		super.append( buffer, fieldName, array, fullDetail );
	}

	@Override
	protected void appendDetail( final StringBuffer buffer, final String fieldName, final boolean[] array )
	{
		super.appendDetail( buffer, fieldName, array );
	}

	@Override
	protected void appendSummary( final StringBuffer buffer, final String fieldName, final boolean[] array )
	{
		super.appendSummary( buffer, fieldName, array );
	}

	@Override
	protected void appendClassName( final StringBuffer buffer, final Object object )
	{
		super.appendClassName( buffer, object );
	}

	@Override
	protected void appendIdentityHashCode( final StringBuffer buffer, final Object object )
	{
		super.appendIdentityHashCode( buffer, object );
	}

	@Override
	protected void appendContentEnd( final StringBuffer buffer )
	{
		super.appendContentEnd( buffer );
	}

	@Override
	protected void appendNullText( final StringBuffer buffer, final String fieldName )
	{
		super.appendNullText( buffer, fieldName );
	}

	@Override
	protected void appendFieldSeparator( final StringBuffer buffer )
	{
		super.appendFieldSeparator( buffer );
	}

	@Override
	protected void appendFieldStart( final StringBuffer buffer, final String fieldName )
	{
		super.appendFieldStart( buffer, fieldName );
	}

	@Override
	protected void appendFieldEnd( final StringBuffer buffer, final String fieldName )
	{
		super.appendFieldEnd( buffer, fieldName );
	}

	@Override
	protected void appendSummarySize( final StringBuffer buffer, final String fieldName, final int size )
	{
		super.appendSummarySize( buffer, fieldName, size );
	}

	@Override
	protected void setSizeStartText( final String sizeStartText )
	{
		super.setSizeStartText( sizeStartText );
	}

	@Override
	protected void setSizeEndText( final String sizeEndText )
	{
		super.setSizeEndText( sizeEndText );
	}

	@Override
	protected void setSummaryObjectStartText( final String summaryObjectStartText )
	{
		super.setSummaryObjectStartText( summaryObjectStartText );
	}

	@Override
	protected void setSummaryObjectEndText( final String summaryObjectEndText )
	{
		super.setSummaryObjectEndText( summaryObjectEndText );
	}

}
