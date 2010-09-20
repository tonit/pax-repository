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

import java.io.InputStream;
import org.junit.Test;
import org.ops4j.pax.repository.ArtifactIdentifier;
import org.ops4j.pax.repository.InputStreamSource;
import org.ops4j.pax.repository.QueryVisitor;
import org.ops4j.pax.repository.Repository;
import org.ops4j.pax.repository.RepositoryException;

import static org.mockito.Mockito.*;

/**
 *
 */
public class ZipRepositorySessionTest
{

    private static final String TEST_JAR = "/sample.jar";

    @Test
    public void reUse()
        throws RepositoryException
    {

        Repository repository = new ZipRepository(
            source( getClass().getResourceAsStream( TEST_JAR ) ),
            new ClassifierRegexFilter( "composite" )
        );

        QueryVisitor visit = mock( QueryVisitor.class );

        repository.index( visit );

        // all contents .composite items of file TEST_JAR from src/test/resources
        verify( visit, times( 5 ) ).touch( any( ArtifactIdentifier.class ) );
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
