package com.framework.site.config;

import com.framework.utils.spring.AppContextProxy;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.lang3.StringUtils;

import java.util.Locale;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.site.config
 *
 * Name   : SiteProperty 
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-01-20 
 *
 * Time   : 23:00
 *
 */

public enum SiteProperty
{

	//region SiteProperty - Enumeration values

	//region  SiteProperty - properties defined in site.properties
	
	US_PROD_URL( "US Production URL" ),
	US_UAT0_URL( "US User Acceptance Testing URL" ),
	US_UAT2_URL( "US User Acceptance Testing 2 URL" ),
	US_SYS1_URL( "US System 1 URL" ),
	US_SYS2_URL( "US System 2 URL" ),
	US_SYS3_URL( "US System 3 URL" ),
	US_SYS4_URL( "US System 4 URL" ),

	UK_PROD_URL( "UK Production URL" ),
	UK_UAT2_URL( "UK User Acceptance Testing2  URL" ),
	UK_SYS1_URL( "UK System 1 URL" ),

	AU_PROD_URL( "AU Production URL" ),
	AU_UAT2_URL( "AU User Acceptance Testing 2 URL" ),

	US_STG_HST_VALUE( "US Staging hidden input value input#hst" ),
	UK_STG_HST_VALUE( "UK Staging hidden input value input#hst" ),
	AU_STG_HST_VALUE( "AU Staging hidden input value input#hst" ),

	CURRENT_ENVIRONMENT_ID( "One of the environments ids, defined in site.properties" ),
	CURRENT_DRIVER_ID( "The driver id configuration to be used. defined in drivers.xml" ),
	SUPPORTED_LOCALES( "A list of supported locales by the AUT"),
	AUTO_STARTUP_HOMEPAGE( "browser Will automatically start on the environment/locale home page." ),

	//endregion

	//region  SiteProperty - properties defined in locale/messages.properties

	IN_ROOM_GIFTS,
	LEARN_MENU_ITEMS,
	EXPLORE_MENU_ITEMS,
	PLAN_MENU_ITEMS,
	MANAGE_MENU_ITEMS,
	HEADER_PHONE_NUMBER,
	HEADER_TOP_DESTINATIONS,

	SHIPS_COUNT( "The number of ship expected on the CruiseShipPage. this property is localized"),
	COMPARE_CAPABILITY( "A boolean value that determines if the ships page has compare capability. AU is currently not" ),
	MAX_MENU_SUB_ITEMS,
	FILTER_SOON_TEXT,
	SITE_CORE_SHIP_FEATURE_IDS,
	COMPARE_SHIPS_LEGEND_KEYS,

	//endregion

	;

	//endregion


	//region SiteProperty - Enumeration members

	private final String propertyName;

	private final String description;

	//endregion


	//region SiteProperty - Enumeration constructors

	private SiteProperty( final String description )
	{
		this.description = description;
		this.propertyName = name().replaceAll( "_", "." ).toLowerCase();
	}

	private SiteProperty( final String propertyName, final String description )
	{
		this.propertyName = propertyName;
		this.description = description;
	}

	private SiteProperty()
	{
		this.propertyName = name().replaceAll( "_", "." ).toLowerCase();
		this.description = StringUtils.EMPTY;
	}

	//endregion


	//region SiteProperty - Enumeration getters

	public String getPropertyName()
	{
		return propertyName;
	}

	public String getDescription()
	{
		return description;
	}

	@Override
	public String toString()
	{
		return propertyName;
	}

	public Object from( Configuration environmentVariables )
	{
		return environmentVariables.getProperty( getPropertyName() );
	}

	public Object fromContext( Object[] args )
	{
		Locale locale = SiteSessionManager.get().getCurrentLocale();
		return AppContextProxy.get().getMessage( getPropertyName(), args, locale );
	}

	public Object fromContext()
	{
		return fromContext( null );
	}

	public String from( Configuration environmentVariables, String defaultValue )
	{
		String value = environmentVariables.getString( getPropertyName() );

		if ( StringUtils.isEmpty( value ) )
		{
			return defaultValue;
		}
		else
		{
			return value;
		}
	}

	//endregion
}
