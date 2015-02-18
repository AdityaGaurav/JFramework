package com.framework.site.objects.body.interfaces;

import org.openqa.selenium.By;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.site.objects.body.interfaces
 *
 * Name   : NavStickEmbedded 
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-02-07 
 *
 * Time   : 17:49
 *
 */

public interface NavStickEmbedded
{
	enum NavStickItem{ WHATS_INCLUDED, ON_THE_SHIP, SHORE_EXCURSIONS, DESTINATIONS }

	static final By ROOT_BY = By.cssSelector( "ul.nav.stickem" );

	static final String LOGICAL_NAME = "Stick Embedded";

	NavStickItem getActiveItem();
}
