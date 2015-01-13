package com.framework.utils.security;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Locale;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: org.framework.webdriver.utils
 *
 * Name   : CipherPassword
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2014-12-14
 *
 * Time   : 22:21
 */

public class CipherPassword extends Cipher
{

	//region CipherPassword - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( CipherPassword.class );

	public static final String ENCIPHERED_PREFIX = "9D4S0S4M29";

	//endregion


	//region CipherPassword - Constructor Methods Section

	public CipherPassword() {}

	//endregion


	//region CipherPassword - Public Methods Section

	public static boolean isPasswordEncrypted( String pwd )
	{
		return pwd.startsWith( ENCIPHERED_PREFIX );
	}

	public int nextKey()
	{
		long reg1 = mod3( reg, prime );
		int key = ( int ) ( reg1 % abLen );
		reg = ( reg * abLen + key ) % prime;
		return key;
	}

	public static String decipherPass( String xp )
	{
		long seed = 0;

		if( xp.length() <= 4 )
		{
			return StringUtils.EMPTY;
		}

		for( int i = 0 ; i < 4 ; i++ )
		{
			char c = xp.charAt( i );
			seed = seed * 16 + Character.digit( c, 16 );
		}

		String p = xp.substring( 4, xp.length() );
		CipherPassword cp = new CipherPassword();
		cp.setParams( 0, 5197, 16 );
		String pass = cp.encipher( p, seed, false );
		byte[] bPass = new byte[ pass.length() / 2 ];

		for( int i = 0 ; i < pass.length() / 2 ; i++ )
		{
			int high = Character.digit( pass.charAt( i * 2 ), 16 );
			int low = Character.digit( pass.charAt( i * 2 + 1 ), 16 );
			bPass[ i ] = ( byte ) ( high * 16 + low );
		}
		pass = new String( bPass );
		return pass;
	}

	public static String encipherPass( String pass ) throws Exception
	{
		String s = StringUtils.EMPTY;

		byte[] bs = pass.getBytes( "8859_1" );

		for( byte b : bs )
		{
			int low = b & 0x0F;
			int high = ( b >> 4 ) & 0x0F;
			s += Character.forDigit( high, 16 );
			s += Character.forDigit( low, 16 );
		}

		long seed = 0;

		for( int i = 0 ; i < 16 ; i++ )
		{
			double r = Math.random();
			seed = seed * 2 + ( ( r > 0.5 ) ? 0 : 1 );
		}
		CipherPassword cp = new CipherPassword();
		cp.setParams( 0, 5197, 16 );
		String xp = cp.encipher( s, seed, true );

		// write down the seed and than the encrypted number
		String ee = StringUtils.EMPTY;

		for( int i = 0 ; i < 4 ; i++ )
		{
			ee = Character.forDigit( ( int ) ( seed % 16 ), 16 ) + ee;
			seed = seed / 16;
		}

		return ee + xp;
	}

	public static String encipherPassIncludePrefix( String pass ) throws Exception
	{
		final StringBuilder sb = new StringBuilder( ( pass.length() * 2 ) + 15 );
		sb.append( ENCIPHERED_PREFIX ).append( encipherPass( pass ) );
		return sb.toString();
	}

	public static String decipherPassIncludePrefix( String xp )
	{
		if( !xp.startsWith( ENCIPHERED_PREFIX ) )
		{
			return xp;
		}
		return decipherPass( xp.substring( ENCIPHERED_PREFIX.length() ) );
	}

	public static void main( String argv[] )
	{
		try
		{
			String pass;

			System.out.print( "Enter Pass:" );
			BufferedReader br = new BufferedReader( new InputStreamReader( System.in ) );
			pass = br.readLine();
			System.out.println( "pass=" + pass );

			String tt = encipherPassIncludePrefix( pass );
			System.out.println( tt.toUpperCase( Locale.getDefault() ) );
			System.out.println( decipherPassIncludePrefix( tt ) );

		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
	}

	//endregion


	//region CipherPassword - Private Function Section

	private long mod3( long reg, long prime )
	{
		long r1 = ( reg * reg ) % prime;
		return ( r1 * reg ) % prime;
	}

	//endregion

}
