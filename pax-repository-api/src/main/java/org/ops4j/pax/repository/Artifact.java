package org.ops4j.pax.repository;

/**
 *
 */
public interface Artifact extends ArtifactIdentifier
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
