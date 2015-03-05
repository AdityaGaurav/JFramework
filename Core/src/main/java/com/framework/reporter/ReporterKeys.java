package com.framework.reporter;


public enum ReporterKeys
{
	META_KEY ( "meta" ),

	LOGGER_KEY ( "logger" ),

	CONSTANTS_KEY ( "const" ),

	JAVA_VERSION_KEY( "java_version" ),

	USER_NAME_KEY( "user_name" ),

	TIMESTAMP_KEY ( "timestamp" ),

	OS_VERSION_KEY ( "os_version" ),

	USER_TIMEZONE_KEY( "user_timezone" ),

	REPORTER_VERSION( "reporter_version" ),

	SCENARIO_UTIL_KEY( "scenario" ),

	SUITES_KEY( "suites" ),

	TEST_CONTEXT_KEY( "testContext" ),

	XML_SUITE_NAMES_KEY( "xml_suite_names" ),

	XML_SUITE_FILES_KEY( "xml_suite_files" ),

	XML_SUITE_CONTENTS_KEY( "xml_suite_contents" );

	private String literal;

	private ReporterKeys( String literal )
	{
		this.literal = literal;
	}

	public String getLiteral()
	{
		return literal;
	}
}
