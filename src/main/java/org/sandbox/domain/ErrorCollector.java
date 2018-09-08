package org.sandbox.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.sandbox.config.Messages;
import org.sandbox.utils.*;
import org.xml.sax.SAXParseException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class ErrorCollector {

    @JsonProperty
    private String title;

    @JsonProperty
    private Collection<String> errors = new ArrayList<>();

    public ErrorCollector() {
    }

    public ErrorCollector(String title) {
        this.title = title;
    }

    public ErrorCollector(String title, Object... parameters) {
        this.title = StringUtils.isTemplate(title) ? StringUtils.format(title, parameters) : title;
    }

    public ErrorCollector(Collection<String> errors) {
        this.errors = errors;
    }

    public void add(Collection<String> errors) {
        if (errors == null || errors.isEmpty()) {
            return;
        }
        this.errors.addAll(errors);
    }

    public void add(ErrorCollector errorCollector) {
        if (errorCollector == null) {
            return;
        }
        if (errorCollector.hasErrors()) {
            errorCollector.getErrors().forEach(error -> errors.add(StringJoiner.on(Format.COLON_SPACE).skipEmpty().join(errorCollector.getTitle(), error)));
        }
    }

    public void add(Throwable throwable) {
        while (throwable.getCause() != null && throwable != throwable.getCause()) {
            throwable = throwable.getCause();
        }
        if (throwable instanceof ErrorCollectorException) {
            ErrorCollectorException errorCollectorException = (ErrorCollectorException) throwable;
            add(errorCollectorException.getErrorCollector());
        } else if (throwable instanceof SAXParseException) {
            SAXParseException parseException = (SAXParseException) throwable;
            add(Messages.ERROR_PARSING_FORMAT, parseException.getLineNumber(), parseException.getColumnNumber(), parseException.getMessage());
        } else {
            add(throwable.getMessage());
        }
    }

    public void add(String format, Object... values) {
        errors.add(StringUtils.format(format, values));
    }

    public Collection<String> getErrors() {
        return errors;
    }

    public boolean hasErrors() {
        return errors.size() > 0;
    }

    @JsonIgnore
    public boolean isValid() {
        return errors.isEmpty();
    }

    public void assertNotNull(Object value, Supplier<String> supplier) {
        if (value == null) {
            errors.add(supplier.get());
        }
    }

    public void assertNotNull(Object value, String errorMessage, Object... parameters) {
        if (value == null) {
            add(errorMessage, parameters);
        }
    }

    public void assertNotEmpty(String value, Supplier<String> supplier) {
        if (value == null || value.isEmpty()) {
            add(supplier.get());
        }
    }

    public void assertNotEmpty(String value, String errorMessage, Object... parameters) {
        if (value == null || value.isEmpty()) {
            add(errorMessage, parameters);
        }
    }

    public void assertNotEmpty(Collection<?> collection, String errorMessage) {
        if (CollectionUtils.isEmpty(collection)) {
            add(errorMessage);
        }
    }

    public void assertOneItem(Collection<?> collection, String errorMessage) {
        if (CollectionUtils.isEmpty(collection)) {
            return;
        }
        if (collection.size() != 1) {
            add(errorMessage);
        }
    }

    public void assertMaxLength(String value, int length, Supplier<String> supplier) {
        if (value == null) {
            return;
        }
        if (value.length() <= length) {
            return;
        }
        errors.add(supplier.get());
    }

    public void assertMaxLength(String value, int length, String errorMessage) {
        if (value == null) {
            return;
        }
        if (value.length() <= length) {
            return;
        }
        errors.add(errorMessage);
    }

    public void assertEnumId(Integer enumId, Class<? extends Enum<?>> enumClass, Supplier<String> supplier) {
        if (enumId == null) {
            return;
        }
        for (Object item : enumClass.getEnumConstants()) {
            if (item instanceof Reference) {
                if (enumId.equals(((Reference) item).getId())) {
                    return;
                }
            }
        }
        errors.add(supplier.get());
    }

    public void assertEnumId(Integer enumId, Class<? extends Enum<?>> enumClass, String errorMessage) {
        if (enumId == null) {
            return;
        }
        for (Object item : enumClass.getEnumConstants()) {
            if (item instanceof Reference) {
                if (enumId.equals(((Reference) item).getId())) {
                    return;
                }
            }
        }
        errors.add(errorMessage);
    }

    public void assertPattern(String value, String pattern, Supplier<String> supplier) {
        if (value == null) {
            return;
        }
        if (value.matches(pattern)) {
            return;
        }
        errors.add(supplier.get());
    }

    public void assertPattern(String value, String pattern, String errorMessage, Object... parameters) {
        if (value == null) {
            return;
        }
        if (value.matches(pattern)) {
            return;
        }
        add(errorMessage, parameters);
    }

    public <T extends Comparable<T>> void assertLess(T first, T second, String errorMessage, Object... parameters) {
        if (first == null || second == null) {
            return;
        }
        if (first.compareTo(second) < 0) {
            return;
        }
        add(errorMessage, parameters);
    }

    public <T extends Comparable<T>> void assertLessOrEquals(T first, T second, String errorMessage, Object... parameters) {
        if (first == null || second == null) {
            return;
        }
        if (first.compareTo(second) <= 0) {
            return;
        }
        add(errorMessage, parameters);
    }

    public void assertTrue(boolean condition, String errorMessage, Object... parameters) {
        if (condition) {
            return;
        }
        add(errorMessage, parameters);
    }

    public <T extends Comparable<T>> void assertEquals(T first, T second, String errorMessage, Object... parameters) {
        if (ObjectUtils.equals(first, second)) {
            return;
        }
        add(errorMessage, parameters);
    }

    public <T extends Comparable<T>> void assertNotEquals(T first, T second, String errorMessage, Object... parameters) {
        if (ObjectUtils.notEquals(first, second)) {
            return;
        }
        add(errorMessage, parameters);
    }

    public void assertNull(Object object, String message) {
        if (object == null) {
            return;
        }
        add(message);
    }

    public void assertNull(Object object, String format, Object... parameters) {
        if (object == null) {
            return;
        }
        add(format, parameters);
    }

    public void assertNotExist(String message, Object object) {
        if (object == null) {
            return;
        }
        add(StringUtils.format(message, object));
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTitle(String format, Object... parameters) {
        this.title = String.format(format, parameters);
    }

    public void throwException() {
        if (hasErrors()) {
            throw new ErrorCollectorException(this);
        }
    }

    public void throwException(Consumer<ErrorCollector> consumer) {
        if (hasErrors()) {
            consumer.accept(this);
            throw new ErrorCollectorException(this);
        }
    }

    public String createErrorMessage() {
        if (errors.size() == 0) {
            return null;
        }
        return StringJoiner.on(Format.COLON_SPACE).skipEmpty().join(title, StringJoiner.on(Format.COMMA_SPACE).join(errors));
    }
}
