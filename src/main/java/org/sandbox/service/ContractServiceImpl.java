package org.sandbox.service;

import org.sandbox.domain.Contract;
import org.sandbox.service.persistence.ContractPersistence;
import org.sandbox.service.search.Search;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Throwable.class)
public class ContractServiceImpl implements ContractService {

    @Autowired
    private ContractPersistence contractPersistence;

    @Override
    @Transactional(readOnly = true)
    public List<Contract> getContractList(Search search) {
        return contractPersistence.list(search);
    }

    @Override
    @Transactional(readOnly = true)
    public Long getContractCount(Search search) {
        return contractPersistence.count(search);
    }

    @Override
    @Transactional(readOnly = true)
    public Contract getContract(Long id) {
        return contractPersistence.get(id);
    }
}
