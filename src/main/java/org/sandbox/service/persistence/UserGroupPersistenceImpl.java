package org.sandbox.service.persistence;

import org.sandbox.domain.UserGroup;
import org.springframework.stereotype.Repository;

@Repository
public class UserGroupPersistenceImpl extends BasePersistenceImpl<UserGroup, Long> implements UserGroupPersistence {
    public UserGroupPersistenceImpl() {
        super(UserGroup.class);
    }
}
