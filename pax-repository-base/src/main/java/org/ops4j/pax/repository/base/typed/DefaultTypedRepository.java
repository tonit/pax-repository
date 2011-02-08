/* * Copyright (C) 2011 Toni Menzel * * Licensed under the Apache License, Version 2.0 (the "License"); * you may not use this file except in compliance with the License. * You may obtain a copy of the License at * *      http://www.apache.org/licenses/LICENSE-2.0 * * Unless required by applicable law or agreed to in writing, software * distributed under the License is distributed on an "AS IS" BASIS, * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. * See the License for the specific language governing permissions and * limitations under the License. */package org.ops4j.pax.repository.base.typed;import java.util.concurrent.ConcurrentHashMap;import java.util.concurrent.ConcurrentMap;import org.ops4j.pax.repository.Provider;import org.ops4j.pax.repository.RepositoryException;import org.ops4j.pax.repository.typed.TypedReference;import org.ops4j.pax.repository.typed.TypedRepository;/** * Default implementation. Should be threadsafe. Mantains all key in memory. * Lazily fetches the actual value for a key (in Provider.get). This gives plenty of time for the system to provide an actual value. * * This implementation also allows for changing values if setOnceOnly=false. */public class DefaultTypedRepository<V> implements TypedRepository<V> {    static final boolean DEFAULT_ALLOW_SET_ONCE_ONLY = false;    final private ConcurrentMap<TypedReference, V> m_map = new ConcurrentHashMap<TypedReference, V>();    final private boolean m_once;    public DefaultTypedRepository()    {        m_once = DEFAULT_ALLOW_SET_ONCE_ONLY;    }    public DefaultTypedRepository( boolean once )    {        m_once = once;    }    public TypedReference set( TypedReference key, V value )        throws RepositoryException    {        if( m_once && m_map.containsKey( key ) ) {            throw new RepositoryException( "Trying to set " + key + " again not allowed. Key is already set to " + m_map.get( key ) );        }        m_map.put( key, value );        return key;    }    /**     * Value is loaded lazily! (upon provider.get() which is the latest possible time.     *     * @param key     *     * @throws IllegalArgumentException when key=null     */    public Provider<V> get( final TypedReference key )        throws RepositoryException    {        if( key == null ) { throw new IllegalArgumentException( "null-key as TypedReference is not allowed, boy." ); }        return new Provider<V>() {            public V get()                throws RepositoryException            {                final V value = m_map.get( key );                if( value == null ) {                    throw new RepositoryException( "No value for typed reference: " + key );                }                return value;            }        };    }}