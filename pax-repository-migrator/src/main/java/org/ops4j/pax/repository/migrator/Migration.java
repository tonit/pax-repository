/*
 * Copyright (C) 2010 Okidokiteam
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.ops4j.pax.repository.migrator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
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
 * Migration Utility.
 * Can also be used for sanity check of old repository.
 */
public class Migration
{

    public final static Logger LOG = LoggerFactory.getLogger( Run.class );

    private File m_source;
    private File m_target;

    public Migration( File source, File target )
    {
        m_source = source;
        m_target = target;
    }

    public void checkout( String scmPrefix, String svnLocation )
        throws MigratorException
    {
        try
        {
            FileUtils.delete( m_source );

            m_source.mkdirs();
            String command = scmPrefix + " co " + svnLocation + " " + m_source.getAbsolutePath();
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

    public void migrate( boolean deleteOld )
        throws MigratorException
    {
        if( deleteOld )
        {
            FileUtils.delete( m_target );
        }

        LOG.info( "Source: " + m_source );
        LOG.info( "Target: " + m_target );

        if( !m_source.exists() )
        {
            throw new MigratorException( "Source " + m_source + " does not exist." );
        }
        Set<File> migrationSet = find( m_source, ".*\\.composite" );

        m_target.mkdirs();

        {
            for( File f : migrationSet )
            {
                copy( f, m_target );
            }
        }
        LOG.info( "Done. Profiles copied: " + migrationSet.size() );

    }

    private void copy( File f, File target )
        throws MigratorException
    {
        try
        {
            File outName = createName( f, target );
            FileInputStream fis = new FileInputStream( f );
            FileOutputStream fout = new FileOutputStream( outName );
            StreamUtils.copyStream( fis, fout, true );
            LOG.debug( "+ " + f.getAbsolutePath() + " --> " + outName.getAbsolutePath() );
        } catch( IOException e )
        {
            throw new MigratorException( "Problem Copying profile " + f.getAbsolutePath(), e );

        }
    }

    private File createName( File name, File target )
        throws MigratorException
    {
        String newName = cutName( name );
        File f = new File( target, newName );
        if( f.exists() )
        {
            throw new MigratorException( "profile already exists in target: " + name );
        }
        else
        {
            return f;
        }
    }

    private String cutName( File f )
        throws MigratorException
    {
        // cut from base:
        StringBuilder sb = new StringBuilder();;
        try
        {
            String base = m_source.getCanonicalPath();
            String extended = f.getCanonicalPath();

            String sub = extended.substring( base.length() );
            for( String part : sub.split( "/" ) )
            {
                if( !isNumerical( part ) && !part.isEmpty() )
                {
                    if( !part.endsWith( ".composite" ) )
                    {
                        sb.append( part );
                        sb.append( "." );
                    }
                    else
                    {
                        // tail
                        sb.append( part );
                    }
                }

            }

            System.out.println( sb.toString() );

        } catch( IOException e )
        {
            throw new MigratorException( "Problem generating flattened name", e );
        }
        return sb.toString();
    }

    private boolean isNumerical( String part )
    {
        try
        {
            Integer.parseInt( part.charAt( 0 ) + "" );
        } catch( Exception e )
        {
            return false;
        }
        return true;
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
