package com.framework.site.objects.body.interfaces;

import org.openqa.selenium.By;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.site.objects.body.interfaces
 *
 * Name   : AppFilters
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-01-10
 *
 * Time   : 02:36
 */

public interface FilterCategories
{
	static final String LOGICAL_NAME = "filter Categories Container";

	static final By ROOT_BY = By.cssSelector( "div.filter-block.app-filters[data-app='ships']" );

	void doToggle();

	interface CurrentFilters
	{

	}

	interface FilterPort
	{

	}

	interface FilterDestination
	{

	}

	interface FilterDuration
	{

	}
}
