package com.framework.site.objects.footer;

import com.framework.asserts.JAssertion;
import com.framework.driver.event.ExtendedBy;
import com.framework.driver.event.HtmlElement;
import com.framework.driver.objects.AbstractWebObject;
import com.framework.driver.objects.Link;
import com.framework.site.objects.footer.enums.FooterItem;
import com.framework.site.objects.footer.interfaces.Footer;
import com.framework.utils.matchers.JMatchers;
import com.google.common.base.Optional;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;

import static com.framework.utils.datetime.TimeConstants.FIVE_SECONDS;
import static com.framework.utils.datetime.TimeConstants.THREE_SECONDS;


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

class SubFooterObject extends AbstractWebObject implements Footer.SubFooter
{

	//region SubFooterObject - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( SubFooterObject.class );

	private HtmlElement ul_minor, ul_social;

	//endregion


	//region SubFooterObject - Constructor Methods Section

	SubFooterObject( final HtmlElement rootElement )
	{
		super( rootElement, Footer.SubFooter.LOGICAL_NAME );
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

		Optional<HtmlElement> e = getRoot().childExists( Footer.LinkList.ROOT_BY, FIVE_SECONDS );
		assertion.assertThat( String.format( REASON, "ul.minor" ), e.isPresent(), JMatchers.is( true ) );
		ul_minor = e.get();

		e = getRoot().childExists( Footer.SubFooter.ROOT_BY, THREE_SECONDS );
		assertion.assertThat( String.format( REASON, "ul.social" ), e.isPresent(), JMatchers.is( true ) );
		ul_social = e.get();
	}

	//endregion


	//region SubFooterObject - Service Methods Section

	private HtmlElement getRoot()
	{
		return getBaseRootElement( Footer.SubFooter.ROOT_BY );
	}

	//endregion


	//region SubFooterObject - Footer.SubFooter Implementation Methods Section

	@Override
	public boolean isDisplayed()
	{
		return getRoot().isDisplayed();
	}

	@Override
	public boolean itemExists( final FooterItem item )
	{
		return false;
	}

	@Override
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

	@Override
	public HtmlElement getTradeMark()
	{
		return null;
	}

	@Override
	public URL getFacebookLikeRef()
	{
		HtmlElement form = findLikeForm();
		String href = form.getAttribute( "ajaxify" );
		getDriver().switchTo().defaultContent();
		try
		{
			return new URL( href );
		}
		catch ( MalformedURLException e )
		{
			logger.error( e.getMessage() );
			return null;
		}
	}

	//endregion


	//region SubFooterObject - Element Finder Methods Section

	private HtmlElement findLikeForm()
	{
		final By findBy = By.id( "u_0_0" );
		getDriver().switchTo().frame( "lazy_like" );
		return getDriver().findElement( findBy );
	}

	private HtmlElement findSocial( FooterItem item )
	{
		String jquery = String.format( "$(\".social img[alt='%s']\").parent()", item.getPropertyName() );
		final By findBy = ExtendedBy.jQuery( jquery );
		return getDriver().findElement( findBy );
	}

	private HtmlElement findLeftItem( FooterItem item )
	{
		final By findBy = By.xpath( String.format( "//a[text()=\"%s\"]", item.getPropertyName() ) );
		return ul_minor.findElement( findBy );
	}

	//endregion

}