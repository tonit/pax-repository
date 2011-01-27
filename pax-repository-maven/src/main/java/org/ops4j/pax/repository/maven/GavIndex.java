package org.ops4j.pax.repository.maven;import java.util.HashMap;import java.util.Map;import org.ops4j.pax.repository.Artifact;import org.ops4j.pax.repository.Index;import org.ops4j.pax.repository.RepositoryException;import org.ops4j.pax.repository.spi.IndexVisitor;import org.ops4j.pax.repository.spi.Repository;/** * This index maps GAV artifacts, which are both, query and indexing notation (see class declaration). * * Actually it allows to query using incomplete GAV in get(GAV). * This is known elsewhere as "Query by template". * * G:A:V:C */public class GavIndex implements Index<GAV, GAV>{    final private Map<GAV, GAV> m_full = new HashMap<GAV, GAV>();    private Repository<GAV> m_repository;    public GavIndex()    {    }    synchronized public void sync( Repository<GAV> repository )        throws RepositoryException    {        m_repository = repository;        repository.index( new IndexVisitor<GAV>()        {            public void touch( GAV key )            {                m_full.put( key, key );                // store version less copy in index                GAV altKey = new DefaultGAV( key.getGroupId(), key.getArtifactId(), "", key.getClassifier() );                // we need to find the alternative highkey connection:                GAV stored = m_full.get( altKey );                if( stored == null || versionHigher( key.getVersion(), stored.getVersion() ) )                {                    // first hit, its also the alternative key!                    m_full.put( altKey, key );                }            }        }        );    }    // TODO do proper maven gav version compare here.    private boolean versionHigher( String left, String right )    {        int v1 = Integer.parseInt( left );        int v2 = Integer.parseInt( right );        return v1 > v2;    }    private boolean isHighKey( GAV key )    {        GAV highkey = m_full.get( key );        return false;    }    synchronized public Artifact get( GAV query )        throws RepositoryException    {        if( m_repository == null )        {            throw new IllegalStateException( "Index was never synced with a repository." );        }        if( m_full.containsKey( query ) )        {            return m_repository.retrieve( m_full.get( query ) );        }        return null;    }}