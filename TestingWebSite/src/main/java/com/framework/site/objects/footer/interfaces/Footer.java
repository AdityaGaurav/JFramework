package com.framework.site.objects.footer.interfaces;

import com.framework.driver.event.HtmlElement;
import com.framework.driver.objects.Link;
import com.framework.site.objects.footer.enums.FooterItem;
import com.google.common.collect.Table;
import org.openqa.selenium.By;

import java.net.URL;
import java.util.Map;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.site.objects.footer.interfaces
 *
 * Name   : Footer
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-01-06
 *
 * Time   : 16:34
 */

public interface Footer
{
	static final By ROOT_BY = By.className( "core_footer" );

	static final String LOGICAL_NAME = "CCL Footer";

	int getFooterSectionsCount();

	SubFooter subFooter();

	ZeroFooter zeroFooter();

	LinkList linkList();

	boolean isDisplayed();

	boolean hasSubFooter();

	boolean hasZeroFooter();

	void scrollIntoView();

	interface SubFooter
	{

		static final By ROOT_BY = By.className( "sub-footer" );

		static final String LOGICAL_NAME = "Sub-Footer";

		boolean isDisplayed();

		boolean itemExists( FooterItem item );

		Link getFooterLinkItem( FooterItem item );

		HtmlElement getTradeMark();

		URL getFacebookLikeRef();

		Map<String,String> getInfo();
	}

	interface ZeroFooter
	{
		static final By ROOT_BY = By.className( "zero-footer" );

		static final String LOGICAL_NAME = "Zero-Footer";

		boolean isDisplayed();
	}

	interface LinkList
	{
		static final By ROOT_BY = By.className( "link-lists" );

		static final String LOGICAL_NAME = "Link-List";

		boolean isDisplayed();

		Table<String,String,String> getInfo();
	}

}
