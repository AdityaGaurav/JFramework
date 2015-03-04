/*
 * Copyright 2011 Software Freedom Conservancy.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */
package com.framework.utils.datetime;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Primitives for sleeping
 */

public class Sleeper
{
	private static final Logger logger = LoggerFactory.getLogger( Sleeper.class );

	/**
	 * Sleeps without explicitly throwing an InterruptedException
	 *
	 * @param timeInMilliseconds Sleep time in seconds.
	 *
	 * @throws RuntimeException wrapping an InterruptedException if one gets thrown
	 */
	public static void pauseFor( final long timeInMilliseconds )
	{
		try
		{
			sleep( timeInMilliseconds );
		}
		catch ( InterruptedException e )
		{
			logger.warn( "Wait interrupted:" + e.getMessage() );
			throw new RuntimeException( "System timer interrupted", e );
		}
	}

	/**
	 * Sleeps without explicitly throwing an InterruptedException
	 *
	 * @param timeInMilliseconds the amount of time to sleep
	 *
	 * @throws RuntimeException wrapping an InterruptedException if one gets thrown
	 */
	protected static void sleep( long timeInMilliseconds ) throws InterruptedException
	{
		Thread.sleep( timeInMilliseconds );
	}

	/**
	 * Find the current system time.
	 */
	public static DateTime getCurrentTime() {
		return new DateTime();
	}

}
