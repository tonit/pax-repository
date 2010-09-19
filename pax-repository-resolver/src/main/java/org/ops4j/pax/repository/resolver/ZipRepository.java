/*
 * Copyright (C) 2010 Toni Menzel
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
package org.ops4j.pax.repository.resolver;

import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import org.ops4j.pax.repository.Artifact;
import org.ops4j.pax.repository.ArtifactIdentifier;
import org.ops4j.pax.repository.InputStreamSource;
import org.ops4j.pax.repository.QueryVisitor;
import org.ops4j.pax.repository.Repository;
import org.ops4j.pax.repository.RepositoryException;

/**
 * URL based repository session.
 */
public class ZipRepository implements Repository
{

    public ZipRepository( final InputStreamSource input )
        throws RepositoryException
    {
        ZipInputStream inp = new ZipInputStream( input.get() );

        try
        {
            ZipEntry entry;
            while( ( entry = inp.getNextEntry() ) != null )
            {

            }
        } catch( IOException e )
        {
            throw new RepositoryException( "Problem opening Repository from Archive.", e );
        }
    }

    public void index( QueryVisitor visit )
        throws RepositoryException
    {

       
    }

    public Artifact retrieve( ArtifactIdentifier id )
        throws RepositoryException
    {
        return null;
    }


}
