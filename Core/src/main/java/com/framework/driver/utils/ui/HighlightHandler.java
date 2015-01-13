package com.framework.driver.utils.ui;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: org.framework.webdriver.driver.highlight
 *
 * Name   : HighlightHandler
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2014-12-21
 *
 * Time   : 17:27
 */

public interface HighlightHandler
{

	/**
	 * Get locator highlighting.
	 *
	 * @return true if use locator highlighting.
	 */
	boolean isHighlight();

	/**
	 * Highlight and backup specified locator.
	 *
	 */
	public void highlight( final WebDriver driver, final WebElement elementFinder, HighlightStyle style );

	/**
	 * Un-highlight backed up styles.
	 */
	void unHighlight( final WebDriver driver );
}
