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
package org.ops4j.pax.repository.base;

import org.ops4j.pax.repository.ArtifactEntry;
import org.ops4j.pax.repository.ArtifactQuery;
import org.ops4j.pax.repository.base.DefaultEntry;
import org.ops4j.pax.repository.base.DefaultQuery;

/**
 *
 */
public class RepositoryFactory
{

    public static ArtifactQuery createQuery( String query )
    {

        return new DefaultQuery( query );

    }

    public static ArtifactEntry entry( String entry )
    {

        return new DefaultEntry( entry );

    }


}
