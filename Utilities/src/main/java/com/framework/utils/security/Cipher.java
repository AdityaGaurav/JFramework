package com.framework.utils.security;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: org.framework.webdriver.utils
 *
 * Name   : Cipher
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2014-12-14
 *
 * Time   : 14:26
 */

public class Cipher
{

	//region Cipher - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( Cipher.class );

	protected long prime = NumberUtils.createLong( "12433" );

	private long multiplier = NumberUtils.createLong( "8629" );

	protected long seed;

	protected int abLen =  NumberUtils.createInteger( "10" );

	protected long reg;

	//endregion


	//region Cipher - Constructor Methods Section

	protected Cipher()
	{
		super();
	}

	//endregion


	//region Cipher - Public Methods Section

	public static boolean isPrimeNumber( Long num )
	{
		Double sqrt = Math.sqrt( num );

		for( long i = 2 ; i < sqrt ; i++ )
		{
			if( num % i == 0 )
			{
				return false;
			}
		}

		return true;
	}

	public static long findPrime( long from )
	{
		long num;

		for( num = from; num > 0 ; num++ )
		{
			if( isPrimeNumber( num ) )
			{
				return num;
			}
		}

		return 0;
	}

	public void setParams( long multiplier, long prime, int ablen )
	{
		this.prime = prime;
		this.multiplier = multiplier;
		this.abLen = ablen;
	}

	public void seed( long seed )
	{
		this.seed = seed;

		if( this.seed < prime / 2 )
		{
			this.seed = prime - this.seed;
		}

		this.reg = this.seed;
	}

	public int nextKey()
	{
		long reg1 = ( reg * this.multiplier ) % prime;

		int key = ( int ) ( reg1 % abLen );

		reg = ( reg * abLen + key ) % prime;

		return key;
	}

	public int encipher( long num )
	{
		int key = nextKey();
		int enc = mod( num + key, abLen );
		logger.trace( "encipher( {} ) -> {}", num, enc );
		return enc;
	}

	public int decipher( long num )
	{
		int key = nextKey();
		int dec = mod( num - key, abLen );
		logger.trace( "decipher( {} ) -> {}", num, dec );
		return dec;
	}

	public String encipher( String s, long seed, boolean encipher )
	{
		seed( seed );

		String enc = StringUtils.EMPTY;

		for( int i = 0 ; i < s.length() ; i++ )
		{
			char c = s.charAt( i );

			int dig = Character.digit( c, abLen );

			int edig = encipher ? encipher( dig ) : decipher( dig );

			enc += Character.forDigit( edig, abLen );
		}

		return enc;
	}

	public long getReg()
	{
		return reg;
	}


	//endregion


	//region Cipher - Protected Methods Section

	//endregion


	//region Cipher - Private Function Section

	private static int mod( long n, long m )
	{
		long r = n % m;

		if( r < 0 )
		{
			r += m;
		}
		return ( int ) r;
	}

	//endregion


}
