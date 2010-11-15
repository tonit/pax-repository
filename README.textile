November 2010

h1. Pax Repository

This is the non-singleton cousin of Pax URL.
Instead of relying on global url handlers and strategies tied into the identifier (read: mvn:bla:bla:1.0 contains mvn where bla bla might come from somewhere else in different contexts of the same app).

In the end, this is
 * a superclass of Sonatype's Aether
 * Lets you write an implementation of a resolver for a custom scheme in < 5 minutes
 * chose from resolvers at runtime
 * develop subsystem agnostic - just like Pax URL but without the singelton flavour.
 * Read: Pax URL is for system integrators, Pax Repository for Developers
 * Pax URL may adopt Pax Repository underneath
 