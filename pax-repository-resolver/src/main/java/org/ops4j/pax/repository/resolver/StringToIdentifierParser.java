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

import org.ops4j.pax.repository.ArtifactIdentifier;

import static org.ops4j.pax.repository.resolver.RepositoryFactory.*;

/**
 * Parses a string of format: GROUP:ARTIFACT:VERSION[:classifier] to an identifier.
 */
public class StringToIdentifierParser
{

    public ArtifactIdentifier parse( String path )
    {

        String paths[] = path.split( ":" );
        if( path.length() > 2 )
        {
            String groupId = paths[ 0 ];
            String artifactId = paths[ 1 ];
            String versionId = paths[ 2 ];
            String classifier = null;

            if( paths.length > 3 )
            {
                classifier = paths[ 3 ];
            }

            return identifier( groupId, artifactId, versionId, classifier );
        }
        else
        {
            throw new IllegalArgumentException( "Cannot parse \"" + path + "\" as <GROUP>:<ARTIFACT>:<VERSION>[:CLASSIFIER]." );
        }


    }
}
