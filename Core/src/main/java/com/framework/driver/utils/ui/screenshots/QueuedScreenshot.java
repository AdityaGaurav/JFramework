package com.framework.driver.utils.ui.screenshots;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.driver.utils.ui.screenshots
 *
 * Name   : QueuedScreenshot
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-01-17
 *
 * Time   : 12:24
 */

public class QueuedScreenshot
{

	//region QueuedScreenshot - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( QueuedScreenshot.class );

	private final File destinationFilename;

	private final File sourceFilename;

	//endregion


	//region QueuedScreenshot - Constructor Methods Section

	public QueuedScreenshot( File sourceFilename, File destinationFilename )
	{
		this.sourceFilename = sourceFilename;
		this.destinationFilename = destinationFilename;
	}

	//endregion


	//region QueuedScreenshot - Public Methods Section

	public File getDestinationFile() {
		return destinationFilename;
	}

	public File getSourceFile() {
		return sourceFilename;
	}

	//endregion

}
