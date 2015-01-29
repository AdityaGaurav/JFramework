package com.framework.driver.objects;

import com.framework.driver.event.EventWebDriver;
import com.framework.driver.exceptions.WebObjectException;
import com.framework.utils.error.PreConditions;
import com.google.common.base.MoreObjects;
import org.apache.commons.lang3.math.NumberUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public abstract class AbstractWebObject implements WebObject
{

	//region AbstractWebObject - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( AbstractWebObject.class );

	private WebElement rootElement;

	private EventWebDriver objectDriver;

	/**
	 * The web object logical name
	 */
	private final String logicalName;

	private final String qualifier;

	private static long counter = NumberUtils.LONG_ZERO;

	//endregion


	//region AbstractWebObject - Constructor Methods Section

	/**
	 * creates a new base WebObject instance
	 *
	 * @param logicalName	 The web object logical name.
	 * @param rootElement	 The reference {@linkplain WebElement} root element instance.
	 *
	 * @throws IllegalStateException if {@code logicalName} is either {@code null} or empty
	 * @throws NullPointerException  if {@code wrappedDriver} is {@code null}
	 */
	public AbstractWebObject( WebDriver driver, WebElement rootElement, final String logicalName )
	{
		final String ERR_MSG1 = "WebElement root element is null.";
		final String ERR_MSG2 = "WebDriver driver is null.";
		final String ERR_MSG3 =  "logical name should is either null, blank or empty.";
		final String ERR_MSG4 = "The argument driver is not an instance of <{}>";
		this.qualifier = String.format( "OBJECT[%d]", ++ counter );
		try
		{
			this.rootElement = PreConditions.checkNotNull( rootElement, ERR_MSG1 );
			WebDriver drv = PreConditions.checkNotNull( driver, ERR_MSG2 );
			PreConditions.checkInstanceOf( EventWebDriver.class, drv, ERR_MSG4, EventWebDriver.class.getCanonicalName() );
			this.objectDriver = ( EventWebDriver ) drv;
			this.logicalName = PreConditions.checkNotNullNotBlankOrEmpty( logicalName, ERR_MSG3 );
			initWebObject();
			logger.debug( "Created new web object -> {}", toString() );
		}
		catch ( Throwable t )
		{
			logger.error( "throwing a new WebObjectException on {}#constructor.", getClass().getSimpleName() );
			WebObjectException woe = new WebObjectException( this.objectDriver.getWrappedDriver(), t.getMessage(), t );
			woe.addInfo( "causing flow", "try to create a new web object -> " + this.qualifier );
			throw woe;
		}
	}

	//endregion


	//region AbstractWebObject - Abstraction Methods Section

	/**
	 * Initializes the web object. this method is implemented on every extension class.
	 */
	protected abstract void initWebObject();

	//endregion


	//region AbstractWebObject - Service Methods Section

	public WebElement getBaseRootElement()
	{
		return rootElement;
	}

	public WebElement getBaseRootElement( By by )
	{
		try
		{
			rootElement.getTagName();
		}
		catch ( StaleElementReferenceException ex )
		{
			logger.warn( "auto recovering from StaleElementReferenceException ..." );
			rootElement = objectDriver.findElement( by );
		}
		return rootElement;
	}

	public EventWebDriver getWrappedDriver()
	{
		return objectDriver;
	}

	/**
	 * @return returns the Web Object logical name {@linkplain #logicalName}
	 */
	@Override
	public final String getLogicalName()
	{
		return logicalName;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper( this )
				.add( "logical name", getLogicalName() )
				.add( "qualifier", qualifier )
				.omitNullValues()
				.toString();
	}

	/**
	 * @return returns the generated unique identifier for the class instance {@linkplain #qualifier}
	 */
	@Override
	public String getQualifier()
	{
		return this.qualifier;
	}

	//endregion




}
