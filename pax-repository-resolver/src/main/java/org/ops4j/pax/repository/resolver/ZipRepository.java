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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import org.ops4j.pax.repository.Artifact;
import org.ops4j.pax.repository.ArtifactFilter;
import org.ops4j.pax.repository.ArtifactIdentifier;
import org.ops4j.pax.repository.InputStreamSource;
import org.ops4j.pax.repository.QueryVisitor;
import org.ops4j.pax.repository.Repository;
import org.ops4j.pax.repository.RepositoryException;
import org.ops4j.store.Store;
import org.ops4j.store.StoreFactory;

/**
 * Repository implementation that reads from zip and stores in underlying flat-file cache.
 */
public class ZipRepository implements Repository
{

    private final Map<ArtifactIdentifier, Artifact> m_map = new HashMap<ArtifactIdentifier, Artifact>();
    private final Store<InputStream> m_store;

    public ZipRepository( final InputStreamSource input, ArtifactFilter filter )
        throws RepositoryException
    {
        PathToIdentifierParser parser = new PathToIdentifierParser();
        try
        {
            m_store = StoreFactory.anonymousStore();

            ZipInputStream inp = new ZipInputStream( input.get() );
            ZipEntry entry;
            long idx = 0;
            while( ( entry = inp.getNextEntry() ) != null )
            {
                ArtifactIdentifier id = parser.parse( entry.getName() );
                if( filter.allow( id ) )
                {
                    m_map.put( id, new CachedArtifact( m_store, inp ) );
                }
            }
        } catch( IOException e )
        {
            throw new RepositoryException( "Problem opening Repository from Archive.", e );
        }
    }

    public void index( QueryVisitor visit )
        throws RepositoryException
    {
        for( ArtifactIdentifier key : m_map.keySet() )
        {
            visit.touch( key );
        }
    }

    public Artifact retrieve( final ArtifactIdentifier id )
        throws RepositoryException
    {
        return m_map.get( id );
    }


}
