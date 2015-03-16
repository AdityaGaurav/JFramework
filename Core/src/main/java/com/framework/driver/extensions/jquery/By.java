package com.framework.driver.extensions.jquery;

import com.framework.config.Configurations;
import com.framework.config.FrameworkProperty;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.driver.extensions.jquery
 *
 * Name   : By 
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-03-10 
 *
 * Time   : 19:09
 *
 */

public abstract class By extends org.openqa.selenium.By
{

	//region By - Variables Declaration and Initialization Section.

	private static final String JQUERY_VARIABLE = ( String )
			FrameworkProperty.JQUERY_VARIABLE_NAME.from( Configurations.getInstance() );


	//endregion


	//region By - Constructor Methods Section

	//endregion


	//region By - By Override Methods Section

	/**
	 * {@inheritDoc}
	 */
	public static org.openqa.selenium.By id( final String id )
	{
		return org.openqa.selenium.By.id( id );
	}

	/**
	 * {@inheritDoc}
	 */
	public static org.openqa.selenium.By className( final String className )
	{
		return org.openqa.selenium.By.className( className );
	}

	/**
	 * {@inheritDoc}
	 */
	public static org.openqa.selenium.By linkText( final String linkText )
	{
		return org.openqa.selenium.By.linkText( linkText );
	}

	/**
	 * {@inheritDoc}
	 */
	public static org.openqa.selenium.By name( final String name )
	{
		return org.openqa.selenium.By.name( name );
	}

	/**
	 * {@inheritDoc}
	 */
	public static org.openqa.selenium.By tagName( final String name )
	{
		return org.openqa.selenium.By.tagName( name );
	}

	/**
	 * {@inheritDoc}
	 */
	public static org.openqa.selenium.By xpath( final String xpath )
	{
		return org.openqa.selenium.By.xpath( xpath );
	}

	/**
	 * {@inheritDoc}
	 */
	public static org.openqa.selenium.By cssSelector( final String cssSelector )
	{
		return org.openqa.selenium.By.cssSelector( cssSelector );
	}

	/**
	 * {@inheritDoc}
	 */
	public static org.openqa.selenium.By partialLinkText( final String partialLinkText )
	{
		return org.openqa.selenium.By.partialLinkText( partialLinkText );
	}

	/// <summary>
	/// Gets a mechanism to find elements matching jQuery selector.
	/// </summary>
	/// <param name="selector">A string containing a selector expression</param>
	/// <param name="context">A DOM Element, Document, or jQuery to use as context.</param>
	/// <param name="jQueryVariable">A variable that has been assigned to jQuery.</param>
	/// <returns>A <see cref="jQuerySelector"/> object the driver can use to find the elements.</returns>

	/**
	 * Gets a mechanism to find elements matching jQuery selector.
	 * @param selector  A string containing a selector expression
	 * @param context   A DOM Element, Document, or jQuery to use as context.
	 *
	 * @return object the driver can use to find the elements.
	 *
	 * @see ByJQuerySelector
	 */
	public static ByJQuerySelector jQuerySelector( String selector, ByJQuerySelector context )
	{
		return new ByJQuerySelector( selector, context, JQUERY_VARIABLE );
	}

	public static ByJQuerySelector jQuerySelector( String selector )
	{
		return new ByJQuerySelector( selector, null, JQUERY_VARIABLE );
	}

	//endregion

}
