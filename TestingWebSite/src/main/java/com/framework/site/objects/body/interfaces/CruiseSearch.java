package com.framework.site.objects.body.interfaces;

import com.framework.site.data.DeparturePorts;
import com.framework.site.data.Destinations;
import com.framework.site.data.Travelers;
import org.joda.time.DateTime;

import java.util.Map;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.site.objects.body.interfaces
 *
 * Name   : CruiseSearch
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-01-06
 *
 * Time   : 20:39
 */

public interface CruiseSearch
{
	String selectSailTo( Destinations destination );

	Map<Destinations,String> getSelectedSailTo();

	String selectSailFrom( DeparturePorts destination );

	Map<DeparturePorts,String> getSelectedSailFrom();

	String selectDate( DateTime dateTime );

	DateTime getSelectedDate() ;

	String selectTravelers( Travelers travelers );

	Travelers getTravelers();

	void doSearch();

	void doAdvancedSearch();

}
