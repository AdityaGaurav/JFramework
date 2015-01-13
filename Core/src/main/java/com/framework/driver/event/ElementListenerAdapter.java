package com.framework.driver.event;

import com.framework.driver.highlight.HighlightHandler;
import com.framework.driver.highlight.HighlightStyle;
import com.framework.driver.highlight.HighlightStyleBackup;
import com.framework.utils.datetime.DateTimeUtils;
import com.google.common.base.Preconditions;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;


//todo: documentation

public class ElementListenerAdapter extends AbstractEventListener implements HighlightHandler
{

	//region ElementListenerAdapter - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( ElementListenerAdapter.class );

	private final Deque<HighlightStyleBackup> styleBackups = new ArrayDeque<HighlightStyleBackup>();

	private String elementDesc = null;

	//endregion


	public ElementListenerAdapter()
	{
		super();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void beforeFindBy( final By by, final WebElement element, final WebDriver driver )
	{
		this.dateTime = new DateTime();
		final String cu = driver.getCurrentUrl();
		final String handle = driver.getWindowHandle();
		final int handles = driver.getWindowHandles().size();
		final String MSG_FORMAT1 = "Searching for element[ {} ].child[ {} ] on \"{}\" ";
		final String MSG_FORMAT2 = MSG_FORMAT1 + "; current handle is <{}>  of total <{}> handles.";

		String desc = getDescription( element );
		if( handles == 1 )
			logger.debug( MSG_FORMAT1, desc, by, cu );
		else
			logger.debug( MSG_FORMAT2, desc, by, cu, handle, handles );
	}


	@Override
	public void afterFindBy( final By by, final WebElement element, final WebDriver driver )
	{
		Duration duration = new Duration( this.dateTime, DateTime.now() );  /* measuring between before and after */
		final String fp = DateTimeUtils.getFormattedPeriod( duration );
		final String MSG_FORMAT = "Element[ {} ].child[ {} ] found; ( duration: {} )";

		logger.debug( MSG_FORMAT, getDescription( element ), by, fp );
	}

	@Override
	public void afterFindBy( final By by, final WebElement element, int size, final WebDriver driver )
	{
		Duration duration = new Duration( this.dateTime, DateTime.now() );  /* measuring between before and after */
		final String fp = DateTimeUtils.getFormattedPeriod( duration );
		final String MSG_FORMAT = "Element[ {} ].child[ {} ] found; number of elements: {} ( duration: {} )";

		logger.debug( MSG_FORMAT, getDescription( element ), by, size, fp );
	}


	@Override
	public void beforeClickOn( final WebElement element, final WebDriver driver )
	{
		this.dateTime = new DateTime();
		final String cu = driver.getCurrentUrl();
		this.elementDesc = getDescription( element );
		final String handle = driver.getWindowHandle();
		final int handles = driver.getWindowHandles().size();
		final String MSG_FORMAT1 = "Clicking on web element[ {} ] on \"{}\"; current handle is <{}>";
		final String MSG_FORMAT2 = MSG_FORMAT1 + " of total <{}> handles.";

		if( handles == 1 )
			logger.debug( MSG_FORMAT1, this.elementDesc, cu, handle );
		else
			logger.debug( MSG_FORMAT2, this.elementDesc, cu, handle, handles );

		if ( isHighlight() )
		{
			highlight( driver, element, HighlightStyle.ELEMENT_STYLES[ 1 ] );
		}
	}


	@Override
	public void afterClickOn( final WebElement element, final WebDriver driver )
	{
		Duration duration = new Duration( this.dateTime, DateTime.now() );  /* measuring between before and after */
		final String fp = DateTimeUtils.getFormattedPeriod( duration );
		final String MSG_FORMAT = "Element [ {} ] was clicked; ( duration: {} )";

		logger.debug( MSG_FORMAT, this.elementDesc, fp );
	}


	@Override
	public void beforeSubmitOn( final WebElement element, final WebDriver driver )
	{
		this.dateTime = new DateTime();
		final String cu = driver.getCurrentUrl();
		this.elementDesc = getDescription( element );
		final String handle = driver.getWindowHandle();
		final int handles = driver.getWindowHandles().size();
		final String MSG_FORMAT1 = "Submitting web element[ {} ] on \"{}\"; current handle is <{}>";
		final String MSG_FORMAT2 = MSG_FORMAT1 + " of total <{}> handles.";

		if( handles == 1 )
			logger.debug( MSG_FORMAT1, this.elementDesc, cu, handle );
		else
			logger.debug( MSG_FORMAT2, this.elementDesc, cu, handle, handles );

		if ( isHighlight() )
		{
			highlight( driver, element, HighlightStyle.ELEMENT_STYLES[ 1 ] );
		}
	}

	@Override
	public void afterSubmitOn( final WebElement element, final WebDriver driver )
	{
		Duration duration = new Duration( this.dateTime, DateTime.now() );  /* measuring between before and after */
		final String fp = DateTimeUtils.getFormattedPeriod( duration );
		final String MSG_FORMAT = "Element [ {} ] was submitted; ( duration: {} )";

		logger.debug( MSG_FORMAT, this.elementDesc, fp );
	}

	@Override
	public void beforeHoverOn( final WebElement element, final WebDriver driver )
	{
		this.dateTime = new DateTime();
		final String cu = driver.getCurrentUrl();
		this.elementDesc = getDescription( element );
		final String handle = driver.getWindowHandle();
		final int handles = driver.getWindowHandles().size();
		final String MSG_FORMAT1 = "Hovering on web element[ {} ] on \"{}\"; current handle is <{}>";
		final String MSG_FORMAT2 = MSG_FORMAT1 + " of total <{}> handles.";

		if( handles == 1 )
			logger.debug( MSG_FORMAT1, this.elementDesc, cu, handle );
		else
			logger.debug( MSG_FORMAT2, this.elementDesc, cu, handle, handles );

		if ( isHighlight() )
		{
			highlight( driver, element, HighlightStyle.ELEMENT_STYLES[ 0 ] );
			sleep( 100 );
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void afterHoverOn( final WebElement element, final WebDriver driver )
	{
		Duration duration = new Duration( this.dateTime, DateTime.now() );  /* measuring between before and after */
		final String fp = DateTimeUtils.getFormattedPeriod( duration );
		final String MSG_FORMAT = "Element [ {} ] was hovered over; ( duration: {} )";

		logger.debug( MSG_FORMAT, this.elementDesc, fp );
		if ( isHighlight() )
		{
			sleep( 100 );
			unHighlight( driver );
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void beforeChangeValueOf( final WebElement element, final WebDriver driver )
	{
		this.dateTime = new DateTime();
		final String cu = driver.getCurrentUrl();
		this.elementDesc = getDescription( element );
		final String handle = driver.getWindowHandle();
		final int handles = driver.getWindowHandles().size();
		final String MSG_FORMAT1 = "Change value of web element[ {} ] on \"{}\"; current handle is <{}>";
		final String MSG_FORMAT2 = MSG_FORMAT1 + " of total <{}> handles.";

		if( handles == 1 )
			logger.debug( MSG_FORMAT1, this.elementDesc, cu, handle );
		else
			logger.debug( MSG_FORMAT2, this.elementDesc, cu, handle, handles );

		if ( isHighlight() )
		{
			highlight( driver, element, HighlightStyle.ELEMENT_STYLES[ 2 ] );
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void afterChangeValueOf( final WebElement element, final WebDriver driver )
	{
		Duration duration = new Duration( this.dateTime, DateTime.now() );  /* measuring between before and after */
		final String fp = DateTimeUtils.getFormattedPeriod( duration );
		final String MSG_FORMAT = "Element value changed [ {} ]; ( duration: {} )";

		logger.debug( MSG_FORMAT, this.elementDesc, fp );
	}




	/**
	 * Get locator highlighting.
	 *
	 * @return true if use locator highlighting.
	 */
	@Override
	public boolean isHighlight()
	{
		return true;
	}

	/**
	 * Get locator highlighting.
	 *
	 * @return true if use locator highlighting.
	 */
	@Override
	public void highlight( final WebDriver driver, final WebElement elementFinder, HighlightStyle style )
	{
		WebDriver drv = Preconditions.checkNotNull( driver, "WebDriver driver should not be null" );
		WebElement ewe = Preconditions.checkNotNull( elementFinder, "WebElement elementFinder should not be null" );

		//List<Locator> selectedFrameLocators = elementFinder.getCurrentFrameLocators();
		Map<String, String> prevStyles = style.doHighlight( drv, ewe );
		if ( prevStyles == null )
		{
			return;
		}
		HighlightStyleBackup backup = new HighlightStyleBackup( prevStyles, ewe );
		this.styleBackups.push( backup );
	}

	/**
	 * Un-highlight backed up styles.
	 *
	 * @param driver  a {@linkplain com.framework.driver.event.EventWebDriver}
	 */
	@Override
	public void unHighlight( final WebDriver driver )
	{
		WebDriver drv = Preconditions.checkNotNull( driver, "WebDriver driver should not be null" );

		while ( ! this.styleBackups.isEmpty() )
		{
			HighlightStyleBackup backup = this.styleBackups.pop();
			backup.restore( drv );
		}
	}
}
