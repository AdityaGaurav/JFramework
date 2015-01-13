package com.framework.site.data;

import org.joda.time.DateTime;

import java.util.Comparator;


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

public class SmartRandomShipCard implements Comparable<SmartRandomShipCard>
{

	//region SmartRandomShipCard - Variables Declaration and Initialization Section.

	private final String id;

	private final DateTime lastSelected;

	private final int selectionCount;

	private String description;

	//endregion


	//region SmartRandomShipCard - Constructor Methods Section

	public SmartRandomShipCard( final String id, final DateTime lastSelected, final int selectionCount )
	{
		this.id = id;
		this.lastSelected = lastSelected;
		this.selectionCount = selectionCount;
	}

	//endregion


	//region SmartRandomShipCard - Public Methods Section

	public String getId()
	{
		return id;
	}

	public DateTime getLastSelected()
	{
		return lastSelected;
	}

	public int getSelectionCount()
	{
		return selectionCount;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription( final String description )
	{
		this.description = description;
	}

	@Override
	public int compareTo( final SmartRandomShipCard o )
	{
		return Comparators.SELECTION_COUNT.compare( this, o );
	}

	public static class Comparators
	{

		public static Comparator<SmartRandomShipCard> LAST_SELECTED = new Comparator<SmartRandomShipCard>()
		{
			@Override
			public int compare( SmartRandomShipCard o1, SmartRandomShipCard o2 )
			{
				return o1.getLastSelected().compareTo( o2.getLastSelected() );
			}
		};

		public static Comparator<SmartRandomShipCard> SELECTION_COUNT = new Comparator<SmartRandomShipCard>()
		{
			@Override
			public int compare( SmartRandomShipCard o1, SmartRandomShipCard o2 )
			{
				return o1.getSelectionCount() - o2.getSelectionCount();
			}
		};

		public static Comparator<SmartRandomShipCard> BOTH = new Comparator<SmartRandomShipCard>()
		{
			@Override
			public int compare( SmartRandomShipCard o1, SmartRandomShipCard o2 )
			{
				int i = o1.getSelectionCount() - o2.getSelectionCount();
				if( i == 0 )
				{
					i = o1.getLastSelected().compareTo( o2.getLastSelected() );
				}

				return i;
			}
		};
	}

	//endregion

}
