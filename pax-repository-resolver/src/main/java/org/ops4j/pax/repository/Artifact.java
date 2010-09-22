package org.ops4j.pax.repository;

/**
 *
 */
public interface Artifact
{

    /**
     * Raw data of this artifact as a delayed InputStream.
     *
     * @return access to the raw data.
     */
    InputStreamSource getContent();

}
