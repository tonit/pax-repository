package org.ops4j.pax.repository.tarball;import org.ops4j.pax.repository.Parser;/** * Do nothing parser. Just returns the imput instance. */public class EmptyParser<T> implements Parser<T, T>{    public T parse( T source )    {        return source;    }}