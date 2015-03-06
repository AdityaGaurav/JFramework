package com.framework.site.exceptions;

import com.framework.driver.event.HtmlElement;
import com.framework.driver.event.HtmlObject;
import com.framework.driver.exceptions.ApplicationException;
import com.framework.driver.utils.ui.screenshots.ScreenshotAndHtmlSource;
import com.google.common.base.Optional;

import java.util.List;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.site.exceptions
 *
 * Name   : BookegGuestLoginException 
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-02-07 
 *
 * Time   : 23:57
 *
 */

public class BookedGuestLoginException extends ApplicationException
{
	private static final long serialVersionUID = - 5201227256222221403L;

	private  HtmlObject container;

	public BookedGuestLoginException( final List<String> messages, HtmlElement container )
	{
		super();

		for( String message : messages )
		{
			addInfo( "error message", message );
		}
		Optional<ScreenshotAndHtmlSource> file = container.captureBitmap();
		if ( file.isPresent() )
		{
			addInfo( "ScreenshotName", file.get().getScreenshotName() );
		}
	}
}
