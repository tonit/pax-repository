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

import java.util.Iterator;
import org.junit.Test;
import org.ops4j.pax.repository.Artifact;
import org.ops4j.pax.repository.ArtifactQuery;
import org.ops4j.pax.repository.Repository;
import org.ops4j.pax.repository.RepositoryException;
import org.ops4j.pax.repository.RepositoryResolver;
import org.ops4j.pax.repository.SearchResult;

import static org.mockito.Mockito.*;
import static org.ops4j.pax.repository.resolver.RepositoryFactory.*;

import static junit.framework.Assert.*;

/**
 *
 */
public class CreateAndQueryTest
{

    @Test
    public void defaultFind()
        throws RepositoryException
    {

        ArtifactQuery query = createQuery( "org.ops4j.pax.profile.log" );

        Repository repos = mock( Repository.class );
        RepositoryResolver resolver = new DefaultResolver( repos );

        SearchResult res = resolver.find( query );

        // nothing found.
        assertNull( res );
        Iterator<Artifact> iterator = res.iterator();
        assertFalse( iterator.hasNext() );
    }
}
