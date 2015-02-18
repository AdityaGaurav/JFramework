package com.framework.site.objects.body;

import com.framework.asserts.JAssertion;
import com.framework.driver.event.ExtendedBy;
import com.framework.driver.event.HtmlElement;
import com.framework.driver.event.HtmlObject;
import com.framework.driver.objects.AbstractWebObject;
import com.framework.site.objects.body.interfaces.LinkTouts;
import com.framework.utils.string.LogStringStyle;
import com.google.common.base.Joiner;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static com.framework.utils.datetime.TimeConstants.FIVE_SECONDS;
import static org.hamcrest.Matchers.is;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.site.objects.body
 *
 * Name   : LinkToutObject
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-01-06
 *
 * Time   : 19:55
 */

public class LinkToutsContainerObject extends AbstractWebObject implements LinkTouts
{

	//region LinkToutsContainerObject - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( LinkToutsContainerObject.class );


	//endregion


	//region LinkToutsContainerObject - Constructor Methods Section

	public LinkToutsContainerObject( final HtmlElement rootElement )
	{
		super( rootElement, LinkTouts.LOGICAL_NAME );
		initWebObject();
	}

	//endregion


	//region LinkToutsContainerObject - Initialization and Validation Methods Section

	@Override
	protected void initWebObject()
	{
		logger.debug( "validating static elements for web object id: <{}>, name:<{}>...",
				getQualifier(), getLogicalName() );

		final String REASON = "assert that element \"%s\" exits";
		JAssertion assertion = getRoot().createAssertion();

		Optional<List<HtmlElement>> es = getRoot().allChildrenExists( By.tagName( "li" ), FIVE_SECONDS );
		assertion.assertThat( String.format( REASON, "li" ), es.isPresent(), is( true ) );
	}

	//endregion


	//region LinkToutsContainerObject - Service Methods Section

	private HtmlElement getRoot()
	{
		return getBaseRootElement( LinkTouts.ROOT_BY );
	}

	//endregion


	//region LinkToutsContainerObject - Business Methods Section

	//endregion


	//region LinkToutsContainerObject - Element Finder Methods Section

	private HtmlElement getLinkToutAnchor()
	{
		return getRoot().findElement( By.tagName( "a" ) );
	}

	private HtmlElement getLinkToutImg()
	{
		return getRoot().findElement( By.tagName( "img" ) );
	}

	private HtmlElement getH4Span()
	{
		return getRoot().findElement( ExtendedBy.composite( By.tagName( "span" ), By.className( "h4" ) ) );
	}

	private HtmlElement getParaSpan()
	{
		return getRoot().findElement( ExtendedBy.composite( By.tagName( "span" ), By.className( "para" ) ) );
	}

	private HtmlElement getLinkSpan()
	{
		return getRoot().findElement( ExtendedBy.composite( By.tagName( "span" ), By.className( "link" ) ) );
	}

	//endregion

	public class LinkTout implements LinkTouts.LinkTout
	{
	 	private final int index;

		private String toutTitle;

		private HtmlElement rootElement;

		//region LinkTout - Constructor Methods Section

		public LinkTout( final HtmlElement rootElement, int index )
		{
			this.index = index;
			this.rootElement = Preconditions.checkNotNull( rootElement, "WebElement rootElement cannot be null" );
			this.toutTitle = Joiner.on( " " ).join( HtmlObject.extractText( findSpanH4Spans() ) );
		}

		//endregion


//		//region LinkToutsContainerObject - Initialization and Validation Methods Section
//
//		protected void initWebObject()
//		{
//			logger.debug( "validating static elements for web object id: <{}>, name:<{}>...",
//					getQualifier(), getLogicalName() );
//
//			JAssertion assertion = new JAssertion( getDriver() );
//			ExpectedCondition<WebElement> condition1 = WaitUtil.presenceBy( By.tagName( "img" ) );
//			ExpectedCondition<WebElement> condition2 = WaitUtil.presenceBy( By.cssSelector( "span.h4" ) );
//			ExpectedCondition<WebElement> condition3 = WaitUtil.presenceBy( By.cssSelector( "span.para" ) );
//
//			assertion.assertWaitThat(
//					"Validate \"img\" element is present", TimeConstants.TWO_SECONDS, condition1 );
//			assertion.assertWaitThat(
//					"Validate \"span.h4\" element is present", TimeConstants.TWO_SECONDS, condition2 );
//			assertion.assertWaitThat(
//					"Validate \"span.para\" element is present", TimeConstants.TWO_SECONDS, condition3 );
//		}
//
//		//endregion

		private HtmlElement getRoot()
		{
			return getBaseRootElement( LinkTouts. ROOT_BY );
		}

		@Override
		public String getReference()
		{
			return null;
		}

		@Override
		public String getImageAlt()
		{
			return null;
		}

		@Override
		public String getLeftTitle()
		{
			return null;
		}

		@Override
		public String getRightTitle()
		{
			return null;
		}

		@Override
		public String getParagraphText()
		{
			return null;
		}

		@Override
		public String activateTout()
		{
			return null;
		}

		@Override
		public String toString()
		{
			return new ToStringBuilder( this, LogStringStyle.LOG_LINE_STYLE )
					.append( "toutTitle", toutTitle != null ? toutTitle : "N/A" )
					.append( "index", index )
					.toString();
		}

		//endregion

		//region LinkToutsContainerObject - Element Finder Methods Section

		private HtmlElement findSpanH4()
		{
			return rootElement.findElement( By.cssSelector( "span.h4" ) );
		}

		private List<HtmlElement> findSpanH4Spans()
		{
			return rootElement.findElements( By.cssSelector( "span.h4 > span" ) );
		}

		//endregion
	}

}