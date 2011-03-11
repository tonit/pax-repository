package org.ops4j.pax.repository.aether;import java.io.File;import java.io.IOException;import java.io.InputStream;import org.junit.Test;import org.slf4j.Logger;import org.slf4j.LoggerFactory;import org.ops4j.pax.repository.Provider;import org.ops4j.pax.repository.RepositoryException;import static org.junit.Assert.*;import static org.ops4j.pax.repository.maven.impl.DefaultGAV.*;/** * Aether */public class AetherResolverTest {    private static Logger LOG = LoggerFactory.getLogger( AetherResolverTest.class );    @Test    public void testPublicArtifact()        throws RepositoryException, IOException    {        String[] remotes = new String[]{ "http://repo1.maven.org/maven2/" };        AetherResolver res = new AetherResolver( getCache(), remotes );        Provider<InputStream> provider = res.find( gav( "org.ops4j.pax.logging", "pax-logging-api", "1.5.1" ) );        assertNotNull( provider );    }    @Test (expected = RepositoryException.class)    public void testNotAvailableArtifact()        throws RepositoryException, IOException    {        String[] remotes = new String[]{ "http://repo1.maven.org/maven2/" };        AetherResolver res = new AetherResolver( getCache(), remotes );        res.find( gav( "do", "not_exist", "1" ) );    }    @Test    public void testLocallyCachedArtifact()        throws RepositoryException, IOException    {        String cache = getCache();        AetherResolver preCache = new AetherResolver( cache, "http://repo1.maven.org/maven2/" );        preCache.find( gav( "org.ops4j.pax.logging", "pax-logging-api", "1.5.1" ) );        //new resolver on same cache but no remotes:        AetherResolver res = new AetherResolver( cache, "none" );        Provider<InputStream> provider = res.find( gav( "org.ops4j.pax.logging", "pax-logging-api", "1.5.1" ) );        assertNotNull( provider );    }    private String getCache()        throws IOException    {        File base = new File( "target" );        base.mkdir();        File f = File.createTempFile( "aethertest", ".dir", base );        f.delete();        f.mkdirs();        LOG.info( "Caching" + " to " + f.getAbsolutePath() );        return f.getAbsolutePath();    }}