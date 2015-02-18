package com.framework.driver.event;

/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.driver.event
 *
 * Name   : EventTypes 
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-02-06 
 *
 * Time   : 16:16
 *
 */

interface EventTypes
{
	/**
	 * Constant for the .
	 */
	static final int EVENT_NAVIGATE_TO = 1;

	static final int EVENT_GET_CURRENT_URL = 2;

	static final int EVENT_GET_TITLE = 3;

	static final int EVENT_GET_PAGE_SOURCE = 4;

	static final int EVENT_CLOSE = 5;

	static final int EVENT_QUIT = 6;

	static final int EVENT_GET_WINDOW_HANDLE_OR_HANDLES = 7;

	static final int EVENT_GET_LOGS = 8;

	static final int EVENT_ADD_COOKIE = 9;

	static final int EVENT_DELETE_COOKIE_NAME = 10;

	static final int EVENT_DELETE_COOKIE = 101;

	static final int EVENT_DELETE_ALL_COOKIES = 11;

	static final int EVENT_GET_COOKIES = 12;

	static final int EVENT_GET_COOKIE = 13;

	static final int EVENT_NAVIGATE_BACK = 14;

	static final int EVENT_NAVIGATE_FORWARD = 15;

	static final int EVENT_NAVIGATE_REFRESH = 16;

	static final int EVENT_DRIVER_FIND_ELEMENT = 17;

	static final int EVENT_DRIVER_FIND_ELEMENTS = 18;

	static final int EVENT_IMPLICITLY_WAIT = 19;

	static final int EVENT_SCRIPT_TIMEOUT = 20;

	static final int EVENT_PAGE_LOAD_TIMEOUT = 21;

	static final int EVENT_WINDOW_SIZE = 22;

	static final int EVENT_WINDOW_POSITION = 23;

	static final int EVENT_WINDOW_MAXIMIZE = 24;

	static final int EVENT_JAVASCRIPT = 25;

	static final int EVENT_ASYNC_JAVASCRIPT = 26;

	static final int EVENT_MOUSE_ACTION = 27;

	static final int EVENT_KEYBOARD_ACTION = 28;

	static final int EVENT_WINDOW_TARGET_LOCATOR = 29;

	static final int EVENT_FRAME_TARGET_LOCATOR = 30;

	static final int EVENT_PARENT_FRAME_TARGET_LOCATOR = 31;

	static final int EVENT_DEFAULT_CONTENT_TARGET_LOCATOR = 32;

	static final int EVENT_ACTIVE_ELEMENT_TARGET_LOCATOR = 33;

	static final int EVENT_ELEMENT_FIND_ELEMENT = 34;

	static final int EVENT_ELEMENT_FIND_ELEMENTS = 35;

	static final int EVENT_HOVER = 36;

	static final int EVENT_CLICK = 37;

	static final int EVENT_SUBMIT = 38;

	static final int EVENT_CLEAR = 39;

	static final int EVENT_SEND_KEYS = 40;

    static final int EVENT_GET_TEXT = 41;
}
