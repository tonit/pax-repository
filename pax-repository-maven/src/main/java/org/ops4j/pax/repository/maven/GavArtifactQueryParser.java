/* * Copyright (C) 2010 Toni Menzel * * Licensed under the Apache License, Version 2.0 (the "License"); * you may not use this file except in compliance with the License. * You may obtain a copy of the License at * *      http://www.apache.org/licenses/LICENSE-2.0 * * Unless required by applicable law or agreed to in writing, software * distributed under the License is distributed on an "AS IS" BASIS, * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. * See the License for the specific language governing permissions and * limitations under the License. */package org.ops4j.pax.repository.maven;import java.util.regex.Pattern;import org.ops4j.pax.repository.ArtifactQuery;import org.ops4j.pax.repository.spi.Parser;/** * Parses a string of format: GROUP:ARTIFACT:VERSION[:classifier] to an identifier. */public class GavArtifactQueryParser implements Parser<ArtifactQuery, GAV>{    private static final Pattern ARTIFACT_MATCHER = Pattern.compile( "(.+)(?:-(\\d+)(?:\\.(\\d+)(?:\\.(\\d+))?)?(?:[^a-zA-Z0-9](.*))?)(?:\\.([^\\.]+))", Pattern.DOTALL );    private static final Pattern FUZZY_MODIFIDER = Pattern.compile( "(?:\\d+[.-])*(.*)", Pattern.DOTALL );    public GAV parse( ArtifactQuery q )    {        String path = q.getQueryString();        String paths[] = path.split( ":" );        if( path.length() > 2 )        {            String groupId = paths[ 0 ];            String artifactId = paths[ 1 ];            String versionId = paths[ 2 ];            String classifier = null;            if( paths.length > 3 )            {                classifier = paths[ 3 ];            }            return new DefaultArtifactQuery( groupId, artifactId, versionId, classifier );        }        else        {            throw new IllegalArgumentException( "Cannot parse \"" + path + "\" as <GROUP>:<ARTIFACT>:<VERSION>[:CLASSIFIER]." );        }    }}