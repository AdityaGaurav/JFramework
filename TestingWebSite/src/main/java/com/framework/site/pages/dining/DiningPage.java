package com.framework.site.pages.dining;

//import com.framework.asserts.JAssertions;

import com.framework.site.config.SiteProperty;
import com.framework.site.pages.BaseCarnivalPage;
import com.framework.testing.annotations.DefaultUrl;
import com.framework.utils.matchers.JMatchers;
import org.hamcrest.Matcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//import com.framework.matchers.MatcherUtils;


@DefaultUrl( value = "/dining", matcher = "endsWith()" )
public class DiningPage extends BaseCarnivalPage
{

	//region DiningPage - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( DiningPage.class );

	private static final String LOGICAL_NAME = "Dining Page";

	// ------------------------------------------------------------------------|
	// --- WEB-OBJECTS DEFINITIONS --------------------------------------------|
	// ------------------------------------------------------------------------|

	//endregion


	//region DiningPage - Constructor Methods Section

	public DiningPage()
	{
		super( LOGICAL_NAME );
		validatePageInitialState();
	}

	//endregion


	//region DiningPage - Initialization and Validation Methods Section


	protected void validatePageInitialState()
	{
		logger.debug( "validating static elements for: <{}>, name:<{}>...", getQualifier(), getLogicalName() );
	}

	@Override
	protected void validatePageTitle()
	{
		String title = ( String ) SiteProperty.DINING_TITLE.fromContext();
		final Matcher<String> EXPECTED_TITLE = JMatchers.equalToIgnoringCase( title );
		final String REASON = String.format( "Asserting \"%s\" page's title", LOGICAL_NAME );

		getDriver().assertThat( REASON, getTitle(), EXPECTED_TITLE );
	}

	//endregion


	//region DiningPage - Service Methods Section



	//endregion


	//region DiningPage - Business Methods Section

	//endregion


	//region DiningPage - Element Finder Methods Section

	//endregion

}
