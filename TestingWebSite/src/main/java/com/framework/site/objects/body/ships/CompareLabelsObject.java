package com.framework.site.objects.body.ships;

import com.framework.driver.event.HtmlElement;
import com.framework.driver.objects.AbstractWebObject;
import com.framework.site.data.Ships;
import com.google.common.collect.Lists;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.site.objects.body.ships
 *
 * Name   : CompareLabelsObject 
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-02-16 
 *
 * Time   : 19:13
 *
 */

class CompareLabelsObject extends AbstractWebObject
{

	//region CompareLabelsObject - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( CompareLabelsObject.class );

	static final String LOGICAL_NAME = "Compare Labels";

	static final By ROOT_BY = By.id( "compare-labels" );

	// ------------------------------------------------------------------------|
	// --- WEB-OBJECTS CACHING ------------------------------------------------|
	// ------------------------------------------------------------------------|

	private List<Ships> ships = Lists.newArrayListWithCapacity( 3 );

	//endregion


	//region CompareLabelsObject - Constructor Methods Section

	CompareLabelsObject( final HtmlElement rootElement )
	{
		super( rootElement, LOGICAL_NAME );
		initWebObject();
	}

	//endregion


	//region CompareLabelsObject -  Initialization and Validation Methods Section

	@Override
	protected void initWebObject()
	{
		logger.debug( "validating static elements for web object id: <{}>, name:<{}>...",
				getQualifier(), getLogicalName() );
	}

	//endregion


	//region CompareLabelsObject - Service Methods Section

	private HtmlElement getRoot()
	{
		return getBaseRootElement( ROOT_BY );
	}


	//endregion


	//region CompareLabelsObject - Interface Implementation Section

	public boolean isVisible()
	{
		return getRoot().getAttribute( "class" ).endsWith( "visible" );
	}

	public List<Ships> getShipSections()
	{
		if( ships.size() == 0 )
		{
			List<HtmlElement> list = getRoot().findElements( By.tagName( "h2" ) );
			for( HtmlElement he : list )
			{
				String text = he.getAttribute( "textContent" );
				if( text.startsWith( "Carnival" ) )
				{
					Ships ship = Ships.valueByName( text );
					ships.add( ship );
				}
			}
		}

		return ships;

	}

	//endregion


	//region CompareLabelsObject - Inner Classes Implementation Section

	//endregion

}
