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
package org.sonatype.nexus.gwt.client.nexus;

import org.sonatype.gwt.client.handler.EntityResponseHandler;
import org.sonatype.gwt.client.resource.Representation;
import org.sonatype.nexus.gwt.client.Nexus;
import org.sonatype.nexus.gwt.client.services.GlobalSettingsService;

public class DefaultGlobalSettingsService
    extends AbstractNexusService
    implements GlobalSettingsService
{

    public DefaultGlobalSettingsService( Nexus nexus, String path )
    {
        super( nexus, path );
    }

    public void listGlobalSettings( EntityResponseHandler handler )
    {
        // TODO Auto-generated method stub
        
    }

    public void readGlobalSettings( String settingsPath, EntityResponseHandler handler )
    {
        // TODO Auto-generated method stub
        
    }

    public void updateGlobalSettings( String settingsPath, Representation representation )
    {
        // TODO Auto-generated method stub
        
    }

}
