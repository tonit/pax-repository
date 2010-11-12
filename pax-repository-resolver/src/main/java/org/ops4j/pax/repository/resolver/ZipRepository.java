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

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import org.ops4j.base.io.InputStreamSource;
import org.ops4j.pax.repository.Artifact;
import org.ops4j.pax.repository.ArtifactFilter;
import org.ops4j.pax.repository.EntryParser;
import org.ops4j.pax.repository.IndexVisitor;
import org.ops4j.pax.repository.Repository;
import org.ops4j.pax.repository.RepositoryException;
import org.ops4j.pax.repository.base.CachedArtifact;
import org.ops4j.store.Store;

import static org.ops4j.pax.repository.resolver.RepositoryFactory.*;

/**
 * Repository implementation that reads from zip and stores in underlying flat-file cache.
 */
public class ZipRepository<T> implements Repository<T>
{

    private final Map<T, Artifact> m_map = new HashMap<T, Artifact>();

    public ZipRepository( final InputStreamSource input, ArtifactFilter<T> filter, Store<InputStream> store, EntryParser<T> parser )
        throws RepositoryException
    {
        try
        {
            ZipInputStream inp = new ZipInputStream( input.get() );
            ZipEntry zipEntry;
            while( ( zipEntry = inp.getNextEntry() ) != null )
            {
                // We have a string.
                T id = parser.parse( entry( zipEntry.getName() ) );
                if( filter.allow( id ) )
                {
                    m_map.put( id, new CachedArtifact( store, inp ) );
                }
            }
        } catch( IOException e )
        {
            throw new RepositoryException( "Problem opening Repository from Archive.", e );
        }
    }

    public void index( IndexVisitor<T> visit )
        throws RepositoryException
    {
        for( T key : m_map.keySet() )
        {
            visit.touch( key );
        }
    }

    public Artifact retrieve( final T id )
        throws RepositoryException
    {
        return m_map.get( id );
    }


}
