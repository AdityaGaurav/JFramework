package com.framework.site.data;

/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.site.data
 *
 * Name   : Enumerators 
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-03-07 
 *
 * Time   : 12:41
 *
 */

public interface Enumerators
{
	/**
	 * the enumeration for sorting ship cards by Featured or by A-Z
	 */
	enum SortType{ FEATURED, A_Z }

	/**
	 * the enumeration for displaying ship cards by gris or by list.
	 */
	enum LayoutType{ BY_GRID, BY_LIST }

	enum Travelers
	{
		ONE_TRAVELER, TWO_TRAVELERS, THREE_TRAVELERS, FOUR_TRAVELERS, FIVE_TRAVELERS, MULTIPLE_STATE_ROOMS
	}

	enum NavStickItem
	{
		WHATS_INCLUDED, ON_THE_SHIP, SHORE_EXCURSIONS, DESTINATIONS, WHERE_WE_SAIL_FROM, EASTERN_CARIBBEAN,
		WESTERN_CARIBBEAN, SOUTHERN_CARIBBEAN
	}

	public enum SelectRandom
	{
		LESS_TIMES_SELECTED, OLDER_SELECTED, MOST_TIME_SELECTED, LAST_SELECTED, RANDOM, NONE
	}

	public enum UserFeedbackTooltip
	{
		BED, SOFT_BEDDING, ROOM_STEWARD, INTERACTIVE_TV, PRIVATE_BALCONY, ROOM_SERVICE, CONNECTING_DOORS
	}

	public enum RoomType
	{
		INTERIOR, BALCONY, SUITE, OCEAN_VIEW
	}

	public enum CboxLoadedContentType
	{
		STATE_ROOMS
	}

	public enum Decks
	{
		LOWER_DECK, AFT, MIDSHIP, FORWARD, UPPER_DECK
	}

	public enum AboutUsLinks
	{
		ABOUT_US, AWARDS, INVESTOR_RELATIONS, WORLD_S_LEADING_CRUISE_LINES, CORPORATE_ADDRESS
	}

	public enum FooterSection
	{
		SPECIAL_OFFERS, BOOK_A_CRUISE, GROUP_TRAVEL, ALREADY_BOOKED, CUSTOMER_SERVICE, ABOUT_CARNIVAL
	}

	public enum ShipDetailsSection { OVERVIEW, DECK_PLANS, ACCOMMODATIONS, DINING_AND_ACTIVITIES, ITINERARY, GUEST_PHOTOS }

	public enum Activities{ VIEW_ALL, ACTIVITIES, BARS, CASINO, ENTERTAINMENT, EVENTS, OUTDOOR, SPA_FITNESS, KIDS_TEENS }
}
