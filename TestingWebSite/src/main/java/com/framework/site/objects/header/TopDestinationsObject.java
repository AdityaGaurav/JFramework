package com.framework.site.objects.header;

import com.framework.asserts.JAssertion;
import com.framework.driver.event.HtmlElement;
import com.framework.driver.event.HtmlObject;
import com.framework.driver.exceptions.UrlNotAvailableException;
import com.framework.driver.objects.Link;
import com.framework.site.data.Destinations;
import com.framework.site.objects.header.interfaces.Header;
import com.framework.site.pages.core.cruiseto.CruiseToDestinationPage;
import com.framework.utils.matchers.JMatchers;
import com.google.common.base.Optional;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static com.framework.utils.datetime.TimeConstants.FIVE_SECONDS;
import static com.framework.utils.datetime.TimeConstants.TWO_SECONDS;
import static org.hamcrest.Matchers.*;


class TopDestinationsObject extends HeaderBrandingObject implements Header.HeaderBranding.TopDestinations
{

	//region TopDestinationsObject - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( TopDestinationsObject.class );

	private HtmlElement nav_tooltip_trigger, nav_tooltip_top_destinations, nav_tooltip_trigger_parent;

	private HtmlElement li_title;

	private List<HtmlElement> top_destinations;

	//endregion


	//region TopDestinationsObject - Constructor Methods Section

	TopDestinationsObject( final HtmlElement rootElement )
	{
		super( rootElement, TopDestinations.LOGICAL_NAME );
		initWebObject();
	}

	//endregion


	//region TopDestinationsObject - Initialization and Validation Methods Section

	@Override
	protected void initWebObject()
	{
		logger.debug( "validating static elements for web object id: <{}>, name:<{}>...",
				getQualifier(), getLogicalName() );

		final String REASON = "assert that element \"%s\" exits";
		JAssertion assertion = getRoot().createAssertion();

		Optional<HtmlElement> e = getRoot().childExists( By.cssSelector( ".nav-tooltip li.title" ), FIVE_SECONDS );
		assertion.assertThat( String.format( REASON, ".nav-tooltip li.title" ), e.isPresent(), JMatchers.is( true ) );
	}

	//endregion


	//region TopDestinationsObject - Service Methods Section

	private HtmlElement getRoot()
	{
		return getBaseRootElement( Header.HeaderBranding.ROOT_BY );
	}

	//endregion


	//region TopDestinationsObject - Business Methods Section

	@Override
	public boolean isDisplayed()
	{
		return getRoot().isDisplayed();
	}

	@Override
	public List<String> openTopDestinationsList()
	{
		logger.debug( "opening the country list, by clicking on top-destinations trigger ..." );

		findNavTooltipTriggerAnchor().click();
		getRoot().waitAttributeToMatch( "class", endsWith( "increaseZ" ), TWO_SECONDS );
		findNavTooltipTriggerParent().waitAttributeToMatch( "class", is( "hover" ), TWO_SECONDS );
		findNavTooltipDiv().waitToBeDisplayed( true, TWO_SECONDS );

		return HtmlObject.extractText( findTopDestinations() );
	}

	@Override
	public void closeTopDestinationsList()
	{
		logger.debug( "closing the country list, by clicking on top-destinations trigger ..." );

		findNavTooltipTriggerAnchor().click();
		getRoot().waitAttributeToMatch( "class", not( endsWith( "increaseZ" ) ), TWO_SECONDS );
		findNavTooltipTriggerParent().waitAttributeToMatch( "class", isEmptyOrNullString(), TWO_SECONDS );
		findNavTooltipDiv().waitToBeDisplayed( false, TWO_SECONDS );
	}

	@Override
	public CruiseToDestinationPage clickLink( final Destinations destination ) throws UrlNotAvailableException
	{
		logger.info( "Clicking and testing href for link {} Cruises", destination.toString() );

		Link destinationLink = new Link( findTopDestination( destination ) );
		destinationLink.checkReference( 5000 );
		destinationLink.click();
		logger.info( "Waiting for page CruiseToDestinationPage to be loaded ..." );
		CruiseToDestinationPage.forDestination( destination );
		CruiseToDestinationPage c2d = new CruiseToDestinationPage();
		logger.info( "returning a new page instance -> '{}'", c2d );

		return c2d;
	}

	@Override
	public String getListTitle()
	{
		return findTitleLi().getText().equals( StringUtils.EMPTY )
				? findTitleLi().getText()
				: findTitleLi().getAttribute( "textContent" );
	}

	@Override
	public boolean isTopDestinationsListOpened()
	{
		return findNavTooltipDiv().isDisplayed();
	}

	//endregion


	//region TopDestinationsObject - Element Finder Methods Section

    private HtmlElement findNavTooltipTriggerAnchor()
	{
   		final By findBy = By.cssSelector( "a[data-id='top-destinations']" );
		if( null == nav_tooltip_trigger )
		{
			nav_tooltip_trigger = super.findZeroNavUl().findElement( findBy );
		}
		return nav_tooltip_trigger;
	}

	private HtmlElement findNavTooltipTriggerParent()
	{
		if( null == nav_tooltip_trigger_parent )
		{
			nav_tooltip_trigger_parent = findNavTooltipTriggerAnchor().parent();
		}
		return nav_tooltip_trigger_parent;
	}

	private HtmlElement findNavTooltipDiv()
	{
		final By findBy = By.cssSelector( "div[data-id='top-destinations']" );
		if( null == nav_tooltip_top_destinations )
		{
			nav_tooltip_top_destinations = super.findZeroNavUl().findElement( findBy );
		}
		return nav_tooltip_top_destinations;
	}

	private HtmlElement findTitleLi()
	{
		final By findBy = By.className( "title" );
		if( null == li_title )
		{
			li_title = findNavTooltipDiv().findElement( findBy );
		}
		return li_title;
	}

	private List<HtmlElement> findTopDestinations()
	{
		final By findBy = By.tagName( "a" );
		if( null == top_destinations )
		{
			top_destinations = findNavTooltipDiv().findElements( findBy );
		}
		return top_destinations;
	}

	private HtmlElement findTopDestination( Destinations destinations )
	{
		final By findBy = By.cssSelector( String.format( "a[href $='%s-cruises.aspx']", destinations.name().toLowerCase() ) );
	    return findNavTooltipDiv().findElement( findBy );
	}

	//endregion

}