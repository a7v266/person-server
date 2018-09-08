package org.sandbox.domain;

import org.sandbox.utils.Format;
import org.sandbox.utils.StringJoiner;

public class ErrorCollectorException extends RuntimeException {

    private ErrorCollector errorCollector;

    public ErrorCollectorException(String error) {
        errorCollector = new ErrorCollector();
        errorCollector.add(error);
    }

    public ErrorCollectorException(String error, Object... parameters) {
        errorCollector = new ErrorCollector();
        errorCollector.add(error, parameters);
    }

    public ErrorCollectorException(Throwable throwable) {
        errorCollector = new ErrorCollector();
        errorCollector.add(throwable);
    }

    public ErrorCollectorException(ErrorCollector errorCollector) {
        this.errorCollector = errorCollector;
    }

    public ErrorCollector getErrorCollector() {
        return errorCollector;
    }

    @Override
    public String getMessage() {
        return StringJoiner.on(Format.COLON_SPACE).skipEmpty().join(errorCollector.getTitle(), StringJoiner.on(Format.COMMA_SPACE).skipEmpty().join(errorCollector.getErrors()));
    }

    @Override
    public String toString() {
        return getMessage();
    }
}
