package com.framework.reporter.utils;

import com.framework.reporter.ReporterRuntimeException;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.internal.Nullable;
import org.testng.reporters.Buffer;
import org.testng.reporters.IBuffer;
import org.testng.reporters.XMLUtils;

import java.util.Properties;
import java.util.Stack;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.reporter.utils
 *
 * Name   : XMLStringBuffer 
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-02-09 
 *
 * Time   : 19:34
 *
 */

public class XMLStringBuffer
{
	//region XMLStringBuffer - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( XMLStringBuffer.class );

	private static final String XML_DOCUMENT_VERSION = "1.0";

	/** End of line */
	private static final String EOL = SystemUtils.LINE_SEPARATOR;

	private static final String EOL_HTML = "<span class=\"pln\">\n</span>";

	/** Tab space indent for XML document */
	private static final String DEFAULT_INDENT_INCREMENT = StringUtils.repeat( " ", 3 );

	/** The buffer to hold the xml document */
	private IBuffer buffer;

	/** A string of space character representing the current indentation. */
	private String currentIndent = "";

	/** The stack of tags to make sure XML document is well formed. */
	private final Stack<Tag> tagStack = new Stack<Tag>();


	//endregion


	//region XMLStringBuffer - Constructor Methods Section

	public XMLStringBuffer()
	{
		this( false );
	}

	public XMLStringBuffer( boolean html )
	{
		if( ! html )
		{
			init( Buffer.create(), StringUtils.EMPTY, XML_DOCUMENT_VERSION, SystemUtils.FILE_ENCODING );
		}
		else
		{
			initHtml( Buffer.create(), StringUtils.EMPTY, XML_DOCUMENT_VERSION, SystemUtils.FILE_ENCODING );
		}
	}

	public XMLStringBuffer( boolean html, String indent )
	{
		if( ! html )
		{
			init( Buffer.create(), indent, null, null );
		}
		else
		{
			initHtml( Buffer.create(), indent, null, null );
		}
	}


	//endregion


	//region XMLStringBuffer - Public Methods Section

	/**
	 * Push a new tag.  Its value is stored and will be compared against the parameter
	 * passed to pop().
	 *
	 * @param tagName The name of the tag.
	 */
	public void push( String tagName )
	{
		push( tagName, StringUtils.EMPTY );
	}

	public void pushHtml( String tagName )
	{
		pushHtml( tagName, StringUtils.EMPTY );
	}

	/**
	 * Push a new tag.  Its value is stored and will be compared against the parameter
	 * passed to pop().
	 *
	 * @param tagName The name of the tag.
	 * @param schema The schema to use (can be null or an empty string).
	 */
	public void push( String tagName, @Nullable String schema )
	{
		push( tagName, schema, null );
	}

	public void pushHtml( String tagName, @Nullable String schema )
	{
		pushHtml( tagName, schema, null );
	}

	/**
	 * Push a new tag.  Its value is stored and will be compared against the parameter
	 * passed to pop().
	 *
	 * @param tagName The name of the tag.
	 * @param attributes A Properties file representing the attributes (or null)
	 */
	public void push( String tagName, @Nullable Properties attributes )
	{
		push( tagName, "", attributes );
	}

	public void pushHtml( String tagName, @Nullable Properties attributes )
	{
		pushHtml( tagName, "", attributes );
	}
	/**
	 * Push a new tag.  Its value is stored and will be compared against the parameter
	 * passed to pop().
	 *
	 * @param tagName The name of the tag.
	 * @param schema The schema to use (can be null or an empty string).
	 * @param attributes A Properties file representing the attributes (or null)
	 */
	public void push( String tagName, @Nullable String schema, @Nullable Properties attributes )
	{
		XMLUtils.xmlOpen( buffer, currentIndent, tagName + schema, attributes );
		tagStack.push( new Tag( currentIndent, tagName, attributes ) );
		currentIndent += DEFAULT_INDENT_INCREMENT;
	}

	public void pushHtml( String tagName, @Nullable String schema, @Nullable Properties attributes )
	{
		XmlHtmlUtils.xmlOpen( buffer, currentIndent, tagName + schema, attributes );
		tagStack.push( new Tag( currentIndent, tagName, attributes ) );
		currentIndent += DEFAULT_INDENT_INCREMENT;
	}


	/**
	 * Pop the last pushed element without verifying it if matches the previously
	 * pushed tag.
	 */
	public void pop()
	{
		pop( null );
	}

	public void popHtml()
	{
		popHtml( null );
	}

	/**
	 * Pop the last pushed element and throws an AssertionError if it doesn't
	 * match the corresponding tag that was pushed earlier.
	 *
	 * @param tagName The name of the tag this pop() is supposed to match.
	 */
	public void pop( String tagName )
	{
		currentIndent = currentIndent.substring( DEFAULT_INDENT_INCREMENT.length() );
		Tag t = tagStack.pop();
		if ( null != tagName )
		{
			if ( ! tagName.equals( t.tagName ) )
			{
				throw new AssertionError( "Popping the wrong tag: " + t.tagName + " but expected " + tagName );
			}
		}
		XMLUtils.xmlClose( buffer, currentIndent, t.tagName, XMLUtils.extractComment( tagName, t.properties ) );
	}

	public void popHtml( String tagName )
	{
		currentIndent = currentIndent.substring( DEFAULT_INDENT_INCREMENT.length() );
		Tag t = tagStack.pop();
		if ( null != tagName )
		{
			if ( ! tagName.equals( t.tagName ) )
			{
				throw new AssertionError( "Popping the wrong tag: " + t.tagName + " but expected " + tagName );
			}
		}
		XmlHtmlUtils.xmlClose( buffer, currentIndent, t.tagName, XmlHtmlUtils.extractComment( tagName, t.properties ) );
	}

	/**
	 * Set the doctype for this document.
	 *
	 * @param docType The DOCTYPE string, without the "&lt;!DOCTYPE " "&gt;"
	 */
	public void setDocTypeHtml( String docType )
	{
		buffer.append( "<span class=\"dec\">&lt;!DOCTYPE " + docType + "&gt;</span>" ).append( EOL_HTML );
	}

	public void setDocType( String docType )
	{
		buffer.append( "<!DOCTYPE " + docType + ">" + EOL );
	}

	//endregion


	//region XMLStringBuffer - Tags Methods Section

	/**
	 * Add a required element to the current tag.  An opening and closing tag
	 * will be generated even if value is null.
	 * @param tagName The name of the tag
	 * @param value The value for this tag
	 */
	public void addRequired( String tagName, @Nullable String value )
	{
		addRequired( tagName, value, ( Properties ) null );
	}

	/**
	 * Add a required element to the current tag.  An opening and closing tag
	 * will be generated even if value is null.
	 * @param tagName The name of the tag
	 * @param value The value for this tag
	 * @param attributes A Properties file containing the attributes (or null)
	 */
	public void addRequired( String tagName, @Nullable String value, @Nullable Properties attributes )
	{
		XMLUtils.xmlRequired( buffer, currentIndent, tagName, value, attributes );
	}

	public void addRequired( String tagName, @Nullable String value, String... attributes )
	{
		addRequired( tagName, value, createProperties( attributes ) );
	}

	public void addOptional( String tagName, @Nullable String value )
	{
		if (value != null)
		{
			XMLUtils.xmlOptional( buffer, currentIndent, tagName, value, ( Properties ) null );
		}
	}

	/**
	 * Add an empty element tag (e.g. <foo/>)
	 *
	 * @param tagName The name of the tag
	 *
	 */
	public void addEmptyElement( String tagName )
	{
		addEmptyElement( tagName, ( Properties ) null );
	}

	public void addEmptyElementHtml( String tagName )
	{
		addEmptyElement( tagName, ( Properties ) null );
	}

	public void addEmptyElement( String tagName, Properties attributes )
	{
		buffer.append( currentIndent ).append( "<" ).append( tagName );
		XMLUtils.appendAttributes( buffer, attributes );
		buffer.append( "/>" ).append( EOL );
	}

	public void addEmptyElementHtml( String tagName, Properties attributes )
	{
		buffer.append( currentIndent ).append( "<span class=\"xml-tag\">&lt;" )
				.append( tagName )
				.append( "</span><span class=\"pln\"></span>" );
		XmlHtmlUtils.appendAttributes( buffer, attributes );
		buffer.append( "<span class=\"xml-tag\">/&gt;</span>" ).append( EOL );
	}

	public void addEmptyElement( String tagName, String... attributes )
	{
		addEmptyElement( tagName, createProperties( attributes ) );
	}

	public void addEmptyElementHtml( String tagName, String... attributes )
	{
		addEmptyElement( tagName, createProperties( attributes ) );
	}

	//endregion


	//region XMLStringBuffer - Private Function Section

	/**
	 *
	 */
	private void init( IBuffer buffer, String start, String version, String encoding )
	{
		this.buffer = buffer;
		this.currentIndent = start;
		if ( version != null )
		{
			setXmlDetails( version, encoding );
		}
	}

	private void initHtml( IBuffer buffer, String start, String version, String encoding )
	{
		this.buffer = buffer;
		this.currentIndent = start;
		if ( version != null )
		{
			setXmlDetailsHtml( version, encoding );
		}
	}

	/**
	 * Set the xml version and encoding for this document.
	 *
	 * @param v the XML version
	 * @param enc the XML encoding
	 */
	public void setXmlDetails( String v, String enc )
	{
		if ( buffer.toString().length() != 0 )
		{
			final String ERR_MSG = "Buffer should be empty: '" + buffer.toString() + "'";
			throw new ReporterRuntimeException( new IllegalStateException( ERR_MSG ) );
		}
		buffer.append( "<?xml version=\"" + v + "\" encoding=\"" + enc + "\"?>" ).append( EOL );
	}

	public void setXmlDetailsHtml( String v, String enc )
	{
		if ( buffer.toString().length() != 0 )
		{
			final String ERR_MSG = "Buffer should be empty: '" + buffer.toString() + "'";
			throw new ReporterRuntimeException( new IllegalStateException( ERR_MSG ) );
		}
		buffer.append( "<span class=\"pun\">&lt;?</span>" )
				.append( "<span class=\"pln\">xml version</span>" )
				.append( "<span class=\"pun\">=</span>" )
				.append( "<span class=\"str\">\"" )
				.append( v )
				.append( "\"</span>" )
				.append( "<span class=\"pln\"> encoding</span>" )
				.append( "<span class=\"pun\">=</span>" )
				.append( "<span class=\"str\">\"" )
				.append( enc )
				.append( "\"</span><span class=\"pun\">" )
				.append( "?&gt;</span>" )
				.append( EOL_HTML );
	}


	/**
	 * @return The StringBuffer used to create the document.
	 */
	public IBuffer getStringBuffer() {
		return buffer;
	}

	/**
	 * @return The String representation of the XML for this XMLStringBuffer.
	 */
	public String toXML()
	{
		return StringEscapeUtils.unescapeXml( buffer.toString() );
	}


	public String toXMLHtml()
	{
		return buffer.toString();
	}

	public void addComment( String comment )
	{
		buffer.append( currentIndent ).append( "<!-- " + comment.replaceAll( "[-]{2,}", "-" ) + " -->\n" );
	}

	public String getCurrentIndent()
	{
		return currentIndent;
	}

	private Properties createProperties(String[] attributes) {
		Properties result = new Properties();
		if (attributes == null) {
			return result;
		}
		if (attributes.length % 2 != 0) {
			throw new IllegalArgumentException("Arguments 'attributes' length must be even. Actual: " + attributes.length);
		}
		for (int i = 0; i < attributes.length; i += 2) {
			result.put(attributes[i], attributes[i + 1]);
		}
		return result;
	}

	//endregion


	//region XMLStringBuffer - Inner Classes Implementation Section

	private class Tag
	{
		public final String tagName;

		public final String indent;

		public final Properties properties;

		public Tag( String ind, String n, Properties p )
		{
			tagName = n;
			indent = ind;
			properties = p;
		}

		@Override
		public String toString()
		{
			return tagName;
		}
	}

	//endregion

}
