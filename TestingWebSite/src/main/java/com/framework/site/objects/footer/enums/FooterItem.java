package com.framework.site.objects.footer.enums;

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
   	ABOUT_CARNIVAL( "About Carnival" ),
	LEGAL_NOTICES( "Legal Notices" ),
	PRIVACY_POLICY( "Privacy Policy" ),
	CAREERS( "Careers" ),
	TRAVEL_PARTNERS( "Travel Partners" ),
	SITE_MAP( "Site Map" ),
	FACEBOOK( "Facebook" ),
	TWITTER( "Twitter" ),
	FUNVILLE( "Funville" ),
	INSTAGRAM( "Instagram" ),
	PINTEREST( "Pinterest" );

	private String itemName;

	private FooterItem( final String itemName )
	{
		this.itemName = itemName;
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
