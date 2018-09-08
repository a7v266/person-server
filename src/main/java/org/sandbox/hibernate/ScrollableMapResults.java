package org.sandbox.hibernate;

import org.hibernate.ScrollableResults;
import org.hibernate.internal.AbstractScrollableResults;
import org.sandbox.utils.MapUtils;

import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Map;

public class ScrollableMapResults {

    private ScrollableResults results;

    private static final String METHOD_GET_RESULT_SET = "getResultSet";

    public ScrollableMapResults(ScrollableResults results) {
        this.results = results;
    }

    public boolean next() {
        return results.next();
    }

    public void close() {
        this.results.close();
    }

    public Map<String, Object> getResultMap() {
        try {
            Map<String, Object> result = MapUtils.createHashMap();
            Method method = AbstractScrollableResults.class.getDeclaredMethod(METHOD_GET_RESULT_SET);
            method.setAccessible(true);
            ResultSet resultSet = (ResultSet) method.invoke(results);
            ResultSetMetaData metaData = resultSet.getMetaData();
            for (int index = 1; index <= metaData.getColumnCount(); index++) {
                result.put(metaData.getColumnName(index), resultSet.getObject(index));
            }
            return result;

        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }
}
