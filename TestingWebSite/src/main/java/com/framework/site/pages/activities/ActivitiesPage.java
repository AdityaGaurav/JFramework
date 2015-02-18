package com.framework.site.pages.activities;

import com.framework.site.config.SiteProperty;
import com.framework.site.pages.BaseCarnivalPage;
import com.framework.testing.annotations.DefaultUrl;
import com.framework.utils.matchers.JMatchers;
import org.hamcrest.Matcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@DefaultUrl( value = "/Activities", matcher = "endsWith()" )
public class ActivitiesPage extends BaseCarnivalPage
{

	//region ActivitiesPage - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( ActivitiesPage.class );

	private static final String LOGICAL_NAME = "Shore Excursions Page";

	// ------------------------------------------------------------------------|
	// --- WEB-OBJECTS DEFINITIONS --------------------------------------------|
	// ------------------------------------------------------------------------|

	//endregion


	//region ActivitiesPage - Constructor Methods Section

	public ActivitiesPage()
	{
		super( LOGICAL_NAME );
	}

	//endregion


	//region ActivitiesPage - Initialization and Validation Methods Section


	protected void validatePageInitialState()
	{
		logger.debug( "validating static elements for: <{}>, name:<{}>...", getQualifier(), getLogicalName() );
	}

	@Override
	protected void validatePageTitle()
	{
		String title = ( String ) SiteProperty.SHORE_EXCURSIONS_TITLE.fromContext();
		final Matcher<String> EXPECTED_TITLE = JMatchers.equalToIgnoringCase( title );
		final String REASON = String.format( "Asserting \"%s\" page's title", LOGICAL_NAME );

		getDriver().assertThat( REASON, getTitle(), EXPECTED_TITLE );
	}

	//endregion


	//region ActivitiesPage - Service Methods Section



	//endregion


	//region ActivitiesPage - Business Methods Section

	//endregion


	//region ActivitiesPage - Element Finder Methods Section

	//endregion

}
