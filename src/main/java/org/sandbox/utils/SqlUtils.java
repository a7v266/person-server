package org.sandbox.utils;

import org.postgresql.util.PGobject;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SqlUtils {

    public static final String ITEM_DELIMITER = ",";

    public static Integer getInteger(ResultSet resultSet, String field) throws SQLException {
        return NumberUtils.getInteger(resultSet.getObject(field));
    }

    public static Long getLong(ResultSet resultSet, String field) throws SQLException {
        return NumberUtils.getLong(resultSet.getObject(field));
    }

    public static String getString(ResultSet resultSet, String field) throws SQLException {
        Object object = resultSet.getObject(field);
        if (object == null) {
            return null;
        }
        if (object instanceof String) {
            return StringUtils.valueOf(StringUtils.trim((String) object));
        }
        if (object instanceof PGobject) {
            return StringUtils.valueOf(((PGobject) object).getValue());
        }
        return null;
    }

    public static List<String> getStringList(ResultSet resultSet, String field) throws SQLException {
        String source = resultSet.getString(field);
        if (source == null || source.isEmpty()) {
            return null;
        }
        List<String> list = new ArrayList<>();
        for (String item : source.split(ITEM_DELIMITER, -1)) {
            list.add(item);
        }
        return list;
    }

    public static BigDecimal getBigDecimal(ResultSet resultSet, String field) throws SQLException {
        return (BigDecimal) resultSet.getObject(field);
    }

    public static Boolean getBoolean(ResultSet resultSet, String field) throws SQLException {
        return resultSet.getBoolean(field);
    }

    public static LocalDate getLocalDate(ResultSet resultSet, String field) throws SQLException {
        return DateUtils.createLocalDate(resultSet.getDate(field));
    }

    public static Instant getInstant(ResultSet resultSet, String field) throws SQLException {
        return DateUtils.getInstant(resultSet.getTimestamp(field));
    }

    public static String formatResultSet(ResultSet resultSet) {
        try {
            ResultSetMetaData metaData = resultSet.getMetaData();
            StringJoiner joiner = new StringJoiner(Format.COMMA_SPACE);
            for (int index = 1; index <= metaData.getColumnCount(); index++) {
                joiner.add(resultSet.getObject(index));
            }
            return StringJoiner.on().join(Format.OPEN_BRACE, joiner.toString(), Format.CLOSED_BRACE);
        } catch (Exception exception) {
            return Format.EMPTY_STRING;
        }
    }
}
