package com.framework.driver.event;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.HasTouchScreen;
import org.openqa.selenium.interactions.TouchScreen;
import org.openqa.selenium.interactions.internal.Coordinates;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: org.framework.webdriver.driver
 *
 * Name   : EventTouch
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2014-12-14
 *
 * Time   : 12:46
 */

public class EventTouch implements TouchScreen
{

	//region EventTouch - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( EventTouch.class );

	private final WebDriver driver;

	private final EventListener dispatcher;

	private final TouchScreen touchScreen;

	//endregion


	//region EventTouch - Constructor Methods Section

	public EventTouch( WebDriver driver, EventListener dispatcher )
	{
		this.driver = driver;
		this.dispatcher = dispatcher;
		this.touchScreen = ( ( HasTouchScreen ) this.driver ).getTouch();
	}

	//endregion


	//region EventTouch - Public Methods Section

	@Override
	public void singleTap( Coordinates where )
	{
		touchScreen.singleTap( where );
	}

	@Override
	public void down( int x, int y )
	{
		touchScreen.down( x, y );
	}

	@Override
	public void up( int x, int y )
	{
		touchScreen.up( x, y );
	}

	@Override
	public void move( int x, int y )
	{
		touchScreen.move( x, y );
	}

	@Override
	public void scroll( Coordinates where, int xOffset, int yOffset )
	{
		touchScreen.scroll( where, xOffset, yOffset );
	}

	@Override
	public void doubleTap( Coordinates where )
	{
		touchScreen.doubleTap( where );
	}

	@Override
	public void longPress( Coordinates where )
	{
		touchScreen.longPress( where );
	}

	@Override
	public void scroll( int xOffset, int yOffset )
	{
		touchScreen.scroll( xOffset, yOffset );
	}

	@Override
	public void flick( int xSpeed, int ySpeed )
	{
		touchScreen.flick( xSpeed, ySpeed );
	}

	@Override
	public void flick( Coordinates where, int xOffset, int yOffset, int speed )
	{
		touchScreen.flick( where, xOffset, yOffset, speed );
	}

	//endregion


}
