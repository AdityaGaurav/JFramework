package com.framework.site.data.xml;

import com.framework.site.config.SiteSessionManager;
import com.framework.site.data.Ships;
import com.framework.site.pages.core.CruiseShipsPage;
import com.framework.utils.error.PreConditions;
import com.framework.utils.io.FileFinder;
import com.framework.utils.xml.XmlParseException;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.SystemUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.*;
import java.net.Inet4Address;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.site.data
 *
 * Name   : SmartRandomShipCard
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-01-12
 *
 * Time   : 04:16
 */

public class SmartRandomShipCard
{
	//region SmartRandomShipCard - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( SmartRandomShipCard.class );

	private File source = null;

	private List<XmlShipCard> xmlShipCards;

	private Set<String> uniqueIdController = Sets.newHashSet();

	private List<XmlShipCard> selectedCards = Lists.newArrayListWithCapacity( 3 );

	private Properties props;

	//endregion


	//region SmartRandomShipCard - Constructor Methods Section

	public SmartRandomShipCard( String fileName ) throws IOException
	{
		this( fileName, null );
	}

	public SmartRandomShipCard( String fileName, Ships exclude ) throws IOException
	{
		PreConditions.checkNotNullNotBlankOrEmpty( fileName, "fileName is either null, empty or blank" );

		if( null != exclude )
		{
			uniqueIdController.add( exclude.getId() );
		}

		logger.debug( "searching file < {} >", fileName );
		Path startingDir = Paths.get( SystemUtils.USER_DIR );

		FileFinder finder = new FileFinder( "compare.random.ships.xml" );
		Files.walkFileTree( startingDir, finder );
		Set<String> files = finder.getFiles();
		for( String path : files )
		{
			if( path.contains( "target" ) ) continue;
			this.source = new File( path ); break;
		}

		if( source == null )
		{
			throw new FileNotFoundException( "Could not locate < " + fileName + " > in < " + SystemUtils.USER_DIR + " >" );
		}

		logger.info( "file < {} > was located at < {} >", fileName, source.getAbsoluteFile() );
	}

	//endregion


	//region SmartRandomShipCard - Public Methods Section

	public List<XmlShipCard> getSelectedCards()
	{
		return selectedCards;
	}

	public Properties getProps()
	{
		return props;
	}

	public File getSource()
	{
		return source;
	}

	public void parse() throws ParserConfigurationException, SAXException, IOException
	{
		PreConditions.checkNotNull( source, "The source file is null" );

		logger.info( "parsing xml source file ..." );

		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser saxParser = factory.newSAXParser();
		ShipCardContentHandler contentHandler = new ShipCardContentHandler( source.getCanonicalPath() );
		saxParser.parse( source, contentHandler );
		this.xmlShipCards = contentHandler.getXmlShipCards();
		this.props = contentHandler.getProperties();

		logger.info( "End parsing. < {} > xml ships where selected for locale < {} >",
				xmlShipCards.size(), SiteSessionManager.get().getCurrentLocale().getDisplayCountry() );
	}

	@SuppressWarnings ( "ForLoopReplaceableByForEach" )
	public void selectXmlCard( CruiseShipsPage.SelectRandom first,
							   CruiseShipsPage.SelectRandom second,
							   CruiseShipsPage.SelectRandom third )
	{
		PreConditions.checkNotNull( first, "first cannot be null" );
		PreConditions.checkNotNull( second, "second cannot be null. use NONE instead" );
		PreConditions.checkNotNull( third, "third cannot be null. use NONE instead" );
		PreConditions.checkState( ! first.equals( CruiseShipsPage.SelectRandom.NONE ), "first value cannot be None." );
		List<CruiseShipsPage.SelectRandom> selections = Lists.newArrayList( first, second, third );

		for( CruiseShipsPage.SelectRandom selection : selections )
		{
			if( selection.equals( CruiseShipsPage.SelectRandom.NONE ) )
			{
				logger.info( "skipping ship selection ...." );
				continue;
			}

			if( selection.equals( CruiseShipsPage.SelectRandom.LAST_SELECTED )
					|| selection.equals( CruiseShipsPage.SelectRandom.LESS_TIMES_SELECTED )
					|| selection.equals( CruiseShipsPage.SelectRandom.MOST_TIME_SELECTED )
					|| selection.equals( CruiseShipsPage.SelectRandom.OLDER_SELECTED ) )
			{
				orderBy( selection );
				for( int i = 0; i < xmlShipCards.size(); i ++ )
				{
					XmlShipCard shipCard = xmlShipCards.get( i );
					if( ! uniqueIdController.contains( shipCard.getShipId() ) )
					{
						uniqueIdController.add( shipCard.getShipId() );
						logger.info( "selected ship < {} > using < {} > selection mode", shipCard.getShipDescription(), selection.name() );
						selectedCards.add( shipCard );
						break;
					}
				}
			}
			else if( selection.equals( CruiseShipsPage.SelectRandom.RANDOM ) )
			{
				boolean selected = false;
				while( ! selected )
				{
					int random = RandomUtils.nextInt( 0, xmlShipCards.size() - 1 );
					XmlShipCard shipCard = xmlShipCards.get( random );
					if( ! uniqueIdController.contains( shipCard.getShipId() ) )
					{
						uniqueIdController.add( shipCard.getShipId() );
						logger.info( "selected ship < {} > using < {} > selection mode", shipCard.getShipDescription(), selection.name() );
						selectedCards.add( shipCard );
						selected = true;
					}
				}
			}
		}

		try
		{
			props.setProperty( "last-updated", DateTime.now().toString( "yyyy-MMM-dd HH:mm:ss" ) );
			props.setProperty( "updater-user", SystemUtils.USER_NAME );
			props.setProperty( "updater-host", Inet4Address.getLocalHost().toString() );
			updateCardInfoAndSave();
		}
		catch ( Exception e )
		{
			throw new XmlParseException( e );
		}
	}

	//endregion


	//region SmartRandomShipCard - Private Methods Section

	private void updateCardInfoAndSave() throws Exception
	{
		OutputStream outputStream = null;
		XMLWriter writer = null;
		try
		{
			SAXReader reader = new SAXReader();
			Document document = reader.read( source );
			document.getRootElement().element( "last-updated" ).setText( props.get( "last-updated" ).toString() );
			document.getRootElement().element( "updater-user" ).setText( props.get( "updater-user" ).toString() );
			document.getRootElement().element( "updater-host" ).setText( props.get( "updater-host" ).toString() );
			for( XmlShipCard card : selectedCards )
			{
				card.increaseSelections();
				card.updateLastUpdated();
				Element ship = document.getRootElement().element( "ships" ).elementByID( card.getShipId() );
				ship.element( "selections" ).setText( String.valueOf( card.getCurrentSelection() ) );
				ship.element( "last-selected" ).setText( String.valueOf( card.getCurrentUpdate() ) );
			}

			OutputFormat format = OutputFormat.createPrettyPrint();
			outputStream = new FileOutputStream( source );
			writer = new XMLWriter( outputStream, format );
			writer.write( document );
		}
		catch ( Exception e )
		{
			throw new XmlParseException( e );
		}
		finally
		{
			if( outputStream != null )
			{
				outputStream.close();
			}
			if( writer != null )
			{
				writer.close();
			}
		}
	}

	private void orderBy( CruiseShipsPage.SelectRandom by )
	{
		if( by.equals( CruiseShipsPage.SelectRandom.LAST_SELECTED ) )
		{
			Collections.sort( xmlShipCards, new Comparator<XmlShipCard>()
			{
				@Override
				public int compare( final XmlShipCard o1, final XmlShipCard o2 )
				{
					if ( o1.getLastUpdated().equals( o2.getLastUpdated() ) ) return 0;
					if ( o1.getLastUpdated().isBefore( o2.getLastUpdated() ) ) return 1;
					return - 1;
				}
			} );
		}
		else if( by.equals( CruiseShipsPage.SelectRandom.LESS_TIMES_SELECTED ) )
		{
			Collections.sort( xmlShipCards, new Comparator<XmlShipCard>()
			{
				@Override
				public int compare( final XmlShipCard o1, final XmlShipCard o2 )
				{
					if( o1.getSelections() == o2.getSelections() ) 	return 0;
					if( o1.getSelections() > o2.getSelections() ) 	return 1;
					return -1;
				}
			} );
		}
		else if( by.equals( CruiseShipsPage.SelectRandom.MOST_TIME_SELECTED ) )
		{
			Collections.sort( xmlShipCards, new Comparator<XmlShipCard>()
			{
				@Override
				public int compare( final XmlShipCard o1, final XmlShipCard o2 )
				{
					if( o1.getSelections() == o2.getSelections() ) 	return 0;
					if( o1.getSelections() < o2.getSelections() ) 	return 1;
					return -1;
				}
			} );
		}
		else if( by.equals( CruiseShipsPage.SelectRandom.OLDER_SELECTED ) )
		{
			Collections.sort( xmlShipCards, new Comparator<XmlShipCard>()
			{
				@Override
				public int compare( final XmlShipCard o1, final XmlShipCard o2 )
				{
					if( o1.getLastUpdated().equals( o2.getLastUpdated() ) )     return 0;
					if( o1.getLastUpdated().isAfter( o2.getLastUpdated() ) ) 	return 1;
					return -1;
				}
			} );
		}
	}

	//endregion

}
