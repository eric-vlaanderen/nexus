/*
 * Nexus Plugin for Maven
 * Copyright (C) 2009 Sonatype, Inc.                                                                                                                          
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 */
package org.sonatype.nexus.plugin;

import static junit.framework.Assert.fail;

import org.codehaus.plexus.components.interactivity.Prompter;
import org.codehaus.plexus.components.interactivity.PrompterException;
import org.codehaus.plexus.util.StringUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

public class ExpectPrompter
    implements Prompter
{
    
    private final LinkedHashMap<String[], String> expectations = new LinkedHashMap<String[], String>();
    
    private int expectationIndex = 0;
    
    private boolean useOrder = false;

    private final Set<String[]> used = new HashSet<String[]>();
    
    private boolean output = false;

    private boolean debug = false;

    public void enableDebugging()
    {
        debug = true;
        enableOutput();
    }

    public void enableOutput()
    {
        output = true;
    }

    public void disableOutput()
    {
        output = false;
    }

    public void addExpectation( final String promptSubstr, final String response )
    {
        addExpectation( response, new String[] { promptSubstr } );
    }

    public void addExpectation( final String response, final String... fragments )
    {
        expectations.put( fragments, response );
    }

    public String prompt( final String prompt )
        throws PrompterException
    {
        return expectationFor( prompt );
    }

    public String prompt( final String prompt, final String defVal )
        throws PrompterException
    {
        String result = expectationFor( prompt, defVal );
        if ( result == null || result.length() < 1 )
        {
            result = defVal;
        }

        return result;
    }

    @SuppressWarnings("unchecked")
    public String prompt( final String prompt, final List values )
        throws PrompterException
    {
        String wholePrompt = StringUtils.join( values.iterator(), "\n" ) + "\n\n" + prompt + ": ";
        return expectationFor( wholePrompt );
    }

    @SuppressWarnings("unchecked")
    public String prompt( final String prompt, final List values, final String defVal )
        throws PrompterException
    {
        String wholePrompt = StringUtils.join( values.iterator(), "\n" ) + "\n\n" + prompt + " [" + defVal + "]: ";

        String result = expectationFor( wholePrompt, defVal );
        if ( result == null || result.length() < 1 )
        {
            result = defVal;
        }

        return result;
    }

    public String promptForPassword( final String prompt )
        throws PrompterException
    {
        return expectationFor( prompt );
    }

    public void showMessage( final String prompt )
        throws PrompterException
    {
        System.out.println( prompt );
    }

    private String expectationFor( final String prompt )
    {
        return expectationFor( prompt, null );
    }

    private String expectationFor( final String prompt, final String defaultValue )
    {
        if ( output )
        {
            System.out.print( prompt );
        }
        
        String result = defaultValue == null ? "-NOT SUPPLIED-" : defaultValue;
        String original = result;

        if( useOrder )
        {
            // NOTE: A LinkedMap from Commons Collections might be great here, but they are not generic
            List<Entry<String[], String>> orderedEntries = new ArrayList<Entry<String[], String>>( expectations.entrySet() );
            Entry<String[], String> entry = orderedEntries.get( expectationIndex++ );
            // just return the value (in order)
            result = entry.getValue();
            // mark it used
            used.add( entry.getKey() );
        }
        else
        {
            promptMatching: for ( Map.Entry<String[], String> entry : expectations.entrySet() )
            {
                int idx = 0;
                for ( String fragment : entry.getKey() )
                {
                    int nxtIdx = prompt.toLowerCase().indexOf( fragment.toLowerCase(), idx );
                    if ( nxtIdx < 0 )
                    {
                        continue promptMatching;
                    }
                    
                    idx = nxtIdx + fragment.length();
                }
    
                result = entry.getValue();
                used.add( entry.getKey() );
                break;
            }
        }
        if ( output )
        {
            System.out.println( result );
        }
        
        if ( debug && original.equals( result ) )
        {
            System.out.println( "Debug mode enabled; suspending for prompt examination." );
            try
            {
                while ( true )
                {
                    Thread.sleep( 5000 );
                }
            }
            catch ( InterruptedException e )
            {
            }
        }

        return result;
    }

    public void verifyPromptsUsed()
    {
        Map<String[], String> remaining = new LinkedHashMap<String[], String>( expectations );
        remaining.keySet().removeAll( used );
        
        if ( !remaining.isEmpty() )
        {
            StringBuilder sb = new StringBuilder();
            
            sb.append( "The following prompt/answer pairs were never used:\n" );
            for ( Map.Entry<String[], String> entry : remaining.entrySet() )
            {
                sb.append( "\n-  " )
                  .append( StringUtils.join( entry.getKey(), "*" ) )
                  .append( " = " )
                  .append( entry.getValue() );
            }
            sb.append( "\n\n" );
            
            System.out.println( sb.toString() );
            fail( sb.toString() );
        }
    }

    public void setUseOrder( boolean useOrder )
    {
        this.useOrder = useOrder;
    }

    public boolean isUseOrder()
    {
        return useOrder;
    }

}