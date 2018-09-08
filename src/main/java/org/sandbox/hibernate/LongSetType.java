package org.sandbox.hibernate;

public class LongSetType extends SetType {

    @Override
    public Class returnedClass() {
        return Long.class;
    }
}
