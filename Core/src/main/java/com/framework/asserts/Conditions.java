package com.framework.asserts;

import com.framework.driver.objects.Link;
import org.assertj.core.api.Condition;
import org.openqa.selenium.WebElement;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.asserts
 *
 * Name   : Conditions
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-01-05
 *
 * Time   : 16:09
 */

public class Conditions
{

	public static final Condition<WebElement> elementIsVisible = new Condition<WebElement>()
	{
		@Override
		public boolean matches( final WebElement value )
		{
			return value.isDisplayed();
		}
	};

	public static final Condition<Link> linkIsVisible = new Condition<Link>()
	{
		@Override
		public boolean matches( final Link value )
		{
			return value.isDisplayed();
		}
	};


}
