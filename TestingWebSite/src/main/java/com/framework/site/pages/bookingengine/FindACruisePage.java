package com.framework.site.pages.bookingengine;

import com.framework.driver.exceptions.ApplicationException;
import com.framework.site.data.TripDurations;
import com.framework.site.pages.BaseCarnivalPage;
import com.google.common.base.MoreObjects;
import com.google.common.base.Throwables;
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

public class FindACruisePage extends BaseCarnivalPage
{

	//region FindACruisePage - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( FindACruisePage.class );

	private static final String LOGICAL_NAME = "Find a Cruise Page";

	private static final String URL_PATH = "/bookingengine/findacruise";

	private static final String PAGE_TITLE_KEY = "find.a.cruise.title";

	private TripDurations tripDuration;

	// ------------------------------------------------------------------------|
	// --- WEB-OBJECTS DEFINITIONS --------------------------------------------|
	// ------------------------------------------------------------------------|

	//endregion


	//region FindACruisePage - Constructor Methods Section

	public FindACruisePage( final WebDriver driver )
	{
		super( LOGICAL_NAME, driver );
	}

	public FindACruisePage( final WebDriver driver, Object ... args )
	{
		super( LOGICAL_NAME, driver );
		for( Object arg : args )
		{
			if( arg instanceof TripDurations )
			{
				this.tripDuration = ( TripDurations ) arg;
			}
		}
	}

	//endregion


	//region FindACruisePage - Initialization and Validation Methods Section

	@Override
	protected void initElements()
	{
		logger.debug( "validating static elements for: <{}>, name:<{}>...", getId(), getLogicalName() );

		try
		{

		}
		catch ( AssertionError ae )
		{
			Throwables.propagateIfInstanceOf( ae, ApplicationException.class );
			logger.error( "throwing a new WebObjectException on {}#initElements.", getClass().getSimpleName() );
			ApplicationException ex = new ApplicationException( pageDriver.getWrappedDriver(), ae.getMessage(), ae );
			ex.addInfo( "cause", "verification and initialization process for page " + getLogicalName() + " was failed." );
			throw ex;
		}
	}

	//endregion


	//region StateRoomsPage - Service Methods Section

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper( this )
				.add( "object id", getId() )
				.add( "page id", pageId() )
				.add( "logical name", getLogicalName() )
				.add( "pageName", pageName() )
				.add( "site region", getSiteRegion() )
				.add( "title", getTitle() )
				.add( "url", getCurrentUrl() )
				.omitNullValues()
				.toString();
	}

	//endregion


	//region FindACruisePage - Business Methods Section

	//endregion


	//region FindACruisePage - Element Finder Methods Section

	//endregion

}
