package com.framework.driver.objects;

import com.framework.asserts.JAssertion;
import com.framework.driver.event.EventWebDriver;
import com.framework.testing.annotations.DefaultUrl;
import com.framework.utils.error.PreConditions;
import com.framework.utils.matchers.JMatchers;
import com.framework.utils.string.LogStringStyle;
import com.google.common.base.Optional;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.math.NumberUtils;
import org.hamcrest.Matcher;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;


public abstract class AbstractPageObject implements PageObject
{
	//region AbstractPageObject - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( AbstractPageObject.class );

	/**
	 * the wrapped web driver. should not be defined final since it might change after switching.
	 */
	private EventWebDriver pageDriver;

	/**
	 * the logical of the extending page;
	 */
	private final String logicalName;

	/**
	 * the qualifier name of the extending page ( includes the index );
	 */
	private final String qualifier;

	/**
	 * a static counter for indexing page objects.
	 */
	private static long counter = NumberUtils.LONG_ZERO;

	/**
	 * the extending object page current url
	 */
	private final String currentURL;

	//endregion


	//region AbstractPageObject - Constructor Methods Section

	/**
	 * creates a new base WebObject instance
	 *
	 * @param logicalName	 Creates a Page object based on PageObject design pattern. all pages must extend this class
	 * @param driver		 The reference {@linkplain org.openqa.selenium.WebDriver} instance.
	 *
	 * @throws IllegalStateException if {@code logicalName} is either {@code null} or empty
	 * @throws NullPointerException  if {@code wrappedDriver} is {@code null}
	 */
	protected AbstractPageObject( final WebDriver driver, final String logicalName )
	{
		final String ERR_MSG1 = "WebDriver argument cannot be null.";
		final String ERR_MSG2 =  "logical name is either null, blank or empty.";
		final String ERR_MSG3 = "The argument driver is not an instance of <{}>";
		WebDriver drv = PreConditions.checkNotNull( driver, ERR_MSG1 );
		PreConditions.checkInstanceOf( EventWebDriver.class, drv, ERR_MSG3, EventWebDriver.class.getCanonicalName() );
		this.qualifier = String.format( "PAGE[%d]", counter++ );
		this.logicalName = PreConditions.checkNotNullNotBlankOrEmpty( logicalName, ERR_MSG2 );
		this.pageDriver = ( EventWebDriver ) drv;
		this.currentURL = pageDriver.getCurrentUrl();
	}

	//endregion


	//region AbstractPageObject - Abstraction Methods Section

	/**
	 * Initializes and assert static elements.
	 */
	protected abstract void initElements();

	//endregion


	//region PageObject - Service Methods Section

	protected void validatePageUrl()
	{
		Optional<DefaultUrl> annotation = Optional.fromNullable( getClass().getAnnotation( DefaultUrl.class ) );
		if( annotation.isPresent() )
		{
			Matcher<String> matcher = null;
			if( annotation.get().matcher().toLowerCase().contains( "contains" ) )
			{
				matcher = JMatchers.containsString( annotation.get().value() );
			}
			if( annotation.get().matcher().toLowerCase().contains( "endswith" ) )
			{
				matcher = JMatchers.endsWith( annotation.get().value() );
			}
			if( annotation.get().matcher().toLowerCase().contains( "startswith" ) )
			{
				matcher = JMatchers.startsWith( annotation.get().value() );
			}
			if( null != matcher )
			{
				new JAssertion( pageDriver ).assertThat( "Validate page url", getCurrentUrl(), matcher );
			}
		}
	}

	@Override
	public final String getLogicalName()
	{
		return logicalName;
	}

	protected static long getIndex()
	{
		return counter;
	}

	@Override
	public final String getQualifier()
	{
		return this.qualifier;
	}

	public EventWebDriver getWrappedDriver()
	{
		return pageDriver;
	}

	//endregion


	//region AbstractPageObject - Implementation Methods Section

	@Override
	public final String getCurrentUrl()
	{
		return pageDriver.getCurrentUrl();
	}

	@Override
	public final String getTitle()
	{
		return pageDriver.getTitle();
	}

	@Override
	public final void scrollToTop()
	{
		pageDriver.executeScript( "jQuery('html, body').animate({ scrollTop: 0 }, 'fast');" );
	}

	@Override
	public void scrollTo( final long x, final long y )
	{
		pageDriver.executeScript( "window.scrollTo( arguments[0],arguments[1] )", x,y );
	}

	@Override
	public void setWindowFocus()
	{
		pageDriver.executeScript( "window.focus()" );
	}

	@Override
	public final String getWindowHandle()
	{
		return pageDriver.getWindowHandle();
	}

	@Override
	public Set<String> getWindowHandles()
	{
		return pageDriver.getWindowHandles();
	}

	@Override
	public final WebDriver.TargetLocator switchTo()
	{
		return pageDriver.switchTo();
	}

	@Override
	public final WebDriver.Navigation navigate()
	{
		return pageDriver.navigate();
	}

	@Override
	public final WebDriver.Options manage()
	{
		return pageDriver.manage();
	}

	@Override
	public final WebDriver.Window window()
	{
		return pageDriver.manage().window();
	}

	//endregion


	//region AbstractPageObject - Object Override Methods Section

	@SuppressWarnings ( "SimplifiableIfStatement" )
	@Override
	public boolean equals( final Object o )
	{
		if ( this == o )
		{
			return true;
		}
		if ( ! ( o instanceof AbstractPageObject ) )
		{
			return false;
		}

		final AbstractPageObject that = ( AbstractPageObject ) o;

		if ( ! qualifier.equals( that.qualifier ) )
		{
			return false;
		}
		return logicalName.equals( that.logicalName );

	}

	@Override
	public int hashCode()
	{
		int result = logicalName.hashCode();
		result = 31 * result + qualifier.hashCode();
		return result;
	}

	@Override
	public String toString()
	{
		return new ToStringBuilder( this, LogStringStyle.LOG_LINE_STYLE )
				.append( "qualifier", qualifier )
				.append( "name", logicalName )
				.append( "current url", currentURL )
				.toString();
	}

	//endregion

}
