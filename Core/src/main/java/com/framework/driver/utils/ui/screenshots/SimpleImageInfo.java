package com.framework.driver.utils.ui.screenshots;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.driver.utils.ui.screenshots
 *
 * Name   : SimpleImageInfo
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-01-18
 *
 * Time   : 03:26
 */

public class SimpleImageInfo
{

	//region SimpleImageInfo - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( SimpleImageInfo.class );

	private int height;

	private int width;

	//endregion


	//region SimpleImageInfo - Constructor Methods Section

	public SimpleImageInfo( final File file ) throws IOException
	{
		InputStream is = new FileInputStream( file );
		try
		{
			processStream( is );
		}
		finally
		{
			is.close();
		}
	}

	//endregion


	//region SimpleImageInfo - Public Methods Section

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	//endregion


	//region SimpleImageInfo - Private Function Section

	private void processStream( final InputStream is ) throws IOException
	{
		int c1 = is.read();
		int c2 = is.read();
		int c3 = is.read();

		width = height = - 1;

		if ( c1 == 137 && c2 == 80 && c3 == 78 )
		{ // PNG
			is.skip( 15 );
			width = readInt( is, 2 );
			is.skip( 2 );
			height = readInt( is, 2 );
		}
		else
		{
			throw new IOException( "Unsupported image type" );
		}
	}

	private int readInt( final InputStream is, final int noOfBytes ) throws IOException
	{
		int ret = 0;
		int sv = ( noOfBytes - 1 ) * 8;
		int cnt = - 8;
		for ( int i = 0; i < noOfBytes; i++ )
		{
			ret |= is.read() << sv;
			sv += cnt;
		}
		return ret;
	}


	//endregion

}
