package org.sandbox.service.persistence;

import org.sandbox.domain.Contract;
import org.springframework.stereotype.Repository;

@Repository
public class ContractPersistenceImpl extends BasePersistenceImpl<Contract, Long> implements ContractPersistence {
    public ContractPersistenceImpl() {
        super(Contract.class);
    }
}
