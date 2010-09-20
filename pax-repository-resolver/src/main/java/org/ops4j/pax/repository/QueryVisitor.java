package org.ops4j.pax.repository;

/**
 * 
 */
public interface QueryVisitor
{

    void touch( ArtifactIdentifier key );
}
