package com.framework.driver.objects;

import com.framework.driver.event.HtmlDriver;
import com.framework.driver.event.HtmlElement;
import com.framework.utils.error.PreConditions;
import com.framework.utils.string.ToLogStringStyle;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.math.NumberUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public abstract class AbstractWebObject // implements WebObject
{

	//region AbstractWebObject - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( AbstractWebObject.class );

	private HtmlElement rootElement;

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
	public AbstractWebObject( HtmlElement rootElement, final String logicalName )
	{
		final String ERR_MSG1 = "HtmlElement root element is null.";
		final String ERR_MSG2 =  "logical name should is either null, blank or empty.";

		this.qualifier = String.format( "WEB-OBJECT[%d]", ++ counter );

		this.rootElement = PreConditions.checkNotNull( rootElement, ERR_MSG1 );
		this.logicalName = PreConditions.checkNotNullNotBlankOrEmpty( logicalName, ERR_MSG2 );
		logger.debug( "Created new web object -> {}", toString() );

	}

	//endregion


	//region AbstractWebObject - Abstraction Methods Section

	/**
	 * Initializes the web object. this method is implemented on every extension class.
	 */
	protected abstract void initWebObject();

	//endregion


	//region AbstractWebObject - Service Methods Section

	protected HtmlElement getBaseRootElement()
	{
		return rootElement;
	}

	public HtmlElement getContainer()
	{
		return rootElement;
	}

	protected HtmlElement getBaseRootElement( By by )
	{
		try
		{
			rootElement.getTagName();
		}
		catch ( StaleElementReferenceException ex )
		{
			logger.warn( "auto recovering from StaleElementReferenceException ..." );
			rootElement = getDriver().findElement( by );
		}
		return rootElement;
	}

	public HtmlDriver getDriver()
	{
		return rootElement.getWrappedHtmlDriver();
	}

	/**
	 * @return returns the Web Object logical name {@linkplain #logicalName}
	 */
	public final String getLogicalName()
	{
		return logicalName;
	}

	@Override
	public String toString()
	{
		return new ToStringBuilder( this, ToLogStringStyle.LOG_LINE_STYLE )
				.append( "logical name", logicalName )
				.append( "qualifier", qualifier )
				.toString();
	}

	public void scrollIntoView()
	{
		getBaseRootElement().scrollIntoView();
	}

	/**
	 * @return returns the generated unique identifier for the class instance {@linkplain #qualifier}
	 */
	public String getQualifier()
	{
		return this.qualifier;
	}

	//endregion




}
