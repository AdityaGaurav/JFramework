package com.framework.site.objects.header;

import com.framework.asserts.JAssertion;
import com.framework.driver.event.HtmlElement;
import com.framework.driver.objects.AbstractWebObject;
import com.framework.site.objects.header.interfaces.Header;
import com.framework.utils.matchers.JMatchers;
import com.google.common.base.Optional;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.framework.utils.datetime.TimeConstants.FIVE_SECONDS;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.site.objects.header
 *
 * Name   : HeaderObject
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-01-05
 *
 * Time   : 23:11
 */

class MessageBarObject extends AbstractWebObject implements Header.MessageBar
{

	//region HeaderObject - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( MessageBarObject.class );

	// ------------------------------------------------------------------------|
	// --- WEB-OBJECTS DEFINITIONS --------------------------------------------|
	// ------------------------------------------------------------------------|

	//endregion


	//region HeaderObject - Constructor Methods Section

	MessageBarObject( final HtmlElement rootElement )
	{
		super( rootElement, LOGICAL_NAME );
		initWebObject();
	}

	//endregion


	//region HeaderObject - Initialization and Validation Methods Section

	@Override
	protected void initWebObject()
	{
		if( getRoot().isDisplayed() )
		{
			logger.debug( "validating static elements for web object id: <{}>, name:<{}>...",
					getQualifier(), getLogicalName() );

			final String REASON = "assert that element \"%s\" exits";
			JAssertion assertion = getRoot().createAssertion();

			Optional<HtmlElement> e = getRoot().childExists( By.className( "cookiemandate" ), FIVE_SECONDS );
			assertion.assertThat( String.format( REASON, "div.cookiemandate" ), e.isPresent(), JMatchers.is( true ) );
		}
	}

	//endregion


	//region HeaderObject - Service Methods Section

	private HtmlElement getRoot()
	{
		return getBaseRootElement( Header.MessageBar.ROOT_BY );
	}

	//endregion


	//region HeaderObject - Business Methods Section

	@Override
	public boolean isDisplayed()
	{
		return getRoot().isDisplayed();
	}

	//endregion


	//region HeaderObject - Element Finder Methods Section

	//endregion

}
