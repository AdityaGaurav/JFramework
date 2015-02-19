package com.framework.utils.string;

import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.StringUtils;

import java.util.List;


/**
 * text-art was taken from <a href="http://patorjk.com/taag/">patorjk.com</a>
 */
public class TextArt
{


	public static final List<String> SUITE_STARTED_HEADINGS = ImmutableList.of(
			"\n----------------------\n" + "---- SUITE STARTED ----\n" + "----------------------\n",
			"\n\n######  ##     ## #### ######## ########     ######  ########    ###    ########  ######## ######## ########\n" +
					"##    ## ##     ##  ##     ##    ##          ##    ##    ##      ## ##   ##     ##    ##    ##       ##     ##\n" +
					"##       ##     ##  ##     ##    ##          ##          ##     ##   ##  ##     ##    ##    ##       ##     ##\n" +
					" ######  ##     ##  ##     ##    ######       ######     ##    ##     ## ########     ##    ######   ##     ##\n" +
					"      ## ##     ##  ##     ##    ##                ##    ##    ######### ##   ##      ##    ##       ##     ##\n" +
					"##    ## ##     ##  ##     ##    ##          ##    ##    ##    ##     ## ##    ##     ##    ##       ##     ##\n" +
					" ######   #######  ####    ##    ########     ######     ##    ##     ## ##     ##    ##    ######## ######## \n",
			"\n\n  ▄████████ ███    █▄   ▄█      ███        ▄████████         ▄████████     ███        ▄████████    ▄████████     ███        ▄████████ ████████▄\n" +
					"  ███    ███ ███    ███ ███  ▀█████████▄   ███    ███        ███    ███ ▀█████████▄   ███    ███   ███    ███ ▀█████████▄   ███    ███ ███   ▀███\n" +
					"  ███    █▀  ███    ███ ███▌    ▀███▀▀██   ███    █▀         ███    █▀     ▀███▀▀██   ███    ███   ███    ███    ▀███▀▀██   ███    █▀  ███    ███\n" +
					"  ███        ███    ███ ███▌     ███   ▀  ▄███▄▄▄            ███            ███   ▀   ███    ███  ▄███▄▄▄▄██▀     ███   ▀  ▄███▄▄▄     ███    ███\n" +
					"▀███████████ ███    ███ ███▌     ███     ▀▀███▀▀▀          ▀███████████     ███     ▀███████████ ▀▀███▀▀▀▀▀       ███     ▀▀███▀▀▀     ███    ███\n" +
					"         ███ ███    ███ ███      ███       ███    █▄                ███     ███       ███    ███ ▀███████████     ███       ███    █▄  ███    ███\n" +
					"   ▄█    ███ ███    ███ ███      ███       ███    ███         ▄█    ███     ███       ███    ███   ███    ███     ███       ███    ███ ███   ▄███\n" +
					" ▄████████▀  ████████▀  █▀      ▄████▀     ██████████       ▄████████▀     ▄████▀     ███    █▀    ███    ███    ▄████▀     ██████████ ████████▀ \n" +
					"                                                                                                   ███    ███                                    \n"
    );

	public static final List<String> SUITE_FAILED_HEADINGS = ImmutableList.of(
			"\n----------------------\n" + "---- SUITE FAILED ----\n" + "----------------------\n",
			"\n\n ######  ##     ## #### ######## ########    ########    ###    #### ##       ######## ######## \n" +
					"##    ## ##     ##  ##     ##    ##          ##         ## ##    ##  ##       ##       ##     ##\n" +
					"##       ##     ##  ##     ##    ##          ##        ##   ##   ##  ##       ##       ##     ##\n" +
					" ######  ##     ##  ##     ##    ######      ######   ##     ##  ##  ##       ######   ##     ##\n" +
					"      ## ##     ##  ##     ##    ##          ##       #########  ##  ##       ##       ##     ##\n" +
					"##    ## ##     ##  ##     ##    ##          ##       ##     ##  ##  ##       ##       ##     ##\n" +
					" ######   #######  ####    ##    ########    ##       ##     ## #### ######## ######## ######## \n" ,

			"\n\n   ▄████████ ███    █▄   ▄█      ███        ▄████████         ▄████████    ▄████████  ▄█   ▄█          ▄████████ ████████▄\n" +
					"  ███    ███ ███    ███ ███  ▀█████████▄   ███    ███        ███    ███   ███    ███ ███  ███         ███    ███ ███   ▀███ \n" +
					"  ███    █▀  ███    ███ ███▌    ▀███▀▀██   ███    █▀         ███    █▀    ███    ███ ███▌ ███         ███    █▀  ███    ███ \n" +
					"  ███        ███    ███ ███▌     ███   ▀  ▄███▄▄▄           ▄███▄▄▄       ███    ███ ███▌ ███        ▄███▄▄▄     ███    ███ \n" +
					"▀███████████ ███    ███ ███▌     ███     ▀▀███▀▀▀          ▀▀███▀▀▀     ▀███████████ ███▌ ███       ▀▀███▀▀▀     ███    ███ \n" +
					"         ███ ███    ███ ███      ███       ███    █▄         ███          ███    ███ ███  ███         ███    █▄  ███    ███ \n" +
					"   ▄█    ███ ███    ███ ███      ███       ███    ███        ███          ███    ███ ███  ███▌    ▄   ███    ███ ███   ▄███ \n" +
					" ▄████████▀  ████████▀  █▀      ▄████▀     ██████████        ███          ███    █▀  █▀   █████▄▄██   ██████████ ████████▀  \n" +
					"                                                                                          ▀                                 \n"
																			  );

	public static final List<String> SUITE_PASSED_HEADINGS = ImmutableList.of(
			"\n----------------------\n" + "---- SUITE PASSED ----\n" + "----------------------\n",
			"\n\n ######  ##     ## #### ######## ########    ########     ###     ######   ######  ######## ########\n" +
					"##    ## ##     ##  ##     ##    ##          ##     ##   ## ##   ##    ## ##    ## ##       ##     ##\n" +
					"##       ##     ##  ##     ##    ##          ##     ##  ##   ##  ##       ##       ##       ##     ##\n" +
					" ######  ##     ##  ##     ##    ######      ########  ##     ##  ######   ######  ######   ##     ##\n" +
					"      ## ##     ##  ##     ##    ##          ##        #########       ##       ## ##       ##     ##\n" +
					"##    ## ##     ##  ##     ##    ##          ##        ##     ## ##    ## ##    ## ##       ##     ##\n" +
					" ######   #######  ####    ##    ########    ##        ##     ##  ######   ######  ######## ######## \n",
			"\n\n   ▄████████ ███    █▄   ▄█      ███        ▄████████         ▄███████▄    ▄████████    ▄████████    ▄████████    ▄████████ ████████▄\n" +
					"  ███    ███ ███    ███ ███  ▀█████████▄   ███    ███        ███    ███   ███    ███   ███    ███   ███    ███   ███    ███ ███   ▀███ \n" +
					"  ███    █▀  ███    ███ ███▌    ▀███▀▀██   ███    █▀         ███    ███   ███    ███   ███    █▀    ███    █▀    ███    █▀  ███    ███ \n" +
					"  ███        ███    ███ ███▌     ███   ▀  ▄███▄▄▄            ███    ███   ███    ███   ███          ███         ▄███▄▄▄     ███    ███ \n" +
					"▀███████████ ███    ███ ███▌     ███     ▀▀███▀▀▀          ▀█████████▀  ▀███████████ ▀███████████ ▀███████████ ▀▀███▀▀▀     ███    ███ \n" +
					"         ███ ███    ███ ███      ███       ███    █▄         ███          ███    ███          ███          ███   ███    █▄  ███    ███ \n" +
					"   ▄█    ███ ███    ███ ███      ███       ███    ███        ███          ███    ███    ▄█    ███    ▄█    ███   ███    ███ ███   ▄███ \n" +
					" ▄████████▀  ████████▀  █▀      ▄████▀     ██████████       ▄████▀        ███    █▀   ▄████████▀   ▄████████▀    ██████████ ████████▀  \n" +
					"                                                                                                                                       \n"

	);

	private static final int MAX_LENGTH = 100;

	private static final String HEADER_FOOTER = "\n" + StringUtils.repeat( '#', MAX_LENGTH );

	private static final String BLANK_SPACE = "\n#" + StringUtils.repeat( ' ', MAX_LENGTH - 2 ) + "#";

	private static final String SUITE = " SUITE";

	private static final String TEST = " TEST-CONTEXT";

	private static final String TEST_CASE = " TEST CASE";

	private static final String STEP = " STEP";

	private static final String FAILED_MSG = " FAILED ";

	private static final String STARTED_MSG = " STARTED ";

	private static final String PASSED_MSG = " SUCCESS ";

	private static final String FAILED_PERCENTAGE_MSG = " WITH SUCCESS PERCENTAGE ";

	public static String getSuiteStart( String testName )
	{
		StringBuilder sb = new StringBuilder( HEADER_FOOTER ).append( BLANK_SPACE );
		String msg = indentMessage( SUITE, testName, STARTED_MSG );
		sb.append( msg ).append( BLANK_SPACE ).append( HEADER_FOOTER );
		return sb.toString();
	}

	public static String getSuiteEnd( String testName, boolean failed )
	{
		StringBuilder sb = new StringBuilder( HEADER_FOOTER ).append( BLANK_SPACE );
		String msg = indentMessage( SUITE, testName, failed ? FAILED_MSG : PASSED_MSG );
		sb.append( msg ).append( BLANK_SPACE ).append( HEADER_FOOTER );
		return sb.toString();
	}

	public static String getTestStart( String testName )
	{
		StringBuilder sb = new StringBuilder( HEADER_FOOTER ).append( BLANK_SPACE );
		String msg = indentMessage( TEST, testName, STARTED_MSG );
		sb.append( msg ).append( BLANK_SPACE ).append( HEADER_FOOTER );
		return sb.toString();
	}

	public static String getTestCaseStart( String testCaseName )
	{
		StringBuilder sb = new StringBuilder( HEADER_FOOTER ).append( BLANK_SPACE );
		String msg = indentMessage( TEST_CASE, testCaseName, STARTED_MSG );
		sb.append( msg ).append( BLANK_SPACE ).append( HEADER_FOOTER );
		return sb.toString();
	}

	public static String getStepStart( String stepNumber )
	{
		StringBuilder sb = new StringBuilder( HEADER_FOOTER ).append( BLANK_SPACE );
		String msg = indentMessage( STEP, stepNumber, STARTED_MSG );
		sb.append( msg ).append( BLANK_SPACE ).append( HEADER_FOOTER );
		return sb.toString();
	}

	//### ------------------------------ TEST CASE: "1.000000" ENDED ------------------------------------#
	public static String getStepEndFailed( String stepNumber )
	{
		StringBuilder sb = new StringBuilder( "\n\n### " );
		String msg = indentSimpleEndMessage( STEP + " ", stepNumber, FAILED_MSG );
		sb.append( msg ).append( " ###\n" );
		return sb.toString();
	}

	public static String getStepEndSuccess( String stepNumber )
	{
		StringBuilder sb = new StringBuilder( "\n\n### " );
		String msg = indentSimpleEndMessage( STEP + " ", stepNumber, PASSED_MSG );
		sb.append( msg ).append( " ###\n" );
		return sb.toString();
	}

	public static String getTestCasePassed( String testCaseName )
	{
		return null;
	}

	public static String getTestPassed( String testName )
	{
		return null;
	}

	public static String getTestCaseFailed( String testCaseName )
	{
		return null;
	}

	public static String getTestFailed( String testName )
	{
		return null;
	}

	public static String getTestCaseFailedWithSuccessPercentage( String testCaseName )
	{
		return null;
	}

	private static String indentMessage( String prefix, String msg, String postFix )
	{
		final String SEPARATOR = ": ";
		int totalLen = prefix.length() + msg.length() + postFix.length();
		if( totalLen > MAX_LENGTH )
		{
			return "# " + prefix + "\"" + msg + " #"; //todo: calculations
		}
		else
		{
			int startAt = ( MAX_LENGTH - totalLen ) / 2;
			String msgLine =  "\n#" + StringUtils.repeat( ' ', startAt - 3 )
					+ prefix
					+ SEPARATOR
					+ "\"" + msg + "\""
					+ postFix
					+ StringUtils.repeat( ' ', startAt - 3 )
			        + " #";
			if( msgLine.length() > ( MAX_LENGTH + 1 ) )
			{
				return "\n#" + StringUtils.repeat( ' ', startAt - 3 )
						+ prefix
						+ SEPARATOR
						+ "\"" + msg + "\""
						+ postFix
						+ StringUtils.repeat( ' ', startAt - 4 )
						+ " #";
			}

			return msgLine;
		}
	}

	private static String indentSimpleEndMessage( String prefix, String msg, String postFix )
	{
		int totalLen = prefix.length() + msg.length() + postFix.length();
		int startAt = ( MAX_LENGTH - totalLen ) / 2;
		String msgLine = StringUtils.repeat( '-', startAt - 5 )
				+ prefix + "\"" + msg + "\"" + postFix + StringUtils.repeat( '-', startAt - 5 );
		return msgLine;
	}


}
