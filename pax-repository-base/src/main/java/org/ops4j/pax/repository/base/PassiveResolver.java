package org.ops4j.pax.repository.base;import javax.inject.Inject;import org.ops4j.pax.repository.Provider;import org.ops4j.pax.repository.Index;import org.ops4j.pax.repository.RepositoryException;import org.ops4j.pax.repository.Resolver;/** * Resolver implementation that just delegates queries to the index. */public class PassiveResolver<Q, T, R> implements Resolver<Q, R> {    private Index<Q, T, R> m_index;    @Inject    PassiveResolver( Index<Q, T, R> index )    {        m_index = index;    }    public Provider<R> find( Q query )        throws RepositoryException    {        return m_index.get( query );    }}