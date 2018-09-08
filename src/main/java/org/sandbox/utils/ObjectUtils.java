package org.sandbox.utils;

import java.util.Collection;

public class ObjectUtils {

    public static <T> boolean equals(T object1, T object2) {
        if (object1 == object2) {
            return true;
        }
        if (object1 == null || object2 == null) {
            return false;
        }
        return object1.equals(object2);
    }

    public static <T> boolean notEquals(T object1, T object2) {
        return !equals(object1, object2);
    }

    public static <T extends Comparable<T>> boolean notEquals(T value1, T value2) {
        return !equals(value1, value2);
    }

    public static <T extends Comparable<T>> boolean equals(T value1, T value2) {
        if (value1 == value2) {
            return true;
        }
        if (value1 == null || value2 == null) {
            return false;
        }
        return value1.compareTo(value2) == 0;
    }

    public static <T> boolean comparable(T value1, T value2) {
        if (value1 == null || value2 == null) {
            return false;
        }
        if (value1.equals(value2)) {
            return false;
        }
        return true;
    }

    public static <T> boolean changed(T value1, T value2) {
        if (value1 == null && value2 == null) {
            return false;
        }
        return notEquals(value1, value2);
    }

    public static String getClassName(Object object) {
        if (object != null) {
            return object.getClass().getSimpleName();
        }
        return null;
    }

    public static boolean isEmpty(Object object) {
        if (object == null) {
            return true;
        }
        if (object instanceof String) {
            return ((String) object).isEmpty();
        }
        if (object instanceof Object[]) {
            return ((Object[]) object).length == 0;
        }
        if (object instanceof Collection) {
            return ((Collection) object).isEmpty();
        }
        return false;
    }

    public static <T> boolean isNotEmpty(T item) {
        return !isEmpty(item);
    }

    public static boolean isNotNull(Object... objects) {
        for (Object object : objects) {
            if (object == null) {
                return false;
            }
        }
        return true;
    }
}
