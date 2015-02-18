package com.framework.site.data;

import com.framework.utils.string.LogStringStyle;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.Period;
import org.joda.time.PeriodType;
import org.springframework.format.annotation.DateTimeFormat;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.site.data
 *
 * Name   : Companion 
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-02-08 
 *
 * Time   : 02:30
 *
 */

public class Companion
{

	//region Companion - Variables Declaration and Initialization Section.

	private final String firstName, lastName;

	private DateTime dateOfBirth;

	private char gender;

	private String nationality, state;

	private String phoneCountryCode, phoneAreaCode;

	private long phoneNumber, vifpClub;

	private String ageName;

	private float age;

	//endregion


	//region Companion - Constructor Methods Section

	public Companion( final String firstName, final String lastName )
	{
		this.firstName = firstName;
		this.lastName = lastName;
	}


	//endregion


	//region Companion - Public Methods Section

	public String getFirstName()
	{
		return firstName;
	}

	public String getLastName()
	{
		return lastName;
	}

	public DateTime getDateOfBirth()
	{
		return dateOfBirth;
	}

	public char getGender()
	{
		return gender;
	}

	public void setGender( final char gender )
	{
		this.gender = gender;
	}

	public String getNationality()
	{
		return nationality;
	}

	public void setNationality( final String nationality )
	{
		this.nationality = nationality;
	}

	public String getState()
	{
		return state;
	}

	public void setState( final String state )
	{
		this.state = state;
	}

	public String getPhoneCountryCode()
	{
		return phoneCountryCode;
	}

	public void setPhoneCountryCode( final String phoneCountryCode )
	{
		this.phoneCountryCode = phoneCountryCode;
	}

	public String getPhoneAreaCode()
	{
		return phoneAreaCode;
	}

	public void setPhoneAreaCode( final String phoneAreaCode )
	{
		this.phoneAreaCode = phoneAreaCode;
	}

	public long getPhoneNumber()
	{
		return phoneNumber;
	}

	public void setPhoneNumber( final long phoneNumber )
	{
		this.phoneNumber = phoneNumber;
	}

	public long getVifpClub()
	{
		return vifpClub;
	}

	public void setVifpClub( final long vifpClub )
	{
		this.vifpClub = vifpClub;
	}

	public String getAgeName()
	{
		return ageName;
	}

	public float getAge()
	{
		return age;
	}

	@DateTimeFormat (pattern = "MM/dd/yyyy")
	public void setDateOfBirth( final DateTime dateOfBirth )
	{
		this.dateOfBirth = dateOfBirth;
		LocalDate birth = new LocalDate( dateOfBirth );
		LocalDate date = new LocalDate( DateTime.now() );
		Period period = new Period( birth, date, PeriodType.yearMonthDay() );
		this.age = NumberUtils.createFloat( period.getYears() + "." + period.getMonths() );
		this.ageName = period.getYears() + " years and " + period.getMonths() + " months";
	}

	@Override
	public String toString()
	{
		return new ToStringBuilder( this, LogStringStyle.LOG_LINE_STYLE )
				.append( "firstName", firstName )
				.append( "lastName", lastName )
				.append( "ageName", ageName )
				.toString();
	}

	//endregion



}
