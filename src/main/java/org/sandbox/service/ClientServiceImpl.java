package org.sandbox.service;

import org.sandbox.domain.Client;
import org.sandbox.service.persistence.ClientPersistence;
import org.sandbox.service.search.Search;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Throwable.class)
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientPersistence clientPersistence;

    @Override
    @Transactional(readOnly = true)
    public List<Client> getClientList(Search search) {
        return clientPersistence.list(search);
    }

    @Override
    @Transactional(readOnly = true)
    public Long getClientCount(Search search) {
        return clientPersistence.count(search);
    }

    @Override
    @Transactional(readOnly = true)
    public Client getClient(Long id) {
        return clientPersistence.get(id);
    }
}
