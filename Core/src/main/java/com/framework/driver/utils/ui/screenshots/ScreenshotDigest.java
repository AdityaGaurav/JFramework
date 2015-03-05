package com.framework.driver.utils.ui.screenshots;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.configuration.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.driver.utils.ui.screenshots
 *
 * Name   : ScreenshotDigest
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-01-17
 *
 * Time   : 12:26
 */

public class ScreenshotDigest
{

	//region ScreenshotDigest - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( ScreenshotDigest.class );

	private final Configuration environmentVariables;

	//endregion


	//region ScreenshotDigest - Constructor Methods Section

	public ScreenshotDigest( Configuration environmentVariables )
	{
		this.environmentVariables = environmentVariables;
	}

	//endregion


	//region ScreenshotDigest - Public Methods Section

	public String forScreenshot( File screenshotFile ) throws IOException
	{
		return DigestUtils.md5Hex( new FileInputStream( screenshotFile ) )
				+ "_"  + optionalWidth() + ".png";
	}

	private String optionalWidth()
	{
		return "960";
		//return environmentVariables.getProperty( ThucydidesSystemProperty.THUCYDIDES_RESIZED_IMAGE_WIDTH, "" );
	}

	//endregion

}
