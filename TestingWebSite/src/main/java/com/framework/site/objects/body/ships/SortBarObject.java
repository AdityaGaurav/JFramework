package com.framework.site.objects.body.ships;

import com.framework.asserts.JAssertion;
import com.framework.driver.event.ExpectedConditions;
import com.framework.driver.event.HtmlCondition;
import com.framework.driver.event.HtmlElement;
import com.framework.driver.exceptions.ApplicationException;
import com.framework.driver.objects.AbstractWebObject;
import com.framework.site.data.Enumerators;
import com.framework.utils.datetime.TimeConstants;
import com.google.common.base.Optional;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.framework.utils.datetime.TimeConstants.THREE_SECONDS;
import static com.framework.utils.datetime.TimeConstants.TWO_SECONDS;
import static com.framework.utils.matchers.JMatchers.*;
import static org.hamcrest.Matchers.is;

//todo: class documentation


public class SortBarObject extends AbstractWebObject implements Enumerators
{

	//region SortBarObject - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( SortBarObject.class );

	static final String LOGICAL_NAME = "Ships Sort Bar";

	public static final By ROOT_BY = By.cssSelector( "div.sort-bar" );

	// ------------------------------------------------------------------------|
	// --- WEB-OBJECTS CACHING ------------------------------------------------|
	// ------------------------------------------------------------------------|

	private HtmlElement toggleSort, toggleLayout;

	private HtmlElement options;

	//endregion


	//region SortBarObject - Constructor Methods Section

	public SortBarObject( final HtmlElement rootElement )
	{
		super( rootElement, LOGICAL_NAME );
		initWebObject();
	}

	//endregion


	//region SortBarObject - Initialization and Validation Methods Section

	@Override
	protected void initWebObject()
	{
		logger.info( "validating static elements for web object id: < {} >, name:< {} >...", getQualifier(), getLogicalName() );

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
		return getBaseRootElement( ROOT_BY );
	}

	private boolean isSortTypeExpanded()
	{
		boolean expanded = findSortToggleAnchor().getAttribute( "class" ).endsWith( "active" );
		logger.info( "Determine if \"Sort\" drop-down list is currently expanded ( class ends with active ) -> < {} >", expanded );
		return expanded;
	}

	private boolean isLayoutTypeExpanded()
	{
		boolean expanded = findLayoutToggleAnchor().getAttribute( "class" ).endsWith( "active" );
		logger.info( "Determine if \"Layout\" drop-down list is currently expanded ( class ends with active ) -> < {} >", expanded );
		return expanded;
	}

	private void expandSort()
	{
		logger.info( "Expanding \"Sort\" drop-down list if required." );
		if ( ! isSortTypeExpanded() )
		{
			findSortToggleAnchor().click();
			logger.info( "\"Sort\" drop-down was expanded. waiting up-to 5 seconds for attribute \"class\" endsWith( \"active\" )" );
			findSortToggleAnchor().waitAttributeToMatch( "class", endsWith( "active" ), TimeConstants.FIVE_SECONDS );
		}
	}

	private void collapseSort()
	{
		logger.info( "Collapsing \"Sort\" drop-down list if required." );
		if ( isSortTypeExpanded() )
		{
			findSortToggleAnchor().click();
			logger.info( "\"Sort\" drop-down was collapsed. waiting up-to 5 seconds for attribute \"class\" not endsWith( \"active\" )" );
			findSortToggleAnchor()
					.waitAttributeToMatch( "class", not( endsWith( "active" ) ), TimeConstants.FIVE_SECONDS );
		}
	}

	private void expandLayout()
	{
		logger.info( "Expanding \"Layout\" drop-down list if required." );
		if ( ! isLayoutTypeExpanded() )
		{
			findLayoutToggleAnchor().click();
			logger.info( "\"Layout\" drop-down was expanded. waiting up-to 5 seconds for attribute \"class\" endsWith( \"active\" )" );
			findLayoutToggleAnchor()
					.waitAttributeToMatch( "class", endsWith( "active" ), TimeConstants.FIVE_SECONDS );
		}
	}

	private void collapseLayout()
	{
		logger.info( "Collapsing \"Layout\" drop-down list if required." );
		if ( isLayoutTypeExpanded() )
		{
			findLayoutToggleAnchor().click();
			logger.info( "\"Layout\" drop-down was collapsed. waiting up-to 5 seconds for attribute \"class\" not endsWith( \"active\" )" );
			findLayoutToggleAnchor()
					.waitAttributeToMatch( "class", not( endsWith( "active" ) ), TimeConstants.FIVE_SECONDS );
		}
	}

	//endregion


	//region SortBarObject - Implementation Methods Section

//	/**
//	 * @return the number displayed in div.sort-bar > ul.options > h3 > span
//	 */
//	@Override
//	public int getResults()
//	{
//		HtmlElement he = findSortBarH3Span();
//
//		Predicate<HtmlElement> predicate = new Predicate<HtmlElement>()
//		{
//			@Override
//			public boolean apply( final HtmlElement input )
//			{
//				String text = input.getText();
//				return NumberUtils.isNumber( text ) && NumberUtils.createInteger( text ) > 0;
//			}
//		};
//
//		new FluentWait<HtmlElement>( he )
//				.withTimeout( 10, TimeUnit.SECONDS )
//				.pollingEvery( 2000, TimeUnit.MILLISECONDS )
//				.withMessage( "Waiting for ships count to be grater than 0" )
//				.until( predicate );
//
//		String text = he.getText();
//		logger.info( "Reading the number of ships results. < {} Results >", text );
//		if ( NumberUtils.isNumber( text ) )
//		{
//			return NumberUtils.createInteger( text );
//		}
//
//		throw new ApplicationException( he, "Ships results is not numeric '" + text + "'" );
//	}

	public HtmlElement getResultsElement()
	{
		HtmlElement he = findSortBarH3Span();
		logger.info( "Returning itineraries result element ..." );
		return he;
	}

	public LayoutType getLayoutType()
	{
		HtmlElement he = findCurrentLayoutType();
	    String text = he.getText();
		logger.info( "Reading the ships default Layout Type ( Grid | List ). current is < {} >", text );
		if ( text.equalsIgnoreCase( "Grid" ) ) return LayoutType.BY_GRID;
		if ( text.equalsIgnoreCase( "List" ) ) return LayoutType.BY_LIST;

		throw new ApplicationException( he, "Layout options expected to be ( Grid | List ), however found: '" + text + "'" );
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
	public void setLayoutType( final LayoutType layout )
	{
		logger.info( "Changing layout type to < {} > if required ...", layout.name() );

		// Determine the current value, if are equals, procedure will be aborted
		LayoutType current = getLayoutType();
		if ( current.equals( layout ) )
		{
			logger.info( "Layout type < {} > was already selected.", layout.name() );
			return;
		}

		/* clicking on toggle and select requested value */
		logger.info( "Changing layout type to", layout.name() );
		HtmlElement subList = getLayoutSubList();
		expandLayout();

		Boolean response = subList.waitToBeDisplayed( true, TimeConstants.THREE_SECONDS );
		logger.debug( "Layout subList class is displayed -> < {} >", response );

		// selecting BY_GRID ?
		HtmlElement optionItem;
		String expectedValue;
		if ( layout.equals( LayoutType.BY_GRID ) )
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
				ExpectedConditions.elementTextToMatch( he, equalToIgnoringWhiteSpace( expectedValue ) );
		he.createAssertion().assertWaitThat( REASON, TimeConstants.FIVE_SECONDS, condition );
	}

	public SortType getSortType()
	{
		HtmlElement he = findCurrentSortType();
		String text = he.getText();
		logger.info( "Reading the ships default sort Type ( FEATURED | A-Z ). current is < {} >", text );
		if ( text.equalsIgnoreCase( "A-Z" ) ) 		return SortType.A_Z;
		if ( text.equalsIgnoreCase( "FEATURED" ) ) 	return SortType.FEATURED;

		throw new ApplicationException( he, "Sort options expected to be ( FEATURED | A-Z ), however found: '" + text + "'" );
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
	public void setSortType( final SortType sort )
	{
		logger.info( "Changing sort type to < {} > if required ...", sort.name() );

		// Determine the current value, if are equals, procedure will be aborted
		SortType current = getSortType();
		if ( current.equals( sort ) )
		{
			logger.info( "Sort type < {} > was already selected.", sort.name() );
			return;
		}

		/* clicking on toggle and select requested value */
		logger.info( "Changing sort type to", sort.name() );
		HtmlElement subList = getSortSubList();
		expandSort();
		Boolean response = subList.waitToBeDisplayed( true, TimeConstants.THREE_SECONDS );

		// selecting FEATURED ?
		HtmlElement optionItem;
		String expectedValue;
		if ( sort.equals( SortType.FEATURED ) )
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
		HtmlElement he = findCurrentSortType();
		String REASON = "Validates that new value matches requested value < " + expectedValue + " >";
		HtmlCondition<Boolean> condition =
				ExpectedConditions.elementTextToMatch( he, equalToIgnoringWhiteSpace( expectedValue ) );
		he.createAssertion().assertWaitThat( REASON, TimeConstants.FIVE_SECONDS, condition );
	}

	public HtmlElement hoverSortType()
	{
		logger.info( "Hovering over sort type toggle anchor ..." );
		findLayoutToggleAnchor().hover();
		return findLayoutToggleAnchor();
	}

	public HtmlElement hoverLayoutType()
	{
		logger.info( "Hovering over layout type toggle anchor ..." );
		findSortToggleAnchor().hover();
		return findSortToggleAnchor();
	}

	public HtmlElement getSortTypeOption( final SortType option )
	{
		logger.info( "Retrieve HtmlElement for sort type option < {} >" );
		HtmlElement subList = getSortSubList();
		expandSort();

		if ( option.equals( SortType.FEATURED ) )
		{
			return findDataLabelListItem( subList, "Featured" );
		}

		return findDataLabelListItem( subList, "A-Z" );
	}

	public HtmlElement getLayoutOption( final LayoutType option )
	{
		logger.info( "Retrieve HtmlElement for layout type option < {} >" );
		HtmlElement subList = getLayoutSubList();
		expandLayout();

		if ( option.equals( LayoutType.BY_GRID ) )
		{
			return findDataLabelListItem( subList, "Grid" );
		}

		return findDataLabelListItem( subList, "List" );
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
		if ( null == toggleLayout )
		{
			final By findBy = By.cssSelector( "li:last-child .toggle" );
			toggleLayout = options.findElement( findBy );
		}

		return toggleLayout;
	}

	private HtmlElement findSortToggleAnchor()
	{
		if ( null == toggleSort )
		{
			final By findBy = By.cssSelector( "li:first-child .toggle" );
			toggleSort = options.findElement( findBy );
		}

		return toggleSort;
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
