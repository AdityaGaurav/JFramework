package com.framework.driver.objects;

import ch.lambdaj.Lambda;
import ch.lambdaj.function.convert.Converter;
import com.framework.asserts.CheckpointAssert;
import com.framework.asserts.JAssertion;
import com.framework.driver.event.EventWebDriver;
import com.framework.driver.event.WrapsEventDriver;
import com.framework.driver.exceptions.ElementObjectException;
import com.framework.driver.utils.ui.HighlightStyle;
import com.framework.utils.error.PreConditionException;
import com.framework.utils.error.PreConditions;
import com.framework.utils.string.LogStringStyle;
import com.google.common.base.Throwables;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.math.NumberUtils;
import org.hamcrest.Matcher;
import org.openqa.selenium.*;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import static ch.lambdaj.Lambda.*;


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

public class BaseElementObject implements ElementObject
{

	//region BaseElementObject - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( BaseElementObject.class );

	private static long counter = NumberUtils.LONG_ZERO;

	private final String id;

	private final String qualifier;

	private final WebElement baseElement;

	protected EventWebDriver driver;

	private final String tagName;

	//endregion


	//region BaseElementObject - Constructor Methods Section

	protected BaseElementObject( WebElement webElement )
	{
		final String ERR_MSG1 = "WebDriver driver cannot null.";
		this.qualifier = String.format( "ELEMENT[%d]", ++ counter );

		try
		{
			this.baseElement = PreConditions.checkNotNull( webElement, ERR_MSG1 );
			this.driver = getWrapperEventDriver();
			this.id = this.driver.getElementId( this.baseElement );
			this.tagName = this.baseElement.getTagName();
		}
		catch ( Throwable t )
		{
			Throwables.propagateIfInstanceOf( t, PreConditionException.class );
			logger.error( "throwing a new WebObjectException on {}#constructor.", getClass().getName() );
			throw  new ElementObjectException( t );
		}
	}

	//endregion


	//region BaseElementObject - Getters Methods Section

	public String getId()
	{
		return id;
	}

	public String getQualifier()
	{
		return qualifier;
	}

	@Override
	public String getTagName()
	{
		return tagName;
	}

	@Override
	public WebElement getWrappedElement()
	{
		return baseElement;
	}

	protected WebDriver getWrapperDriver()
	{
		EventWebDriver ewe =  getWrapperEventDriver();
		return ewe.getWrappedDriver();
	}

	protected EventWebDriver getWrapperEventDriver()
	{
		return (  ( WrapsEventDriver ) baseElement ).getWrappedEventDriver();
	}

	//endregion


	//region BaseElementObject - WebElement Implementation Section

	@Override
	public String getAttribute( final String name )
	{
		return baseElement.getAttribute( name );
	}

	@Override
	public String getCssValue( final String propertyName )
	{
		return baseElement.getCssValue( propertyName );
	}

	@Override
	public String getText()
	{
		return baseElement.getText();
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
	public boolean isEnabled()
	{
		return baseElement.isEnabled();
	}

	@Override
	public boolean isDisplayed()
	{
		return baseElement.isDisplayed();
	}

	@Override
	public boolean isSelected()
	{
		return baseElement.isSelected();
	}

	@Override
	public List<WebElement> findElements( final By by )
	{
		return baseElement.findElements( by );
	}

	@Override
	public WebElement findElement( final By by )
	{
		return baseElement.findElement( by );
	}

	@Override
	public void click()
	{
		baseElement.click();
	}

	@Override
	public final void clear()
	{
		baseElement.clear();
	}

	@Override
	public void submit()
	{
		baseElement.submit();
	}

	@Override
	public void sendKeys( final CharSequence... keysToSend )
	{
		baseElement.sendKeys( keysToSend );
	}

	//endregion


	//region BaseElementObject - ElementObject Implementation Section

	@Override
	public Actions createAction()
	{
		return new Actions( getWrapperDriver() );
	}

	@Override
	public <T> void assertThat( final String reason, final T actual, final Matcher<? super T> matcher )
	{
		JAssertion assertion = new JAssertion( getWrapperEventDriver() );
		assertion.assertThat( reason, actual, matcher);
	}

	@Override
	public <T> void assertWaitThat( final String reason, final long timeout, final ExpectedCondition<?> condition )
	{
		JAssertion assertion = new JAssertion( getWrapperEventDriver() );
		assertion.assertWaitThat( reason, timeout, condition);
	}

	@Override
	public void blur()
	{
		final String SCRIPT = "jQuery( arguments[0] ).blur();";
		driver.executeScript( SCRIPT, baseElement );
	}

	@Override
	public File captureBitmap() throws IOException
	{
		final int AREA_OFFSET = 20;

		// Get the entire Screenshot from the driver of passed WebElement
		File screen = driver.getScreenshotAs( OutputType.FILE );

		// Create an instance of Buffered Image from captured screenshot
		BufferedImage img = ImageIO.read( screen );

		// Get the Width and Height of the WebElement using
		int width = baseElement.getSize().getWidth() + AREA_OFFSET;
		int height = baseElement.getSize().getHeight() + AREA_OFFSET;

		// Create a rectangle using Width and Height
		Rectangle rect = new Rectangle( width, height );

		/**
		 *   Get the Location of WebElement in a Point.
		 *   This will provide X & Y co-ordinates of the WebElement
		 */
		org.openqa.selenium.Point p = baseElement.getLocation();

		/**
		 * Create image by for element using its location and size.
		 * This will give image data specific to the WebElement
		 */
		int x = p.getX() - AREA_OFFSET;
		int y = p.getY() - AREA_OFFSET;
		BufferedImage destination = img.getSubimage( x, y, rect.width, rect.height );

		// Write back the image data for element in File object
		ImageIO.write( destination, "png", screen );

		// Return the File object containing image data
		return screen;
	}

	@Override
	public <T> CheckpointAssert checkPoint( final String id, final String reason, final T actual, final Matcher<? super T> matcher )
	{
		CheckpointAssert checkPoint = new CheckpointAssert( id );
		checkPoint.assertThat( reason, actual, matcher);
		return checkPoint;
	}

	@Override
	public void contextClick()
	{
		createAction().contextClick( baseElement ).perform();
	}

	@Override
	public void doubleClick()
	{
		createAction().doubleClick( baseElement ).perform();
	}

	@Override
	public boolean exists( final By by )
	{
		return getWrapperEventDriver().elementExists( baseElement, by );
	}

	@Override
	public void scrollIntoView()
	{
		final String SCRIPT = "jQuery('html, body').animate({" +
				"scrollTop: arguments[0]," +
				"scrollLeft: arguments[1]" +
				"});";

		long offsetLeft = Long.valueOf( baseElement.getAttribute( "offsetLeft" ) );   // 10
		long offsetTop = Long.valueOf( baseElement.getAttribute( "offsetTop" ) );   // 2
		driver.executeScript( SCRIPT, offsetLeft - 20, offsetTop - 20 );
	}

	@Override
	public void focus()
	{
		final String SCRIPT = "jQuery( arguments[0] ).focus();";
		driver.executeScript( SCRIPT, baseElement );
	}

	@Override
	public String getLocator()
	{
		int index = baseElement.toString().indexOf( "->" );
		return StringUtils.substring( baseElement.toString(), index + 3 ).replace( "]", StringUtils.EMPTY );
	}

	@Override
	public boolean hasAttribute( final String name )
	{
		return ! StringUtils.isEmpty( baseElement.getAttribute( name ) );
	}

	@Override
	public void hover()
	{
		final String SCRIPT = "jQuery( arguments[0] ).hover();";
		driver.executeScript( SCRIPT, baseElement );
	}

	@Override
	public void jsDoubleClick()
	{
		final String SCRIPT = "jQuery( arguments[0] ).dblclick();";
		driver.executeScript( SCRIPT, baseElement );
	}

	@Override
	public void jsClick()
	{
		final String SCRIPT = "jQuery( arguments[0] ).click();";
		driver.executeScript( SCRIPT, baseElement );
	}

	@Override
	public void select()
	{
		final String SCRIPT = "jQuery( arguments[0] ).select();";
		driver.executeScript( SCRIPT, baseElement );
	}

	@Override
	public <T> CheckpointAssert waitCheckPoint( final String id, final String reason, final long timeout, final ExpectedCondition<?> condition )
	{
		CheckpointAssert checkPoint = new CheckpointAssert( id );
		checkPoint.assertWaitThat( reason, timeout, condition );
		return checkPoint;
	}

	//endregion


	//region BaseElementObject - Service Methods Section

	@Override
	public String toString()
	{
		return new ToStringBuilder( this, LogStringStyle.LOG_LINE_STYLE )
				.append( "tagName", tagName )
				.append( "qualifier", qualifier )
				.append( "id", id )
				.toString();
	}


	//endregion


	//region BaseElementObject - Utility Methods Section

	public static void dump( WebElement e )
	{
		ToStringBuilder tsb = new ToStringBuilder( e, LogStringStyle.MULTI_LINE_STYLE )
				.append( "tag name", e.getTagName() )
				.append( "text", e.getText() )
				.append( "class", e.getAttribute( "class" ) )
				.append( "is displayed", e.isDisplayed() )
				.append( "is enabled", e.isEnabled() )
				.append( "is selected", e.isSelected() )
				.append( "href", e.getAttribute( "href" ) )
				.append( "value", e.getAttribute( "value" ) )
				.append( "type", e.getAttribute( "type" ) )
				.append( "textContent", e.getAttribute( "textContent" ) )
				.append( "location", e.getLocation() )
				.append( "dimension", e.getSize() );

		logger.info( tsb.toString() );
	}

	public static void blink( EventWebDriver driver, WebElement e )
	{
		final String SCRIPT = "jQuery( arguments[0] ).delay( 100 ).fadeOut( 200 ).fadeIn( 150 ).fadeOut( 150 ).fadeIn( 200 )";
		driver.executeScript( SCRIPT );
	}

	public static void mark( final EventWebDriver driver, final WebElement element )
	{
		if( null == driver || null == element ) return;

		HighlightStyle.ELEMENT_STYLES[ 0 ].doHighlight( driver, element );
	}

	public static List<String> extractText( List<? extends WebElement> elements )
	{
		return extract( elements, on( WebElement.class ).getText() );
	}

	public static List<String> extractTagNames( List<? extends WebElement> elements )
	{
		return extract( elements, on( WebElement.class ).getTagName() );
	}

	public static List<Boolean> extractVisibility( List<? extends WebElement> elements )
	{
		return extract( elements, on( WebElement.class ).isDisplayed() );
	}

	public static List<String> extractAttribute( List<? extends WebElement> elements, String attributeName )
	{
		return extract( elements, on( WebElement.class ).getAttribute( attributeName ) );
	}

	public static String joinElementsText( List<WebElement> elements, String delimiter )
	{
		return join( extractText( elements ), delimiter );
	}

	public static List<? extends WebElement> selectMatchingText( List<? extends WebElement> elements, Matcher<String> matcher )
	{
		return Lambda.select( elements, having( on( WebElement.class ).getText(), matcher ) );
	}

	public static List<? extends WebElement> selectMatchingAttributeValue(
			List<? extends WebElement> elements, String attributeName, Matcher<String> matcher )
	{
		return Lambda.select( elements, having( on( WebElement.class ).getAttribute( attributeName ), matcher ) );
	}

	public static List<Link> convertToLink( List<WebElement> list )
	{
		Converter<WebElement,Link> converter = new Converter<WebElement,Link>()
		{
			@Override
			public Link convert( final WebElement from )
			{
				return new Link( from );
			}
		};

		return convert( list, converter );
	}

	public static List<RadioButton> convertToRadioButton( List<WebElement> list )
	{
		Converter<WebElement,RadioButton> converter = new Converter<WebElement,RadioButton>()
		{
			@Override
			public RadioButton convert( final WebElement from )
			{
				return new RadioButton( from );
			}
		};

		return convert( list, converter );
	}




	//endregion


	//region BaseEventWebElement - jQuery Traversing Methods Section // todo: experimental!!

	// ========================================================================================== /
	// Siblings
	// ========================================================================================== /

	/**
	 * Get the immediately following sibling of each element in the set of matched elements.
	 * If a selector is provided, it retrieves the next sibling only if it matches that selector.
	 * <pre>
	 *      WebElement e = webElement.next( null );         // will return the next sibling
	 *      WebElement e = webElement.next( ".selected" );  // will return the next sibling with class <b>.selected</b>
	 * </pre>
	 *
	 * @param jQuerySelector an optional qualified jQuery selector, {@code null} for none.
	 *
	 * @return a {@code WebElement} matching searching criteria
	 *
	 * @throws org.openqa.selenium.NoSuchElementException if not found.
	 */
	private WebElement next( String jQuerySelector )
	{
		final String SCRIPT = String.format( "jQuery( arguments[0] ).next(%s);", jQuerySelector );
		return driver.getJavaScriptSupport().getWebElement( SCRIPT, baseElement );
	}

	/**
	 * Get all following siblings of each element in the set of matched elements, optionally filtered by a selector.
	 * <pre>
	 *       List<WebElement> l = webElement.nextAll();
	 *       List<WebElement> l = webElement.nextAll( "p" )
	 * </pre>
	 * @param jQuerySelector A string containing a selector expression to match elements against.
	 *
	 * @return a {@code WebElement} list matching search criteria, or an empty list.
	 */
	private List<WebElement> nextAll( String jQuerySelector )
	{
		final String SCRIPT = String.format( "jQuery( arguments[0] ).nextAll(%s);", jQuerySelector );
		return driver.getJavaScriptSupport().getWebElements( SCRIPT, baseElement );
	}

	/**
	 * Get all following siblings of each element up to but not including the element matched by the selector,
	 * DOM node, or jQuery object passed.
	 * <pre>
	 *      List<WebElement> l = webElement.nextUntil( "ul" )
	 * </pre>
	 *
	 * @param jQuerySelector  A string containing a selector expression to indicate
	 *                        where to stop matching following sibling elements.
	 * @param filter          A string containing a selector expression to match elements against.
	 *
	 * @return a {@code WebElement} list matching search criteria, or an empty list.
	 */
	private List<WebElement> nextUntil( String jQuerySelector, String filter )
	{
		if( jQuerySelector != null )
		{
			if( filter != null )
			{
				jQuerySelector += ",";
			}
		}

		final String SCRIPT = String.format( "jQuery( arguments[0] ).nextUntil(%s%s);", jQuerySelector, filter );
		return driver.getJavaScriptSupport().getWebElements( SCRIPT, baseElement );
	}

	private WebElement prev()
	{
		final String SCRIPT = "jQuery( arguments[0] ).prev();";
		return driver.getJavaScriptSupport().getWebElement( SCRIPT, baseElement );
	}

	private List<WebElement> prevUntil( String jQuerySelector )
	{
		final String SCRIPT = "jQuery( arguments[0] ).prevUntil( arguments[1] );";
		return driver.getJavaScriptSupport().getWebElements( SCRIPT, baseElement, jQuerySelector );
	}

	public List<WebElement> prevAll()
	{
		final String SCRIPT = "jQuery( arguments[0] ).prevAll();";
		return driver.getJavaScriptSupport().getWebElements( SCRIPT, baseElement );
	}

	// ========================================================================================== /
	// Ancestors
	// ========================================================================================== /

	private WebElement parent()
	{
		final String SCRIPT = "jQuery( arguments[0] ).parent();";
		return driver.getJavaScriptSupport().getWebElement( SCRIPT, baseElement );
	}

	private List<WebElement> parents()
	{
		final String SCRIPT = "jQuery( arguments[0] ).parents();";
		return driver.getJavaScriptSupport().getWebElements( SCRIPT, baseElement );
	}

	private List<WebElement> parentsUntil( String jQuerySelector )
	{
		final String SCRIPT = "jQuery( arguments[0] ).parentsUntil( arguments[1] );";
		return driver.getJavaScriptSupport().getWebElements( SCRIPT, baseElement, jQuerySelector );
	}

	// ========================================================================================== /
	// Descendants
	// ========================================================================================== /

	private List<WebElement> children()
	{
		final String SCRIPT = "jQuery( arguments[0] ).children();";
		return driver.getJavaScriptSupport().getWebElements( SCRIPT, baseElement );
	}

	//endregion


}
