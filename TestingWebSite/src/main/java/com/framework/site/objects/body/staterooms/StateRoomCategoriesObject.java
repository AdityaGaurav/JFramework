package com.framework.site.objects.body.staterooms;

import com.framework.asserts.JAssertion;
import com.framework.driver.event.HtmlElement;
import com.framework.driver.objects.AbstractWebObject;
import com.framework.utils.datetime.TimeConstants;
import com.framework.utils.matchers.JMatchers;
import com.google.common.base.Optional;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static com.framework.utils.datetime.TimeConstants.THREE_SECONDS;
import static org.hamcrest.Matchers.is;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.site.objects.body.staterooms
 *
 * Name   : StateRoomCategories 
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-03-08 
 *
 * Time   : 10:14
 *
 */

public class StateRoomCategoriesObject extends AbstractWebObject
{

	//region StateRoomCategories - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( StateRoomCategoriesObject.class );

	static final String LOGICAL_NAME = "State Rooms Categories";

	public static final By ROOT_BY = By.id( "accommodations" );

	public static final String SUBSET_FAMILY_FRIENDLY_FEATURES = "family-friendly-features";

	public static final String SUBSET_CLOUD_9_SPA = "cloud-9-spa-staterooms";

	public static final String SUBSET_FAMILY_HARBOR = "family-harbor-staterooms";

	public static final String SUBSET_HAVANA_STATEROOMS = "havana-staterooms";

	// ------------------------------------------------------------------------|
	// --- WEB-OBJECTS DEFINITIONS --------------------------------------------|
	// ------------------------------------------------------------------------|

	// ------------------------------------------------------------------------|
	// --- WEB-OBJECTS CACHING ------------------------------------------------|
	// ------------------------------------------------------------------------|

	private HtmlElement h2, intro, filter_list;

	//endregion


	//region StateRoomCategories - Constructor Methods Section

	public StateRoomCategoriesObject( final HtmlElement rootElement )
	{
		super( rootElement, LOGICAL_NAME );
		initWebObject();
	}

	//endregion


	//region StateRoomCategories - Initialization and Validation Methods Section

	@Override
	protected void initWebObject()
	{
		logger.info( "validating static elements for web object id: <{}>, name:<{}>...",
				getQualifier(), getLogicalName() );

		final String REASON1 = "assert that element \"%s\" exits";

		JAssertion assertion = getRoot().createAssertion();
		Optional<HtmlElement> e = getRoot().childExists( By.className( "intro" ), THREE_SECONDS );
		assertion.assertThat( String.format( REASON1, ".intro" ), e.isPresent(), is( true ) );
		intro = e.get();

		e = getRoot().childExists( By.tagName( "h2" ), THREE_SECONDS );
		assertion.assertThat( String.format( REASON1, "h2" ), e.isPresent(), is( true ) );
		h2 = e.get();

		e = getRoot().childExists( By.className( "stateroom-widget" ), THREE_SECONDS );
		assertion.assertThat( String.format( REASON1, "stateroom-widget" ), e.isPresent(), is( true ) );
	}

	//endregion


	//region StateRoomCategories - Service Methods Section

	private HtmlElement getRoot()
	{
		return getBaseRootElement( ROOT_BY );
	}

	//endregion


	//region StateRoomCategories - Implementation Function Section

	public HtmlElement getContainer()
	{
		return getRoot();
	}

	public List<HtmlElement> getActiveSubsets()
	{
		return findActiveLis();
	}

	public boolean hasTitle()
	{
		return h2.getText().length() > 0;
	}

	public boolean hasIntro()
	{
		return intro.findElement( By.tagName( "p" ) ).getText().length() > 0;
	}

	public boolean isSelected( String subset )
	{
		HtmlElement a = findSubsetAnchor( subset );
		HtmlElement parentLi = a.findElement( By.xpath( "/../parent::li" ) );
		String className = parentLi.getAttribute( "class" );
		boolean active = className.equals( "active" );
		logger.info( "Determine if < {} > subset is currently selected ... < {} >", subset, active );
		return active;
	}

	public void selectSubset( String subset )
	{
		logger.info( "Clicking on subset < {} >", subset );
		HtmlElement a = findSubsetAnchor( subset );
		a.click();
		HtmlElement parentLi = a.findElement( By.xpath( "/../parent::li" ) );
		parentLi.waitAttributeToMatch( "class", JMatchers.is( "active" ), TimeConstants.FIVE_SECONDS );
	}

	public int getSubsetCount()
	{
   		return getDriver().findElements( By.cssSelector( ".main li a.show-subset" ) ).size();
	}

	//endregion


	//region StateRoomCategories - Element Finder Section

	private HtmlElement findSubsetAnchor( String subset )
	{
		return getRoot().findElement( By.cssSelector( String.format( ".main li a.show-subset[data-id=\"%s\"]", subset ) ) );
	}

	private List<HtmlElement> findActiveLis()
	{
		return getDriver().findElements( By.cssSelector( "div.filter-block.filter-list li.active" ) );
	}

	//endregion

}
