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
package org.sonatype.nexus.proxy.http;

import org.codehaus.plexus.component.annotations.Component;
import org.codehaus.plexus.component.annotations.Requirement;
import org.sonatype.nexus.proxy.events.AbstractEventInspector;
import org.sonatype.nexus.proxy.events.AsynchronousEventInspector;
import org.sonatype.nexus.proxy.events.EventInspector;
import org.sonatype.nexus.proxy.events.NexusStartedEvent;
import org.sonatype.nexus.proxy.events.NexusStoppingEvent;
import org.sonatype.plexus.appevents.Event;

@Component( role = EventInspector.class, hint = "HttpProxyServiceEventInspector" )
public class HttpProxyServiceEventInspector
    extends AbstractEventInspector
    implements AsynchronousEventInspector
{
    @Requirement
    private HttpProxyService httpProxyService;

    @Override
    public boolean accepts( Event<?> evt )
    {
        return evt instanceof NexusStartedEvent || evt instanceof NexusStoppingEvent;
    }

    @Override
    public void inspect( Event<?> evt )
    {
        if ( evt instanceof NexusStartedEvent )
        {
            httpProxyService.start();
        }
        else if ( evt instanceof NexusStoppingEvent )
        {
            httpProxyService.stop();
        }
    }
}
