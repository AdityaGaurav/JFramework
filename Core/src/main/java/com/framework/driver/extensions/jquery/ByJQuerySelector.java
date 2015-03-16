package com.framework.driver.extensions.jquery;

import com.framework.config.Configurations;
import com.framework.config.FrameworkProperty;
import com.framework.utils.error.PreConditions;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.driver.extensions.jquery
 *
 * Name   : jQuerySelector
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-03-10 
 *
 * Time   : 19:29
 *
 */

public class ByJQuerySelector extends By implements Serializable
{

	//region jQuerySelector - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( ByJQuerySelector.class );

	private static final long serialVersionUID = 5449544452408311270L;

	private static final String JQUERY_VARIABLE = ( String )
			FrameworkProperty.JQUERY_VARIABLE_NAME.from( Configurations.getInstance() );

	private String selector, jQueryVariable;

	private ByJQuerySelector byJQuerySelector;

	//endregion


	//region jQuerySelector - Constructor Methods Section

	/**
	 * Initializes a new instance of the {@linkplain ByJQuerySelector} class.
	 *
	 * @param selector       	A String containing a selector expression
	 * @param jQueryVariable    A DOM Element, Document, or jQuery to use as context.
	 * @param context           A variable that has been assigned to jQuery
	 */
	public ByJQuerySelector( final String selector, final ByJQuerySelector context, final String jQueryVariable )
	{
		PreConditions.checkNotNullNotBlankOrEmpty( selector, "String argument selector cannot be null, empty or blank" );
		this.jQueryVariable = jQueryVariable == null ? JQUERY_VARIABLE : jQueryVariable;
		this.byJQuerySelector = context;
		this.selector = jQueryVariable + "('" + selector.replace( '\'', '"' ) + "'"
				+ ( this.byJQuerySelector != null ? ", " + this.byJQuerySelector : StringUtils.EMPTY ) + ")";
	}

	protected ByJQuerySelector()
	{

	}

	//endregion


	//region jQuerySelector - Public Methods Section

	public String getSelector()
	{
		return selector;
	}

	public String getJqueryVariable()
	{
		return jQueryVariable;
	}

	public ByJQuerySelector getJQuerySelector()
	{
		return byJQuerySelector;
	}

	@Override
	public String toString()
	{
		return selector;
	}

	@Override
	public List<WebElement> findElements( final SearchContext context )
	{
		String jquery = "return " + selector + ".get();";
		Object o = ( ( JavascriptExecutor ) context ).executeScript( jquery );
		if( o.getClass().isAssignableFrom( java.util.ArrayList.class ) )
		{
			return ( java.util.ArrayList ) o;
		}

		return Lists.newArrayList();
	}

	public WebElement findElement( SearchContext context )
	{
		WebElement e = ( WebElement ) ( ( JavascriptExecutor ) context ).executeScript( "return " + selector + ".get(0);" );
		if ( e == null )
		{
			throw new NoSuchElementException( "No element found matching JQuery selector " + selector );
		}

		return e;
	}

	/// <summary>
	/// Adds elements to the set of matched elements.
	/// </summary>
	/// <param name="selector">
	/// A String representing a selector expression to find additional elements to add to the set of matched
	/// elements.
	/// </param>
	/// <returns>The Selenium jQuery selector.</returns>
	public ByJQuerySelector add( String selector )
	{
		return this.chain( "add", selector, false );
	}

	/// <summary>
	/// Add the previous set of elements on the stack to the current set, optionally filtered by a selector.
	/// </summary>
	/// <param name="selector">
	/// A String containing a selector expression to match the current set of elements against.
	/// </param>
	/// <returns>The Selenium jQuery selector.</returns>
	public ByJQuerySelector addBack( String selector )
	{
		return this.chain( "addBack", selector, false );
	}


	/// <summary>
	/// Add the previous set of elements on the stack to the current set.
	/// </summary>
	/// <returns>The Selenium jQuery selector.</returns>
	/// <remarks>While this method is obsolete in jQuery 1.8+ we will support it.</remarks>
	public ByJQuerySelector andSelf()
	{
		return this.chain( "andSelf", null, false );
	}

	/// <summary>
	/// Get the children of each element in the set of matched elements, optionally filtered by a selector.
	/// </summary>
	/// <param name="selector">A String containing a selector expression to match elements against.</param>
	/// <returns>The Selenium jQuery selector.</returns>
	public ByJQuerySelector children( String selector )
	{
		return this.chain( "children", selector, false );
	}

	/// <summary>
	/// For each element in the set, get the first element that matches the selector by testing the element itself
	/// and traversing up through its ancestors in the DOM tree.
	/// </summary>
	/// <param name="selector">A String containing a selector expression to match elements against.</param>
	/// <returns>The Selenium jQuery selector.</returns>
	public ByJQuerySelector closest( String selector )
	{
		return this.chain( "closest", selector, false );
	}

	/// <summary>
	/// Get the children of each element in the set of matched elements, including text and comment nodes.
	/// </summary>
	/// <returns>The Selenium jQuery selector.</returns>
	public ByJQuerySelector contents()
	{
		return this.chain( "contents", null, false );
	}

	/// <summary>
	/// End the most recent filtering operation in the current chain and return the set of matched elements to its 
	/// previous state.
	/// </summary>
	/// <returns>The Selenium jQuery selector.</returns>
	public ByJQuerySelector end()
	{
		return this.chain( "end", null, false );
	}

	/// <summary>
	/// Reduce the set of matched elements to the one at the specified index.
	/// </summary>
	/// <param name="index">An integer indicating the 0-based position of the element.</param>
	/// <returns>The Selenium jQuery selector.</returns>
	public ByJQuerySelector eq( int index )
	{
		return this.chain( "eq", String.valueOf( index ), true );
	}

	/// <summary>
	/// Reduce the set of matched elements to those that match the selector or pass the function's test.
	/// </summary>
	/// <param name="selector">A String containing a selector expression to match elements against.</param>
	/// <returns>The Selenium jQuery selector.</returns>
	public ByJQuerySelector filter( String selector )
	{
		return this.chain( "filter", selector, false );
	}

	/// <summary>
	/// Get the descendants of each element in the current set of matched elements, filtered by a selector, jQuery 
	/// object, or element.
	/// </summary>
	/// <param name="selector">A String containing a selector expression to match elements against.</param>
	/// <returns>The Selenium jQuery selector.</returns>
	public ByJQuerySelector find( String selector )
	{
		return this.chain( "find", selector, false );
	}

	/// <summary>
	/// Reduce the set of matched elements to the first in the set.
	/// </summary>
	/// <returns>The Selenium jQuery selector.</returns>
	public ByJQuerySelector first()
	{
		return this.chain( "first", null, false );
	}

	/// <summary>
	/// Reduce the set of matched elements to those that have a descendant that matches the selector or DOM 
	/// element.
	/// </summary>
	/// <param name="selector">A String containing a selector expression to match elements against.</param>
	/// <returns>The Selenium jQuery selector.</returns>
	public ByJQuerySelector has( String selector )
	{
		return this.chain( "has", selector, false );
	}

	/// <summary>
	/// Check the current matched set of elements against a selector, element, or jQuery object and return true if 
	/// at least one of these elements matches the given arguments.
	/// </summary>
	/// <param name="selector">A String containing a selector expression to match elements against.</param>
	/// <returns>The Selenium jQuery selector.</returns>
	public ByJQuerySelector is( String selector )
	{
		return this.chain( "is", selector, false );
	}

	/// <summary>
	/// Reduce the set of matched elements to the final one in the set.
	/// </summary>
	/// <returns>The Selenium jQuery selector.</returns>
	public ByJQuerySelector last()
	{
		return this.chain( "last", null, false );
	}

	/// <summary>
	/// Get the immediately following sibling of each element in the set of matched elements. If a selector is 
	/// provided, it retrieves the next sibling only if it matches that selector.
	/// </summary>
	/// <param name="selector">A String containing a selector expression to match elements against.</param>
	/// <returns>The Selenium jQuery selector.</returns>
	public ByJQuerySelector next( String selector )
	{
		return this.chain( "next", selector, false );
	}

	/// <summary>
	/// Get all following siblings of each element in the set of matched elements, optionally filtered by a 
	/// selector.
	/// </summary>
	/// <param name="selector">A String containing a selector expression to match elements against.</param>
	/// <returns>The Selenium jQuery selector.</returns>
	public ByJQuerySelector nextAll( String selector )
	{
		return this.chain( "nextAll", selector, false );
	}

	/// <summary>
	/// Get all following siblings of each element up to but not including the element matched by the selector, 
	/// DOM node, or jQuery object passed.
	/// </summary>
	/// <param name="selector">
	/// A String containing a selector expression to indicate where to stop matching following sibling elements.
	/// </param>
	/// <param name="filter">A String containing a selector expression to match elements against.</param>
	/// <returns>The Selenium jQuery selector.</returns>
	public ByJQuerySelector nextUntil( String selector, String filter )
	{
		String data = handleSelectorWithFilter( selector, filter );
		return this.chain( "nextUntil", data, true );
	}

	/// <summary>
	/// Remove elements from the set of matched elements.
	/// </summary>
	/// <param name="selector">
	/// A String containing a selector expression, a DOM element, or an array of elements to match against the 
	/// set.
	/// </param>
	/// <returns>The Selenium jQuery selector.</returns>
	public ByJQuerySelector not( String selector )
	{
		return this.chain( "not", selector, false );
	}

	/// <summary>
	/// Get the closest ancestor element that is positioned.
	/// </summary>
	/// <returns>The Selenium jQuery selector.</returns>
	public ByJQuerySelector offsetParent()
	{
		return this.chain( "offsetParent", null, false );
	}

	/// <summary>
	/// Get the parent of each element in the current set of matched elements, optionally filtered by a selector.
	/// </summary>
	/// <param name="selector">A String containing a selector expression to match elements against.</param>
	/// <returns>The Selenium jQuery selector.</returns>
	public ByJQuerySelector parent( String selector )
	{
		return this.chain( "parent", selector, false );
	}

	public ByJQuerySelector parent()
	{
		return this.chain( "parent", null, false );
	}

	/// <summary>
	/// Get the ancestors of each element in the current set of matched elements, optionally filtered by a 
	/// selector.
	/// </summary>
	/// <param name="selector">A String containing a selector expression to match elements against.</param>
	/// <returns>The Selenium jQuery selector.</returns>
	public ByJQuerySelector parents( String selector )
	{
		return this.chain( "parents", selector, false );
	}

	public ByJQuerySelector parents()
	{
		return this.chain( "parents", null, false );
	}

	/// <summary>
	/// Get the ancestors of each element in the current set of matched elements, up to but not including the 
	/// element matched by the selector, DOM node, or jQuery object.
	/// </summary>
	/// <param name="selector">
	/// A String containing a selector expression to indicate where to stop matching ancestor elements.
	/// </param>
	/// <param name="filter">A String containing a selector expression to match elements against.</param>
	/// <returns>The Selenium jQuery selector.</returns>
	public ByJQuerySelector parentsUntil( String selector, String filter )
	{
		String data = handleSelectorWithFilter( selector, filter );
		return this.chain( "parentsUntil", data, true );
	}

	/// <summary>
	/// Get the immediately preceding sibling of each element in the set of matched elements, optionally filtered 
	/// by a selector.
	/// </summary>
	/// <param name="selector">A String containing a selector expression to match elements against.</param>
	/// <returns>The Selenium jQuery selector.</returns>
	public ByJQuerySelector prev( String selector )
	{
		return this.chain( "prev", selector, false );
	}

	/// <summary>
	/// Get all preceding siblings of each element in the set of matched elements, optionally filtered by a 
	/// selector.
	/// </summary>
	/// <param name="selector">A String containing a selector expression to match elements against.</param>
	/// <returns>The Selenium jQuery selector.</returns>
	public ByJQuerySelector prevAll( String selector )
	{
		return this.chain( "prevAll", selector, false );
	}

	/// <summary>
	/// Get all preceding siblings of each element up to but not including the element matched by the selector, 
	/// DOM node, or jQuery object.
	/// </summary>
	/// <param name="selector">
	/// A String containing a selector expression to indicate where to stop matching preceding sibling elements.
	/// </param>
	/// <param name="filter">A String containing a selector expression to match elements against.</param>
	/// <returns>The Selenium jQuery selector.</returns>
	public ByJQuerySelector prevUntil( String selector, String filter )
	{
		String data = handleSelectorWithFilter( selector, filter );
		return this.chain( "prevUntil", data, true );
	}

	/// <summary>
	/// Get the siblings of each element in the set of matched elements, optionally filtered by a selector.
	/// </summary>
	/// <param name="selector">A String containing a selector expression to match elements against.</param>
	/// <returns>The Selenium jQuery selector.</returns>
	public ByJQuerySelector siblings( String selector )
	{
		return this.chain( "siblings", selector, false );
	}

	/// <summary>
	/// Reduce the set of matched elements to a subset specified by a range of indices.
	/// </summary>
	/// <param name="start">
	/// An integer indicating the 0-based position at which the elements begin to be selected. If negative, it 
	/// indicates an offset from the end of the set.
	/// </param>
	/// <param name="end">
	/// An integer indicating the 0-based position at which the elements stop being selected. If negative, it 
	/// indicates an offset from the end of the set. If omitted, the range continues until the end of the set.
	/// </param>
	/// <returns>The Selenium jQuery selector.</returns>
	public ByJQuerySelector slice( int start, Optional<Integer> end )
	{
		String data = end.isPresent() ? start + ", " + end : String.valueOf( start );
		return this.chain( "slice", data, true );
	}



	//endregion


	//region jQuerySelector - Private Function Section

	/// <summary>
	/// Handles the selector with filter scenario by generating the proper chained function arguments.
	/// </summary>
	/// <param name="selector">The selector.</param>
	/// <param name="filter">The filter.</param>
	/// <returns>Chained function arguments string generated based on given selector and filter.</returns>
	private static String handleSelectorWithFilter( String selector, String filter )
	{
		String data = StringUtils.EMPTY;
		if ( StringUtils.isNoneEmpty( selector ) )
		{
			data = StringUtils.isEmpty( filter )
					? "'" + selector + "'"
					: "'" + selector + "', '" + filter + "'";
		}

		return data;
	}

	/// <summary>
	/// Chain a jQuery method to a selector.
	/// </summary>
	/// <param name="name">The jQuery method name.</param>
	/// <param name="selector">The jQuery method selector.</param>
	/// <param name="noWrap">
	/// <c>true</c> to not to wrap the selector into quotes; otherwise, <c>false</c>.
	/// </param>
	/// <returns>The Selenium jQuery selector.</returns>
	private ByJQuerySelector chain( String name, String selector, boolean noWrap )
	{
		selector = StringUtils.isEmpty( selector )
				? StringUtils.EMPTY
				: ( noWrap ? selector.trim() : "'" + selector.trim() + "'" );

		this.selector = this.selector + "." + name + "(" + selector + ")";

		return this;//new ByJQuerySelector( this.selector, this.byJQuerySelector, this.jQueryVariable );
	}



	//endregion
}
