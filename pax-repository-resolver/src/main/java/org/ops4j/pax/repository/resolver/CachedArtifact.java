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

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipInputStream;
import org.ops4j.pax.repository.Artifact;
import org.ops4j.pax.repository.InputStreamSource;
import org.ops4j.store.Handle;
import org.ops4j.store.Store;

/**
 * Convinience, OPS4J-Base-Store based Artifact implementation.
 */
public class CachedArtifact implements Artifact
{

    private Store<InputStream> m_store;
    private Handle m_handle;

    public CachedArtifact( Store<InputStream> store, InputStream inp )
        throws IOException
    {
        m_store = store;
        m_handle = store.store( inp );

    }

    public InputStreamSource getContent()
    {
        return new InputStreamSource()
        {

            public InputStream get()
                throws IOException
            {
                return m_store.load( m_handle );
            }
        };
    }
}
