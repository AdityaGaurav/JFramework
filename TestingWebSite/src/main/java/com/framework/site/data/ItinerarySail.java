package com.framework.site.data;

import com.framework.driver.event.HtmlElement;
import com.framework.driver.extensions.jquery.By;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Map;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.site.data
 *
 * Name   : ItinerarySail 
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-03-13 
 *
 * Time   : 10:52
 *
 */

public class ItinerarySail implements Enumerators
{

	//region ItinerarySail - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( ItinerarySail.class );

	private final String dataIdx;

	private boolean dataBpx = false;

	private DateTime startDate;

	private final String weekDays;

	private Map<RoomType,BigDecimal> rates = Maps.newHashMap();

	private final HtmlElement bookNow;

	//endregion


	//region ItinerarySail - Constructor Methods Section

	public ItinerarySail( final HtmlElement root )
	{
	 	this.dataIdx = root.getAttribute( "data-idx" );
		String bpx = root.getAttribute( "data-bpx" );
		if( "true".equals( bpx ) ) dataBpx = true;

		// retrieve the price of Ocean View
		HtmlElement he = root.findElement( By.cssSelector( "li:nth-child(3)" ) );
		String innerHtml = he.getWrappedElement().getAttribute( "innerHtml" );
		if( ! innerHtml.contains( "empty" ) )
		{
			String text = he.getText();
			text = StringUtils.remove( "$", text );
			rates.put( RoomType.OCEAN_VIEW, new BigDecimal( text ) );
		}
		he = root.findElement( By.cssSelector( "li:nth-child(4)" ) );
		innerHtml = he.getWrappedElement().getAttribute( "innerHtml" );
		if( ! innerHtml.contains( "empty" ) )
		{
			String text = he.getText();
			text = StringUtils.remove( "$", text );
			rates.put( RoomType.BALCONY, new BigDecimal( text ) );
		}
		he = root.findElement( By.cssSelector( "li:nth-child(5)" ) );
		innerHtml = he.getWrappedElement().getAttribute( "innerHtml" );
		if( ! innerHtml.contains( "empty" ) )
		{
			String text = he.getText();
			text = StringUtils.remove( "$", text );
			rates.put( RoomType.SUITE, new BigDecimal( text ) );
		}
		he = root.findElement( By.cssSelector( "li.interior-rate" ) );
		String text = he.getText();
		text = StringUtils.remove( "$", text );
		rates.put( RoomType.INTERIOR, new BigDecimal( text ) );
		he = root.findElement( By.cssSelector( "li.date-col > h5" ) );
		DateTimeFormatter formatter = DateTimeFormat.forPattern( "MMM dd, yyyy" );
		this.startDate = formatter.parseDateTime( he.getText() );
		he = root.findElement( By.cssSelector( "li.date-col > span" ) );
		this.weekDays = he.getText();
	    this.bookNow = root.findElement( By.cssSelector( "li.action-col > a" ) );
	}


	//endregion


	//region ItinerarySail - Public Methods Section

	public String getDataIdx()
	{
		return dataIdx;
	}

	public boolean isDataBpx()
	{
		return dataBpx;
	}

	public DateTime getStartDate()
	{
		return startDate;
	}

	public String getWeekDays()
	{
		return weekDays;
	}

	public Map<RoomType, BigDecimal> getRates()
	{
		return rates;
	}

	public void bookNow()
	{
		bookNow.click();
	}

	//endregion
}
