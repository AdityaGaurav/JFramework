package com.framework.driver.objects;

import ch.lambdaj.Lambda;
import ch.lambdaj.function.convert.Converter;
import com.framework.driver.event.HtmlDriver;
import com.framework.driver.event.HtmlElement;
import com.framework.driver.event.HtmlObject;
import com.framework.utils.error.PreConditions;
import com.google.common.base.Optional;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
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

	private final List<RadioButton> radioButtons;

	//endregion


	//region RadioButtonGroup - Constructor Methods Section

	public RadioButtonGroup( List<HtmlElement> radioButtons )
	{
		String ERR_MSG1 = "The radio buttons list cannot be null.";
		String ERR_MSG2 = "The provided list should be greater than 1, however is < %d >";

		PreConditions.checkNotNull( radioButtons, ERR_MSG1, radioButtons.size() );
		PreConditions.checkArgument( radioButtons.size() > 1, ERR_MSG2, radioButtons.size() );

		this.radioButtons = HtmlObject.convertToRadioButton( radioButtons );
	}

	public RadioButtonGroup( HtmlDriver driver, By.ByName byName )
	{
		PreConditions.checkNotNull( driver, "HtmlDriver cannot be null!" );
		PreConditions.checkNotNull( byName, "ByName cannot be null!" );
		List<HtmlElement> elements = driver.findElements( byName );
		this.radioButtons = HtmlObject.convertToRadioButton( elements );
	}

	//endregion


	//region RadioButtonGroup - Public Methods Section

	public List<String> getValues()
	{
		return Lambda.convert( radioButtons, new Converter<RadioButton, String>()
		{
			@Override
			public String convert( final RadioButton from )
			{
				String value = from.getValue();
				return StringUtils.removePattern( value, "(\t|\n)" );
			}
		} );
	}

	public Optional<String> getSelectedValue()
	{
		for ( RadioButton radioButton : radioButtons )
		{
			if ( radioButton.getHtmlElement().isSelected() )
			{
				return Optional.of( radioButton.getValue() );
			}
		}
		return Optional.absent();
	}

	public void selectByValue( String value )
	{
		for ( RadioButton radioButton : radioButtons )
		{
			if ( value.equals( radioButton.getValue() ) )
			{
				radioButton.getHtmlElement().click();
				break;
			}
		}
	}

	public void selectByVisibleText( String label )
	{
		for ( RadioButton radioButton : radioButtons )
		{
			if ( label.equalsIgnoreCase( radioButton.getHtmlElement().getText() ) )
			{
				radioButton.getHtmlElement().click();
				break;
			}
		}
	}

	//endregion
}
