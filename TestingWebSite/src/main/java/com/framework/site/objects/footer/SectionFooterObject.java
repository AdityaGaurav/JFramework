package com.framework.site.objects.footer;

import com.framework.asserts.JAssertion;
import com.framework.driver.objects.AbstractWebObject;
import com.framework.driver.utils.ui.WaitUtil;
import com.framework.site.objects.footer.interfaces.Footer;
import com.framework.utils.datetime.TimeConstants;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.NoSuchElementException;


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

public class SectionFooterObject extends AbstractWebObject implements Footer
{

	//region FooterObject - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( SectionFooterObject.class );

	private static final By footerBy = By.id( "ccl-refresh-footer" );
	private WebElement cclFooter = null;

	// ------------------------------------------------------------------------|
	// --- WEB-OBJECTS DEFINITIONS --------------------------------------------|
	// ------------------------------------------------------------------------|

	private ZeroFooter zeroFooterDiv = null;

	private SubFooter subFooterDiv = null;

	private LinkList linkList = null;

	//endregion


	//region FooterObject - Constructor Methods Section

	public SectionFooterObject( WebDriver driver, final WebElement rootElement )
	{
		super( driver, rootElement, Footer.LOGICAL_NAME );
	}

	//endregion


	//region FooterObject - Initialization and Validation Methods Section

	@Override
	protected void initWebObject()
	{
		JAssertion assertion = new JAssertion( getWrappedDriver() );

		assertion.assertWaitThat(
				"assert that element \".link-list\" exits",
				TimeConstants.FIFTY_HUNDRED_MILLIS,
				WaitUtil.presenceOfChildBy( getRoot(), LinkList.ROOT_BY ) );
		assertion.assertWaitThat(
				"assert that element \".sub-footer\" exits",
				TimeConstants.FIFTEEN_HUNDRED_MILLIS,
				WaitUtil.presenceOfChildBy( getRoot(), SubFooter.ROOT_BY ) );
		assertion.assertWaitThat(
				"assert that element \".zero-footer\" exits",
				TimeConstants.FIFTY_HUNDRED_MILLIS,
				WaitUtil.presenceOfChildBy( getRoot(), ZeroFooter.ROOT_BY ) );
	}

	//endregion


	//region FooterObject - Service Methods Section

	@Override
	public SubFooter subFooter()
	{
		if ( null == this.subFooterDiv )
		{
			this.subFooterDiv = new SubFooterObject( getWrappedDriver(), getSubFooterDiv()  );
		}
		return subFooterDiv;
	}

	@Override
	public ZeroFooter zeroFooter()
	{
		if ( null == this.zeroFooterDiv )
		{
			this.zeroFooterDiv = new ZeroFooterObject( getWrappedDriver(), getZeroFooterDiv() );
		}
		return zeroFooterDiv;
	}

	@Override
	public LinkList linkList()
	{
		if ( null == this.linkList )
		{
			this.linkList = new LinkListObject( getWrappedDriver(), getZeroFooterDiv() );
		}
		return linkList;
	}

	private WebElement getRoot()
	{
		return getBaseRootElement( Footer.ROOT_BY );
	}

	//endregion


	//region FooterObject - Business Methods Section

	@Override
	public int getFooterSectionsCount()
	{
		return 0;
	}

	@Override
	public boolean isDisplayed()
	{
		return getRoot().isDisplayed();
	}

	@Override
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

	@Override
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

	private WebElement getSubFooterDiv()
	{
		return getRoot().findElement( SubFooter.ROOT_BY );
	}

	private WebElement getZeroFooterDiv()
	{
		return getRoot().findElement( ZeroFooter.ROOT_BY );
	}

	//endregion

}