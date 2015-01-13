package com.framework.driver.event;

import com.framework.utils.datetime.Sleeper;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.internal.Coordinates;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.concurrent.TimeUnit;


/**
 * Use this class as base class, if you want to implement a {@link com.framework.driver.event.EventListener} and are
 * only interested in some events. All methods provided by this class have an empty method body.
 */

public abstract class AbstractEventListener implements EventListener
{
	//private Logger logger = null;
	
	protected DateTime dateTime;

	protected NumberFormat formatter = new DecimalFormat( "#,###,###" );

	//private static int logLevel = Level.DEBUG_INT;

	protected void sleep( long millis )
	{
		Sleeper.sleepTight( millis );
	}

	protected String getDescription( WebElement e )
	{
		int index = e.toString().indexOf( "->" );
		return StringUtils.substring( e.toString(), index + 3 ).replace( "]", StringUtils.EMPTY );
	}

	@Override
	public void beforeSetScriptTimeout( final WebDriver driver, final long time, final TimeUnit unit )
	{

	}

	@Override
	public void afterSetScriptTimeout( final long time, final TimeUnit unit )
	{

	}

	@Override
	public void beforePageLoadTimeout( final WebDriver driver, final long time, final TimeUnit unit )
	{

	}

	@Override
	public void afterPageLoadTimeout( final long time, final TimeUnit unit )
	{

	}

	@Override
	public void beforeImplicitlyWait( final WebDriver driver, final long time, final TimeUnit unit )
	{

	}

	@Override
	public void afterImplicitlyWait( final long time, final TimeUnit unit )
	{

	}

	@Override
	public void beforeSetWindowSize( final WebDriver driver, final Dimension targetSize )
	{

	}

	@Override
	public void afterSetWindowSize( final WebDriver driver )
	{

	}

	@Override
	public void beforeSetWindowPosition( final WebDriver driver, final Point targetLocation )
	{

	}

	@Override
	public void afterSetWindowPosition( final WebDriver driver )
	{

	}

	@Override
	public void beforeMaximize( final WebDriver driver )
	{

	}

	@Override
	public void afterMaximize( final WebDriver driver )
	{

	}

	@Override
	public void beforeDeleteAllCookies( final WebDriver driver )
	{

	}

	@Override
	public void afterDeleteAllCookies( final WebDriver driver )
	{

	}

	@Override
	public void beforeDeleteCookie( final WebDriver driver, final Cookie cookie )
	{

	}

	@Override
	public void afterDeleteCookie( final WebDriver driver, final Cookie cookie )
	{

	}

	@Override
	public void beforeDeleteCookie( final WebDriver driver, final String cookieName )
	{

	}

	@Override
	public void afterDeleteCookie( final WebDriver driver, final String cookieName )
	{

	}

	@Override
	public void beforeAddCookie( final WebDriver driver, final Cookie cookie )
	{

	}

	@Override
	public void afterAddCookie( final WebDriver driver, final Cookie cookie )
	{

	}

	@Override
	public void beforeClose( final WebDriver driver )
	{

	}

	@Override
	public void afterClose( final WebDriver driver )
	{

	}

	@Override
	public void beforeQuit( final WebDriver driver )
	{

	}

	@Override
	public void afterQuit()
	{

	}

	@Override
	public void beforeRefresh( final WebDriver driver )
	{

	}

	@Override
	public void afterRefresh( final WebDriver driver )
	{

	}

	@Override
	public void beforeSwitchToFrame( final WebDriver driver, final int frameIndex )
	{

	}

	@Override
	public void afterSwitchToFrame( final WebDriver driver, final WebDriver switched, final int frameIndex )
	{

	}

	@Override
	public void beforeSwitchToFrame( final WebDriver driver, final String name )
	{

	}

	@Override
	public void afterSwitchToFrame( final WebDriver driver, final WebDriver switched, final String name )
	{

	}

	@Override
	public void beforeSwitchToFrame( final WebDriver driver, final WebElement element )
	{

	}

	@Override
	public void afterSwitchToFrame( final WebDriver driver, final WebDriver switched, final WebElement element )
	{

	}

	@Override
	public void beforeSwitchToParentFrame( final WebDriver driver )
	{

	}

	@Override
	public void afterSwitchToParentFrame( final WebDriver driver, final WebDriver switched )
	{

	}

	@Override
	public void beforeSwitchToWindow( final WebDriver driver, final String windowName )
	{

	}

	@Override
	public void afterSwitchToWindow( final WebDriver driver, final WebDriver switched, final String windowName )
	{

	}

	@Override
	public void beforeSwitchToDefaultContent( final WebDriver driver )
	{

	}

	@Override
	public void afterSwitchToDefaultContent( final WebDriver driver, final WebDriver switched )
	{

	}

	@Override
	public void beforeSwitchToActiveElement( final WebDriver driver )
	{

	}

	@Override
	public void afterSwitchToActiveElement( final WebDriver driver, final WebElement element )
	{

	}

	@Override
	public void beforeNavigateTo( final String url, final WebDriver driver )
	{

	}

	@Override
	public void afterNavigateTo( final String url, final WebDriver driver )
	{

	}

	@Override
	public void beforeNavigateBack( final WebDriver driver )
	{

	}

	@Override
	public void afterNavigateBack( final WebDriver driver )
	{

	}

	@Override
	public void beforeNavigateForward( final WebDriver driver )
	{

	}

	@Override
	public void afterNavigateForward( final WebDriver driver )
	{

	}

	@Override
	public void beforeFindBy( final By by, final WebElement element, final WebDriver driver )
	{

	}

	@Override
	public void beforeFindBy( final By by, final WebDriver driver )
	{

	}

	@Override
	public void afterFindBy( final By by, final WebDriver driver )
	{

	}

	@Override
	public void afterFindBy( final By by, final WebElement element, final WebDriver driver )
	{

	}

	@Override
	public void afterFindBy( final By by, final int size, final WebDriver driver )
	{

	}

	@Override
	public void afterFindBy( final By by, final WebElement element, final int size, final WebDriver driver )
	{

	}

	@Override
	public void beforeScript( final String script, final WebDriver driver )
	{

	}

	@Override
	public void afterScript( final WebDriver driver )
	{

	}

	@Override
	public void beforeActionOn( final WebElement element, final WebDriver driver, final String action )
	{

	}

	@Override
	public void afterActionOn( final WebElement element, final WebDriver driver, final String action )
	{

	}

	@Override
	public void beforeClickOn( final WebElement element, final WebDriver driver )
	{

	}

	@Override
	public void afterClickOn( final WebElement element, final WebDriver driver )
	{

	}

	@Override
	public void beforeChangeValueOf( final WebElement element, final WebDriver driver )
	{

	}

	@Override
	public void afterChangeValueOf( final WebElement element, final WebDriver driver )
	{

	}

	@Override
	public void beforeSubmitOn( final WebElement element, final WebDriver driver )
	{

	}

	@Override
	public void afterSubmitOn( final WebElement element, final WebDriver driver )
	{

	}

	@Override
	public void beforeHoverOn( final WebElement element, final WebDriver driver )
	{

	}

	@Override
	public void afterHoverOn( final WebElement element, final WebDriver driver )
	{

	}

	@Override
	public void beforeHoverOn( final WebElement element, final WebDriver driver, final int xOffset, final int yOffset )
	{

	}

	@Override
	public void afterHoverOn( final WebElement element, final WebDriver driver, final int xOffset, final int yOffset )
	{

	}

	@Override
	public void beforeBlurFrom( final WebElement element, final WebDriver driver )
	{

	}

	@Override
	public void afterBlurFrom( final WebElement element, final WebDriver driver )
	{

	}

	@Override
	public void beforeFocusOn( final WebElement element, final WebDriver driver )
	{

	}

	@Override
	public void afterFocusOn( final WebElement element, final WebDriver driver )
	{

	}

	@Override
	public void beforeGetAttributeValueOf( final WebElement element, final WebDriver driver, final String attributeName )
	{

	}

	@Override
	public void beforeGetCssPropertyValueOf( final WebElement element, final WebDriver driver, final String propertyName )
	{

	}

	@Override
	public void beforeKeyboardAction( final WebDriver driver, final String action, final CharSequence sequence )
	{

	}

	@Override
	public void afterKeyboardAction( final WebDriver driver, final String action, final CharSequence sequence )
	{

	}

	@Override
	public void beforeMouseAction( final WebDriver driver, final String action, final Coordinates coordinates )
	{

	}

	@Override
	public void afterMouseAction( final WebDriver driver, final String action, final Coordinates coordinates )
	{

	}

	@Override
	public void onException( final Throwable throwable, final WebDriver driver )
	{

	}
}
