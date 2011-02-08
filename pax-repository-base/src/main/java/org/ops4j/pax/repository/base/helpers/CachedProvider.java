package org.ops4j.pax.repository.base.helpers;import java.io.IOException;import org.ops4j.pax.repository.Provider;import org.ops4j.store.Handle;import org.ops4j.store.Store;/** * Cache. Just makes sense if R is a resource that must be flushed eagerly but is needed (being processed) locally at a later point in time. */public class CachedProvider<R> implements Provider<R> {    private Store<R> m_store;    private Handle m_handle;    public CachedProvider( Store<R> store, R inp )        throws IOException    {        m_store = store;        m_handle = m_store.store( inp );    }     public CachedProvider( Store<R> store, Handle handle )        throws IOException    {        m_store = store;        m_handle = handle;    }    public R get()    {        try {            // usually nothing breaks here.            return m_store.load( m_handle );        } catch( IOException e ) {            throw new RuntimeException( e );        }    }}