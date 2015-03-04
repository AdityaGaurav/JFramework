package com.framework.utils.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.Assert;

import java.util.Locale;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: org.framework.webdriver.utils
 *
 * Name   : AppContextProxy
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2014-12-31
 *
 * Time   : 21:33
 */

public class AppContextProxy implements ApplicationContextAware
{
	private static final AppContextProxy _inst = new AppContextProxy();

	private ApplicationContext ctx;

	private AppContextProxy()
	{
	}

	public static AppContextProxy get()
	{
		return _inst;
	}

	public void setApplicationContext( ApplicationContext appCtx )
	{
		this.ctx = appCtx;
	}

	public Object getBean( String beanId )
	{
		Assert.notNull( ctx, "calling getApplicationContext before " + this.getClass().getName() + " is set by Spring" );
		return ctx.getBean( beanId );
	}

	/**
	 * @param location
	 *
	 * @return
	 */
	public Object getResource( String location )
	{
		Assert.notNull( ctx, "calling getApplicationContext before " + this.getClass().getName() + " is set by Spring" );
		return ctx.getResource( location );
	}

	public Object getMessage( String code, Object[] args, Locale locale )
	{
		Assert.notNull( ctx, "calling getApplicationContext before " + this.getClass().getName() + " is set by Spring" );
		return ctx.getMessage( code, args, locale );
	}

	public Object getMessage( String code, Object[] args, String defaultMessage, Locale locale )
	{
		Assert.notNull( ctx, "calling getApplicationContext before " + this.getClass().getName() + " is set by Spring" );
		return ctx.getMessage( code, args, defaultMessage, locale );
	}

}
