package com.framework.site.data.xml;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.site.data.xml
 *
 * Name   : XmlShipCard 
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-02-15 
 *
 * Time   : 16:49
 *
 */

public class XmlShipCard
{

	//region XmlShipCard - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( XmlShipCard.class );

	private static final DateTime NULLABLE_DATE = DateTime.now();

	private static final DateTimeFormatter dtf = DateTimeFormat.forPattern( "yyyy-MMM-dd HH:mm:ss" );

	private String shipId, description;

	private int selections = 0, currentSelection = 0;

	private DateTime lastUpdated, currentUpdate;

	//endregion


	//region XmlShipCard - Constructor Methods Section

	protected XmlShipCard()
	{
		//
	}


	//endregion


	//region XmlShipCard - Public Methods Section

	public String getShipId()
	{
		return shipId;
	}

	public void setShipId( final String shipId )
	{
		this.shipId = shipId;
	}

	public String getShipDescription()
	{
		return description;
	}

	public void setDescription( final String description )
	{
		this.description = description;
	}

	public int getSelections()
	{
		return selections;
	}

	public void setSelections( final int selections )
	{
		this.selections = selections;
	}

	public void increaseSelections()
	{
		this.currentSelection = selections + 1;
	}

	public void updateLastUpdated()
	{
		this.currentUpdate = DateTime.now();
	}

	public int getCurrentSelection()
	{
		return currentSelection;
	}

	public String getCurrentUpdate()
	{
		return currentUpdate.toString( "yyyy-MMM-dd HH:mm:ss" );
	}

	public DateTime getLastUpdated()
	{
		return lastUpdated;
	}

	public void setLastUpdated( final String lastUpdated )
	{
		if( null == lastUpdated || StringUtils.isEmpty( lastUpdated ) )
		{
			this.lastUpdated = NULLABLE_DATE;
		}
		else
		{
			this.lastUpdated = dtf.parseDateTime( lastUpdated );
		}
	}

	@Override
	public String toString()
	{
		return new ToStringBuilder( this )
				.append( "shipId", shipId )
				.append( "description", description )
				.append( "selections", selections )
				.append( "currentSelection", currentSelection )
				.append( "lastUpdated", lastUpdated )
				.toString();
	}

	@SuppressWarnings ( "SimplifiableIfStatement" )
	@Override
	public boolean equals( final Object o )
	{
		if ( this == o )
		{
			return true;
		}
		if ( ! ( o instanceof XmlShipCard ) )
		{
			return false;
		}

		final XmlShipCard that = ( XmlShipCard ) o;

		if ( selections != that.selections )
		{
			return false;
		}
		if ( ! description.equals( that.description ) )
		{
			return false;
		}
		if ( lastUpdated != null ? ! lastUpdated.equals( that.lastUpdated ) : that.lastUpdated != null )
		{
			return false;
		}
		return shipId.equals( that.shipId );

	}

	@Override
	public int hashCode()
	{
		int result = shipId.hashCode();
		result = 31 * result + description.hashCode();
		result = 31 * result + selections;
		result = 31 * result + ( lastUpdated != null ? lastUpdated.hashCode() : 0 );
		return result;
	}

	//endregion

}
