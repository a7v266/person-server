package org.sandbox.domain;

import org.springframework.security.core.GrantedAuthority;

public enum UserRole implements GrantedAuthority {

    ROLE_USER_VIEW,
    ROLE_USER_SAVE,
    ROLE_USER_DELETE,

    ROLE_USER_GROUP_VIEW,
    ROLE_USER_GROUP_SAVE,
    ROLE_USER_GROUP_DELETE;

    @Override
    public String getAuthority() {
        return name();
    }
}
