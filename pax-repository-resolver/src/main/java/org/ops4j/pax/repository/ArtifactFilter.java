package org.ops4j.pax.repository;

/**
 *
 */
public interface ArtifactFilter
{

    boolean allow( ArtifactIdentifier identifier );
}
