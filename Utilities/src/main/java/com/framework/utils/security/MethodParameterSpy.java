/*
 * Copyright (c) 2013, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.framework.utils.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;


public class MethodParameterSpy
{

	private static final Logger logger = LoggerFactory.getLogger( MethodParameterSpy.class );

	private static final String fmt = "%24s: %s%n";

	public static void printClassConstructors( Class c )
	{
		Constructor[] allConstructors = c.getConstructors();
		System.out.format( fmt, "Number of constructors", allConstructors.length );
		for ( Constructor currentConstructor : allConstructors )
		{
			printConstructor( currentConstructor );
		}
		Constructor[] allDeclConst = c.getDeclaredConstructors();
		System.out.format( fmt, "Number of declared constructors",
				allDeclConst.length );
		for ( Constructor currentDeclConst : allDeclConst )
		{
			printConstructor( currentDeclConst );
		}
	}

	public static void printClassMethods( Class c )
	{
		Method[] allMethods = c.getDeclaredMethods();
		System.out.format( fmt, "Number of methods", allMethods.length );
		for ( Method m : allMethods )
		{
			printMethod( m );
		}
	}

	public static void printConstructor( Constructor c )
	{
		System.out.format( "%s%n", c.toGenericString() );
		Parameter[] params = c.getParameters();
		System.out.format( fmt, "Number of parameters", params.length );
		for ( int i = 0; i < params.length; i++ )
		{
			printParameter( params[ i ] );
		}
	}

	public static void printMethod( Method m )
	{
		System.out.format( "%s%n", m.toGenericString() );
		System.out.format( fmt, "Return type", m.getReturnType() );
		System.out.format( fmt, "Generic return type", m.getGenericReturnType() );

		Parameter[] params = m.getParameters();
		for ( int i = 0; i < params.length; i++ )
		{
			printParameter( params[ i ] );
		}
	}

	public static void printParameter( Parameter p )
	{
		System.out.format( fmt, "Parameter class", p.getType() );
		System.out.format( fmt, "Parameter name", p.getName() );
		System.out.format( fmt, "Modifiers", p.getModifiers() );
		System.out.format( fmt, "Is implicit?", p.isImplicit() );
		System.out.format( fmt, "Is name present?", p.isNamePresent() );
		System.out.format( fmt, "Is synthetic?", p.isSynthetic() );
	}

	public static void main( String... args )
	{

		try
		{
			printClassConstructors( Class.forName( args[ 0 ] ) );
			printClassMethods( Class.forName( args[ 0 ] ) );
		}
		catch ( ClassNotFoundException x )
		{
			x.printStackTrace();
		}
	}

	<E extends RuntimeException> void genericThrow() throws E {}

}
