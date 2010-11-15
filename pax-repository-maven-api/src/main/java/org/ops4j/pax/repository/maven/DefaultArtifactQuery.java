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
package org.ops4j.pax.repository.maven;

import org.ops4j.pax.repository.ArtifactQuery;

/**
 *
 */
public class DefaultArtifactQuery implements GavArtifactQuery
{

    final private String m_artifact;
    final private String m_group;

    final private String m_version;
    final private String m_classifier;

    private static final String DEFAULT_VERSION = "0";
    private static final String DEFAULT_CLASSIFIER = "";

    public DefaultArtifactQuery( String group, String artifact, String version, String classifier )
    {
        m_artifact = artifact;
        m_group = group;
        m_version = version;

        if( classifier == null )
        {
            m_classifier = "jar";
        }
        else
        {
            m_classifier = classifier;

        }
    }

    public DefaultArtifactQuery( String name, String version, String classifier )
    {
        this( "", name, version, classifier );
    }

    public DefaultArtifactQuery( String id )
    {
        this( id, DEFAULT_VERSION, DEFAULT_CLASSIFIER );
    }

    public String getGroupId()
    {
        return m_group;
    }

    public String getArtifactId()
    {
        return m_artifact;
    }

    @Override
    public int hashCode()
    {
        return ( m_artifact + m_version + m_classifier ).hashCode();
    }

    @Override
    public boolean equals( Object obj )
    {
        if( obj instanceof ArtifactQuery )
        {
            ArtifactQuery comp = (ArtifactQuery) obj;
            return ( getQueryString() ).equals( ( comp.getQueryString() ) );
        }
        else
        {
            return false; // its not of my type
        }
    }

    public String getVersion()
    {
        return m_version;
    }

    public String getClassifier()
    {
        return m_classifier;
    }

    public String getQueryString()
    {
        return m_group + ":" + m_artifact + ":" + m_classifier;
    }

    @Override
    public String toString()
    {
        return "[ ArtifactQuery group=" + m_group + " artifact=" + m_artifact + " version=" + m_version + " classifier=" + m_classifier + " ]";
    }
}
