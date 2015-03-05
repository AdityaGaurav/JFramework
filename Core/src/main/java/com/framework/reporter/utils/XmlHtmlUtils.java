package com.framework.reporter.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.reporters.Buffer;
import org.testng.reporters.IBuffer;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.Map;
import java.util.Properties;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.reporter.utils
 *
 * Name   : XmlHtmlUtils 
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-02-11 
 *
 * Time   : 16:25
 *
 */

public class XmlHtmlUtils
{

	//region XmlHtmlUtils - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( XmlHtmlUtils.class );

	private static final String EOL_HTML = "<span class=\"pln\">\n</span>";

	//endregion


	//region XmlHtmlUtils - Constructor Methods Section

	private XmlHtmlUtils()
	{
		// Hide constructor
	}

	//endregion


	//region XmlHtmlUtils - Public Methods Section

	static public String xml( String indent, String name, String content, Properties attributes )
	{
		IBuffer result = Buffer.create();
		xmlOpen( result, indent, name, attributes, true /* no newline */ );
		if ( content != null )
		{
			result.append( content );
		}
		xmlClose( result, "", name, extractComment( name, attributes ) );

		return result.toString();
	}

	static void xmlOpen( IBuffer result, String indent, String tag, Properties attributes )
	{
		xmlOpen( result, indent, tag, attributes, false /* no newline */ );
	}

	static void xmlOpen( IBuffer result, String indent, String tag, Properties attributes, boolean noNewLine )
	{
		result.append( indent )
				.append( "<span class=\"xml-tag\">" )
				.append( "&lt;" ).append( tag )
				.append( "</span>" );
		appendAttributes( result, attributes );
		result.append( "<span class=\"xml-tag\">&gt;</span>" );
		if ( ! noNewLine )
		{
			result.append( EOL_HTML );
		}
	}

	static void appendAttributes( IBuffer result, Properties attributes )
	{
		if ( null != attributes )
		{
			for ( Object element : attributes.entrySet() )
			{
				Map.Entry entry = ( Map.Entry ) element;
				String key = entry.getKey().toString();
				String value = escape( entry.getValue().toString() );
				result.append( "<span class=\"pln\"> </span>" )
						.append( "<span class=\"atn\">" )
						.append( key )
						.append( "</span>" )
						.append( "<span class=\"pun\">=</span>" )
						.append( "<span class=\"atv\">" )
						.append( "\"" + value + "\"</span>" );
			}
		}
	}

	static String escape( String input )
	{
		if ( input == null )
		{
			return null;
		}
		StringBuilder result = new StringBuilder();
		StringCharacterIterator iterator = new StringCharacterIterator( input );
		char character = iterator.current();
		while ( character != CharacterIterator.DONE )
		{
			if ( character == '<' )
			{
				result.append( "&lt;" );
			}
			else if ( character == '>' )
			{
				result.append( "&gt;" );
			}
			else if ( character == '\"' )
			{
				result.append( "&quot;" );
			}
			else if ( character == '\'' )
			{
				result.append( "&#039;" );
			}
			else if ( character == '&' )
			{
				result.append( "&amp;" );
			}
			else
			{
				result.append( character );
			}
			character = iterator.next();
		}
		return result.toString();
	}

	public static void xmlClose( IBuffer result, String indent, String tag, String comment )
	{
		result.append( indent ).append( "<span class=\"xml-tag\">&lt;/" ).append( tag ).append( "&gt;</span><span class=\"pln\">" )
				.append( comment != null ? comment : "" )
				.append( EOL_HTML );
	}

	static String extractComment( String tag, Properties properties )
	{
		if ( properties == null || "span".equals( tag ) )
		{
			return null;
		}

		String[] attributes = new String[] { "id", "name", "class" };
		for ( String a : attributes )
		{
			String comment = properties.getProperty( a );
			if ( comment != null )
			{
				return "<span class=\"com\">&lt;!-- " + comment.replaceAll( "[-]{2,}", "-" )
						+ " --&gt;</span>";
			}
		}

		return null;
	}

	//endregion


	//region XmlHtmlUtils - Protected Methods Section

	//endregion


	//region XmlHtmlUtils - Private Function Section

	//endregion


	//region XmlHtmlUtils - Inner Classes Implementation Section

	//endregion

}
