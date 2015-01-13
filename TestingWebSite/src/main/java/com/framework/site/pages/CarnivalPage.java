package com.framework.site.pages;

import com.framework.driver.exceptions.PageObjectException;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.site.pages
 *
 * Name   : CarnivalPage
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-01-06
 *
 * Time   : 20:28
 */

public abstract class CarnivalPage extends BaseCarnivalPage
{

	//region AbstractCarnivalPage - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( CarnivalPage.class );

	// ------------------------------------------------------------------------|
	// --- WEB-OBJECTS DEFINITIONS --------------------------------------------|
	// ------------------------------------------------------------------------|


	//endregion


	//region AbstractCarnivalPage - Constructor Methods Section

	protected CarnivalPage( final String logicalName, final WebDriver driver )
	{
		super( logicalName, driver );

		try
		{
			validatePageUrl();
		}
		catch ( Throwable e )
		{
			logger.error( "throwing a new PageObjectException on {}#constructor.", getClass().getSimpleName() );
			PageObjectException poe = new PageObjectException( driver, e.getMessage(), e );
			poe.addInfo( "causing flow", "trying to create a new CarnivalPage -> " + logicalName );
			throw poe;
		}

	}

	//endregion


	//region AbstractCarnivalPage - Initialization and Validation Methods Section

	/**
	 * Validates a new page object url.
	 * <p>this method will be implemented on all extensions classes.</p>
	 *
	 * @throws com.framework.driver.exceptions.ApplicationException in case the url does not match
	 *
	 * @see com.framework.driver.utils.ui.WaitUtil#urlMatches(org.hamcrest.Matcher)
	 */
	protected abstract void validatePageUrl();

	//endregion


	//region AbstractCarnivalPage - Service Methods Section


	//endregion


	//region AbstractCarnivalPage - Element Finder Methods Section




	//endregion

}
