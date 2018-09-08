package org.sandbox.hibernate;

public class LongCollectionType extends CollectionType {

    @Override
    public Class returnedClass() {
        return Long.class;
    }
}
