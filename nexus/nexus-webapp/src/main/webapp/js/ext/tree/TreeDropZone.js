/*
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

Ext.override(Ext.tree.TreeDropZone, {
      completeDrop : function(de) {
        var ns = de.dropNode, p = de.point, t = de.target;
        if (!Ext.isArray(ns))
        {
          ns = [ns];
        }
        var n, node, ins = false;
        if (p != 'append')
        {
          ins = true;
          node = (p == 'above') ? t : t.nextSibling;
        }
        for (var i = 0, len = ns.length; i < len; i++)
        {
          n = ns[i];
          if (ins)
          {
            t.parentNode.insertBefore(n, node);
          }
          else
          {
            t.appendChild(n);
          }
          if (Ext.enableFx && this.tree.hlDrop)
          {
            n.ui.highlight();
          }
        }
        ns[0].ui.focus();
        t.ui.endDrop();
        this.tree.fireEvent("nodedrop", de);
      }

    });
