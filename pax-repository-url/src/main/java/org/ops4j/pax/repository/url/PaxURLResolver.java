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
package org.ops4j.pax.repository.url;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import org.ops4j.base.io.InputStreamSource;
import org.ops4j.pax.repository.Artifact;
import org.ops4j.pax.repository.ArtifactQuery;
import org.ops4j.pax.repository.Parser;
import org.ops4j.pax.repository.RepositoryException;
import org.ops4j.pax.repository.Resolver;

/**
 * Simple Pax Repository to url mapping
 */
public class PaxURLResolver implements Resolver
{

    private String m_protocol;
    private Parser<String, String> m_parser;

    public PaxURLResolver( String protocol, Parser<String, String> parser )
    {
        m_protocol = protocol;
        m_parser = parser;
    }

    public Artifact find( final ArtifactQuery query )
        throws RepositoryException
    {
        return new Artifact()
        {

            public InputStreamSource getContent()
                throws RepositoryException
            {
                return new InputStreamSource()
                {

                    public InputStream get()
                        throws IOException
                    {
                        String parsed = m_parser.parse( query.getQueryString() );
                        return new URL( m_protocol + parsed ).openStream();
                    }
                };
            }
        };
    }
}
