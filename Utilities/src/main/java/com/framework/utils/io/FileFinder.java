package com.framework.utils.io;

import com.google.common.collect.Sets;
import org.apache.commons.lang3.SystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Set;

import static java.nio.file.FileVisitResult.CONTINUE;

/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.utils.io
 *
 * Name   : FileFinder 
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-02-15 
 *
 * Time   : 21:34
 *
 */

public class FileFinder extends SimpleFileVisitor<Path>
{

	//region FileFinder - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( FileFinder.class );

	private final PathMatcher matcher;

	private int numMatches = 0;

	private Set<String> files = Sets.newConcurrentHashSet();

	//endregion


	//region FileFinder - Constructor Methods Section

	public FileFinder( String pattern )
	{
		matcher = FileSystems.getDefault().getPathMatcher( "glob:" + pattern );
	}

	public void find( Path file )
	{
		Path name = file.getFileName();
		if ( name != null && matcher.matches( name ) )
		{
			numMatches ++;
			files.add( file.toString() );
		}
	}

	@Override
	public FileVisitResult visitFile( final Path file, final BasicFileAttributes attrs ) throws IOException
	{
		find( file );
		return CONTINUE;
	}

	@Override
	public FileVisitResult preVisitDirectory( Path dir, BasicFileAttributes attrs )
	{
		find( dir );
		return CONTINUE;
	}

	public FileVisitResult visitFileFailed( Path file, IOException exc )
	{
		logger.error( exc.getMessage() );
		return CONTINUE;
	}

	public int getNumMatches()
	{
		return numMatches;
	}

	public Set<String> getFiles()
	{
		return files;
	}

	//endregion


	//region FileFinder - Inner Classes Implementation Section

	public static void main( String[] args )
	throws IOException
	{

		Path startingDir = Paths.get( SystemUtils.USER_DIR );

		FileFinder finder = new FileFinder( "compare.random.ships.xml" );
		Path path = Files.walkFileTree( startingDir, finder );
		Set<String> set = finder.getFiles();
		System.out.println( "FileFinder.main" );
	}

	//endregion

}
