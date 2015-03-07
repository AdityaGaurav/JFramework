package com.framework.site.objects.body.interfaces;

/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.site.objects.body.interfaces
 *
 * Name   : LinkTout 
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-03-07 
 *
 * Time   : 12:51
 *
 */

public interface LinkTout
{
	static final String LOGICAL_NAME = "Link Tout";

	String getReference();

	String getImageAlt();

	String getLeftTitle();

	String getRightTitle();

	String getParagraphText();

	String activateTout();
}
