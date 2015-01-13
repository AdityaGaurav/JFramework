package com.framework.driver.event;

import java.util.Set;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.driver.event
 *
 * Name   : SessionStorage
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-01-12
 *
 * Time   : 14:24
 */

public interface SessionStorage
{
	void removeItemFromSessionStorage( String item );

	boolean isItemPresentInSessionStorage( String item );

	public String getItemFromSessionStorage( String key );

	public String getKeyFromSessionStorage( int key );

	public Long getSessionStorageLength();

	public void setItemInSessionStorage( String item, String value );

	public void clearSessionStorage();

	public Set<String> getSessionStorageKeys();
}
