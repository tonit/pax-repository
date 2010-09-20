package org.ops4j.pax.repository.migrator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import org.ops4j.io.StreamUtils;

/**
 *
 */
public class Run
{

    // iterate over folder, pick any xml, check valid content, copy to dest.
    static public void main( String[] args )

    {
        //

        File source = new File( "/Users/tonit/rr" );
        File target = new File( "pax-repository-content/src/resources/java/gen" );

        try
        {
            new Run().migrate( source, target );
        } catch( MigratorException e )
        {
            e.printStackTrace();
        }
    }

    private void migrate( File source, File target )
        throws MigratorException
    {
        Set<File> migrationSet = find( source, ".*\\.composite" );
        target.mkdirs();
        {
            for( File f : migrationSet )
            {
                copy( f, target );
            }
        }

    }

    private void copy( File f, File target )
        throws MigratorException
    {
        try
        {
            File outName = createName( f.getName(), target );
            FileInputStream fis = new FileInputStream( f );
            FileOutputStream fout = new FileOutputStream( outName );
            StreamUtils.copyStream( fis, fout, true );
            System.out.println( "+ " + f.getAbsolutePath() + " --> " + outName.getAbsolutePath() );
        } catch( IOException e )
        {
            throw new MigratorException( "Problem Copying profile " + f.getAbsolutePath(), e );

        }
    }

    private File createName( String name, File target )
        throws MigratorException
    {
        File f = new File( target, name );
        if( f.exists() )
        {
            throw new MigratorException( "profile already exists in target: " + name );
        }
        else
        {
            return f;
        }
    }

    private Set<File> find( File source, String s )
    {
        Set<File> collected = new HashSet<File>();
        find( collected, source, s );
        return collected;
    }

    private void find( Set<File> collected, File source, String s )
    {
        for( File f : source.listFiles() )
        {
            if( f.isDirectory() )
            {
                find( collected, f, s );
            }
            if( f.getName().matches( s ) )
            {
                collected.add( f );
            }
        }
    }
}
