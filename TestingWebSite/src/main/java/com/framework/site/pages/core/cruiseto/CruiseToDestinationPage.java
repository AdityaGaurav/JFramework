package com.framework.site.pages.core.cruiseto;

import com.framework.asserts.JAssertion;
import com.framework.driver.event.ExpectedConditions;
import com.framework.driver.event.HtmlCondition;
import com.framework.driver.event.HtmlElement;
import com.framework.site.config.SiteProperty;
import com.framework.site.data.Destinations;
import com.framework.site.objects.body.common.SectionBreadcrumbsBarObject;
import com.framework.site.objects.body.interfaces.BreadcrumbsBar;
import com.framework.site.pages.BaseCarnivalPage;
import com.framework.testing.annotations.DefaultUrl;
import com.framework.utils.datetime.TimeConstants;
import com.framework.utils.matchers.JMatchers;
import com.google.common.base.Optional;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.hamcrest.Matchers.is;


@DefaultUrl( matcher = "containsPattern()", value = "/cruise-to/(alaska|bahamas|bermuda|caribbean|europe|mexico)-cruises.aspx" )
public class CruiseToDestinationPage extends BaseCarnivalPage
{

	//region CruiseToDestinationPage - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( CruiseToDestinationPage.class );

	private static final String LOGICAL_NAME = "Cruise To Destination Page";

	private static Destinations destination;

	// ------------------------------------------------------------------------|
	// --- WEB-OBJECTS DEFINITIONS --------------------------------------------|
	// ------------------------------------------------------------------------|

	private BreadcrumbsBar breadcrumbsBar = null;

	//endregion


	//region CruiseToDestinationPage - Constructor Methods Section

	public CruiseToDestinationPage()
	{
		super( LOGICAL_NAME );
		validatePageInitialState();
	}

	//endregion


	//region CruiseToDestinationPage - Initialization and Validation Methods Section

	@Override
	protected void validatePageInitialState()
	{
		logger.debug( "validating static elements for web object id: <{}>, name:<{}>...",
				getQualifier(), getLogicalName() );

		final String REASON = "assert that element \"%s\" exits";
		JAssertion assertion = new JAssertion( getDriver() );

		Optional<HtmlElement> e = getDriver().elementExists( By.cssSelector( ".hero-title > h1" ) );
		assertion.assertThat( String.format( REASON, ".hero-title > h1" ), e.isPresent(), is( true ) );

		e = getDriver().elementExists( By.className( "hero-slide" ) );
		assertion.assertThat( String.format( REASON, ".hero-slide" ), e.isPresent(), is( true ) );

		String heroTitle = findHeroTitleH1().getText();
		assertion.assertThat( "Validate page H1 inner title", heroTitle, JMatchers.is( destination.getDestination().toUpperCase() ) );
	}

	@Override
	protected void validatePageTitle()
	{

		final String EXPECTED_TITLE_LIST =
				( String ) SiteProperty.CRUISE_TO_DESTINATION_TITLE.fromContext( new Object[] { destination.getCapitalized() } );
		String[] expectedTitles = StringUtils.split( EXPECTED_TITLE_LIST, "," );
		final String REASON = String.format( "Asserting \"%s\" page's title", LOGICAL_NAME );
		HtmlCondition<Boolean> condition = ExpectedConditions.titleMatches( JMatchers.anyOf(
						JMatchers.startsWith( expectedTitles[ 0 ] ),
						JMatchers.startsWith( expectedTitles[ 1 ] ),
						JMatchers.startsWith( expectedTitles[ 2 ] ) )
																		  );
		new JAssertion( getDriver() ).assertWaitThat( REASON, TimeConstants.ONE_MINUTE, condition );
	}

	//endregion


	//region CruiseToDestinationPage - Service Methods Section

	public static void forDestination( final Destinations destination )
	{
		CruiseToDestinationPage.destination = destination;
	}

	public Destinations getDestination()
	{
		return destination;
	}

	public BreadcrumbsBar subscribe()
	{
		if ( null == this.breadcrumbsBar )
		{
			this.breadcrumbsBar = new SectionBreadcrumbsBarObject( findBreadcrumbsBar() );
		}
		return breadcrumbsBar;
	}

	//endregion


	//region CruiseToDestinationPage - Business Methods Section


	//endregion


	//region CruiseToDestinationPage - Element Finder Methods Section

	private HtmlElement findBreadcrumbsBar()
	{
		return getDriver().findElement( BreadcrumbsBar.ROOT_BY );
	}

	private HtmlElement findHeroTitleH1()
	{
		By findBy = By.cssSelector( ".hero-title > h1" );
		return getDriver().findElement( findBy );
	}

	private HtmlElement findHeroTitlePara()
	{
		By findBy = By.cssSelector( ".hero-title > p" );
		return getDriver().findElement( findBy );
	}

	//endregion

}
