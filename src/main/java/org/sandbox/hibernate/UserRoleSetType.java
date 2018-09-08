package org.sandbox.hibernate;

import org.sandbox.domain.UserRole;

public class UserRoleSetType extends SetType {

    @Override
    public Class returnedClass() {
        return UserRole.class;
    }
}
