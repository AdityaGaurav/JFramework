package com.framework.site.objects.header.interfaces;

import com.framework.driver.event.HtmlElement;
import com.framework.driver.exceptions.UrlNotAvailableException;
import com.framework.driver.extensions.jquery.By;
import com.framework.driver.objects.Link;
import com.framework.driver.objects.PageObject;
import com.framework.site.data.Destinations;
import com.framework.site.objects.header.enums.LevelOneMenuItem;
import com.framework.site.objects.header.enums.MenuItems;
import com.framework.site.pages.bookedguest.BookedGuestLogonPage;
import com.framework.site.pages.core.CruiseDealsPage;
import com.framework.site.pages.core.HomePage;
import com.framework.site.pages.core.cruiseto.CruiseToDestinationPage;

import java.util.List;
import java.util.Locale;



public interface Header
{
	public static final org.openqa.selenium.By ROOT_BY = By.id( "ccl-refresh-header" );

	//static final String SITE_REGION_SCRIPT = "return utag_data.site_region;";

	public static final String LOGICAL_NAME = "CCL Header";

	NotificationBar notificationBar();

	MessageBar messageBar();

	HeaderLinks headerLinks();

	HeaderSubscribe subscribe();

	HeaderBranding headerBranding();

	NavigationAdditional navigationAdditional();

	HtmlElement getContainer();

	//region Header - Child Interfaces under div.ccl-refresh-header > div.header-nav

	interface MessageBar
	{
		public static final org.openqa.selenium.By ROOT_BY = By.className( "message-bar" );

		static final String LOGICAL_NAME = "Message Bar";

		boolean isDisplayed();
	}

 	interface NotificationBar
	{
		public static final org.openqa.selenium.By ROOT_BY = By.className( "notif-bar" );

		static final String LOGICAL_NAME = "Notification Bar";

		boolean isDisplayed();
	}

	interface HeaderBranding
	{
		static final org.openqa.selenium.By ROOT_BY = By.cssSelector( "div.header-branding" );

		static final String LOGICAL_NAME = "Header Branding";

		String getLocalePhoneNumber();

		void clickSubscribeAndSave();

		CruiseDealsPage clickYourCruiseDeals();

		HomePage clickOnLogo();

		HomePage changeLocale( final Locale locale );

		boolean hasTopDestinations();

		boolean hasCurrencySelector();

		boolean isDisplayed();

		List<String> openLocalesList();

		void closeLocalesList();

		boolean isLocalesListOpened();

		Locale getDisplayedLocale();

		interface TopDestinations
		{
			static final String LOGICAL_NAME = "Header Branding Top Destinations";

			static final org.openqa.selenium.By ROOT_BY =
					By.xpath( "//a[@class='nav-tooltip-trigger' and @data-id='top-destinations']/.." );

			boolean isDisplayed();

			List<String> openTopDestinationsList();

			void closeTopDestinationsList();

			boolean isTopDestinationsListOpened();

			CruiseToDestinationPage clickLink( final Destinations destination ) throws UrlNotAvailableException;

			String getListTitle();
		}

		interface CurrencySelector
		{
			static final String LOGICAL_NAME = "Header Branding Currency Selector";

			static final org.openqa.selenium.By ROOT_BY = By.jQuerySelector( "#currencySelector" ).parent();

			boolean isDisplayed();
		}
	}

	interface HeaderSubscribe
	{
		static final org.openqa.selenium.By ROOT_BY = By.className( "header-subscribe" );

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
		static final org.openqa.selenium.By ROOT_BY = By.className( "header-links" );

		static final String LOGICAL_NAME = "Header Links";

		String[] getLinkNames();

		Link getLink( LevelOneMenuItem item );

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
		 * @see com.framework.site.objects.header.enums.LevelOneMenuItem
		 * @see com.framework.driver.event.EventListener#beforeClickOn(org.openqa.selenium.WebElement, org.openqa.selenium.WebDriver)
		 * @see com.framework.driver.event.EventListener#afterClickOn(org.openqa.selenium.WebElement, org.openqa.selenium.WebDriver)
		 */
		PageObject clickItem( LevelOneMenuItem item );

		/**
		 * Hovers on a top level menu item
		 *
		 * @param item a top level menu item
		 *
		 * @throws org.apache.commons.lang3.NotImplementedException when {@code menuItem} is not a top level menu item.
		 *
		 * @see com.framework.site.objects.header.HeaderLinksObject
		 * @see com.framework.site.objects.header.enums.LevelOneMenuItem
		 * @see com.framework.driver.event.EventListener#beforeHoverOn(org.openqa.selenium.WebElement, org.openqa.selenium.WebDriver)
		 * @see com.framework.driver.event.EventListener#afterHoverOn(org.openqa.selenium.WebElement, org.openqa.selenium.WebDriver)
		 */
		void hoverOnItem( LevelOneMenuItem item );

		BookedGuestLogonPage clickLogin();

		String getGreeting();

		Link getGreetingLink();

		Link getLoginLink();
	}

	//endregion

	interface NavigationAdditional
	{
		static final org.openqa.selenium.By ROOT_BY = By.className( "header-nav-additional" );

		static final String LOGICAL_NAME = "Additional Navigation";

		boolean isDisplayed();

		PageObject clickOnMenuItem( LevelOneMenuItem level1, MenuItems level2 );

		PageObject clickOnMenuItem( Link link, final MenuItems level2 );

		Link hoverOnMenuItem( LevelOneMenuItem level1, MenuItems level2 );

		void hoverOnMenuItem( Link link );

		HtmlElement getImage( Link link );

		String getDataDefaultImg( Link link );

		String getDataHoverImg( Link link );

		Link getLink( LevelOneMenuItem level1, MenuItems level2 );

		List<String> getChildMenuItemsNames( LevelOneMenuItem level1 );
	}
}
