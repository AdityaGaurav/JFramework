package com.framework.site.pages.bookingengine;

import com.framework.site.config.SiteProperty;
import com.framework.site.data.TripDurations;
import com.framework.site.pages.BaseCarnivalPage;
import com.framework.testing.annotations.DefaultUrl;
import com.framework.utils.matchers.JMatchers;
import org.hamcrest.Matcher;
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

@DefaultUrl( value = "/bookingengine/findacruise", matcher = "endsWith()" )
public class FindACruisePage extends BaseCarnivalPage
{

	//region FindACruisePage - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( FindACruisePage.class );

	private static final String LOGICAL_NAME = "Find a Cruise Page";

	private static final String PAGE_TITLE_KEY = "find.a.cruise.title";

	// ------------------------------------------------------------------------|
	// --- WEB-OBJECTS DEFINITIONS --------------------------------------------|
	// ------------------------------------------------------------------------|

	//endregion


	//region FindACruisePage - Constructor Methods Section

	public FindACruisePage()
	{
		super( LOGICAL_NAME );
	}

	public FindACruisePage( Object ... args )
	{
		super( LOGICAL_NAME );
		for( Object arg : args )
		{
			if( arg instanceof TripDurations )
			{
				//this.tripDuration = ( TripDurations ) arg;
			}
		}
	}

	//endregion


	//region FindACruisePage - Initialization and Validation Methods Section

	@Override
	protected void validatePageInitialState()
	{
		logger.debug( "validating static elements for: <{}>, name:<{}>...", getQualifier(), getLogicalName() );
	}


	@Override
	protected void validatePageTitle()
	{
		String title = ( String ) SiteProperty.FIND_A_CRUISE_TITLE.fromContext();
		final Matcher<String> EXPECTED_TITLE = JMatchers.equalToIgnoringCase( title );
		final String REASON = String.format( "Asserting \"%s\" page's title", LOGICAL_NAME );

		getDriver().assertThat( REASON, getTitle(), EXPECTED_TITLE );
	}

	//endregion


	//region StateRoomsPage - Service Methods Section


	//endregion


	//region FindACruisePage - Business Methods Section

	//endregion


	//region FindACruisePage - Element Finder Methods Section

	//endregion

}
