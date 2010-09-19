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
package org.ops4j.pax.repository;

/**
 * Create a repository using a RepositoryWriter.
 */
public interface RepositoryWriter
{

    /**
     * Add an artifact to this repository.
     * Note that resources may not be written after method returns. Be sure to close the transaction using calling {@link #close()}
     *
     * @param id       identifier to be assiciated with artifact
     * @param artifact content to be associated with the id param
     *
     * @return this. Fluent API
     */
    RepositoryWriter addArtifact( ArtifactIdentifier id, Artifact artifact );

    /**
     * When done adding artifact, close this writer so resources can be persistened.
     */
    void close();
}
