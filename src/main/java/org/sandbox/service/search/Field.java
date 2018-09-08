package org.sandbox.service.search;

import com.google.common.base.Joiner;
import org.sandbox.utils.Format;

public class Field {
    public static String path(String... names) {
        return Joiner.on(Format.DOT).skipNulls().join(names);
    }
}
