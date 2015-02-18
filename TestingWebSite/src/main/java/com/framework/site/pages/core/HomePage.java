package com.framework.site.pages.core;

import com.framework.asserts.JAssertion;
import com.framework.driver.event.ExpectedConditions;
import com.framework.driver.event.HtmlCondition;
import com.framework.driver.event.HtmlElement;
import com.framework.site.config.SiteProperty;
import com.framework.site.config.SiteSessionManager;
import com.framework.site.objects.body.LinkToutsContainerObject;
import com.framework.site.objects.body.interfaces.LinkTouts;
import com.framework.site.objects.footer.interfaces.Footer;
import com.framework.site.objects.header.interfaces.Header;
import com.framework.site.pages.BaseCarnivalPage;
import com.framework.utils.datetime.TimeConstants;
import com.framework.utils.matchers.JMatchers;
import com.framework.utils.string.LogStringStyle;
import com.google.common.base.Optional;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.hamcrest.Matchers.is;


public class HomePage extends BaseCarnivalPage
{

	//region HomePage - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( HomePage.class );

	private static final String LOGICAL_NAME = "Home Page";

	// ------------------------------------------------------------------------|
	// --- WEB-OBJECTS DEFINITIONS --------------------------------------------|
	// ------------------------------------------------------------------------|

	private LinkTouts linkTouts = null;

	//endregion


	//region HomePage - Constructor Methods Section

	public HomePage()
	{
		super( LOGICAL_NAME );
		validatePageInitialState();
	}

	//endregion


	//region HomePage - Initialization and Validation Methods Section

	@Override
	protected void validatePageUrl()
	{
		logger.debug( "validating page url and title for: <'{}'>, name: <'{}'> ...", getQualifier(), getLogicalName() );

		final String EXPECTED_ENV_URL = SiteSessionManager.get().getBaseUrl().toString();
		HtmlCondition<Boolean> condition = ExpectedConditions.urlMatches( JMatchers.equalToIgnoringCase( EXPECTED_ENV_URL ) );
		getDriver().assertWaitThat( "Asserting Home Page Url", TimeConstants.ONE_MINUTE, condition );
	}

	/**
	 * asserting presence of static web elements on page.
	 * <ul>
	 *     <li>div#ccl_homepage</li>
	 *     <li>form#form2</li>
	 *     <li>div#ccl-refresh-header</li>
	 *     <li>div.core-footer</li>
	 * </ul>
	 */
	@Override
	protected void validatePageInitialState()
	{
		logger.debug( "validating static elements for web object id: <{}>, name:<{}>...",
				getQualifier(), getLogicalName() );

		final String REASON = "assert that element \"%s\" exits";
		JAssertion assertion = new JAssertion( getDriver() );
		Optional<HtmlElement> e = getDriver().elementExists( By.id( "ccl_homepage" ) );
		assertion.assertThat( String.format( REASON, "#ccl_homepage" ), e.isPresent(), is( true ) );

		e = getDriver().elementExists( By.id( "form2" ) );
		assertion.assertThat( String.format( REASON, "#form2" ), e.isPresent(), is( true ) );

		e = getDriver().elementExists( Header.ROOT_BY );
		assertion.assertThat( String.format( REASON, Header.ROOT_BY.toString() ), e.isPresent(), is( true ) );

		e = getDriver().elementExists( Footer.ROOT_BY );
		assertion.assertThat( String.format( REASON, Footer.ROOT_BY.toString() ), e.isPresent(), is( true ) );
	}

	@Override
	protected void validatePageTitle()
	{
		final String EXPECTED_TITLE = ( String ) SiteProperty.HOME_PAGE_TITLE.fromContext();
		final String REASON = String.format( "Asserting \"%s\" page's title", LOGICAL_NAME );

		HtmlCondition<Boolean> condition = ExpectedConditions.titleMatches( JMatchers.equalToIgnoringCase( EXPECTED_TITLE ) );
		getDriver().assertWaitThat( REASON, TimeConstants.HALF_MINUTE, condition );
	}

	//endregion


	//region HomePage - Business Methods Section

	public String getSecuredUrl()
	{
   		return findHiddenSecuredUrlInput().getAttribute( "value" );
	}

	//endregion


	//region HomePage - Service Methods Section

	@Override
	public String toString()
	{
		return new ToStringBuilder( this, LogStringStyle.LOG_LINE_STYLE )
				.appendSuper( super.toString() )
				.append( "locale", getCurrentLocale().getDisplayCountry() )
				.toString();
	}

	public LinkTouts linkToutsContainer()
	{
		if ( null == this.linkTouts )
		{
			this.linkTouts = new LinkToutsContainerObject( finLinkTouts() );
		}
		return linkTouts;
	}

	//endregion


	//region HomePage - Element Finder Methods Section

	private HtmlElement findForm2()
	{
		By findBy = By.id( "form2" );
		return getDriver().findElement( findBy );
	}

	private HtmlElement findHiddenSecuredUrlInput()
	{
		By findBy = By.id( "hSecUrl" );
		return getDriver().findElement( findBy );
	}

	private HtmlElement finLinkTouts()
	{
		return getDriver().findElement( LinkTouts.ROOT_BY );
	}

	//endregion

}
