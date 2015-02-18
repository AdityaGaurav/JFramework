package com.framework.site.pages.core;

import com.framework.site.config.SiteProperty;
import com.framework.site.pages.BaseCarnivalPage;
import com.framework.testing.annotations.DefaultUrl;
import com.framework.utils.matchers.JMatchers;
import org.hamcrest.Matcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@DefaultUrl( value = "/what-to-expect-on-a-cruise.aspx", matcher = "contains()" )
public class What2ExpectPage extends BaseCarnivalPage
{

	//region What2ExpectPage - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( What2ExpectPage.class );

	private static final String LOGICAL_NAME = "What To Expect on Cruise Page";

	private static final String PAGE_TITLE_KEY = "what.to.expect.title";

	// ------------------------------------------------------------------------|
	// --- WEB-OBJECTS DEFINITIONS --------------------------------------------|
	// ------------------------------------------------------------------------|


	//endregion


	//region What2ExpectPage - Constructor Methods Section

	public What2ExpectPage()
	{
		super( LOGICAL_NAME );
		validatePageInitialState();
	}

	//endregion


	//region What2ExpectPage - Initialization and Validation Methods Section

	protected void validatePageInitialState()
	{
		logger.debug( "validating static elements for: <{}>, name:<{}>...", getQualifier(), getLogicalName() );
	}

	@Override
	protected void validatePageTitle()
	{
		String title = ( String ) SiteProperty.WHAT_TO_EXPECT_TITLE.fromContext();
		final Matcher<String> EXPECTED_TITLE = JMatchers.equalToIgnoringCase( title );
		final String REASON = String.format( "Asserting \"%s\" page's title", LOGICAL_NAME );

		getDriver().assertThat( REASON, getTitle(), EXPECTED_TITLE );
	}

	//endregion


	//region What2ExpectPage - Service Methods Section




	//endregion


	//region What2ExpectPage - Business Methods Section

	//endregion


	//region What2ExpectPage - Element Finder Methods Section

	//endregion

}
