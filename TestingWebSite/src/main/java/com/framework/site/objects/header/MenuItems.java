package com.framework.site.objects.header;

/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.site.objects.header
 *
 * Name   : MenuItems
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-01-08
 *
 * Time   : 22:03
 */

public enum MenuItems
{
	// menu item Learn -> Why Carnival?
	WHY_CARNIVAL ( "Why Carnival?" ),

	// menu item Learn -> What's it like?
	WHATS_IT_LIKE ( "What's it like?" ),

	// menu item Learn -> Where can i go?
	WHERE_CAN_I_GO ( "Where can I go?" ),

	// menu item Learn -> How much is it?
	HOW_MUCH_IS_IT ( "How much is it?" ),

	// menu item Learn -> Help me decide
	HELP_ME_DECIDE ( "Help me decide." ),

	// menu item Learn -> Caribbean ( UK only )
	CARIBBEAN ( "Caribbean" ),

	// menu item Learn -> What's included ( UK only )
	WHATS_INCLUDED ( "What's included?" ),

	// menu item Learn -> On the ship ( UK only )
	ON_THE_SHIP ( "On the Ship" ),

	// menu item Explore -> Destinations ( on UK under Learn )
	DESTINATIONS ( "Destinations" ),

	// menu item Explore -> Onboard activities
	ONBOARD_ACTIVITIES ( "Onboard Activities" ),

	// menu item Explore -> Dining
	DINING ( "Dining" ),

	// menu item Explore -> Accommodations
	ACCOMMODATIONS ( "Accommodations" ),

	// menu item Explore -> Our ships
	OUR_SHIPS ( "Our Ships" ),

	// menu item Explore -> Shore Excursions ( on UK under LEARN )
	SHORE_EXCURSIONS ( "Shore Excursions" ),

	// menu item Plan -> Find a cruise
	FIND_A_CRUISE ( "Find a Cruise" ),

	// menu item Plan -> Find a port
	FIND_A_PORT ( "Find a Port" ),

	// menu item Plan -> FAQ's
	FAQ_S ( "FAQs" ),

	// menu item Plan -> Forums
	FORUMS ( "Forums" ),

	// menu item Manage -> My bookings ( UK )
	MY_BOOKING ( "My Booking" ),

	// menu item Manage -> Plan activities
	PLAN_ACTIVITIES ( "Plan Activities" ),

	// menu item Manage -> In room gifts & shopping
	IN_ROOM_GIFTS_AND_SHOPPING ( "In-Room Gifts & Shopping" ),

	// menu item Manage -> check-in
	CHECK_IN ( "Check-in" ),

	// menu item Manage -> VIFP club
	VIFP_CLUB ( "VIFP Club" ),

	// menu item Manage -> Already booked
	ALREADY_BOOKED ( "Already Booked" ),

	// menu item Manage -> Nouveau reservation
	NOUVEAU_RESERVATION ( "Nouveau Reservation" );

	private final String title;

	private MenuItems( final String title )
	{
		this.title = title;
	}

	public String getTitle()
	{
		return title;
	}

	public String getBundleKey( MenuItems item )
	{
		return item.name().toLowerCase().replace( "_", "." ) + ".png";
	}
}