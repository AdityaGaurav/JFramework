package com.framework.testing.steping;

import com.framework.testing.annotations.Step;
import com.framework.testing.annotations.Steps;
import com.framework.testing.steping.exceptions.DuplicateTestStepException;
import com.framework.utils.error.PreConditions;
import com.google.common.collect.Maps;
import org.apache.commons.configuration.ConfigurationException;

import java.util.Map;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.testing.annotations
 *
 * Name   : StepsAnnotation 
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-01-25 
 *
 * Time   : 02:25
 *
 */

public class StepsAnnotation
{
	//region StepsAnnotation - Variables Declaration and Initialization Section.

	private Map<Float,Step> map = Maps.newLinkedHashMap();

	//endregion


	//region StepsAnnotation - Constructor Methods Section

	public StepsAnnotation( Steps steps ) throws ConfigurationException
	{
		boolean isFirst = true;
		float prev = 0F;
		try
		{
			for( Step step : steps.steps() )
			{
				PreConditions.checkState( step.number() >= 1, "A Step number must be positive < %d >", step.number() );
				if( isFirst )
				{
					PreConditions.checkState( step.number() == 1, "First step number should be 1, however is < %d >", step.number() );
					prev = step.number();
					map.put( step.number(), step );
					isFirst = false;
				}
				else
				{
					PreConditions.checkState(
							( step.number() - prev ) <= 1F , "Step sequence is wrong. prev: <%d>, current: <%d>", prev, step.number() );
					if( map.containsKey( step.number() ) )
					{
						throw new DuplicateTestStepException( "Duplicated step id found (" + step.number() + ")" );
					}

					map.put( step.number(), step );
				}
			}
		}
		catch ( Exception e )
		{
			throw new ConfigurationException( e );
		}
	}

	public Map<Float, Step> getMap()
	{
		return map;
	}

	//endregion

}
