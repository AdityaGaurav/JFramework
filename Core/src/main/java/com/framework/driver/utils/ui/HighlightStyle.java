package com.framework.driver.utils.ui;

import org.openqa.selenium.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: org.framework.webdriver.driver.highlight
 *
 * Name   : HighlightStyle
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2014-12-21
 *
 * Time   : 17:29
 */

public class HighlightStyle
{

	//region HighlightStyle - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( HighlightStyle.class );

	/**
	 * element styles.
	 */
	public static HighlightStyle[] ELEMENT_STYLES = new HighlightStyle[] {
			new HighlightStyle( "backgroundColor: yellow", "outline: #8f8 solid 2px" ),
			new HighlightStyle( "backgroundColor: orange", "outline: #484 solid 2px" ),
			new HighlightStyle( "backgroundColor: #00CCFF", "outline: #330066 solid 2px" )
	};

	private static final String SCRIPT = "return (function(element, hlStyle) {\n"
			+ "  var backup = {};\n"
			+ "  var style = element.style;\n"
			+ "  for (var key in hlStyle) {\n"
			+ "    backup[key] = style[key];\n"
			+ "    style[key] = hlStyle[key];\n"
			+ "  }\n"
			+ "  return backup;\n"
			+ "}).apply(window, arguments);";

	private final Map<String, String> styles;

	//endregion


	//region HighlightStyle - Constructor Methods Section

	/**
	 * Constructor.
	 *
	 * @param styles style for highlighting.
	 */
	public HighlightStyle( Map<String, String> styles )
	{
		this.styles = styles;
	}

	/**
	 * Constructor.
	 *
	 * @param styles style for highlighting.
	 */
	public HighlightStyle( String... styles )
	{
		this.styles = new HashMap<String, String>();
		for ( String style : styles )
		{
			String[] kv = style.split( "\\s*:\\s*", 2 );
			this.styles.put( kv[ 0 ], kv[ 1 ] );
		}
	}


	//endregion


	//region HighlightStyle - Public Methods Section

	/**
	 * Do highlight specified element.
	 *
	 * @param driver          instance of WebDriver.
	 * @param element         element finder.
	 *
	 * @return previous style.
	 */
	@SuppressWarnings ( "unchecked" )
	public Map<String, String> doHighlight( WebDriver driver, WebElement element )
	{
		try
		{
			if ( driver instanceof JavascriptExecutor )
			{
				Object result = ( ( JavascriptExecutor ) driver ).executeScript( SCRIPT, element, styles );
				return result instanceof Map ? ( Map<String, String> ) result : null;
			}
			else
			{
				return null;
			}
		}
		catch ( Exception e )
		{
			// element specified by locator is not found.
			if ( e instanceof NotFoundException || e.getCause() instanceof NotFoundException || e instanceof StaleElementReferenceException )
			{
				return null;
			}
			throw e;
		}
	}

	//endregion

}
