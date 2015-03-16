package com.framework.site.objects.footer;

import com.framework.asserts.JAssertion;
import com.framework.driver.event.HtmlElement;
import com.framework.driver.objects.AbstractWebObject;
import com.framework.utils.matchers.JMatchers;
import com.google.common.base.Optional;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static com.framework.utils.datetime.TimeConstants.FIVE_SECONDS;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.site.objects.footer
 *
 * Name   : ZeroFooterObject
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-01-06
 *
 * Time   : 16:56
 */

class ZeroFooterObject extends AbstractWebObject
{

	//region ZeroFooterObject - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( ZeroFooterObject.class );

	static final By ROOT_BY = By.className( "zero-footer" );

	static final String LOGICAL_NAME = "Zero-Footer";

	//endregion


	//region ZeroFooterObject - Constructor Methods Section

	ZeroFooterObject( final HtmlElement rootElement )
	{
		super( rootElement, LOGICAL_NAME );
		initWebObject();
	}

	//endregion


	//region ZeroFooterObject - Initialization and Validation Methods Section

	@Override
	protected void initWebObject()
	{
		logger.debug( "validating static elements for web object id: <{}>, name:<{}>...",
				getQualifier(), getLogicalName() );

		final String REASON = "assert that all elements \"%s\" exits";
		JAssertion assertion = getRoot().createAssertion();

		Optional<List<HtmlElement>> e = getRoot().allChildrenExists( By.tagName( "li" ), FIVE_SECONDS );
		assertion.assertThat( String.format( REASON, "li" ), e.isPresent(), JMatchers.is( true ) );
	}

	//endregion


	//region ZeroFooterObject - Service Methods Section

	private HtmlElement getRoot()
	{
		return getBaseRootElement( ROOT_BY );
	}

	//endregion


	//region ZeroFooterObject - Business Methods Section

	public boolean isDisplayed()
	{
		return getRoot().isDisplayed();
	}

	//endregion


	//region ZeroFooterObject - Element Finder Methods Section

	//endregion

}