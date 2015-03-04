package com.framework.site.pages.core;

import com.framework.asserts.JAssertion;
import com.framework.driver.event.HtmlElement;
import com.framework.site.objects.body.common.SectionBreadcrumbsBarObject;
import com.framework.site.objects.body.interfaces.BreadcrumbsBar;
import com.framework.site.pages.BaseCarnivalPage;
import com.framework.testing.annotations.DefaultUrl;
import com.google.common.base.Optional;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.hamcrest.Matchers.is;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.site.pages.core
 *
 * Name   : CruiseDealsPage
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-01-06
 *
 * Time   : 11:48
 */

@DefaultUrl( matcher = "contains()", value = "/cruise-deals.aspx" )
public class CruiseDealsPage extends BaseCarnivalPage
{

	//region CruiseDealsPage - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( CruiseDealsPage.class );

	private static final String LOGICAL_NAME = "Cruise Deals Page";

	// ------------------------------------------------------------------------|
	// --- WEB-OBJECTS DEFINITIONS --------------------------------------------|
	// ------------------------------------------------------------------------|

	private BreadcrumbsBar breadcrumbsBar = null;

	//endregion


	//region CruiseDealsPage - Constructor Methods Section

	public CruiseDealsPage()
	{
		super( LOGICAL_NAME );
		validatePageInitialState();
	}

	//endregion


	//region CruiseDealsPage - Initialization and Validation Methods Section

	@Override
	protected void validatePageInitialState()
	{
		logger.debug( "validating static elements for web object id: <{}>, name:<{}>...",
				getQualifier(), getLogicalName() );

		final String REASON = "assert that element \"%s\" exits";
		JAssertion assertion = new JAssertion( getDriver() );

		Optional<HtmlElement> e = getDriver().elementExists( By.cssSelector( "div.intro" ) );
		assertion.assertThat( String.format( REASON, "div.intro" ), e.isPresent(), is( true ) );

		e = getDriver().elementExists( By.id( "emailSignupIFrame" ) );
		assertion.assertThat( String.format( REASON, "#emailSignupIFrame" ), e.isPresent(), is( true ) );
	}

	//endregion


	//region CruiseDealsPage - Service Methods Section

	public BreadcrumbsBar subscribe()
	{
		if ( null == this.breadcrumbsBar )
		{
			this.breadcrumbsBar = new SectionBreadcrumbsBarObject( findBreadcrumbsBar() );
		}
		return breadcrumbsBar;
	}

	//endregion


	//region CruiseDealsPage - Business Methods Section

	//endregion


	//region CruiseDealsPage - Element Finder Methods Section

	HtmlElement findBreadcrumbsBar()
	{
		return getDriver().findElement( BreadcrumbsBar.ROOT_BY );
	}

	//endregion

}