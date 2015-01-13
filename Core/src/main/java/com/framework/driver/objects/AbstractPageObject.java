package com.framework.driver.objects;

import com.framework.driver.event.EventWebDriver;
import com.framework.driver.exceptions.PageObjectException;
import com.framework.driver.exceptions.PreConditionException;
import com.framework.driver.utils.PreConditions;
import com.google.common.base.MoreObjects;
import org.apache.commons.lang3.math.NumberUtils;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.OutputStreamWriter;
import java.util.Set;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.driver.objects
 *
 * Name   : AbstractPageObject
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-01-05
 *
 * Time   : 18:40
 */

public abstract class AbstractPageObject implements PageObject
{

	//region AbstractPageObject - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( AbstractPageObject.class );

	/**
	 * the wrapped web driver. should not be defined final since it might change after switching.
	 */
	protected EventWebDriver pageDriver;

	/**
	 * the logical of the extending page;
	 */
	private final String logicalName;

	/**
	 * the id of the extending page ( includes the index );
	 */
	private final String id;

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
	public AbstractPageObject( final String logicalName, final WebDriver driver )
	{
		final String ERR_MSG1 = "WebDriver argument is null.";
		final String ERR_MSG2 =  "logical name is either null, blank or empty.";
		final String ERR_MSG4 = "The argument driver is not an instance of <{}>";
		this.id = String.format( "PAGE[%d]", counter++ );
		try
		{
			this.logicalName = PreConditions.checkNotNullNotBlankOrEmpty( logicalName, ERR_MSG2 );
			WebDriver drv = PreConditions.checkNotNull( driver, ERR_MSG1 );
			PreConditions.checkInstanceOf( EventWebDriver.class, drv, ERR_MSG4, EventWebDriver.class.getCanonicalName() );
			this.pageDriver = ( EventWebDriver ) drv;
			currentURL = pageDriver.getCurrentUrl();
		}
		catch ( NullPointerException | IllegalArgumentException e )
		{
			logger.error( "throwing a new PreConditionException on {}#constructor.", getClass().getSimpleName() );
			throw new PreConditionException( e.getMessage(), e );
		}
		catch ( Throwable e )
		{
			logger.error( "throwing a new PageObjectException on {}#constructor.", getClass().getSimpleName() );
			PageObjectException poe = new PageObjectException( driver, e.getMessage(), e );
			poe.addInfo( "causing flow", "trying to create a new PageObject -> " + this.id );
			throw poe;
		}
	}

	//endregion


	//region AbstractPageObject - Abstraction Methods Section

	/**
	 * Initializes and assert static elements.
	 */
	protected abstract void initElements();

	//endregion


	//region PageObject - Service Methods Section

	@Override
	public final String getLogicalName()
	{
		return logicalName;
	}

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

		if ( ! id.equals( that.id ) )
		{
			return false;
		}
		if ( ! logicalName.equals( that.logicalName ) )
		{
			return false;
		}

		return true;
	}

	@Override
	public int hashCode()
	{
		int result = logicalName.hashCode();
		result = 31 * result + id.hashCode();
		return result;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper( this )
				.add( "id", getId() )
				.add( "name", getLogicalName() )
				.add( "url", currentURL )
				.add( "title", getTitle() )
				.omitNullValues()
				.toString();
	}

	protected static long getIndex()
	{
		return counter;
	}

	@Override
	public final String getId()
	{
		return this.id;
	}

	//endregion


	//region AbstractPageObject - Implementation Methods Section

	@Override
	public final void savePageSource( final OutputStreamWriter wr )
	{

	}

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

}
