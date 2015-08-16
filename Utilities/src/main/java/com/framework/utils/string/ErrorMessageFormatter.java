package com.framework.utils.string;

import com.google.common.base.Optional;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.utils.string
 *
 * Name   : ErrorMessageFormatter
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-01-17
 *
 * Time   : 11:40
 */

public class ErrorMessageFormatter
{

	//region ErrorMessageFormatter - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( ErrorMessageFormatter.class );

	private final Optional<String> originalMessage;

	Pattern LEADING_EXCEPTIONS = Pattern.compile("^<?([\\w]*\\.[\\w]*)+:");

	//endregion


	//region ErrorMessageFormatter - Constructor Methods Section

	public ErrorMessageFormatter( String originalMessage )
	{
		this.originalMessage = Optional.fromNullable( originalMessage );
	}

	//endregion


	//region ErrorMessageFormatter - Public Methods Section

	/**
	 * Returns the first line only of the error message.
	 * This avoids polluting the UI with unnecessary details such as browser versions and so forth.
	 *
	 * @return  the first line only of the error message.
	 */
	public String getShortErrorMessage()
	{
		return escapedHtml( ( originalMessage.isPresent() ) ? getUsefulMessageSummary() : "" );
	}


	//endregion


	//region ErrorMessageFormatter - Private Function Section

	private String escapedHtml( String message )
	{
		return StringEscapeUtils.escapeHtml4( message );
	}

	private String getUsefulMessageSummary()
	{
		if ( isHamcrestException() )
		{
			return compressedHamcrestMessage();
		}
		else
		{
			return extractFirstLine();
		}
	}

	private String compressedHamcrestMessage()
	{
		String messageWithoutExceptions = removeLeadingExceptionFrom( originalMessage.get() );
		String words[] = StringUtils.split( messageWithoutExceptions );
		return StringUtils.join( words, " " );
	}

	private String extractFirstLine()
	{
		String lines[] = originalMessage.get().split( "\\r?\\n" );
		return StringUtils.trimToEmpty( replaceDoubleQuotesIn( firstNonExceptionLineIn( lines ) ) );
	}

	private String firstNonExceptionLineIn( String lines[] )
	{
		for ( String candidateLine : lines )
		{
			String lineWithoutExceptions = removeLeadingExceptionFrom( candidateLine );
			if ( StringUtils.isNotEmpty( lineWithoutExceptions ) )
			{
				return lineWithoutExceptions;
			}
		}
		return "";
	}

	private String removeLeadingExceptionFrom( final String message )
	{
		Matcher matcher = LEADING_EXCEPTIONS.matcher( message );

		if ( matcher.find() )
		{
			return matcher.replaceFirst( "" );
		}
		else
		{
			return message;
		}
	}

	private boolean isHamcrestException()
	{
		return originalMessage.get().contains( "Expected:" );
	}

	private String replaceDoubleQuotesIn( final String message )
	{
		return message.replaceAll( "\"", "'" );
	}

	//endregion

}
