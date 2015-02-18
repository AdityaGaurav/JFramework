package com.framework.site.pages.core;

import com.framework.site.config.SiteProperty;
import com.framework.site.pages.BaseCarnivalPage;
import com.framework.testing.annotations.DefaultUrl;
import com.framework.utils.matchers.JMatchers;
import org.hamcrest.Matcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@DefaultUrl( value = "/vacation-planner.aspx", matcher = "contains()" )
public class VacationPlannerPage extends BaseCarnivalPage
{

	//region VacationPlannerPage - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( VacationPlannerPage.class );

	private static final String LOGICAL_NAME = "Vacation Planner Page";

	// ------------------------------------------------------------------------|
	// --- WEB-OBJECTS DEFINITIONS --------------------------------------------|
	// ------------------------------------------------------------------------|


	//endregion


	//region VacationPlannerPage - Constructor Methods Section

	public VacationPlannerPage()
	{
		super( LOGICAL_NAME );
		validatePageInitialState();
	}

	//endregion


	//region VacationPlannerPage - Initialization and Validation Methods Section

	protected void validatePageInitialState()
	{
		logger.debug( "validating static elements for: <{}>, name:<{}>...", getQualifier(), getLogicalName() );
	}

	@Override
	protected void validatePageTitle()
	{
		String title = ( String ) SiteProperty.VACATION_PLANNER_TITLE.fromContext();
		final Matcher<String> EXPECTED_TITLE = JMatchers.equalToIgnoringCase( title );
		final String REASON = String.format( "Asserting \"%s\" page's title", LOGICAL_NAME );

		getDriver().assertThat( REASON, getTitle(), EXPECTED_TITLE );
	}

	//endregion


	//region VacationPlannerPage - Service Methods Section



	//endregion


	//region VacationPlannerPage - Business Methods Section

	//endregion


	//region VacationPlannerPage - Element Finder Methods Section

	//endregion

}
