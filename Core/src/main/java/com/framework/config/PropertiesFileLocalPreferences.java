package com.framework.config;

import com.google.common.base.Optional;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.lang3.SystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.utils.system
 *
 * Name   : PropertiesFileLocalPreferences
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-01-17
 *
 * Time   : 03:17
 */

public class PropertiesFileLocalPreferences implements LocalPreferences
{

	//region PropertiesFileLocalPreferences - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( PropertiesFileLocalPreferences.class );

	private File workingDirectory;

	private File homeDirectory;

	private final Configuration environmentVariables;

	//endregion


	//region PropertiesFileLocalPreferences - Constructor Methods Section

	public PropertiesFileLocalPreferences( Configuration environmentVariables )
	{
		this.environmentVariables = environmentVariables;
		this.homeDirectory = SystemUtils.getUserHome();
		this.workingDirectory = SystemUtils.getUserDir();
	}

	//endregion


	//region PropertiesFileLocalPreferences - Public Methods Section

	/**
	 * Loads properties file named {@linkplain  Configurations#USER_PROPERTIES_PREFERENCES}
	 *
	 * @throws IOException in case that loading caused error.
	 */
	public File locatePreferences() throws IOException
	{
		// create a list of files to locate

		File[] files = new File[] {
				new File( homeDirectory, Configurations.USER_PROPERTIES_PREFERENCES ),
				new File( workingDirectory, Configurations.USER_PROPERTIES_PREFERENCES ),
		};

		try
		{

			// locating properties file in some locations, if found, will be added to the list

			for( File file : files )
			{
				if( locatePreferencesFrom( file ) )
				{
					return file;
				}
			}

			// locating preferences in classpath

			Optional<URL> url = locatePreferencesFromClasspath();
			if( url.isPresent() )
			{
				return new File( url.get().toURI() );
			}
		}
		catch ( URISyntaxException e )
		{
			logger.warn( "Failed to load properties file from class path --> <'" + Configurations.USER_PROPERTIES_PREFERENCES + "'>" );
		}

		return null;
	}

	//endregion


	//region PropertiesFileLocalPreferences - Private Function Section

	private Optional<URL> locatePreferencesFromClasspath() throws IOException
	{
		logger.debug( "Searching for <'{}'> in classpath.", Configurations.USER_PROPERTIES_PREFERENCES );
		return Optional.fromNullable( Thread.currentThread().getContextClassLoader().getResource( Configurations.USER_PROPERTIES_PREFERENCES ) );
	}

	/**
	 * Searching for a specific properties file and merge into environment variables ( if found )
	 *
	 * @param preferencesFile  The preference file name.
	 *
	 * @throws IOException if preference file could not be uploaded.
	 */
	private boolean locatePreferencesFrom( File preferencesFile ) throws IOException
	{
		logger.debug( "Trying to find preferences file <'{}'> ...", preferencesFile.getCanonicalPath() );

		if ( preferencesFile.exists() )
		{
			logger.info( "LOADING LOCAL JFRAMEWORK PROPERTIES FROM {} ", preferencesFile.getAbsolutePath() );
			return true;
		}

		return false;
	}

	//endregion

}
