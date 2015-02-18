package com.framework.site.data.xml;

import com.framework.site.pages.core.HomePage;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.Stack;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.site.data.xml
 *
 * Name   : ShipCardContentHandler 
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-02-15 
 *
 * Time   : 17:21
 *
 */

public class ShipCardContentHandler extends DefaultHandler
{

	//region ShipCardContentHandler - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( ShipCardContentHandler.class );

	private static final String COMPARE_SHIPS_NODE = "compare-ships";
	private static final String SHIP_SELECTOR_NODE = "ship-selector";
	private static final String LAST_UPDATED_NODE = "last-updated";
	private static final String UPDATER_USER_NODE = "updater-user";
	private static final String UPDATER_HOST_NODE = "updater-host";
	private static final String VALID_FOR_NODE = "valid-for";
	private static final String SELECTIONS_NODE = "selections";
	private static final String LAST_SELECTED_NODE = "last-selected";
	private static final String SHIPS_NODE = "ships";
	private static final String SHIP_NODE = "ship";


	private final String fileName;

	private Stack<XmlShipCard> stack = new Stack<XmlShipCard>();

	private List<XmlShipCard> xmlShipCards = Lists.newArrayList();

	private Properties properties = new Properties();

	private String currentValue = null;

	//endregion


	//region ShipCardContentHandler - Constructor Methods Section

	public ShipCardContentHandler( final String fileName )
	{
		this.fileName = fileName;
	}

	//endregion


	//region ShipCardContentHandler - Public Methods Section

	public Properties getProperties()
	{
		return properties;
	}

	public List<XmlShipCard> getXmlShipCards()
	{
		return xmlShipCards;
	}

	//endregion


	//region ShipCardContentHandler - DefaultHandler Implementation Methods Section

	@Override
	public void error( SAXParseException e ) throws SAXException
	{
		logger.error( ExceptionUtils.getRootCauseMessage( e ) );
		throw e;
	}

	@Override
	public void startElement( String uri, String localName, String qName, Attributes attributes ) throws SAXException
	{
		currentValue = null;
		if( SHIP_NODE.equals( qName ) )
		{
			XmlShipCard card = new XmlShipCard();
			card.setShipId( attributes.getValue( "ID" ) );
			card.setDescription( attributes.getValue( "desc" ) );
			stack.push( card );
		}
	}

	@Override
	public void endElement( String uri, String localName, String qName ) throws SAXException
	{
		switch ( qName )
		{
			case LAST_UPDATED_NODE:
				properties.put( LAST_UPDATED_NODE, StringUtils.defaultString( currentValue, StringUtils.EMPTY ) );
				break;
			case UPDATER_USER_NODE:
				properties.put( UPDATER_USER_NODE, StringUtils.defaultString( currentValue, StringUtils.EMPTY ) );
				break;
			case UPDATER_HOST_NODE:
				properties.put( UPDATER_HOST_NODE, StringUtils.defaultString( currentValue, StringUtils.EMPTY ) );
				break;
			case SHIP_NODE:
				if ( ! stack.isEmpty() )
				{
					XmlShipCard card = stack.pop();
					xmlShipCards.add( card );
				}
				break;
			case VALID_FOR_NODE:
				handleLocales( currentValue );
				break;
			case SELECTIONS_NODE:
				if ( ! stack.isEmpty() )
				{
					XmlShipCard card = stack.peek();
					int selections = NumberUtils.createInteger( currentValue );
					card.setSelections( selections );
				}
				break;
			case LAST_SELECTED_NODE:
				if ( ! stack.isEmpty() )
				{
					XmlShipCard card = stack.peek();
					card.setLastUpdated( currentValue );
				}
				break;
		}

		currentValue = null;
	}

	public void characters( char ch[], int start, int length ) throws SAXException
	{
		currentValue = new String( ch, start, length );
	}

	//endregion


	//region ShipCardContentHandler - Private Methods Section

	private void handleLocales( String locales )
	{
		boolean isValidFor = false;
		Locale current = Locale.US; //SiteSessionManager.get().getCurrentLocale();  //todo: temporary DO REMOVE!!
		String[] list = StringUtils.split( locales, "," );
		for( String locale : list )
		{
			if( locale.equals( "US" ) && current.equals( Locale.US ) )
			{
				isValidFor = true;
				break;
			}
			else if( locale.equals( "UK" ) && current.equals( Locale.UK ) )
			{
				isValidFor = true;
				break;
			}
			else if( locale.equals( "AU" ) && current.equals( HomePage.AU ) )
			{
				isValidFor = true;
				break;
			}
		}

		if( ! isValidFor )
		{
			stack.pop(); // xmlShipCard will be discarded for current locale
		}
	}

	//endregion

}
