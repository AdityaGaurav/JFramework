package com.framework.site.data;

import com.framework.utils.string.LogStringStyle;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.math.NumberUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.Period;
import org.joda.time.PeriodType;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.List;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.site.data
 *
 * Name   : Guest 
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-02-08 
 *
 * Time   : 02:00
 *
 */

public class Guest
{
	//region Guest - Variables Declaration and Initialization Section.

   	private final String firstName, lastName, emailAddress;

	private String currentPassword;

	private String middleName, title;

	private DateTime dateOfBirth;

	private String ageName;

	private float age;

	private char gender;

	private Number vifpClub;

	private boolean useEmailAsUserName, sendOffersToEmail, sendOffersToAddress;

	private String country, address1, address2, city, state, zip, phoneType;

	private long phoneNumber;

	private String phoneAreaCode;

	private List<Companion> companions;

	private String preferenceDining;

	private boolean prepaidGratuities, vacationProtection, cruiseTransferFromAirport, cruiseTransferToAirport;

	//endregion


	//region Guest - Constructor Methods Section

	public Guest( final String firstName, final String lastName, final String emailAddress )
	{
		this.firstName = firstName;
		this.lastName = lastName;
		this.emailAddress = emailAddress;
	}

	//endregion


	//region Guest - Public Methods Section

	public String getFirstName()
	{
		return firstName;
	}

	public String getLastName()
	{
		return lastName;
	}

	public String getEmailAddress()
	{
		return emailAddress;
	}

	public String getCurrentPassword()
	{
		return currentPassword;
	}

	public void setCurrentPassword( final String currentPassword )
	{
		this.currentPassword = currentPassword;
	}

	public String getMiddleName()
	{
		return middleName;
	}

	public void setMiddleName( final String middleName )
	{
		this.middleName = middleName;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle( final String title )
	{
		this.title = title;
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

	public DateTime getDateOfBirth()
	{
		return dateOfBirth;
	}

	public String getAgeName()
	{
		return ageName;
	}

	public float getAge()
	{
		return age;
	}

	public char getGender()
	{
		return gender;
	}

	public void setGender( final char gender )
	{
		this.gender = gender;
	}

	public Number getVifpClub()
	{
		return vifpClub;
	}

	public void setVifpClub( final Number vifpClub )
	{
		this.vifpClub = vifpClub;
	}

	public boolean isUseEmailAsUserName()
	{
		return useEmailAsUserName;
	}

	public void setUseEmailAsUserName( final boolean useEmailAsUserName )
	{
		this.useEmailAsUserName = useEmailAsUserName;
	}

	public boolean isSendOffersToEmail()
	{
		return sendOffersToEmail;
	}

	public void setSendOffersToEmail( final boolean sendOffersToEmail )
	{
		this.sendOffersToEmail = sendOffersToEmail;
	}

	public boolean isSendOffersToAddress()
	{
		return sendOffersToAddress;
	}

	public void setSendOffersToAddress( final boolean sendOffersToAddress )
	{
		this.sendOffersToAddress = sendOffersToAddress;
	}

	public String getCountry()
	{
		return country;
	}

	public void setCountry( final String country )
	{
		this.country = country;
	}

	public String getAddress1()
	{
		return address1;
	}

	public void setAddress1( final String address1 )
	{
		this.address1 = address1;
	}

	public String getAddress2()
	{
		return address2;
	}

	public void setAddress2( final String address2 )
	{
		this.address2 = address2;
	}

	public String getCity()
	{
		return city;
	}

	public void setCity( final String city )
	{
		this.city = city;
	}

	public String getState()
	{
		return state;
	}

	public void setState( final String state )
	{
		this.state = state;
	}

	public String getZip()
	{
		return zip;
	}

	public void setZip( final String zip )
	{
		this.zip = zip;
	}

	public String getPhoneType()
	{
		return phoneType;
	}

	public void setPhoneType( final String phoneType )
	{
		this.phoneType = phoneType;
	}

	public long getPhoneNumber()
	{
		return phoneNumber;
	}

	public void setPhoneNumber( final long phoneNumber )
	{
		this.phoneNumber = phoneNumber;
	}

	public String getPhoneAreaCode()
	{
		return phoneAreaCode;
	}

	public void setPhoneAreaCode( final String phoneAreaCode )
	{
		this.phoneAreaCode = phoneAreaCode;
	}

	public List<Companion> getCompanions()
	{
		return companions;
	}

	public void setCompanions( final List<Companion> companions )
	{
		this.companions = companions;
	}

	public String getPreferenceDining()
	{
		return preferenceDining;
	}

	public void setPreferenceDining( final String preferenceDining )
	{
		this.preferenceDining = preferenceDining;
	}

	public boolean isPrepaidGratuities()
	{
		return prepaidGratuities;
	}

	public void setPrepaidGratuities( final boolean prepaidGratuities )
	{
		this.prepaidGratuities = prepaidGratuities;
	}

	public boolean isVacationProtection()
	{
		return vacationProtection;
	}

	public void setVacationProtection( final boolean vacationProtection )
	{
		this.vacationProtection = vacationProtection;
	}

	public boolean isCruiseTransferFromAirport()
	{
		return cruiseTransferFromAirport;
	}

	public void setCruiseTransferFromAirport( final boolean cruiseTransferFromAirport )
	{
		this.cruiseTransferFromAirport = cruiseTransferFromAirport;
	}

	public boolean isCruiseTransferToAirport()
	{
		return cruiseTransferToAirport;
	}

	public void setCruiseTransferToAirport( final boolean cruiseTransferToAirport )
	{
		this.cruiseTransferToAirport = cruiseTransferToAirport;
	}

	@Override
	public String toString()
	{
		return new ToStringBuilder( this, LogStringStyle.LOG_LINE_STYLE )
				.append( "firstName", firstName )
				.append( "lastName", lastName )
				.append( "email", emailAddress )
				.append( "ageName", ageName )
				.toString();
	}


	//endregion


}
