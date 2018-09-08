package org.sandbox.validation;

import javax.validation.ConstraintValidatorContext;
import java.util.Collection;

public class Validator {

    private boolean valid = true;
    private boolean interrupted = false;

    private final ConstraintValidatorContext context;

    public Validator(ConstraintValidatorContext context) {
        this.context = context;
        this.context.disableDefaultConstraintViolation();
    }

    public boolean isValid() {
        return valid;
    }

    public void setInterrupted(boolean interrupted) {
        this.interrupted = interrupted;
    }

    protected void addViolationMessage(Collection<String> messages) {
        if (messages == null) {
            return;
        }
        if (messages.isEmpty()) {
            return;
        }
        messages.forEach(this::addViolationMessage);
    }

    protected void addViolationMessage(String message) {
        context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
        valid = false;
    }

    protected void assertNotNull(Object object, String message) {
        if (interrupted && !valid) {
            return;
        }
        if (object == null) {
            addViolationMessage(message);
        }
    }

    protected void assertNotEmpty(Iterable<?> iterable, String message) {
        if (interrupted && !valid) {
            return;
        }
        if (iterable == null || !iterable.iterator().hasNext()) {
            addViolationMessage(message);
        }
    }

    protected void assertNotEmpty(String value, String errorMessage) {
        if (interrupted && !valid) {
            return;
        }
        if (value == null || value.isEmpty()) {
            addViolationMessage(errorMessage);
        }
    }

    protected void assertTrue(boolean condition, String errorMessage) {
        if (interrupted && !valid) {
            return;
        }
        if (!condition) {
            addViolationMessage(errorMessage);
        }
    }

    public void assertIsNull(Object value, String message) {
        if (interrupted && !valid) {
            return;
        }
        if (value != null) {
            addViolationMessage(message);
        }
    }

    public void assertEquals(Object first, Object last, String message) {
        if (interrupted && !valid) {
            return;
        }
        if (first != null && last != null && first.equals(last)) {
            return;
        }
        addViolationMessage(message);
    }

    public void assertLessOrEquals(Comparable first, Comparable last, String message) {
        if (interrupted && !valid) {
            return;
        }
        if (first != null && last != null && first.compareTo(last) <= 0) {
            return;
        }
        addViolationMessage(message);
    }
}