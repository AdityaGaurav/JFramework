package com.framework.driver.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.driver.event
 *
 * Name   : WebDriverErrorListenerAdapter 
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-02-05 
 *
 * Time   : 00:32
 *
 */

public class WebDriverErrorListenerAdapter implements WebDriverErrorListener
{

	//region WebDriverErrorListenerAdapter - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( WebDriverErrorListenerAdapter.class );

	//endregion

	@Override
	public void onException( final WebDriverErrorEvent event )
	{
		logger.error( event.getCause().getMessage() );
	}
}
