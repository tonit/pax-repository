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
package org.ops4j.pax.repository.base;

import java.util.ArrayList;
import java.util.List;
import org.ops4j.pax.repository.ArtifactQuery;
import org.ops4j.pax.repository.RepositoryException;
import org.ops4j.pax.repository.RepositoryResolver;
import org.ops4j.pax.repository.SearchResult;

/**
 * Simple composite of resolvers.
 */
public class CompositeResolver implements RepositoryResolver
{

    private RepositoryResolver[] m_resolvers;

    public CompositeResolver( RepositoryResolver... resolvers )
    {
        m_resolvers = resolvers;
    }

    public SearchResult find( ArtifactQuery query )
        throws RepositoryException
    {
        List<SearchResult> sr = new ArrayList<SearchResult>();
        for( RepositoryResolver resolver : m_resolvers )
        {
            SearchResult artifact = resolver.find( query );
            if( artifact != null )
            {
                sr.add( artifact );
            }
        }
        return new CompositeSearchResult( sr );
    }
}
