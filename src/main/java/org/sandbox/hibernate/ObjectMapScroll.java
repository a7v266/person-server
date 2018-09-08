package org.sandbox.hibernate;

import org.hibernate.ScrollableResults;

import java.util.Iterator;
import java.util.Map;

public class ObjectMapScroll implements Scroll<Map<String, Object>> {

    private ScrollableMapResults results;

    private static final String METHOD_GET_RESULT_SET = "getResultSet";

    public ObjectMapScroll(ScrollableResults results) {
        this.results = new ScrollableMapResults(results);
    }

    @Override
    public Iterator<Map<String, Object>> iterator() {
        return this;
    }

    @Override
    public boolean hasNext() {
        if (results == null) {
            return false;
        }
        return results.next();
    }

    @Override
    public Map<String, Object> next() {
        if (results == null) {
            return null;
        }
        return results.getResultMap();
    }

    @Override
    public void close() {
        if (results != null) {
            results.close();
        }
    }
}
