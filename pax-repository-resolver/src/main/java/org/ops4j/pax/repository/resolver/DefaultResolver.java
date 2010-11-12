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
import org.ops4j.pax.repository.ArtifactQuery;
import org.ops4j.pax.repository.IndexVisitor;
import org.ops4j.pax.repository.Repository;
import org.ops4j.pax.repository.RepositoryException;
import org.ops4j.pax.repository.RepositoryResolver;
import org.ops4j.pax.repository.SearchResult;

/**
 * This resolver requires a repository underneath that provides a full index that can be visited.
 * Should not be used for adhoc queries against foreign repositories.
 */
public class DefaultResolver<T> implements RepositoryResolver
{

    final private Repository m_repository;
    final private Map<String, String> m_highestVersion = new HashMap<String, String>();

    public DefaultResolver( Repository<T> repos )
        throws RepositoryException
    {
        assert repos != null : "Repository may never be null.";
        m_repository = repos;
        repos.index( new IndexVisitor<T>()
        {

            public void touch( T key )
            {
                if( m_highestVersion.containsKey( key.getQueryString() ) )
                {
                    String version = key.getVersion();
                    String versionOld = m_highestVersion.get( key.getQueryString() );
                    if( version.compareTo( versionOld ) > 0 )
                    {
                        m_highestVersion.put( key.getQueryString(), key.getVersion() );

                    }
                }
                else
                {
                    m_highestVersion.put( key.getQueryString(), key.getVersion() );
                }
            }
        }
        );

    }

    /**
     * {@inheritDoc}
     */
    public SearchResult find( ArtifactQuery query )
        throws RepositoryException
    {
        // must support graceful reduction of possibly incomplete identifier scheme

        // direct access:
        Artifact artifact = m_repository.retrieve( query );

        if( artifact == null )
        {
            // ask highest version index:
            String version = m_highestVersion.get( query.getQueryString() );
            if( version != null )
            {
                return m_repository.retrieve( identifier( query.getArtifactId(), version, query.getClassifier() ) );
            }

        }
        return artifact;
    }
}
