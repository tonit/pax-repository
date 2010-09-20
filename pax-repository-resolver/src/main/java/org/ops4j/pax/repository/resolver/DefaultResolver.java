/*
 * Copyright (C) 2010 Toni Menzel
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

import java.util.HashMap;
import java.util.Map;
import org.ops4j.pax.repository.Artifact;
import org.ops4j.pax.repository.ArtifactIdentifier;
import org.ops4j.pax.repository.QueryVisitor;
import org.ops4j.pax.repository.Repository;
import org.ops4j.pax.repository.RepositoryException;
import org.ops4j.pax.repository.RepositoryResolver;

import static org.ops4j.pax.repository.resolver.RepositoryFactory.*;

/**
 *
 */
public class DefaultResolver implements RepositoryResolver
{

    final private Repository m_repository;
    final private Map<String, String> m_highestVersion = new HashMap<String, String>();

    public DefaultResolver( Repository repos )
        throws RepositoryException
    {
        m_repository = repos;
        repos.index( new QueryVisitor()
        {

            public void touch( ArtifactIdentifier key )
            {
                if( m_highestVersion.containsKey( key.getName() ) )
                {
                    String version = key.getVersion();
                    String versionOld = m_highestVersion.get( key.getName() );
                    if( version.compareTo( versionOld ) > 0 )
                    {
                        m_highestVersion.put( key.getName(), key.getVersion() );

                    }
                }
                else
                {
                    m_highestVersion.put( key.getName(), key.getVersion() );
                }
            }
        }
        );

    }

    public Artifact find( ArtifactIdentifier ident )
        throws RepositoryException
    {
        // must support graceful reduction of possibly incomplete identifier scheme

        // direct access:
        Artifact artifact = m_repository.retrieve( ident );

        if( artifact == null )
        {
            // ask highest version index:
            String version = m_highestVersion.get( ident.getName() );
            if( version != null )
            {
                return m_repository.retrieve( identifier( ident.getArtifactId(), version, ident.getClassifier() ) );
            }

        }
        return artifact;
    }
}
