package org.ops4j.pax.repository;

/**
 * 
 */
public interface IndexVisitor<T>
{

    void touch( T key );
}
