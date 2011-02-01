package org.ops4j.pax.repository;/** * Optional Service that privides some means of refreshing (or initially provisioning) an index with a repository. */public interface SyncService{    /**     * Invokation of the actual sync.     * Implementations may or may not allow calling this service actively.     * If not, this is some kind of background job being executed without you dealing with it.     *     * Implementations may also decide to make this function blocking or non-blocking.     *     * @throws RepositoryException In case of a serious problem.     */    public void sync()        throws RepositoryException;}