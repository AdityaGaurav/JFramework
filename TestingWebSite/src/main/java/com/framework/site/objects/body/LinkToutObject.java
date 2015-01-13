package com.framework.site.objects.body;

import com.framework.driver.objects.AbstractWebObject;
import com.framework.driver.utils.ui.ExtendedBy;
import com.framework.site.objects.body.interfaces.LinkTout;
import com.google.common.base.MoreObjects;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.site.objects.body
 *
 * Name   : LinkToutObject
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-01-06
 *
 * Time   : 19:55
 */

public class LinkToutObject extends AbstractWebObject implements LinkTout
{

	//region LinkToutObject - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( LinkToutObject.class );


	//endregion


	//region LinkToutObject - Constructor Methods Section

	public LinkToutObject( WebDriver driver, final WebElement rootElement )
	{
		super( LOGICAL_NAME, driver, rootElement );
	}

	//endregion


	//region LinkToutObject - Initialization and Validation Methods Section

	@Override
	protected void initWebObject()
	{

	}

	//endregion


	//region LinkToutObject - Service Methods Section

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper( this )
				.add( "logical name", getLogicalName() )
				.add( "id", getId() )
				.omitNullValues()
				.toString();
	}

	//endregion


	//region LinkToutObject - Business Methods Section

	//endregion


	//region LinkToutObject - Element Finder Methods Section

	private WebElement getLinkToutAnchor()
	{
		return rootElement.findElement( By.tagName( "a" ) );
	}

	private WebElement getLinkToutImg()
	{
		return rootElement.findElement( By.tagName( "img" ) );
	}

	private WebElement getH4Span()
	{
		return rootElement.findElement( ExtendedBy.composite( By.tagName( "span" ), By.className( "h4" ) ) );
	}

	private WebElement getParaSpan()
	{
		return rootElement.findElement( ExtendedBy.composite( By.tagName( "span" ), By.className( "para" ) ) );
	}

	private WebElement getLinkSpan()
	{
		return rootElement.findElement( ExtendedBy.composite( By.tagName( "span" ), By.className( "link" ) ) );
	}


	//endregion

}