package com.framework.driver.objects;

import com.framework.driver.event.ExpectedConditions;
import com.framework.driver.event.HtmlDriver;
import com.framework.driver.exceptions.ApplicationException;
import com.framework.driver.exceptions.FrameworkException;
import com.framework.driver.exceptions.PageObjectException;
import com.framework.testing.annotations.DefaultUrl;
import com.framework.utils.datetime.TimeConstants;
import com.framework.utils.error.PreConditions;
import com.framework.utils.matchers.JMatchers;
import com.framework.utils.string.ToLogStringStyle;
import com.google.common.base.Optional;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.math.NumberUtils;
import org.hamcrest.Matcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;


/**
 * All pages extends this abstract class.
 * The class provides basic functionality for all extending classes.
 *
 * it validates a URL if annotation {@link com.framework.testing.annotations.DefaultUrl} was defined
 * on extended class. if you want to skip url validation you must override {@linkplain #validatePageUrl()}
 * otherwise an {@linkplain ApplicationException} exception will thrown.
 *
 * it also contains the abstract method {@linkplain #validatePageInitialState} that extending classes should implement.
 *
 * The class stores an instance of the active <b>WebDriver</b>, accessing to instance only by {@linkplain #getDriver()} method.
 */
public abstract class AbstractPageObject implements PageObject
{
	//region AbstractPageObject - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( AbstractPageObject.class );

	/**
	 * the wrapped web driver. should not be defined final since it might change after switching.
	 */
	private HtmlDriver driver;

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
	protected AbstractPageObject( final HtmlDriver driver, final String logicalName )   //todo: remove
	{
		final String ERR_MSG =  "logical name is either null, blank or empty.";

		this.driver = PreConditions.checkNotNull( driver, "The WebDriver cannot be null" );
		this.qualifier = String.format( "PAGE[%d]", counter++ );
		this.logicalName = PreConditions.checkNotNullNotBlankOrEmpty( logicalName, ERR_MSG );
		this.currentURL = driver.getCurrentUrl();
		validatePageUrl();
	}

	//endregion


	//region AbstractPageObject - Abstraction Methods Section

	/**
	 * Initializes and assert static elements.
	 */
	protected abstract void validatePageInitialState();

	//private void validatePageInitialState();

	//endregion


	//region PageObject - Service Methods Section

	/**
	 * Validates the extending page url.
	 * The validation is determined by the {@link DefaultUrl} annotation.
	 * the url text is validated by {@link DefaultUrl#value()} and the DefaultUrl#matcher()} attribute.
	 * if no matcher provided an {@code ApplicationException} is thrown.
	 *
	 * @throws ApplicationException if no
	 */
	protected void validatePageUrl()
	{
		Optional<DefaultUrl> annotation = Optional.fromNullable( getClass().getAnnotation( DefaultUrl.class ) );
		if( annotation.isPresent() )
		{
			Matcher<String> matcher = null;
			if( annotation.get().matcher().equalsIgnoreCase( "contains()" ) ||
					annotation.get().matcher().equalsIgnoreCase( "containsString()" ) 	)
			{
				matcher = JMatchers.containsString( annotation.get().value().toLowerCase() );
			}
			if( annotation.get().matcher().equalsIgnoreCase( "equalToIgnoringCase()" ) )
			{
				matcher = JMatchers.equalToIgnoringCase( annotation.get().value() );
			}
			if( annotation.get().matcher().equalsIgnoreCase( "equalToIgnoringWhiteSpace()" ) )
			{
				matcher = JMatchers.equalToIgnoringWhiteSpace( annotation.get().value() );
			}
			else if( annotation.get().matcher().equalsIgnoreCase( "endswith()" ) )
			{
				matcher = JMatchers.endsWith( annotation.get().value().toLowerCase() );
			}
			else if( annotation.get().matcher().equalsIgnoreCase( "startswith()" ) )
			{
				matcher = JMatchers.startsWith( annotation.get().value().toLowerCase() );
			}
			else if( annotation.get().matcher().equalsIgnoreCase( "containsPattern()" ) )
			{
				matcher = JMatchers.containsPattern( annotation.get().value() );
			}
			else if( annotation.get().matcher().equalsIgnoreCase( "matchesPattern()" ) )
			{
				matcher = JMatchers.matchesPattern( annotation.get().value() );
			}

			if( null != matcher )
			{
				driver.assertWaitThat( "Validate page url", TimeConstants.ONE_MINUTE, ExpectedConditions.urlMatches( matcher ) );
			}
			else
			{
				throw new PageObjectException( new FrameworkException( "No url validation was made on page" ) );
			}
		}
	}


	@Override
	public final String getLogicalName()
	{
		return logicalName;
	}

	/**
	 * @return the next index to be applied.
	 */
	protected static long getIndex()
	{
		return counter;
	}

	@Override
	public final String getQualifier()
	{
		return this.qualifier;
	}

	/**
	 * @return an instance of the active driver.
	 */
	protected HtmlDriver getDriver()
	{
		return driver;
	}

	//endregion


	//region AbstractPageObject - Implementation Methods Section

	@Override
	public final String getCurrentUrl()
	{
		return getDriver().getCurrentUrl();
	}

	@Override
	public URL getURL() throws MalformedURLException
	{
		return new URL( getCurrentUrl() );
	}

	@Override
	public final String getTitle()
	{
		return getDriver().getTitle();
	}

	@Override
	public void close()
	{
		getDriver().close();
	}

	/**
	 * {@inheritDoc}
	 * execute a jQuery script on client browser using the functions scrollTop with a <i>fast</i> animation
	 *
	 * @throws org.openqa.selenium.WebDriverException if jQuery is not supported on client page.
	 *
	 * @see <a href="http://api.jquery.com/animate/">animate</a>
	 * @see <a href="http://api.jquery.com/scrolltop/#scrollTop2">scrollTop</a>
	 */
	@Override
	public final void scrollToTop()
	{
		final String SCRIPT = "jQuery('html, body').animate({ scrollTop: 0 }, 'fast');";
		 getDriver().executeScript( SCRIPT );
	}

	/**
	 * {@inheritDoc}
	 * @param x   x-coord is the pixel along the horizontal axis of the document that you want displayed in the upper left
	 * @param y   y-coord is the pixel along the vertical axis of the document that you want displayed in the upper left.
	 *
	 */
	@Override
	public void scrollTo( final long x, final long y )
	{
		final String SCRIPT = "window.scrollTo( arguments[0],arguments[1] )";
		 getDriver().executeScript( SCRIPT, x, y );
	}

	/**
	 * {@inheritDoc}
	 * executes the{@code window.focus()} javascript on client.
	 */
	@Override
	public void setWindowFocus()
	{
		getDriver().executeScript( "window.focus()" );
	}

	/**
	 * {@inheritDoc}
	 * the function provided functionality to tests that cannot access web driver.
	 */
	@Override
	public final String getWindowHandle()
	{
		return getDriver().getWindowHandle();
	}

	/**
	 * {@inheritDoc}
	 * the function provided functionality to tests that cannot access web driver.
	 */
	@Override
	public Set<String> getWindowHandles()
	{
		return getDriver().getWindowHandles();
	}

	@Override
	public final HtmlDriver.TargetLocator switchTo()
	{
		return getDriver().switchTo();
	}

	@Override
	public final HtmlDriver.Navigation navigate()
	{
		return getDriver().navigate();
	}

	@Override
	public final HtmlDriver.Options manage()
	{
		return getDriver().manage();
	}

	@Override
	public final HtmlDriver.Window window()
	{
		return getDriver().manage().window();
	}

	@Override
	public HtmlDriver.History history()
	{
		return getDriver().navigate().history();
	}

	@Override
	public HtmlDriver.SessionStorage sessionStorage()
	{
		return getDriver().sessionStorage();
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
		return new ToStringBuilder( this, ToLogStringStyle.LOG_LINE_STYLE )
				.append( "qualifier", qualifier )
				.append( "name", logicalName )
				.append( "current url", currentURL )
				.toString();
	}

	//endregion

}
