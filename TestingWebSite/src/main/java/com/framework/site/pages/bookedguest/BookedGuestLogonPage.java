package com.framework.site.pages.bookedguest;

import com.framework.driver.event.ExpectedConditions;
import com.framework.driver.event.HtmlCondition;
import com.framework.driver.event.HtmlDriverWait;
import com.framework.driver.event.HtmlElement;
import com.framework.site.config.SiteProperty;
import com.framework.site.exceptions.BookedGuestLoginException;
import com.framework.site.pages.BaseCarnivalPage;
import com.framework.testing.annotations.DefaultUrl;
import com.framework.utils.matchers.JMatchers;
import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.site.pages.core
 *
 * Name   : CruiseToPage
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-01-10
 *
 * Time   : 01:17
 */

@DefaultUrl( value = "/BookedGuest/GuestManagement/MyCarnival/LogOn", matcher = "contains()" )
public class BookedGuestLogonPage extends BaseCarnivalPage
{

	//region MyBookingPage - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( BookedGuestLogonPage.class );

	private static final String LOGICAL_NAME = "Booked Guest Logon Page";

	private List<String> loginErrors;

	private List<String> validationErrors;

	// ------------------------------------------------------------------------|
	// --- WEB-OBJECTS DEFINITIONS --------------------------------------------|
	// ------------------------------------------------------------------------|

	//endregion


	//region MyBookingPage - Constructor Methods Section

	public BookedGuestLogonPage()
	{
		super( LOGICAL_NAME );
	}

	//endregion


	//region MyBookingPage - Initialization and Validation Methods Section

	protected void validatePageInitialState()
	{
		logger.debug( "validating static elements for: <{}>, name:<{}>...", getQualifier(), getLogicalName() );
	}

	@Override
	protected void validatePageTitle()
	{
		String title = ( String ) SiteProperty.BOOKED_GUEST_LOGON_TITLE.fromContext();
		final org.hamcrest.Matcher<String> EXPECTED_TITLE = JMatchers.equalToIgnoringCase( title );
		final String REASON = String.format( "Asserting \"%s\" page's title", LOGICAL_NAME );

		getDriver().assertThat( REASON, getTitle(), EXPECTED_TITLE );
	}

	//endregion


	//region MyBookingPage - Service Methods Section

	private boolean areLoginErrors()
	{
		this.loginErrors = Lists.newArrayList();
		List<HtmlElement> errors = findFieldErrors();
		if( errors.size() > 0 )
		{
			logger.error( "Booking Guest Login errors were found." );
			for( HtmlElement e : errors )
			{
				loginErrors.add( getError( e ).getText() );
			}
		}

		return loginErrors.size() > 0;
	}

	private boolean areValidationErrors()
	{
		Optional<HtmlElement> validation = findValidationError();
		if( validation.isPresent() )
		{
			logger.error( "Booking Guest Validation errors were found." );
			logger.error( validation.get().findElement( By.tagName( "span" ) ).getText() );
			List<HtmlElement> errors =  validation.get().findElements( By.tagName( "li" ) );
			this.validationErrors = Lists.newArrayListWithCapacity( errors.size() );
			for(  HtmlElement he : errors )
			{
				validationErrors.add( he.getText() );
			}
			return true;
		}
		return false;
	}

	//endregion


	//region MyBookingPage - Business Methods Section

	public void login( String usr, String pwd ) throws BookedGuestLoginException
	{
		HtmlElement usrInput = findUserNameInput();
		HtmlElement pwdInput = findUserPasswordInput();
		HtmlElement login = findLoginAnchor();

		usrInput.clear();
		usrInput.sendKeys( usr + Keys.TAB );
		pwdInput.clear();
		pwdInput.sendKeys( pwd + Keys.TAB );
		login.click();

		if ( areLoginErrors() )
		{
			throw new BookedGuestLoginException( loginErrors, getLoginContentBlock() );
		}
		if ( areValidationErrors() )
		{
			throw new BookedGuestLoginException( validationErrors, getLoginValidationContainer() );
		}
	}

	//endregion


	//region MyBookingPage - Element Finder Methods Section

	private HtmlElement findUserNameInput()
	{
		final By findBy = By.id( "username" );
		return getDriver().findElement( findBy );
	}

	private HtmlElement findUserPasswordInput()
	{
		final By findBy = By.id( "password" );
		return getDriver().findElement( findBy );
	}

	private HtmlElement findLoginAnchor()
	{
		final By findBy = By.id( "login" );
		return getDriver().findElement( findBy );
	}

	private HtmlElement getError( HtmlElement field )
	{
		final By findBy = By.className( "error" );
		return field.findElement( findBy );
	}

	private List<HtmlElement> findFieldErrors()
	{
		try
		{
			final By findBy = By.cssSelector( "div.field.err" );
			HtmlCondition<List<HtmlElement>> condition = ExpectedConditions.presenceOfAllBy( findBy );
			return HtmlDriverWait.wait5( getDriver() ).until( condition );
		}
		catch ( TimeoutException e )
		{
			return Lists.newArrayListWithExpectedSize( 0 );
		}
	}

	private HtmlElement getLoginValidationContainer()
	{
		final By findBy = By.cssSelector( "div.sidebar-content" );
		return getDriver().findElement( findBy );
	}

	private HtmlElement getLoginContentBlock()
	{
		final By findBy = By.cssSelector( ".content-block.equalize-target.login-module" );
		return getDriver().findElement( findBy );
	}

	private Optional<HtmlElement> findValidationError()
	{
		try
		{
			final By findBy = By.cssSelector( "validation-summary-errors validation-message error" );
			HtmlCondition<HtmlElement> condition = ExpectedConditions.presenceBy( findBy );
			return Optional.of( HtmlDriverWait.wait5( getDriver() ).until( condition ) );
		}
		catch ( TimeoutException e )
		{
			return Optional.absent();
		}

	}

	//endregion

}
