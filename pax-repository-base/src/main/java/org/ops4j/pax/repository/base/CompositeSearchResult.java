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
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import org.ops4j.pax.repository.Artifact;
import org.ops4j.pax.repository.SearchResult;

/**
 *
 */
public class CompositeSearchResult implements SearchResult
{

    private List<Artifact> m_results;

    public CompositeSearchResult( Iterable<SearchResult> sr )
    {
        m_results = new ArrayList<Artifact>();
        for( SearchResult r : sr )
        {
            for( Artifact a : r )
            {
                m_results.add( a );
            }
        }
    }

    public CompositeSearchResult( SearchResult... results )
    {
        this( Arrays.asList( results ) );
    }

    public Iterator<Artifact> iterator()
    {
        return m_results.iterator();
    }

}
