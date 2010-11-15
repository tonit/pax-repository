package org.ops4j.pax.repository.spi;

/**
 *
 */
public interface ArtifactFilter<T>
{

    boolean allow( T identifier );
}
