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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.ops4j.pax.repository.Artifact;
import org.ops4j.pax.repository.ArtifactIdentifier;
import org.ops4j.pax.repository.InputStreamSource;

/**
 * Local Artifact.
 */
public class LocalArtifact implements Artifact
{

    private final ArtifactIdentifier m_ident;
    private final File m_file;

    public LocalArtifact( ArtifactIdentifier ident, File file )
    {
        m_file = file;
        m_ident = ident;
    }

    public InputStreamSource getContent()
    {
        return new InputStreamSource()
        {

            public InputStream get()
                throws IOException
            {
                return new FileInputStream( m_file );
            }
        };
    }

    public String getGroupId()
    {
        return m_ident.getGroupId();
    }

    public String getArtifactId()
    {
        return m_ident.getArtifactId();
    }

    public String getVersion()
    {
        return m_ident.getVersion();
    }

    public String getClassifier()
    {
        return m_ident.getClassifier();
    }

    public String getName()
    {
        return m_ident.getName();
    }
}
