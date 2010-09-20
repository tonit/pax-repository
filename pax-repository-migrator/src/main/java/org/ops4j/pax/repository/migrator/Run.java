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

    private static final String SVN_LOCATION = "http://scm.ops4j.org/repos/ops4j/projects/pax/runner-repository";
    private static final String SCM = "/usr/bin/svn";

    public final static Logger LOG = LoggerFactory.getLogger( Run.class );

    static public void main( String[] args )
        throws MigratorException
    {
        File source = new File( "target/checkout/" );
        File target = new File( "pax-repository-content/src/main/resources/gen/" );

        Migration run = new Migration( source, target );
        
       // run.checkout( SCM, SVN_LOCATION );
        run.migrate( true );
    }

}
