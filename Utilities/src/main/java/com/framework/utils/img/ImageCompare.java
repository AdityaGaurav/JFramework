package com.framework.utils.img;

import com.framework.utils.error.PreConditions;
import org.openqa.selenium.net.UrlChecker;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.TimeUnit;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.utils.img
 *
 * Name   : ImageCompare 
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-03-04 
 *
 * Time   : 17:24
 *
 */

public class ImageCompare
{

	public static boolean imagesEqual( File source, URL destination ) throws UrlChecker.TimeoutException, IOException
	{
		PreConditions.checkNotNull( source, "The source file cannot be null" );
		PreConditions.checkNotNull( destination, "The destination url cannot be null" );
		PreConditions.checkState( source.exists(), "The source file does not exists" );
		UrlChecker checker = new UrlChecker();
		checker.waitUntilAvailable( 5000, TimeUnit.SECONDS, destination );

		boolean response = true;

		BufferedImage src = ImageIO.read( source );
		BufferedImage dst = ImageIO.read( destination );

		Raster rasterSource = src.getData();  Raster rasterDestination = dst.getData();

		// Comparing the the two images for number of bands,width & height.
		if ( rasterSource.getNumBands() != rasterDestination.getNumBands()
				|| rasterSource.getWidth() != rasterDestination.getWidth()
				|| rasterSource.getHeight() != rasterDestination.getHeight() )
		{
			response = false;
		}
		else
		{
			// Once the band ,width & height matches, comparing the images.

			search:
			for ( int i = 0; i < rasterSource.getNumBands(); ++ i )
			{
				for ( int x = 0; x < rasterSource.getWidth(); ++ x )
				{
					for ( int y = 0; y < rasterSource.getHeight(); ++ y )
					{
						if ( rasterSource.getSample( x, y, i ) != rasterDestination.getSample( x, y, i ) )
						{
							// If one of the result is false setting the result as false and breaking the loop.
							response = false;
							break search;
						}
					}
				}
			}
		}
		return response;
	}

}
