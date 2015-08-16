package com.framework.driver.factory.firefox;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.driver.factory.firefox
 *
 * Name   : FireFoxProfileBuilder 
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-03-15 
 *
 * Time   : 12:25
 *
 */

public class FireFoxProfileBuilder
{

	//region FireFoxProfileBuilder - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( FireFoxProfileBuilder.class );

	/**
	 * focus is normally indicated by a dotted outline surrounding the element.
	 * If browser.display.use_focus_colors is set to {@code true}, the element will also have its background color
	 * set to this preference’s value.
	 *
	 *  Hex color value determining the background color for focused elements. Default is #117722.
	 *  browser.display.use_focus_colors must be {@code true} for this preference to have an effect.
	 *
	 * @see #BROWSER_DISPLAY_USE_FOCUS_COLOR
	 */
	private static final String BROWSER_DISPLAY_FOCUS_BACKGROUND_COLOR = "browser.display.focus_background_color";

	private static final String BROWSER_DISPLAY_USE_FOCUS_COLOR = "browser.display.use_focus_colors";

	/**
	 * Takes a number.
	 * It tells Firefox which download directory to use.
	 * <table>
	 *     <tr>
	 *         <td>0</td>
	 *         <td>would place them on the <b>Desktop</b></td>
	 *     </tr>
	 *     <tr>
	 *         <td>1</td>
	 *         <td>would use the browser's default path usually in the <b>Downloads</b> folder</td>
	 *     </tr>
	 *     <tr>
	 *         <td>2</td>
	 *         <td>tells it to use a custom download path</td>
	 *     </tr>
	 * </table>
	 *
	 * @see  #BROWSER_DOWNLOAD_DIR
	 */
	private static final String BROWSER_DOWNLOAD_FOLDER_LIST = "browser.download.folderList";

	/**
	 * Accepts a string. This is how we set the custom download path. It needs to be an absolute path.
	 */
	private static final String BROWSER_DOWNLOAD_DIR = "browser.download.dir";

	/**
	 *  Tells Firefox when not to prompt for a file download.
	 *  It accepts a string of the file's MIME type.
	 *  If you want to specify more than one, you do it with a comma-separated string.
	 *
	 *  example: {@code images/jpeg, application/pdf}
	 */
	private static final String BROWSER_HELPER_APPS_NEVER_ASK_SAVE_2_DISK = "browser.helperApps.neverAsk.saveToDisk";

	/**
	 *  allows the user to specify the amount of days worth of browsing history that is saved.
	 *  Browsing history older than the numbers of days specified in this Preference are automatically deleted.
	 *  The default value is 180 days
	 */
	private static final String BROWSER_HISTORY_EXPIRE_DAYS = "browser.history_expire_days";

	/**
	 * Takes a number
	 * Allows the user to choose between one of three pre-specified locations in which to store file downloads.
	 * The default value is 1.
	 * <table>
	 *     <tr>
	 *         <td>0</td>
	 *         <td>a blank page <code>(about:blank)</code> is opened upon launch.</td>
	 *     </tr>
	 *     <tr>
	 *         <td>1</td>
	 *         <td>causes Firefox to open whatever page(s) is set as the browser's home page.</td>
	 *     </tr>
	 *     <tr>
	 *         <td>2</td>
	 *         <td>the Web page that the user last visited is opened.</td>
	 *     </tr>
	 *     <tr>
	 *         <td>3</td>
	 *         <td>The user's previous browsing session is restored</td>
	 *     </tr>
	 * </table>
	 */
	private static final String BROWSER_STARTUP_PAGE = "browser.startup.page";

	/**
	 * is for when downloading PDFs.
	 * This overrides the sensible default in Firefox that previews PDFs in the browser.
	 * It accepts a boolean.
	 */
	private static final String PDF_JS_DISABLED = "pdfjs.disabled";

	//endregion


	//region FireFoxProfileBuilder - Constructor Methods Section

	//endregion


	//region FireFoxProfileBuilder - Public Methods Section

	//endregion


	//region FireFoxProfileBuilder - Protected Methods Section

	//endregion


	//region FireFoxProfileBuilder - Private Function Section

	//endregion


	//region FireFoxProfileBuilder - Inner Classes Implementation Section

	//endregion

}
