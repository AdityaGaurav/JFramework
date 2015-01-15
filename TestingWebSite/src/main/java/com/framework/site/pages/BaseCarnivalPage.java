package com.framework.site.pages;

import com.framework.driver.event.EventWebDriver;
import com.framework.driver.event.JavaScript;
import com.framework.driver.exceptions.PageObjectException;
import com.framework.driver.objects.AbstractPageObject;
import com.framework.site.objects.body.interfaces.BreadcrumbsBar;
import com.framework.site.objects.footer.FooterObject;
import com.framework.site.objects.footer.interfaces.Footer;
import com.framework.site.objects.header.SectionHeaderObject;
import com.framework.site.objects.header.interfaces.Header;
import com.google.common.base.MoreObjects;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Locale;


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

	public static final String DATA_DOM_REFERRER_JS = "return utag_data.dom.referrer;";

	public static final String DATA_PAGE_ID_JS = "return utag_data.page_id;";

	public static final String DATA_PAGE_NAME_JS = "return utag_data.page_name;";

	public static final String DATA_REGION_NAME_JS = "return utag_data.site_region;";

	private String siteRegion, pageId, pageName;

	// ------------------------------------------------------------------------|
	// --- WEB-OBJECTS DEFINITIONS --------------------------------------------|
	// ------------------------------------------------------------------------|

	private Header header = null;

	private Footer footer = null;

	//endregion


	//region BaseCarnivalPage - Constructor Methods Section

	protected BaseCarnivalPage( final String logicalName, final WebDriver driver )
	{
		super( logicalName, driver );

		try
		{
			JavaScript js = ( ( EventWebDriver ) driver ).getJavaScript();

			/* reading app values using javascript */

			logger.debug( "reading app values using javascript ..." );
			this.siteRegion = js.getString( DATA_REGION_NAME_JS );
			this.pageId = js.getString( DATA_PAGE_ID_JS );
			this.pageName = js.getString( DATA_PAGE_NAME_JS );

			initElements();

			logger.info( "new CarnivalPage was created -> {}", toString() );
		}
		catch ( Throwable e )
		{
			logger.error( "throwing a new PageObjectException on {}#constructor.", getClass().getSimpleName() );
			PageObjectException poe = new PageObjectException( driver, e.getMessage(), e );
			poe.addInfo( "causing flow", "trying to create a new CarnivalPage -> " + logicalName );
			throw poe;
		}

	}

	//endregion


	//region BaseCarnivalPage - Initialization and Validation Methods Section


	//endregion


	//region BaseCarnivalPage - Service Methods Section

	protected Locale getApplicationLocale()
	{
		if( siteRegion.equals( "US" ) ) return Locale.US;
		if( siteRegion.equals( "UK" ) ) return Locale.UK;
		if( siteRegion.equals( "AU" ) ) return AU;
		return null;
	}

	protected String getSiteRegion()
	{
		return siteRegion;
	}

	protected String getReferrer()
	{
		return pageDriver.getJavaScript().getString( DATA_DOM_REFERRER_JS );
	}

	protected String pageId()
	{
		return pageId;
	}

	protected String pageName()
	{
		return pageName;
	}

	public Header header()
	{
		if ( null == this.header )
		{
			this.header = new SectionHeaderObject( pageDriver, pageDriver.findElement( Header.ROOT_BY ) );
		}
		return header;
	}

	public Footer footer()
	{
		if ( null == this.footer )
		{
			this.footer = new FooterObject( pageDriver, pageDriver.findElement( Footer.ROOT_BY ) );
		}
		return footer;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper( this )
				.addValue( super.toString() )
				.add( "pageId", pageId )
				.add( "pageName", pageName )
				.add( "siteRegion", siteRegion )
				.omitNullValues()
				.toString();
	}

	//endregion

	//region BaseCarnivalPage - Element Finder Methods Section

	protected WebElement findBreadcrumbBarDiv()
	{
		return pageDriver.findElement( BreadcrumbsBar.ROOT_BY );
	}

	//endregion
}
