package com.framework.site.objects.footer;

import com.framework.asserts.JAssertion;
import com.framework.driver.event.HtmlElement;
import com.framework.driver.objects.AbstractWebObject;
import com.framework.utils.matchers.JMatchers;
import com.google.common.base.Optional;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.NoSuchElementException;

import static com.framework.utils.datetime.TimeConstants.FIVE_SECONDS;
import static com.framework.utils.datetime.TimeConstants.THREE_SECONDS;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.site.objects.footer
 *
 * Name   : FooterObject
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-01-06
 *
 * Time   : 16:34
 */

public class SectionFooterObject extends AbstractWebObject
{

	//region FooterObject - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( SectionFooterObject.class );

	public static final By ROOT_BY = By.id( "ccl-refresh-footer" );

	private static final String LOGICAL_NAME = "CCL Footer";

	// ------------------------------------------------------------------------|
	// --- WEB-OBJECTS DEFINITIONS --------------------------------------------|
	// ------------------------------------------------------------------------|

	private ZeroFooterObject zeroFooterDiv = null;

	private SubFooterObject subFooterDiv = null;

	private LinkListObject linkList = null;

	//endregion


	//region FooterObject - Constructor Methods Section

	public SectionFooterObject( final HtmlElement rootElement )
	{
		super( rootElement, LOGICAL_NAME );
		initWebObject();
	}

	//endregion


	//region FooterObject - Initialization and Validation Methods Section

	@Override
	protected void initWebObject()
	{
		logger.debug( "validating static elements for web object id: <{}>, name:<{}>...",
				getQualifier(), getLogicalName() );

		final String REASON = "assert that element \"%s\" exits";
		JAssertion assertion = getRoot().createAssertion();

		Optional<HtmlElement> e = getRoot().childExists( LinkListObject.ROOT_BY, FIVE_SECONDS );
		assertion.assertThat( String.format( REASON, ".link-list" ), e.isPresent(), JMatchers.is( true ) );

		e = getRoot().childExists( SubFooterObject.ROOT_BY, THREE_SECONDS );
		assertion.assertThat( String.format( REASON, ".sub-footer" ), e.isPresent(), JMatchers.is( true ) );

		e = getRoot().childExists( ZeroFooterObject.ROOT_BY, THREE_SECONDS );
		assertion.assertThat( String.format( REASON, ".zero-footer" ), e.isPresent(), JMatchers.is( true ) );
	}

	//endregion


	//region FooterObject - Service Methods Section

	public SubFooterObject subFooter()
	{
		if ( null == this.subFooterDiv )
		{
			this.subFooterDiv = new SubFooterObject( getSubFooterDiv()  );
		}
		return subFooterDiv;
	}

	public ZeroFooterObject zeroFooter()
	{
		if ( null == this.zeroFooterDiv )
		{
			this.zeroFooterDiv = new ZeroFooterObject( getZeroFooterDiv() );
		}
		return zeroFooterDiv;
	}

	public LinkListObject linkList()
	{
		if ( null == this.linkList )
		{
			this.linkList = new LinkListObject( getLinkListDiv() );
		}
		return linkList;
	}

	private HtmlElement getRoot()
	{
		return getBaseRootElement( ROOT_BY );
	}

	//endregion


	//region FooterObject - Business Methods Section

	public int getFooterSectionsCount()
	{
		return 0;
	}

	public boolean isDisplayed()
	{
		return getRoot().isDisplayed();
	}

	public boolean hasSubFooter()
	{
		try
		{
			return getSubFooterDiv() != null;
		}
		catch ( NoSuchElementException e )
		{
			return false;
		}
	}

	public boolean hasZeroFooter()
	{
		try
		{
			return getZeroFooterDiv() != null;
		}
		catch ( NoSuchElementException e )
		{
			return false;
		}
	}

	//endregion


	//region FooterObject - Element Finder Methods Section

	private HtmlElement getSubFooterDiv()
	{
		return getRoot().findElement( SubFooterObject.ROOT_BY );
	}

	private HtmlElement getZeroFooterDiv()
	{
		return getRoot().findElement( ZeroFooterObject.ROOT_BY );
	}

	private HtmlElement getLinkListDiv()
	{
		return getRoot().findElement( LinkListObject.ROOT_BY );
	}

	//endregion

}