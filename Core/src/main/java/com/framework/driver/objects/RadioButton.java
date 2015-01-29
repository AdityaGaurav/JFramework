package com.framework.driver.objects;

import com.framework.utils.error.PreConditions;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.driver.objects
 *
 * Name   : RadioButton 
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-01-28 
 *
 * Time   : 12:40
 *
 */

public class RadioButton extends BaseElementObject
{

	//region RadioButton - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( RadioButton.class );

	//endregion


	//region RadioButton - Constructor Methods Section

	public RadioButton( final WebElement element )
	{
		super( element );

		final String ERR_MSG1 = "Invalid tag name found for radio button -> %s";
		final String ERR_MSG2 = "Invalid type found for radio button -> %s";

		String tagName = element.getTagName();
		String typeName = element.getAttribute( "type" );
		PreConditions.checkArgument( tagName.toLowerCase().equals( "input" ), ERR_MSG1, tagName );
		PreConditions.checkArgument( typeName.toLowerCase().equals( "radio" ), ERR_MSG2, typeName );

		logger.debug( "Creating a new Radio-Button object for ( tag:'{}', text:'{}' )", tagName, getText() );
	}


	//endregion


	//region RadioButton - Public Methods Section

	public String getValue()
	{
		return super.getAttribute( "value" );
	}

	@Override
	public String toString()
	{
		return new ToStringBuilder( this )
				.appendSuper( super.toString() )
				.append( "text", super.getText() )
				.append( "value", getValue() )
				.toString();
	}

	//endregion
}
