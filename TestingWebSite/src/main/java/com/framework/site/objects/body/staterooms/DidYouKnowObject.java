package com.framework.site.objects.body.staterooms;

import com.framework.asserts.JAssertion;
import com.framework.driver.event.HtmlElement;
import com.framework.driver.objects.AbstractWebObject;
import com.framework.site.objects.body.interfaces.CallOut;
import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.BooleanUtils;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static com.framework.utils.datetime.TimeConstants.THREE_SECONDS;
import static org.hamcrest.Matchers.is;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.site.objects.body
 *
 * Name   : DidYouKnowObject 
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-03-06 
 *
 * Time   : 19:57
 *
 */

public class DidYouKnowObject extends AbstractWebObject
{

	//region DidYouKnowObject - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( DidYouKnowObject.class );

	static final String LOGICAL_NAME = "Did You Know";

	public static final By ROOT_BY = By.cssSelector( "div.content-block.scroll-section.white-gradient" );

	// ------------------------------------------------------------------------|
	// --- WEB-OBJECTS CACHING ------------------------------------------------|
	// ------------------------------------------------------------------------|

	private HtmlElement intro, callOuts;

	//endregion


	//region DidYouKnowObject - Constructor Methods Section

	public DidYouKnowObject( final HtmlElement rootElement )
	{
		super( rootElement, LOGICAL_NAME );
		initWebObject();
	}

	//endregion


	//region DidYouKnowObject - Initialization and Validation Methods Section

	@Override
	protected void initWebObject()
	{
		logger.info( "validating static elements for web object id: <{}>, name:<{}>...",
				getQualifier(), getLogicalName() );

		final String REASON = "assert that element \"%s\" exits";

		JAssertion assertion = getRoot().createAssertion();
		Optional<HtmlElement> e = getRoot().childExists( By.className( "intro" ), THREE_SECONDS );
		assertion.assertThat( String.format( REASON, "div.intro" ), e.isPresent(), is( true ) );
		intro = e.get();

		e = getRoot().childExists( By.className( "dotted-callouts" ), THREE_SECONDS );
		assertion.assertThat( String.format( REASON, "div.dotted-callouts" ), e.isPresent(), is( true ) );
		callOuts = e.get();
	}

	//endregion


	//region DidYouKnowObject - Service Methods Section

	private HtmlElement getRoot()
	{
		return getBaseRootElement( ROOT_BY );
	}

	//endregion


	//region DidYouKnowObject - DidYouKnow Implementation Section

	public boolean isDisplayed()
	{
		boolean displayed = getRoot().isDisplayed();
		logger.info( "Determine if the \"Did you know\" section is displayed -> > {} >", BooleanUtils.toStringYesNo( displayed ) );
		return displayed;
	}

	public List<CallOut> getCallOuts()
	{
		List<HtmlElement> divs = findCallOuts();
		logger.info( "Return a list of call-outs. found < {} >", divs.size() );
		if( divs.size() > 0 )
		{
			List<CallOut> callOutList = Lists.newArrayListWithCapacity( divs.size() );
			for( HtmlElement div : divs )
			{
				callOutList.add( new CallOutObject( div ) );
			}
			return callOutList;
		}
		else
		{
			return Lists.newArrayList();
		}
	}

	public boolean hasIntroTitle()
	{
		Optional<HtmlElement> oe = findIntro().childExists( By.tagName( "h2" ) );
		logger.info( "Determine intro title exists < {} >", BooleanUtils.toStringYesNo( oe.isPresent() ) );
		return oe.isPresent();
	}

	public boolean hasIntroBrief()
	{
		Optional<HtmlElement> oe = findIntro().childExists( By.tagName( "p" ) );
		logger.info( "Determine intro brief exists < {} >", BooleanUtils.toStringYesNo( oe.isPresent() ) );
		return oe.isPresent();
	}

	//endregion


	//region DidYouKnowObject - Element Finder Section

	private List<HtmlElement> findCallOuts()
	{
		final By findBy = By.cssSelector( "div.col.col-4-20" );
		return getRoot().findElements( findBy );
	}

	private HtmlElement findIntro()
	{
		if( null == intro )
		{
			final By findBy = By.className( "intro" );
			this.intro = getRoot().findElement( findBy );
		}
		return intro;
	}

	//endregion


	//region DidYouKnowObject - Inner class Implementation

	private class CallOutObject implements CallOut
	{
		private final HtmlElement root;

		private CallOutObject( final HtmlElement root )
		{
			this.root = root;
		}

		public String getTitle()
		{
			return root.findElement( By.tagName( "h5" ) ).getText();
		}

		public String getParagraph()
		{
			HtmlElement p = root.findElement( By.tagName( "p" ) );
			return p.getWrappedElement().getText().trim();
		}

		public HtmlElement getImage()
		{
			return root.findElement( By.tagName( "img" ) );
		}

		@Override
		public boolean hasTitle()
		{
			Optional<HtmlElement> oe = root.childExists( By.tagName( "h5" ) );
			return oe.isPresent();
		}

		@Override
		public boolean hasImage()
		{
			Optional<HtmlElement> oe = root.childExists( By.tagName( "img" ) );
			return oe.isPresent();
		}

		@Override
		public boolean hasParagraph()
		{
			Optional<HtmlElement> oe = root.childExists( By.tagName( "p" ) );
			return oe.isPresent();
		}
	}

	//endregion
}
