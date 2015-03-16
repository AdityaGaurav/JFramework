package com.framework.site.pages.core;

import com.framework.driver.event.ExpectedConditions;
import com.framework.driver.event.HtmlCondition;
import com.framework.driver.event.HtmlElement;
import com.framework.driver.extensions.jquery.By;
import com.framework.site.config.SiteSessionManager;
import com.framework.site.objects.body.LinkToutsContainerObject;
import com.framework.site.objects.footer.SectionFooterObject;
import com.framework.site.objects.header.interfaces.Header;
import com.framework.site.pages.BaseCarnivalPage;
import com.framework.utils.datetime.TimeConstants;
import com.framework.utils.matchers.JMatchers;
import com.framework.utils.string.ToLogStringStyle;
import com.google.common.base.Optional;
import org.apache.commons.lang3.builder.ToStringBuilder;
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

	private LinkToutsContainerObject linkTouts = null;

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
		logger.info( "validating page url for page < {} >, name < {} >; current < {} >", getQualifier(), getLogicalName(), getCurrentUrl() );

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
		logger.info( "validating static elements for page < {} >, name < {} > ...", getQualifier(), getLogicalName() );

		final String REASON = "assert that element \"%s\" exits";
		Optional<HtmlElement> e = getDriver().elementExists( By.id( "ccl_homepage" ) );
		getDriver().assertThat( String.format( REASON, "#ccl_homepage" ), e.isPresent(), is( true ) );

		e = getDriver().elementExists( By.id( "form2" ) );
		getDriver().assertThat( String.format( REASON, "#form2" ), e.isPresent(), is( true ) );

		e = getDriver().elementExists( Header.ROOT_BY );
		getDriver().assertThat( String.format( REASON, Header.ROOT_BY.toString() ), e.isPresent(), is( true ) );

		e = getDriver().elementExists( SectionFooterObject.ROOT_BY );
		getDriver().assertThat( String.format( REASON, SectionFooterObject.ROOT_BY.toString() ), e.isPresent(), is( true ) );
	}

	//endregion


	//region HomePage - Business Methods Section



	//endregion


	//region HomePage - Service Methods Section

	@Override
	public String toString()
	{
		return new ToStringBuilder( this, ToLogStringStyle.LOG_LINE_STYLE )
				.appendSuper( super.toString() )
				.append( "locale", getCurrentLocale().getDisplayCountry() )
				.toString();
	}

	public LinkToutsContainerObject linkToutsContainer()
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
		return getDriver().findElement( By.id( "form2" ) );
	}

	private HtmlElement finLinkTouts()
	{
		return getDriver().findElement( LinkToutsContainerObject.ROOT_BY );
	}

	//endregion

}
