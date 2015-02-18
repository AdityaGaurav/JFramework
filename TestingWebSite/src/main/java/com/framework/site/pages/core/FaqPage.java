package com.framework.site.pages.core;

import com.framework.site.config.SiteProperty;
import com.framework.site.pages.BaseCarnivalPage;
import com.framework.testing.annotations.DefaultUrl;
import com.framework.utils.matchers.JMatchers;
import org.hamcrest.Matcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@DefaultUrl( value = "/core/faq.aspx", matcher = "endsWith()" )
public class FaqPage extends BaseCarnivalPage
{

	//region FaqPage - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( FaqPage.class );

	private static final String LOGICAL_NAME = "Faq Page";

	// ------------------------------------------------------------------------|
	// --- WEB-OBJECTS DEFINITIONS --------------------------------------------|
	// ------------------------------------------------------------------------|

	//endregion


	//region FaqPage - Constructor Methods Section

	public FaqPage()
	{
		super( LOGICAL_NAME );
		validatePageInitialState();
	}

	//endregion


	//region FaqPage - Initialization and Validation Methods Section


	protected void validatePageInitialState()
	{
		logger.debug( "validating static elements for: <{}>, name:<{}>...", getQualifier(), getLogicalName() );
	}

	@Override
	protected void validatePageTitle()
	{
		String title = ( String ) SiteProperty.FAQ_TITLE.fromContext();
		final Matcher<String> EXPECTED_TITLE = JMatchers.equalToIgnoringCase( title );
		final String REASON = String.format( "Asserting \"%s\" page's title", LOGICAL_NAME );

		getDriver().assertThat( REASON, getTitle(), EXPECTED_TITLE );
	}

	//endregion


	//region FaqPage - Service Methods Section


	//endregion


	//region FaqPage - Business Methods Section

	//endregion


	//region FaqPage - Element Finder Methods Section

	//endregion

}
