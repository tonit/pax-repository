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

import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.ops4j.pax.repository.resolver.RepositoryFactory.*;

/**
 * Testing the Parser
 */
public class PathToIdentifierParserTest
{

    @Test
    public void parseEmpty()
    {
        assertThat( getParser().parse( "" ), is( equalTo( identifier( "", "0","" ) ) ) );
    }

    @Test
    public void parseNoVersionAndSuffix()
    {
        assertThat( getParser().parse( "cheese" ), is( equalTo( identifier( "cheese", "0","" ) ) ) );
    }

    @Test
    public void parseSuffixOnly()
    {
        assertThat( getParser().parse( "cheese.composite" ), is( equalTo( identifier( "cheese", "0","composite" ) ) ) );
    }

    @Test
    public void parseSuffixAndVersion()
    {
        assertThat( getParser().parse( "cheese-0.1.0.composite" ), is( equalTo( identifier( "cheese", "0.1.0","composite" ) ) ) );
    }

    @Test
    public void parseSuffixAndVersionSnapshot()
    {
        assertThat( getParser().parse( "cheese-0.1.0-SNAPSHOT.composite" ), is( equalTo( identifier( "cheese", "0.1.0-SNAPSHOT","composite" ) ) ) );
    }

    @Test
    public void parseLongNameWithSuffixAndVersionBeta()
    {
        assertThat( getParser().parse( "cheese-bacon-0.1.0-beta.composite" ), is( equalTo( identifier( "cheese-bacon", "0.1.0-beta","composite" ) ) ) );
    }

    private PathToIdentifierParser getParser()
    {
        return new PathToIdentifierParser();
    }
}
