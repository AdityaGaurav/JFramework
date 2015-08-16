package com.framework.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.config
 *
 * Name   : LocalConfigurations 
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-03-06 
 *
 * Time   : 08:58
 *
 */

@Configuration
@ComponentScan
public class LocalConfigurations
{

	//region LocalConfigurations - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( LocalConfigurations.class );

	//endregion


	//region LocalConfigurations - Constructor Methods Section

	//endregion


	//region LocalConfigurations - Public Methods Section

	@Bean
	LocalConfigService mockMessageService()
	{
		return new LocalConfigService()
		{
			public String getMessage()
			{
				return "Hello World!";
			}
		};
	}

	//endregion


	//region LocalConfigurations - Protected Methods Section

	//endregion


	//region LocalConfigurations - Private Function Section

	//endregion


	//region LocalConfigurations - Inner Classes Implementation Section

	//endregion

}
