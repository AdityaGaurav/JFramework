package com.framework.driver.utils.ui.screenshots;

import com.framework.utils.string.ErrorMessageFormatter;
import org.apache.commons.lang3.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.driver.utils.ui.screenshots
 *
 * Name   : Screenshot
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-01-17
 *
 * Time   : 15:05
 */

public class Screenshot
{

	//region Screenshot - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( Screenshot.class );

	private final String filename;

	private final String description;

	private final int width;

	private final Throwable error;

	//endregion


	//region Screenshot - Constructor Methods Section

	public Screenshot( final String filename,
					   final String description,
					   final int width,
					   final Throwable error )
	{
		this.filename = filename;
		this.description = description;
		this.width = width;
		this.error = error;
	}

	public Screenshot( final String filename,
					   final String description,
					   final int width )
	{
		this( filename, description, width, null );
	}


	//endregion


	//region Screenshot - Public Methods Section

	public Throwable getError() {
		return error;
	}

	public String getErrorMessage() {
		return (error != null) ? error.getMessage(): "";
	}

	/**
	 * Returns the first line only of the error message.
	 * This avoids polluting the UI with unnecessary details such as browser versions and so forth.
	 * @return
	 */
	public String getShortErrorMessage() {
		return new ErrorMessageFormatter(getErrorMessage()).getShortErrorMessage();
	}

	public String getFilename() {
		return filename;
	}

	public String getDescription() {
		return description;
	}

	public int getWidth() {
		return width;
	}

	public HtmlFormattedInfo getHtml() {
		return new HtmlFormattedInfo(description);
	}

	//endregion



	//region Screenshot - Inner Classes Implementation Section


	public class HtmlFormattedInfo
	{

		private final String description;

		public HtmlFormattedInfo( String description )
		{
			this.description = description;
		}

		public String getDescription()
		{
			return StringEscapeUtils.escapeHtml4( description );
		}
	}

	//endregion

}
