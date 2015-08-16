package com.framework.utils.log;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.LayoutBase;
import com.framework.utils.string.JConstants;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.utils.log
 *
 * Name   : CustomLayout 
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-01-21 
 *
 * Time   : 18:04
 *
 */

public class CustomLayout extends LayoutBase<ILoggingEvent>
{

	public String doLayout( ILoggingEvent event )
	{
		StringBuffer sbuf = new StringBuffer( 128 );
		sbuf.append( event.getTimeStamp() - event.getLoggerContextVO().getBirthTime() );
		sbuf.append( " " );
		sbuf.append( event.getLevel() );
		sbuf.append( " [" );
		sbuf.append( event.getThreadName() );
		sbuf.append( "] " );
		sbuf.append( event.getLoggerName() );
		sbuf.append( " - " );
		sbuf.append( event.getFormattedMessage() );
		sbuf.append( JConstants.LINE_SEP );
		return sbuf.toString();
	}
}
