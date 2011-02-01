/* * Copyright (C) 2010 Toni Menzel * * Licensed under the Apache License, Version 2.0 (the "License"); * you may not use this file except in compliance with the License. * You may obtain a copy of the License at * *      http://www.apache.org/licenses/LICENSE-2.0 * * Unless required by applicable law or agreed to in writing, software * distributed under the License is distributed on an "AS IS" BASIS, * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. * See the License for the specific language governing permissions and * limitations under the License. */package org.ops4j.pax.repository.maven.impl;import org.junit.Test;import org.mockito.Matchers;import org.ops4j.pax.repository.Artifact;import org.ops4j.pax.repository.RepositoryException;import org.ops4j.pax.repository.base.InMemoryRepository;import org.ops4j.pax.repository.maven.GAV;import org.ops4j.pax.repository.maven.impl.DefaultGAV;import org.ops4j.pax.repository.maven.impl.GavIndex;import org.ops4j.pax.repository.spi.IndexVisitor;import org.ops4j.pax.repository.spi.Repository;import static org.junit.Assert.*;import static org.mockito.Mockito.*;import static org.ops4j.pax.repository.base.RepositoryEntry.*;/** * */public class GavIndexTest{    @Test    public void testSync()        throws RepositoryException    {        GavIndex index = new GavIndex();        Repository<GAV> repo = mock( Repository.class );        index.sync( repo );        verify( repo ).index( Matchers.<IndexVisitor<GAV>>any() );    }    @Test( expected = IllegalStateException.class )    public void testGetWithoutSync()        throws RepositoryException    {        GavIndex index = new GavIndex();        GAV query = mock( GAV.class );        index.get( query );    }    @Test    public void testSingleMatching()        throws RepositoryException    {        GavIndex index = new GavIndex();        Artifact storedArtifact1 = mock( Artifact.class );        Artifact storedArtifact2 = mock( Artifact.class );        GAV key1 = new DefaultGAV( "foo", "bar", "1", "jar" );        GAV key2 = new DefaultGAV( "other", "bar", "2", "jar" );        index.sync( InMemoryRepository.create(            createEntry( key1, storedArtifact1 ),            createEntry( key2, storedArtifact2 )        )        );        Artifact artifact1 = index.get( new DefaultGAV( "foo", "bar", "", "jar" ) );        assertEquals( artifact1, storedArtifact1 );    }    @Test    public void testDifferentVersions()        throws RepositoryException    {        GavIndex index = new GavIndex();        Artifact storedArtifact1 = mock( Artifact.class );        Artifact storedArtifact2 = mock( Artifact.class );        Artifact storedArtifact3 = mock( Artifact.class );        GAV key1 = new DefaultGAV( "foo", "bar", "1", "jar" );        GAV key2 = new DefaultGAV( "foo", "bar", "100", "jar" );        GAV key3 = new DefaultGAV( "foo", "bar", "2", "jar" );        index.sync( InMemoryRepository.create(            createEntry( key1, storedArtifact1 ),            createEntry( key2, storedArtifact2 ),            createEntry( key3, storedArtifact3 )        )        );        Artifact a = index.get( new DefaultGAV( "foo", "bar", "", "jar" ) );        assertEquals( a, storedArtifact2 );    }    @Test    public void testNoneMatching()        throws RepositoryException    {        GavIndex index = new GavIndex();        Artifact storedArtifact1 = mock( Artifact.class );        Artifact storedArtifact2 = mock( Artifact.class );        GAV key1 = new DefaultGAV( "other", "bar", "1", "jar" );        GAV key2 = new DefaultGAV( "foo", "another", "2", "jar" );        index.sync( InMemoryRepository.create(            createEntry( key1, storedArtifact1 ),            createEntry( key2, storedArtifact2 )        )        );        Artifact artifact1 = index.get( new DefaultGAV( "foo", "bar", "", "jar" ) );        assertNull( "There should be no match", artifact1 );    }}