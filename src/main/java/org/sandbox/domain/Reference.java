package org.sandbox.domain;

import org.sandbox.utils.Format;
import org.sandbox.utils.ObjectUtils;
import org.sandbox.utils.SqlUtils;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface Reference {

    String ID = "id";
    String TITLE = "title";

    Integer getId();

    String getTitle();

    static boolean equals(Reference reference1, Reference reference2) {
        return ObjectUtils.equals(getId(reference1), getId(reference2));
    }

    static int getHashCode(Reference reference) {
        return getId(reference).hashCode();
    }

    static Integer getId(Reference reference) {
        if (reference == null) {
            return null;
        }
        return reference.getId();
    }

    static String getTitle(Reference reference) {
        if (reference == null) {
            return Format.EMPTY_STRING;
        }
        return reference.getTitle();
    }

    static <T extends Reference> T getReference(Class<T> referenceClass, Integer id) {
        if (id == null) {
            return null;
        }
        for (T value : referenceClass.getEnumConstants()) {
            if (id.equals(value.getId())) {
                return value;
            }
        }
        return null;
    }

    static <T extends Reference> T getReference(Class<T> referenceClass, ResultSet resultSet, String field) throws SQLException {
        return getReference(referenceClass, SqlUtils.getInteger(resultSet, field));
    }
}
