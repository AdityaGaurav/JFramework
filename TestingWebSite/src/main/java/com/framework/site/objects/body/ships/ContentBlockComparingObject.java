package com.framework.site.objects.body.ships;

import com.framework.asserts.JAssertion;
import com.framework.driver.event.HtmlElement;
import com.framework.driver.objects.AbstractWebObject;
import com.framework.site.data.Ships;
import com.framework.site.objects.body.interfaces.ContentBlockComparing;
import com.framework.utils.matchers.JMatchers;
import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import org.hamcrest.Matcher;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static com.framework.utils.datetime.TimeConstants.FIVE_SECONDS;
import static org.hamcrest.Matchers.is;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.site.objects.body.ships
 *
 * Name   : CompareSectionObject 
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-02-16 
 *
 * Time   : 17:22
 *
 */

public class ContentBlockComparingObject extends AbstractWebObject implements ContentBlockComparing
{

	//region ContentBlockComparingObject - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( ContentBlockComparingObject.class );

	private int comparingShips = 1;

	private List<Ships> compareShips;

	// ------------------------------------------------------------------------|
	// --- WEB-OBJECTS DEFINITIONS --------------------------------------------|
	// ------------------------------------------------------------------------|

	private CompareLabels compareLabels;

	// ------------------------------------------------------------------------|
	// --- WEB-OBJECTS CACHING ------------------------------------------------|
	// ------------------------------------------------------------------------|

	private List<CompareSection> sections = Lists.newArrayList();

	//endregion


	//region ContentBlockComparingObject - Constructor Methods Section

	public ContentBlockComparingObject( final HtmlElement rootElement, int ships )
	{
		super( rootElement, ContentBlockComparing.LOGICAL_NAME );
		comparingShips = ships;
		initWebObject();
	}

	//endregion


	//region ContentBlockComparingObject - Initialization and Validation Methods Section

	@Override
	protected void initWebObject()
	{
		logger.debug( "validating static elements for web object id: <{}>, name:<{}>...",
				getQualifier(), getLogicalName() );

		final String REASON = "assert that element \"%s\" exits";

		String REASON1 = "Validate that comparison is among/between " + comparingShips + " ships";
		String ACTUAL_RESULT_STR = getRoot().getAttribute( "class" );
		Matcher<String> EXPECTED_OF_STR = JMatchers.endsWith( String.format( "comparing-%d", comparingShips ) );
		getDriver().assertThat( REASON1, ACTUAL_RESULT_STR, EXPECTED_OF_STR );

		JAssertion assertion = getRoot().createAssertion();
		Optional<HtmlElement> e = getRoot().childExists( By.cssSelector( "div.compare-section.quick-preview" ), FIVE_SECONDS );
		assertion.assertThat( String.format( REASON, ".compare-section.quick-preview" ), e.isPresent(), is( true ) );
	}

	//endregion


	//region ContentBlockComparingObject - Service Methods Section

	private HtmlElement getRoot()
	{
		return getBaseRootElement( ContentBlockComparing.ROOT_BY );
	}

	private CompareLabels compareLabels()
	{
		if( null == compareLabels )
		{
			compareLabels =  new CompareLabelsObject( getDriver().findElement( CompareLabels.ROOT_BY ) );
		}

		return compareLabels;
	}

	//endregion


	//region ContentBlockComparingObject - Implementation Methods Section

	@Override
	public String getSectionName( final int index )
	{
		return null;
	}

	@Override
	public List<CompareSection> getSections()
	{
		if( sections.size() == 0 )
		{
			List<HtmlElement> list = findComparisonSections();
			for( HtmlElement he : list )
			{
				CompareSectionObject cso = new CompareSectionObject( he );
				cso.setShipsSections( compareLabels().getShipSections() );
				sections.add( cso );
			}
		}

		return sections;
	}


	//endregion


	//region ContentBlockComparingObject - Element Finder Methods Section

	private List<HtmlElement> findComparisonSections()
	{
		final By findBy = By.cssSelector( ".compare-section.comparison:not(.detailed-preview)" );
		return getDriver().findElements( findBy );
	}

	//endregion
}
