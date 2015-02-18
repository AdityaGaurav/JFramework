package com.framework.site.objects.body.ships;

import com.framework.asserts.JAssertion;
import com.framework.driver.event.ExpectedConditions;
import com.framework.driver.event.HtmlCondition;
import com.framework.driver.event.HtmlElement;
import com.framework.driver.objects.AbstractWebObject;
import com.framework.site.objects.body.interfaces.ShipSortBar;
import com.framework.utils.datetime.TimeConstants;
import com.framework.utils.matchers.JMatchers;
import com.google.common.base.Optional;
import org.apache.commons.lang3.math.NumberUtils;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.framework.utils.datetime.TimeConstants.THREE_SECONDS;
import static com.framework.utils.datetime.TimeConstants.TWO_SECONDS;
import static org.hamcrest.Matchers.is;

//todo: class documentation

public class SortBarObject extends AbstractWebObject implements ShipSortBar
{

	//region SortBarObject - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( SortBarObject.class );

	private static final int LAYOUT_INDEX = 1;

	private static final int SORT_INDEX = 0;

	// ------------------------------------------------------------------------|
	// --- WEB-OBJECTS CACHING ------------------------------------------------|
	// ------------------------------------------------------------------------|

	HtmlElement options;

	//endregion


	//region SortBarObject - Constructor Methods Section

	public SortBarObject( final HtmlElement rootElement )
	{
		super( rootElement, ShipSortBar.LOGICAL_NAME );
		initWebObject();
	}

	//endregion


	//region SortBarObject - Initialization and Validation Methods Section

	@Override
	protected void initWebObject()
	{
		logger.debug( "validating static elements for web object id: <{}>, name:<{}>...",
				getQualifier(), getLogicalName() );

		final String REASON = "assert that element \"%s\" exits";

		JAssertion assertion = getRoot().createAssertion();
		Optional<HtmlElement> e = getRoot().childExists( By.tagName( "h3" ), THREE_SECONDS );
		assertion.assertThat( String.format( REASON, "h3" ), e.isPresent(), is( true ) );

		e = getRoot().childExists( By.cssSelector( "ul.options" ), TWO_SECONDS );
		assertion.assertThat( String.format( REASON, "ul.options" ), e.isPresent(), is( true ) );
		options = e.get();
	}

	//endregion


	//region SortBarObject - Service Methods Section

	private HtmlElement getRoot()
	{
		return getBaseRootElement( ShipSortBar.ROOT_BY );
	}

	//endregion


	//region SortBarObject - Implementation Methods Section

	/**
	 * @return the number displayed in div.sort-bar > ul.options > h3 > span
	 */
	@Override
	public int getResults()
	{
		logger.info( "Reading the number of ships results ..." );

		HtmlElement he = findSortBarH3Span();
		String text = he.getText();
		if( NumberUtils.isNumber( text ) )
		{
			return NumberUtils.createInteger( text );
		}

		return 0;
	}

	/**
	 * {@inheritDoc}
	 * first is determined the current value of the layout.
	 * if the requested value equals to the actual value, the procedure is aborted.
	 * <ul>
	 *     <li>clicks on the layout toggle anchor</li>
	 *     <li>validates sub-list is displayed and anchor class ends with active</li>
	 *     <li>clicks on the requested layout option</li>
	 *     <li>waits to list to be not visible</li>
	 *     <li>asserts that new value matches the requested value</li>
	 * </ul>
	 */
	@Override
	public void setLayoutType( final LayoutType layout )
	{
		logger.info( "Changing layout type to <\"{}\">", layout.name() );

		// Determine the current value, if are equals, procedure will be aborted
		LayoutType current = getLayoutType();
		if( current.equals( layout ) )
		{
			logger.info( "Layout type < {} > is already selected.", layout.name() );
			return;
		}

		/* clicking on toggle and select requested value */
		HtmlElement toggle = findLayoutToggleAnchor();
		HtmlElement subList = getLayoutSubList();
		toggle.click();

		Boolean response = toggle.waitAttributeToMatch( "class", JMatchers.endsWith( "active" ), TimeConstants.THREE_SECONDS );
		logger.debug( "Layout toggle class is \"active\" -> < {} >", response );
		response = subList.waitToBeDisplayed( true, TimeConstants.THREE_SECONDS );
		logger.debug( "Layout subList class is displayed -> < {} >", response );

		// selecting BY_GRID ?
		HtmlElement optionItem; String expectedValue;
		if( layout.equals( LayoutType.BY_GRID ) )
		{
			expectedValue = "Grid";
			optionItem = findDataLabelListItem( subList, expectedValue );
		}
		else
		{
			expectedValue = "List";
			optionItem = findDataLabelListItem( subList, expectedValue );
		}
		optionItem.click();
		response = subList.waitToBeDisplayed( false, TimeConstants.THREE_SECONDS );
		logger.debug( "Layout subList class is not displayed  -> < {} >", response );

		// validating value was selected
		HtmlElement he = findCurrentLayoutType();
		String REASON = "Validates that new value matches requested value < " + expectedValue + " >";
		HtmlCondition<Boolean> condition =
				ExpectedConditions.elementTextToMatch( he, JMatchers.equalToIgnoringWhiteSpace( expectedValue ) );
		he.createAssertion().assertWaitThat( REASON, TimeConstants.FIVE_SECONDS, condition );
	}

	/**
	 * {@inheritDoc}
	 * first is determined the current value of the SortType.
	 * if the requested value equals to the actual value, the procedure is aborted.
	 * <ul>
	 *     <li>clicks on the layout toggle anchor</li>
	 *     <li>validates sub-list is displayed and anchor class ends with active</li>
	 *     <li>clicks on the requested layout option</li>
	 *     <li>waits to list to be not visible</li>
	 *     <li>asserts that new value matches the requested value</li>
	 * </ul>
	 */
	@Override
	public void setSortType( final SortType sort )
	{
		logger.info( "Changing sort type to <\"{}\">", sort.name() );

		// Determine the current value, if are equals, procedure will be aborted
		SortType current = getSortType();
		if( current.equals( sort ) )
		{
			logger.info( "Sort type < {} > is already selected.", sort.name() );
			return;
		}

		/* clicking on toggle and select requested value */
		HtmlElement toggle = findSortToggleAnchor();
		HtmlElement subList = getSortSubList();
		toggle.click();

		Boolean response = toggle.waitAttributeToMatch( "class", JMatchers.endsWith( "active" ), TimeConstants.THREE_SECONDS );
		logger.debug( "Sort toggle class is \"active\" -> < {} >", response );
		response = subList.waitToBeDisplayed( true, TimeConstants.THREE_SECONDS );
		logger.debug( "Sort subList class is displayed -> < {} >", response );

		// selecting FEATURED ?
		HtmlElement optionItem; String expectedValue;
		if( sort.equals( SortType.DISPLAY_FEATURED ) )
		{
			expectedValue = "Featured";
			optionItem = findDataLabelListItem( subList, expectedValue );
		}
		else
		{
			expectedValue = "A-Z";
			optionItem = findDataLabelListItem( subList, expectedValue );
		}
		optionItem.click();
		response = subList.waitToBeDisplayed( false, TimeConstants.THREE_SECONDS );
		logger.debug( "Sort subList class is not displayed  -> < {} >", response );

		// validating value was selected
		HtmlElement he = findCurrentLayoutType();
		String REASON = "Validates that new value matches requested value < " + expectedValue + " >";
		HtmlCondition<Boolean> condition =
				ExpectedConditions.elementTextToMatch( he, JMatchers.equalToIgnoringWhiteSpace( expectedValue ) );
		he.createAssertion().assertWaitThat( REASON, TimeConstants.FIVE_SECONDS, condition );
	}

	/**
	 * determine the current display layout type
	 *
	 * @return a {@linkplain ShipSortBar.SortType} enumeration value.
	 */
	@Override
	public LayoutType getLayoutType()
	{
		logger.info( "Reading the ships default Layout Type ( Grid | List ) ..." );
		HtmlElement he = findCurrentLayoutType();

		if( he.getText().equalsIgnoreCase( "Grid" ) ) return LayoutType.BY_GRID;
		if( he.getText().equalsIgnoreCase( "List" ) ) return LayoutType.BY_LIST;
		return null;
	}

	/**
	 * determine the current display sort type
	 *
	 * @return a {@linkplain ShipSortBar.SortType} enumeration value.
	 */
	@Override
	public SortType getSortType()
	{
		logger.info( "Reading the ships default sort Type ( FEATURED | A-Z ) ..." );
		HtmlElement he = findCurrentSortType();

		if( he.getText().equalsIgnoreCase( "A-Z" ) ) return SortType.DISPLAY_AZ;
		if( he.getText().equalsIgnoreCase( "Featured" ) ) return SortType.DISPLAY_FEATURED;
		return null;
	}

	//endregion


	//region SortBarObject - Element Finder Methods Section

	private HtmlElement findSortBarH3Span()
	{
		final By findBy = By.cssSelector( "h3 > span" );
		return getDriver().findElement( findBy );
	}

	private HtmlElement findCurrentSortType()
	{
		final By findBy = By.cssSelector( "li:first-child a.toggle > span" );
		return options.findElement( findBy );
	}

	private HtmlElement findCurrentLayoutType()
	{
		final By findBy = By.cssSelector( "li:last-child a.toggle > span" );
		return options.findElement( findBy );
	}

	private HtmlElement findLayoutToggleAnchor()
	{
		final By findBy = By.cssSelector( "li:last-child .toggle" );
		return options.findElement( findBy );
	}

	private HtmlElement findSortToggleAnchor()
	{
		final By findBy = By.cssSelector( "li:first-child .toggle" );
		return options.findElement( findBy );
	}

	private HtmlElement getLayoutSubList()
	{
		final By findBy = By.cssSelector( "li:last-child > ul.sub-list" );
		return options.findElement( findBy );
	}

	private HtmlElement getSortSubList()
	{
		final By findBy = By.cssSelector( "li:first-child > ul.sub-list" );
		return options.findElement( findBy );
	}

	private HtmlElement findDataLabelListItem( HtmlElement subList, String type )
	{
		final By findBy = By.xpath( String.format( "//a[@data-label=\"%s\"]", type ) );
		return subList.findElement( findBy );
	}

	//endregion

}
