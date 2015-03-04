package com.framework.site.pages.core.cruiseships;

import com.framework.driver.event.HtmlElement;
import com.framework.site.objects.body.interfaces.ContentBlockComparing;
import com.framework.site.objects.body.ships.ContentBlockComparingObject;
import com.framework.site.pages.BaseCarnivalPage;
import com.framework.testing.annotations.DefaultUrl;
import com.framework.utils.matchers.JMatchers;
import org.hamcrest.Matcher;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.site.pages.core.cruiseships
 *
 * Name   : CompareCruiseShipsPage 
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-02-16 
 *
 * Time   : 00:10
 *
 */

@DefaultUrl( value = "/cruise-ships/compare-cruise-ships", matcher = "endsWith()" )
public class CompareCruiseShipsPage extends BaseCarnivalPage
{

	//region CompareCruiseShipsPage - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( CompareCruiseShipsPage.class );

	private static final String LOGICAL_NAME = "Compare Cruise Ships";

	private static int compareShipsCount = 1;

	// ------------------------------------------------------------------------|
	// --- WEB-OBJECTS DEFINITIONS --------------------------------------------|
	// ------------------------------------------------------------------------|

	private ContentBlockComparing contentBlockComparing = null;

	// ------------------------------------------------------------------------|
	// --- WEB-OBJECTS CACHING ------------------------------------------------|
	// ------------------------------------------------------------------------|

	HtmlElement legend;

	//endregion


	//region CompareCruiseShipsPage - Constructor Methods Section

	public CompareCruiseShipsPage()
	{
		super( LOGICAL_NAME );
		validatePageInitialState();
	}


	//endregion


	//region CompareCruiseShipsPage - Initialization and Validation Methods Section

	@Override
	protected void validatePageInitialState()
	{
		logger.debug( "validating page initial state for: <{}>, name:<{}>...", getQualifier(), getLogicalName() );

		String REASON = "Validate that comparison is among/between " + compareShipsCount + " ships";
		String ACTUAL_RESULT_STR = findContentBlockDiv().getAttribute( "class" );
		Matcher<String> EXPECTED_OF_STR = JMatchers.endsWith( String.format( "comparing-%d", compareShipsCount ) );
		getDriver().assertThat( REASON, ACTUAL_RESULT_STR, EXPECTED_OF_STR );

		REASON = "Validate Legend keys";
		ACTUAL_RESULT_STR = findLegendDiv().getAttribute( "textContent" ).trim();
		EXPECTED_OF_STR = JMatchers.allOf(  JMatchers.containsString( "Key:" ),
				JMatchers.containsString( "Available" ),
				JMatchers.containsString( "| Not Available" ) );
		getDriver().assertThat( REASON, ACTUAL_RESULT_STR, EXPECTED_OF_STR );

		List<HtmlElement> images = findLegendImages();
		REASON = "Validate Legend Image Available";
		ACTUAL_RESULT_STR = images.get( 0 ).getAttribute( "class" );
		EXPECTED_OF_STR = JMatchers.is( "availableIcon" );
		getDriver().assertThat( REASON, ACTUAL_RESULT_STR, EXPECTED_OF_STR );

		REASON = "Validate Legend Image Not Available";
		ACTUAL_RESULT_STR = images.get( 1 ).getAttribute( "class" );
		EXPECTED_OF_STR = JMatchers.is( "notAvailableIcon" );
		getDriver().assertThat( REASON, ACTUAL_RESULT_STR, EXPECTED_OF_STR );
	}


	//endregion


	//region CompareCruiseShipsPage - Service Methods Section

	public static void forShips( final int compareShipsCount )
	{
		CompareCruiseShipsPage.compareShipsCount = compareShipsCount;
	}

	public ContentBlockComparing contentBlockComparing()
	{
		if( null == this.contentBlockComparing )
		{
			this.contentBlockComparing =
					new ContentBlockComparingObject( getDriver().findElement( ContentBlockComparing.ROOT_BY ), compareShipsCount );
		}

		return this.contentBlockComparing;
	}

	//endregion


	//region CompareCruiseShipsPage - Element Finder Methods Section

	private HtmlElement findContentBlockDiv()
	{
		final By findBy = By.cssSelector( ".content-block[class*=\"comparing\"]" );
		return getDriver().findElement( findBy );
	}

	private HtmlElement findLegendDiv()
	{
		if( null == legend )
		{
			final By findBy = By.className( "legend" );
			legend = getDriver().findElement( findBy );
		}

		return legend;
	}

	private List<HtmlElement> findLegendImages()
	{
		final By findBy = By.tagName( "img" );
		return findLegendDiv().findElements( findBy );
	}

	//endregion

}
