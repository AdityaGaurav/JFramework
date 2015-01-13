package com.framework.site.objects.header;

//todo: documentation


/**
 * Enumerates the Level 1 menu items.
 * provides information about
 * <ul>
 *     <li>the menu title -> span.org[text()]</li>
 *     <li>the menu item link -> a.canHover[data-ccl-flyout]</li>
 * </ul>,
 */
public enum LevelOneMenuItem
{
	LEARN( "Learn" ),

	EXPLORE( "Explore" ),

	PLAN( "Plan" ),

	MANAGE( "Manage" );

	private final String title;

	private LevelOneMenuItem( final String title )
	{
		this.title = title;
	}

	public String getTitle()
	{
		return title;
	}

	public String getDataCcl()
	{
		return getTitle().toLowerCase();
	}
}
