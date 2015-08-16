package com.framework.reporter.utils;

import org.apache.velocity.app.Velocity;
import org.apache.velocity.runtime.RuntimeServices;
import org.apache.velocity.runtime.log.LogChute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.reporter.utils
 *
 * Name   : VelocityLogger 
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-02-12 
 *
 * Time   : 14:02
 *
 */

public class VelocityLogger implements LogChute
{

	//region VelocityLogger - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( VelocityLogger.class );

	//endregion


	//region VelocityLogger - Constructor Methods Section

	public VelocityLogger()
	{
		try
		{
            /*
             *  this class implements the LogSystem interface, so we
             *  can use it as a logger for Velocity
             */

			Velocity.setProperty( Velocity.RUNTIME_LOG_LOGSYSTEM, this );
			Velocity.init();

            /*
             *  that will be enough.  The Velocity initialization will be
             *  output to stdout because of our
             *  logVelocityMessage() method in this class
             */
		}
		catch( Exception e )
		{
			System.err.println("Exception : " + e);
		}
	}


	//endregion


	//region VelocityLogger - Public Methods Section

	@Override
	public void init( final RuntimeServices rs ) throws Exception
	{
		//
	}

	@Override
	public void log( final int level, final String message )
	{
   		logger.debug( message );
	}

	@Override
	public void log( final int level, final String message, final Throwable t )
	{
		logger.error( message, t );
	}

	@Override
	public boolean isLevelEnabled( final int level )
	{
		return true;
	}


	//endregion


	//region VelocityLogger - Protected Methods Section

	//endregion


	//region VelocityLogger - Private Function Section

	//endregion


	//region VelocityLogger - Inner Classes Implementation Section

	//endregion

}
