package com.framework.site.objects.footer;

import com.framework.asserts.JAssertion;
import com.framework.driver.event.HtmlElement;
import com.framework.driver.extensions.jquery.By;
import com.framework.driver.objects.AbstractWebObject;
import com.framework.driver.objects.Link;
import com.framework.site.data.Enumerators;
import com.framework.utils.matchers.JMatchers;
import com.google.common.base.Optional;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Lists;
import com.google.common.collect.Table;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static com.framework.utils.datetime.TimeConstants.FIVE_SECONDS;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.site.objects.footer
 *
 * Name   : LinkListObject
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-01-09
 *
 * Time   : 19:07
 */

public class LinkListObject extends AbstractWebObject implements Enumerators
{

	//region LinkListObject - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( LinkListObject.class );

	static final org.openqa.selenium.By ROOT_BY = By.className( "link-lists" );

	static final String LOGICAL_NAME = "Link-List";

	// ------------------------------------------------------------------------|
	// --- WEB-OBJECTS DEFINITIONS --------------------------------------------|
	// ------------------------------------------------------------------------|

	// ------------------------------------------------------------------------|
	// --- WEB-OBJECTS CACHING ------------------------------------------------|
	// ------------------------------------------------------------------------|

	private List<HtmlElement> linkListUl = Lists.newArrayList();

	//endregion


	//region LinkListObject - Constructor Methods Section

	LinkListObject( final HtmlElement rootElement )
	{
		super( rootElement, LOGICAL_NAME );
		initWebObject();
	}

	//endregion


	//region LinkListObject - Initialization and Validation Methods Section

	@Override
	protected void initWebObject()
	{
		logger.debug( "validating static elements for web object id: <{}>, name:<{}>...",
				getQualifier(), getLogicalName() );

		final String REASON = "assert that all elements \"%s\" exits";
		JAssertion assertion = getRoot().createAssertion();

		Optional<List<HtmlElement>> e = getRoot().allChildrenExists( By.tagName( "ul" ), FIVE_SECONDS );
		assertion.assertThat( String.format( REASON, ".link-lists ul" ), e.isPresent(), JMatchers.is( true ) );
		linkListUl = e.get();
	}

	//endregion


	//region LinkListObject - Service Methods Section

	private HtmlElement getRoot()
	{
		return getBaseRootElement( LinkListObject.ROOT_BY );
	}

	//endregion


	//region LinkListObject - Business Methods Section

	public boolean isDisplayed()
	{
		return getRoot().isDisplayed();
	}

	public Table<String,String,String> getInfo()
	{
		Table<String,String,String> linkList = HashBasedTable.create();
		List<HtmlElement> uls = findFooterSectionsUl();
		for( HtmlElement ul : uls )
		{
			List<HtmlElement> anchors = ul.findElements( By.tagName( "a" ) );
			String sectionName = ul.findElement( By.tagName( "h5" ) ).getText();
			for( HtmlElement anchor : anchors )
			{
				String text = anchor.getText();
				String href = anchor.getAttribute( "href" );
				linkList.put( sectionName, text, href );
			}
		}

		return linkList;
	}

	public void clickLink( FooterSection section, String name )
	{
		Link link = null;
		switch ( section )
		{
			case ABOUT_CARNIVAL:
				link = new Link( findLink( "About Carnival", name ) );
				break;
			case ALREADY_BOOKED:
				link = new Link( findLink( "Already Booked", name ) );
				break;
			case BOOK_A_CRUISE:
				link = new Link( findLink( "Book A Cruise", name ) );
				break;
			case CUSTOMER_SERVICE:
				link = new Link( findLink( "Customer Service", name ) );
				break;
			case GROUP_TRAVEL:
				link = new Link( findLink( "Group Travel", name ) );
				break;
			case SPECIAL_OFFERS:
				link = new Link( findLink( "Special Offers", name ) );
		}

		link.click();
	}

	//endregion


	//region LinkListObject - Element Finder Methods Section

	private List<HtmlElement> findFooterSectionsUl()
	{
		if( linkListUl.size() == 0 )
		{
			linkListUl = getRoot().findElements( By.tagName( "ul" ) );
		}

		return linkListUl;
	}

	private HtmlElement findLink( String section, String name )
	{
		HtmlElement he = getDriver().findElement( By.jQuerySelector( "ul > li > h5:contains('" + section + "')" ) );
		return he.findElement( By.linkText( name ) );
	}

	//endregion

}
