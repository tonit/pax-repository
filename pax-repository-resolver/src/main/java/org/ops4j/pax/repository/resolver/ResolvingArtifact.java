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
import org.ops4j.pax.repository.InputStreamSource;
import org.ops4j.pax.repository.RepositoryException;
import org.ops4j.pax.repository.RepositoryResolver;

/**
 * Handy Artifact implementation (api helper)
 */
public class ResolvingArtifact implements Artifact
{

    private Artifact m_artifact;

    public ResolvingArtifact( RepositoryResolver resolver, ArtifactIdentifier identifier )
        throws RepositoryException
    {
        m_artifact = resolver.find( identifier );
    }

    public InputStreamSource getContent()
    {
        return m_artifact.getContent();
    }

    public String getGroupId()
    {
        return m_artifact.getGroupId();
    }

    public String getArtifactId()
    {
        return m_artifact.getArtifactId();
    }

    public String getVersion()
    {
        return m_artifact.getVersion();
    }

    public String getClassifier()
    {
        return m_artifact.getClassifier();
    }

    public String getName()
    {
        return m_artifact.getName();
    }
}
