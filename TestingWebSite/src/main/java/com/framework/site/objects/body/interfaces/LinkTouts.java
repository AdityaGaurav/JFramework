package com.framework.site.objects.body.interfaces;

import org.openqa.selenium.By;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.site.objects.body
 *
 * Name   : LinkTout
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-01-06
 *
 * Time   : 19:47
 */

public interface LinkTouts
{
	static final String LOGICAL_NAME = "Link Touts Container";

	static final By ROOT_BY = By.className( "link-tout" );

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
}
