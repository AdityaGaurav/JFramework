package com.framework.site.objects.body.common.cbox;

import com.framework.asserts.JAssertion;
import com.framework.driver.event.HtmlElement;
import com.framework.driver.objects.AbstractWebObject;
import com.framework.utils.datetime.TimeConstants;
import com.google.common.base.Optional;
import org.apache.commons.lang3.BooleanUtils;
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
 * Name   : CboxWrapper 
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-03-07 
 *
 * Time   : 13:46
 *
 */

public class CBoxWrapperObject extends AbstractWebObject
{

	//region CBoxWrapper - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( CBoxWrapperObject.class );

	static final String LOGICAL_NAME = "CBox Wrapper";

	public static final By ROOT_BY = By.id( "cboxWrapper" );

	// ------------------------------------------------------------------------|
	// --- WEB-OBJECTS DEFINITIONS --------------------------------------------|
	// ------------------------------------------------------------------------|

	private CBoxContentObject cBoxContent;

	// ------------------------------------------------------------------------|
	// --- WEB-OBJECTS CACHING ------------------------------------------------|
	// ------------------------------------------------------------------------|

	private HtmlElement cbox_content, cbox_title;

	//endregion


	//region CBoxWrapper - Constructor Methods Section

	public CBoxWrapperObject( final HtmlElement rootElement )
	{
		super( rootElement, LOGICAL_NAME );
		initWebObject();
	}

	//endregion


	//region CBoxWrapper - Initialization and Validation Methods Section

	@Override
	protected void initWebObject()
	{
		logger.info( "validating static elements for web object id: <{}>, name:<{}>...",
				getQualifier(), getLogicalName() );

		final String REASON = "assert that element \"%s\" exits";
		JAssertion assertion = getRoot().createAssertion();

		Optional<HtmlElement> e = getRoot().childExists( By.id( "cboxContent" ), FIVE_SECONDS );
		assertion.assertThat( String.format( REASON, "#cboxContent" ), e.isPresent(), is( true ) );
		cbox_content = e.get();

		e = getRoot().childExists( By.id( "cboxTitle" ), FIVE_SECONDS );
		assertion.assertThat( String.format( REASON, "#cboxTitle" ), e.isPresent(), is( true ) );
		cbox_title = e.get();
	}

	//endregion


	//region CBoxWrapper - Service Methods Section

	private HtmlElement getRoot()
	{
		return getBaseRootElement( ROOT_BY );
	}

	public CBoxContentObject cBoxContent()
	{
		if( null == cBoxContent )
		{
			cBoxContent = new CBoxContentObject( cbox_content );
		}
		return cBoxContent;
	}

	//endregion


	//region CBoxWrapper - Implementation Function Section

	public boolean isVisible()
	{
		boolean displayed = getRoot().isDisplayed();
		logger.info( "Determine if cbox is currently displayed: < {} >", BooleanUtils.toStringYesNo( displayed ) );
		return displayed;
	}

	public void clickOnColorBoxArea()
	{
		getDriver().findElement( By.id( "cboxOverlay" ) ).getWrappedElement().click();
		getRoot().waitToBeDisplayed( false, TimeConstants.FIVE_SECONDS );
	}

	public void close()
	{
		cBoxContent().close();
		getRoot().waitToBeDisplayed( false, TimeConstants.FIVE_SECONDS );
		cBoxContent().cBoxLoadedContent().roomLightbox().reset();
	}

	//endregion


	//region CBoxWrapper - Element Finder Section

	//endregion

}
