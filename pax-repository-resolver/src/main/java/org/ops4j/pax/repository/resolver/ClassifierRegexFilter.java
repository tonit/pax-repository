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

import org.ops4j.pax.repository.ArtifactFilter;
import org.ops4j.pax.repository.ArtifactIdentifier;

/**
 * Filter for regex on artifactId only.
 */
public class ClassifierRegexFilter implements ArtifactFilter
{

    private String m_regex;

    public ClassifierRegexFilter( String regex )
    {
        m_regex = regex;
    }

    public boolean allow( ArtifactIdentifier identifier )
    {
        return identifier.getClassifier().matches( m_regex );
    }
}
