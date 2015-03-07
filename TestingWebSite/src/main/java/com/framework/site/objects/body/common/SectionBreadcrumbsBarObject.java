package com.framework.site.objects.body.common;

import com.framework.asserts.JAssertion;
import com.framework.driver.event.HtmlElement;
import com.framework.driver.objects.AbstractWebObject;
import com.google.common.base.Optional;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.framework.utils.datetime.TimeConstants.FIVE_SECONDS;
import static com.framework.utils.datetime.TimeConstants.TWO_SECONDS;
import static org.hamcrest.Matchers.is;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.site.objects.body.common
 *
 * Name   : BreadcrumbsObject
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-01-12
 *
 * Time   : 11:06
 */

public class SectionBreadcrumbsBarObject extends AbstractWebObject
{

	//region SectionBreadcrumbsBarObject - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( SectionBreadcrumbsBarObject.class );

	static final String LOGICAL_NAME = "Cruise Deals Adjustment";

	public static final By ROOT_BY = By.className( "breadcrumb-bar" );

	// ------------------------------------------------------------------------|
	// --- WEB-OBJECTS DEFINITIONS --------------------------------------------|
	// ------------------------------------------------------------------------|

	private BreadcrumbsObject breadcrumbs;

	private BreadcrumbsShareObject share;

	// ------------------------------------------------------------------------|
	// --- WEB-OBJECTS CACHING ------------------------------------------------|
	// ------------------------------------------------------------------------|

	private HtmlElement breadCrumbBar;

	//endregion


	//region SectionBreadcrumbsBarObject - Constructor Methods Section

	public SectionBreadcrumbsBarObject( final HtmlElement rootElement )
	{
		super( rootElement, SectionBreadcrumbsBarObject.LOGICAL_NAME );
		initWebObject();
	}

	//endregion


	//region SectionBreadcrumbsBarObject - Initialization and Validation Methods Section

	@Override
	protected void initWebObject()
	{
		logger.debug( "validating static elements for web object id: <{}>, name:<{}>...",
				getQualifier(), getLogicalName() );

		final String REASON = "assert that element \"%s\" exits";
		JAssertion assertion = getRoot().createAssertion();

		Optional<HtmlElement> e = getRoot().childExists( By.className( "breadcrumbs" ), FIVE_SECONDS );
		assertion.assertThat( String.format( REASON, ".breadcrumbs" ), e.isPresent(), is( true ) );

		e = getRoot().childExists( By.className( "share" ), TWO_SECONDS );
		assertion.assertThat( String.format( REASON, ".share" ), e.isPresent(), is( true ) );
	}

	//endregion


	//region SectionBreadcrumbsBarObject - Service Methods Section

	private HtmlElement getRoot()
	{
		return getBaseRootElement( SectionBreadcrumbsBarObject.ROOT_BY );
	}

	public BreadcrumbsShareObject share()
	{
		if ( null == this.share )
		{
			this.share = new BreadcrumbsShareObject( findShareUl() );
		}
		return share;
	}

	public BreadcrumbsObject breadcrumbs()
	{
		if ( null == this.breadcrumbs )
		{
			this.breadcrumbs = new BreadcrumbsObject( findBreadcrumbUl() );
		}
		return breadcrumbs;
	}

	public HtmlElement getContainer()
	{
		if( null == breadCrumbBar )
		{
			breadCrumbBar = getDriver().findElement( ROOT_BY );
		}

		return breadCrumbBar;
	}

	//endregion


	//region SectionBreadcrumbsBarObject - Business Methods Section


	//endregion


	//region SectionBreadcrumbsBarObject - Element Finder Methods Section

	private HtmlElement findHiddenFunActivitiesInput()
	{
		By findBy = By.id( "FunActivitiesBaseUrlFragment" );
		return getRoot().findElement( findBy );
	}

	private HtmlElement findBreadcrumbUl()
	{
		return getRoot().findElement( BreadcrumbsObject.ROOT_BY  );
	}

	private HtmlElement findShareUl()
	{
		return getRoot().findElement( BreadcrumbsShareObject.ROOT_BY );
	}

	//endregion

}
