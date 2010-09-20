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
package org.ops4j.pax.repository.resolver;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.ops4j.pax.repository.ArtifactIdentifier;
import org.ops4j.pax.repository.QueryVisitor;

/**
 * For test purpose.
 */
public class IdentifierRecorder implements QueryVisitor, Iterable<ArtifactIdentifier>
{

    private List<ArtifactIdentifier> m_recorded = new ArrayList<ArtifactIdentifier>();

    public void touch( ArtifactIdentifier id )
    {
        m_recorded.add( id );
    }

    public Iterator<ArtifactIdentifier> iterator()
    {
        return m_recorded.iterator();
    }
}
