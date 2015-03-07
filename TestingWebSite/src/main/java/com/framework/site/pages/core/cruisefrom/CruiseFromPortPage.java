package com.framework.site.pages.core.cruisefrom;

import com.framework.asserts.JAssertion;
import com.framework.driver.event.HtmlElement;
import com.framework.site.data.DeparturePorts;
import com.framework.site.objects.body.common.SectionBreadcrumbsBarObject;
import com.framework.site.pages.BaseCarnivalPage;
import com.framework.testing.annotations.DefaultUrl;
import com.framework.utils.matchers.JMatchers;
import com.google.common.base.Optional;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



@DefaultUrl ( matcher = "containsPattern()",
		value = "/cruise-from/(athens|baltimore|barcelona|charleston|ft-lauderdale|galveston|jacksonville|los-angeles|miami" +
				"|new-orleans|new-york|norfolk|port-canaveral|san-juan|seattle|tampa|trieste|vancouver).aspx" )
public class CruiseFromPortPage extends BaseCarnivalPage
{

	//region CruiseToDestinationPage - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( CruiseFromPortPage.class );

	private static final String LOGICAL_NAME = "Cruise From Port";

	private static DeparturePorts departurePort;

	// ------------------------------------------------------------------------|
	// --- WEB-OBJECTS DEFINITIONS --------------------------------------------|
	// ------------------------------------------------------------------------|

	private SectionBreadcrumbsBarObject breadcrumbsBar = null;

	//endregion


	//region CruiseToDestinationPage - Constructor Methods Section

	public CruiseFromPortPage()
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

		Optional<HtmlElement> e = getDriver().elementExists( By.cssSelector( ".hero-title > h2" ) );
		assertion.assertThat( String.format( REASON, ".hero-title > h2" ), e.isPresent(), JMatchers.is( true ) );
		HtmlElement heroTitle = e.get();

		e = getDriver().elementExists( By.className( "hero-slide" ) );
		assertion.assertThat( String.format( REASON, ".hero-slide" ), e.isPresent(), JMatchers.is( true ) );

		String heroTitleText = heroTitle.getText();
		assertion.assertThat( "Validate page H1 inner title", heroTitleText, JMatchers.containsStringIgnoreCase( departurePort.getDeparturePort() ) );
	}

	//endregion


	//region CruiseToDestinationPage - Service Methods Section

	public static void fromDeparturePort( final DeparturePorts departurePort )
	{
		CruiseFromPortPage.departurePort = departurePort;
	}

	public DeparturePorts getDeparturePort()
	{
		return departurePort;
	}

	public SectionBreadcrumbsBarObject breadcrumbsBar()
	{
		if ( null == this.breadcrumbsBar )
		{
			this.breadcrumbsBar = new SectionBreadcrumbsBarObject( findBreadcrumbsBar() );
		}
		return breadcrumbsBar;
	}

	//endregion


	//region CruiseToDestinationPage - Element Finder Methods Section

	private HtmlElement findBreadcrumbsBar()
	{
		return getDriver().findElement( SectionBreadcrumbsBarObject.ROOT_BY );
	}

	private HtmlElement findHeroTitlePara()
	{
		By findBy = By.cssSelector( ".hero-title > p" );
		return getDriver().findElement( findBy );
	}

	//endregion

}
