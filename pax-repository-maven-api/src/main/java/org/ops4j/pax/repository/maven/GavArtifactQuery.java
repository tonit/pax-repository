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
public interface GavArtifactQuery extends ArtifactQuery
{

    /**
     * May be null.
     *
     * @return groupId or null
     */
    String getGroupId();

    /**
     * May be null.
     *
     * @return artifactId or null
     */
    String getArtifactId();

    /**
     * May be null.
     *
     * @return version or null
     */
    String getVersion();

    /**
     * May be null.
     *
     * @return classifier or null
     */
    String getClassifier();

}
