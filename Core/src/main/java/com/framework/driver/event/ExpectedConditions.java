package com.framework.driver.event;

import com.framework.utils.datetime.DateTimeUtils;
import com.framework.utils.error.PreConditions;
import org.apache.commons.lang3.SystemUtils;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.openqa.selenium.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.util.List;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.driver.event
 *
 * Name   : ExpectedConditions 
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-02-05 
 *
 * Time   : 01:22
 *
 */

public class ExpectedConditions
{

	//region ExpectedConditions - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( ExpectedConditions.class );

	private static boolean disableDoubleLog = false;

	//endregion


	//region ExpectedConditions - ExpectedCondition<Boolean> General Methods Section

	public static HtmlCondition<Boolean> urlMatches( final Matcher<String> matcher )
	{
		final DateTime dt = DateTime.now();

		return new HtmlCondition<Boolean>()
		{
			@Override
			public Boolean apply( HtmlDriver input )
			{
				PreConditions.checkNotNull( input, "HtmlDriver cannot be null." );
				final Description desc = new StringDescription().appendText( this.toString() );

				String currentUrl = input.getCurrentUrl().toLowerCase();
				boolean response = matcher.matches( currentUrl );
				if ( ! response ) desc.appendText( ", however current url is <'" ).appendValue( currentUrl ).appendText( "'>" );
				logger.debug( getDurationString( dt, desc ) );

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

	//todo: method documentation
	public static HtmlCondition<Boolean> titleMatches( final Matcher<String> matcher )
	{
		final DateTime dt = DateTime.now();

		return new HtmlCondition<Boolean>()
		{
			@Override
			public Boolean apply( HtmlDriver input )
			{
				PreConditions.checkNotNull( input, "HtmlDriver cannot be null." );
				final Description desc = new StringDescription().appendText( this.toString() );

				String currentTitle = input.getTitle();
				boolean response = matcher.matches( currentTitle );
				if ( ! response )  desc.appendText( ", however current title is <" ).appendValue( currentTitle ).appendText( ">" );
				logger.debug( getDurationString( dt, desc ) );

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

	//todo: method documentation
	public static HtmlCondition<Cookie> cookieIsPresent( final String cookieName )
	{
		final DateTime dt = DateTime.now();

		return new HtmlCondition<Cookie>()
		{
			@Override
			public Cookie apply( HtmlDriver input )
			{
				PreConditions.checkNotNull( input, "HtmlDriver cannot be null." );
				final Description desc = new StringDescription().appendText( this.toString() );

				Cookie cookie = input.manage().getCookieNamed( cookieName );
				boolean response = cookie != null;
				if ( ! response )  desc.appendText( ", however cookie is not is present" );
				logger.debug( getDurationString( dt, desc ) );

				return response ? cookie : null;
			}

			@Override
			public String toString()
			{
				return "presence of cookie named <'" + cookieName + "'>";
			}
		};
	}

	//todo: method documentation
	public static HtmlCondition<Boolean> not( final HtmlCondition<?> condition )
	{
		final DateTime dt = DateTime.now();

		return new HtmlCondition<Boolean>()
		{
			@Override
			public Boolean apply( HtmlDriver input )
			{
				PreConditions.checkNotNull( input, "HtmlDriver cannot be null." );
				final Description desc = new StringDescription().appendText( this.toString() );

				disableDoubleLog = true;
				Object result = condition.apply( input );
				disableDoubleLog = false;
				boolean response = ( result == null || result == Boolean.FALSE );
				desc.appendValue( " is " + response );
				logger.debug( getDurationString( dt, desc ) );

				return response;
			}

			@Override
			public String toString()
			{
				return "condition to not be valid: <" + condition + ">";
			}
		};
	}

	//todo: method documentation
	public static HtmlCondition<Boolean> jQueryToBeActive()
	{
		final DateTime dt = DateTime.now();

		return new HtmlCondition<Boolean>()
		{
			@Override
			public Boolean apply( final HtmlDriver input )
			{
				PreConditions.checkNotNull( input, "HtmlDriver cannot be null." );
				final Description desc = new StringDescription().appendText( this.toString() );

				JavascriptExecutor js = ( JavascriptExecutor ) input;
				logger.debug( getDurationString( dt, desc ) );
				return ( Boolean ) js.executeScript( "return jQuery.active == 0" );
			}

			@Override
			public String toString()
			{
				return "jQuery to be active";
			}
		};
	}

	//endregion


	//region ExpectedConditions - ExpectedCondition<WebElement> ExpectedCondition<List<WebElement>> Presence Methods Section

	//todo: method documentation
	public static HtmlCondition<HtmlElement> presenceBy( final By locator )
	{
		final DateTime dt = DateTime.now();

		return new HtmlCondition<HtmlElement>()
		{
			@Override
			public HtmlElement apply( HtmlDriver input )
			{
				PreConditions.checkNotNull( input, "HtmlDriver cannot be null." );
				final Description desc = new StringDescription().appendText( this.toString() );

				HtmlElement element = input.findElement( locator );
				if( ! disableDoubleLog ) logger.debug( getDurationString( dt, desc ) );
				return ( null != element ) ? element : null;
			}

			@Override
			public String toString()
			{
				return "presence of element by < " + locator + " >";
			}
		};
	}

	//todo: method documentation
	public static HtmlCondition<List<HtmlElement>> presenceOfAllBy( final By locator )
	{
		final DateTime dt = DateTime.now();

		return new HtmlCondition<List<HtmlElement>>()
		{
			@Override
			public List<HtmlElement> apply( HtmlDriver input )
			{
				PreConditions.checkNotNull( input, "HtmlDriver cannot be null." );
				final Description desc = new StringDescription().appendText( this.toString() );

				List<HtmlElement> elements = input.findElements( locator );
				if ( ! disableDoubleLog ) logger.debug( getDurationString( dt, desc ) );
				return elements.size() > 0 ? elements : null;
			}

			@Override
			public String toString()
			{
				return "presence of all elements by <" + locator + ">";
			}
		};
	}

	public static HtmlCondition<HtmlElement> presenceOfChildBy( final HtmlElement element, final By locator )
	{
		final DateTime dt = DateTime.now();

		return new HtmlCondition<HtmlElement>()
		{
			@Override
			public HtmlElement apply( HtmlDriver input )
			{
				PreConditions.checkNotNull( input, "HtmlDriver cannot be null." );
				final Description desc = new StringDescription().appendText( this.toString() );

				try
				{
					HtmlElement e = element.findElement( locator );
					if( ! disableDoubleLog ) logger.debug( getDurationString( dt, desc ) );
					return ( null != e ) ? e : null;
				}
				catch ( WebDriverException e )
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

	public static HtmlCondition<List<HtmlElement>> presenceOfAllChildrenBy( final HtmlElement element, final By locator )
	{
		final DateTime dt = DateTime.now();

		return new HtmlCondition<List<HtmlElement>>()
		{
			@Override
			public List<HtmlElement> apply( HtmlDriver input )
			{
				PreConditions.checkNotNull( input, "HtmlDriver cannot be null." );
				final Description desc = new StringDescription().appendText( this.toString() );

				try
				{
					List<HtmlElement> elements = element.findElements( locator );
					if ( ! disableDoubleLog ) logger.debug( getDurationString( dt, desc ) );
					return elements.size() > 0 ? elements : null;
				}

				// ------------------------------------------------------------------------------------------------------
				// When searching child element parent can throw StaleElementReferenceException
				// ------------------------------------------------------------------------------------------------------

				catch ( WebDriverException ex )
				{
					return null;
				}
			}

			@Override
			public String toString()
			{
				return "presence of all child's elements by " + locator;
			}
		};
	}

	//endregion


	//region ExpectedConditions - ExpectedCondition<WebElement> ExpectedCondition<List<WebElement>> Visibility Methods Section

	//todo: method documentation
	public static HtmlCondition<Boolean> visibilityOf( final HtmlElement element, final boolean visible )
	{
		final DateTime dt = DateTime.now();

		return new HtmlCondition<Boolean>()
		{
			@Override
			public Boolean apply( HtmlDriver input )
			{
				try
				{
					PreConditions.checkNotNull( input, "HtmlDriver cannot be null." );
					final Description desc = new StringDescription().appendText( this.toString() );

					boolean response = element.isDisplayed() == visible;
					logger.debug( getDurationString( dt, desc ) );

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
				return "element <" + element.getLocator() + ">" + ( visible ? "to" : "not to" ) + " be displayed.";
			}
		};
	}

	//todo: method documentation
	public static HtmlCondition<HtmlElement> visibilityBy( final By locator, final boolean visible )
	{
		final DateTime dt = DateTime.now();

		return new HtmlCondition<HtmlElement>()
		{
			public HtmlCondition<HtmlElement> presenceOf = presenceBy( locator );

			@Override
			public HtmlElement apply( HtmlDriver input )
			{
				PreConditions.checkNotNull( input, "HtmlDriver cannot be null." );
				final Description desc = new StringDescription().appendText( this.toString() );

				disableDoubleLog = true;
				HtmlElement element = presenceOf.apply( input );
				disableDoubleLog = false;

				if( null == element )
				{
					throw new NoSuchElementException( "element '" + locator + "' does not exists in the DOM." );
				}

				boolean response = element.isDisplayed();
				logger.debug( getDurationString( dt, desc ) );
				return ( response == visible ) ? element : null;
			}

			public String toString()
			{
				return "element <" + locator + "> " + ( visible ? "to" : "not to" ) + " be displayed.";
			}
		};
	}

	public static HtmlCondition<Boolean> visibilityOfAll( final List<HtmlElement> elements, final boolean visible )
	{
		final DateTime dt = DateTime.now();

		return new HtmlCondition<Boolean>()
		{
			@Override
			public Boolean apply( HtmlDriver input )
			{
				PreConditions.checkNotNull( input, "HtmlDriver cannot be null." );
				final Description desc = new StringDescription().appendText( this.toString() );
				try
				{
					for ( HtmlElement element : elements )
					{
						if ( element.isDisplayed() != visible )
						{
							logger.debug( getDurationString( dt, desc ) );
							return false;
						}
					}

					logger.debug( getDurationString( dt, desc ) );
				}
				catch ( StaleElementReferenceException serEx )
				{
					serEx.addInfo( "procedure name", "ExpectedCondition<Boolean> visibilityOfAll" );
					throw serEx;
				}

				return elements.size() > 0;
			}

			@Override
			public String toString()
			{
				return "waiting for " + elements.size() + " elements " + ( visible ? "to" : "not to" ) + " be displayed.";
			}
		};
	}

	//todo: method documentation
	public static HtmlCondition<List<HtmlElement>> visibilityOfAllBy( final By locator, final boolean visible )
	{
		final DateTime dt = DateTime.now();

		return new HtmlCondition<List<HtmlElement>>()
		{
			public HtmlCondition<List<HtmlElement>> presenceOfAllBy = presenceOfAllBy( locator );
			int index = 0;

			@Override
			public List<HtmlElement> apply( HtmlDriver input )
			{
				PreConditions.checkNotNull( input, "HtmlDriver cannot be null." );
				final Description desc = new StringDescription().appendText( this.toString() );

				List<HtmlElement> elements;
				try
				{
					disableDoubleLog = true;
					elements = presenceOfAllBy.apply( input );
					disableDoubleLog = false;
					if ( null == elements )
					{
						logger.debug( getDurationString( dt, desc ) );
						return null;
					}

					for ( HtmlElement element : elements )
					{
						if ( element.isDisplayed() != visible )
						{
							logger.debug( getDurationString( dt, desc ) );
							return null;
						}
						index++;
					}

					logger.debug( getDurationString( dt, desc ) );
					return elements.size() > 0 ? elements : null;
				}
				catch ( WebDriverException e )
				{
					e.addInfo( "cause", String.format( "WebDriverException thrown by findElement(%s)", locator ) );
					throw e;
				}
			}

			@Override
			public String toString()
			{
				return "all elements <" + locator + "> " + ( visible ? "to" : "not to" ) + " be displayed.";
			}
		};
	}

	//endregion


	//region ExpectedConditions - ExpectedCondition<Boolean> States and Statuses Methods Section

	//todo: method documentation
	public static HtmlCondition<Boolean> elementToBeEnabled( final HtmlElement element, final boolean enabled )
	{
		final DateTime dt = DateTime.now();

		return new HtmlCondition<Boolean>()
		{
			@Override
			public Boolean apply( HtmlDriver input )
			{
				try
				{
					PreConditions.checkNotNull( input, "HtmlDriver cannot be null." );
					final Description desc = new StringDescription().appendText( this.toString() );

					boolean response = element.isEnabled() == enabled;
					logger.debug( getDurationString( dt, desc ) );

					return response;
				}
				catch ( WebDriverException ex )
				{
					ex.addInfo( "procedure name", "EventContextCondition<Boolean> elementToBeEnabled" );
					throw ex;
				}
			}

			@Override
			public String toString()
			{
				return "element <" +  element.getLocator() + ">" + ( enabled ? "to" : "not to" ) + " be enabled.";
			}
		};
	}


	//todo: method documentation
	public static HtmlCondition<Boolean> elementToBeSelected( final HtmlElement element, final boolean selected )
	{
		final DateTime dt = DateTime.now();

		return new HtmlCondition<Boolean>()
		{
			@Override
			public Boolean apply( HtmlDriver input )
			{
				try
				{
					PreConditions.checkNotNull( input, "HtmlDriver cannot be null." );
					final Description desc = new StringDescription().appendText( this.toString() );

					boolean response = element.isSelected() == selected;
					logger.debug( getDurationString( dt, desc ) );
					return response;
				}
				catch ( StaleElementReferenceException ex )
				{
					ex.addInfo( "procedure name", "ExpectedCondition<Boolean> elementToBeSelected" );
					throw ex;
				}
			}

			@Override
			public String toString()
			{
				return "element <" + element.getLocator() + ">" + ( selected ? "to" : "not to" ) + " be selected.";
			}
		};
	}

	//todo: method documentation
	public static HtmlCondition<Boolean> elementAttributeToMatch( final HtmlElement element,
																	  	  final String attributeName,
																	      final Matcher<String> matcher )
	{
		final DateTime dt = DateTime.now();

		return new HtmlCondition<Boolean>()
		{
			@Override
			public Boolean apply( HtmlDriver input )
			{
				try
				{
					PreConditions.checkNotNull( input, "HtmlDriver cannot be null." );
					final Description desc = new StringDescription().appendText( this.toString() );

					String attributeValue = element.getAttribute( attributeName );
					String urlDecoded;
					try
					{
						urlDecoded = java.net.URLDecoder.decode( attributeValue, SystemUtils.FILE_ENCODING );
					}
					catch ( UnsupportedEncodingException e )
					{
						urlDecoded = attributeValue;
					}
					boolean response = matcher.matches( urlDecoded );

					if ( ! response )
					{
						desc.appendText( ", however attribute " )
								.appendValue( attributeName )
								.appendText( " is <" )
								.appendValue( attributeValue )
								.appendText( ">" );
					}
					logger.debug( getDurationString( dt, desc ) );
					return response;

				}
				catch ( WebDriverException ex )
				{
					ex.addInfo( "procedure name", "EventContextCondition<Boolean> elementsAttributeToMatch" );
					throw ex;
				}
			}

			@Override
			public String toString()
			{
				final Description description = new StringDescription()
						.appendText( "element " )
						.appendValue( element )
						.appendText( " attribute " )
						.appendValue( attributeName )
						.appendText( " value matches " )
						.appendDescriptionOf( matcher );
				return description.toString();
			}
		};
	}

	//todo: method documentation
	public static HtmlCondition<Boolean> elementCssPropertyToMatch( final HtmlElement element,
																		    final String propertyName,
																		    final Matcher<String> matcher )
	{
		final DateTime dt = DateTime.now();

		return new HtmlCondition<Boolean>()
		{
			@Override
			public Boolean apply( HtmlDriver input )
			{
				try
				{
					PreConditions.checkNotNull( input, "HtmlDriver cannot be null." );
					final Description desc = new StringDescription().appendText( this.toString() );

					String propertyValue = element.getCssValue( propertyName );
					boolean response = matcher.matches( propertyValue );

					if ( ! response )
					{
						desc.appendText( ", however css property" )
								.appendValue( propertyName )
								.appendText( "is <" )
								.appendValue( propertyValue )
								.appendText( ">" );
					}
					logger.debug( getDurationString( dt, desc ) );
					return response;
				}
				catch ( WebDriverException ex )
				{
					ex.addInfo( "procedure name", "EventContextCondition<Boolean> elementCssPropertyToMatch" );
					throw ex;
				}
			}

			@Override
			public String toString()
			{
				final Description description = new StringDescription()
						.appendText( "element " )
						.appendValue( element )
						.appendText( " css property " )
						.appendValue( propertyName )
						.appendText( " value matches " )
						.appendDescriptionOf( matcher );
				return description.toString();
			}
		};
	}

	//todo: method documentation
	public static HtmlCondition<Boolean> elementTextToMatch( final HtmlElement element,
																     final Matcher<String> matcher )
	{
		final DateTime dt = DateTime.now();

		return new HtmlCondition<Boolean>()
		{
			@Override
			public Boolean apply( HtmlDriver input )
			{
				try
				{
					PreConditions.checkNotNull( input, "HtmlDriver cannot be null." );
					final Description desc = new StringDescription().appendText( this.toString() );

					String text = element.getText();
					boolean response = matcher.matches( text );

					if ( ! response )
					{
						desc.appendText( ", however text value is <" ).appendValue( text ).appendText( ">" );
					}
					logger.debug( getDurationString( dt, desc ) );
					return response;
				}
				catch ( WebDriverException ex )
				{
					ex.addInfo( "procedure name", "EventContextCondition<Boolean> elementTextToMatch" );
					throw ex;
				}
			}

			@Override
			public String toString()
			{
				final Description description = new StringDescription()
						.appendText( "element " )
						.appendValue( element )
						.appendText( " text value matches " )
						.appendDescriptionOf( matcher );
				return description.toString();
			}
		};
	}

	//endregion


	//region WaitUtil - ExpectedCondition<WebDriver> Frames Window Handles and Alerts Methods Section

	public static HtmlCondition<Boolean> frameToBeAvailableBy( final String frameLocator, final boolean available )
	{
		final DateTime dt = DateTime.now();

		return new HtmlCondition<Boolean>()
		{
			@Override
			public Boolean apply( HtmlDriver input )
			{
				PreConditions.checkNotNull( input, "HtmlDriver cannot be null." );
				final Description desc = new StringDescription().appendText( this.toString() );

				try
				{
					boolean response = input.switchTo().frame( frameLocator );
					logger.debug( getDurationString( dt, desc ) );
					return response;
				}
				catch ( WebDriverException e )
				{
					return false;
				}
			}

			@Override
			public String toString()
			{
				return "frame to be available: " + frameLocator;
			}
		};
	}

	public static HtmlCondition<Boolean> frameToBeAvailableBy( final By frameLocator )
	{
		final DateTime dt = DateTime.now();

		return new HtmlCondition<Boolean>()
		{
			@Override
			public Boolean apply( HtmlDriver input )
			{
				PreConditions.checkNotNull( input, "HtmlDriver cannot be null." );
				final Description desc = new StringDescription().appendText( this.toString() );

				try
				{
					boolean response = input.switchTo().frame( input.findElement( frameLocator ) );
					logger.debug( getDurationString( dt, desc ) );
					return response;
				}
				catch ( WebDriverException e )
				{
					return false;
				}
			}

			@Override
			public String toString()
			{
				return "frame to be available: " + frameLocator;
			}
		};
	}

	public static HtmlCondition<Alert> alertIsPresent()
	{
		final DateTime dt = DateTime.now();

		return new HtmlCondition<Alert>()
		{
			@Override
			public Alert apply( HtmlDriver input )
			{
				PreConditions.checkNotNull( input, "HtmlDriver cannot be null." );
				final Description desc = new StringDescription().appendText( this.toString() );

				try
				{
					Alert alert = input.switchTo().alert();
					logger.debug( getDurationString( dt, desc ) );
					return alert;
				}
				catch ( NoAlertPresentException e )
				{
					return null;
				}
			}

			@Override
			public String toString()
			{
				return "alert to be present";
			}
		};
	}

	public static HtmlCondition<Boolean> windowHandlesCountToBe( final int handles )
	{
		final DateTime dt = DateTime.now();

		return new HtmlCondition<Boolean>()
		{
			@Override
			public Boolean apply( HtmlDriver input )
			{
				PreConditions.checkNotNull( input, "HtmlDriver cannot be null." );
				final Description desc = new StringDescription().appendText( this.toString() );

				try
				{
					int response = input.getWindowHandles().size();
					logger.debug( getDurationString( dt, desc ) );
					return response == handles;
				}
				catch ( WebDriverException e )
				{
					return false;
				}
			}

			@Override
			public String toString()
			{
				return  " window handles to be present: " + handles;
			}
		};
	}

	//endregion


	private static String getDurationString( final DateTime s, Description d )
	{
		Duration duration = /* measuring between before and after */ new Duration( DateTime.now().getMillis() - s.getMillis() );
		String durationString = DateTimeUtils.getFormattedPeriod( duration );
		return d.appendText( "  [ elapsed time: " ).appendValue( durationString ).appendText( " ]" ).toString();
	}
}
