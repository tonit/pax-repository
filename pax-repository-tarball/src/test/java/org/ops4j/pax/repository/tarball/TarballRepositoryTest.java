package org.ops4j.pax.repository.tarball;import java.io.IOException;import java.io.InputStream;import org.hamcrest.CoreMatchers;import org.junit.Test;import org.mockito.Matchers;import org.ops4j.base.io.InputStreamSource;import org.ops4j.pax.repository.Artifact;import org.ops4j.pax.repository.RepositoryException;import org.ops4j.pax.repository.base.DefaultEntryParser;import org.ops4j.pax.repository.base.EmptyFilter;import org.ops4j.pax.repository.spi.ArtifactEntry;import org.ops4j.pax.repository.spi.ArtifactFilter;import org.ops4j.pax.repository.spi.EntryParser;import org.ops4j.pax.repository.spi.IndexVisitor;import org.ops4j.store.Store;import org.ops4j.store.StoreFactory;import static org.hamcrest.CoreMatchers.*;import static org.junit.Assert.*;import static org.mockito.Mockito.*;import static org.ops4j.pax.repository.base.RepositoryFactory.*;/** * */public class TarballRepositoryTest{    @Test( expected = IllegalStateException.class )    public void retrieveWithoutIndexing()        throws RepositoryException    {        TarballRepository<ArtifactEntry> tr = createRepository( getPath() );        // this is a foreign entry lookup. This must fail as it is an api usage bug!        tr.retrieve( entry( "bee" ) );    }    @Test    public void indexer()        throws RepositoryException    {        TarballRepository<ArtifactEntry> tr = createRepository( getPath() );        // this is a foreign entry lookup. This must fail as it is an api usage bug!        IndexVisitor<ArtifactEntry> visitor = mock( IndexVisitor.class );        tr.index( visitor );        verify( visitor, times( 4 ) ).touch( Matchers.<ArtifactEntry>any() );    }    private String getPath()    {        return "/gouken-tarball-0.1.0-SNAPSHOT-bin.zip";    }    private TarballRepository<ArtifactEntry> createRepository( String path )        throws RepositoryException    {        Store<InputStream> store = StoreFactory.defaultStore();        // the source:        InputStreamSource input = source( getClass().getResourceAsStream( path ) );        ArtifactFilter<ArtifactEntry> filter = new EmptyFilter<ArtifactEntry>( true );        EntryParser<ArtifactEntry> parser = new DefaultEntryParser();        return new TarballRepository<ArtifactEntry>(            input,            filter,            store,            parser        );    }    private InputStreamSource source( final InputStream stream )    {        assert stream != null : stream;        return new InputStreamSource()        {            public InputStream get()                throws IOException            {                return stream;            }        };    }}