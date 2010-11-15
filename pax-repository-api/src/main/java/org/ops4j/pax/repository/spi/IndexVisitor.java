package org.ops4j.pax.repository.spi;

/**
 * 
 */
public interface IndexVisitor<T>
{

    void touch( T key );
}
