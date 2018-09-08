package org.sandbox.hibernate;

import java.io.Closeable;
import java.util.Iterator;

public interface Scroll<T> extends Iterable<T>, Iterator<T>, Closeable {
}
