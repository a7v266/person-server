package org.sandbox.service.persistence;

import org.sandbox.domain.Client;
import org.springframework.stereotype.Repository;

@Repository
public class ClientPersistenceImpl extends BasePersistenceImpl<Client, Long> implements ClientPersistence {
    public ClientPersistenceImpl() {
        super(Client.class);
    }
}
