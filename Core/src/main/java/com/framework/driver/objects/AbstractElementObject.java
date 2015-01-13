package com.framework.driver.objects;

import com.framework.driver.event.EventWebDriver;
import com.framework.driver.exceptions.ElementObjectException;
import com.framework.driver.exceptions.PreConditionException;
import com.framework.driver.utils.PreConditions;
import com.google.common.base.MoreObjects;
import org.apache.commons.lang3.math.NumberUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Action;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.driver.objects
 *
 * Name   : HtmlElement
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-01-08
 *
 * Time   : 15:12
 */

public abstract class AbstractElementObject implements ElementObject
{

	//region HtmlElement - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( AbstractElementObject.class );

	private static long counter = NumberUtils.LONG_ZERO;

	private final String id;

	private final String elementId;

	protected final WebElement webElement;

	protected final EventWebDriver driver;

	private final String tagName;

	//endregion


	//region AbstractElementObject - Constructor Methods Section

	public AbstractElementObject( WebDriver driver, WebElement webElement )
	{
		final String ERR_MSG1 = "WebElement root element is null.";
		final String ERR_MSG2 = "WebDriver driver is null.";
		final String ERR_MSG3 = "The argument driver is not an instance of <{}>";
		this.id = String.format( "ELEMENT[%d]", ++ counter );

		try
		{

			WebDriver drv = PreConditions.checkNotNull( driver, ERR_MSG1 );
			this.webElement = PreConditions.checkNotNull( webElement, ERR_MSG2 );
			PreConditions.checkInstanceOf( EventWebDriver.class, drv, ERR_MSG3, EventWebDriver.class.getCanonicalName() );

			this.driver = ( EventWebDriver ) drv;
			this.elementId = this.driver.getElementId( this.webElement );
			this.tagName = this.webElement.getTagName();
			logger.debug( "Created new element object -> {}", toString() );
		}
		catch ( NullPointerException | IllegalArgumentException e )
		{
			logger.error( "throwing a new PreConditionException on AbstractElementObject#constructor." );
			throw new PreConditionException( e.getMessage(), e );
		}
		catch ( Throwable e )
		{
			logger.error( "throwing a new WebObjectException on AbstractWebObject#constructor." );
			ElementObjectException eoe = new ElementObjectException( driver, e.getMessage(), e );
			eoe.addInfo( "causing flow", "try to create a new element object -> " + this.id  );
			throw eoe;
		}
	}

	//endregion


	//region AbstractElementObject - Service Methods Section

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper( this )
				.add( "tag name", tagName )
				.add( "id", id )
				.add( "element Id", elementId )
				.omitNullValues()
				.toString();
	}

	//endregion



	//region AbstractElementObject - Implementation Methods Section

	@Override
	public void click()
	{
		webElement.click();
	}

	@Override
	public final void clear()
	{
		webElement.clear();
	}

	@Override
	public void submit()
	{
		webElement.submit();
	}

	@Override
	public void sendKeys( final CharSequence... keysToSend )
	{
		webElement.sendKeys( keysToSend );
	}

	@Override
	public String getAttribute( final String name )
	{
		return webElement.getAttribute( name );
	}

	@Override
	public String getCssValue( final String propertyName )
	{
		return webElement.getCssValue( propertyName );
	}

	@Override
	public String getText()
	{
		return webElement.getText();
	}

	@Override
	public boolean isEnabled()
	{
		return webElement.isEnabled();
	}

	@Override
	public boolean isDisplayed()
	{
		return webElement.isDisplayed();
	}

	@Override
	public boolean isSelected()
	{
		return webElement.isSelected();
	}

	@Override
	public Action createAction()
	{
		return null;
	}

	@Override
	public void blur()
	{
		driver.blur( webElement );
	}

	@Override
	public void hover()
	{
		driver.hover( webElement );
	}

	@Override
	public void focus()
	{
		driver.focus( webElement );
	}

	@Override
	public void jsClick()
	{
		driver.jsClick( webElement );
	}

	@Override
	public String getLocator()
	{
		return driver.getElementLocator( webElement );
	}

	@Override
	public boolean hasAttribute( final String name )
	{
		return driver.hasAttribute( webElement, name );
	}

	@Override
	public boolean exists( final By by )
	{
		return driver.elementExists( webElement, by );
	}

	@Override
	public String getTagName()
	{
		return webElement.getTagName();
	}

	@Override
	public List<WebElement> findElements( final By by )
	{
		return webElement.findElements( by );
	}

	@Override
	public WebElement findElement( final By by )
	{
		return webElement.findElement( by );
	}

	@Override
	public Point getLocation()
	{
		return webElement.getLocation();
	}

	@Override
	public Dimension getSize()
	{
		return webElement.getSize();
	}

	@Override
	public WebElement getWrappedElement()
	{
		return webElement;
	}

	//endregion


}
