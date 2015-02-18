package com.framework.site.objects.footer.enums;

import org.apache.commons.lang3.StringUtils;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.site.objects.footer.enums
 *
 * Name   : FooterItem 
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-02-09 
 *
 * Time   : 10:17
 *
 */

public enum FooterItem
{
   	ABOUT_CARNIVAL,
	LEGAL_NOTICES,
	PRIVACY_POLICY,
	CAREERS,
	TRAVEL_PARTNERS,
	SITE_MAP,
	FACEBOOK,
	TWITTER,
	FUNVILLE,
	INSTAGRAM,
	PINTEREST;

	private String itemName;

	private FooterItem( final String itemName )
	{
		this.itemName = itemName;
	}

	private FooterItem()
	{
		this.itemName = StringUtils.capitalize( name().replaceAll( "_", " " ) );
	}

	public String getPropertyName()
	{
		return itemName;
	}

	@Override
	public String toString()
	{
		return itemName;
	}

}
