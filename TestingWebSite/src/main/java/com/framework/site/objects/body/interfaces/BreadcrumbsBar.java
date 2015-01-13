package com.framework.site.objects.body.interfaces;

import com.framework.driver.utils.ui.ExtendedBy;
import com.framework.site.pages.core.HomePage;
import org.openqa.selenium.By;

import java.util.List;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.site.objects.body.interfaces
 *
 * Name   : Breadcrumbs
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-01-10
 *
 * Time   : 02:26
 */

public interface BreadcrumbsBar
{
	static final String LOGICAL_NAME = "Breadcrumbs Bar";

	static final By ROOT_BY = By.className( "breadcrumb-bar" );

	Share share();

	Breadcrumbs breadcrumbs();

	interface Breadcrumbs
	{
		static final String LOGICAL_NAME = "Breadcrumbs";

		static final By ROOT_BY = ExtendedBy.composite( By.tagName( "ul" ), By.className( "breadcrumbs" ) );

		/**
		 * Clicks on the {@code Home} link.
		 *
		 * @return a new {@code HomePage} object
		 */
		HomePage navigateHome();

		HomePage navigateFirstChild();

		boolean isLastChildClickable();

		List<String> getNames();

		boolean breadcrumbItemExists( String itemName );
	}

	interface Share
	{
		static final String LOGICAL_NAME = "Breadcrumb Bar Share";

		static final By ROOT_BY = By.id( "" );
	}
}
