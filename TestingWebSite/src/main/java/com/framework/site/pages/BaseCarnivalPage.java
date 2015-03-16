package com.framework.site.pages;

import com.framework.asserts.JAssertion;
import com.framework.driver.event.*;
import com.framework.driver.objects.AbstractPageObject;
import com.framework.site.config.SiteSessionManager;
import com.framework.site.objects.body.common.SectionBreadcrumbsBarObject;
import com.framework.site.objects.footer.SectionFooterObject;
import com.framework.site.objects.header.SectionHeaderObject;
import com.framework.site.objects.header.interfaces.Header;
import com.framework.utils.matchers.JMatchers;
import com.google.common.base.MoreObjects;
import com.google.common.base.Optional;
import com.google.common.collect.Sets;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.NoSuchElementException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Locale;
import java.util.Map;
import java.util.Set;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.site.pages
 *
 * Name   : CarnivalPage
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-01-06
 *
 * Time   : 20:28
 */

public abstract class BaseCarnivalPage extends AbstractPageObject
{

	//region BaseCarnivalPage - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( BaseCarnivalPage.class );

	public static final Locale AU = new Locale( "en", "AU" );

	protected static final String USER_LAST_NAME_COOKIE = "UserLastName";

	public static final String USER_FIRST_NAME_COOKIE = "UserFirstName";

	public static final String VACATION_WITH_KIDS_COOKIE = "ccl_learn_experience";

	protected static final String USER_EMAIL_ADDRESS_COOKIE = "UserEmailAddress";

	private String pageId, pageName, site_region, referrer;

	private final String hst;

	private Locale currentLocale = SiteSessionManager.get().getCurrentLocale();

	// ------------------------------------------------------------------------|
	// --- WEB-OBJECTS DEFINITIONS --------------------------------------------|
	// ------------------------------------------------------------------------|

	private Header header = null;

	private SectionFooterObject footer = null;

	//endregion


	//region BaseCarnivalPage - Constructor Methods Section

	protected BaseCarnivalPage( final String logicalName )
	{
		super( SiteSessionManager.get().getDriver(), logicalName );


		this.hst = getHstValue();
		SiteSessionManager.get().setHstValue( hst );
		logger.info( "Current environment code is < '{}' >", hst );
		if( parseUtagData() )
		{
			Locale expected = SiteSessionManager.get().getCurrentLocale();
			new JAssertion( getDriver() )
					.assertThat( "Asserting locale from configuration and actual", parseRegion(), JMatchers.is( expected ) );
		}
	}

	//endregion


	//region BaseCarnivalPage - UTAG getters Section

	protected Optional<String> u_tag_data_page_id()
	{
		return Optional.fromNullable( pageId );
	}

	protected Optional<String> u_tag_data_page_name()
	{
		return Optional.fromNullable( pageName );
	}

	protected Optional<String> u_tag_data_site_region()
	{
		return Optional.fromNullable( site_region );
	}

	protected Optional<String> u_tag_data_referrer()
	{
		return Optional.fromNullable( referrer );
	}

	//endregion


	//region BaseCarnivalPage - Service Methods Section

	protected Locale getCurrentLocale()
	{
		return currentLocale;
	}

	/**
	 * @return the {@link Header} instance implemented by {@link com.framework.site.objects.header.SectionHeaderObject}
	 */
	public Header header()
	{
		if ( null == this.header )
		{
			this.header = new SectionHeaderObject( getDriver().findElement( Header.ROOT_BY ) );
		}
		return header;
	}

	public SectionFooterObject footer()
	{
		if ( null == this.footer )
		{
			this.footer = new SectionFooterObject( getDriver().findElement( SectionFooterObject.ROOT_BY ) );
		}
		return footer;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper( this )
				.addValue( super.toString() )
				.add( "hst", hst )
				.add( "utag_data.site_region", u_tag_data_site_region() == null ? "N/A" : site_region )
				.add( "utag_data.pageId", u_tag_data_page_id() == null ? "N/A" : pageId )
				.add( "utag_data.pageName", u_tag_data_page_name() == null ? "N/A" : pageName )
				.add( "utag_data.referrer", referrer )
				.omitNullValues()
				.toString();
	}

	public String getHstValue()
	{
		Optional<HtmlElement> optional = findHstInput();
		if( optional.isPresent() )
		{
			return optional.get().getAttribute( "value" );
		}

		return "N/A";
	}

	public Set<Cookie> waitForUserCookies()
	{
		Set<Cookie> cookies = Sets.newHashSet();
		HtmlCondition<Cookie> uln = ExpectedConditions.cookieIsPresent( USER_LAST_NAME_COOKIE );
		HtmlCondition<Cookie> ufn = ExpectedConditions.cookieIsPresent( USER_FIRST_NAME_COOKIE );
		HtmlCondition<Cookie> uea = ExpectedConditions.cookieIsPresent( USER_EMAIL_ADDRESS_COOKIE );
		cookies.add( HtmlDriverWait.wait10( getDriver() ).until( uln ) );
		cookies.add( HtmlDriverWait.wait10( getDriver() ).until( ufn ) );
		cookies.add( HtmlDriverWait.wait10( getDriver() ).until( uea ) );
		return cookies;
	}

	public boolean isUserLoggedIn()
	{
		return getDriver().manage().getCookieNamed( USER_LAST_NAME_COOKIE ) != null;
	}

	public String getSecuredUrl()
	{
		return findHiddenSecuredUrlInput().getAttribute( "value" );
	}


	//endregion


	//region BaseCarnivalPage - Element Finder Methods Section

	protected HtmlElement findBreadcrumbBarDiv()
	{
		return getDriver().findElement( SectionBreadcrumbsBarObject.ROOT_BY );
	}

	private Optional<HtmlElement> findHstInput()
	{
		try
		{
			HtmlElement he = getDriver().findElement( By.id( "hst" ) );
			return Optional.of( he );
		}
		catch ( NoSuchElementException e )
		{
			return Optional.absent();
		}
	}

	//endregion


	//region BaseCarnivalPage - Private Functions Section

	private boolean parseUtagData()
	{
		final String SCRIPT = "if( typeof utag_data == 'undefined' ) return false; return true;";
		HtmlDriver.JavaScriptSupport js = getDriver().javascript();
		logger.debug( "determine if utag_data is defined on page ..." );
		Optional<Boolean> utag = js.getBoolean( SCRIPT );
		if( utag.isPresent() && utag.get() )
		{
			final String UTAG_SCRIPT = "var utag = {};\n"
					+ "  utag[\"site_region\"] = utag_data[\"site_region\"];\n"
					+ "  utag[\"page_id\"] = utag_data[\"page_id\"];\n"
					+ "  utag[\"page_name\"] = utag_data[\"page_name\"];\n"
					+ "  utag[\"dom.referrer\"] = utag_data[\"dom.referrer\"];\n"
					+ "  return utag;";

			Map<String,String> map = js.getMap( UTAG_SCRIPT );

			logger.debug( "reading utag_data values using javascript ..." );
			this.site_region = map.get( "site_region" );
			this.pageId = map.get( "page_id" );
			this.pageName = map.get( "page_name" );
			this.referrer = map.get( "dom.referrer" );
			return true;
		}

		return false;
	}

	private Locale parseRegion()
	{
		if( site_region.equals( "US" ) ) return Locale.US;
		if( site_region.equals( "UK" ) ) return Locale.UK;
		if( site_region.equals( "AU" ) ) return AU;
		return null;
	}

	private HtmlElement findHiddenSecuredUrlInput()
	{
		By findBy = By.id( "hSecUrl" );
		return getDriver().findElement( findBy );
	}

	//endregion
}
