package com.framework.site.objects.footer;

import com.framework.asserts.JAssertion;
import com.framework.driver.event.HtmlElement;
import com.framework.driver.extensions.jquery.By;
import com.framework.driver.objects.AbstractWebObject;
import com.framework.driver.objects.Link;
import com.framework.site.objects.footer.enums.FooterItem;
import com.framework.utils.matchers.JMatchers;
import com.google.common.base.Optional;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.site.objects.footer
 *
 * Name   : SubFooterObject
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-01-06
 *
 * Time   : 16:55
 */

public class SubFooterObject extends AbstractWebObject
{

	//region SubFooterObject - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( SubFooterObject.class );

	static final org.openqa.selenium.By ROOT_BY = By.className( "sub-footer" );

	private static final String LOGICAL_NAME = "Sub-Footer";

	// ------------------------------------------------------------------------|
	// --- WEB-OBJECTS CACHING ------------------------------------------------|
	// ------------------------------------------------------------------------|

	private HtmlElement ul_minor, ul_social;

	//endregion


	//region SubFooterObject - Constructor Methods Section

	SubFooterObject( final HtmlElement rootElement )
	{
		super( rootElement, LOGICAL_NAME );
		initWebObject();
	}

	//endregion


	//region SubFooterObject - Initialization and Validation Methods Section

	@Override
	protected void initWebObject()
	{

		logger.debug( "validating static elements for web object id: <{}>, name:<{}>...",
				getQualifier(), getLogicalName() );

		final String REASON = "assert that element \"%s\" exits";
		JAssertion assertion = getRoot().createAssertion();

		Optional<HtmlElement> e = getDriver().elementExists( LinkListObject.ROOT_BY );
		assertion.assertThat( String.format( REASON, "ul.minor" ), e.isPresent(), JMatchers.is( true ) );
		ul_minor = e.get();

		e = getDriver().elementExists( SubFooterObject.ROOT_BY );
		assertion.assertThat( String.format( REASON, "ul.social" ), e.isPresent(), JMatchers.is( true ) );
		ul_social = e.get();
	}

	//endregion


	//region SubFooterObject - Service Methods Section

	private HtmlElement getRoot()
	{
		return getBaseRootElement( SubFooterObject.ROOT_BY );
	}

	//endregion


	//region SubFooterObject - Footer.SubFooter Implementation Methods Section

	public boolean isDisplayed()
	{
		boolean isDisplayed = getRoot().isDisplayed();
		logger.info( "Determine if sub footer is currently displayed < {} >", isDisplayed );
		return isDisplayed;
	}

	public boolean itemExists( final FooterItem item )
	{
		logger.info( "Determine if item < {} > exists", item );
		switch ( item )
		{
			case ABOUT_CARNIVAL:
				return getRoot().childExists( By.linkText( "About Carnival" ) ).isPresent();
			case LEGAL_NOTICES:
				return getRoot().childExists( By.linkText( "Legal Notices" ) ).isPresent();
			case PRIVACY_POLICY:
				return getRoot().childExists( By.linkText( "Privacy Policy" ) ).isPresent();
			case CAREERS:
				return getRoot().childExists( By.linkText( "Careers" ) ).isPresent();
			case TRAVEL_PARTNERS:
				return getRoot().childExists( By.linkText( "Travel Partners" ) ).isPresent();
			case SITE_MAP:
				return getRoot().childExists( By.linkText( "Site Map" ) ).isPresent();
		}

		return false;
	}

	public Link getFooterLinkItem( final FooterItem item )
	{
		switch ( item )
		{
			case FACEBOOK:
			case INSTAGRAM:
			case PINTEREST:
			case TWITTER:
			case FUNVILLE:
			{
				return new Link( findSocial( item ) );
			}
			default:
			{
				return new Link( findLeftItem( item ) );
			}
		}
	}

	public HtmlElement getTradeMark()
	{
		logger.info( "Returning Trademark element" );
		return getDriver().findElement( By.cssSelector( ".social.pull-right h4" ) );
	}

	public String getFacebookLikeRef()
	{
		try
		{
			HtmlElement form = findLikeForm();
			String href = form.getAttribute( "ajaxify" );
			logger.info( "Returning facebook link href < {} >", href );
			return href;
		}
		finally
		{
			getDriver().switchTo().defaultContent();
		}
	}

	public Map<String,String> getInfo()
	{
		logger.info( "Returning a map of sub-footer links ..." );
		Map<String,String> links = Maps.newHashMap();
		List<HtmlElement> anchors = findMinorAnchors();
		for( HtmlElement he : anchors )
		{
			links.put( he.getText(), he.getAttribute( "href" ) );
		}

		return links;
	}

	//endregion


	//region SubFooterObject - Element Finder Methods Section

	private HtmlElement findLikeForm()
	{
		final org.openqa.selenium.By findBy = By.id( "u_0_0" );
		getDriver().switchTo().frame( "lazy_like" );
		return getDriver().findElement( findBy );
	}

	private HtmlElement findSocial( FooterItem item )
	{
		String jquery = String.format( ".social img[alt='%s']", item.getPropertyName() );
		return getDriver().findElement( By.jQuerySelector( jquery ).parent() );
	}

	private HtmlElement findLeftItem( FooterItem item )
	{
		return ul_minor.findElement( By.linkText( item.getPropertyName() ) );
	}

	private List<HtmlElement> findMinorAnchors()
	{
		return getDriver().findElements( By.cssSelector( "ul.minor.pull-left a" ) );
	}

	//endregion

}