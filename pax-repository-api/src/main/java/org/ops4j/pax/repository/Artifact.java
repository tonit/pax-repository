package org.ops4j.pax.repository;

import org.ops4j.base.io.InputStreamSource;

/**
 * A resolved artifact. You can get the resource
 */
public interface Artifact
{

    /**
     * Raw data of this artifact as a delayed InputStream.
     *
     * @return access to the raw data.
     *
     * @throws RepositoryException problems ?
     */
    InputStreamSource getContent()
        throws RepositoryException;

}
