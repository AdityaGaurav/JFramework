package com.framework.site.objects.body.common;

import com.framework.asserts.JAssertion;
import com.framework.driver.event.HtmlElement;
import com.framework.driver.objects.AbstractWebObject;
import com.framework.site.objects.body.interfaces.BreadcrumbsBar;
import com.google.common.base.Optional;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static com.framework.utils.datetime.TimeConstants.FIVE_SECONDS;
import static com.framework.utils.datetime.TimeConstants.TWO_SECONDS;
import static org.hamcrest.Matchers.is;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.site.objects.body.common
 *
 * Name   : BreadcrumbsShareObject
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-01-12
 *
 * Time   : 12:17
 */

public class BreadcrumbsShareObject extends AbstractWebObject implements BreadcrumbsBar.Share
{

	//region BreadcrumbsShareObject - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( BreadcrumbsShareObject.class );

	//endregion


	//region BreadcrumbsShareObject - Constructor Methods Section

	public BreadcrumbsShareObject( final HtmlElement rootElement )
	{
		super( rootElement, BreadcrumbsBar.Share.LOGICAL_NAME );
		initWebObject();
	}

	//endregion


	//region BreadcrumbsShareObject - Initialization and Validation Methods Section

	@Override
	protected void initWebObject()
	{
		logger.debug( "validating static elements for web object id: <{}>, name:<{}>...",
				getQualifier(), getLogicalName() );

		final String REASON = "assert that element \"%s\" exits";
		JAssertion assertion = getRoot().createAssertion();

		Optional<HtmlElement> e = getRoot().childExists( By.id( "HeaderFBLike" ), FIVE_SECONDS );
		assertion.assertThat( String.format( REASON, "#HeaderFBLike" ), e.isPresent(), is( true ) );

		e = getRoot().childExists( By.className( "options" ), TWO_SECONDS );
		assertion.assertThat( String.format( REASON, "#options" ), e.isPresent(), is( true ) );
	}


	//endregion


	//region BreadcrumbsShareObject - Service Methods Section

	private HtmlElement getRoot()
	{
		return getBaseRootElement( BreadcrumbsBar.Share.ROOT_BY );
	}

	//endregion


	//region BreadcrumbsShareObject - Business Methods Section

	//endregion


	//region BreadcrumbsShareObject - Element Finder Methods Section

	private List<HtmlElement> getTopChickletsLis()
	{
		By findBy = By.id( "top_chicklets" );
		return getRoot().findElement( findBy ).findElements( By.tagName( "li" ) );
	}

	private HtmlElement getChicletsSearchDiv()
	{
		By findBy = By.id( "chicklet_search" );
		return getRoot().findElement( findBy );
	}

	private HtmlElement getAllChickletsDiv()
	{
		By findBy = By.id( "all_chicklets" );
		return getRoot().findElement( findBy );
	}

	//endregion

}
