package org.sandbox.utils;

import java.math.BigInteger;

public class NumberUtils {

    public static Integer getInteger(Object object) {
        if (object == null) {
            return null;
        }
        if (object instanceof Integer) {
            return (Integer) object;
        }
        if (object instanceof String) {
            return parseInteger((String) object);
        }
        if (object instanceof Long) {
            return ((Long) object).intValue();
        }
        if (object instanceof BigInteger) {
            return ((BigInteger) object).intValue();
        }
        return null;
    }

    public static Long getLong(Object object) {
        return getLong(object, null);
    }

    public static Long toLong(Object object) {
        return getLong(object, 0L);
    }

    public static Long getLong(Object object, Long defaultValue) {
        if (object == null) {
            return defaultValue;
        }
        if (object instanceof Long) {
            return (Long) object;
        }
        if (object instanceof String) {
            return parseLong((String) object);
        }
        if (object instanceof Integer) {
            return Long.valueOf((Integer) object);
        }
        if (object instanceof BigInteger) {
            return ((BigInteger) object).longValue();
        }
        return defaultValue;
    }

    public static BigInteger getBigInteger(Object object) {
        if (object == null) {
            return null;
        }
        if (object instanceof BigInteger) {
            return (BigInteger) object;
        }
        if (object instanceof String) {
            return parseBigInteger((String) object);
        }
        if (object instanceof Long) {
            return BigInteger.valueOf((Long) object);
        }
        if (object instanceof Integer) {
            return BigInteger.valueOf((Integer) object);
        }
        return null;
    }

    public static Double getDouble(Object object) {
        if (object == null) {
            return null;
        }
        if (object instanceof Double) {
            return (Double) object;
        }
        if (object instanceof String) {
            return parseDouble((String) object);
        }
        return null;
    }

    public static double getNativeDouble(Object source) {
        Double value = getDouble(source);
        return value != null ? value : 0;
    }

    public static Double parseDouble(String source) {
        if (StringUtils.isEmpty(source)) {
            return null;
        }
        try {
            return Double.valueOf(source);
        } catch (NumberFormatException exception) {
            return null;
        }
    }

    public static boolean equals(Integer first, Integer second) {
        if (first == null || second == null) {
            return false;
        }
        return first.equals(second);
    }

    public static boolean greaterZero(Long value) {
        if (value == null) {
            return false;
        }
        return value > 0;
    }

    public static Long add(Long... values) {
        long result = 0L;
        if (ArrayUtils.isNotEmpty(values)) {
            for (Long value : values) {
                if (value != null) {
                    result += value;
                }
            }
        }
        return result;
    }

    public static Integer parseInteger(String source) {
        if (StringUtils.isEmpty(source)) {
            return null;
        }
        try {
            return Integer.valueOf(source);
        } catch (NumberFormatException exception) {
            return null;
        }
    }

    public static Long parseLong(String source) {
        if (StringUtils.isEmpty(source)) {
            return null;
        }
        try {
            return Long.valueOf(source);
        } catch (NumberFormatException exception) {
            return null;
        }
    }

    public static BigInteger parseBigInteger(String source) {
        if (StringUtils.isEmpty(source)) {
            return null;
        }
        try {
            return new BigInteger(source);
        } catch (NumberFormatException exception) {
            return null;
        }
    }
}
