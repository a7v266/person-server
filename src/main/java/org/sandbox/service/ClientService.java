package org.sandbox.service;

import org.sandbox.domain.Client;
import org.sandbox.service.search.Search;

import java.util.List;

public interface ClientService {

    List<Client> getClientList(Search search);

    Client getClient(Long id);

    Long getClientCount(Search search);
}
