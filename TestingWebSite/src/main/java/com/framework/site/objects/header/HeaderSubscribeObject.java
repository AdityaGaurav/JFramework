package com.framework.site.objects.header;

import com.framework.asserts.JAssertion;
import com.framework.driver.event.HtmlElement;
import com.framework.driver.objects.AbstractWebObject;
import com.framework.site.objects.header.interfaces.Header;
import com.google.common.base.Optional;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.framework.utils.datetime.TimeConstants.FIVE_SECONDS;
import static org.hamcrest.Matchers.is;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.site.objects.header
 *
 * Name   : HeaderSubscribeObject
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-01-06
 *
 * Time   : 10:08
 */

class HeaderSubscribeObject extends AbstractWebObject implements Header.HeaderSubscribe
{

	//region HeaderSubscribeObject - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( HeaderSubscribeObject.class );

	//endregion


	//region HeaderSubscribeObject - Constructor Methods Section

	HeaderSubscribeObject( final HtmlElement rootElement )
	{
		super( rootElement, Header.HeaderSubscribe.LOGICAL_NAME );
		initWebObject();
	}

	//endregion


	//region HeaderSubscribeObject - Initialization and Validation Methods Section

	@Override
	protected void initWebObject()
	{
		logger.debug( "validating static elements for web object id: <{}>, name:<{}>...",
				getQualifier(), getLogicalName() );

		final String REASON = "assert that element \"%s\" exits";
		JAssertion assertion = getRoot().createAssertion();

		Optional<HtmlElement> e = getRoot().childExists( By.id( "emailOnlyForm" ), FIVE_SECONDS );
		assertion.assertThat( String.format( REASON, "#emailOnlyForm" ), e.isPresent(), is( true ) );
	}

	//endregion


	//region HeaderSubscribeObject - Service Methods Section

	private HtmlElement getRoot()
	{
		return getBaseRootElement( Header.HeaderSubscribe.ROOT_BY );
	}

	//endregion


	//region HeaderSubscribeObject - Business Methods Section

	@Override
	public boolean isDisplayed()
	{
		return getRoot().isDisplayed();
	}

	@Override
	public void subscribe( final String email, final boolean waitAlert )
	{

	}

	//endregion


	//region HeaderSubscribeObject - Element Finder Methods Section

	/**
	 * Full xpath  : {@code /html/body/div[4]/div[1]/div[1]/div[1]/div[4]/div/form}
	 * Short xpath : {@code //*[@id='emailOnlyForm']}
	 * Css selector: {@code form#emailOnlyForm}
	 *
	 * @return The {@linkplain org.openqa.selenium.WebElement}
	 */
	private HtmlElement getSubscribeForm()
	{
		return getRoot().findElement( By.id( "emailOnlyForm" ) );
	}

	/**
	 * Full xpath  : {@code /html/body/div[4]/div[1]/div[1]/div[1]/div[4]/div/form/input[6]}
	 * Short xpath : {@code //input[@id='email']}
	 * Css selector: {@code form#emailOnlyForm input#email.email.white}
	 *
	 * @return The {@linkplain org.openqa.selenium.WebElement}
	 */
	private HtmlElement getEmailInput()
	{
		return getSubscribeForm().findElement( By.cssSelector( "input#email.email.white" ) );
	}

	/**
	 * Full xpath  : {@code /html/body/div[4]/div[1]/div[1]/div[1]/div[4]/div/form/a}
	 * Css selector: {@code form#emailOnlyForm a#submit.email-subscribe-submit-btn}
	 *
	 * @return The {@linkplain org.openqa.selenium.WebElement}
	 */
	private HtmlElement getEmailSubmitAnchor()
	{
		return getSubscribeForm().findElement( By.id( "submit" ) );
	}

	/**
	 * Full xpath  : {@code /html/body/div[4]/div[1]/div[1]/div[1]/div[4]/div/a}
	 * Css selector: {@code div#ccl-refresh-header div.header-nav div.header-subscribe div.max-width a.close}
	 *
	 * @return The {@linkplain org.openqa.selenium.WebElement}
	 */
	private HtmlElement getCloseAnchor()
	{
		return getRoot().findElement( By.className( "close" ) );
	}

	//endregion

}
