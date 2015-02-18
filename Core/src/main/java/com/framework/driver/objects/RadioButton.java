package com.framework.driver.objects;

import com.framework.driver.event.HtmlElement;
import com.framework.utils.error.PreConditions;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.math.NumberUtils;
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

public class RadioButton
{

	//region RadioButton - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( RadioButton.class );

	private HtmlElement element;

	private final String qualifier;

	private static long counter = NumberUtils.LONG_ZERO;

	//endregion


	//region RadioButton - Constructor Methods Section

	public RadioButton( final HtmlElement element )
	{
		final String ERR_MSG1 = "Invalid tag name found for radio button -> %s";
		final String ERR_MSG2 = "Invalid type found for radio button -> %s";

		String tagName = element.getTagName();
		String typeName = element.getAttribute( "type" );
		PreConditions.checkArgument( tagName.toLowerCase().equals( "input" ), ERR_MSG1, tagName );
		PreConditions.checkArgument( typeName.toLowerCase().equals( "radio" ), ERR_MSG2, typeName );

		this.element = element;
		this.qualifier = String.format( "LINK[%d]", ++ counter );
		logger.debug( "Created a new Radio-Button element < {} >", qualifier );
	}


	//endregion


	//region RadioButton - Public Methods Section

	public String getValue()
	{
		return element.getAttribute( "value" );
	}

	public String getName()
	{
		return element.getAttribute( "name" );
	}

	public String getQualifier()
	{
		return qualifier;
	}

	public HtmlElement getHtmlElement()
	{
		return element;
	}

	@Override
	public String toString()
	{
		return new ToStringBuilder( this )
				.appendSuper( super.toString() )
				.append( "qualifier", qualifier )
				.append( "name", getName() )
				.append( "value", getValue() )
				.toString();
	}

	//endregion
}
