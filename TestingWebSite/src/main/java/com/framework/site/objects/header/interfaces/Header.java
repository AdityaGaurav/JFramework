package com.framework.site.objects.header.interfaces;

import com.framework.driver.objects.Link;
import com.framework.driver.objects.PageObject;
import com.framework.site.objects.header.LevelOneMenuItem;
import com.framework.site.objects.header.MenuItems;
import com.framework.site.pages.core.CruiseDealsPage;
import com.framework.site.pages.core.HomePage;
import com.framework.site.pages.core.cruiseto.CruiseToPage;
import org.openqa.selenium.By;

import java.util.List;
import java.util.Locale;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.site.objects.header.interfaces
 *
 * Name   : Header
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-01-05
 *
 * Time   : 23:11
 */

public interface Header
{
	public static final By ROOT_BY = By.id( "ccl-refresh-header" );

	static final String SITE_REGION_SCRIPT = "return utag_data.site_region;";

	public static final String LOGICAL_NAME = "CCL Header";

	NotificationBar notificationBar();

	MessageBar messageBar();

	HeaderLinks headerLinks();

	HeaderSubscribe subscribe();

	HeaderBranding headerBranding();

	NavigationAdditional navigationAdditional();

	// ------------------------------------------------------------------------|
	// --- HEADER-CHILD INTERFACES --------------------------------------------|
	// ------------------------------------------------------------------------|

	//region Header - Child Interfaces

	interface MessageBar
	{
		public static final By ROOT_BY = By.className( "message-bar" );

		static final String LOGICAL_NAME = "Message Bar";

		boolean isDisplayed();
	}

 	interface NotificationBar
	{
		public static final By ROOT_BY = By.className( "notif-bar" );

		static final String LOGICAL_NAME = "Notification Bar";

		boolean isDisplayed();
	}

	interface HeaderBranding
	{
		static final By ROOT_BY = By.className( "header-branding" );

		static final String LOGICAL_NAME = "Header Branding";

		String getLocalePhoneNumber();

		void clickSubscribeAndSave();

		CruiseDealsPage clickYourCruiseDeals();

		HomePage clickOnLogo();

		Locale getCurrentLocale();

		HomePage changeLocale( final Locale locale );

		boolean hasTopDestinations();

		boolean hasCurrencySelector();

		boolean isDisplayed();

		interface TopDestinations
		{
			static final String LOGICAL_NAME = "Header Branding Top Destinations";

			static final By ROOT_BY = By.xpath( ".//a[@class='nav-tooltip-trigger' and @data-id='top-destinations']" );

			CruiseToPage clickOnTopDestinationLink( int index );

			List<String> getTopDestinationsNames();

			boolean isDisplayed();

			void clickTopDestinations();
		}

		interface CurrencySelector
		{
			static final String LOGICAL_NAME = "Header Branding Currency Selector";

			static final By ROOT_BY = By.xpath( "//select[@id= 'currencySelector']/parent::li" );

			boolean isDisplayed();
		}
	}

	interface HeaderSubscribe
	{
		static final By ROOT_BY = By.className( "header-subscribe" );

		static final String LOGICAL_NAME = "Header Subscribe";

		static final String SIGN_UP_EMAIL_COOKIE_NAME = "ccl_signup_form_email";

		boolean isDisplayed();

		/**
		 * Subscribe for special offers procedure
		 * <ul>
		 *     <li>waits for form to be visible</li>
		 *     <li>send keys to email input box</li>
		 *     <li>submitting the form</li>
		 *     <li>if expecting {@code waitAlert} set to {@code true}
		 *     		<ul>
		 *            	<li>Wait for {@link org.openqa.selenium.Alert}</li>
		 *            	<li>accepting alert</li>
		 *            	<li>closing subscribe section</li>
		 *            	<li>wait for form to be hidden</li>
		 *     		</ul>
		 *     </li>
		 * </ul>
		 * @param email  		a string representing an email
		 * @param waitAlert     a flag that determines if procedure expected to fail
		 *
		 * @see org.openqa.selenium.Alert
		 */
		void subscribe( String email, boolean waitAlert );
	}

	interface HeaderLinks
	{
		static final By ROOT_BY = By.className( "header-links" );

		static final String LOGICAL_NAME = "Header Links";

		public static final String LEARN = "Learn";

		public static final String EXPLORE = "Explore";

		public static final String PLAN = "Plan";

		public static final String MANAGE = "Manage";

		boolean isDisplayed();

		String[] getLinkNames();

		/**
		 * Select ( clicks ) a top level menu item
		 *
		 * @param item a top level menu item
		 *
		 * @return a corresponding {@link com.framework.driver.objects.AbstractPageObject}
		 *
		 * @throws org.apache.commons.lang3.NotImplementedException when {@code menuItem} is not a top level menu item.
		 *
		 * @see com.framework.site.objects.header.HeaderLinksObject
		 * @see com.framework.site.objects.header.LevelOneMenuItem
		 * @see com.framework.driver.event.EventListener#beforeClickOn(org.openqa.selenium.WebElement, org.openqa.selenium.WebDriver)
		 * @see com.framework.driver.event.EventListener#afterClickOn(org.openqa.selenium.WebElement, org.openqa.selenium.WebDriver)
		 */
		PageObject selectMenuItem( LevelOneMenuItem item );

		/**
		 * Hovers on a top level menu item
		 *
		 * @param item a top level menu item
		 *
		 * @throws org.apache.commons.lang3.NotImplementedException when {@code menuItem} is not a top level menu item.
		 *
		 * @see com.framework.site.objects.header.HeaderLinksObject
		 * @see com.framework.site.objects.header.LevelOneMenuItem
		 * @see com.framework.driver.event.EventListener#beforeHoverOn(org.openqa.selenium.WebElement, org.openqa.selenium.WebDriver)
		 * @see com.framework.driver.event.EventListener#afterHoverOn(org.openqa.selenium.WebElement, org.openqa.selenium.WebDriver)
		 */
		void hoverOnMenuItem( LevelOneMenuItem item );
	}

	interface NavigationAdditional
	{
		static final By ROOT_BY = By.className( "header-nav-additional" );

		static final String LOGICAL_NAME = "Additional Navigation";

		boolean isDisplayed();

		public List<Link> getChildMenuItems( LevelOneMenuItem item );

		PageObject selectMenuItem( List<Link> links, final MenuItems menuItem );

		List<String> getChildMenuItemsNames( List<Link> links );

		Link hoverMenuItem( List<Link> links, final MenuItems menuItem );

//		PageObject selectMenuItem( final String menuItem );
//
//		void hoverOnMenuItem( final String menuItem );
//
//		String getMenuItemImageSrc( final String menuItem );
//
//		String getMenuItemImageHref( final String menuItem );
//
//		boolean isMenuItemVisible( final String menuItem );


	}

	//endregion
}
