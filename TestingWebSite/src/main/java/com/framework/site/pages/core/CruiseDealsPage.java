package com.framework.site.pages.core;

import com.framework.site.pages.CarnivalPage;
import com.google.common.base.MoreObjects;
import com.google.common.collect.Lists;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.site.pages.core
 *
 * Name   : CruiseDealsPage
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-01-06
 *
 * Time   : 11:48
 */

public class CruiseDealsPage extends CarnivalPage
{

	//region CruiseDealsPage - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( CruiseDealsPage.class );

	private static final String LOGICAL_NAME = "Cruise Deals Page";

	//endregion


	//region CruiseDealsPage - Constructor Methods Section

	public CruiseDealsPage( final WebDriver driver )
	{
		super( LOGICAL_NAME, driver );
	}

	//endregion


	//region CruiseDealsPage - Initialization and Validation Methods Section

	@Override
	protected void validatePageUrl()
	{

	}

	@Override
	protected void initElements()
	{
		List<WebElement> y = Lists.newArrayList();
	}

	//endregion


	//region CruiseDealsPage - Service Methods Section

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper( this )
				.add( "object id", getId() )
				.add( "page id", pageId() )
				.add( "logical name", getLogicalName() )
				.add( "pageName", pageName() )
				.add( "site region", getSiteRegion() )
				.add( "title", getTitle() )
				.add( "url", getCurrentUrl() )
				.omitNullValues()
				.toString();
	}

	//endregion


	//region CruiseDealsPage - Business Methods Section

	//endregion


	//region CruiseDealsPage - Element Finder Methods Section

	//endregion

}