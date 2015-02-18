package com.framework.site.pages.core;

import com.framework.site.config.SiteProperty;
import com.framework.site.objects.body.common.NavStickEmbeddedObject;
import com.framework.site.objects.body.interfaces.NavStickEmbedded;
import com.framework.site.pages.BaseCarnivalPage;
import com.framework.testing.annotations.DefaultUrl;
import com.framework.utils.matchers.JMatchers;
import org.hamcrest.Matcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@DefaultUrl( value = "/uk-caribbean.aspx", matcher = "endsWith()")
public class UKCaribbeanPage extends BaseCarnivalPage
{

	//region UKCaribbeanPage - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( UKCaribbeanPage.class );

	private static final String LOGICAL_NAME = "UK Caribbean Page";

	// ------------------------------------------------------------------------|
	// --- WEB-OBJECTS DEFINITIONS --------------------------------------------|
	// ------------------------------------------------------------------------|

	private NavStickEmbedded navStickEmbedded;

	//endregion


	//region UKCaribbeanPage - Constructor Methods Section

	public UKCaribbeanPage()
	{
		super( LOGICAL_NAME );
		validatePageInitialState();
	}

	//endregion


	//region UKCaribbeanPage - Initialization and Validation Methods Section


	protected void validatePageInitialState()
	{
		logger.debug( "validating static elements for: <{}>, name:<{}>...", getQualifier(), getLogicalName() );
	}

	@Override
	protected void validatePageTitle()
	{
		String title = ( String ) SiteProperty.UK_CARIBBEAN_TITLE.fromContext();
		final Matcher<String> EXPECTED_TITLE = JMatchers.equalToIgnoringCase( title );
		final String REASON = String.format( "Asserting \"%s\" page's title", LOGICAL_NAME );

		getDriver().assertThat( REASON, getTitle(), EXPECTED_TITLE );
	}


	//endregion


	//region UKCaribbeanPage - Service Methods Section

	public NavStickEmbedded navStickEmbedded()
	{
		if ( null == this.navStickEmbedded )
		{
			this.navStickEmbedded = new NavStickEmbeddedObject( findBreadcrumbBarDiv() );
		}
		return navStickEmbedded;
	}

	//endregion


	//region UKCaribbeanPage - Business Methods Section

	//endregion


	//region UKCaribbeanPage - Element Finder Methods Section

	//endregion

}
