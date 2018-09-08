package org.sandbox.utils;

import org.sandbox.config.Loggers;

import java.time.DateTimeException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.TemporalAccessor;

public class DateFormatter {

    public final static String PATTERN_RUS_DATE = "dd.MM.yyyy";
    public final static String PATTERN_RUS_DATE_TIME = "dd.MM.yyyy HH:mm:ss";
    public final static String PATTERN_ISO_DATE = "yyyy-MM-dd";
    public final static String PATTERN_SQL_DATE_TIME = "yyyy-MM-dd HH:mm:ss";

    public static String format(LocalDate value) {
        return format(value, PATTERN_RUS_DATE);
    }

    public static String format(LocalDateTime value) {
        return format(value, PATTERN_RUS_DATE_TIME);
    }

    public static String format(Instant value) {
        return format(value, PATTERN_RUS_DATE_TIME);
    }

    public static LocalDate parseLocalDate(String value) {
        return parseLocalDate(value, PATTERN_RUS_DATE);
    }

    public static LocalDateTime parseLocalDateTime(String value) {
        return parseLocalDateTime(value, PATTERN_RUS_DATE_TIME);
    }

    public static String format(TemporalAccessor value, String pattern) {
        if (value == null) {
            return Format.EMPTY_STRING;
        }
        return format(value, DateTimeFormatter.ofPattern(pattern));
    }

    public static String format(Instant value, String pattern) {
        if (value == null) {
            return Format.EMPTY_STRING;
        }
        return format(DateUtils.createLocalDateTime(value), DateTimeFormatter.ofPattern(pattern));
    }

    public static LocalDate parseLocalDate(String value, String pattern) {
        if (StringUtils.isEmpty(value)) {
            return null;
        }
        return parseLocalDate(value, DateTimeFormatter.ofPattern(pattern));
    }

    public static LocalDateTime parseLocalDateTime(String value, String pattern) {
        if (StringUtils.isEmpty(value)) {
            return null;
        }
        return parseLocalDateTime(value, DateTimeFormatter.ofPattern(pattern));
    }

    private static String format(TemporalAccessor value, DateTimeFormatter formatter) {
        try {
            return formatter.format(value);
        } catch (DateTimeException exception) {
            Loggers.SYSTEM_LOGGER.error(exception.getMessage(), exception);
        }
        return null;
    }

    private static LocalDate parseLocalDate(String value, DateTimeFormatter formatter) {
        try {
            return LocalDate.parse(value, formatter);
        } catch (DateTimeParseException exception) {
            Loggers.SYSTEM_LOGGER.error(exception.getMessage(), exception);
        }
        return null;
    }

    private static LocalDateTime parseLocalDateTime(String value, DateTimeFormatter formatter) {
        try {
            return LocalDateTime.parse(value, formatter);
        } catch (DateTimeParseException exception) {
            Loggers.SYSTEM_LOGGER.error(exception.getMessage(), exception);
        }
        return null;
    }
}
