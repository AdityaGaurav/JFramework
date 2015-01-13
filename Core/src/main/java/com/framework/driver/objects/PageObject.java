package com.framework.driver.objects;

import org.openqa.selenium.WebDriver;

import java.io.OutputStreamWriter;
import java.util.Set;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.driver.objects
 *
 * Name   : PageObject
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-01-05
 *
 * Time   : 12:35
 */

public interface PageObject
{
	String getLogicalName();

 	void savePageSource( OutputStreamWriter wr );

	String getCurrentUrl();

	String getTitle();

	void scrollToTop();

	String getWindowHandle();

	Set<String> getWindowHandles();

	WebDriver.TargetLocator switchTo();

	WebDriver.Navigation navigate();

	WebDriver.Options manage();

	WebDriver.Window window();

	String getId();
}
