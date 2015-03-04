package com.framework.testing.steping;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.testing.steping
 *
 * Name   : Issue 
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-01-25 
 *
 * Time   : 20:48
 *
 */

public class IssueTracker
{

	//region Issue - Variables Declaration and Initialization Section.

	private final String value;

	private String issueTrackerUrl;

	//endregion


	//region Issue - Constructor Methods Section

	public IssueTracker( final String value )
	{
		this.value = value;
	}

	//endregion


	//region Issue - Public Methods Section

	public String getValue()
	{
		return value;
	}

	public String getIssueTrackerUrl()
	{
		return issueTrackerUrl;
	}

	public void setIssueTrackerUrl( final String issueTrackerUrl )
	{
		this.issueTrackerUrl = issueTrackerUrl;
	}

	//endregion

	@Override
	public boolean equals( final Object o )
	{
		if ( this == o )
		{
			return true;
		}
		if ( ! ( o instanceof IssueTracker ) )
		{
			return false;
		}

		final IssueTracker that = ( IssueTracker ) o;

		if ( ! value.equals( that.value ) )
		{
			return false;
		}

		return true;
	}

	@Override
	public int hashCode()
	{
		return value.hashCode();
	}

	@Override
	public String toString()
	{
		return new ToStringBuilder( this )
				.append( "value", value )
				.append( "issue Tracker Url", StringUtils.defaultString( issueTrackerUrl, "N/A" ) )
				.toString();
	}
}
