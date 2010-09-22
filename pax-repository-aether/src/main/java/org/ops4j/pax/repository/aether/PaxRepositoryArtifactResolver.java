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
package org.ops4j.pax.repository.aether;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.apache.xbean.recipe.DefaultRepository;
import org.codehaus.plexus.component.annotations.Component;
import org.codehaus.plexus.component.annotations.Requirement;
import org.sonatype.aether.RepositorySystemSession;
import org.sonatype.aether.impl.ArtifactResolver;
import org.sonatype.aether.impl.LocalRepositoryMaintainer;
import org.sonatype.aether.impl.RemoteRepositoryManager;
import org.sonatype.aether.impl.UpdateCheckManager;
import org.sonatype.aether.impl.VersionResolver;
import org.sonatype.aether.repository.RemoteRepository;
import org.sonatype.aether.resolution.ArtifactRequest;
import org.sonatype.aether.resolution.ArtifactResolutionException;
import org.sonatype.aether.resolution.ArtifactResult;
import org.sonatype.aether.spi.log.Logger;
import org.sonatype.aether.spi.log.NullLogger;
import org.ops4j.pax.repository.Artifact;
import org.ops4j.pax.repository.InputStreamSource;
import org.ops4j.pax.repository.RepositoryResolver;
import org.ops4j.pax.repository.resolver.ClassifierRegexFilter;
import org.ops4j.pax.repository.resolver.DefaultResolver;
import org.ops4j.pax.repository.resolver.ZipRepository;

import static org.ops4j.pax.repository.resolver.RepositoryFactory.*;

/**
 * Bridges Aether System Repo to Pax Repository
 */
@Component( role = ArtifactResolver.class )
public class PaxRepositoryArtifactResolver implements ArtifactResolver
{

    @Requirement
    private Logger logger = NullLogger.INSTANCE;

    @Requirement
    private VersionResolver versionResolver;

    @Requirement
    private UpdateCheckManager updateCheckManager;

    @Requirement
    private RemoteRepositoryManager remoteRepositoryManager;

    @Requirement( role = LocalRepositoryMaintainer.class )
    private List<LocalRepositoryMaintainer> localRepositoryMaintainers = new ArrayList<LocalRepositoryMaintainer>();

    public PaxRepositoryArtifactResolver()
    {
        init();
    }

    private void init()
    {

    }

    public ArtifactResult resolveArtifact( RepositorySystemSession repositorySystemSession, ArtifactRequest artifactRequest )
        throws ArtifactResolutionException
    {
        String artifact = artifactRequest.getArtifact().getArtifactId();
        String version = artifactRequest.getArtifact().getVersion();
        String classifier = artifactRequest.getArtifact().getClassifier();

        ArtifactResult result =  new ArtifactResult(artifactRequest);
        // try to resolve from local store:

        // no ?
        try
        {
            for( final RemoteRepository repos : artifactRequest.getRepositories() )
            {
                RepositoryResolver resolver = new DefaultResolver(
                    new ZipRepository( new InputStreamSource()
                    {

                        public InputStream get()
                            throws IOException
                        {
                            return new URL( repos.getUrl() ).openStream();
                        }
                    }, new ClassifierRegexFilter( "composite" )
                    )
                );
                Artifact paxrepoArtifact = resolver.find( identifier( artifact, version, classifier ) );
                // map to ArtifactResult
               if (paxrepoArtifact != null) {
                   // result.setArtifact( artifactRequest.getArtifact().setFile( paxrepoArtifact.getFile() ));
               }
            }

        } catch( Exception e )
        {
            e.printStackTrace();
            // throw new ArtifactResolutionException( "Subsystem Problem", e );
        }
        return null;
    }

    public List<ArtifactResult> resolveArtifacts( RepositorySystemSession repositorySystemSession, Collection<? extends ArtifactRequest> artifactRequests )
        throws ArtifactResolutionException
    {
        return null;
    }
}
