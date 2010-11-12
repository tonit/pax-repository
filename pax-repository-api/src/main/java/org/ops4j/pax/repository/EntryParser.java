package org.ops4j.pax.repository;

/**
 *
 */
public interface EntryParser<T>
{

    T parse( ArtifactEntry entry );
}
