/* * Copyright (C) 2010 Toni Menzel * * Licensed under the Apache License, Version 2.0 (the "License"); * you may not use this file except in compliance with the License. * You may obtain a copy of the License at * *      http://www.apache.org/licenses/LICENSE-2.0 * * Unless required by applicable law or agreed to in writing, software * distributed under the License is distributed on an "AS IS" BASIS, * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. * See the License for the specific language governing permissions and * limitations under the License. */package org.ops4j.pax.repository;/** * Access to an index. * Instances whould be lightweigt leveraging {@link Index} instance(s). */public interface Resolver<Q>{    /**     * Map an query to concrete resource.     *     * @param query what to find     *     * @return An artifact     *     * @throws RepositoryException in case of a problem.     */    Artifact find( Q query )        throws RepositoryException;}