package com.framework.site.pages.funville;

import com.framework.site.config.SiteProperty;
import com.framework.site.pages.BaseCarnivalPage;
import com.framework.testing.annotations.DefaultUrl;
import com.framework.utils.matchers.JMatchers;
import org.hamcrest.Matcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



@DefaultUrl( value = "/Funville/forums/", matcher = "endsWith()" )
public class ForumsPage extends BaseCarnivalPage
{

	//region ForumsPage - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( ForumsPage.class );

	private static final String LOGICAL_NAME = "Forums Page";

	// ------------------------------------------------------------------------|
	// --- WEB-OBJECTS DEFINITIONS --------------------------------------------|
	// ------------------------------------------------------------------------|

	//endregion


	//region ForumsPage - Constructor Methods Section

	public ForumsPage()
	{
		super( LOGICAL_NAME );
		validatePageInitialState();
	}

	//endregion


	//region ForumsPage - Initialization and Validation Methods Section

	protected void validatePageInitialState()
	{
		logger.debug( "validating static elements for: <{}>, name:<{}>...", getQualifier(), getLogicalName() );
	}

	@Override
	protected void validatePageTitle()
	{
		String title = ( String ) SiteProperty.FORUMS_TITLE.fromContext();
		final Matcher<String> EXPECTED_TITLE = JMatchers.equalToIgnoringCase( title );
		final String REASON = String.format( "Asserting \"%s\" page's title", LOGICAL_NAME );

		getDriver().assertThat( REASON, getTitle(), EXPECTED_TITLE );
	}

	//endregion


	//region ForumsPage - Service Methods Section


	//endregion


	//region ForumsPage - Business Methods Section

	//endregion


	//region ForumsPage - Element Finder Methods Section

	//endregion

}
