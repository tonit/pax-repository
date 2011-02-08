/* * Copyright (C) 2011 Toni Menzel * * Licensed under the Apache License, Version 2.0 (the "License"); * you may not use this file except in compliance with the License. * You may obtain a copy of the License at * *      http://www.apache.org/licenses/LICENSE-2.0 * * Unless required by applicable law or agreed to in writing, software * distributed under the License is distributed on an "AS IS" BASIS, * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. * See the License for the specific language governing permissions and * limitations under the License. */package org.ops4j.pax.repository.base.typed;import java.util.Collection;import java.util.HashMap;import java.util.Map;import org.ops4j.pax.repository.Provider;import org.ops4j.pax.repository.RepositoryException;import org.ops4j.pax.repository.Resolver;import org.ops4j.pax.repository.typed.TypedReference;import org.ops4j.pax.repository.typed.TypedRepository;/** * Make an existing {@link org.ops4j.pax.repository.Resolver} a typed one. Using {@link TypeMapping}. * * This implementation is lazy, so it will execute the query on resolver upon {@link #get(org.ops4j.pax.repository.typed.TypedReference)}. */public class TypedWrapperRepository<Q, V> implements TypedRepository<V> {    private Map<TypedReference, Q> m_map;    private Resolver<Q, V> m_resolver;    public TypedWrapperRepository( Resolver<Q, V> resolver,                                   Collection<TypeMapping<Q>> mappings )        throws RepositoryException    {        m_resolver = resolver;        m_map = new HashMap<TypedReference, Q>();        for( TypeMapping<Q> m : mappings ) {            m_map.put( m.typed(), m.untyped() );        }    }    /**     * Does not do anything.     *     * @param key   to set     * @param value to set     */    public TypedReference set( TypedReference key, V value )        throws RepositoryException    {        return key;    }    /**     * @param key to load from underlying repo     *     * @return value from underlying repo     */    public Provider<V> get( TypedReference key )        throws RepositoryException    {        Q query = m_map.get( key );        if( query == null ) {            throw new RepositoryException( "Type " + key + " has not been mapped initially. This may be a setup problem when configuring " + TypedWrapperRepository.class.getName() );        }        Provider<V> result = m_resolver.find( m_map.get( key ) );        if( result == null ) {            throw new RepositoryException( "Type " + key + " has been mapped but no value has been found in underlying Resolver " + m_resolver );        }        return result;    }}