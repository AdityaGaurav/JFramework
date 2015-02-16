package com.framework.config;

import org.apache.commons.configuration.event.ConfigurationErrorEvent;
import org.apache.commons.configuration.event.ConfigurationErrorListener;
import org.apache.commons.configuration.event.ConfigurationEvent;
import org.apache.commons.configuration.event.ConfigurationListener;
import org.slf4j.Logger;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.config
 *
 * Name   : ConfigurationListenerAdapter 
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-01-20 
 *
 * Time   : 21:27
 *
 */

public class ConfigurationListenerAdapter implements ConfigurationListener, ConfigurationErrorListener
{
	private final Logger logger;

	private static boolean isFirst = true;

//	@Override
//	public void configurationError( final ConfigurationErrorEvent event )
//	{
//		logger.debug( event. );
//	}

	@Override
	public void configurationChanged( final ConfigurationEvent event )
	{
		final String MSG_FMT1 = "new property {} added: [ name: <'{}'>, value: <'{}'> ]";
		final String MSG_FMT2 = "property {} cleared: [ name: <'{}'> ]";
		final String MSG_FMT3 = "property [ name: <'{}'> ] {} set with value: [ value: <'{}'> ]";
		final String MSG_FMT4 = "event 'CLEAR' was sent";
		final String MSG_FMT5 = "property [ name: <'{}'> ] {} queried.";

		switch ( event.getType() )
		{
			case 1:
			{
				logger.debug( MSG_FMT1, isFirst ? "was" : "will be", event.getPropertyName(), event.getPropertyValue() );
				break;
			}
			case 2:
			{
				logger.debug( MSG_FMT2, isFirst ? "was" : "will be", event.getPropertyValue() );
				break;
			}
			case 3:
			{
				logger.debug( MSG_FMT3, event.getPropertyName(), isFirst ? "was" : "will be", event.getPropertyValue() );
				break;
			}
			case 4:
			{
				logger.debug( MSG_FMT4 );
				break;
			}
			case 5:
			{
				logger.debug( MSG_FMT5, event.getPropertyName(), isFirst ? "was" : "will be" );
				break;
			}
			case 20:
			{
				logger.debug( "EVENT_RELOAD was fired from: <'{}'>", event.getSource().getClass().getSimpleName() );
				break;
			}
			case 21:
			{
				logger.debug( "EVENT_CONFIG_CHANGED was fired from: <'{}'>", event.getSource().getClass().getSimpleName() );
				break;
			}
			case 40:
			{
				logger.debug( "EVENT_COMBINED_INVALIDATE was fired from: <'{}'>", event.getSource().getClass().getSimpleName() );
				break;
			}
			default:
			{
				logger.debug( "Event <'{}'> was sent.", event.getType() );
			}
		}

		isFirst = ! isFirst;
	}

	/**
	 * Notifies this listener that in an observed configuration an error occurred.
	 * All information available about this error, including the causing {@code Throwable} object, can be obtained from the passed
	 * in event object.
	 *
	 * @param event the event object with information about the error
	 */
	@Override
	public void configurationError( final ConfigurationErrorEvent event )
	{

	}

	public ConfigurationListenerAdapter( Logger logger )
	{
		this.logger = logger;
	}
}
