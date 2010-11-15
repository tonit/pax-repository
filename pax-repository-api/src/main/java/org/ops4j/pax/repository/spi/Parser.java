package org.ops4j.pax.repository.spi;

/**
 * Parser for specialising a type to a more concrete sub-type
 */
public interface Parser<S, T extends S>
{

    T parse( S source );
}
