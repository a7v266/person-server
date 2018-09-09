package org.sandbox.domain;

public enum ClientType implements Reference {

    LEGAL_ENTITY(1, "Юридическое лицо"),
    SELF_EMPLOYED(2, "Индивидуальный предприниматель"),
    NATURAL_PERSON(3, "Физическое лицо");

    private Integer id;
    private String title;

    ClientType(Integer id, String title) {
        this.id = id;
        this.title = title;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public String getTitle() {
        return title;
    }
}
