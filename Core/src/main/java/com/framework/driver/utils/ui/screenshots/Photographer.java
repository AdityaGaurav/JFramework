package com.framework.driver.utils.ui.screenshots;

import com.framework.config.Configurations;
import com.framework.config.FrameworkConfiguration;
import com.framework.driver.event.HtmlDriver;
import com.framework.driver.event.HtmlElement;
import com.framework.utils.error.PreConditions;
import com.google.common.base.Optional;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static com.framework.config.FrameworkProperty.JFRAMEWORK_BASE_SCREENSHOTS_DIRECTORY;
import static com.framework.config.FrameworkProperty.JFRAMEWORK_STORE_HTML_SOURCE;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.driver.utils.ui
 *
 * Name   : Photographer
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-01-17
 *
 * Time   : 12:10
 */

public class Photographer
{

	//region Photographer - Variables Declaration and Initialization Section.

	private Logger logger = null;

	private ScreenshotProcessor screenshotProcessor = new SingleThreadScreenshotProcessor();

	private static final FrameworkConfiguration environmentVariables = Configurations.getInstance();

	private final HtmlDriver driver;

	private final HtmlElement element;

	private File targetDirectory;

	private boolean storeHtmlSourceCode;

	//endregion


	//region Photographer - Constructor Methods Section

	public Photographer( final HtmlDriver driver )
	{
		this( driver, environmentVariables.getBoolean( JFRAMEWORK_STORE_HTML_SOURCE ) );
	}

	public Photographer( final HtmlDriver driver, boolean storeHtmlSourceCode )
	{
		this( driver, new File( environmentVariables.getString( JFRAMEWORK_BASE_SCREENSHOTS_DIRECTORY ) ), storeHtmlSourceCode );
	}

	public Photographer( final HtmlDriver driver, final File targetDirectory, boolean attachSourceCode )
	{
		this.driver = PreConditions.checkNotNull( driver, "The WebDriver cannot be null" );
		File f = PreConditions.checkNotNull( targetDirectory, "The File targetDirectory cannot be null" );
		setTargetDirectory( f );
		setStoreHtmlSourceCode( attachSourceCode );
		this.element = null;
	}

	public Photographer( final HtmlElement element )
	{
		this( element, false );
	}

	public Photographer( final HtmlElement element, boolean storeHtmlSourceCode )
	{
		this( element, new File( environmentVariables.getString( JFRAMEWORK_BASE_SCREENSHOTS_DIRECTORY ) ), storeHtmlSourceCode );
	}

	public Photographer( final HtmlElement element, final File targetDirectory, boolean attachSourceCode )
	{
		this.element = PreConditions.checkNotNull( element, "The HtmlElement cannot be null" );
		File f = PreConditions.checkNotNull( targetDirectory, "The File targetDirectory cannot be null" );
		setTargetDirectory( f );
		setStoreHtmlSourceCode( attachSourceCode );
		this.driver = null;
	}

	//endregion


	//region Photographer - Public Methods Section

	/**
	 * Take a screenshot of the current browser and store it in the output directory.
	 */
	protected Optional<File> takeScreenshot()
	{
		if ( driver != null && driverCanTakeSnapshots() )
		{
			try
			{
				File screenshotTempFile = null;
				Object capturedScreenshot = ( ( TakesScreenshot ) driver ).getScreenshotAs( OutputType.FILE );
				if ( isAFile( capturedScreenshot ) )
				{
					screenshotTempFile = ( File ) capturedScreenshot;
					File target = new File( targetDirectory, "temp.png" );
					FileUtils.copyFile( screenshotTempFile, target );
				}
				else if ( isByteArray( capturedScreenshot ) )
				{
					screenshotTempFile = saveScreenshotData( ( byte[] ) capturedScreenshot );
				}
				if ( screenshotTempFile != null )
				{
					String storedFilename = getDigestScreenshotNameFor( screenshotTempFile );
					File savedScreenshot = targetScreenshot( storedFilename );
					screenshotProcessor.queueScreenshot( new QueuedScreenshot( screenshotTempFile, savedScreenshot ) );
					return Optional.of( savedScreenshot );
				}
			}
			catch ( Throwable e )
			{
				getLogger().warn( "Failed to write screenshot ( possibly an out of memory error ): " + e.getMessage() );
			}
		}
		else if( element != null )
		{
			//element.scrollIntoView();
			//Sleeper.pauseFor( 100 );
			HtmlDriver drv = element.getWrappedHtmlDriver();
			try
			{
				Object capturedScreenshot = ( ( TakesScreenshot ) drv ).getScreenshotAs( OutputType.FILE );
				File screenshotTempFile = ( File ) capturedScreenshot;
				// Create an instance of Buffered Image from captured screenshot
				BufferedImage img = ImageIO.read( screenshotTempFile );
				int width = element.getSize().getWidth();
				int height = element.getSize().getHeight();
				// Create a rectangle using Width and Height
				Rectangle rect = new Rectangle( width, height );
				Point p = element.getLocation();
				Point offset = new Point( p.getX(), p.getY() );

				//Create image by for element using its location and size.
				//This will give image data specific to the WebElement
				BufferedImage bi = img.getSubimage( offset.getX(), offset.getY(), rect.width, rect.height );

				//Write back the image data for element in File object
				ImageIO.write( bi, "png", screenshotTempFile );
				String storedFilename = getDigestScreenshotNameFor( screenshotTempFile );
				File savedScreenshot = targetScreenshot( storedFilename );
				screenshotProcessor.queueScreenshot( new QueuedScreenshot( screenshotTempFile, savedScreenshot ) );
				return Optional.of( savedScreenshot );
			}
			catch ( Throwable e )
			{
				getLogger().warn( "Failed to write screenshot ( possibly an out of memory error ): " + e.getMessage() );
			}

		}
		return Optional.absent();
	}

	public Optional<ScreenshotAndHtmlSource> grabScreenshot()
	{
		Optional<File> screenshot = takeScreenshot();
		if ( screenshot.isPresent() )
		{
			if ( isStoreHtmlSourceCode() )
			{
				File sourcecodeFile = sourcecodeForScreenshot( screenshot.get(), getPageSource() );
				return Optional.of( new ScreenshotAndHtmlSource( screenshot.get(), sourcecodeFile ) );
			}
			else
			{
				return Optional.of( new ScreenshotAndHtmlSource( screenshot.get() ) );
			}
		}
		return Optional.absent();
	}

	//endregion


	//region Photographer - Setters and Getters Methods Section

	public void setScreenshotProcessor( ScreenshotProcessor screenshotProcessor )
	{
		this.screenshotProcessor = screenshotProcessor;
	}

	public void setTargetDirectory( final File targetDirectory )
	{
		this.targetDirectory = targetDirectory;
	}

	public void setStoreHtmlSourceCode( final boolean storeHtmlSourceCode )
	{
		this.storeHtmlSourceCode = storeHtmlSourceCode;
	}

	public void setLogger( final Logger logger )
	{
		this.logger = logger;
	}

	public static FrameworkConfiguration getEnvironmentVariables()
	{
		return environmentVariables;
	}

	protected HtmlDriver getDriver()
	{
		return driver;
	}

	public File getTargetDirectory()
	{
		return targetDirectory;
	}

	public boolean isStoreHtmlSourceCode()
	{
		return storeHtmlSourceCode;
	}

	protected Logger getLogger()
	{
		if( null == logger )
		{
			logger =  LoggerFactory.getLogger( Photographer.class );
		}
		return logger;
	}

	protected ScreenshotProcessor getScreenshotProcessor()
	{
		return screenshotProcessor;
	}

	//endregion


	//region Photographer - Service Methods Section

	protected boolean driverCanTakeSnapshots()
	{
		return driver != null && TakesScreenshot.class.isAssignableFrom( driver.getClass() );
	}

	//endregion


	//region Photographer - Private Function Section

	private String getDigestScreenshotNameFor( File screenshotTempFile ) throws IOException
	{
		return DigestUtils.md5Hex( new FileInputStream( screenshotTempFile ) ) + ".png";
	}

	private File saveScreenshotData( byte[] capturedScreenshot ) throws IOException
	{
		Path temporaryScreenshotFile = Files.createTempFile( "screenshot", "" );
		Files.write( temporaryScreenshotFile, capturedScreenshot );
		return temporaryScreenshotFile.toFile();
	}

	private boolean isAFile( Object screenshot )
	{
		return ( screenshot instanceof File );
	}

	private boolean isByteArray( Object screenshot )
	{
		return ( screenshot instanceof byte[] );
	}

	@SuppressWarnings ( "ResultOfMethodCallIgnored" )
	private File targetScreenshot( String storedFilename )
	{
		targetDirectory.mkdirs();
		return new File( targetDirectory, storedFilename );
	}

	private File sourcecodeForScreenshot( File screenshotFile, String pageSource )
	{
		File pageSourceFile = new File( screenshotFile.getAbsolutePath() + ".html" );

		try
		{
			Files.write( pageSourceFile.toPath(), pageSource.getBytes() );
		}
		catch ( IOException e )
		{
			logger.warn( "Failed to write screen source code", e );
		}
		return pageSourceFile;
	}

	private String getPageSource()
	{
		return driver.getPageSource();
	}

	//endregion
}
