package org.ops4j.pax.repository.example;import java.io.InputStream;import javax.inject.Provider;import com.google.inject.AbstractModule;import com.google.inject.Guice;import com.google.inject.Injector;import com.google.inject.Key;import com.google.inject.TypeLiteral;import org.junit.Test;import org.ops4j.base.io.UncachedProvider;import org.ops4j.pax.repository.RepositoryException;import org.ops4j.pax.repository.Resolver;import org.ops4j.pax.repository.SyncService;import org.ops4j.pax.repository.base.PassiveBlockingSyncService;import org.ops4j.pax.repository.base.PassiveResolver;import org.ops4j.pax.repository.maven.GAV;import org.ops4j.pax.repository.maven.MavenModule;import org.ops4j.pax.repository.tarball.TarballModule;import static com.google.inject.name.Names.*;import static org.ops4j.pax.repository.maven.impl.DefaultGAV.*;/** * */public class MavenAndTarballsDemo{    private AbstractModule configuration()    {        return new AbstractModule()        {            @Override            protected void configure()            {                install( new TarballModule<String>() );                install( new MavenModule()) ;                // This is what you may want to select on a custom level:                // 1. The Data Source for Repo                bind( new TypeLiteral<Provider<InputStream>>() {}  ).annotatedWith( named( "repository" ) ).toInstance( new UncachedProvider<InputStream>( MavenAndTarballsDemo.class.getResourceAsStream( "/" ) ) );                // 2. Provisioning of index strategy                bind(SyncService.class).to( new TypeLiteral<PassiveBlockingSyncService<String,InputStream, String>>() { } );                // 3. A Resolver to map queries properly                bind(new TypeLiteral<Resolver<GAV,InputStream>>() { }  ).to( new TypeLiteral<PassiveResolver<GAV, String, InputStream>>() { } );            }        };    }    /**     * The input data.     * @return A fully prepared resolver subsystem     * @throws org.ops4j.pax.repository.RepositoryException In case of a repository system problem.     */    private Resolver<GAV,InputStream> setup()        throws RepositoryException    {        Injector injector = Guice.createInjector( configuration() );        // Because we chose a passive resolver, we need to do this initially        SyncService sync = injector.getInstance( SyncService.class );        sync.sync();        return injector.getInstance( Key.get( new TypeLiteral<Resolver<GAV,InputStream>>() { }  ));    }    @Test    public void story()        throws RepositoryException    {        // Now lets rock.        Resolver<GAV,InputStream> resolver = setup();        resolver.find( gav("","","") );    }}