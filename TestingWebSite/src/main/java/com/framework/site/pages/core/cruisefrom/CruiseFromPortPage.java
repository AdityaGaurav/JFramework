package com.framework.site.pages.core.cruisefrom;

import com.framework.site.data.DeparturePorts;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.site.pages.core
 *
 * Name   : CruiseToPage
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-01-10
 *
 * Time   : 01:17
 */

public class CruiseFromPortPage
{

	//region CruiseToDestinationPage - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( CruiseFromPortPage.class );

	private static final String LOGICAL_NAME = "Cruise From Port Page";

	private static final String PAGE_TITLE_KEY = "cruise.from.port.title:";

	private static final String URL_PATH = "/cruise-from/${port}.aspx";

	private final DeparturePorts port;

	// ------------------------------------------------------------------------|
	// --- WEB-OBJECTS DEFINITIONS --------------------------------------------|
	// ------------------------------------------------------------------------|


	//endregion


	//region CruiseToDestinationPage - Constructor Methods Section

	public CruiseFromPortPage( final WebDriver driver, DeparturePorts port )
	{
		//super( LOGICAL_NAME, driver );
		this.port = port;
	}

	//endregion


	//region CruiseToDestinationPage - Initialization and Validation Methods Section

	protected void initElements()
	{
//		logger.debug( "validating static elements for: <{}>, name:<{}>...", getId(), getLogicalName() );
//
//		try
//		{
//
//		}
//		catch ( AssertionError ae )
//		{
//			Throwables.propagateIfInstanceOf( ae, ApplicationException.class );
//			logger.error( "throwing a new WebObjectException on {}#validatePageInitialState.", getClass().getSimpleName() );
//			ApplicationException ex = new ApplicationException( pageDriver.getDriver(), ae.getMessage(), ae );
//			ex.addInfo( "cause", "verification and initialization process for page " + getLogicalName() + " was failed." );
//			throw ex;
//		}
	}

	protected void validatePageTitle()
	{

	}

	//	@Override
//	public void assertURL( final URL url ) throws AssertionError
//	{
//
//	}

	//endregion


	//region CruiseToDestinationPage - Service Methods Section



	//endregion


	//region CruiseToDestinationPage - Business Methods Section

	//endregion


	//region CruiseToDestinationPage - Element Finder Methods Section

	//endregion

}
