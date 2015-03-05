package com.framework.driver.objects.jquery;

/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.driver.objects.jquery
 *
 * Name   : JqueryClasses 
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-01-28 
 *
 * Time   : 17:04
 *
 */

public interface JqueryUI
{
	static final String WIDGET = "ui-widget";

	static final String ACCORDION = "ui.accordion";

	static final String STATE_ACTIVE = "ui-state-active";

	static final String WIDGET_CONTENT = "ui-widget-content";

	static final String AUTO_COMPLETE = "ui-autocomplete";

	static final String STATE_DEFAULT = "ui-state-default";

	/* ========================================================================================= /
       JQUERY UI WIDGET ACCORDION
	// ========================================================================================= */

	static final String ACCORDION_HEADER = "ui-accordion-header";

	static final String ACCORDION_HEADER_ACTIVE = "ui-accordion-header-active";

	static final String ACCORDION_ICONS = "ui-accordion-icons";

	static final String ACCORDION_HEADER_ICON = "ui-accordion-header-icon";

	static final String ACCORDION_CONTENT = "ui-accordion-content";

	static final String ACCORDION_CONTENT_ACTIVE = "ui-accordion-content-active";

	interface Roles
	{
		static final String TAB_PANEL = "tabpanel";

		static final String TAB_LIST = "tablist";

		static final String TAB = "tab";
	}

	interface Aria
	{
		static final String SELECTED = "aria-selected";

		static final String EXPANDED = "aria-expanded";

		static final String HIDDEN = "aria-hidden";

		static final String LABELLED_BY = "aria-labelledby";

		static final String CONTROLS = "aria-controls";


	}

}
