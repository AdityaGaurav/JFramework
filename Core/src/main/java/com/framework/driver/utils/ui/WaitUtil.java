/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.framework.driver.utils.ui;

import com.framework.driver.event.EventWebDriver;
import com.framework.driver.exceptions.PreConditionException;
import com.framework.driver.utils.PreConditions;
import com.framework.utils.datetime.DateTimeUtils;
import com.framework.utils.datetime.TimeConstants;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.util.List;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.driver.utils
 *
 * Name   : WaitUtils
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-01-03
 *
 * Time   : 21:30
 */

public class WaitUtil implements TimeConstants
{

	private static final Logger logger = LoggerFactory.getLogger( WaitUtil.class );

	private static boolean enableFinalConditionLog = true;


	//region WaitUtil - Constructor Methods Section

	private WaitUtil()
	{
		super();
	}

	//endregion


	//region WaitUtil - ExpectedCondition Methods Section

	//todo: documentation
	public static ExpectedCondition<Boolean> not( final ExpectedCondition<?> condition )
	{
		final DateTime dt = DateTime.now();

		return new ExpectedCondition<Boolean>()
		{
			@Override
			public Boolean apply( WebDriver driver )
			{
				final Description desc = new StringDescription()
						.appendText( this.toString() );

				enableFinalConditionLog = /* prevent double logging */ false;
				Object result = condition.apply( driver );
				boolean response = ( result == null || result == Boolean.FALSE );
				desc.appendValue( " is " + response );
				logger. /* reporting progress */ debug( getDurationString( dt, desc ) );
				enableFinalConditionLog = true;
				return response;
			}

			@Override
			public String toString()
			{
				return "condition to not be valid: <" + condition + ">";
			}
		};
	}

	//todo: documentation
	public static ExpectedCondition<Boolean> multiple( final ExpectedCondition<?>... conditions )
	{
		final DateTime dt = DateTime.now();

		return new ExpectedCondition<Boolean>()
		{
			@Override
			public Boolean apply( WebDriver driver )
			{
				List<Boolean> responses = Lists.newArrayList();
				final Description desc = new StringDescription()
						.appendText( this.toString() );

				if( enableFinalConditionLog )
				{
					logger. /* reporting progress */ debug( getDurationString( dt, desc ) );
				}

				for ( ExpectedCondition<?> condition : conditions )
				{
					Object result = condition.apply( driver );
					boolean response = ! ( result == null || result == Boolean.FALSE );
					responses.add( response );
				}

				return BooleanUtils.and( responses.toArray( new Boolean[ responses.size() ] ) );
			}

			@Override
			public String toString()
			{
				List<ExpectedCondition<?>> conditionList = Lists.newArrayList( conditions );
				String conditionsText = Joiner.on( " | " ).join( conditionList );
				return "all conditions to be valid: <" + conditionsText + ">";
			}
		};
	}

	/**
	 * An expectation for checking that the url matches a string expression.
	 *
	 * @param matcher a {@code Matcher<String>} expression.
	 *
	 * @return {@code true} when the url matches, {@code false} otherwise
	 *
	 * @see org.hamcrest.Matchers(String)
	 * @see org.openqa.selenium.WebDriver#getCurrentUrl()
	 */
	public static ExpectedCondition<Boolean> urlMatches( final Matcher<String> matcher )
	{
		final DateTime dt = DateTime.now();

		return new ExpectedCondition<Boolean>()
		{
			@Override
			public Boolean apply( WebDriver input )
			{
				final Description desc = new StringDescription().appendText( this.toString() );
				String urlDecoded = null;
				String currentUrl = ( ( EventWebDriver ) input ).getWrappedDriver().getCurrentUrl();
				try
				{
					urlDecoded = java.net.URLDecoder.decode( currentUrl, "UTF-8" );
				}
				catch ( UnsupportedEncodingException e )
				{
					urlDecoded = currentUrl;
				}
				boolean response = matcher.matches( urlDecoded );
				if ( ! response )
				{
					desc.appendText( ", however current url is <'" ).appendValue( urlDecoded ).appendText( "'>" );
				}

				/* disable double logging */
				if( enableFinalConditionLog )
				{
					logger. /* reporting progress */ debug( getDurationString( dt, desc ) );
				}

				return response;
			}

			@Override
			public String toString()
			{
				final Description description = new StringDescription()
						.appendText( " url to match " )
						.appendDescriptionOf( matcher );
				return description.toString();
			}
		};
	}

	//todo: documentation
	public static ExpectedCondition<Boolean> titleMatches( final Matcher<String> matcher )
	{
		final DateTime dt = DateTime.now();

		return new ExpectedCondition<Boolean>()
		{
			@Override
			public Boolean apply( WebDriver input )
			{
				final Description desc = new StringDescription().appendText( this.toString() );
				String currentTitle = ( ( EventWebDriver ) input ).getWrappedDriver().getTitle();
				boolean response = matcher.matches( currentTitle );
				if ( ! response )
				{
					desc.appendText( ", however current title is <'" ).appendValue( currentTitle ).appendText( "'>" );
				}
				if /* disable double logging */ ( enableFinalConditionLog )
				{
					logger. /* reporting progress */ debug( getDurationString( dt, desc ) );
				}

				return response;
			}

			@Override
			public String toString()
			{
				final Description description = new StringDescription()
						.appendText( " title to match " )
						.appendDescriptionOf( matcher );
				return description.toString();
			}
		};
	}

	//todo: documentation
	public static ExpectedCondition<Cookie> cookieIsPresent( final String cookieName )
	{
		final DateTime dt = DateTime.now();

		return new ExpectedCondition<Cookie>()
		{
			@Override
			public Cookie apply( WebDriver input )
			{
				final Description desc = new StringDescription().appendText( this.toString() );
				Cookie cookie = ( ( EventWebDriver ) input ).getWrappedDriver().manage().getCookieNamed( cookieName );
				if /* disable double logging */ ( enableFinalConditionLog )
				{
					logger. /* reporting progress */ debug( getDurationString( dt, desc ) );
				}
				return cookie;
			}

			@Override
			public String toString()
			{
				return "presence of cookie named <'" + cookieName + "'>";
			}
		};
	}

	//todo: documentation
	public static ExpectedCondition<WebElement> presenceBy( final By locator )
	{
		final DateTime dt = DateTime.now();

		return new ExpectedCondition<WebElement>()
		{
			@Override
			public WebElement apply( WebDriver input )
			{
				Description desc = new StringDescription();
				try
				{
					desc.appendText( "Waiting for presence of element " ).appendValue( locator );
					if /* disable double logging */ ( enableFinalConditionLog )
					{
						logger. /* reporting progress */ debug( getDurationString( dt, desc ) );
					}

					WebDriver remoteDriver = ( ( EventWebDriver ) input ).getWrappedDriver();
					WebElement remoteElement = findElement( locator, remoteDriver );
					return ( ( EventWebDriver ) input ).createEventWebElement( remoteElement );
				}
				catch ( NoSuchElementException e )
				{
					return null;
				}
			}

			@Override
			public String toString()
			{
				return "presence of element by <" + locator + ">";
			}
		};
	}

	//todo: documentation
	public static ExpectedCondition<List<WebElement>> presenceOfAllBy( final By locator )
	{
		final DateTime dt = DateTime.now();

		return new ExpectedCondition<List<WebElement>>()
		{
			@Override
			public List<WebElement> apply( WebDriver input )
			{
				Description desc = new StringDescription()
						.appendText( this.toString() );

				WebDriver drv = /* preventing firing events while logging */ ( ( EventWebDriver ) input ).getWrappedDriver();
				List<WebElement> elements = findElements( locator, drv );

				/* report only if elements were not found ( show progress ) */
				if ( elements.size() == 0 )
				{
					if /* disable double logging */ ( enableFinalConditionLog )
					{
						logger. /* reporting progress */ debug( getDurationString( dt, desc ) );
					}
				}


				if ( elements.size() > 0 )
				{
					return findElements( locator, input );
				}
				else
				{
					return null;
				}
			}

			@Override
			public String toString()
			{
				return "presence of all elements by <" + locator + ">";
			}
		};
	}

	//todo:documentation
	public static ExpectedCondition<WebElement> presenceOfChildBy( final WebElement element, final By locator )
	{
		final DateTime dt = DateTime.now();

		return new ExpectedCondition<WebElement>()
		{
			@Override
			public WebElement apply( WebDriver input )
			{
				Description desc = new StringDescription();
				try
				{
					desc.appendText( this.toString() );

					// Checking if disable logging flag was set to true and report the conditional waiting process.

					if ( enableFinalConditionLog )
					{
						logger.debug( getDurationString( dt, desc ) );
					}

					findElement( locator, element );
					return findElement( locator, element );
				}

				// ------------------------------------------------------------------------------------------------------
				// When searching child element parent can throw StaleElementReferenceException
				// ------------------------------------------------------------------------------------------------------

				catch ( StaleElementReferenceException ex )
				{
					logger.error( "throwing a new StaleElementReferenceException on WaitUtil#presenceOfChildBy." );
					throw getParentStaleReferenceDescription( element, desc, ex );
				}
				catch ( NoSuchElementException e )
				{
					return null;
				}
			}

			@Override
			public String toString()
			{
				return "presence of child element by " + locator;
			}
		};
	}

	//todo:documentation
	public static ExpectedCondition<List<WebElement>> presenceOfAllChildrenBy( final WebElement element, final By locator )
	{
		final DateTime dt = DateTime.now();

		return new ExpectedCondition<List<WebElement>>()
		{
			@Override
			public List<WebElement> apply( WebDriver input )
			{
				Description desc = new StringDescription()
						.appendText( this.toString() );

				try
				{
					List<WebElement> elements = findElements( locator, element );

					if ( elements.size() == 0 )
					{
						// Checking if disable logging flag was set to true and report the conditional waiting process.

						if ( enableFinalConditionLog )
						{
							logger.debug( getDurationString( dt, desc ) );
						}

					}

					if ( elements.size() > 0 )
					{
						return findElements( locator, element );
					}
					else
					{
						return null;
					}
				}

				// ------------------------------------------------------------------------------------------------------
				// When searching child element parent can throw StaleElementReferenceException
				// ------------------------------------------------------------------------------------------------------

				catch ( StaleElementReferenceException ex )
				{
					throw getParentStaleReferenceDescription( input, desc, ex );
				}
			}

			@Override
			public String toString()
			{
				return "presence of all child's elements by " + locator;
			}
		};
	}


	//todo: documentation
	public static ExpectedCondition<Boolean> visibilityOf( final WebElement element, final boolean visible )
	{
		final DateTime dt = DateTime.now();

		return new ExpectedCondition<Boolean>()
		{
			@Override
			public Boolean apply( WebDriver input )
			{
				try
				{
					Description desc = new StringDescription().appendText( this.toString() );
					WebElement we = /* avoid firing events */ ( ( EventWebDriver ) input ).getWrappedElement( element );
					boolean response = we.isDisplayed() == visible;

					if /* disable double logging */ ( enableFinalConditionLog )
					{
						logger. /* reporting progress */ debug( getDurationString( dt, desc ) );
					}

					return response;
				}
				catch ( StaleElementReferenceException ex )
				{
					ex.addInfo( "procedure name", "ExpectedCondition<Boolean> visibilityOf" );
					throw ex;
				}
			}

			@Override
			public String toString()
			{
				return "element <" + getElementDescription( element ) + ">" + ( visible ? "to" : "not to" ) + " be displayed.";
			}
		};
	}

	//todo: documentation
	public static ExpectedCondition<WebElement> visibilityBy( final By locator, final boolean visible )
	{
		final DateTime dt = DateTime.now();

		return new ExpectedCondition<WebElement>()
		{
			public ExpectedCondition<WebElement> presenceOf = WaitUtil.presenceBy( locator );

			@Override
			public WebElement apply( WebDriver input )
			{
				Description desc = new StringDescription().appendText( this.toString() );

				//enableFinalConditionLog = false;  todo:examine the log
				WebElement element = presenceOf.apply( input );
				//enableFinalConditionLog = true;

				if /* disable double logging */ ( enableFinalConditionLog )
				{
					logger. /* reporting progress */ debug( getDurationString( dt, desc ) );
				}

				if ( null == element )
				{
					return null;
				}
				return elementIfVisible( element );
			}

			public String toString()
			{
				return "element <" + locator + "> " + ( visible ? "to" : "not to" ) + " be displayed.";
			}
		};
	}

	//todo: documentation
	public static ExpectedCondition<List<WebElement>> visibilityOfAll( final List<WebElement> elements, final boolean visible )
	{
		final DateTime dt = DateTime.now();

		return new ExpectedCondition<List<WebElement>>()
		{
			@Override
			public List<WebElement> apply( WebDriver input )
			{
				Description desc = new StringDescription().appendText( this.toString() );
				if /* disable double logging */ ( enableFinalConditionLog )
				{
					logger. /* reporting progress */ debug( getDurationString( dt, desc ) );
				}

				for ( WebElement element : elements )
				{
					WebElement we = /* avoid firing events */ ( ( EventWebDriver ) input ).getWrappedElement( element );
					if ( we.isDisplayed() != visible )
					{
						return null;
					}
				}

				return elements.size() > 0 ? elements : null;
			}

			@Override
			public String toString()
			{
				return "<" + elements.size() + "> elements " + ( visible ? "to" : "not to" ) + " be displayed.";
			}
		};
	}

	//todo: documentation
	public static ExpectedCondition<List<WebElement>> visibilityOfAllBy( final By locator, final boolean visible )
	{
		final DateTime dt = DateTime.now();

		return new ExpectedCondition<List<WebElement>>()
		{
			@Override
			public List<WebElement> apply( WebDriver input )
			{
				Description desc = new StringDescription().appendText( this.toString() );

				List<WebElement> elements = findElements( locator, input );

				int elementsCount = elements.size();
				desc.appendText( " examining " ).appendValue( elementsCount ).appendText( " elements..." );
				if /* disable double logging */ ( enableFinalConditionLog )
				{
					logger. /* reporting progress */ debug( getDurationString( dt, desc ) );
				}

				for ( WebElement element : elements )
				{
					if ( element.isDisplayed() != visible )
					{
						return null;
					}
				}
				return elements.size() > 0 ? elements : null;
			}

			@Override
			public String toString()
			{
				return "all elements <" + locator + "> " + ( visible ? "to" : "not to" ) + " be displayed.";
			}
		};
	}

	//todo: documentation
	public static ExpectedCondition<Boolean> clickableOf( final WebElement element, final boolean enabled )
	{
		final DateTime dt = DateTime.now();
		logger.debug( "Before waiting for object to be clickable, validate that object is displayed." );

		return new ExpectedCondition<Boolean>()
		{
			@Override
			public Boolean apply( WebDriver input )
			{
				Description desc = new StringDescription().appendText( this.toString() );
				if /* disable double logging */ ( enableFinalConditionLog )
				{
					logger. /* reporting progress */ debug( getDurationString( dt, desc ) );
				}

				WebElement we = /* avoid firing events */ ( ( EventWebDriver ) input ).getWrappedElement( element );
				return null != elementIfVisible( we ) && we.isEnabled() == enabled;
			}

			@Override
			public String toString()
			{
				return "element <" + getElementDescription( element ) + "> " + ( enabled ? "to" : "not to" ) + " be enabled/clickable.";
			}
		};
	}

	//todo: documentation
	public static ExpectedCondition<WebElement> clickableBy( final By locator, final boolean displayed )
	{
		final DateTime dt = DateTime.now();
		logger.debug( "Before waiting for object to be clickable, checking that object is exists and is displayed." );

		return new ExpectedCondition<WebElement>()
		{
			public ExpectedCondition<WebElement> presenceBy = WaitUtil.presenceBy( locator );

			@Override
			public WebElement apply( WebDriver input )
			{
				Description desc = new StringDescription().appendText( this.toString() );

				enableFinalConditionLog = false;
				WebElement element = elementIfVisible( presenceBy.apply( input ) );
				enableFinalConditionLog = true;

				if /* disable double logging */ ( enableFinalConditionLog )
				{
					logger. /* reporting progress */ debug( getDurationString( dt, desc ) );
				}

				if ( null == element || element.isEnabled() != displayed )
				{
					return null;
				}

				return element;
			}

			public String toString()
			{
				return "element <" + locator + "> " + ( displayed ? "to" : "not to" ) + " be enabled/clickable.";
			}
		};
	}

	//todo: documentation
	public static ExpectedCondition<Boolean> selectionOf( final WebElement element, final boolean selected )
	{
		final DateTime dt = DateTime.now();

		return new ExpectedCondition<Boolean>()
		{
			@Override
			public Boolean apply( WebDriver input )
			{
				Description desc = new StringDescription().appendText( this.toString() );
				if /* disable double logging */ ( enableFinalConditionLog )
				{
					logger. /* reporting progress */ debug( getDurationString( dt, desc ) );
				}

				WebElement we = /* avoid firing events */ ( ( EventWebDriver ) input ).getWrappedElement( element );
				return null != elementIfVisible( we ) && we.isSelected() == selected;
			}

			@Override
			public String toString()
			{
				return "element <" + getElementDescription( element ) + "> " + ( selected ? "to" : "not to" ) + " be selected/checked.";
			}
		};
	}

	//todo: documentation
	public static ExpectedCondition<WebElement> selectionBy( final By locator, final boolean selected )
	{
		final DateTime dt = DateTime.now();

		return new ExpectedCondition<WebElement>()
		{
			public ExpectedCondition<WebElement> presenceBy = WaitUtil.presenceBy( locator );

			@Override
			public WebElement apply( WebDriver input )
			{
				Description desc = new StringDescription().appendText( this.toString() );

				enableFinalConditionLog = false;
				WebElement element = presenceBy.apply( input );
				enableFinalConditionLog = true;

				if /* disable double logging */ ( enableFinalConditionLog )
				{
					logger. /* reporting progress */ debug( getDurationString( dt, desc ) );
				}

				if ( null == element || element.isSelected() != selected )
				{
					return null;
				}

				return element;
			}

			public String toString()
			{
				return "element <" + locator + "> " + ( selected ? "to" : "not to" ) + " be selected/checked.";
			}
		};
	}

	//todo: documentation
	public static ExpectedCondition<Boolean> elementsAttributeToMatch( final List<WebElement> elements,
																	   final String attributeName,
																	   final Matcher<String> matcher )
	{
		final DateTime dt = DateTime.now();

		return new ExpectedCondition<Boolean>()
		{
			@Override
			public Boolean apply( WebDriver input )
			{
				Description desc = new StringDescription().appendText( this.toString() );
				if /* disable double logging */ ( enableFinalConditionLog )
				{
					logger. /* reporting progress */ debug( getDurationString( dt, desc ) );
				}

				for ( WebElement element : elements )
				{
					WebElement we = /* avoid firing events */ ( ( EventWebDriver ) input ).getWrappedElement( element );
					String attributeValue = we.getAttribute( attributeName );

					/* attributes that contain urls values ( src, href ) might contain decoding characters ( %20 etc ) */

					String decodedValue;
					try
					{
						decodedValue = java.net.URLDecoder.decode( attributeValue, "UTF-8" );
						if( decodedValue.equals( attributeValue ) )
						{
							logger.debug( "Attribute value was decoded ..." );
						}
					}
					catch ( UnsupportedEncodingException e )
					{
						decodedValue = attributeValue;
					}

					boolean response = matcher.matches( decodedValue );
					if ( ! response )
					{
						return false;
					}
				}

				return elements.size() > 0;
			}

			@Override
			public String toString()
			{
				final Description description = new StringDescription()
						.appendText( "<" )
						.appendValue( elements.size() )
						.appendText( " > elements with attribute value matches " )
						.appendDescriptionOf( matcher );
				return description.toString();
			}
		};
	}

	//todo: documentation
	public static ExpectedCondition<Boolean> elementAttributeToMatch( final WebElement element,
																	  final String attributeName,
																	  final Matcher<String> matcher )
	{
		final DateTime dt = DateTime.now();

		return new ExpectedCondition<Boolean>()
		{
			@Override
			public Boolean apply( WebDriver input )
			{
				Description desc = new StringDescription().appendText( this.toString() );
				WebElement we = /* avoid firing events */ ( ( EventWebDriver ) input ).getWrappedElement( element );
				String attributeValue = we.getAttribute( attributeName );

				/* attributes that contain urls values ( src, href ) might contain decoding characters ( %20 etc ) */

				String decodedValue;
				try
				{
					decodedValue = java.net.URLDecoder.decode( attributeValue, "UTF-8" );
					if( ! decodedValue.equals( attributeValue ) )
					{
						logger.debug( "Attribute value was decoded ..." );
					}
				}
				catch ( UnsupportedEncodingException e )
				{
					decodedValue = attributeValue;
				}

				boolean response = matcher.matches( decodedValue );

				if ( ! response )
				{
					desc.appendText( ", however attribute value is <'" ).appendValue( decodedValue ).appendText( "'>" );
				}
				if /* disable double logging */ ( enableFinalConditionLog )
				{
					logger. /* reporting progress */ debug( getDurationString( dt, desc ) );
				}

				return response;
			}

			@Override
			public String toString()
			{
				final Description description = new StringDescription()
						.appendText( "waiting for element " )
						.appendValue( getElementDescription( element ) )
						.appendText( " attribute value matches " )
						.appendDescriptionOf( matcher );
				return description.toString();
			}
		};
	}

	//todo: documentation
	public static ExpectedCondition<Boolean> elementCssPropertyToMatch( final WebElement element,
																		final String propertyName,
																		final Matcher<String> matcher )
	{
		final DateTime dt = DateTime.now();

		return new ExpectedCondition<Boolean>()
		{
			@Override
			public Boolean apply( WebDriver input )
			{
				Description desc = new StringDescription().appendText( this.toString() );
				WebElement we = /* avoid firing events */ ( ( EventWebDriver ) input ).getWrappedElement( element );
				String propertyValue = we.getCssValue( propertyName );
				boolean response = matcher.matches( propertyValue );

				if ( ! response )
				{
					desc.appendText( ", however css property value is <'" ).appendValue( propertyValue ).appendText( "'>" );
				}
				if /* disable double logging */ ( enableFinalConditionLog )
				{
					logger. /* reporting progress */ debug( getDurationString( dt, desc ) );
				}

				return response;
			}

			@Override
			public String toString()
			{
				final Description description = new StringDescription()
						.appendText( "waiting for element " )
						.appendValue( getElementDescription( element ) )
						.appendText( " css property value matches " )
						.appendDescriptionOf( matcher );
				return description.toString();
			}
		};
	}

	//todo: documentation
	public static ExpectedCondition<Boolean> elementTextToMatch( final WebElement element,
																 final Matcher<String> matcher )
	{
		final DateTime dt = DateTime.now();

		return new ExpectedCondition<Boolean>()
		{
			@Override
			public Boolean apply( WebDriver input )
			{
				Description desc = new StringDescription().appendText( this.toString() );
				WebElement we = /* avoid firing events */ ( ( EventWebDriver ) input ).getWrappedElement( element );
				String text = we.getText();
				boolean response = matcher.matches( text );

				if ( ! response )
				{
					desc.appendText( ", however text value is <'" ).appendValue( text ).appendText( "'>" );
				}
				if /* disable double logging */ ( enableFinalConditionLog )
				{
					logger. /* reporting progress */ debug( getDurationString( dt, desc ) );
				}

				return response;
			}

			@Override
			public String toString()
			{
				final Description description = new StringDescription()
						.appendText( "waiting for element " )
						.appendValue( getElementDescription( element ) )
						.appendText( " text value matches " )
						.appendDescriptionOf( matcher );
				return description.toString();
			}
		};
	}

	//endregion


	//region WaitUtil - Service Methods Section

	/**
	 * Static convenience method that returns a {@linkplain org.openqa.selenium.support.ui.WebDriverWait} instance of 5 seconds
	 *
	 * @param driver a valid {@link org.openqa.selenium.WebDriver} instance.
	 *
	 * @return an instance of {@code WebDriverWait} set to five ( 5 ) seconds
	 *
	 * @throws com.framework.driver.exceptions.PreConditionException in case driver is null.
	 */
	public static WebDriverWait wait5( WebDriver driver )
	{
		try
		{
			logger.debug( "creating a new WebDriverWait[5]" );
			WebDriver ewd = PreConditions.checkNotNull( driver, "WebDriver 'driver' should not be null" );
			return new WebDriverWait( ewd, FIVE_SECONDS, HALF_OF_SECOND );
		}
		catch ( NullPointerException e )
		{
			throw new PreConditionException( e.getMessage() );
		}
	}

	/**
	 * Static convenience method that returns a {@linkplain org.openqa.selenium.support.ui.WebDriverWait} instance of 10 seconds and
	 * 1000 milliseconds of polling interval.
	 *
	 * @param driver a valid {@link org.openqa.selenium.WebDriver} instance.
	 *
	 * @return an instance of {@code WebDriverWait} set to five ( 10 ) seconds and 1000 millis polling interval.
	 *
	 * @throws com.framework.driver.exceptions.PreConditionException in case driver is null.
	 */
	public static WebDriverWait wait10( WebDriver driver )
	{
		try
		{
			logger.debug( "creating a new WebDriverWait[10]" );
			WebDriver ewd = PreConditions.checkNotNull( driver, "WebDriver 'driver' should not be null" );
			return new WebDriverWait( ewd, TEN_SECONDS, FIFTEEN_HUNDRED_MILLIS );
		}
		catch ( NullPointerException e )
		{
			throw new PreConditionException( e.getMessage() );
		}
	}

	/**
	 * Static convenience method that returns a {@linkplain org.openqa.selenium.support.ui.WebDriverWait} instance of 20 seconds and
	 * 2000 milliseconds of polling interval.
	 *
	 * @param driver a valid {@link org.openqa.selenium.WebDriver} instance.
	 *
	 * @return an instance of {@code WebDriverWait} set to five ( 20 ) seconds and 2000 millis polling interval.
	 *
	 * @throws com.framework.driver.exceptions.PreConditionException in case driver is null.
	 */
	public static WebDriverWait wait20( WebDriver driver )
	{
		try
		{
			logger.debug( "creating a new WebDriverWait[20]" );
			WebDriver ewd = PreConditions.checkNotNull( driver, "WebDriver 'driver' should not be null" );
			return new WebDriverWait( ewd, TWENTY_SECONDS, TWENTY_FIVE_HUNDRED_MILLIS );
		}
		catch ( NullPointerException e )
		{
			throw new PreConditionException( e.getMessage() );
		}
	}

	/**
	 * Static convenience method that returns a {@linkplain org.openqa.selenium.support.ui.WebDriverWait} instance of 30 seconds and
	 * 3000 milliseconds of polling interval.
	 *
	 * @param driver a valid {@link org.openqa.selenium.WebDriver} instance.
	 *
	 * @return an instance of {@code WebDriverWait} set to five ( 30 ) seconds and 3000 millis polling interval.
	 *
	 * @throws com.framework.driver.exceptions.PreConditionException in case driver is null.
	 */
	public static WebDriverWait wait30( WebDriver driver )
	{
		try
		{
			logger.debug( "creating a new WebDriverWait[30]" );
			WebDriver ewd = PreConditions.checkNotNull( driver, "WebDriver 'driver' should not be null" );
			return new WebDriverWait( ewd, HALF_MINUTE, THIRTY_HUNDRED_MILLIS );
		}
		catch ( NullPointerException e )
		{
			throw new PreConditionException( e.getMessage() );
		}
	}

	/**
	 * Static convenience method that returns a {@linkplain org.openqa.selenium.support.ui.WebDriverWait} instance of 60 seconds and
	 * 10000 milliseconds of polling interval.
	 *
	 * @param driver a valid {@link org.openqa.selenium.WebDriver} instance.
	 *
	 * @return an instance of {@code WebDriverWait} set to five ( 30 ) seconds and 10000 millis polling interval.
	 *
	 * @throws com.framework.driver.exceptions.PreConditionException in case driver is null.
	 */
	public static WebDriverWait wait60( WebDriver driver )
	{
		try
		{
			logger.debug( "creating a new WebDriverWait[60]" );
			WebDriver ewd = PreConditions.checkNotNull( driver, "WebDriver 'driver' should not be null" );
			return new WebDriverWait( ewd, HALF_MINUTE * 2, TWENTY_HUNDRED_MILLIS * 2 );
		}
		catch ( NullPointerException e )
		{
			throw new PreConditionException( e.getMessage() );
		}
	}

	//endregion


	//region WaitUtil - Private Functions Section

	private static WebElement elementIfVisible( WebElement element )
	{
		if ( null == element )
		{
			return null;
		}
		return element.isDisplayed() ? element : null;
	}

	private static String getDurationString( final DateTime s, Description d )
	{
		Duration duration = /* measuring between before and after */ new Duration( DateTime.now().getMillis() - s.getMillis() );
		String durationString = DateTimeUtils.getFormattedPeriod( duration );
		return d.appendText( "  [ elapsed time: " ).appendValue( durationString ).appendText( " ]" ).toString();
	}

	private static String getElementDescription( WebElement e )
	{
		String we = e.toString();
		return "found by " + we.substring( we.indexOf( "->" ) ).replace( "]", StringUtils.EMPTY );
	}

	/**
	 * @see #findElement(org.openqa.selenium.By, org.openqa.selenium.SearchContext)
	 */
	private static List<WebElement> findElements( By by, SearchContext context )
	{
		try
		{
			return context.findElements( by );
		}
		catch ( NoSuchElementException | StaleElementReferenceException e )
		{
			throw e;
		}
		catch ( WebDriverException e )
		{
			logger.warn( "WebDriverException thrown by findElement( {} ) -> {}", by, e.getMessage() );
			throw e;
		}
	}

	/**
	 * Looks up an element. Logs and re-throws WebDriverException if thrown. <p/>
	 * Method exists to gather data for http://code.google.com/p/selenium/issues/detail?id=1800
	 */
	private static WebElement findElement( By by, SearchContext context )
	{
		try
		{
			return context.findElement( by );
		}
		catch ( NoSuchElementException | StaleElementReferenceException e )
		{
			throw e;
		}
		catch ( WebDriverException e )
		{
			logger.warn( "WebDriverException thrown by findElement( {} ) -> {}", by, e.getMessage() );
			throw e;
		}
	}

	private static StaleElementReferenceException getParentStaleReferenceDescription(
			SearchContext context, Description description, StaleElementReferenceException ex )
	{
		if ( context instanceof WebElement )
		{
			WebElement we = ( WebElement ) context;
			description.appendText( " However the parent element <" )
					.appendText( getElementDescription( we ) )
					.appendText( "> had thrown " )
					.appendValue( StaleElementReferenceException.class.getName() )
					.appendText( " exception." );
			ex.addInfo( "error cause", description.toString() );
			return ex;
		}

		return ex;
	}

	//endregion

}
