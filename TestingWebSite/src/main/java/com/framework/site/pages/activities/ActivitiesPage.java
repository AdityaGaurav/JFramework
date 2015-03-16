package com.framework.site.pages.activities;

import com.framework.asserts.JAssertion;
import com.framework.driver.event.HtmlElement;
import com.framework.driver.extensions.jquery.By;
import com.framework.site.objects.body.common.SectionBreadcrumbsBarObject;
import com.framework.site.pages.BaseCarnivalPage;
import com.framework.testing.annotations.DefaultUrl;
import com.google.common.base.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.hamcrest.Matchers.is;


@DefaultUrl( value = "/Activities", matcher = "endsWith()" )
public class ActivitiesPage extends BaseCarnivalPage
{

	//region ActivitiesPage - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( ActivitiesPage.class );

	private static final String LOGICAL_NAME = "Shore Excursions Page";

	// ------------------------------------------------------------------------|
	// --- WEB-OBJECTS DEFINITIONS --------------------------------------------|
	// ------------------------------------------------------------------------|

	private SectionBreadcrumbsBarObject breadcrumbsBar;

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

		final String REASON = "assert that element \"%s\" exits";
		JAssertion assertion = new JAssertion( getDriver() );
		Optional<HtmlElement> e = getDriver().elementExists( By.jQuerySelector( "h1:contains('Shore Excursions')" ) );
		assertion.assertThat( String.format( REASON, "h1:contains('Shore Excursions')" ), e.isPresent(), is( true ) );
	}

	//endregion


	//region ActivitiesPage - Service Methods Section

	public SectionBreadcrumbsBarObject breadcrumbsBar()
	{
		if ( null == this.breadcrumbsBar )
		{
			this.breadcrumbsBar = new SectionBreadcrumbsBarObject( findBreadcrumbBarDiv() );
		}
		return breadcrumbsBar;
	}

	//endregion


	//region ActivitiesPage - Business Methods Section

	//endregion


	//region ActivitiesPage - Element Finder Methods Section

	//endregion

}
