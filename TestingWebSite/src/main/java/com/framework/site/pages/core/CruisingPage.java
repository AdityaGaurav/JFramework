package com.framework.site.pages.core;

import com.framework.driver.event.HtmlElement;
import com.framework.site.pages.BaseCarnivalPage;
import com.framework.testing.annotations.DefaultUrl;
import com.framework.utils.datetime.TimeConstants;
import org.apache.commons.lang3.BooleanUtils;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.site.pages.core
 *
 * Name   : CruisingPage
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-01-09
 *
 * Time   : 00:08
 */

@DefaultUrl( value = "/cruising.aspx", matcher = "contains()" )
public class CruisingPage extends BaseCarnivalPage
{

	//region CruisingPage - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( CruisingPage.class );

	private static final String LOGICAL_NAME = "Cruising Page";

	// ------------------------------------------------------------------------|
	// --- WEB-OBJECTS DEFINITIONS --------------------------------------------|
	// ------------------------------------------------------------------------|

	// ------------------------------------------------------------------------|
	// --- WEB-OBJECTS CACHING ------------------------------------------------|
	// ------------------------------------------------------------------------|

	private HtmlElement cBoxContent;


	//endregion


	//region CruisingPage - Constructor Methods Section

	public CruisingPage()
	{
		super( LOGICAL_NAME );
		validatePageInitialState();
	}

	//endregion


	//region CruisingPage - Initialization and Validation Methods Section

	protected void validatePageInitialState()
	{
		if( ! isVacationWithKidsDisplayed() )
		{
			logger.debug( "validating static elements for: <{}>, name:<{}>...", getQualifier(), getLogicalName() );
		}
	}

	//endregion


	//region CruisingPage - Service Methods Section




	//endregion


	//region CruisingPage - Business Methods Section

	public boolean isVacationWithKidsDisplayed()
	{
		boolean displayed = findCBoxContentDiv().isDisplayed();
		logger.info( "Determine if Vacation with Kids popup is displayed -> {}", BooleanUtils.toStringYesNo( displayed ) );
		return displayed;
	}

	public void vacationWithKidsClick( boolean yesOrNo )
	{
		if( isVacationWithKidsDisplayed() )
		{
			logger.info( "Clicking < {} > on vacation with kids popup", yesOrNo ? "Yes" : "No" );
			if( yesOrNo )
			{
				findCBoxContentDiv().findElement( By.id( "yesLink" ) ).click();
			}
			else
			{
				findCBoxContentDiv().findElement( By.id( "noLink" ) ).click();
			}
		}

		findCBoxContentDiv().waitToBeDisplayed( false, TimeConstants.FIVE_SECONDS );
	}

	//endregion


	//region CruisingPage - Element Finder Methods Section

	private HtmlElement findCBoxContentDiv()
	{
		if( null == cBoxContent )
		{
			final By findBy = By.id( "cboxContent" );
			cBoxContent = getDriver().findElement( findBy );
		}
		return cBoxContent;
	}


	//endregion

}
