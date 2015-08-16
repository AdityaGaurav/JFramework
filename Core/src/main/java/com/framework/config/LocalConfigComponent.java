package com.framework.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.config
 *
 * Name   : LocalConfigComponent 
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-03-06 
 *
 * Time   : 09:01
 *
 */

@Component
public class LocalConfigComponent
{

	//region LocalConfigComponent - Variables Declaration and Initialization Section.

	final private LocalConfigService service;

	//endregion


	//region LocalConfigComponent - Constructor Methods Section

	@Autowired
	public LocalConfigComponent( final LocalConfigService service )
	{
		this.service = service;
	}

	public String getMessage()
	{
		return service.getMessage();
	}

	//endregion


	//region LocalConfigComponent - Public Methods Section

	//endregion


	//region LocalConfigComponent - Protected Methods Section

	//endregion


	//region LocalConfigComponent - Private Function Section

	//endregion


	//region LocalConfigComponent - Inner Classes Implementation Section

	//endregion

}
