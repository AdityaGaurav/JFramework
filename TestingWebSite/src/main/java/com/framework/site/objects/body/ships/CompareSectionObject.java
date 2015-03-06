package com.framework.site.objects.body.ships;

import ch.lambdaj.Lambda;
import com.framework.asserts.JAssertion;
import com.framework.config.Configurations;
import com.framework.driver.event.HtmlElement;
import com.framework.driver.exceptions.ApplicationException;
import com.framework.driver.objects.AbstractWebObject;
import com.framework.site.config.SiteProperty;
import com.framework.site.data.Ships;
import com.framework.site.objects.body.interfaces.ContentBlockComparing;
import com.framework.utils.matchers.JMatchers;
import com.google.common.base.Joiner;
import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.framework.utils.datetime.TimeConstants.ONE_SECOND;
import static com.framework.utils.datetime.TimeConstants.THREE_SECONDS;
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
 * Time   : 18:59
 *
 */

class CompareSectionObject extends AbstractWebObject implements ContentBlockComparing.CompareSection
{

	//region CompareSectionObject - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( CompareSectionObject.class );

	public static final int SCROLL_OFFSET = 130;

	private String sectionName = "N/A";

	private Map<String,Map.Entry<HtmlElement,Map<Ships,String>>> rows = Maps.newLinkedHashMap();

	private List<Ships> shipsSections = Lists.newArrayListWithCapacity( 3 );

	// ------------------------------------------------------------------------|
	// --- WEB-OBJECTS CACHING ------------------------------------------------|
	// ------------------------------------------------------------------------|

	private HtmlElement h2, table;

	//endregion


	//region CompareSectionObject - Constructor Methods Section

	CompareSectionObject( final HtmlElement rootElement )
	{
		super( rootElement, ContentBlockComparing.CompareSection.LOGICAL_NAME );
		initWebObject();
	}

	//endregion


	//region CompareSectionObject - Initialization and Validation Methods Section

	@Override
	protected void initWebObject()
	{
		logger.info( "validating static elements for web object id: <{}>, name:<{}>...",
				getQualifier(), getLogicalName() );

		final String REASON = "assert that element \"%s\" exits";

		JAssertion assertion = getRoot().createAssertion();
		Optional<HtmlElement> e = getRoot().childExists( By.tagName( "h2" ), THREE_SECONDS );
		assertion.assertThat( String.format( REASON, "h2" ), e.isPresent(), is( true ) );
		h2 = e.get();
		this.sectionName = h2.getText();

		e = getRoot().childExists( By.tagName( "table" ), ONE_SECOND );
		assertion.assertThat( String.format( REASON, "h2" ), e.isPresent(), is( true ) );
		table = e.get();
	}

	//endregion


	//region CompareSectionObject - Service Function Section

	@Override
	public String toString()
	{
		return new ToStringBuilder( this )
				.append( "section name", sectionName )
				.toString();
	}

	private HtmlElement getRoot()
	{
		return getBaseRootElement();
	}

	private void populateTableData()
	{
		List<HtmlElement> trs = table.findElements( By.tagName( "tr" ) );
		logger.info( "parsing < {} > table rows ...", trs.size() );
		for( HtmlElement tr : trs )
		{
			HtmlElement th =  tr.findElement( By.tagName( "th" ) );
			List<HtmlElement> values = tr.findElements( By.tagName( "td" ) );
			Map<Ships,String> mapValues = Maps.newHashMap();

			for( int i = 0; i < values.size(); i ++ )
			{
				HtmlElement value = values.get( i );
				String innerHtml = value.getAttribute( "innerHTML" );
				if( sectionName.equalsIgnoreCase( "Ship Details" ) )
				{
					logger.info( "parsing section SHIP DETAILS ..." );
					mapValues.put( shipsSections.get( i ), value.getText() );
				}
				else if( innerHtml.contains( "filter-soon" ) )
				{
	  				String expectedText = SiteProperty.FILTER_SOON_TEXT.from( Configurations.getInstance(), "COMING SOON" );
					new JAssertion( getDriver() ).assertThat( "Validate FILTER-SOON text", innerHtml, JMatchers.containsString( expectedText ) );
					mapValues.put( shipsSections.get( i ), "CS" );
				}
				else if( innerHtml.contains( "availableIcon" ) )
				{
					mapValues.put( shipsSections.get( i ), "AV" );
				}
				else if( innerHtml.contains( "notAvailableIcon" ) )
				{
					mapValues.put( shipsSections.get( i ), "NA" );
				}
			}

			Map.Entry<HtmlElement,Map<Ships,String>> cols = new ImmutablePair<>( th, mapValues );
			rows.put( th.getText(), cols );
		}
	}

	void setShipsSections( final List<Ships> shipsSections )
	{
		this.shipsSections = shipsSections;
	}

	//endregion


	//region CompareSectionObject - Interface Implementation Section

	@Override
	public List<Ships> getShipsSections()
	{
		return shipsSections;
	}

	@Override
	public void expand()
	{
		if( ! isExpanded() )
		{
			logger.info( "Expanding section < {} >", sectionName );
			HtmlElement he = getRoot().findElement( By.className( "compare-items" ) );
			he.scrollBy( 0, SCROLL_OFFSET );
			findExpandCollapseI( h2 ).click();
			he.waitToBeDisplayed( true, THREE_SECONDS );
		}
		populateTableData();
	}

	@Override
	public void collapse()
	{
		if( isExpanded() )
		{
			logger.info( "Collapsing section < {} >", sectionName );
			HtmlElement he = findCompareItemsDiv();
			findExpandCollapseI( h2 ).click();
			he.waitToBeDisplayed( false, THREE_SECONDS );
		}
	}

	@Override
	public boolean isExpanded()
	{
		boolean expanded = h2.getAttribute( "class" ).equals( "expanded" );
		logger.info( "determine if section < {} > currently expanded", sectionName );
		return expanded;
	}

	@Override
	public String getSectionName()
	{
		logger.info( "return the current section name < {} >", sectionName );
		return sectionName;
	}

	@Override
	public List<String> getParameters()
	{
		List<String> params = Lists.newArrayList( rows.keySet() );
		logger.info( "return a list of parameters < {} >", Lambda.join( params, ", " ) );
		return params;
	}

	@Override
	public Map<String, Map.Entry<HtmlElement, Map<Ships, String>>> getRows()
	{
		return rows;
	}

	@Override
	public HtmlElement getTable()
	{
		return findTable();
	}

	@Override
	public boolean indicatorNotVisible( Map<Ships,String> values )
	{
		String valuesJoined = Joiner.on( "," ).join( ( ( HashMap ) values ).values() );
		logger.info( "Examine comparison values < {} >", valuesJoined );
		if ( valuesJoined.equals( "NA,NA,NA" ) || valuesJoined.equals( "NA,NA" ) || valuesJoined.equals( "NA" ) )
		{
			throw new ApplicationException( getDriver(), "All parameters are unavailable" );
		}
		else if ( valuesJoined.equals( "AV,AV,AV" ) || valuesJoined.equals( "AV,AV" ) || valuesJoined.equals( "AV" ) )
		{
			return true;
		}

		return false;
	}


	//endregion


	//region CompareSectionObject - Element Finder Section

	private HtmlElement findCompareItemsDiv()
	{
		final By findBy = By.className( "compare-items" );
		return getRoot().findElement( findBy );
	}

	private HtmlElement findExpandCollapseI( HtmlElement he )
	{
		final By findBy = By.tagName( "i" );
		return he.findElement( findBy );
	}

	private HtmlElement findTable()
	{
		if( null == table )
		{
			final By findBy = By.tagName( "table" );
			table = getRoot().findElement( findBy );
		}

		return table;
	}

	//endregion

}
