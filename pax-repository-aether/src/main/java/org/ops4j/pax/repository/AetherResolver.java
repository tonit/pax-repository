/*
 * Copyright (C) 2010 Okidokiteam
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.ops4j.pax.repository;

import java.io.File;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.maven.repository.internal.DefaultServiceLocator;
import org.apache.maven.repository.internal.MavenRepositorySystemSession;
import org.sonatype.aether.RepositorySystem;
import org.sonatype.aether.RepositorySystemSession;
import org.sonatype.aether.artifact.Artifact;
import org.sonatype.aether.collection.CollectRequest;
import org.sonatype.aether.collection.DependencyCollectionException;
import org.sonatype.aether.connector.wagon.WagonProvider;
import org.sonatype.aether.connector.wagon.WagonRepositoryConnectorFactory;
import org.sonatype.aether.graph.Dependency;
import org.sonatype.aether.graph.DependencyNode;
import org.sonatype.aether.repository.LocalRepository;
import org.sonatype.aether.repository.RemoteRepository;
import org.sonatype.aether.resolution.ArtifactResolutionException;
import org.sonatype.aether.spi.connector.RepositoryConnectorFactory;
import org.sonatype.aether.util.artifact.DefaultArtifact;
import org.ops4j.pax.repository.resolver.LocalArtifact;

/**
 *
 */
public class AetherResolver implements RepositoryResolver
{

    private static final Log LOG = LogFactory.getLog( AetherResolver.class );
  
    final private String m_localRepo;
    final private String[] m_repositories;
    final private RepositorySystem m_repoSystem;

    public org.ops4j.pax.repository.Artifact find( ArtifactIdentifier identifier )
        throws RepositoryException
    {
        return new LocalArtifact( identifier, resolve( identifier ) );
    }

    public AetherResolver( String local, String[] repos )
    {
        m_localRepo = local;
        m_repositories = repos;
        m_repoSystem = newRepositorySystem();
    }

    private File resolve( ArtifactIdentifier identifier )
        throws RepositoryException
    {
        try
        {
            RepositorySystemSession session = newSession( m_repoSystem );

            Dependency dependency = new Dependency( new DefaultArtifact( identifier.getGroupId(), identifier.getArtifactId(), identifier.getClassifier(), identifier.getVersion() ), "provided" );
            CollectRequest collectRequest = prepareResolveRequest( dependency );
            DependencyNode node = m_repoSystem.collectDependencies( session, collectRequest ).getRoot();

            Artifact artifact = m_repoSystem.resolveDependencies( session, node, null ).get( 0 ).getArtifact();

            return artifact.getFile();

        } catch( DependencyCollectionException e )
        {
            throw new RepositoryException( "Problem resolving " + identifier + " with Aether Binding", e );
        } catch( ArtifactResolutionException e )
        {
            throw new RepositoryException( "Problem resolving " + identifier + " with Aether Binding", e );
        }

    }

    private CollectRequest prepareResolveRequest( Dependency dependency )
    {
        CollectRequest collectRequest = new CollectRequest();
        collectRequest.setRoot( dependency );

        int i = 0;
        for( String repos : m_repositories )
        {
            RemoteRepository central = new RemoteRepository( "repos" + ( i++ ), "default", repos );
            LOG.info( "+ " + repos );
            collectRequest.addRepository( central );
        }
        return collectRequest;
    }

    private RepositorySystemSession newSession( RepositorySystem system )
    {
        MavenRepositorySystemSession session = new MavenRepositorySystemSession();

        LocalRepository localRepo = new LocalRepository( m_localRepo );
        session.setLocalRepositoryManager( system.newLocalRepositoryManager( localRepo ) );

        return session;
    }

    private RepositorySystem newRepositorySystem()
    {
        DefaultServiceLocator locator = new DefaultServiceLocator();

        locator.setServices( WagonProvider.class, new ManualWagonProvider() );
        locator.addService( RepositoryConnectorFactory.class, WagonRepositoryConnectorFactory.class );

        return locator.getService( RepositorySystem.class );
    }
}
