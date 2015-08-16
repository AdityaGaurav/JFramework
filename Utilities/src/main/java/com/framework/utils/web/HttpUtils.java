package com.framework.utils.web;

import com.framework.utils.security.CipherPassword;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.util.List;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.utils.web
 *
 * Name   : Requests 
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-03-15 
 *
 * Time   : 13:46
 *
 */

public class HttpUtils
{

	//region HttpUtils - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( HttpUtils.class );

	private static final String GOOGLE_SERVICE_LOG_IN = "https://accounts.google.com/ServiceLoginAuth";

	private static final String GOOGLE_GMAIL = "https://mail.google.com/mail/";

	static final String USER_AGENT = "Mozilla/5.0";

	//endregion



	public static HttpResponse httpGetHttpResponse( String url, Header... headers ) throws ClientProtocolException, IOException
	{
		HttpClient client = HttpClientBuilder.create().build();
		HttpGet request = new HttpGet( url );

		if( null != headers )
		{
			for ( Header header : headers )
			{
				request.addHeader( header );
			}
		}

		return client.execute( request );
	}

	public static String loginToGmail( String userName, String password ) throws Exception
	{
		final String SERVICE_LOGIN = "https://accounts.google.com/ServiceLoginAuth";
		final String GOOGLE_GMAIL = "https://mail.google.com/mail/";

		// make sure cookies is turn on
		CookieHandler.setDefault( new CookieManager() );

		HttpGmailClient gmail = new HttpGmailClient();
		String page = gmail.getPageContent( GOOGLE_SERVICE_LOG_IN );
		String pwd = password;
		if( CipherPassword.isPasswordEncrypted( password ) )
		{
			pwd = CipherPassword.decipherPassIncludePrefix( password );
		}
		List<NameValuePair> postParams = gmail.getFormParams( page, userName, pwd );
		gmail.sendPost( GOOGLE_SERVICE_LOG_IN, postParams );

		return gmail.getPageContent( GOOGLE_GMAIL );
	}

	public static void main( String[] args )
	{
		try
		{
			String response = loginToGmail( "solmarkn@gmail.com", "10Martin19" );
			System.out.println( "response" );
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}

	}

}
