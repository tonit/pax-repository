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
import org.junit.Test;
import org.ops4j.base.io.InputStreamSource;
import org.ops4j.pax.repository.ArtifactEntry;
import org.ops4j.pax.repository.Repository;
import org.ops4j.pax.repository.RepositoryException;
import org.ops4j.pax.repository.RepositoryResolver;
import org.ops4j.pax.repository.SearchResult;
import org.ops4j.pax.repository.base.DefaultEntryParser;
import org.ops4j.store.StoreFactory;

import static junit.framework.Assert.*;
import static org.ops4j.pax.repository.resolver.RepositoryFactory.*;

/**
 *
 */
public class DefaultResolverTest
{

    private static final String TEST_JAR = "/sample.jar";

    @Test( expected = AssertionError.class )
    public void noRepo()
        throws RepositoryException, IOException
    {
        new DefaultResolver( null );
    }

    @Test
    public void defaultFind()
        throws RepositoryException, IOException
    {

        Repository<ArtifactEntry> repository = new ZipRepository<ArtifactEntry>(
            source( getClass().getResourceAsStream( TEST_JAR ) ),
            new ClassifierRegexFilter<ArtifactEntry>( "composite" ),
            StoreFactory.anonymousStore(),
            new DefaultEntryParser()
        );

        RepositoryResolver resolver = new DefaultResolver<ArtifactEntry>( repository );

        SearchResult res = resolver.find( createQuery( "org.ops4j.pax.runner.profiles.blueprint.blueprint:0:composite" ) );

        // nothing found.
        assertNotNull( res );
    }

    private InputStreamSource source( final InputStream resourceAsStream )
    {
        return new InputStreamSource()
        {

            public InputStream get()
            {
                return resourceAsStream;
            }
        };
    }
}
