package com.framework.driver.event;

import ch.lambdaj.Lambda;
import ch.lambdaj.function.convert.Converter;
import com.framework.asserts.JAssertion;
import com.framework.config.Configurations;
import com.framework.driver.objects.RadioButton;
import com.framework.driver.utils.ui.HighlightStyle;
import com.framework.driver.utils.ui.HighlightStyleBackup;
import com.framework.testing.steping.screenshots.ScreenshotAndHtmlSource;
import com.framework.utils.datetime.Sleeper;
import com.framework.utils.string.LogStringStyle;
import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.math.NumberUtils;
import org.hamcrest.Matcher;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.internal.Coordinates;
import org.openqa.selenium.remote.RemoteWebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

import static ch.lambdaj.Lambda.*;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.driver.event
 *
 * Name   : HtmlObject 
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-02-04 
 *
 * Time   : 22:19
 *
 */

public class HtmlObject implements HtmlElement, EventTypes
{

	//region HtmlObject - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( HtmlObject.class );

	private final WebElement baseElement;

	/** the parsed {@link org.openqa.selenium.By} locator */
	private String foundBy;

	/** the element id retrieved from {@link org.openqa.selenium.remote.RemoteWebElement} */
	protected String id;

	/** the html web driver that created this object */
	protected HtmlDriver parent;

	/** a unique name for the instance */
	private final String qualifier;

	private static long counter = NumberUtils.LONG_ZERO;

	private final Deque<HighlightStyleBackup> styleBackups = new ArrayDeque<HighlightStyleBackup>();

	//endregion


	//region HtmlObject - Constructor Methods Section

	protected HtmlObject( final WebElement baseElement )
	{
		this.baseElement = baseElement;
		String tagName = baseElement.getTagName();
		this.qualifier = String.format( "ELEMENT[%s_%d]", tagName.toUpperCase(), ++ counter );
	}

	//endregion


	//region HtmlObject - WebElement classic Implementation Methods Section

	@Override
	public void click()
	{
		HtmlWebDriver drv = ( HtmlWebDriver ) getWrappedHtmlDriver();

		try
		{

			drv.fireEvent( this, EVENT_CLICK, drv.getWrappedDriver(), true );
			baseElement.click();
			drv.fireEvent( this, EVENT_CLICK, drv.getWrappedDriver(), false );
		}
		catch ( Exception e )
		{
			drv.fireError( this, EVENT_CLICK, drv.getWrappedDriver(), e );
			throw new WebDriverException( e );
		}
	}

	@Override
	public void submit()
	{
		HtmlWebDriver drv = ( HtmlWebDriver ) getWrappedHtmlDriver();
		try
		{
			drv.fireEvent( this, EVENT_SUBMIT, drv.getWrappedDriver(), true, getLocator() );
			baseElement.submit();
			drv.fireEvent( this, EVENT_SUBMIT, drv.getWrappedDriver(), false, getLocator() );
		}
		catch ( Exception e )
		{
			drv.fireError( this, EVENT_SUBMIT, drv.getWrappedDriver(), e );
			throw new WebDriverException( e );
		}
	}

	@Override
	public void clear()
	{
		HtmlWebDriver drv = ( HtmlWebDriver ) getWrappedHtmlDriver();

		try
		{
			String value = baseElement.getAttribute( "value" );
			drv.fireEvent( this, EVENT_CLEAR, drv.getWrappedDriver(), true, getLocator(), value );
			baseElement.clear();
			drv.fireEvent( this, EVENT_CLEAR, drv.getWrappedDriver(), false, getLocator() );
		}
		catch ( Exception e )
		{
			drv.fireError( this, EVENT_CLEAR, drv.getWrappedDriver(), e );
			throw new WebDriverException( e );
		}
	}

	@Override
	public String getTagName()
	{
		return baseElement.getTagName();
	}

	@Override
	public String getAttribute( final String name )
	{
		return baseElement.getAttribute( name );
	}

	@Override
	public boolean isSelected()
	{
		return baseElement.isSelected();
	}

	@Override
	public boolean isEnabled()
	{
		return baseElement.isEnabled();
	}

	@Override
	public String getText()
	{
		HtmlWebDriver drv = ( HtmlWebDriver ) getWrappedHtmlDriver();
		try
		{
			drv.fireEvent( this, EVENT_GET_TEXT, drv.getWrappedDriver(), true, getLocator() );

			String text = baseElement.getText();

			drv.fireEvent( this, EVENT_GET_TEXT, drv.getWrappedDriver(), false, text );

			return text;
		}
		catch ( Exception e )
		{
			drv.fireError( this, EVENT_GET_TEXT, drv.getWrappedDriver(), e );
			throw new WebDriverException( e );
		}
	}

	@Override
	public boolean isDisplayed()
	{
		return baseElement.isDisplayed();
	}

	@Override
	public Point getLocation()
	{
		return baseElement.getLocation();
	}

	@Override
	public Dimension getSize()
	{
		return baseElement.getSize();
	}

	@Override
	public String getCssValue( final String propertyName )
	{
		return baseElement.getCssValue( propertyName );
	}

	@Override
	public List<HtmlElement> findElements( final By by )
	{
		int type = EVENT_ELEMENT_FIND_ELEMENTS;
		HtmlWebDriver drv = ( HtmlWebDriver ) getWrappedHtmlDriver();

		try
		{
			drv.fireEvent( this, type, drv.getWrappedDriver(), true, by );
			List<WebElement> temp = baseElement.findElements( by ); // searching elements using web-driver
			drv.fireEvent( this, type, drv.getWrappedDriver(), false, by, temp.size() );

			if( temp.size() > 0 )  // converting to WebElement -> HtmlElement
			{
				List<HtmlElement> list = Lists.newArrayListWithExpectedSize( temp.size() );
				for( WebElement e : temp )
				{
					HtmlElement he = drv.createHtmlElement( e, baseElement, getWrappedHtmlDriver(), by );
					list.add( he );
				}

				return list;
			}

			// returns an empty list
			return Lists.newArrayListWithExpectedSize( 0 );
		}
		catch ( Exception e )
		{
			drv.fireError( this, type, drv.getWrappedDriver(), e );
			throw new WebDriverException( e );
		}
	}

	@Override
	public HtmlElement findElement( final By by )
	{
		int type = EVENT_ELEMENT_FIND_ELEMENT;
		HtmlWebDriver drv = ( HtmlWebDriver ) getWrappedHtmlDriver();

		try
		{
			drv.fireEvent( this, type, drv.getWrappedDriver(), true, by );
			WebElement e = baseElement.findElement( by );
			drv.fireEvent( this, type, drv.getWrappedDriver(), false, by );
			return drv.createHtmlElement( e, baseElement, getWrappedHtmlDriver(), by );
		}
		catch ( NoSuchElementException | StaleElementReferenceException e )
		{
			drv.fireError( this, type,drv.getWrappedDriver(), e );
			throw e;
		}
	}


	@Override
	public void sendKeys( final CharSequence... keysToSend )
	{
		int type = EVENT_SEND_KEYS;
		HtmlWebDriver drv = ( HtmlWebDriver ) getWrappedHtmlDriver();

		try
		{
			drv.fireEvent( this, type, drv.getWrappedDriver(), true, keysToSend );
			baseElement.sendKeys( keysToSend );
			drv.fireEvent( this, type, drv.getWrappedDriver(), false, keysToSend );
		}
		catch ( NoSuchElementException | StaleElementReferenceException e )
		{
			drv.fireError( this, type, drv.getWrappedDriver(), e );
			throw e;
		}
	}

	//endregion


	//region HtmlObject - Service Methods Section

	protected void setFoundBy( SearchContext foundFrom, By locator )
	{
		this.foundBy = String.format( "[%s] -> %s", foundFrom.getClass().getSimpleName(), locator );
	}

	protected void setId( String id )
	{
		this.id = id;
	}

	public void setParent( HtmlDriver parent )
	{
		this.parent = parent;
		//mouse = parent.getMouse();
	}

	@Override
	public String getId()
	{
		return this.id;
	}

	@Override
	public String getLocator()
	{
		return foundBy;
	}

	@Override
	public WebElement getWrappedElement()
	{
		return baseElement;
	}

	@Override
	public HtmlDriver getWrappedHtmlDriver()
	{
		return parent;
	}

	public void blink()
	{
		final String SCRIPT = "jQuery( arguments[0] ).delay( 10 ).fadeOut( 200 ).fadeIn( 150 )";
		getWrappedHtmlDriver().executeScript( SCRIPT, this );
		Sleeper.pauseFor( 200 );
	}

	@Override
	public boolean hasAttribute( final String name )
	{
		String value = baseElement.getAttribute( name );
		return ! StringUtils.isEmpty( value );
	}

	@Override
	public Coordinates getCoordinates()
	{
		return null;
	}

	@Override
	public String toString()
	{
		return new ToStringBuilder( this, LogStringStyle.LOG_LINE_STYLE )
				.append( "qualifier", qualifier )
				.append( "locator", foundBy )
				.append( "element id", id )
				.toString();
	}

	//endregion


	//region HtmlObject - Javascript Methods Section

	@Override
	public void scrollIntoView()
	{
		Point p = ( ( RemoteWebElement ) baseElement ).getCoordinates().inViewPort();
		Sleeper.pauseFor( 200 );
	}

	@Override
	public void mark( final HighlightStyle style )
	{
		style.doHighlight( getWrappedHtmlDriver(), this );
	}

	@Override
	public void scrollBy( final int xNum, final int yNum )
	{
		final String SCRIPT = "window.scrollBy( arguments[0], arguments[1] );";
		getWrappedHtmlDriver().executeScript( SCRIPT, xNum, yNum );
	}

	@Override
	public void jsDoubleClick()
	{

	}

	@Override
	public void jsClick()
	{
		HtmlWebDriver drv = ( HtmlWebDriver ) getWrappedHtmlDriver();

		try
		{
			drv.fireEvent( this, EVENT_CLICK, drv.getWrappedDriver(), true );
			final String SCRIPT = "$( arguments[0] ).trigger( \"click\" );";
			drv.executeScript( SCRIPT, baseElement );
			drv.fireEvent( this, EVENT_CLICK, getWrappedHtmlDriver().getWrappedDriver(), false );
		}
		catch ( Exception e )
		{
			drv.fireError( this, EVENT_CLICK, drv.getWrappedDriver(), e );
			throw new WebDriverException( e );
		}
	}

	//endregion


	//region HtmlObject - Conditions Methods Section

	@Override
	public Optional<HtmlElement> childExists( final By by )
	{
		try
		{
			WebDriverEventSource.setIgnoreOnError( true );
			getWrappedHtmlDriver().manage().timeouts().implicitlyWait( 0 );
			HtmlElement he = findElement( by );
			return Optional.of( he );
		}
		catch ( NoSuchElementException nsEx )
		{
			return Optional.absent();
		}
		finally
		{
			WebDriverEventSource.setIgnoreOnError( false );
			long defaultValue = Configurations.getInstance().defaultImplicitlyWait();
			getWrappedHtmlDriver().manage().timeouts().implicitlyWait( defaultValue );
		}
	}

	@Override
	public Optional<HtmlElement> childExists( final By locator, final long timeoutMillis )
	{
		try
		{
			HtmlCondition<HtmlElement> condition = ExpectedConditions.presenceOfChildBy( this, locator );
			HtmlDriverWait wait = HtmlDriverWait.wait( getWrappedHtmlDriver(), timeoutMillis );
			HtmlElement he = wait.ignoring( NoSuchElementException.class ).until( condition );
			return Optional.fromNullable( he );
		}
		catch ( TimeoutException e )
		{
			return Optional.absent();
		}
	}

	@Override
	public Optional<List<HtmlElement>> allChildrenExists( final By by )
	{
		return null;
	}

	@Override
	public Optional<List<HtmlElement>> allChildrenExists( final By by, final long timeoutSeconds )
	{
		try
		{
			HtmlCondition<List<HtmlElement>> condition = ExpectedConditions.presenceOfAllChildrenBy( this, by );
			HtmlDriverWait wait = HtmlDriverWait.wait( getWrappedHtmlDriver(), timeoutSeconds );
			List<HtmlElement> list = wait.ignoring( StaleElementReferenceException.class ).until( condition );
			return Optional.fromNullable( list );
		}
		catch ( TimeoutException e )
		{
			return Optional.absent();
		}
	}

	@Override
	public Boolean waitToBeDisplayed( final boolean visible, final long timeoutSeconds )
	{
		long sleepInMillis = ( timeoutSeconds * 1000 ) / 5;
		HtmlDriverWait wait = new HtmlDriverWait( getWrappedHtmlDriver(), timeoutSeconds, sleepInMillis );
		return wait.until( ExpectedConditions.visibilityOf( this, visible ) );
	}

	@Override
	public Boolean waitToBeEnabled( final boolean enabled, final long timeoutSeconds )
	{
		long sleepInMillis = ( timeoutSeconds * 1000 ) / 5;
		HtmlDriverWait wait = new HtmlDriverWait( getWrappedHtmlDriver(), timeoutSeconds, sleepInMillis );
		return wait.until( ExpectedConditions.elementToBeEnabled( this, enabled ) );
	}

	@Override
	public Boolean waitToBeSelected( final boolean selected, final long timeoutSeconds )
	{
		long sleepInMillis = ( timeoutSeconds * 1000 ) / 5;
		HtmlDriverWait wait = new HtmlDriverWait( getWrappedHtmlDriver(), timeoutSeconds, sleepInMillis );
		return wait.until( ExpectedConditions.elementToBeSelected( this, selected ) );
	}

	@Override
	public Boolean waitAttributeToMatch( final String attributeName, final Matcher<String> matcher, final long timeoutSeconds )
	{
		long sleepInMillis = ( timeoutSeconds * 1000 ) / 5;
		HtmlDriverWait wait = new HtmlDriverWait( getWrappedHtmlDriver(), timeoutSeconds, sleepInMillis );
		return wait.until( ExpectedConditions.elementAttributeToMatch( this, attributeName, matcher ) );
	}

	@Override
	public Boolean waitCssPropertyToMatch( final String cssProperty, final Matcher<String> matcher, final long timeoutSeconds )
	{
		long sleepInMillis = ( timeoutSeconds * 1000 ) / 5;
		HtmlDriverWait wait = new HtmlDriverWait( getWrappedHtmlDriver(), timeoutSeconds, sleepInMillis );
		return wait.until( ExpectedConditions.elementCssPropertyToMatch( this, cssProperty, matcher ) );
	}

	@Override
	public Boolean waitTextToMatch( final Matcher<String> matcher, final long timeoutSeconds )
	{
		long sleepInMillis = ( timeoutSeconds * 1000 ) / 5;
		HtmlDriverWait wait = new HtmlDriverWait( getWrappedHtmlDriver(), timeoutSeconds, sleepInMillis );
		return wait.until( ExpectedConditions.elementTextToMatch( this, matcher ) );
	}

	//endregion


	//region HtmlObject - Utilities Methods Section

	public static String joinElementsText( List<HtmlElement> elements, String delimiter )
	{
		return join( extractText( elements ), delimiter );
	}

	public static List<String> extractText( List<HtmlElement> elements )
	{
		return extract( elements, on( HtmlElement.class ).getText() );
	}

	public static List<String> extractAttribute( List<HtmlElement> elements, final String attributeName )
	{
		return Lambda.convert( elements, new Converter<HtmlElement, String>()
		{
			@Override
			public String convert( final HtmlElement from )
			{
				String value = from.getAttribute( attributeName );
				return StringUtils.removePattern( value, "(\t|\n)" );
			}
		} );
	}

	public static List<RadioButton> convertToRadioButton( List<HtmlElement> list )
	{
		Converter<HtmlElement,RadioButton> converter = new Converter<HtmlElement,RadioButton>()
		{
			@Override
			public RadioButton convert( final HtmlElement from )
			{
				return new RadioButton( from );
			}
		};

		return convert( list, converter );
	}

	//endregion


	//region HtmlObject - Mouse and Keyboard Helper Methods Section

	@Override
	public void contextClick()
	{

	}

	@Override
	public Actions createAction()
	{
		return null;
	}

	@Override
	public void doubleClick()
	{

	}

	@Override
	public void hover()
	{
		HtmlWebDriver drv = ( HtmlWebDriver ) getWrappedHtmlDriver();
		try
		{
			drv.fireEvent( this, EVENT_HOVER, drv.getWrappedDriver(), true, getLocator() );
			new Actions( drv.getWrappedDriver() ).moveToElement( getWrappedElement() ).build().perform();
			drv.fireEvent( this, EVENT_HOVER, drv.getWrappedDriver(), false, getLocator() );
		}
		catch ( Exception e )
		{
			drv.fireError( this, EVENT_HOVER,drv.getWrappedDriver(), e );
			throw new WebDriverException( e );
		}
	}

	//endregion


	@Override
	public void blur()
	{

	}

	@Override
	public Optional<ScreenshotAndHtmlSource> captureBitmap() throws IOException
	{
		return null;
	}




	@Override
	public void focus()
	{

	}


	@Override
	public JAssertion createAssertion()
	{
		return new JAssertion( getWrappedHtmlDriver() );
	}








	//endregion


	//region HtmlObject - Private Function Section

	//endregion


	// ========================================================================================== /
	// Ancestors
	// ========================================================================================== /

	public HtmlElement parent()
	{
		final String SCRIPT = "return jQuery( arguments[0] ).parent();";
		return getWrappedHtmlDriver().javascript().getHtmlElement( SCRIPT, baseElement );
	}


	//region HtmlObject - Inner Classes Implementation Section




	//endregion

}
