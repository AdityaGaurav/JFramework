package com.framework.driver.utils.ui;

import com.framework.driver.event.HtmlDriver;
import com.framework.driver.event.HtmlElement;

import java.util.Map;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: org.framework.webdriver.driver.highlight
 *
 * Name   : HighlightStyleBackup
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2014-12-21
 *
 * Time   : 17:35
 */

public class HighlightStyleBackup extends HighlightStyle
{

	//region HighlightStyleBackup - Variables Declaration and Initialization Section.

	private final HtmlElement element;

	//endregion


	//region HighlightStyleBackup - Constructor Methods Section

	/**
	 * Constructor.
	 *
	 * @param styles               style of highlighted element.
	 */
	public HighlightStyleBackup( Map<String, String> styles, HtmlElement element )
	{
		super( styles );
		this.element = element;
	}

	//endregion


	//region HighlightStyleBackup - Public Methods Section

	/**
	 * Restore style.
	 *
	 * @param driver        instance of WebDriver.
	 */
	public void restore( HtmlDriver driver )
	{
		doHighlight( driver, element );
	}

	//endregion

}
