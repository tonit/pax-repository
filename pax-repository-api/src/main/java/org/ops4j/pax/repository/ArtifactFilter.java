package org.ops4j.pax.repository;

/**
 *
 */
public interface ArtifactFilter<T>
{

    boolean allow( T identifier );
}
