package org.sandbox.hibernate;

public class StringCollectionType extends CollectionType {

    @Override
    public Class returnedClass() {
        return String.class;
    }
}
