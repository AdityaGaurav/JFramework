package com.framework.site.objects.body.common;

import com.framework.asserts.JAssertion;
import com.framework.driver.event.ExpectedConditions;
import com.framework.driver.event.HtmlDriverWait;
import com.framework.driver.event.HtmlElement;
import com.framework.driver.exceptions.ApplicationException;
import com.framework.driver.objects.AbstractWebObject;
import com.framework.driver.objects.Link;
import com.framework.site.objects.body.interfaces.BreadcrumbsBar;
import com.framework.utils.datetime.Sleeper;
import com.google.common.base.Optional;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.awt.event.KeyEvent;

import static com.framework.utils.datetime.TimeConstants.TWO_SECONDS;
import static org.hamcrest.Matchers.is;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.site.objects.body.common
 *
 * Name   : BreadcrumbsShareObject
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-01-12
 *
 * Time   : 12:17
 */

public class BreadcrumbsShareObject extends AbstractWebObject implements BreadcrumbsBar.Share
{

	//region BreadcrumbsShareObject - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( BreadcrumbsShareObject.class );

	// ------------------------------------------------------------------------|
	// --- WEB-OBJECTS CACHING ------------------------------------------------|
	// ------------------------------------------------------------------------|

	private HtmlElement print, iShare;

	//endregion


	//region BreadcrumbsShareObject - Constructor Methods Section

	public BreadcrumbsShareObject( final HtmlElement rootElement )
	{
		super( rootElement, BreadcrumbsBar.Share.LOGICAL_NAME );
		initWebObject();
	}

	//endregion


	//region BreadcrumbsShareObject - Initialization and Validation Methods Section

	@Override
	protected void initWebObject()
	{
		logger.debug( "validating static elements for web object id: <{}>, name:<{}>...",
				getQualifier(), getLogicalName() );

		final String REASON = "assert that element \"%s\" exits";
		JAssertion assertion = getRoot().createAssertion();

		Optional<HtmlElement> e = getRoot().childExists( By.id( "HeaderFBLike" ), TWO_SECONDS );
		assertion.assertThat( String.format( REASON, "#HeaderFBLike" ), e.isPresent(), is( true ) );

		e = getRoot().childExists( By.cssSelector( ".green.st_sharethis_custom" ), TWO_SECONDS );
		assertion.assertThat( String.format( REASON, ".green.st_sharethis_custom" ), e.isPresent(), is( true ) );
		iShare = e.get();

		e = getRoot().childExists( By.className( "print" ), TWO_SECONDS );
		assertion.assertThat( String.format( REASON, ".print" ), e.isPresent(), is( true ) );
		print = e.get();
	}


	//endregion


	//region BreadcrumbsShareObject - Service Methods Section

	private HtmlElement getRoot()
	{
		return getBaseRootElement( BreadcrumbsBar.Share.ROOT_BY );
	}

	//endregion


	//region BreadcrumbsShareObject - Business Methods Section

	@Override
	public void clickPrint()
	{
		/** adds a new tag when event onbeforeprint is fired by the browser */

		final String SCRIPT_LISTENER_BEFORE = "window.onbeforeprint = function(){\n" +
				"   var div = document.createElement(\"div\");\n" +
				"	div.setAttribute( \"id\", \"beforePrint\" );\n" +
				"   document.body.appendChild( div );\n" +
				"   window.close();\n" +
				"}";
		final String SCRIPT_LISTENER_AFTER = "window.onafterprint = function(){\n" +
				"   var div = document.createElement(\"div\");\n" +
				"	div.setAttribute( \"id\", \"afterPrint\" );\n" +
				"   document.body.appendChild( div );\n" +
				"}";

		// injecting session listeners
		getDriver().executeScript( SCRIPT_LISTENER_BEFORE );
		getDriver().executeScript( SCRIPT_LISTENER_AFTER );

		Link link = new Link( findPrint() );
		link.hover( true );
		getDriver().executeScript( "arguments[0].click()", findPrint() );
		//findPrint().jsClick();

		try
		{
			Sleeper.pauseFor( 2000 );
			Robot r = new Robot();
			r.keyPress( KeyEvent.VK_ESCAPE );
			r.keyRelease( KeyEvent.VK_ESCAPE );
		}
		catch ( AWTException e )
		{
			logger.error( e.getMessage() );
			throw new ApplicationException( e );
		}

		/* validates if div#beforePrint was created */

		logger.debug( "waiting for element beforePrint to be created..." );
		HtmlDriverWait.wait10( getDriver() ).until( ExpectedConditions.presenceBy( By.id( "beforePrint" ) ) );
		logger.debug( "waiting for element afterPrint to be created..." );
		HtmlDriverWait.wait5( getDriver() ).until( ExpectedConditions.presenceBy( By.id( "afterPrint" ) ) );
	}

	@Override
	public void clickShare()
	{
		//todo current a bug: cannot implement
	}

	//endregion


	//region BreadcrumbsShareObject - Element Finder Methods Section

//	private List<HtmlElement> getTopChickletsLis()
//	{
//		By findBy = By.id( "top_chicklets" );
//		return getRoot().findElement( findBy ).findElements( By.tagName( "li" ) );
//	}
//
//	private HtmlElement getChicletsSearchDiv()
//	{
//		By findBy = By.id( "chicklet_search" );
//		return getRoot().findElement( findBy );
//	}
//
//	private HtmlElement getAllChickletsDiv()
//	{
//		By findBy = By.id( "all_chicklets" );
//		return getRoot().findElement( findBy );       document.createElement("div"); myPara.setAttribute("id", "id_you_like");  // add the newly created element and its content into the DOM
//var currentDiv = document.getElementById("div1");
//	document.body.insertBefore(newDiv, currentDiv);
	//	}

	private HtmlElement findPrint()
	{
		if( null == print )
		{
			By findBy = By.className( "print" );
			print = getRoot().findElement( findBy );
		}

		return print;
	}

	private HtmlElement findIShare()
	{
		if( null == iShare )
		{
			By findBy = By.className( "i-share" );
			iShare = getRoot().findElement( findBy );
		}

		return iShare;
	}

	//endregion

}
