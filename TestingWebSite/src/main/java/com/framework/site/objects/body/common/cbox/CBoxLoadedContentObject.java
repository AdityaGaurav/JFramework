package com.framework.site.objects.body.common.cbox;

import com.framework.asserts.JAssertion;
import com.framework.driver.event.HtmlElement;
import com.framework.driver.objects.AbstractWebObject;
import com.framework.site.data.Enumerators;
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
 * Name   : CBoxLoadedContent 
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-03-07 
 *
 * Time   : 14:10
 *
 */

public class CBoxLoadedContentObject extends AbstractWebObject implements Enumerators
{

	//region CBoxLoadedContent - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( CBoxLoadedContentObject.class );

	static final String LOGICAL_NAME = "CBox Loaded Content";

	static final By ROOT_BY = By.id( "cboxLoadedContent" );

	private static CboxLoadedContentType loadedContentType;

	// ------------------------------------------------------------------------|
	// --- WEB-OBJECTS DEFINITIONS --------------------------------------------|
	// ------------------------------------------------------------------------|

	private RoomLightboxObject roomLightbox;

	// ------------------------------------------------------------------------|
	// --- WEB-OBJECTS CACHING ------------------------------------------------|
	// ------------------------------------------------------------------------|

	private HtmlElement room_ligthbox;

	//endregion


	//region CBoxLoadedContent - Constructor Methods Section

	CBoxLoadedContentObject( final HtmlElement rootElement )
	{
		super( rootElement, LOGICAL_NAME );
		initWebObject();
	}


	//endregion


	//region CBoxLoadedContent - Initialization and Validation Methods Section

	@Override
	protected void initWebObject()
	{
		logger.info( "validating static elements for web object id: <{}>, name:<{}>...",
				getQualifier(), getLogicalName() );

		final String REASON = "assert that element \"%s\" exits";
		JAssertion assertion = getRoot().createAssertion();

		if( loadedContentType.equals( CboxLoadedContentType.STATE_ROOMS ) )
		{
			Optional<HtmlElement> e = getRoot().childExists( By.id( "room_ligthbox" ), FIVE_SECONDS );
			assertion.assertThat( String.format( REASON, "#room_ligthbox" ), e.isPresent(), is( true ) );
			room_ligthbox = e.get();
		}
	}

	//endregion


	//region CBoxLoadedContent - Service Methods Section

	private HtmlElement getRoot()
	{
		return getBaseRootElement( ROOT_BY );
	}

	public static CboxLoadedContentType getLoadedContentType()
	{
		return loadedContentType;
	}

	public static void forContentType( final CboxLoadedContentType loadedContentType )
	{
		CBoxLoadedContentObject.loadedContentType = loadedContentType;
	}

	public RoomLightboxObject roomLightbox()
	{
		if( null == roomLightbox )
		{
			roomLightbox = new RoomLightboxObject( room_ligthbox );
		}

		return roomLightbox;
	}

	//endregion


	//region CBoxLoadedContent - Implementations Methods Section

	//endregion


	//region CBoxLoadedContent - Element Finder Section

	//endregion

}
