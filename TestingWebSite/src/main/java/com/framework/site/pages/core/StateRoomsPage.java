package com.framework.site.pages.core;

import com.framework.asserts.JAssertion;
import com.framework.driver.event.HtmlElement;
import com.framework.site.config.SiteSessionManager;
import com.framework.site.objects.body.common.SectionBreadcrumbsBarObject;
import com.framework.site.objects.body.staterooms.DidYouKnowObject;
import com.framework.site.objects.body.staterooms.UserFeedbackObject;
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
 * Name   : CruiseToPage
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-01-10
 *
 * Time   : 01:17
 */

@DefaultUrl( matcher = "endsWith()", value = "/staterooms.aspx" )
public class StateRoomsPage extends BaseCarnivalPage
{

	//region StateRoomsPage - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( StateRoomsPage.class );

	private static final String LOGICAL_NAME = "State Rooms Page";

	// ------------------------------------------------------------------------|
	// --- WEB-OBJECTS DEFINITIONS --------------------------------------------|
	// ------------------------------------------------------------------------|

	private SectionBreadcrumbsBarObject breadcrumbsBar;

	private DidYouKnowObject didYouKnow;

	private UserFeedbackObject userFeedback;

	// ------------------------------------------------------------------------|
	// --- WEB-OBJECTS CACHING ------------------------------------------------|
	// ------------------------------------------------------------------------|

	//endregion


	//region StateRoomsPage - Constructor Methods Section

	public StateRoomsPage()
	{
		super( LOGICAL_NAME );
		validatePageInitialState();
	}

	//endregion


	//region StateRoomsPage - Initialization and Validation Methods Section

	@Override
	protected void validatePageInitialState()
	{
		logger.debug( "validating static elements for: <{}>, name:<{}>...", getQualifier(), getLogicalName() );

		final String REASON = "assert that element \"%s\" exits";
		JAssertion assertion = new JAssertion( getDriver() );
		Optional<HtmlElement> e = getDriver().elementExists( By.id( "HeroSlidesContainer" ) );
		assertion.assertThat( String.format( REASON, "#HeroSlidesContainer" ), e.isPresent(), is( true ) );

		e = getDriver().elementExists( By.cssSelector( ".content-block.white-gradient:not(.scroll-section)" ) );
		assertion.assertThat( String.format( REASON, ".content-block.white-gradient:not(.scroll-section)" ), e.isPresent(), is( true ) );

		e = getDriver().elementExists( By.className( "explore-room" ) );
		assertion.assertThat( String.format( REASON, "explore-room" ), e.isPresent(), is( true ) );

		e = getDriver().elementExists( By.id( "accommodations" ) );
		assertion.assertThat( String.format( REASON, "#accommodations" ), e.isPresent(), is( true ) );
	}

	public static boolean isStateRoomsPage()
	{
		return SiteSessionManager.get().getCurrentUrl().getPath().endsWith( "/staterooms.aspx" );
	}


	//endregion


	//region StateRoomsPage - Service Methods Section



	public SectionBreadcrumbsBarObject breadcrumbsBar()
	{
		if ( null == this.breadcrumbsBar )
		{
			this.breadcrumbsBar = new SectionBreadcrumbsBarObject( findBreadcrumbBarDiv() );
		}
		return breadcrumbsBar;
	}

	public DidYouKnowObject didYouKnow()
	{
		if ( null == this.didYouKnow )
		{
			this.didYouKnow = new DidYouKnowObject( findDidYouKnowDiv() );
		}
		return didYouKnow;
	}

	public UserFeedbackObject userFeedback()
	{
		if ( null == this.userFeedback )
		{
			this.userFeedback = new UserFeedbackObject( findUserFeedbackDiv() );
		}
		return userFeedback;
	}


	//endregion


	//region StateRoomsPage - Business Methods Section



	//endregion


	//region StateRoomsPage - Element Finder Methods Section

	private HtmlElement findDidYouKnowDiv()
	{
		return getDriver().findElement( DidYouKnowObject.ROOT_BY );
	}

	private HtmlElement findUserFeedbackDiv()
	{
		return getDriver().findElement( DidYouKnowObject.ROOT_BY );
	}

	//endregion

}
