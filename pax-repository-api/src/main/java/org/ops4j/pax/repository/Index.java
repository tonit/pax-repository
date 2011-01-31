package org.ops4j.pax.repository;import org.ops4j.pax.repository.spi.Repository;/** * Intermidiate Layer between Resolver and Repository. * Expensive to create, access underlying repository. * Presumably requires some kind of local storage for caching. * * Will spawn indexed artifacts in a way that they can be queried efficiently using any valid variants of Q. * An Index where Q == T is called Template based Query Index. */public interface Index<Q, T>{    /**     * Map a query to concrete resource.     * A {@link Resolver} implementation should make sure that the query is sufficient.     * So it may add data like defaults or even block unsufficient queries.     *     * @param query what to find     *     * @return An artifact     *     * @throws RepositoryException in case of a problem.     */    Artifact get( Q query )        throws RepositoryException;    /**     * @param repository to be indexed     *     * @throws RepositoryException in case of a problem interacting with repository     */    void sync( Repository<T> repository )        throws RepositoryException;}