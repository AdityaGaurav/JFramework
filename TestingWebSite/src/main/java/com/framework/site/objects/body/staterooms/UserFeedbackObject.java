package com.framework.site.objects.body.staterooms;

import com.framework.driver.event.ExpectedConditions;
import com.framework.driver.event.HtmlCondition;
import com.framework.driver.event.HtmlDriverWait;
import com.framework.driver.event.HtmlElement;
import com.framework.driver.objects.AbstractWebObject;
import com.framework.site.data.Enumerators;
import com.framework.utils.datetime.TimeConstants;
import com.framework.utils.error.PreConditions;
import com.framework.utils.matchers.JMatchers;
import com.google.common.collect.Maps;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.site.objects.body.staterooms
 *
 * Name   : UserFeedbackObject 
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-03-07 
 *
 * Time   : 01:49
 *
 */

public class UserFeedbackObject extends AbstractWebObject implements Enumerators
{

	//region UserFeedbackObject - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( UserFeedbackObject.class );

	static final String LOGICAL_NAME = "User Feedback";

	static final By ROOT_BY = By.className( "explore-room" );

	// ------------------------------------------------------------------------|
	// --- WEB-OBJECTS CACHING ------------------------------------------------|
	// ------------------------------------------------------------------------|

	private HtmlElement explore_tooltips;

	//endregion


	//region UserFeedbackObject - Constructor Methods Section

	public UserFeedbackObject( final HtmlElement rootElement )
	{
		super( rootElement, LOGICAL_NAME );
		initWebObject();
	}

	//endregion


	//region UserFeedbackObject - Initialization and Validation Methods Section

	@Override
	protected void initWebObject()
	{
		logger.info( "validating static elements for web object id: <{}>, name:<{}>...",
				getQualifier(), getLogicalName() );

		final String REASON = "assert that element \"%s\" exits";
		HtmlCondition<HtmlElement> condition = ExpectedConditions.presenceBy( By.className( "explore-tooltips" ) );
		getDriver().assertWaitThat( String.format( REASON, "explore-tooltips" ), TimeConstants.FIVE_SECONDS, condition );
		explore_tooltips = getDriver().findElement( By.className( "explore-tooltips" ) );
	}

	//endregion


	//region UserFeedbackObject - Service Methods Section

	private HtmlElement getRoot()
	{
		return getBaseRootElement( ROOT_BY );
	}

	//endregion


	//region UserFeedbackObject - UserFeedback interface Methods Section

	public HtmlElement activateTooltip( UserFeedbackTooltip tooltip, HtmlElement he )
	{
		he.click();
		he.waitAttributeToMatch( "aria-describedby", JMatchers.startsWithIgnoreCase( "qtip-" ), TimeConstants.FIVE_SECONDS );
		String describedBy = he.getAttribute( "aria-describedby" );
		logger.info( "Activating tooltip < {} >, returning qTip < {} >", tooltip, describedBy );
		PreConditions.checkNotNullNotBlankOrEmpty( describedBy, "qtip was found null, empty or blank" );
		HtmlElement qTip = HtmlDriverWait.wait5( getDriver() ).until( ExpectedConditions.presenceBy( By.id( describedBy ) ) );
		qTip.waitToBeDisplayed( true, TimeConstants.THREE_SECONDS );
		return qTip;
	}

	public Map<UserFeedbackTooltip,HtmlElement> getToolTips()
	{
		List<HtmlElement> spans = findTooltipSpans();
		Map<UserFeedbackTooltip,HtmlElement> tooltips = Maps.newHashMap();
		for( HtmlElement span : spans )
		{
			String dataTag = span.getAttribute( "data-tag" );
			PreConditions.checkNotNull( dataTag, "DataTag is null" );
			if( dataTag.endsWith( "beds" ) )
			{
				tooltips.put( UserFeedbackTooltip.BED, span );
			}
			else if( dataTag.endsWith( "soft bedding" ) )
			{
				tooltips.put( UserFeedbackTooltip.SOFT_BEDDING, span );
			}
			else if( dataTag.endsWith( "room steward" ) )
			{
				tooltips.put( UserFeedbackTooltip.ROOM_STEWARD, span );
			}
			else if( dataTag.endsWith( "interactive TV" ) )
			{
				tooltips.put( UserFeedbackTooltip.INTERACTIVE_TV, span );
			}
			else if( dataTag.endsWith( "private balcony" ) )
			{
				tooltips.put( UserFeedbackTooltip.PRIVATE_BALCONY, span );
			}
			else if( dataTag.endsWith( "room service" ) )
			{
				tooltips.put( UserFeedbackTooltip.ROOM_SERVICE, span );
			}
			else if( dataTag.endsWith( "connecting doors" ) )
			{
				tooltips.put( UserFeedbackTooltip.CONNECTING_DOORS, span );
			}
			else
			{
				throw new IllegalArgumentException( "No user feedback tooltip for " + dataTag );
			}
		}

		return tooltips;
	}

	//endregion


	//region UserFeedbackObject -  Element Finder Methods Section

	private HtmlElement findExploreTooltipsDiv()
	{
		if( explore_tooltips == null )
		{
			final By findBy = By.className( "explore-tooltips" );
			explore_tooltips = getRoot().findElement( findBy );
		}
		return explore_tooltips;
	}

	private List<HtmlElement> findTooltipSpans()
	{
		final By findBy = By.cssSelector( ".explore-room span.eroom-tooltip" );
		return getDriver().findElements( findBy );
	}

	//endregion

}
