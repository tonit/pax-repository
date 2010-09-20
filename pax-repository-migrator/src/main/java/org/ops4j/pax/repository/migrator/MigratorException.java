package org.ops4j.pax.repository.migrator;

import java.io.IOException;

/**
 * Model Exception
 */
public class MigratorException extends Throwable
{

    public MigratorException( String s )
    {
        super( s );

    }

    public MigratorException( String s, Exception e )
    {
        super( s, e );
    }
}
