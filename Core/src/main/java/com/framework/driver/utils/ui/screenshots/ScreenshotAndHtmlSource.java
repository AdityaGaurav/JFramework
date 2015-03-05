package com.framework.driver.utils.ui.screenshots;

import com.google.common.base.Optional;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.driver.utils.ui
 *
 * Name   : ScreenshotAndHtmlSource
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-01-17
 *
 * Time   : 10:57
 */

public class ScreenshotAndHtmlSource
{

	//region ScreenshotAndHtmlSource - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( ScreenshotAndHtmlSource.class );

	private final File screenshotFile;

	private final File htmlSource;

	//endregion


	//region ScreenshotAndHtmlSource - Constructor Methods Section

	public ScreenshotAndHtmlSource( String screenshotName, String sourcecodeName )
	{
		this.screenshotFile = new File( screenshotName );
		this.htmlSource = ( sourcecodeName != null ) ? new File( sourcecodeName ) : null;
	}

	public ScreenshotAndHtmlSource( File screenshotFile, File sourcecode )
	{
		this.screenshotFile = screenshotFile;
		this.htmlSource = sourcecode;
	}

	public ScreenshotAndHtmlSource( File screenshotFile )
	{
		this( screenshotFile, null );
	}

	//endregion


	//region ScreenshotAndHtmlSource - Public Methods Section

	public String getScreenshotName()
	{
		return screenshotFile.getName();
	}

	public String getHtmlSourceName()
	{
		if ( htmlSource == null )
		{
			return null;
		}
		return htmlSource.getName();
	}

	public File getScreenshotFile() {
		return screenshotFile;
	}

	public Optional<File> getHtmlSource()
	{
		return Optional.fromNullable( htmlSource );
	}

	public boolean wasTaken()
	{
		return ( screenshotFile != null );
	}

	public boolean hasIdenticalScreenshotsAs( ScreenshotAndHtmlSource anotherScreenshotAndHtmlSource )
	{
		if ( hasNoScreenshot() || anotherScreenshotAndHtmlSource.hasNoScreenshot() )
		{
			return false;
		}
		return ( getScreenshotFile().getName().equals( anotherScreenshotAndHtmlSource.getScreenshotFile().getName() ) );
	}

	public File getScreenshotFile( File screenshotTargetDirectory )
	{
		return new File( screenshotTargetDirectory, getScreenshotFile().getName() );
	}

	public boolean hasNoScreenshot()
	{
		return getScreenshotFile() == null;
	}

	@Override
	public boolean equals( Object o )
	{
		if ( this == o )
		{
			return true;
		}
		if ( ! ( o instanceof ScreenshotAndHtmlSource ) )
		{
			return false;
		}

		ScreenshotAndHtmlSource that = ( ScreenshotAndHtmlSource ) o;

		if ( screenshotFile == null )
		{
			return ( that.screenshotFile == null );
		}
		else if ( that.screenshotFile == null )
		{
			return ( this.screenshotFile == null );
		}
		else
		{
			try
			{
				return FileUtils.contentEquals( screenshotFile, that.screenshotFile );
			}
			catch ( IOException e )
			{
				return false;
			}
		}
	}

	@Override
	public int hashCode()
	{
		return screenshotFile != null ? screenshotFile.hashCode() : 0;
	}

	//endregion
}
