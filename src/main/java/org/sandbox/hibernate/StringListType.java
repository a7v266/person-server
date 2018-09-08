package org.sandbox.hibernate;

public class StringListType extends ListType {

    @Override
    public Class returnedClass() {
        return String.class;
    }
}
