package com.framework.driver.event;

import com.google.common.base.Function;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.driver.event
 *
 * Name   : ExpectedCondition 
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-02-05 
 *
 * Time   : 01:20
 *
 */

public interface HtmlCondition<T> extends Function<HtmlDriver, T> {}
