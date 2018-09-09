package org.sandbox.hibernate;

import org.sandbox.domain.ClientType;

public class ClientTypeType extends ReferenceType<ClientType> {
    @Override
    public Class<ClientType> returnedClass() {
        return ClientType.class;
    }
}
