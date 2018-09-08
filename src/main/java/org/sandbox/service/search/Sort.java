package org.sandbox.service.search;

import org.sandbox.utils.Format;
import org.sandbox.utils.StringJoiner;
import org.springframework.util.Assert;

import java.io.Serializable;

public class Sort implements Serializable {

    private String property;
    private Boolean desc;

    private Sort(String property) {
        this.property = property;
    }

    private Sort(String property, Boolean desc) {
        this.property = property;
        this.desc = desc;
    }

    public static Sort create(String property, Boolean desc) {
        Assert.hasLength(property, () -> "Empty property for Sort.create");
        return new Sort(property, desc);
    }

    public static Sort asc(String property) {
        Assert.hasLength(property, () -> "Empty property for Sort.asc");
        return new Sort(property);
    }

    public static Sort desc(String property) {
        Assert.hasLength(property, () -> "Empty property for Sort.desc");
        return new Sort(property, Boolean.TRUE);
    }

    @Override
    public String toString() {
        StringJoiner joiner = StringJoiner.on(Format.DOT, Format.OPEN_BRACE, Format.CLOSED_BRACE);
        joiner.add(property);
        joiner.add(desc);
        return joiner.toString();
    }

    public String getProperty() {
        return property;
    }

    protected void setProperty(String property) {
        this.property = property;
    }

    public boolean isDesc() {
        return desc != null ? desc : false;
    }
}
