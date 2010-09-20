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
package org.ops4j.pax.repository.resolver;

import java.util.regex.Pattern;
import org.ops4j.pax.repository.ArtifactIdentifier;

import static org.ops4j.pax.repository.resolver.RepositoryFactory.*;

/**
 *
 */
public class PathToIdentifierParser
{

    public ArtifactIdentifier parse( String path )
    {
        String paths[] = path.split( "/" );

        String tail = paths[ paths.length - 1 ];

        return parseStripped( tail );


    }

    private ArtifactIdentifier parseStripped( String tail )
    {
        String name = "";
        String version = "0";
        String suffix = "";

        // expexted:
        // bla-bla-0.1.0-alpha1.compsite --> id=bla-bla version=0.1.0-alpha1

        StringBuilder sbName = new StringBuilder();
        StringBuilder sbVersion = new StringBuilder();

        StringBuilder sbActive = sbName;

        for( String dashed : tail.split( "-" ) )
        {
            if( numerical( dashed ) )
            {
                sbActive = sbVersion;
            }

            if( dashed != null )
            {
                if( sbActive.length() == 0 )
                {
                    sbActive.append( dashed );
                }
                else
                {
                    sbActive.append( "-" );
                    sbActive.append( dashed );
                }
            }

        }

        name = sbName.toString();
        version = sbVersion.toString();

        // Suffix is flying around in one of the parts (we know). Kick it out.
        if( version.isEmpty() )
        {
            version = "0";
            String[] parts = eraseLastPart( name );
            name = parts[ 0 ];
            suffix = parts[ 1 ];

        }
        else
        {
            // rip suffix from version in that case:
            String[] parts = eraseLastPart( version );
            version = parts[ 0 ];

            suffix = parts[ 1 ];

        }

        // select default:

        return identifier( name, version, suffix );

    }

    private String[] eraseLastPart( String s )
    {
        int idx = s.lastIndexOf( "." );
        if( idx < 0 )
        {
            return new String[]{ s, "" };
        }
        else
        {
            return new String[]{ s.substring( 0, idx ), s.substring( idx + 1 ) };
        }
    }

    private boolean numerical( String part )
    {
        if( part != null && !part.isEmpty() )
        {
            String s = part.charAt( 0 ) + "";
            return Pattern.matches( "^\\d*$", s );
        }
        else
        {
            return false;
        }

    }
}
