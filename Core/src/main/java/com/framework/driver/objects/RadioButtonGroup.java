package com.framework.driver.objects;

import com.framework.driver.event.EventWebDriver;
import com.framework.utils.error.PreConditions;
import com.google.common.base.Optional;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.driver.objects
 *
 * Name   : RadioButtonGroup
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-01-05
 *
 * Time   : 20:12
 */

public class RadioButtonGroup
{

	//region RadioButtonGroup - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( RadioButtonGroup.class );

	//private final List<WebElement> elements;

	private final List<RadioButton> radioButtons;

	private EventWebDriver driver;

	//endregion


	//region RadioButtonGroup - Constructor Methods Section

	public RadioButtonGroup( List<WebElement> radioButtons )
	{
		String ERR_MSG1 = "The radio buttons list cannot be null.";
		String ERR_MSG2 = "The provided list should be greater than 1, however is < %d >";

		PreConditions.checkNotNull( radioButtons, ERR_MSG1, radioButtons.size() );
		PreConditions.checkArgument( radioButtons.size() > 1, ERR_MSG2, radioButtons.size() );

		this.radioButtons = BaseElementObject.convertToRadioButton( radioButtons );
	}

	public RadioButtonGroup( WebDriver driver, By.ByName byName )
	{
		this.driver = ( EventWebDriver ) PreConditions.checkNotNull( driver, "WebDriver cannot be null!" );
		PreConditions.checkNotNull( byName, "ByName cannot be null!" );
		List<WebElement> elements = driver.findElements( byName );
		this.radioButtons = BaseElementObject.convertToRadioButton( elements );
	}

	//endregion


	//region RadioButtonGroup - Public Methods Section

	public List<String> getValues()
	{
		return BaseElementObject.extractAttribute( radioButtons, "value" );
	}

	public Optional<String> getSelectedValue()
	{
		for ( RadioButton radioButton : radioButtons )
		{
			if ( radioButton.isSelected() )
			{
				return Optional.of( radioButton.getAttribute( "value" ) );
			}
		}
		return Optional.absent();
	}

	public void selectByValue( String value )
	{
		for ( RadioButton radioButton : radioButtons )
		{
			if ( value.equals( radioButton.getAttribute( "value" ) ) )
			{
				radioButton.click();
				break;
			}
		}
	}

	public void selectByVisibleText( String label )
	{
		for ( RadioButton radioButton : radioButtons )
		{
			if ( label.equalsIgnoreCase( radioButton.getText() ) )
			{
				radioButton.click();
				break;
			}
		}
	}

	//endregion
}
