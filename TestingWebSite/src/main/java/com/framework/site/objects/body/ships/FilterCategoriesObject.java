package com.framework.site.objects.body.ships;

import com.framework.asserts.JAssertion;
import com.framework.driver.event.HtmlElement;
import com.framework.driver.objects.AbstractWebObject;
import com.framework.site.objects.body.interfaces.FilterCategories;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


//todo: class documentation

public class FilterCategoriesObject extends AbstractWebObject implements FilterCategories
{

	//region FilterCategoriesObject - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( FilterCategoriesObject.class );

	//endregion


	//region FilterCategoriesObject - Constructor Methods Section

	public FilterCategoriesObject( final HtmlElement rootElement )
	{
		super( rootElement, FilterCategories.LOGICAL_NAME );
		initWebObject();
	}

	//endregion


	//region FilterCategoriesObject - Initialization and Validation Methods Section

	@Override
	protected void initWebObject()
	{
		logger.debug( "validating static elements for web object id: <{}>, name:<{}>...",
				getQualifier(), getLogicalName() );

		final String REASON = "assert that element \"%s\" exits";
		JAssertion assertion = getRoot().createAssertion();
	}

	//endregion


	//region FilterCategoriesObject - Service Methods Section



	private HtmlElement getRoot()
	{
//		try
//		{
//			rootElement.getTagName();
//			return rootElement;
//		}
//		catch ( StaleElementReferenceException ex )
//		{
//			logger.warn( "auto recovering from StaleElementReferenceException ..." );
//			return objectDriver.findElement( ShipSortBar.ROOT_BY );
//		}
		return null;
	}

	//endregion


	//region FilterCategoriesObject - Business Methods Section

	@Override
	public void doToggle()
	{

	}


	//endregion


	//region FilterCategoriesObject - Element Finder Methods Section

	//endregion

}
