package com.framework.reporter;


public enum Templates
{
	INDEX_VM ( "index.html" ),

	COMMON_HEAD_VM ( "common.head.vm" ),

	ELEMENTS_VM ( "elements.vm" ),

	HIGHLIGHTS_VM( "highlights" ),

	TABS_VM( "tabs.vm" ),

	TEST_NG_XML( "testng.xml.html" ),

	SUITES_VM( "suites.html" );

	private String literal;

	private Templates( String literal )
	{
		this.literal = literal;
	}

	public String getLiteral()
	{
		return literal;
	}
}
