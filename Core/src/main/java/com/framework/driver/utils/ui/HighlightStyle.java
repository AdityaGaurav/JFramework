package com.framework.driver.utils.ui;

import com.framework.driver.event.HtmlDriver;
import com.framework.driver.event.HtmlElement;
import com.google.common.collect.Maps;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.StaleElementReferenceException;

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

	/**
	 * element styles.
	 *
	 * .css("outline", "blue solid 1px");
	 * .css("backgroundColor","rgba(100,100,255,0.5)");63, 187, 81, 0.5
	 */
	public static HighlightStyle[] ELEMENT_STYLES = new HighlightStyle[] {
			new HighlightStyle( "backgroundColor: rgba(63,187,81,0.5)", "outline: #2e3b0b solid 1px" ),
			new HighlightStyle( "backgroundColor: orange", "outline: #484 solid 2px" ),
			new HighlightStyle( "backgroundColor: rgba(63,187,81,0.5)", "outline: #2e3b0b solid 3px" ),
			new HighlightStyle( "backgroundColor: transparent", "outline: #339900 solid 2px" ),
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
		this.styles = Maps.newHashMap();

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
	public Map<String, String> doHighlight( HtmlDriver driver, HtmlElement element )
	{
		try
		{
			Object result = driver.executeScript( SCRIPT, element, styles );
			return result instanceof Map ? ( Map<String,String> ) result : null;
		}
		catch ( Exception e )
		{
			if ( e instanceof NotFoundException || e.getCause() instanceof NotFoundException || e instanceof StaleElementReferenceException )
			{
				return null;
			}
			throw e;
		}
	}

	//endregion

}
