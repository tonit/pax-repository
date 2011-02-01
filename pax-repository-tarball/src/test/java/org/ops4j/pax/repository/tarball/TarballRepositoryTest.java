/* * Copyright (C) 2010 Toni Menzel * * Licensed under the Apache License, Version 2.0 (the "License"); * you may not use this file except in compliance with the License. * You may obtain a copy of the License at * *      http://www.apache.org/licenses/LICENSE-2.0 * * Unless required by applicable law or agreed to in writing, software * distributed under the License is distributed on an "AS IS" BASIS, * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. * See the License for the specific language governing permissions and * limitations under the License. */package org.ops4j.pax.repository.tarball;import java.io.IOException;import java.io.InputStream;import org.junit.Test;import org.mockito.Matchers;import org.ops4j.base.io.Source;import org.ops4j.pax.repository.IndexVisitor;import org.ops4j.pax.repository.Parser;import org.ops4j.pax.repository.RepositoryException;import org.ops4j.pax.repository.base.EmptyFilter;import org.ops4j.pax.repository.ArtifactFilter;import org.ops4j.store.Store;import org.ops4j.store.StoreFactory;import static org.mockito.Mockito.*;/** * */public class TarballRepositoryTest{    @Test( expected = IllegalStateException.class )    public void retrieveWithoutIndexing()        throws RepositoryException    {        TarballRepository<String> tr = createRepository( getPath() );        // this is a foreign entry lookup. This must fail as it is an api usage bug!        tr.retrieve( "bee" );    }    @Test    public void indexer()        throws RepositoryException    {        TarballRepository<String> tr = createRepository( getPath() );        // this is a foreign entry lookup. This must fail as it is an api usage bug!        IndexVisitor<String> visitor = mock( IndexVisitor.class );        tr.index( visitor );        verify( visitor, times( 4 ) ).touch( Matchers.<String>any() );    }    private String getPath()    {        return "/gouken-tarball-0.1.0-SNAPSHOT-bin.zip";    }    private TarballRepository<String> createRepository( String path )        throws RepositoryException    {        Store<InputStream> store = StoreFactory.defaultStore();        // the source:        Source<InputStream> input = source( getClass().getResourceAsStream( path ) );        ArtifactFilter<String> filter = new EmptyFilter<String>( true );        Parser<String, String> parser = new EmptyParser();        return new TarballRepository<String>(            input,            store,            filter,            parser        );    }    private Source<InputStream> source( final InputStream stream )    {        assert stream != null : stream;        return new Source<InputStream>()        {            public InputStream get()                throws IOException            {                return stream;            }        };    }}