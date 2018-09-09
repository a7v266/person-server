package org.sandbox.domain;

import org.springframework.security.core.GrantedAuthority;

public enum UserRole implements GrantedAuthority {

    ROLE_USER_VIEW,
    ROLE_USER_SAVE,
    ROLE_USER_DELETE,

    ROLE_USER_GROUP_VIEW,
    ROLE_USER_GROUP_SAVE,
    ROLE_USER_GROUP_DELETE,

    ROLE_CLIENT_VIEW,
    ROLE_CLIENT_SAVE,
    ROLE_CLIENT_DELETE,

    ROLE_CONTRACT_VIEW,
    ROLE_CONTRACT_SAVE,
    ROLE_CONTRACT_DELETE,

    ROLE_CLIENT_TYPE_VIEW;

    @Override
    public String getAuthority() {
        return name();
    }
}
