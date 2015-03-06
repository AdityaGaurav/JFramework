package com.framework.site.objects.body.ships;

import ch.lambdaj.Lambda;
import com.framework.asserts.JAssertion;
import com.framework.driver.event.HtmlElement;
import com.framework.driver.objects.AbstractWebObject;
import com.framework.site.data.Ships;
import com.framework.site.objects.body.interfaces.ContentBlockComparing;
import com.framework.utils.error.PreConditions;
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
			compareLabels = new CompareLabelsObject( getDriver().findElement( CompareLabels.ROOT_BY ) );
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
	public List<String> getSectionNames( final int index )
	{
		List<HtmlElement> spans = findSectionsSpans();
		List<String> names = Lambda.extractProperty( spans, "text" );
		logger.info( "Return a list of section names < {} >", Lambda.join( names, ", " ) );
		return names;
	}

	@Override
	public List<CompareSection> getExpandedSections()
	{
		List<HtmlElement> expanded = findExpandedSectionsDivs();
		logger.info( "current expanded sections: < {} >", expanded.size() );
		List<CompareSection> sections = Lists.newArrayListWithExpectedSize( expanded.size() );
		for( HtmlElement he : expanded )
		{
			CompareSectionObject cso = new CompareSectionObject( he );
			sections.add( cso );
		}

		return sections;
	}

	@Override
	public List<CompareSection> getCollapsedSections()
	{
		List<HtmlElement> collapsed = findCollapsedSectionsDivs();
		logger.info( "current collapsed sections: < {} >", collapsed.size() );
		List<CompareSection> sections = Lists.newArrayListWithExpectedSize( collapsed.size() );
		for( HtmlElement he : collapsed )
		{
			CompareSectionObject cso = new CompareSectionObject( he );
			sections.add( cso );
		}

		return sections;
	}

	@Override
	public void collapseAll()
	{
		List<CompareSection> expanded = getExpandedSections();
		for( CompareSection section : expanded )
		{
			section.collapse();
		}

	}

	@Override
	public CompareSection getSection( final String name )
	{
		PreConditions.checkNotNullNotBlankOrEmpty( name, "The section name is either null, empty or blank" );
		logger.info( "Return section element < {} >", name );
		HtmlElement he = findSectionDiv( name );
		return new CompareSectionObject( he );
	}

	@Override
	public List<CompareSection> getComparisonSections()
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

	private HtmlElement findSectionDiv( String name )
	{
		final By findBy = By.xpath( String.format( "//span[contains(.,'%s')]", name ) );
		return getDriver().findElement( findBy );
	}

	private List<HtmlElement> findSectionsSpans()
	{
		final By findBy = By.cssSelector( ".compare-section.comparison h2 > span" );
		return getDriver().findElements( findBy );
	}

	private List<HtmlElement> findExpandedSectionsDivs()
	{
		final By findBy = By.xpath( "//h2[@class='expanded']/.." );
		return getDriver().findElements( findBy );
	}

	private List<HtmlElement> findCollapsedSectionsDivs()
	{
		final By findBy = By.xpath( "//div[contains(@class,'compare-section')]/h2[not(@class='expanded')]" );
		return getDriver().findElements( findBy );
	}

	//endregion
}
