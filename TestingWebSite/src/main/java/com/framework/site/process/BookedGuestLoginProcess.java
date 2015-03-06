package com.framework.site.process;

import com.framework.driver.objects.PageObject;
import com.framework.site.data.Guest;
import com.framework.site.exceptions.BookedGuestLoginException;
import com.framework.site.objects.header.interfaces.Header;
import com.framework.site.pages.bookedguest.BookedGuestLogonPage;
import com.framework.utils.error.PreConditions;
import com.framework.utils.security.CipherPassword;
import com.framework.utils.string.LogStringStyle;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.AbstractMap;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.site.process
 *
 * Name   : LoginProcess 
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-02-07 
 *
 * Time   : 23:01
 *
 */

public class BookedGuestLoginProcess
{

	//region BookedGuestLoginProcess - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( BookedGuestLoginProcess.class );

	private Guest user;

	private final Header.HeaderLinks headerLinks;

	private String userPassword;

	//endregion


	//region BookedGuestLoginProcess - Constructor Methods Section

	public BookedGuestLoginProcess( Header.HeaderLinks headerLinks )
	{
		this.headerLinks = PreConditions.checkNotNull( headerLinks, "HeaderLinks cannot be null." );
	}

	//endregion


	//region BookedGuestLoginProcess - Getters and Setter Methods Section

	public Guest getUser()
	{
		return user;
	}

	public void setUser( final Guest user )
	{
		this.user = user;
		this.userPassword = user.getCurrentPassword();
	}

	//endregion


	//region BookedGuestLoginProcess - Service Methods Section

	@Override
	public String toString()
	{
		return new ToStringBuilder( this, LogStringStyle.LOG_LINE_STYLE )
				.append( "user", this.user )
				.toString();
	}

	//endregion


	//region LoginProcess - Business Function Section

	public PageObject doLogin( Class<?> returnPage ) throws BookedGuestLoginException
	{
		java.util.Map.Entry<String,String> userCredential = getUserCredentials();
		BookedGuestLogonPage page = headerLinks.clickLogin();
		page.login( userCredential.getKey(), userCredential.getValue() );

		try
		{
			return ( PageObject ) returnPage.newInstance();
		}
		catch ( InstantiationException | IllegalAccessException e )
		{
			logger.warn( "Could not create class {}", returnPage.getName() );
			//throw new BookedGuestLoginException( e.getMessage(), e );
			throw new RuntimeException( e );
		}
	}

	private java.util.Map.Entry<String,String> getUserCredentials()
	{
		if( CipherPassword.isPasswordEncrypted( userPassword ) )
		{
			userPassword = CipherPassword.decipherPassIncludePrefix( userPassword );
		}
		return new AbstractMap.SimpleImmutableEntry<>( user.getEmailAddress(), userPassword );
	}

	//endregion


}
