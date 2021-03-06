/**
 * Sonatype Nexus (TM) Open Source Version
 * Copyright (c) 2007-2012 Sonatype, Inc.
 * All rights reserved. Includes the third-party code listed at http://links.sonatype.com/products/nexus/oss/attributions.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License Version 1.0,
 * which accompanies this distribution and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Sonatype Nexus (TM) Professional Version is available from Sonatype, Inc. "Sonatype" and "Sonatype Nexus" are trademarks
 * of Sonatype, Inc. Apache Maven is a trademark of the Apache Software Foundation. M2eclipse is a trademark of the
 * Eclipse Foundation. All other trademarks are the property of their respective owners.
 */
package org.sonatype.nexus.rest.indexng;

import org.apache.maven.index.ArtifactInfo;
import org.apache.maven.index.context.IndexingContext;

/**
 * A special filter that actually does not filter, but collects the latest and release version for every RGA. After
 * iteratorSearchResponse has been processed, this collector will hold all the needed versions of the processed artifact
 * infos.
 * 
 * @author cstamas
 */
public class RepositoryWideLatestVersionCollector
    extends AbstractLatestVersionCollector
{
    @Override
    public LatestVersionHolder createLVH( IndexingContext ctx, ArtifactInfo ai )
    {
        return new LatestECVersionHolder( ai );
    }

    @Override
    public String getKeyFromAi( IndexingContext ctx, ArtifactInfo ai )
    {
        return getKey( ai.repository, ai.groupId, ai.artifactId );
    }

    @Override
    public LatestECVersionHolder getLVHForKey( String key )
    {
        return (LatestECVersionHolder) getLvhs().get( key );
    }

    public String getKey( String repositoryId, String groupId, String artifactId )
    {
        return repositoryId + ":" + groupId + ":" + artifactId;
    }
}
