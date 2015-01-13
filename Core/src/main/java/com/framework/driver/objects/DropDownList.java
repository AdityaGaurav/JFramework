package com.framework.driver.objects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.driver.objects
 *
 * Name   : DropDownList
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-01-05
 *
 * Time   : 20:12
 */

public class DropDownList extends AbstractElementObject
{

	//region DropDownList - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( DropDownList.class );

	//endregion


	//region DropDownList - Constructor Methods Section

	public DropDownList( final WebDriver driver, final WebElement webElement )
	{
		super( driver, webElement );
	}

	//endregion


	//region DropDownList - Public Methods Section

	//endregion


	//region DropDownList - Protected Methods Section

	//endregion

}
