package org.ops4j.pax.repository.spi;

/**
 *
 */
public interface EntryParser<T>
{

    T parse( ArtifactEntry entry );
}
