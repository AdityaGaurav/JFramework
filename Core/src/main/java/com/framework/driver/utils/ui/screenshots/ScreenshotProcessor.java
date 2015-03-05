package com.framework.driver.utils.ui.screenshots;

/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.driver.utils.ui.screenshots
 *
 * Name   : ScreenshotProcessor
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-01-17
 *
 * Time   : 12:21
 */

public interface ScreenshotProcessor
{

	void waitUntilDone();

	void terminate();

	void queueScreenshot( QueuedScreenshot queuedScreenshot );

	boolean isEmpty();
}
