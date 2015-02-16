package com.framework.config;

import org.apache.commons.configuration.Configuration;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.config
 *
 * Name   : JFrameworkSystemProperty
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-01-17
 *
 * Time   : 15:12
 */

public enum FrameworkProperty
{
	//region JFrameworkSystemProperties - Enumeration values

	JFRAMEWORK_DATA_DIR,
	JFRAMEWORK_STORE_HTML_SOURCE,
	JFRAMEWORK_OUTPUT_DIRECTORY,
	JFRAMEWORK_BASE_REPORTS_DIRECTORY,
	JFRAMEWORK_BASE_LOGS_DIRECTORY,
	JFRAMEWORK_BASE_SCREENSHOTS_DIRECTORY,
	REPORT_DIRECTORY_PATTERN,
	REPORTER_VERSION,
	SUCCESS_RATE,

	/**
	 * Determine the jquery version used
	 */
	JQUERY_VERSION,

	/**
	 * Determine the jquery version used
	 */
	JQUERY_FILE_PATH,

	/**
	 * Enable JQuery integration.
	 * If set to true (the default), JQuery will be injected into any page that does not already have it.
	 * You can turn this option off for performance reasons if you are not using JQuery selectors.
	 */
	JFRAMEWORK_JQUERY_INTEGRATION_ENABLED,

	BROWSER_FIREFOX_FIREBUG_VERSION,

	BROWSER_FIREFOX_FIREPATH_VERSION,


	// ============================================================================================================ /

	PROPERTIES_LIST_DELIMITER,

	// ============================================================================================================ /

	/**
	 * How long webdriver waits for elements to appear by default, in milliseconds.
	 */
	WEBDRIVER_TIMEOUTS_IMPLICITLY_WAIT_MILLIS( Group.WEB_DRIVER, "" ),

	/**
	 * How long webdriver waits for page to be loaded
	 */
	WEBDRIVER_TIMEOUTS_PAGE_LOAD_TIMEOUT_MILLIS( Group.WEB_DRIVER, "" ),

	/**
	 * How long webdriver waits for script to be executed
	 */
	WEBDRIVER_TIMEOUTS_SCRIPT_TIMEOUT_MILLIS( Group.WEB_DRIVER, "" ),

	WEBDRIVER_CLEAR_ALL_COOKIES,

	WEBDRIVER_BASE_URL,

	/**
	 * Determine if the browser will be maximized on startup
	 */
	BROWSER_MAXIMIZE_AT_STARTUP( Group.WEB_DRIVER, "" ),

	/**
	 * Determine if the browser will be marked an object before click
	 */
	BROWSER_MARK_ON_CLICK,

	/**
	 * Determine if the browser will highlight an object before hovering
	 */
	BROWSER_BLINK_ON_HOVER,

	BROWSER_HIGHLIGHT_ON_CLEAR,


	/**
	 * If set, resize screenshots to this size to save space.
	 */
	JFRAMEWORK_RE_SIZED_IMAGE_WIDTH,

	/**
	 *  Re-dimension the browser to enable larger screenshots.
	 */
	JFRAMEWORK_BROWSER_HEIGHT,

	/**
	 *  Re-dimension the browser to enable larger screenshots.
	 */
	JFRAMEWORK_BROWSER_WIDTH(
			Group.WEB_DRIVER,
			"Re-dimension the browser to enable larger screenshots."
	),

	/**
	 * Whether or not enabling stepping feature
	 */
	JFRAMEWORK_ENABLE_STEPPING,

	JFRAMEWORK_SPRING_FRAMEWORK_LOCATIONS,

	JFRAMEWORK_SUITE_START_STYLE,
	JFRAMEWORK_SUITE_FAILED_STYLE,
	JFRAMEWORK_SUITE_SUCCESS_STYLE,
	;
	//endregion



	//region JFrameworkSystemProperties - Enumeration members

	private String propertyName;

	private Group group;

	private String longDescription;

	//endregion


	//region JFrameworkSystemProperties - Enumeration constructors

	private FrameworkProperty( final String propertyName )
	{
		this.propertyName = propertyName;
	}

	private FrameworkProperty( final Group group, final String desc )
	{
		this.propertyName = name().replaceAll( "_", "." ).toLowerCase();
		this.group = group;
		this.longDescription = desc;
	}

	private FrameworkProperty( final String propertyName, Group group, final String desc )
	{
		this.propertyName = propertyName;
		this.group = group;
		this.longDescription = desc;
	}

	private FrameworkProperty()
	{
		this.propertyName = name().replaceAll( "_", "." ).toLowerCase();
	}

	//endregion


	//region JFrameworkSystemProperties - Enumeration getters

	public String getPropertyName()
	{
		return propertyName;
	}

	public Group getGroup()
	{
		return group;
	}

	public String getLongDescription()
	{
		return longDescription;
	}

	//endregion


	@Override
	public String toString()
	{
		return propertyName;
	}

	public Object from( Configuration environmentVariables )
	{
		return environmentVariables.getProperty( getPropertyName() );
	}

	public Boolean from( Configuration environmentVariables, Boolean defaultValue )
	{
		return environmentVariables.getBoolean( getPropertyName(), defaultValue );
	}

	public BigDecimal from( Configuration environmentVariables, BigDecimal defaultValue )
	{
		return environmentVariables.getBigDecimal( getPropertyName(), defaultValue );
	}

	public BigInteger from( Configuration environmentVariables, BigInteger defaultValue )
	{
		return environmentVariables.getBigInteger( getPropertyName(), defaultValue );
	}

	public Byte from( Configuration environmentVariables, Byte defaultValue )
	{
		return environmentVariables.getByte( getPropertyName(), defaultValue );
	}

	public Double from( Configuration environmentVariables, Double defaultValue )
	{
		return environmentVariables.getDouble( getPropertyName(), defaultValue );
	}

	public Float from( Configuration environmentVariables, Float defaultValue )
	{
		return environmentVariables.getFloat( getPropertyName(), defaultValue );
	}

	public int from( Configuration environmentVariables, int defaultValue )
	{
		return environmentVariables.getInt( getPropertyName(), defaultValue );
	}

	public Integer from( Configuration environmentVariables, Integer defaultValue )
	{
		return environmentVariables.getInteger( getPropertyName(), defaultValue );
	}

	public List<Object> from( Configuration environmentVariables, List<?> defaultValue )
	{
		return environmentVariables.getList( getPropertyName(), defaultValue );
	}

	public Long from( Configuration environmentVariables, Long defaultValue )
	{
		return environmentVariables.getLong( getPropertyName(), defaultValue );
	}

	public Short from( Configuration environmentVariables, Short defaultValue )
	{
		return environmentVariables.getShort( getPropertyName(), defaultValue );
	}

	public String from( Configuration environmentVariables, String defaultValue )
	{
		return environmentVariables.getString( getPropertyName(), defaultValue );
	}

	public boolean isDefinedIn( Configuration environmentVariables )
	{
		return environmentVariables.containsKey( getPropertyName() );
	}



	//public static final int DEFAULT_HEIGHT = 700;

	//public static final int DEFAULT_WIDTH = 960;



	//static FrameworkSettings frameworkSettings = ( FrameworkSettings ) AppContextProxy.get().getBean( "frameworkSettings" );









	//============================================================================================================

	public enum Group
	{
		TEST_EXECUTION( 0, "Test Execution" ),

		WEB_DRIVER( 1, "Web Driver" ),

		REPORTER( 2, "Reporter" ),

		MAIL( 3, "Mail" ),

		FRAMEWORK( 4, "Framework" ),

		ADVANCED( 5, "Advanced" ),

		SYSTEM_ENVIRONMENT( 6, "System Environment" ),

		SOURCE_CONTROL( 7, "Source Control" ),

		REPORTS_PUBLISHER( 8, "Reports Publisher" );

		private int index;

		private String value;

		Group( int index, String value )
		{
			this.index = index;
			this.value = value;
		}

		public int getIndex()
		{
			return ( index );
		}

		public String getValue()
		{
			return ( value );
		}
	}
}
