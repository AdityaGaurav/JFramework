package com.framework.site.pages.core;

import com.framework.asserts.JAssertion;
import com.framework.driver.event.HtmlElement;
import com.framework.driver.extensions.jquery.By;
import com.framework.site.data.Enumerators;
import com.framework.site.objects.body.common.SectionBreadcrumbsBarObject;
import com.framework.site.pages.BaseCarnivalPage;
import com.framework.testing.annotations.DefaultUrl;
import com.framework.utils.matchers.JMatchers;
import com.google.common.base.Optional;
import org.hamcrest.Matcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.hamcrest.Matchers.is;


@DefaultUrl ( value = "/about-carnival/about-us", matcher = "contains()" )
public class AboutUsPage extends BaseCarnivalPage implements Enumerators
{

	//region AboutUsPage - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( AboutUsPage.class );

	private static final String LOGICAL_NAME = "About Us Page";

	static AboutUsLinks reference;

	// ------------------------------------------------------------------------|
	// --- WEB-OBJECTS DEFINITIONS --------------------------------------------|
	// ------------------------------------------------------------------------|

	private SectionBreadcrumbsBarObject breadcrumbsBarObject;

	// ------------------------------------------------------------------------|
	// --- WEB-OBJECTS CACHING ------------------------------------------------|
	// ------------------------------------------------------------------------|

	private HtmlElement horizontal_nav;

	//endregion


	//region AboutUsPage - Constructor Methods Section

	public AboutUsPage()
	{
		super( LOGICAL_NAME );
		validatePageInitialState();
	}

	//endregion


	//region AboutUsPage - Initialization and Validation Methods Section

	@Override
	protected void validatePageInitialState()
	{
		logger.debug( "validating static elements for: <{}>, name:<{}>...", getQualifier(), getLogicalName() );

		final String REASON = "assert that element \"%s\" exits";
		JAssertion assertion = new JAssertion( getDriver() );
		Optional<HtmlElement> e = getDriver().elementExists( By.cssSelector( "div.horizontal-nav.clearfix" ) );
		assertion.assertThat( String.format( REASON, "div.horizontal-nav.clearfix" ), e.isPresent(), is( true ) );
		horizontal_nav = e.get();

		HtmlElement he = getBreadcrumbsBarObject().breadcrumbs().getLastChild();
		String text = he.getText();
		if( null != reference )
		{
			Matcher<String> EXPECTED_STR = null;
			switch( reference )
			{
				case ABOUT_US:
					EXPECTED_STR = JMatchers.is( "About Us" );
					break;
				case AWARDS:
					EXPECTED_STR = JMatchers.is( "Awards" );
					break;
				case CORPORATE_ADDRESS:
					EXPECTED_STR = JMatchers.startsWith( "Awards" );
					break;
				case INVESTOR_RELATIONS:
					EXPECTED_STR = JMatchers.startsWith( "Investor" );
					break;
				case WORLD_S_LEADING_CRUISE_LINES:
					EXPECTED_STR = JMatchers.endsWith( "Cruise Lines" );
					break;
			}
			assertion.assertThat( "Validate last-child breadcrumb text", text, EXPECTED_STR );
		}
		else
		{
			assertion.assertThat( "Validate last-child breadcrumb text", text, JMatchers.is( "About Us" ) );
		}
	}

	//endregion


	//region AboutUsPage - Service Methods Section

	public SectionBreadcrumbsBarObject getBreadcrumbsBarObject()
	{
		if( null == breadcrumbsBarObject )
		{
			breadcrumbsBarObject = new SectionBreadcrumbsBarObject( findBreadcrumbBarDiv() );
		}
		return breadcrumbsBarObject;
	}


	//endregion


	//region AboutUsPage - Business Function Section

	public AboutUsPage clickLink( AboutUsLinks link )
	{
		HtmlElement he = null;
		switch ( link )
		{
			case ABOUT_US:
				he = findHorizontalNavLink( "About Us", false );
				break;
			case AWARDS:
				he = findHorizontalNavLink( "Awards", false );
				break;
			case CORPORATE_ADDRESS:
				he = findHorizontalNavLink( "Corporate", true );
				break;
			case INVESTOR_RELATIONS:
				he = findHorizontalNavLink( "Investor", true );
				break;
			case WORLD_S_LEADING_CRUISE_LINES:
				he = findHorizontalNavLink( "Cruise Lines", true );
				break;
		}

		he.click();
		reference = link;
		return new AboutUsPage();
	}

	public String getHeadlineText()
	{
		HtmlElement he = findHeadline();
		String text = he.getText();
		logger.info( "Return the headline text < {} >", text );
		return text;
	}

	//endregion


	//region AboutUsPage - Element Finder Section

	private HtmlElement findHorizontalNavDiv()
	{
		if( null == horizontal_nav )
		{
			horizontal_nav = getDriver().findElement( By.cssSelector( "div.horizontal-nav.clearfix" ) );
		}

		return horizontal_nav;
	}

	private HtmlElement findHorizontalNavLink( String text, boolean full )
	{
		if( full )
		{
			return findHorizontalNavDiv().findElement( By.linkText( text ) );
		}
		return findHorizontalNavDiv().findElement( By.partialLinkText( text ) );
	}

	private HtmlElement findHeadline()
	{
		return getDriver().findElement( By.className( "headline" ) );
	}

	//endregion

}
