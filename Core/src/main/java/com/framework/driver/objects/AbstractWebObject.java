package com.framework.driver.objects;

import com.framework.driver.event.EventWebDriver;
import com.framework.driver.exceptions.PreConditionException;
import com.framework.driver.exceptions.WebObjectException;
import com.framework.driver.utils.PreConditions;
import com.google.common.base.MoreObjects;
import org.apache.commons.lang3.math.NumberUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public abstract class AbstractWebObject implements WebObject
{

	//region AbstractWebObject - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( AbstractWebObject.class );

	protected WebElement rootElement;

	protected EventWebDriver objectDriver;

	/**
	 * The web object logical name
	 */
	private final String logicalName;

	private final String id;

	private static long counter = NumberUtils.LONG_ZERO;

	//endregion


	//region AbstractWebObject - Constructor Methods Section

	/**
	 * creates a new base WebObject instance
	 *
	 * @param logicalName	 The web object logical name.
	 * @param rootElement	 The reference {@linkplain org.openqa.selenium.WebElement} root element instance.
	 *
	 * @throws IllegalStateException if {@code logicalName} is either {@code null} or empty
	 * @throws NullPointerException  if {@code wrappedDriver} is {@code null}
	 */
	public AbstractWebObject( final String logicalName, WebDriver driver, WebElement rootElement )
	{
		final String ERR_MSG1 = "WebElement root element is null.";
		final String ERR_MSG2 = "WebDriver driver is null.";
		final String ERR_MSG3 =  "logical name should is either null, blank or empty.";
		final String ERR_MSG4 = "The argument driver is not an instance of <{}>";
		this.id = String.format( "OBJECT[%d]", ++ counter );

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
		catch ( NullPointerException | IllegalArgumentException e )
		{
			logger.error( "throwing a new PreConditionException on {}#constructor.", getClass().getSimpleName() );
			throw new PreConditionException( e.getMessage(), e );
		}
		catch ( Throwable e )
		{
			logger.error( "throwing a new WebObjectException on {}#constructor.", getClass().getSimpleName() );
			WebObjectException woe = new WebObjectException( this.objectDriver.getWrappedDriver(), e.getMessage(), e );
			woe.addInfo( "causing flow", "try to create a new web object -> " + this.id );
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
				.add( "logicalName", getLogicalName() )
				.add( "id", getId() )
				.omitNullValues()
				.toString();
	}

	/**
	 * @return returns the generated unique identifier for the class instance {@linkplain #id}
	 */
	@Override
	public String getId()
	{
		return this.id;
	}

	@Override
	public boolean equals( final Object o )
	{
		if ( this == o )
		{
			return true;
		}
		if ( ! ( o instanceof AbstractWebObject ) )
		{
			return false;
		}

		final AbstractWebObject that = ( AbstractWebObject ) o;

		return id.equals( that.id ) && logicalName.equals( that.logicalName );

	}

	@Override
	public int hashCode()
	{
		int result = logicalName.hashCode();
		result = 31 * result + id.hashCode();
		return result;
	}

	//endregion




}
