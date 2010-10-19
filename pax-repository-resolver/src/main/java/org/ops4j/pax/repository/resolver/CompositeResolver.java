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

import org.ops4j.pax.repository.Artifact;
import org.ops4j.pax.repository.ArtifactIdentifier;
import org.ops4j.pax.repository.RepositoryException;
import org.ops4j.pax.repository.RepositoryResolver;

/**
 * Simple composite of resolvers.
 * Does record resolved artifacts (so second lookup is faster). It is assumed that users of this class implements the "caching".
 */
public class CompositeResolver implements RepositoryResolver
{

    private RepositoryResolver[] m_resolvers;

    public CompositeResolver( RepositoryResolver... resolvers )
    {
        m_resolvers = resolvers;
    }

    public Artifact find( ArtifactIdentifier identifier )
        throws RepositoryException
    {
        for( RepositoryResolver resolver : m_resolvers )
        {
            Artifact artifact = resolver.find( identifier );
            if( artifact != null )
            {
                return artifact;
            }
        }
        return null;
    }
}
