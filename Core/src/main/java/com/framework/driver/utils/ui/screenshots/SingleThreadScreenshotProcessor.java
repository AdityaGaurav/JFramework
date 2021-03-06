package com.framework.driver.utils.ui.screenshots;

import com.framework.config.Configurations;
import org.apache.commons.configuration.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.driver.utils.ui.screenshots
 *
 * Name   : SingleThreadScreenshotProcessor
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-01-18
 *
 * Time   : 17:51
 */

public class SingleThreadScreenshotProcessor implements ScreenshotProcessor
{

	//region SingleThreadScreenshotProcessor - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( SingleThreadScreenshotProcessor.class );

	Thread screenshotThread;

	private final Configuration environmentVariables;

	final Queue<QueuedScreenshot> queue;

	boolean done = false;


	//endregion


	//region SingleThreadScreenshotProcessor - Constructor Methods Section

	public SingleThreadScreenshotProcessor()
	{
		this.environmentVariables = Configurations.getInstance();
		this.queue = new ConcurrentLinkedQueue<>();
		start();
	}

	//endregion


	//region SingleThreadScreenshotProcessor - Public Methods Section

	public void start()
	{
		screenshotThread = new Thread( new Processor( queue ) );
		screenshotThread.setDaemon( true );
		screenshotThread.start();
	}

	@Override
	public void waitUntilDone()
	{
		while ( ! isEmpty() )
		{
			try
			{
				Thread.sleep( 10 );
			}
			catch ( InterruptedException ignore )
			{
			}
		}
	}

	@Override
	public void terminate()
	{
		done = true;
	}

	@Override
	public void queueScreenshot( QueuedScreenshot queuedScreenshot )
	{
		queue.offer( queuedScreenshot );
		synchronized ( queue )
		{
			queue.notifyAll();
		}
	}

	public synchronized boolean isEmpty()
	{
		return queue.isEmpty();
	}

	//endregion


	//region SingleThreadScreenshotProcessor - Protected Methods Section

	//endregion


	//region SingleThreadScreenshotProcessor - Private Function Section

	//endregion


	//region SingleThreadScreenshotProcessor - Inner Classes Implementation Section


	class Processor implements Runnable
	{
		private final Queue<QueuedScreenshot> queue;

		Processor( Queue<QueuedScreenshot> queue )
		{
			this.queue = queue;
		}

		public void run()
		{
			while ( ! done )
			{
				synchronized ( queue )
				{
					saveQueuedScreenshot();
					try
					{
						if ( ! done )
						{
							queue.wait();
						}
					}
					catch ( InterruptedException ignore )
					{
					}
				}
			}
		}

		private void saveQueuedScreenshot()
		{
			while ( ! queue.isEmpty() )
			{
				QueuedScreenshot queuedScreenshot = queue.poll();
				if ( queuedScreenshot != null )
				{
					processScreenshot( queuedScreenshot );
				}
			}
		}

		private void processScreenshot( QueuedScreenshot queuedScreenshot )
		{
			if ( ! queuedScreenshot.getDestinationFile().exists() )
			{
				resizeOrMoveScreenshot( queuedScreenshot );
			}
		}

		private void resizeOrMoveScreenshot( QueuedScreenshot queuedScreenshot )
		{
			if ( shouldResize( queuedScreenshot ) )
			{
				resizeScreenshot( queuedScreenshot );
			}
			else
			{
				moveScreenshot( queuedScreenshot );
			}
		}

		private int getResizedWidth()
		{
			//return environmentVariables.getPropertyAsInteger( ThucydidesSystemProperty.THUCYDIDES_RESIZED_IMAGE_WIDTH, 0 );
			return 0;//environmentVariables.getPropertyAsInteger( "", 0 );
		}

		private boolean shouldResize( QueuedScreenshot queuedScreenshot )
		{
			if ( getResizedWidth() > 0 )
			{
				BufferedImage image = readImage( queuedScreenshot );
				if ( image != null )
				{
					int width = image.getData().getWidth();
					return ( width != getResizedWidth() );
				}
			}
			return false;
		}

		private BufferedImage readImage( QueuedScreenshot queuedScreenshot )
		{
			BufferedImage image = null;
			try
			{
				image = ImageIO.read( queuedScreenshot.getSourceFile() );
			}
			catch ( IOException e )
			{
				logger.warn( "Failed to read the stored screenshot (possibly an out of memory error): " + e.getMessage() );
			}
			return image;
		}

		private void moveScreenshot( QueuedScreenshot queuedScreenshot )
		{
			try
			{
				CopyOption[] options = new CopyOption[] { StandardCopyOption.COPY_ATTRIBUTES };

				Path sourcePath = queuedScreenshot.getSourceFile().toPath();
				Path destinationPath = queuedScreenshot.getDestinationFile().toPath();
				Path destinationDir = queuedScreenshot.getDestinationFile().toPath().getParent();
				if ( Files.notExists( destinationDir ) )
				{
					Files.createDirectories( destinationDir );
				}
				if ( Files.notExists( destinationPath ) )
				{
					Files.copy( sourcePath, destinationPath, options );
				}
				try
				{
					Files.deleteIfExists( sourcePath );
				}
				catch ( IOException e )
				{
					queuedScreenshot.getSourceFile().deleteOnExit();
				}
			}
			catch ( Throwable e )
			{
				logger.warn( "Failed to copy the screenshot to the destination directory: " + e.getMessage() );
			}
		}

		private void resizeScreenshot( QueuedScreenshot queuedScreenshot )
		{
			try
			{
				BufferedImage image = ImageIO.read( queuedScreenshot.getSourceFile() );
				int width = image.getData().getWidth();
				int height = image.getData().getHeight();
				int targetWidth = getResizedWidth();
				int targetHeight = ( int ) ( ( ( double ) targetWidth / ( double ) width ) * ( double ) height );

				BufferedImage resizedImage = resize( image, targetWidth, targetHeight );
				ImageIO.write( resizedImage, "png", queuedScreenshot.getDestinationFile() );
				//FileUtils.deleteQuietly( queuedScreenshot.getSourceFile() );
			}
			catch ( Throwable e )
			{
				logger.warn( "Failed to resize screenshot: using original size " + e.getMessage() );
				moveScreenshot( queuedScreenshot );
			}
		}

		private BufferedImage resize( BufferedImage image, int width, int height )
		{
			int type = image.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : image.getType();
			BufferedImage resizedImage = new BufferedImage( width, height, type );
			Graphics2D g = resizedImage.createGraphics();
			g.setComposite( AlphaComposite.Src );

			g.setRenderingHint( RenderingHints.KEY_INTERPOLATION,
					RenderingHints.VALUE_INTERPOLATION_BILINEAR );

			g.setRenderingHint( RenderingHints.KEY_RENDERING,
					RenderingHints.VALUE_RENDER_QUALITY );

			g.setRenderingHint( RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON );

			g.drawImage( image, 0, 0, width, height, null );
			g.dispose();
			return resizedImage;
		}
	}

	//endregion

}
