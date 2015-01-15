package com.framework.driver.utils.ui;

import ch.lambdaj.function.convert.Converter;
import com.framework.driver.objects.ElementObject;
import com.framework.driver.objects.Link;
import org.hamcrest.Matcher;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

import static ch.lambdaj.Lambda.*;



/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.driver.utils.ui
 *
 * Name   : ListWebElementUtils
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-01-07
 *
 * Time   : 11:34
 */

public class ListWebElementUtils
{
	private ListWebElementUtils(){}

	//todo: documentation
	public static String joinElementsText( List<WebElement> elements, String delimiter )
	{
		return join( extractElementsText( elements ), delimiter );
	}

	//todo: documentation
	public static List<WebElement> selectElementsMatchingText( List<WebElement> elements, Matcher<String> matcher )
	{
		return select( elements, having( on( WebElement.class ).getText(), matcher ) );
	}

	//todo: documentation
	public static List<WebElement> selectElementsMatchingAttributeValue(
			List<WebElement> elements, String attributeName, Matcher<String> matcher )
	{
		return select( elements, having( on( WebElement.class ).getAttribute( attributeName ), matcher ) );
	}

	//todo: documentation
	public static List<String> extractElementsText( List<WebElement> elements )
	{
		return extract( elements, on( WebElement.class ).getText() );
	}

	//todo: documentation
	public static List<String> extractElementsAttribute( List<WebElement> elements, String attributeName )
	{
		return extract( elements, on( WebElement.class ).getAttribute( attributeName ) );
	}

	//todo: documentation
	public static List<Link> convertToLink( final WebDriver driver, List<WebElement> list )
	{
		Converter<WebElement,Link> converter = new Converter<WebElement,Link>()
		{
			@Override
			public Link convert( final WebElement from )
			{
				return new Link( driver, from );
			}
		};

		return convert( list, converter );
	}

	public static List<WebElement> convertToLinkToWebElement( List<Link> list )
	{
		Converter<Link,WebElement> converter = new Converter<Link,WebElement>()
		{
			@Override
			public WebElement convert( final Link from )
			{
				return from.getWrappedElement();
			}
		};

		return convert( list, converter );
	}

	public static List<WebElement> extractWebElement( List<? extends ElementObject> list )
	{
		Converter<ElementObject,WebElement> converter = new Converter<ElementObject,WebElement>()
		{
			@Override
			public WebElement convert( final ElementObject from )
			{
				return from.getWrappedElement();
			}
		};

		return convert( list, converter );

	}

}
