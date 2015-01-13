package com.framework.site.objects.footer;

import com.framework.asserts.JAssertions;
import com.framework.driver.exceptions.ApplicationException;
import com.framework.driver.objects.AbstractWebObject;
import com.framework.driver.utils.ui.WaitUtil;
import com.framework.site.objects.footer.interfaces.Footer;
import com.google.common.base.MoreObjects;
import com.google.common.base.Throwables;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
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

public class FooterObject extends AbstractWebObject implements Footer
{

	//region FooterObject - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( FooterObject.class );

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

	public FooterObject( WebDriver driver, final WebElement rootElement )
	{
		super( LOGICAL_NAME, driver, rootElement );
	}

	//endregion


	//region FooterObject - Initialization and Validation Methods Section

	@Override
	protected void initWebObject()
	{
		logger.debug( "validating static elements for web object id: <{}>, name:<{}>...", getId(), getLogicalName() );
		WebDriverWait wew = WaitUtil.wait10( objectDriver );

		try
		{
			JAssertions.assertWaitThat( wew ).matchesCondition( WaitUtil.presenceOfChildBy( getRoot(), SubFooter.ROOT_BY ) );
			JAssertions.assertWaitThat( wew ).matchesCondition( WaitUtil.presenceOfChildBy( getRoot(), ZeroFooter.ROOT_BY ) );
		}
		catch ( AssertionError ae )
		{
			Throwables.propagateIfInstanceOf( ae, ApplicationException.class );
			logger.error( "throwing a new WebObjectException on FooterObject#initWebObject." );
			ApplicationException ex = new ApplicationException( objectDriver.getWrappedDriver(), ae.getMessage(), ae );
			ex.addInfo( "cause", "verification and initialization process for object " + getLogicalName() + " was failed." );
			throw ex;
		}
	}

	//endregion


	//region FooterObject - Service Methods Section

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper( this )
				.add( "logical name", getLogicalName() )
				.add( "id", getId() )
				.omitNullValues()
				.toString();
	}

	@Override
	public SubFooter subFooter()
	{
		if ( null == this.subFooterDiv )
		{
			this.subFooterDiv = new SubFooterObject( objectDriver, getSubFooterDiv()  );
		}
		return subFooterDiv;
	}

	@Override
	public ZeroFooter zeroFooter()
	{
		if ( null == this.zeroFooterDiv )
		{
			this.zeroFooterDiv = new ZeroFooterObject( objectDriver, getZeroFooterDiv() );
		}
		return zeroFooterDiv;
	}

	@Override
	public LinkList linkList()
	{
		if ( null == this.linkList )
		{
			this.linkList = new LinkListObject( objectDriver, getZeroFooterDiv() );
		}
		return linkList;
	}


	private WebElement getRoot()
	{
		try
		{
			rootElement.getTagName();
			return rootElement;
		}
		catch ( StaleElementReferenceException ex )
		{
			logger.warn( "auto recovering from StaleElementReferenceException ..." );
			return objectDriver.findElement( Footer.ROOT_BY );
		}
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