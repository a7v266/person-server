package org.sandbox.hibernate;

public class IntegerSetType extends SetType {

    @Override
    public Class returnedClass() {
        return Integer.class;
    }
}
