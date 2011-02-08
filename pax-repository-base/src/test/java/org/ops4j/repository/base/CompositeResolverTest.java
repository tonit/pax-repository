/* * Copyright (C) 2010 Toni Menzel * * Licensed under the Apache License, Version 2.0 (the "License"); * you may not use this file except in compliance with the License. * You may obtain a copy of the License at * *      http://www.apache.org/licenses/LICENSE-2.0 * * Unless required by applicable law or agreed to in writing, software * distributed under the License is distributed on an "AS IS" BASIS, * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. * See the License for the specific language governing permissions and * limitations under the License. */package org.ops4j.repository.base;import java.io.IOException;import java.io.InputStream;import org.ops4j.pax.repository.Provider;import org.junit.Test;import org.ops4j.pax.repository.RepositoryException;import org.ops4j.pax.repository.Resolver;import org.ops4j.pax.repository.base.CompositeResolver;import static junit.framework.Assert.*;import static org.mockito.Mockito.*;/** * Test */public class CompositeResolverTest {    @Test    public void testWithoutAnyResolver()        throws IOException, RepositoryException    {        Resolver<String, InputStream> resolver = new CompositeResolver<String, InputStream>();        String query = "foo";        Provider<InputStream> artifact = resolver.find( query );        assertNull( artifact );    }    @Test    public void testWithResolvers()        throws IOException, RepositoryException    {        Resolver<String, InputStream> r1 = mock( Resolver.class );        Resolver<String, InputStream> r2 = mock( Resolver.class );        Resolver<String, InputStream> resolver = new CompositeResolver<String, InputStream>( r1, r2 );        String query = "foo";        Provider<InputStream> artifact = resolver.find( query );        verify( r1, times( 1 ) ).find( query );        verify( r2, times( 1 ) ).find( query );    }}