package com.framework.site.objects.body.common.cbox;

import com.framework.asserts.JAssertion;
import com.framework.driver.event.HtmlElement;
import com.framework.driver.objects.AbstractWebObject;
import com.framework.utils.datetime.TimeConstants;
import com.google.common.base.Optional;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.framework.utils.datetime.TimeConstants.FIVE_SECONDS;
import static org.hamcrest.Matchers.is;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.site.objects.body.common.cbox
 *
 * Name   : CBoxContent 
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-03-07 
 *
 * Time   : 14:00
 *
 */

public class CBoxContentObject extends AbstractWebObject
{

	//region CBoxContent - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( CBoxContentObject.class );

	static final String LOGICAL_NAME = "CBox Content";

	static final By ROOT_BY = By.id( "cboxContent" );

	// ------------------------------------------------------------------------|
	// --- WEB-OBJECTS DEFINITIONS --------------------------------------------|
	// ------------------------------------------------------------------------|

	private CBoxLoadedContentObject cBoxLoadedContentObject;

	// ------------------------------------------------------------------------|
	// --- WEB-OBJECTS CACHING ------------------------------------------------|
	// ------------------------------------------------------------------------|

	private HtmlElement cbox_loaded_content, cbox_close;

	//endregion


	//region CBoxContent - Constructor Methods Section

	CBoxContentObject( final HtmlElement rootElement )
	{
		super( rootElement, LOGICAL_NAME );
		initWebObject();
	}

	//endregion


	//region CBoxContent - Initialization and Validation Methods Section

	@Override
	protected void initWebObject()
	{
		logger.info( "validating static elements for web object id: <{}>, name:<{}>...",
				getQualifier(), getLogicalName() );

		final String REASON = "assert that element \"%s\" exits";
		JAssertion assertion = getRoot().createAssertion();

		Optional<HtmlElement> e = getRoot().childExists( By.id( "cboxLoadedContent" ), FIVE_SECONDS );
		assertion.assertThat( String.format( REASON, "#cboxLoadedContent" ), e.isPresent(), is( true ) );
		cbox_loaded_content = e.get();

		e = getRoot().childExists( By.id( "cboxClose" ), FIVE_SECONDS );
		assertion.assertThat( String.format( REASON, "#cboxClose" ), e.isPresent(), is( true ) );
		cbox_close = e.get();
	}

	//endregion


	//region CBoxContent - Service Methods Section

	private HtmlElement getRoot()
	{
		return getBaseRootElement( ROOT_BY );
	}

	public CBoxLoadedContentObject cBoxLoadedContent()
	{
		if( null == cBoxLoadedContentObject )
		{
			cBoxLoadedContentObject = new CBoxLoadedContentObject( cbox_loaded_content );
		}

		return cBoxLoadedContentObject;
	}

	//endregion


	//region CBoxContent - Implementations Methods Section

	public void close()
	{
		logger.info( "Closing cbox ...." );
		cbox_close.click();
		HtmlElement wrapper = getDriver().findElement( CBoxWrapperObject.ROOT_BY );
		wrapper.waitToBeDisplayed( false, TimeConstants.THREE_SECONDS );
	}

	//endregion


	//region CBoxContent - Element Finder Section

	//endregion

}
