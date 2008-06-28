package org.sonatype.nexus.ext.gwt.ui.client.reposerver.model;

import org.sonatype.nexus.ext.gwt.ui.client.data.Entity;

import com.extjs.gxt.ui.client.data.BaseModelData;

public class AuthenticationLoginResource extends BaseModelData implements Entity {
    
    private String authToken;
    
    private AuthenticationClientPermissions clientPermissions;

    public String getType() {
        return "org.sonatype.nexus.rest.model.AuthenticationLoginResource";
    }

    public Class getFieldType(String fieldName) {
        return "clientPermissions".equals(fieldName) ? AuthenticationClientPermissions.class : String.class;
    }

    public Entity createEntity(String fieldName) {
        return "clientPermissions".equals(fieldName) ? new AuthenticationClientPermissions() : null;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public AuthenticationClientPermissions getClientPermissions() {
        return clientPermissions;
    }

    public void setClientPermissions(
            AuthenticationClientPermissions clientPermissions) {
        this.clientPermissions = clientPermissions;
    }

}
