package com.framework.site.objects.body.interfaces;

import com.framework.site.pages.core.HomePage;
import org.openqa.selenium.By;

import java.util.List;


/**
 * Feb-1-2015  breadcrumb-bar changed to div.toolbar.toolbar-cruisedeals-adjustment
 */

public interface BreadcrumbsBar
{
	static final String LOGICAL_NAME = "Cruise Deals Adjustment";

	static final By ROOT_BY = By.className( "breadcrumb-bar" );

	Share share();

	Breadcrumbs breadcrumbs();

	interface Breadcrumbs
	{
		static final String LOGICAL_NAME = "Breadcrumbs";

		static final org.openqa.selenium.By ROOT_BY = By.cssSelector( "ul.breadcrumbs" );

		/**
		 * Clicks on the {@code Home} link.
		 *
		 * @return a new {@code HomePage} object
		 */
		HomePage navigateHome();

		boolean isLastChildEnabled();

		String getLastChildName();

		List<String> getNames();

		boolean breadcrumbItemExists( String itemName );
	}

	interface Share
	{
		static final String LOGICAL_NAME = "Breadcrumb Bar Share";

		static final By ROOT_BY = By.className( "share" );
	}
}
