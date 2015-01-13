package com.framework.utils.web;

import com.google.common.collect.Maps;

import java.util.Map;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: org.framework.webdriver.utils
 *
 * Name   : HttpURLConnectionErrorCodes
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2014-12-14
 *
 * Time   : 13:23
 */

public final class HttpURLConnectionErrorCodes
{

	private static final Map<Integer,String> codes;

	static
	{
		codes = Maps.newHashMap();
		codes.put( 200, "HTTP_OK" );
		codes.put( 201, "HTTP_CREATED" );
		codes.put( 202, "HTTP_ACCEPTED" );
		codes.put( 203, "HTTP_NOT_AUTHORITATIVE" );
		codes.put( 204, "HTTP_NO_CONTENT" );
		codes.put( 205, "HTTP_RESET" );
		codes.put( 206, "HTTP_PARTIAL" );
		codes.put( 300, "HTTP_MULT_CHOICE" );
		codes.put( 301, "HTTP_MOVED_PERM" );
		codes.put( 302, "HTTP_MOVED_TEMP" );
		codes.put( 303, "HTTP_SEE_OTHER" );
		codes.put( 304, "HTTP_NOT_MODIFIED" );
		codes.put( 305, "HTTP_USE_PROXY" );
		codes.put( 400, "HTTP_BAD_REQUEST" );
		codes.put( 401, "HTTP_UNAUTHORIZED" );
		codes.put( 402, "HTTP_PAYMENT_REQUIRED" );
		codes.put( 403, "HTTP_FORBIDDEN" );
		codes.put( 404, "HTTP_NOT_FOUND" );
		codes.put( 405, "HTTP_BAD_METHOD" );
		codes.put( 406, "HTTP_NOT_ACCEPTABLE" );
		codes.put( 407, "HTTP_PROXY_AUTH" );
		codes.put( 408, "HTTP_CLIENT_TIMEOUT" );
		codes.put( 409, "HTTP_CONFLICT" );
		codes.put( 410, "HTTP_GONE" );
		codes.put( 411, "HTTP_LENGTH_REQUIRED" );
		codes.put( 412, "HTTP_PRECON_FAILED" );
		codes.put( 413, "HTTP_ENTITY_TOO_LARGE" );
		codes.put( 414, "HTTP_REQ_TOO_LONG" );
		codes.put( 415, "HTTP_UNSUPPORTED_TYPE" );
		codes.put( 500, "HTTP_INTERNAL_ERROR/HTTP_SERVER_ERROR" );
		codes.put( 501, "HTTP_NOT_IMPLEMENTED" );
		codes.put( 502, "HTTP_BAD_GATEWAY" );
		codes.put( 503, "HTTP_UNAVAILABLE" );
		codes.put( 504, "HTTP_GATEWAY_TIMEOUT" );
		codes.put( 505, "HTTP_VERSION" );
	}

	public static String getHttpErrorName( int errorCode )
	{
		return codes.get( errorCode );
	}

}
