package com.framework.site.objects.body.interfaces;

import com.framework.driver.event.HtmlElement;
import org.openqa.selenium.By;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.site.objects.body.interfaces
 *
 * Name   : ShipSortBar
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-01-10
 *
 * Time   : 03:18
 */

public interface ShipSortBar
{

	static final String LOGICAL_NAME = "Ships Sort Bar";

	static final By ROOT_BY = By.cssSelector( "div.sort-bar" );

	/**
	 * the enumeration for sorting ship cards by Featured or by A-Z
	 */
	public enum SortType{ FEATURED, A_Z }

	/**
	 * the enumeration for displaying ship cards by gris or by list.
	 */
	public enum LayoutType{ BY_GRID, BY_LIST }

	/**
	 * @return the number of ships found by finding the text {@code div.sort-bar > h3} item
	 */
	int getResults();

	/**
	 * Sets a display type on the sort-bar.
	 *
	 * @param layout a {@linkplain com.framework.site.objects.body.interfaces.ShipSortBar.LayoutType} value
	 */
	void setLayoutType( LayoutType layout );

	/**
	 * Sets a sort type on the sort-bar.
	 *
	 * @param sort a {@linkplain com.framework.site.objects.body.interfaces.ShipSortBar.SortType} value
	 */
	void setSortType( SortType sort );

	HtmlElement hoverSortType();

	HtmlElement hoverLayoutType();

	HtmlElement getSortTypeOption( SortType option );

	HtmlElement getLayoutOption( LayoutType option );

	/**
	 * @return the current {@linkplain com.framework.site.objects.body.interfaces.ShipSortBar.LayoutType} value
	 */
	LayoutType getLayoutType();

	/**
	 * @return the current {@linkplain com.framework.site.objects.body.interfaces.ShipSortBar.SortType} value
	 */
	SortType getSortType();
}
