package org.ops4j.pax.repository.migrator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.ops4j.io.FileUtils;
import org.ops4j.io.StreamUtils;

/**
 *
 */
public class Run
{

    public final static Logger LOG = LoggerFactory.getLogger( Run.class );

    private static final String SVN_LOCATION = "http://scm.ops4j.org/repos/ops4j/projects/pax/runner-repository";

    private static final String SCM = "/usr/bin/svn";

    // iterate over folder, pick any xml, check valid content, copy to dest.
    static public void main( String[] args )

    {
        File source = new File( "/Users/tonit/rr" );
        File target = new File( "pax-repository-content/src/main/resources/gen/" );

        if( args.length > 1 )
        {
            source = new File( args[ 1 ] );
        }
        if( args.length > 2 )
        {
            target = new File( args[ 2 ] );
        }

        try
        {
            FileUtils.delete( source );
            FileUtils.delete( target );

            Run run = new Run();
            run.checkout( SVN_LOCATION, source );
            run.migrate( source, target );
        } catch( MigratorException e )
        {
            LOG.error( "Problem!", e );
        }
    }

    private void checkout( String svnLocation, File source )
        throws MigratorException
    {
        try
        {
            source.mkdirs();
            String command = SCM + " co " + svnLocation + " " + source.getAbsolutePath();
            int res = runCommand( command );
            if( res != 0 )
            {
                throw new MigratorException( "svn command failed." );
            }
            LOG.info( "Checkout successful." );

        } catch( IOException e )
        {
            throw new MigratorException( "Problem executing svn checkout command.", e );
        } catch( InterruptedException e )
        {
            throw new MigratorException( "SVN Checkout aborted", e );
        }
    }

    private int runCommand( String command )
        throws IOException, InterruptedException
    {
        LOG.info( "Exec command: " + command );

        Process exec = Runtime.getRuntime().exec( command );
        BufferedReader input =
            new BufferedReader
                ( new InputStreamReader( exec.getInputStream() ) );
        String line = null;
        while( ( line = input.readLine() ) != null )
        {
            LOG.info( line );
        }
        input.close();
        return exec.waitFor();
    }

    private void migrate( File source, File target )
        throws MigratorException
    {
        LOG.info( "Source: " + source );
        LOG.info( "Target: " + target );

        if( !source.exists() )
        {
            throw new MigratorException( "Source " + source + " does not exist." );
        }
        Set<File> migrationSet = find( source, ".*\\.composite" );

        target.mkdirs();

        {
            for( File f : migrationSet )
            {
                copy( f, target );
            }
        }
        LOG.info( "Done. Profiles copied: " + migrationSet.size() );

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
            LOG.debug( "+ " + f.getAbsolutePath() + " --> " + outName.getAbsolutePath() );
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
