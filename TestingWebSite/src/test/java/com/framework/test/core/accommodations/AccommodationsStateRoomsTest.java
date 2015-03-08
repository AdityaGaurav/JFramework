package com.framework.test.core.accommodations;

import com.framework.BaseTest;
import com.framework.driver.event.ExpectedConditions;
import com.framework.driver.event.HtmlCondition;
import com.framework.driver.event.HtmlElement;
import com.framework.driver.exceptions.ApplicationException;
import com.framework.site.config.SiteSessionManager;
import com.framework.site.data.Enumerators;
import com.framework.site.objects.body.common.cbox.CBoxWrapperObject;
import com.framework.site.objects.body.common.cbox.RoomLightboxObject;
import com.framework.site.objects.header.enums.LevelOneMenuItem;
import com.framework.site.objects.header.enums.MenuItems;
import com.framework.site.pages.core.HomePage;
import com.framework.site.pages.core.StateRoomsPage;
import com.framework.testing.annotations.*;
import com.framework.utils.datetime.Sleeper;
import com.framework.utils.datetime.TimeConstants;
import com.framework.utils.error.PreConditions;
import com.framework.utils.matchers.JMatchers;
import com.framework.utils.web.CSS2Properties;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.RandomUtils;
import org.hamcrest.Matcher;
import org.openqa.selenium.WebDriverException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;
import org.testng.xml.XmlTest;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.test.core
 *
 * Name   : AccommodationsTest 
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-03-06 
 *
 * Time   : 10:41
 *
 */

public class AccommodationsStateRoomsTest extends BaseTest
{

	//region AccommodationsTest - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( AccommodationsStateRoomsTest.class );

	private HomePage homePage = null;

	private StateRoomsPage stateRoomsPage = null;


	//endregion


	//region AccommodationsTest - Constructor Methods Section

	private AccommodationsStateRoomsTest()
	{
		BaseTest.classLogger = LoggerFactory.getLogger( AccommodationsStateRoomsTest.class );
	}

	//endregion


	//region AccommodationsTest - Before Configurations Methods Section

	@BeforeClass ( description = "starts a web driver",
			alwaysRun = true,
			groups = { "US", "UK", "AU" }
	)
	public void beforeClass( ITestContext testContext, XmlTest xmlTest ) throws Exception
	{
		killExistingBrowsersOpenedByWebDriver();
		SiteSessionManager.get().startSession();
		testContext.setAttribute( "CloseLightbox", "NONE" );
	}

	@BeforeGroups ( description = "", enabled = true, groups = { "AU" } )
	public void beforeGroup( ITestContext testContext ) throws Exception
	{
		testContext.setAttribute( "currentGroup", "US" );
	}

	@BeforeMethod ( description = "Initiates Home Page",
			enabled = true,
			alwaysRun = true,
			groups = { "US", "UK", "AU" }
	)
	public void beforeMethod( ITestContext testContext, XmlTest xmlTest, Method method, Object[] parameters ) throws Exception
	{
		try
		{
			if ( ! SiteSessionManager.get().isSessionRunning() )
			{
				this.homePage = SiteSessionManager.get().getHomePage();
			}
			if ( SiteSessionManager.get().isHomePage() )
			{
				this.homePage = new HomePage();
				this.homePage.header().headerLinks().hoverOnItem( LevelOneMenuItem.EXPLORE );
				this.stateRoomsPage = ( StateRoomsPage )
						this.homePage.header().navigationAdditional().clickOnMenuItem( LevelOneMenuItem.EXPLORE, MenuItems.ACCOMMODATIONS );
			}
			else if ( StateRoomsPage.isStateRoomsPage() )
			{
				List<String> groups = Lists.newArrayList( testContext.getIncludedGroups() );
				this.stateRoomsPage = new StateRoomsPage();
			}

		}
		catch ( Throwable e )
		{
			if ( e instanceof WebDriverException )
			{
				throw new ApplicationException( SiteSessionManager.get().getDriver(), e );
			}
			else if ( e instanceof AssertionError )
			{
				throw new ApplicationException( SiteSessionManager.get().getDriver(), e );
			}
			throw new Exception( e.getMessage(), e );
		}
	}

	//endregion


	//region AccommodationsTest - Tests Methods Section

	@UserStory ( "PBI-63213" )
	@TestCaseId( id = 65016 )
	@Steps ( steps = {
			@Step ( number = 1, description = "GIVEN that the user is in Accommodations Landing page /state-rooms.aspx" ),
			@Step ( number = 2, description = "WHEN user clicks on any tile displayed in \"What's Your Room Type\" section" ),
			@Step ( number = 3, description = "AND WHEN user clicks outside the displayed light-box",
					expectedResults = "THEN the light-box is closed." )
	} )
	@Test ( description = "Accommodations. What's Your Room Type Lightbox. closing lightbox",
			enabled = true,
			groups = { "AU", "US", "UK"  }
	)
	public void accommodationsPage_WhatsYourRoomType_CloseLightbox( ITestContext context ) throws Exception
	{
		/* GIVEN that the user is in Accommodations Landing page /state-rooms.aspx */
		PreConditions.checkNotNull( this.stateRoomsPage, "State-Rooms page is null, before starting test" );
		try
		{
			/* WHEN user clicks on any tile displayed in "What's Your Room Type" section */

			HtmlElement container = stateRoomsPage.stateRooms().getContainer();
			container.scrollIntoView();

			Enumerators.RoomType[] roomTypes = Enumerators.RoomType.values();
			boolean reSelect = true;
			Enumerators.RoomType selected = null;
			while ( reSelect )
			{
				int rnd = RandomUtils.nextInt( 0, roomTypes.length - 1 );
				selected = roomTypes[ rnd ];

				if( ! context.getAttribute( "CloseLightbox" ).toString().equals( selected.name() ) )
				{
					context.setAttribute( "CloseLightbox", selected.name() );
					reSelect = false;
				}
			}

			CBoxWrapperObject wrapper = stateRoomsPage.stateRooms().clickStateRoom( selected );
			RoomLightboxObject rooms = wrapper.cBoxContent().cBoxLoadedContent().roomLightbox();

			String REASON = "Validate Lightbox is displayed";
			Boolean ACTUAL_BOOL = wrapper.isVisible();
			Matcher<Boolean> EXPECTED_OF_BOOL = JMatchers.is( true );
			SiteSessionManager.get().createCheckPoint( "LIGHTBOX_IS_VISIBLE" )
					.assertThat( REASON, ACTUAL_BOOL, EXPECTED_OF_BOOL );

			REASON = "Validate \"ROOM_TYPE\" Lightbox title";
			Enumerators.RoomType ACTUAL_ROOM_TYPE = rooms.getRoomType();
			Matcher<Enumerators.RoomType > EXPECTED_OF_ROOM_TYPE = JMatchers.is( selected );
			SiteSessionManager.get().createCheckPoint( "LIGHTBOX_ROOM_TYPE" )
					.assertThat( REASON, ACTUAL_ROOM_TYPE, EXPECTED_OF_ROOM_TYPE );

			/* AND WHEN user clicks outside the displayed light-box */
			wrapper.clickOnColorBoxArea();

			/* THEN the light-box is closed */
			REASON = "Validate Lightbox is not displayed";
			ACTUAL_BOOL = wrapper.isVisible();
			EXPECTED_OF_BOOL = JMatchers.is( false );
			SiteSessionManager.get().createCheckPoint( "LIGHTBOX_IS_NOT_VISIBLE" )
					.assertThat( REASON, ACTUAL_BOOL, EXPECTED_OF_BOOL );

			SiteSessionManager.get().assertAllCheckpoints();
		}
		catch ( WebDriverException | AssertionError e )
		{
			throw new ApplicationException( SiteSessionManager.get().getDriver(), e );
		}
	}

	@UserStory ( "PBI-63213" )
	@TestCaseId( id = 65018 )
	@Steps ( steps = {
			@Step ( number = 1, description = "GIVEN that the user is in Accommodations Landing page /state-rooms.aspx" ),
			@Step ( number = 2, description = "WHEN user clicks on any tile displayed in \"What's Your Room Type\" section" ),
			@Step ( number = 3, description = "AND WHEN user clicks the X on the upper-right side of the displayed light-box",
					expectedResults = "THEN the light-box is closed." )
	} )
	@Test ( description = "Accommodations. What's Your Room Type Lightbox. closing lightbox",
			enabled = true,
			dependsOnMethods = { "accommodationsPage_WhatsYourRoomType_CloseLightbox" },
			groups = { "AU", "US", "UK"  }
	)
	public void accommodationsPage_WhatsYourRoomType_CloseLightboxButton( ITestContext context ) throws Exception
	{
		/* GIVEN that the user is in Accommodations Landing page /state-rooms.aspx */
		PreConditions.checkNotNull( this.stateRoomsPage, "State-Rooms page is null, before starting test" );
		try
		{
			/* WHEN user clicks on any tile displayed in "What's Your Room Type" section */
			HtmlElement container = stateRoomsPage.stateRooms().getContainer();
			container.scrollIntoView();
			Enumerators.RoomType[] roomTypes = Enumerators.RoomType.values();
			boolean reSelect = true;
			Enumerators.RoomType selected = null;
			while ( reSelect )
			{
				int rnd = RandomUtils.nextInt( 0, roomTypes.length - 1 );
				selected = roomTypes[ rnd ];

				if( ! context.getAttribute( "CloseLightbox" ).toString().equals( selected.name() ) )
				{
					context.setAttribute( "CloseLightbox", selected.name() );
					reSelect = false;
				}
			}

			CBoxWrapperObject wrapper = stateRoomsPage.stateRooms().clickStateRoom( selected );
			RoomLightboxObject rooms = wrapper.cBoxContent().cBoxLoadedContent().roomLightbox();

			String REASON = "Validate Lightbox is displayed";
			Boolean ACTUAL_BOOL = wrapper.isVisible();
			Matcher<Boolean> EXPECTED_OF_BOOL = JMatchers.is( true );
			SiteSessionManager.get().createCheckPoint( "LIGHTBOX_IS_VISIBLE" )
					.assertThat( REASON, ACTUAL_BOOL, EXPECTED_OF_BOOL );

			REASON = "Validate \"ROOM_TYPE\" Lightbox title";
			Enumerators.RoomType ACTUAL_ROOM_TYPE = rooms.getRoomType();
			Matcher<Enumerators.RoomType > EXPECTED_OF_ROOM_TYPE = JMatchers.is( selected );
			SiteSessionManager.get().createCheckPoint( "LIGHTBOX_ROOM_TYPE" )
					.assertThat( REASON, ACTUAL_ROOM_TYPE, EXPECTED_OF_ROOM_TYPE );

			/* AND WHEN user clicks the X on the upper-right side of the displayed light-box */
			wrapper.close();

			/* THEN the light-box is closed */
			REASON = "Validate Lightbox is not displayed";
			ACTUAL_BOOL = wrapper.isVisible();
			EXPECTED_OF_BOOL = JMatchers.is( false );
			SiteSessionManager.get().createCheckPoint( "LIGHTBOX_IS_NOT_VISIBLE" )
					.assertThat( REASON, ACTUAL_BOOL, EXPECTED_OF_BOOL );

			SiteSessionManager.get().assertAllCheckpoints();
		}
		catch ( WebDriverException | AssertionError e )
		{
			throw new ApplicationException( SiteSessionManager.get().getDriver(), e );
		}
	}

	@UserStory ( "PBI-63213" )
	@TestCaseId( id = 65020 )
	@Steps ( steps = {
			@Step ( number = 1, description = "GIVEN that the user is in Accommodations Landing page /state-rooms.aspx" ),
			@Step ( number = 2, description = "WHEN user examine any tile displayed in \"What's Your Room Type\" section",
					expectedResults = "THEN the image ALT text is defined" )
	} )
	@Test ( description = "Accommodations. What's Your Room Type Lightbox. image ALT text",
			enabled = true,
			groups = { "AU", "US", "UK"  }
	)
	public void accommodationsPage_WhatsYourRoomType_AltText() throws Exception
	{
		/* GIVEN that the user is in Accommodations Landing page /state-rooms.aspx */
		PreConditions.checkNotNull( this.stateRoomsPage, "State-Rooms page is null, before starting test" );

		try
		{
			/* WHEN user examine any tile displayed in "What's Your Room Type" section */
			HtmlElement container = stateRoomsPage.stateRooms().getContainer();
			container.scrollIntoView();
			Enumerators.RoomType[] roomTypes = Enumerators.RoomType.values();
			for( Enumerators.RoomType roomType : roomTypes )
			{
				HtmlElement image = stateRoomsPage.stateRooms().getImage( roomType );
				String REASON = "Validate img ALT text is not empty for " + roomType.name();
				String ACTUAL_STR = image.getAttribute( "alt" );
				Matcher<String> EXPECTED_STR = JMatchers.not( JMatchers.isEmptyOrNullString() );
				SiteSessionManager.get().createCheckPoint( container, "ALT_TEXT_" + roomType.name() )
						.assertThat( REASON, ACTUAL_STR, EXPECTED_STR );
			}

			SiteSessionManager.get().assertAllCheckpoints();
		}
		catch ( WebDriverException | AssertionError e )
		{
			throw new ApplicationException( SiteSessionManager.get().getDriver(), e );
		}
	}

	@UserStory ( "PBI-63213" )
	@TestCasesIds ( ids = { 65021, 65022, 65023 } )
	@Steps ( steps = {
			@Step ( number = 1, description = "GIVEN that the user is in Accommodations Landing page /state-rooms.aspx" ),
			@Step ( number = 2, description = "WHEN user clicks on any tile displayed in \"What's Your Room Type\" section" ),
			@Step ( number = 3, description = "AND verifies the displayed images and blue circles displayed in the light box",
					expectedResults = { "The amount of blue circles matches the available images" } ),
			@Step ( number = 4, description = "AND WHEN user hover over previous and next place holders",
					expectedResults = { "THEN the arrows background-position changes to 0 -1333px and 0 -1463px" } ),
			@Step ( number = 5, description = "AND WHEN user clicks on a blue circle",
					expectedResults = { "THEN the matching image is displayed" } )
	} )
	@Test ( description = "Accommodations. What's Your Room Type Lightbox. image Pagination",
			enabled = true,
			groups = { "AU", "US", "UK"  }
	)
	public void accommodationsPage_WhatsYourRoomType_ImagePagination() throws Exception
	{
		/* GIVEN that the user is in Accommodations Landing page /state-rooms.aspx */
		PreConditions.checkNotNull( this.stateRoomsPage, "State-Rooms page is null, before starting test" );

		try
		{
			/* WHEN user clicks on any tile displayed in "What's Your Room Type" section */
			HtmlElement container = stateRoomsPage.stateRooms().getContainer();
			container.scrollIntoView();
			Enumerators.RoomType[] roomTypes = Enumerators.RoomType.values();
			for( Enumerators.RoomType roomType : roomTypes )
			{
				CBoxWrapperObject wrapper = stateRoomsPage.stateRooms().clickStateRoom( roomType );
				RoomLightboxObject rooms = wrapper.cBoxContent().cBoxLoadedContent().roomLightbox();

				/* AND verifies the displayed images and blue circles displayed in the light box */
				List<HtmlElement> buttons = rooms.getCarouselPagerButtons();
				List<HtmlElement> slides = rooms.getSlides();

				/* The amount of blue circles matches the available images */
				String REASON = "Validate that slides and carousel button are same size for " + roomType.name();
				Matcher<Collection<?>> EXPECTED_COLL = JMatchers.hasSize( slides.size() );
				SiteSessionManager.get().createCheckPoint( container, "SLIDES_COUNT_" + roomType.name() )
						.assertThat( REASON, buttons, EXPECTED_COLL );

				/* AND WHEN user hover over previous and next place holders */
				HtmlElement prev = rooms.hoverOnPrevSlide();

				/* THEN the arrows background-position changes to 0 -1333px and 0 -1463px */
				REASON = "Validate that arrow position changed for prev. in " + roomType.name();
				String ACTUAL_STR = prev.getCssValue( CSS2Properties.BACKGROUND_POSITION.getStringValue() );
				Matcher<String> EXPECTED_STR = JMatchers.is( "0px -1333px" );
				SiteSessionManager.get().createCheckPoint( container, "BACKGROUND_POSITION_PREV_" + roomType.name() )
						.assertThat( REASON, ACTUAL_STR, EXPECTED_STR );

				HtmlElement next = rooms.hoverOnNextSlide();

				REASON = "Validate that arrow position changed for next. in " + roomType.name();
				ACTUAL_STR = next.getCssValue( CSS2Properties.BACKGROUND_POSITION.getStringValue() );
				EXPECTED_STR = JMatchers.is( "0px -1463px" );
				SiteSessionManager.get().createCheckPoint( container, "BACKGROUND_POSITION_NEXT_" + roomType.name() )
						.assertThat( REASON, ACTUAL_STR, EXPECTED_STR );

				/* AND WHEN user clicks on a blue circle */
				for( int slideIndex = 0; slideIndex < slides.size(); slideIndex ++ )
				{
					HtmlElement img = slides.get( slideIndex );
					rooms.clickCarouselButton( buttons.get( slideIndex ) );

					/* THEN the matching image is displayed */
					REASON = "Validate that image index is displayed. image index : " + slideIndex;
					HtmlCondition<Boolean> CONDITION_OF_BOOL = ExpectedConditions.visibilityOf( img, true );
					SiteSessionManager.get().createCheckPoint( container, "IMG_VISIBLE_" + roomType.name() + "_" + slideIndex )
							.assertWaitThat( REASON, TimeConstants.FIVE_SECONDS, CONDITION_OF_BOOL );

					Sleeper.pauseFor( 200 );
				}

				wrapper.close();
			}

			SiteSessionManager.get().assertAllCheckpoints();
		}
		catch ( WebDriverException | AssertionError e )
		{
			throw new ApplicationException( SiteSessionManager.get().getDriver(), e );
		}
	}


	@UserStory ( "PBI-63213" )
	@TestCaseId( id = 65024 )
	@Steps ( steps = {
			@Step ( number = 1, description = "GIVEN that the user is in Accommodations Landing page /state-rooms.aspx" ),
			@Step ( number = 2, description = "WHEN user clicks on any tile displayed in \"What's Your Room Type\" section" ),
			@Step ( number = 3, description = "AND user clicks on the right/left arrows displayed in the image",
					expectedResults = { "THEN the images rotate according to the clicked arrow" } ),
	} )
	@Test ( description = "Accommodations. What's Your Room Type Lightbox. Rotating Images",
			enabled = true,
			groups = { "AU", "US", "UK"  }
	)
	public void accommodationsPage_WhatsYourRoomType_RotatingImages() throws Exception
	{
		 /* GIVEN that the user is in Accommodations Landing page /state-rooms.aspx */
		PreConditions.checkNotNull( this.stateRoomsPage, "State-Rooms page is null, before starting test" );
		try
		{
			/* WHEN user clicks on any tile displayed in "What's Your Room Type" section */
			HtmlElement container = stateRoomsPage.stateRooms().getContainer();
			container.scrollIntoView();
			Enumerators.RoomType[] roomTypes = Enumerators.RoomType.values();
			for( Enumerators.RoomType roomType : roomTypes )
			{
				/* WHEN user clicks on any tile displayed in "What's Your Room Type" section */
				CBoxWrapperObject wrapper = stateRoomsPage.stateRooms().clickStateRoom( roomType );
				RoomLightboxObject rooms = wrapper.cBoxContent().cBoxLoadedContent().roomLightbox();

				List<HtmlElement> buttons = rooms.getCarouselPagerButtons();
				List<HtmlElement> parents = rooms.getCarouselPagerButtonsParents();
				int lastIndex = buttons.size() - 1;
				rooms.clickCarouselButton( buttons.get( 0 ) );

				/* AND user clicks on the right/left arrows displayed in the image */
				rooms.clickOnNextSlide( parents.get( 1 ) );
				String REASON = "Validates that 2nd button is now active";
				String ACTUAL_STR = parents.get( 1 ).getAttribute( "class" );
				Matcher<String> EXPECTED_OF_STR = JMatchers.equalToIgnoringCase( "cycle-pager-active" );
				SiteSessionManager.get().createCheckPoint( container, "NEXT_PAGER_" + roomType )
						.assertThat( REASON, ACTUAL_STR, EXPECTED_OF_STR );

				// clicking on las bullet to record a PREV movement
				rooms.clickCarouselButton( buttons.get( lastIndex ) );
				rooms.clickOnPrevSlide( parents.get( lastIndex - 1 ) );
				REASON = "Validates that prev-last button is now active";
				ACTUAL_STR = parents.get( lastIndex - 1 ).getAttribute( "class" );
				EXPECTED_OF_STR = JMatchers.equalToIgnoringCase( "cycle-pager-active" );
				SiteSessionManager.get().createCheckPoint( container, "PREV_PAGER_" + roomType )
						.assertThat( REASON, ACTUAL_STR, EXPECTED_OF_STR );

				wrapper.close();
				Sleeper.pauseFor( 200 );
			}

			SiteSessionManager.get().assertAllCheckpoints();
		}
		catch ( WebDriverException | AssertionError e )
		{
			throw new ApplicationException( SiteSessionManager.get().getDriver(), e );
		}
	}

	@UserStory ( "PBI-63213" )
	@TestCaseId( id = 65026 )
	@Steps ( steps = {
			@Step ( number = 1, description = "GIVEN that the user is in Accommodations Landing page /state-rooms.aspx" ),
			@Step ( number = 2, description = "WHEN user clicks on any tile displayed in \"What's Your Room Type\" section" ),
			@Step ( number = 3, description = "AND user verifies the information displayed on the lightbox",
					expectedResults = { "THEN \"Available Features\" section is displayed." } ),
	} )
	@Test ( description = "Accommodations. What's Your Room Type Lightbox. Available Features",
			enabled = true,
			groups = { "AU", "US", "UK"  }
	)
	public void accommodationsPage_WhatsYourRoomType_AvailableFeatures() throws Exception
	{
		/* GIVEN that the user is in Accommodations Landing page /state-rooms.aspx */
		PreConditions.checkNotNull( this.stateRoomsPage, "State-Rooms page is null, before starting test" );
		try
		{
			HtmlElement container = stateRoomsPage.stateRooms().getContainer();
			container.scrollIntoView();
			Enumerators.RoomType[] roomTypes = Enumerators.RoomType.values();

			/* WHEN user clicks on any tile displayed in "What's Your Room Type" section */
			for( Enumerators.RoomType roomType : roomTypes )
			{
				CBoxWrapperObject wrapper = stateRoomsPage.stateRooms().clickStateRoom( roomType );
				RoomLightboxObject rooms = wrapper.cBoxContent().cBoxLoadedContent().roomLightbox();

				/* AND user verifies the information displayed on the lightbox */
				String title = rooms.getAvailableFeaturesTitle();
				List<HtmlElement> features = rooms.getAvailableFeatures();

				/* THEN "Available Features" section is displayed. */
				String REASON = "Validates the title is \"Available Features\" for " + roomType.name();
				Matcher<String> EXPECTED_OF_STR = JMatchers.equalToIgnoringCase( "Available Features" );
				SiteSessionManager.get().createCheckPoint( container, "FEATURES_TITLE_" + roomType.name() )
						.assertThat( REASON, title, EXPECTED_OF_STR );

				REASON = "Validates that features list is not empty " + roomType.name();
				Matcher<Collection<?>> EXPECTED_OF_COLL = JMatchers.hasSize( JMatchers.greaterThan( 0 ) );
				SiteSessionManager.get().createCheckPoint( container, "FEATURES_SIZE_" + roomType.name() )
						.assertThat( REASON, features, EXPECTED_OF_COLL );

				for( HtmlElement feature : features )
				{
					String text = feature.getAttribute( "textContent" );
					REASON = "Validates that feature '" + text + "' is displayed for " + roomType.name();
					Boolean ACTUAL_BOOL = feature.isDisplayed();
					Matcher<Boolean> EXPECTED_OF_BOOL = JMatchers.is( true );
					String checkpointId = text.replace( " ", "_" ).toUpperCase() + "_" + roomType.name();
					SiteSessionManager.get().createCheckPoint( container, "FEATURE_" + checkpointId )
							.assertThat( REASON, ACTUAL_BOOL, EXPECTED_OF_BOOL );
				}

				wrapper.close();
				Sleeper.pauseFor( 200 );
			}

			SiteSessionManager.get().assertAllCheckpoints();
		}
		catch ( WebDriverException | AssertionError e )
		{
			throw new ApplicationException( SiteSessionManager.get().getDriver(), e );
		}
	}

	//endregion


	//region AccommodationsTest -  After Configurations Methods Section

	@AfterMethod ( description = "", groups = { "US", "UK", "AU" }, enabled = true, alwaysRun = false )
	public void afterMethod( ITestResult testResult, XmlTest xmlTest, Method method ) throws Exception
	{
		try
		{
			if ( shouldCloseSession( testResult ) )
			{
				SiteSessionManager.get().closeSession();
				this.homePage = null;
			}
		}
		catch ( Throwable e )
		{
			//TestReporter.error( e.getLocalizedMessage() );
			throw new Exception( e.getMessage(), e );
		}
	}

	@AfterClass ( description = "quit all drivers", alwaysRun = true )
	public void afterClass() throws Exception
	{
		try
		{
			SiteSessionManager.get().endSession();
			this.homePage = null;
		}
		catch ( Throwable e )
		{
			throw new Exception( e.getMessage(), e );
		}
	}


	//endregion


	//region AccommodationsTest - Data-Provider Implementation Section

	//endregion

}
