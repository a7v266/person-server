package org.sandbox.service.persistence;

import org.sandbox.domain.User;
import org.springframework.stereotype.Repository;

@Repository
public class UserPersistenceImpl extends BasePersistenceImpl<User, Long> implements UserPersistence {
    public UserPersistenceImpl() {
        super(User.class);
    }
}
