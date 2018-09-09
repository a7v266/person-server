package org.sandbox.service;

import org.sandbox.domain.Contract;
import org.sandbox.service.search.Search;

import java.util.List;

public interface ContractService {

    List<Contract> getContractList(Search search);

    Contract getContract(Long id);

    Long getContractCount(Search search);
}
