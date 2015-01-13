package com.framework.site.objects.body.interfaces;

import com.framework.site.data.Ships;
import com.framework.site.pages.core.cruiseships.CompareCruiseShipsPage;
import org.openqa.selenium.By;

import java.util.List;
import java.util.Map;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.site.objects.body.interfaces
 *
 * Name   : CompareBanner
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-01-10
 *
 * Time   : 02:34
 */

public interface CompareShipBanner
{
	static final String LOGICAL_NAME = "Ship Compare Banner";

	static final By ROOT_BY = By.id( "compare-banner" );

	/**
	 * Click on the a.blue-cta button.
	 *
	 * @return n new instance of {@code CompareCruiseShipsPage}
	 *
	 * @throws com.framework.driver.exceptions.PreConditionException if the banner is not visible
	 */
	CompareCruiseShipsPage doCompareShips();

	/**
	 * Get a ship by his index from left to right.
	 *
	 * @param index the index of the ship to retrieve
	 *
	 * @throws com.framework.driver.exceptions.PreConditionException if the banner is not visible
	 *  	   or if the index is out of bound.
	 */
	Ships getShip( final int index );

	/**
	 * Remove a ship by his index, and returning the ship that was removed.
	 *
	 * @param index the index of the ship to remove
	 *
	 * @throws com.framework.driver.exceptions.ApplicationException if the banner is not visible or
	 *  	   if the index is out of bound.
	 */
	Ships remove( int index );

	/**
	 * Remove a ship by his index, and returning the ship that was removed.
	 *
	 * @param ship the ship to remove
	 *
	 * @throws com.framework.driver.exceptions.PreConditionException if the index is out bound
	 * 		   or the banner is not visible
	 * @throws com.framework.site.exceptions.NoSuchShipException
	 *         if the ship is not listed inside the comparison banner.
	 */
	void remove( Ships ship );

	/**
	 * @return a list of comparing ships from left to right.
	 *
	 * @throws com.framework.driver.exceptions.PreConditionException if the banner is not visible.
	 */
	List<Ships> getShips();

	/**
	 * @return a list of comparing ships names
	 *
	 * @throws com.framework.driver.exceptions.PreConditionException if the banner is not visible.
	 */
	List<String> getShipNames();

	/**
	 * returns a compare ship information by argument {@code ship}
	 * <p>
	 *     The map will contains the following values.
	 *     <ul>
	 *      	<li>a[data-id] - key: id</li>
	 *      	<li>h4.text - key: name</li>
	 *      	<li>img[src] - key src.</li>
	 *      	<li>img[alt] - key: alt</li>
	 *     </ul>
	 * </p>
	 * @param ship the {@linkplain com.framework.site.data.Ships} item to be queried
	 *
	 * @return a {@code Map} with the described above keys.
	 *
	 * @throws com.framework.driver.exceptions.PreConditionException if the index is out bound
	 * 		   or the banner is not visible
	 * @throws com.framework.site.exceptions.NoSuchShipException
	 *         if the ship is not listed inside the comparison banner.
	 */
	Map<String,String> getCompareShipInfo( Ships ship );

	/**
	 * @return {@code true} if the ship compare banner is visible, otherwise {@code false}.
	 */
	boolean isVisible();

}
